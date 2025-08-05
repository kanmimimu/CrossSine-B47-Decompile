package com.viaversion.viaversion.protocols.v1_12_1to1_12_2;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class Protocol1_12_1To1_12_2 extends AbstractProtocol {
   public Protocol1_12_1To1_12_2() {
      super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT, Types.LONG);
         }
      });
      this.registerServerbound(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.LONG, Types.VAR_INT);
         }
      });
   }
}
