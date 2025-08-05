package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J@\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0002H\u0016J0\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0016J0\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/ColorElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "Ljava/awt/Color;", "colorValue", "Lnet/ccbluex/liquidbounce/features/value/ColorValue;", "(Lnet/ccbluex/liquidbounce/features/value/ColorValue;)V", "alpha", "", "brightness", "draggingAlpha", "", "draggingHue", "draggingSB", "expanded", "expandedAnim", "hue", "saturation", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "accentColor", "onClick", "", "onRelease", "CrossSine"}
)
public final class ColorElement extends ValueElement {
   @NotNull
   private final ColorValue colorValue;
   private float hue;
   private float saturation;
   private float brightness;
   private float alpha;
   private boolean draggingSB;
   private boolean draggingHue;
   private boolean draggingAlpha;
   private boolean expanded;
   private float expandedAnim;

   public ColorElement(@NotNull ColorValue colorValue) {
      Intrinsics.checkNotNullParameter(colorValue, "colorValue");
      super(colorValue);
      this.colorValue = colorValue;
      this.saturation = 1.0F;
      this.brightness = 1.0F;
      this.alpha = 1.0F;
      float[] hsb = Color.RGBtoHSB(((Color)this.colorValue.getValue()).getRed(), ((Color)this.colorValue.getValue()).getGreen(), ((Color)this.colorValue.getValue()).getBlue(), (float[])null);
      this.hue = hsb[0];
      this.saturation = hsb[1];
      this.brightness = hsb[2];
      this.alpha = (float)((Color)this.colorValue.getValue()).getAlpha() / 255.0F;
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      Color baseColor = Color.getHSBColor(this.hue, this.saturation, this.brightness);
      this.expandedAnim = AnimHelperKt.animSmooth(this.expandedAnim, this.expanded ? 1.0F : 0.0F, 0.1F);
      float percent = (float)EaseUtils.INSTANCE.easeOutCirc((double)this.expandedAnim);
      ColorValue var10000 = this.colorValue;
      Intrinsics.checkNotNullExpressionValue(baseColor, "baseColor");
      var10000.set(ColorExtensionKt.setAlpha(baseColor, this.alpha));
      Fonts.font40SemiBold.drawString(Intrinsics.stringPlus(this.getValue().getName(), ": "), x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, Color.WHITE.getRGB());
      RenderUtils.drawRoundedLimitArea(x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F, y + (float)5, x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F + 30.0F, y + (float)17, 5.0F, ColorElement::drawElement$lambda-0);
      RenderUtils.drawRoundedRect(x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F, y + (float)5, x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F + 30.0F, y + (float)17, 5.0F, ColorExtensionKt.setAlpha(baseColor, this.alpha).getRGB());
      RenderUtils.drawRoundedOutline(x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F, y + (float)5, x + (float)Fonts.font40SemiBold.func_78256_a(this.getValue().getName()) + 10.0F + 5.0F + 30.0F, y + (float)17, 6.0F, 2.0F, Color.WHITE.getRGB());
      float pickerSize = 75.0F;
      if (this.expandedAnim > 0.0F) {
         float pickerX = x + (float)15;
         float pickerY = y + 20.0F + (float)5 * percent;
         RenderUtils.drawRoundedLimitArea(pickerX - 5.0F, pickerY - 5.0F, (pickerX + pickerSize + 5.0F) * percent, (pickerY + pickerSize + 5.0F) * percent, 5.0F, ColorElement::drawElement$lambda-1);
         float circleX = pickerX + this.saturation * pickerSize * percent;
         float circleY = pickerY + ((float)1 - this.brightness) * pickerSize * percent;
         RenderUtils.drawCircleOutline(circleX, circleY, 4.0F * percent, Color.WHITE);
         float hueBarY = pickerY + (pickerSize + 8.0F) * percent;
         RenderUtils.drawRoundedLimitArea(pickerX - 5.0F, hueBarY, (pickerX - 5.0F + pickerSize + 10.0F) * percent, (hueBarY + 10.0F) * percent, 5.0F, ColorElement::drawElement$lambda-2);
         RenderUtils.drawCircleOutline(pickerX + this.hue * pickerSize * percent, hueBarY + 5.0F, 4.0F * percent, Color.WHITE);
         if (this.colorValue.getAlpha()) {
            float alphaBarY = hueBarY + 5.0F * percent + 8.0F;
            RenderUtils.drawRoundedLimitArea(pickerX - 5.0F, alphaBarY, (pickerX - 5.0F + pickerSize + 10.0F) * percent, (alphaBarY + 10.0F) * percent, 5.0F, ColorElement::drawElement$lambda-3);
            RenderUtils.drawCircleOutline(pickerX + this.alpha * pickerSize * percent, alphaBarY + 5.0F, 4.0F * percent, Color.WHITE);
         }

         if (this.draggingSB) {
            this.saturation = RangesKt.coerceIn(((float)mouseX - pickerX) / pickerSize, 0.0F, 1.0F);
            this.brightness = RangesKt.coerceIn(1.0F - ((float)mouseY - pickerY) / pickerSize, 0.0F, 1.0F);
         }

         if (this.draggingHue) {
            this.hue = RangesKt.coerceIn(((float)mouseX - pickerX) / pickerSize, 0.0F, 1.0F);
         }

         if (this.colorValue.getAlpha() && this.draggingAlpha) {
            this.alpha = RangesKt.coerceIn(((float)mouseX - pickerX) / pickerSize, 0.0F, 1.0F);
         }
      }

      this.setValueHeight(20.0F + (pickerSize + 20.0F + (this.colorValue.getAlpha() ? 20.0F : 0.0F)) * percent);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      float topHeight = 14.0F;
      if (MouseUtils.mouseWithinBounds(mouseX, mouseY, x, y, x + width, y + topHeight)) {
         this.expanded = !this.expanded;
      } else {
         if (this.expanded) {
            float pickerX = x + (float)15;
            float pickerY = y + (float)20 + (float)5;
            float pickerSize = 75.0F;
            float hueBarY = pickerY + pickerSize + 8.0F;
            float alphaBarY = hueBarY + 20.0F;
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, pickerX, pickerY, pickerX + pickerSize, pickerY + pickerSize)) {
               this.draggingSB = true;
            } else if (MouseUtils.mouseWithinBounds(mouseX, mouseY, pickerX, hueBarY - 3.0F, pickerX + pickerSize, hueBarY + 10.0F)) {
               this.draggingHue = true;
            } else if (MouseUtils.mouseWithinBounds(mouseX, mouseY, pickerX, alphaBarY - 8.0F, pickerX + pickerSize, alphaBarY + 5.0F) && this.colorValue.getAlpha()) {
               this.draggingAlpha = true;
            }
         }

      }
   }

   public void onRelease(int mouseX, int mouseY, float x, float y, float width) {
      this.draggingSB = false;
      this.draggingHue = false;
      this.draggingAlpha = false;
   }

   private static final void drawElement$lambda_0/* $FF was: drawElement$lambda-0*/(float $x, ColorElement this$0, float $y) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      RenderUtils.drawCheckerboard($x + (float)Fonts.font40SemiBold.func_78256_a(this$0.getValue().getName()) + 10.0F + 5.0F, $y + (float)5, 30.0F, 12.0F, 4);
   }

   private static final void drawElement$lambda_1/* $FF was: drawElement$lambda-1*/(float $pickerX, float $pickerY, float $pickerSize, float $percent, ColorElement this$0) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      RenderUtils.drawSaturationBrightnessPicker($pickerX - 5.0F, $pickerY - 5.0F, ($pickerSize + 10.0F) * $percent, ($pickerSize + 10.0F) * $percent, this$0.hue);
   }

   private static final void drawElement$lambda_2/* $FF was: drawElement$lambda-2*/(float $pickerX, float $hueBarY, float $pickerSize, float $percent) {
      RenderUtils.drawHueBar($pickerX - 5.0F, $hueBarY, ($pickerSize + 10.0F) * $percent, 10.0F * $percent);
   }

   private static final void drawElement$lambda_3/* $FF was: drawElement$lambda-3*/(float $pickerX, float $alphaBarY, float $pickerSize, float $percent) {
      RenderUtils.drawAlphaBar($pickerX - 5.0F, $alphaBarY, ($pickerSize + 10.0F) * $percent, 10.0F * $percent);
   }
}
