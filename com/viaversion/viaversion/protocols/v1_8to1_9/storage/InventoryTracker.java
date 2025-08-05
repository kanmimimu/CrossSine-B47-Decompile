package com.viaversion.viaversion.protocols.v1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import java.util.HashMap;
import java.util.Map;

public class InventoryTracker implements StorableObject {
   private String inventory;
   private final Map windowItemCache = new HashMap();
   private int itemIdInCursor;
   private boolean dragging;

   public String getInventory() {
      return this.inventory;
   }

   public void setInventory(String inventory) {
      this.inventory = inventory;
   }

   public void resetInventory(short windowId) {
      if (this.inventory == null) {
         this.itemIdInCursor = 0;
         this.dragging = false;
         if (windowId != 0) {
            this.windowItemCache.remove(windowId);
         }
      }

   }

   public int getItemId(short windowId, short slot) {
      Map<Short, Integer> itemMap = (Map)this.windowItemCache.get(windowId);
      return itemMap == null ? 0 : (Integer)itemMap.getOrDefault(slot, 0);
   }

   public void setItemId(short windowId, short slot, int itemId) {
      if (windowId == -1 && slot == -1) {
         this.itemIdInCursor = itemId;
      } else {
         ((Map)this.windowItemCache.computeIfAbsent(windowId, (k) -> new HashMap())).put(slot, itemId);
      }

   }

   public void handleWindowClick(UserConnection user, short windowId, byte mode, short hoverSlot, byte button) {
      EntityTracker1_9 entityTracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_8To1_9.class);
      if (hoverSlot != -1) {
         if (hoverSlot == 45) {
            entityTracker.setSecondHand((Item)null);
         } else {
            boolean isArmorOrResultSlot = hoverSlot >= 5 && hoverSlot <= 8 || hoverSlot == 0;
            switch (mode) {
               case 0:
                  if (this.itemIdInCursor == 0) {
                     this.itemIdInCursor = this.getItemId(windowId, hoverSlot);
                     this.setItemId(windowId, hoverSlot, 0);
                  } else if (hoverSlot == -999) {
                     this.itemIdInCursor = 0;
                  } else if (!isArmorOrResultSlot) {
                     int previousItem = this.getItemId(windowId, hoverSlot);
                     this.setItemId(windowId, hoverSlot, this.itemIdInCursor);
                     this.itemIdInCursor = previousItem;
                  }
               case 1:
               case 3:
               default:
                  break;
               case 2:
                  if (!isArmorOrResultSlot) {
                     short hotkeySlot = (short)(button + 36);
                     int sourceItem = this.getItemId(windowId, hoverSlot);
                     int destinationItem = this.getItemId(windowId, hotkeySlot);
                     this.setItemId(windowId, hotkeySlot, sourceItem);
                     this.setItemId(windowId, hoverSlot, destinationItem);
                  }
                  break;
               case 4:
                  int hoverItem = this.getItemId(windowId, hoverSlot);
                  if (hoverItem != 0) {
                     this.setItemId(windowId, hoverSlot, 0);
                  }
                  break;
               case 5:
                  switch (button) {
                     case 0:
                     case 4:
                        this.dragging = true;
                        break;
                     case 1:
                     case 5:
                        if (this.dragging && this.itemIdInCursor != 0 && !isArmorOrResultSlot) {
                           int previousItem = this.getItemId(windowId, hoverSlot);
                           this.setItemId(windowId, hoverSlot, this.itemIdInCursor);
                           this.itemIdInCursor = previousItem;
                        }
                        break;
                     case 2:
                     case 6:
                        this.dragging = false;
                     case 3:
                  }
            }

            entityTracker.syncShieldWithSword();
         }
      }
   }
}
