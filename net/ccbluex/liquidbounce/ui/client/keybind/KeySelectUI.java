package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0002J\u0018\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0010H\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0016J \u0010\u000f\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u0010H\u0016J\b\u0010!\u001a\u00020\u0015H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/keybind/KeySelectUI;", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "info", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "animationScroll", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "height", "", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "maxStroll", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scroll", "", "singleHeight", "str", "", "apply", "", "module", "click", "mouseX", "mouseY", "close", "key", "typedChar", "", "keyCode", "render", "wheel", "update", "CrossSine"}
)
public final class KeySelectUI extends PopUI {
   @NotNull
   private final KeyInfo info;
   @NotNull
   private String str;
   @NotNull
   private List modules;
   private final float singleHeight;
   private int scroll;
   @Nullable
   private Animation animationScroll;
   private float maxStroll;
   private final float height;

   public KeySelectUI(@NotNull KeyInfo info) {
      Intrinsics.checkNotNullParameter(info, "info");
      super("Select a module to bind");
      this.info = info;
      this.str = "";
      this.modules = CollectionsKt.toList((Iterable)CrossSine.INSTANCE.getModuleManager().getModules());
      this.singleHeight = (float)Fonts.SFApple35.getHeight();
      this.maxStroll = (float)this.modules.size() * this.singleHeight;
      this.height = 8.0F + (float)Fonts.SFApple40.getHeight() + (float)Fonts.SFApple35.getHeight() + 0.5F;
   }

   @NotNull
   public final KeyInfo getInfo() {
      return this.info;
   }

   public void render() {
      if (this.animationScroll == null) {
         this.animationScroll = new Animation(Easing.EASE_OUT_CIRC, 20L);
         Animation var10000 = this.animationScroll;
         Intrinsics.checkNotNull(var10000);
         var10000.value = (double)this.scroll;
      }

      Animation var10 = this.animationScroll;
      Intrinsics.checkNotNull(var10);
      var10.run((double)this.scroll);
      float var11 = this.height;
      Animation var10001 = this.animationScroll;
      Intrinsics.checkNotNull(var10001);
      float yOffset = var11 - (float)var10001.value + 5.0F;
      if (StringsKt.startsWith$default(this.str, ".", false, 2, (Object)null)) {
         Fonts.SFApple35.func_175065_a("Press ENTER to add macro.", 8.0F, this.singleHeight + yOffset, Color.BLACK.getRGB(), false);
      } else {
         for(Module module : this.modules) {
            if (yOffset > this.height - this.singleHeight && yOffset - this.singleHeight < 190.0F) {
               GL11.glPushMatrix();
               GL11.glTranslatef(0.0F, yOffset, 0.0F);
               String name = module.getName();
               GameFontRenderer var12 = Fonts.SFApple35;
               String var16;
               if (((CharSequence)this.str).length() > 0) {
                  StringBuilder var14 = (new StringBuilder()).append("§0");
                  String var5 = name.substring(0, this.str.length());
                  Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String…ing(startIndex, endIndex)");
                  var14 = var14.append(var5).append("§7");
                  var5 = name.substring(this.str.length(), name.length());
                  Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String…ing(startIndex, endIndex)");
                  var16 = var14.append(var5).toString();
               } else {
                  var16 = Intrinsics.stringPlus("§0", name);
               }

               var12.func_175065_a(var16, 8.0F, this.singleHeight * 0.5F, Color.BLACK.getRGB(), false);
               GL11.glPopMatrix();
            }

            yOffset += this.singleHeight;
         }
      }

      RenderUtils.drawRect(0.0F, 8.0F + (float)Fonts.SFApple40.getHeight(), (float)this.getBaseWidth(), this.height + 5.0F, Color.WHITE.getRGB());
      RenderUtils.drawRect(0.0F, (float)this.getBaseHeight() - this.singleHeight, (float)this.getBaseWidth(), (float)this.getBaseHeight(), Color.WHITE.getRGB());
      GameFontRenderer var13 = Fonts.SFApple35;
      CharSequence var7 = (CharSequence)this.str;
      Object var17;
      if (var7.length() == 0) {
         GameFontRenderer var6 = var13;
         int var8 = 0;
         var17 = "Search...";
         var13 = var6;
      } else {
         var17 = var7;
      }

      var13.func_175065_a((String)var17, 8.0F, 8.0F + (float)Fonts.SFApple40.getHeight() + 4.0F, Color.LIGHT_GRAY.getRGB(), false);
      RenderUtils.drawRect(8.0F, this.height + 2.0F, (float)this.getBaseWidth() - 8.0F, this.height + 3.0F, Color.LIGHT_GRAY.getRGB());
   }

   public void key(char typedChar, int keyCode) {
      switch (keyCode) {
         case 14:
            if (((CharSequence)this.str).length() > 0) {
               String var3 = this.str.substring(0, this.str.length() - 1);
               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String…ing(startIndex, endIndex)");
               this.str = var3;
               this.update();
            }

            return;
         case 28:
            if (StringsKt.startsWith$default(this.str, ".", false, 2, (Object)null)) {
               CrossSine.INSTANCE.getMacroManager().getMacros().add(new Macro(this.info.getKey(), this.str));
               CrossSine.INSTANCE.getKeyBindManager().updateAllKeys();
               this.close();
            } else if (!((Collection)this.modules).isEmpty()) {
               this.apply((Module)this.modules.get(0));
            }

            return;
         default:
            if (ChatAllowedCharacters.func_71566_a(typedChar)) {
               this.str = Intrinsics.stringPlus(this.str, typedChar);
               this.update();
            }

      }
   }

   public void scroll(float mouseX, float mouseY, int wheel) {
      int afterStroll = this.scroll - wheel / 10;
      if (afterStroll > 0 && (float)afterStroll < this.maxStroll - (float)100) {
         this.scroll = afterStroll;
      }

   }

   public void click(float mouseX, float mouseY) {
      if (!(mouseX < 8.0F) && !(mouseX > (float)(this.getBaseWidth() - 8)) && !(mouseY < this.height) && !(mouseY > (float)this.getBaseHeight() - this.singleHeight)) {
         double var10000 = (double)this.height;
         Animation var10001 = this.animationScroll;
         Intrinsics.checkNotNull(var10001);
         double yOffset = var10000 - var10001.value + (double)2.0F;

         for(Module module : this.modules) {
            if ((double)mouseY > yOffset && (double)mouseY < yOffset + (double)this.singleHeight) {
               this.apply(module);
               break;
            }

            yOffset += (double)this.singleHeight;
         }

      }
   }

   private final void apply(Module module) {
      module.setKeyBind(this.info.getKey());
      CrossSine.INSTANCE.getKeyBindManager().updateAllKeys();
      this.close();
   }

   public void close() {
      this.setAnimatingOut(false);
      if (this.getAnimationProgress() >= 1.0F) {
         CrossSine.INSTANCE.getKeyBindManager().setPopUI((PopUI)null);
      }

   }

   private final void update() {
      KeySelectUI var10000 = this;
      List var10001;
      if (((CharSequence)this.str).length() > 0) {
         Iterable $this$filter$iv = (Iterable)CrossSine.INSTANCE.getModuleManager().getModules();
         int $i$f$filter = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterTo = 0;

         for(Object element$iv$iv : $this$filter$iv) {
            Module it = (Module)element$iv$iv;
            int var9 = 0;
            if (StringsKt.startsWith(it.getName(), this.str, true)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var10001 = (List)destination$iv$iv;
         var10000 = this;
      } else {
         var10001 = CollectionsKt.toList((Iterable)CrossSine.INSTANCE.getModuleManager().getModules());
      }

      var10000.modules = var10001;
      this.maxStroll = (float)this.modules.size() * this.singleHeight;
      this.scroll = 0;
   }
}
