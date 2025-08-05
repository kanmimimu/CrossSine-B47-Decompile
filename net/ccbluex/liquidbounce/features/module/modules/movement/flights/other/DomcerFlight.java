package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/DomcerFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "flyValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "ticks", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"}
)
public final class DomcerFlight extends FlightMode {
   @NotNull
   private FloatValue flyValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Vertical"), 0.5F, 0.1F, 3.0F);
   private int ticks;

   public DomcerFlight() {
      super("Domcer");
   }

   public void onEnable() {
      this.ticks = 0;
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   public void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.ticks % 10 == 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
         MovementUtils.INSTANCE.strafe(1.0F);
         event.setY(0.42);
         this.ticks = 0;
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         MovementUtils.INSTANCE.setMotion(1.1485 + Math.random() / (double)50);
      } else {
         if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && this.ticks % 2 == 1) {
            event.setY((double)((Number)this.flyValue.get()).floatValue());
            MovementUtils.INSTANCE.strafe(0.425F);
            Flight var2 = this.getFlight();
            var2.setLaunchY(var2.getLaunchY() + (double)((Number)this.flyValue.get()).floatValue());
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.95F;
            return;
         }

         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         MovementUtils.INSTANCE.strafe(0.685F);
      }

      int var3 = this.ticks++;
   }

   public void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY()) {
         event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)event.getX() + (double)1.0F, this.getFlight().getLaunchY(), (double)event.getZ() + (double)1.0F));
      }

   }

   public void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      event.cancelEvent();
   }
}
