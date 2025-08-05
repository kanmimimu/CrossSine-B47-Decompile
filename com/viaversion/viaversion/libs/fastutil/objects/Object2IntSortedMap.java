package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import java.util.Comparator;
import java.util.SortedMap;

public interface Object2IntSortedMap extends Object2IntMap, SortedMap {
   Object2IntSortedMap subMap(Object var1, Object var2);

   Object2IntSortedMap headMap(Object var1);

   Object2IntSortedMap tailMap(Object var1);

   /** @deprecated */
   @Deprecated
   default ObjectSortedSet entrySet() {
      return this.object2IntEntrySet();
   }

   ObjectSortedSet object2IntEntrySet();

   ObjectSortedSet keySet();

   IntCollection values();

   Comparator comparator();

   public interface FastSortedEntrySet extends ObjectSortedSet, Object2IntMap.FastEntrySet {
      ObjectBidirectionalIterator fastIterator();

      ObjectBidirectionalIterator fastIterator(Object2IntMap.Entry var1);
   }
}
