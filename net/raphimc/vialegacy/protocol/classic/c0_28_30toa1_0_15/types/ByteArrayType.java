package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ByteArrayType extends Type {
   public ByteArrayType() {
      super(byte[].class);
   }

   public void write(ByteBuf buffer, byte[] array) {
      if (array.length != 1024) {
         throw new IllegalStateException("Byte array needs to be exactly 1024 bytes long");
      } else {
         buffer.writeBytes(array);
      }
   }

   public byte[] read(ByteBuf buffer) {
      byte[] array = new byte[1024];
      buffer.readBytes(array);
      return array;
   }
}
