package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Comparator;
import java.util.SortedMap;

public interface Reference2ObjectSortedMap extends Reference2ObjectMap, SortedMap {
   Reference2ObjectSortedMap subMap(Object var1, Object var2);

   Reference2ObjectSortedMap headMap(Object var1);

   Reference2ObjectSortedMap tailMap(Object var1);

   default ObjectSortedSet entrySet() {
      return this.reference2ObjectEntrySet();
   }

   ObjectSortedSet reference2ObjectEntrySet();

   ReferenceSortedSet keySet();

   ObjectCollection values();

   Comparator comparator();

   public interface FastSortedEntrySet extends ObjectSortedSet, Reference2ObjectMap.FastEntrySet {
      ObjectBidirectionalIterator fastIterator();

      ObjectBidirectionalIterator fastIterator(Reference2ObjectMap.Entry var1);
   }
}
