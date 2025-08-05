package net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/vanilla/BlocksMCPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "tick", "", "tickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class BlocksMCPhase extends PhaseMode {
   private int tick;
   @NotNull
   private final tickTimer tickTimer = new tickTimer();

   public BlocksMCPhase() {
      super("BlocksMC");
   }

   public void onEnable() {
      this.tickTimer.reset();
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E);
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      AxisAlignedBB var3 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
      Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer.entityBoundingBox");
      boolean isInsideBlock = BlockUtils.collideBlockIntersects(var3, null.INSTANCE);
      if (isInsideBlock) {
         MinecraftInstance.mc.field_71439_g.field_70145_X = true;
         MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70122_E = true;
         if (GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A)) {
            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
            int var10 = this.tick++;
            switch (this.tick) {
               case 1:
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.42, MinecraftInstance.mc.field_71439_g.field_70161_v);
                  break;
               case 2:
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.33, MinecraftInstance.mc.field_71439_g.field_70161_v);
                  break;
               case 3:
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)0.25F, MinecraftInstance.mc.field_71439_g.field_70161_v);
                  this.tick = 0;
            }
         } else {
            this.tick = 0;
         }
      }

      if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
         MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = true;
      } else {
         MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E);
      }

      this.tickTimer.update();
      if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.tickTimer.hasTimePassed(2) && MinecraftInstance.mc.field_71439_g.field_70123_F && (!isInsideBlock || MinecraftInstance.mc.field_71439_g.func_70093_af())) {
         double direction = MovementUtils.INSTANCE.getDirection();
         double posX = -Math.sin(direction) * 0.3;
         double posZ = Math.cos(direction) * 0.3;

         for(int i = 0; i < 3; ++i) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.06, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + posX * (double)i, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + posZ * (double)i, true)));
         }

         MinecraftInstance.mc.field_71439_g.func_174826_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(posX, (double)0.0F, posZ));
         MinecraftInstance.mc.field_71439_g.func_70634_a(MinecraftInstance.mc.field_71439_g.field_70165_t + posX, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + posZ);
         this.tickTimer.reset();
      }
   }
}
