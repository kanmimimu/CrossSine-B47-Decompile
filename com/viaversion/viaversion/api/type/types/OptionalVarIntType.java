package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class OptionalVarIntType extends Type {
   public OptionalVarIntType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) {
      int value = Types.VAR_INT.readPrimitive(buffer);
      return value == 0 ? null : value - 1;
   }

   public void write(ByteBuf buffer, Integer object) {
      if (object == null) {
         Types.VAR_INT.writePrimitive(buffer, 0);
      } else {
         Types.VAR_INT.writePrimitive(buffer, object + 1);
      }

   }
}
