package net.ccbluex.liquidbounce.features.special;

import io.netty.buffer.Unpooled;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.ClientSpoofModule;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ClientSpoof extends MinecraftInstance implements Listenable {
   public static final boolean enabled = true;
   public static final String LUNAR_DISPLAY_NAME = "lunarclient:v2.14.5-2411";

   @EventTarget
   public void onPacket(PacketEvent event) {
      if (((ClientSpoofModule)CrossSine.moduleManager.getModule(ClientSpoofModule.class)).getState()) {
         Packet<?> packet = event.getPacket();
         ClientSpoofModule clientSpoof = (ClientSpoofModule)CrossSine.moduleManager.getModule(ClientSpoofModule.class);
         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("Vanilla")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("vanilla");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("LabyMod")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("LMC");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("CheatBreaker")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("CB");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("Lunar")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("lunarclient:v2.5.1-2316");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("PvPLounge")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("PLC18");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("Forge")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a("FML");
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!Minecraft.func_71410_x().func_71387_A() && ((String)clientSpoof.modeValue.get()).equals("Custom")) {
            try {
               if (packet instanceof C17PacketCustomPayload) {
                  C17PacketCustomPayload customPayload = (C17PacketCustomPayload)packet;
                  customPayload.field_149561_c = (new PacketBuffer(Unpooled.buffer())).func_180714_a((String)clientSpoof.CustomClient.get());
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }

   }

   public static String handleClientBrand() {
      ClientSpoofModule clientSpoof = (ClientSpoofModule)CrossSine.moduleManager.getModule(ClientSpoofModule.class);
      boolean enabled = clientSpoof != null && clientSpoof.getState();
      if (!enabled) {
         return "vanilla";
      } else {
         switch (((String)clientSpoof.modeValue.get()).toLowerCase()) {
            case "lunar":
               return "lunarclient:v2.14.5-2411";
            default:
               return "vanilla";
         }
      }
   }

   public boolean handleEvents() {
      return true;
   }
}
