package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class LongTag implements NumberTag {
   public static final int ID = 4;
   public static final LongTag ZERO = new LongTag(0L);
   private final long value;

   public LongTag(long value) {
      this.value = value;
   }

   public static LongTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countLong();
      return new LongTag(in.readLong());
   }

   /** @deprecated */
   @Deprecated
   public Long getValue() {
      return this.value;
   }

   public String asRawString() {
      return Long.toString(this.value);
   }

   public void write(DataOutput out) throws IOException {
      out.writeLong(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LongTag longTag = (LongTag)o;
         return this.value == longTag.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Long.hashCode(this.value);
   }

   public byte asByte() {
      return (byte)((int)this.value);
   }

   public short asShort() {
      return (short)((int)this.value);
   }

   public int asInt() {
      return (int)this.value;
   }

   public long asLong() {
      return this.value;
   }

   public float asFloat() {
      return (float)this.value;
   }

   public double asDouble() {
      return (double)this.value;
   }

   public int getTagId() {
      return 4;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
