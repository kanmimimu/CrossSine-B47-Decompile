package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.DoubleTag;

public class DoubleTagConverter implements TagConverter {
   public Double convert(DoubleTag tag) {
      return tag.getValue();
   }

   public DoubleTag convert(Double value) {
      return new DoubleTag(value);
   }
}
