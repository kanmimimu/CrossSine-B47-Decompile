package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;

public interface ReferenceObjectPair extends Pair {
   static ReferenceObjectPair of(Object left, Object right) {
      return new ReferenceObjectImmutablePair(left, right);
   }
}
