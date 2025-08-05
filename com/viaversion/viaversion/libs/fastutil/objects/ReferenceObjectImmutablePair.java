package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ReferenceObjectImmutablePair implements ReferenceObjectPair, Serializable {
   private static final long serialVersionUID = 0L;
   protected final Object left;
   protected final Object right;

   public ReferenceObjectImmutablePair(Object left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static ReferenceObjectImmutablePair of(Object left, Object right) {
      return new ReferenceObjectImmutablePair(left, right);
   }

   public Object left() {
      return this.left;
   }

   public Object right() {
      return this.right;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         return this.left == ((Pair)other).left() && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   public int hashCode() {
      return System.identityHashCode(this.left) * 19 + (this.right == null ? 0 : this.right.hashCode());
   }

   public String toString() {
      return "<" + this.left() + "," + this.right() + ">";
   }
}
