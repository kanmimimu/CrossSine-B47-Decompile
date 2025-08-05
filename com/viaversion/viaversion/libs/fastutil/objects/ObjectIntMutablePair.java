package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectIntMutablePair implements ObjectIntPair, Serializable {
   private static final long serialVersionUID = 0L;
   protected Object left;
   protected int right;

   public ObjectIntMutablePair(Object left, int right) {
      this.left = left;
      this.right = right;
   }

   public static ObjectIntMutablePair of(Object left, int right) {
      return new ObjectIntMutablePair(left, right);
   }

   public Object left() {
      return this.left;
   }

   public ObjectIntMutablePair left(Object l) {
      this.left = l;
      return this;
   }

   public int rightInt() {
      return this.right;
   }

   public ObjectIntMutablePair right(int r) {
      this.right = r;
      return this;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof ObjectIntPair) {
         return Objects.equals(this.left, ((ObjectIntPair)other).left()) && this.right == ((ObjectIntPair)other).rightInt();
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         return Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   public int hashCode() {
      return (this.left == null ? 0 : this.left.hashCode()) * 19 + this.right;
   }

   public String toString() {
      return "<" + this.left() + "," + this.rightInt() + ">";
   }
}
