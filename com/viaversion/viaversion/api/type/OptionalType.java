package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class OptionalType extends Type {
   private final Type type;

   protected OptionalType(Type type) {
      super(type.getOutputClass());
      this.type = type;
   }

   public @Nullable Object read(ByteBuf buffer) {
      return buffer.readBoolean() ? this.type.read(buffer) : null;
   }

   public void write(ByteBuf buffer, @Nullable Object value) {
      if (value == null) {
         buffer.writeBoolean(false);
      } else {
         buffer.writeBoolean(true);
         this.type.write(buffer, value);
      }

   }
}
