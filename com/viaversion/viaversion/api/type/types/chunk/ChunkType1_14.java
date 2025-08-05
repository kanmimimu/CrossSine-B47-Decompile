package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkType1_14 extends Type {
   public static final Type TYPE = new ChunkType1_14();

   public ChunkType1_14() {
      super(Chunk.class);
   }

   public Chunk read(ByteBuf input) {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int primaryBitmask = Types.VAR_INT.readPrimitive(input);
      CompoundTag heightMap = (CompoundTag)Types.NAMED_COMPOUND_TAG.read(input);
      ByteBuf data = input.readSlice(Types.VAR_INT.readPrimitive(input));
      ChunkSection[] sections = new ChunkSection[16];

      for(int i = 0; i < 16; ++i) {
         if ((primaryBitmask & 1 << i) != 0) {
            short nonAirBlocksCount = data.readShort();
            ChunkSection section = (ChunkSection)Types1_13.CHUNK_SECTION.read(data);
            section.setNonAirBlocksCount(nonAirBlocksCount);
            sections[i] = section;
         }
      }

      int[] biomeData = fullChunk ? new int[256] : null;
      if (fullChunk) {
         for(int i = 0; i < 256; ++i) {
            biomeData[i] = data.readInt();
         }
      }

      List<CompoundTag> nbtData = new ArrayList(Arrays.asList((CompoundTag[])Types.NAMED_COMPOUND_TAG_ARRAY.read(input)));
      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      Types.VAR_INT.writePrimitive(output, chunk.getBitmask());
      Types.NAMED_COMPOUND_TAG.write(output, chunk.getHeightMap());
      ByteBuf buf = output.alloc().buffer();

      try {
         for(int i = 0; i < 16; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               buf.writeShort(section.getNonAirBlocksCount());
               Types1_13.CHUNK_SECTION.write(buf, section);
            }
         }

         buf.readerIndex(0);
         Types.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      if (chunk.isBiomeData()) {
         for(int value : chunk.getBiomeData()) {
            output.writeInt(value & 255);
         }
      }

      Types.NAMED_COMPOUND_TAG_ARRAY.write(output, (CompoundTag[])chunk.getBlockEntities().toArray(new CompoundTag[0]));
   }
}
