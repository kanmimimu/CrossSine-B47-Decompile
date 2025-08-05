package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ShortTag implements NumberTag {
   public static final int ID = 2;
   public static final ShortTag ZERO = new ShortTag((short)0);
   private final short value;

   public ShortTag(short value) {
      this.value = value;
   }

   public static ShortTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countShort();
      return new ShortTag(in.readShort());
   }

   /** @deprecated */
   @Deprecated
   public Short getValue() {
      return this.value;
   }

   public String asRawString() {
      return Short.toString(this.value);
   }

   public void write(DataOutput out) throws IOException {
      out.writeShort(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ShortTag shortTag = (ShortTag)o;
         return this.value == shortTag.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value;
   }

   public byte asByte() {
      return (byte)this.value;
   }

   public short asShort() {
      return this.value;
   }

   public int asInt() {
      return this.value;
   }

   public long asLong() {
      return (long)this.value;
   }

   public float asFloat() {
      return (float)this.value;
   }

   public double asDouble() {
      return (double)this.value;
   }

   public int getTagId() {
      return 2;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
