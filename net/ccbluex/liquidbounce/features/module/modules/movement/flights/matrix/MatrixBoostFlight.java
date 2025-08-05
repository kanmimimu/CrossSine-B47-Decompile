package net.ccbluex.liquidbounce.features.module.modules.movement.flights.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/MatrixBoostFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostMotion", "", "boostTimer", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "bypassMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "customYMotion", "jumpTimer", "speed", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class MatrixBoostFlight extends FlightMode {
   @NotNull
   private final ListValue bypassMode;
   @NotNull
   private final FloatValue speed;
   @NotNull
   private final FloatValue customYMotion;
   @NotNull
   private final FloatValue jumpTimer;
   @NotNull
   private final FloatValue boostTimer;
   private int boostMotion;

   public MatrixBoostFlight() {
      super("MatrixBoost");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "BypassMode");
      String[] var1 = new String[]{"New", "Stable", "Test", "Custom"};
      this.bypassMode = new ListValue(var10003, var1, "New");
      this.speed = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0F, 1.0F, 3.0F);
      this.customYMotion = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "CustomJumpMotion"), 0.6F, 0.2F, 5.0F);
      this.jumpTimer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "JumpTimer"), 0.1F, 0.1F, 2.0F);
      this.boostTimer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "BoostTimer"), 1.0F, 0.5F, 3.0F);
   }

   public void onEnable() {
      this.boostMotion = 0;
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.boostMotion == 0) {
         double yaw = Math.toRadians((double)MinecraftInstance.mc.field_71439_g.field_70177_z);
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
         if (this.bypassMode.equals("Test")) {
            MovementUtils.INSTANCE.strafe(5.0F);
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)2.0F;
         } else {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * (double)1.5F, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * (double)1.5F, false)));
         }

         this.boostMotion = 1;
         MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimer.get()).floatValue();
      } else if (this.boostMotion == 1 && this.bypassMode.equals("Test")) {
         MovementUtils.INSTANCE.strafe(1.89F);
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)2.0F;
      } else if (this.boostMotion == 2) {
         MovementUtils.INSTANCE.strafe(((Number)this.speed.get()).floatValue());
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var4) {
            case "custom":
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.customYMotion.get()).floatValue();
               break;
            case "stable":
               MinecraftInstance.mc.field_71439_g.field_70181_x = 0.8;
               break;
            case "new":
               MinecraftInstance.mc.field_71439_g.field_70181_x = 0.48;
               break;
            case "test":
               double yaw = Math.toRadians((double)MinecraftInstance.mc.field_71439_g.field_70177_z);
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * (double)2, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2.0F, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * (double)2, true)));
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)2.0F;
               MovementUtils.INSTANCE.strafe(1.89F);
         }

         this.boostMotion = 3;
      } else if (this.boostMotion < 5) {
         int var5 = this.boostMotion++;
      } else if (this.boostMotion >= 5) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.boostTimer.get()).floatValue();
         if (MinecraftInstance.mc.field_71439_g.field_70163_u < this.getFlight().getLaunchY() - (double)1.0F) {
            this.boostMotion = 0;
         }
      }

   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (MinecraftInstance.mc.field_71462_r == null && packet instanceof S08PacketPlayerPosLook) {
         MinecraftInstance.mc.field_71439_g.func_70107_b(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c);
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).field_148936_d, ((S08PacketPlayerPosLook)packet).field_148937_e, false)));
         if (this.boostMotion == 1) {
            this.boostMotion = 2;
         }

         event.cancelEvent();
      }

   }
}
