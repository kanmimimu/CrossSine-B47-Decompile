package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Comparator;

public interface ObjectIntPair extends Pair {
   int rightInt();

   /** @deprecated */
   @Deprecated
   default Integer right() {
      return this.rightInt();
   }

   default ObjectIntPair right(int r) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default ObjectIntPair right(Integer l) {
      return this.right(l);
   }

   default int secondInt() {
      return this.rightInt();
   }

   /** @deprecated */
   @Deprecated
   default Integer second() {
      return this.secondInt();
   }

   default ObjectIntPair second(int r) {
      return this.right(r);
   }

   /** @deprecated */
   @Deprecated
   default ObjectIntPair second(Integer l) {
      return this.second(l);
   }

   default int valueInt() {
      return this.rightInt();
   }

   /** @deprecated */
   @Deprecated
   default Integer value() {
      return this.valueInt();
   }

   default ObjectIntPair value(int r) {
      return this.right(r);
   }

   /** @deprecated */
   @Deprecated
   default ObjectIntPair value(Integer l) {
      return this.value(l);
   }

   static ObjectIntPair of(Object left, int right) {
      return new ObjectIntImmutablePair(left, right);
   }

   static Comparator lexComparator() {
      return (x, y) -> {
         int t = ((Comparable)x.left()).compareTo(y.left());
         return t != 0 ? t : Integer.compare(x.rightInt(), y.rightInt());
      };
   }
}
