package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl;

import java.awt.Color;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J@\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J0\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/impl/FontElement;", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "Lnet/minecraft/client/gui/FontRenderer;", "saveValue", "Lnet/ccbluex/liquidbounce/features/value/FontValue;", "(Lnet/ccbluex/liquidbounce/features/value/FontValue;)V", "getSaveValue", "()Lnet/ccbluex/liquidbounce/features/value/FontValue;", "drawElement", "", "mouseX", "", "mouseY", "x", "y", "width", "bgColor", "Ljava/awt/Color;", "accentColor", "onClick", "", "CrossSine"}
)
public final class FontElement extends ValueElement {
   @NotNull
   private final FontValue saveValue;

   public FontElement(@NotNull FontValue saveValue) {
      Intrinsics.checkNotNullParameter(saveValue, "saveValue");
      super(saveValue);
      this.saveValue = saveValue;
   }

   @NotNull
   public final FontValue getSaveValue() {
      return this.saveValue;
   }

   public float drawElement(int mouseX, int mouseY, float x, float y, float width, @NotNull Color bgColor, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(bgColor, "bgColor");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      FontRenderer fontValue = (FontRenderer)this.getValue().get();
      String displayString = "Font: Unknown";
      if (fontValue instanceof GameFontRenderer) {
         displayString = "Font: " + ((GameFontRenderer)fontValue).getDefaultFont().getFont().getName() + " - " + ((GameFontRenderer)fontValue).getDefaultFont().getFont().getSize();
      } else if (Intrinsics.areEqual((Object)fontValue, (Object)Fonts.minecraftFont)) {
         displayString = "Font: Minecraft";
      } else {
         Object[] objects = Fonts.getFontDetails(fontValue);
         if (objects != null) {
            StringBuilder var10000 = (new StringBuilder()).append(objects[0]);
            Object var10001 = objects[1];
            if (objects[1] == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
            }

            displayString = var10000.append((Integer)var10001 != -1 ? Intrinsics.stringPlus(" - ", objects[1]) : "").toString();
         }
      }

      Fonts.font40SemiBold.func_175063_a(displayString, x + 10.0F, y + 10.0F - (float)Fonts.font40SemiBold.field_78288_b / 2.0F + 2.0F, -1);
      return this.getValueHeight();
   }

   public void onClick(int mouseX, int mouseY, float x, float y, float width) {
      FontRenderer fontValue = (FontRenderer)this.getValue().get();
      if (this.isDisplayable() && MouseUtils.mouseWithinBounds(mouseX, mouseY, x, y, x + width, y + 20.0F)) {
         List fonts = Fonts.getFonts();
         if (Mouse.isButtonDown(0)) {
            for(int i = 0; i < fonts.size(); ++i) {
               FontRenderer font = (FontRenderer)fonts.get(i);
               if (Intrinsics.areEqual((Object)font, (Object)fontValue)) {
                  ++i;
                  if (i >= fonts.size()) {
                     i = 0;
                  }

                  Value var10000 = this.getValue();
                  Object var10 = fonts.get(i);
                  Intrinsics.checkNotNullExpressionValue(var10, "fonts[i]");
                  var10000.set(var10);
                  break;
               }
            }
         } else {
            for(int i = fonts.size() - 1; i >= 0; i += -1) {
               Object var15 = fonts.get(i);
               Intrinsics.checkNotNullExpressionValue(var15, "fonts[i]");
               FontRenderer font = (FontRenderer)var15;
               if (Intrinsics.areEqual((Object)font, (Object)fontValue)) {
                  i += -1;
                  if (i >= fonts.size()) {
                     i = 0;
                  }

                  if (i < 0) {
                     i = fonts.size() - 1;
                  }

                  Value var17 = this.getValue();
                  var15 = fonts.get(i);
                  Intrinsics.checkNotNullExpressionValue(var15, "fonts[i]");
                  var17.set(var15);
                  break;
               }
            }
         }
      }

   }
}
