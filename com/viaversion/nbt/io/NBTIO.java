package com.viaversion.nbt.io;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.Tag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

public final class NBTIO {
   private NBTIO() {
   }

   public static TagReader reader() {
      return new TagReader((Class)null);
   }

   public static TagReader reader(Class expectedTagType) {
      return new TagReader(expectedTagType);
   }

   public static TagWriter writer() {
      return new TagWriter();
   }

   public static Tag readTag(DataInput in, TagLimiter tagLimiter, boolean named, @Nullable Class expectedTagType) throws IOException {
      int id = in.readByte();
      if (expectedTagType != null && expectedTagType != TagRegistry.getClassFor(id)) {
         throw new IOException("Expected tag type " + expectedTagType.getSimpleName() + " but got " + TagRegistry.getClassFor(id).getSimpleName());
      } else {
         if (named) {
            in.skipBytes(in.readUnsignedShort());
         }

         return TagRegistry.read(id, in, tagLimiter, 0);
      }
   }

   public static void writeTag(DataOutput out, Tag tag, boolean named) throws IOException {
      out.writeByte(tag.getTagId());
      if (named) {
         out.writeUTF("");
      }

      tag.write(out);
   }
}
