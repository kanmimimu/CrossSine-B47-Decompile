package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.Objects;
import java.util.UUID;

public final class VelocityCommandSender implements ViaCommandSender {
   private final CommandSource source;

   public VelocityCommandSender(CommandSource source) {
      this.source = source;
   }

   public boolean hasPermission(String permission) {
      return this.source.hasPermission(permission);
   }

   public void sendMessage(String msg) {
      this.source.sendMessage(VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
   }

   public UUID getUUID() {
      CommandSource var2 = this.source;
      if (var2 instanceof Player) {
         Player player = (Player)var2;
         return player.getUniqueId();
      } else {
         return new UUID(0L, 0L);
      }
   }

   public String getName() {
      CommandSource var2 = this.source;
      if (var2 instanceof Player) {
         Player player = (Player)var2;
         return player.getUsername();
      } else {
         return "?";
      }
   }

   public CommandSource source() {
      return this.source;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof VelocityCommandSender)) {
         return false;
      } else {
         VelocityCommandSender var2 = (VelocityCommandSender)var1;
         return Objects.equals(this.source, var2.source);
      }
   }

   public int hashCode() {
      return 0 * 31 + Objects.hashCode(this.source);
   }

   public String toString() {
      return String.format("%s[source=%s]", this.getClass().getSimpleName(), Objects.toString(this.source));
   }
}
