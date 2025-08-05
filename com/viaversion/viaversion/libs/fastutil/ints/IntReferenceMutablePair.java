package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class IntReferenceMutablePair implements IntReferencePair, Serializable {
   private static final long serialVersionUID = 0L;
   protected int left;
   protected Object right;

   public IntReferenceMutablePair(int left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static IntReferenceMutablePair of(int left, Object right) {
      return new IntReferenceMutablePair(left, right);
   }

   public int leftInt() {
      return this.left;
   }

   public IntReferenceMutablePair left(int l) {
      this.left = l;
      return this;
   }

   public Object right() {
      return this.right;
   }

   public IntReferenceMutablePair right(Object r) {
      this.right = r;
      return this;
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
