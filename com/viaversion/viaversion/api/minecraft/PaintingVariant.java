package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class PaintingVariant {
   final int width;
   final int height;
   final String assetId;
   public static HolderType TYPE = new HolderType() {
      public PaintingVariant readDirect(ByteBuf buffer) {
         int width = Types.VAR_INT.readPrimitive(buffer);
         int height = Types.VAR_INT.readPrimitive(buffer);
         String assetId = (String)Types.STRING.read(buffer);
         return new PaintingVariant(width, height, assetId);
      }

      public void writeDirect(ByteBuf buffer, PaintingVariant variant) {
         Types.VAR_INT.writePrimitive(buffer, variant.width());
         Types.VAR_INT.writePrimitive(buffer, variant.height());
         Types.STRING.write(buffer, variant.assetId());
      }
   };

   public PaintingVariant(int width, int height, String assetId) {
      this.width = width;
      this.height = height;
      this.assetId = assetId;
   }

   public int width() {
      return this.width;
   }

   public int height() {
      return this.height;
   }

   public String assetId() {
      return this.assetId;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PaintingVariant)) {
         return false;
      } else {
         PaintingVariant var2 = (PaintingVariant)var1;
         return this.width == var2.width && this.height == var2.height && Objects.equals(this.assetId, var2.assetId);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height)) * 31 + Objects.hashCode(this.assetId);
   }

   public String toString() {
      return String.format("%s[width=%s, height=%s, assetId=%s]", this.getClass().getSimpleName(), Integer.toString(this.width), Integer.toString(this.height), Objects.toString(this.assetId));
   }
}
