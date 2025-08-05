package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.BitSet;

public class BitSetType extends Type {
   private final int length;
   private final int bytesLength;

   public BitSetType(int length) {
      super(BitSet.class);
      this.length = length;
      this.bytesLength = -Math.floorDiv(-length, 8);
   }

   public BitSet read(ByteBuf buffer) {
      byte[] bytes = new byte[this.bytesLength];
      buffer.readBytes(bytes);
      return BitSet.valueOf(bytes);
   }

   public void write(ByteBuf buffer, BitSet object) {
      boolean var10000 = object.length() <= this.length;
      int var10001 = object.length();
      int var6 = this.length;
      int var5 = var10001;
      Preconditions.checkArgument(var10000, "BitSet of length " + var5 + " larger than max length " + var6);
      byte[] bytes = object.toByteArray();
      buffer.writeBytes(Arrays.copyOf(bytes, this.bytesLength));
   }
}
