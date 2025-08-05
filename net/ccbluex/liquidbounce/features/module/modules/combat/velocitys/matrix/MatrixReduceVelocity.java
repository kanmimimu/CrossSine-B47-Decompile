package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/matrix/MatrixReduceVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class MatrixReduceVelocity extends VelocityMode {
   public MatrixReduceVelocity() {
      super("MatrixReduce");
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.field_70737_aN > 0) {
         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 6) {
               EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
               var2.field_70159_w *= 0.7;
               var2 = MinecraftInstance.mc.field_71439_g;
               var2.field_70179_y *= 0.7;
            }

            if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 5) {
               EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
               var4.field_70159_w *= 0.8;
               var4 = MinecraftInstance.mc.field_71439_g;
               var4.field_70179_y *= 0.8;
            }
         } else if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 10) {
            EntityPlayerSP var6 = MinecraftInstance.mc.field_71439_g;
            var6.field_70159_w *= 0.6;
            var6 = MinecraftInstance.mc.field_71439_g;
            var6.field_70179_y *= 0.6;
         }
      }

   }
}
