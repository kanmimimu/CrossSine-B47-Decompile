package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.fireball;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0004H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0019H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/fireball/FireBallLongjump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "initTicks", "", "motionValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "sentPlace", "", "setSpeed", "speedValue", "spoofValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "strafeValue", "thrown", "ticks", "velocity", "getFBSlot", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"}
)
public final class FireBallLongjump extends LongJumpMode {
   @NotNull
   private final BoolValue spoofValue = new BoolValue("SpoofItem", true);
   @NotNull
   private final FloatValue speedValue = new FloatValue("Speed", 1.5F, 0.0F, 2.0F);
   @NotNull
   private final FloatValue motionValue = new FloatValue("Motion", 0.3F, 0.01F, 0.4F);
   @NotNull
   private final BoolValue strafeValue = new BoolValue("Strafe", false);
   private int ticks = -1;
   private boolean setSpeed;
   private boolean sentPlace;
   private int initTicks;
   private boolean velocity;
   private boolean thrown;

   public FireBallLongjump() {
      super("FireBall");
   }

   public void onEnable() {
      if (Velocity.INSTANCE.getState()) {
         Velocity.INSTANCE.setState(false);
         this.velocity = true;
      }

   }

   public void onDisable() {
      this.ticks = -1;
      this.setSpeed = false;
      this.sentPlace = false;
      this.initTicks = 0;
      if (this.velocity) {
         Velocity.INSTANCE.setState(true);
         this.velocity = false;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof S12PacketEntityVelocity) {
         if (((S12PacketEntityVelocity)packet).func_149412_c() != MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            return;
         }

         if (this.sentPlace) {
            this.ticks = 0;
            this.setSpeed = true;
            this.thrown = false;
         }
      }

   }

   public void onPreMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getFBSlot() == -1 && !this.sentPlace) {
         ClientUtils.INSTANCE.displayChatMessage("FireBall not found");
         this.getLongjump().setState(false);
      }

      switch (this.initTicks) {
         case 1:
            if (this.getFBSlot() != -1 && this.getFBSlot() != MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c) {
               SlotUtils.INSTANCE.setSlot(this.getFBSlot(), (Boolean)this.spoofValue.get(), this.getLongjump().getName());
            }

            RotationUtils.setTargetRotation(new Rotation(MovementUtils.INSTANCE.getMovingYaw() - 180.0F, 89.0F), 1);
            break;
         case 2:
            RotationUtils.setTargetRotation(new Rotation(MovementUtils.INSTANCE.getMovingYaw() - 180.0F, 89.0F), 1);
            if (!this.sentPlace) {
               MinecraftInstance.mc.func_147121_ag();
               this.sentPlace = true;
            }
            break;
         case 3:
            SlotUtils.INSTANCE.stopSet();
         case 4:
         default:
            break;
         case 5:
            if ((Boolean)this.getLongjump().getAutoDisableValue().get()) {
               this.getLongjump().setState(false);
            }
      }

      int var2 = this.initTicks++;
      if (this.ticks >= 0) {
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.motionValue.get()).floatValue();
         if ((Boolean)this.strafeValue.get()) {
            MovementUtils.INSTANCE.strafe();
         }
      }

      if (this.setSpeed) {
         if (this.ticks > 1) {
            this.setSpeed = false;
            return;
         }

         var2 = this.ticks++;
         MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
      }

   }

   private final int getFBSlot() {
      int var1 = 36;

      while(var1 < 45) {
         int i = var1++;
         ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
         if (stack != null && stack.func_77973_b() instanceof ItemFireball) {
            return i - 36;
         }
      }

      return -1;
   }
}
