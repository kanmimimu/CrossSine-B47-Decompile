package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.ShortTag;

public class ShortTagConverter implements TagConverter {
   public Short convert(ShortTag tag) {
      return tag.getValue();
   }

   public ShortTag convert(Short value) {
      return new ShortTag(value);
   }
}
