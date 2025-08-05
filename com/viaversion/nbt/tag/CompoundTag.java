package com.viaversion.nbt.tag;

import com.viaversion.nbt.io.TagRegistry;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public final class CompoundTag implements Tag, Iterable {
   public static final int ID = 10;
   private Map value;

   public CompoundTag() {
      this(new LinkedHashMap());
   }

   public CompoundTag(Map value) {
      this.value = new LinkedHashMap(value);
   }

   public CompoundTag(LinkedHashMap value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public static CompoundTag read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      tagLimiter.checkLevel(nestingLevel);
      int newNestingLevel = nestingLevel + 1;
      CompoundTag compoundTag = new CompoundTag();

      while(true) {
         tagLimiter.countByte();
         int id = in.readByte();
         if (id == 0) {
            return compoundTag;
         }

         String name = in.readUTF();
         tagLimiter.countBytes(2 * name.length());

         Tag tag;
         try {
            tag = TagRegistry.read(id, in, tagLimiter, newNestingLevel);
         } catch (IllegalArgumentException e) {
            throw new IOException("Failed to create tag.", e);
         }

         compoundTag.value.put(name, tag);
      }
   }

   public Map getValue() {
      return this.value;
   }

   public String asRawString() {
      return this.value.toString();
   }

   public void setValue(Map value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = new LinkedHashMap(value);
      }
   }

   public void setValue(LinkedHashMap value) {
      if (value == null) {
         throw new NullPointerException("value cannot be null");
      } else {
         this.value = value;
      }
   }

   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   public boolean contains(String tagName) {
      return this.value.containsKey(tagName);
   }

   public @Nullable Tag get(String tagName) {
      return (Tag)this.value.get(tagName);
   }

   public @Nullable Tag getUnchecked(String tagName) {
      return (Tag)this.value.get(tagName);
   }

   public @Nullable StringTag getStringTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof StringTag ? (StringTag)tag : null;
   }

   public @Nullable CompoundTag getCompoundTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof CompoundTag ? (CompoundTag)tag : null;
   }

   public @Nullable ListTag getListTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof ListTag ? (ListTag)tag : null;
   }

   public @Nullable ListTag getListTag(String tagName, Class type) {
      Tag tag = (Tag)this.value.get(tagName);
      if (!(tag instanceof ListTag)) {
         return null;
      } else {
         Class<? extends Tag> elementType = ((ListTag)tag).getElementType();
         return elementType != type && elementType != null ? null : (ListTag)tag;
      }
   }

   public @Nullable ListTag getNumberListTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      if (!(tag instanceof ListTag)) {
         return null;
      } else {
         Class<? extends Tag> elementType = ((ListTag)tag).getElementType();
         return elementType != null && !NumberTag.class.isAssignableFrom(elementType) ? null : (ListTag)tag;
      }
   }

   public @Nullable IntTag getIntTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof IntTag ? (IntTag)tag : null;
   }

   public @Nullable LongTag getLongTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof LongTag ? (LongTag)tag : null;
   }

   public @Nullable ShortTag getShortTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof ShortTag ? (ShortTag)tag : null;
   }

   public @Nullable ByteTag getByteTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof ByteTag ? (ByteTag)tag : null;
   }

   public @Nullable FloatTag getFloatTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof FloatTag ? (FloatTag)tag : null;
   }

   public @Nullable DoubleTag getDoubleTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof DoubleTag ? (DoubleTag)tag : null;
   }

   public @Nullable NumberTag getNumberTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? (NumberTag)tag : null;
   }

   public @Nullable ByteArrayTag getByteArrayTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof ByteArrayTag ? (ByteArrayTag)tag : null;
   }

   public @Nullable IntArrayTag getIntArrayTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof IntArrayTag ? (IntArrayTag)tag : null;
   }

   public @Nullable LongArrayTag getLongArrayTag(String tagName) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof LongArrayTag ? (LongArrayTag)tag : null;
   }

   public @Nullable String getString(String tagName) {
      return this.getString(tagName, (String)null);
   }

   public @Nullable String getString(String tagName, @Nullable String def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof StringTag ? ((StringTag)tag).getValue() : def;
   }

   public int getInt(String tagName) {
      return this.getInt(tagName, 0);
   }

   public int getInt(String tagName, int def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asInt() : def;
   }

   public long getLong(String tagName) {
      return this.getLong(tagName, 0L);
   }

   public long getLong(String tagName, long def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asLong() : def;
   }

   public short getShort(String tagName) {
      return this.getShort(tagName, (short)0);
   }

   public short getShort(String tagName, short def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asShort() : def;
   }

   public byte getByte(String tagName) {
      return this.getByte(tagName, (byte)0);
   }

   public byte getByte(String tagName, byte def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asByte() : def;
   }

   public float getFloat(String tagName) {
      return this.getFloat(tagName, 0.0F);
   }

   public float getFloat(String tagName, float def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asFloat() : def;
   }

   public double getDouble(String tagName) {
      return this.getDouble(tagName, (double)0.0F);
   }

   public double getDouble(String tagName, double def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asDouble() : def;
   }

   public boolean getBoolean(String tagName) {
      return this.getBoolean(tagName, false);
   }

   public boolean getBoolean(String tagName, boolean def) {
      Tag tag = (Tag)this.value.get(tagName);
      return tag instanceof NumberTag ? ((NumberTag)tag).asBoolean() : def;
   }

   public @Nullable Tag put(String tagName, Tag tag) {
      if (tag == this) {
         throw new IllegalArgumentException("Cannot add a tag to itself");
      } else {
         return (Tag)this.value.put(tagName, tag);
      }
   }

   public void putString(String tagName, String value) {
      this.value.put(tagName, new StringTag(value));
   }

   public void putByte(String tagName, byte value) {
      this.value.put(tagName, new ByteTag(value));
   }

   public void putInt(String tagName, int value) {
      this.value.put(tagName, new IntTag(value));
   }

   public void putShort(String tagName, short value) {
      this.value.put(tagName, new ShortTag(value));
   }

   public void putLong(String tagName, long value) {
      this.value.put(tagName, new LongTag(value));
   }

   public void putFloat(String tagName, float value) {
      this.value.put(tagName, new FloatTag(value));
   }

   public void putDouble(String tagName, double value) {
      this.value.put(tagName, new DoubleTag(value));
   }

   public void putBoolean(String tagName, boolean value) {
      this.value.put(tagName, new ByteTag((byte)(value ? 1 : 0)));
   }

   public void putAll(CompoundTag compoundTag) {
      this.value.putAll(compoundTag.value);
   }

   public @Nullable Tag remove(String tagName) {
      return (Tag)this.value.remove(tagName);
   }

   public @Nullable Tag removeUnchecked(String tagName) {
      return (Tag)this.value.remove(tagName);
   }

   public Set keySet() {
      return this.value.keySet();
   }

   public Collection values() {
      return this.value.values();
   }

   public Set entrySet() {
      return this.value.entrySet();
   }

   public int size() {
      return this.value.size();
   }

   public void clear() {
      this.value.clear();
   }

   public Iterator iterator() {
      return this.value.entrySet().iterator();
   }

   public void write(DataOutput out) throws IOException {
      for(Map.Entry entry : this.value.entrySet()) {
         Tag tag = (Tag)entry.getValue();
         out.writeByte(tag.getTagId());
         out.writeUTF((String)entry.getKey());
         tag.write(out);
      }

      out.writeByte(0);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         CompoundTag tags = (CompoundTag)o;
         return this.value.equals(tags.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public CompoundTag copy() {
      LinkedHashMap<String, Tag> newMap = new LinkedHashMap();

      for(Map.Entry entry : this.value.entrySet()) {
         newMap.put((String)entry.getKey(), ((Tag)entry.getValue()).copy());
      }

      return new CompoundTag(newMap);
   }

   public int getTagId() {
      return 10;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
