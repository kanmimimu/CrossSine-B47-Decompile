package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeArrayMap implements PacketTypeMap {
   private final Map packetsByName;
   private final PacketType[] packets;

   PacketTypeArrayMap(Map packetsByName, PacketType[] packets) {
      this.packetsByName = packetsByName;
      this.packets = packets;
   }

   public @Nullable PacketType typeByName(String packetTypeName) {
      return (PacketType)this.packetsByName.get(packetTypeName);
   }

   public @Nullable PacketType typeById(int packetTypeId) {
      return packetTypeId >= 0 && packetTypeId < this.packets.length ? this.packets[packetTypeId] : null;
   }

   public Collection types() {
      return Arrays.asList(this.packets);
   }
}
