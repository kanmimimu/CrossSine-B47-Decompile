package com.viaversion.viaversion.velocity.command.subs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;

public class ProbeSubCmd implements ViaSubCommand {
   public String name() {
      return "probe";
   }

   public String description() {
      String var3 = ((VelocityViaConfig)Via.getConfig()).getVelocityPingInterval() == -1 ? "" : "(Also happens at an interval)";
      return "Forces ViaVersion to scan server protocol versions " + var3;
   }

   public boolean execute(ViaCommandSender sender, String[] args) {
      Via.proxyPlatform().protocolDetectorService().probeAllServers();
      this.sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
      return true;
   }
}
