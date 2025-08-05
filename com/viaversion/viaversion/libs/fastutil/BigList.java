package com.viaversion.viaversion.libs.fastutil;

import java.util.Collection;

public interface BigList extends Collection, Size64 {
   Object get(long var1);

   Object remove(long var1);

   Object set(long var1, Object var3);

   void add(long var1, Object var3);

   void size(long var1);

   boolean addAll(long var1, Collection var3);

   long indexOf(Object var1);

   long lastIndexOf(Object var1);

   BigListIterator listIterator();

   BigListIterator listIterator(long var1);

   BigList subList(long var1, long var3);

   /** @deprecated */
   @Deprecated
   default int size() {
      return Size64.super.size();
   }
}
