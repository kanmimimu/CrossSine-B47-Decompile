package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Comparator;
import java.util.SortedMap;

public interface Object2ObjectSortedMap extends Object2ObjectMap, SortedMap {
   Object2ObjectSortedMap subMap(Object var1, Object var2);

   Object2ObjectSortedMap headMap(Object var1);

   Object2ObjectSortedMap tailMap(Object var1);

   default ObjectSortedSet entrySet() {
      return this.object2ObjectEntrySet();
   }

   ObjectSortedSet object2ObjectEntrySet();

   ObjectSortedSet keySet();

   ObjectCollection values();

   Comparator comparator();

   public interface FastSortedEntrySet extends ObjectSortedSet, Object2ObjectMap.FastEntrySet {
      ObjectBidirectionalIterator fastIterator();

      ObjectBidirectionalIterator fastIterator(Object2ObjectMap.Entry var1);
   }
}
