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

public class ChunkSectionType1_16 extends Type {
   private static final int GLOBAL_PALETTE = 15;

   public ChunkSectionType1_16() {
      super(ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) {
      int bitsPerBlock = buffer.readUnsignedByte();
      if (bitsPerBlock > 8) {
         bitsPerBlock = 15;
      } else if (bitsPerBlock < 4) {
         bitsPerBlock = 4;
      }

      ChunkSection chunkSection;
      if (bitsPerBlock != 15) {
         int paletteLength = Types.VAR_INT.readPrimitive(buffer);
         chunkSection = new ChunkSectionImpl(false, paletteLength);
         DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);

         for(int i = 0; i < paletteLength; ++i) {
            blockPalette.addId(Types.VAR_INT.readPrimitive(buffer));
         }
      } else {
         chunkSection = new ChunkSectionImpl(false);
      }

      long[] blockData = (long[])Types.LONG_ARRAY_PRIMITIVE.read(buffer);
      if (blockData.length > 0) {
         char valuesPerLong = (char)(64 / bitsPerBlock);
         int expectedLength = (4096 + valuesPerLong - 1) / valuesPerLong;
         if (blockData.length == expectedLength) {
            DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
            BiIntConsumer var10003;
            if (bitsPerBlock == 15) {
               Objects.requireNonNull(blockPalette);
               var10003 = blockPalette::setIdAt;
            } else {
               Objects.requireNonNull(blockPalette);
               var10003 = blockPalette::setPaletteIndexAt;
            }

            CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerBlock, 4096, blockData, var10003);
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
         bitsPerBlock = 15;
      }

      buffer.writeByte(bitsPerBlock);
      if (bitsPerBlock != 15) {
         Types.VAR_INT.writePrimitive(buffer, blockPalette.size());

         for(int i = 0; i < blockPalette.size(); ++i) {
            Types.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
         }
      }

      IntToLongFunction var10002;
      if (bitsPerBlock == 15) {
         Objects.requireNonNull(blockPalette);
         var10002 = blockPalette::idAt;
      } else {
         Objects.requireNonNull(blockPalette);
         var10002 = blockPalette::paletteIndexAt;
      }

      long[] data = CompactArrayUtil.createCompactArrayWithPadding(bitsPerBlock, 4096, var10002);
      Types.LONG_ARRAY_PRIMITIVE.write(buffer, data);
   }
}
