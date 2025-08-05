package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class NoSlowCheck extends Check {
   short sprintBuffer = 0;
   short motionBuffer = 0;

   public NoSlowCheck(EntityOtherPlayerMP playerMP) {
      super(playerMP);
      this.name = "NoSlow";
      this.checkViolationLevel = (double)20.0F;
   }

   public void onLivingUpdate() {
      if ((Boolean)HackerDetector.INSTANCE.noSlowValue.get() && (this.handlePlayer.func_71039_bw() || this.handlePlayer.func_70632_aY())) {
         if (this.handlePlayer.func_70051_ag()) {
            if (++this.sprintBuffer > 5) {
               this.flag("Sprinting when using item or blocking", (double)5.0F);
            }

            return;
         }

         double dx = this.handlePlayer.field_70169_q - this.handlePlayer.field_70165_t;
         double dz = this.handlePlayer.field_70166_s - this.handlePlayer.field_70161_v;
         if (dx * dx + dz * dz > (double)0.0625F && ++this.motionBuffer > 10) {
            this.flag("NoSprint but keep in sprint motion when blocking", (double)5.0F);
            this.motionBuffer = 7;
            return;
         }

         this.motionBuffer -= (short)(this.motionBuffer > 0 ? 1 : 0);
         this.sprintBuffer -= (short)(this.sprintBuffer > 0 ? 1 : 0);
         if (this.sprintBuffer < 2) {
            this.reward();
         }
      }

   }

   public String description() {
      return "using item and moving suspiciously";
   }
}
