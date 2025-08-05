package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class DoubleType extends Type implements TypeConverter {
   public DoubleType() {
      super(Double.class);
   }

   /** @deprecated */
   @Deprecated
   public Double read(ByteBuf buffer) {
      return buffer.readDouble();
   }

   public double readPrimitive(ByteBuf buffer) {
      return buffer.readDouble();
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Double object) {
      buffer.writeDouble(object);
   }

   public void writePrimitive(ByteBuf buffer, double object) {
      buffer.writeDouble(object);
   }

   public Double from(Object o) {
      if (o instanceof Number) {
         Number number = (Number)o;
         return number.doubleValue();
      } else if (o instanceof Boolean) {
         Boolean boo = (Boolean)o;
         return boo ? (double)1.0F : (double)0.0F;
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
