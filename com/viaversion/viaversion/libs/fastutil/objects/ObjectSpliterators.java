package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ObjectSpliterators {
   static final int BASE_SPLITERATOR_CHARACTERISTICS = 0;
   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 64;
   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16464;
   public static final int SET_SPLITERATOR_CHARACTERISTICS = 65;
   private static final int SORTED_CHARACTERISTICS = 20;
   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 85;
   public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

   private ObjectSpliterators() {
   }

   public static ObjectSpliterator emptySpliterator() {
      return EMPTY_SPLITERATOR;
   }

   public static ObjectSpliterator singleton(Object element) {
      return new SingletonSpliterator(element);
   }

   public static ObjectSpliterator singleton(Object element, Comparator comparator) {
      return new SingletonSpliterator(element, comparator);
   }

   public static ObjectSpliterator wrap(Object[] array, int offset, int length) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ArraySpliterator(array, offset, length, 0);
   }

   public static ObjectSpliterator wrap(Object[] array) {
      return new ArraySpliterator(array, 0, array.length, 0);
   }

   public static ObjectSpliterator wrap(Object[] array, int offset, int length, int additionalCharacteristics) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ArraySpliterator(array, offset, length, additionalCharacteristics);
   }

   public static ObjectSpliterator wrapPreSorted(Object[] array, int offset, int length, int additionalCharacteristics, Comparator comparator) {
      ObjectArrays.ensureOffsetLength(array, offset, length);
      return new ArraySpliteratorWithComparator(array, offset, length, additionalCharacteristics, comparator);
   }

   public static ObjectSpliterator wrapPreSorted(Object[] array, int offset, int length, Comparator comparator) {
      return wrapPreSorted(array, offset, length, 0, comparator);
   }

   public static ObjectSpliterator wrapPreSorted(Object[] array, Comparator comparator) {
      return wrapPreSorted(array, 0, array.length, comparator);
   }

   public static ObjectSpliterator asObjectSpliterator(Spliterator i) {
      return (ObjectSpliterator)(i instanceof ObjectSpliterator ? (ObjectSpliterator)i : new SpliteratorWrapper(i));
   }

   public static ObjectSpliterator asObjectSpliterator(Spliterator i, Comparator comparatorOverride) {
      if (i instanceof ObjectSpliterator) {
         throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName());
      } else {
         return new SpliteratorWrapperWithComparator(i, comparatorOverride);
      }
   }

   public static void onEachMatching(Spliterator spliterator, Predicate predicate, Consumer action) {
      Objects.requireNonNull(predicate);
      Objects.requireNonNull(action);
      spliterator.forEachRemaining((value) -> {
         if (predicate.test(value)) {
            action.accept(value);
         }

      });
   }

   @SafeVarargs
   public static ObjectSpliterator concat(ObjectSpliterator... a) {
      return concat(a, 0, a.length);
   }

   public static ObjectSpliterator concat(ObjectSpliterator[] a, int offset, int length) {
      return new SpliteratorConcatenator(a, offset, length);
   }

   public static ObjectSpliterator asSpliterator(ObjectIterator iter, long size, int additionalCharacterisitcs) {
      return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
   }

   public static ObjectSpliterator asSpliteratorFromSorted(ObjectIterator iter, long size, int additionalCharacterisitcs, Comparator comparator) {
      return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
   }

   public static ObjectSpliterator asSpliteratorUnknownSize(ObjectIterator iter, int characterisitcs) {
      return new SpliteratorFromIterator(iter, characterisitcs);
   }

   public static ObjectSpliterator asSpliteratorFromSortedUnknownSize(ObjectIterator iter, int additionalCharacterisitcs, Comparator comparator) {
      return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
   }

   public static ObjectIterator asIterator(ObjectSpliterator spliterator) {
      return new IteratorFromSpliterator(spliterator);
   }

   public static class EmptySpliterator implements ObjectSpliterator, Serializable, Cloneable {
      private static final long serialVersionUID = 8379247926738230492L;
      private static final int CHARACTERISTICS = 16448;

      protected EmptySpliterator() {
      }

      public boolean tryAdvance(Consumer action) {
         return false;
      }

      public ObjectSpliterator trySplit() {
         return null;
      }

      public long estimateSize() {
         return 0L;
      }

      public int characteristics() {
         return 16448;
      }

      public void forEachRemaining(Consumer action) {
      }

      public Object clone() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }

      private Object readResolve() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }
   }

   private static class SingletonSpliterator implements ObjectSpliterator {
      private final Object element;
      private final Comparator comparator;
      private boolean consumed;
      private static final int CHARACTERISTICS = 17493;

      public SingletonSpliterator(Object element) {
         this(element, (Comparator)null);
      }

      public SingletonSpliterator(Object element, Comparator comparator) {
         this.consumed = false;
         this.element = element;
         this.comparator = comparator;
      }

      public boolean tryAdvance(Consumer action) {
         Objects.requireNonNull(action);
         if (this.consumed) {
            return false;
         } else {
            this.consumed = true;
            action.accept(this.element);
            return true;
         }
      }

      public ObjectSpliterator trySplit() {
         return null;
      }

      public long estimateSize() {
         return this.consumed ? 0L : 1L;
      }

      public int characteristics() {
         return 17493 | (this.element != null ? 256 : 0);
      }

      public void forEachRemaining(Consumer action) {
         Objects.requireNonNull(action);
         if (!this.consumed) {
            this.consumed = true;
            action.accept(this.element);
         }

      }

      public Comparator getComparator() {
         return this.comparator;
      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n != 0L && !this.consumed) {
            this.consumed = true;
            return 1L;
         } else {
            return 0L;
         }
      }
   }

   private static class ArraySpliterator implements ObjectSpliterator {
      private static final int BASE_CHARACTERISTICS = 16464;
      final Object[] array;
      private final int offset;
      private int length;
      private int curr;
      final int characteristics;

      public ArraySpliterator(Object[] array, int offset, int length, int additionalCharacteristics) {
         this.array = array;
         this.offset = offset;
         this.length = length;
         this.characteristics = 16464 | additionalCharacteristics;
      }

      public boolean tryAdvance(Consumer action) {
         if (this.curr >= this.length) {
            return false;
         } else {
            Objects.requireNonNull(action);
            action.accept(this.array[this.offset + this.curr++]);
            return true;
         }
      }

      public long estimateSize() {
         return (long)(this.length - this.curr);
      }

      public int characteristics() {
         return this.characteristics;
      }

      protected ArraySpliterator makeForSplit(int newOffset, int newLength) {
         return new ArraySpliterator(this.array, newOffset, newLength, this.characteristics);
      }

      public ObjectSpliterator trySplit() {
         int retLength = this.length - this.curr >> 1;
         if (retLength <= 1) {
            return null;
         } else {
            int myNewCurr = this.curr + retLength;
            int retOffset = this.offset + this.curr;
            this.curr = myNewCurr;
            return this.makeForSplit(retOffset, retLength);
         }
      }

      public void forEachRemaining(Consumer action) {
         Objects.requireNonNull(action);

         for(K[] array = (K[])this.array; this.curr < this.length; ++this.curr) {
            action.accept(array[this.offset + this.curr]);
         }

      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.curr >= this.length) {
            return 0L;
         } else {
            int remaining = this.length - this.curr;
            if (n < (long)remaining) {
               this.curr = SafeMath.safeLongToInt((long)this.curr + n);
               return n;
            } else {
               n = (long)remaining;
               this.curr = this.length;
               return n;
            }
         }
      }
   }

   private static class ArraySpliteratorWithComparator extends ArraySpliterator {
      private final Comparator comparator;

      public ArraySpliteratorWithComparator(Object[] array, int offset, int length, int additionalCharacteristics, Comparator comparator) {
         super(array, offset, length, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      protected ArraySpliteratorWithComparator makeForSplit(int newOffset, int newLength) {
         return new ArraySpliteratorWithComparator(this.array, newOffset, newLength, this.characteristics, this.comparator);
      }

      public Comparator getComparator() {
         return this.comparator;
      }
   }

   private static class SpliteratorWrapper implements ObjectSpliterator {
      final Spliterator i;

      public SpliteratorWrapper(Spliterator i) {
         this.i = i;
      }

      public boolean tryAdvance(Consumer action) {
         return this.i.tryAdvance(action);
      }

      public void forEachRemaining(Consumer action) {
         this.i.forEachRemaining(action);
      }

      public long estimateSize() {
         return this.i.estimateSize();
      }

      public int characteristics() {
         return this.i.characteristics();
      }

      public Comparator getComparator() {
         return ObjectComparators.asObjectComparator(this.i.getComparator());
      }

      public ObjectSpliterator trySplit() {
         Spliterator<K> innerSplit = this.i.trySplit();
         return innerSplit == null ? null : new SpliteratorWrapper(innerSplit);
      }
   }

   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
      final Comparator comparator;

      public SpliteratorWrapperWithComparator(Spliterator i, Comparator comparator) {
         super(i);
         this.comparator = comparator;
      }

      public Comparator getComparator() {
         return this.comparator;
      }

      public ObjectSpliterator trySplit() {
         Spliterator<K> innerSplit = this.i.trySplit();
         return innerSplit == null ? null : new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
      }
   }

   public abstract static class AbstractIndexBasedSpliterator extends AbstractObjectSpliterator {
      protected int pos;

      protected AbstractIndexBasedSpliterator(int initialPos) {
         this.pos = initialPos;
      }

      protected abstract Object get(int var1);

      protected abstract int getMaxPos();

      protected abstract ObjectSpliterator makeForSplit(int var1, int var2);

      protected int computeSplitPoint() {
         return this.pos + (this.getMaxPos() - this.pos) / 2;
      }

      private void splitPointCheck(int splitPoint, int observedMax) {
         if (splitPoint < this.pos || splitPoint > observedMax) {
            throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
         }
      }

      public int characteristics() {
         return 16464;
      }

      public long estimateSize() {
         return (long)this.getMaxPos() - (long)this.pos;
      }

      public boolean tryAdvance(Consumer action) {
         if (this.pos >= this.getMaxPos()) {
            return false;
         } else {
            action.accept(this.get(this.pos++));
            return true;
         }
      }

      public void forEachRemaining(Consumer action) {
         for(int max = this.getMaxPos(); this.pos < max; ++this.pos) {
            action.accept(this.get(this.pos));
         }

      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getMaxPos();
            if (this.pos >= max) {
               return 0L;
            } else {
               int remaining = max - this.pos;
               if (n < (long)remaining) {
                  this.pos = SafeMath.safeLongToInt((long)this.pos + n);
                  return n;
               } else {
                  n = (long)remaining;
                  this.pos = max;
                  return n;
               }
            }
         }
      }

      public ObjectSpliterator trySplit() {
         int max = this.getMaxPos();
         int splitPoint = this.computeSplitPoint();
         if (splitPoint != this.pos && splitPoint != max) {
            this.splitPointCheck(splitPoint, max);
            int oldPos = this.pos;
            ObjectSpliterator<K> maybeSplit = this.makeForSplit(oldPos, splitPoint);
            if (maybeSplit != null) {
               this.pos = splitPoint;
            }

            return maybeSplit;
         } else {
            return null;
         }
      }
   }

   public abstract static class EarlyBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator {
      protected final int maxPos;

      protected EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
         super(initialPos);
         this.maxPos = maxPos;
      }

      protected final int getMaxPos() {
         return this.maxPos;
      }
   }

   public abstract static class LateBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator {
      protected int maxPos = -1;
      private boolean maxPosFixed;

      protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
         super(initialPos);
         this.maxPosFixed = false;
      }

      protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
         super(initialPos);
         this.maxPos = fixedMaxPos;
         this.maxPosFixed = true;
      }

      protected abstract int getMaxPosFromBackingStore();

      protected final int getMaxPos() {
         return this.maxPosFixed ? this.maxPos : this.getMaxPosFromBackingStore();
      }

      public ObjectSpliterator trySplit() {
         ObjectSpliterator<K> maybeSplit = super.trySplit();
         if (!this.maxPosFixed && maybeSplit != null) {
            this.maxPos = this.getMaxPosFromBackingStore();
            this.maxPosFixed = true;
         }

         return maybeSplit;
      }
   }

   private static class SpliteratorConcatenator implements ObjectSpliterator {
      private static final int EMPTY_CHARACTERISTICS = 16448;
      private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
      final ObjectSpliterator[] a;
      int offset;
      int length;
      long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
      int characteristics = 0;

      public SpliteratorConcatenator(ObjectSpliterator[] a, int offset, int length) {
         this.a = a;
         this.offset = offset;
         this.length = length;
         this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
         this.characteristics = this.computeCharacteristics();
      }

      private long recomputeRemaining() {
         int curLength = this.length - 1;
         int curOffset = this.offset + 1;
         long result = 0L;

         while(curLength > 0) {
            long cur = this.a[curOffset++].estimateSize();
            --curLength;
            if (cur == Long.MAX_VALUE) {
               return Long.MAX_VALUE;
            }

            result += cur;
            if (result == Long.MAX_VALUE || result < 0L) {
               return Long.MAX_VALUE;
            }
         }

         return result;
      }

      private int computeCharacteristics() {
         if (this.length <= 0) {
            return 16448;
         } else {
            int current = -1;
            int curLength = this.length;
            int curOffset = this.offset;
            if (curLength > 1) {
               current &= -6;
            }

            while(curLength > 0) {
               current &= this.a[curOffset++].characteristics();
               --curLength;
            }

            return current;
         }
      }

      private void advanceNextSpliterator() {
         if (this.length <= 0) {
            throw new AssertionError("advanceNextSpliterator() called with none remaining");
         } else {
            ++this.offset;
            --this.length;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
         }
      }

      public boolean tryAdvance(Consumer action) {
         boolean any = false;

         while(this.length > 0) {
            if (this.a[this.offset].tryAdvance(action)) {
               any = true;
               break;
            }

            this.advanceNextSpliterator();
         }

         return any;
      }

      public void forEachRemaining(Consumer action) {
         while(this.length > 0) {
            this.a[this.offset].forEachRemaining(action);
            this.advanceNextSpliterator();
         }

      }

      public long estimateSize() {
         if (this.length <= 0) {
            return 0L;
         } else {
            long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            return est < 0L ? Long.MAX_VALUE : est;
         }
      }

      public int characteristics() {
         return this.characteristics;
      }

      public Comparator getComparator() {
         if (this.length == 1 && (this.characteristics & 4) != 0) {
            return this.a[this.offset].getComparator();
         } else {
            throw new IllegalStateException();
         }
      }

      public ObjectSpliterator trySplit() {
         switch (this.length) {
            case 0:
               return null;
            case 1:
               ObjectSpliterator<K> split = this.a[this.offset].trySplit();
               this.characteristics = this.a[this.offset].characteristics();
               return split;
            case 2:
               ObjectSpliterator<K> split = this.a[this.offset++];
               --this.length;
               this.characteristics = this.a[this.offset].characteristics();
               this.remainingEstimatedExceptCurrent = 0L;
               return split;
            default:
               int mid = this.length >> 1;
               int ret_offset = this.offset;
               int new_offset = this.offset + mid;
               int new_length = this.length - mid;
               this.offset = new_offset;
               this.length = new_length;
               this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
               this.characteristics = this.computeCharacteristics();
               return new SpliteratorConcatenator(this.a, ret_offset, mid);
         }
      }

      public long skip(long n) {
         long skipped = 0L;
         if (this.length <= 0) {
            return 0L;
         } else {
            while(skipped < n && this.length >= 0) {
               long curSkipped = this.a[this.offset].skip(n - skipped);
               skipped += curSkipped;
               if (skipped < n) {
                  this.advanceNextSpliterator();
               }
            }

            return skipped;
         }
      }
   }

   private static class SpliteratorFromIterator implements ObjectSpliterator {
      private static final int BATCH_INCREMENT_SIZE = 1024;
      private static final int BATCH_MAX_SIZE = 33554432;
      private final ObjectIterator iter;
      final int characteristics;
      private final boolean knownSize;
      private long size = Long.MAX_VALUE;
      private int nextBatchSize = 1024;
      private ObjectSpliterator delegate = null;

      SpliteratorFromIterator(ObjectIterator iter, int characteristics) {
         this.iter = iter;
         this.characteristics = 0 | characteristics;
         this.knownSize = false;
      }

      SpliteratorFromIterator(ObjectIterator iter, long size, int additionalCharacteristics) {
         this.iter = iter;
         this.knownSize = true;
         this.size = size;
         if ((additionalCharacteristics & 4096) != 0) {
            this.characteristics = 0 | additionalCharacteristics;
         } else {
            this.characteristics = 16448 | additionalCharacteristics;
         }

      }

      public boolean tryAdvance(Consumer action) {
         if (this.delegate != null) {
            boolean hadRemaining = this.delegate.tryAdvance(action);
            if (!hadRemaining) {
               this.delegate = null;
            }

            return hadRemaining;
         } else if (!this.iter.hasNext()) {
            return false;
         } else {
            --this.size;
            action.accept(this.iter.next());
            return true;
         }
      }

      public void forEachRemaining(Consumer action) {
         if (this.delegate != null) {
            this.delegate.forEachRemaining(action);
            this.delegate = null;
         }

         this.iter.forEachRemaining(action);
         this.size = 0L;
      }

      public long estimateSize() {
         if (this.delegate != null) {
            return this.delegate.estimateSize();
         } else if (!this.iter.hasNext()) {
            return 0L;
         } else {
            return this.knownSize && this.size >= 0L ? this.size : Long.MAX_VALUE;
         }
      }

      public int characteristics() {
         return this.characteristics;
      }

      protected ObjectSpliterator makeForSplit(Object[] batch, int len) {
         return ObjectSpliterators.wrap(batch, 0, len, this.characteristics);
      }

      public ObjectSpliterator trySplit() {
         if (!this.iter.hasNext()) {
            return null;
         } else {
            int batchSizeEst = this.knownSize && this.size > 0L ? (int)Math.min((long)this.nextBatchSize, this.size) : this.nextBatchSize;
            K[] batch = (K[])(new Object[batchSizeEst]);

            int actualSeen;
            for(actualSeen = 0; actualSeen < batchSizeEst && this.iter.hasNext(); --this.size) {
               batch[actualSeen++] = this.iter.next();
            }

            if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
               for(batch = (K[])Arrays.copyOf(batch, this.nextBatchSize); this.iter.hasNext() && actualSeen < this.nextBatchSize; --this.size) {
                  batch[actualSeen++] = this.iter.next();
               }
            }

            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            ObjectSpliterator<K> split = this.makeForSplit(batch, actualSeen);
            if (!this.iter.hasNext()) {
               this.delegate = split;
               return split.trySplit();
            } else {
               return split;
            }
         }
      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.iter instanceof ObjectBigListIterator) {
            long skipped = ((ObjectBigListIterator)this.iter).skip(n);
            this.size -= skipped;
            return skipped;
         } else {
            long skippedSoFar;
            int skipped;
            for(skippedSoFar = 0L; skippedSoFar < n && this.iter.hasNext(); skippedSoFar += (long)skipped) {
               skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
               this.size -= (long)skipped;
            }

            return skippedSoFar;
         }
      }
   }

   private static class SpliteratorFromIteratorWithComparator extends SpliteratorFromIterator {
      private final Comparator comparator;

      SpliteratorFromIteratorWithComparator(ObjectIterator iter, int additionalCharacteristics, Comparator comparator) {
         super(iter, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      SpliteratorFromIteratorWithComparator(ObjectIterator iter, long size, int additionalCharacteristics, Comparator comparator) {
         super(iter, size, additionalCharacteristics | 20);
         this.comparator = comparator;
      }

      public Comparator getComparator() {
         return this.comparator;
      }

      protected ObjectSpliterator makeForSplit(Object[] array, int len) {
         return ObjectSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
      }
   }

   private static final class IteratorFromSpliterator implements ObjectIterator, Consumer {
      private final ObjectSpliterator spliterator;
      private Object holder = null;
      private boolean hasPeeked = false;

      IteratorFromSpliterator(ObjectSpliterator spliterator) {
         this.spliterator = spliterator;
      }

      public void accept(Object item) {
         this.holder = item;
      }

      public boolean hasNext() {
         if (this.hasPeeked) {
            return true;
         } else {
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
               return false;
            } else {
               this.hasPeeked = true;
               return true;
            }
         }
      }

      public Object next() {
         if (this.hasPeeked) {
            this.hasPeeked = false;
            return this.holder;
         } else {
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
               throw new NoSuchElementException();
            } else {
               return this.holder;
            }
         }
      }

      public void forEachRemaining(Consumer action) {
         if (this.hasPeeked) {
            this.hasPeeked = false;
            action.accept(this.holder);
         }

         this.spliterator.forEachRemaining(action);
      }

      public int skip(int n) {
         if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int skipped = 0;
            if (this.hasPeeked) {
               this.hasPeeked = false;
               this.spliterator.skip(1L);
               ++skipped;
               --n;
            }

            if (n > 0) {
               skipped += SafeMath.safeLongToInt(this.spliterator.skip((long)n));
            }

            return skipped;
         }
      }
   }
}
