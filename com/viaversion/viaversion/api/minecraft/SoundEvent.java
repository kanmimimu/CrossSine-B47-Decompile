package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SoundEvent {
   private final String identifier;
   private final @Nullable Float fixedRange;

   public SoundEvent(String identifier, @Nullable Float fixedRange) {
      this.identifier = identifier;
      this.fixedRange = fixedRange;
   }

   public SoundEvent withIdentifier(String identifier) {
      return new SoundEvent(identifier, this.fixedRange);
   }

   public String identifier() {
      return this.identifier;
   }

   public @Nullable Float fixedRange() {
      return this.fixedRange;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SoundEvent)) {
         return false;
      } else {
         SoundEvent var2 = (SoundEvent)var1;
         return Objects.equals(this.identifier, var2.identifier) && Objects.equals(this.fixedRange, var2.fixedRange);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.identifier)) * 31 + Objects.hashCode(this.fixedRange);
   }

   public String toString() {
      return String.format("%s[identifier=%s, fixedRange=%s]", this.getClass().getSimpleName(), Objects.toString(this.identifier), Objects.toString(this.fixedRange));
   }
}
