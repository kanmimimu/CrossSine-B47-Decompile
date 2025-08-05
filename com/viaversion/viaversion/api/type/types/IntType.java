package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class IntType extends Type implements TypeConverter {
   public IntType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) {
      return buffer.readInt();
   }

   public void write(ByteBuf buffer, Integer object) {
      buffer.writeInt(object);
   }

   public Integer from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.intValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return boo ? 1 : 0;
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
