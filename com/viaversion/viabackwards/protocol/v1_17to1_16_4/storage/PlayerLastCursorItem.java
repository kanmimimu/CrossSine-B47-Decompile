package com.viaversion.viabackwards.protocol.v1_17to1_16_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.item.Item;

public class PlayerLastCursorItem implements StorableObject {
   private Item lastCursorItem;

   public Item getLastCursorItem() {
      return copyItem(this.lastCursorItem);
   }

   public void setLastCursorItem(Item item) {
      this.lastCursorItem = copyItem(item);
   }

   public void setLastCursorItem(Item item, int amount) {
      this.lastCursorItem = copyItem(item);
      this.lastCursorItem.setAmount(amount);
   }

   public boolean isSet() {
      return this.lastCursorItem != null;
   }

   private static Item copyItem(Item item) {
      return item == null ? null : item.copy();
   }
}
