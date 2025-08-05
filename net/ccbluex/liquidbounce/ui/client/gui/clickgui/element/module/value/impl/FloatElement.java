package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components.Slider;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J@\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0016J0\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002H\u0016J0\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0002H\u0016J\u0010\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0002H\u0002R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/FloatElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "savedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "(Lnet/ccbluex/liquidbounce/features/value/FloatValue;)V", "anim", "dragged", "", "formatter", "Ljava/text/DecimalFormat;", "getSavedValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "slider", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/Slider;", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onRelease", "round", "f", "CrossSine"}
)
public final class FloatElement extends ValueElement {
   @NotNull
   private final FloatValue savedValue;
   @NotNull
   private final Slider slider;
   private boolean dragged;
   private float anim;
   @NotNull
   private final DecimalFormat formatter;

   public FloatElement(@NotNull FloatValue savedValue) {
      Intrinsics.checkNotNullParameter(savedValue, "savedValue");
      super(savedValue);
      this.savedValue = savedValue;
      this.slider = new Slider();
      this.formatter = new DecimalFormat("0.00");
   }

   @NotNull
   public final FloatValue getSavedValue() {
      return this.savedValue;
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      GameFontRenderer var10000 = Fonts.font40SemiBold;
      String var9 = this.formatter.format(this.savedValue.getMaximum() + 0.01F);
      Intrinsics.checkNotNullExpressionValue(var9, "formatter.format(savedValue.maximum + 0.01F)");
      int valueDisplay = var10000.func_78256_a(var9);
      float nameLength = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) - 5.0F;
      float sliderWidth = width - 50.0F - nameLength - (float)valueDisplay;
      float startPoint = x + width - 20.0F - sliderWidth - (float)valueDisplay;
      this.anim = AnimHelperKt.animSmooth(this.anim, this.dragged ? 1.0F : 0.0F, 0.05F);
      float percent = (float)EaseUtils.INSTANCE.easeInSine((double)this.anim);
      if (this.dragged) {
         this.savedValue.set(RangesKt.coerceIn(this.round(this.savedValue.getMinimum() + (this.savedValue.getMaximum() - this.savedValue.getMinimum()) / sliderWidth * ((float)mouseX - startPoint)), this.savedValue.getMinimum(), this.savedValue.getMaximum()));
      }

      Fonts.font40SemiBold.func_175063_a(this.getValue().getName(), x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      this.slider.setValue(RangesKt.coerceIn(((Number)this.savedValue.get()).floatValue(), this.savedValue.getMinimum(), this.savedValue.getMaximum()), this.savedValue.getMinimum(), this.savedValue.getMaximum());
      this.slider.onDraw(x + width - 20.0F - sliderWidth - (float)valueDisplay, y + 11.0F, sliderWidth, accentColor, percent);
      var10000 = Fonts.font40SemiBold;
      String var13 = this.formatter.format(this.round(((Number)this.savedValue.get()).floatValue()));
      Intrinsics.checkNotNullExpressionValue(var13, "formatter.format(round(savedValue.get()))");
      var10000.func_175063_a(var13, x + width - (float)valueDisplay - 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      GameFontRenderer var10000 = Fonts.font40SemiBold;
      String var7 = this.formatter.format(this.savedValue.getMaximum() + 0.01F);
      Intrinsics.checkNotNullExpressionValue(var7, "formatter.format(savedValue.maximum + 0.01F)");
      int valueDisplay = var10000.func_78256_a(var7);
      float nameLength = (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) - 5.0F;
      float sliderWidth = width - 50.0F - nameLength - (float)valueDisplay;
      float startPoint = x + width - 30.0F - sliderWidth - (float)valueDisplay;
      float endPoint = x + width - 10.0F - (float)valueDisplay;
      if (MouseUtils.mouseWithinBounds(mouseX, mouseY, startPoint, y + 5.0F, endPoint, y + 15.0F)) {
         this.dragged = true;
      }

   }

   public void onRelease(int mouseX, int mouseY, float x, float y, float width) {
      if (this.dragged) {
         this.dragged = false;
      }

   }

   private final float round(float f) {
      return (new BigDecimal(String.valueOf(f))).setScale(2, 4).floatValue();
   }
}
