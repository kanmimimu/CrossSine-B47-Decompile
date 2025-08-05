package net.ccbluex.liquidbounce.protocol.api;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class PacketManager extends MinecraftInstance implements Listenable {
   public static float eyeHeight;
   public static float lastEyeHeight;

   @EventTarget
   public void onMotion(MotionEvent event) {
      mc.field_71429_W = 0;
      float START_HEIGHT = 1.62F;
      lastEyeHeight = eyeHeight;
      float END_HEIGHT;
      if (ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2()) {
         END_HEIGHT = 1.47F;
      } else if (ProtocolFixer.newerThanOrEqualsTo1_14()) {
         END_HEIGHT = 1.32F;
      } else {
         END_HEIGHT = 1.54F;
      }

      float delta;
      if (ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2()) {
         delta = 0.147F;
      } else if (ProtocolFixer.newerThanOrEqualsTo1_14()) {
         delta = 0.132F;
      } else {
         delta = 0.154F;
      }

      if (mc.field_71439_g.func_70093_af()) {
         eyeHeight = AnimationUtils.animate(END_HEIGHT, eyeHeight, 4.0F * delta);
      } else if (eyeHeight < START_HEIGHT) {
         eyeHeight = AnimationUtils.animate(START_HEIGHT, eyeHeight, 4.0F * delta);
      }

   }

   public boolean handleEvents() {
      return true;
   }
}
