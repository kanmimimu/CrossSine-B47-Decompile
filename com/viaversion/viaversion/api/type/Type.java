package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.util.Either;
import io.netty.buffer.ByteBuf;

public abstract class Type implements ByteBufReader, ByteBufWriter {
   private final Class outputClass;
   private final String typeName;

   protected Type(Class outputClass) {
      this((String)null, outputClass);
   }

   protected Type(String typeName, Class outputClass) {
      this.outputClass = outputClass;
      this.typeName = typeName;
   }

   public Class getOutputClass() {
      return this.outputClass;
   }

   public String getTypeName() {
      return this.typeName != null ? this.typeName : this.getClass().getSimpleName();
   }

   public Class getBaseClass() {
      return this.getClass();
   }

   public String toString() {
      return this.getTypeName();
   }

   public static Either readEither(ByteBuf buf, Type leftType, Type rightType) {
      return buf.readBoolean() ? Either.left(leftType.read(buf)) : Either.right(rightType.read(buf));
   }

   public static void writeEither(ByteBuf buf, Either value, Type leftType, Type rightType) {
      if (value.isLeft()) {
         buf.writeBoolean(true);
         leftType.write(buf, value.left());
      } else {
         buf.writeBoolean(false);
         rightType.write(buf, value.right());
      }

   }
}
