package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.blocksmc;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/blocksmc/BlocksMC;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "damageBoostValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "damageCustomValue", "damageValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "lowHopValue", "onUpdate", "", "CrossSine"}
)
public final class BlocksMC extends SpeedMode {
   @NotNull
   private final BoolValue damageBoostValue = new BoolValue("Damage-Boost", true);
   @NotNull
   private final BoolValue damageCustomValue = new BoolValue("Custom-Boost", false);
   @NotNull
   private final FloatValue damageValue = new FloatValue("Boost-Value", 0.65F, 0.1F, 2.0F);
   @NotNull
   private final BoolValue lowHopValue = new BoolValue("LowHop", false);

   public BlocksMC() {
      super("BlocksMC");
   }

   public void onUpdate() {
      if (MovementUtils.INSTANCE.isMoving()) {
         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
            if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
               MovementUtils.INSTANCE.strafe(0.5F);
            } else {
               MovementUtils.INSTANCE.strafe(0.47F);
            }
         }

         if ((Boolean)this.lowHopValue.get() && PlayerUtils.getOffGroundTicks() == 4) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.09800000190734863;
         }

         MovementUtils.INSTANCE.strafe(MovementUtils.INSTANCE.getSpeed());
         if ((Boolean)this.damageBoostValue.get() && MinecraftInstance.mc.field_71439_g.field_70737_aN > 8) {
            MovementUtils.INSTANCE.strafe((Boolean)this.damageCustomValue.get() ? ((Number)this.damageValue.get()).floatValue() : 0.455F);
         }
      } else {
         MovementUtils.INSTANCE.resetMotion(false);
      }

   }
}
