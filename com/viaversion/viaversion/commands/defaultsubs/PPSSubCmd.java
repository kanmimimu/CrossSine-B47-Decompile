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

public class PPSSubCmd implements ViaSubCommand {
   public String name() {
      return "pps";
   }

   public String description() {
      return "Shows the packets per second of online players.";
   }

   public String usage() {
      return "pps";
   }

   public boolean execute(ViaCommandSender sender, String[] args) {
      Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap(ProtocolVersion::compareTo);
      int totalPackets = 0;
      int clients = 0;
      long max = 0L;

      for(UserConnection p : Via.getManager().getConnectionManager().getConnections()) {
         ProtocolVersion playerVersion = p.getProtocolInfo().protocolVersion();
         if (!playerVersions.containsKey(playerVersion)) {
            playerVersions.put(playerVersion, new HashSet());
         }

         if (p.getPacketTracker().getPacketsPerSecond() > -1L) {
            Set var10000 = (Set)playerVersions.get(playerVersion);
            String var10001 = p.getProtocolInfo().getUsername();
            long var13 = p.getPacketTracker().getPacketsPerSecond();
            String var12 = var10001;
            var10000.add(var12 + " (" + var13 + " PPS)");
            totalPackets = (int)((long)totalPackets + p.getPacketTracker().getPacketsPerSecond());
            if (p.getPacketTracker().getPacketsPerSecond() > max) {
               max = p.getPacketTracker().getPacketsPerSecond();
            }

            ++clients;
         }
      }

      this.sendMessage(sender, "&4Live Packets Per Second", new Object[0]);
      if (clients > 1) {
         int var15 = totalPackets / clients;
         this.sendMessage(sender, "&cAverage: &f" + var15, new Object[0]);
         this.sendMessage(sender, "&cHighest: &f" + max, new Object[0]);
      }

      if (clients == 0) {
         this.sendMessage(sender, "&cNo clients to display.", new Object[0]);
      }

      for(Map.Entry entry : playerVersions.entrySet()) {
         this.sendMessage(sender, "&8[&6%s&8]: &b%s", new Object[]{((ProtocolVersion)entry.getKey()).getName(), entry.getValue()});
      }

      playerVersions.clear();
      return true;
   }
}
