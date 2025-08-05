package net.raphimc.vialegacy.protocol.release.r1_6_1tor1_6_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import java.nio.charset.StandardCharsets;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.BlockFaceUtil;
import net.raphimc.vialegacy.protocol.release.r1_6_1tor1_6_2.packet.ClientboundPackets1_6_1;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ClientboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_6_1Tor1_6_2 extends StatelessProtocol {
   public Protocolr1_6_1Tor1_6_2() {
      super(ClientboundPackets1_6_1.class, ClientboundPackets1_6_4.class, ServerboundPackets1_6_4.class, ServerboundPackets1_6_4.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_6_1.LOGIN, (wrapper) -> {
         PacketWrapper brand = PacketWrapper.create(ClientboundPackets1_6_4.CUSTOM_PAYLOAD, (UserConnection)wrapper.user());
         brand.write(Types1_6_4.STRING, "MC|Brand");
         byte[] brandBytes = "legacy".getBytes(StandardCharsets.UTF_8);
         brand.write(Types.SHORT, (short)brandBytes.length);
         brand.write(Types.REMAINING_BYTES, brandBytes);
         wrapper.send(Protocolr1_6_1Tor1_6_2.class);
         brand.send(Protocolr1_6_1Tor1_6_2.class);
         wrapper.cancel();
      });
      this.registerClientbound(ClientboundPackets1_6_1.UPDATE_ATTRIBUTES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int amount = (Integer)wrapper.passthrough(Types.INT);

               for(int i = 0; i < amount; ++i) {
                  wrapper.passthrough(Types1_6_4.STRING);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.write(Types.SHORT, Short.valueOf((short)0));
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_6_4.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_7_6.ITEM);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
               short direction = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               Item item = (Item)wrapper.get(Types1_7_6.ITEM, 0);
               if (item != null && item.identifier() == ItemList1_6.sign.itemId() && direction != 255 && direction != 0) {
                  PacketWrapper openSignEditor = PacketWrapper.create(ClientboundPackets1_6_4.OPEN_SIGN_EDITOR, (UserConnection)wrapper.user());
                  openSignEditor.write(Types.BYTE, (byte)0);
                  openSignEditor.write(Types1_7_6.BLOCK_POSITION_INT, pos.getRelative(BlockFaceUtil.getFace(direction)));
                  openSignEditor.send(Protocolr1_6_1Tor1_6_2.class);
               }

            });
         }
      });
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolr1_6_1Tor1_6_2.class, ClientboundPackets1_6_1::getPacket));
   }
}
