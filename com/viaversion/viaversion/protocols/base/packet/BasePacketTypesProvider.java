package com.viaversion.viaversion.protocols.base.packet;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundStatusPackets;
import java.util.EnumMap;
import java.util.Map;

public final class BasePacketTypesProvider implements PacketTypesProvider {
   public static final PacketTypesProvider INSTANCE = new BasePacketTypesProvider();
   private final Map clientboundPacketTypes = new EnumMap(State.class);
   private final Map serverboundPacketTypes = new EnumMap(State.class);

   private BasePacketTypesProvider() {
      this.clientboundPacketTypes.put(State.STATUS, PacketTypeMap.of(ClientboundStatusPackets.class));
      this.clientboundPacketTypes.put(State.LOGIN, PacketTypeMap.of(ClientboundLoginPackets.class));
      this.serverboundPacketTypes.put(State.STATUS, PacketTypeMap.of(ServerboundStatusPackets.class));
      this.serverboundPacketTypes.put(State.HANDSHAKE, PacketTypeMap.of(ServerboundHandshakePackets.class));
      this.serverboundPacketTypes.put(State.LOGIN, PacketTypeMap.of(ServerboundLoginPackets.class));
   }

   public Map unmappedClientboundPacketTypes() {
      return this.clientboundPacketTypes;
   }

   public Map unmappedServerboundPacketTypes() {
      return this.serverboundPacketTypes;
   }

   public Map mappedClientboundPacketTypes() {
      return this.unmappedClientboundPacketTypes();
   }

   public Map mappedServerboundPacketTypes() {
      return this.unmappedServerboundPacketTypes();
   }
}
