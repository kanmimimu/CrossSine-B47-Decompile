package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class IntObjectMutablePair implements IntObjectPair, Serializable {
   private static final long serialVersionUID = 0L;
   protected int left;
   protected Object right;

   public IntObjectMutablePair(int left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static IntObjectMutablePair of(int left, Object right) {
      return new IntObjectMutablePair(left, right);
   }

   public int leftInt() {
      return this.left;
   }

   public IntObjectMutablePair left(int l) {
      this.left = l;
      return this;
   }

   public Object right() {
      return this.right;
   }

   public IntObjectMutablePair right(Object r) {
      this.right = r;
      return this;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof IntObjectPair) {
         return this.left == ((IntObjectPair)other).leftInt() && Objects.equals(this.right, ((IntObjectPair)other).right());
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         return Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   public int hashCode() {
      return this.left * 19 + (this.right == null ? 0 : this.right.hashCode());
   }

   public String toString() {
      return "<" + this.leftInt() + "," + this.right() + ">";
   }
}
