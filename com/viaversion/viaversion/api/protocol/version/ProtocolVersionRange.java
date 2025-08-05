package com.viaversion.viaversion.api.protocol.version;

import com.google.common.collect.Range;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProtocolVersionRange {
   private List ranges;

   private ProtocolVersionRange(List ranges) {
      if (ranges != null) {
         this.ranges = new ArrayList(ranges);
      }

   }

   public static ProtocolVersionRange all() {
      return new ProtocolVersionRange((List)null);
   }

   public static ProtocolVersionRange of(ProtocolVersion min, ProtocolVersion max) {
      return new ProtocolVersionRange(Collections.singletonList(Range.open(min, max)));
   }

   public static ProtocolVersionRange of(Range range) {
      return new ProtocolVersionRange(Collections.singletonList(range));
   }

   public static ProtocolVersionRange of(List ranges) {
      return new ProtocolVersionRange(ranges);
   }

   public ProtocolVersionRange add(Range range) {
      if (this.ranges == null) {
         throw new UnsupportedOperationException("Range already contains all versions. Cannot add a new range.");
      } else {
         this.ranges.add(range);
         return this;
      }
   }

   public boolean contains(ProtocolVersion version) {
      if (this.ranges == null) {
         return true;
      } else {
         for(Range range : this.ranges) {
            if (range.contains(version)) {
               return true;
            }
         }

         return false;
      }
   }

   public String toString() {
      if (this.ranges != null) {
         StringBuilder rangeString = new StringBuilder();
         int i = 0;

         for(Range range : this.ranges) {
            ++i;
            ProtocolVersion min = range.hasLowerBound() ? (ProtocolVersion)range.lowerEndpoint() : null;
            ProtocolVersion max = range.hasUpperBound() ? (ProtocolVersion)range.upperEndpoint() : null;
            if (min == null) {
               rangeString.append("<= ").append(max.getName());
            } else if (max == null) {
               rangeString.append(">= ").append(min.getName());
            } else if (Objects.equals(min, max)) {
               rangeString.append(min.getName());
            } else {
               rangeString.append(min.getName()).append(" - ").append(max.getName());
            }

            if (i != this.ranges.size()) {
               rangeString.append(", ");
            }
         }

         return rangeString.toString();
      } else {
         return "*";
      }
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         ProtocolVersionRange that = (ProtocolVersionRange)object;
         return Objects.equals(this.ranges, that.ranges);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.ranges});
   }

   public static ProtocolVersionRange fromString(String str) {
      if ("*".equals(str)) {
         return all();
      } else if (str.contains(",")) {
         String[] rangeParts = str.split(", ");
         ProtocolVersionRange versionRange = null;

         for(String part : rangeParts) {
            if (versionRange == null) {
               versionRange = of(parseSinglePart(part));
            } else {
               versionRange.add(parseSinglePart(part));
            }
         }

         return versionRange;
      } else {
         return of(parseSinglePart(str));
      }
   }

   private static Range parseSinglePart(String part) {
      if (part.startsWith("<= ")) {
         return Range.atMost(ProtocolVersion.getClosest(part.substring(3)));
      } else if (part.startsWith(">= ")) {
         return Range.atLeast(ProtocolVersion.getClosest(part.substring(3)));
      } else if (part.contains(" - ")) {
         String[] rangeParts = part.split(" - ");
         ProtocolVersion min = ProtocolVersion.getClosest(rangeParts[0]);
         ProtocolVersion max = ProtocolVersion.getClosest(rangeParts[1]);
         return Range.open(min, max);
      } else {
         return Range.singleton(ProtocolVersion.getClosest(part));
      }
   }
}
