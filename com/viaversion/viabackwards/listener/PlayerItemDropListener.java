package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemDropListener extends ViaBukkitListener {
   public PlayerItemDropListener(BukkitPlugin plugin) {
      super(plugin, Protocol1_13_1To1_13.class);
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onItemDrop(PlayerDropItemEvent event) {
      Player player = event.getPlayer();
      if (this.isOnPipe(player)) {
         int slot = player.getInventory().getHeldItemSlot();
         ItemStack item = player.getInventory().getItem(slot);
         player.getInventory().setItem(slot, item);
      }
   }
}
