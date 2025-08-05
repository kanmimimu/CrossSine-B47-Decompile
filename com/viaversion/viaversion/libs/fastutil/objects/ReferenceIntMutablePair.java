package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ReferenceIntMutablePair implements ReferenceIntPair, Serializable {
   private static final long serialVersionUID = 0L;
   protected Object left;
   protected int right;

   public ReferenceIntMutablePair(Object left, int right) {
      this.left = left;
      this.right = right;
   }

   public static ReferenceIntMutablePair of(Object left, int right) {
      return new ReferenceIntMutablePair(left, right);
   }

   public Object left() {
      return this.left;
   }

   public ReferenceIntMutablePair left(Object l) {
      this.left = l;
      return this;
   }

   public int rightInt() {
      return this.right;
   }

   public ReferenceIntMutablePair right(int r) {
      this.right = r;
      return this;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof ReferenceIntPair) {
         return this.left == ((ReferenceIntPair)other).left() && this.right == ((ReferenceIntPair)other).rightInt();
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         return this.left == ((Pair)other).left() && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   public int hashCode() {
      return System.identityHashCode(this.left) * 19 + this.right;
   }

   public String toString() {
      return "<" + this.left() + "," + this.rightInt() + ">";
   }
}
