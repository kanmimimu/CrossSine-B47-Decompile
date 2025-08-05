package com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.data.BlockEntities1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.data.BlockEntityMappings1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.storage.ChunkLightStorage;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class WorldPacketRewriter1_18 {
   public static void register(Protocol1_17_1To1_18 protocol) {
      protocol.registerClientbound(ClientboundPackets1_17_1.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_14);
            this.handler((wrapper) -> {
               short id = (Short)wrapper.read(Types.UNSIGNED_BYTE);
               int newId = BlockEntityMappings1_18.newId(id);
               wrapper.write(Types.VAR_INT, newId);
               WorldPacketRewriter1_18.handleSpawners(newId, (CompoundTag)wrapper.passthrough(Types.NAMED_COMPOUND_TAG));
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_17_1.LIGHT_UPDATE, (wrapper) -> {
         int chunkX = (Integer)wrapper.passthrough(Types.VAR_INT);
         int chunkZ = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).isLoaded(chunkX, chunkZ)) {
            if (!Via.getConfig().cache1_17Light()) {
               return;
            }
         } else {
            wrapper.cancel();
         }

         boolean trustEdges = (Boolean)wrapper.passthrough(Types.BOOLEAN);
         long[] skyLightMask = (long[])wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
         long[] blockLightMask = (long[])wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
         long[] emptySkyLightMask = (long[])wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
         long[] emptyBlockLightMask = (long[])wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
         int skyLightLenght = (Integer)wrapper.passthrough(Types.VAR_INT);
         byte[][] skyLight = new byte[skyLightLenght][];

         for(int i = 0; i < skyLightLenght; ++i) {
            skyLight[i] = (byte[])wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
         }

         int blockLightLength = (Integer)wrapper.passthrough(Types.VAR_INT);
         byte[][] blockLight = new byte[blockLightLength][];

         for(int i = 0; i < blockLightLength; ++i) {
            blockLight[i] = (byte[])wrapper.passthrough(Types.BYTE_ARRAY_PRIMITIVE);
         }

         ChunkLightStorage lightStorage = (ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class);
         lightStorage.storeLight(chunkX, chunkZ, new ChunkLightStorage.ChunkLight(trustEdges, skyLightMask, blockLightMask, emptySkyLightMask, emptyBlockLightMask, skyLight, blockLight));
      });
      protocol.registerClientbound(ClientboundPackets1_17_1.LEVEL_CHUNK, ClientboundPackets1_18.LEVEL_CHUNK_WITH_LIGHT, (wrapper) -> {
         EntityTracker tracker = protocol.getEntityRewriter().tracker(wrapper.user());
         Chunk oldChunk = (Chunk)wrapper.read(new ChunkType1_17(tracker.currentWorldSectionHeight()));
         List<BlockEntity> blockEntities = new ArrayList(oldChunk.getBlockEntities().size());

         for(CompoundTag tag : oldChunk.getBlockEntities()) {
            NumberTag xTag = tag.getNumberTag("x");
            NumberTag yTag = tag.getNumberTag("y");
            NumberTag zTag = tag.getNumberTag("z");
            StringTag idTag = tag.getStringTag("id");
            if (xTag != null && yTag != null && zTag != null && idTag != null) {
               String id = idTag.getValue();
               int typeId = BlockEntities1_18.blockEntityIds().getInt(Key.stripMinecraftNamespace(id));
               if (typeId == -1) {
                  protocol.getLogger().warning("Unknown block entity: " + id);
               }

               handleSpawners(typeId, tag);
               byte packedXZ = (byte)((xTag.asInt() & 15) << 4 | zTag.asInt() & 15);
               blockEntities.add(new BlockEntityImpl(packedXZ, yTag.asShort(), typeId, tag));
            }
         }

         int[] biomeData = oldChunk.getBiomeData();
         ChunkSection[] sections = oldChunk.getSections();

         for(int i = 0; i < sections.length; ++i) {
            ChunkSection section = sections[i];
            if (section == null) {
               section = new ChunkSectionImpl();
               sections[i] = section;
               section.setNonAirBlocksCount(0);
               DataPaletteImpl blockPalette = new DataPaletteImpl(4096);
               blockPalette.addId(0);
               section.addPalette(PaletteType.BLOCKS, blockPalette);
            }

            DataPaletteImpl biomePalette = new DataPaletteImpl(64);
            section.addPalette(PaletteType.BIOMES, biomePalette);
            int offset = i * 64;
            int biomeIndex = 0;

            for(int biomeArrayIndex = offset; biomeIndex < 64; ++biomeArrayIndex) {
               int biome = biomeData[biomeArrayIndex];
               biomePalette.setIdAt(biomeIndex, biome != -1 ? biome : 0);
               ++biomeIndex;
            }
         }

         Chunk chunk = new Chunk1_18(oldChunk.getX(), oldChunk.getZ(), sections, oldChunk.getHeightMap(), blockEntities);
         wrapper.write(new ChunkType1_18(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent())), chunk);
         ChunkLightStorage lightStorage = (ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class);
         boolean alreadyLoaded = !lightStorage.addLoadedChunk(chunk.getX(), chunk.getZ());
         ChunkLightStorage.ChunkLight light = Via.getConfig().cache1_17Light() ? lightStorage.getLight(chunk.getX(), chunk.getZ()) : lightStorage.removeLight(chunk.getX(), chunk.getZ());
         if (light == null) {
            ProtocolLogger var10000 = protocol.getLogger();
            int var10001 = chunk.getX();
            int var19 = chunk.getZ();
            int var18 = var10001;
            var10000.warning("No light data found for chunk at " + var18 + ", " + var19 + ". Chunk was already loaded: " + alreadyLoaded);
            BitSet emptyLightMask = new BitSet();
            emptyLightMask.set(0, tracker.currentWorldSectionHeight() + 2);
            wrapper.write(Types.BOOLEAN, false);
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, new long[0]);
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, new long[0]);
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, 0);
         } else {
            wrapper.write(Types.BOOLEAN, light.trustEdges());
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, light.skyLightMask());
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, light.blockLightMask());
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, light.emptySkyLightMask());
            wrapper.write(Types.LONG_ARRAY_PRIMITIVE, light.emptyBlockLightMask());
            wrapper.write(Types.VAR_INT, light.skyLight().length);

            for(byte[] skyLight : light.skyLight()) {
               wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, skyLight);
            }

            wrapper.write(Types.VAR_INT, light.blockLight().length);

            for(byte[] blockLight : light.blockLight()) {
               wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, blockLight);
            }
         }

      });
      protocol.registerClientbound(ClientboundPackets1_17_1.FORGET_LEVEL_CHUNK, (wrapper) -> {
         int chunkX = (Integer)wrapper.passthrough(Types.INT);
         int chunkZ = (Integer)wrapper.passthrough(Types.INT);
         ((ChunkLightStorage)wrapper.user().get(ChunkLightStorage.class)).clear(chunkX, chunkZ);
      });
   }

   static void handleSpawners(int typeId, CompoundTag tag) {
      if (typeId == 8) {
         CompoundTag entity = tag.getCompoundTag("SpawnData");
         if (entity != null) {
            CompoundTag spawnData = new CompoundTag();
            tag.put("SpawnData", spawnData);
            spawnData.put("entity", entity);
         }
      }

   }
}
