package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.LongTag;

public class LongTagConverter implements TagConverter {
   public Long convert(LongTag tag) {
      return tag.getValue();
   }

   public LongTag convert(Long value) {
      return new LongTag(value);
   }
}
