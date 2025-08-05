package net.ccbluex.liquidbounce.features.module.modules.movement.flights.vulcan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan3Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "flags", "", "groundX", "", "groundY", "groundZ", "modifyTicks", "stage", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan3Flight$FlyStage;", "ticks", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "FlyStage", "CrossSine"}
)
public final class Vulcan3Flight extends FlightMode {
   @NotNull
   private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 2.0F, 1.0F, 3.0F);
   private int ticks;
   private int modifyTicks;
   @NotNull
   private FlyStage stage;
   private int flags;
   private double groundX;
   private double groundY;
   private double groundZ;

   public Vulcan3Flight() {
      super("Vulcan3");
      this.stage = Vulcan3Flight.FlyStage.WAITING;
   }

   public void onEnable() {
      this.ticks = 0;
      this.modifyTicks = 0;
      this.flags = 0;
      MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, (double)Math.round(MinecraftInstance.mc.field_71439_g.field_70163_u * (double)2) / (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v);
      this.stage = Vulcan3Flight.FlyStage.WAITING;
      ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §aPlease press Sneak before you land on ground!");
      ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §aYou can go Up/Down by pressing Jump/Sneak");
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      int var2 = this.ticks++;
      var2 = this.modifyTicks++;
      MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
      MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
      switch (Vulcan3Flight.WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
         case 1:
         case 2:
            if (this.stage == Vulcan3Flight.FlyStage.FLYING) {
               MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
            } else {
               MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            }

            if (this.ticks == 2 && GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A) && this.modifyTicks >= 6 && MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)0.5F, (double)0.0F)).isEmpty()) {
               MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)0.5F, MinecraftInstance.mc.field_71439_g.field_70161_v);
               this.modifyTicks = 0;
            }

            if (!MovementUtils.INSTANCE.isMoving() && this.ticks == 1 && (GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E) || GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74314_A)) && this.modifyTicks >= 5) {
               double playerYaw = (double)MinecraftInstance.mc.field_71439_g.field_70177_z * Math.PI / (double)180;
               MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + 0.05 * -Math.sin(playerYaw), MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + 0.05 * Math.cos(playerYaw));
            }

            if (this.ticks == 2 && GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E) && this.modifyTicks >= 6 && MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-0.5F, (double)0.0F)).isEmpty()) {
               MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)0.5F, MinecraftInstance.mc.field_71439_g.field_70161_v);
               this.modifyTicks = 0;
            } else if (this.ticks == 2 && GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74311_E) && !MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-0.5F, (double)0.0F)).isEmpty()) {
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + 0.05, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.42, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.7532, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
               PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
               MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v);
               this.stage = Vulcan3Flight.FlyStage.WAIT_APPLY;
               this.modifyTicks = 0;
               this.groundY = MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1.0F;
               this.groundX = MinecraftInstance.mc.field_71439_g.field_70165_t;
               this.groundZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
               ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §aWaiting to land...");
            }

            MinecraftInstance.mc.field_71439_g.field_70122_E = true;
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
            break;
         case 3:
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            MovementUtils.INSTANCE.resetMotion(true);
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
            if (this.modifyTicks >= 10) {
               double playerYaw = (double)MinecraftInstance.mc.field_71439_g.field_70177_z * Math.PI / (double)180;
               if (this.modifyTicks % 2 != 0) {
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + 0.1 * -Math.sin(playerYaw), MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + 0.1 * Math.cos(playerYaw));
               } else {
                  MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t - 0.1 * -Math.sin(playerYaw), MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v - 0.1 * Math.cos(playerYaw));
                  if (this.modifyTicks >= 16 && this.ticks == 2) {
                     this.modifyTicks = 16;
                     MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)0.5F, MinecraftInstance.mc.field_71439_g.field_70161_v);
                  }
               }
            }
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer) {
         if (this.ticks > 2) {
            this.ticks = 0;
            ((C03PacketPlayer)packet).field_149477_b += (double)0.5F;
         }

         ((C03PacketPlayer)packet).field_149474_g = true;
      } else if (packet instanceof S08PacketPlayerPosLook) {
         if (this.stage == Vulcan3Flight.FlyStage.WAITING) {
            int var3 = this.flags++;
            if (this.flags >= 2) {
               this.flags = 0;
               this.stage = Vulcan3Flight.FlyStage.FLYING;
            }
         }

         if (this.stage == Vulcan3Flight.FlyStage.WAIT_APPLY && Math.sqrt((((S08PacketPlayerPosLook)packet).field_148940_a - this.groundX) * (((S08PacketPlayerPosLook)packet).field_148940_a - this.groundX) + (((S08PacketPlayerPosLook)packet).field_148939_c - this.groundZ) * (((S08PacketPlayerPosLook)packet).field_148939_c - this.groundZ)) < 1.4 && ((S08PacketPlayerPosLook)packet).field_148938_b >= this.groundY - (double)0.5F) {
            this.getFlight().setState(false);
            return;
         }

         event.cancelEvent();
      } else if (packet instanceof C0BPacketEntityAction) {
         event.cancelEvent();
      }

   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan3Flight$FlyStage;", "", "(Ljava/lang/String;I)V", "WAITING", "FLYING", "WAIT_APPLY", "CrossSine"}
   )
   public static enum FlyStage {
      WAITING,
      FLYING,
      WAIT_APPLY;

      // $FF: synthetic method
      private static final FlyStage[] $values() {
         FlyStage[] var0 = new FlyStage[]{WAITING, FLYING, WAIT_APPLY};
         return var0;
      }
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[Vulcan3Flight.FlyStage.values().length];
         var0[Vulcan3Flight.FlyStage.FLYING.ordinal()] = 1;
         var0[Vulcan3Flight.FlyStage.WAITING.ordinal()] = 2;
         var0[Vulcan3Flight.FlyStage.WAIT_APPLY.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
