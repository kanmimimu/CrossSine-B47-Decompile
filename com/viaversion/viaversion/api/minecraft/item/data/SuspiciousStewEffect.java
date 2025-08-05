package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;

public final class SuspiciousStewEffect {
   final int mobEffect;
   final int duration;
   public static final Type TYPE = new Type(SuspiciousStewEffect.class) {
      public SuspiciousStewEffect read(ByteBuf buffer) {
         int effect = Types.VAR_INT.readPrimitive(buffer);
         int duration = Types.VAR_INT.readPrimitive(buffer);
         return new SuspiciousStewEffect(effect, duration);
      }

      public void write(ByteBuf buffer, SuspiciousStewEffect value) {
         Types.VAR_INT.writePrimitive(buffer, value.mobEffect);
         Types.VAR_INT.writePrimitive(buffer, value.duration);
      }
   };
   public static final Type ARRAY_TYPE;

   public SuspiciousStewEffect(int mobEffect, int duration) {
      this.mobEffect = mobEffect;
      this.duration = duration;
   }

   public int mobEffect() {
      return this.mobEffect;
   }

   public int duration() {
      return this.duration;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SuspiciousStewEffect)) {
         return false;
      } else {
         SuspiciousStewEffect var2 = (SuspiciousStewEffect)var1;
         return this.mobEffect == var2.mobEffect && this.duration == var2.duration;
      }
   }

   public int hashCode() {
      return (0 * 31 + Integer.hashCode(this.mobEffect)) * 31 + Integer.hashCode(this.duration);
   }

   public String toString() {
      return String.format("%s[mobEffect=%s, duration=%s]", this.getClass().getSimpleName(), Integer.toString(this.mobEffect), Integer.toString(this.duration));
   }
}
