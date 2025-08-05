package net.ccbluex.liquidbounce.features.module.modules.world.disablers.server;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.impl.DynamicIsland;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/server/WatchDogDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "done", "", "dotCount", "", "flagged", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class WatchDogDisabler extends DisablerMode {
   private int flagged;
   private boolean done;
   @NotNull
   private Animation animation;
   private int dotCount;
   @NotNull
   private TimerMS timer;

   public WatchDogDisabler() {
      super("WatchDog");
      this.animation = new Animation(Easing.LINEAR, 250L);
      this.timer = new TimerMS();
   }

   public void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.isPre()) {
         if (this.done || MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.field_70173_aa < 20) {
            return;
         }

         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            }
         } else if (PlayerUtils.getOffGroundTicks() >= 9) {
            if (PlayerUtils.getOffGroundTicks() % 2 == 0) {
               event.setZ(event.getZ() + (double)RandomUtils.INSTANCE.nextFloat(0.09F, 0.12F));
            }

            MovementUtils.INSTANCE.resetMotion(true);
         }
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof S08PacketPlayerPosLook && !this.done) {
         int var2 = this.flagged++;
         if (this.flagged == 20) {
            this.done = true;
            this.flagged = 0;
            DynamicIsland.INSTANCE.setDisabler((Float)null);
            this.done = true;
         }
      }

   }

   public void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.done = false;
      this.flagged = 0;
      this.animation.value = (double)0.0F;
   }

   public void onDisable() {
      this.done = false;
   }

   public void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.animation.run((double)this.flagged);
      if (!this.done) {
         DynamicIsland.INSTANCE.setDisabler((float)this.animation.value / 20.0F);
      }

      if (this.timer.hasTimePassed(2500L)) {
         int var2 = this.dotCount++;
      }

      if (this.dotCount >= 4 && this.timer.hasTimePassed(2500L)) {
         this.dotCount = 0;
      }

   }

   public void onEnable() {
      this.done = false;
      this.flagged = 0;
   }
}
