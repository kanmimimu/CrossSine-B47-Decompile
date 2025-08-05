package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FireDamageListener extends ViaBukkitListener {
   public FireDamageListener(BukkitPlugin plugin) {
      super(plugin, Protocol1_12To1_11_1.class);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST,
      ignoreCancelled = true
   )
   public void onFireDamage(EntityDamageEvent event) {
      if (event.getEntityType() == EntityType.PLAYER) {
         EntityDamageEvent.DamageCause cause = event.getCause();
         if (cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK || cause == DamageCause.LAVA || cause == DamageCause.DROWNING) {
            Player player = (Player)event.getEntity();
            if (this.isOnPipe(player)) {
               player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }

         }
      }
   }
}
