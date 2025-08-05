package com.viaversion.nbt.io;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.fastutil.io.FastBufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import org.jetbrains.annotations.Nullable;

public final class TagReader {
   private final Class expectedTagType;
   private TagLimiter tagLimiter = TagLimiter.noop();
   private boolean named;

   TagReader(@Nullable Class expectedTagType) {
      this.expectedTagType = expectedTagType;
   }

   public TagReader tagLimiter(TagLimiter tagLimiter) {
      this.tagLimiter = tagLimiter;
      return this;
   }

   public TagReader named() {
      this.named = true;
      return this;
   }

   public Tag read(DataInput in) throws IOException {
      this.tagLimiter.reset();
      return NBTIO.readTag(in, this.tagLimiter, this.named, this.expectedTagType);
   }

   public Tag read(InputStream in) throws IOException {
      DataInput dataInput = new DataInputStream(in);
      return this.read(dataInput);
   }

   public Tag read(Path path, boolean compressed) throws IOException {
      InputStream in = new FastBufferedInputStream(Files.newInputStream(path));

      Tag var4;
      try {
         if (compressed) {
            in = new GZIPInputStream(in);
         }

         var4 = this.read(in);
      } finally {
         in.close();
      }

      return var4;
   }
}
