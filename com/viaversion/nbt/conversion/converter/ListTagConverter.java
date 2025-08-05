package com.viaversion.nbt.conversion.converter;

import com.viaversion.nbt.conversion.ConverterRegistry;
import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import java.util.ArrayList;
import java.util.List;

public class ListTagConverter implements TagConverter {
   public List convert(ListTag tag) {
      List<Object> ret = new ArrayList();

      for(Tag t : tag.getValue()) {
         ret.add(ConverterRegistry.convertToValue(t));
      }

      return ret;
   }

   public ListTag convert(List value) {
      List<Tag> tags = new ArrayList();

      for(Object o : value) {
         tags.add(ConverterRegistry.convertToTag(o));
      }

      return new ListTag(tags);
   }
}
