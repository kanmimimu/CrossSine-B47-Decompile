package net.ccbluex.liquidbounce.features.module.modules.movement.flights.verus;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/verus/VerusBasicFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "verusMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onMove", "", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class VerusBasicFlight extends FlightMode {
   @NotNull
   private final ListValue verusMode;

   public VerusBasicFlight() {
      super("VerusBasic");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "Mode");
      String[] var1 = new String[]{"Packet1", "Packet2"};
      this.verusMode = new ListValue(var10003, var1, "Packet1");
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer && this.verusMode.get() == "Packet1") {
         ((C03PacketPlayer)packet).field_149474_g = true;
      }

   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.verusMode.get() == "Packet1") {
         if (MinecraftInstance.mc.field_71439_g.field_70181_x < 0.4) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         }

         MinecraftInstance.mc.field_71439_g.field_70122_E = true;
      }

   }

   public void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      BlockPos pos = MinecraftInstance.mc.field_71439_g.func_180425_c().func_177963_a((double)0.0F, (double)-1.5F, (double)0.0F);
      PacketUtils.sendPacketNoEvent((Packet)(new C08PacketPlayerBlockPlacement(pos, 1, new ItemStack(Blocks.field_150348_b.func_180665_b((World)MinecraftInstance.mc.field_71441_e, pos)), 0.0F, 0.5F + (float)Math.random() * 0.44F, 0.0F)));
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         event.setY(0.42);
      } else {
         event.setY((double)0.0F);
         MovementUtils.INSTANCE.strafe(0.35F);
      }

   }
}
