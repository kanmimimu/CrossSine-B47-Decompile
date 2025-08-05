package com.viaversion.viaversion.protocols.v1_20_5to1_21.packet;

import com.viaversion.viaversion.api.protocol.packet.State;

public enum ClientboundConfigurationPackets1_21 implements ClientboundPacket1_21 {
   COOKIE_REQUEST,
   CUSTOM_PAYLOAD,
   DISCONNECT,
   FINISH_CONFIGURATION,
   KEEP_ALIVE,
   PING,
   RESET_CHAT,
   REGISTRY_DATA,
   RESOURCE_PACK_POP,
   RESOURCE_PACK_PUSH,
   STORE_COOKIE,
   TRANSFER,
   UPDATE_ENABLED_FEATURES,
   UPDATE_TAGS,
   SELECT_KNOWN_PACKS,
   CUSTOM_REPORT_DETAILS,
   SERVER_LINKS;

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
   private static ClientboundConfigurationPackets1_21[] $values() {
      return new ClientboundConfigurationPackets1_21[]{COOKIE_REQUEST, CUSTOM_PAYLOAD, DISCONNECT, FINISH_CONFIGURATION, KEEP_ALIVE, PING, RESET_CHAT, REGISTRY_DATA, RESOURCE_PACK_POP, RESOURCE_PACK_PUSH, STORE_COOKIE, TRANSFER, UPDATE_ENABLED_FEATURES, UPDATE_TAGS, SELECT_KNOWN_PACKS, CUSTOM_REPORT_DETAILS, SERVER_LINKS};
   }
}
