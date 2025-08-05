package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

@ModuleInfo(
   name = "AutoPlace",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoPlace;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dl", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "down", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "f", "", "fakeMouseDown", "l", "", "lm", "Lnet/minecraft/util/MovingObjectPosition;", "lp", "Lnet/minecraft/util/BlockPos;", "md", "nofly", "pitch", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/IntRange;", "pitchLimit", "side", "up", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"}
)
public final class AutoPlace extends Module {
   @NotNull
   private final FloatValue dl = new FloatValue("Delay", 0.0F, 0.0F, 10.0F);
   @NotNull
   private final BoolValue md = new BoolValue("MouseDown", false);
   @NotNull
   private final BoolValue fakeMouseDown = new BoolValue("Fake-Mouse-Down", false);
   @NotNull
   private final BoolValue up = new BoolValue("Up", false);
   @NotNull
   private final BoolValue down = new BoolValue("Down", false);
   @NotNull
   private final BoolValue side = new BoolValue("Side", true);
   @NotNull
   private final BoolValue nofly = new BoolValue("NoFly", false);
   @NotNull
   private final BoolValue pitchLimit = new BoolValue("Pitch", false);
   @NotNull
   private final Value pitch = (new IntegerRangeValue("Pitch", 75, 90, 0, 90, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)AutoPlace.this.pitchLimit.get();
      }
   });
   private long l;
   private int f;
   @Nullable
   private MovingObjectPosition lm;
   @Nullable
   private BlockPos lp;

   @EventTarget
   public final void onRender3D(@Nullable Render3DEvent event) {
      if ((Boolean)this.fakeMouseDown.get() && !Mouse.isButtonDown(1)) {
         MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x.field_72313_a == MovingObjectType.BLOCK && ((Boolean)this.up.get() || MinecraftInstance.mc.field_71476_x.field_178784_b != EnumFacing.UP) && ((Boolean)this.down.get() || MinecraftInstance.mc.field_71476_x.field_178784_b != EnumFacing.DOWN) || !(Boolean)this.side.get() || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.NORTH || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.EAST || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.SOUTH || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.WEST;
      }

      if (MinecraftInstance.mc.field_71462_r == null && (!(Boolean)this.nofly.get() || !MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b) && (!(Boolean)this.pitchLimit.get() || MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((IntRange)this.pitch.get()).getLast() && MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((IntRange)this.pitch.get()).getFirst())) {
         ItemStack i = MinecraftInstance.mc.field_71439_g.func_70694_bm();
         if (i != null && i.func_77973_b() instanceof ItemBlock) {
            MovingObjectPosition m = MinecraftInstance.mc.field_71476_x;
            if ((m == null || m.field_72313_a != MovingObjectType.BLOCK || !(Boolean)this.up.get() && m.field_178784_b == EnumFacing.UP || !(Boolean)this.down.get() && m.field_178784_b == EnumFacing.DOWN) && (Boolean)this.side.get()) {
               Intrinsics.checkNotNull(m);
               if (m.field_178784_b != EnumFacing.NORTH && m.field_178784_b != EnumFacing.EAST && m.field_178784_b != EnumFacing.SOUTH && m.field_178784_b != EnumFacing.WEST) {
                  return;
               }
            }

            if (this.lm != null && (double)this.f < (double)((Number)this.dl.get()).floatValue()) {
               ++this.f;
               int var10 = this.f;
            } else {
               this.lm = m;
               BlockPos pos = m.func_178782_a();
               if (this.lp != null) {
                  int var10000 = pos.func_177958_n();
                  BlockPos var10001 = this.lp;
                  Intrinsics.checkNotNull(var10001);
                  if (var10000 == var10001.func_177958_n()) {
                     var10000 = pos.func_177956_o();
                     var10001 = this.lp;
                     Intrinsics.checkNotNull(var10001);
                     if (var10000 == var10001.func_177956_o()) {
                        var10000 = pos.func_177952_p();
                        var10001 = this.lp;
                        Intrinsics.checkNotNull(var10001);
                        if (var10000 == var10001.func_177952_p()) {
                           return;
                        }
                     }
                  }
               }

               Block b = MinecraftInstance.mc.field_71441_e.func_180495_p(pos).func_177230_c();
               if (b != null && b != Blocks.field_150350_a && !(b instanceof BlockLiquid) && (!(Boolean)this.md.get() || MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d())) {
                  long n = System.currentTimeMillis();
                  if (n - this.l >= 25L) {
                     this.l = n;
                     if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, i, pos, m.field_178784_b, m.field_72307_f)) {
                        MouseUtils.INSTANCE.setMouseButtonState(1, true);
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                        MinecraftInstance.mc.func_175597_ag().func_78444_b();
                        MouseUtils.INSTANCE.setMouseButtonState(1, false);
                        this.lp = pos;
                        this.f = 0;
                     }
                  }
               }
            }
         }
      }

   }
}
