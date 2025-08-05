package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class FoodEffect {
   final PotionEffect effect;
   final float probability;
   public static final Type TYPE = new Type(FoodEffect.class) {
      public FoodEffect read(ByteBuf buffer) {
         PotionEffect effect = (PotionEffect)PotionEffect.TYPE.read(buffer);
         float probability = buffer.readFloat();
         return new FoodEffect(effect, probability);
      }

      public void write(ByteBuf buffer, FoodEffect value) {
         PotionEffect.TYPE.write(buffer, value.effect);
         buffer.writeFloat(value.probability);
      }
   };
   public static final Type ARRAY_TYPE;

   public FoodEffect(PotionEffect effect, float probability) {
      this.effect = effect;
      this.probability = probability;
   }

   public PotionEffect effect() {
      return this.effect;
   }

   public float probability() {
      return this.probability;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof FoodEffect)) {
         return false;
      } else {
         FoodEffect var2 = (FoodEffect)var1;
         return Objects.equals(this.effect, var2.effect) && Float.compare(this.probability, var2.probability) == 0;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.effect)) * 31 + Float.hashCode(this.probability);
   }

   public String toString() {
      return String.format("%s[effect=%s, probability=%s]", this.getClass().getSimpleName(), Objects.toString(this.effect), Float.toString(this.probability));
   }
}
