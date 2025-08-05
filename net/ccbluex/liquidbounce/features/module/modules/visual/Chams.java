package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Chams",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0016\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0007R\u0011\u0010\u001a\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0007¨\u0006\u001f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Chams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "behindColorModeValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getBehindColorModeValue", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "brightnessValue", "", "getBrightnessValue", "colorModeValue", "getColorModeValue", "colorValue", "Ljava/awt/Color;", "getColorValue", "itemsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getItemsValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "legacyMode", "getLegacyMode", "localPlayerValue", "getLocalPlayerValue", "saturationValue", "getSaturationValue", "targetsValue", "getTargetsValue", "texturedValue", "", "getTexturedValue", "CrossSine"}
)
public final class Chams extends Module {
   @NotNull
   private final BoolValue targetsValue = new BoolValue("Targets", true);
   @NotNull
   private final BoolValue itemsValue = new BoolValue("Items", true);
   @NotNull
   private final BoolValue localPlayerValue = new BoolValue("LocalPlayer", true);
   @NotNull
   private final BoolValue legacyMode = new BoolValue("Legacy-Mode", false);
   @NotNull
   private final Value texturedValue = (new BoolValue("Textured", false)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return !(Boolean)Chams.this.getLegacyMode().get();
      }
   });
   @NotNull
   private final Value colorModeValue;
   @NotNull
   private final Value behindColorModeValue;
   @NotNull
   private final Value colorValue;
   @NotNull
   private final Value saturationValue;
   @NotNull
   private final Value brightnessValue;

   public Chams() {
      String[] var1 = new String[]{"Custom", "Slowly", "Fade"};
      this.colorModeValue = (new ListValue("Color", var1, "Custom")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)Chams.this.getLegacyMode().get();
         }
      });
      var1 = new String[]{"Same", "Opposite", "Red"};
      this.behindColorModeValue = (new ListValue("Behind-Color", var1, "Red")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)Chams.this.getLegacyMode().get();
         }
      });
      this.colorValue = (new ColorValue("Color", new Color(0, 200, 0), false, 4, (DefaultConstructorMarker)null)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)Chams.this.getLegacyMode().get();
         }
      });
      this.saturationValue = (new FloatValue("Saturation", 1.0F, 0.0F, 1.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)Chams.this.getLegacyMode().get();
         }
      });
      this.brightnessValue = (new FloatValue("Brightness", 1.0F, 0.0F, 1.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return !(Boolean)Chams.this.getLegacyMode().get();
         }
      });
   }

   @NotNull
   public final BoolValue getTargetsValue() {
      return this.targetsValue;
   }

   @NotNull
   public final BoolValue getItemsValue() {
      return this.itemsValue;
   }

   @NotNull
   public final BoolValue getLocalPlayerValue() {
      return this.localPlayerValue;
   }

   @NotNull
   public final BoolValue getLegacyMode() {
      return this.legacyMode;
   }

   @NotNull
   public final Value getTexturedValue() {
      return this.texturedValue;
   }

   @NotNull
   public final Value getColorModeValue() {
      return this.colorModeValue;
   }

   @NotNull
   public final Value getBehindColorModeValue() {
      return this.behindColorModeValue;
   }

   @NotNull
   public final Value getColorValue() {
      return this.colorValue;
   }

   @NotNull
   public final Value getSaturationValue() {
      return this.saturationValue;
   }

   @NotNull
   public final Value getBrightnessValue() {
      return this.brightnessValue;
   }
}
