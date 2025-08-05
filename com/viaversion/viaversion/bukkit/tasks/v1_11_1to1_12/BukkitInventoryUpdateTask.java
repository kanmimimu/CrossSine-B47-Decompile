package com.viaversion.viaversion.bukkit.tasks.v1_11_1to1_12;

import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.storage.ItemTransactionStorage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitInventoryUpdateTask implements Runnable {
   private final BukkitInventoryQuickMoveProvider provider;
   private final UUID uuid;
   private final List items;

   public BukkitInventoryUpdateTask(BukkitInventoryQuickMoveProvider provider, UUID uuid) {
      this.provider = provider;
      this.uuid = uuid;
      this.items = Collections.synchronizedList(new ArrayList());
   }

   public void addItem(short windowId, short slotId, short actionId) {
      ItemTransactionStorage storage = new ItemTransactionStorage(windowId, slotId, actionId);
      this.items.add(storage);
   }

   public void run() {
      Player p = Bukkit.getServer().getPlayer(this.uuid);
      if (p == null) {
         this.provider.onTaskExecuted(this.uuid);
      } else {
         try {
            synchronized(this.items) {
               for(ItemTransactionStorage storage : this.items) {
                  Object packet = this.provider.buildWindowClickPacket(p, storage);
                  boolean result = this.provider.sendPacketToServer(p, packet);
                  if (!result) {
                     break;
                  }
               }

               this.items.clear();
            }
         } finally {
            this.provider.onTaskExecuted(this.uuid);
         }

      }
   }
}
