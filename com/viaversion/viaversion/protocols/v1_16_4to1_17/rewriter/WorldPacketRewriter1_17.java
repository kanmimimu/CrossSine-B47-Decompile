package com.viaversion.viaversion.protocols.v1_16_4to1_17.rewriter;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class WorldPacketRewriter1_17 {
   public static void register(Protocol1_16_4To1_17 protocol) {
      BlockRewriter<ClientboundPackets1_16_2> blockRewriter = BlockRewriter.for1_14(protocol);
      blockRewriter.registerBlockEvent(ClientboundPackets1_16_2.BLOCK_EVENT);
      blockRewriter.registerBlockUpdate(ClientboundPackets1_16_2.BLOCK_UPDATE);
      blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE);
      blockRewriter.registerBlockBreakAck(ClientboundPackets1_16_2.BLOCK_BREAK_ACK);
      protocol.registerClientbound(ClientboundPackets1_16_2.SET_BORDER, (ClientboundPacketType)null, (wrapper) -> {
         int type = (Integer)wrapper.read(Types.VAR_INT);
         ClientboundPackets1_17 var10000;
         switch (type) {
            case 0:
               var10000 = ClientboundPackets1_17.SET_BORDER_SIZE;
               break;
            case 1:
               var10000 = ClientboundPackets1_17.SET_BORDER_LERP_SIZE;
               break;
            case 2:
               var10000 = ClientboundPackets1_17.SET_BORDER_CENTER;
               break;
            case 3:
               var10000 = ClientboundPackets1_17.INITIALIZE_BORDER;
               break;
            case 4:
               var10000 = ClientboundPackets1_17.SET_BORDER_WARNING_DELAY;
               break;
            case 5:
               var10000 = ClientboundPackets1_17.SET_BORDER_WARNING_DISTANCE;
               break;
            default:
               throw new IllegalArgumentException("Invalid world border type received: " + type);
         }

         ClientboundPacketType packetType = var10000;
         wrapper.setPacketType(packetType);
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.LIGHT_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int skyLightMask = (Integer)wrapper.read(Types.VAR_INT);
               int blockLightMask = (Integer)wrapper.read(Types.VAR_INT);
               wrapper.write(Types.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(skyLightMask));
               wrapper.write(Types.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(blockLightMask));
               wrapper.write(Types.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((Integer)wrapper.read(Types.VAR_INT)));
               wrapper.write(Types.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((Integer)wrapper.read(Types.VAR_INT)));
               this.writeLightArrays(wrapper, skyLightMask);
               this.writeLightArrays(wrapper, blockLightMask);
            });
         }

         void writeLightArrays(PacketWrapper wrapper, int bitMask) {
            List<byte[]> light = new ArrayList();

            for(int i = 0; i < 18; ++i) {
               if (this.isSet(bitMask, i)) {
                  light.add((byte[])wrapper.read(Types.BYTE_ARRAY_PRIMITIVE));
               }
            }

            wrapper.write(Types.VAR_INT, light.size());

            for(byte[] bytes : light) {
               wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, bytes);
            }

         }

         long[] toBitSetLongArray(int bitmask) {
            return new long[]{(long)bitmask};
         }

         boolean isSet(int mask, int i) {
            return (mask & 1 << i) != 0;
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.LEVEL_CHUNK, (wrapper) -> {
         Chunk chunk = (Chunk)wrapper.read(ChunkType1_16_2.TYPE);
         if (!chunk.isFullChunk()) {
            writeMultiBlockChangePacket(wrapper, chunk);
            wrapper.cancel();
         } else {
            wrapper.write(new ChunkType1_17(chunk.getSections().length), chunk);
            chunk.setChunkMask(BitSet.valueOf(new long[]{(long)chunk.getBitmask()}));
            blockRewriter.handleChunk(chunk);
         }
      });
      blockRewriter.registerLevelEvent(ClientboundPackets1_16_2.LEVEL_EVENT, 1010, 2001);
   }

   static void writeMultiBlockChangePacket(PacketWrapper wrapper, Chunk chunk) {
      long chunkPosition = ((long)chunk.getX() & 4194303L) << 42;
      chunkPosition |= ((long)chunk.getZ() & 4194303L) << 20;
      ChunkSection[] sections = chunk.getSections();

      for(int chunkY = 0; chunkY < sections.length; ++chunkY) {
         ChunkSection section = sections[chunkY];
         if (section != null) {
            PacketWrapper blockChangePacket = wrapper.create(ClientboundPackets1_17.SECTION_BLOCKS_UPDATE);
            blockChangePacket.write(Types.LONG, chunkPosition | (long)chunkY & 1048575L);
            blockChangePacket.write(Types.BOOLEAN, true);
            BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[4096];
            DataPalette palette = section.palette(PaletteType.BLOCKS);
            int j = 0;

            for(int x = 0; x < 16; ++x) {
               for(int y = 0; y < 16; ++y) {
                  for(int z = 0; z < 16; ++z) {
                     int blockStateId = Protocol1_16_4To1_17.MAPPINGS.getNewBlockStateId(palette.idAt(x, y, z));
                     blockChangeRecords[j++] = new BlockChangeRecord1_16_2(x, y, z, blockStateId);
                  }
               }
            }

            blockChangePacket.write(Types.VAR_LONG_BLOCK_CHANGE_ARRAY, blockChangeRecords);
            blockChangePacket.send(Protocol1_16_4To1_17.class);
         }
      }

   }
}
