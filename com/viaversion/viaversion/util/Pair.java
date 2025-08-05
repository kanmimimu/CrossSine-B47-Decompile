package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Pair {
   private final @Nullable Object key;
   private final @Nullable Object value;

   public Pair(@Nullable Object key, @Nullable Object value) {
      this.key = key;
      this.value = value;
   }

   public @Nullable Object key() {
      return this.key;
   }

   public @Nullable Object value() {
      return this.value;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Pair)) {
         return false;
      } else {
         Pair var2 = (Pair)var1;
         return Objects.equals(this.key, var2.key) && Objects.equals(this.value, var2.value);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.key)) * 31 + Objects.hashCode(this.value);
   }

   public String toString() {
      return String.format("%s[key=%s, value=%s]", this.getClass().getSimpleName(), Objects.toString(this.key), Objects.toString(this.value));
   }
}
