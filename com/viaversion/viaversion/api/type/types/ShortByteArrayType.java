package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ShortByteArrayType extends Type {
   public ShortByteArrayType() {
      super(byte[].class);
   }

   public void write(ByteBuf buffer, byte[] object) {
      buffer.writeShort(object.length);
      buffer.writeBytes(object);
   }

   public byte[] read(ByteBuf buffer) {
      byte[] array = new byte[buffer.readShort()];
      buffer.readBytes(array);
      return array;
   }
}
