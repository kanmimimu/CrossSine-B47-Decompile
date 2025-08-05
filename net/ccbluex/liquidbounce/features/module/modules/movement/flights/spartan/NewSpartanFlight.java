package net.ccbluex.liquidbounce.features.module.modules.movement.flights.spartan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/spartan/NewSpartanFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class NewSpartanFlight extends FlightMode {
   @NotNull
   private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0F, 0.5F, 8.0F);

   public NewSpartanFlight() {
      super("NewSpartan");
   }

   public void onEnable() {
      MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
      MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
      MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v);
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
      MovementUtils.INSTANCE.resetMotion(true);
      if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
         EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
         ++var2.field_70181_x;
      } else if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         --var3.field_70181_x;
      } else {
         MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer) {
         ((C03PacketPlayer)packet).field_149474_g = true;
      }

   }
}
