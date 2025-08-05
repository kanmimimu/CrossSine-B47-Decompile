package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public final class DyedColor {
   final int rgb;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(DyedColor.class) {
      public DyedColor read(ByteBuf buffer) {
         int rgb = buffer.readInt();
         boolean showInTooltip = buffer.readBoolean();
         return new DyedColor(rgb, showInTooltip);
      }

      public void write(ByteBuf buffer, DyedColor value) {
         buffer.writeInt(value.rgb);
         buffer.writeBoolean(value.showInTooltip);
      }
   };

   public DyedColor(int rgb, boolean showInTooltip) {
      this.rgb = rgb;
      this.showInTooltip = showInTooltip;
   }

   public int rgb() {
      return this.rgb;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DyedColor)) {
         return false;
      } else {
         DyedColor var2 = (DyedColor)var1;
         return this.rgb == var2.rgb && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return (0 * 31 + Integer.hashCode(this.rgb)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[rgb=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Integer.toString(this.rgb), Boolean.toString(this.showInTooltip));
   }
}
