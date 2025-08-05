package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.TimerTask;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class AutoPlay$onPacket$$inlined$schedule$1 extends TimerTask {
   // $FF: synthetic field
   final AutoPlay this$0;
   // $FF: synthetic field
   final int $windowId$inlined;
   // $FF: synthetic field
   final int $slot$inlined;
   // $FF: synthetic field
   final ItemStack $item$inlined;

   public AutoPlay$onPacket$$inlined$schedule$1(AutoPlay var1, int var2, int var3, ItemStack var4) {
      this.this$0 = var1;
      this.$windowId$inlined = var2;
      this.$slot$inlined = var3;
      this.$item$inlined = var4;
   }

   public void run() {
      TimerTask $this$onPacket_u24lambda_u2d0 = this;
      int var2 = 0;
      AutoPlay.access$setClicking$p(this.this$0, false);
      AutoPlay.access$setClickState$p(this.this$0, 0);
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0EPacketClickWindow(this.$windowId$inlined, this.$slot$inlined, 0, 0, this.$item$inlined, (short)1919)));
   }
}
