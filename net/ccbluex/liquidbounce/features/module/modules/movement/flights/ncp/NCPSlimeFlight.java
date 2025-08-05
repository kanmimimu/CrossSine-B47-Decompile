package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import java.util.LinkedList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0019H\u0016J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020'H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "firstlaunch", "", "needreset", "packetBuffer", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "packets", "", "stage", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight$Stage;", "swingModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "test", "", "ticks", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerBoostValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "vanillabypass", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Stage", "CrossSine"}
)
public final class NCPSlimeFlight extends FlightMode {
   @NotNull
   private final BoolValue timerBoostValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DoTimer"), true);
   @NotNull
   private final ListValue swingModeValue;
   @NotNull
   private Stage stage;
   private int ticks;
   private int packets;
   @NotNull
   private final MSTimer timer;
   private boolean firstlaunch;
   private boolean needreset;
   private int vanillabypass;
   private double test;
   @NotNull
   private final LinkedList packetBuffer;

   public NCPSlimeFlight() {
      super("NCPSlime");
      String var10003 = Intrinsics.stringPlus(this.getValuePrefix(), "SwingMode");
      String[] var1 = new String[]{"Normal", "Packet"};
      this.swingModeValue = new ListValue(var10003, var1, "Normal");
      this.stage = NCPSlimeFlight.Stage.WAITING;
      this.timer = new MSTimer();
      this.firstlaunch = true;
      this.test = (double)1.0F;
      this.packetBuffer = new LinkedList();
   }

   public void onEnable() {
      this.test = (double)1.0F;
      this.needreset = false;
      this.firstlaunch = true;
      this.vanillabypass = 0;
      this.packets = 0;
      this.ticks = 0;
      this.packetBuffer.clear();
      this.timer.reset();
      if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
         this.stage = NCPSlimeFlight.Stage.WAITING;
         MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
      } else {
         this.stage = NCPSlimeFlight.Stage.INFFLYING;
      }

   }

   public void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.packetBuffer.clear();
      this.timer.reset();
   }

   public void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.stage != NCPSlimeFlight.Stage.WAITING) {
         Packet packet = event.getPacket();
         if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = true;
            this.packetBuffer.add(packet);
            event.cancelEvent();
         }

         if (packet instanceof S12PacketEntityVelocity) {
            if (MinecraftInstance.mc.field_71439_g == null) {
               return;
            }

            WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
            Entity var3 = var10000 == null ? null : var10000.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
            if (var3 == null) {
               return;
            }

            if (!Intrinsics.areEqual((Object)var3, (Object)MinecraftInstance.mc.field_71439_g)) {
               return;
            }

            event.cancelEvent();
         }

      }
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;

      for(Packet packet : this.packetBuffer) {
         Intrinsics.checkNotNullExpressionValue(packet, "packet");
         PacketUtils.sendPacketNoEvent(packet);
      }

      this.packetBuffer.clear();
   }

   public void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.timer.hasTimePassed((long)(Math.random() * (double)1000))) {
         this.timer.reset();

         for(Packet packet : this.packetBuffer) {
            Intrinsics.checkNotNullExpressionValue(packet, "packet");
            PacketUtils.sendPacketNoEvent(packet);
         }

         this.packetBuffer.clear();
      }

      switch (NCPSlimeFlight.WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
         case 1:
            if (MinecraftInstance.mc.field_71439_g.field_70163_u >= this.getFlight().getLaunchY() + 0.8) {
               if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0F), 0);
                  EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNullExpressionValue(var5, "mc.thePlayer");
                  MovingObjectPosition movingObjectPosition = EntityExtensionKt.rayTraceWithCustomRotation((Entity)var5, (double)4.5F, MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0F);
                  if ((movingObjectPosition == null ? null : movingObjectPosition.field_72313_a) != MovingObjectType.BLOCK) {
                     return;
                  }

                  BlockPos blockPos = movingObjectPosition.func_178782_a();
                  EnumFacing enumFacing = movingObjectPosition.field_178784_b;
                  if (MinecraftInstance.mc.field_71442_b.func_180512_c(blockPos, enumFacing)) {
                     this.stage = NCPSlimeFlight.Stage.FLYING;
                  }

                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
               } else {
                  RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0F), 0);
                  int slot = -1;
                  int oldSlot = 0;

                  while(oldSlot < 9) {
                     int j = oldSlot++;
                     if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(j) != null && MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() instanceof ItemBlock) {
                        Integer var10000 = PlayerUtils.INSTANCE.findSlimeBlock();
                        Intrinsics.checkNotNull(var10000);
                        slot = var10000;
                        break;
                     }
                  }

                  if (slot == -1) {
                     this.getFlight().setState(false);
                     return;
                  }

                  oldSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
                  MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = slot;
                  EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNullExpressionValue(var7, "mc.thePlayer");
                  MovingObjectPosition movingObjectPosition = EntityExtensionKt.rayTraceWithCustomRotation((Entity)var7, (double)4.5F, MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0F);
                  if ((movingObjectPosition == null ? null : movingObjectPosition.field_72313_a) != MovingObjectType.BLOCK) {
                     return;
                  }

                  BlockPos blockPos = movingObjectPosition.func_178782_a();
                  EnumFacing enumFacing = movingObjectPosition.field_178784_b;
                  Vec3 hitVec = movingObjectPosition.field_72307_f;
                  Intrinsics.checkNotNullExpressionValue(hitVec, "movingObjectPosition.hitVec");
                  if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), blockPos, enumFacing, hitVec)) {
                     String var11 = ((String)this.swingModeValue.get()).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                     if (Intrinsics.areEqual((Object)var11, (Object)"normal")) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                     } else if (Intrinsics.areEqual((Object)var11, (Object)"packet")) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                     }
                  }

                  MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
               }
            }
            break;
         case 2:
         case 3:
            if ((Boolean)this.timerBoostValue.get()) {
               int movingObjectPosition = this.ticks++;
               movingObjectPosition = this.ticks;
               if (1 <= movingObjectPosition ? movingObjectPosition < 11 : false) {
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 2.0F;
               } else if (10 <= movingObjectPosition ? movingObjectPosition < 16 : false) {
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 0.4F;
               }

               if (this.ticks >= 15) {
                  this.ticks = 0;
                  MinecraftInstance.mc.field_71428_T.field_74278_d = 0.6F;
               }
            } else {
               MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
            }
      }

   }

   public void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      switch (NCPSlimeFlight.WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
         case 1:
            if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY() + (double)100) {
               event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)event.getX() + (double)1.0F, this.getFlight().getLaunchY(), (double)event.getZ() + (double)1.0F));
            }
            break;
         case 2:
            if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY() + (double)1) {
               event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)event.getX() + (double)1.0F, this.getFlight().getLaunchY(), (double)event.getZ() + (double)1.0F));
            }
            break;
         case 3:
            if (event.getBlock() instanceof BlockAir && (double)event.getY() <= MinecraftInstance.mc.field_71439_g.field_70163_u) {
               event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)event.getX() + (double)1.0F, this.getFlight().getLaunchY(), (double)event.getZ() + (double)1.0F));
            }
      }

   }

   public void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.stage != NCPSlimeFlight.Stage.WAITING) {
         event.cancelEvent();
      }
   }

   public void onStep(@NotNull StepEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.stage != NCPSlimeFlight.Stage.WAITING) {
         event.setStepHeight(0.0F);
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight$Stage;", "", "(Ljava/lang/String;I)V", "WAITING", "FLYING", "INFFLYING", "CrossSine"}
   )
   public static enum Stage {
      WAITING,
      FLYING,
      INFFLYING;

      // $FF: synthetic method
      private static final Stage[] $values() {
         Stage[] var0 = new Stage[]{WAITING, FLYING, INFFLYING};
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
         int[] var0 = new int[NCPSlimeFlight.Stage.values().length];
         var0[NCPSlimeFlight.Stage.WAITING.ordinal()] = 1;
         var0[NCPSlimeFlight.Stage.FLYING.ordinal()] = 2;
         var0[NCPSlimeFlight.Stage.INFFLYING.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
