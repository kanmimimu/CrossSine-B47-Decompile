package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Triple {
   private final @Nullable Object first;
   private final @Nullable Object second;
   private final @Nullable Object third;

   public Triple(@Nullable Object first, @Nullable Object second, @Nullable Object third) {
      this.first = first;
      this.second = second;
      this.third = third;
   }

   public @Nullable Object first() {
      return this.first;
   }

   public @Nullable Object second() {
      return this.second;
   }

   public @Nullable Object third() {
      return this.third;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Triple)) {
         return false;
      } else {
         Triple var2 = (Triple)var1;
         return Objects.equals(this.first, var2.first) && Objects.equals(this.second, var2.second) && Objects.equals(this.third, var2.third);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.first)) * 31 + Objects.hashCode(this.second)) * 31 + Objects.hashCode(this.third);
   }

   public String toString() {
      return String.format("%s[first=%s, second=%s, third=%s]", this.getClass().getSimpleName(), Objects.toString(this.first), Objects.toString(this.second), Objects.toString(this.third));
   }
}
