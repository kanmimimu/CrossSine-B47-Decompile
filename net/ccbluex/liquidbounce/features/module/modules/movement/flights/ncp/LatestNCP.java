package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class LatestNCP extends FlightMode {
   final BoolValue teleportValue = new BoolValue("Teleport", false);
   final BoolValue timerValue = new BoolValue("Timer", true);
   final FloatValue addValue = new FloatValue("AddSpeed", 0.0F, 0.0F, 1.5F);
   private boolean started;
   private boolean notUnder;
   private boolean clipped;

   public LatestNCP() {
      super("LatestNCP");
   }

   @EventTarget
   public void onUpdate(UpdateEvent event) {
      if ((Boolean)this.timerValue.get()) {
         if (!mc.field_71439_g.field_70122_E) {
            mc.field_71428_T.field_74278_d = 0.4F;
         } else {
            mc.field_71428_T.field_74278_d = 1.0F;
         }
      }

      AxisAlignedBB bb = mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)1.0F, (double)0.0F);
      if (!mc.field_71441_e.func_72945_a(mc.field_71439_g, bb).isEmpty() && !this.started) {
         this.notUnder = true;
         if (this.clipped) {
            return;
         }

         this.clipped = true;
         if ((Boolean)this.teleportValue.get()) {
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, false));
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.1, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, false));
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70177_z, mc.field_71439_g.field_70125_A, false));
         }
      } else {
         PlayerUtils var10000 = PlayerUtils.INSTANCE;
         switch (PlayerUtils.getOffGroundTicks()) {
            case 0:
               if (this.notUnder && this.clipped) {
                  this.started = true;
                  MovementUtils.INSTANCE.strafe((double)9.5F + (double)(Float)this.addValue.get());
                  mc.field_71439_g.field_70181_x = (double)0.42F;
                  this.notUnder = false;
               }
               break;
            case 1:
               if (this.started) {
                  MovementUtils.INSTANCE.strafe((double)8.0F + (double)(Float)this.addValue.get());
               }
         }
      }

      MovementUtils.INSTANCE.strafe();
   }

   public void onEnable() {
      this.notUnder = false;
      this.started = false;
      this.clipped = false;
   }
}
