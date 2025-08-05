package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;

public class ReferenceReferenceImmutablePair implements ReferenceReferencePair, Serializable {
   private static final long serialVersionUID = 0L;
   protected final Object left;
   protected final Object right;

   public ReferenceReferenceImmutablePair(Object left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static ReferenceReferenceImmutablePair of(Object left, Object right) {
      return new ReferenceReferenceImmutablePair(left, right);
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
         return this.left == ((Pair)other).left() && this.right == ((Pair)other).right();
      }
   }

   public int hashCode() {
      return System.identityHashCode(this.left) * 19 + (this.right == null ? 0 : System.identityHashCode(this.right));
   }

   public String toString() {
      return "<" + this.left() + "," + this.right() + ">";
   }
}
