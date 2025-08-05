package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ReferenceObjectMutablePair implements ReferenceObjectPair, Serializable {
   private static final long serialVersionUID = 0L;
   protected Object left;
   protected Object right;

   public ReferenceObjectMutablePair(Object left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static ReferenceObjectMutablePair of(Object left, Object right) {
      return new ReferenceObjectMutablePair(left, right);
   }

   public Object left() {
      return this.left;
   }

   public ReferenceObjectMutablePair left(Object l) {
      this.left = l;
      return this;
   }

   public Object right() {
      return this.right;
   }

   public ReferenceObjectMutablePair right(Object r) {
      this.right = r;
      return this;
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
