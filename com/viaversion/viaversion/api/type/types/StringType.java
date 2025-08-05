package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

public class StringType extends Type {
   static final int MAX_CHAR_UTF_8_LENGTH;
   final int maxLength;

   public StringType() {
      this(32767);
   }

   public StringType(int maxLength) {
      super(String.class);
      this.maxLength = maxLength;
   }

   public String read(ByteBuf buffer) {
      int len = Types.VAR_INT.readPrimitive(buffer);
      boolean var10000 = len <= this.maxLength * MAX_CHAR_UTF_8_LENGTH;
      int var5 = MAX_CHAR_UTF_8_LENGTH;
      Preconditions.checkArgument(var10000, "Cannot receive string longer than Short.MAX_VALUE * " + var5 + " bytes (got %s bytes)", new Object[]{len});
      String string = buffer.toString(buffer.readerIndex(), len, StandardCharsets.UTF_8);
      buffer.skipBytes(len);
      Preconditions.checkArgument(string.length() <= this.maxLength, "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", new Object[]{string.length()});
      return string;
   }

   public void write(ByteBuf buffer, String object) {
      if (object.length() > this.maxLength) {
         int var5 = object.length();
         throw new IllegalArgumentException("Cannot send string longer than Short.MAX_VALUE characters (got " + var5 + " characters)");
      } else {
         byte[] b = object.getBytes(StandardCharsets.UTF_8);
         Types.VAR_INT.writePrimitive(buffer, b.length);
         buffer.writeBytes(b);
      }
   }

   static {
      MAX_CHAR_UTF_8_LENGTH = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
   }

   public static final class OptionalStringType extends OptionalType {
      public OptionalStringType() {
         super(Types.STRING);
      }
   }
}
