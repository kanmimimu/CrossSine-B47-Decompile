package net.ccbluex.liquidbounce.utils.render;

public class AnimationUtil {
   public static float fastmax(float a, float b) {
      return a >= b ? a : b;
   }

   public static float fastmin(float a, float b) {
      return a <= b ? a : b;
   }

   public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
      float movement = (end - current) * smoothSpeed;
      if (movement > 0.0F) {
         movement = fastmax(minSpeed, movement);
         movement = fastmin(end - current, movement);
      } else if (movement < 0.0F) {
         movement = fastmin(-minSpeed, movement);
         movement = fastmax(end - current, movement);
      }

      return current + movement;
   }

   public static double getAnimationState(double animation, double finalState, double speed) {
      float add = (float)(0.01 * speed);
      animation = animation < finalState ? Math.min(animation + (double)add, finalState) : Math.max(animation - (double)add, finalState);
      return animation;
   }

   public static float getAnimationState(float animation, float finalState, float speed) {
      float add = (float)(0.01 * (double)speed);
      animation = animation < finalState ? Math.min(animation + add, finalState) : Math.max(animation - add, finalState);
      return animation;
   }

   public static double animate(double target, double current, double speed) {
      boolean larger = target > current;
      if (speed < (double)0.0F) {
         speed = (double)0.0F;
      } else if (speed > (double)1.0F) {
         speed = (double)1.0F;
      }

      if (target == current) {
         return target;
      } else {
         double dif = Math.max(target, current) - Math.min(target, current);
         double factor = Math.max(dif * speed, (double)1.0F);
         if (factor < 0.1) {
            factor = 0.1;
         }

         if (larger) {
            if (current + factor > target) {
               current = target;
            } else {
               current += factor;
            }
         } else if (current - factor < target) {
            current = target;
         } else {
            current -= factor;
         }

         return current;
      }
   }
}
