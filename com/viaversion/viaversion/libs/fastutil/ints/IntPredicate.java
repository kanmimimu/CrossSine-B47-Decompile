package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface IntPredicate extends Predicate, java.util.function.IntPredicate {
   /** @deprecated */
   @Deprecated
   default boolean test(Integer t) {
      return this.test(t);
   }

   default IntPredicate and(java.util.function.IntPredicate other) {
      Objects.requireNonNull(other);
      return (t) -> this.test(t) && other.test(t);
   }

   default IntPredicate and(IntPredicate other) {
      return this.and((java.util.function.IntPredicate)other);
   }

   /** @deprecated */
   @Deprecated
   default Predicate and(Predicate other) {
      return super.and(other);
   }

   default IntPredicate negate() {
      return (t) -> !this.test(t);
   }

   default IntPredicate or(java.util.function.IntPredicate other) {
      Objects.requireNonNull(other);
      return (t) -> this.test(t) || other.test(t);
   }

   default IntPredicate or(IntPredicate other) {
      return this.or((java.util.function.IntPredicate)other);
   }

   /** @deprecated */
   @Deprecated
   default Predicate or(Predicate other) {
      return super.or(other);
   }
}
