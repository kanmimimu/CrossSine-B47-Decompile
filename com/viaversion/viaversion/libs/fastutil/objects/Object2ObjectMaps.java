package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Object2ObjectMaps {
   public static final EmptyMap EMPTY_MAP = new EmptyMap();

   private Object2ObjectMaps() {
   }

   public static ObjectIterator fastIterator(Object2ObjectMap map) {
      ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      return entries instanceof Object2ObjectMap.FastEntrySet ? ((Object2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Object2ObjectMap map, Consumer consumer) {
      ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      if (entries instanceof Object2ObjectMap.FastEntrySet) {
         ((Object2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Object2ObjectMap map) {
      final ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      return (ObjectIterable)(entries instanceof Object2ObjectMap.FastEntrySet ? new ObjectIterable() {
         public ObjectIterator iterator() {
            return ((Object2ObjectMap.FastEntrySet)entries).fastIterator();
         }

         public ObjectSpliterator spliterator() {
            return entries.spliterator();
         }

         public void forEach(Consumer consumer) {
            ((Object2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
         }
      } : entries);
   }

   public static Object2ObjectMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Object2ObjectMap singleton(Object key, Object value) {
      return new Singleton(key, value);
   }

   public static Object2ObjectMap synchronize(Object2ObjectMap m) {
      return new SynchronizedMap(m);
   }

   public static Object2ObjectMap synchronize(Object2ObjectMap m, Object sync) {
      return new SynchronizedMap(m, sync);
   }

   public static Object2ObjectMap unmodifiable(Object2ObjectMap m) {
      return new UnmodifiableMap(m);
   }

   public static class EmptyMap extends Object2ObjectFunctions.EmptyFunction implements Object2ObjectMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyMap() {
      }

      public boolean containsValue(Object v) {
         return false;
      }

      public Object getOrDefault(Object key, Object defaultValue) {
         return defaultValue;
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet object2ObjectEntrySet() {
         return ObjectSets.EMPTY_SET;
      }

      public ObjectSet keySet() {
         return ObjectSets.EMPTY_SET;
      }

      public ObjectCollection values() {
         return ObjectSets.EMPTY_SET;
      }

      public void forEach(BiConsumer consumer) {
      }

      public Object clone() {
         return Object2ObjectMaps.EMPTY_MAP;
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

   public static class Singleton extends Object2ObjectFunctions.Singleton implements Object2ObjectMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected transient ObjectSet entries;
      protected transient ObjectSet keys;
      protected transient ObjectCollection values;

      protected Singleton(Object key, Object value) {
         super(key, value);
      }

      public boolean containsValue(Object v) {
         return Objects.equals(this.value, v);
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet object2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value));
         }

         return this.entries;
      }

      public ObjectSet entrySet() {
         return this.object2ObjectEntrySet();
      }

      public ObjectSet keySet() {
         if (this.keys == null) {
            this.keys = ObjectSets.singleton(this.key);
         }

         return this.keys;
      }

      public ObjectCollection values() {
         if (this.values == null) {
            this.values = ObjectSets.singleton(this.value);
         }

         return this.values;
      }

      public boolean isEmpty() {
         return false;
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
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
