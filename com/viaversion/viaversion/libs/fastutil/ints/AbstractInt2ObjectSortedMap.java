package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;

public abstract class AbstractInt2ObjectSortedMap extends AbstractInt2ObjectMap implements Int2ObjectSortedMap {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractInt2ObjectSortedMap() {
   }

   public IntSortedSet keySet() {
      return new KeySet();
   }

   public ObjectCollection values() {
      return new ValuesCollection();
   }

   protected class KeySet extends AbstractIntSortedSet {
      public boolean contains(int k) {
         return AbstractInt2ObjectSortedMap.this.containsKey(k);
      }

      public int size() {
         return AbstractInt2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractInt2ObjectSortedMap.this.clear();
      }

      public IntComparator comparator() {
         return AbstractInt2ObjectSortedMap.this.comparator();
      }

      public int firstInt() {
         return AbstractInt2ObjectSortedMap.this.firstIntKey();
      }

      public int lastInt() {
         return AbstractInt2ObjectSortedMap.this.lastIntKey();
      }

      public IntSortedSet headSet(int to) {
         return AbstractInt2ObjectSortedMap.this.headMap(to).keySet();
      }

      public IntSortedSet tailSet(int from) {
         return AbstractInt2ObjectSortedMap.this.tailMap(from).keySet();
      }

      public IntSortedSet subSet(int from, int to) {
         return AbstractInt2ObjectSortedMap.this.subMap(from, to).keySet();
      }

      public IntBidirectionalIterator iterator(int from) {
         return new KeySetIterator(AbstractInt2ObjectSortedMap.this.int2ObjectEntrySet().iterator(new AbstractInt2ObjectMap.BasicEntry(from, (Object)null)));
      }

      public IntBidirectionalIterator iterator() {
         return new KeySetIterator(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
      }
   }

   protected static class KeySetIterator implements IntBidirectionalIterator {
      protected final ObjectBidirectionalIterator i;

      public KeySetIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public int nextInt() {
         return ((Int2ObjectMap.Entry)this.i.next()).getIntKey();
      }

      public int previousInt() {
         return ((Int2ObjectMap.Entry)this.i.previous()).getIntKey();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }

      public boolean hasPrevious() {
         return this.i.hasPrevious();
      }
   }

   protected class ValuesCollection extends AbstractObjectCollection {
      public ObjectIterator iterator() {
         return new ValuesIterator(Int2ObjectSortedMaps.fastIterator(AbstractInt2ObjectSortedMap.this));
      }

      public boolean contains(Object k) {
         return AbstractInt2ObjectSortedMap.this.containsValue(k);
      }

      public int size() {
         return AbstractInt2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractInt2ObjectSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator implements ObjectIterator {
      protected final ObjectBidirectionalIterator i;

      public ValuesIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Int2ObjectMap.Entry)this.i.next()).getValue();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
