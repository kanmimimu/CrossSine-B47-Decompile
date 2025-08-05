package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;

public interface Either {
   static Either left(Object left) {
      Preconditions.checkNotNull(left);
      return new EitherImpl(left, (Object)null);
   }

   static Either right(Object right) {
      Preconditions.checkNotNull(right);
      return new EitherImpl((Object)null, right);
   }

   boolean isLeft();

   boolean isRight();

   Object left();

   Object right();
}
