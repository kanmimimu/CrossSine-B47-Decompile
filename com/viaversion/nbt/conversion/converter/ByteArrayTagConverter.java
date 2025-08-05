package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.ByteArrayTag;

public class ByteArrayTagConverter implements TagConverter {
   public byte[] convert(ByteArrayTag tag) {
      return tag.getValue();
   }

   public ByteArrayTag convert(byte[] value) {
      return new ByteArrayTag(value);
   }
}
