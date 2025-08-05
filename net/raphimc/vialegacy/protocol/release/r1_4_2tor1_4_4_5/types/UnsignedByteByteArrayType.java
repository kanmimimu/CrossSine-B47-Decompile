package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class UnsignedByteByteArrayType extends Type {
   public UnsignedByteByteArrayType() {
      super(byte[].class);
   }

   public void write(ByteBuf buffer, byte[] array) {
      buffer.writeByte(array.length & 255);
      buffer.writeBytes(array);
   }

   public byte[] read(ByteBuf buffer) {
      byte[] array = new byte[buffer.readUnsignedByte()];
      buffer.readBytes(array);
      return array;
   }
}
