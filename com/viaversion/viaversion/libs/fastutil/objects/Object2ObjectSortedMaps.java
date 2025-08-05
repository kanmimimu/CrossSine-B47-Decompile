package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Object2ObjectSortedMaps {
   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

   private Object2ObjectSortedMaps() {
   }

   public static Comparator entryComparator(Comparator comparator) {
      return (x, y) -> comparator.compare(x.getKey(), y.getKey());
   }

   public static ObjectBidirectionalIterator fastIterator(Object2ObjectSortedMap map) {
      ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      return entries instanceof Object2ObjectSortedMap.FastSortedEntrySet ? ((Object2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static ObjectBidirectionalIterable fastIterable(Object2ObjectSortedMap map) {
      ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
      Object var2;
      if (entries instanceof Object2ObjectSortedMap.FastSortedEntrySet) {
         var2 = (Object2ObjectSortedMap.FastSortedEntrySet)entries;
         Objects.requireNonNull(entries);
         var2 = var2::fastIterator;
      } else {
         var2 = entries;
      }

      return var2;
   }

   public static Object2ObjectSortedMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Object2ObjectSortedMap singleton(Object key, Object value) {
      return new Singleton(key, value);
   }

   public static Object2ObjectSortedMap singleton(Object key, Object value, Comparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Object2ObjectSortedMap synchronize(Object2ObjectSortedMap m) {
      return new SynchronizedSortedMap(m);
   }

   public static Object2ObjectSortedMap synchronize(Object2ObjectSortedMap m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static Object2ObjectSortedMap unmodifiable(Object2ObjectSortedMap m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap extends Object2ObjectMaps.EmptyMap implements Object2ObjectSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      public Comparator comparator() {
         return null;
      }

      public ObjectSortedSet object2ObjectEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public ObjectSortedSet entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public ObjectSortedSet keySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public Object2ObjectSortedMap subMap(Object from, Object to) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
      }

      public Object2ObjectSortedMap headMap(Object to) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
      }

      public Object2ObjectSortedMap tailMap(Object from) {
         return Object2ObjectSortedMaps.EMPTY_MAP;
      }

      public Object firstKey() {
         throw new NoSuchElementException();
      }

      public Object lastKey() {
         throw new NoSuchElementException();
      }
   }

   public static class Singleton extends Object2ObjectMaps.Singleton implements Object2ObjectSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Comparator comparator;

      protected Singleton(Object key, Object value, Comparator comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(Object key, Object value) {
         this(key, value, (Comparator)null);
      }

      final int compare(Object k1, Object k2) {
         return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
      }

      public Comparator comparator() {
         return this.comparator;
      }

      public ObjectSortedSet object2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value), Object2ObjectSortedMaps.entryComparator(this.comparator));
         }

         return (ObjectSortedSet)this.entries;
      }

      public ObjectSortedSet entrySet() {
         return this.object2ObjectEntrySet();
      }

      public ObjectSortedSet keySet() {
         if (this.keys == null) {
            this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
         }

         return (ObjectSortedSet)this.keys;
      }

      public Object2ObjectSortedMap subMap(Object from, Object to) {
         return (Object2ObjectSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
      }

      public Object2ObjectSortedMap headMap(Object to) {
         return (Object2ObjectSortedMap)(this.compare(this.key, to) < 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
      }

      public Object2ObjectSortedMap tailMap(Object from) {
         return (Object2ObjectSortedMap)(this.compare(from, this.key) <= 0 ? this : Object2ObjectSortedMaps.EMPTY_MAP);
      }

      public Object firstKey() {
         return this.key;
      }

      public Object lastKey() {
         return this.key;
      }
   }
}
