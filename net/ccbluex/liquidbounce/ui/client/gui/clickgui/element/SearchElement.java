package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.IconManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 JT\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u001d2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0006\u0010\u001f\u001a\u00020 J(\u0010-\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002J\u001c\u0010.\u001a\b\u0012\u0004\u0012\u00020/0+2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0002JL\u00100\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+JL\u00102\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+J\u0018\u00103\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u0003H\u0002JD\u00104\u001a\u00020\u001b2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+J\u0006\u00108\u001a\u00020\u001bJ\u0010\u00109\u001a\u00020\u001b2\u0006\u0010:\u001a\u00020/H\u0002R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000b\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\u0015¨\u0006;"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchElement;", "", "xPos", "", "yPos", "width", "height", "(FFFF)V", "animBox", "animScrollHeight", "getHeight", "()F", "lastHeight", "scrollHeight", "searchBox", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchBox;", "getSearchBox", "()Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchBox;", "startYY", "getWidth", "setWidth", "(F)V", "getXPos", "setXPos", "getYPos", "setYPos", "drawBox", "", "mouseX", "", "mouseY", "accentColor", "Ljava/awt/Color;", "drawPanel", "", "mX", "mY", "x", "y", "w", "h", "wheel", "ces", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/CategoryElement;", "drawScroll", "getSearchModules", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/ModuleElement;", "handleMouseClick", "mouseButton", "handleMouseRelease", "handleScrolling", "handleTyping", "typedChar", "", "keyCode", "isTyping", "searchMatch", "module", "CrossSine"}
)
public final class SearchElement {
   private float xPos;
   private float yPos;
   private float width;
   private final float height;
   private float scrollHeight;
   private float animScrollHeight;
   private float lastHeight;
   private final float startYY;
   private float animBox;
   @NotNull
   private final SearchBox searchBox;

   public SearchElement(float xPos, float yPos, float width, float height) {
      this.xPos = xPos;
      this.yPos = yPos;
      this.width = width;
      this.height = height;
      this.startYY = 5.0F;
      this.searchBox = new SearchBox(0, (int)this.xPos + 2, (int)this.yPos + 2, (int)this.width - 4, (int)this.height - 2);
   }

   public final float getXPos() {
      return this.xPos;
   }

   public final void setXPos(float var1) {
      this.xPos = var1;
   }

   public final float getYPos() {
      return this.yPos;
   }

   public final void setYPos(float var1) {
      this.yPos = var1;
   }

   public final float getWidth() {
      return this.width;
   }

   public final void setWidth(float var1) {
      this.width = var1;
   }

   public final float getHeight() {
      return this.height;
   }

   @NotNull
   public final SearchBox getSearchBox() {
      return this.searchBox;
   }

   public final boolean drawBox(int mouseX, int mouseY, @NotNull Color accentColor) {
      float var10001;
      float var10002;
      float var10003;
      label35: {
         Intrinsics.checkNotNullParameter(accentColor, "accentColor");
         var10001 = this.animBox;
         var10002 = 0.0045000003F * (float)RenderUtils.deltaTime;
         if (!this.searchBox.func_146206_l()) {
            String var4 = this.searchBox.func_146179_b();
            Intrinsics.checkNotNullExpressionValue(var4, "searchBox.text");
            if (((CharSequence)var4).length() <= 0) {
               var10003 = -1.0F;
               break label35;
            }
         }

         var10003 = 1.0F;
      }

      this.animBox = var10001 + var10002 * var10003;
      this.animBox = RangesKt.coerceIn(this.animBox, 0.0F, 1.0F);
      float percent = (float)EaseUtils.INSTANCE.easeInOutCirc((double)this.animBox);
      Stencil.write(true);
      RenderUtils.drawBloomGradientRoundedRect(this.xPos + (this.width - 20.0F - (this.width - 20.0F) * percent) / (float)2, this.yPos, this.xPos + (this.width - 20.0F - (this.width - 20.0F) * percent) / (float)2 + this.width * percent + (20.0F - 20.0F * percent), this.yPos + this.height, 6.0F, 5.0F, new Color(25, 25, 25), new Color(35, 35, 35), RenderUtils.ShaderBloom.BOTH);
      Stencil.erase(true);
      if (this.searchBox.func_146206_l()) {
         this.searchBox.func_146194_f();
      } else {
         String var5 = this.searchBox.func_146179_b();
         Intrinsics.checkNotNullExpressionValue(var5, "searchBox.text");
         if (((CharSequence)var5).length() == 0) {
            this.searchBox.func_146194_f();
            this.searchBox.func_146180_a("");
         } else {
            this.searchBox.func_146194_f();
         }
      }

      Stencil.dispose();
      GlStateManager.func_179118_c();
      RenderUtils.drawImage2(IconManager.INSTANCE.getSearch(), this.xPos + (this.width - 15.0F) * percent + (5.0F - 5.0F * percent) + (this.width - 20.0F - (this.width - 20.0F) * percent) / (float)2, this.yPos + 5.0F, 10, 10);
      GlStateManager.func_179141_d();
      String var7 = this.searchBox.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(var7, "searchBox.text");
      return ((CharSequence)var7).length() > 0;
   }

   private final boolean searchMatch(ModuleElement module) {
      CharSequence var10000 = (CharSequence)module.getModule().getName();
      String var2 = this.searchBox.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(var2, "searchBox.text");
      return StringsKt.contains(var10000, (CharSequence)var2, true);
   }

   private final List getSearchModules(List ces) {
      List modules = (List)(new ArrayList());
      Iterable $this$forEach$iv = (Iterable)ces;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         CategoryElement cat = (CategoryElement)element$iv;
         int var8 = 0;
         Iterable $this$filter$iv = (Iterable)cat.getModuleElements();
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            ModuleElement it = (ModuleElement)element$iv$iv;
            int var18 = 0;
            if (this.searchMatch(it)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         modules.addAll((Collection)((List)destination$iv$iv));
      }

      return modules;
   }

   public final void drawPanel(int mX, int mY, float x, float y, float w, float h, int wheel, @NotNull List ces, @NotNull Color accentColor) {
      Intrinsics.checkNotNullParameter(ces, "ces");
      Intrinsics.checkNotNullParameter(accentColor, "accentColor");
      int mouseX = 0;
      mouseX = mX;
      int mouseY = 0;
      mouseY = mY;
      this.lastHeight = 0.0F;
      Iterable $this$forEach$iv = (Iterable)this.getSearchModules(ces);
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         ModuleElement mod = (ModuleElement)element$iv;
         int var17 = 0;
         if (this.searchMatch(mod)) {
            this.lastHeight += mod.getAnimHeight() + 40.0F;
         }
      }

      if (this.lastHeight >= 10.0F) {
         this.lastHeight -= 10.0F;
      }

      this.handleScrolling(wheel, h);
      this.drawScroll(x, y + this.startYY, w, h);
      Fonts.font30SemiBold.func_175063_a("Search", ClickGui.Companion.getInstance().getWindowXStart() + 20.0F, y - 12.0F, -1);
      RenderUtils.drawImage2(IconManager.INSTANCE.getBack(), ClickGui.Companion.getInstance().getWindowXStart() + 4.0F, y - 15.0F, 10, 10);
      float startY = 0.0F;
      startY = y + this.startYY;
      if ((float)mY < y + this.startYY || (float)mY >= y + h) {
         mouseY = -1;
      }

      RenderUtils.makeScissorBox(x, y + this.startYY, x + w, y + h);
      GL11.glEnable(3089);
      Iterable $this$forEach$iv = (Iterable)ces;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         CategoryElement cat = (CategoryElement)element$iv;
         int var18 = 0;
         Iterable $this$forEach$iv = (Iterable)cat.getModuleElements();
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            int var24 = 0;
            if (this.searchMatch(mod)) {
               startY += !(startY + this.animScrollHeight > y + h) && !(startY + this.animScrollHeight + 40.0F + mod.getAnimHeight() < y + this.startYY) ? mod.drawElement(mouseX, mouseY, x, startY + this.animScrollHeight, w, 40.0F, accentColor) : 40.0F + mod.getAnimHeight();
            }
         }
      }

      GL11.glDisable(3089);
   }

   private final void handleScrolling(int wheel, float height) {
      if (wheel != 0) {
         if (wheel > 0) {
            this.scrollHeight += 50.0F;
         } else {
            this.scrollHeight -= 50.0F;
         }
      }

      if (this.lastHeight > height - (this.startYY + 10.0F)) {
         this.scrollHeight = RangesKt.coerceIn(this.scrollHeight, -this.lastHeight + height - (this.startYY + 10.0F), 0.0F);
      } else {
         this.scrollHeight = 0.0F;
      }

      this.animScrollHeight = AnimHelperKt.animSmooth(this.animScrollHeight, this.scrollHeight, 0.5F);
   }

   private final void drawScroll(float x, float y, float width, float height) {
      if (this.lastHeight > height - (this.startYY + 10.0F)) {
         float last = height - (this.startYY + 10.0F) - (height - (this.startYY + 10.0F)) * ((height - (this.startYY + 10.0F)) / this.lastHeight);
         float multiply = last * RangesKt.coerceIn(Math.abs(this.animScrollHeight / (-this.lastHeight + height - (this.startYY + 10.0F))), 0.0F, 1.0F);
         RenderUtils.originalRoundedRect(x + width - 6.0F, y + 5.0F + multiply, x + width - 4.0F, y + 5.0F + (height - (this.startYY + 10.0F)) * ((height - (this.startYY + 10.0F)) / this.lastHeight) + multiply, 1.0F, 1358954495);
      }

   }

   public final void handleMouseClick(int mX, int mY, int mouseButton, float x, float y, float w, float h, @NotNull List ces) {
      Intrinsics.checkNotNullParameter(ces, "ces");
      if (!MouseUtils.mouseWithinBounds(mX, mY, x - 200.0F, y - 20.0F, x - 170.0F, y)) {
         int mouseY = 0;
         mouseY = mY;
         this.searchBox.func_146192_a(mX, mY, mouseButton);
         String var10 = this.searchBox.func_146179_b();
         Intrinsics.checkNotNullExpressionValue(var10, "searchBox.text");
         if (((CharSequence)var10).length() != 0) {
            if ((float)mY < y + this.startYY || (float)mY >= y + h) {
               mouseY = -1;
            }

            float startY = 0.0F;
            startY = y + this.startYY;
            Iterable $this$forEach$iv = (Iterable)this.getSearchModules(ces);
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               ModuleElement mod = (ModuleElement)element$iv;
               int var16 = 0;
               mod.handleClick(mX, mouseY, mouseButton, x, startY + this.animScrollHeight, w, 40.0F);
               startY += 40.0F + mod.getAnimHeight();
            }

         }
      }
   }

   public final void handleMouseRelease(int mX, int mY, int mouseButton, float x, float y, float w, float h, @NotNull List ces) {
      Intrinsics.checkNotNullParameter(ces, "ces");
      int mouseX = 0;
      mouseX = mX;
      int mouseY = 0;
      mouseY = mY;
      String var11 = this.searchBox.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(var11, "searchBox.text");
      if (((CharSequence)var11).length() != 0) {
         if ((float)mY < y + this.startYY || (float)mY >= y + h) {
            mouseY = -1;
         }

         float startY = 0.0F;
         startY = y + this.startYY;
         Iterable $this$forEach$iv = (Iterable)this.getSearchModules(ces);
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            int var17 = 0;
            mod.handleRelease(mouseX, mouseY, x, startY + this.animScrollHeight, w, 40.0F);
            startY += 40.0F + mod.getAnimHeight();
         }

      }
   }

   public final boolean handleTyping(char typedChar, int keyCode, float x, float y, float w, float h, @NotNull List ces) {
      Intrinsics.checkNotNullParameter(ces, "ces");
      this.searchBox.func_146201_a(typedChar, keyCode);
      String var8 = this.searchBox.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(var8, "searchBox.text");
      if (((CharSequence)var8).length() == 0) {
         return false;
      } else {
         Iterable $this$forEach$iv = (Iterable)this.getSearchModules(ces);
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            int var13 = 0;
            if (mod.handleKeyTyped(typedChar, keyCode)) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean isTyping() {
      String var1 = this.searchBox.func_146179_b();
      Intrinsics.checkNotNullExpressionValue(var1, "searchBox.text");
      return ((CharSequence)var1).length() > 0;
   }
}
