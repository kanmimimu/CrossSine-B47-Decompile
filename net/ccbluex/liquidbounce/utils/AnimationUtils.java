package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;

public final class AnimationUtils {
   public static double animate(double target, double current, double speed) {
      if (current == target) {
         return current;
      } else {
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

         if (larger) {
            current += factor;
            if (current >= target) {
               current = target;
            }
         } else if (target < current) {
            current -= factor;
            if (current <= target) {
               current = target;
            }
         }

         return current;
      }
   }

   public static float easeOut(float t, float d) {
      float var2;
      return (var2 = t / d - 1.0F) * var2 * var2 + 1.0F;
   }

   public static float animate(float target, float current, float speed) {
      if (current == target) {
         return current;
      } else {
         boolean larger = target > current;
         if (speed < 0.0F) {
            speed = 0.0F;
         } else if (speed > 1.0F) {
            speed = 1.0F;
         }

         double dif = Math.max((double)target, (double)current) - Math.min((double)target, (double)current);
         double factor = dif * (double)speed;
         if (factor < 0.1) {
            factor = 0.1;
         }

         if (larger) {
            current += (float)factor;
            if (current >= target) {
               current = target;
            }
         } else if (target < current) {
            current -= (float)factor;
            if (current <= target) {
               current = target;
            }
         }

         return current;
      }
   }

   public static float lstransition(float now, float desired, double speed) {
      double dif = (double)Math.abs(desired - now);
      float a = (float)Math.abs((double)(desired - (desired - Math.abs(desired - now))) / ((double)100.0F - speed * (double)10.0F));
      float x = now;
      if (dif > (double)0.0F) {
         if (now < desired) {
            x = now + a * (float)RenderUtils.deltaTime;
         } else if (now > desired) {
            x = now - a * (float)RenderUtils.deltaTime;
         }
      } else {
         x = desired;
      }

      if ((double)Math.abs(desired - x) < 0.01 && x != desired) {
         x = desired;
      }

      return x;
   }

   public static double changer(double current, double add, double min, double max) {
      current += add;
      if (current > max) {
         current = max;
      }

      if (current < min) {
         current = min;
      }

      return current;
   }

   public static float changer(float current, float add, float min, float max) {
      current += add;
      if (current > max) {
         current = max;
      }

      if (current < min) {
         current = min;
      }

      return current;
   }
}
