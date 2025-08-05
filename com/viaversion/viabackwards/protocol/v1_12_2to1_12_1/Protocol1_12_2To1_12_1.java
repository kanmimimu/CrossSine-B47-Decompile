package com.viaversion.viabackwards.protocol.v1_12_2to1_12_1;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.v1_12_2to1_12_1.storage.KeepAliveTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class Protocol1_12_2To1_12_1 extends BackwardsProtocol {
   public Protocol1_12_2To1_12_1() {
      super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.handler((packetWrapper) -> {
               Long keepAlive = (Long)packetWrapper.read(Types.LONG);
               ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(keepAlive);
               packetWrapper.write(Types.VAR_INT, keepAlive.hashCode());
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.handler((packetWrapper) -> {
               int keepAlive = (Integer)packetWrapper.read(Types.VAR_INT);
               long realKeepAlive = ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).getKeepAlive();
               if (keepAlive != Long.hashCode(realKeepAlive)) {
                  packetWrapper.cancel();
               } else {
                  packetWrapper.write(Types.LONG, realKeepAlive);
                  ((KeepAliveTracker)packetWrapper.user().get(KeepAliveTracker.class)).setKeepAlive(2147483647L);
               }
            });
         }
      });
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new KeepAliveTracker());
   }
}
