package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.FloatTag;

public class FloatTagConverter implements TagConverter {
   public Float convert(FloatTag tag) {
      return tag.getValue();
   }

   public FloatTag convert(Float value) {
      return new FloatTag(value);
   }
}
