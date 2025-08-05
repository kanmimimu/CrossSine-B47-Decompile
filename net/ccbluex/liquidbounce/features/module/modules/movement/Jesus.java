package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "Jesus",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020 H\u0007J\u0012\u0010!\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\"H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006#"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Jesus;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jumpMotionValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "nextTick", "", "noJumpValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "isLiquidBlock", "bb", "Lnet/minecraft/util/AxisAlignedBB;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class Jesus extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final BoolValue noJumpValue;
   @NotNull
   private final Value jumpMotionValue;
   private boolean nextTick;
   @NotNull
   private final MSTimer msTimer;

   public Jesus() {
      String[] var1 = new String[]{"Vanilla", "NCP", "Jump", "AAC", "AACFly", "AAC3.3.11", "AAC4.2.1", "Horizon1.4.6", "Spartan", "Twilight", "Matrix", "Medusa", "Vulcan", "Dolphin", "Legit"};
      this.modeValue = new ListValue("Mode", var1, "Vanilla");
      this.noJumpValue = new BoolValue("NoJump", false);
      this.jumpMotionValue = (new FloatValue("JumpMotion", 0.5F, 0.1F, 1.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Jesus.this.getModeValue().equals("Jump") || Jesus.this.getModeValue().equals("AACFly");
         }
      });
      this.msTimer = new MSTimer();
   }

   @NotNull
   public final ListValue getModeValue() {
      return this.modeValue;
   }

   private final boolean isLiquidBlock(AxisAlignedBB bb) {
      return BlockUtils.collideBlock(bb, null.INSTANCE);
   }

   // $FF: synthetic method
   static boolean isLiquidBlock$default(Jesus var0, AxisAlignedBB var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         AxisAlignedBB var4 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
         Intrinsics.checkNotNullExpressionValue(var4, "mc.thePlayer.entityBoundingBox");
         var1 = var4;
      }

      return var0.isLiquidBlock(var1);
   }

   @EventTarget
   public final void onUpdate(@Nullable UpdateEvent event) {
      if (MinecraftInstance.mc.field_71439_g != null && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
         BlockPos blockPos = MinecraftInstance.mc.field_71439_g.func_180425_c().func_177977_b();
         String var5 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var5.hashCode()) {
            case -2011701869:
               if (var5.equals("spartan") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                     EntityPlayerSP var22 = MinecraftInstance.mc.field_71439_g;
                     var22.field_70181_x += 0.15;
                     return;
                  }

                  Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                  Block blockUp = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                  if (blockUp instanceof BlockLiquid) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                  } else if (block instanceof BlockLiquid) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  }

                  MinecraftInstance.mc.field_71439_g.field_70122_E = true;
                  EntityPlayerSP var26 = MinecraftInstance.mc.field_71439_g;
                  var26.field_70159_w *= 1.085;
                  var26 = MinecraftInstance.mc.field_71439_g;
                  var26.field_70179_y *= 1.085;
               }

               return;
            case -1081239615:
               if (var5.equals("matrix") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
                  if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.09;
                     return;
                  }

                  Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                  Block blockUp = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                  if (blockUp instanceof BlockLiquid) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                  } else if (block instanceof BlockLiquid) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = (double)0.0F;
                  }

                  EntityPlayerSP var6 = MinecraftInstance.mc.field_71439_g;
                  var6.field_70159_w *= 1.15;
                  var6 = MinecraftInstance.mc.field_71439_g;
                  var6.field_70179_y *= 1.15;
               }

               return;
            case -1078019017:
               if (!var5.equals("medusa")) {
                  return;
               }
               break;
            case -805359837:
               if (!var5.equals("vulcan")) {
                  return;
               }
               break;
            case 96323:
               if (var5.equals("aac")) {
                  if (!MinecraftInstance.mc.field_71439_g.field_70122_E && BlockUtils.getBlock(blockPos) == Blocks.field_150355_j || MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                     if (!MinecraftInstance.mc.field_71439_g.func_70051_ag()) {
                        EntityPlayerSP var14 = MinecraftInstance.mc.field_71439_g;
                        var14.field_70159_w *= 0.99999;
                        var14 = MinecraftInstance.mc.field_71439_g;
                        var14.field_70181_x *= (double)0.0F;
                        var14 = MinecraftInstance.mc.field_71439_g;
                        var14.field_70179_y *= 0.99999;
                        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                           MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((float)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1)))) / 8.0F);
                        }
                     } else {
                        EntityPlayerSP var17 = MinecraftInstance.mc.field_71439_g;
                        var17.field_70159_w *= 0.99999;
                        var17 = MinecraftInstance.mc.field_71439_g;
                        var17.field_70181_x *= (double)0.0F;
                        var17 = MinecraftInstance.mc.field_71439_g;
                        var17.field_70179_y *= 0.99999;
                        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                           MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((float)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1)))) / 8.0F);
                        }
                     }

                     if (MinecraftInstance.mc.field_71439_g.field_70143_R >= 4.0F) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = -0.004;
                     } else if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.09;
                     }
                  }

                  if (MinecraftInstance.mc.field_71439_g.field_70737_aN != 0) {
                     MinecraftInstance.mc.field_71439_g.field_70122_E = false;
                     return;
                  }
               }

               return;
            case 108891:
               if (!var5.equals("ncp")) {
                  return;
               }
               break;
            case 3273774:
               if (var5.equals("jump") && BlockUtils.getBlock(blockPos) == Blocks.field_150355_j && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.jumpMotionValue.get()).floatValue();
               }

               return;
            case 326150744:
               if (var5.equals("aac4.2.1") && (!MinecraftInstance.mc.field_71439_g.field_70122_E && BlockUtils.getBlock(blockPos) == Blocks.field_150355_j || MinecraftInstance.mc.field_71439_g.func_70090_H())) {
                  EntityPlayerSP var13 = MinecraftInstance.mc.field_71439_g;
                  var13.field_70181_x *= (double)0.0F;
                  MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.08F;
                  if (MinecraftInstance.mc.field_71439_g.field_70143_R > 0.0F) {
                     return;
                  }

                  if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                     MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = true;
                     return;
                  }
               }

               return;
            case 1492139161:
               if (var5.equals("aac3.3.11") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  EntityPlayerSP var10 = MinecraftInstance.mc.field_71439_g;
                  var10.field_70159_w *= 1.17;
                  var10 = MinecraftInstance.mc.field_71439_g;
                  var10.field_70179_y *= 1.17;
                  if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                     MinecraftInstance.mc.field_71439_g.field_70181_x = 0.24;
                  } else if (MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c() != Blocks.field_150350_a) {
                     var10 = MinecraftInstance.mc.field_71439_g;
                     var10.field_70181_x += 0.04;
                     return;
                  }

                  return;
               }

               return;
            case 1650323088:
               if (var5.equals("twilight") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  EntityPlayerSP var8 = MinecraftInstance.mc.field_71439_g;
                  var8.field_70159_w *= 1.04;
                  var8 = MinecraftInstance.mc.field_71439_g;
                  var8.field_70179_y *= 1.04;
                  MovementUtils.INSTANCE.strafe();
               }

               return;
            case 1837070814:
               if (var5.equals("dolphin") && MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                  EntityPlayerSP block = MinecraftInstance.mc.field_71439_g;
                  block.field_70181_x += (double)0.04F;
               }

               return;
            case 2008740100:
               if (var5.equals("horizon1.4.6")) {
                  MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = MinecraftInstance.mc.field_71439_g.func_70090_H();
                  if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                     MovementUtils.INSTANCE.strafe();
                     if (MovementUtils.INSTANCE.isMoving() && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        EntityPlayerSP block = MinecraftInstance.mc.field_71439_g;
                        block.field_70181_x += 0.13;
                     }

                     return;
                  }
               }

               return;
            default:
               return;
         }

         if (isLiquidBlock$default(this, (AxisAlignedBB)null, 1, (Object)null) && MinecraftInstance.mc.field_71439_g.func_70055_a(Material.field_151579_a)) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.08;
         }

      }
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
         String var3 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         if (Intrinsics.areEqual((Object)var3, (Object)"aacfly")) {
            event.setY((double)((Number)this.jumpMotionValue.get()).floatValue());
            MinecraftInstance.mc.field_71439_g.field_70181_x = (double)((Number)this.jumpMotionValue.get()).floatValue();
         } else if (Intrinsics.areEqual((Object)var3, (Object)"twilight")) {
            event.setY(0.01);
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.01;
         }

      }
   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71439_g.func_174813_aQ() != null) {
         if (event.getBlock() instanceof BlockLiquid && !isLiquidBlock$default(this, (AxisAlignedBB)null, 1, (Object)null) && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
            String var3 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (var3.hashCode()) {
               case -1078019017:
                  if (!var3.equals("medusa")) {
                     return;
                  }
                  break;
               case -805359837:
                  if (!var3.equals("vulcan")) {
                     return;
                  }
                  break;
               case 108891:
                  if (!var3.equals("ncp")) {
                     return;
                  }
                  break;
               case 3273774:
                  if (!var3.equals("jump")) {
                     return;
                  }
                  break;
               case 233102203:
                  if (!var3.equals("vanilla")) {
                     return;
                  }
                  break;
               default:
                  return;
            }

            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)(event.getX() + 1), (double)(event.getY() + 1), (double)(event.getZ() + 1)));
            if (Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"Vulcan")) {
               MovementUtils.INSTANCE.strafe(MovementUtils.INSTANCE.getSpeed() * 0.39F);
            }
         }

      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null) {
         if (event.getPacket() instanceof C03PacketPlayer) {
            String var2 = (String)this.modeValue.get();
            switch (var2.hashCode()) {
               case -1994151849:
                  if (var2.equals("Medusa")) {
                     this.nextTick = !this.nextTick;
                     ((C03PacketPlayer)event.getPacket()).field_149477_b = MinecraftInstance.mc.field_71439_g.field_70163_u + (this.nextTick ? 0.1 : -0.1);
                     if (this.msTimer.hasTimePassed(1000L)) {
                        ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
                        this.msTimer.reset();
                     } else {
                        ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
                     }
                  }
                  break;
               case -1721492669:
                  if (var2.equals("Vulcan")) {
                     this.nextTick = !this.nextTick;
                     ((C03PacketPlayer)event.getPacket()).field_149477_b = MinecraftInstance.mc.field_71439_g.field_70163_u + (this.nextTick ? 0.1 : -0.1);
                     if (this.msTimer.hasTimePassed(1500L)) {
                        event.cancelEvent();
                        PacketUtils.sendPacketNoEvent((Packet)(new C03PacketPlayer(true)));
                        this.msTimer.reset();
                     } else {
                        ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
                     }
                  }
                  break;
               case 77115:
                  if (var2.equals("NCP") && this.isLiquidBlock(new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c))) {
                     this.nextTick = !this.nextTick;
                     if (this.nextTick) {
                        Packet var3 = event.getPacket();
                        ((C03PacketPlayer)var3).field_149477_b -= 0.001;
                     }
                  }
            }
         }

      }
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (MinecraftInstance.mc.field_71439_g != null) {
         Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.01, MinecraftInstance.mc.field_71439_g.field_70161_v));
         if (((Boolean)this.noJumpValue.get() || Intrinsics.areEqual((Object)this.modeValue.get(), (Object)"Vulcan")) && block instanceof BlockLiquid) {
            event.cancelEvent();
         }

      }
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
