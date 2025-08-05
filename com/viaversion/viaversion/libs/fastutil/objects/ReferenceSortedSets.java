package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class ReferenceSortedSets {
   public static final EmptySet EMPTY_SET = new EmptySet();

   private ReferenceSortedSets() {
   }

   public static ReferenceSortedSet emptySet() {
      return EMPTY_SET;
   }

   public static ReferenceSortedSet singleton(Object element) {
      return new Singleton(element);
   }

   public static ReferenceSortedSet singleton(Object element, Comparator comparator) {
      return new Singleton(element, comparator);
   }

   public static ReferenceSortedSet synchronize(ReferenceSortedSet s) {
      return new SynchronizedSortedSet(s);
   }

   public static ReferenceSortedSet synchronize(ReferenceSortedSet s, Object sync) {
      return new SynchronizedSortedSet(s, sync);
   }

   public static ReferenceSortedSet unmodifiable(ReferenceSortedSet s) {
      return new UnmodifiableSortedSet(s);
   }

   public static class EmptySet extends ReferenceSets.EmptySet implements ReferenceSortedSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      public ObjectBidirectionalIterator iterator(Object from) {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      public ReferenceSortedSet subSet(Object from, Object to) {
         return ReferenceSortedSets.EMPTY_SET;
      }

      public ReferenceSortedSet headSet(Object from) {
         return ReferenceSortedSets.EMPTY_SET;
      }

      public ReferenceSortedSet tailSet(Object to) {
         return ReferenceSortedSets.EMPTY_SET;
      }

      public Object first() {
         throw new NoSuchElementException();
      }

      public Object last() {
         throw new NoSuchElementException();
      }

      public Comparator comparator() {
         return null;
      }

      public Object clone() {
         return ReferenceSortedSets.EMPTY_SET;
      }

      private Object readResolve() {
         return ReferenceSortedSets.EMPTY_SET;
      }
   }

   public static class Singleton extends ReferenceSets.Singleton implements ReferenceSortedSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      final Comparator comparator;

      protected Singleton(Object element, Comparator comparator) {
         super(element);
         this.comparator = comparator;
      }

      Singleton(Object element) {
         this(element, (Comparator)null);
      }

      final int compare(Object k1, Object k2) {
         return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
      }

      public ObjectBidirectionalIterator iterator(Object from) {
         ObjectBidirectionalIterator<K> i = this.iterator();
         if (this.compare(this.element, from) <= 0) {
            i.next();
         }

         return i;
      }

      public Comparator comparator() {
         return this.comparator;
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.singleton(this.element, this.comparator);
      }

      public ReferenceSortedSet subSet(Object from, Object to) {
         return (ReferenceSortedSet)(this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0 ? this : ReferenceSortedSets.EMPTY_SET);
      }

      public ReferenceSortedSet headSet(Object to) {
         return (ReferenceSortedSet)(this.compare(this.element, to) < 0 ? this : ReferenceSortedSets.EMPTY_SET);
      }

      public ReferenceSortedSet tailSet(Object from) {
         return (ReferenceSortedSet)(this.compare(from, this.element) <= 0 ? this : ReferenceSortedSets.EMPTY_SET);
      }

      public Object first() {
         return this.element;
      }

      public Object last() {
         return this.element;
      }
   }
}
