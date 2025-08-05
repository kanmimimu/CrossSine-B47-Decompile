package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class VarIntArrayType extends Type {
   public VarIntArrayType() {
      super(int[].class);
   }

   public int[] read(ByteBuf buffer) {
      int length = Types.VAR_INT.readPrimitive(buffer);
      Preconditions.checkArgument(buffer.isReadable(length));
      int[] array = new int[length];

      for(int i = 0; i < array.length; ++i) {
         array[i] = Types.VAR_INT.readPrimitive(buffer);
      }

      return array;
   }

   public void write(ByteBuf buffer, int[] object) {
      Types.VAR_INT.writePrimitive(buffer, object.length);

      for(int i : object) {
         Types.VAR_INT.writePrimitive(buffer, i);
      }

   }
}
