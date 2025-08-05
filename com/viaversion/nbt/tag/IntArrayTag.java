package com.viaversion.nbt.tag;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public final class IntArrayTag implements NumberArrayTag {
   public static final int ID = 11;
   private static final int[] EMPTY_ARRAY = new int[0];
   private int[] value;

   public IntArrayTag() {
      this(EMPTY_ARRAY);
   }

   public IntArrayTag(int[] value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public static IntArrayTag read(DataInput in, TagLimiter tagLimiter) throws IOException {
      tagLimiter.countInt();
      int[] value = new int[in.readInt()];
      tagLimiter.countBytes(4 * value.length);

      for(int index = 0; index < value.length; ++index) {
         value[index] = in.readInt();
      }

      return new IntArrayTag(value);
   }

   public int[] getValue() {
      return this.value;
   }

   public String asRawString() {
      return Arrays.toString(this.value);
   }

   public void setValue(int[] value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public int get(int index) {
      return this.value[index];
   }

   public void set(int index, int value) {
      this.value[index] = value;
   }

   public int length() {
      return this.value.length;
   }

   public ListTag toListTag() {
      ListTag<IntTag> list = new ListTag(IntTag.class);

      for(int i : this.value) {
         list.add(new IntTag(i));
      }

      return list;
   }

   public void write(DataOutput out) throws IOException {
      out.writeInt(this.value.length);

      for(int i : this.value) {
         out.writeInt(i);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         IntArrayTag that = (IntArrayTag)o;
         return Arrays.equals(this.value, that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   public IntArrayTag copy() {
      return new IntArrayTag((int[])this.value.clone());
   }

   public int getTagId() {
      return 11;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
