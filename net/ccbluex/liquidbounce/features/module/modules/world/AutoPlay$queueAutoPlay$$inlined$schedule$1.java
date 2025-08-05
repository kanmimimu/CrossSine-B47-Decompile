package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class AutoPlay$queueAutoPlay$$inlined$schedule$1 extends TimerTask {
   // $FF: synthetic field
   final AutoPlay this$0;
   // $FF: synthetic field
   final Function0 $runnable$inlined;

   public AutoPlay$queueAutoPlay$$inlined$schedule$1(AutoPlay var1, Function0 var2) {
      this.this$0 = var1;
      this.$runnable$inlined = var2;
   }

   public void run() {
      TimerTask $this$queueAutoPlay_u24lambda_u2d7 = this;
      int var2 = 0;
      AutoPlay.access$setQueued$p(this.this$0, false);
      if (this.this$0.getState()) {
         this.$runnable$inlined.invoke();
      }

   }
}
