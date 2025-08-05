package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck;

import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Check extends MinecraftInstance {
   public static boolean debug = false;
   public static PlayerData data = new PlayerData();
   protected EntityOtherPlayerMP handlePlayer = null;
   protected String name = "NONE";
   protected boolean enabled = true;
   protected double violationLevel = (double)0.0F;
   protected double checkViolationLevel = (double)20.0F;
   protected double vlStep = (double)5.0F;

   public Check(EntityOtherPlayerMP playerMP) {
      this.handlePlayer = playerMP;
   }

   public void positionUpdate(double x, double y, double z) {
   }

   public void onLivingUpdate() {
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void flag(String verbose, double vl) {
      this.violationLevel += vl;
      if (debug) {
         ClientUtils.INSTANCE.displayChatMessage(String.format("§l§7[§l§9HackDetector§l§7]§r §c%s§3 failed§2 %s §r§7(x§4%s§7) %s", this.handlePlayer.func_70005_c_(), this.name, (int)this.violationLevel, verbose));
      }

   }

   public void reward() {
      this.reward(0.1);
   }

   public void reward(double rewardVL) {
      this.violationLevel = Math.max((double)0.0F, this.violationLevel - rewardVL);
   }

   public void shrinkVL(double t) {
      this.violationLevel *= t;
   }

   public boolean wasFailed() {
      return this.violationLevel > this.checkViolationLevel;
   }

   public String description() {
      return "cheating";
   }

   public void reset() {
      this.violationLevel = (double)0.0F;
   }

   public double getPoint() {
      return (double)5.0F;
   }

   public String reportName() {
      return this.name;
   }
}
