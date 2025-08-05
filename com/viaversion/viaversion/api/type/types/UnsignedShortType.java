package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedShortType extends Type implements TypeConverter {
   public UnsignedShortType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) {
      return buffer.readUnsignedShort();
   }

   public void write(ByteBuf buffer, Integer object) {
      buffer.writeShort(object);
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
