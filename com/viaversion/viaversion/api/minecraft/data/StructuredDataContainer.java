package com.viaversion.viaversion.api.minecraft.data;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Unit;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StructuredDataContainer {
   private final Map data;
   private FullMappings lookup;
   private boolean mappedNames;

   public StructuredDataContainer(Map data) {
      this.data = data;
   }

   public StructuredDataContainer(StructuredData[] dataArray) {
      this((Map)(new Reference2ObjectOpenHashMap(dataArray.length)));

      for(StructuredData data : dataArray) {
         this.data.put(data.key(), data);
      }

   }

   public StructuredDataContainer() {
      this((Map)(new Reference2ObjectOpenHashMap()));
   }

   public @Nullable Object get(StructuredDataKey key) {
      StructuredData<?> data = (StructuredData)this.data.get(key);
      return data != null && !data.isEmpty() ? data.value() : null;
   }

   public @Nullable StructuredData getData(StructuredDataKey key) {
      return (StructuredData)this.data.get(key);
   }

   public @Nullable StructuredData getNonEmptyData(StructuredDataKey key) {
      StructuredData<?> data = (StructuredData)this.data.get(key);
      return data != null && data.isPresent() ? data : null;
   }

   public void set(StructuredDataKey key, Object value) {
      int id = this.serializerId(key);
      if (id != -1) {
         this.data.put(key, StructuredData.of(key, value, id));
      }

   }

   public void set(StructuredDataKey key) {
      this.set(key, Unit.INSTANCE);
   }

   public void setEmpty(StructuredDataKey key) {
      this.data.put(key, StructuredData.empty(key, this.serializerId(key)));
   }

   public void replace(StructuredDataKey key, Function valueMapper) {
      StructuredData<T> data = this.getNonEmptyData(key);
      if (data != null) {
         T replacement = (T)valueMapper.apply(data.value());
         if (replacement != null) {
            data.setValue(replacement);
         } else {
            this.data.remove(key);
         }

      }
   }

   public void replaceKey(StructuredDataKey key, StructuredDataKey toKey) {
      this.replace(key, toKey, Function.identity());
   }

   public void replace(StructuredDataKey key, StructuredDataKey toKey, Function valueMapper) {
      StructuredData<?> data = (StructuredData)this.data.remove(key);
      if (data != null) {
         if (data.isPresent()) {
            T value = (T)data.value();
            V replacement = (V)valueMapper.apply(value);
            if (replacement != null) {
               this.set(toKey, replacement);
            }
         } else {
            this.setEmpty(toKey);
         }

      }
   }

   public void remove(StructuredDataKey key) {
      this.data.remove(key);
   }

   public boolean has(StructuredDataKey key) {
      return this.data.containsKey(key);
   }

   public boolean hasValue(StructuredDataKey key) {
      StructuredData<?> data = (StructuredData)this.data.get(key);
      return data != null && data.isPresent();
   }

   public boolean hasEmpty(StructuredDataKey key) {
      StructuredData<?> data = (StructuredData)this.data.get(key);
      return data != null && data.isEmpty();
   }

   public void setIdLookup(Protocol protocol, boolean mappedNames) {
      this.lookup = protocol.getMappingData().getDataComponentSerializerMappings();
      Preconditions.checkNotNull(this.lookup, "Data component serializer mappings are null");
      this.mappedNames = mappedNames;
   }

   public void updateIds(Protocol protocol, Int2IntFunction rewriter) {
      for(StructuredData data : this.data.values()) {
         int mappedId = rewriter.applyAsInt(data.id());
         if (mappedId != -1) {
            data.setId(mappedId);
         }
      }

   }

   public StructuredDataContainer copy() {
      StructuredDataContainer copy = new StructuredDataContainer(new Reference2ObjectOpenHashMap(this.data));
      copy.lookup = this.lookup;
      return copy;
   }

   private int serializerId(StructuredDataKey key) {
      int id = this.mappedNames ? this.lookup.mappedId(key.identifier()) : this.lookup.id(key.identifier());
      if (id == -1) {
         Via.getPlatform().getLogger().severe("Could not find item data serializer for type " + key);
      }

      return id;
   }

   public Map data() {
      return this.data;
   }

   public String toString() {
      boolean var5 = this.mappedNames;
      FullMappings var4 = this.lookup;
      Map var3 = this.data;
      return "StructuredDataContainer{data=" + var3 + ", lookup=" + var4 + ", mappedNames=" + var5 + "}";
   }
}
