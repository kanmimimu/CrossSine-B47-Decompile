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
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "doCancel", "", "isSuccess", "stage", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight$FlyStage;", "startX", "", "startY", "startZ", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "vticks", "", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "FlyStage", "CrossSine"}
)
public final class Vulcan2Flight extends FlightMode {
   @NotNull
   private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 1.0F, 0.1F, 6.0F);
   private boolean isSuccess;
   private int vticks;
   private boolean doCancel;
   @NotNull
   private FlyStage stage;
   private double startX;
   private double startZ;
   private double startY;

   public Vulcan2Flight() {
      super("Vulcan2");
      this.stage = Vulcan2Flight.FlyStage.FLYING;
   }

   public void onEnable() {
      this.vticks = 0;
      this.doCancel = false;
      if (MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1 != (double)0.0F) {
         this.getFlight().setState(false);
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §cPlease stand on a solid block to fly!");
         this.isSuccess = true;
      } else {
         this.stage = Vulcan2Flight.FlyStage.FLYING;
         this.isSuccess = false;
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §aPlease press Sneak before you land on ground!");
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §7Tips: DO NOT Use killaura when you're flying!");
         this.startX = MinecraftInstance.mc.field_71439_g.field_70165_t;
         this.startY = MinecraftInstance.mc.field_71439_g.field_70163_u;
         this.startZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
      }
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      if (!this.isSuccess) {
         MinecraftInstance.mc.field_71439_g.func_70107_b(this.startX, this.startY, this.startZ);
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §cFly attempt Failed...");
         ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §cIf it keeps happen, DONT use it again in CURRENT gameplay");
      }

   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      switch (Vulcan2Flight.WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
         case 1:
            this.isSuccess = false;
            MovementUtils.INSTANCE.resetMotion(true);
            MovementUtils.INSTANCE.strafe(((Number)this.timerValue.get()).floatValue());
            this.doCancel = true;
            if (MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e) {
               MovementUtils.INSTANCE.strafe(0.45F);
            }

            if (MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e && MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 1) {
               double fixedY = MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1;
               Block var10000 = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY - (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v));
               if (var10000 == null) {
                  return;
               }

               Block underBlock2 = var10000;
               if (underBlock2.func_149730_j()) {
                  this.stage = Vulcan2Flight.FlyStage.WAIT_APPLY;
                  MovementUtils.INSTANCE.resetMotion(true);
                  MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
                  this.doCancel = false;
                  MinecraftInstance.mc.field_71439_g.field_70122_E = false;
                  double fixedX = MinecraftInstance.mc.field_71439_g.field_70165_t - MinecraftInstance.mc.field_71439_g.field_70165_t % (double)1;
                  double fixedZ = MinecraftInstance.mc.field_71439_g.field_70161_v - MinecraftInstance.mc.field_71439_g.field_70161_v % (double)1;
                  if (fixedX > (double)0.0F) {
                     fixedX += (double)0.5F;
                  } else {
                     fixedX -= (double)0.5F;
                  }

                  if (fixedZ > (double)0.0F) {
                     fixedZ += (double)0.5F;
                  } else {
                     fixedZ -= (double)0.5F;
                  }

                  MinecraftInstance.mc.field_71439_g.func_70107_b(fixedX, fixedY, fixedZ);
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
                  this.doCancel = true;
                  ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §aWaiting for landing...");
               } else {
                  ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §cYou can only land on a solid block!");
               }
            }
            break;
         case 2:
            int fixedY = this.vticks++;
            this.doCancel = false;
            if (this.vticks == 60) {
               ClientUtils.INSTANCE.displayChatMessage("§8[§c§lVulcan-Fly§8] §cSeems took a long time! Please turn off the Fly manually");
            }

            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            MovementUtils.INSTANCE.resetMotion(true);
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0F;
            double fixedY = MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1;
            if (MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-10.0F, (double)0.0F)).isEmpty() && MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-12.0F, (double)0.0F)).isEmpty()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY - (double)10, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            } else {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY - (double)1024, MinecraftInstance.mc.field_71439_g.field_70161_v, true)));
            }

            this.doCancel = true;
      }

   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C03PacketPlayer) {
         if (this.doCancel) {
            event.cancelEvent();
            this.doCancel = false;
         }

         ((C03PacketPlayer)packet).field_149474_g = true;
      } else if (packet instanceof S08PacketPlayerPosLook) {
         if (this.stage == Vulcan2Flight.FlyStage.WAIT_APPLY && Math.sqrt((((S08PacketPlayerPosLook)packet).field_148940_a - MinecraftInstance.mc.field_71439_g.field_70165_t) * (((S08PacketPlayerPosLook)packet).field_148940_a - MinecraftInstance.mc.field_71439_g.field_70165_t) + (((S08PacketPlayerPosLook)packet).field_148938_b - MinecraftInstance.mc.field_71439_g.field_70163_u) * (((S08PacketPlayerPosLook)packet).field_148938_b - MinecraftInstance.mc.field_71439_g.field_70163_u) + (((S08PacketPlayerPosLook)packet).field_148939_c - MinecraftInstance.mc.field_71439_g.field_70161_v) * (((S08PacketPlayerPosLook)packet).field_148939_c - MinecraftInstance.mc.field_71439_g.field_70161_v)) < 1.4) {
            this.isSuccess = true;
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
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight$FlyStage;", "", "(Ljava/lang/String;I)V", "FLYING", "WAIT_APPLY", "CrossSine"}
   )
   public static enum FlyStage {
      FLYING,
      WAIT_APPLY;

      // $FF: synthetic method
      private static final FlyStage[] $values() {
         FlyStage[] var0 = new FlyStage[]{FLYING, WAIT_APPLY};
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
         int[] var0 = new int[Vulcan2Flight.FlyStage.values().length];
         var0[Vulcan2Flight.FlyStage.FLYING.ordinal()] = 1;
         var0[Vulcan2Flight.FlyStage.WAIT_APPLY.ordinal()] = 2;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
