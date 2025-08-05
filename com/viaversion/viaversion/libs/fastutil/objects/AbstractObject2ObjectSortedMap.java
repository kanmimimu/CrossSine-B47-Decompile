package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Comparator;

public abstract class AbstractObject2ObjectSortedMap extends AbstractObject2ObjectMap implements Object2ObjectSortedMap {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractObject2ObjectSortedMap() {
   }

   public ObjectSortedSet keySet() {
      return new KeySet();
   }

   public ObjectCollection values() {
      return new ValuesCollection();
   }

   protected class KeySet extends AbstractObjectSortedSet {
      public boolean contains(Object k) {
         return AbstractObject2ObjectSortedMap.this.containsKey(k);
      }

      public int size() {
         return AbstractObject2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractObject2ObjectSortedMap.this.clear();
      }

      public Comparator comparator() {
         return AbstractObject2ObjectSortedMap.this.comparator();
      }

      public Object first() {
         return AbstractObject2ObjectSortedMap.this.firstKey();
      }

      public Object last() {
         return AbstractObject2ObjectSortedMap.this.lastKey();
      }

      public ObjectSortedSet headSet(Object to) {
         return AbstractObject2ObjectSortedMap.this.headMap(to).keySet();
      }

      public ObjectSortedSet tailSet(Object from) {
         return AbstractObject2ObjectSortedMap.this.tailMap(from).keySet();
      }

      public ObjectSortedSet subSet(Object from, Object to) {
         return AbstractObject2ObjectSortedMap.this.subMap(from, to).keySet();
      }

      public ObjectBidirectionalIterator iterator(Object from) {
         return new KeySetIterator(AbstractObject2ObjectSortedMap.this.object2ObjectEntrySet().iterator(new AbstractObject2ObjectMap.BasicEntry(from, (Object)null)));
      }

      public ObjectBidirectionalIterator iterator() {
         return new KeySetIterator(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
      }
   }

   protected static class KeySetIterator implements ObjectBidirectionalIterator {
      protected final ObjectBidirectionalIterator i;

      public KeySetIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Object2ObjectMap.Entry)this.i.next()).getKey();
      }

      public Object previous() {
         return ((Object2ObjectMap.Entry)this.i.previous()).getKey();
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
         return new ValuesIterator(Object2ObjectSortedMaps.fastIterator(AbstractObject2ObjectSortedMap.this));
      }

      public boolean contains(Object k) {
         return AbstractObject2ObjectSortedMap.this.containsValue(k);
      }

      public int size() {
         return AbstractObject2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractObject2ObjectSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator implements ObjectIterator {
      protected final ObjectBidirectionalIterator i;

      public ValuesIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Object2ObjectMap.Entry)this.i.next()).getValue();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
