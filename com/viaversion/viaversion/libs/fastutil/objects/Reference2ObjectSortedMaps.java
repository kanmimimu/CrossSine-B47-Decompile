package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Reference2ObjectSortedMaps {
   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

   private Reference2ObjectSortedMaps() {
   }

   public static Comparator entryComparator(Comparator comparator) {
      return (x, y) -> comparator.compare(x.getKey(), y.getKey());
   }

   public static ObjectBidirectionalIterator fastIterator(Reference2ObjectSortedMap map) {
      ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
      return entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet ? ((Reference2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static ObjectBidirectionalIterable fastIterable(Reference2ObjectSortedMap map) {
      ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
      Object var2;
      if (entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet) {
         var2 = (Reference2ObjectSortedMap.FastSortedEntrySet)entries;
         Objects.requireNonNull(entries);
         var2 = var2::fastIterator;
      } else {
         var2 = entries;
      }

      return var2;
   }

   public static Reference2ObjectSortedMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Reference2ObjectSortedMap singleton(Object key, Object value) {
      return new Singleton(key, value);
   }

   public static Reference2ObjectSortedMap singleton(Object key, Object value, Comparator comparator) {
      return new Singleton(key, value, comparator);
   }

   public static Reference2ObjectSortedMap synchronize(Reference2ObjectSortedMap m) {
      return new SynchronizedSortedMap(m);
   }

   public static Reference2ObjectSortedMap synchronize(Reference2ObjectSortedMap m, Object sync) {
      return new SynchronizedSortedMap(m, sync);
   }

   public static Reference2ObjectSortedMap unmodifiable(Reference2ObjectSortedMap m) {
      return new UnmodifiableSortedMap(m);
   }

   public static class EmptySortedMap extends Reference2ObjectMaps.EmptyMap implements Reference2ObjectSortedMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySortedMap() {
      }

      public Comparator comparator() {
         return null;
      }

      public ObjectSortedSet reference2ObjectEntrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public ObjectSortedSet entrySet() {
         return ObjectSortedSets.EMPTY_SET;
      }

      public ReferenceSortedSet keySet() {
         return ReferenceSortedSets.EMPTY_SET;
      }

      public Reference2ObjectSortedMap subMap(Object from, Object to) {
         return Reference2ObjectSortedMaps.EMPTY_MAP;
      }

      public Reference2ObjectSortedMap headMap(Object to) {
         return Reference2ObjectSortedMaps.EMPTY_MAP;
      }

      public Reference2ObjectSortedMap tailMap(Object from) {
         return Reference2ObjectSortedMaps.EMPTY_MAP;
      }

      public Object firstKey() {
         throw new NoSuchElementException();
      }

      public Object lastKey() {
         throw new NoSuchElementException();
      }
   }

   public static class Singleton extends Reference2ObjectMaps.Singleton implements Reference2ObjectSortedMap, Serializable, Cloneable {
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

      public ObjectSortedSet reference2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSortedSets.singleton(new AbstractReference2ObjectMap.BasicEntry(this.key, this.value), Reference2ObjectSortedMaps.entryComparator(this.comparator));
         }

         return (ObjectSortedSet)this.entries;
      }

      public ObjectSortedSet entrySet() {
         return this.reference2ObjectEntrySet();
      }

      public ReferenceSortedSet keySet() {
         if (this.keys == null) {
            this.keys = ReferenceSortedSets.singleton(this.key, this.comparator);
         }

         return (ReferenceSortedSet)this.keys;
      }

      public Reference2ObjectSortedMap subMap(Object from, Object to) {
         return (Reference2ObjectSortedMap)(this.compare(from, this.key) <= 0 && this.compare(this.key, to) < 0 ? this : Reference2ObjectSortedMaps.EMPTY_MAP);
      }

      public Reference2ObjectSortedMap headMap(Object to) {
         return (Reference2ObjectSortedMap)(this.compare(this.key, to) < 0 ? this : Reference2ObjectSortedMaps.EMPTY_MAP);
      }

      public Reference2ObjectSortedMap tailMap(Object from) {
         return (Reference2ObjectSortedMap)(this.compare(from, this.key) <= 0 ? this : Reference2ObjectSortedMaps.EMPTY_MAP);
      }

      public Object firstKey() {
         return this.key;
      }

      public Object lastKey() {
         return this.key;
      }
   }
}
