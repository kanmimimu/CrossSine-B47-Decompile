package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import java.util.Comparator;

public abstract class AbstractObject2IntSortedMap extends AbstractObject2IntMap implements Object2IntSortedMap {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractObject2IntSortedMap() {
   }

   public ObjectSortedSet keySet() {
      return new KeySet();
   }

   public IntCollection values() {
      return new ValuesCollection();
   }

   protected class KeySet extends AbstractObjectSortedSet {
      public boolean contains(Object k) {
         return AbstractObject2IntSortedMap.this.containsKey(k);
      }

      public int size() {
         return AbstractObject2IntSortedMap.this.size();
      }

      public void clear() {
         AbstractObject2IntSortedMap.this.clear();
      }

      public Comparator comparator() {
         return AbstractObject2IntSortedMap.this.comparator();
      }

      public Object first() {
         return AbstractObject2IntSortedMap.this.firstKey();
      }

      public Object last() {
         return AbstractObject2IntSortedMap.this.lastKey();
      }

      public ObjectSortedSet headSet(Object to) {
         return AbstractObject2IntSortedMap.this.headMap(to).keySet();
      }

      public ObjectSortedSet tailSet(Object from) {
         return AbstractObject2IntSortedMap.this.tailMap(from).keySet();
      }

      public ObjectSortedSet subSet(Object from, Object to) {
         return AbstractObject2IntSortedMap.this.subMap(from, to).keySet();
      }

      public ObjectBidirectionalIterator iterator(Object from) {
         return new KeySetIterator(AbstractObject2IntSortedMap.this.object2IntEntrySet().iterator(new AbstractObject2IntMap.BasicEntry(from, 0)));
      }

      public ObjectBidirectionalIterator iterator() {
         return new KeySetIterator(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
      }
   }

   protected static class KeySetIterator implements ObjectBidirectionalIterator {
      protected final ObjectBidirectionalIterator i;

      public KeySetIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Object2IntMap.Entry)this.i.next()).getKey();
      }

      public Object previous() {
         return ((Object2IntMap.Entry)this.i.previous()).getKey();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }

      public boolean hasPrevious() {
         return this.i.hasPrevious();
      }
   }

   protected class ValuesCollection extends AbstractIntCollection {
      public IntIterator iterator() {
         return new ValuesIterator(Object2IntSortedMaps.fastIterator(AbstractObject2IntSortedMap.this));
      }

      public boolean contains(int k) {
         return AbstractObject2IntSortedMap.this.containsValue(k);
      }

      public int size() {
         return AbstractObject2IntSortedMap.this.size();
      }

      public void clear() {
         AbstractObject2IntSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator implements IntIterator {
      protected final ObjectBidirectionalIterator i;

      public ValuesIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public int nextInt() {
         return ((Object2IntMap.Entry)this.i.next()).getIntValue();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
