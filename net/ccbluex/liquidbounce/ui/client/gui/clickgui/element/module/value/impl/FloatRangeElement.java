package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components.RangeSlider;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J@\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J0\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u0016J0\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0003H\u0016R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/FloatRangeElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "savedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;", "(Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;)V", "anim", "draggingMax", "", "draggingMin", "formatter", "Ljava/text/DecimalFormat;", "getSavedValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;", "slider", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/RangeSlider;", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onRelease", "CrossSine"}
)
public final class FloatRangeElement extends ValueElement {
   @NotNull
   private final FloatRangeValue savedValue;
   @NotNull
   private final RangeSlider slider;
   private boolean draggingMin;
   private boolean draggingMax;
   private float anim;
   @NotNull
   private final DecimalFormat formatter;

   public FloatRangeElement(@NotNull FloatRangeValue savedValue) {
      Intrinsics.checkNotNullParameter(savedValue, "savedValue");
      super(savedValue);
      this.savedValue = savedValue;
      this.slider = new RangeSlider();
      this.formatter = new DecimalFormat("0.00");
   }

   @NotNull
   public final FloatRangeValue getSavedValue() {
      return this.savedValue;
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      float nameWidth = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName());
      float sliderX = x + nameWidth + 15.0F;
      String valueText = this.formatter.format(this.savedValue.get().getStart()) + " - " + this.formatter.format(this.savedValue.get().getEndInclusive());
      float valueTextWidth = (float)Fonts.font40SemiBold.func_78256_a(valueText) + 15.0F;
      float sliderWidth = RangesKt.coerceAtLeast(width - nameWidth - valueTextWidth - 20.0F, 30.0F);
      this.anim = AnimHelperKt.animSmooth(this.anim, !this.draggingMin && !this.draggingMax ? 0.0F : 1.0F, 0.25F);
      if (this.draggingMin || this.draggingMax) {
         float percent = RangesKt.coerceIn(((float)mouseX - sliderX) / sliderWidth, 0.0F, 1.0F);
         float newValue = this.savedValue.getMinimum() + (this.savedValue.getMaximum() - this.savedValue.getMinimum()) * percent;
         if (this.draggingMin) {
            this.savedValue.setMin(RangesKt.coerceAtMost(newValue, ((Number)this.savedValue.get().getEndInclusive()).floatValue()));
         } else if (this.draggingMax) {
            this.savedValue.setMax(RangesKt.coerceAtLeast(newValue, ((Number)this.savedValue.get().getStart()).floatValue()));
         }
      }

      Fonts.font40SemiBold.func_175063_a(this.getValue().getName(), x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      this.slider.setValue(((Number)this.savedValue.get().getStart()).floatValue(), ((Number)this.savedValue.get().getEndInclusive()).floatValue(), this.savedValue.getMinimum(), this.savedValue.getMaximum());
      this.slider.onDraw(sliderX, y + 11.0F, sliderWidth, accentColor, (float)EaseUtils.INSTANCE.easeInSine((double)this.anim));
      Fonts.font40SemiBold.func_175063_a(valueText, x + width - valueTextWidth + 5.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      float nameWidth = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName());
      float sliderX = x + nameWidth + 15.0F;
      String valueText = ((Number)this.savedValue.get().getStart()).floatValue() + " - " + ((Number)this.savedValue.get().getEndInclusive()).floatValue();
      float valueTextWidth = (float)Fonts.font40SemiBold.func_78256_a(valueText) + 15.0F;
      float sliderWidth = RangesKt.coerceAtLeast(width - nameWidth - valueTextWidth - 20.0F, 30.0F);
      float minX = sliderX + sliderWidth * ((((Number)this.savedValue.get().getStart()).floatValue() - this.savedValue.getMinimum()) / (this.savedValue.getMaximum() - this.savedValue.getMinimum()));
      float maxX = sliderX + sliderWidth * ((((Number)this.savedValue.get().getEndInclusive()).floatValue() - this.savedValue.getMinimum()) / (this.savedValue.getMaximum() - this.savedValue.getMinimum()));
      float sliderEnd = sliderX + sliderWidth;
      float clickedX = (float)mouseX;
      if (MouseUtils.mouseWithinBounds(mouseX, mouseY, sliderX, y + 5.0F, sliderEnd, y + 15.0F)) {
         float distToMin = Math.abs(clickedX - minX);
         float distToMax = Math.abs(clickedX - maxX);
         if (Math.abs(minX - maxX) < 1.5F) {
            if (clickedX < maxX) {
               this.draggingMin = true;
            } else {
               this.draggingMax = true;
            }
         } else if (distToMin < distToMax) {
            this.draggingMin = true;
         } else {
            this.draggingMax = true;
         }
      }

   }

   public void onRelease(int mouseX, int mouseY, float x, float y, float width) {
      this.draggingMin = false;
      this.draggingMax = false;
   }
}
