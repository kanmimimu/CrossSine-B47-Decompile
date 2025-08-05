package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 22\u00020\u0001:\u00012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J&\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ.\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 J&\u0010!\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ0\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 H\u0016J(\u0010#\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J2\u0010$\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020 H\u0002J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010-\u001a\u00020)H\u0002J\u0010\u0010.\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u000201H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010¨\u00063"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "Lnet/minecraft/client/gui/FontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;)V", "height", "", "getHeight", "()I", "italicFont", "size", "getSize", "bindTexture", "", "location", "Lnet/minecraft/util/ResourceLocation;", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawText", "colorHex", "ignoreColor", "getCharWidth", "character", "", "getColorCode", "charCode", "getColorIndex2", "type", "getStringWidth", "onResourceManagerReload", "resourceManager", "Lnet/minecraft/client/resources/IResourceManager;", "Companion", "CrossSine"}
)
public final class GameFontRenderer extends FontRenderer {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private AWTFontRenderer defaultFont;
   @NotNull
   private AWTFontRenderer boldFont;
   @NotNull
   private AWTFontRenderer italicFont;
   @NotNull
   private AWTFontRenderer boldItalicFont;

   public GameFontRenderer(@NotNull Font font) {
      Intrinsics.checkNotNullParameter(font, "font");
      super(Minecraft.func_71410_x().field_71474_y, new ResourceLocation("textures/font/ascii.png"), Minecraft.func_71410_x().func_110434_K(), false);
      this.defaultFont = new AWTFontRenderer(font, 0, 0, 6, (DefaultConstructorMarker)null);
      Font var2 = font.deriveFont(1);
      Intrinsics.checkNotNullExpressionValue(var2, "font.deriveFont(Font.BOLD)");
      this.boldFont = new AWTFontRenderer(var2, 0, 0, 6, (DefaultConstructorMarker)null);
      var2 = font.deriveFont(2);
      Intrinsics.checkNotNullExpressionValue(var2, "font.deriveFont(Font.ITALIC)");
      this.italicFont = new AWTFontRenderer(var2, 0, 0, 6, (DefaultConstructorMarker)null);
      var2 = font.deriveFont(3);
      Intrinsics.checkNotNullExpressionValue(var2, "font.deriveFont(Font.BOLD or Font.ITALIC)");
      this.boldItalicFont = new AWTFontRenderer(var2, 0, 0, 6, (DefaultConstructorMarker)null);
      this.field_78288_b = this.getHeight();
   }

   @NotNull
   public final AWTFontRenderer getDefaultFont() {
      return this.defaultFont;
   }

   public final void setDefaultFont(@NotNull AWTFontRenderer var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.defaultFont = var1;
   }

   public final int getHeight() {
      return this.defaultFont.getHeight() / 2;
   }

   public final int getSize() {
      return this.defaultFont.getFont().getSize();
   }

   private final int getColorIndex2(char type) {
      return ('0' <= type ? type < ':' : false) ? type - 48 : (('a' <= type ? type < 'g' : false) ? type - 97 + 10 : (('k' <= type ? type < 'p' : false) ? type - 107 + 16 : (type == 'r' ? 21 : -1)));
   }

   public final int drawString(@NotNull String s, float x, float y, int color) {
      Intrinsics.checkNotNullParameter(s, "s");
      return this.func_175065_a(s, x, y, color, false);
   }

   public int func_175063_a(@NotNull String text, float x, float y, int color) {
      Intrinsics.checkNotNullParameter(text, "text");
      return this.func_175065_a(text, x, y, color, true);
   }

   public final int drawCenteredString(@NotNull String s, float x, float y, int color, boolean shadow) {
      Intrinsics.checkNotNullParameter(s, "s");
      return this.func_175065_a(s, x - (float)this.func_78256_a(s) / 2.0F, y, color, shadow);
   }

   public final int drawCenteredString(@NotNull String s, float x, float y, int color) {
      Intrinsics.checkNotNullParameter(s, "s");
      return this.func_175063_a(s, x - (float)this.func_78256_a(s) / 2.0F, y, color);
   }

   public int func_175065_a(@NotNull String text, float x, float y, int color, boolean shadow) {
      Intrinsics.checkNotNullParameter(text, "text");
      TextEvent event = new TextEvent(text);
      CrossSine.INSTANCE.getEventManager().callEvent(event);
      String var10000 = event.getText();
      if (var10000 == null) {
         return 0;
      } else {
         String currentText = var10000;
         float currY = y - 3.0F;
         if (shadow) {
            this.drawText(currentText, x + 1.0F, currY + 1.0F, (new Color(0, 0, 0, 150)).getRGB(), true);
         }

         return this.drawText(currentText, x, currY, color, false);
      }
   }

   private final int drawText(String text, float x, float y, int colorHex, boolean ignoreColor) {
      if (text == null) {
         return 0;
      } else if (((CharSequence)text).length() == 0) {
         return (int)x;
      } else {
         GlStateManager.func_179137_b((double)x - (double)1.5F, (double)y + (double)0.5F, (double)0.0F);
         GlStateManager.func_179141_d();
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         GlStateManager.func_179098_w();
         int hexColor = 0;
         hexColor = colorHex;
         if ((colorHex & -67108864) == 0) {
            hexColor = colorHex | -16777216;
         }

         int alpha = hexColor >> 24 & 255;
         if (StringsKt.contains$default((CharSequence)text, (CharSequence)"§", false, 2, (Object)null)) {
            CharSequence var10000 = (CharSequence)text;
            String[] var9 = new String[]{"§"};
            List parts = StringsKt.split$default(var10000, var9, false, 0, 6, (Object)null);
            Object currentFont = null;
            currentFont = this.defaultFont;
            double width = (double)0.0F;
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;
            Iterable $this$forEachIndexed$iv = (Iterable)parts;
            int $i$f$forEachIndexed = 0;
            int index$iv = 0;

            for(Object item$iv : $this$forEachIndexed$iv) {
               int index = index$iv++;
               if (index < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               String part = (String)item$iv;
               int var23 = 0;
               if (((CharSequence)part).length() != 0) {
                  if (index == 0) {
                     ((AWTFontRenderer)currentFont).drawString(part, width, (double)0.0F, hexColor);
                     width += (double)((AWTFontRenderer)currentFont).getStringWidth(part);
                  } else {
                     String var24 = part.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var24, "this as java.lang.String).substring(startIndex)");
                     char type = part.charAt(0);
                     int colorIndex = Companion.getColorIndex(type);
                     if (0 <= colorIndex ? colorIndex < 16 : false) {
                        if (!ignoreColor) {
                           hexColor = ColorUtils.hexColors[colorIndex] | alpha << 24;
                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                     } else if (colorIndex == 16) {
                        randomCase = true;
                     } else if (colorIndex == 17) {
                        bold = true;
                     } else if (colorIndex == 18) {
                        strikeThrough = true;
                     } else if (colorIndex == 19) {
                        underline = true;
                     } else if (colorIndex == 20) {
                        italic = true;
                     } else if (colorIndex == 21) {
                        hexColor = colorHex;
                        if ((colorHex & -67108864) == 0) {
                           hexColor = colorHex | -16777216;
                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                     }

                     currentFont = bold && italic ? this.boldItalicFont : (bold ? this.boldFont : (italic ? this.italicFont : this.getDefaultFont()));
                     ((AWTFontRenderer)currentFont).drawString(randomCase ? ColorUtils.INSTANCE.randomMagicText(var24) : var24, width, (double)0.0F, hexColor);
                     if (strikeThrough) {
                        RenderUtils.drawLine(width / (double)2.0F + (double)1, (double)((AWTFontRenderer)currentFont).getHeight() / (double)3.0F, (width + (double)((AWTFontRenderer)currentFont).getStringWidth(var24)) / (double)2.0F + (double)1, (double)((AWTFontRenderer)currentFont).getHeight() / (double)3.0F, (float)this.field_78288_b / 16.0F);
                     }

                     if (underline) {
                        RenderUtils.drawLine(width / (double)2.0F + (double)1, (double)((AWTFontRenderer)currentFont).getHeight() / (double)2.0F, (width + (double)((AWTFontRenderer)currentFont).getStringWidth(var24)) / (double)2.0F + (double)1, (double)((AWTFontRenderer)currentFont).getHeight() / (double)2.0F, (float)this.field_78288_b / 16.0F);
                     }

                     width += (double)((AWTFontRenderer)currentFont).getStringWidth(var24);
                  }
               }
            }
         } else {
            this.defaultFont.drawString(text, (double)0.0F, (double)0.0F, hexColor);
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179137_b(-((double)x - (double)1.5F), -((double)y + (double)0.5F), (double)0.0F);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         return (int)(x + (float)this.func_78256_a(text));
      }
   }

   public int func_175064_b(char charCode) {
      return ColorUtils.hexColors[this.getColorIndex2(charCode)];
   }

   public int func_78256_a(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      TextEvent event = new TextEvent(text);
      CrossSine.INSTANCE.getEventManager().callEvent(event);
      String var10000 = event.getText();
      if (var10000 == null) {
         return 0;
      } else {
         String currentText = var10000;
         int var25;
         if (StringsKt.contains$default((CharSequence)currentText, (CharSequence)"§", false, 2, (Object)null)) {
            CharSequence var24 = (CharSequence)currentText;
            String[] var5 = new String[]{"§"};
            List parts = StringsKt.split$default(var24, var5, false, 0, 6, (Object)null);
            Object currentFont = null;
            currentFont = this.defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;
            Iterable $this$forEachIndexed$iv = (Iterable)parts;
            int $i$f$forEachIndexed = 0;
            int index$iv = 0;

            for(Object item$iv : $this$forEachIndexed$iv) {
               int index = index$iv++;
               if (index < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               String part = (String)item$iv;
               int var17 = 0;
               if (((CharSequence)part).length() != 0) {
                  if (index == 0) {
                     width += ((AWTFontRenderer)currentFont).getStringWidth(part);
                  } else {
                     String words = part.substring(1);
                     Intrinsics.checkNotNullExpressionValue(words, "this as java.lang.String).substring(startIndex)");
                     char type = part.charAt(0);
                     int colorIndex = this.getColorIndex2(type);
                     if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                     } else if (colorIndex == 17) {
                        bold = true;
                     } else if (colorIndex == 20) {
                        italic = true;
                     } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                     }

                     currentFont = bold && italic ? this.boldItalicFont : (bold ? this.boldFont : (italic ? this.italicFont : this.getDefaultFont()));
                     width += ((AWTFontRenderer)currentFont).getStringWidth(words);
                  }
               }
            }

            var25 = width / 2;
         } else {
            var25 = this.defaultFont.getStringWidth(currentText) / 2;
         }

         return var25;
      }
   }

   public int func_78263_a(char character) {
      return this.func_78256_a(String.valueOf(character));
   }

   public void func_110549_a(@NotNull IResourceManager resourceManager) {
      Intrinsics.checkNotNullParameter(resourceManager, "resourceManager");
   }

   protected void bindTexture(@Nullable ResourceLocation location) {
   }

   @JvmStatic
   public static final int getColorIndex(char type) {
      return Companion.getColorIndex(type);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      @JvmStatic
      public final int getColorIndex(char type) {
         return ('0' <= type ? type < ':' : false) ? type - 48 : (('a' <= type ? type < 'g' : false) ? type - 97 + 10 : (('k' <= type ? type < 'p' : false) ? type - 107 + 16 : (type == 'r' ? 21 : -1)));
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
