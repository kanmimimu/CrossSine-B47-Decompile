package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.nbt.io.TagRegistry;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.IOException;

public class TagType extends Type {
   public TagType() {
      super(Tag.class);
   }

   public Tag read(ByteBuf buffer) {
      byte id = buffer.readByte();
      if (id == 0) {
         return null;
      } else {
         TagLimiter tagLimiter = TagLimiter.create(2097152, 512);

         try {
            return TagRegistry.read(id, new ByteBufInputStream(buffer), tagLimiter, 0);
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public void write(ByteBuf buffer, Tag tag) {
      try {
         NamedCompoundTagType.write(buffer, tag, (String)null);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static final class OptionalTagType extends OptionalType {
      public OptionalTagType() {
         super(Types.TAG);
      }
   }
}
