package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class ReferenceLists {
   public static final EmptyList EMPTY_LIST = new EmptyList();

   private ReferenceLists() {
   }

   public static ReferenceList shuffle(ReferenceList l, Random random) {
      int i = l.size();

      while(i-- != 0) {
         int p = random.nextInt(i + 1);
         K t = (K)l.get(i);
         l.set(i, l.get(p));
         l.set(p, t);
      }

      return l;
   }

   public static ReferenceList emptyList() {
      return EMPTY_LIST;
   }

   public static ReferenceList singleton(Object element) {
      return new Singleton(element);
   }

   public static ReferenceList synchronize(ReferenceList l) {
      return (ReferenceList)(l instanceof RandomAccess ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l));
   }

   public static ReferenceList synchronize(ReferenceList l, Object sync) {
      return (ReferenceList)(l instanceof RandomAccess ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync));
   }

   public static ReferenceList unmodifiable(ReferenceList l) {
      return (ReferenceList)(l instanceof RandomAccess ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l));
   }

   public static class EmptyList extends ReferenceCollections.EmptyCollection implements ReferenceList, RandomAccess, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyList() {
      }

      public Object get(int i) {
         throw new IndexOutOfBoundsException();
      }

      public boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      public Object remove(int i) {
         throw new UnsupportedOperationException();
      }

      public void add(int index, Object k) {
         throw new UnsupportedOperationException();
      }

      public Object set(int index, Object k) {
         throw new UnsupportedOperationException();
      }

      public int indexOf(Object k) {
         return -1;
      }

      public int lastIndexOf(Object k) {
         return -1;
      }

      public boolean addAll(int i, Collection c) {
         throw new UnsupportedOperationException();
      }

      public void replaceAll(UnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      public void sort(Comparator comparator) {
      }

      public void unstableSort(Comparator comparator) {
      }

      public ObjectListIterator listIterator() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      public ObjectListIterator iterator() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      public ObjectListIterator listIterator(int i) {
         if (i == 0) {
            return ObjectIterators.EMPTY_ITERATOR;
         } else {
            throw new IndexOutOfBoundsException(String.valueOf(i));
         }
      }

      public ReferenceList subList(int from, int to) {
         if (from == 0 && to == 0) {
            return this;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public void getElements(int from, Object[] a, int offset, int length) {
         if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
            throw new IndexOutOfBoundsException();
         }
      }

      public void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      public void addElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      public void addElements(int index, Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void setElements(Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void setElements(int index, Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void setElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      public void size(int s) {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return ReferenceLists.EMPTY_LIST;
      }

      public int hashCode() {
         return 1;
      }

      public boolean equals(Object o) {
         return o instanceof List && ((List)o).isEmpty();
      }

      public String toString() {
         return "[]";
      }

      private Object readResolve() {
         return ReferenceLists.EMPTY_LIST;
      }
   }

   public static class Singleton extends AbstractReferenceList implements RandomAccess, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      private final Object element;

      protected Singleton(Object element) {
         this.element = element;
      }

      public Object get(int i) {
         if (i == 0) {
            return this.element;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      public Object remove(int i) {
         throw new UnsupportedOperationException();
      }

      public boolean contains(Object k) {
         return k == this.element;
      }

      public int indexOf(Object k) {
         return k == this.element ? 0 : -1;
      }

      public Object[] toArray() {
         return new Object[]{this.element};
      }

      public ObjectListIterator listIterator() {
         return ObjectIterators.singleton(this.element);
      }

      public ObjectListIterator iterator() {
         return this.listIterator();
      }

      public ObjectSpliterator spliterator() {
         return ObjectSpliterators.singleton(this.element);
      }

      public ObjectListIterator listIterator(int i) {
         if (i <= 1 && i >= 0) {
            ObjectListIterator<K> l = this.listIterator();
            if (i == 1) {
               l.next();
            }

            return l;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public ReferenceList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return (ReferenceList)(from == 0 && to == 1 ? this : ReferenceLists.EMPTY_LIST);
         }
      }

      public void forEach(Consumer action) {
         action.accept(this.element);
      }

      public boolean addAll(int i, Collection c) {
         throw new UnsupportedOperationException();
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

      public void replaceAll(UnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      public void sort(Comparator comparator) {
      }

      public void unstableSort(Comparator comparator) {
      }

      public void getElements(int from, Object[] a, int offset, int length) {
         if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
         } else if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
         } else if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
         } else if (length > 0) {
            a[offset] = this.element;
         }
      }

      public void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      public void addElements(int index, Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void addElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      public void setElements(Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void setElements(int index, Object[] a) {
         throw new UnsupportedOperationException();
      }

      public void setElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return 1;
      }

      public void size(int size) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return this;
      }
   }

   abstract static class ImmutableListBase extends AbstractReferenceList implements ReferenceList {
      /** @deprecated */
      @Deprecated
      public final void add(int index, Object k) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean add(Object k) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean addAll(int index, Collection c) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final Object remove(int index) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean removeAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final boolean removeIf(Predicate c) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void replaceAll(UnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final Object set(int index, Object k) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void clear() {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void size(int size) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void addElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void setElements(int index, Object[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void sort(Comparator comparator) {
         throw new UnsupportedOperationException();
      }

      /** @deprecated */
      @Deprecated
      public final void unstableSort(Comparator comparator) {
         throw new UnsupportedOperationException();
      }
   }
}
