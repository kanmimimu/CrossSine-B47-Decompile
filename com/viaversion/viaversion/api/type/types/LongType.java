package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class LongType extends Type implements TypeConverter {
   public LongType() {
      super(Long.class);
   }

   /** @deprecated */
   @Deprecated
   public Long read(ByteBuf buffer) {
      return buffer.readLong();
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Long object) {
      buffer.writeLong(object);
   }

   public Long from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.longValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return boo ? 1L : 0L;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public long readPrimitive(ByteBuf buffer) {
      return buffer.readLong();
   }

   public void writePrimitive(ByteBuf buffer, long object) {
      buffer.writeLong(object);
   }
}
