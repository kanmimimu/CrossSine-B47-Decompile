package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.StringTag;

public class StringTagConverter implements TagConverter {
   public String convert(StringTag tag) {
      return tag.getValue();
   }

   public StringTag convert(String value) {
      return new StringTag(value);
   }
}
