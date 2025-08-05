package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;

public enum ServerboundStatusPackets implements BaseServerboundPacket {
   STATUS_REQUEST,
   PING_REQUEST;

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
   private static ServerboundStatusPackets[] $values() {
      return new ServerboundStatusPackets[]{STATUS_REQUEST, PING_REQUEST};
   }
}
