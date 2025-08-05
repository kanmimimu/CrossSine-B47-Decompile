package com.viaversion.nbt.tag;

import java.io.DataOutput;
import java.io.IOException;

public interface Tag {
   Object getValue();

   String asRawString();

   void write(DataOutput var1) throws IOException;

   int getTagId();

   Tag copy();
}
