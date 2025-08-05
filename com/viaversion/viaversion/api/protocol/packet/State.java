package com.viaversion.viaversion.api.protocol.packet;

public enum State {
   HANDSHAKE,
   STATUS,
   LOGIN,
   CONFIGURATION,
   PLAY;

   // $FF: synthetic method
   private static State[] $values() {
      return new State[]{HANDSHAKE, STATUS, LOGIN, CONFIGURATION, PLAY};
   }
}
