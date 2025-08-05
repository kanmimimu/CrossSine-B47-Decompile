package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class FloatType extends Type implements TypeConverter {
   public FloatType() {
      super(Float.class);
   }

   public float readPrimitive(ByteBuf buffer) {
      return buffer.readFloat();
   }

   public void writePrimitive(ByteBuf buffer, float object) {
      buffer.writeFloat(object);
   }

   /** @deprecated */
   @Deprecated
   public Float read(ByteBuf buffer) {
      return buffer.readFloat();
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Float object) {
      buffer.writeFloat(object);
   }

   public Float from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.floatValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return boo ? 1.0F : 0.0F;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public static final class OptionalFloatType extends OptionalType {
      public OptionalFloatType() {
         super(Types.FLOAT);
      }
   }
}
