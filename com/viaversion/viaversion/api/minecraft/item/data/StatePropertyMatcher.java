package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StatePropertyMatcher {
   final String name;
   final Either matcher;
   public static final Type TYPE = new Type(StatePropertyMatcher.class) {
      public StatePropertyMatcher read(ByteBuf buffer) {
         String name = (String)Types.STRING.read(buffer);
         if (buffer.readBoolean()) {
            String value = (String)Types.STRING.read(buffer);
            return new StatePropertyMatcher(name, Either.left(value));
         } else {
            String minValue = (String)Types.OPTIONAL_STRING.read(buffer);
            String maxValue = (String)Types.OPTIONAL_STRING.read(buffer);
            return new StatePropertyMatcher(name, Either.right(new RangedMatcher(minValue, maxValue)));
         }
      }

      public void write(ByteBuf buffer, StatePropertyMatcher value) {
         Types.STRING.write(buffer, value.name);
         if (value.matcher.isLeft()) {
            buffer.writeBoolean(true);
            Types.STRING.write(buffer, (String)value.matcher.left());
         } else {
            buffer.writeBoolean(false);
            Types.OPTIONAL_STRING.write(buffer, ((RangedMatcher)value.matcher.right()).minValue());
            Types.OPTIONAL_STRING.write(buffer, ((RangedMatcher)value.matcher.right()).maxValue());
         }

      }
   };
   public static final Type ARRAY_TYPE;

   public StatePropertyMatcher(String name, Either matcher) {
      this.name = name;
      this.matcher = matcher;
   }

   public String name() {
      return this.name;
   }

   public Either matcher() {
      return this.matcher;
   }

   static {
      ARRAY_TYPE = new ArrayType(TYPE);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof StatePropertyMatcher)) {
         return false;
      } else {
         StatePropertyMatcher var2 = (StatePropertyMatcher)var1;
         return Objects.equals(this.name, var2.name) && Objects.equals(this.matcher, var2.matcher);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.matcher);
   }

   public String toString() {
      return String.format("%s[name=%s, matcher=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.matcher));
   }

   public static final class RangedMatcher {
      final @Nullable String minValue;
      final @Nullable String maxValue;

      public RangedMatcher(@Nullable String minValue, @Nullable String maxValue) {
         this.minValue = minValue;
         this.maxValue = maxValue;
      }

      public @Nullable String minValue() {
         return this.minValue;
      }

      public @Nullable String maxValue() {
         return this.maxValue;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof RangedMatcher)) {
            return false;
         } else {
            RangedMatcher var2 = (RangedMatcher)var1;
            return Objects.equals(this.minValue, var2.minValue) && Objects.equals(this.maxValue, var2.maxValue);
         }
      }

      public int hashCode() {
         return (0 * 31 + Objects.hashCode(this.minValue)) * 31 + Objects.hashCode(this.maxValue);
      }

      public String toString() {
         return String.format("%s[minValue=%s, maxValue=%s]", this.getClass().getSimpleName(), Objects.toString(this.minValue), Objects.toString(this.maxValue));
      }
   }
}
