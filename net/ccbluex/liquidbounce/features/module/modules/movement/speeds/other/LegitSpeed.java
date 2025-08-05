package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/LegitSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onUpdate", "", "CrossSine"}
)
public final class LegitSpeed extends SpeedMode {
   public LegitSpeed() {
      super("Legit");
   }

   public void onUpdate() {
      if (MovementUtils.INSTANCE.isMoving() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
      }

   }
}
