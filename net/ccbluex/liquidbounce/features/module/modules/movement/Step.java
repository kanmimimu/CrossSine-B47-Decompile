package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Phase;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Step",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020\u0004H\u0002J\b\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020%H\u0016J\u0010\u0010'\u001a\u00020%2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020%2\u0006\u0010(\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020%2\u0006\u0010(\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020%2\u0006\u0010(\u001a\u00020/H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canStep", "", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "isAACStep", "isStep", "jumpHeightValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "lastOnGround", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "ncpNextStep", "", "spartanSwitch", "stepX", "", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerDynValue", "timerValue", "wasTimer", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class Step extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final FloatValue heightValue;
   @NotNull
   private final Value jumpHeightValue;
   @NotNull
   private final IntegerValue delayValue;
   @NotNull
   private final Value timerValue;
   @NotNull
   private final Value timerDynValue;
   private boolean isStep;
   private double stepX;
   private double stepY;
   private double stepZ;
   private int ncpNextStep;
   private boolean spartanSwitch;
   private boolean isAACStep;
   private boolean wasTimer;
   private boolean lastOnGround;
   private boolean canStep;
   @NotNull
   private final MSTimer timer;

   public Step() {
      String[] var1 = new String[]{"Vanilla", "Jump", "Matrix6.7.0", "NCP", "NCPNew", "MotionNCP", "MotionNCP2", "OldNCP", "OldAAC", "LAAC", "AAC3.3.4", "AAC3.6.4", "AAC4.4.0", "Spartan", "Rewinside", "Vulcan", "Verus", "BlocksMC"};
      this.modeValue = new ListValue("Mode", var1, "NCP");
      this.heightValue = new FloatValue("Height", 1.0F, 0.6F, 10.0F);
      this.jumpHeightValue = (new FloatValue("JumpMotion", 0.42F, 0.37F, 0.42F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Step.this.getModeValue().equals("Jump") || Step.this.getModeValue().equals("TimerJump");
         }
      });
      this.delayValue = new IntegerValue("Delay", 0, 0, 500);
      this.timerValue = (new FloatValue("Timer", 1.0F, 0.05F, 1.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !Step.this.getModeValue().equals("Matrix6.7.0") && !Step.this.getModeValue().equals("Verus");
         }
      });
      this.timerDynValue = (new BoolValue("UseDynamicTimer", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !Step.this.getModeValue().equals("Matrix6.7.0") && !Step.this.getModeValue().equals("Verus");
         }
      });
      this.timer = new MSTimer();
   }

   @NotNull
   public final ListValue getModeValue() {
      return this.modeValue;
   }

   public void onDisable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6F;
         if (this.wasTimer) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         }

         this.wasTimer = false;
         this.lastOnGround = MinecraftInstance.mc.field_71439_g.field_70122_E;
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E && this.lastOnGround) {
         this.canStep = true;
         if (this.modeValue.equals("AAC4.4.0") || this.modeValue.equals("NCPNew") || this.modeValue.equals("Matrix6.7.0")) {
            MinecraftInstance.mc.field_71439_g.field_70138_W = ((Number)this.heightValue.get()).floatValue();
         }
      } else {
         this.canStep = false;
         MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6F;
      }

      this.lastOnGround = MinecraftInstance.mc.field_71439_g.field_70122_E;
      if (this.wasTimer) {
         this.wasTimer = false;
         if (this.modeValue.equals("AAC4.4.0")) {
            EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
            var2.field_70159_w *= 0.913;
            var2 = MinecraftInstance.mc.field_71439_g;
            var2.field_70179_y *= 0.913;
         }

         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      }

      String mode = (String)this.modeValue.get();
      if (StringsKt.equals(mode, "jump", true) && MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
         this.fakeJump();
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.jumpHeightValue.get()).floatValue();
      } else if (StringsKt.equals(mode, "timerjump", true)) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
               this.fakeJump();
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.jumpHeightValue.get()).floatValue();
               this.isStep = true;
            } else if (this.isStep) {
               MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F ? (float)((double)1 - MinecraftInstance.mc.field_71439_g.field_70181_x / 1.8) : 1.25F;
            }
         } else {
            this.isStep = false;
         }
      } else if (StringsKt.equals(mode, "laac", true)) {
         if (MinecraftInstance.mc.field_71439_g.field_70123_F && !MinecraftInstance.mc.field_71439_g.func_70617_f_() && !MinecraftInstance.mc.field_71439_g.func_70090_H() && !MinecraftInstance.mc.field_71439_g.func_180799_ab() && !MinecraftInstance.mc.field_71439_g.field_70134_J) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.timer.hasTimePassed((long)((Number)this.delayValue.get()).intValue())) {
               this.isStep = true;
               this.fakeJump();
               EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
               var3.field_70181_x += 0.620000001490116;
               float f = MinecraftInstance.mc.field_71439_g.field_70177_z * ((float)Math.PI / 180F);
               EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
               var4.field_70159_w -= (double)MathHelper.func_76126_a(f) * 0.2;
               var4 = MinecraftInstance.mc.field_71439_g;
               var4.field_70179_y += (double)MathHelper.func_76134_b(f) * 0.2;
               this.timer.reset();
            }

            MinecraftInstance.mc.field_71439_g.field_70122_E = true;
         } else {
            this.isStep = false;
         }
      } else if (StringsKt.equals(mode, "aac3.6.4", true)) {
         if (MinecraftInstance.mc.field_71439_g.field_70123_F && MovementUtils.INSTANCE.isMoving()) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
               EntityPlayerSP var8 = MinecraftInstance.mc.field_71439_g;
               var8.field_70159_w *= 1.12;
               var8 = MinecraftInstance.mc.field_71439_g;
               var8.field_70179_y *= 1.12;
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               this.isAACStep = true;
            }

            if (this.isAACStep) {
               EntityPlayerSP var10 = MinecraftInstance.mc.field_71439_g;
               var10.field_70181_x -= 0.015;
               if (!MinecraftInstance.mc.field_71439_g.func_71039_bw() && MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0F) {
                  MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.3F;
               }
            }
         } else {
            this.isAACStep = false;
         }
      } else if (StringsKt.equals(mode, "aac3.3.4", true)) {
         if (MinecraftInstance.mc.field_71439_g.field_70123_F && MovementUtils.INSTANCE.isMoving()) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
               EntityPlayerSP var11 = MinecraftInstance.mc.field_71439_g;
               var11.field_70159_w *= 1.26;
               var11 = MinecraftInstance.mc.field_71439_g;
               var11.field_70179_y *= 1.26;
               MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
               this.isAACStep = true;
            }

            if (this.isAACStep) {
               EntityPlayerSP var13 = MinecraftInstance.mc.field_71439_g;
               var13.field_70181_x -= 0.015;
               if (!MinecraftInstance.mc.field_71439_g.func_71039_bw() && MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0F) {
                  MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.3F;
               }
            }
         } else {
            this.isAACStep = false;
         }
      }

   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String mode = (String)this.modeValue.get();
      if (StringsKt.equals(mode, "motionncp2", true) && MinecraftInstance.mc.field_71439_g.field_70123_F) {
         MinecraftInstance.mc.field_71439_g.field_70181_x = 0.404 + Math.random() / (double)500;
      } else if (StringsKt.equals(mode, "motionncp", true) && MinecraftInstance.mc.field_71439_g.field_70123_F && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
         if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
            this.fakeJump();
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
            event.setY(0.41999998688698);
            this.ncpNextStep = 1;
         } else if (this.ncpNextStep == 1) {
            event.setY(0.33319999363422);
            this.ncpNextStep = 2;
         } else if (this.ncpNextStep == 2) {
            double yaw = MovementUtils.INSTANCE.getDirection();
            event.setY(0.24813599859094704);
            event.setX(-Math.sin(yaw) * 0.7);
            event.setZ(Math.cos(yaw) * 0.7);
            this.ncpNextStep = 0;
         }
      }

   }

   @EventTarget
   public final void onStep(@NotNull StepEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null) {
         String mode = (String)this.modeValue.get();
         if (event.getEventState() == EventState.PRE) {
            Module var177 = CrossSine.INSTANCE.getModuleManager().get(Phase.class);
            Intrinsics.checkNotNull(var177);
            if (((Phase)var177).getState()) {
               event.setStepHeight(0.0F);
               return;
            }

            if (StringsKt.equals(mode, "AAC4.4.0", true) || StringsKt.equals(mode, "NCPNew", true) || this.modeValue.equals("Matrix6.7.0")) {
               if (event.getStepHeight() > 0.6F && !this.canStep) {
                  return;
               }

               if (event.getStepHeight() <= 0.6F) {
                  return;
               }
            }

            if (!MinecraftInstance.mc.field_71439_g.field_70122_E || !this.timer.hasTimePassed((long)((Number)this.delayValue.get()).intValue()) || StringsKt.equals(mode, "Jump", true) || StringsKt.equals(mode, "MotionNCP", true) || StringsKt.equals(mode, "LAAC", true) || StringsKt.equals(mode, "AAC3.3.4", true) || StringsKt.equals(mode, "TimerJump", true)) {
               MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6F;
               event.setStepHeight(0.6F);
               return;
            }

            float height = ((Number)this.heightValue.get()).floatValue();
            MinecraftInstance.mc.field_71439_g.field_70138_W = height;
            event.setStepHeight(height);
            if (event.getStepHeight() > 0.6F) {
               this.isStep = true;
               this.stepX = MinecraftInstance.mc.field_71439_g.field_70165_t;
               this.stepY = MinecraftInstance.mc.field_71439_g.field_70163_u;
               this.stepZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
            }
         } else {
            if (!this.isStep) {
               return;
            }

            if (MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY > 0.6) {
               if ((double)((Number)this.timerValue.get()).floatValue() < (double)1.0F) {
                  this.wasTimer = true;
                  MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
                  if ((Boolean)this.timerDynValue.get()) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = (float)((double)MinecraftInstance.mc.field_71428_T.field_74278_d / Math.sqrt(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY));
                  }
               }

               if (StringsKt.equals(mode, "BlocksMC", true)) {
                  this.fakeJump();
                  if (PlayerUtils.getGroundTicks() == 0) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                  }

                  if (PlayerUtils.getOffGroundTicks() == 2) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 2.0F;
                  }

                  if (PlayerUtils.getOffGroundTicks() == 3) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                     MinecraftInstance.mc.field_71439_g.field_70181_x = MovementUtils.INSTANCE.predictedMotion(MinecraftInstance.mc.field_71439_g.field_70181_x, 5);
                  }
               }

               if (!StringsKt.equals(mode, "NCP", true) && !StringsKt.equals(mode, "OldAAC", true)) {
                  if (StringsKt.equals(mode, "NCPNew", true)) {
                     double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                     this.fakeJump();
                     if (rstepHeight > 2.019) {
                        Double[] stpPacket = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.919};
                        int $i$f$forEach = 0;
                        Double[] var75 = stpPacket;
                        int var94 = 0;
                        int var114 = stpPacket.length;

                        while(var94 < var114) {
                           Object element$iv = var75[var94];
                           ++var94;
                           double it = ((Number)element$iv).doubleValue();
                           int var171 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     } else if (rstepHeight <= 2.019 && rstepHeight > 1.869) {
                        Double[] stpPacket = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
                        int $i$f$forEach = 0;
                        Double[] var80 = stpPacket;
                        int var99 = 0;
                        int var119 = stpPacket.length;

                        while(var99 < var119) {
                           Object element$iv = var80[var99];
                           ++var99;
                           double it = ((Number)element$iv).doubleValue();
                           int var176 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     } else if (rstepHeight <= 1.869 && rstepHeight > (double)1.5F) {
                        Double[] $this$forEach$iv = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652};
                        int $i$f$forEach = 0;
                        Double[] var79 = $this$forEach$iv;
                        int var98 = 0;
                        int var118 = $this$forEach$iv.length;

                        while(var98 < var118) {
                           Object element$iv = var79[var98];
                           ++var98;
                           double it = ((Number)element$iv).doubleValue();
                           int var175 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     } else if (rstepHeight <= (double)1.5F && rstepHeight > 1.015) {
                        Double[] $this$forEach$iv = new Double[]{0.42, 0.7532, 1.01, 1.093, 1.015};
                        int $i$f$forEach = 0;
                        Double[] var78 = $this$forEach$iv;
                        int var97 = 0;
                        int var117 = $this$forEach$iv.length;

                        while(var97 < var117) {
                           Object element$iv = var78[var97];
                           ++var97;
                           double it = ((Number)element$iv).doubleValue();
                           int var174 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }
                     } else if (rstepHeight <= 1.015 && rstepHeight > (double)0.875F) {
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212};
                        int $i$f$forEach = 0;
                        Double[] var77 = $this$forEach$iv;
                        int var96 = 0;
                        int var116 = $this$forEach$iv.length;

                        while(var96 < var116) {
                           Object element$iv = var77[var96];
                           ++var96;
                           double it = ((Number)element$iv).doubleValue();
                           int var173 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }
                     } else if (rstepHeight <= (double)0.875F && rstepHeight > 0.6) {
                        Double[] $this$forEach$iv = new Double[]{0.39, 0.6938};
                        int $i$f$forEach = 0;
                        Double[] var76 = $this$forEach$iv;
                        int var95 = 0;
                        int var115 = $this$forEach$iv.length;

                        while(var95 < var115) {
                           Object element$iv = var76[var95];
                           ++var95;
                           double it = ((Number)element$iv).doubleValue();
                           int var172 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }
                     }

                     this.timer.reset();
                  } else if (StringsKt.equals(mode, "Verus", true)) {
                     double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F / (float)Math.ceil(rstepHeight * (double)2.0F);
                     double stpHight = (double)0.0F;
                     this.fakeJump();
                     int $this$forEach$iv = (int)(Math.ceil(rstepHeight * (double)2.0F) - (double)1.0F);

                     Unit var10000;
                     for(int $i$f$forEach = 0; $i$f$forEach < $this$forEach$iv; var10000 = Unit.INSTANCE) {
                        ++$i$f$forEach;
                        int var113 = 0;
                        stpHight += (double)0.5F;
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + stpHight, this.stepZ, true)));
                     }

                     this.wasTimer = true;
                  } else if (StringsKt.equals(mode, "Vulcan", true)) {
                     double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                     this.fakeJump();
                     if (rstepHeight > (double)2.0F) {
                        Double[] $this$forEach$iv = new Double[]{(double)0.5F, (double)1.0F, (double)1.5F, (double)2.0F};
                        int $i$f$forEach = 0;
                        Double[] var71 = $this$forEach$iv;
                        int var90 = 0;
                        int var109 = $this$forEach$iv.length;

                        while(var90 < var109) {
                           Object element$iv = var71[var90];
                           ++var90;
                           double it = ((Number)element$iv).doubleValue();
                           int var167 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                        }
                     } else if (rstepHeight <= (double)2.0F && rstepHeight > (double)1.5F) {
                        Double[] $this$forEach$iv = new Double[]{(double)0.5F, (double)1.0F, (double)1.5F};
                        int $i$f$forEach = 0;
                        Double[] var74 = $this$forEach$iv;
                        int var93 = 0;
                        int var112 = $this$forEach$iv.length;

                        while(var93 < var112) {
                           Object element$iv = var74[var93];
                           ++var93;
                           double it = ((Number)element$iv).doubleValue();
                           int var170 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                        }
                     } else if (rstepHeight <= (double)1.5F && rstepHeight > (double)1.0F) {
                        Double[] $this$forEach$iv = new Double[]{(double)0.5F, (double)1.0F};
                        int $i$f$forEach = 0;
                        Double[] var73 = $this$forEach$iv;
                        int var92 = 0;
                        int var111 = $this$forEach$iv.length;

                        while(var92 < var111) {
                           Object element$iv = var73[var92];
                           ++var92;
                           double it = ((Number)element$iv).doubleValue();
                           int var169 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                        }
                     } else if (rstepHeight <= (double)1.0F && rstepHeight > 0.6) {
                        Double[] $this$forEach$iv = new Double[]{(double)0.5F};
                        int $i$f$forEach = 0;
                        Double[] var72 = $this$forEach$iv;
                        int var91 = 0;
                        int var110 = $this$forEach$iv.length;

                        while(var91 < var110) {
                           Object element$iv = var72[var91];
                           ++var91;
                           double it = ((Number)element$iv).doubleValue();
                           int var168 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                        }
                     }

                     this.timer.reset();
                  } else if (!StringsKt.equals(mode, "Matrix6.7.0", true)) {
                     if (StringsKt.equals(mode, "Spartan", true)) {
                        this.fakeJump();
                        if (this.spartanSwitch) {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147, this.stepZ, false)));
                        } else {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.6, this.stepZ, false)));
                        }

                        this.spartanSwitch = !this.spartanSwitch;
                        this.timer.reset();
                     } else if (StringsKt.equals(mode, "AAC4.4.0", true)) {
                        double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                        this.fakeJump();
                        this.timer.reset();
                        if (rstepHeight >= (double)0.984375F && rstepHeight < (double)1.484375F) {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.4, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.9, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (double)1.0F, this.stepZ, true)));
                        } else if (rstepHeight >= (double)1.484375F && rstepHeight < (double)1.984375F) {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.42, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7718, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.0556, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.2714, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.412, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (double)1.5F, this.stepZ, true)));
                        } else if (rstepHeight >= (double)1.984375F) {
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.45, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (double)0.84375F, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.18125, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.4625, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (double)1.6875F, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.85625, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (double)1.96875F, this.stepZ, false)));
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX + MinecraftInstance.mc.field_71439_g.field_70159_w * (double)0.5F, this.stepY + (double)2.0F, this.stepZ + MinecraftInstance.mc.field_71439_g.field_70179_y * (double)0.5F, true)));
                        }
                     } else if (StringsKt.equals(mode, "Rewinside", true)) {
                        this.fakeJump();
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false)));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false)));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147, this.stepZ, false)));
                        this.timer.reset();
                     }
                  } else {
                     double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                     this.fakeJump();
                     if (rstepHeight <= 3.0042 && rstepHeight > 2.95) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371, 2.35453, 2.40423};
                        int $i$f$forEach = 0;
                        Double[] var70 = $this$forEach$iv;
                        int var89 = 0;
                        int var108 = $this$forEach$iv.length;

                        while(var89 < var108) {
                           Object element$iv = var70[var89];
                           ++var89;
                           double it = ((Number)element$iv).doubleValue();
                           int var166 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.11F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 2.95 && rstepHeight > 2.83) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371, 2.35453};
                        int $i$f$forEach = 0;
                        Double[] var69 = $this$forEach$iv;
                        int var88 = 0;
                        int var107 = $this$forEach$iv.length;

                        while(var88 < var107) {
                           Object element$iv = var69[var88];
                           ++var88;
                           double it = ((Number)element$iv).doubleValue();
                           int var165 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.12F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 2.83 && rstepHeight > 2.64) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371};
                        int $i$f$forEach = 0;
                        Double[] var68 = $this$forEach$iv;
                        int var87 = 0;
                        int var106 = $this$forEach$iv.length;

                        while(var87 < var106) {
                           Object element$iv = var68[var87];
                           ++var87;
                           double it = ((Number)element$iv).doubleValue();
                           int var164 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.13F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 2.64 && rstepHeight > 2.37) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032};
                        int $i$f$forEach = 0;
                        Double[] var67 = $this$forEach$iv;
                        int var86 = 0;
                        int var105 = $this$forEach$iv.length;

                        while(var86 < var105) {
                           Object element$iv = var67[var86];
                           ++var86;
                           double it = ((Number)element$iv).doubleValue();
                           int var163 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.14F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 2.37 && rstepHeight > 2.02) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289};
                        int $i$f$forEach = 0;
                        Double[] var66 = $this$forEach$iv;
                        int var85 = 0;
                        int var104 = $this$forEach$iv.length;

                        while(var85 < var104) {
                           Object element$iv = var66[var85];
                           ++var85;
                           double it = ((Number)element$iv).doubleValue();
                           int var162 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.16F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 2.02 && rstepHeight > 1.77) {
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989};
                        int $i$f$forEach = 0;
                        Double[] var65 = $this$forEach$iv;
                        int var84 = 0;
                        int var103 = $this$forEach$iv.length;

                        while(var84 < var103) {
                           Object element$iv = var65[var84];
                           ++var84;
                           double it = ((Number)element$iv).doubleValue();
                           int var161 = 0;
                           if (0.9 <= it ? it <= 1.01 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.21F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 1.77 && rstepHeight > 1.6) {
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212, 1.17319996740818};
                        int $i$f$forEach = 0;
                        Double[] var64 = $this$forEach$iv;
                        int var83 = 0;
                        int var102 = $this$forEach$iv.length;

                        while(var83 < var102) {
                           Object element$iv = var64[var83];
                           ++var83;
                           double it = ((Number)element$iv).doubleValue();
                           int var160 = 0;
                           if (0.753 <= it ? it <= 0.754 : false) {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true)));
                           } else {
                              MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                           }
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.28F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 1.6 && rstepHeight > 1.3525) {
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212, 1.001335979112147};
                        int $i$f$forEach = 0;
                        Double[] var63 = $this$forEach$iv;
                        int var82 = 0;
                        int var101 = $this$forEach$iv.length;

                        while(var82 < var101) {
                           Object element$iv = var63[var82];
                           ++var82;
                           double it = ((Number)element$iv).doubleValue();
                           int var159 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.28F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 1.3525 && rstepHeight > 1.02) {
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212};
                        int $i$f$forEach = 0;
                        Double[] var62 = $this$forEach$iv;
                        int var81 = 0;
                        int var100 = $this$forEach$iv.length;

                        while(var81 < var100) {
                           Object element$iv = var62[var81];
                           ++var81;
                           double it = ((Number)element$iv).doubleValue();
                           int var158 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.34F;
                        this.wasTimer = true;
                     } else if (rstepHeight <= 1.02 && rstepHeight > 0.6) {
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698};
                        int $i$f$forEach = 0;
                        Double[] var8 = $this$forEach$iv;
                        int it = 0;
                        int var10 = $this$forEach$iv.length;

                        while(it < var10) {
                           Object element$iv = var8[it];
                           ++it;
                           double it = ((Number)element$iv).doubleValue();
                           int var14 = 0;
                           MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false)));
                        }

                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5F;
                        this.wasTimer = true;
                     }

                     this.timer.reset();
                  }
               } else {
                  this.fakeJump();
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false)));
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false)));
                  this.timer.reset();
               }
            }

            this.isStep = false;
            this.stepX = (double)0.0F;
            this.stepY = (double)0.0F;
            this.stepZ = (double)0.0F;
         }

      }
   }

   @EventTarget(
      ignoreCondition = true
   )
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer && this.isStep && this.modeValue.equals("OldNCP")) {
         ((C03PacketPlayer)packet).field_149477_b += 0.07;
         this.isStep = false;
      }

   }

   private final void fakeJump() {
      MinecraftInstance.mc.field_71439_g.field_70160_al = true;
      MinecraftInstance.mc.field_71439_g.func_71029_a(StatList.field_75953_u);
   }

   private final boolean couldStep() {
      double yaw = MovementUtils.INSTANCE.getDirection();
      double x = -Math.sin(yaw) * 0.32;
      double z = Math.cos(yaw) * 0.32;
      return MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(x, 1.001335979112147, z)).isEmpty();
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
