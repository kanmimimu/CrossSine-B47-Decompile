package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "CustomClientColor",
   category = ModuleCategory.CLIENT,
   loadConfig = false
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/client/CustomClientColor;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "color", "Lnet/ccbluex/liquidbounce/features/value/ColorValue;", "getColor", "()Lnet/ccbluex/liquidbounce/features/value/ColorValue;", "CrossSine"}
)
public final class CustomClientColor extends Module {
   @NotNull
   public static final CustomClientColor INSTANCE = new CustomClientColor();
   @NotNull
   private static final ColorValue color = new ColorValue("Color", new Color(255, 255, 255), false);

   private CustomClientColor() {
   }

   @NotNull
   public final ColorValue getColor() {
      return color;
   }
}
