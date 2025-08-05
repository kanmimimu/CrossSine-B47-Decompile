package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class FoodProperties {
   final int nutrition;
   final float saturationModifier;
   final boolean canAlwaysEat;
   final float eatSeconds;
   final Item usingConvertsTo;
   final FoodEffect[] possibleEffects;
   public static final Type TYPE1_20_5 = new Type(FoodProperties.class) {
      public FoodProperties read(ByteBuf buffer) {
         int nutrition = Types.VAR_INT.readPrimitive(buffer);
         float saturationModifier = buffer.readFloat();
         boolean canAlwaysEat = buffer.readBoolean();
         float eatSeconds = buffer.readFloat();
         FoodEffect[] possibleEffects = (FoodEffect[])FoodEffect.ARRAY_TYPE.read(buffer);
         return new FoodProperties(nutrition, saturationModifier, canAlwaysEat, eatSeconds, (Item)null, possibleEffects);
      }

      public void write(ByteBuf buffer, FoodProperties value) {
         Types.VAR_INT.writePrimitive(buffer, value.nutrition);
         buffer.writeFloat(value.saturationModifier);
         buffer.writeBoolean(value.canAlwaysEat);
         buffer.writeFloat(value.eatSeconds);
         FoodEffect.ARRAY_TYPE.write(buffer, value.possibleEffects);
      }
   };
   public static final Type TYPE1_21 = new Type(FoodProperties.class) {
      public FoodProperties read(ByteBuf buffer) {
         int nutrition = Types.VAR_INT.readPrimitive(buffer);
         float saturationModifier = buffer.readFloat();
         boolean canAlwaysEat = buffer.readBoolean();
         float eatSeconds = buffer.readFloat();
         Item usingConvertsTo = (Item)Types1_21.OPTIONAL_ITEM.read(buffer);
         FoodEffect[] possibleEffects = (FoodEffect[])FoodEffect.ARRAY_TYPE.read(buffer);
         return new FoodProperties(nutrition, saturationModifier, canAlwaysEat, eatSeconds, usingConvertsTo, possibleEffects);
      }

      public void write(ByteBuf buffer, FoodProperties value) {
         Types.VAR_INT.writePrimitive(buffer, value.nutrition);
         buffer.writeFloat(value.saturationModifier);
         buffer.writeBoolean(value.canAlwaysEat);
         buffer.writeFloat(value.eatSeconds);
         Types1_21.OPTIONAL_ITEM.write(buffer, value.usingConvertsTo);
         FoodEffect.ARRAY_TYPE.write(buffer, value.possibleEffects);
      }
   };

   public FoodProperties(int nutrition, float saturationModifier, boolean canAlwaysEat, float eatSeconds, Item usingConvertsTo, FoodEffect[] possibleEffects) {
      this.nutrition = nutrition;
      this.saturationModifier = saturationModifier;
      this.canAlwaysEat = canAlwaysEat;
      this.eatSeconds = eatSeconds;
      this.usingConvertsTo = usingConvertsTo;
      this.possibleEffects = possibleEffects;
   }

   public int nutrition() {
      return this.nutrition;
   }

   public float saturationModifier() {
      return this.saturationModifier;
   }

   public boolean canAlwaysEat() {
      return this.canAlwaysEat;
   }

   public float eatSeconds() {
      return this.eatSeconds;
   }

   public Item usingConvertsTo() {
      return this.usingConvertsTo;
   }

   public FoodEffect[] possibleEffects() {
      return this.possibleEffects;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof FoodProperties)) {
         return false;
      } else {
         FoodProperties var2 = (FoodProperties)var1;
         return this.nutrition == var2.nutrition && Float.compare(this.saturationModifier, var2.saturationModifier) == 0 && this.canAlwaysEat == var2.canAlwaysEat && Float.compare(this.eatSeconds, var2.eatSeconds) == 0 && Objects.equals(this.usingConvertsTo, var2.usingConvertsTo) && Objects.equals(this.possibleEffects, var2.possibleEffects);
      }
   }

   public int hashCode() {
      return (((((0 * 31 + Integer.hashCode(this.nutrition)) * 31 + Float.hashCode(this.saturationModifier)) * 31 + Boolean.hashCode(this.canAlwaysEat)) * 31 + Float.hashCode(this.eatSeconds)) * 31 + Objects.hashCode(this.usingConvertsTo)) * 31 + Objects.hashCode(this.possibleEffects);
   }

   public String toString() {
      return String.format("%s[nutrition=%s, saturationModifier=%s, canAlwaysEat=%s, eatSeconds=%s, usingConvertsTo=%s, possibleEffects=%s]", this.getClass().getSimpleName(), Integer.toString(this.nutrition), Float.toString(this.saturationModifier), Boolean.toString(this.canAlwaysEat), Float.toString(this.eatSeconds), Objects.toString(this.usingConvertsTo), Objects.toString(this.possibleEffects));
   }
}
