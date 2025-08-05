package net.ccbluex.liquidbounce.features.module.modules.movement.flights.default;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/default/VanillaFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "keepAliveValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "kickBypassModeValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "kickBypassMotion", "", "kickBypassMotionSpeedValue", "kickBypassValue", "noClipValue", "packets", "", "smoothValue", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "spoofValue", "vspeedValue", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class VanillaFlight extends FlightMode {
   @NotNull
   private final BoolValue smoothValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Smooth"), false);
   @NotNull
   private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0F, 0.0F, 5.0F);
   @NotNull
   private final FloatValue vspeedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Vertical"), 2.0F, 0.0F, 5.0F);
   @NotNull
   private final BoolValue kickBypassValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "KickBypass"), false);
   @NotNull
   private final Value kickBypassModeValue;
   @NotNull
   private final Value kickBypassMotionSpeedValue;
   @NotNull
   private final BoolValue keepAliveValue;
   @NotNull
   private final BoolValue noClipValue;
   @NotNull
   private final BoolValue spoofValue;
   private int packets;
   private float kickBypassMotion;

   public VanillaFlight() {
      super("Vanilla");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "KickBypassMode");
      String[] var1 = new String[]{"Motion", "Packet"};
      this.kickBypassModeValue = (new ListValue(var10003, var1, "Packet")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)VanillaFlight.this.kickBypassValue.get();
         }
      });
      this.kickBypassMotionSpeedValue = (new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "KickBypass-MotionSpeed"), 0.0626F, 0.05F, 0.1F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Intrinsics.areEqual((Object)VanillaFlight.this.kickBypassModeValue.get(), (Object)"Motion") && (Boolean)VanillaFlight.this.kickBypassValue.get();
         }
      });
      this.keepAliveValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "KeepAlive"), false);
      this.noClipValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "NoClip"), false);
      this.spoofValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "SpoofGround"), false);
   }

   public void onEnable() {
      this.packets = 0;
      this.kickBypassMotion = 0.0F;
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.keepAliveValue.get()) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C00PacketKeepAlive()));
      }

      if ((Boolean)this.noClipValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_70145_X = true;
      }

      if ((Boolean)this.kickBypassValue.get() && this.kickBypassModeValue.get() == "Motion") {
         this.kickBypassMotion = ((Number)this.kickBypassMotionSpeedValue.get()).floatValue();
         if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0) {
            this.kickBypassMotion = -this.kickBypassMotion;
         }

         if (!MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e && !MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)this.kickBypassMotion;
         }
      }

      if ((Boolean)this.smoothValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
         MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(((Number)this.speedValue.get()).floatValue() * 0.05F);
      } else {
         MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
         MovementUtils.INSTANCE.resetMotion(true);
         if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
            var2.field_70181_x += ((Number)this.vspeedValue.get()).doubleValue();
         }

         if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
            var3.field_70181_x -= ((Number)this.vspeedValue.get()).doubleValue();
         }

         MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer) {
         if ((Boolean)this.spoofValue.get()) {
            ((C03PacketPlayer)packet).field_149474_g = true;
         }

         int var3 = this.packets++;
         if (var3 >= 40 && (Boolean)this.kickBypassValue.get()) {
            this.packets = 0;
            if (this.kickBypassModeValue.get() == "Packet") {
               MovementUtils.INSTANCE.handleVanillaKickBypass();
            }
         }
      }

   }
}
