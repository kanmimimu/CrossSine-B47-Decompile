package net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/vanilla/FastFallPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class FastFallPhase extends PhaseMode {
   public FastFallPhase() {
      super("FastFall");
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      MinecraftInstance.mc.field_71439_g.field_70145_X = true;
      EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
      var2.field_70181_x -= (double)10.0F;
      MinecraftInstance.mc.field_71439_g.func_70634_a(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)0.5F, MinecraftInstance.mc.field_71439_g.field_70161_v);
      EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
      AxisAlignedBB var3 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
      Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer.entityBoundingBox");
      var10000.field_70122_E = BlockUtils.collideBlockIntersects(var3, null.INSTANCE);
   }
}
