package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.CrossSine;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

public final class ServerUtils extends MinecraftInstance {
   public static ServerData serverData;

   public static void connectToLastServer() {
      if (serverData != null) {
         mc.func_147108_a(new GuiConnecting(new GuiMultiplayer(CrossSine.mainMenu), mc, serverData));
      }
   }

   public static String getRemoteIp() {
      String serverIp = "MainMenu";
      if (mc.func_71387_A()) {
         serverIp = "SinglePlayer";
      } else if (mc.field_71441_e != null && mc.field_71441_e.field_72995_K) {
         ServerData serverData = mc.func_147104_D();
         if (serverData != null) {
            serverIp = serverData.field_78845_b;
         }
      }

      return serverIp;
   }
}
