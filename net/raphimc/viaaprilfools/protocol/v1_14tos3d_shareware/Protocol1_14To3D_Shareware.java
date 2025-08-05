package net.raphimc.viaaprilfools.protocol.v1_14tos3d_shareware;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ClientboundPackets3D_Shareware;
import net.raphimc.viaaprilfools.protocol.s3d_sharewaretov1_14.packet.ServerboundPackets3D_Shareware;

public class Protocol1_14To3D_Shareware extends BackwardsProtocol {
   public Protocol1_14To3D_Shareware() {
      super(ClientboundPackets1_14.class, ClientboundPackets3D_Shareware.class, ServerboundPackets1_14.class, ServerboundPackets3D_Shareware.class);
   }

   protected void registerPackets() {
      this.cancelClientbound(ClientboundPackets1_14.SET_CHUNK_CACHE_CENTER);
   }
}
