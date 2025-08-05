package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;

public enum ClientboundLoginPackets implements BaseClientboundPacket {
   LOGIN_DISCONNECT,
   HELLO,
   GAME_PROFILE,
   LOGIN_COMPRESSION,
   CUSTOM_QUERY,
   COOKIE_REQUEST;

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
   private static ClientboundLoginPackets[] $values() {
      return new ClientboundLoginPackets[]{LOGIN_DISCONNECT, HELLO, GAME_PROFILE, LOGIN_COMPRESSION, CUSTOM_QUERY, COOKIE_REQUEST};
   }
}
