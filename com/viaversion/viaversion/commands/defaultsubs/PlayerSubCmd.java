package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerSubCmd implements ViaSubCommand {
   public String name() {
      return "player";
   }

   public String description() {
      return "Shows connection information about one or all players.";
   }

   public String usage() {
      return "player <name|*>";
   }

   public boolean execute(ViaCommandSender sender, String[] args) {
      if (args.length == 0) {
         return false;
      } else {
         for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            ProtocolInfo info = connection.getProtocolInfo();
            if (args[0].equalsIgnoreCase(info.getUsername()) || args[0].equals("*")) {
               String var10002 = info.getUsername();
               UUID var10003 = info.getUuid();
               String var10004 = info.protocolVersion().getName();
               String var10 = info.serverProtocolVersion().getName();
               String var9 = var10004;
               UUID var8 = var10003;
               String var7 = var10002;
               this.sendMessage(sender, "&7[&6" + var7 + "&7] UUID: &2" + var8 + " &7Client-Protocol: &2" + var9 + " &7Server-Protocol: &2" + var10, new Object[0]);
            }
         }

         return true;
      }
   }

   public List onTabComplete(ViaCommandSender sender, String[] args) {
      if (args.length != 1) {
         return ViaSubCommand.super.onTabComplete(sender, args);
      } else {
         String input = args[0].toLowerCase();
         List<String> matches = new ArrayList();

         for(UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            String name = connection.getProtocolInfo().getUsername();
            if (input.isEmpty() || name.toLowerCase().startsWith(input)) {
               matches.add(name);
            }
         }

         matches.add("*");
         return matches;
      }
   }
}
