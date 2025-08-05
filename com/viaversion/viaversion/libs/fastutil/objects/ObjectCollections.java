package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ObjectCollections {
   private ObjectCollections() {
   }

   public static ObjectCollection synchronize(ObjectCollection c) {
      return new SynchronizedCollection(c);
   }

   public static ObjectCollection synchronize(ObjectCollection c, Object sync) {
      return new SynchronizedCollection(c, sync);
   }

   public static ObjectCollection unmodifiable(ObjectCollection c) {
      return new UnmodifiableCollection(c);
   }

   public static ObjectCollection asCollection(ObjectIterable iterable) {
      return (ObjectCollection)(iterable instanceof ObjectCollection ? (ObjectCollection)iterable : new IterableCollection(iterable));
   }

   public abstract static class EmptyCollection extends AbstractObjectCollection {
      protected EmptyCollection() {
      }

      public boolean contains(Object k) {
         return false;
      }

      public Object[] toArray() {
         return ObjectArrays.EMPTY_ARRAY;
      }

      public Object[] toArray(Object[] array) {
         if (array.length > 0) {
            array[0] = null;
         }

         return array;
      }

      public ObjectBidirectionalIterator iterator() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }

      public int size() {
         return 0;
      }

      public void clear() {
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return !(o instanceof Collection) ? false : ((Collection)o).isEmpty();
         }
      }

      public void forEach(Consumer action) {
      }

      public boolean containsAll(Collection c) {
         return c.isEmpty();
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
         Objects.requireNonNull(filter);
         return false;
      }
   }

   public static class IterableCollection extends AbstractObjectCollection implements Serializable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final ObjectIterable iterable;

      protected IterableCollection(ObjectIterable iterable) {
         this.iterable = (ObjectIterable)Objects.requireNonNull(iterable);
      }

      public int size() {
         long size = this.iterable.spliterator().getExactSizeIfKnown();
         if (size >= 0L) {
            return (int)Math.min(2147483647L, size);
         } else {
            int c = 0;

            for(ObjectIterator<K> iterator = this.iterator(); iterator.hasNext(); ++c) {
               iterator.next();
            }

            return c;
         }
      }

      public boolean isEmpty() {
         return !this.iterable.iterator().hasNext();
      }

      public ObjectIterator iterator() {
         return this.iterable.iterator();
      }

      public ObjectSpliterator spliterator() {
         return this.iterable.spliterator();
      }
   }

   static class SizeDecreasingSupplier implements Supplier {
      static final int RECOMMENDED_MIN_SIZE = 8;
      final AtomicInteger suppliedCount = new AtomicInteger(0);
      final int expectedFinalSize;
      final IntFunction builder;

      SizeDecreasingSupplier(int expectedFinalSize, IntFunction builder) {
         this.expectedFinalSize = expectedFinalSize;
         this.builder = builder;
      }

      public ObjectCollection get() {
         int expectedNeededNextSize = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
         if (expectedNeededNextSize < 0) {
            expectedNeededNextSize = 8;
         }

         return (ObjectCollection)this.builder.apply(expectedNeededNextSize);
      }
   }
}
