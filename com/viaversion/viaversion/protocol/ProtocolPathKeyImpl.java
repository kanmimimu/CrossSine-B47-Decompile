package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;

public final class ProtocolPathKeyImpl implements ProtocolPathKey {
   private final ProtocolVersion clientProtocolVersion;
   private final ProtocolVersion serverProtocolVersion;

   public ProtocolPathKeyImpl(ProtocolVersion clientProtocolVersion, ProtocolVersion serverProtocolVersion) {
      this.clientProtocolVersion = clientProtocolVersion;
      this.serverProtocolVersion = serverProtocolVersion;
   }

   public ProtocolVersion clientProtocolVersion() {
      return this.clientProtocolVersion;
   }

   public ProtocolVersion serverProtocolVersion() {
      return this.serverProtocolVersion;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProtocolPathKeyImpl)) {
         return false;
      } else {
         ProtocolPathKeyImpl var2 = (ProtocolPathKeyImpl)var1;
         return Objects.equals(this.clientProtocolVersion, var2.clientProtocolVersion) && Objects.equals(this.serverProtocolVersion, var2.serverProtocolVersion);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.clientProtocolVersion)) * 31 + Objects.hashCode(this.serverProtocolVersion);
   }

   public String toString() {
      return String.format("%s[clientProtocolVersion=%s, serverProtocolVersion=%s]", this.getClass().getSimpleName(), Objects.toString(this.clientProtocolVersion), Objects.toString(this.serverProtocolVersion));
   }
}
