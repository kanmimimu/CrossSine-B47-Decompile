package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;

public enum ClientboundStatusPackets implements BaseClientboundPacket {
   STATUS_RESPONSE,
   PONG_RESPONSE;

   public final int getId() {
      return this.ordinal();
   }

   public final String getName() {
      return this.name();
   }

   public final State state() {
      return State.STATUS;
   }

   // $FF: synthetic method
   private static ClientboundStatusPackets[] $values() {
      return new ClientboundStatusPackets[]{STATUS_RESPONSE, PONG_RESPONSE};
   }
}
