package net.ccbluex.liquidbounce.features.module.modules.movement.flights.zonecraft;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/zonecraft/ZoneCraftFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "timerBoostValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onMove", "", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"}
)
public final class ZoneCraftFlight extends FlightMode {
   @NotNull
   private final BoolValue timerBoostValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "TimerBoost"), false);

   public ZoneCraftFlight() {
      super("ZoneCraft");
   }

   public void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
         event.setY(0.42);
      } else {
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         event.setY((double)0.0F);
         if ((Boolean)this.timerBoostValue.get()) {
            if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 20 < 10) {
               MinecraftInstance.mc.field_71428_T.field_74278_d = 1.25F;
            } else {
               MinecraftInstance.mc.field_71428_T.field_74278_d = 0.8F;
            }
         }

         switch (MinecraftInstance.mc.field_71439_g.field_70173_aa % 20) {
            case 1:
               MovementUtils.INSTANCE.strafe(0.33396F);
               break;
            case 9:
               MovementUtils.INSTANCE.strafe(MovementUtils.INSTANCE.getSpeed() * 1.125F);
         }
      }

   }
}
