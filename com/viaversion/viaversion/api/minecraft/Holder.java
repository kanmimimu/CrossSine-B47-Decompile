package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;

public interface Holder {
   static Holder of(int id) {
      return new HolderImpl(id);
   }

   static Holder of(Object value) {
      return new HolderImpl(value);
   }

   boolean isDirect();

   boolean hasId();

   Object value();

   int id();

   Holder updateId(Int2IntFunction var1);
}
