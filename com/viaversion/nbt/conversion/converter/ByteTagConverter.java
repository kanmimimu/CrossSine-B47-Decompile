package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.ByteTag;

public class ByteTagConverter implements TagConverter {
   public Byte convert(ByteTag tag) {
      return tag.getValue();
   }

   public ByteTag convert(Byte value) {
      return new ByteTag(value);
   }
}
