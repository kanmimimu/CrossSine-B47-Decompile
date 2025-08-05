package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

@ModuleInfo(
   name = "FreeLook",
   category = ModuleCategory.VISUAL,
   triggerType = EnumTriggerType.PRESS
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0016J\u0006\u0010\u000f\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/FreeLook;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "reverse", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getReverse", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "thirdPerson", "disable", "", "enable", "move", "", "onDisable", "onEnable", "setRotations", "Companion", "CrossSine"}
)
public final class FreeLook extends Module {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final BoolValue thirdPerson = new BoolValue("ThirdPerson", true);
   @NotNull
   private final BoolValue reverse = new BoolValue("Reverse", false);
   private static final Minecraft mc;
   @JvmField
   public static boolean isReverse;
   @JvmField
   public static boolean isEnabled;
   @JvmField
   public static boolean moveCorrect;
   @JvmField
   public static boolean perspectiveToggled;
   @JvmField
   public static float cameraYaw;
   @JvmField
   public static float cameraPitch;
   @JvmField
   public static float cameraYaw2;
   @JvmField
   public static float cameraPitch2;
   private static int previousPerspective;

   @NotNull
   public final BoolValue getReverse() {
      return this.reverse;
   }

   public void onEnable() {
      if (!isEnabled) {
         this.setRotations();
         Companion var10000 = Companion;
         isEnabled = true;
      }

      Companion var1 = Companion;
      cameraYaw2 = cameraYaw;
      var1 = Companion;
      cameraPitch2 = cameraPitch;
      var1 = Companion;
      isReverse = (Boolean)this.reverse.get();
      var1 = Companion;
      perspectiveToggled = true;
      var1 = Companion;
      previousPerspective = mc.field_71474_y.field_74320_O;
      if ((Boolean)this.thirdPerson.get()) {
         mc.field_71474_y.field_74320_O = 1;
      }

   }

   public void onDisable() {
      if (RotationUtils.freeLookRotation == null) {
         Companion var10000 = Companion;
         isEnabled = false;
         Companion.resetPerspective();
      } else {
         mc.field_71474_y.field_74320_O = previousPerspective;
         Companion var1 = Companion;
         cameraYaw2 = cameraYaw;
         var1 = Companion;
         cameraPitch2 = cameraPitch;
      }

   }

   public final void setRotations() {
      Companion var10000 = Companion;
      cameraYaw = mc.field_71439_g.field_70177_z;
      var10000 = Companion;
      cameraPitch = mc.field_71439_g.field_70125_A;
   }

   public final void enable(boolean move) {
      Companion var10000 = Companion;
      isEnabled = true;
      var10000 = Companion;
      isReverse = false;
      var10000 = Companion;
      perspectiveToggled = true;
      var10000 = Companion;
      moveCorrect = move;
      this.setRotations();
      var10000 = Companion;
      previousPerspective = mc.field_71474_y.field_74320_O;
   }

   public final void disable() {
      if (isEnabled) {
         Companion var10000 = Companion;
         isEnabled = false;
         var10000 = Companion;
         perspectiveToggled = false;
         mc.field_71439_g.field_70177_z = cameraYaw;
         mc.field_71439_g.field_70125_A = cameraPitch;
      }

   }

   @JvmStatic
   public static final boolean overrideMouse() {
      return Companion.overrideMouse();
   }

   @JvmStatic
   public static final void resetPerspective() {
      Companion.resetPerspective();
   }

   static {
      mc = MinecraftInstance.mc;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\tH\u0007J\b\u0010\u0013\u001a\u00020\u0014H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/FreeLook$Companion;", "", "()V", "cameraPitch", "", "cameraPitch2", "cameraYaw", "cameraYaw2", "isEnabled", "", "isReverse", "mc", "Lnet/minecraft/client/Minecraft;", "kotlin.jvm.PlatformType", "moveCorrect", "perspectiveToggled", "previousPerspective", "", "overrideMouse", "resetPerspective", "", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      @JvmStatic
      public final boolean overrideMouse() {
         if (FreeLook.mc.field_71415_G && Display.isActive()) {
            if (!FreeLook.perspectiveToggled) {
               return true;
            }

            float f3;
            float f4;
            label36: {
               FreeLook.mc.field_71417_B.func_74374_c();
               float f1 = FreeLook.mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
               float f2 = f1 * f1 * f1 * 8.0F;
               f3 = (float)FreeLook.mc.field_71417_B.field_74377_a * f2;
               f4 = (float)FreeLook.mc.field_71417_B.field_74375_b * f2;
               if (RotationUtils.freeLookRotation != null) {
                  Module var10000 = CrossSine.INSTANCE.getModuleManager().getModule(FreeLook.class);
                  Intrinsics.checkNotNull(var10000);
                  if (((FreeLook)var10000).getState()) {
                     break label36;
                  }
               }

               FreeLook.cameraYaw += f3 * 0.15F;
               FreeLook.cameraPitch -= f4 * 0.15F;
               if (FreeLook.cameraPitch > 90.0F) {
                  FreeLook.cameraPitch = 90.0F;
               }

               if (FreeLook.cameraPitch < -90.0F) {
                  FreeLook.cameraPitch = -90.0F;
               }
            }

            FreeLook.cameraYaw2 += f3 * 0.15F;
            FreeLook.cameraPitch2 -= f4 * 0.15F;
            if (FreeLook.cameraPitch2 > 90.0F) {
               FreeLook.cameraPitch2 = 90.0F;
            }

            if (FreeLook.cameraPitch2 < -90.0F) {
               FreeLook.cameraPitch2 = -90.0F;
            }
         }

         return false;
      }

      @JvmStatic
      public final void resetPerspective() {
         FreeLook.perspectiveToggled = false;
         FreeLook.mc.field_71474_y.field_74320_O = FreeLook.previousPerspective;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
