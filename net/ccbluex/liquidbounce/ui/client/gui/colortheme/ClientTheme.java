package net.ccbluex.liquidbounce.ui.client.gui.colortheme;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.CustomClientColor;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u000b\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J(\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J \u0010\u001b\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017J \u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0019\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/gui/colortheme/ClientTheme;", "", "()V", "ClientColorMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getClientColorMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "fadespeed", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getFadespeed", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "index", "getIndex", "mode", "", "", "getMode", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getColor", "Ljava/awt/Color;", "", "customColor", "", "getColorFromName", "name", "alpha", "getColorWithAlpha", "setColor", "type", "CrossSine"}
)
public final class ClientTheme {
   @NotNull
   public static final ClientTheme INSTANCE = new ClientTheme();
   @NotNull
   private static final String[] mode;
   @NotNull
   private static final ListValue ClientColorMode;
   @NotNull
   private static final IntegerValue fadespeed;
   @NotNull
   private static final IntegerValue index;

   private ClientTheme() {
   }

   @NotNull
   public final String[] getMode() {
      return mode;
   }

   @NotNull
   public final ListValue getClientColorMode() {
      return ClientColorMode;
   }

   @NotNull
   public final IntegerValue getFadespeed() {
      return fadespeed;
   }

   @NotNull
   public final IntegerValue getIndex() {
      return index;
   }

   @NotNull
   public final Color setColor(boolean type, int alpha, boolean customColor) {
      if (CustomClientColor.INSTANCE.getState() && customColor) {
         return ColorExtensionKt.setAlpha((Color)CustomClientColor.INSTANCE.getColor().get(), alpha);
      } else {
         Color var10000;
         label184: {
            Intrinsics.checkNotNullExpressionValue(mode, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (mode) {
               case "darknight":
                  var10000 = type ? new Color(203, 200, 204, alpha) : new Color(93, 95, 95, alpha);
                  break label184;
               case "cherry":
                  var10000 = type ? new Color(215, 171, 168, alpha) : new Color(206, 58, 98, alpha);
                  break label184;
               case "flower":
                  var10000 = type ? new Color(182, 140, 195, alpha) : new Color(184, 85, 199, alpha);
                  break label184;
               case "terminal":
                  var10000 = type ? new Color(15, 155, 15, alpha) : new Color(25, 30, 25, alpha);
                  break label184;
               case "soniga":
                  var10000 = type ? new Color(100, 255, 255, alpha) : new Color(255, 100, 255, alpha);
                  break label184;
               case "sundae":
                  var10000 = type ? new Color(206, 74, 126, alpha) : new Color(28, 28, 27, alpha);
                  break label184;
               case "astolfo":
                  var10000 = type ? ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(0, 1.0F, 0.6F, -((double)((Number)fadespeed.get()).intValue())), alpha) : ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(90, 1.0F, 0.6F, -((double)((Number)fadespeed.get()).intValue())), alpha);
                  break label184;
               case "polarized":
                  var10000 = type ? new Color(173, 239, 209, alpha) : new Color(0, 32, 64, alpha);
                  break label184;
               case "pumpkin":
                  var10000 = type ? new Color(241, 166, 98, alpha) : new Color(255, 216, 169, alpha);
                  break label184;
               case "may":
                  var10000 = type ? new Color(255, 255, 255, alpha) : new Color(255, 80, 255, alpha);
                  break label184;
               case "sun":
                  var10000 = type ? new Color(252, 205, 44, alpha) : new Color(255, 143, 0, alpha);
                  break label184;
               case "aqua":
                  var10000 = type ? new Color(80, 255, 255, alpha) : new Color(80, 190, 255, alpha);
                  break label184;
               case "cero":
                  var10000 = type ? new Color(170, 255, 170, alpha) : new Color(170, 0, 170, alpha);
                  break label184;
               case "fire":
                  var10000 = type ? new Color(255, 45, 30, alpha) : new Color(255, 123, 15, alpha);
                  break label184;
               case "mint":
                  var10000 = type ? new Color(85, 255, 255, alpha) : new Color(85, 255, 140, alpha);
                  break label184;
               case "tree":
                  var10000 = type ? new Color(76, 255, 102, alpha) : new Color(18, 155, 38, alpha);
                  break label184;
               case "azure":
                  var10000 = type ? new Color(0, 180, 255, alpha) : new Color(0, 90, 255, alpha);
                  break label184;
               case "blaze":
                  var10000 = type ? new Color(255, 0, 0, alpha) : new Color(255, 100, 100, alpha);
                  break label184;
               case "coral":
                  var10000 = type ? new Color(244, 168, 150, alpha) : new Color(52, 133, 151, alpha);
                  break label184;
               case "loyoi":
                  var10000 = type ? new Color(255, 131, 124, alpha) : new Color(255, 131, 0, alpha);
                  break label184;
               case "magic":
                  var10000 = type ? new Color(255, 180, 255, alpha) : new Color(192, 67, 255, alpha);
                  break label184;
               case "peony":
                  var10000 = type ? new Color(255, 120, 255, alpha) : new Color(255, 190, 255, alpha);
                  break label184;
               case "water":
                  var10000 = type ? new Color(108, 170, 207, alpha) : new Color(35, 69, 148, alpha);
                  break label184;
               case "rainbow":
                  var10000 = type ? ColorExtensionKt.setAlpha(ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(0, 1.0F, 1.0F, -((double)((Number)fadespeed.get()).intValue())), alpha), alpha) : ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(90, 1.0F, 1.0F, -((double)((Number)fadespeed.get()).intValue())), alpha);
                  break label184;
            }

            var10000 = new Color(-1);
         }

         Color color = var10000;
         return color;
      }
   }

   // $FF: synthetic method
   public static Color setColor$default(ClientTheme var0, boolean var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = true;
      }

      return var0.setColor(var1, var2, var3);
   }

   @NotNull
   public final Color getColor(int index, boolean customColor) {
      if (CustomClientColor.INSTANCE.getState() && customColor) {
         return (Color)CustomClientColor.INSTANCE.getColor().get();
      } else if (ClientColorMode.equals("Rainbow")) {
         return ColorUtils.skyRainbow(index * ((Number)ClientTheme.index.get()).intValue(), 1.0F, 1.0F, -((double)((Number)fadespeed.get()).intValue()));
      } else if (ClientColorMode.equals("Astolfo")) {
         return ColorUtils.skyRainbow(index * ((Number)ClientTheme.index.get()).intValue(), 1.0F, 0.6F, -((double)((Number)fadespeed.get()).intValue()));
      } else {
         String var5 = ((String)ClientColorMode.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         String mode = var5;
         Pair colorPair = new Pair[]{TuplesKt.to("cherry", new Pair(new Color(206, 58, 98), new Color(215, 171, 168))), TuplesKt.to("water", new Pair(new Color(35, 69, 148), new Color(108, 170, 207))), TuplesKt.to("magic", new Pair(new Color(255, 180, 255), new Color(181, 139, 194))), TuplesKt.to("tree", new Pair(new Color(18, 155, 38), new Color(76, 255, 102))), TuplesKt.to("darknight", new Pair(new Color(93, 95, 95), new Color(203, 200, 204))), TuplesKt.to("sun", new Pair(new Color(255, 143, 0), new Color(252, 205, 44))), TuplesKt.to("flower", new Pair(new Color(184, 85, 199), new Color(182, 140, 195))), TuplesKt.to("loyoi", new Pair(new Color(255, 131, 0), new Color(255, 131, 124))), TuplesKt.to("soniga", new Pair(new Color(255, 100, 255), new Color(100, 255, 255))), TuplesKt.to("may", new Pair(new Color(255, 80, 255), new Color(255, 255, 255))), TuplesKt.to("mint", new Pair(new Color(85, 255, 140), new Color(85, 255, 255))), TuplesKt.to("cero", new Pair(new Color(170, 0, 170), new Color(170, 255, 170))), TuplesKt.to("azure", new Pair(new Color(0, 90, 255), new Color(0, 180, 255))), TuplesKt.to("pumpkin", new Pair(new Color(255, 216, 169), new Color(241, 166, 98))), TuplesKt.to("polarized", new Pair(new Color(0, 32, 64), new Color(173, 239, 209))), TuplesKt.to("sundae", new Pair(new Color(28, 28, 27), new Color(206, 74, 126))), TuplesKt.to("terminal", new Pair(new Color(25, 30, 25), new Color(15, 155, 15))), TuplesKt.to("coral", new Pair(new Color(52, 133, 151), new Color(244, 168, 150))), TuplesKt.to("fire", new Pair(new Color(255, 45, 30), new Color(255, 123, 15))), TuplesKt.to("aqua", new Pair(new Color(80, 255, 255), new Color(80, 190, 255))), TuplesKt.to("peony", new Pair(new Color(255, 120, 255), new Color(255, 190, 255))), TuplesKt.to("blaze", new Pair(new Color(255, 0, 0), new Color(255, 100, 100)))};
         Map colorMap = MapsKt.mapOf(colorPair);
         colorPair = (Pair)colorMap.get(mode);
         return colorPair != null ? ColorUtils.INSTANCE.mixColors((Color)colorPair.getFirst(), (Color)colorPair.getSecond(), ((Number)fadespeed.get()).doubleValue() / (double)5.0F, index * ((Number)ClientTheme.index.get()).intValue()) : new Color(-1);
      }
   }

   // $FF: synthetic method
   public static Color getColor$default(ClientTheme var0, int var1, boolean var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = true;
      }

      return var0.getColor(var1, var2);
   }

   @NotNull
   public final Color getColorFromName(@NotNull String name, final int index, final int alpha, boolean customColor) {
      Intrinsics.checkNotNullParameter(name, "name");
      if (CustomClientColor.INSTANCE.getState() && customColor) {
         return (Color)CustomClientColor.INSTANCE.getColor().get();
      } else {
         Pair[] var6 = new Pair[]{TuplesKt.to("cherry", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(206, 58, 98), new Color(215, 171, 168), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("water", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(35, 69, 148), new Color(108, 170, 207), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("magic", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 180, 255), new Color(181, 139, 194), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("tree", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(18, 155, 38), new Color(76, 255, 102), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("darknight", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(93, 95, 95), new Color(203, 200, 204), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("sun", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 143, 0), new Color(252, 205, 44), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("flower", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(184, 85, 199), new Color(182, 140, 195), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("loyoi", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 131, 0), new Color(255, 131, 124), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("soniga", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 100, 255), new Color(100, 255, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("may", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 80, 255), new Color(255, 255, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("mint", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(85, 255, 140), new Color(85, 255, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("cero", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(170, 0, 170), new Color(170, 255, 170), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("azure", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 90, 255), new Color(0, 180, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("rainbow", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index * ((Number)ClientTheme.this.getIndex().get()).intValue(), 1.0F, 1.0F, fadeSpeed * (double)-5), alpha);
            }
         }), TuplesKt.to("astolfo", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index * ((Number)ClientTheme.this.getIndex().get()).intValue(), 1.0F, 0.6F, fadeSpeed * (double)-5), alpha);
            }
         }), TuplesKt.to("pumpkin", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 216, 169), new Color(241, 166, 98), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("polarized", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 32, 64), new Color(173, 239, 209), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("sundae", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(28, 28, 27), new Color(206, 74, 126), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("terminal", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(25, 30, 25), new Color(15, 155, 15), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("coral", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(52, 133, 151), new Color(244, 168, 150), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("fire", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 45, 30), new Color(255, 123, 15), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("aqua", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(80, 255, 255), new Color(80, 190, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("peony", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 120, 255), new Color(255, 190, 255), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         }), TuplesKt.to("blaze", new Function1() {
            @NotNull
            public final Color invoke(double fadeSpeed) {
               return ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 0, 0), new Color(255, 100, 100), fadeSpeed, index * ((Number)ClientTheme.this.getIndex().get()).intValue()), alpha);
            }
         })};
         Map colorMap = MapsKt.mapOf(var6);
         double fadeSpeed = ((Number)fadespeed.get()).doubleValue() / (double)5.0F;
         String var8 = name.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var8, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         Function1 var10000 = (Function1)colorMap.get(var8);
         Color var10 = var10000 == null ? null : (Color)var10000.invoke(fadeSpeed);
         if (var10 == null) {
            var10 = new Color(-1);
         }

         return var10;
      }
   }

   // $FF: synthetic method
   public static Color getColorFromName$default(ClientTheme var0, String var1, int var2, int var3, boolean var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = true;
      }

      return var0.getColorFromName(var1, var2, var3, var4);
   }

   @NotNull
   public final Color getColorWithAlpha(int index, int alpha, boolean customColor) {
      if (CustomClientColor.INSTANCE.getState() && customColor) {
         return ColorExtensionKt.setAlpha((Color)CustomClientColor.INSTANCE.getColor().get(), alpha);
      } else {
         double fadeSpeed = ((Number)fadespeed.get()).doubleValue() / (double)5.0F;
         Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         Color var10000;
         switch (var7) {
            case "darknight":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(93, 95, 95), new Color(203, 200, 204), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "cherry":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(206, 58, 98), new Color(215, 171, 168), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "flower":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(184, 85, 199), new Color(182, 140, 195), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "terminal":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(25, 30, 25), new Color(15, 155, 15), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "soniga":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 100, 255), new Color(100, 255, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "sundae":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(28, 28, 27), new Color(206, 74, 126), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "astolfo":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index * ((Number)ClientTheme.index.get()).intValue(), 1.0F, 0.6F, fadeSpeed * (double)-5), alpha);
               return var10000;
            case "polarized":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 32, 64), new Color(173, 239, 209), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "pumpkin":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 216, 169), new Color(241, 166, 98), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "may":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 80, 255), new Color(255, 255, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "sun":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 143, 0), new Color(252, 205, 44), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "aqua":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(80, 255, 255), new Color(80, 190, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "cero":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(170, 0, 170), new Color(170, 255, 170), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "fire":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 45, 30), new Color(255, 123, 15), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "mint":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(85, 255, 180), new Color(85, 255, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "tree":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(18, 155, 38), new Color(76, 255, 102), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "azure":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(0, 90, 255), new Color(0, 180, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "blaze":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 0, 0), new Color(255, 100, 100), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "coral":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(52, 133, 151), new Color(244, 168, 150), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "loyoi":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 131, 0), new Color(255, 131, 124), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "magic":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 180, 255), new Color(181, 139, 194), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "peony":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(255, 120, 255), new Color(255, 190, 255), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "water":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.INSTANCE.mixColors(new Color(35, 69, 148), new Color(108, 170, 207), fadeSpeed, index * ((Number)ClientTheme.index.get()).intValue()), alpha);
               return var10000;
            case "rainbow":
               var10000 = ColorExtensionKt.setAlpha(ColorUtils.skyRainbow(index * ((Number)ClientTheme.index.get()).intValue(), 1.0F, 1.0F, fadeSpeed * (double)-5), alpha);
               return var10000;
         }

         var10000 = new Color(-1);
         return var10000;
      }
   }

   // $FF: synthetic method
   public static Color getColorWithAlpha$default(ClientTheme var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = true;
      }

      return var0.getColorWithAlpha(var1, var2, var3);
   }

   static {
      String[] var0 = new String[]{"Cherry", "Water", "Magic", "DarkNight", "Sun", "Tree", "Flower", "Loyoi", "Soniga", "May", "Mint", "Cero", "Azure", "Rainbow", "Astolfo", "Pumpkin", "Polarized", "Sundae", "Terminal", "Coral", "Fire", "Aqua", "Peony", "Blaze"};
      mode = var0;
      ClientTheme var10003 = INSTANCE;
      ClientColorMode = new ListValue("ColorMode", mode, "Cherry");
      fadespeed = new IntegerValue("Fade-speed", 1, 1, 10);
      index = new IntegerValue("index", 1, 1, 10);
   }
}
