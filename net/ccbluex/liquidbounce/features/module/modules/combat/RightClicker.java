package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "RightClicker",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\b\u0010\u0015\u001a\u00020\u0012H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/RightClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canRightClick", "", "getCanRightClick", "()Z", "setCanRightClick", "(Z)V", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "rightBlockOnly", "Lnet/ccbluex/liquidbounce/features/value/Value;", "rightCPS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "rightOption", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "rightClicker", "CrossSine"}
)
public final class RightClicker extends Module {
   @NotNull
   public static final RightClicker INSTANCE = new RightClicker();
   @NotNull
   private static final BoolValue rightOption = new BoolValue("Right-Option", true);
   @NotNull
   private static final IntegerRangeValue cpsValue = new IntegerRangeValue("CPS", 10, 12, 1, 40, (Function0)null, 32, (DefaultConstructorMarker)null);
   @NotNull
   private static final Value rightBlockOnly;
   private static boolean canRightClick;
   @NotNull
   private static TimerMS rightCPS;

   private RightClicker() {
   }

   public final boolean getCanRightClick() {
      return canRightClick;
   }

   public final void setCanRightClick(boolean var1) {
      canRightClick = var1;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.rightClicker();
   }

   private final void rightClicker() {
      label36: {
         if ((Boolean)rightBlockOnly.get()) {
            ItemStack var10000 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            if (!((var10000 == null ? null : var10000.func_77973_b()) instanceof ItemBlock)) {
               break label36;
            }
         }

         ItemStack var1 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
         if (!((var1 == null ? null : var1.func_77973_b()) instanceof ItemSword)) {
            if (MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && rightCPS.hasTimePassed(TimeUtils.INSTANCE.randomClickDelay(cpsValue.get().getFirst(), cpsValue.get().getLast()))) {
               MouseUtils.INSTANCE.setRightClicked(true);
               KeyBinding.func_74507_a(MinecraftInstance.mc.field_71474_y.field_74313_G.func_151463_i());
               canRightClick = true;
               rightCPS.reset();
            } else if (rightCPS.hasTimePassed(10L)) {
               MouseUtils.INSTANCE.setRightClicked(false);
            }

            return;
         }
      }

      MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G));
      canRightClick = false;
   }

   static {
      rightBlockOnly = (new BoolValue("Right-BlockOnly", false)).displayable(null.INSTANCE);
      rightCPS = new TimerMS();
   }
}
