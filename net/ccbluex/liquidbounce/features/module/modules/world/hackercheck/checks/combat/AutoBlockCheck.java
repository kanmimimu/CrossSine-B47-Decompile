package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class AutoBlockCheck extends Check {
   short blockingTime = 0;
   short buffer = 0;

   public AutoBlockCheck(EntityOtherPlayerMP playerMP) {
      super(playerMP);
      this.name = "AutoBlock";
      this.checkViolationLevel = (double)2.0F;
   }

   public void onLivingUpdate() {
      if ((Boolean)HackerDetector.INSTANCE.autoBlockValue.get()) {
         if (this.handlePlayer.func_70632_aY()) {
            ++this.blockingTime;
         } else {
            this.blockingTime = 0;
         }

         if (this.blockingTime > 10 && this.handlePlayer.field_82175_bq) {
            if (++this.buffer > 5) {
               this.flag("swing when blocking " + this.blockingTime, (double)1.0F);
            }
         } else {
            this.buffer = 0;
         }
      }

   }

   public String description() {
      return "Swing when blocking";
   }
}
