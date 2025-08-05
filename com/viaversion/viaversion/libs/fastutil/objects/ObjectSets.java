package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectSets {
   static final int ARRAY_SET_CUTOFF = 4;
   public static final EmptySet EMPTY_SET = new EmptySet();
   static final ObjectSet UNMODIFIABLE_EMPTY_SET;

   private ObjectSets() {
   }

   public static ObjectSet emptySet() {
      return EMPTY_SET;
   }

   public static ObjectSet singleton(Object element) {
      return new Singleton(element);
   }

   public static ObjectSet synchronize(ObjectSet s) {
      return new SynchronizedSet(s);
   }

   public static ObjectSet synchronize(ObjectSet s, Object sync) {
      return new SynchronizedSet(s, sync);
   }

   public static ObjectSet unmodifiable(ObjectSet s) {
      return new UnmodifiableSet(s);
   }

   static {
      UNMODIFIABLE_EMPTY_SET = unmodifiable(new ObjectArraySet(ObjectArrays.EMPTY_ARRAY));
   }

   public static class EmptySet extends ObjectCollections.EmptyCollection implements ObjectSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      public boolean remove(Object ok) {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return ObjectSets.EMPTY_SET;
      }

      public boolean equals(Object o) {
         return o instanceof Set && ((Set)o).isEmpty();
      }

      private Object readResolve() {
         return ObjectSets.EMPTY_SET;
      }
   }

   public static class Singleton extends AbstractObjectSet implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final Object element;

      protected Singleton(Object element) {
         this.element = element;
      }

      public boolean contains(Object k) {
         return Objects.equals(k, this.element);
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
