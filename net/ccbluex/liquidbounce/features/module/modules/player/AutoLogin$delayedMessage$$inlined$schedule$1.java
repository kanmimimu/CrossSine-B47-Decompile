package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.TimerTask;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class AutoLogin$delayedMessage$$inlined$schedule$1 extends TimerTask {
   // $FF: synthetic field
   final AutoLogin this$0;
   // $FF: synthetic field
   final String $message$inlined;

   public AutoLogin$delayedMessage$$inlined$schedule$1(AutoLogin var1, String var2) {
      this.this$0 = var1;
      this.$message$inlined = var2;
   }

   public void run() {
      TimerTask $this$delayedMessage_u24lambda_u2d0 = this;
      int var2 = 0;
      if (this.this$0.getState() && MinecraftInstance.mc.field_71439_g != null) {
         MinecraftInstance.mc.field_71439_g.func_71165_d(this.$message$inlined);
      }

   }
}
