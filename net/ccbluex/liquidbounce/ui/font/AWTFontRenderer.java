package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\u0018\u0000 /2\u00020\u0001:\u0002./B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J.\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020(J&\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020+2\u0006\u0010 \u001a\u00020+2\u0006\u0010'\u001a\u00020\u0005J\u000e\u0010,\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\nJ\u0018\u0010-\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "", "font", "Ljava/awt/Font;", "startChar", "", "stopChar", "(Ljava/awt/Font;II)V", "cachedStrings", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "Lkotlin/collections/HashMap;", "charLocations", "", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "[Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "getFont", "()Ljava/awt/Font;", "fontHeight", "height", "getHeight", "()I", "textureHeight", "textureID", "textureWidth", "collectGarbage", "", "drawChar", "char", "x", "", "y", "drawCharToImage", "Ljava/awt/image/BufferedImage;", "ch", "", "drawOutlineStringWithoutGL", "s", "color", "Lnet/minecraft/client/gui/FontRenderer;", "drawString", "text", "", "getStringWidth", "renderBitmap", "CharLocation", "Companion", "CrossSine"}
)
public final class AWTFontRenderer {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Font font;
   private int fontHeight;
   @NotNull
   private final CharLocation[] charLocations;
   @NotNull
   private final HashMap cachedStrings;
   private int textureID;
   private int textureWidth;
   private int textureHeight;
   private static boolean assumeNonVolatile;
   @NotNull
   private static final ArrayList activeFontRenderers = new ArrayList();
   private static int gcTicks;
   private static final int GC_TICKS = 600;
   private static final int CACHED_FONT_REMOVAL_TIME = 30000;

   public AWTFontRenderer(@NotNull Font font, int startChar, int stopChar) {
      Intrinsics.checkNotNullParameter(font, "font");
      super();
      this.font = font;
      this.fontHeight = -1;
      this.charLocations = new CharLocation[stopChar];
      this.cachedStrings = new HashMap();
      this.renderBitmap(startChar, stopChar);
      activeFontRenderers.add(this);
   }

   // $FF: synthetic method
   public AWTFontRenderer(Font var1, int var2, int var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = 255;
      }

      this(var1, var2, var3);
   }

   @NotNull
   public final Font getFont() {
      return this.font;
   }

   private final void collectGarbage() {
      long currentTime = System.currentTimeMillis();
      Map $this$filter$iv = (Map)this.cachedStrings;
      int $i$f$filter = 0;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$filterTo = 0;

      for(Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
         int var11 = 0;
         if (currentTime - ((CachedFont)element$iv$iv.getValue()).getLastUsage() > 30000L) {
            destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
         }
      }

      $i$f$filter = 0;

      for(Map.Entry element$iv : destination$iv$iv.entrySet()) {
         int var14 = 0;
         GL11.glDeleteLists(((CachedFont)element$iv.getValue()).getDisplayList(), 1);
         ((CachedFont)element$iv.getValue()).setDeleted(true);
         this.cachedStrings.remove(element$iv.getKey());
      }

   }

   public final int getHeight() {
      return (this.fontHeight - 8) / 2;
   }

   public final void drawString(@NotNull String text, double x, double y, int color) {
      Intrinsics.checkNotNullParameter(text, "text");
      double scale = (double)0.25F;
      double reverse = (double)1 / scale;
      GlStateManager.func_179094_E();
      GlStateManager.func_179139_a(scale, scale, scale);
      GL11.glTranslated(x * (double)2.0F, y * (double)2.0F - (double)2.0F, (double)0.0F);
      GlStateManager.func_179144_i(this.textureID);
      float red = (float)(color >> 16 & 255) / 255.0F;
      float green = (float)(color >> 8 & 255) / 255.0F;
      float blue = (float)(color & 255) / 255.0F;
      float alpha = (float)(color >> 24 & 255) / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
      double currX = (double)0.0F;
      CachedFont cached = (CachedFont)this.cachedStrings.get(text);
      if (cached != null) {
         GL11.glCallList(cached.getDisplayList());
         cached.setLastUsage(System.currentTimeMillis());
         GlStateManager.func_179121_F();
      } else {
         int list = -1;
         if (assumeNonVolatile) {
            list = GL11.glGenLists(1);
            GL11.glNewList(list, 4865);
         }

         GL11.glBegin(7);
         char[] var21 = text.toCharArray();
         Intrinsics.checkNotNullExpressionValue(var21, "this as java.lang.String).toCharArray()");
         char[] var19 = var21;
         int var20 = 0;
         int var26 = var21.length;

         while(var20 < var26) {
            char var22 = var19[var20];
            ++var20;
            if (var22 >= this.charLocations.length) {
               GL11.glEnd();
               GlStateManager.func_179139_a(reverse, reverse, reverse);
               Minecraft.func_71410_x().field_71466_p.func_175065_a(String.valueOf(var22), (float)currX * (float)scale + (float)1, 2.0F, color, false);
               currX += (double)Minecraft.func_71410_x().field_71466_p.func_78256_a(String.valueOf(var22)) * reverse;
               GlStateManager.func_179139_a(scale, scale, scale);
               GlStateManager.func_179144_i(this.textureID);
               GlStateManager.func_179131_c(red, green, blue, alpha);
               GL11.glBegin(7);
            } else {
               CharLocation var10000 = this.charLocations[var22];
               if (var10000 != null) {
                  CharLocation fontChar = var10000;
                  this.drawChar(fontChar, (float)currX, 0.0F);
                  currX += (double)fontChar.getWidth() - (double)8.0F;
               }
            }
         }

         GL11.glEnd();
         if (assumeNonVolatile) {
            Map var24 = (Map)this.cachedStrings;
            CachedFont var25 = new CachedFont(list, System.currentTimeMillis(), false, 4, (DefaultConstructorMarker)null);
            var24.put(text, var25);
            GL11.glEndList();
         }

         GlStateManager.func_179121_F();
      }
   }

   private final void drawChar(CharLocation var1, float x, float y) {
      float width = (float)var1.getWidth();
      float height = (float)var1.getHeight();
      float srcX = (float)var1.getX();
      float srcY = (float)var1.getY();
      float renderX = srcX / (float)this.textureWidth;
      float renderY = srcY / (float)this.textureHeight;
      float renderWidth = width / (float)this.textureWidth;
      float renderHeight = height / (float)this.textureHeight;
      GL11.glTexCoord2f(renderX, renderY);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(renderX, renderY + renderHeight);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
      GL11.glVertex2f(x + width, y + height);
      GL11.glTexCoord2f(renderX + renderWidth, renderY);
      GL11.glVertex2f(x + width, y);
   }

   private final void renderBitmap(int startChar, int stopChar) {
      BufferedImage[] fontImages = new BufferedImage[stopChar];
      int rowHeight = 0;
      int charX = 0;
      int charY = 0;
      int var7 = startChar;

      while(var7 < stopChar) {
         int targetChar = var7++;
         BufferedImage fontImage = this.drawCharToImage((char)targetChar);
         CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());
         if (fontChar.getHeight() > this.fontHeight) {
            this.fontHeight = fontChar.getHeight();
         }

         if (fontChar.getHeight() > rowHeight) {
            rowHeight = fontChar.getHeight();
         }

         this.charLocations[targetChar] = fontChar;
         fontImages[targetChar] = fontImage;
         charX += fontChar.getWidth();
         if (charX > 2048) {
            if (charX > this.textureWidth) {
               this.textureWidth = charX;
            }

            charX = 0;
            charY += rowHeight;
            rowHeight = 0;
         }
      }

      this.textureHeight = charY + rowHeight;
      BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
      Graphics var10000 = bufferedImage.getGraphics();
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
      } else {
         Graphics2D graphics2D = (Graphics2D)var10000;
         graphics2D.setFont(this.font);
         graphics2D.setColor(new Color(255, 255, 255, 0));
         graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
         graphics2D.setColor(Color.white);
         int var13 = startChar;

         while(var13 < stopChar) {
            int targetChar = var13++;
            if (fontImages[targetChar] != null && this.charLocations[targetChar] != null) {
               Image var10001 = (Image)fontImages[targetChar];
               CharLocation var10002 = this.charLocations[targetChar];
               Intrinsics.checkNotNull(var10002);
               int var15 = var10002.getX();
               CharLocation var10003 = this.charLocations[targetChar];
               Intrinsics.checkNotNull(var10003);
               graphics2D.drawImage(var10001, var15, var10003.getY(), (ImageObserver)null);
            }
         }

         this.textureID = TextureUtil.func_110989_a(TextureUtil.func_110996_a(), bufferedImage, true, true);
      }
   }

   private final BufferedImage drawCharToImage(char ch) {
      Graphics var10000 = (new BufferedImage(1, 1, 2)).getGraphics();
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
      } else {
         Graphics2D graphics2D = (Graphics2D)var10000;
         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         graphics2D.setFont(this.font);
         FontMetrics fontMetrics = graphics2D.getFontMetrics();
         int charWidth = fontMetrics.charWidth(ch) + 8;
         if (charWidth <= 0) {
            charWidth = 7;
         }

         int charHeight = fontMetrics.getHeight() + 3;
         if (charHeight <= 0) {
            charHeight = this.font.getSize();
         }

         BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);
         var10000 = fontImage.getGraphics();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.awt.Graphics2D");
         } else {
            Graphics2D graphics = (Graphics2D)var10000;
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setFont(this.font);
            graphics.setColor(Color.WHITE);
            graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
            return fontImage;
         }
      }
   }

   public final int getStringWidth(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      int width = 0;
      char[] var5 = text.toCharArray();
      Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toCharArray()");
      char[] var3 = var5;
      int var4 = 0;
      int var9 = var5.length;

      while(var4 < var9) {
         char c = var3[var4];
         ++var4;
         CharLocation fontChar = this.charLocations[c < this.charLocations.length ? c : 3];
         if (fontChar != null) {
            width += fontChar.getWidth() - 8;
         }
      }

      return width / 2;
   }

   public final void drawOutlineStringWithoutGL(@NotNull String s, float x, float y, int color, @NotNull FontRenderer font) {
      Intrinsics.checkNotNullParameter(s, "s");
      Intrinsics.checkNotNullParameter(font, "font");
      font.func_78276_b(ColorUtils.stripColor(s), (int)(x * (float)2 - (float)1), (int)(y * (float)2), Color.BLACK.getRGB());
      font.func_78276_b(ColorUtils.stripColor(s), (int)(x * (float)2 + (float)1), (int)(y * (float)2), Color.BLACK.getRGB());
      font.func_78276_b(ColorUtils.stripColor(s), (int)(x * (float)2), (int)(y * (float)2 - (float)1), Color.BLACK.getRGB());
      font.func_78276_b(ColorUtils.stripColor(s), (int)(x * (float)2), (int)(y * (float)2 + (float)1), Color.BLACK.getRGB());
      font.func_78276_b(s, (int)(x * (float)2), (int)(y * (float)2), color);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion;", "", "()V", "CACHED_FONT_REMOVAL_TIME", "", "GC_TICKS", "activeFontRenderers", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lkotlin/collections/ArrayList;", "getActiveFontRenderers", "()Ljava/util/ArrayList;", "assumeNonVolatile", "", "getAssumeNonVolatile", "()Z", "setAssumeNonVolatile", "(Z)V", "gcTicks", "garbageCollectionTick", "", "CrossSine"}
   )
   public static final class Companion {
      private Companion() {
      }

      public final boolean getAssumeNonVolatile() {
         return AWTFontRenderer.assumeNonVolatile;
      }

      public final void setAssumeNonVolatile(boolean var1) {
         AWTFontRenderer.assumeNonVolatile = var1;
      }

      @NotNull
      public final ArrayList getActiveFontRenderers() {
         return AWTFontRenderer.activeFontRenderers;
      }

      public final void garbageCollectionTick() {
         int var1 = AWTFontRenderer.gcTicks;
         AWTFontRenderer.gcTicks = var1 + 1;
         if (var1 > 600) {
            Iterable $this$forEach$iv = (Iterable)this.getActiveFontRenderers();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               AWTFontRenderer it = (AWTFontRenderer)element$iv;
               int var6 = 0;
               it.collectGarbage();
            }

            AWTFontRenderer.gcTicks = 0;
         }

      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001d"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "", "x", "", "y", "width", "height", "(IIII)V", "getHeight", "()I", "setHeight", "(I)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "CrossSine"}
   )
   private static final class CharLocation {
      private int x;
      private int y;
      private int width;
      private int height;

      public CharLocation(int x, int y, int width, int height) {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
      }

      public final int getX() {
         return this.x;
      }

      public final void setX(int var1) {
         this.x = var1;
      }

      public final int getY() {
         return this.y;
      }

      public final void setY(int var1) {
         this.y = var1;
      }

      public final int getWidth() {
         return this.width;
      }

      public final void setWidth(int var1) {
         this.width = var1;
      }

      public final int getHeight() {
         return this.height;
      }

      public final void setHeight(int var1) {
         this.height = var1;
      }

      public final int component1() {
         return this.x;
      }

      public final int component2() {
         return this.y;
      }

      public final int component3() {
         return this.width;
      }

      public final int component4() {
         return this.height;
      }

      @NotNull
      public final CharLocation copy(int x, int y, int width, int height) {
         return new CharLocation(x, y, width, height);
      }

      // $FF: synthetic method
      public static CharLocation copy$default(CharLocation var0, int var1, int var2, int var3, int var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var1 = var0.x;
         }

         if ((var5 & 2) != 0) {
            var2 = var0.y;
         }

         if ((var5 & 4) != 0) {
            var3 = var0.width;
         }

         if ((var5 & 8) != 0) {
            var4 = var0.height;
         }

         return var0.copy(var1, var2, var3, var4);
      }

      @NotNull
      public String toString() {
         return "CharLocation(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ')';
      }

      public int hashCode() {
         int result = Integer.hashCode(this.x);
         result = result * 31 + Integer.hashCode(this.y);
         result = result * 31 + Integer.hashCode(this.width);
         result = result * 31 + Integer.hashCode(this.height);
         return result;
      }

      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof CharLocation)) {
            return false;
         } else {
            CharLocation var2 = (CharLocation)other;
            if (this.x != var2.x) {
               return false;
            } else if (this.y != var2.y) {
               return false;
            } else if (this.width != var2.width) {
               return false;
            } else {
               return this.height == var2.height;
            }
         }
      }
   }
}
