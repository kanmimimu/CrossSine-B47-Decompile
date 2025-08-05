package com.viaversion.viaversion.libs.gson.internal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.RandomAccess;

public class NonNullElementWrapperList extends AbstractList implements RandomAccess {
   private final ArrayList delegate;

   public NonNullElementWrapperList(ArrayList delegate) {
      this.delegate = (ArrayList)Objects.requireNonNull(delegate);
   }

   public Object get(int index) {
      return this.delegate.get(index);
   }

   public int size() {
      return this.delegate.size();
   }

   private Object nonNull(Object element) {
      if (element == null) {
         throw new NullPointerException("Element must be non-null");
      } else {
         return element;
      }
   }

   public Object set(int index, Object element) {
      return this.delegate.set(index, this.nonNull(element));
   }

   public void add(int index, Object element) {
      this.delegate.add(index, this.nonNull(element));
   }

   public Object remove(int index) {
      return this.delegate.remove(index);
   }

   public void clear() {
      this.delegate.clear();
   }

   public boolean remove(Object o) {
      return this.delegate.remove(o);
   }

   public boolean removeAll(Collection c) {
      return this.delegate.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.delegate.retainAll(c);
   }

   public boolean contains(Object o) {
      return this.delegate.contains(o);
   }

   public int indexOf(Object o) {
      return this.delegate.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      return this.delegate.lastIndexOf(o);
   }

   public Object[] toArray() {
      return this.delegate.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.delegate.toArray(a);
   }

   public boolean equals(Object o) {
      return this.delegate.equals(o);
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }
}
