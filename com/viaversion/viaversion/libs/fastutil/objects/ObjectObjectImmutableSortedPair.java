package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectObjectImmutableSortedPair extends ObjectObjectImmutablePair implements SortedPair, Serializable {
   private static final long serialVersionUID = 0L;

   private ObjectObjectImmutableSortedPair(Comparable left, Comparable right) {
      super(left, right);
   }

   public static ObjectObjectImmutableSortedPair of(Comparable left, Comparable right) {
      return left.compareTo(right) <= 0 ? new ObjectObjectImmutableSortedPair(left, right) : new ObjectObjectImmutableSortedPair(right, left);
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!(other instanceof SortedPair)) {
         return false;
      } else {
         return Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right());
      }
   }

   public String toString() {
      return "{" + this.left() + "," + this.right() + "}";
   }
}
