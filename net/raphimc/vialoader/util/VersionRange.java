package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VersionRange {
   private final ProtocolVersion min;
   private final ProtocolVersion max;
   private final List ranges;

   private VersionRange(ProtocolVersion min, ProtocolVersion max) {
      this.min = min;
      this.max = max;
      this.ranges = new ArrayList();
   }

   public static VersionRange andNewer(ProtocolVersion version) {
      return new VersionRange(version, (ProtocolVersion)null);
   }

   public static VersionRange single(ProtocolVersion version) {
      return new VersionRange(version, version);
   }

   public static VersionRange andOlder(ProtocolVersion version) {
      return new VersionRange((ProtocolVersion)null, version);
   }

   public static VersionRange of(ProtocolVersion min, ProtocolVersion max) {
      return new VersionRange(min, max);
   }

   public static VersionRange all() {
      return new VersionRange((ProtocolVersion)null, (ProtocolVersion)null);
   }

   public VersionRange add(VersionRange range) {
      this.ranges.add(range);
      return this;
   }

   public boolean contains(ProtocolVersion version) {
      for(VersionRange range : this.ranges) {
         if (range.contains(version)) {
            return true;
         }
      }

      if (this.min == null && this.max == null) {
         return true;
      } else if (this.min == null) {
         return version.olderThanOrEqualTo(this.max);
      } else if (this.max == null) {
         return version.newerThanOrEqualTo(this.min);
      } else {
         return version.newerThanOrEqualTo(this.min) && version.olderThanOrEqualTo(this.max);
      }
   }

   public ProtocolVersion getMin() {
      return this.min;
   }

   public ProtocolVersion getMax() {
      return this.max;
   }

   public String toString() {
      if (this.min == null && this.max == null) {
         return "*";
      } else {
         StringBuilder rangeString = new StringBuilder();
         if (!this.ranges.isEmpty()) {
            for(VersionRange range : this.ranges) {
               rangeString.append(", ").append(range.toString());
            }
         }

         if (this.min == null) {
            String var5 = this.max.getName();
            return "<= " + var5 + rangeString;
         } else if (this.max == null) {
            String var8 = this.min.getName();
            return ">= " + var8 + rangeString;
         } else if (Objects.equals(this.min, this.max)) {
            return this.min.getName();
         } else {
            String var10000 = this.min.getName();
            String var12 = this.max.getName();
            String var11 = var10000;
            return var11 + " - " + var12 + rangeString;
         }
      }
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         VersionRange that = (VersionRange)object;
         return this.min == that.min && this.max == that.max && Objects.equals(this.ranges, that.ranges);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.min, this.max, this.ranges});
   }

   public static VersionRange fromString(String str) {
      if ("*".equals(str)) {
         return all();
      } else if (str.contains(",")) {
         String[] rangeParts = str.split(", ");
         VersionRange versionRange = null;

         for(String part : rangeParts) {
            if (versionRange == null) {
               versionRange = parseSinglePart(part);
            } else {
               versionRange.add(parseSinglePart(part));
            }
         }

         return versionRange;
      } else {
         return parseSinglePart(str);
      }
   }

   private static VersionRange parseSinglePart(String part) {
      if (part.startsWith("<= ")) {
         return andOlder(ProtocolVersion.getClosest(part.substring(3)));
      } else if (part.startsWith(">= ")) {
         return andNewer(ProtocolVersion.getClosest(part.substring(3)));
      } else if (part.contains(" - ")) {
         String[] rangeParts = part.split(" - ");
         ProtocolVersion min = ProtocolVersion.getClosest(rangeParts[0]);
         ProtocolVersion max = ProtocolVersion.getClosest(rangeParts[1]);
         return of(min, max);
      } else {
         return single(ProtocolVersion.getClosest(part));
      }
   }
}
