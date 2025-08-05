package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "Nuker",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackedBlocks", "Ljava/util/ArrayList;", "Lnet/minecraft/util/BlockPos;", "Lkotlin/collections/ArrayList;", "blockHitDelay", "", "currentBlock", "hitDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "layerValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "nuke", "nukeDelayValue", "nukeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "nukeValue", "priorityValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "rotationsValue", "throughWallsValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "validBlock", "", "block", "Lnet/minecraft/block/Block;", "Companion", "CrossSine"}
)
public final class Nuker extends Module {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final FloatValue radiusValue = new FloatValue("Radius", 5.2F, 1.0F, 6.0F);
   @NotNull
   private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
   @NotNull
   private final ListValue priorityValue;
   @NotNull
   private final BoolValue rotationsValue;
   @NotNull
   private final BoolValue layerValue;
   @NotNull
   private final IntegerValue hitDelayValue;
   @NotNull
   private final IntegerValue nukeValue;
   @NotNull
   private final IntegerValue nukeDelayValue;
   @NotNull
   private final ArrayList attackedBlocks;
   @Nullable
   private BlockPos currentBlock;
   private int blockHitDelay;
   @NotNull
   private tickTimer nukeTimer;
   private int nuke;
   private static float currentDamage;

   public Nuker() {
      String[] var1 = new String[]{"Distance", "Hardness"};
      this.priorityValue = new ListValue("Priority", var1, "Distance");
      this.rotationsValue = new BoolValue("Rotations", true);
      this.layerValue = new BoolValue("Layer", false);
      this.hitDelayValue = new IntegerValue("HitDelay", 4, 0, 20);
      this.nukeValue = new IntegerValue("Nuke", 1, 1, 20);
      this.nukeDelayValue = new IntegerValue("NukeDelay", 1, 1, 20);
      this.attackedBlocks = new ArrayList();
      this.nukeTimer = new tickTimer();
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.blockHitDelay > 0) {
         Module var10000 = CrossSine.INSTANCE.getModuleManager().get(FastBreak.class);
         Intrinsics.checkNotNull(var10000);
         if (!((FastBreak)var10000).getState()) {
            int var25 = this.blockHitDelay;
            this.blockHitDelay = var25 + -1;
            return;
         }
      }

      this.nukeTimer.update();
      if (this.nukeTimer.hasTimePassed(((Number)this.nukeDelayValue.get()).intValue())) {
         this.nuke = 0;
         this.nukeTimer.reset();
      }

      this.attackedBlocks.clear();
      EntityPlayerSP var78 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNull(var78);
      EntityPlayerSP thePlayer = var78;
      if (MinecraftInstance.mc.field_71442_b.func_78758_h()) {
         ItemStack var91 = thePlayer.func_70694_bm();
         if (!((var91 == null ? null : var91.func_77973_b()) instanceof ItemSword)) {
            Map $this$filter$iv = BlockUtils.searchBlocks(MathKt.roundToInt(((Number)this.radiusValue.get()).floatValue()) + 1);
            int $i$f$filter = 0;
            Map destination$iv$iv = (Map)(new LinkedHashMap());
            int $i$f$filterTo = 0;

            for(Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
               int var50 = 0;
               BlockPos pos = (BlockPos)element$iv$iv.getKey();
               Block block = (Block)element$iv$iv.getValue();
               boolean var92;
               if (BlockUtils.getCenterDistance(pos) <= (double)((Number)this.radiusValue.get()).floatValue() && this.validBlock(block)) {
                  if ((Boolean)this.layerValue.get() && (double)pos.func_177956_o() < thePlayer.field_70163_u) {
                     var92 = false;
                  } else if (!(Boolean)this.throughWallsValue.get()) {
                     Vec3 eyesPos = new Vec3(thePlayer.field_70165_t, thePlayer.func_174813_aQ().field_72338_b + (double)thePlayer.eyeHeight, thePlayer.field_70161_v);
                     Vec3 blockVec = new Vec3((double)pos.func_177958_n() + (double)0.5F, (double)pos.func_177956_o() + (double)0.5F, (double)pos.func_177952_p() + (double)0.5F);
                     WorldClient var93 = MinecraftInstance.mc.field_71441_e;
                     Intrinsics.checkNotNull(var93);
                     MovingObjectPosition rayTrace = var93.func_147447_a(eyesPos, blockVec, false, true, false);
                     var92 = rayTrace != null && Intrinsics.areEqual((Object)rayTrace.func_178782_a(), (Object)pos);
                  } else {
                     var92 = true;
                  }
               } else {
                  var92 = false;
               }

               if (var92) {
                  destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
               }
            }

            $i$f$filter = 0;

            for(Map.Entry element$iv : destination$iv$iv.entrySet()) {
               int var40 = 0;
               BlockPos pos = (BlockPos)element$iv.getKey();
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN)));
               thePlayer.func_71038_i();
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, pos, EnumFacing.DOWN)));
               this.attackedBlocks.add(pos);
            }

         }
      } else {
         Map $this$filter$iv = BlockUtils.searchBlocks(MathKt.roundToInt(((Number)this.radiusValue.get()).floatValue()) + 1);
         int $i$f$filter = 0;
         Map destination$iv$iv = (Map)(new LinkedHashMap());
         int $i$f$filterTo = 0;

         for(Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
            int var13 = 0;
            BlockPos pos = (BlockPos)element$iv$iv.getKey();
            Block block = (Block)element$iv$iv.getValue();
            boolean var79;
            if (BlockUtils.getCenterDistance(pos) <= (double)((Number)this.radiusValue.get()).floatValue() && this.validBlock(block)) {
               if ((Boolean)this.layerValue.get() && (double)pos.func_177956_o() < thePlayer.field_70163_u) {
                  var79 = false;
               } else if (!(Boolean)this.throughWallsValue.get()) {
                  Vec3 eyesPos = new Vec3(thePlayer.field_70165_t, thePlayer.func_174813_aQ().field_72338_b + (double)thePlayer.eyeHeight, thePlayer.field_70161_v);
                  Vec3 blockVec = new Vec3((double)pos.func_177958_n() + (double)0.5F, (double)pos.func_177956_o() + (double)0.5F, (double)pos.func_177952_p() + (double)0.5F);
                  WorldClient var80 = MinecraftInstance.mc.field_71441_e;
                  Intrinsics.checkNotNull(var80);
                  MovingObjectPosition rayTrace = var80.func_147447_a(eyesPos, blockVec, false, true, false);
                  var79 = rayTrace != null && Intrinsics.areEqual((Object)rayTrace.func_178782_a(), (Object)pos);
               } else {
                  var79 = true;
               }
            } else {
               var79 = false;
            }

            if (var79) {
               destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
         }

         Map validBlocks = MapsKt.toMutableMap(destination$iv$iv);

         Block block;
         while(true) {
            String $this$filterTo$iv$iv = (String)this.priorityValue.get();
            Map.Entry var82;
            if (Intrinsics.areEqual((Object)$this$filterTo$iv$iv, (Object)"Distance")) {
               Iterator $dstr$pos$block = ((Iterable)validBlocks.entrySet()).iterator();
               Object var81;
               if (!$dstr$pos$block.hasNext()) {
                  var81 = null;
               } else {
                  Object $dstr$pos$block = $dstr$pos$block.next();
                  if (!$dstr$pos$block.hasNext()) {
                     var81 = $dstr$pos$block;
                  } else {
                     Map.Entry $dstr$pos$block = (Map.Entry)$dstr$pos$block;
                     int var51 = 0;
                     BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                     Block block = (Block)$dstr$pos$block.getValue();
                     double distance = BlockUtils.getCenterDistance(pos);
                     BlockPos safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - (double)1, thePlayer.field_70161_v);
                     double $dstr$pos$block = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MAX_VALUE - distance : distance;

                     do {
                        Object var52 = $dstr$pos$block.next();
                        Map.Entry $dstr$pos$block = (Map.Entry)var52;
                        int var64 = 0;
                        BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                        Block block = (Block)$dstr$pos$block.getValue();
                        double distance = BlockUtils.getCenterDistance(pos);
                        BlockPos safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - (double)1, thePlayer.field_70161_v);
                        double pos = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MAX_VALUE - distance : distance;
                        if (Double.compare($dstr$pos$block, pos) > 0) {
                           $dstr$pos$block = var52;
                           $dstr$pos$block = pos;
                        }
                     } while($dstr$pos$block.hasNext());

                     var81 = $dstr$pos$block;
                  }
               }

               var82 = (Map.Entry)var81;
            } else {
               if (!Intrinsics.areEqual((Object)$this$filterTo$iv$iv, (Object)"Hardness")) {
                  return;
               }

               Iterator $dstr$pos$block = ((Iterable)validBlocks.entrySet()).iterator();
               Object var83;
               if (!$dstr$pos$block.hasNext()) {
                  var83 = null;
               } else {
                  Object $dstr$pos$block = $dstr$pos$block.next();
                  if (!$dstr$pos$block.hasNext()) {
                     var83 = $dstr$pos$block;
                  } else {
                     Map.Entry $dstr$pos$block = (Map.Entry)$dstr$pos$block;
                     int pos = 0;
                     BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                     Block block = (Block)$dstr$pos$block.getValue();
                     EntityPlayer var10001 = (EntityPlayer)thePlayer;
                     WorldClient var10002 = MinecraftInstance.mc.field_71441_e;
                     Intrinsics.checkNotNull(var10002);
                     double hardness = (double)block.func_180647_a(var10001, (World)var10002, pos);
                     BlockPos safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - (double)1, thePlayer.field_70161_v);
                     double var49 = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MIN_VALUE + hardness : hardness;

                     do {
                        Object var54 = $dstr$pos$block.next();
                        Map.Entry $dstr$pos$block = (Map.Entry)var54;
                        int var66 = 0;
                        BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                        Block block = (Block)$dstr$pos$block.getValue();
                        var10001 = (EntityPlayer)thePlayer;
                        var10002 = MinecraftInstance.mc.field_71441_e;
                        Intrinsics.checkNotNull(var10002);
                        double hardness = (double)block.func_180647_a(var10001, (World)var10002, pos);
                        BlockPos safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - (double)1, thePlayer.field_70161_v);
                        double var61 = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MIN_VALUE + hardness : hardness;
                        if (Double.compare(var49, var61) < 0) {
                           $dstr$pos$block = var54;
                           var49 = var61;
                        }
                     } while($dstr$pos$block.hasNext());

                     var83 = $dstr$pos$block;
                  }
               }

               var82 = (Map.Entry)var83;
            }

            Map.Entry blockPos = var82;
            if (blockPos == null) {
               return;
            }

            Map.Entry $i$f$filter = blockPos;
            blockPos = (BlockPos)blockPos.getKey();
            block = (Block)$i$f$filter.getValue();
            if (!Intrinsics.areEqual((Object)blockPos, (Object)this.currentBlock)) {
               Companion var84 = Companion;
               currentDamage = 0.0F;
            }

            if ((Boolean)this.rotationsValue.get()) {
               VecRotation var85 = RotationUtils.faceBlock(blockPos);
               if (var85 == null) {
                  return;
               }

               VecRotation rotation = var85;
               RotationUtils.setTargetRotation(rotation.getRotation(), 0);
            }

            this.currentBlock = blockPos;
            this.attackedBlocks.add(blockPos);
            if (currentDamage != 0.0F) {
               break;
            }

            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN)));
            EntityPlayer var95 = (EntityPlayer)thePlayer;
            WorldClient var97 = MinecraftInstance.mc.field_71441_e;
            Intrinsics.checkNotNull(var97);
            if (!(block.func_180647_a(var95, (World)var97, blockPos) >= 1.0F)) {
               break;
            }

            Companion var86 = Companion;
            currentDamage = 0.0F;
            thePlayer.func_71038_i();
            MinecraftInstance.mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
            this.blockHitDelay = ((Number)this.hitDelayValue.get()).intValue();
            validBlocks.remove(blockPos);
            int $i$f$filterTo = this.nuke++;
            if (this.nuke >= ((Number)this.nukeValue.get()).intValue()) {
               return;
            }
         }

         thePlayer.func_71038_i();
         Companion var87 = Companion;
         float var88 = currentDamage;
         EntityPlayer var98 = (EntityPlayer)thePlayer;
         WorldClient var10003 = MinecraftInstance.mc.field_71441_e;
         Intrinsics.checkNotNull(var10003);
         currentDamage = var88 + block.func_180647_a(var98, (World)var10003, blockPos);
         WorldClient var89 = MinecraftInstance.mc.field_71441_e;
         Intrinsics.checkNotNull(var89);
         var89.func_175715_c(thePlayer.func_145782_y(), blockPos, (int)(currentDamage * 10.0F) - 1);
         if (currentDamage >= 1.0F) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN)));
            MinecraftInstance.mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
            this.blockHitDelay = ((Number)this.hitDelayValue.get()).intValue();
            Companion var90 = Companion;
            currentDamage = 0.0F;
         }

      }
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)this.layerValue.get()) {
         EntityPlayerSP var10002 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNull(var10002);
         double var6 = var10002.field_70165_t;
         EntityPlayerSP var10003 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNull(var10003);
         double var7 = var10003.field_70163_u - (double)1;
         EntityPlayerSP var10004 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNull(var10004);
         BlockPos safePos = new BlockPos(var6, var7, var10004.field_70161_v);
         Block safeBlock = BlockUtils.getBlock(safePos);
         if (safeBlock != null && this.validBlock(safeBlock)) {
            RenderUtils.drawBlockBox(safePos, Color.GREEN, true);
         }
      }

      for(BlockPos blockPos : this.attackedBlocks) {
         RenderUtils.drawBlockBox(blockPos, Color.RED, true);
      }

   }

   private final boolean validBlock(Block block) {
      return !Intrinsics.areEqual((Object)block, (Object)Blocks.field_150350_a) && !(block instanceof BlockLiquid) && !Intrinsics.areEqual((Object)block, (Object)Blocks.field_150357_h);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion;", "", "()V", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      public final float getCurrentDamage() {
         return Nuker.currentDamage;
      }

      public final void setCurrentDamage(float var1) {
         Nuker.currentDamage = var1;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
