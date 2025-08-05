package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import java.util.Objects;
import java.util.SortedSet;

public final class ServerProtocolVersionRange implements ServerProtocolVersion {
   private final ProtocolVersion lowestSupportedProtocolVersion;
   private final ProtocolVersion highestSupportedProtocolVersion;
   private final SortedSet supportedProtocolVersions;

   public ServerProtocolVersionRange(ProtocolVersion lowestSupportedProtocolVersion, ProtocolVersion highestSupportedProtocolVersion, SortedSet supportedProtocolVersions) {
      this.lowestSupportedProtocolVersion = lowestSupportedProtocolVersion;
      this.highestSupportedProtocolVersion = highestSupportedProtocolVersion;
      this.supportedProtocolVersions = supportedProtocolVersions;
   }

   public ProtocolVersion lowestSupportedProtocolVersion() {
      return this.lowestSupportedProtocolVersion;
   }

   public ProtocolVersion highestSupportedProtocolVersion() {
      return this.highestSupportedProtocolVersion;
   }

   public SortedSet supportedProtocolVersions() {
      return this.supportedProtocolVersions;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ServerProtocolVersionRange)) {
         return false;
      } else {
         ServerProtocolVersionRange var2 = (ServerProtocolVersionRange)var1;
         return Objects.equals(this.lowestSupportedProtocolVersion, var2.lowestSupportedProtocolVersion) && Objects.equals(this.highestSupportedProtocolVersion, var2.highestSupportedProtocolVersion) && Objects.equals(this.supportedProtocolVersions, var2.supportedProtocolVersions);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.lowestSupportedProtocolVersion)) * 31 + Objects.hashCode(this.highestSupportedProtocolVersion)) * 31 + Objects.hashCode(this.supportedProtocolVersions);
   }

   public String toString() {
      return String.format("%s[lowestSupportedProtocolVersion=%s, highestSupportedProtocolVersion=%s, supportedProtocolVersions=%s]", this.getClass().getSimpleName(), Objects.toString(this.lowestSupportedProtocolVersion), Objects.toString(this.highestSupportedProtocolVersion), Objects.toString(this.supportedProtocolVersions));
   }
}
