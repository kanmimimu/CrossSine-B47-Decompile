package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.Nullable;

public class NamedCompoundTagType extends Type {
   public static final int MAX_NBT_BYTES = 2097152;
   public static final int MAX_NESTING_LEVEL = 512;

   public NamedCompoundTagType() {
      super(CompoundTag.class);
   }

   public CompoundTag read(ByteBuf buffer) {
      try {
         return read(buffer, true);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void write(ByteBuf buffer, CompoundTag object) {
      try {
         write(buffer, object, "");
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static CompoundTag read(ByteBuf buffer, boolean readName) throws IOException {
      byte id = buffer.readByte();
      if (id == 0) {
         return null;
      } else if (id != 10) {
         throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", id));
      } else {
         if (readName) {
            buffer.skipBytes(buffer.readUnsignedShort());
         }

         TagLimiter tagLimiter = TagLimiter.create(2097152, 512);
         return CompoundTag.read(new ByteBufInputStream(buffer), tagLimiter, 0);
      }
   }

   public static void write(ByteBuf buffer, Tag tag, @Nullable String name) throws IOException {
      if (tag == null) {
         buffer.writeByte(0);
      } else {
         ByteBufOutputStream out = new ByteBufOutputStream(buffer);
         out.writeByte(tag.getTagId());
         if (name != null) {
            out.writeUTF(name);
         }

         tag.write(out);
      }
   }

   public static final class OptionalNamedCompoundTagType extends OptionalType {
      public OptionalNamedCompoundTagType() {
         super(Types.NAMED_COMPOUND_TAG);
      }
   }
}
