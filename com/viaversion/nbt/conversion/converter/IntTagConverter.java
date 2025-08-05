package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.IntTag;

public class IntTagConverter implements TagConverter {
   public Integer convert(IntTag tag) {
      return tag.getValue();
   }

   public IntTag convert(Integer value) {
      return new IntTag(value);
   }
}
