package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.LongArrayTag;

public class LongArrayTagConverter implements TagConverter {
   public long[] convert(LongArrayTag tag) {
      return tag.getValue();
   }

   public LongArrayTag convert(long[] value) {
      return new LongArrayTag(value);
   }
}
