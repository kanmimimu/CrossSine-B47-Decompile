package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public final class PaletteType1_18 extends Type {
   private final int globalPaletteBits;
   private final PaletteType type;

   public PaletteType1_18(PaletteType type, int globalPaletteBits) {
      super(DataPalette.class);
      this.globalPaletteBits = globalPaletteBits;
      this.type = type;
   }

   public DataPalette read(ByteBuf buffer) {
      int bitsPerValue = buffer.readByte();
      if (bitsPerValue == 0) {
         DataPaletteImpl palette = new DataPaletteImpl(this.type.size(), 1);
         palette.addId(Types.VAR_INT.readPrimitive(buffer));
         Types.LONG_ARRAY_PRIMITIVE.read(buffer);
         return palette;
      } else {
         if (bitsPerValue >= 0 && bitsPerValue <= this.type.highestBitsPerValue()) {
            if (this.type == PaletteType.BLOCKS && bitsPerValue < 4) {
               bitsPerValue = 4;
            }
         } else {
            bitsPerValue = this.globalPaletteBits;
         }

         DataPaletteImpl palette;
         if (bitsPerValue != this.globalPaletteBits) {
            int paletteLength = Types.VAR_INT.readPrimitive(buffer);
            palette = new DataPaletteImpl(this.type.size(), paletteLength);

            for(int i = 0; i < paletteLength; ++i) {
               palette.addId(Types.VAR_INT.readPrimitive(buffer));
            }
         } else {
            palette = new DataPaletteImpl(this.type.size());
         }

         long[] values = (long[])Types.LONG_ARRAY_PRIMITIVE.read(buffer);
         if (values.length > 0) {
            int valuesPerLong = (char)(64 / bitsPerValue);
            int expectedLength = (this.type.size() + valuesPerLong - 1) / valuesPerLong;
            if (values.length == expectedLength) {
               int var10001 = this.type.size();
               BiIntConsumer var10003;
               if (bitsPerValue == this.globalPaletteBits) {
                  Objects.requireNonNull(palette);
                  var10003 = palette::setIdAt;
               } else {
                  Objects.requireNonNull(palette);
                  var10003 = palette::setPaletteIndexAt;
               }

               CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerValue, var10001, values, var10003);
            }
         }

         return palette;
      }
   }

   public void write(ByteBuf buffer, DataPalette palette) {
      int size = palette.size();
      if (size == 1) {
         buffer.writeByte(0);
         Types.VAR_INT.writePrimitive(buffer, palette.idByIndex(0));
         Types.VAR_INT.writePrimitive(buffer, 0);
      } else {
         int min = this.type == PaletteType.BLOCKS ? 4 : 1;
         int bitsPerValue = Math.max(min, MathUtil.ceilLog2(size));
         if (bitsPerValue > this.type.highestBitsPerValue()) {
            bitsPerValue = this.globalPaletteBits;
         }

         buffer.writeByte(bitsPerValue);
         if (bitsPerValue != this.globalPaletteBits) {
            Types.VAR_INT.writePrimitive(buffer, size);

            for(int i = 0; i < size; ++i) {
               Types.VAR_INT.writePrimitive(buffer, palette.idByIndex(i));
            }
         }

         Type var10000 = Types.LONG_ARRAY_PRIMITIVE;
         int var10003 = this.type.size();
         IntToLongFunction var10004;
         if (bitsPerValue == this.globalPaletteBits) {
            Objects.requireNonNull(palette);
            var10004 = palette::idAt;
         } else {
            Objects.requireNonNull(palette);
            var10004 = palette::paletteIndexAt;
         }

         var10000.write(buffer, CompactArrayUtil.createCompactArrayWithPadding(bitsPerValue, var10003, var10004));
      }
   }
}
