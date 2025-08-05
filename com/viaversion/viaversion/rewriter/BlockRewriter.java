package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.MathUtil;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockRewriter {
   final Protocol protocol;
   final Type positionType;
   final Type compoundTagType;

   public BlockRewriter(Protocol protocol, Type positionType, Type compoundTagType) {
      this.protocol = protocol;
      this.positionType = positionType;
      this.compoundTagType = compoundTagType;
   }

   public static BlockRewriter legacy(Protocol protocol) {
      return new BlockRewriter(protocol, Types.BLOCK_POSITION1_8, Types.NAMED_COMPOUND_TAG);
   }

   public static BlockRewriter for1_14(Protocol protocol) {
      return new BlockRewriter(protocol, Types.BLOCK_POSITION1_14, Types.NAMED_COMPOUND_TAG);
   }

   public static BlockRewriter for1_20_2(Protocol protocol) {
      return new BlockRewriter(protocol, Types.BLOCK_POSITION1_14, Types.COMPOUND_TAG);
   }

   public void registerBlockEvent(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(BlockRewriter.this.positionType);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               if (BlockRewriter.this.protocol.getMappingData().getBlockMappings() != null) {
                  int id = (Integer)wrapper.get(Types.VAR_INT, 0);
                  int mappedId = BlockRewriter.this.protocol.getMappingData().getNewBlockId(id);
                  if (mappedId == -1) {
                     wrapper.cancel();
                  } else {
                     wrapper.set(Types.VAR_INT, 0, mappedId);
                  }
               }
            });
         }
      }));
   }

   public void registerBlockUpdate(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(BlockRewriter.this.positionType);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> wrapper.set(Types.VAR_INT, 0, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId((Integer)wrapper.get(Types.VAR_INT, 0))));
         }
      }));
   }

   public void registerChunkBlocksUpdate(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Types.BLOCK_CHANGE_ARRAY)) {
                  record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      }));
   }

   public void registerSectionBlocksUpdate(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.LONG);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                  record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      }));
   }

   public void registerSectionBlocksUpdate1_20(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.LONG);
            this.handler((wrapper) -> {
               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                  record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
               }

            });
         }
      }));
   }

   public void registerBlockBreakAck(ClientboundPacketType packetType) {
      this.registerBlockUpdate(packetType);
   }

   public void registerLevelEvent(ClientboundPacketType packetType, int playRecordId, int blockBreakId) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int id = (Integer)wrapper.passthrough(Types.INT);
         wrapper.passthrough(this.positionType);
         int data = (Integer)wrapper.read(Types.INT);
         MappingData mappingData = this.protocol.getMappingData();
         if (playRecordId != -1 && id == playRecordId && mappingData.getItemMappings() != null) {
            wrapper.write(Types.INT, mappingData.getNewItemId(data));
         } else if (id == blockBreakId && mappingData.getBlockStateMappings() != null) {
            wrapper.write(Types.INT, mappingData.getNewBlockStateId(data));
         } else {
            wrapper.write(Types.INT, data);
         }

      }));
   }

   public void registerLevelEvent1_21(ClientboundPacketType packetType, int blockBreakId) {
      this.registerLevelEvent(packetType, -1, blockBreakId);
   }

   public void registerLevelChunk(ClientboundPacketType packetType, Type chunkType, Type newChunkType) {
      this.registerLevelChunk(packetType, chunkType, newChunkType, (BiConsumer)null);
   }

   public void registerLevelChunk(ClientboundPacketType packetType, Type chunkType, Type newChunkType, @Nullable BiConsumer chunkRewriter) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         Chunk chunk = (Chunk)wrapper.read(chunkType);
         wrapper.write(newChunkType, chunk);
         this.handleChunk(chunk);
         if (chunkRewriter != null) {
            chunkRewriter.accept(wrapper.user(), chunk);
         }

      }));
   }

   public void handleChunk(Chunk chunk) {
      for(int s = 0; s < chunk.getSections().length; ++s) {
         ChunkSection section = chunk.getSections()[s];
         if (section != null) {
            DataPalette palette = section.palette(PaletteType.BLOCKS);

            for(int i = 0; i < palette.size(); ++i) {
               int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
               palette.setIdByIndex(i, mappedBlockStateId);
            }
         }
      }

   }

   public void registerLevelChunk1_19(ClientboundPacketType packetType, ChunkTypeSupplier chunkTypeSupplier) {
      this.registerLevelChunk1_19(packetType, chunkTypeSupplier, (BiConsumer)null);
   }

   public void registerLevelChunk1_19(ClientboundPacketType packetType, ChunkTypeSupplier chunkTypeSupplier, @Nullable BiConsumer blockEntityHandler) {
      this.protocol.registerClientbound(packetType, this.chunkHandler1_19(chunkTypeSupplier, blockEntityHandler));
   }

   public PacketHandler chunkHandler1_19(ChunkTypeSupplier chunkTypeSupplier, @Nullable BiConsumer blockEntityHandler) {
      return (wrapper) -> {
         Chunk chunk = this.handleChunk1_19(wrapper, chunkTypeSupplier);
         Mappings blockEntityMappings = this.protocol.getMappingData().getBlockEntityMappings();
         if (blockEntityMappings != null || blockEntityHandler != null) {
            List<BlockEntity> blockEntities = chunk.blockEntities();

            for(int i = 0; i < blockEntities.size(); ++i) {
               BlockEntity blockEntity = (BlockEntity)blockEntities.get(i);
               if (blockEntityMappings != null) {
                  int id = blockEntity.typeId();
                  int mappedId = blockEntityMappings.getNewIdOrDefault(id, id);
                  if (id != mappedId) {
                     blockEntities.set(i, blockEntity.withTypeId(mappedId));
                  }
               }

               if (blockEntityHandler != null && blockEntity.tag() != null) {
                  blockEntityHandler.accept(wrapper.user(), blockEntity);
               }
            }
         }

      };
   }

   public Chunk handleChunk1_19(PacketWrapper wrapper, ChunkTypeSupplier chunkTypeSupplier) {
      EntityTracker tracker = this.protocol.getEntityRewriter().tracker(wrapper.user());
      Preconditions.checkArgument(tracker.biomesSent() != -1, "Biome count not set");
      Preconditions.checkArgument(tracker.currentWorldSectionHeight() != -1, "Section height not set");
      Type<Chunk> chunkType = chunkTypeSupplier.supply(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(this.protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
      Chunk chunk = (Chunk)wrapper.passthrough(chunkType);

      for(ChunkSection section : chunk.getSections()) {
         DataPalette blockPalette = section.palette(PaletteType.BLOCKS);

         for(int i = 0; i < blockPalette.size(); ++i) {
            int id = blockPalette.idByIndex(i);
            blockPalette.setIdByIndex(i, this.protocol.getMappingData().getNewBlockStateId(id));
         }
      }

      return chunk;
   }

   public void registerBlockEntityData(ClientboundPacketType packetType) {
      this.registerBlockEntityData(packetType, (Consumer)null);
   }

   public void registerBlockEntityData(ClientboundPacketType packetType, @Nullable Consumer blockEntityHandler) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         BlockPosition position = (BlockPosition)wrapper.passthrough(this.positionType);
         int blockEntityId = (Integer)wrapper.read(Types.VAR_INT);
         Mappings mappings = this.protocol.getMappingData().getBlockEntityMappings();
         if (mappings != null) {
            wrapper.write(Types.VAR_INT, mappings.getNewIdOrDefault(blockEntityId, blockEntityId));
         } else {
            wrapper.write(Types.VAR_INT, blockEntityId);
         }

         CompoundTag tag;
         if (blockEntityHandler != null && (tag = (CompoundTag)wrapper.passthrough(this.compoundTagType)) != null) {
            BlockEntity blockEntity = new BlockEntityImpl(BlockEntity.pack(position.x(), position.z()), (short)position.y(), blockEntityId, tag);
            blockEntityHandler.accept(blockEntity);
         }

      }));
   }

   @FunctionalInterface
   public interface ChunkTypeSupplier {
      Type supply(int var1, int var2, int var3);
   }
}
