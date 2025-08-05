package net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ClientboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ServerboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.storage.PlayerAirTimeStorage;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.task.PlayerAirTimeUpdateTask;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ClientboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ServerboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_8_0_1tor1_0_0_1 extends StatelessProtocol {
   final ItemRewriter itemRewriter = new ItemRewriter(this);

   public Protocolb1_8_0_1tor1_0_0_1() {
      super(ClientboundPacketsb1_8.class, ClientboundPackets1_0_0.class, ServerboundPacketsb1_8.class, ServerboundPackets1_0_0.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.registerClientbound(ClientboundPacketsb1_8.SET_EXPERIENCE, (wrapper) -> {
         float experience = (float)(Byte)wrapper.read(Types.BYTE);
         byte experienceLevel = (Byte)wrapper.read(Types.BYTE);
         short experienceTotal = (Short)wrapper.read(Types.SHORT);
         experience = (experience - 1.0F) / (float)(10 * experienceLevel);
         wrapper.write(Types.FLOAT, experience);
         wrapper.write(Types.SHORT, (short)experienceLevel);
         wrapper.write(Types.SHORT, experienceTotal);
      });
      this.registerClientbound(ClientboundPacketsb1_8.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types1_4_2.NBTLESS_ITEM, Types1_2_4.NBT_ITEM);
         }
      });
      this.registerClientbound(ClientboundPacketsb1_8.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types1_4_2.NBTLESS_ITEM_ARRAY, Types1_2_4.NBT_ITEM_ARRAY);
         }
      });
      this.registerServerbound(ServerboundPackets1_0_0.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_2_4.NBT_ITEM, Types1_4_2.NBTLESS_ITEM);
         }
      });
      this.registerServerbound(ServerboundPackets1_0_0.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types1_2_4.NBT_ITEM, Types1_4_2.NBTLESS_ITEM);
         }
      });
      this.cancelServerbound(ServerboundPackets1_0_0.CONTAINER_BUTTON_CLICK);
   }

   public void register(ViaProviders providers) {
      Via.getPlatform().runRepeatingSync(new PlayerAirTimeUpdateTask(), 1L);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolb1_8_0_1tor1_0_0_1.class, ClientboundPacketsb1_8::getPacket));
      userConnection.put(new PlayerAirTimeStorage());
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
