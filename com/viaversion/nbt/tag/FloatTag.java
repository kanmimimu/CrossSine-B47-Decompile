package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class FloatTag implements NumberTag {
   public static final int ID = 5;
   public static final FloatTag ZERO = new FloatTag(0.0F);
   private final float value;

   public FloatTag(float value) {
      this.value = value;
   }

   public static FloatTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countFloat();
      return new FloatTag(in.readFloat());
   }

   /** @deprecated */
   @Deprecated
   public Float getValue() {
      return this.value;
   }

   public String asRawString() {
      return Float.toString(this.value);
   }

   public void write(DataOutput out) throws IOException {
      out.writeFloat(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         FloatTag floatTag = (FloatTag)o;
         return this.value == floatTag.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Float.hashCode(this.value);
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
      return (long)this.value;
   }

   public float asFloat() {
      return this.value;
   }

   public double asDouble() {
      return (double)this.value;
   }

   public int getTagId() {
      return 5;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
