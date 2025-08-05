package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Stack;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;

public abstract class AbstractObjectList extends AbstractObjectCollection implements ObjectList, Stack {
   protected AbstractObjectList() {
   }

   protected void ensureIndex(int index) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      }
   }

   protected void ensureRestrictedIndex(int index) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index >= this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
      }
   }

   public void add(int index, Object k) {
      throw new UnsupportedOperationException();
   }

   public boolean add(Object k) {
      this.add(this.size(), k);
      return true;
   }

   public Object remove(int i) {
      throw new UnsupportedOperationException();
   }

   public Object set(int index, Object k) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      this.ensureIndex(index);
      Iterator<? extends K> i = c.iterator();
      boolean retVal = i.hasNext();

      while(i.hasNext()) {
         this.add(index++, i.next());
      }

      return retVal;
   }

   public boolean addAll(Collection c) {
      return this.addAll(this.size(), c);
   }

   public ObjectListIterator iterator() {
      return this.listIterator();
   }

   public ObjectListIterator listIterator() {
      return this.listIterator(0);
   }

   public ObjectListIterator listIterator(int index) {
      this.ensureIndex(index);
      return new ObjectIterators.AbstractIndexBasedListIterator(0, index) {
         protected final Object get(int i) {
            return AbstractObjectList.this.get(i);
         }

         protected final void add(int i, Object k) {
            AbstractObjectList.this.add(i, k);
         }

         protected final void set(int i, Object k) {
            AbstractObjectList.this.set(i, k);
         }

         protected final void remove(int i) {
            AbstractObjectList.this.remove(i);
         }

         protected final int getMaxPos() {
            return AbstractObjectList.this.size();
         }
      };
   }

   public boolean contains(Object k) {
      return this.indexOf(k) >= 0;
   }

   public int indexOf(Object k) {
      ObjectListIterator<K> i = this.listIterator();

      while(i.hasNext()) {
         K e = (K)i.next();
         if (Objects.equals(k, e)) {
            return i.previousIndex();
         }
      }

      return -1;
   }

   public int lastIndexOf(Object k) {
      ObjectListIterator<K> i = this.listIterator(this.size());

      while(i.hasPrevious()) {
         K e = (K)i.previous();
         if (Objects.equals(k, e)) {
            return i.nextIndex();
         }
      }

      return -1;
   }

   public void size(int size) {
      int i = this.size();
      if (size > i) {
         while(i++ < size) {
            this.add((Object)null);
         }
      } else {
         while(i-- != size) {
            this.remove(i);
         }
      }

   }

   public ObjectList subList(int from, int to) {
      this.ensureIndex(from);
      this.ensureIndex(to);
      if (from > to) {
         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         return (ObjectList)(this instanceof RandomAccess ? new ObjectRandomAccessSubList(this, from, to) : new ObjectSubList(this, from, to));
      }
   }

   public void forEach(Consumer action) {
      if (this instanceof RandomAccess) {
         int i = 0;

         for(int max = this.size(); i < max; ++i) {
            action.accept(this.get(i));
         }
      } else {
         ObjectList.super.forEach(action);
      }

   }

   public void removeElements(int from, int to) {
      this.ensureIndex(to);
      ObjectListIterator<K> i = this.listIterator(from);
      int n = to - from;
      if (n < 0) {
         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         while(n-- != 0) {
            i.next();
            i.remove();
         }

      }
   }

   public void addElements(int index, Object[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      if (this instanceof RandomAccess) {
         while(length-- != 0) {
            this.add(index++, a[offset++]);
         }
      } else {
         ObjectListIterator<K> iter = this.listIterator(index);

         while(length-- != 0) {
            iter.add(a[offset++]);
         }
      }

   }

   public void addElements(int index, Object[] a) {
      this.addElements(index, a, 0, a.length);
   }

   public void getElements(int from, Object[] a, int offset, int length) {
      this.ensureIndex(from);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      if (from + length > this.size()) {
         throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
      } else {
         if (this instanceof RandomAccess) {
            for(int current = from; length-- != 0; a[offset++] = this.get(current++)) {
            }
         } else {
            for(ObjectListIterator<K> i = this.listIterator(from); length-- != 0; a[offset++] = i.next()) {
            }
         }

      }
   }

   public void setElements(int index, Object[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      if (index + length > this.size()) {
         throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
      } else {
         if (this instanceof RandomAccess) {
            for(int i = 0; i < length; ++i) {
               this.set(i + index, a[i + offset]);
            }
         } else {
            ObjectListIterator<K> iter = this.listIterator(index);
            int i = 0;

            while(i < length) {
               iter.next();
               iter.set(a[offset + i++]);
            }
         }

      }
   }

   public void clear() {
      this.removeElements(0, this.size());
   }

   public Object[] toArray() {
      int size = this.size();
      if (size == 0) {
         return ObjectArrays.EMPTY_ARRAY;
      } else {
         Object[] ret = new Object[size];
         this.getElements(0, ret, 0, size);
         return ret;
      }
   }

   public Object[] toArray(Object[] a) {
      int size = this.size();
      if (a.length < size) {
         a = (T[])Arrays.copyOf(a, size);
      }

      this.getElements(0, a, 0, size);
      if (a.length > size) {
         a[size] = null;
      }

      return a;
   }

   public int hashCode() {
      ObjectIterator<K> i = this.iterator();
      int h = 1;

      K k;
      for(int s = this.size(); s-- != 0; h = 31 * h + (k == null ? 0 : k.hashCode())) {
         k = (K)i.next();
      }

      return h;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof List)) {
         return false;
      } else {
         List<?> l = (List)o;
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            ListIterator<?> i1 = this.listIterator();
            ListIterator<?> i2 = l.listIterator();

            while(s-- != 0) {
               if (!Objects.equals(i1.next(), i2.next())) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int compareTo(List l) {
      if (l == this) {
         return 0;
      } else if (l instanceof ObjectList) {
         ObjectListIterator<K> i1 = this.listIterator();
         ObjectListIterator<K> i2 = ((ObjectList)l).listIterator();

         while(i1.hasNext() && i2.hasNext()) {
            K e1 = (K)i1.next();
            K e2 = (K)i2.next();
            int r;
            if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
               return r;
            }
         }

         return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
      } else {
         ListIterator<? extends K> i1 = this.listIterator();
         ListIterator<? extends K> i2 = l.listIterator();

         while(i1.hasNext() && i2.hasNext()) {
            int r;
            if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) {
               return r;
            }
         }

         return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
      }
   }

   public void push(Object o) {
      this.add(o);
   }

   public Object pop() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.remove(this.size() - 1);
      }
   }

   public Object top() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.get(this.size() - 1);
      }
   }

   public Object peek(int i) {
      return this.get(this.size() - 1 - i);
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator<K> i = this.iterator();
      int n = this.size();
      boolean first = true;
      s.append("[");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         K k = (K)i.next();
         if (this == k) {
            s.append("(this list)");
         } else {
            s.append(String.valueOf(k));
         }
      }

      s.append("]");
      return s.toString();
   }

   static final class IndexBasedSpliterator extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator {
      final ObjectList l;

      IndexBasedSpliterator(ObjectList l, int pos) {
         super(pos);
         this.l = l;
      }

      IndexBasedSpliterator(ObjectList l, int pos, int maxPos) {
         super(pos, maxPos);
         this.l = l;
      }

      protected final int getMaxPosFromBackingStore() {
         return this.l.size();
      }

      protected final Object get(int i) {
         return this.l.get(i);
      }

      protected final IndexBasedSpliterator makeForSplit(int pos, int maxPos) {
         return new IndexBasedSpliterator(this.l, pos, maxPos);
      }
   }

   public static class ObjectSubList extends AbstractObjectList implements Serializable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final ObjectList l;
      protected final int from;
      protected int to;

      public ObjectSubList(ObjectList l, int from, int to) {
         this.l = l;
         this.from = from;
         this.to = to;
      }

      private boolean assertRange() {
         assert this.from <= this.l.size();

         assert this.to <= this.l.size();

         assert this.to >= this.from;

         return true;
      }

      public boolean add(Object k) {
         this.l.add(this.to, k);
         ++this.to;

         assert this.assertRange();

         return true;
      }

      public void add(int index, Object k) {
         this.ensureIndex(index);
         this.l.add(this.from + index, k);
         ++this.to;

         assert this.assertRange();

      }

      public boolean addAll(int index, Collection c) {
         this.ensureIndex(index);
         this.to += c.size();
         return this.l.addAll(this.from + index, c);
      }

      public Object get(int index) {
         this.ensureRestrictedIndex(index);
         return this.l.get(this.from + index);
      }

      public Object remove(int index) {
         this.ensureRestrictedIndex(index);
         --this.to;
         return this.l.remove(this.from + index);
      }

      public Object set(int index, Object k) {
         this.ensureRestrictedIndex(index);
         return this.l.set(this.from + index, k);
      }

      public int size() {
         return this.to - this.from;
      }

      public void getElements(int from, Object[] a, int offset, int length) {
         this.ensureIndex(from);
         if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
         } else {
            this.l.getElements(this.from + from, a, offset, length);
         }
      }

      public void removeElements(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         this.l.removeElements(this.from + from, this.from + to);
         this.to -= to - from;

         assert this.assertRange();

      }

      public void addElements(int index, Object[] a, int offset, int length) {
         this.ensureIndex(index);
         this.l.addElements(this.from + index, a, offset, length);
         this.to += length;

         assert this.assertRange();

      }

      public void setElements(int index, Object[] a, int offset, int length) {
         this.ensureIndex(index);
         this.l.setElements(this.from + index, a, offset, length);

         assert this.assertRange();

      }

      public ObjectListIterator listIterator(int index) {
         this.ensureIndex(index);
         return (ObjectListIterator)(this.l instanceof RandomAccess ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from)));
      }

      public ObjectSpliterator spliterator() {
         return (ObjectSpliterator)(this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator());
      }

      public ObjectList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new ObjectSubList(this, from, to);
         }
      }

      private final class RandomAccessIter extends ObjectIterators.AbstractIndexBasedListIterator {
         RandomAccessIter(int pos) {
            super(0, pos);
         }

         protected final Object get(int i) {
            return ObjectSubList.this.l.get(ObjectSubList.this.from + i);
         }

         protected final void add(int i, Object k) {
            ObjectSubList.this.add(i, k);
         }

         protected final void set(int i, Object k) {
            ObjectSubList.this.set(i, k);
         }

         protected final void remove(int i) {
            ObjectSubList.this.remove(i);
         }

         protected final int getMaxPos() {
            return ObjectSubList.this.to - ObjectSubList.this.from;
         }

         public void add(Object k) {
            super.add(k);

            assert ObjectSubList.this.assertRange();

         }

         public void remove() {
            super.remove();

            assert ObjectSubList.this.assertRange();

         }
      }

      private class ParentWrappingIter implements ObjectListIterator {
         private ObjectListIterator parent;

         ParentWrappingIter(ObjectListIterator parent) {
            this.parent = parent;
         }

         public int nextIndex() {
            return this.parent.nextIndex() - ObjectSubList.this.from;
         }

         public int previousIndex() {
            return this.parent.previousIndex() - ObjectSubList.this.from;
         }

         public boolean hasNext() {
            return this.parent.nextIndex() < ObjectSubList.this.to;
         }

         public boolean hasPrevious() {
            return this.parent.previousIndex() >= ObjectSubList.this.from;
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               return this.parent.next();
            }
         }

         public Object previous() {
            if (!this.hasPrevious()) {
               throw new NoSuchElementException();
            } else {
               return this.parent.previous();
            }
         }

         public void add(Object k) {
            this.parent.add(k);
         }

         public void set(Object k) {
            this.parent.set(k);
         }

         public void remove() {
            this.parent.remove();
         }

         public int back(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int currentPos = this.parent.previousIndex();
               int parentNewPos = currentPos - n;
               if (parentNewPos < ObjectSubList.this.from - 1) {
                  parentNewPos = ObjectSubList.this.from - 1;
               }

               int toSkip = parentNewPos - currentPos;
               return this.parent.back(toSkip);
            }
         }

         public int skip(int n) {
            if (n < 0) {
               throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            } else {
               int currentPos = this.parent.nextIndex();
               int parentNewPos = currentPos + n;
               if (parentNewPos > ObjectSubList.this.to) {
                  parentNewPos = ObjectSubList.this.to;
               }

               int toSkip = parentNewPos - currentPos;
               return this.parent.skip(toSkip);
            }
         }
      }
   }

   public static class ObjectRandomAccessSubList extends ObjectSubList implements RandomAccess {
      private static final long serialVersionUID = -107070782945191929L;

      public ObjectRandomAccessSubList(ObjectList l, int from, int to) {
         super(l, from, to);
      }

      public ObjectList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return new ObjectRandomAccessSubList(this, from, to);
         }
      }
   }
}
