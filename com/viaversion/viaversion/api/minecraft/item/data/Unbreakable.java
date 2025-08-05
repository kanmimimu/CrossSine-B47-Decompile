package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public final class Unbreakable {
   final boolean showInTooltip;
   public static final Type TYPE = new Type(Unbreakable.class) {
      public Unbreakable read(ByteBuf buffer) {
         return new Unbreakable(buffer.readBoolean());
      }

      public void write(ByteBuf buffer, Unbreakable value) {
         buffer.writeBoolean(value.showInTooltip());
      }
   };

   public Unbreakable(boolean showInTooltip) {
      this.showInTooltip = showInTooltip;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Unbreakable)) {
         return false;
      } else {
         Unbreakable var2 = (Unbreakable)var1;
         return this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return 0 * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[showInTooltip=%s]", this.getClass().getSimpleName(), Boolean.toString(this.showInTooltip));
   }
}
