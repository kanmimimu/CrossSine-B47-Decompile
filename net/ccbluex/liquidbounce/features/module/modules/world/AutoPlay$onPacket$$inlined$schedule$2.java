package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.TimerTask;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class AutoPlay$onPacket$$inlined$schedule$2 extends TimerTask {
   public void run() {
      TimerTask $this$onPacket_u24lambda_u2d3 = this;
      int var2 = 0;
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(7)));
      byte var3 = 2;
      int var4 = 0;

      while(var4 < var3) {
         ++var4;
         int var7 = 0;
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g())));
      }

   }
}
