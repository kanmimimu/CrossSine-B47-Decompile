package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;

public interface ReferenceReferencePair extends Pair {
   static ReferenceReferencePair of(Object left, Object right) {
      return new ReferenceReferenceImmutablePair(left, right);
   }
}
