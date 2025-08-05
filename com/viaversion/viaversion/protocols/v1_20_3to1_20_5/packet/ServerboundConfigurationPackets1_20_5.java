package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet;

import com.viaversion.viaversion.api.protocol.packet.State;

public enum ServerboundConfigurationPackets1_20_5 implements ServerboundPacket1_20_5 {
   CLIENT_INFORMATION,
   COOKIE_RESPONSE,
   CUSTOM_PAYLOAD,
   FINISH_CONFIGURATION,
   KEEP_ALIVE,
   PONG,
   RESOURCE_PACK,
   SELECT_KNOWN_PACKS;

   public int getId() {
      return this.ordinal();
   }

   public String getName() {
      return this.name();
   }

   public State state() {
      return State.CONFIGURATION;
   }

   // $FF: synthetic method
   private static ServerboundConfigurationPackets1_20_5[] $values() {
      return new ServerboundConfigurationPackets1_20_5[]{CLIENT_INFORMATION, COOKIE_RESPONSE, CUSTOM_PAYLOAD, FINISH_CONFIGURATION, KEEP_ALIVE, PONG, RESOURCE_PACK, SELECT_KNOWN_PACKS};
   }
}
