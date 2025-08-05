package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "HighJump",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/HighJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "glassValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "heightValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "jumpY", "", "martrixStatus", "", "martrixWasTimer", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "stableMotionValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onDisable", "", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class HighJump extends Module {
   @NotNull
   private final FloatValue heightValue = new FloatValue("Height", 2.0F, 1.1F, 7.0F);
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final BoolValue glassValue;
   @NotNull
   private final Value stableMotionValue;
   private double jumpY;
   private int martrixStatus;
   private boolean martrixWasTimer;
   @NotNull
   private final MSTimer timer;

   public HighJump() {
      String[] var1 = new String[]{"Vanilla", "StableMotion", "Damage", "AACv3", "DAC", "Mineplex", "Matrix", "MatrixWater", "Pika"};
      this.modeValue = new ListValue("Mode", var1, "Vanilla");
      this.glassValue = new BoolValue("OnlyGlassPane", false);
      this.stableMotionValue = (new FloatValue("StableMotion", 0.42F, 0.1F, 1.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return HighJump.this.modeValue.equals("StableMotion");
         }
      });
      this.jumpY = (double)114514.0F;
      this.timer = new MSTimer();
   }

   public void onEnable() {
      this.jumpY = (double)114514.0F;
      this.martrixStatus = 0;
      this.martrixWasTimer = false;
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)this.glassValue.get() || BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane) {
         String var4 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var4.hashCode()) {
            case -1362669950:
               if (var4.equals("mineplex") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  MovementUtils.INSTANCE.strafe(0.35F);
               }
               break;
            case -1339126929:
               if (var4.equals("damage") && MinecraftInstance.mc.field_71439_g.field_70737_aN > 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  EntityPlayerSP var13 = MinecraftInstance.mc.field_71439_g;
                  var13.field_70181_x += (double)(0.42F * ((Number)this.heightValue.get()).floatValue());
               }
               break;
            case -1331973455:
               if (var4.equals("stablemotion") && this.jumpY != (double)114514.0F) {
                  if (this.jumpY + ((Number)this.heightValue.get()).doubleValue() - (double)1 > MinecraftInstance.mc.field_71439_g.field_70163_u) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.stableMotionValue.get()).floatValue();
                  } else {
                     this.jumpY = (double)114514.0F;
                  }
               }
               break;
            case -1081239615:
               if (var4.equals("matrix")) {
                  if (this.martrixWasTimer) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                     this.martrixWasTimer = false;
                  }

                  label131: {
                     List var10 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, MinecraftInstance.mc.field_71439_g.field_70181_x, (double)0.0F).func_72314_b((double)0.0F, (double)0.0F, (double)0.0F));
                     Intrinsics.checkNotNullExpressionValue(var10, "mc.theWorld.getColliding…0).expand(0.0, 0.0, 0.0))");
                     if (((Collection)var10).isEmpty()) {
                        var10 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-4.0F, (double)0.0F).func_72314_b((double)0.0F, (double)0.0F, (double)0.0F));
                        Intrinsics.checkNotNullExpressionValue(var10, "mc.theWorld.getColliding…0).expand(0.0, 0.0, 0.0))");
                        if (((Collection)var10).isEmpty()) {
                           break label131;
                        }
                     }

                     if (MinecraftInstance.mc.field_71439_g.field_70143_R > 10.0F && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.1F;
                        this.martrixWasTimer = true;
                     }
                  }

                  if (this.timer.hasTimePassed(1000L) && this.martrixStatus == 1) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                     MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     this.martrixStatus = 0;
                     return;
                  }

                  if (this.martrixStatus == 1 && MinecraftInstance.mc.field_71439_g.field_70737_aN > 0) {
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)3.0F;
                     MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
                     this.martrixStatus = 0;
                     return;
                  }

                  if (this.martrixStatus == 2) {
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C0APacketAnimation()));
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
                     byte var12 = 8;
                     int var14 = 0;

                     while(var14 < var12) {
                        ++var14;
                        int var7 = 0;
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.399, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
                     }

                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 0.6F;
                     this.martrixStatus = 1;
                     this.timer.reset();
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C07PacketPlayerDigging(Action.ABORT_DESTROY_BLOCK, new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v), EnumFacing.UP)));
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C0APacketAnimation()));
                     return;
                  }

                  if (MinecraftInstance.mc.field_71439_g.field_70123_F && this.martrixStatus == 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v), EnumFacing.UP)));
                     MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C0APacketAnimation()));
                     this.martrixStatus = 2;
                     MinecraftInstance.mc.field_71428_T.field_74278_d = 0.05F;
                  }

                  if (MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                     MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
                     MinecraftInstance.mc.field_71439_g.field_70122_E = false;
                  }
               }
               break;
            case -358093354:
               if (var4.equals("matrixWater") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  if (Intrinsics.areEqual((Object)MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c(), (Object)Block.func_149729_e(9))) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.18;
                  } else if (Intrinsics.areEqual((Object)MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c(), (Object)Block.func_149729_e(9))) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.heightValue.get()).floatValue();
                     MinecraftInstance.mc.field_71439_g.field_70122_E = true;
                  }
               }
               break;
            case 99206:
               if (var4.equals("dac") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
                  var9.field_70181_x += 0.049999;
               }
               break;
            case 3440911:
               if (var4.equals("pika") && MinecraftInstance.mc.field_71439_g.field_70737_aN > 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  EntityPlayerSP var8 = MinecraftInstance.mc.field_71439_g;
                  var8.field_70181_x += (double)6.2999997F;
               }
               break;
            case 92570112:
               if (var4.equals("aacv3") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
                  var3.field_70181_x += 0.059;
               }
         }

      }
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)this.glassValue.get() || BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane) {
         if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            String var3 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual((Object)"mineplex", (Object)var3)) {
               EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
               var2.field_70181_x += MinecraftInstance.mc.field_71439_g.field_70143_R == 0.0F ? 0.0499 : 0.05;
            }
         }

      }
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)this.glassValue.get() || BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane) {
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var3) {
            case "mineplex":
               event.setMotion(0.47F);
               break;
            case "stablemotion":
               this.jumpY = MinecraftInstance.mc.field_71439_g.field_70163_u;
               break;
            case "vanilla":
               event.setMotion(event.getMotion() * ((Number)this.heightValue.get()).floatValue());
         }

      }
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
