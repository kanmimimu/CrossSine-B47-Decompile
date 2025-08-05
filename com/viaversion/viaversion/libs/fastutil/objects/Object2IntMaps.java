package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Object2IntMaps {
   public static final EmptyMap EMPTY_MAP = new EmptyMap();

   private Object2IntMaps() {
   }

   public static ObjectIterator fastIterator(Object2IntMap map) {
      ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      return entries instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Object2IntMap map, Consumer consumer) {
      ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      if (entries instanceof Object2IntMap.FastEntrySet) {
         ((Object2IntMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Object2IntMap map) {
      final ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      return (ObjectIterable)(entries instanceof Object2IntMap.FastEntrySet ? new ObjectIterable() {
         public ObjectIterator iterator() {
            return ((Object2IntMap.FastEntrySet)entries).fastIterator();
         }

         public ObjectSpliterator spliterator() {
            return entries.spliterator();
         }

         public void forEach(Consumer consumer) {
            ((Object2IntMap.FastEntrySet)entries).fastForEach(consumer);
         }
      } : entries);
   }

   public static Object2IntMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Object2IntMap singleton(Object key, int value) {
      return new Singleton(key, value);
   }

   public static Object2IntMap singleton(Object key, Integer value) {
      return new Singleton(key, value);
   }

   public static Object2IntMap synchronize(Object2IntMap m) {
      return new SynchronizedMap(m);
   }

   public static Object2IntMap synchronize(Object2IntMap m, Object sync) {
      return new SynchronizedMap(m, sync);
   }

   public static Object2IntMap unmodifiable(Object2IntMap m) {
      return new UnmodifiableMap(m);
   }

   public static class EmptyMap extends Object2IntFunctions.EmptyFunction implements Object2IntMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyMap() {
      }

      public boolean containsValue(int v) {
         return false;
      }

      /** @deprecated */
      @Deprecated
      public Integer getOrDefault(Object key, Integer defaultValue) {
         return defaultValue;
      }

      public int getOrDefault(Object key, int defaultValue) {
         return defaultValue;
      }

      /** @deprecated */
      @Deprecated
      public boolean containsValue(Object ov) {
         return false;
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet object2IntEntrySet() {
         return ObjectSets.EMPTY_SET;
      }

      public ObjectSet keySet() {
         return ObjectSets.EMPTY_SET;
      }

      public IntCollection values() {
         return IntSets.EMPTY_SET;
      }

      public void forEach(BiConsumer consumer) {
      }

      public Object clone() {
         return Object2IntMaps.EMPTY_MAP;
      }

      public boolean isEmpty() {
         return true;
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         return !(o instanceof Map) ? false : ((Map)o).isEmpty();
      }

      public String toString() {
         return "{}";
      }
   }

   public static class Singleton extends Object2IntFunctions.Singleton implements Object2IntMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected transient ObjectSet entries;
      protected transient ObjectSet keys;
      protected transient IntCollection values;

      protected Singleton(Object key, int value) {
         super(key, value);
      }

      public boolean containsValue(int v) {
         return this.value == v;
      }

      /** @deprecated */
      @Deprecated
      public boolean containsValue(Object ov) {
         return (Integer)ov == this.value;
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet object2IntEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value));
         }

         return this.entries;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSet entrySet() {
         return this.object2IntEntrySet();
      }

      public ObjectSet keySet() {
         if (this.keys == null) {
            this.keys = ObjectSets.singleton(this.key);
         }

         return this.keys;
      }

      public IntCollection values() {
         if (this.values == null) {
            this.values = IntSets.singleton(this.value);
         }

         return this.values;
      }

      public boolean isEmpty() {
         return false;
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Map)) {
            return false;
         } else {
            Map<?, ?> m = (Map)o;
            return m.size() != 1 ? false : ((Map.Entry)m.entrySet().iterator().next()).equals(this.entrySet().iterator().next());
         }
      }

      public String toString() {
         return "{" + this.key + "=>" + this.value + "}";
      }
   }
}
