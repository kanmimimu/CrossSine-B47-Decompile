package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public interface Int2ObjectMap extends Int2ObjectFunction, Map {
   int size();

   default void clear() {
      throw new UnsupportedOperationException();
   }

   void defaultReturnValue(Object var1);

   Object defaultReturnValue();

   ObjectSet int2ObjectEntrySet();

   /** @deprecated */
   @Deprecated
   default ObjectSet entrySet() {
      return this.int2ObjectEntrySet();
   }

   /** @deprecated */
   @Deprecated
   default Object put(Integer key, Object value) {
      return Int2ObjectFunction.super.put(key, value);
   }

   /** @deprecated */
   @Deprecated
   default Object get(Object key) {
      return Int2ObjectFunction.super.get(key);
   }

   /** @deprecated */
   @Deprecated
   default Object remove(Object key) {
      return Int2ObjectFunction.super.remove(key);
   }

   IntSet keySet();

   ObjectCollection values();

   boolean containsKey(int var1);

   /** @deprecated */
   @Deprecated
   default boolean containsKey(Object key) {
      return Int2ObjectFunction.super.containsKey(key);
   }

   default void forEach(BiConsumer consumer) {
      ObjectSet<Entry<V>> entrySet = this.int2ObjectEntrySet();
      Consumer<Entry<V>> wrappingConsumer = (entry) -> consumer.accept(entry.getIntKey(), entry.getValue());
      if (entrySet instanceof FastEntrySet) {
         ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
      } else {
         entrySet.forEach(wrappingConsumer);
      }

   }

   default Object getOrDefault(int key, Object defaultValue) {
      V v;
      return (v = (V)this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   /** @deprecated */
   @Deprecated
   default Object getOrDefault(Object key, Object defaultValue) {
      return super.getOrDefault(key, defaultValue);
   }

   default Object putIfAbsent(int key, Object value) {
      V v = (V)this.get(key);
      V drv = (V)this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(int key, Object value) {
      V curValue = (V)this.get(key);
      if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(int key, Object oldValue, Object newValue) {
      V curValue = (V)this.get(key);
      if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default Object replace(int key, Object value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default Object computeIfAbsent(int key, IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      V v = (V)this.get(key);
      if (v == this.defaultReturnValue() && !this.containsKey(key)) {
         V newValue = (V)mappingFunction.apply(key);
         this.put(key, newValue);
         return newValue;
      } else {
         return v;
      }
   }

   default Object computeIfAbsent(int key, Int2ObjectFunction mappingFunction) {
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
   default Object computeIfAbsentPartial(int key, Int2ObjectFunction mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   default Object computeIfPresent(int key, BiFunction remappingFunction) {
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

   default Object compute(int key, BiFunction remappingFunction) {
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

   default Object merge(int key, Object value, BiFunction remappingFunction) {
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
      int getIntKey();

      /** @deprecated */
      @Deprecated
      default Integer getKey() {
         return this.getIntKey();
      }
   }
}
