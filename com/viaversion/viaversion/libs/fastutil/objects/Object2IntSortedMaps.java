package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Object2IntSortedMaps {
   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

   private Object2IntSortedMaps() {
   }

   public static Comparator entryComparator(Comparator comparator) {
      return (x, y) -> comparator.compare(x.getKey(), y.getKey());
   }

   public static ObjectBidirectionalIterator fastIterator(Object2IntSortedMap map) {
      ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      return entries instanceof Object2IntSortedMap.FastSortedEntrySet ? ((Object2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static ObjectBidirectionalIterable fastIterable(Object2IntSortedMap map) {
      ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
      Object var2;
      if (entries instanceof Object2IntSortedMap.FastSortedEntrySet) {
         var2 = (Object2IntSortedMap.FastSortedEntrySet)entries;
         Objects.requireNonNull(entries);
         var2 = var2::fastIterator;
      } else {
         var2 = entries;
      }

      return var2;
   }

   public static Object2IntSortedMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Object2IntSortedMap singleton(Object key, Integer value) {
      return new Singleton(key, value);
   }

   public static Object2IntSortedMap singleton(Object key, Integer value, Comparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Object2IntSortedMap singleton(Object key, int value) {
      return new Singleton(key, value);
   }

   public static Object2IntSortedMap singleton(Object key, int value, Comparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Object2IntSortedMap synchronize(Object2IntSortedMap m) {
      return new SynchronizedSortedMap(m);
   }

   public static Object2IntSortedMap synchronize(Object2IntSortedMap m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static Object2IntSortedMap unmodifiable(Object2IntSortedMap m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap extends Object2IntMaps.EmptyMap implements Object2IntSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      public Comparator comparator() {
         return null;
      }

      public ObjectSortedSet object2IntEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSortedSet entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public ObjectSortedSet keySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public Object2IntSortedMap subMap(Object from, Object to) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      public Object2IntSortedMap headMap(Object to) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      public Object2IntSortedMap tailMap(Object from) {
         return Object2IntSortedMaps.EMPTY_MAP;
      }

      public Object firstKey() {
         throw new NoSuchElementException();
      }

      public Object lastKey() {
         throw new NoSuchElementException();
      }
   }

   public static class Singleton extends Object2IntMaps.Singleton implements Object2IntSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Comparator comparator;

      protected Singleton(Object key, int value, Comparator comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(Object key, int value) {
         this(key, value, (Comparator)null);
      }

      final int compare(Object k1, Object k2) {
         return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
      }

      public Comparator comparator() {
         return this.comparator;
      }

      public ObjectSortedSet object2IntEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value), Object2IntSortedMaps.entryComparator(this.comparator));
         }

         return (ObjectSortedSet)this.entries;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSortedSet entrySet() {
         return this.object2IntEntrySet();
      }

      public ObjectSortedSet keySet() {
         if (this.keys == null) {
            this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
         }

         return (ObjectSortedSet)this.keys;
      }

      public Object2IntSortedMap subMap(Object from, Object to) {
         return (Object2IntSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      public Object2IntSortedMap headMap(Object to) {
         return (Object2IntSortedMap)(this.compare(this.key, to) < 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      public Object2IntSortedMap tailMap(Object from) {
         return (Object2IntSortedMap)(this.compare(from, this.key) <= 0 ? this : Object2IntSortedMaps.EMPTY_MAP);
      }

      public Object firstKey() {
         return this.key;
      }

      public Object lastKey() {
         return this.key;
      }
   }
}
