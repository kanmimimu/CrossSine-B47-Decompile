package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Glint",
   category = ModuleCategory.VISUAL
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Glint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Ljava/awt/Color;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getColor", "CrossSine"}
)
public final class Glint extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value colorValue;

   public Glint() {
      String[] var1 = new String[]{"Theme", "AnotherRainbow", "Custom"};
      this.modeValue = new ListValue("Mode", var1, "Custom");
      this.colorValue = (new ColorValue("Color", new Color(255, 0, 0), false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return Glint.this.modeValue.equals("Custom");
         }
      });
   }

   @NotNull
   public final Color getColor() {
      String var2 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      return Intrinsics.areEqual((Object)var2, (Object)"theme") ? ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, (Object)null) : (Intrinsics.areEqual((Object)var2, (Object)"anotherrainbow") ? ColorUtils.skyRainbow(10, 0.9F, 1.0F, (double)1.0F) : (Color)this.colorValue.get());
   }
}
