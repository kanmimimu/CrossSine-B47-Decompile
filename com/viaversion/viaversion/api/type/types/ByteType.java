package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class ByteType extends Type implements TypeConverter {
   public ByteType() {
      super(Byte.class);
   }

   public byte readPrimitive(ByteBuf buffer) {
      return buffer.readByte();
   }

   public void writePrimitive(ByteBuf buffer, byte object) {
      buffer.writeByte(object);
   }

   /** @deprecated */
   @Deprecated
   public Byte read(ByteBuf buffer) {
      return buffer.readByte();
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Byte object) {
      buffer.writeByte(object);
   }

   public Byte from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.byteValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return Byte.valueOf((byte)(boo ? 1 : 0));
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
