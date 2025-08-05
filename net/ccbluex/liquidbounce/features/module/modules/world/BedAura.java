package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBed.EnumPartType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "BedAura",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u00108\u001a\u0004\u0018\u00010\"2\b\b\u0002\u00109\u001a\u00020\u001c2\b\b\u0002\u0010:\u001a\u00020\u001c2\b\b\u0002\u0010;\u001a\u00020\u001cH\u0002J\n\u0010<\u001a\u0004\u0018\u00010\"H\u0002J\b\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u00020>H\u0016J\u0012\u0010@\u001a\u00020>2\b\u0010A\u001a\u0004\u0018\u00010BH\u0007J\u0012\u0010C\u001a\u00020>2\b\u0010A\u001a\u0004\u0018\u00010DH\u0007J\b\u0010E\u001a\u00020>H\u0002J\u0010\u0010F\u001a\u00020>2\u0006\u0010G\u001a\u00020\"H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0,X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\u0002018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R\u000e\u00104\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/BedAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "getAnimation", "()Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "setAnimation", "(Lnet/ccbluex/liquidbounce/utils/animation/Animation;)V", "bestSlot", "", "blockHitDelay", "breakSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "damageRender", "delay", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getDelay", "()Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "setDelay", "(Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;)V", "hasAir", "", "ignoreGround", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "ignoreSlow", "isRealBlock", "oldPos", "Lnet/minecraft/util/BlockPos;", "pos", "getPos", "()Lnet/minecraft/util/BlockPos;", "setPos", "(Lnet/minecraft/util/BlockPos;)V", "rangeValue", "renderPos", "snapRotation", "spoofItem", "Lnet/ccbluex/liquidbounce/features/value/Value;", "surroundingsValue", "swapValue", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "throughWall", "title1", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "toolValue", "find", "limit", "head", "foot", "findNearBlock", "onDisable", "", "onEnable", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "setSlot", "tool", "blockPos", "CrossSine"}
)
public final class BedAura extends Module {
   @NotNull
   public static final BedAura INSTANCE = new BedAura();
   @NotNull
   private static final FloatValue rangeValue = new FloatValue("Range", 5.0F, 1.0F, 7.0F);
   @NotNull
   private static final BoolValue swingValue = new BoolValue("Swing", false);
   @NotNull
   private static final FloatValue breakSpeed = new FloatValue("BreakSpeed", 1.0F, 1.0F, 2.0F);
   @NotNull
   private static final BoolValue snapRotation = new BoolValue("Snap-Rotation", false);
   @NotNull
   private static final BoolValue ignoreSlow = new BoolValue("IgnoreSlow", false);
   @NotNull
   private static final BoolValue ignoreGround = new BoolValue("IgnoreGround", false);
   @NotNull
   private static final BoolValue throughWall = new BoolValue("ThroughWall", false);
   @NotNull
   private static final Value surroundingsValue;
   @NotNull
   private static final BoolValue toolValue;
   @NotNull
   private static final Value swapValue;
   @NotNull
   private static final Value spoofItem;
   @NotNull
   private static final TitleValue title1;
   @NotNull
   private static final BoolValue renderPos;
   @Nullable
   private static BlockPos pos;
   @Nullable
   private static BlockPos oldPos;
   private static int blockHitDelay;
   private static boolean isRealBlock;
   private static float currentDamage;
   private static float damageRender;
   @NotNull
   private static Animation animation;
   @NotNull
   private static TimerMS delay;
   private static int bestSlot;
   private static boolean hasAir;

   private BedAura() {
   }

   @Nullable
   public final BlockPos getPos() {
      return pos;
   }

   public final void setPos(@Nullable BlockPos var1) {
      pos = var1;
   }

   public final float getCurrentDamage() {
      return currentDamage;
   }

   public final void setCurrentDamage(float var1) {
      currentDamage = var1;
   }

   @NotNull
   public final Animation getAnimation() {
      return animation;
   }

   public final void setAnimation(@NotNull Animation var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      animation = var1;
   }

   @NotNull
   public final TimerMS getDelay() {
      return delay;
   }

   public final void setDelay(@NotNull TimerMS var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      delay = var1;
   }

   public void onEnable() {
      pos = null;
      currentDamage = 0.0F;
      damageRender = 0.0F;
      hasAir = false;
   }

   public void onDisable() {
      pos = null;
      currentDamage = 0.0F;
      damageRender = 0.0F;
      hasAir = false;
      if ((Boolean)spoofItem.get()) {
         SlotUtils.INSTANCE.stopSet();
      }

   }

   @EventTarget
   public final void onUpdate(@Nullable UpdateEvent event) {
      label256: {
         if (pos != null && Block.func_149682_b(BlockUtils.getBlock(pos)) == 26) {
            BlockPos var10000 = pos;
            Intrinsics.checkNotNull(var10000);
            if (!(BlockUtils.getCenterDistance(var10000) > (double)((Number)rangeValue.get()).floatValue())) {
               break label256;
            }
         }

         if (currentDamage == 0.0F || currentDamage >= 1.0F) {
            pos = !hasAir && (Boolean)throughWall.get() && (Boolean)surroundingsValue.get() ? this.findNearBlock() : find$default(this, false, false, false, 7, (Object)null);
         }
      }

      if (pos == null) {
         currentDamage = 0.0F;
      } else {
         BlockPos var11 = pos;
         if (var11 != null) {
            BlockPos currentPos = var11;
            VecRotation var12 = RotationUtils.faceBlock(currentPos);
            if (var12 != null) {
               VecRotation rotations = var12;
               if (!(Boolean)snapRotation.get()) {
                  RotationUtils.setTargetRotation(rotations.getRotation(), 1);
               }

               boolean surroundings = false;
               if (!(Boolean)throughWall.get()) {
                  Vec3 eyes = MinecraftInstance.mc.field_71439_g.func_174824_e(1.0F);
                  MovingObjectPosition rayTraceResult = MinecraftInstance.mc.field_71441_e.func_147447_a(eyes, rotations.getVec(), false, false, false);
                  if (rayTraceResult != null) {
                     BlockPos var13 = rayTraceResult.func_178782_a();
                     if (var13 == null) {
                        return;
                     }

                     BlockPos blockPos = var13;
                     Block block = BlockExtensionKt.getBlock(blockPos);
                     if (block instanceof BlockBed) {
                        if (!Intrinsics.areEqual((Object)currentPos, (Object)blockPos)) {
                           pos = blockPos;
                           currentDamage = 0.0F;
                        }

                        currentPos = blockPos;
                        VecRotation var14 = RotationUtils.faceBlock(blockPos);
                        if (var14 == null) {
                           return;
                        }

                        rotations = var14;
                     } else if (!(block instanceof BlockAir)) {
                        if (!Intrinsics.areEqual((Object)currentPos, (Object)blockPos)) {
                           surroundings = true;
                           if (currentDamage == 0.0F || currentDamage >= 1.0F) {
                              pos = blockPos;
                           }
                        }

                        var13 = pos;
                        if (var13 == null) {
                           return;
                        }

                        currentPos = var13;
                        VecRotation var16 = RotationUtils.faceBlock(currentPos);
                        if (var16 == null) {
                           return;
                        }

                        rotations = var16;
                     }
                  }
               }

               if (oldPos != null && !Intrinsics.areEqual((Object)oldPos, (Object)currentPos)) {
                  currentDamage = 0.0F;
               }

               oldPos = currentPos;
               if (blockHitDelay > 0) {
                  int var10 = blockHitDelay;
                  blockHitDelay = var10 + -1;
               } else {
                  if ((Boolean)throughWall.get() && (Boolean)surroundingsValue.get()) {
                     BlockPos var17 = oldPos;
                     Intrinsics.checkNotNull(var17);
                     if (!(BlockUtils.getBlock(var17) instanceof BlockAir)) {
                        hasAir = false;
                     }
                  }

                  this.tool(currentPos);
                  if (!surroundings && isRealBlock) {
                     if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), pos, EnumFacing.DOWN, new Vec3((double)currentPos.func_177958_n(), (double)currentPos.func_177956_o(), (double)currentPos.func_177952_p()))) {
                        if ((Boolean)swingValue.get()) {
                           PlayerUtils.INSTANCE.swing();
                        }

                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                        blockHitDelay = 4;
                        currentDamage = 0.0F;
                        pos = null;
                     }
                  } else {
                     if ((Boolean)toolValue.get() && !(Boolean)swapValue.get()) {
                        this.setSlot();
                     }

                     Block var18 = BlockExtensionKt.getBlock(currentPos);
                     if (var18 == null) {
                        return;
                     }

                     Block block = var18;
                     if (currentDamage != 0.0F && currentDamage != 1.0F) {
                        delay.reset();
                     }

                     if (currentDamage == 0.0F) {
                        damageRender = 0.0F;
                        if ((Boolean)toolValue.get() && (Boolean)swapValue.get()) {
                           this.setSlot();
                        }

                        if ((Boolean)snapRotation.get()) {
                           RotationUtils.setTargetRotation(rotations.getRotation(), 1);
                        }

                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, currentPos, EnumFacing.DOWN)));
                        SlotUtils.INSTANCE.stopSet();
                        if (MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75098_d || block.func_180647_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, (World)MinecraftInstance.mc.field_71441_e, pos) >= 1.0F) {
                           if ((Boolean)swingValue.get()) {
                              PlayerUtils.INSTANCE.swing();
                           }

                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                           MinecraftInstance.mc.field_71442_b.func_178888_a(pos, EnumFacing.DOWN);
                           if (Block.func_149682_b(BlockUtils.getBlock(currentPos)) == 26) {
                              damageRender = 0.0F;
                           }

                           currentDamage = 0.0F;
                           pos = null;
                           return;
                        }
                     }

                     if ((Boolean)swingValue.get()) {
                        PlayerUtils.INSTANCE.swing();
                     }

                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                     float var19 = currentDamage;
                     BlockUtils var10001 = BlockUtils.INSTANCE;
                     Block var10002 = BlockUtils.getBlock(currentPos);
                     Intrinsics.checkNotNull(var10002);
                     currentDamage = var19 + var10001.getBlockHardness(var10002, (Boolean)toolValue.get() && (Boolean)swapValue.get() && bestSlot != -1 ? MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot) : MinecraftInstance.mc.field_71439_g.func_70694_bm(), (Boolean)ignoreSlow.get(), (Boolean)ignoreGround.get()) * ((Number)breakSpeed.get()).floatValue();
                     var19 = damageRender;
                     var10001 = BlockUtils.INSTANCE;
                     var10002 = BlockUtils.getBlock(currentPos);
                     Intrinsics.checkNotNull(var10002);
                     damageRender = var19 + var10001.getBlockHardness(var10002, (Boolean)toolValue.get() && (Boolean)swapValue.get() && bestSlot != -1 ? MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot) : MinecraftInstance.mc.field_71439_g.func_70694_bm(), (Boolean)ignoreSlow.get(), (Boolean)ignoreGround.get()) * ((Number)breakSpeed.get()).floatValue();
                     MinecraftInstance.mc.field_71441_e.func_175715_c(MinecraftInstance.mc.field_71439_g.func_145782_y(), currentPos, (int)(currentDamage * 10.0F) - 1);
                     if (currentDamage >= 1.0F) {
                        if ((Boolean)snapRotation.get()) {
                           RotationUtils.setTargetRotation(rotations.getRotation(), 1);
                        }

                        if ((Boolean)toolValue.get() && (Boolean)swapValue.get()) {
                           this.setSlot();
                        }

                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, currentPos, EnumFacing.DOWN)));
                        SlotUtils.INSTANCE.stopSet();
                        if ((Boolean)surroundingsValue.get() && (Boolean)throughWall.get()) {
                           if (Block.func_149682_b(BlockUtils.getBlock(currentPos)) != 26 && !hasAir) {
                              hasAir = true;
                           }

                           if (Block.func_149682_b(BlockUtils.getBlock(currentPos)) == 26 && hasAir) {
                              hasAir = false;
                           }
                        }

                        MinecraftInstance.mc.field_71442_b.func_178888_a(currentPos, EnumFacing.DOWN);
                        blockHitDelay = 4;
                        currentDamage = 0.0F;
                        if (Block.func_149682_b(BlockUtils.getBlock(currentPos)) == 26) {
                           damageRender = 0.0F;
                           animation.value = (double)damageRender;
                        }

                        pos = null;
                     }
                  }

               }
            }
         }
      }
   }

   @EventTarget
   public final void onRender3D(@Nullable Render3DEvent event) {
      animation.run((double)damageRender);
      animation.value = RangesKt.coerceIn(animation.value, (double)0.0F, (double)1.0F);
      if (pos != null && (Boolean)renderPos.get()) {
         GlStateManager.func_179094_E();
         BlockPos var10000 = pos;
         Intrinsics.checkNotNull(var10000);
         RenderUtils.drawBlockBox(var10000, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, (Object)null), true, false, 2.0F, (float)animation.value);
         GlStateManager.func_179117_G();
         GlStateManager.func_179121_F();
      }

   }

   private final BlockPos find(boolean limit, boolean head, boolean foot) {
      int radius = (int)((Number)rangeValue.get()).floatValue() + 1;
      BlockPos nearestBlock = null;
      double closestBed = Double.MAX_VALUE;
      int var8 = -radius;
      int x;
      if (var8 <= radius) {
         do {
            x = var8++;
            int var10 = -radius;
            int y;
            if (var10 <= radius) {
               do {
                  y = var10++;
                  int var12 = -radius;
                  int z;
                  if (var12 <= radius) {
                     do {
                        z = var12++;
                        BlockPos blockPos = new BlockPos((int)MinecraftInstance.mc.field_71439_g.field_70165_t + x, (int)MinecraftInstance.mc.field_71439_g.field_70163_u + y, (int)MinecraftInstance.mc.field_71439_g.field_70161_v + z);
                        Block var10000 = BlockUtils.getBlock(blockPos);
                        if (var10000 != null) {
                           Block block = var10000;
                           double distance = MinecraftInstance.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n() + (double)0.5F, (double)blockPos.func_177956_o() + (double)0.5F, (double)blockPos.func_177952_p() + (double)0.5F);
                           if (Block.func_149682_b(block) == 26) {
                              if (limit) {
                                 if (MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177229_b((IProperty)BlockBed.field_176472_a) == EnumPartType.HEAD && head) {
                                    if (distance < closestBed) {
                                       closestBed = distance;
                                       nearestBlock = blockPos;
                                    }
                                 } else if (MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177229_b((IProperty)BlockBed.field_176472_a) == EnumPartType.FOOT && foot && distance < closestBed) {
                                    closestBed = distance;
                                    nearestBlock = blockPos;
                                 }
                              } else if (distance < closestBed) {
                                 closestBed = distance;
                                 nearestBlock = blockPos;
                              }
                           }
                        }
                     } while(z != radius);
                  }
               } while(y != radius);
            }
         } while(x != radius);
      }

      return nearestBlock;
   }

   // $FF: synthetic method
   static BlockPos find$default(BedAura var0, boolean var1, boolean var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = false;
      }

      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return var0.find(var1, var2, var3);
   }

   private final BlockPos findNearBlock() {
      BlockPos closestPos = null;
      double closestDistance = Double.MAX_VALUE;
      BlockPos var10000 = find$default(this, false, false, false, 7, (Object)null);
      if (var10000 == null) {
         return null;
      } else {
         BlockPos bedPos = var10000;
         var10000 = this.find(true, true, false);
         if (var10000 == null) {
            return null;
         } else {
            BlockPos bedPosHead = var10000;
            var10000 = this.find(true, false, true);
            if (var10000 == null) {
               return null;
            } else {
               BlockPos bedPosFoot = var10000;
               EnumFacing[] var7 = EnumFacing.values();
               int var8 = 0;
               int var9 = var7.length;

               while(var8 < var9) {
                  EnumFacing direction = var7[var8];
                  ++var8;
                  if (direction != EnumFacing.DOWN) {
                     BlockPos targetPos = bedPos.func_177972_a(direction);
                     BlockPos targetPosHead = bedPosHead.func_177972_a(direction);
                     BlockPos targetPosFoot = bedPosFoot.func_177972_a(direction);
                     if (BlockUtils.getBlock(targetPosHead) instanceof BlockAir) {
                        hasAir = true;
                        return null;
                     }

                     if (BlockUtils.getBlock(targetPosFoot) instanceof BlockAir) {
                        hasAir = true;
                        return null;
                     }

                     double distance = MinecraftInstance.mc.field_71439_g.func_70011_f((double)targetPos.func_177958_n() + (double)0.5F, (double)targetPos.func_177956_o() + (double)0.5F, (double)targetPos.func_177952_p() + (double)0.5F);
                     if (distance < closestDistance) {
                        closestDistance = distance;
                        closestPos = targetPos;
                     }
                  }
               }

               return closestPos;
            }
         }
      }
   }

   private final void tool(BlockPos blockPos) {
      float bestSpeed = 1.0F;
      Block block = MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
      int var4 = 0;

      while(var4 < 9) {
         int i = var4++;
         ItemStack var10000 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (var10000 != null) {
            ItemStack item = var10000;
            float speed = item.func_150997_a(block);
            if (speed > bestSpeed) {
               bestSpeed = speed;
               bestSlot = i;
            }
         }
      }

   }

   private final void setSlot() {
      if (bestSlot != -1) {
         SlotUtils.INSTANCE.setSlot(bestSlot, (Boolean)spoofItem.get(), this.getName());
      }

   }

   @NotNull
   public String getTag() {
      return (Boolean)swapValue.get() ? "Swap" : ((Boolean)throughWall.get() ? "Blatant" : "Legit");
   }

   static {
      surroundingsValue = (new BoolValue("Surroundings", false)).displayable(null.INSTANCE);
      toolValue = new BoolValue("AutoTool", false);
      swapValue = (new BoolValue("Swap", false)).displayable(null.INSTANCE);
      spoofItem = (new BoolValue("SpoofItem", false)).displayable(null.INSTANCE);
      title1 = new TitleValue("Visual :");
      renderPos = new BoolValue("Render-Pos", false);
      animation = new Animation(Easing.LINEAR, 40L);
      delay = new TimerMS();
      bestSlot = -1;
   }
}
