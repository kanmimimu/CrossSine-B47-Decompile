package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public class ReferenceArraySet extends AbstractReferenceSet implements Serializable, Cloneable {
   private static final long serialVersionUID = 1L;
   protected transient Object[] a;
   protected int size;

   public ReferenceArraySet(Object[] a) {
      this.a = a;
      this.size = a.length;
   }

   public ReferenceArraySet() {
      this.a = ObjectArrays.EMPTY_ARRAY;
   }

   public ReferenceArraySet(int capacity) {
      this.a = new Object[capacity];
   }

   public ReferenceArraySet(ReferenceCollection c) {
      this(c.size());
      this.addAll(c);
   }

   public ReferenceArraySet(Collection c) {
      this(c.size());
      this.addAll(c);
   }

   public ReferenceArraySet(ReferenceSet c) {
      this(c.size());
      int i = 0;

      for(Object x : c) {
         this.a[i] = x;
         ++i;
      }

      this.size = i;
   }

   public ReferenceArraySet(Set c) {
      this(c.size());
      int i = 0;

      for(Object x : c) {
         this.a[i] = x;
         ++i;
      }

      this.size = i;
   }

   public ReferenceArraySet(Object[] a, int size) {
      this.a = a;
      this.size = size;
      if (size > a.length) {
         throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
      }
   }

   public static ReferenceArraySet of() {
      return ofUnchecked();
   }

   public static ReferenceArraySet of(Object e) {
      return ofUnchecked(e);
   }

   @SafeVarargs
   public static ReferenceArraySet of(Object... a) {
      if (a.length == 2) {
         if (a[0] == a[1]) {
            throw new IllegalArgumentException("Duplicate element: " + a[1]);
         }
      } else if (a.length > 2) {
         ReferenceOpenHashSet.of(a);
      }

      return ofUnchecked(a);
   }

   public static ReferenceArraySet ofUnchecked() {
      return new ReferenceArraySet();
   }

   @SafeVarargs
   public static ReferenceArraySet ofUnchecked(Object... a) {
      return new ReferenceArraySet(a);
   }

   private int findKey(Object o) {
      Object[] a = this.a;
      int i = this.size;

      while(i-- != 0) {
         if (a[i] == o) {
            return i;
         }
      }

      return -1;
   }

   public ObjectIterator iterator() {
      return new ObjectIterator() {
         int curr = -1;
         int next = 0;

         public boolean hasNext() {
            return this.next < ReferenceArraySet.this.size;
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return ReferenceArraySet.this.a[this.curr = this.next++];
            }
         }

         public void remove() {
            if (this.curr == -1) {
               throw new IllegalStateException();
            } else {
               this.curr = -1;
               int tail = ReferenceArraySet.this.size-- - this.next--;
               System.arraycopy(ReferenceArraySet.this.a, this.next + 1, ReferenceArraySet.this.a, this.next, tail);
               ReferenceArraySet.this.a[ReferenceArraySet.this.size] = null;
            }
         }

         public int skip(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               n = Math.min(n, ReferenceArraySet.this.size - this.next);
               this.next += n;
               if (n != 0) {
                  this.curr = this.next - 1;
               }

               return n;
            }
         }

         public void forEachRemaining(Consumer action) {
            Object[] a = ReferenceArraySet.this.a;

            while(this.next < ReferenceArraySet.this.size) {
               action.accept(a[this.next++]);
            }

         }
      };
   }

   public ObjectSpliterator spliterator() {
      return new Spliterator();
   }

   public boolean contains(Object k) {
      return this.findKey(k) != -1;
   }

   public int size() {
      return this.size;
   }

   public boolean remove(Object k) {
      int pos = this.findKey(k);
      if (pos == -1) {
         return false;
      } else {
         int tail = this.size - pos - 1;

         for(int i = 0; i < tail; ++i) {
            this.a[pos + i] = this.a[pos + i + 1];
         }

         --this.size;
         this.a[this.size] = null;
         return true;
      }
   }

   public boolean add(Object k) {
      int pos = this.findKey(k);
      if (pos != -1) {
         return false;
      } else {
         if (this.size == this.a.length) {
            Object[] b = new Object[this.size == 0 ? 2 : this.size * 2];

            for(int i = this.size; i-- != 0; b[i] = this.a[i]) {
            }

            this.a = b;
         }

         this.a[this.size++] = k;
         return true;
      }
   }

   public void clear() {
      Arrays.fill(this.a, 0, this.size, (Object)null);
      this.size = 0;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Object[] toArray() {
      int size = this.size();
      return size == 0 ? ObjectArrays.EMPTY_ARRAY : Arrays.copyOf(this.a, size, Object[].class);
   }

   public Object[] toArray(Object[] a) {
      if (a == null) {
         a = (T[])(new Object[this.size]);
      } else if (a.length < this.size) {
         a = (T[])((Object[])Array.newInstance(a.getClass().getComponentType(), this.size));
      }

      System.arraycopy(this.a, 0, a, 0, this.size);
      if (a.length > this.size) {
         a[this.size] = null;
      }

      return a;
   }

   public ReferenceArraySet clone() {
      ReferenceArraySet<K> c;
      try {
         c = (ReferenceArraySet)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.a = this.a.clone();
      return c;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      Object[] a = this.a;

      for(int i = 0; i < this.size; ++i) {
         s.writeObject(a[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      Object[] a = this.a = new Object[this.size];

      for(int i = 0; i < this.size; ++i) {
         a[i] = s.readObject();
      }

   }

   private final class Spliterator implements ObjectSpliterator {
      boolean hasSplit;
      int pos;
      int max;

      public Spliterator() {
         this(0, ReferenceArraySet.this.size, false);
      }

      private Spliterator(int pos, int max, boolean hasSplit) {
         this.hasSplit = false;

         assert pos <= max : "pos " + pos + " must be <= max " + max;

         this.pos = pos;
         this.max = max;
         this.hasSplit = hasSplit;
      }

      private int getWorkingMax() {
         return this.hasSplit ? this.max : ReferenceArraySet.this.size;
      }

      public int characteristics() {
         return 16465;
      }

      public long estimateSize() {
         return (long)(this.getWorkingMax() - this.pos);
      }

      public boolean tryAdvance(Consumer action) {
         if (this.pos >= this.getWorkingMax()) {
            return false;
         } else {
            action.accept(ReferenceArraySet.this.a[this.pos++]);
            return true;
         }
      }

      public void forEachRemaining(Consumer action) {
         Object[] a = ReferenceArraySet.this.a;

         for(int max = this.getWorkingMax(); this.pos < max; ++this.pos) {
            action.accept(a[this.pos]);
         }

      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else {
            int max = this.getWorkingMax();
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
         int max = this.getWorkingMax();
         int retLen = max - this.pos >> 1;
         if (retLen <= 1) {
            return null;
         } else {
            this.max = max;
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            this.hasSplit = true;
            return ReferenceArraySet.this.new Spliterator(oldPos, myNewPos, true);
         }
      }
   }
}
