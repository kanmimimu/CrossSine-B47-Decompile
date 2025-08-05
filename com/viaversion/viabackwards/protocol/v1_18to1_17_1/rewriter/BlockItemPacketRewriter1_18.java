package com.viaversion.viabackwards.protocol.v1_18to1_17_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viabackwards.protocol.v1_18to1_17_1.data.BlockEntityMappings1_17_1;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class BlockItemPacketRewriter1_18 extends BackwardsItemRewriter {
   public BlockItemPacketRewriter1_18(Protocol1_18To1_17_1 protocol) {
      super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
   }

   protected void registerPackets() {
      (new RecipeRewriter(this.protocol)).register(ClientboundPackets1_18.UPDATE_RECIPES);
      this.registerCooldown(ClientboundPackets1_18.COOLDOWN);
      this.registerSetContent1_17_1(ClientboundPackets1_18.CONTAINER_SET_CONTENT);
      this.registerSetSlot1_17_1(ClientboundPackets1_18.CONTAINER_SET_SLOT);
      this.registerSetEquipment(ClientboundPackets1_18.SET_EQUIPMENT);
      this.registerMerchantOffers(ClientboundPackets1_18.MERCHANT_OFFERS);
      this.registerAdvancements(ClientboundPackets1_18.UPDATE_ADVANCEMENTS);
      this.registerContainerClick1_17_1(ServerboundPackets1_17.CONTAINER_CLICK);
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.LEVEL_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BLOCK_POSITION1_14);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.INT, 0);
               int data = (Integer)wrapper.get(Types.INT, 1);
               if (id == 1010) {
                  wrapper.set(Types.INT, 1, ((Protocol1_18To1_17_1)BlockItemPacketRewriter1_18.this.protocol).getMappingData().getNewItemId(data));
               }

            });
         }
      });
      this.registerSetCreativeModeSlot(ServerboundPackets1_17.SET_CREATIVE_MODE_SLOT);
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.LEVEL_PARTICLES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.INT, 0);
               if (id == 3) {
                  int blockState = (Integer)wrapper.read(Types.VAR_INT);
                  if (blockState == 7786) {
                     wrapper.set(Types.INT, 0, 3);
                  } else {
                     wrapper.set(Types.INT, 0, 2);
                  }

               } else {
                  ParticleMappings mappings = ((Protocol1_18To1_17_1)BlockItemPacketRewriter1_18.this.protocol).getMappingData().getParticleMappings();
                  if (mappings.isBlockParticle(id)) {
                     int data = (Integer)wrapper.passthrough(Types.VAR_INT);
                     wrapper.set(Types.VAR_INT, 0, ((Protocol1_18To1_17_1)BlockItemPacketRewriter1_18.this.protocol).getMappingData().getNewBlockStateId(data));
                  } else if (mappings.isItemParticle(id)) {
                     BlockItemPacketRewriter1_18.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                  }

                  int newId = ((Protocol1_18To1_17_1)BlockItemPacketRewriter1_18.this.protocol).getMappingData().getNewParticleId(id);
                  if (newId != id) {
                     wrapper.set(Types.INT, 0, newId);
                  }

               }
            });
         }
      });
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_14);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.read(Types.VAR_INT);
               CompoundTag tag = (CompoundTag)wrapper.read(Types.NAMED_COMPOUND_TAG);
               int mappedId = BlockEntityMappings1_17_1.mappedId(id);
               if (mappedId == -1) {
                  wrapper.cancel();
               } else {
                  String identifier = (String)((Protocol1_18To1_17_1)BlockItemPacketRewriter1_18.this.protocol).getMappingData().blockEntities().get(id);
                  if (identifier == null) {
                     wrapper.cancel();
                  } else {
                     CompoundTag newTag = tag == null ? new CompoundTag() : tag;
                     BlockPosition pos = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_14, 0);
                     newTag.putString("id", Key.namespaced(identifier));
                     newTag.putInt("x", pos.x());
                     newTag.putInt("y", pos.y());
                     newTag.putInt("z", pos.z());
                     BlockItemPacketRewriter1_18.this.handleSpawner(id, newTag);
                     wrapper.write(Types.UNSIGNED_BYTE, (short)mappedId);
                     wrapper.write(Types.NAMED_COMPOUND_TAG, newTag);
                  }
               }
            });
         }
      });
      ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_18.LEVEL_CHUNK_WITH_LIGHT, ClientboundPackets1_17_1.LEVEL_CHUNK, (wrapper) -> {
         EntityTracker tracker = ((Protocol1_18To1_17_1)this.protocol).getEntityRewriter().tracker(wrapper.user());
         ChunkType1_18 chunkType = new ChunkType1_18(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_18To1_17_1)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
         Chunk oldChunk = (Chunk)wrapper.read(chunkType);
         ChunkSection[] sections = oldChunk.getSections();
         BitSet mask = new BitSet(oldChunk.getSections().length);
         int[] biomeData = new int[sections.length * 64];
         int biomeIndex = 0;

         for(int j = 0; j < sections.length; ++j) {
            ChunkSection section = sections[j];
            DataPalette biomePalette = section.palette(PaletteType.BIOMES);

            for(int i = 0; i < 64; ++i) {
               biomeData[biomeIndex++] = biomePalette.idAt(i);
            }

            if (section.getNonAirBlocksCount() == 0) {
               sections[j] = null;
            } else {
               mask.set(j);
            }
         }

         List<CompoundTag> blockEntityTags = new ArrayList(oldChunk.blockEntities().size());

         for(BlockEntity blockEntity : oldChunk.blockEntities()) {
            String id = (String)((Protocol1_18To1_17_1)this.protocol).getMappingData().blockEntities().get(blockEntity.typeId());
            if (id != null) {
               CompoundTag tag;
               if (blockEntity.tag() != null) {
                  tag = blockEntity.tag();
                  this.handleSpawner(blockEntity.typeId(), tag);
               } else {
                  tag = new CompoundTag();
               }

               blockEntityTags.add(tag);
               tag.putInt("x", (oldChunk.getX() << 4) + blockEntity.sectionX());
               tag.putInt("y", blockEntity.y());
               tag.putInt("z", (oldChunk.getZ() << 4) + blockEntity.sectionZ());
               tag.putString("id", Key.namespaced(id));
            }
         }

         Chunk chunk = new BaseChunk(oldChunk.getX(), oldChunk.getZ(), true, false, mask, oldChunk.getSections(), biomeData, oldChunk.getHeightMap(), blockEntityTags);
         wrapper.write(new ChunkType1_17(tracker.currentWorldSectionHeight()), chunk);
         PacketWrapper lightPacket = wrapper.create(ClientboundPackets1_17_1.LIGHT_UPDATE);
         lightPacket.write(Types.VAR_INT, chunk.getX());
         lightPacket.write(Types.VAR_INT, chunk.getZ());
         lightPacket.write(Types.BOOLEAN, (Boolean)wrapper.read(Types.BOOLEAN));
         lightPacket.write(Types.LONG_ARRAY_PRIMITIVE, (long[])wrapper.read(Types.LONG_ARRAY_PRIMITIVE));
         lightPacket.write(Types.LONG_ARRAY_PRIMITIVE, (long[])wrapper.read(Types.LONG_ARRAY_PRIMITIVE));
         lightPacket.write(Types.LONG_ARRAY_PRIMITIVE, (long[])wrapper.read(Types.LONG_ARRAY_PRIMITIVE));
         lightPacket.write(Types.LONG_ARRAY_PRIMITIVE, (long[])wrapper.read(Types.LONG_ARRAY_PRIMITIVE));
         int skyLightLength = (Integer)wrapper.read(Types.VAR_INT);
         lightPacket.write(Types.VAR_INT, skyLightLength);

         for(int i = 0; i < skyLightLength; ++i) {
            lightPacket.write(Types.BYTE_ARRAY_PRIMITIVE, (byte[])wrapper.read(Types.BYTE_ARRAY_PRIMITIVE));
         }

         int blockLightLength = (Integer)wrapper.read(Types.VAR_INT);
         lightPacket.write(Types.VAR_INT, blockLightLength);

         for(int i = 0; i < blockLightLength; ++i) {
            lightPacket.write(Types.BYTE_ARRAY_PRIMITIVE, (byte[])wrapper.read(Types.BYTE_ARRAY_PRIMITIVE));
         }

         lightPacket.send(Protocol1_18To1_17_1.class);
      });
      ((Protocol1_18To1_17_1)this.protocol).cancelClientbound(ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
   }

   void handleSpawner(int typeId, CompoundTag tag) {
      if (typeId == 8) {
         CompoundTag spawnData = tag.getCompoundTag("SpawnData");
         CompoundTag entity;
         if (spawnData != null && (entity = spawnData.getCompoundTag("entity")) != null) {
            tag.put("SpawnData", entity);
         }
      }

   }
}
