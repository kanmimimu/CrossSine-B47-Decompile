package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.Protocola1_2_3_5_1_2_6Tob1_0_1_1_1;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.AlphaInventoryTracker;

public class TrackingAlphaInventoryProvider extends AlphaInventoryProvider {
   public boolean usesInventoryTracker() {
      return true;
   }

   public Item[] getMainInventoryItems(UserConnection user) {
      return Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(((AlphaInventoryTracker)user.get(AlphaInventoryTracker.class)).getMainInventory());
   }

   public Item[] getCraftingInventoryItems(UserConnection user) {
      return Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(((AlphaInventoryTracker)user.get(AlphaInventoryTracker.class)).getCraftingInventory());
   }

   public Item[] getArmorInventoryItems(UserConnection user) {
      return Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(((AlphaInventoryTracker)user.get(AlphaInventoryTracker.class)).getArmorInventory());
   }

   public Item[] getContainerItems(UserConnection user) {
      return Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(((AlphaInventoryTracker)user.get(AlphaInventoryTracker.class)).getOpenContainerItems());
   }

   public void addToInventory(UserConnection user, Item item) {
      ((AlphaInventoryTracker)user.get(AlphaInventoryTracker.class)).addItem(item);
   }
}
