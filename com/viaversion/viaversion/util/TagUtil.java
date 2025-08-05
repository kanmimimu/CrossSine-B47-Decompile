package com.viaversion.viaversion.util;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class TagUtil {
   public static ListTag getRegistryEntries(CompoundTag tag, String key) {
      return getRegistryEntries(tag, key, (ListTag)null);
   }

   public static ListTag getRegistryEntries(CompoundTag tag, String key, @Nullable ListTag defaultValue) {
      CompoundTag registry = getNamespacedCompoundTag(tag, key);
      return registry == null ? defaultValue : registry.getListTag("value", CompoundTag.class);
   }

   public static ListTag removeRegistryEntries(CompoundTag tag, String key) {
      return removeRegistryEntries(tag, key, (ListTag)null);
   }

   public static ListTag removeRegistryEntries(CompoundTag tag, String key, @Nullable ListTag defaultValue) {
      String currentKey = Key.namespaced(key);
      CompoundTag registry = tag.getCompoundTag(currentKey);
      if (registry == null) {
         currentKey = Key.stripMinecraftNamespace(key);
         registry = tag.getCompoundTag(currentKey);
         if (registry == null) {
            return defaultValue;
         }
      }

      tag.remove(currentKey);
      return registry.getListTag("value", CompoundTag.class);
   }

   public static boolean removeNamespaced(CompoundTag tag, String key) {
      return tag.remove(Key.namespaced(key)) != null || tag.remove(Key.stripMinecraftNamespace(key)) != null;
   }

   public static @Nullable CompoundTag getNamespacedCompoundTag(CompoundTag tag, String key) {
      CompoundTag compoundTag = tag.getCompoundTag(Key.namespaced(key));
      return compoundTag != null ? compoundTag : tag.getCompoundTag(Key.stripMinecraftNamespace(key));
   }

   public static @Nullable ListTag getNamespacedCompoundTagList(CompoundTag tag, String key) {
      ListTag<CompoundTag> listTag = tag.getListTag(Key.namespaced(key), CompoundTag.class);
      return listTag != null ? listTag : tag.getListTag(Key.stripMinecraftNamespace(key), CompoundTag.class);
   }

   public static Tag handleDeep(Tag tag, TagUpdater consumer) {
      return handleDeep((String)null, tag, consumer);
   }

   static Tag handleDeep(@Nullable String key, Tag tag, TagUpdater consumer) {
      if (tag instanceof CompoundTag) {
         CompoundTag compoundTag = (CompoundTag)tag;

         for(Map.Entry entry : compoundTag.entrySet()) {
            Tag updatedTag = handleDeep((String)entry.getKey(), (Tag)entry.getValue(), consumer);
            entry.setValue(updatedTag);
         }
      } else if (tag instanceof ListTag) {
         ListTag<?> listTag = (ListTag)tag;
         handleListTag(listTag, consumer);
      }

      return consumer.update(key, tag);
   }

   static void handleListTag(ListTag listTag, TagUpdater consumer) {
      listTag.getValue().replaceAll((t) -> handleDeep((String)null, t, consumer));
   }

   @FunctionalInterface
   public interface TagUpdater {
      Tag update(@Nullable String var1, Tag var2);
   }
}
