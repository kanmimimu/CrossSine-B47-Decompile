package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Reference2ObjectMaps {
   public static final EmptyMap EMPTY_MAP = new EmptyMap();

   private Reference2ObjectMaps() {
   }

   public static ObjectIterator fastIterator(Reference2ObjectMap map) {
      ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
      return entries instanceof Reference2ObjectMap.FastEntrySet ? ((Reference2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Reference2ObjectMap map, Consumer consumer) {
      ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
      if (entries instanceof Reference2ObjectMap.FastEntrySet) {
         ((Reference2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Reference2ObjectMap map) {
      final ObjectSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
      return (ObjectIterable)(entries instanceof Reference2ObjectMap.FastEntrySet ? new ObjectIterable() {
         public ObjectIterator iterator() {
            return ((Reference2ObjectMap.FastEntrySet)entries).fastIterator();
         }

         public ObjectSpliterator spliterator() {
            return entries.spliterator();
         }

         public void forEach(Consumer consumer) {
            ((Reference2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
         }
      } : entries);
   }

   public static Reference2ObjectMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Reference2ObjectMap singleton(Object key, Object value) {
      return new Singleton(key, value);
   }

   public static Reference2ObjectMap synchronize(Reference2ObjectMap m) {
      return new SynchronizedMap(m);
   }

   public static Reference2ObjectMap synchronize(Reference2ObjectMap m, Object sync) {
      return new SynchronizedMap(m, sync);
   }

   public static Reference2ObjectMap unmodifiable(Reference2ObjectMap m) {
      return new UnmodifiableMap(m);
   }

   public static class EmptyMap extends Reference2ObjectFunctions.EmptyFunction implements Reference2ObjectMap, Serializable, Cloneable {
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

      public ObjectSet reference2ObjectEntrySet() {
         return ObjectSets.EMPTY_SET;
      }

      public ReferenceSet keySet() {
         return ReferenceSets.EMPTY_SET;
      }

      public ObjectCollection values() {
         return ObjectSets.EMPTY_SET;
      }

      public void forEach(BiConsumer consumer) {
      }

      public Object clone() {
         return Reference2ObjectMaps.EMPTY_MAP;
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

   public static class Singleton extends Reference2ObjectFunctions.Singleton implements Reference2ObjectMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected transient ObjectSet entries;
      protected transient ReferenceSet keys;
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

      public ObjectSet reference2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSets.singleton(new AbstractReference2ObjectMap.BasicEntry(this.key, this.value));
         }

         return this.entries;
      }

      public ObjectSet entrySet() {
         return this.reference2ObjectEntrySet();
      }

      public ReferenceSet keySet() {
         if (this.keys == null) {
            this.keys = ReferenceSets.singleton(this.key);
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
         return System.identityHashCode(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
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
