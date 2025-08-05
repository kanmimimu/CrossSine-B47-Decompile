package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020$H\u0016J\b\u0010&\u001a\u00020$H\u0016J\b\u0010'\u001a\u00020$H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "AirSpaceKepPressed", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "GroundSpaceKeyPressed", "addYMotionValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "doCustomYValue", "doJump", "doLaunchSpeedValue", "doMinimumSpeedValue", "downAirSpeedValue", "downTimerValue", "groundResetXZValue", "groundStay", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "jumpTimerValue", "launchSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "minimumSpeedValue", "plusMode", "", "plusMultiply", "resetXZValue", "resetYValue", "speedValue", "strafeBeforeJump", "strafeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "upAirSpeedValue", "upTimerValue", "usePreMotion", "yValue", "onDisable", "", "onEnable", "onPreMotion", "onUpdate", "CrossSine"}
)
public final class CustomSpeed extends SpeedMode {
   @NotNull
   private final FloatValue speedValue = new FloatValue("CustomSpeed", 1.6F, 0.0F, 2.0F);
   @NotNull
   private final BoolValue doLaunchSpeedValue = new BoolValue("CustomDoLaunchSpeed", true);
   @NotNull
   private final Value launchSpeedValue = (new FloatValue("CustomLaunchSpeed", 1.6F, 0.2F, 2.0F)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)CustomSpeed.this.doLaunchSpeedValue.get();
      }
   });
   @NotNull
   private final BoolValue strafeBeforeJump = new BoolValue("CustomLaunchMoveBeforeJump", false);
   @NotNull
   private final BoolValue doMinimumSpeedValue = new BoolValue("CustomDoMinimumSpeed", true);
   @NotNull
   private final Value minimumSpeedValue = (new FloatValue("CustomMinimumSpeed", 0.25F, 0.1F, 2.0F)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)CustomSpeed.this.doMinimumSpeedValue.get();
      }
   });
   @NotNull
   private final FloatValue addYMotionValue = new FloatValue("CustomAddYMotion", 0.0F, 0.0F, 2.0F);
   @NotNull
   private final BoolValue doCustomYValue = new BoolValue("CustomDoModifyJumpY", true);
   @NotNull
   private final Value yValue = (new FloatValue("CustomY", 0.42F, 0.0F, 4.0F)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)CustomSpeed.this.doCustomYValue.get();
      }
   });
   @NotNull
   private final FloatValue upTimerValue = new FloatValue("CustomUpTimer", 1.0F, 0.1F, 2.0F);
   @NotNull
   private final FloatValue jumpTimerValue = new FloatValue("CustomJumpTimer", 1.25F, 0.1F, 2.0F);
   @NotNull
   private final FloatValue downTimerValue = new FloatValue("CustomDownTimer", 1.0F, 0.1F, 2.0F);
   @NotNull
   private final FloatValue upAirSpeedValue = new FloatValue("CustomUpAirSpeed", 2.03F, 0.5F, 3.5F);
   @NotNull
   private final FloatValue downAirSpeedValue = new FloatValue("CustomDownAirSpeed", 2.01F, 0.5F, 3.5F);
   @NotNull
   private final ListValue strafeValue;
   @NotNull
   private final Value plusMode;
   @NotNull
   private final Value plusMultiply;
   @NotNull
   private final IntegerValue groundStay;
   @NotNull
   private final BoolValue groundResetXZValue;
   @NotNull
   private final BoolValue resetXZValue;
   @NotNull
   private final BoolValue resetYValue;
   @NotNull
   private final BoolValue doJump;
   @NotNull
   private final BoolValue GroundSpaceKeyPressed;
   @NotNull
   private final BoolValue AirSpaceKepPressed;
   @NotNull
   private final BoolValue usePreMotion;

   public CustomSpeed() {
      super("Custom");
      String[] var1 = new String[]{"Strafe", "Boost", "AirSpeed", "Plus", "PlusOnlyUp", "PlusOnlyDown", "Non-Strafe"};
      this.strafeValue = new ListValue("CustomStrafe", var1, "Boost");
      var1 = new String[]{"Add", "Multiply"};
      this.plusMode = (new ListValue("PlusBoostMode", var1, "Add")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return CustomSpeed.this.strafeValue.equals("Plus") || CustomSpeed.this.strafeValue.equals("PlusOnlyUp") || CustomSpeed.this.strafeValue.equals("PlusOnlyDown");
         }
      });
      this.plusMultiply = (new FloatValue("PlusMultiplyAmount", 1.1F, 1.0F, 2.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return CustomSpeed.this.plusMode.equals("Multiply") && (CustomSpeed.this.strafeValue.equals("Plus") || CustomSpeed.this.strafeValue.equals("PlusOnlyUp") || CustomSpeed.this.strafeValue.equals("PlusOnlyDown"));
         }
      });
      this.groundStay = new IntegerValue("CustomGroundStay", 0, 0, 10);
      this.groundResetXZValue = new BoolValue("CustomGroundResetXZ", false);
      this.resetXZValue = new BoolValue("CustomResetXZ", false);
      this.resetYValue = new BoolValue("CustomResetY", false);
      this.doJump = new BoolValue("CustomDoJump", true);
      this.GroundSpaceKeyPressed = new BoolValue("CustomPressSpaceKeyOnGround", true);
      this.AirSpaceKepPressed = new BoolValue("CustomPressSpaceKeyInAir", false);
      this.usePreMotion = new BoolValue("CustomUsePreMotion", true);
   }

   public void onPreMotion() {
      if ((Boolean)this.usePreMotion.get()) {
         if (MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F ? ((Number)this.upTimerValue.get()).floatValue() : ((Number)this.downTimerValue.get()).floatValue();
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
               if (PlayerUtils.getGroundTicks() >= ((Number)this.groundStay.get()).intValue()) {
                  if ((Boolean)this.GroundSpaceKeyPressed.get()) {
                     MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A);
                  }

                  MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimerValue.get()).floatValue();
                  if ((Boolean)this.doLaunchSpeedValue.get() && (Boolean)this.strafeBeforeJump.get()) {
                     MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                  }

                  if ((Boolean)this.doJump.get()) {
                     MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
                  } else if (!(Boolean)this.doCustomYValue.get()) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                  }

                  if ((Boolean)this.doLaunchSpeedValue.get() && !(Boolean)this.strafeBeforeJump.get()) {
                     MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                  }

                  if ((Boolean)this.doCustomYValue.get() && ((Number)this.yValue.get()).floatValue() != 0.0F) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.yValue.get()).floatValue();
                  }
               } else if ((Boolean)this.groundResetXZValue.get()) {
                  MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
               }
            } else {
               if ((Boolean)this.AirSpaceKepPressed.get()) {
                  MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A);
               }

               if ((Boolean)this.doMinimumSpeedValue.get() && MovementUtils.INSTANCE.getSpeed() < ((Number)this.minimumSpeedValue.get()).floatValue()) {
                  MovementUtils.INSTANCE.strafe(((Number)this.minimumSpeedValue.get()).floatValue());
               }

               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var3) {
                  case "strafe":
                     MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
                     break;
                  case "airspeed":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F) {
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01F * ((Number)this.upAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                     } else {
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01F * ((Number)this.downAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                     }
                     break;
                  case "plusonlydown":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x < (double)0.0F) {
                        String var12 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (Intrinsics.areEqual((Object)var12, (Object)"plus")) {
                           MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                        } else if (Intrinsics.areEqual((Object)var12, (Object)"multiply")) {
                           EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
                           var9.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                           var9 = MinecraftInstance.mc.field_71439_g;
                           var9.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        }
                     } else {
                        MovementUtils.INSTANCE.strafe();
                     }
                     break;
                  case "non-strafe":
                     MovementUtils.INSTANCE.strafe();
                     break;
                  case "plus":
                     String var11 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (Intrinsics.areEqual((Object)var11, (Object)"plus")) {
                        MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                     } else if (Intrinsics.areEqual((Object)var11, (Object)"multiply")) {
                        EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
                        var7.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                        var7 = MinecraftInstance.mc.field_71439_g;
                        var7.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                     }
                     break;
                  case "boost":
                     MovementUtils.INSTANCE.strafe();
                     break;
                  case "plusonlyup":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F) {
                        String var4 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (Intrinsics.areEqual((Object)var4, (Object)"plus")) {
                           MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                        } else if (Intrinsics.areEqual((Object)var4, (Object)"multiply")) {
                           EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
                           var5.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                           var5 = MinecraftInstance.mc.field_71439_g;
                           var5.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        }
                     } else {
                        MovementUtils.INSTANCE.strafe();
                     }
               }

               EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
               var1.field_70181_x += ((Number)this.addYMotionValue.get()).doubleValue() * 0.03;
            }
         } else if ((Boolean)this.resetXZValue.get()) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
            MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
         }

      }
   }

   public void onUpdate() {
      if (!(Boolean)this.usePreMotion.get()) {
         if (MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F ? ((Number)this.upTimerValue.get()).floatValue() : ((Number)this.downTimerValue.get()).floatValue();
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
               if (PlayerUtils.getGroundTicks() >= ((Number)this.groundStay.get()).intValue()) {
                  if ((Boolean)this.GroundSpaceKeyPressed.get()) {
                     MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A);
                  }

                  MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimerValue.get()).floatValue();
                  if ((Boolean)this.doLaunchSpeedValue.get() && (Boolean)this.strafeBeforeJump.get()) {
                     MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                  }

                  if ((Boolean)this.doJump.get()) {
                     MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
                  } else if (!(Boolean)this.doCustomYValue.get()) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                  }

                  if ((Boolean)this.doLaunchSpeedValue.get() && !(Boolean)this.strafeBeforeJump.get()) {
                     MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                  }

                  if ((Boolean)this.doCustomYValue.get() && ((Number)this.yValue.get()).floatValue() != 0.0F) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.yValue.get()).floatValue();
                  }
               } else if ((Boolean)this.groundResetXZValue.get()) {
                  MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
               }

               PlayerUtils var10000 = PlayerUtils.INSTANCE;
               int var2 = PlayerUtils.getGroundTicks();
               PlayerUtils.setGroundTicks(var2 + 1);
            } else {
               PlayerUtils.setGroundTicks(0);
               if ((Boolean)this.AirSpaceKepPressed.get()) {
                  MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A);
               }

               if ((Boolean)this.doMinimumSpeedValue.get() && MovementUtils.INSTANCE.getSpeed() < ((Number)this.minimumSpeedValue.get()).floatValue()) {
                  MovementUtils.INSTANCE.strafe(((Number)this.minimumSpeedValue.get()).floatValue());
               }

               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var3) {
                  case "strafe":
                     MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
                     break;
                  case "airspeed":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F) {
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01F * ((Number)this.upAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                     } else {
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01F * ((Number)this.downAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                     }
                     break;
                  case "plusonlydown":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x < (double)0.0F) {
                        String var12 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var12, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (Intrinsics.areEqual((Object)var12, (Object)"plus")) {
                           MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                        } else if (Intrinsics.areEqual((Object)var12, (Object)"multiply")) {
                           EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
                           var9.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                           var9 = MinecraftInstance.mc.field_71439_g;
                           var9.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        }
                     } else {
                        MovementUtils.INSTANCE.strafe();
                     }
                     break;
                  case "non-strafe":
                     MovementUtils.INSTANCE.strafe();
                     break;
                  case "plus":
                     String var11 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (Intrinsics.areEqual((Object)var11, (Object)"plus")) {
                        MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                     } else if (Intrinsics.areEqual((Object)var11, (Object)"multiply")) {
                        EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
                        var7.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                        var7 = MinecraftInstance.mc.field_71439_g;
                        var7.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                     }
                     break;
                  case "boost":
                     MovementUtils.INSTANCE.strafe();
                     break;
                  case "plusonlyup":
                     if (MinecraftInstance.mc.field_71439_g.field_70181_x > (double)0.0F) {
                        String var4 = ((String)this.plusMode.get()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (Intrinsics.areEqual((Object)var4, (Object)"plus")) {
                           MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1F);
                        } else if (Intrinsics.areEqual((Object)var4, (Object)"multiply")) {
                           EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
                           var5.field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                           var5 = MinecraftInstance.mc.field_71439_g;
                           var5.field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        }
                     } else {
                        MovementUtils.INSTANCE.strafe();
                     }
               }

               EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
               var1.field_70181_x += ((Number)this.addYMotionValue.get()).doubleValue() * 0.03;
            }
         } else if ((Boolean)this.resetXZValue.get()) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
            MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
         }

      }
   }

   public void onEnable() {
      if ((Boolean)this.resetXZValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
      }

      if ((Boolean)this.resetYValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
      }

   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNull(var10000);
      var10000.field_71102_ce = 0.02F;
   }
}
