package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ReachCheck extends Check {
   int swingBuffer;

   public ReachCheck(EntityOtherPlayerMP playerMP) {
      super(playerMP);
      this.name = "Reach";
      this.checkViolationLevel = (double)5.0F;
   }

   public void onLivingUpdate() {
      if ((Boolean)HackerDetector.INSTANCE.reachValue.get()) {
         EntityPlayer target = null;

         for(Entity entity : mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityPlayer) {
               float yaw = getRotationsForEntity(this.handlePlayer, (EntityPlayer)entity)[0];
               if (this.handlePlayer.field_70177_z >= yaw - 20.0F && this.handlePlayer.field_70177_z <= yaw + 20.0F) {
                  target = (EntityPlayer)entity;
               }
            }
         }

         if (target != null) {
            if (this.handlePlayer.field_82175_bq) {
               ++this.swingBuffer;
            }

            if (this.swingBuffer >= 10 && target.field_70172_ad > 8 && this.handlePlayer.func_70068_e(target) >= (double)3.5F) {
               this.flag("Attack other entity for long distance", (double)1.0F);
            }
         }
      }

   }

   public static float[] getRotationsForEntity(EntityPlayer player, EntityPlayer target) {
      if (player != null && target != null) {
         double diffX = target.field_70165_t - player.field_70165_t;
         double diffY = target.field_70163_u + (double)target.func_70047_e() * 0.9 - (player.field_70163_u + (double)player.func_70047_e());
         double diffZ = target.field_70161_v - player.field_70161_v;
         double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
         float yaw = (float)(Math.atan2(diffZ, diffX) * (double)180.0F / Math.PI - (double)90.0F);
         float pitch = (float)(-(Math.atan2(diffY, dist) * (double)180.0F / Math.PI)) + 11.0F;
         return new float[]{player.field_70177_z + MathHelper.func_76142_g(yaw - player.field_70177_z), player.field_70125_A + MathHelper.func_76142_g(pitch - player.field_70125_A)};
      } else {
         return new float[]{player.field_70177_z, player.field_70125_A};
      }
   }
}
