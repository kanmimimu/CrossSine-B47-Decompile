package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;

public interface IntReferencePair extends Pair {
   int leftInt();

   /** @deprecated */
   @Deprecated
   default Integer left() {
      return this.leftInt();
   }

   default IntReferencePair left(int l) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default IntReferencePair left(Integer l) {
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

   default IntReferencePair first(int l) {
      return this.left(l);
   }

   /** @deprecated */
   @Deprecated
   default IntReferencePair first(Integer l) {
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

   default IntReferencePair key(int l) {
      return this.left(l);
   }

   /** @deprecated */
   @Deprecated
   default IntReferencePair key(Integer l) {
      return this.key(l);
   }

   static IntReferencePair of(int left, Object right) {
      return new IntReferenceImmutablePair(left, right);
   }
}
