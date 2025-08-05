package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;

public final class ProtocolPathEntryImpl implements ProtocolPathEntry {
   private final ProtocolVersion outputProtocolVersion;
   private final Protocol protocol;

   public ProtocolPathEntryImpl(ProtocolVersion outputProtocolVersion, Protocol protocol) {
      this.outputProtocolVersion = outputProtocolVersion;
      this.protocol = protocol;
   }

   public ProtocolVersion outputProtocolVersion() {
      return this.outputProtocolVersion;
   }

   public Protocol protocol() {
      return this.protocol;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProtocolPathEntryImpl)) {
         return false;
      } else {
         ProtocolPathEntryImpl var2 = (ProtocolPathEntryImpl)var1;
         return Objects.equals(this.outputProtocolVersion, var2.outputProtocolVersion) && Objects.equals(this.protocol, var2.protocol);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.outputProtocolVersion)) * 31 + Objects.hashCode(this.protocol);
   }

   public String toString() {
      return String.format("%s[outputProtocolVersion=%s, protocol=%s]", this.getClass().getSimpleName(), Objects.toString(this.outputProtocolVersion), Objects.toString(this.protocol));
   }
}
