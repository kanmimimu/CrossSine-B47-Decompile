package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ReferenceSets {
   static final int ARRAY_SET_CUTOFF = 4;
   public static final EmptySet EMPTY_SET = new EmptySet();
   static final ReferenceSet UNMODIFIABLE_EMPTY_SET;

   private ReferenceSets() {
   }

   public static ReferenceSet emptySet() {
      return EMPTY_SET;
   }

   public static ReferenceSet singleton(Object element) {
      return new Singleton(element);
   }

   public static ReferenceSet synchronize(ReferenceSet s) {
      return new SynchronizedSet(s);
   }

   public static ReferenceSet synchronize(ReferenceSet s, Object sync) {
      return new SynchronizedSet(s, sync);
   }

   public static ReferenceSet unmodifiable(ReferenceSet s) {
      return new UnmodifiableSet(s);
   }

   static {
      UNMODIFIABLE_EMPTY_SET = unmodifiable(new ReferenceArraySet(ObjectArrays.EMPTY_ARRAY));
   }

   public static class EmptySet extends ReferenceCollections.EmptyCollection implements ReferenceSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      public boolean remove(Object ok) {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return ReferenceSets.EMPTY_SET;
      }

      public boolean equals(Object o) {
         return o instanceof Set && ((Set)o).isEmpty();
      }

      private Object readResolve() {
         return ReferenceSets.EMPTY_SET;
      }
   }

   public static class Singleton extends AbstractReferenceSet implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Object element;

      protected Singleton(Object element) {
         this.element = element;
      }

      public boolean contains(Object k) {
         return k == this.element;
      }

      public boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      public ObjectListIterator iterator() {
         return ObjectIterators.singleton(this.element);
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.singleton(this.element);
      }

      public int size() {
         return 1;
      }

      public Object[] toArray() {
         return new Object[]{this.element};
      }

      public void forEach(Consumer action) {
         action.accept(this.element);
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public boolean removeIf(Predicate filter) {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return this;
      }
   }
}
