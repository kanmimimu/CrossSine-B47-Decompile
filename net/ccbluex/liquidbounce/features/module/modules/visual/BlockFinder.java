package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BlockValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "BlockFinder",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0007J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/BlockFinder;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockfinder", "Lnet/ccbluex/liquidbounce/features/value/BlockValue;", "color", "Ljava/awt/Color;", "kotlin.jvm.PlatformType", "posList", "", "Lnet/minecraft/util/BlockPos;", "searchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tag", "", "getTag", "()Ljava/lang/String;", "thread", "Ljava/lang/Thread;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class BlockFinder extends Module {
   @NotNull
   private final BlockValue blockfinder = new BlockValue("Block", 1);
   @NotNull
   private final MSTimer searchTimer = new MSTimer();
   @NotNull
   private final List posList = (List)(new ArrayList());
   private Color color;
   @Nullable
   private Thread thread;

   public BlockFinder() {
      this.color = Color.CYAN;
   }

   @EventTarget
   public final void onUpdate(@Nullable UpdateEvent event) {
      this.color = ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null);
      if (this.searchTimer.hasTimePassed(1000L)) {
         if (this.thread != null) {
            Thread var10000 = this.thread;
            Intrinsics.checkNotNull(var10000);
            if (var10000.isAlive()) {
               return;
            }
         }

         int radius = 100;
         Block selectedBlock = Block.func_149729_e(((Number)this.blockfinder.get()).intValue());
         if (selectedBlock == null || selectedBlock == Blocks.field_150350_a) {
            return;
         }

         this.thread = new Thread(BlockFinder::onUpdate$lambda-1, "BlockESP-BlockFinder");
         Thread var4 = this.thread;
         Intrinsics.checkNotNull(var4);
         var4.start();
      }

   }

   @EventTarget
   public final void onRender3D(@Nullable Render3DEvent event) {
      List var2 = this.posList;
      synchronized(var2) {
         int var3 = 0;

         for(BlockPos blockPos : this.posList) {
            RenderUtils.drawBlockBox(blockPos, this.color, false, true, 0.5F, 1.0F);
         }

         Unit var7 = Unit.INSTANCE;
      }
   }

   @Nullable
   public String getTag() {
      return BlockExtensionKt.getBlockName(((Number)this.blockfinder.get()).intValue());
   }

   private static final void onUpdate$lambda_1/* $FF was: onUpdate$lambda-1*/(int $radius, Block $selectedBlock, BlockFinder this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      List blockList = (List)(new ArrayList());
      int var4 = -$radius;

      while(var4 < $radius) {
         int x = var4++;
         int var6 = $radius;
         int var7 = -$radius + 1;
         if (var7 <= $radius) {
            while(true) {
               int y = var6--;
               int var9 = -$radius;

               while(var9 < $radius) {
                  int z = var9++;
                  int xPos = (int)MinecraftInstance.mc.field_71439_g.field_70165_t + x;
                  int yPos = (int)MinecraftInstance.mc.field_71439_g.field_70163_u + y;
                  int zPos = (int)MinecraftInstance.mc.field_71439_g.field_70161_v + z;
                  BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                  Block block = BlockUtils.getBlock(blockPos);
                  if (block == $selectedBlock) {
                     blockList.add(blockPos);
                  }
               }

               if (y == var7) {
                  break;
               }
            }
         }
      }

      this$0.searchTimer.reset();
      List var17 = this$0.posList;
      synchronized(var17) {
         int var18 = 0;
         this$0.posList.clear();
         this$0.posList.addAll((Collection)blockList);
         Unit var19 = Unit.INSTANCE;
      }
   }
}
