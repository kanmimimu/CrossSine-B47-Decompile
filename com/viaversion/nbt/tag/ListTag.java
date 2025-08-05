package com.viaversion.nbt.tag;

import com.viaversion.nbt.io.TagRegistry;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.stringified.SNBT;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public final class ListTag implements Tag, Iterable {
   public static final int ID = 9;
   private Class type;
   private List value;

   /** @deprecated */
   @Deprecated
   public ListTag() {
      this.value = new ArrayList();
   }

   public ListTag(Class type) {
      this.type = type;
      this.value = new ArrayList();
   }

   public ListTag(List value) {
      this.setValue(value);
   }

   public static ListTag read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      tagLimiter.checkLevel(nestingLevel);
      tagLimiter.countBytes(5);
      int id = in.readByte();
      Class<? extends Tag> type = null;
      if (id != 0) {
         type = TagRegistry.getClassFor(id);
         if (type == null) {
            throw new IOException("Unknown tag ID in ListTag: " + id);
         }
      }

      return read(in, id, type, tagLimiter, nestingLevel);
   }

   private static ListTag read(DataInput in, int id, Class type, TagLimiter tagLimiter, int nestingLevel) throws IOException {
      ListTag<T> listTag = new ListTag(type);
      int count = in.readInt();
      int newNestingLevel = nestingLevel + 1;

      for(int index = 0; index < count; ++index) {
         T tag;
         try {
            tag = (T)TagRegistry.read(id, in, tagLimiter, newNestingLevel);
         } catch (IllegalArgumentException e) {
            throw new IOException("Failed to create tag.", e);
         }

         listTag.add(tag);
      }

      return listTag;
   }

   public List getValue() {
      return this.value;
   }

   public String asRawString() {
      return this.value.toString();
   }

   public void setValue(List value) {
      this.value = new ArrayList(value);
      if (!value.isEmpty()) {
         if (this.type == null) {
            this.type = ((Tag)value.get(0)).getClass();
         }

         for(Tag t : value) {
            this.checkType(t);
         }
      }

   }

   public @Nullable Class getElementType() {
      return this.type;
   }

   public boolean add(Tag tag) throws IllegalArgumentException {
      this.checkAddedTag(tag);
      return this.value.add(tag);
   }

   private void checkAddedTag(Tag tag) {
      if (this.type == null) {
         this.type = tag.getClass();
      } else {
         this.checkType(tag);
      }

   }

   private void checkType(Tag tag) {
      if (tag.getClass() != this.type) {
         throw new IllegalArgumentException("Tag type " + tag.getClass().getSimpleName() + " differs from list type " + this.type.getSimpleName());
      }
   }

   public boolean remove(Tag tag) {
      return this.value.remove(tag);
   }

   public Tag get(int index) {
      return (Tag)this.value.get(index);
   }

   public Tag set(int index, Tag tag) {
      this.checkAddedTag(tag);
      return (Tag)this.value.set(index, tag);
   }

   public Tag remove(int index) {
      return (Tag)this.value.remove(index);
   }

   public int size() {
      return this.value.size();
   }

   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   public Stream stream() {
      return this.value.stream();
   }

   public Iterator iterator() {
      return this.value.iterator();
   }

   public void write(DataOutput out) throws IOException {
      if (this.value.isEmpty()) {
         out.writeByte(0);
      } else {
         int id = TagRegistry.getIdFor(this.type);
         if (id == -1) {
            throw new IOException("ListTag contains unregistered tag class.");
         }

         out.writeByte(id);
      }

      out.writeInt(this.value.size());

      for(Tag tag : this.value) {
         tag.write(out);
      }

   }

   public ListTag copy() {
      ListTag<T> copy = new ListTag(this.type);
      copy.value = new ArrayList(this.value.size());

      for(Tag value : this.value) {
         copy.add(value.copy());
      }

      return copy;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ListTag<?> tags = (ListTag)o;
         return !Objects.equals(this.type, tags.type) ? false : this.value.equals(tags.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.type != null ? this.type.hashCode() : 0;
      result = 31 * result + this.value.hashCode();
      return result;
   }

   public int getTagId() {
      return 9;
   }

   public String toString() {
      return SNBT.serialize(this);
   }
}
