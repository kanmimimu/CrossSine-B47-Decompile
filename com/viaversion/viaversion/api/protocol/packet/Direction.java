package com.viaversion.viaversion.api.protocol.packet;

public enum Direction {
   CLIENTBOUND,
   SERVERBOUND;

   // $FF: synthetic method
   private static Direction[] $values() {
      return new Direction[]{CLIENTBOUND, SERVERBOUND};
   }
}
