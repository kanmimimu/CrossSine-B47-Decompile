package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.BoolValue;

public class AnimationHelper {
   public float animationX;
   public int alpha;

   public int getAlpha() {
      return this.alpha;
   }

   public float getAnimationX() {
      return this.animationX;
   }

   public void resetAlpha() {
      this.alpha = 0;
   }

   public AnimationHelper() {
      this.alpha = 0;
   }

   public void updateAlpha(int speed) {
      if (this.alpha < 255) {
         this.alpha += speed;
      }

   }

   public AnimationHelper(BoolValue value) {
      this.animationX = (Boolean)value.get() ? 5.0F : -5.0F;
   }

   public AnimationHelper(Module module) {
      this.animationX = module.getState() ? 5.0F : -5.0F;
   }

   public static double animate(double target, double current, double speed) {
      boolean larger = target > current;
      if (speed < (double)0.0F) {
         speed = (double)0.0F;
      } else if (speed > (double)1.0F) {
         speed = (double)1.0F;
      }

      double dif = Math.max(target, current) - Math.min(target, current);
      double factor = dif * speed;
      if (factor < 0.1) {
         factor = 0.1;
      }

      current = larger ? current + factor : current - factor;
      return current;
   }
}
