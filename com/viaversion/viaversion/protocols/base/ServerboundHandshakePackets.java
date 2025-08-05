package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;

public enum ServerboundHandshakePackets implements BaseServerboundPacket {
   CLIENT_INTENTION;

   public final int getId() {
      return this.ordinal();
   }

   public final String getName() {
      return this.name();
   }

   public final State state() {
      return State.HANDSHAKE;
   }

   // $FF: synthetic method
   private static ServerboundHandshakePackets[] $values() {
      return new ServerboundHandshakePackets[]{CLIENT_INTENTION};
   }
}
