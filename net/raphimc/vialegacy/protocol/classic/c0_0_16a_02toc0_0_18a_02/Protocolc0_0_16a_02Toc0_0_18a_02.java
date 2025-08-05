package net.raphimc.vialegacy.protocol.classic.c0_0_16a_02toc0_0_18a_02;

import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ClientboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ServerboundPacketsc0_19a;

public class Protocolc0_0_16a_02Toc0_0_18a_02 extends StatelessProtocol {
   public Protocolc0_0_16a_02Toc0_0_18a_02() {
      super(ClientboundPacketsc0_19a.class, ClientboundPacketsc0_19a.class, ServerboundPacketsc0_19a.class, ServerboundPacketsc0_19a.class);
   }
}
