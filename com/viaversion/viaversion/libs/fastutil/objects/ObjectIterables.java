package com.viaversion.viaversion.libs.fastutil.objects;

public final class ObjectIterables {
   private ObjectIterables() {
   }

   public static long size(Iterable iterable) {
      long c = 0L;

      for(Object dummy : iterable) {
         ++c;
      }

      return c;
   }
}
