package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class HolderSetType extends Type {
   public HolderSetType() {
      super(HolderSet.class);
   }

   public HolderSet read(ByteBuf buffer) {
      int size = Types.VAR_INT.readPrimitive(buffer) - 1;
      if (size == -1) {
         String tag = (String)Types.STRING.read(buffer);
         return HolderSet.of(tag);
      } else {
         int[] values = new int[size];

         for(int i = 0; i < size; ++i) {
            values[i] = Types.VAR_INT.readPrimitive(buffer);
         }

         return HolderSet.of(values);
      }
   }

   public void write(ByteBuf buffer, HolderSet object) {
      if (object.hasTagKey()) {
         Types.VAR_INT.writePrimitive(buffer, 0);
         Types.STRING.write(buffer, object.tagKey());
      } else {
         int[] values = object.ids();
         Types.VAR_INT.writePrimitive(buffer, values.length + 1);

         for(int value : values) {
            Types.VAR_INT.writePrimitive(buffer, value);
         }
      }

   }

   public static final class OptionalHolderSetType extends OptionalType {
      public OptionalHolderSetType() {
         super(Types.HOLDER_SET);
      }
   }
}
