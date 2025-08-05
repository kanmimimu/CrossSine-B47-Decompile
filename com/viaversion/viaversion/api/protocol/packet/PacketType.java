package com.viaversion.viaversion.api.protocol.packet;

import ()Ljava.lang.String;;

public sealed interface PacketType permits ()I, String; {
   int getId();

   String getName();

   Direction direction();

   default State state() {
      return State.PLAY;
   }
}
