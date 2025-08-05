package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectReferenceImmutablePair implements ObjectReferencePair, Serializable {
   private static final long serialVersionUID = 0L;
   protected final Object left;
   protected final Object right;

   public ObjectReferenceImmutablePair(Object left, Object right) {
      this.left = left;
      this.right = right;
   }

   public static ObjectReferenceImmutablePair of(Object left, Object right) {
      return new ObjectReferenceImmutablePair(left, right);
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
         return Objects.equals(this.left, ((Pair)other).left()) && this.right == ((Pair)other).right();
      }
   }

   public int hashCode() {
      return (this.left == null ? 0 : this.left.hashCode()) * 19 + (this.right == null ? 0 : System.identityHashCode(this.right));
   }

   public String toString() {
      return "<" + this.left() + "," + this.right() + ">";
   }
}
