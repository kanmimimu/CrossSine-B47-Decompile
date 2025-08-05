package com.viaversion.viaversion.api.protocol.version;

import com.google.common.base.Preconditions;
import java.util.Objects;

public final class SubVersionRange {
   private final String baseVersion;
   private final int rangeFrom;
   private final int rangeTo;

   public SubVersionRange(String baseVersion, int rangeFrom, int rangeTo) {
      Preconditions.checkNotNull(baseVersion);
      Preconditions.checkArgument(rangeFrom >= 0);
      Preconditions.checkArgument(rangeTo > rangeFrom);
      this.baseVersion = baseVersion;
      this.rangeFrom = rangeFrom;
      this.rangeTo = rangeTo;
   }

   public String baseVersion() {
      return this.baseVersion;
   }

   public int rangeFrom() {
      return this.rangeFrom;
   }

   public int rangeTo() {
      return this.rangeTo;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SubVersionRange)) {
         return false;
      } else {
         SubVersionRange var2 = (SubVersionRange)var1;
         return Objects.equals(this.baseVersion, var2.baseVersion) && this.rangeFrom == var2.rangeFrom && this.rangeTo == var2.rangeTo;
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.baseVersion)) * 31 + Integer.hashCode(this.rangeFrom)) * 31 + Integer.hashCode(this.rangeTo);
   }

   public String toString() {
      return String.format("%s[baseVersion=%s, rangeFrom=%s, rangeTo=%s]", this.getClass().getSimpleName(), Objects.toString(this.baseVersion), Integer.toString(this.rangeFrom), Integer.toString(this.rangeTo));
   }
}
