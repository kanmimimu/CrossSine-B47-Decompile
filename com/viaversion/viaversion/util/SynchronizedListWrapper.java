package com.viaversion.viaversion.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SynchronizedListWrapper implements List {
   private final List list;
   private final Consumer addHandler;

   public SynchronizedListWrapper(List inputList, Consumer addHandler) {
      this.list = inputList;
      this.addHandler = addHandler;
   }

   public List originalList() {
      return this.list;
   }

   private void handleAdd(Object o) {
      this.addHandler.accept(o);
   }

   public int size() {
      synchronized(this) {
         return this.list.size();
      }
   }

   public boolean isEmpty() {
      synchronized(this) {
         return this.list.isEmpty();
      }
   }

   public boolean contains(Object o) {
      synchronized(this) {
         return this.list.contains(o);
      }
   }

   public @NonNull Iterator iterator() {
      return this.listIterator();
   }

   public Object @NonNull [] toArray() {
      synchronized(this) {
         return this.list.toArray();
      }
   }

   public boolean add(Object o) {
      synchronized(this) {
         this.handleAdd(o);
         return this.list.add(o);
      }
   }

   public boolean remove(Object o) {
      synchronized(this) {
         return this.list.remove(o);
      }
   }

   public boolean addAll(Collection c) {
      synchronized(this) {
         for(Object o : c) {
            this.handleAdd(o);
         }

         return this.list.addAll(c);
      }
   }

   public boolean addAll(int index, Collection c) {
      synchronized(this) {
         for(Object o : c) {
            this.handleAdd(o);
         }

         return this.list.addAll(index, c);
      }
   }

   public void clear() {
      synchronized(this) {
         this.list.clear();
      }
   }

   public Object get(int index) {
      synchronized(this) {
         return this.list.get(index);
      }
   }

   public Object set(int index, Object element) {
      synchronized(this) {
         return this.list.set(index, element);
      }
   }

   public void add(int index, Object element) {
      synchronized(this) {
         this.list.add(index, element);
      }
   }

   public Object remove(int index) {
      synchronized(this) {
         return this.list.remove(index);
      }
   }

   public int indexOf(Object o) {
      synchronized(this) {
         return this.list.indexOf(o);
      }
   }

   public int lastIndexOf(Object o) {
      synchronized(this) {
         return this.list.lastIndexOf(o);
      }
   }

   public @NonNull ListIterator listIterator() {
      return this.list.listIterator();
   }

   public @NonNull ListIterator listIterator(int index) {
      return this.list.listIterator(index);
   }

   public @NonNull List subList(int fromIndex, int toIndex) {
      synchronized(this) {
         return this.list.subList(fromIndex, toIndex);
      }
   }

   public boolean retainAll(@NonNull Collection c) {
      synchronized(this) {
         return this.list.retainAll(c);
      }
   }

   public boolean removeAll(@NonNull Collection c) {
      synchronized(this) {
         return this.list.removeAll(c);
      }
   }

   public boolean containsAll(@NonNull Collection c) {
      synchronized(this) {
         return this.list.containsAll(c);
      }
   }

   public Object @NonNull [] toArray(Object @NonNull [] a) {
      synchronized(this) {
         return this.list.toArray(a);
      }
   }

   public void sort(Comparator c) {
      synchronized(this) {
         this.list.sort(c);
      }
   }

   public void forEach(Consumer consumer) {
      synchronized(this) {
         this.list.forEach(consumer);
      }
   }

   public boolean removeIf(Predicate filter) {
      synchronized(this) {
         return this.list.removeIf(filter);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         synchronized(this) {
            return this.list.equals(o);
         }
      }
   }

   public int hashCode() {
      synchronized(this) {
         return this.list.hashCode();
      }
   }

   public String toString() {
      synchronized(this) {
         return this.list.toString();
      }
   }
}
