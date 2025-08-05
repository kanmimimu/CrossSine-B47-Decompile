package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class BannerPatternLayer {
   final Holder pattern;
   final int dyeColor;
   public static final Type TYPE = new Type(BannerPatternLayer.class) {
      public BannerPatternLayer read(ByteBuf buffer) {
         Holder<BannerPattern> pattern = BannerPattern.TYPE.read(buffer);
         int color = Types.VAR_INT.readPrimitive(buffer);
         return new BannerPatternLayer(pattern, color);
      }

      public void write(ByteBuf buffer, BannerPatternLayer value) {
         BannerPattern.TYPE.write(buffer, value.pattern);
         Types.VAR_INT.writePrimitive(buffer, value.dyeColor);
      }
   };
   public static final Type ARRAY_TYPE;

   public BannerPatternLayer(Holder pattern, int dyeColor) {
      this.pattern = pattern;
      this.dyeColor = dyeColor;
   }

   public Holder pattern() {
      return this.pattern;
   }

   public int dyeColor() {
      return this.dyeColor;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BannerPatternLayer)) {
         return false;
      } else {
         BannerPatternLayer var2 = (BannerPatternLayer)var1;
         return Objects.equals(this.pattern, var2.pattern) && this.dyeColor == var2.dyeColor;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.pattern)) * 31 + Integer.hashCode(this.dyeColor);
   }

   public String toString() {
      return String.format("%s[pattern=%s, dyeColor=%s]", this.getClass().getSimpleName(), Objects.toString(this.pattern), Integer.toString(this.dyeColor));
   }
}
