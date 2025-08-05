package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ColorManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004J&\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/RangeSlider;", "", "()V", "maxValue", "", "getMaxValue", "()F", "setMaxValue", "(F)V", "minValue", "getMinValue", "setMinValue", "smoothMax", "smoothMin", "onDraw", "", "x", "y", "width", "accentColor", "Ljava/awt/Color;", "anim", "setValue", "min", "max", "rangeMin", "rangeMax", "CrossSine"}
)
public final class RangeSlider {
   private float smoothMin;
   private float smoothMax = 100.0F;
   private float minValue;
   private float maxValue = 100.0F;

   public final float getMinValue() {
      return this.minValue;
   }

   public final void setMinValue(float var1) {
      this.minValue = var1;
   }

   public final float getMaxValue() {
      return this.maxValue;
   }

   public final void setMaxValue(float var1) {
      this.maxValue = var1;
   }

   public final void setValue(float min, float max, float rangeMin, float rangeMax) {
      this.minValue = RangesKt.coerceIn((min - rangeMin) / (rangeMax - rangeMin) * 100.0F, 0.0F, 100.0F);
      this.maxValue = RangesKt.coerceIn((max - rangeMin) / (rangeMax - rangeMin) * 100.0F, 0.0F, 100.0F);
   }

   public final void onDraw(float x, float y, float width, @NotNull Color accentColor, float anim) {
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      this.smoothMin = AnimHelperKt.animSmooth(this.smoothMin, this.minValue, 0.5F);
      this.smoothMax = AnimHelperKt.animSmooth(this.smoothMax, this.maxValue, 0.5F);
      float trackHeight = 3.5F;
      float trackY = y - trackHeight / 2.0F;
      RenderUtils.drawRoundedRect(x - 1.0F, y - 1.5F - 2.5F * anim, width + 2.0F, 3.0F + 5.0F * anim, 1.0F + 2.0F * anim, ColorManager.INSTANCE.getUnusedSlider().getRGB(), 1.0F, Color.WHITE.getRGB());
      float filledStart = x + width * (Math.min(this.smoothMin, this.smoothMax) / 100.0F);
      float filledEnd = x + width * (Math.max(this.smoothMin, this.smoothMax) / 100.0F);
      RenderUtils.drawRoundedRect(filledStart, trackY - 2.5F * anim, filledEnd, trackY + trackHeight + 2.5F * anim, 1.0F + 2.0F * anim, ColorExtensionKt.setAlpha(accentColor, 100).getRGB(), true);
      RenderUtils.drawFilledCircle(filledStart, y, 4.0F + 2.5F * anim, Color.WHITE);
      RenderUtils.drawFilledCircle(filledStart, y, 3.0F + 2.5F * anim, accentColor);
      RenderUtils.drawFilledCircle(filledEnd, y, 4.0F + 2.5F * anim, Color.WHITE);
      RenderUtils.drawFilledCircle(filledEnd, y, 3.0F + 2.5F * anim, accentColor);
   }
}
