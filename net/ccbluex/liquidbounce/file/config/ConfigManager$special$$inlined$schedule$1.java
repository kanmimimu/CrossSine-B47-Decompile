package net.ccbluex.liquidbounce.file.config;

import java.util.TimerTask;
import kotlin.Metadata;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"}
)
public final class ConfigManager$special$$inlined$schedule$1 extends TimerTask {
   // $FF: synthetic field
   final ConfigManager this$0;

   public ConfigManager$special$$inlined$schedule$1(ConfigManager var1) {
      this.this$0 = var1;
   }

   public void run() {
      TimerTask $this$_init__u24lambda_u2d0 = this;
      int var2 = 0;
      ConfigManager.access$saveTicker(this.this$0);
   }
}
