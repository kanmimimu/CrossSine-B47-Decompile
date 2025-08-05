package com.viaversion.viaversion.protocols.v1_16_1to1_16_2.rewriter;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.List;

public class WorldPacketRewriter1_16_2 {
   private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

   public static void register(Protocol1_16_1To1_16_2 protocol) {
      BlockRewriter<ClientboundPackets1_16> blockRewriter = BlockRewriter.for1_14(protocol);
      blockRewriter.registerBlockEvent(ClientboundPackets1_16.BLOCK_EVENT);
      blockRewriter.registerBlockUpdate(ClientboundPackets1_16.BLOCK_UPDATE);
      blockRewriter.registerBlockBreakAck(ClientboundPackets1_16.BLOCK_BREAK_ACK);
      blockRewriter.registerLevelChunk(ClientboundPackets1_16.LEVEL_CHUNK, ChunkType1_16.TYPE, ChunkType1_16_2.TYPE);
      protocol.registerClientbound(ClientboundPackets1_16.CHUNK_BLOCKS_UPDATE, ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE, (wrapper) -> {
         wrapper.cancel();
         int chunkX = (Integer)wrapper.read(Types.INT);
         int chunkZ = (Integer)wrapper.read(Types.INT);
         long chunkPosition = 0L;
         chunkPosition |= ((long)chunkX & 4194303L) << 42;
         chunkPosition |= ((long)chunkZ & 4194303L) << 20;
         List<BlockChangeRecord>[] sectionRecords = new List[16];
         BlockChangeRecord[] blockChangeRecord = (BlockChangeRecord[])wrapper.read(Types.BLOCK_CHANGE_ARRAY);

         for(BlockChangeRecord record : blockChangeRecord) {
            int chunkY = record.getY() >> 4;
            List<BlockChangeRecord> list = sectionRecords[chunkY];
            if (list == null) {
               sectionRecords[chunkY] = list = new ArrayList();
            }

            int blockId = protocol.getMappingData().getNewBlockStateId(record.getBlockId());
            list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
         }

         for(int chunkY = 0; chunkY < sectionRecords.length; ++chunkY) {
            List<BlockChangeRecord> sectionRecord = sectionRecords[chunkY];
            if (sectionRecord != null) {
               PacketWrapper newPacket = wrapper.create(ClientboundPackets1_16_2.SECTION_BLOCKS_UPDATE);
               newPacket.write(Types.LONG, chunkPosition | (long)chunkY & 1048575L);
               newPacket.write(Types.BOOLEAN, false);
               newPacket.write(Types.VAR_LONG_BLOCK_CHANGE_ARRAY, (BlockChangeRecord[])sectionRecord.toArray(EMPTY_RECORDS));
               newPacket.send(Protocol1_16_1To1_16_2.class);
            }
         }

      });
      blockRewriter.registerLevelEvent(ClientboundPackets1_16.LEVEL_EVENT, 1010, 2001);
   }
}
