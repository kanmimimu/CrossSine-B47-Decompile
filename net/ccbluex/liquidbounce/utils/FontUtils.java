package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.font.CFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015JJ\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015J\u001d\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0018¢\u0006\u0002\u0010\u0019J \u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u0013H\u0002R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/FontUtils;", "", "()V", "cache", "", "Lkotlin/Pair;", "", "Lnet/minecraft/client/gui/FontRenderer;", "drawGradientCenterString", "", "fontRenderer", "Lnet/ccbluex/liquidbounce/font/CFontRenderer;", "text", "x", "", "y", "colorStart", "colorEnd", "gradientRange", "", "shadow", "", "drawGradientString", "getAllFontDetails", "", "()[Lkotlin/Pair;", "lerpColor", "ratio", "CrossSine"}
)
public final class FontUtils {
   @NotNull
   public static final FontUtils INSTANCE = new FontUtils();
   @NotNull
   private static final List cache = (List)(new ArrayList());

   private FontUtils() {
   }

   public final void drawGradientString(@NotNull CFontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
      Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
      Intrinsics.checkNotNullParameter(text, "text");
      int posX = x;
      int var10 = 0;

      int i;
      for(int var11 = text.length(); var10 < var11; posX += fontRenderer.getStringWidth(String.valueOf(text.charAt(i)))) {
         i = var10++;
         float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0F, 1.0F);
         int color = this.lerpColor(colorStart, colorEnd, ratio);
         Color shadowColor = new Color(-16777216, true);
         if (shadow) {
            fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)posX + 1.0F, (float)y + 1.0F, shadowColor.getRGB());
         }

         fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)posX, (float)y, color);
      }

      GlStateManager.func_179117_G();
   }

   // $FF: synthetic method
   public static void drawGradientString$default(FontUtils var0, CFontRenderer var1, String var2, int var3, int var4, int var5, int var6, float var7, boolean var8, int var9, Object var10) {
      if ((var9 & 64) != 0) {
         var7 = 1.0F;
      }

      if ((var9 & 128) != 0) {
         var8 = false;
      }

      var0.drawGradientString(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final void drawGradientString(@NotNull FontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
      Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
      Intrinsics.checkNotNullParameter(text, "text");
      int posX = x;
      int var10 = 0;

      int i;
      for(int var11 = text.length(); var10 < var11; posX += fontRenderer.func_78256_a(String.valueOf(text.charAt(i)))) {
         i = var10++;
         float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0F, 1.0F);
         int color = this.lerpColor(colorStart, colorEnd, ratio);
         Color shadowColor = new Color(-16777216, true);
         if (shadow) {
            fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX + 1, y + 1, shadowColor.getRGB());
         }

         fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX, y, color);
      }

      GlStateManager.func_179117_G();
   }

   // $FF: synthetic method
   public static void drawGradientString$default(FontUtils var0, FontRenderer var1, String var2, int var3, int var4, int var5, int var6, float var7, boolean var8, int var9, Object var10) {
      if ((var9 & 64) != 0) {
         var7 = 1.0F;
      }

      if ((var9 & 128) != 0) {
         var8 = false;
      }

      var0.drawGradientString(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final void drawGradientCenterString(@NotNull CFontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
      Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
      Intrinsics.checkNotNullParameter(text, "text");
      int posX = x;
      int var10 = 0;

      int i;
      for(int var11 = text.length(); var10 < var11; posX += fontRenderer.getStringWidth(String.valueOf(text.charAt(i)))) {
         i = var10++;
         float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0F, 1.0F);
         int color = this.lerpColor(colorStart, colorEnd, ratio);
         Color shadowColor = new Color(-16777216, true);
         if (shadow) {
            fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)(posX - fontRenderer.getStringWidth(text) / 2) + 1.0F, (float)y + 1.0F, shadowColor.getRGB());
         }

         fontRenderer.drawString(String.valueOf(text.charAt(i)), (float)(posX - fontRenderer.getStringWidth(text) / 2) + 1.0F, (float)y + 1.0F, color);
      }

      GlStateManager.func_179117_G();
   }

   // $FF: synthetic method
   public static void drawGradientCenterString$default(FontUtils var0, CFontRenderer var1, String var2, int var3, int var4, int var5, int var6, float var7, boolean var8, int var9, Object var10) {
      if ((var9 & 64) != 0) {
         var7 = 1.0F;
      }

      if ((var9 & 128) != 0) {
         var8 = false;
      }

      var0.drawGradientCenterString(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final void drawGradientCenterString(@NotNull FontRenderer fontRenderer, @NotNull String text, int x, int y, int colorStart, int colorEnd, float gradientRange, boolean shadow) {
      Intrinsics.checkNotNullParameter(fontRenderer, "fontRenderer");
      Intrinsics.checkNotNullParameter(text, "text");
      int posX = x;
      int var10 = 0;

      int i;
      for(int var11 = text.length(); var10 < var11; posX += fontRenderer.func_78256_a(String.valueOf(text.charAt(i)))) {
         i = var10++;
         float ratio = RangesKt.coerceIn((float)i / (float)(text.length() - 1) * gradientRange, 0.0F, 1.0F);
         int color = this.lerpColor(colorStart, colorEnd, ratio);
         Color shadowColor = new Color(-16777216, true);
         if (shadow) {
            fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX - fontRenderer.func_78256_a(text) / 2 + 1, y + 1, shadowColor.getRGB());
         }

         fontRenderer.func_78276_b(String.valueOf(text.charAt(i)), posX - fontRenderer.func_78256_a(text) / 2, y, color);
      }

      GlStateManager.func_179117_G();
   }

   // $FF: synthetic method
   public static void drawGradientCenterString$default(FontUtils var0, FontRenderer var1, String var2, int var3, int var4, int var5, int var6, float var7, boolean var8, int var9, Object var10) {
      if ((var9 & 64) != 0) {
         var7 = 1.0F;
      }

      if ((var9 & 128) != 0) {
         var8 = false;
      }

      var0.drawGradientCenterString(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   private final int lerpColor(int colorStart, int colorEnd, float ratio) {
      int startAlpha = colorStart >> 24 & 255;
      int startRed = colorStart >> 16 & 255;
      int startGreen = colorStart >> 8 & 255;
      int startBlue = colorStart & 255;
      int endAlpha = colorEnd >> 24 & 255;
      int endRed = colorEnd >> 16 & 255;
      int endGreen = colorEnd >> 8 & 255;
      int endBlue = colorEnd & 255;
      int alpha = (int)((float)startAlpha + ratio * (float)(endAlpha - startAlpha));
      int red = (int)((float)startRed + ratio * (float)(endRed - startRed));
      int green = (int)((float)startGreen + ratio * (float)(endGreen - startGreen));
      int blue = (int)((float)startBlue + ratio * (float)(endBlue - startBlue));
      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   @NotNull
   public final Pair[] getAllFontDetails() {
      if (cache.size() == 0) {
         cache.clear();

         for(FontRenderer fontOfFonts : Fonts.getFonts()) {
            Object[] var10000 = Fonts.getFontDetails(fontOfFonts);
            if (var10000 != null) {
               Object[] details = var10000;
               String name = details[0].toString();
               int size = Integer.parseInt(details[1].toString());
               String format = name + ' ' + size;
               cache.add(TuplesKt.to(format, fontOfFonts));
            }
         }

         List $this$sortBy$iv = cache;
         int $i$f$sortBy = 0;
         if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new FontUtils$getAllFontDetails$$inlined$sortBy$1());
         }
      }

      Collection $this$toTypedArray$iv = (Collection)cache;
      int $i$f$toTypedArray = 0;
      Object[] var11 = $this$toTypedArray$iv.toArray(new Pair[0]);
      if (var11 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         return (Pair[])var11;
      }
   }
}
