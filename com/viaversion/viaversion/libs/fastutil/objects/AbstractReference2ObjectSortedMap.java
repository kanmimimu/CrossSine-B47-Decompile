package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Comparator;

public abstract class AbstractReference2ObjectSortedMap extends AbstractReference2ObjectMap implements Reference2ObjectSortedMap {
   private static final long serialVersionUID = -1773560792952436569L;

   protected AbstractReference2ObjectSortedMap() {
   }

   public ReferenceSortedSet keySet() {
      return new KeySet();
   }

   public ObjectCollection values() {
      return new ValuesCollection();
   }

   protected class KeySet extends AbstractReferenceSortedSet {
      public boolean contains(Object k) {
         return AbstractReference2ObjectSortedMap.this.containsKey(k);
      }

      public int size() {
         return AbstractReference2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractReference2ObjectSortedMap.this.clear();
      }

      public Comparator comparator() {
         return AbstractReference2ObjectSortedMap.this.comparator();
      }

      public Object first() {
         return AbstractReference2ObjectSortedMap.this.firstKey();
      }

      public Object last() {
         return AbstractReference2ObjectSortedMap.this.lastKey();
      }

      public ReferenceSortedSet headSet(Object to) {
         return AbstractReference2ObjectSortedMap.this.headMap(to).keySet();
      }

      public ReferenceSortedSet tailSet(Object from) {
         return AbstractReference2ObjectSortedMap.this.tailMap(from).keySet();
      }

      public ReferenceSortedSet subSet(Object from, Object to) {
         return AbstractReference2ObjectSortedMap.this.subMap(from, to).keySet();
      }

      public ObjectBidirectionalIterator iterator(Object from) {
         return new KeySetIterator(AbstractReference2ObjectSortedMap.this.reference2ObjectEntrySet().iterator(new AbstractReference2ObjectMap.BasicEntry(from, (Object)null)));
      }

      public ObjectBidirectionalIterator iterator() {
         return new KeySetIterator(Reference2ObjectSortedMaps.fastIterator(AbstractReference2ObjectSortedMap.this));
      }
   }

   protected static class KeySetIterator implements ObjectBidirectionalIterator {
      protected final ObjectBidirectionalIterator i;

      public KeySetIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Reference2ObjectMap.Entry)this.i.next()).getKey();
      }

      public Object previous() {
         return ((Reference2ObjectMap.Entry)this.i.previous()).getKey();
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
         return new ValuesIterator(Reference2ObjectSortedMaps.fastIterator(AbstractReference2ObjectSortedMap.this));
      }

      public boolean contains(Object k) {
         return AbstractReference2ObjectSortedMap.this.containsValue(k);
      }

      public int size() {
         return AbstractReference2ObjectSortedMap.this.size();
      }

      public void clear() {
         AbstractReference2ObjectSortedMap.this.clear();
      }
   }

   protected static class ValuesIterator implements ObjectIterator {
      protected final ObjectBidirectionalIterator i;

      public ValuesIterator(ObjectBidirectionalIterator i) {
         this.i = i;
      }

      public Object next() {
         return ((Reference2ObjectMap.Entry)this.i.next()).getValue();
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }
   }
}
