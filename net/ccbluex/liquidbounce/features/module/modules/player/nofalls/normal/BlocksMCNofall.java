package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/BlocksMCNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "bmcModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "damaged", "", "fallDistance", "", "onEnable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"}
)
public final class BlocksMCNofall extends NoFallMode {
   @NotNull
   private final ListValue bmcModeValue;
   private double fallDistance;
   private boolean damaged;

   public BlocksMCNofall() {
      super("BlocksMC");
      String[] var1 = new String[]{"Packet1", "Packet2"};
      this.bmcModeValue = new ListValue("BMC-Packet-Mode", var1, "Packet1");
   }

   public void onEnable() {
      this.damaged = false;
   }

   public void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getEventState() == EventState.PRE) {
         if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.fallDistance = (double)0.0F;
         } else {
            this.fallDistance += RangesKt.coerceAtLeast(-MinecraftInstance.mc.field_71439_g.field_70181_x, (double)0.0F);
         }

         if (this.bmcModeValue.equals("Packet1") && this.fallDistance > 3.7 && (!KillAura.INSTANCE.getState() || KillAura.INSTANCE.getCurrentTarget() == null) && (!SilentAura.INSTANCE.getState() || SilentAura.INSTANCE.getTarget() == null)) {
            event.setOnGround(true);
            this.fallDistance = (double)0.0F;
         }

         if (this.bmcModeValue.equals("Packet2") && this.fallDistance > (double)3.0F && (!KillAura.INSTANCE.getState() || KillAura.INSTANCE.getCurrentTarget() == null) && (!SilentAura.INSTANCE.getState() || SilentAura.INSTANCE.getTarget() == null)) {
            event.setOnGround(true);
            this.fallDistance = (double)0.0F;
         }
      }

   }
}
