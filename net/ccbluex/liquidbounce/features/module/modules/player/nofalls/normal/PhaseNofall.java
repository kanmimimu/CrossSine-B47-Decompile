package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/PhaseNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "phaseOffsetValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onNoFall", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class PhaseNofall extends NoFallMode {
   @NotNull
   private final IntegerValue phaseOffsetValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "PhaseOffset"), 1, 0, 5);

   public PhaseNofall() {
      super("Phase");
   }

   public void onNoFall(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.field_70143_R > (float)(3 + ((Number)this.phaseOffsetValue.get()).intValue())) {
         EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var4, "mc.thePlayer");
         BlockPos var3 = (new FallingPlayer((EntityPlayer)var4)).findCollision(5);
         if (var3 == null) {
            return;
         }

         BlockPos fallPos = var3;
         if ((double)var3.func_177956_o() - MinecraftInstance.mc.field_71439_g.field_70181_x / (double)20.0F < MinecraftInstance.mc.field_71439_g.field_70163_u) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.05F;
            Timer var7 = new Timer();
            long var8 = 100L;
            TimerTask var6 = new PhaseNofall$onNoFall$$inlined$schedule$1(fallPos);
            var7.schedule(var6, var8);
         }
      }

   }
}
