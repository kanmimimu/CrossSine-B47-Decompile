package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;

public interface ObjectReferencePair extends Pair {
   static ObjectReferencePair of(Object left, Object right) {
      return new ObjectReferenceImmutablePair(left, right);
   }
}
