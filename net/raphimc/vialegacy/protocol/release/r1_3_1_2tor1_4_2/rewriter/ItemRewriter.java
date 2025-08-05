package net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.Protocolr1_3_1_2Tor1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter extends LegacyItemRewriter {
   public ItemRewriter(Protocolr1_3_1_2Tor1_4_2 protocol) {
      super(protocol, "1.3.2", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
      this.addNonExistentItemRange(137, 145);
      this.addNonExistentItemRange(389, 400);
      this.addNonExistentItem(383, 65);
      this.addNonExistentItem(383, 66);
      this.addNonExistentItems(new int[]{422});
   }

   protected void registerPackets() {
      this.registerCreativeInventoryAction(ServerboundPackets1_5_2.SET_CREATIVE_MODE_SLOT);
   }

   public Item handleItemToServer(UserConnection user, Item item) {
      if (item != null && item.identifier() == ItemList1_6.emptyMap.itemId()) {
         item.setIdentifier(ItemList1_6.map.itemId());
      }

      return super.handleItemToServer(user, item);
   }
}
