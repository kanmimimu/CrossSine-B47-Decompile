package net.ccbluex.liquidbounce.features.module.modules.movement.flights.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/MatrixDamageFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostTicks", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "customstrafe", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "motionreduceonend", "packetymotion", "", "randomAmount", "randomNum", "randomize", "speedBoost", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "tick", "", "timer", "velocitypacket", "", "warn", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "resetmotion", "CrossSine"}
)
public final class MatrixDamageFlight extends FlightMode {
   @NotNull
   private final ListValue mode;
   @NotNull
   private final BoolValue warn;
   @NotNull
   private final FloatValue timer;
   @NotNull
   private final FloatValue speedBoost;
   @NotNull
   private final IntegerValue boostTicks;
   @NotNull
   private final BoolValue randomize;
   @NotNull
   private final IntegerValue randomAmount;
   @NotNull
   private final BoolValue customstrafe;
   @NotNull
   private final BoolValue motionreduceonend;
   private boolean velocitypacket;
   private double packetymotion;
   private int tick;
   private double randomNum;

   public MatrixDamageFlight() {
      super("MatrixDamage");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "Mode");
      String[] var1 = new String[]{"Stable", "Test", "Custom"};
      this.mode = new ListValue(var10003, var1, "Stable");
      this.warn = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DamageWarn"), true);
      this.timer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 1.0F, 0.0F, 2.0F);
      this.speedBoost = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-BoostSpeed"), 0.5F, 0.0F, 3.0F);
      this.boostTicks = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-BoostTicks"), 27, 10, 40);
      this.randomize = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-Randomize"), true);
      this.randomAmount = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-RandomAmount"), 1, 0, 30);
      this.customstrafe = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-Strafe"), true);
      this.motionreduceonend = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionReduceOnEnd"), true);
      this.randomNum = 0.2;
   }

   public void onEnable() {
      if ((Boolean)this.warn.get()) {
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lMatrix-Dmg-Fly§8] §aGetting damage from other entities (players, arrows, snowballs, eggs...) is required to bypass.");
      }

      this.velocitypacket = false;
      this.packetymotion = (double)0.0F;
      this.tick = 0;
   }

   private final void resetmotion() {
      if ((Boolean)this.motionreduceonend.get()) {
         MinecraftInstance.mc.field_71439_g.field_70159_w /= (double)10;
         MinecraftInstance.mc.field_71439_g.field_70181_x /= (double)10;
         MinecraftInstance.mc.field_71439_g.field_70179_y /= (double)10;
      }

   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.motionreduceonend.get()) {
         this.getFlight().setNeedReset(false);
      }

      if (this.velocitypacket) {
         double yaw = Math.toRadians((double)MinecraftInstance.mc.field_71439_g.field_70177_z);
         Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var6) {
            case "custom":
               if ((Boolean)this.customstrafe.get()) {
                  MovementUtils.INSTANCE.strafe();
               }

               this.randomNum = (Boolean)this.randomize.get() ? Math.random() * ((Number)this.randomAmount.get()).doubleValue() * 0.01 : (double)0.0F;
               MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timer.get()).floatValue();
               EntityPlayerSP var15 = MinecraftInstance.mc.field_71439_g;
               var15.field_70159_w += -Math.sin(yaw) * (0.3 + (double)((Number)this.speedBoost.get()).floatValue() / (double)10 + this.randomNum);
               var15 = MinecraftInstance.mc.field_71439_g;
               var15.field_70179_y += Math.cos(yaw) * (0.3 + (double)((Number)this.speedBoost.get()).floatValue() / (double)10 + this.randomNum);
               MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
               int var17 = this.tick++;
               if (var17 >= ((Number)this.boostTicks.get()).intValue()) {
                  this.resetmotion();
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                  this.velocitypacket = false;
                  this.packetymotion = (double)0.0F;
                  this.tick = 0;
               }
               break;
            case "stable":
               MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
               EntityPlayerSP var12 = MinecraftInstance.mc.field_71439_g;
               var12.field_70159_w += -Math.sin(yaw) * 0.416;
               var12 = MinecraftInstance.mc.field_71439_g;
               var12.field_70179_y += Math.cos(yaw) * 0.416;
               MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
               int var14 = this.tick++;
               if (var14 >= 27) {
                  this.resetmotion();
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                  this.velocitypacket = false;
                  this.packetymotion = (double)0.0F;
                  this.tick = 0;
               }
               break;
            case "test":
               int var5 = this.tick++;
               if (var5 >= 4) {
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 1.1F;
                  EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
                  var7.field_70159_w += -Math.sin(yaw) * 0.42;
                  var7 = MinecraftInstance.mc.field_71439_g;
                  var7.field_70179_y += Math.cos(yaw) * 0.42;
               } else {
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 0.9F;
                  EntityPlayerSP var9 = MinecraftInstance.mc.field_71439_g;
                  var9.field_70159_w += -Math.sin(yaw) * 0.33;
                  var9 = MinecraftInstance.mc.field_71439_g;
                  var9.field_70179_y += Math.cos(yaw) * 0.33;
               }

               MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
               var5 = this.tick++;
               if (var5 >= 27) {
                  this.resetmotion();
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
                  this.velocitypacket = false;
                  this.packetymotion = (double)0.0F;
                  this.tick = 0;
               }
         }
      }

   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      this.resetmotion();
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof S12PacketEntityVelocity) {
         if (MinecraftInstance.mc.field_71439_g == null) {
            return;
         }

         WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
         Entity var3 = var10000 == null ? null : var10000.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
         if (var3 == null) {
            return;
         }

         if (!Intrinsics.areEqual((Object)var3, (Object)MinecraftInstance.mc.field_71439_g)) {
            return;
         }

         if ((double)((S12PacketEntityVelocity)packet).field_149416_c / (double)8000.0F > 0.2) {
            this.velocitypacket = true;
            this.packetymotion = (double)((S12PacketEntityVelocity)packet).field_149416_c / (double)8000.0F;
         }
      }

   }
}
