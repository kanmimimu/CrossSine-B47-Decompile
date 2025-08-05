package net.ccbluex.liquidbounce.protocol.api;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class McUpdatesHandler extends MinecraftInstance implements Listenable {
   public static boolean isSwimmingOrCrawling = false;
   public static boolean doingEyeRot = false;
   public static float eyeHeight;
   public static float lastEyeHeight;

   private static boolean underWater() {
      World world = mc.field_71439_g.func_130014_f_();
      double eyeBlock = mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e() - (double)0.25F;
      BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, eyeBlock, mc.field_71439_g.field_70161_v);
      return world.func_180495_p(blockPos).func_177230_c().func_149688_o() == Material.field_151586_h && !(mc.field_71439_g.field_70154_o instanceof EntityBoat);
   }

   private static boolean isSwimming() {
      return !mc.field_71439_g.field_70145_X && mc.field_71439_g.func_70090_H() && mc.field_71439_g.func_70051_ag();
   }

   public static boolean shouldAnimation() {
      AxisAlignedBB box = mc.field_71439_g.func_174813_aQ();
      AxisAlignedBB crawl = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + (double)1.5F, box.field_72339_c + 0.6);
      return !mc.field_71439_g.field_70145_X && (isSwimmingOrCrawling && mc.field_71439_g.func_70051_ag() && mc.field_71439_g.func_70090_H() || isSwimmingOrCrawling && !mc.field_71441_e.func_147461_a(crawl).isEmpty());
   }

   @EventTarget
   public void onPushOut(PushOutEvent event) {
      if (ProtocolFixer.newerThanOrEqualsTo1_13() && (shouldAnimation() || mc.field_71439_g.func_70093_af())) {
         event.cancelEvent();
      }

   }

   @EventTarget
   public void onMotion(MotionEvent event) {
      if (ProtocolFixer.newerThanOrEqualsTo1_13()) {
         float START_HEIGHT = 1.62F;
         lastEyeHeight = eyeHeight;
         float END_HEIGHT = 0.45F;
         float delta = 0.085F;
         if (shouldAnimation()) {
            eyeHeight = AnimationUtils.animate(END_HEIGHT, eyeHeight, 4.0F * delta);
            doingEyeRot = true;
         } else if (eyeHeight < START_HEIGHT) {
            eyeHeight = AnimationUtils.animate(START_HEIGHT, eyeHeight, 4.0F * delta);
         }

         if (eyeHeight >= START_HEIGHT && doingEyeRot) {
            doingEyeRot = false;
         }
      }

   }

   @EventTarget
   public void onUpdate(UpdateEvent event) {
      if (ProtocolFixer.newerThanOrEqualsTo1_13() && isSwimming()) {
         if (mc.field_71439_g.field_70159_w < -0.4) {
            mc.field_71439_g.field_70159_w = (double)-0.39F;
         }

         if (mc.field_71439_g.field_70159_w > 0.4) {
            mc.field_71439_g.field_70159_w = (double)0.39F;
         }

         if (mc.field_71439_g.field_70181_x < -0.4) {
            mc.field_71439_g.field_70181_x = (double)-0.39F;
         }

         if (mc.field_71439_g.field_70181_x > 0.4) {
            mc.field_71439_g.field_70181_x = (double)0.39F;
         }

         if (mc.field_71439_g.field_70179_y < -0.4) {
            mc.field_71439_g.field_70179_y = (double)-0.39F;
         }

         if (mc.field_71439_g.field_70179_y > 0.4) {
            mc.field_71439_g.field_70179_y = (double)0.39F;
         }

         double d3 = mc.field_71439_g.func_70040_Z().field_72448_b;
         double d4 = 0.025;
         if (d3 <= (double)0.0F || mc.field_71439_g.field_70170_p.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)1.0F - 0.64, mc.field_71439_g.field_70161_v)).func_177230_c().func_149688_o() == Material.field_151586_h) {
            EntityPlayerSP var10000 = mc.field_71439_g;
            var10000.field_70181_x += (d3 - mc.field_71439_g.field_70181_x) * d4;
         }

         EntityPlayerSP var14 = mc.field_71439_g;
         var14.field_70181_x += 0.018;
         if (shouldAnimation()) {
            var14 = mc.field_71439_g;
            var14.field_70159_w *= (double)1.09F;
            var14 = mc.field_71439_g;
            var14.field_70179_y *= (double)1.09F;
         }
      }

      float sneakLength;
      if (ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2()) {
         sneakLength = 1.65F;
      } else if (ProtocolFixer.newerThanOrEqualsTo1_14()) {
         sneakLength = 1.5F;
      } else {
         sneakLength = 1.8F;
      }

      double d0 = (double)mc.field_71439_g.field_70130_N / (double)2.0F;
      AxisAlignedBB box = mc.field_71439_g.func_174813_aQ();
      AxisAlignedBB setThrough = new AxisAlignedBB(mc.field_71439_g.field_70165_t - d0, box.field_72338_b, mc.field_71439_g.field_70161_v - d0, mc.field_71439_g.field_70165_t + d0, box.field_72338_b + (double)mc.field_71439_g.field_70131_O, mc.field_71439_g.field_70161_v + d0);
      AxisAlignedBB sneak = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + 1.8, box.field_72339_c + 0.6);
      AxisAlignedBB crawl = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + (double)1.5F, box.field_72339_c + 0.6);
      float newHeight;
      float newWidth;
      if (ProtocolFixer.newerThanOrEqualsTo1_13() && isSwimmingOrCrawling && underWater() && (double)mc.field_71439_g.field_70125_A >= (double)0.0F) {
         newHeight = 0.6F;
         newWidth = 0.6F;
         isSwimmingOrCrawling = true;
         mc.field_71439_g.func_174826_a(setThrough);
      } else if (!ProtocolFixer.newerThanOrEqualsTo1_13() || (!isSwimming() || !underWater()) && mc.field_71441_e.func_147461_a(crawl).isEmpty()) {
         if (mc.field_71439_g.func_70093_af() && !underWater()) {
            newHeight = sneakLength;
            newWidth = 0.6F;
            mc.field_71439_g.func_174826_a(setThrough);
         } else {
            if (isSwimmingOrCrawling) {
               isSwimmingOrCrawling = false;
            }

            newHeight = 1.8F;
            newWidth = 0.6F;
            mc.field_71439_g.func_174826_a(setThrough);
         }
      } else {
         newHeight = 0.6F;
         newWidth = 0.6F;
         isSwimmingOrCrawling = true;
         mc.field_71439_g.func_174826_a(setThrough);
      }

      if (ProtocolFixer.newerThanOrEqualsTo1_9() && mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70093_af() && !underWater() && (mc.field_71439_g.field_70131_O == sneakLength || mc.field_71439_g.field_70131_O == 0.6F) && !mc.field_71441_e.func_147461_a(sneak).isEmpty()) {
         mc.field_71474_y.field_74311_E.field_74513_e = true;
      }

      try {
         mc.field_71439_g.field_70131_O = newHeight;
         mc.field_71439_g.field_70130_N = newWidth;
      } catch (IllegalArgumentException var12) {
      }

   }

   public boolean handleEvents() {
      return true;
   }
}
