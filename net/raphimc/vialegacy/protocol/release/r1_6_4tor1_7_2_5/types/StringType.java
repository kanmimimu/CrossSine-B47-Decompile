package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class StringType extends Type {
   public StringType() {
      super(String.class);
   }

   public String read(ByteBuf buffer) {
      short length = buffer.readShort();
      StringBuilder builder = new StringBuilder();

      for(int i = 0; i < length; ++i) {
         builder.append(buffer.readChar());
      }

      return builder.toString();
   }

   public void write(ByteBuf buffer, String s) {
      buffer.writeShort(s.length());

      for(char c : s.toCharArray()) {
         buffer.writeChar(c);
      }

   }
}
