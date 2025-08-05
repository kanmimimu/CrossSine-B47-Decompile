package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;

public enum ServerboundLoginPackets implements BaseServerboundPacket {
   HELLO,
   ENCRYPTION_KEY,
   CUSTOM_QUERY_ANSWER,
   LOGIN_ACKNOWLEDGED,
   COOKIE_RESPONSE;

   public final int getId() {
      return this.ordinal();
   }

   public final String getName() {
      return this.name();
   }

   public final State state() {
      return State.LOGIN;
   }

   // $FF: synthetic method
   private static ServerboundLoginPackets[] $values() {
      return new ServerboundLoginPackets[]{HELLO, ENCRYPTION_KEY, CUSTOM_QUERY_ANSWER, LOGIN_ACKNOWLEDGED, COOKIE_RESPONSE};
   }
}
