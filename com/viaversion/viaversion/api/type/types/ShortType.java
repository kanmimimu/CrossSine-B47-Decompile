package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class ShortType extends Type implements TypeConverter {
   public ShortType() {
      super(Short.class);
   }

   public short readPrimitive(ByteBuf buffer) {
      return buffer.readShort();
   }

   public void writePrimitive(ByteBuf buffer, short object) {
      buffer.writeShort(object);
   }

   /** @deprecated */
   @Deprecated
   public Short read(ByteBuf buffer) {
      return buffer.readShort();
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Short object) {
      buffer.writeShort(object);
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
