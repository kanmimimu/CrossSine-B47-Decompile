package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import java.util.TimerTask;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class PhaseNofall$onNoFall$$inlined$schedule$1 extends TimerTask {
   // $FF: synthetic field
   final BlockPos $fallPos$inlined;

   public PhaseNofall$onNoFall$$inlined$schedule$1(BlockPos var1) {
      this.$fallPos$inlined = var1;
   }

   public void run() {
      TimerTask $this$onNoFall_u24lambda_u2d0 = this;
      int var2 = 0;
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition((double)this.$fallPos$inlined.func_177958_n(), (double)this.$fallPos$inlined.func_177956_o(), (double)this.$fallPos$inlined.func_177952_p(), true)));
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }
}
