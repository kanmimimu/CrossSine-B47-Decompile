package net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.packet.ClientboundPacketsa1_0_16;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.storage.TimeLockStorage;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.task.TimeLockTask;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.packet.ClientboundPacketsa1_0_17;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.packet.ServerboundPacketsa1_0_17;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocola1_0_16_2Toa1_0_17_1_0_17_4 extends StatelessProtocol {
   public Protocola1_0_16_2Toa1_0_17_1_0_17_4() {
      super(ClientboundPacketsa1_0_16.class, ClientboundPacketsa1_0_17.class, ServerboundPacketsa1_0_17.class, ServerboundPacketsa1_0_17.class);
   }

   protected void registerPackets() {
      this.registerServerbound(ServerboundPacketsa1_0_17.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               if ((Short)wrapper.get(Types.SHORT, 0) < 0) {
                  wrapper.cancel();
               }

            });
         }
      });
   }

   public void register(ViaProviders providers) {
      Via.getPlatform().runRepeatingSync(new TimeLockTask(), 20L);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocola1_0_16_2Toa1_0_17_1_0_17_4.class, ClientboundPacketsa1_0_16::getPacket));
      userConnection.put(new TimeLockStorage(0L));
   }
}
