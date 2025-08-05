package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeMapMap implements PacketTypeMap {
   private final Map packetsByName;
   private final Int2ObjectMap packetsById;

   PacketTypeMapMap(Map packetsByName, Int2ObjectMap packetsById) {
      this.packetsByName = packetsByName;
      this.packetsById = packetsById;
   }

   public @Nullable PacketType typeByName(String packetTypeName) {
      return (PacketType)this.packetsByName.get(packetTypeName);
   }

   public @Nullable PacketType typeById(int packetTypeId) {
      return (PacketType)this.packetsById.get(packetTypeId);
   }

   public Collection types() {
      return this.packetsById.values();
   }
}
