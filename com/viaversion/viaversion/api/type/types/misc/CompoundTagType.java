package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class CompoundTagType extends Type {
   public CompoundTagType() {
      super(CompoundTag.class);
   }

   public CompoundTag read(ByteBuf buffer) {
      try {
         return NamedCompoundTagType.read(buffer, false);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void write(ByteBuf buffer, CompoundTag object) {
      try {
         NamedCompoundTagType.write(buffer, object, (String)null);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static final class OptionalCompoundTagType extends OptionalType {
      public OptionalCompoundTagType() {
         super(Types.COMPOUND_TAG);
      }
   }
}
