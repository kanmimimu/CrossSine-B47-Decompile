package com.viaversion.nbt.conversion;

import com.viaversion.nbt.tag.Tag;

public interface TagConverter {
   Object convert(Tag var1);

   Tag convert(Object var1);
}
