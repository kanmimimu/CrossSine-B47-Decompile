package net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.packet.ClientboundPacketsa1_0_17;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.packet.ServerboundPacketsa1_0_17;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ClientboundPacketsa1_1_0;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ServerboundPacketsa1_1_0;

public class Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1 extends StatelessProtocol {
   public Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1() {
      super(ClientboundPacketsa1_0_17.class, ClientboundPacketsa1_1_0.class, ServerboundPacketsa1_0_17.class, ServerboundPacketsa1_1_0.class);
   }

   protected void registerPackets() {
      this.cancelServerbound(ServerboundPacketsa1_1_0.BLOCK_ENTITY_DATA);
      this.cancelServerbound(ServerboundPacketsa1_1_0.PLAYER_INVENTORY);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1.class, ClientboundPacketsa1_0_17::getPacket));
   }
}
