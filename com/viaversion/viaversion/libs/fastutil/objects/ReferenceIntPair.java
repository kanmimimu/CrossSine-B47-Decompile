package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;

public interface ReferenceIntPair extends Pair {
   int rightInt();

   /** @deprecated */
   @Deprecated
   default Integer right() {
      return this.rightInt();
   }

   default ReferenceIntPair right(int r) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default ReferenceIntPair right(Integer l) {
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

   default ReferenceIntPair second(int r) {
      return this.right(r);
   }

   /** @deprecated */
   @Deprecated
   default ReferenceIntPair second(Integer l) {
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

   default ReferenceIntPair value(int r) {
      return this.right(r);
   }

   /** @deprecated */
   @Deprecated
   default ReferenceIntPair value(Integer l) {
      return this.value(l);
   }

   static ReferenceIntPair of(Object left, int right) {
      return new ReferenceIntImmutablePair(left, right);
   }
}
