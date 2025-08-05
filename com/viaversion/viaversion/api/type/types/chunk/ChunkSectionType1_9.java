package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public class ChunkSectionType1_9 extends Type {
   private static final int GLOBAL_PALETTE = 13;

   public ChunkSectionType1_9() {
      super(ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) {
      int bitsPerBlock = buffer.readUnsignedByte();
      if (bitsPerBlock < 4) {
         bitsPerBlock = 4;
      }

      if (bitsPerBlock > 8) {
         bitsPerBlock = 13;
      }

      int paletteLength = Types.VAR_INT.readPrimitive(buffer);
      ChunkSection chunkSection = bitsPerBlock != 13 ? new ChunkSectionImpl(true, paletteLength) : new ChunkSectionImpl(true);
      DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);

      for(int i = 0; i < paletteLength; ++i) {
         if (bitsPerBlock != 13) {
            blockPalette.addId(Types.VAR_INT.readPrimitive(buffer));
         } else {
            Types.VAR_INT.readPrimitive(buffer);
         }
      }

      long[] blockData = (long[])Types.LONG_ARRAY_PRIMITIVE.read(buffer);
      if (blockData.length > 0) {
         int expectedLength = (int)Math.ceil((double)(4096 * bitsPerBlock) / (double)64.0F);
         if (blockData.length == expectedLength) {
            BiIntConsumer var10003;
            if (bitsPerBlock == 13) {
               Objects.requireNonNull(blockPalette);
               var10003 = blockPalette::setIdAt;
            } else {
               Objects.requireNonNull(blockPalette);
               var10003 = blockPalette::setPaletteIndexAt;
            }

            CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, var10003);
         }
      }

      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection chunkSection) {
      int bitsPerBlock = 4;

      DataPalette blockPalette;
      for(blockPalette = chunkSection.palette(PaletteType.BLOCKS); blockPalette.size() > 1 << bitsPerBlock; ++bitsPerBlock) {
      }

      if (bitsPerBlock > 8) {
         bitsPerBlock = 13;
      }

      buffer.writeByte(bitsPerBlock);
      if (bitsPerBlock != 13) {
         Types.VAR_INT.writePrimitive(buffer, blockPalette.size());

         for(int i = 0; i < blockPalette.size(); ++i) {
            Types.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
         }
      } else {
         Types.VAR_INT.writePrimitive(buffer, 0);
      }

      IntToLongFunction var10002;
      if (bitsPerBlock == 13) {
         Objects.requireNonNull(blockPalette);
         var10002 = blockPalette::idAt;
      } else {
         Objects.requireNonNull(blockPalette);
         var10002 = blockPalette::paletteIndexAt;
      }

      long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, var10002);
      Types.LONG_ARRAY_PRIMITIVE.write(buffer, data);
   }
}
