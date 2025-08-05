package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedByteType extends Type implements TypeConverter {
   public static final int MAX_VALUE = 255;

   public UnsignedByteType() {
      super("Unsigned Byte", Short.class);
   }

   public Short read(ByteBuf buffer) {
      return buffer.readUnsignedByte();
   }

   public void write(ByteBuf buffer, Short object) {
      buffer.writeByte(object);
   }

   public Short from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.shortValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return Short.valueOf((short)(boo ? 1 : 0));
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
