package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectImmutableList extends ObjectLists.ImmutableListBase implements ObjectList, RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = 0L;
   static final ObjectImmutableList EMPTY;
   private final Object[] a;
   private static final Collector TO_LIST_COLLECTOR;

   private static final Object[] emptyArray() {
      return ObjectArrays.EMPTY_ARRAY;
   }

   public ObjectImmutableList(Object[] a) {
      this.a = a;
   }

   public ObjectImmutableList(Collection c) {
      this(c.isEmpty() ? emptyArray() : ObjectIterators.unwrap(c.iterator()));
   }

   public ObjectImmutableList(ObjectCollection c) {
      this(c.isEmpty() ? emptyArray() : ObjectIterators.unwrap(c.iterator()));
   }

   public ObjectImmutableList(ObjectList l) {
      this(l.isEmpty() ? emptyArray() : new Object[l.size()]);
      l.getElements(0, this.a, 0, l.size());
   }

   public ObjectImmutableList(Object[] a, int offset, int length) {
      this(length == 0 ? emptyArray() : new Object[length]);
      System.arraycopy(a, offset, this.a, 0, length);
   }

   public ObjectImmutableList(ObjectIterator i) {
      this(i.hasNext() ? ObjectIterators.unwrap(i) : emptyArray());
   }

   public static ObjectImmutableList of() {
      return EMPTY;
   }

   @SafeVarargs
   public static ObjectImmutableList of(Object... init) {
      return init.length == 0 ? of() : new ObjectImmutableList(init);
   }

   private static ObjectImmutableList convertTrustedToImmutableList(ObjectArrayList arrayList) {
      if (arrayList.isEmpty()) {
         return of();
      } else {
         K[] backingArray = (K[])arrayList.elements();
         if (arrayList.size() != backingArray.length) {
            backingArray = (K[])Arrays.copyOf(backingArray, arrayList.size());
         }

         return new ObjectImmutableList(backingArray);
      }
   }

   public static Collector toList() {
      return TO_LIST_COLLECTOR;
   }

   public static Collector toListWithExpectedSize(int expectedSize) {
      return expectedSize <= 10 ? toList() : Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, (size) -> size <= 10 ? new ObjectArrayList() : new ObjectArrayList(size)), ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList);
   }

   public Object get(int index) {
      if (index >= this.a.length) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")");
      } else {
         return this.a[index];
      }
   }

   public int indexOf(Object k) {
      Object[] a = this.a;
      int i = 0;

      for(int size = a.length; i < size; ++i) {
         if (Objects.equals(k, a[i])) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(Object k) {
      Object[] a = this.a;
      int i = a.length;

      while(i-- != 0) {
         if (Objects.equals(k, a[i])) {
            return i;
         }
      }

      return -1;
   }

   public int size() {
      return this.a.length;
   }

   public boolean isEmpty() {
      return this.a.length == 0;
   }

   public void getElements(int from, Object[] a, int offset, int length) {
      ObjectArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.a, from, a, offset, length);
   }

   public void forEach(Consumer action) {
      K[] a = (K[])this.a;

      for(int i = 0; i < a.length; ++i) {
         action.accept(a[i]);
      }

   }

   public Object[] toArray() {
      if (this.a.length == 0) {
         return ObjectArrays.EMPTY_ARRAY;
      } else {
         return this.a.getClass() == Object[].class ? (Object[])this.a.clone() : Arrays.copyOf(this.a, this.a.length, Object[].class);
      }
   }

   public Object[] toArray(Object[] a) {
      if (a == null) {
         a = (T[])(new Object[this.size()]);
      } else if (a.length < this.size()) {
         a = (T[])((Object[])Array.newInstance(a.getClass().getComponentType(), this.size()));
      }

      System.arraycopy(this.a, 0, a, 0, this.size());
      if (a.length > this.size()) {
         a[this.size()] = null;
      }

      return a;
   }

   public ObjectListIterator listIterator(final int index) {
      this.ensureIndex(index);
      return new ObjectListIterator() {
         int pos = index;

         public boolean hasNext() {
            return this.pos < ObjectImmutableList.this.a.length;
         }

         public boolean hasPrevious() {
            return this.pos > 0;
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return ObjectImmutableList.this.a[this.pos++];
            }
         }

         public Object previous() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return ObjectImmutableList.this.a[--this.pos];
            }
         }

         public int nextIndex() {
            return this.pos;
         }

         public int previousIndex() {
            return this.pos - 1;
         }

         public void forEachRemaining(Consumer action) {
            K[] a = (K[])ObjectImmutableList.this.a;

            while(this.pos < a.length) {
               action.accept(a[this.pos++]);
            }

         }

         public void add(Object k) {
            throw new UnsupportedOperationException();
         }

         public void set(Object k) {
            throw new UnsupportedOperationException();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }

         public int back(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = this.pos;
               if (n < remaining) {
                  this.pos -= n;
               } else {
                  n = remaining;
                  this.pos = 0;
               }

               return n;
            }
         }

         public int skip(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int remaining = ObjectImmutableList.this.a.length - this.pos;
               if (n < remaining) {
                  this.pos += n;
               } else {
                  n = remaining;
                  this.pos = ObjectImmutableList.this.a.length;
               }

               return n;
            }
         }
      };
   }

   public ObjectSpliterator spliterator() {
      return new Spliterator();
   }

   public ObjectList subList(int from, int to) {
      if (from == 0 && to == this.size()) {
         return this;
      } else {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from == to) {
            return EMPTY;
         } else if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new ImmutableSubList(this, from, to);
         }
      }
   }

   public ObjectImmutableList clone() {
      return this;
   }

   public boolean equals(ObjectImmutableList l) {
      if (l == this) {
         return true;
      } else if (this.a == l.a) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            K[] a1 = (K[])this.a;
            K[] a2 = (K[])l.a;
            return Arrays.equals(a1, a2);
         }
      }
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof List)) {
         return false;
      } else if (o instanceof ObjectImmutableList) {
         return this.equals((ObjectImmutableList)o);
      } else {
         return o instanceof ImmutableSubList ? ((ImmutableSubList)o).equals(this) : super.equals(o);
      }
   }

   public int compareTo(ObjectImmutableList l) {
      int s1 = this.size();
      int s2 = l.size();
      K[] a1 = (K[])this.a;
      K[] a2 = (K[])l.a;

      int i;
      for(i = 0; i < s1 && i < s2; ++i) {
         K e1 = (K)a1[i];
         K e2 = (K)a2[i];
         int r;
         if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
            return r;
         }
      }

      return i < s2 ? -1 : (i < s1 ? 1 : 0);
   }

   public int compareTo(List l) {
      if (l instanceof ObjectImmutableList) {
         return this.compareTo((ObjectImmutableList)l);
      } else if (l instanceof ImmutableSubList) {
         ImmutableSubList<K> other = (ImmutableSubList)l;
         return -other.compareTo((List)this);
      } else {
         return super.compareTo(l);
      }
   }

   static {
      EMPTY = new ObjectImmutableList(ObjectArrays.EMPTY_ARRAY);
      TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList);
   }

   private final class Spliterator implements ObjectSpliterator {
      int pos;
      int max;

      public Spliterator() {
         this(0, ObjectImmutableList.this.a.length);
      }

      private Spliterator(int pos, int max) {
         assert pos <= max : "pos " + pos + " must be <= max " + max;

         this.pos = pos;
         this.max = max;
      }

      public int characteristics() {
         return 17488;
      }

      public long estimateSize() {
         return (long)(this.max - this.pos);
      }

      public boolean tryAdvance(Consumer action) {
         if (this.pos >= this.max) {
            return false;
         } else {
            action.accept(ObjectImmutableList.this.a[this.pos++]);
            return true;
         }
      }

      public void forEachRemaining(Consumer action) {
         for(K[] a = (K[])ObjectImmutableList.this.a; this.pos < this.max; ++this.pos) {
            action.accept(a[this.pos]);
         }

      }

      public long skip(long n) {
         if (n < 0L) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
         } else if (this.pos >= this.max) {
            return 0L;
         } else {
            int remaining = this.max - this.pos;
            if (n < (long)remaining) {
               this.pos = SafeMath.safeLongToInt((long)this.pos + n);
               return n;
            } else {
               n = (long)remaining;
               this.pos = this.max;
               return n;
            }
         }
      }

      public ObjectSpliterator trySplit() {
         int retLen = this.max - this.pos >> 1;
         if (retLen <= 1) {
            return null;
         } else {
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            return ObjectImmutableList.this.new Spliterator(oldPos, myNewPos);
         }
      }
   }

   private static final class ImmutableSubList extends ObjectLists.ImmutableListBase implements RandomAccess, Serializable {
      private static final long serialVersionUID = 7054639518438982401L;
      final ObjectImmutableList innerList;
      final int from;
      final int to;
      final transient Object[] a;

      ImmutableSubList(ObjectImmutableList innerList, int from, int to) {
         this.innerList = innerList;
         this.from = from;
         this.to = to;
         this.a = innerList.a;
      }

      public Object get(int index) {
         this.ensureRestrictedIndex(index);
         return this.a[index + this.from];
      }

      public int indexOf(Object k) {
         Object[] a = this.a;

         for(int i = this.from; i < this.to; ++i) {
            if (Objects.equals(k, a[i])) {
               return i - this.from;
            }
         }

         return -1;
      }

      public int lastIndexOf(Object k) {
         Object[] a = this.a;
         int i = this.to;

         while(i-- != this.from) {
            if (Objects.equals(k, a[i])) {
               return i - this.from;
            }
         }

         return -1;
      }

      public int size() {
         return this.to - this.from;
      }

      public boolean isEmpty() {
         return this.to <= this.from;
      }

      public void getElements(int fromSublistIndex, Object[] a, int offset, int length) {
         ObjectArrays.ensureOffsetLength(a, offset, length);
         this.ensureRestrictedIndex(fromSublistIndex);
         if (this.from + length > this.to) {
            throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + this.size());
         } else {
            System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
         }
      }

      public void forEach(Consumer action) {
         K[] a = (K[])this.a;

         for(int i = this.from; i < this.to; ++i) {
            action.accept(a[i]);
         }

      }

      public Object[] toArray() {
         return Arrays.copyOfRange(this.a, this.from, this.to, Object[].class);
      }

      public Object[] toArray(Object[] a) {
         int size = this.size();
         if (a == null) {
            a = (K[])(new Object[size]);
         } else if (a.length < size) {
            a = (K[])((Object[])Array.newInstance(a.getClass().getComponentType(), size));
         }

         System.arraycopy(this.a, this.from, a, 0, size);
         if (a.length > size) {
            a[size] = null;
         }

         return a;
      }

      public ObjectListIterator listIterator(final int index) {
         this.ensureIndex(index);
         return new ObjectListIterator() {
            int pos;

            {
               this.pos = index + ImmutableSubList.this.from;
            }

            public boolean hasNext() {
               return this.pos < ImmutableSubList.this.to;
            }

            public boolean hasPrevious() {
               return this.pos > ImmutableSubList.this.from;
            }

            public Object next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return ImmutableSubList.this.a[this.pos++];
               }
            }

            public Object previous() {
               if (!this.hasPrevious()) {
                  throw new NoSuchElementException();
               } else {
                  return ImmutableSubList.this.a[--this.pos];
               }
            }

            public int nextIndex() {
               return this.pos - ImmutableSubList.this.from;
            }

            public int previousIndex() {
               return this.pos - ImmutableSubList.this.from - 1;
            }

            public void forEachRemaining(Consumer action) {
               K[] a = (K[])ImmutableSubList.this.a;

               while(this.pos < ImmutableSubList.this.to) {
                  action.accept(a[this.pos++]);
               }

            }

            public void add(Object k) {
               throw new UnsupportedOperationException();
            }

            public void set(Object k) {
               throw new UnsupportedOperationException();
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }

            public int back(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  int remaining = this.pos - ImmutableSubList.this.from;
                  if (n < remaining) {
                     this.pos -= n;
                  } else {
                     n = remaining;
                     this.pos = ImmutableSubList.this.from;
                  }

                  return n;
               }
            }

            public int skip(int n) {
               if (n < 0) {
                  throw new IllegalArgumentException("Argument must be nonnegative: " + n);
               } else {
                  int remaining = ImmutableSubList.this.to - this.pos;
                  if (n < remaining) {
                     this.pos += n;
                  } else {
                     n = remaining;
                     this.pos = ImmutableSubList.this.to;
                  }

                  return n;
               }
            }
         };
      }

      public ObjectSpliterator spliterator() {
         return new SubListSpliterator();
      }

      boolean contentsEquals(Object[] otherA, int otherAFrom, int otherATo) {
         if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
            return true;
         } else if (otherATo - otherAFrom != this.size()) {
            return false;
         } else {
            int pos = this.from;
            int otherPos = otherAFrom;

            while(pos < this.to) {
               if (!Objects.equals(this.a[pos++], otherA[otherPos++])) {
                  return false;
               }
            }

            return true;
         }
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (o == null) {
            return false;
         } else if (!(o instanceof List)) {
            return false;
         } else if (o instanceof ObjectImmutableList) {
            ObjectImmutableList<K> other = (ObjectImmutableList)o;
            return this.contentsEquals(other.a, 0, other.size());
         } else if (o instanceof ImmutableSubList) {
            ImmutableSubList<K> other = (ImmutableSubList)o;
            return this.contentsEquals(other.a, other.from, other.to);
         } else {
            return super.equals(o);
         }
      }

      int contentsCompareTo(Object[] otherA, int otherAFrom, int otherATo) {
         K[] a = (K[])this.a;
         int i = this.from;

         for(int j = otherAFrom; i < this.to && i < otherATo; ++j) {
            K e1 = (K)a[i];
            K e2 = (K)otherA[j];
            int r;
            if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
               return r;
            }

            ++i;
         }

         return i < otherATo ? -1 : (i < this.to ? 1 : 0);
      }

      public int compareTo(List l) {
         if (l instanceof ObjectImmutableList) {
            ObjectImmutableList<K> other = (ObjectImmutableList)l;
            return this.contentsCompareTo(other.a, 0, other.size());
         } else if (l instanceof ImmutableSubList) {
            ImmutableSubList<K> other = (ImmutableSubList)l;
            return this.contentsCompareTo(other.a, other.from, other.to);
         } else {
            return super.compareTo(l);
         }
      }

      private Object readResolve() throws ObjectStreamException {
         try {
            return this.innerList.subList(this.from, this.to);
         } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
            throw (InvalidObjectException)(new InvalidObjectException(((RuntimeException)ex).getMessage())).initCause(ex);
         }
      }

      public ObjectList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from == to) {
            return ObjectImmutableList.EMPTY;
         } else if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
         }
      }

      private final class SubListSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator {
         SubListSpliterator() {
            super(ImmutableSubList.this.from, ImmutableSubList.this.to);
         }

         private SubListSpliterator(int pos, int maxPos) {
            super(pos, maxPos);
         }

         protected final Object get(int i) {
            return ImmutableSubList.this.a[i];
         }

         protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
            return ImmutableSubList.this.new SubListSpliterator(pos, maxPos);
         }

         public boolean tryAdvance(Consumer action) {
            if (this.pos >= this.maxPos) {
               return false;
            } else {
               action.accept(ImmutableSubList.this.a[this.pos++]);
               return true;
            }
         }

         public void forEachRemaining(Consumer action) {
            K[] a = (K[])ImmutableSubList.this.a;
            int max = this.maxPos;

            while(this.pos < max) {
               action.accept(a[this.pos++]);
            }

         }

         public int characteristics() {
            return 17488;
         }
      }
   }
}
