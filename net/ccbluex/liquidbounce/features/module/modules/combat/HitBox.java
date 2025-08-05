package net.ccbluex.liquidbounce.features.module.modules.combat;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "HitBox",
   category = ModuleCategory.COMBAT
)
public class HitBox extends Module {
   public static FloatRangeValue hitbox = new FloatRangeValue("Size", 3.5F, 4.0F, 3.0F, 7.0F);

   public static double getSize() {
      double min = (double)Math.min((Float)hitbox.get().getStart(), (Float)hitbox.get().getEndInclusive());
      double max = (double)Math.max((Float)hitbox.get().getStart(), (Float)hitbox.get().getEndInclusive());
      return Math.random() * (max - min) + min;
   }

   @Nullable
   public String getTag() {
      return hitbox.get().getEndInclusive() + "-" + hitbox.get().getStart();
   }
}
