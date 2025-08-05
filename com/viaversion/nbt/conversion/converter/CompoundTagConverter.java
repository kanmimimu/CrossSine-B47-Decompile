package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.ConverterRegistry;
import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import java.util.HashMap;
import java.util.Map;

public class CompoundTagConverter implements TagConverter {
   public Map convert(CompoundTag tag) {
      Map<String, Object> ret = new HashMap();
      Map<String, Tag> tags = tag.getValue();

      for(Map.Entry entry : tags.entrySet()) {
         ret.put((String)entry.getKey(), ConverterRegistry.convertToValue((Tag)entry.getValue()));
      }

      return ret;
   }

   public CompoundTag convert(Map value) {
      Map<String, Tag> tags = new HashMap();

      for(Object na : value.keySet()) {
         String n = (String)na;
         tags.put(n, ConverterRegistry.convertToTag(value.get(n)));
      }

      return new CompoundTag(tags);
   }
}
