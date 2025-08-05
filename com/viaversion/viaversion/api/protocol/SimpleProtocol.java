package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public interface SimpleProtocol extends Protocol {
   public static enum DummyPacketTypes implements ClientboundPacketType, ServerboundPacketType {
      public int getId() {
         return 0;
      }

      public String getName() {
         return this.name();
      }

      public Direction direction() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      static DummyPacketTypes[] $values() {
         return new DummyPacketTypes[0];
      }
   }
}
