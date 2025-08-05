package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

public interface ObjectList extends List, Comparable, ObjectCollection {
   ObjectListIterator iterator();

   default ObjectSpliterator spliterator() {
      return (ObjectSpliterator)(this instanceof RandomAccess ? new AbstractObjectList.IndexBasedSpliterator(this, 0) : ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf((Collection)this), 16464));
   }

   ObjectListIterator listIterator();

   ObjectListIterator listIterator(int var1);

   ObjectList subList(int var1, int var2);

   void size(int var1);

   void getElements(int var1, Object[] var2, int var3, int var4);

   void removeElements(int var1, int var2);

   void addElements(int var1, Object[] var2);

   void addElements(int var1, Object[] var2, int var3, int var4);

   default void setElements(Object[] a) {
      this.setElements(0, a);
   }

   default void setElements(int index, Object[] a) {
      this.setElements(index, a, 0, a.length);
   }

   default void setElements(int index, Object[] a, int offset, int length) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      } else {
         ObjectArrays.ensureOffsetLength(a, offset, length);
         if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
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

   default boolean addAll(int index, ObjectList l) {
      return this.addAll(index, l);
   }

   default boolean addAll(ObjectList l) {
      return this.addAll(this.size(), l);
   }

   static ObjectList of() {
      return ObjectImmutableList.of();
   }

   static ObjectList of(Object e) {
      return ObjectLists.singleton(e);
   }

   static ObjectList of(Object e0, Object e1) {
      return ObjectImmutableList.of(e0, e1);
   }

   static ObjectList of(Object e0, Object e1, Object e2) {
      return ObjectImmutableList.of(e0, e1, e2);
   }

   @SafeVarargs
   static ObjectList of(Object... a) {
      switch (a.length) {
         case 0:
            return of();
         case 1:
            return of(a[0]);
         default:
            return ObjectImmutableList.of(a);
      }
   }

   default void sort(Comparator comparator) {
      K[] elements = (K[])this.toArray();
      if (comparator == null) {
         ObjectArrays.stableSort(elements);
      } else {
         ObjectArrays.stableSort(elements, comparator);
      }

      this.setElements(elements);
   }

   default void unstableSort(Comparator comparator) {
      K[] elements = (K[])this.toArray();
      if (comparator == null) {
         ObjectArrays.unstableSort(elements);
      } else {
         ObjectArrays.unstableSort(elements, comparator);
      }

      this.setElements(elements);
   }
}
