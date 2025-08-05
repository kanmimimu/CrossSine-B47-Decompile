package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Comparator;

public interface IntObjectPair extends Pair {
   int leftInt();

   /** @deprecated */
   @Deprecated
   default Integer left() {
      return this.leftInt();
   }

   default IntObjectPair left(int l) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default IntObjectPair left(Integer l) {
      return this.left(l);
   }

   default int firstInt() {
      return this.leftInt();
   }

   /** @deprecated */
   @Deprecated
   default Integer first() {
      return this.firstInt();
   }

   default IntObjectPair first(int l) {
      return this.left(l);
   }

   /** @deprecated */
   @Deprecated
   default IntObjectPair first(Integer l) {
      return this.first(l);
   }

   default int keyInt() {
      return this.firstInt();
   }

   /** @deprecated */
   @Deprecated
   default Integer key() {
      return this.keyInt();
   }

   default IntObjectPair key(int l) {
      return this.left(l);
   }

   /** @deprecated */
   @Deprecated
   default IntObjectPair key(Integer l) {
      return this.key(l);
   }

   static IntObjectPair of(int left, Object right) {
      return new IntObjectImmutablePair(left, right);
   }

   static Comparator lexComparator() {
      return (x, y) -> {
         int t = Integer.compare(x.leftInt(), y.leftInt());
         return t != 0 ? t : ((Comparable)x.right()).compareTo(y.right());
      };
   }
}
