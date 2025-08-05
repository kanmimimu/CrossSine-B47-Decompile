package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat.AutoBlockCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat.VelocityCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move.NoSlowCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move.ScaffoldCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.rotation.RotationCheck;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class CheckManager {
   private final PlayerData data = new PlayerData();
   private static final Class[] checksClz = new Class[]{AutoBlockCheck.class, NoSlowCheck.class, ScaffoldCheck.class, RotationCheck.class, VelocityCheck.class};
   private final LinkedList checks = new LinkedList();
   private double totalVL = (double)0.0F;
   private short addedTicks = 0;

   public CheckManager(EntityOtherPlayerMP target) {
      for(Class clz : checksClz) {
         try {
            this.checks.add((Check)clz.getConstructor(EntityOtherPlayerMP.class).newInstance(target));
         } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            ((ReflectiveOperationException)e).printStackTrace();
         }
      }

   }

   public void livingUpdate() {
      for(Check check : this.checks) {
         try {
            check.onLivingUpdate();
            if (check.wasFailed()) {
               if (HackerDetector.shouldAlert()) {
                  ClientUtils.INSTANCE.displayChatMessage("§l§7[§l§9HackDetector§l§7]§F: " + check.handlePlayer.func_145748_c_().func_150254_d() + "dectected for §C" + check.name);
               }

               this.totalVL += check.getPoint();
               if (HackerDetector.catchPlayer(check.handlePlayer.func_145748_c_().func_150254_d().toString(), check.reportName(), this.totalVL)) {
                  this.totalVL = (double)-5.0F;
               }

               this.addedTicks = 40;
               check.reset();
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      if (--this.addedTicks <= 0) {
         this.totalVL -= this.totalVL > (double)0.0F ? 0.005 : (double)0.0F;
      }

   }

   public void positionUpdate(double x, double y, double z) {
      for(Check check : this.checks) {
         try {
            check.positionUpdate(x, y, z);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }
}
