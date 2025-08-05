package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ListSubCmd implements ViaSubCommand {
   public String name() {
      return "list";
   }

   public String description() {
      return "Shows lists of the versions from logged in players.";
   }

   public String usage() {
      return "list";
   }

   public boolean execute(ViaCommandSender sender, String[] args) {
      Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap(ProtocolVersion::compareTo);

      for(UserConnection p : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolVersion version = p.getProtocolInfo().protocolVersion();
         ((Set)playerVersions.computeIfAbsent(version, (s) -> new HashSet())).add(p.getProtocolInfo().getUsername());
      }

      for(Map.Entry entry : playerVersions.entrySet()) {
         this.sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", new Object[]{((ProtocolVersion)entry.getKey()).getName(), ((Set)entry.getValue()).size(), entry.getValue()});
      }

      playerVersions.clear();
      return true;
   }
}
