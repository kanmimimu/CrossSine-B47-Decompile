package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.rotation;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class RotationCheck extends Check {
   public RotationCheck(EntityOtherPlayerMP playerMP) {
      super(playerMP);
      this.name = "RotationInvalid";
      this.checkViolationLevel = (double)20.0F;
   }

   public void onLivingUpdate() {
      if ((Boolean)HackerDetector.INSTANCE.rotationValue.get() && (this.handlePlayer.field_70125_A > 90.0F || this.handlePlayer.field_70125_A < -90.0F)) {
         this.flag("Invalid Rotation pitch", (double)5.0F);
      }

   }
}
