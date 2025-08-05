package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketTypesProvider {
   Map unmappedClientboundPacketTypes();

   Map unmappedServerboundPacketTypes();

   Map mappedClientboundPacketTypes();

   Map mappedServerboundPacketTypes();

   default @Nullable ClientboundPacketType unmappedClientboundType(State state, String typeName) {
      PacketTypeMap<CU> map = (PacketTypeMap)this.unmappedClientboundPacketTypes().get(state);
      return map != null ? (ClientboundPacketType)map.typeByName(typeName) : null;
   }

   default @Nullable ServerboundPacketType unmappedServerboundType(State state, String typeName) {
      PacketTypeMap<SU> map = (PacketTypeMap)this.unmappedServerboundPacketTypes().get(state);
      return map != null ? (ServerboundPacketType)map.typeByName(typeName) : null;
   }

   default @Nullable ClientboundPacketType unmappedClientboundType(State state, int packetId) {
      PacketTypeMap<CU> map = (PacketTypeMap)this.unmappedClientboundPacketTypes().get(state);
      return map != null ? (ClientboundPacketType)map.typeById(packetId) : null;
   }

   default @Nullable ServerboundPacketType unmappedServerboundType(State state, int packetId) {
      PacketTypeMap<SU> map = (PacketTypeMap)this.unmappedServerboundPacketTypes().get(state);
      return map != null ? (ServerboundPacketType)map.typeById(packetId) : null;
   }
}
