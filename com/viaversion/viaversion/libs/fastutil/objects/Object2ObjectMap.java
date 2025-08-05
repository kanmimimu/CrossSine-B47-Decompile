package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface Object2ObjectMap extends Object2ObjectFunction, Map {
   int size();

   default void clear() {
      throw new UnsupportedOperationException();
   }

   void defaultReturnValue(Object var1);

   Object defaultReturnValue();

   ObjectSet object2ObjectEntrySet();

   default ObjectSet entrySet() {
      return this.object2ObjectEntrySet();
   }

   default Object put(Object key, Object value) {
      return Object2ObjectFunction.super.put(key, value);
   }

   default Object remove(Object key) {
      return Object2ObjectFunction.super.remove(key);
   }

   ObjectSet keySet();

   ObjectCollection values();

   boolean containsKey(Object var1);

   default void forEach(BiConsumer consumer) {
      ObjectSet<Entry<K, V>> entrySet = this.object2ObjectEntrySet();
      Consumer<Entry<K, V>> wrappingConsumer = (entry) -> consumer.accept(entry.getKey(), entry.getValue());
      if (entrySet instanceof FastEntrySet) {
         ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
      } else {
         entrySet.forEach(wrappingConsumer);
      }

   }

   default Object getOrDefault(Object key, Object defaultValue) {
      V v;
      return (v = (V)this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default Object putIfAbsent(Object key, Object value) {
      V v = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(Object key, Object value) {
      V curValue = (V)this.get(key);
      if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(Object key, Object oldValue, Object newValue) {
      V curValue = (V)this.get(key);
      if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default Object replace(Object key, Object value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default Object computeIfAbsent(Object key, Object2ObjectFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      V v = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         if (!mappingFunction.containsKey(key)) {
            return drv;
         } else {
            V newValue = (V)mappingFunction.get(key);
            this.put(key, newValue);
            return newValue;
         }
      } else {
         return v;
      }
   }

   /** @deprecated */
   @Deprecated
   default Object computeObjectIfAbsentPartial(Object key, Object2ObjectFunction mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   default Object computeIfPresent(Object key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      V oldValue = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      if (oldValue == drv && !this.containsKey(key)) {
         return drv;
      } else {
         V newValue = (V)remappingFunction.apply(key, oldValue);
         if (newValue == null) {
            this.remove(key);
            return drv;
         } else {
            this.put(key, newValue);
            return newValue;
         }
      }
   }

   default Object compute(Object key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      V oldValue = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      boolean contained = oldValue != drv || this.containsKey(key);
      V newValue = (V)remappingFunction.apply(key, contained ? oldValue : null);
      if (newValue == null) {
         if (contained) {
            this.remove(key);
         }

         return drv;
      } else {
         this.put(key, newValue);
         return newValue;
      }
   }

   default Object merge(Object key, Object value, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Objects.requireNonNull(value);
      V oldValue = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      V newValue;
      if (oldValue == drv && !this.containsKey(key)) {
         newValue = value;
      } else {
         V mergedValue = (V)remappingFunction.apply(oldValue, value);
         if (mergedValue == null) {
            this.remove(key);
            return drv;
         }

         newValue = mergedValue;
      }

      this.put(key, newValue);
      return newValue;
   }

   public interface FastEntrySet extends ObjectSet {
      ObjectIterator fastIterator();

      default void fastForEach(Consumer consumer) {
         this.forEach(consumer);
      }
   }

   public interface Entry extends Map.Entry {
   }
}
