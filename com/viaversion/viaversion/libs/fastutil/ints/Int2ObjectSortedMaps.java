package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Int2ObjectSortedMaps {
   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

   private Int2ObjectSortedMaps() {
   }

   public static Comparator entryComparator(IntComparator comparator) {
      return (x, y) -> comparator.compare((Integer)x.getKey(), (Integer)y.getKey());
   }

   public static ObjectBidirectionalIterator fastIterator(Int2ObjectSortedMap map) {
      ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
      return entries instanceof Int2ObjectSortedMap.FastSortedEntrySet ? ((Int2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static ObjectBidirectionalIterable fastIterable(Int2ObjectSortedMap map) {
      ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
      Object var2;
      if (entries instanceof Int2ObjectSortedMap.FastSortedEntrySet) {
         var2 = (Int2ObjectSortedMap.FastSortedEntrySet)entries;
         Objects.requireNonNull(entries);
         var2 = var2::fastIterator;
      } else {
         var2 = entries;
      }

      return var2;
   }

   public static Int2ObjectSortedMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Int2ObjectSortedMap singleton(Integer key, Object value) {
      return new Singleton(key, value);
   }

   public static Int2ObjectSortedMap singleton(Integer key, Object value, IntComparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Int2ObjectSortedMap singleton(int key, Object value) {
      return new Singleton(key, value);
   }

   public static Int2ObjectSortedMap singleton(int key, Object value, IntComparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Int2ObjectSortedMap synchronize(Int2ObjectSortedMap m) {
      return new SynchronizedSortedMap(m);
   }

   public static Int2ObjectSortedMap synchronize(Int2ObjectSortedMap m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static Int2ObjectSortedMap unmodifiable(Int2ObjectSortedMap m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap extends Int2ObjectMaps.EmptyMap implements Int2ObjectSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      public IntComparator comparator() {
         return null;
      }

      public ObjectSortedSet int2ObjectEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSortedSet entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public IntSortedSet keySet() {
         return IntSortedSets.EMPTY_SET;
      }

      public Int2ObjectSortedMap subMap(int from, int to) {
         return Int2ObjectSortedMaps.EMPTY_MAP;
      }

      public Int2ObjectSortedMap headMap(int to) {
         return Int2ObjectSortedMaps.EMPTY_MAP;
      }

      public Int2ObjectSortedMap tailMap(int from) {
         return Int2ObjectSortedMaps.EMPTY_MAP;
      }

      public int firstIntKey() {
         throw new NoSuchElementException();
      }

      public int lastIntKey() {
         throw new NoSuchElementException();
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap headMap(Integer oto) {
         return this.headMap(oto);
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap tailMap(Integer ofrom) {
         return this.tailMap(ofrom);
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap subMap(Integer ofrom, Integer oto) {
         return this.subMap(ofrom, oto);
      }

      /** @deprecated */
      @Deprecated
      public Integer firstKey() {
         return this.firstIntKey();
      }

      /** @deprecated */
      @Deprecated
      public Integer lastKey() {
         return this.lastIntKey();
      }
   }

   public static class Singleton extends Int2ObjectMaps.Singleton implements Int2ObjectSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final IntComparator comparator;

      protected Singleton(int key, Object value, IntComparator comparator) {
         super(key, value);
         this.comparator = comparator;
      }

      protected Singleton(int key, Object value) {
         this(key, value, (IntComparator)null);
      }

      final int compare(int k1, int k2) {
         return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
      }

      public IntComparator comparator() {
         return this.comparator;
      }

      public ObjectSortedSet int2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value), Int2ObjectSortedMaps.entryComparator(this.comparator));
         }

         return (ObjectSortedSet)this.entries;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSortedSet entrySet() {
         return this.int2ObjectEntrySet();
      }

      public IntSortedSet keySet() {
         if (this.keys == null) {
            this.keys = IntSortedSets.singleton(this.key, this.comparator);
         }

         return (IntSortedSet)this.keys;
      }

      public Int2ObjectSortedMap subMap(int from, int to) {
         return (Int2ObjectSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
      }

      public Int2ObjectSortedMap headMap(int to) {
         return (Int2ObjectSortedMap)(this.compare(this.key, to) < 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
      }

      public Int2ObjectSortedMap tailMap(int from) {
         return (Int2ObjectSortedMap)(this.compare(from, this.key) <= 0 ? this : Int2ObjectSortedMaps.EMPTY_MAP);
      }

      public int firstIntKey() {
         return this.key;
      }

      public int lastIntKey() {
         return this.key;
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap headMap(Integer oto) {
         return this.headMap(oto);
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap tailMap(Integer ofrom) {
         return this.tailMap(ofrom);
      }

      /** @deprecated */
      @Deprecated
      public Int2ObjectSortedMap subMap(Integer ofrom, Integer oto) {
         return this.subMap(ofrom, oto);
      }

      /** @deprecated */
      @Deprecated
      public Integer firstKey() {
         return this.firstIntKey();
      }

      /** @deprecated */
      @Deprecated
      public Integer lastKey() {
         return this.lastIntKey();
      }
   }
}
