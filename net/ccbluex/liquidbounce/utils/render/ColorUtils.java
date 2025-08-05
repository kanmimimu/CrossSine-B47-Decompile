package net.ccbluex.liquidbounce.utils.render;

import com.ibm.icu.text.NumberFormat;
import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0014\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0002\b\u000e\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J \u0010\u0010\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\rJ \u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\rH\u0007J\u001e\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u000fJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010 \u001a\u00020\rH\u0007J\u0016\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u000fJ\u0010\u0010%\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000bH\u0007J \u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000f2\b\b\u0002\u0010\u0018\u001a\u00020\rJ\u0018\u0010)\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J%\u0010*\u001a\u0004\u0018\u00010\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0014¢\u0006\u0002\u0010.J\"\u0010/\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000fH\u0007J \u00101\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000fJ\u001e\u00102\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u0014J \u00103\u001a\u00020\r2\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020\u0014H\u0007J&\u00104\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\rJ \u00107\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u000fH\u0007J\u0018\u00107\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u00107\u001a\u00020\u000b2\u0006\u00106\u001a\u00020\tH\u0007J\u000e\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u0017J\u0018\u0010:\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000fH\u0007J\u0018\u0010:\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\rH\u0007J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\rH\u0007J(\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020\u000f2\u0006\u0010A\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u0014H\u0007J(\u0010B\u001a\u00020\u000b2\u0006\u0010C\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u000fH\u0007J\u0010\u0010F\u001a\u00020\u00172\u0006\u0010G\u001a\u00020\u0017H\u0007J\u0010\u0010H\u001a\u00020\u00172\u0006\u0010I\u001a\u00020\u0017H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006J"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "hexColors", "", "startTime", "", "astolfo", "Ljava/awt/Color;", "index", "", "speed", "", "blend", "color1", "color2", "ratio", "", "colorCode", "code", "", "alpha", "fade", "color", "count", "getColor", "hueoffset", "saturation", "brightness", "n", "getFraction", "fractions", "", "progress", "getOppositeColor", "healthColor", "hp", "maxHP", "hslRainbow", "interpolate", "oldValue", "newValue", "interpolationValue", "(DDD)Ljava/lang/Double;", "interpolateColorC", "amount", "interpolateColorHue", "interpolateFloat", "interpolateInt", "mixColors", "ms", "offset", "rainbow", "randomMagicText", "text", "reAlpha", "setColour", "", "colour", "skyRainbow", "var2", "bright", "st", "slowlyRainbow", "time", "qd", "sq", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "CrossSine"}
)
public final class ColorUtils {
   @NotNull
   public static final ColorUtils INSTANCE = new ColorUtils();
   private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
   private static final long startTime = System.currentTimeMillis();
   @JvmField
   @NotNull
   public static final int[] hexColors = new int[16];

   private ColorUtils() {
   }

   @JvmStatic
   @NotNull
   public static final String stripColor(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      String var1 = COLOR_PATTERN.matcher((CharSequence)input).replaceAll("");
      Intrinsics.checkNotNullExpressionValue(var1, "COLOR_PATTERN.matcher(input).replaceAll(\"\")");
      return var1;
   }

   @JvmStatic
   @NotNull
   public static final String translateAlternateColorCodes(@NotNull String textToTranslate) {
      Intrinsics.checkNotNullParameter(textToTranslate, "textToTranslate");
      char[] var3 = textToTranslate.toCharArray();
      Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toCharArray()");
      char[] chars = var3;
      int var2 = 0;
      int var5 = var3.length - 1;

      while(var2 < var5) {
         int i = var2++;
         if (chars[i] == '&' && StringsKt.contains((CharSequence)"0123456789AaBbCcDdEeFfKkLlMmNnOoRr", chars[i + 1], true)) {
            chars[i] = 167;
            chars[i + 1] = Character.toLowerCase(chars[i + 1]);
         }
      }

      return new String(chars);
   }

   @NotNull
   public final String randomMagicText(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      StringBuilder stringBuilder = new StringBuilder();
      String allowedCharacters = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";
      char[] var6 = text.toCharArray();
      Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toCharArray()");
      char[] var4 = var6;
      int var5 = 0;
      int var11 = var6.length;

      while(var5 < var11) {
         char c = var4[var5];
         ++var5;
         if (ChatAllowedCharacters.func_71566_a(c)) {
            int index = (new Random()).nextInt(allowedCharacters.length());
            char[] var9 = allowedCharacters.toCharArray();
            Intrinsics.checkNotNullExpressionValue(var9, "this as java.lang.String).toCharArray()");
            stringBuilder.append(var9[index]);
         }
      }

      String var10 = stringBuilder.toString();
      Intrinsics.checkNotNullExpressionValue(var10, "stringBuilder.toString()");
      return var10;
   }

   @JvmStatic
   @NotNull
   public static final Color getOppositeColor(@NotNull Color color) {
      Intrinsics.checkNotNullParameter(color, "color");
      return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
   }

   @NotNull
   public final Color colorCode(@NotNull String code, int alpha) {
      Intrinsics.checkNotNullParameter(code, "code");
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var4) {
         case "0":
            return new Color(0, 0, 0, alpha);
         case "1":
            return new Color(0, 0, 170, alpha);
         case "2":
            return new Color(0, 170, 0, alpha);
         case "3":
            return new Color(0, 170, 170, alpha);
         case "4":
            return new Color(170, 0, 0, alpha);
         case "5":
            return new Color(170, 0, 170, alpha);
         case "6":
            return new Color(255, 170, 0, alpha);
         case "7":
            return new Color(170, 170, 170, alpha);
         case "8":
            return new Color(85, 85, 85, alpha);
         case "9":
            return new Color(85, 85, 255, alpha);
         case "a":
            return new Color(85, 255, 85, alpha);
         case "b":
            return new Color(85, 255, 255, alpha);
         case "c":
            return new Color(255, 85, 85, alpha);
         case "d":
            return new Color(255, 85, 255, alpha);
         case "e":
            return new Color(255, 255, 85, alpha);
      }

      return new Color(255, 255, 255, alpha);
   }

   // $FF: synthetic method
   public static Color colorCode$default(ColorUtils var0, String var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 255;
      }

      return var0.colorCode(var1, var2);
   }

   @Nullable
   public final Color blend(@NotNull Color color1, @NotNull Color color2, double ratio) {
      Intrinsics.checkNotNullParameter(color1, "color1");
      Intrinsics.checkNotNullParameter(color2, "color2");
      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      float red = rgb1[0] * r + rgb2[0] * ir;
      float green = rgb1[1] * r + rgb2[1] * ir;
      float blue = rgb1[2] * r + rgb2[2] * ir;
      if (red < 0.0F) {
         red = 0.0F;
      } else if (red > 255.0F) {
         red = 255.0F;
      }

      if (green < 0.0F) {
         green = 0.0F;
      } else if (green > 255.0F) {
         green = 255.0F;
      }

      if (blue < 0.0F) {
         blue = 0.0F;
      } else if (blue > 255.0F) {
         blue = 255.0F;
      }

      Color color3 = null;

      try {
         color3 = new Color(red, green, blue);
      } catch (IllegalArgumentException exp) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         exp.printStackTrace();
      }

      return color3;
   }

   @NotNull
   public final int[] getFraction(@NotNull float[] fractions, float progress) {
      Intrinsics.checkNotNullParameter(fractions, "fractions");
      int startPoint = 0;
      int[] range = new int[2];

      for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
      }

      if (startPoint >= fractions.length) {
         startPoint = fractions.length - 1;
      }

      range[0] = startPoint - 1;
      range[1] = startPoint;
      return range;
   }

   public final int getColor(float hueoffset, float saturation, float brightness) {
      float speed = 4500.0F;
      float hue = (float)(System.currentTimeMillis() % (long)((int)speed)) / speed;
      return Color.HSBtoRGB(hue - hueoffset / (float)54, saturation, brightness);
   }

   @JvmStatic
   public static final void setColour(int colour) {
      float a = (float)(colour >> 24 & 255) / 255.0F;
      float r = (float)(colour >> 16 & 255) / 255.0F;
      float g = (float)(colour >> 8 & 255) / 255.0F;
      float b = (float)(colour & 255) / 255.0F;
      GL11.glColor4f(r, g, b, a);
   }

   @JvmStatic
   @Nullable
   public static final String getColor(int n) {
      if (n != 1) {
         if (n == 2) {
            return "§a";
         }

         if (n == 3) {
            return "§3";
         }

         if (n == 4) {
            return "§4";
         }

         if (n >= 5) {
            return "§e";
         }
      }

      return "§f";
   }

   @JvmStatic
   @NotNull
   public static final Color astolfo(int index, float speed) {
      Color var2 = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index * 200) / speed % (float)2 - (float)1) * 0.3F + 0.55F, 0.55F, 1.0F);
      Intrinsics.checkNotNullExpressionValue(var2, "getHSBColor((abs(((((Sys….3F)) + 0.55F, 0.55F, 1F)");
      return var2;
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(int index, float speed) {
      Color var2 = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index * 200) / speed % (float)2 - (float)1) * 0.3F + 0.55F, 1.0F, 1.0F);
      Intrinsics.checkNotNullExpressionValue(var2, "getHSBColor((abs(((((Sys… (0.3F)) + 0.55F, 1F, 1F)");
      return var2;
   }

   @Nullable
   public final Double interpolate(double oldValue, double newValue, double interpolationValue) {
      return oldValue + (newValue - oldValue) * interpolationValue;
   }

   public final float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
      Double var10000 = this.interpolate((double)oldValue, (double)newValue, (double)((float)interpolationValue));
      Intrinsics.checkNotNull(var10000);
      return (float)var10000;
   }

   @Nullable
   public final Color interpolateColorHue(@NotNull Color color1, @NotNull Color color2, float amount) {
      Intrinsics.checkNotNullParameter(color1, "color1");
      Intrinsics.checkNotNullParameter(color2, "color2");
      float amount = Math.min(1.0F, Math.max(0.0F, amount));
      float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), (float[])null);
      float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), (float[])null);
      Color resultColor = Color.getHSBColor(this.interpolateFloat(color1HSB[0], color2HSB[0], (double)amount), this.interpolateFloat(color1HSB[1], color2HSB[1], (double)amount), this.interpolateFloat(color1HSB[2], color2HSB[2], (double)amount));
      return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(long offset) {
      Color currentColor = new Color(Color.HSBtoRGB((float)(System.nanoTime() + offset) / 1.0E10F % (float)1, 1.0F, 1.0F));
      return new Color((float)currentColor.getRed() / 255.0F * 1.0F, (float)currentColor.getGreen() / 255.0F * 1.0F, (float)currentColor.getBlue() / 255.0F * 1.0F, (float)currentColor.getAlpha() / 255.0F);
   }

   @JvmStatic
   public static final int interpolateInt(int oldValue, int newValue, double interpolationValue) {
      Double var10000 = INSTANCE.interpolate((double)oldValue, (double)newValue, (double)((float)interpolationValue));
      Intrinsics.checkNotNull(var10000);
      return (int)var10000;
   }

   @JvmStatic
   @Nullable
   public static final Color interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      Intrinsics.checkNotNullParameter(color1, "color1");
      Intrinsics.checkNotNullParameter(color2, "color2");
      float amount = Math.min(1.0F, Math.max(0.0F, amount));
      ColorUtils var10002 = INSTANCE;
      int var4 = interpolateInt(color1.getRed(), color2.getRed(), (double)amount);
      ColorUtils var10003 = INSTANCE;
      int var5 = interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount);
      ColorUtils var10004 = INSTANCE;
      int var6 = interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount);
      ColorUtils var10005 = INSTANCE;
      return new Color(var4, var5, var6, interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   @JvmStatic
   @NotNull
   public static final Color reAlpha(@NotNull Color color, int alpha) {
      Intrinsics.checkNotNullParameter(color, "color");
      return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
   }

   @JvmStatic
   @NotNull
   public static final Color reAlpha(@NotNull Color color, float alpha) {
      Intrinsics.checkNotNullParameter(color, "color");
      return new Color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, alpha);
   }

   @JvmStatic
   @NotNull
   public static final Color slowlyRainbow(long time, int count, float qd, float sq) {
      Color color = new Color(Color.HSBtoRGB(((float)time + (float)count * -3000000.0F) / (float)2 / 1.0E9F, qd, sq));
      return new Color((float)color.getRed() / 255.0F * (float)1, (float)color.getGreen() / 255.0F * (float)1, (float)color.getBlue() / 255.0F * (float)1, (float)color.getAlpha() / 255.0F);
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(int index, double speed, float saturation) {
      double hue = (double)index * speed % (double)360 / (double)360;
      Color var6 = Color.getHSBColor((float)hue, Math.abs(RangesKt.coerceIn(saturation, 0.0F, 1.0F)), 1.0F);
      Intrinsics.checkNotNullExpressionValue(var6, "getHSBColor(hue.toFloat(…on.coerceIn(0f, 1f)), 1f)");
      return var6;
   }

   @JvmStatic
   @NotNull
   public static final Color skyRainbow(int var2, float bright, float st, double speed) {
      double v1 = (double)0.0F;
      v1 = Math.ceil((double)System.currentTimeMillis() / speed + (double)((long)var2 * 109L)) / (double)5;
      double it = (double)360.0F;
      int var10 = 0;
      v1 %= it;
      Color var5 = Color.getHSBColor(it / (double)360.0F < (double)0.5F ? -((float)(v1 / (double)360.0F)) : (float)(v1 / (double)360.0F), st, bright);
      Intrinsics.checkNotNullExpressionValue(var5, "getHSBColor(if ((360.0.a….toFloat() }, st, bright)");
      return var5;
   }

   @JvmStatic
   @NotNull
   public static final Color fade(@NotNull Color color, int index, int count) {
      Intrinsics.checkNotNullParameter(color, "color");
      float[] hsb = new float[3];
      Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
      float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
      brightness = 0.5F + 0.5F * brightness;
      hsb[2] = brightness % 2.0F;
      return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
   }

   @NotNull
   public final Color healthColor(float hp, float maxHP, int alpha) {
      int pct = (int)(hp / maxHP * 255.0F);
      int var5 = 255 - pct;
      short var6 = 255;
      var5 = Math.min(var5, var6);
      var6 = 0;
      int var10002 = Math.max(var5, var6);
      var5 = 255;
      var5 = Math.min(pct, var5);
      var6 = 0;
      return new Color(var10002, Math.max(var5, var6), 0, alpha);
   }

   // $FF: synthetic method
   public static Color healthColor$default(ColorUtils var0, float var1, float var2, int var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = 255;
      }

      return var0.healthColor(var1, var2, var3);
   }

   @NotNull
   public final Color mixColors(@NotNull Color color1, @NotNull Color color2, double ms, int offset) {
      Intrinsics.checkNotNullParameter(color1, "color1");
      Intrinsics.checkNotNullParameter(color2, "color2");
      double timer = (double)System.currentTimeMillis() / (double)1.0E8F * ms * (double)400000.0F;
      double percent = (Math.sin(timer + (double)((float)offset * 0.55F)) + (double)1) * (double)0.5F;
      double inverse_percent = (double)1.0F - percent;
      int redPart = (int)((double)color1.getRed() * percent + (double)color2.getRed() * inverse_percent);
      int greenPart = (int)((double)color1.getGreen() * percent + (double)color2.getGreen() * inverse_percent);
      int bluePart = (int)((double)color1.getBlue() * percent + (double)color2.getBlue() * inverse_percent);
      return new Color(redPart, greenPart, bluePart);
   }

   @JvmStatic
   @NotNull
   public static final Color hslRainbow(int index, float speed) {
      Color var2 = Color.getHSBColor(Math.abs((float)((int)(System.currentTimeMillis() - startTime) - index * 200) / speed % (float)2 - (float)1), 1.0F, 1.0F);
      Intrinsics.checkNotNullExpressionValue(var2, "getHSBColor((abs(((((Sys…peed) % 2) - 1)), 1F, 1F)");
      return var2;
   }

   static {
      byte var0 = 16;

      int i;
      int red;
      int green;
      int blue;
      for(int var1 = 0; var1 < var0; hexColors[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255) {
         i = var1++;
         int var4 = 0;
         int baseColor = (i >> 3 & 1) * 85;
         red = (i >> 2 & 1) * 170 + baseColor + (i == 6 ? 85 : 0);
         green = (i >> 1 & 1) * 170 + baseColor;
         blue = (i & 1) * 170 + baseColor;
      }

   }
}
