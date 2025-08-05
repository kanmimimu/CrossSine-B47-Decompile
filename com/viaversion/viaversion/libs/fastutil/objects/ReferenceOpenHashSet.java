package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

public class ReferenceOpenHashSet extends AbstractReferenceSet implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient Object[] key;
   protected transient int mask;
   protected transient boolean containsNull;
   protected transient int n;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   protected final float f;
   private static final Collector TO_SET_COLLECTOR;

   public ReferenceOpenHashSet(int expected, float f) {
      if (!(f <= 0.0F) && !(f >= 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.f = f;
            this.minN = this.n = HashCommon.arraySize(expected, f);
            this.mask = this.n - 1;
            this.maxFill = HashCommon.maxFill(this.n, f);
            this.key = new Object[this.n + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
      }
   }

   public ReferenceOpenHashSet(int expected) {
      this(expected, 0.75F);
   }

   public ReferenceOpenHashSet() {
      this(16, 0.75F);
   }

   public ReferenceOpenHashSet(Collection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public ReferenceOpenHashSet(Collection c) {
      this(c, 0.75F);
   }

   public ReferenceOpenHashSet(ReferenceCollection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public ReferenceOpenHashSet(ReferenceCollection c) {
      this(c, 0.75F);
   }

   public ReferenceOpenHashSet(Iterator i, float f) {
      this(16, f);

      while(i.hasNext()) {
         this.add(i.next());
      }

   }

   public ReferenceOpenHashSet(Iterator i) {
      this(i, 0.75F);
   }

   public ReferenceOpenHashSet(Object[] a, int offset, int length, float f) {
      this(length < 0 ? 0 : length, f);
      ObjectArrays.ensureOffsetLength(a, offset, length);

      for(int i = 0; i < length; ++i) {
         this.add(a[offset + i]);
      }

   }

   public ReferenceOpenHashSet(Object[] a, int offset, int length) {
      this(a, offset, length, 0.75F);
   }

   public ReferenceOpenHashSet(Object[] a, float f) {
      this(a, 0, a.length, f);
   }

   public ReferenceOpenHashSet(Object[] a) {
      this(a, 0.75F);
   }

   public static ReferenceOpenHashSet of() {
      return new ReferenceOpenHashSet();
   }

   public static ReferenceOpenHashSet of(Object e) {
      ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet(1, 0.75F);
      result.add(e);
      return result;
   }

   public static ReferenceOpenHashSet of(Object e0, Object e1) {
      ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet(2, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else {
         return result;
      }
   }

   public static ReferenceOpenHashSet of(Object e0, Object e1, Object e2) {
      ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet(3, 0.75F);
      result.add(e0);
      if (!result.add(e1)) {
         throw new IllegalArgumentException("Duplicate element: " + e1);
      } else if (!result.add(e2)) {
         throw new IllegalArgumentException("Duplicate element: " + e2);
      } else {
         return result;
      }
   }

   @SafeVarargs
   public static ReferenceOpenHashSet of(Object... a) {
      ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet(a.length, 0.75F);

      for(Object element : a) {
         if (!result.add(element)) {
            throw new IllegalArgumentException("Duplicate element " + element);
         }
      }

      return result;
   }

   private ReferenceOpenHashSet combine(ReferenceOpenHashSet toAddFrom) {
      this.addAll(toAddFrom);
      return this;
   }

   public static Collector toSet() {
      return TO_SET_COLLECTOR;
   }

   public static Collector toSetWithExpectedSize(int expectedSize) {
      return expectedSize <= 16 ? toSet() : Collector.of(new ReferenceCollections.SizeDecreasingSupplier(expectedSize, (size) -> size <= 16 ? new ReferenceOpenHashSet() : new ReferenceOpenHashSet(size)), ReferenceOpenHashSet::add, ReferenceOpenHashSet::combine, Characteristics.UNORDERED);
   }

   private int realSize() {
      return this.containsNull ? this.size - 1 : this.size;
   }

   public void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.f);
      if (needed > this.n) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((double)((float)capacity / this.f)))));
      if (needed > this.n) {
         this.rehash(needed);
      }

   }

   public boolean addAll(Collection c) {
      if ((double)this.f <= (double)0.5F) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity((long)(this.size() + c.size()));
      }

      return super.addAll(c);
   }

   public boolean add(Object k) {
      if (k == null) {
         if (this.containsNull) {
            return false;
         }

         this.containsNull = true;
      } else {
         K[] key = (K[])this.key;
         int pos;
         K curr;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
            if (curr == k) {
               return false;
            }

            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (curr == k) {
                  return false;
               }
            }
         }

         key[pos] = k;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.f));
      }

      return true;
   }

   protected final void shiftKeys(int pos) {
      K[] key = (K[])this.key;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         K curr;
         while(true) {
            if ((curr = (K)key[pos]) == null) {
               key[last] = null;
               return;
            }

            int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
            if (last <= pos) {
               if (last >= slot || slot > pos) {
                  break;
               }
            } else if (last >= slot && slot > pos) {
               break;
            }

            pos = pos + 1 & this.mask;
         }

         key[last] = curr;
      }
   }

   private boolean removeEntry(int pos) {
      --this.size;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return true;
   }

   private boolean removeNullEntry() {
      this.containsNull = false;
      this.key[this.n] = null;
      --this.size;
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

      return true;
   }

   public boolean remove(Object k) {
      if (k == null) {
         return this.containsNull ? this.removeNullEntry() : false;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return false;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return false;
         }
      }
   }

   public boolean contains(Object k) {
      if (k == null) {
         return this.containsNull;
      } else {
         K[] key = (K[])this.key;
         K curr;
         int pos;
         if ((curr = (K)key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) {
            return false;
         } else if (k == curr) {
            return true;
         } else {
            while((curr = (K)key[pos = pos + 1 & this.mask]) != null) {
               if (k == curr) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNull = false;
         Arrays.fill(this.key, (Object)null);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public ObjectIterator iterator() {
      return new SetIterator();
   }

   public ObjectSpliterator spliterator() {
      return new SetSpliterator();
   }

   public void forEach(Consumer action) {
      K[] key = (K[])this.key;
      if (this.containsNull) {
         action.accept(key[this.n]);
      }

      int pos = this.n;

      while(pos-- != 0) {
         if (key[pos] != null) {
            action.accept(key[pos]);
         }
      }

   }

   public boolean trim() {
      return this.trim(this.size);
   }

   public boolean trim(int n) {
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((double)((float)n / this.f)));
      if (l < this.n && this.size <= HashCommon.maxFill(l, this.f)) {
         try {
            this.rehash(l);
            return true;
         } catch (OutOfMemoryError var4) {
            return false;
         }
      } else {
         return true;
      }
   }

   protected void rehash(int newN) {
      K[] key = (K[])this.key;
      int mask = newN - 1;
      K[] newKey = (K[])(new Object[newN + 1]);
      int i = this.n;

      int pos;
      for(int j = this.realSize(); j-- != 0; newKey[pos] = key[i]) {
         --i;
         if (key[i] != null && newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) {
            while(newKey[pos = pos + 1 & mask] != null) {
            }
         }
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.key = newKey;
   }

   public ReferenceOpenHashSet clone() {
      ReferenceOpenHashSet<K> c;
      try {
         c = (ReferenceOpenHashSet)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = this.key.clone();
      c.containsNull = this.containsNull;
      return c;
   }

   public int hashCode() {
      int h = 0;
      K[] key = (K[])this.key;
      int j = this.realSize();

      for(int i = 0; j-- != 0; ++i) {
         while(key[i] == null) {
            ++i;
         }

         if (this != key[i]) {
            h += System.identityHashCode(key[i]);
         }
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      ObjectIterator<K> i = this.iterator();
      s.defaultWriteObject();
      int j = this.size;

      while(j-- != 0) {
         s.writeObject(i.next());
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.n = HashCommon.arraySize(this.size, this.f);
      this.maxFill = HashCommon.maxFill(this.n, this.f);
      this.mask = this.n - 1;
      K[] key = (K[])(this.key = new Object[this.n + 1]);

      K k;
      int pos;
      for(int i = this.size; i-- != 0; key[pos] = k) {
         k = (K)s.readObject();
         if (k == null) {
            pos = this.n;
            this.containsNull = true;
         } else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) {
            while(key[pos = pos + 1 & this.mask] != null) {
            }
         }
      }

   }

   private void checkTable() {
   }

   static {
      TO_SET_COLLECTOR = Collector.of(ReferenceOpenHashSet::new, ReferenceOpenHashSet::add, ReferenceOpenHashSet::combine, Characteristics.UNORDERED);
   }

   private final class SetIterator implements ObjectIterator {
      int pos;
      int last;
      int c;
      boolean mustReturnNull;
      ReferenceArrayList wrapped;

      private SetIterator() {
         this.pos = ReferenceOpenHashSet.this.n;
         this.last = -1;
         this.c = ReferenceOpenHashSet.this.size;
         this.mustReturnNull = ReferenceOpenHashSet.this.containsNull;
      }

      public boolean hasNext() {
         return this.c != 0;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.c;
            K[] key = (K[])ReferenceOpenHashSet.this.key;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               this.last = ReferenceOpenHashSet.this.n;
               return key[ReferenceOpenHashSet.this.n];
            } else {
               while(--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return key[this.last = this.pos];
                  }
               }

               this.last = Integer.MIN_VALUE;
               return this.wrapped.get(-this.pos - 1);
            }
         }
      }

      private final void shiftKeys(int pos) {
         K[] key = (K[])ReferenceOpenHashSet.this.key;

         while(true) {
            int last = pos;
            pos = pos + 1 & ReferenceOpenHashSet.this.mask;

            K curr;
            while(true) {
               if ((curr = (K)key[pos]) == null) {
                  key[last] = null;
                  return;
               }

               int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceOpenHashSet.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & ReferenceOpenHashSet.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new ReferenceArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == ReferenceOpenHashSet.this.n) {
               ReferenceOpenHashSet.this.containsNull = false;
               ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n] = null;
            } else {
               if (this.pos < 0) {
                  ReferenceOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, (Object)null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --ReferenceOpenHashSet.this.size;
            this.last = -1;
         }
      }

      public void forEachRemaining(Consumer action) {
         K[] key = (K[])ReferenceOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            this.last = ReferenceOpenHashSet.this.n;
            action.accept(key[ReferenceOpenHashSet.this.n]);
            --this.c;
         }

         while(this.c != 0) {
            if (--this.pos < 0) {
               this.last = Integer.MIN_VALUE;
               action.accept(this.wrapped.get(-this.pos - 1));
               --this.c;
            } else if (key[this.pos] != null) {
               action.accept(key[this.last = this.pos]);
               --this.c;
            }
         }

      }
   }

   private final class SetSpliterator implements ObjectSpliterator {
      private static final int POST_SPLIT_CHARACTERISTICS = 1;
      int pos = 0;
      int max;
      int c;
      boolean mustReturnNull;
      boolean hasSplit;

      SetSpliterator() {
         this.max = ReferenceOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = ReferenceOpenHashSet.this.containsNull;
         this.hasSplit = false;
      }

      SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
         this.max = ReferenceOpenHashSet.this.n;
         this.c = 0;
         this.mustReturnNull = ReferenceOpenHashSet.this.containsNull;
         this.hasSplit = false;
         this.pos = pos;
         this.max = max;
         this.mustReturnNull = mustReturnNull;
         this.hasSplit = hasSplit;
      }

      public boolean tryAdvance(Consumer action) {
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            ++this.c;
            action.accept(ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n]);
            return true;
         } else {
            for(K[] key = (K[])ReferenceOpenHashSet.this.key; this.pos < this.max; ++this.pos) {
               if (key[this.pos] != null) {
                  ++this.c;
                  action.accept(key[this.pos++]);
                  return true;
               }
            }

            return false;
         }
      }

      public void forEachRemaining(Consumer action) {
         K[] key = (K[])ReferenceOpenHashSet.this.key;
         if (this.mustReturnNull) {
            this.mustReturnNull = false;
            action.accept(key[ReferenceOpenHashSet.this.n]);
            ++this.c;
         }

         for(; this.pos < this.max; ++this.pos) {
            if (key[this.pos] != null) {
               action.accept(key[this.pos]);
               ++this.c;
            }
         }

      }

      public int characteristics() {
         return this.hasSplit ? 1 : 65;
      }

      public long estimateSize() {
         return !this.hasSplit ? (long)(ReferenceOpenHashSet.this.size - this.c) : Math.min((long)(ReferenceOpenHashSet.this.size - this.c), (long)((double)ReferenceOpenHashSet.this.realSize() / (double)ReferenceOpenHashSet.this.n * (double)(this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
      }

      public SetSpliterator trySplit() {
         if (this.pos >= this.max - 1) {
            return null;
         } else {
            int retLen = this.max - this.pos >> 1;
            if (retLen <= 1) {
               return null;
            } else {
               int myNewPos = this.pos + retLen;
               int retPos = this.pos;
               ReferenceOpenHashSet<K>.SetSpliterator split = ReferenceOpenHashSet.this.new SetSpliterator(retPos, myNewPos, this.mustReturnNull, true);
               this.pos = myNewPos;
               this.mustReturnNull = false;
               this.hasSplit = true;
               return split;
            }
         }
      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (n == 0L) {
            return 0L;
         } else {
            long skipped = 0L;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               ++skipped;
               --n;
            }

            K[] key = (K[])ReferenceOpenHashSet.this.key;

            while(this.pos < this.max && n > 0L) {
               if (key[this.pos++] != null) {
                  ++skipped;
                  --n;
               }
            }

            return skipped;
         }
      }
   }
}
