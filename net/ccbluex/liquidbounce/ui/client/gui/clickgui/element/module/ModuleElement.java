package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ColorValue;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.KeyBindValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.ValueElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.BooleanElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.ColorElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.FloatElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.FloatRangeElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.FontElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.IntElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.IntRangeElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.KeyBindElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.ListElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.TextElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.value.impl.TitleElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J>\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$J>\u0010%\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J\u0016\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u001dJ6\u0010,\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006J\u0006\u0010-\u001a\u00020\rJ\u0006\u0010.\u001a\u00020&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/ModuleElement;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "animHeight", "", "getAnimHeight", "()F", "setAnimHeight", "(F)V", "animPercent", "expanded", "", "getExpanded", "()Z", "setExpanded", "(Z)V", "fadeKeybind", "listeningToKey", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "smooth", "smoothHovered", "valueElements", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/value/ValueElement;", "drawElement", "mouseX", "", "mouseY", "x", "y", "width", "height", "accentColor", "Ljava/awt/Color;", "handleClick", "", "mouseButton", "handleKeyTyped", "typed", "", "code", "handleRelease", "listeningKeybind", "resetState", "CrossSine"}
)
public final class ModuleElement extends MinecraftInstance {
   @NotNull
   private final Module module;
   @NotNull
   private final List valueElements;
   private float smooth;
   private float smoothHovered;
   private float animHeight;
   private float fadeKeybind;
   private float animPercent;
   private boolean listeningToKey;
   private boolean expanded;

   public ModuleElement(@NotNull Module module) {
      Intrinsics.checkNotNullParameter(module, "module");
      super();
      this.module = module;
      this.valueElements = (List)(new ArrayList());

      for(Value value : this.module.getValues()) {
         if (value instanceof BoolValue) {
            this.valueElements.add(new BooleanElement((BoolValue)value));
         }

         if (value instanceof ListValue) {
            this.valueElements.add(new ListElement((ListValue)value));
         }

         if (value instanceof IntegerValue) {
            this.valueElements.add(new IntElement((IntegerValue)value));
         }

         if (value instanceof FloatValue) {
            this.valueElements.add(new FloatElement((FloatValue)value));
         }

         if (value instanceof FontValue) {
            this.valueElements.add(new FontElement((FontValue)value));
         }

         if (value instanceof TitleValue) {
            this.valueElements.add(new TitleElement((TitleValue)value));
         }

         if (value instanceof TextValue) {
            this.valueElements.add(new TextElement((TextValue)value));
         }

         if (value instanceof KeyBindValue) {
            this.valueElements.add(new KeyBindElement((KeyBindValue)value));
         }

         if (value instanceof ColorValue) {
            this.valueElements.add(new ColorElement((ColorValue)value));
         }

         if (value instanceof IntegerRangeValue) {
            this.valueElements.add(new IntRangeElement((IntegerRangeValue)value));
         }

         if (value instanceof FloatRangeValue) {
            this.valueElements.add(new FloatRangeElement((FloatRangeValue)value));
         }
      }

   }

   @NotNull
   public final Module getModule() {
      return this.module;
   }

   public final float getAnimHeight() {
      return this.animHeight;
   }

   public final void setAnimHeight(float var1) {
      this.animHeight = var1;
   }

   public final boolean getExpanded() {
      return this.expanded;
   }

   public final void setExpanded(boolean var1) {
      this.expanded = var1;
   }

   public final float drawElement(int mouseX, int mouseY, float x, float y, float width, float height, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      this.animPercent = AnimHelperKt.animSmooth(this.animPercent, this.expanded ? 100.0F : 0.0F, 0.75F);
      this.smooth = AnimHelperKt.animLinear(this.smooth, (this.module.getState() ? 0.3F : -0.3F) * (float)RenderUtils.deltaTime * 0.025F, 0.0F, 1.0F);
      this.smoothHovered = AnimHelperKt.animLinear(this.smoothHovered, (MouseUtils.mouseWithinBounds(mouseX, mouseY, x + 10.0F, y + 5.0F, x + width - 10.0F, y + height - 5.0F) ? 0.15F : -0.15F) * (float)RenderUtils.deltaTime * 0.025F, 0.0F, 1.0F);
      float easeSmooth = (float)EaseUtils.INSTANCE.easeOutCirc((double)this.smooth);
      float expectedHeight = 0.0F;

      for(ValueElement ve : this.valueElements) {
         if (ve.isDisplayable()) {
            expectedHeight += ve.getValueHeight();
         }
      }

      this.animHeight = this.animPercent / 100.0F * (expectedHeight + 10.0F);
      RenderUtils.drawBloomGradientRoundedRect(x + 10.0F, y + 5.0F, x + width - 10.0F, y + height + this.animHeight - 5.0F, 6.0F, 0.5F, new Color(25 + (int)((float)20 * this.smoothHovered), 25 + (int)((float)20 * this.smoothHovered), 25 + (int)((float)20 * this.smoothHovered)), new Color(35 - (int)((float)20 * this.smoothHovered), 35 - (int)((float)20 * this.smoothHovered), 35 - (int)((float)20 * this.smoothHovered)), RenderUtils.ShaderBloom.BOTH);
      if (this.smooth > 0.0F) {
         RenderUtils.drawRoundedGradientOutlineCorner(x + 10.0F + width / (float)2 - width / (float)2 * easeSmooth, y + 5.0F + ((height + this.animHeight - 5.0F) / (float)2 - (height + this.animHeight - 5.0F) / (float)2 * easeSmooth), x + width - 10.0F - width / (float)2 + width / (float)2 * easeSmooth, y + height + this.animHeight - 5.0F - (height + this.animHeight - 5.0F) / (float)2 + (height + this.animHeight - 5.0F) / (float)2 * easeSmooth, 3.0F, 8.0F * easeSmooth, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 255, false, 4, (Object)null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 90, 255, false, 4, (Object)null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 180, 255, false, 4, (Object)null).getRGB(), ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 270, 255, false, 4, (Object)null).getRGB());
      }

      RenderUtils.drawRoundedLimitArea(x + 10.0F, y + 5.0F, x + width - 10.0F, y + height + this.animHeight - 5.0F, 6.0F, ModuleElement::drawElement$lambda-0);
      return height + this.animHeight;
   }

   public final void handleClick(int mouseX, int mouseY, int mouseButton, float x, float y, float width, float height) {
      if (this.listeningToKey) {
         this.resetState();
      } else {
         String keyName = this.listeningToKey ? "Listening" : Keyboard.getKeyName(this.module.getKeyBind());
         float var10002 = x + 25.0F + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
         float var10003 = y + height / 2.0F - (float)Fonts.font40SemiBold.field_78288_b + 4.5F;
         float var10004 = x + 35.0F + (float)Fonts.font40SemiBold.func_78256_a(this.module.getName());
         GameFontRenderer var10005 = Fonts.font24SemiBold;
         Intrinsics.checkNotNullExpressionValue(keyName, "keyName");
         if (MouseUtils.mouseWithinBounds(mouseX, mouseY, var10002, var10003, var10004 + (float)var10005.func_78256_a(keyName), y + 2.5F + height / 2.0F)) {
            this.listeningToKey = true;
            ClickGui.Companion.getInstance().setCant(true);
         } else {
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, x + 10.0F, y + 5.0F, x + width - 10.0F, y + height - 5.0F)) {
               if (mouseButton == 0) {
                  this.module.toggle();
               } else if (!((Collection)this.module.getValues()).isEmpty() && mouseButton == 1) {
                  this.expanded = !this.expanded;
               }
            }

            if (this.expanded) {
               float startY = y + height;

               for(ValueElement ve : this.valueElements) {
                  if (ve.isDisplayable()) {
                     ve.onClick(mouseX, mouseY, x + 10.0F, startY, width - 20.0F);
                     startY += ve.getValueHeight();
                  }
               }
            }

         }
      }
   }

   public final void handleRelease(int mouseX, int mouseY, float x, float y, float width, float height) {
      if (this.expanded) {
         float startY = y + height;

         for(ValueElement ve : this.valueElements) {
            if (ve.isDisplayable()) {
               ve.onRelease(mouseX, mouseY, x + 10.0F, startY, width - 20.0F);
               startY += ve.getValueHeight();
            }
         }
      }

   }

   public final boolean handleKeyTyped(char typed, int code) {
      if (this.listeningToKey) {
         if (code == 1) {
            this.module.setKeyBind(0);
            this.listeningToKey = false;
            ClickGui.Companion.getInstance().setCant(false);
         } else {
            this.module.setKeyBind(code);
            this.listeningToKey = false;
            ClickGui.Companion.getInstance().setCant(false);
         }

         return true;
      } else {
         if (this.expanded) {
            for(ValueElement ve : this.valueElements) {
               if (ve.isDisplayable() && ve.onKeyPress(typed, code)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public final boolean listeningKeybind() {
      return this.listeningToKey;
   }

   public final void resetState() {
      this.listeningToKey = false;
      ClickGui.Companion.getInstance().setCant(false);
   }

   private static final void drawElement$lambda_0/* $FF was: drawElement$lambda-0*/(ModuleElement this$0, float $x, float $y, float $height, int $mouseX, int $mouseY, float $width, Color $accentColor) {
      Intrinsics.checkNotNullParameter(this$0, "this$0");
      Intrinsics.checkNotNullParameter($accentColor, "$accentColor");
      Fonts.font40SemiBold.func_175063_a(this$0.module.getName(), $x + 18.5F, $y + 4.0F + $height / 2.0F - (float)Fonts.font40SemiBold.field_78288_b + 3.0F, -1);
      String keyName = this$0.listeningToKey ? "Listening" : Keyboard.getKeyName(this$0.module.getKeyBind());
      float var10002 = $x + 25.0F + (float)Fonts.font40SemiBold.func_78256_a(this$0.module.getName());
      float var10003 = $y + $height / 2.0F - (float)Fonts.font40SemiBold.field_78288_b + 4.5F;
      float var10004 = $x + 35.0F + (float)Fonts.font40SemiBold.func_78256_a(this$0.module.getName());
      GameFontRenderer var10005 = Fonts.font24SemiBold;
      Intrinsics.checkNotNullExpressionValue(keyName, "keyName");
      if (MouseUtils.mouseWithinBounds($mouseX, $mouseY, var10002, var10003, var10004 + (float)var10005.func_78256_a(keyName), $y + 2.5F + $height / 2.0F)) {
         this$0.fadeKeybind = RangesKt.coerceIn(this$0.fadeKeybind + 0.1F * (float)RenderUtils.deltaTime * 0.025F, 0.0F, 1.0F);
      } else {
         this$0.fadeKeybind = RangesKt.coerceIn(this$0.fadeKeybind - 0.1F * (float)RenderUtils.deltaTime * 0.025F, 0.0F, 1.0F);
      }

      RenderUtils.drawRoundedRect($x + 25.0F + (float)Fonts.font40SemiBold.func_78256_a(this$0.module.getName()), $y + $height / 2.0F - (float)Fonts.font40SemiBold.field_78288_b + 6.5F, $x + 35.0F + (float)Fonts.font40SemiBold.func_78256_a(this$0.module.getName()) + (float)Fonts.font24SemiBold.func_78256_a(keyName), $y + 4.5F + $height / 2.0F, 2.0F, BlendUtils.blend(new Color(-12237499), new Color(-13290187), (double)this$0.fadeKeybind).getRGB());
      Fonts.font24SemiBold.func_175063_a(keyName, $x + 30.5F + (float)Fonts.font40SemiBold.func_78256_a(this$0.module.getName()), $y + $height / 2.0F - (float)Fonts.font40SemiBold.field_78288_b + 10.0F, -1);
      if (this$0.expanded || this$0.animHeight > 0.0F) {
         float startYPos = $y + $height;

         for(ValueElement ve : this$0.valueElements) {
            if (ve.isDisplayable()) {
               startYPos += ve.drawElement($mouseX, $mouseY, $x + 10.0F, startYPos, $width - 20.0F, new Color(-14342875), $accentColor);
            }
         }
      }

   }
}
