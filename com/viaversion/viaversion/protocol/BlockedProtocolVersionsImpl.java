package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;
import java.util.Set;

public final class BlockedProtocolVersionsImpl implements BlockedProtocolVersions {
   private final Set singleBlockedVersions;
   private final ProtocolVersion blocksBelow;
   private final ProtocolVersion blocksAbove;

   public BlockedProtocolVersionsImpl(Set singleBlockedVersions, ProtocolVersion blocksBelow, ProtocolVersion blocksAbove) {
      this.singleBlockedVersions = singleBlockedVersions;
      this.blocksBelow = blocksBelow;
      this.blocksAbove = blocksAbove;
   }

   public boolean contains(ProtocolVersion protocolVersion) {
      return this.blocksBelow.isKnown() && protocolVersion.olderThan(this.blocksBelow) || this.blocksAbove.isKnown() && protocolVersion.newerThan(this.blocksAbove) || this.singleBlockedVersions.contains(protocolVersion);
   }

   public Set singleBlockedVersions() {
      return this.singleBlockedVersions;
   }

   public ProtocolVersion blocksBelow() {
      return this.blocksBelow;
   }

   public ProtocolVersion blocksAbove() {
      return this.blocksAbove;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BlockedProtocolVersionsImpl)) {
         return false;
      } else {
         BlockedProtocolVersionsImpl var2 = (BlockedProtocolVersionsImpl)var1;
         return Objects.equals(this.singleBlockedVersions, var2.singleBlockedVersions) && Objects.equals(this.blocksBelow, var2.blocksBelow) && Objects.equals(this.blocksAbove, var2.blocksAbove);
      }
   }

   public int hashCode() {
      return ((0 * 31 + Objects.hashCode(this.singleBlockedVersions)) * 31 + Objects.hashCode(this.blocksBelow)) * 31 + Objects.hashCode(this.blocksAbove);
   }

   public String toString() {
      return String.format("%s[singleBlockedVersions=%s, blocksBelow=%s, blocksAbove=%s]", this.getClass().getSimpleName(), Objects.toString(this.singleBlockedVersions), Objects.toString(this.blocksBelow), Objects.toString(this.blocksAbove));
   }
}
