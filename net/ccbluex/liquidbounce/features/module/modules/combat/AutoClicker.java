package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(
   name = "AutoClicker",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020\u001fH\u0002J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u0006&"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canLeftClick", "", "getCanLeftClick", "()Z", "setCanLeftClick", "(Z)V", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/IntRange;", "invClicker", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getInvClicker", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "jitterAmount", "", "leftCPS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "leftDelay", "", "leftSwordOnlyValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "getDelay", "inInvClick", "", "guiScreen", "Lnet/minecraft/client/gui/GuiScreen;", "leftClicker", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"}
)
public final class AutoClicker extends Module {
   @NotNull
   public static final AutoClicker INSTANCE = new AutoClicker();
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue invClicker;
   @NotNull
   private static final Value cpsValue;
   @NotNull
   private static final Value jitterAmount;
   @NotNull
   private static final BoolValue leftSwordOnlyValue;
   @NotNull
   private static final TimerMS leftCPS;
   private static boolean canLeftClick;
   private static int leftDelay;

   private AutoClicker() {
   }

   @NotNull
   public final BoolValue getInvClicker() {
      return invClicker;
   }

   public final boolean getCanLeftClick() {
      return canLeftClick;
   }

   public final void setCanLeftClick(boolean var1) {
      canLeftClick = var1;
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)invClicker.get() && MinecraftInstance.mc.field_71462_r != null) {
         if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
            return;
         }

         GuiScreen var2 = MinecraftInstance.mc.field_71462_r;
         Intrinsics.checkNotNullExpressionValue(var2, "mc.currentScreen");
         this.inInvClick(var2);
      }

      this.leftClicker();
   }

   private final int getDelay() {
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var2) {
         case "jitter":
            leftDelay = Random.Default.nextInt(1, 14) <= 3 ? (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(98, 102) : Random.Default.nextInt(114, 117)) : (Random.Default.nextInt(1, 4) == 1 ? Random.Default.nextInt(64, 69) : Random.Default.nextInt(83, 85));
            break;
         case "normal":
            leftDelay = (int)TimeUtils.INSTANCE.randomClickDelay(((IntRange)cpsValue.get()).getFirst(), ((IntRange)cpsValue.get()).getLast());
            break;
         case "butterfly":
            if (Random.Default.nextInt(1, 10) == 1) {
               leftDelay = Random.Default.nextInt(225, 250);
            } else {
               leftDelay = Random.Default.nextInt(1, 6) == 1 ? Random.Default.nextInt(89, 94) : (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(95, 103) : (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(115, 123) : (Random.Default.nextBoolean() ? Random.Default.nextInt(131, 136) : Random.Default.nextInt(165, 174))));
            }
      }

      return leftDelay;
   }

   private final void leftClicker() {
      if (!MinecraftInstance.mc.field_71439_g.func_70632_aY() && !MinecraftInstance.mc.field_71439_g.func_70113_ah()) {
         label64: {
            if ((Boolean)leftSwordOnlyValue.get()) {
               ItemStack var10000 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
               if (!((var10000 == null ? null : var10000.func_77973_b()) instanceof ItemSword)) {
                  break label64;
               }
            }

            if (MinecraftInstance.mc.field_71476_x == null || MinecraftInstance.mc.field_71476_x.field_72313_a != MovingObjectType.BLOCK) {
               if (HitSelect.INSTANCE.getState() && HitSelect.INSTANCE.getCancelClick() && HitSelect.INSTANCE.getMode().equals("Pause")) {
                  if (leftCPS.hasTimePassed(30L)) {
                     MouseUtils.INSTANCE.setLeftClicked(false);
                  }

                  return;
               }

               if (SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null) {
                  canLeftClick = false;
                  return;
               }

               if (MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && leftCPS.hasTimePassed((long)this.getDelay())) {
                  KeyBinding.func_74507_a(MinecraftInstance.mc.field_71474_y.field_74312_F.func_151463_i());
                  if (modeValue.equals("Jitter")) {
                     EntityPlayerSP var1 = MinecraftInstance.mc.field_71439_g;
                     var1.field_70177_z += RandomUtils.INSTANCE.nextFloat(-((Number)jitterAmount.get()).floatValue(), ((Number)jitterAmount.get()).floatValue());
                     var1 = MinecraftInstance.mc.field_71439_g;
                     var1.field_70125_A += RandomUtils.INSTANCE.nextFloat(-((Number)jitterAmount.get()).floatValue(), ((Number)jitterAmount.get()).floatValue());
                  }

                  MouseUtils.INSTANCE.setLeftClicked(true);
                  canLeftClick = true;
                  leftCPS.reset();
               } else if (leftCPS.hasTimePassed(30L)) {
                  MouseUtils.INSTANCE.setLeftClicked(false);
               }

               return;
            }
         }
      }

      MouseUtils.INSTANCE.setLeftClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74312_F));
      canLeftClick = false;
   }

   private final void inInvClick(GuiScreen guiScreen) {
      int mouseInGUIPosX = Mouse.getX() * guiScreen.field_146294_l / MinecraftInstance.mc.field_71443_c;
      int mouseInGUIPosY = guiScreen.field_146295_m - Mouse.getY() * guiScreen.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;

      try {
         if (leftCPS.hasTimePassed((long)this.getDelay())) {
            String[] var4 = new String[]{"func_73864_a", "mouseClicked"};
            String[] var10002 = var4;
            Class[] var7 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Method var10000 = ReflectionHelper.findMethod(GuiScreen.class, (Object)null, var10002, var7);
            Object[] var8 = new Object[]{mouseInGUIPosX, mouseInGUIPosY, 0};
            var10000.invoke(guiScreen, var8);
            leftCPS.reset();
         }
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
      }

   }

   @Nullable
   public String getTag() {
      return ((IntRange)cpsValue.get()).getLast() + " - " + ((IntRange)cpsValue.get()).getFirst() + " CPS";
   }

   static {
      String[] var0 = new String[]{"Normal", "Jitter", "Butterfly"};
      modeValue = new ListValue("Left-Click-Mode", var0, "Normal");
      invClicker = new BoolValue("InvClicker", false);
      cpsValue = (new IntegerRangeValue("CPS", 10, 12, 1, 40, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      jitterAmount = (new FloatValue("Jitter-Rotate-Amount", 1.0F, 0.0F, 10.0F)).displayable(null.INSTANCE);
      leftSwordOnlyValue = new BoolValue("Left-SwordOnly", false);
      leftCPS = new TimerMS();
   }
}
