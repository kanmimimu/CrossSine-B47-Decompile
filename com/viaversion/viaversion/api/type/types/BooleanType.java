package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BooleanType extends Type implements TypeConverter {
   public BooleanType() {
      super(Boolean.class);
   }

   public Boolean read(ByteBuf buffer) {
      return buffer.readBoolean();
   }

   public void write(ByteBuf buffer, Boolean object) {
      buffer.writeBoolean(object);
   }

   public Boolean from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.intValue() == 1;
      } else {
         return (Boolean)o;
      }
   }

   public static final class OptionalBooleanType extends OptionalType {
      public OptionalBooleanType() {
         super(Types.BOOLEAN);
      }
   }
}
