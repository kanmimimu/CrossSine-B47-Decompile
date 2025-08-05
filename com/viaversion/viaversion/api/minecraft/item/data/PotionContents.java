package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PotionContents {
   final @Nullable Integer potion;
   final @Nullable Integer customColor;
   final PotionEffect[] customEffects;
   public static final Type TYPE = new Type(PotionContents.class) {
      public PotionContents read(ByteBuf buffer) {
         Integer potion = buffer.readBoolean() ? Types.VAR_INT.readPrimitive(buffer) : null;
         Integer customColor = buffer.readBoolean() ? buffer.readInt() : null;
         PotionEffect[] customEffects = (PotionEffect[])PotionEffect.ARRAY_TYPE.read(buffer);
         return new PotionContents(potion, customColor, customEffects);
      }

      public void write(ByteBuf buffer, PotionContents value) {
         buffer.writeBoolean(value.potion != null);
         if (value.potion != null) {
            Types.VAR_INT.writePrimitive(buffer, value.potion);
         }

         buffer.writeBoolean(value.customColor != null);
         if (value.customColor != null) {
            buffer.writeInt(value.customColor);
         }

         PotionEffect.ARRAY_TYPE.write(buffer, value.customEffects);
      }
   };

   public PotionContents(@Nullable Integer potion, @Nullable Integer customColor, PotionEffect[] customEffects) {
      this.potion = potion;
      this.customColor = customColor;
      this.customEffects = customEffects;
   }

   public @Nullable Integer potion() {
      return this.potion;
   }

   public @Nullable Integer customColor() {
      return this.customColor;
   }

   public PotionEffect[] customEffects() {
      return this.customEffects;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof PotionContents)) {
         return false;
      } else {
         PotionContents var2 = (PotionContents)var1;
         return Objects.equals(this.potion, var2.potion) && Objects.equals(this.customColor, var2.customColor) && Objects.equals(this.customEffects, var2.customEffects);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.potion)) * 31 + Objects.hashCode(this.customColor)) * 31 + Objects.hashCode(this.customEffects);
   }

   public String toString() {
      return String.format("%s[potion=%s, customColor=%s, customEffects=%s]", this.getClass().getSimpleName(), Objects.toString(this.potion), Objects.toString(this.customColor), Objects.toString(this.customEffects));
   }
}
