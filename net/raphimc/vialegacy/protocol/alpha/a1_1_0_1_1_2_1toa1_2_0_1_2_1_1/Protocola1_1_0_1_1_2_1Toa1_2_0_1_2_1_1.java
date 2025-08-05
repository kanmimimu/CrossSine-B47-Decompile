package net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ClientboundPacketsa1_1_0;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ServerboundPacketsa1_1_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_0_1_2_1_1toa1_2_2.packet.ClientboundPacketsa1_2_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_0_1_2_1_1toa1_2_2.packet.ServerboundPacketsa1_2_0;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;

public class Protocola1_1_0_1_1_2_1Toa1_2_0_1_2_1_1 extends StatelessProtocol {
   public Protocola1_1_0_1_1_2_1Toa1_2_0_1_2_1_1() {
      super(ClientboundPacketsa1_1_0.class, ClientboundPacketsa1_2_0.class, ServerboundPacketsa1_1_0.class, ServerboundPacketsa1_2_0.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsa1_1_0.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Typesb1_7_0_3.STRING);
            this.map(Typesb1_7_0_3.STRING);
            this.create(Types.LONG, 0L);
            this.create(Types.BYTE, (byte)0);
         }
      });
      this.registerServerbound(ServerboundPacketsa1_2_0.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Typesb1_7_0_3.STRING);
            this.map(Typesb1_7_0_3.STRING);
            this.read(Types.LONG);
            this.read(Types.BYTE);
         }
      });
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocola1_1_0_1_1_2_1Toa1_2_0_1_2_1_1.class, ClientboundPacketsa1_1_0::getPacket));
   }
}
