package net.ccbluex.liquidbounce.features.module.modules.movement.flights.verus;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0018H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/verus/VerusBoostFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "reDamageValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "ticks", "", "ticks2", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class VerusBoostFlight extends FlightMode {
   @NotNull
   private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 1.5F, 0.0F, 10.0F);
   @NotNull
   private final ListValue boostModeValue;
   @NotNull
   private final BoolValue reDamageValue;
   private int ticks;
   private int ticks2;

   public VerusBoostFlight() {
      super("VerusBoost");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "BoostMode");
      String[] var1 = new String[]{"Boost1", "Boost2", "Boost3"};
      this.boostModeValue = new ListValue(var10003, var1, "Boost1");
      this.reDamageValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Boost3-ReDamage"), true);
   }

   public void onEnable() {
      this.ticks = 0;
      this.ticks2 = 1;
   }

   public void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String var2 = (String)this.boostModeValue.get();
      if (Intrinsics.areEqual((Object)var2, (Object)"Boost1")) {
         float speed = ((Number)this.speedValue.get()).floatValue();
         BlockPos pos = MinecraftInstance.mc.field_71439_g.func_180425_c().func_177963_a((double)0.0F, (double)-1.5F, (double)0.0F);
         PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(pos, 1, new ItemStack(Blocks.field_150348_b.func_180665_b((World)MinecraftInstance.mc.field_71441_e, pos)), 0.0F, 0.5F + (float)Math.random() * 0.44F, 0.0F)));
         if (this.ticks < 3) {
            event.cancelEvent();
         }

         if (this.ticks > 4) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0684;
         }

         if (this.ticks <= 25) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.8F;
            MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
            MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
            MovementUtils.INSTANCE.strafe(speed);
         } else {
            MovementUtils.INSTANCE.strafe(0.29F);
         }
      } else if (Intrinsics.areEqual((Object)var2, (Object)"Boost2")) {
         if (this.ticks < 3) {
            event.cancelEvent();
         }

         if (this.ticks == 3) {
            BlockPos pos = MinecraftInstance.mc.field_71439_g.func_180425_c().func_177963_a((double)0.0F, (double)-1.5F, (double)0.0F);
            PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(pos, 1, new ItemStack(Blocks.field_150348_b.func_180665_b((World)MinecraftInstance.mc.field_71441_e, pos)), 0.0F, 0.5F + (float)Math.random() * 0.44F, 0.0F)));
            double x = MinecraftInstance.mc.field_71439_g.field_70165_t;
            double y = MinecraftInstance.mc.field_71439_g.field_70163_u;
            double z = MinecraftInstance.mc.field_71439_g.field_70161_v;
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(x, y + (double)3 + Math.random() * 0.07, z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, false)));
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, false)));
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, true)));
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.25F;
         }

         if (MinecraftInstance.mc.field_71439_g.field_70737_aN > 2) {
            EntityPlayerSP var11 = MinecraftInstance.mc.field_71439_g;
            var11.field_70181_x += (double)0.4F;
            event.setY(MinecraftInstance.mc.field_71439_g.field_70181_x);
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
         }

         if (MinecraftInstance.mc.field_71439_g.field_70737_aN == 3) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
         }

         if (MinecraftInstance.mc.field_71439_g.field_70737_aN == 0) {
            MovementUtils.INSTANCE.strafe(0.36F);
            if (MinecraftInstance.mc.field_71439_g.field_70143_R > 0.0F) {
               MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
            }
         }
      }

   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.boostModeValue.get() == "Boost3") {
         if (this.ticks2 == 1) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, Action.START_SPRINTING)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 3.42, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.15F;
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            MinecraftInstance.mc.field_71439_g.field_70122_E = true;
         } else if (this.ticks2 == 2) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         }

         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         }

         if (MinecraftInstance.mc.field_71439_g.field_70143_R > 1.0F) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -(MinecraftInstance.mc.field_71439_g.field_70163_u - Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u));
         }

         if (MinecraftInstance.mc.field_71439_g.field_70181_x == (double)0.0F) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            MinecraftInstance.mc.field_71439_g.field_70122_E = true;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0F;
         }

         if (this.ticks2 < 25) {
            MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
         } else {
            if (this.ticks2 == 25) {
               MovementUtils.INSTANCE.strafe(0.48F);
            }

            if ((Boolean)this.reDamageValue.get()) {
               this.ticks2 = 1;
            }

            MovementUtils.INSTANCE.strafe();
         }

         int var2 = this.ticks2++;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      String var3 = (String)this.boostModeValue.get();
      if ((Intrinsics.areEqual((Object)var3, (Object)"Boost1") ? true : Intrinsics.areEqual((Object)var3, (Object)"Boost2")) && packet instanceof C03PacketPlayer && this.ticks < 3) {
         ((C03PacketPlayer)packet).field_149474_g = true;
      }

   }

   public void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String var2 = (String)this.boostModeValue.get();
      if (Intrinsics.areEqual((Object)var2, (Object)"Boost1")) {
         if (!event.isPre()) {
            return;
         }

         int var3 = this.ticks++;
         if (this.ticks == 3) {
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)3.25F, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false)));
            PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer(true)));
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.4F;
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         } else if (this.ticks == 4) {
            EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
            var4.field_70181_x += 0.3;
         }
      } else if (Intrinsics.areEqual((Object)var2, (Object)"Boost2")) {
         if (!event.isPre()) {
            return;
         }

         int var5 = this.ticks++;
      }

   }

   public void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.boostModeValue.get() == "Boost1") {
         event.cancelEvent();
      }

   }
}
