package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Objects;
import java.util.SortedSet;

public final class ServerProtocolVersionSingleton implements ServerProtocolVersion {
   private final ProtocolVersion protocolVersion;

   public ServerProtocolVersionSingleton(ProtocolVersion protocolVersion) {
      this.protocolVersion = protocolVersion;
   }

   public ProtocolVersion lowestSupportedProtocolVersion() {
      return this.protocolVersion;
   }

   public ProtocolVersion highestSupportedProtocolVersion() {
      return this.protocolVersion;
   }

   public SortedSet supportedProtocolVersions() {
      SortedSet<ProtocolVersion> set = new ObjectLinkedOpenHashSet();
      set.add(this.protocolVersion);
      return set;
   }

   public ProtocolVersion protocolVersion() {
      return this.protocolVersion;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ServerProtocolVersionSingleton)) {
         return false;
      } else {
         ServerProtocolVersionSingleton var2 = (ServerProtocolVersionSingleton)var1;
         return Objects.equals(this.protocolVersion, var2.protocolVersion);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.protocolVersion);
   }

   public String toString() {
      return String.format("%s[protocolVersion=%s]", this.getClass().getSimpleName(), Objects.toString(this.protocolVersion));
   }
}
