package com.viaversion.viaversion.util;

import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class KeyMappings {
   private final Object2IntMap keyToId;
   private final String[] keys;

   public KeyMappings(String... keys) {
      this.keys = keys;
      this.keyToId = new Object2IntOpenHashMap(keys.length);
      this.keyToId.defaultReturnValue(-1);

      for(int i = 0; i < keys.length; ++i) {
         this.keyToId.put(keys[i], i);
      }

   }

   public KeyMappings(Collection keys) {
      this((String[])keys.toArray(new String[0]));
   }

   public KeyMappings(ListTag keys) {
      this((String[])keys.getValue().stream().map(StringTag::getValue).toArray((x$0) -> new String[x$0]));
   }

   public @Nullable String idToKey(int id) {
      return id >= 0 && id < this.keys.length ? this.keys[id] : null;
   }

   public int keyToId(String identifier) {
      return this.keyToId.getInt(Key.stripMinecraftNamespace(identifier));
   }

   public String[] keys() {
      return this.keys;
   }

   public int size() {
      return this.keys.length;
   }
}
