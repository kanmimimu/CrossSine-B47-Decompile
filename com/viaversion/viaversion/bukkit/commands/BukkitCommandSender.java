package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public final class BukkitCommandSender implements ViaCommandSender {
   private final CommandSender sender;

   public BukkitCommandSender(CommandSender sender) {
      this.sender = sender;
   }

   public boolean hasPermission(String permission) {
      return this.sender.hasPermission(permission);
   }

   public void sendMessage(String msg) {
      this.sender.sendMessage(msg);
   }

   public UUID getUUID() {
      CommandSender var2 = this.sender;
      if (var2 instanceof Entity) {
         Entity entity = (Entity)var2;
         return entity.getUniqueId();
      } else {
         return new UUID(0L, 0L);
      }
   }

   public String getName() {
      return this.sender.getName();
   }

   public CommandSender sender() {
      return this.sender;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof BukkitCommandSender)) {
         return false;
      } else {
         BukkitCommandSender var2 = (BukkitCommandSender)var1;
         return Objects.equals(this.sender, var2.sender);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.sender);
   }

   public String toString() {
      return String.format("%s[sender=%s]", this.getClass().getSimpleName(), Objects.toString(this.sender));
   }
}
