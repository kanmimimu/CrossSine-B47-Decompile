package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
   name = "SafeWalk",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/SafeWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "noSpeedPotion", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "og", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onBlock", "onHoldShift", "pitchLimit", "pitchRange", "Lkotlin/ranges/IntRange;", "shiftRangeValue", "shiftValue", "getShift", "", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"}
)
public final class SafeWalk extends Module {
   @NotNull
   public static final SafeWalk INSTANCE = new SafeWalk();
   @NotNull
   private static final BoolValue shiftValue = new BoolValue("Shift", false);
   @NotNull
   private static final BoolValue og = new BoolValue("OnlyGround", false);
   @NotNull
   private static final Value onBlock;
   @NotNull
   private static final Value noSpeedPotion;
   @NotNull
   private static final Value onHoldShift;
   @NotNull
   private static final Value shiftRangeValue;
   @NotNull
   private static final BoolValue pitchLimit;
   @NotNull
   private static final Value pitchRange;

   private SafeWalk() {
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)shiftValue.get()) {
         if (!(Boolean)og.get() || MinecraftInstance.mc.field_71439_g.field_70122_E) {
            event.setSafeWalk(!(Boolean)pitchLimit.get() || MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((IntRange)pitchRange.get()).getLast() && MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((IntRange)pitchRange.get()).getFirst());
         }

      }
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)shiftValue.get() && !Scaffold.INSTANCE.getState()) {
         if (MinecraftInstance.mc.field_71462_r == null) {
            if (MinecraftInstance.mc.field_71474_y.field_74368_y.func_151470_d()) {
               if ((Boolean)onBlock.get() && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
                  MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
               } else if ((!(Boolean)onHoldShift.get() || Keyboard.isKeyDown(MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i())) && (!(Boolean)og.get() || MinecraftInstance.mc.field_71439_g.field_70122_E) && (!(Boolean)noSpeedPotion.get() || !MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) && (!(Boolean)pitchLimit.get() || MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((IntRange)pitchRange.get()).getLast() && MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((IntRange)pitchRange.get()).getFirst())) {
                  MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Intrinsics.areEqual((Object)MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t + MinecraftInstance.mc.field_71439_g.field_70159_w * this.getShift(), MinecraftInstance.mc.field_71439_g.field_70163_u - (double)1.0F, MinecraftInstance.mc.field_71439_g.field_70161_v + MinecraftInstance.mc.field_71439_g.field_70179_y * this.getShift())).func_177230_c(), (Object)Blocks.field_150350_a);
                  return;
               }
            }

            if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0F && MinecraftInstance.mc.field_71439_g.func_70093_af() && !Keyboard.isKeyDown(MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i())) {
               MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Keyboard.isKeyDown(MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i());
            }

            if ((Boolean)onBlock.get() && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
               MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Keyboard.isKeyDown(MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i());
            }
         }

      }
   }

   private final double getShift() {
      int fuckmin = ((IntRange)shiftRangeValue.get()).getFirst() / 10;
      int fuckmax = ((IntRange)shiftRangeValue.get()).getLast() / 10;
      double min = (double)Math.min(fuckmin, fuckmax);
      double max = (double)Math.max(fuckmin, fuckmax);
      return Math.random() * (max - min) + min;
   }

   public void onDisable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         ;
      }
   }

   static {
      onBlock = (new BoolValue("Block only", false)).displayable(null.INSTANCE);
      noSpeedPotion = (new BoolValue("NoPotionSpeed", false)).displayable(null.INSTANCE);
      onHoldShift = (new BoolValue("OnHoldShift", false)).displayable(null.INSTANCE);
      shiftRangeValue = (new IntegerRangeValue("ShiftRange", 0, 0, 0, 20, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      pitchLimit = new BoolValue("Pitch", false);
      pitchRange = (new IntegerRangeValue("PitchRange", 0, 0, 0, 90, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
   }
}
