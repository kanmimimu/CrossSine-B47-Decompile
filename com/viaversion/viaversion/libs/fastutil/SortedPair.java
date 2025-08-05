package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutableSortedPair;
import java.util.Objects;

public interface SortedPair extends Pair {
   static SortedPair of(Comparable l, Comparable r) {
      return ObjectObjectImmutableSortedPair.of(l, r);
   }

   default boolean contains(Object o) {
      return Objects.equals(o, this.left()) || Objects.equals(o, this.right());
   }
}
