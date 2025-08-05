package net.raphimc.vialoader.commands;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.UUID;

public class UserCommandSender implements ViaCommandSender {
   private final UserConnection user;

   public UserCommandSender(UserConnection user) {
      this.user = user;
   }

   public boolean hasPermission(String s) {
      return true;
   }

   public void sendMessage(String s) {
      Via.getPlatform().sendMessage(this.getUUID(), s);
   }

   public UUID getUUID() {
      return this.user.getProtocolInfo().getUuid();
   }

   public String getName() {
      return this.user.getProtocolInfo().getUsername();
   }
}
