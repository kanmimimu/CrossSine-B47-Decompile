package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class AttributeModifiers1_21 {
   final AttributeModifier[] modifiers;
   final boolean showInTooltip;
   public static final Type TYPE = new Type(AttributeModifiers1_21.class) {
      public AttributeModifiers1_21 read(ByteBuf buffer) {
         AttributeModifier[] modifiers = (AttributeModifier[])AttributeModifiers1_21.AttributeModifier.ARRAY_TYPE.read(buffer);
         boolean showInTooltip = buffer.readBoolean();
         return new AttributeModifiers1_21(modifiers, showInTooltip);
      }

      public void write(ByteBuf buffer, AttributeModifiers1_21 value) {
         AttributeModifiers1_21.AttributeModifier.ARRAY_TYPE.write(buffer, value.modifiers());
         buffer.writeBoolean(value.showInTooltip());
      }
   };

   public AttributeModifiers1_21(AttributeModifier[] modifiers, boolean showInTooltip) {
      this.modifiers = modifiers;
      this.showInTooltip = showInTooltip;
   }

   public AttributeModifiers1_21 rewrite(Int2IntFunction rewriteFunction) {
      AttributeModifier[] modifiers = new AttributeModifier[this.modifiers.length];

      for(int i = 0; i < this.modifiers.length; ++i) {
         AttributeModifier modifier = this.modifiers[i];
         modifiers[i] = new AttributeModifier(rewriteFunction.applyAsInt(modifier.attribute()), modifier.modifier(), modifier.slotType());
      }

      return new AttributeModifiers1_21(modifiers, this.showInTooltip);
   }

   public AttributeModifier[] modifiers() {
      return this.modifiers;
   }

   public boolean showInTooltip() {
      return this.showInTooltip;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AttributeModifiers1_21)) {
         return false;
      } else {
         AttributeModifiers1_21 var2 = (AttributeModifiers1_21)var1;
         return Objects.equals(this.modifiers, var2.modifiers) && this.showInTooltip == var2.showInTooltip;
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.modifiers)) * 31 + Boolean.hashCode(this.showInTooltip);
   }

   public String toString() {
      return String.format("%s[modifiers=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.modifiers), Boolean.toString(this.showInTooltip));
   }

   public static final class AttributeModifier {
      final int attribute;
      final ModifierData modifier;
      final int slotType;
      public static final Type TYPE = new Type(AttributeModifier.class) {
         public AttributeModifier read(ByteBuf buffer) {
            int attribute = Types.VAR_INT.readPrimitive(buffer);
            ModifierData modifier = (ModifierData)AttributeModifiers1_21.ModifierData.TYPE.read(buffer);
            int slot = Types.VAR_INT.readPrimitive(buffer);
            return new AttributeModifier(attribute, modifier, slot);
         }

         public void write(ByteBuf buffer, AttributeModifier value) {
            Types.VAR_INT.writePrimitive(buffer, value.attribute);
            AttributeModifiers1_21.ModifierData.TYPE.write(buffer, value.modifier);
            Types.VAR_INT.writePrimitive(buffer, value.slotType);
         }
      };
      public static final Type ARRAY_TYPE;

      public AttributeModifier(int attribute, ModifierData modifier, int slotType) {
         this.attribute = attribute;
         this.modifier = modifier;
         this.slotType = slotType;
      }

      public int attribute() {
         return this.attribute;
      }

      public ModifierData modifier() {
         return this.modifier;
      }

      public int slotType() {
         return this.slotType;
      }

      static {
         ARRAY_TYPE = new ArrayType(TYPE);
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof AttributeModifier)) {
            return false;
         } else {
            AttributeModifier var2 = (AttributeModifier)var1;
            return this.attribute == var2.attribute && Objects.equals(this.modifier, var2.modifier) && this.slotType == var2.slotType;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Integer.hashCode(this.attribute)) * 31 + Objects.hashCode(this.modifier)) * 31 + Integer.hashCode(this.slotType);
      }

      public String toString() {
         return String.format("%s[attribute=%s, modifier=%s, slotType=%s]", this.getClass().getSimpleName(), Integer.toString(this.attribute), Objects.toString(this.modifier), Integer.toString(this.slotType));
      }
   }

   public static final class ModifierData {
      final String id;
      final double amount;
      final int operation;
      public static final Type TYPE = new Type(ModifierData.class) {
         public ModifierData read(ByteBuf buffer) {
            String id = (String)Types.STRING.read(buffer);
            double amount = buffer.readDouble();
            int operation = Types.VAR_INT.readPrimitive(buffer);
            return new ModifierData(id, amount, operation);
         }

         public void write(ByteBuf buffer, ModifierData value) {
            Types.STRING.write(buffer, value.id);
            buffer.writeDouble(value.amount);
            Types.VAR_INT.writePrimitive(buffer, value.operation);
         }
      };

      public ModifierData(String id, double amount, int operation) {
         this.id = id;
         this.amount = amount;
         this.operation = operation;
      }

      public String id() {
         return this.id;
      }

      public double amount() {
         return this.amount;
      }

      public int operation() {
         return this.operation;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ModifierData)) {
            return false;
         } else {
            ModifierData var2 = (ModifierData)var1;
            return Objects.equals(this.id, var2.id) && Double.compare(this.amount, var2.amount) == 0 && this.operation == var2.operation;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Objects.hashCode(this.id)) * 31 + Double.hashCode(this.amount)) * 31 + Integer.hashCode(this.operation);
      }

      public String toString() {
         return String.format("%s[id=%s, amount=%s, operation=%s]", this.getClass().getSimpleName(), Objects.toString(this.id), Double.toString(this.amount), Integer.toString(this.operation));
      }
   }
}
