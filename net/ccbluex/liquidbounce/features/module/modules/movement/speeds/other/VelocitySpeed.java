package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/VelocitySpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "CustomStrafeValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "hurtTimeValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "strafeSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onUpdate", "", "CrossSine"}
)
public final class VelocitySpeed extends SpeedMode {
   @NotNull
   private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 0, 0, 10);
   @NotNull
   private final BoolValue CustomStrafeValue = new BoolValue("CustomStrafe", false);
   @NotNull
   private final FloatValue strafeSpeed = new FloatValue("Speed", 0.4F, 0.0F, 0.7F);

   public VelocitySpeed() {
      super("Velocity");
   }

   public void onUpdate() {
      if (MinecraftInstance.mc.field_71439_g.field_70122_E && MovementUtils.INSTANCE.isMoving()) {
         MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
      }

      if (MinecraftInstance.mc.field_71439_g.field_70737_aN > ((Number)this.hurtTimeValue.get()).intValue() && (Boolean)this.CustomStrafeValue.get()) {
         MovementUtils.INSTANCE.strafe(((Number)this.strafeSpeed.get()).floatValue());
      } else if (MinecraftInstance.mc.field_71439_g.field_70737_aN > ((Number)this.hurtTimeValue.get()).intValue()) {
         MovementUtils.INSTANCE.strafe();
      }

   }
}
