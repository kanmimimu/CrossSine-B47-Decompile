package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.IntArrayTag;

public class IntArrayTagConverter implements TagConverter {
   public int[] convert(IntArrayTag tag) {
      return tag.getValue();
   }

   public IntArrayTag convert(int[] value) {
      return new IntArrayTag(value);
   }
}
