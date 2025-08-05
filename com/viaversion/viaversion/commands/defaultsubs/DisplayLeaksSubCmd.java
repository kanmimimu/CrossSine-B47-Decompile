package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayLeaksSubCmd implements ViaSubCommand {
   public String name() {
      return "displayleaks";
   }

   public String description() {
      return "Try to hunt memory leaks!";
   }

   public String usage() {
      return "displayleaks <level>";
   }

   public boolean execute(ViaCommandSender sender, String[] args) {
      if (args.length == 1) {
         try {
            ResourceLeakDetector.Level level = Level.valueOf(args[0]);
            ResourceLeakDetector.setLevel(level);
            this.sendMessage(sender, "&6Set leak detector level to &2" + level, new Object[0]);
         } catch (IllegalArgumentException var10) {
            String var7 = Arrays.toString(Level.values());
            this.sendMessage(sender, "&cInvalid level (" + var7 + ")", new Object[0]);
         }
      } else {
         ResourceLeakDetector.Level var9 = ResourceLeakDetector.getLevel();
         this.sendMessage(sender, "&6Current leak detection level is &2" + var9, new Object[0]);
      }

      return true;
   }

   public List onTabComplete(ViaCommandSender sender, String[] args) {
      return args.length == 1 ? (List)Arrays.stream(Level.values()).map(Enum::name).filter((it) -> it.startsWith(args[0])).collect(Collectors.toList()) : ViaSubCommand.super.onTabComplete(sender, args);
   }
}
