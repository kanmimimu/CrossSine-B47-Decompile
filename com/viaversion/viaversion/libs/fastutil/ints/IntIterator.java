package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

public interface IntIterator extends PrimitiveIterator.OfInt {
   int nextInt();

   /** @deprecated */
   @Deprecated
   default Integer next() {
      return this.nextInt();
   }

   default void forEachRemaining(IntConsumer action) {
      this.forEachRemaining((java.util.function.IntConsumer)action);
   }

   /** @deprecated */
   @Deprecated
   default void forEachRemaining(Consumer action) {
      java.util.function.IntConsumer var10001;
      if (action instanceof java.util.function.IntConsumer) {
         var10001 = (java.util.function.IntConsumer)action;
      } else {
         Objects.requireNonNull(action);
         var10001 = action::accept;
      }

      this.forEachRemaining((java.util.function.IntConsumer)var10001);
   }

   default int skip(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("Argument must be nonnegative: " + n);
      } else {
         int i = n;

         while(i-- != 0 && this.hasNext()) {
            this.nextInt();
         }

         return n - i - 1;
      }
   }
}
