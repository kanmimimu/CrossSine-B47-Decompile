package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.KeyBindValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J@\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J0\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0002H\u0016R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/KeyBindElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "", "value", "Lnet/ccbluex/liquidbounce/features/value/KeyBindValue;", "(Lnet/ccbluex/liquidbounce/features/value/KeyBindValue;)V", "displayText", "", "kotlin.jvm.PlatformType", "drawElement", "", "mouseX", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "onKeyPress", "", "typed", "", "keyCode", "CrossSine"}
)
public final class KeyBindElement extends ValueElement {
   private String displayText;

   public KeyBindElement(@NotNull KeyBindValue value) {
      Intrinsics.checkNotNullParameter(value, "value");
      super(value);
      this.displayText = Keyboard.getKeyName(((Number)value.getValue()).intValue());
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      String showingText = ClickGui.Companion.getInstance().getKeyBinding() ? "Press a key..." : this.displayText;
      Fonts.font40SemiBold.func_175063_a(this.getValue().getName() + " : " + showingText, x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      float textX = x + 10.0F + (float)Fonts.font40SemiBold.func_78256_a(Intrinsics.stringPlus(this.getValue().getName(), " : "));
      float textY = y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F;
      GameFontRenderer var10000 = Fonts.font40SemiBold;
      String var9 = this.displayText;
      Intrinsics.checkNotNullExpressionValue(var9, "displayText");
      int textWidth = var10000.func_78256_a(var9);
      int textHeight = Fonts.font40SemiBold.field_78288_b;
      if ((float)mouseX >= textX && (float)mouseX <= textX + (float)textWidth && (float)mouseY >= textY && (float)mouseY <= textY + (float)textHeight) {
         ClickGui.Companion.getInstance().setKeyBinding(true);
      }

   }

   public boolean onKeyPress(char typed, int keyCode) {
      if (ClickGui.Companion.getInstance().getKeyBinding()) {
         if (keyCode == 211) {
            this.getValue().setValue(0);
            this.displayText = "NONE";
         } else if (keyCode != 1) {
            this.getValue().setValue(keyCode);
            this.displayText = Keyboard.getKeyName(keyCode);
         } else {
            this.getValue().setValue(0);
            this.displayText = "NONE";
         }

         ClickGui.Companion.getInstance().setKeyBinding(false);
         return true;
      } else {
         return false;
      }
   }
}
