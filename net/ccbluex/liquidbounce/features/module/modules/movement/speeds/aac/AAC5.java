package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC5;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onUpdate", "CrossSine"}
)
public final class AAC5 extends SpeedMode {
   public AAC5() {
      super("AAC5");
   }

   public void onUpdate() {
      if (MovementUtils.INSTANCE.isMoving()) {
         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, (double)0.0F, 6, (Object)null);
            MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.0201F;
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.94F;
         }

         if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 0.7 && (double)MinecraftInstance.mc.field_71439_g.field_70143_R < 1.3) {
            MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02F;
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.8F;
         }

      }
   }

   public void onDisable() {
      EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNull(var10000);
      var10000.field_71102_ce = 0.02F;
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }
}
