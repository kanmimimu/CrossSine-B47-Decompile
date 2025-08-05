package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class IntReferenceImmutablePair implements IntReferencePair, Serializable {
   private static final long serialVersionUID = 0L;
   protected final int left;
   protected final Object right;

   public IntReferenceImmutablePair(int left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static IntReferenceImmutablePair of(int left, Object right) {
      return new IntReferenceImmutablePair(left, right);
   }

   public int leftInt() {
      return this.left;
   }

   public Object right() {
      return this.right;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof IntReferencePair) {
         return this.left == ((IntReferencePair)other).leftInt() && this.right == ((IntReferencePair)other).right();
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         return Objects.equals(this.left, ((Pair)other).left()) && this.right == ((Pair)other).right();
      }
   }

   public int hashCode() {
      return this.left * 19 + (this.right == null ? 0 : System.identityHashCode(this.right));
   }

   public String toString() {
      return "<" + this.leftInt() + "," + this.right() + ">";
   }
}
