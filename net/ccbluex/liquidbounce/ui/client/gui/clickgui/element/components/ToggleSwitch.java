package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.components;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/components/ToggleSwitch;", "", "()V", "smooth", "", "state", "", "getState", "()Z", "setState", "(Z)V", "onDraw", "", "x", "y", "width", "height", "bgColor", "Ljava/awt/Color;", "accentColor", "CrossSine"}
)
public final class ToggleSwitch {
   private float smooth;
   private boolean state;

   public final boolean getState() {
      return this.state;
   }

   public final void setState(boolean var1) {
      this.state = var1;
   }

   public final void onDraw(float x, float y, float width, float height, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      this.smooth = AnimHelperKt.animLinear(this.smooth, (this.state ? 0.2F : -0.2F) * (float)RenderUtils.deltaTime * 0.045F, 0.0F, 1.0F);
      float[] var8 = new float[]{0.0F, 1.0F};
      float[] var10000 = var8;
      Color mainColor = new Color[]{new Color(160, 160, 160), accentColor};
      Color borderColor = BlendUtils.blendColors(var10000, mainColor, this.smooth);
      float[] var9 = new float[]{0.0F, 1.0F};
      var10000 = var9;
      Color switchColor = new Color[]{bgColor, accentColor};
      mainColor = BlendUtils.blendColors(var10000, switchColor, this.smooth);
      float[] var10 = new float[]{0.0F, 1.0F};
      var10000 = var10;
      Color[] var15 = new Color[]{new Color(160, 160, 160), bgColor};
      switchColor = BlendUtils.blendColors(var10000, var15, this.smooth);
      RenderUtils.drawRoundedRect(x - 0.5F, y - 0.5F, x + width + 0.5F, y + height + 0.5F, (height + 1.0F) / 2.0F, borderColor.getRGB());
      RenderUtils.drawRoundedRect(x, y, x + width, y + height, height / 2.0F, mainColor.getRGB());
      RenderUtils.drawFilledCircle(x + (1.0F - this.smooth) * (2.0F + (height - 4.0F) / 2.0F) + this.smooth * (width - 2.0F - (height - 4.0F) / 2.0F), y + 2.0F + (height - 4.0F) / 2.0F, (height - 4.0F) / 2.0F, switchColor);
   }
}
