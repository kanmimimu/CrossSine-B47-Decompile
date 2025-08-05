package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;

public class NoOpAlphaInventoryProvider extends AlphaInventoryProvider {
   public boolean usesInventoryTracker() {
      return false;
   }

   public Item[] getMainInventoryItems(UserConnection user) {
      return new Item[36];
   }

   public Item[] getCraftingInventoryItems(UserConnection user) {
      return new Item[4];
   }

   public Item[] getArmorInventoryItems(UserConnection user) {
      return new Item[4];
   }

   public Item[] getContainerItems(UserConnection user) {
      InventoryStorage inventoryStorage = (InventoryStorage)user.get(InventoryStorage.class);
      return (Item[])inventoryStorage.containers.get(inventoryStorage.openContainerPos);
   }

   public void addToInventory(UserConnection user, Item item) {
   }
}
