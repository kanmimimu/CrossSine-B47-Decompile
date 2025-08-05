package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Map;
import java.util.Objects;

public final class SimplePacketTypesProvider implements PacketTypesProvider {
   private final Map unmappedClientboundPacketTypes;
   private final Map mappedClientboundPacketTypes;
   private final Map mappedServerboundPacketTypes;
   private final Map unmappedServerboundPacketTypes;

   public SimplePacketTypesProvider(Map unmappedClientboundPacketTypes, Map mappedClientboundPacketTypes, Map mappedServerboundPacketTypes, Map unmappedServerboundPacketTypes) {
      this.unmappedClientboundPacketTypes = unmappedClientboundPacketTypes;
      this.mappedClientboundPacketTypes = mappedClientboundPacketTypes;
      this.mappedServerboundPacketTypes = mappedServerboundPacketTypes;
      this.unmappedServerboundPacketTypes = unmappedServerboundPacketTypes;
   }

   public Map unmappedClientboundPacketTypes() {
      return this.unmappedClientboundPacketTypes;
   }

   public Map mappedClientboundPacketTypes() {
      return this.mappedClientboundPacketTypes;
   }

   public Map mappedServerboundPacketTypes() {
      return this.mappedServerboundPacketTypes;
   }

   public Map unmappedServerboundPacketTypes() {
      return this.unmappedServerboundPacketTypes;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SimplePacketTypesProvider)) {
         return false;
      } else {
         SimplePacketTypesProvider var2 = (SimplePacketTypesProvider)var1;
         return Objects.equals(this.unmappedClientboundPacketTypes, var2.unmappedClientboundPacketTypes) && Objects.equals(this.mappedClientboundPacketTypes, var2.mappedClientboundPacketTypes) && Objects.equals(this.mappedServerboundPacketTypes, var2.mappedServerboundPacketTypes) && Objects.equals(this.unmappedServerboundPacketTypes, var2.unmappedServerboundPacketTypes);
      }
   }

   public int hashCode() {
      return (((0 * 31 + Objects.hashCode(this.unmappedClientboundPacketTypes)) * 31 + Objects.hashCode(this.mappedClientboundPacketTypes)) * 31 + Objects.hashCode(this.mappedServerboundPacketTypes)) * 31 + Objects.hashCode(this.unmappedServerboundPacketTypes);
   }

   public String toString() {
      return String.format("%s[unmappedClientboundPacketTypes=%s, mappedClientboundPacketTypes=%s, mappedServerboundPacketTypes=%s, unmappedServerboundPacketTypes=%s]", this.getClass().getSimpleName(), Objects.toString(this.unmappedClientboundPacketTypes), Objects.toString(this.mappedClientboundPacketTypes), Objects.toString(this.mappedServerboundPacketTypes), Objects.toString(this.unmappedServerboundPacketTypes));
   }
}
