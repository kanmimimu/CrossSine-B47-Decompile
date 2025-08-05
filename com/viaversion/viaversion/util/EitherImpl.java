package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import java.util.Objects;

public class EitherImpl implements Either {
   private final Object left;
   private final Object right;

   protected EitherImpl(Object left, Object value) {
      this.left = left;
      this.right = value;
      Preconditions.checkArgument(left == null || value == null, "Either.left and Either.right are both present");
      Preconditions.checkArgument(left != null || value != null, "Either.left and Either.right are both null");
   }

   public boolean isLeft() {
      return this.left != null;
   }

   public boolean isRight() {
      return this.right != null;
   }

   public Object left() {
      return this.left;
   }

   public Object right() {
      return this.right;
   }

   public String toString() {
      Object var4 = this.right;
      Object var3 = this.left;
      return "Either{" + var3 + ", " + var4 + "}";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EitherImpl<?, ?> pair = (EitherImpl)o;
         return !Objects.equals(this.left, pair.left) ? false : Objects.equals(this.right, pair.right);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.left != null ? this.left.hashCode() : 0;
      result = 31 * result + (this.right != null ? this.right.hashCode() : 0);
      return result;
   }
}
