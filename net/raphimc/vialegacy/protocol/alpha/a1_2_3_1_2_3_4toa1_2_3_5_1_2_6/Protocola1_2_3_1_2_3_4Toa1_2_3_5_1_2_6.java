package net.raphimc.vialegacy.protocol.alpha.a1_2_3_1_2_3_4toa1_2_3_5_1_2_6;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_1_2_3_4toa1_2_3_5_1_2_6.packet.ClientboundPacketsa1_2_3;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet.ClientboundPacketsa1_2_6;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet.ServerboundPacketsa1_2_6;

public class Protocola1_2_3_1_2_3_4Toa1_2_3_5_1_2_6 extends StatelessProtocol {
   public Protocola1_2_3_1_2_3_4Toa1_2_3_5_1_2_6() {
      super(ClientboundPacketsa1_2_3.class, ClientboundPacketsa1_2_6.class, ServerboundPacketsa1_2_6.class, ServerboundPacketsa1_2_6.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsa1_2_3.SET_ENTITY_MOTION, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.SHORT, Types.SHORT, (v) -> (short)((int)((float)v / 4.0F)));
            this.map(Types.SHORT, Types.SHORT, (v) -> (short)((int)((float)v / 4.0F)));
            this.map(Types.SHORT, Types.SHORT, (v) -> (short)((int)((float)v / 4.0F)));
         }
      });
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocola1_2_3_1_2_3_4Toa1_2_3_5_1_2_6.class, ClientboundPacketsa1_2_3::getPacket));
   }
}
