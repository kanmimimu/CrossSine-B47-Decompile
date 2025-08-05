package com.viaversion.viabackwards.protocol.v1_16_4to1_16_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.v1_16_4to1_16_3.storage.PlayerHandStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;

public class Protocol1_16_4To1_16_3 extends BackwardsProtocol {
   public Protocol1_16_4To1_16_3() {
      super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
   }

   protected void registerPackets() {
      this.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketHandlers() {
         public void register() {
            this.map(Types.ITEM1_13_2);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int slot = (Integer)wrapper.read(Types.VAR_INT);
               if (slot == 1) {
                  wrapper.write(Types.VAR_INT, 40);
               } else {
                  wrapper.write(Types.VAR_INT, ((PlayerHandStorage)wrapper.user().get(PlayerHandStorage.class)).getCurrentHand());
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_16_2.SET_CARRIED_ITEM, (wrapper) -> {
         short slot = (Short)wrapper.passthrough(Types.SHORT);
         ((PlayerHandStorage)wrapper.user().get(PlayerHandStorage.class)).setCurrentHand(slot);
      });
   }

   public void init(UserConnection user) {
      user.put(new PlayerHandStorage());
   }
}
