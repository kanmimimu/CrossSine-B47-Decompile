package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectIterators {
   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

   private ObjectIterators() {
   }

   public static ObjectIterator emptyIterator() {
      return EMPTY_ITERATOR;
   }

   public static ObjectListIterator singleton(Object element) {
      return new SingletonIterator(element);
   }

   public static ObjectListIterator wrap(Object[] array, int offset, int length) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ArrayIterator(array, offset, length);
   }

   public static ObjectListIterator wrap(Object[] array) {
      return new ArrayIterator(array, 0, array.length);
   }

   public static int unwrap(Iterator i, Object[] array, int offset, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0 && offset + max <= array.length) {
         int j;
         for(j = max; j-- != 0 && i.hasNext(); array[offset++] = i.next()) {
         }

         return max - j - 1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static int unwrap(Iterator i, Object[] array) {
      return unwrap(i, array, 0, array.length);
   }

   public static Object[] unwrap(Iterator i, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         K[] array = (K[])(new Object[16]);

         int j;
         for(j = 0; max-- != 0 && i.hasNext(); array[j++] = i.next()) {
            if (j == array.length) {
               array = (K[])ObjectArrays.grow(array, j + 1);
            }
         }

         return ObjectArrays.trim(array, j);
      }
   }

   public static Object[] unwrap(Iterator i) {
      return unwrap(i, Integer.MAX_VALUE);
   }

   public static long unwrap(Iterator i, Object[][] array, long offset, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0L && offset + max <= BigArrays.length(array)) {
         long j = max;

         while(j-- != 0L && i.hasNext()) {
            BigArrays.set(array, offset++, i.next());
         }

         return max - j - 1L;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static long unwrap(Iterator i, Object[][] array) {
      return unwrap(i, array, 0L, BigArrays.length(array));
   }

   public static int unwrap(Iterator i, ObjectCollection c, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while(j-- != 0 && i.hasNext()) {
            c.add(i.next());
         }

         return max - j - 1;
      }
   }

   public static Object[][] unwrapBig(Iterator i, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         K[][] array = (K[][])ObjectBigArrays.newBigArray(16L);

         long j;
         for(j = 0L; max-- != 0L && i.hasNext(); BigArrays.set(array, j++, i.next())) {
            if (j == BigArrays.length(array)) {
               array = (K[][])BigArrays.grow(array, j + 1L);
            }
         }

         return BigArrays.trim(array, j);
      }
   }

   public static Object[][] unwrapBig(Iterator i) {
      return unwrapBig(i, Long.MAX_VALUE);
   }

   public static long unwrap(Iterator i, ObjectCollection c) {
      long n;
      for(n = 0L; i.hasNext(); ++n) {
         c.add(i.next());
      }

      return n;
   }

   public static int pour(Iterator i, ObjectCollection s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while(j-- != 0 && i.hasNext()) {
            s.add(i.next());
         }

         return max - j - 1;
      }
   }

   public static int pour(Iterator i, ObjectCollection s) {
      return pour(i, s, Integer.MAX_VALUE);
   }

   public static ObjectList pour(Iterator i, int max) {
      ObjectArrayList<K> l = new ObjectArrayList();
      pour(i, l, max);
      l.trim();
      return l;
   }

   public static ObjectList pour(Iterator i) {
      return pour(i, Integer.MAX_VALUE);
   }

   public static ObjectIterator asObjectIterator(Iterator i) {
      return (ObjectIterator)(i instanceof ObjectIterator ? (ObjectIterator)i : new IteratorWrapper(i));
   }

   public static ObjectListIterator asObjectIterator(ListIterator i) {
      return (ObjectListIterator)(i instanceof ObjectListIterator ? (ObjectListIterator)i : new ListIteratorWrapper(i));
   }

   public static boolean any(Iterator iterator, Predicate predicate) {
      return indexOf(iterator, predicate) != -1;
   }

   public static boolean all(Iterator iterator, Predicate predicate) {
      Objects.requireNonNull(predicate);

      while(iterator.hasNext()) {
         if (!predicate.test(iterator.next())) {
            return false;
         }
      }

      return true;
   }

   public static int indexOf(Iterator iterator, Predicate predicate) {
      Objects.requireNonNull(predicate);

      for(int i = 0; iterator.hasNext(); ++i) {
         if (predicate.test(iterator.next())) {
            return i;
         }
      }

      return -1;
   }

   @SafeVarargs
   public static ObjectIterator concat(ObjectIterator... a) {
      return concat(a, 0, a.length);
   }

   public static ObjectIterator concat(ObjectIterator[] a, int offset, int length) {
      return new IteratorConcatenator(a, offset, length);
   }

   public static ObjectIterator unmodifiable(ObjectIterator i) {
      return new UnmodifiableIterator(i);
   }

   public static ObjectBidirectionalIterator unmodifiable(ObjectBidirectionalIterator i) {
      return new UnmodifiableBidirectionalIterator(i);
   }

   public static ObjectListIterator unmodifiable(ObjectListIterator i) {
      return new UnmodifiableListIterator(i);
   }

   public static class EmptyIterator implements ObjectListIterator, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyIterator() {
      }

      public boolean hasNext() {
         return false;
      }

      public boolean hasPrevious() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public Object previous() {
         throw new NoSuchElementException();
      }

      public int nextIndex() {
         return 0;
      }

      public int previousIndex() {
         return -1;
      }

      public int skip(int n) {
         return 0;
      }

      public int back(int n) {
         return 0;
      }

      public void forEachRemaining(Consumer action) {
      }

      public Object clone() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      private Object readResolve() {
         return ObjectIterators.EMPTY_ITERATOR;
      }
   }

   private static class SingletonIterator implements ObjectListIterator {
      private final Object element;
      private byte curr;

      public SingletonIterator(Object element) {
         this.element = element;
      }

      public boolean hasNext() {
         return this.curr == 0;
      }

      public boolean hasPrevious() {
         return this.curr == 1;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 1;
            return this.element;
         }
      }

      public Object previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 0;
            return this.element;
         }
      }

      public void forEachRemaining(Consumer action) {
         Objects.requireNonNull(action);
         if (this.curr == 0) {
            action.accept(this.element);
            this.curr = 1;
         }

      }

      public int nextIndex() {
         return this.curr;
      }

      public int previousIndex() {
         return this.curr - 1;
      }

      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0 && this.curr >= 1) {
            this.curr = 1;
            return 1;
         } else {
            return 0;
         }
      }

      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0 && this.curr <= 0) {
            this.curr = 0;
            return 1;
         } else {
            return 0;
         }
      }
   }

   private static class ArrayIterator implements ObjectListIterator {
      private final Object[] array;
      private final int offset;
      private final int length;
      private int curr;

      public ArrayIterator(Object[] array, int offset, int length) {
         this.array = array;
         this.offset = offset;
         this.length = length;
      }

      public boolean hasNext() {
         return this.curr < this.length;
      }

      public boolean hasPrevious() {
         return this.curr > 0;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + this.curr++];
         }
      }

      public Object previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + --this.curr];
         }
      }

      public void forEachRemaining(Consumer action) {
         Objects.requireNonNull(action);

         for(K[] array = (K[])this.array; this.curr < this.length; ++this.curr) {
            action.accept(array[this.offset + this.curr]);
         }

      }

      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n <= this.length - this.curr) {
            this.curr += n;
            return n;
         } else {
            n = this.length - this.curr;
            this.curr = this.length;
            return n;
         }
      }

      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n <= this.curr) {
            this.curr -= n;
            return n;
         } else {
            n = this.curr;
            this.curr = 0;
            return n;
         }
      }

      public int nextIndex() {
         return this.curr;
      }

      public int previousIndex() {
         return this.curr - 1;
      }
   }

   private static class IteratorWrapper implements ObjectIterator {
      final Iterator i;

      public IteratorWrapper(Iterator i) {
         this.i = i;
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }

      public void remove() {
         this.i.remove();
      }

      public Object next() {
         return this.i.next();
      }

      public void forEachRemaining(Consumer action) {
         this.i.forEachRemaining(action);
      }
   }

   private static class ListIteratorWrapper implements ObjectListIterator {
      final ListIterator i;

      public ListIteratorWrapper(ListIterator i) {
         this.i = i;
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }

      public boolean hasPrevious() {
         return this.i.hasPrevious();
      }

      public int nextIndex() {
         return this.i.nextIndex();
      }

      public int previousIndex() {
         return this.i.previousIndex();
      }

      public void set(Object k) {
         this.i.set(k);
      }

      public void add(Object k) {
         this.i.add(k);
      }

      public void remove() {
         this.i.remove();
      }

      public Object next() {
         return this.i.next();
      }

      public Object previous() {
         return this.i.previous();
      }

      public void forEachRemaining(Consumer action) {
         this.i.forEachRemaining(action);
      }
   }

   public abstract static class AbstractIndexBasedIterator extends AbstractObjectIterator {
      protected final int minPos;
      protected int pos;
      protected int lastReturned;

      protected AbstractIndexBasedIterator(int minPos, int initialPos) {
         this.minPos = minPos;
         this.pos = initialPos;
      }

      protected abstract Object get(int var1);

      protected abstract void remove(int var1);

      protected abstract int getMaxPos();

      public boolean hasNext() {
         return this.pos < this.getMaxPos();
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.get(this.lastReturned = this.pos++);
         }
      }

      public void remove() {
         if (this.lastReturned == -1) {
            throw new IllegalStateException();
         } else {
            this.remove(this.lastReturned);
            if (this.lastReturned < this.pos) {
               --this.pos;
            }

            this.lastReturned = -1;
         }
      }

      public void forEachRemaining(Consumer action) {
         while(this.pos < this.getMaxPos()) {
            action.accept(this.get(this.lastReturned = this.pos++));
         }

      }

      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getMaxPos();
            int remaining = max - this.pos;
            if (n < remaining) {
               this.pos += n;
            } else {
               n = remaining;
               this.pos = max;
            }

            this.lastReturned = this.pos - 1;
            return n;
         }
      }
   }

   public abstract static class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements ObjectListIterator {
      protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
         super(minPos, initialPos);
      }

      protected abstract void add(int var1, Object var2);

      protected abstract void set(int var1, Object var2);

      public boolean hasPrevious() {
         return this.pos > this.minPos;
      }

      public Object previous() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.get(this.lastReturned = --this.pos);
         }
      }

      public int nextIndex() {
         return this.pos;
      }

      public int previousIndex() {
         return this.pos - 1;
      }

      public void add(Object k) {
         this.add(this.pos++, k);
         this.lastReturned = -1;
      }

      public void set(Object k) {
         if (this.lastReturned == -1) {
            throw new IllegalStateException();
         } else {
            this.set(this.lastReturned, k);
         }
      }

      public int back(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int remaining = this.pos - this.minPos;
            if (n < remaining) {
               this.pos -= n;
            } else {
               n = remaining;
               this.pos = this.minPos;
            }

            this.lastReturned = this.pos;
            return n;
         }
      }
   }

   private static class IteratorConcatenator implements ObjectIterator {
      final ObjectIterator[] a;
      int offset;
      int length;
      int lastOffset = -1;

      public IteratorConcatenator(ObjectIterator[] a, int offset, int length) {
         this.a = a;
         this.offset = offset;
         this.length = length;
         this.advance();
      }

      private void advance() {
         while(this.length != 0 && !this.a[this.offset].hasNext()) {
            --this.length;
            ++this.offset;
         }

      }

      public boolean hasNext() {
         return this.length > 0;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            K next = (K)this.a[this.lastOffset = this.offset].next();
            this.advance();
            return next;
         }
      }

      public void forEachRemaining(Consumer action) {
         while(this.length > 0) {
            this.a[this.lastOffset = this.offset].forEachRemaining(action);
            this.advance();
         }

      }

      public void remove() {
         if (this.lastOffset == -1) {
            throw new IllegalStateException();
         } else {
            this.a[this.lastOffset].remove();
         }
      }

      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            this.lastOffset = -1;

            int skipped;
            for(skipped = 0; skipped < n && this.length != 0; ++this.offset) {
               skipped += this.a[this.offset].skip(n - skipped);
               if (this.a[this.offset].hasNext()) {
                  break;
               }

               --this.length;
            }

            return skipped;
         }
      }
   }
}
