package net.ccbluex.liquidbounce.utils.render;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002+,B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010 \u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010!\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\"\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010%\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020'2\u0006\u0010(\u001a\u00020)¨\u0006-"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils;", "", "()V", "apply", "", "type", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "order", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "value", "easeInBack", "x", "easeInCirc", "easeInCubic", "easeInElastic", "easeInExpo", "easeInOutBack", "easeInOutCirc", "easeInOutCubic", "easeInOutElastic", "easeInOutExpo", "easeInOutQuad", "easeInOutQuart", "easeInOutQuint", "easeInOutSine", "easeInQuad", "easeInQuart", "easeInQuint", "easeInSine", "easeOutBack", "easeOutCirc", "easeOutCubic", "easeOutElastic", "easeOutExpo", "easeOutQuad", "easeOutQuart", "easeOutQuint", "easeOutSine", "getEnumEasingList", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "name", "", "getEnumEasingOrderList", "EnumEasingOrder", "EnumEasingType", "CrossSine"}
)
public final class EaseUtils {
   @NotNull
   public static final EaseUtils INSTANCE = new EaseUtils();

   private EaseUtils() {
   }

   public final double easeInSine(double x) {
      return (double)1 - Math.cos(x * Math.PI / (double)2);
   }

   public final double easeOutSine(double x) {
      return Math.sin(x * Math.PI / (double)2);
   }

   public final double easeInOutSine(double x) {
      return -(Math.cos(Math.PI * x) - (double)1) / (double)2;
   }

   public final double easeInQuad(double x) {
      return x * x;
   }

   public final double easeOutQuad(double x) {
      return (double)1 - ((double)1 - x) * ((double)1 - x);
   }

   public final double easeInOutQuad(double x) {
      return x < (double)0.5F ? (double)2 * x * x : (double)1 - Math.pow((double)-2 * x + (double)2, (double)2) / (double)2;
   }

   public final double easeInCubic(double x) {
      return x * x * x;
   }

   public final double easeOutCubic(double x) {
      return (double)1 - Math.pow((double)1 - x, (double)3);
   }

   public final double easeInOutCubic(double x) {
      return x < (double)0.5F ? (double)4 * x * x * x : (double)1 - Math.pow((double)-2 * x + (double)2, (double)3) / (double)2;
   }

   public final double easeInQuart(double x) {
      return x * x * x * x;
   }

   @JvmStatic
   public static final double easeOutQuart(double x) {
      return (double)1 - Math.pow((double)1 - x, (double)4);
   }

   @JvmStatic
   public static final double easeInOutQuart(double x) {
      return x < (double)0.5F ? (double)8 * x * x * x * x : (double)1 - Math.pow((double)-2 * x + (double)2, (double)4) / (double)2;
   }

   public final double easeInQuint(double x) {
      return x * x * x * x * x;
   }

   @JvmStatic
   public static final double easeOutQuint(double x) {
      return (double)1 - Math.pow((double)1 - x, (double)5);
   }

   @JvmStatic
   public static final double easeInOutQuint(double x) {
      return x < (double)0.5F ? (double)16 * x * x * x * x * x : (double)1 - Math.pow((double)-2 * x + (double)2, (double)5) / (double)2;
   }

   @JvmStatic
   public static final double easeInExpo(double x) {
      return x == (double)0.0F ? (double)0.0F : Math.pow((double)2.0F, (double)10 * x - (double)10);
   }

   public final double easeOutExpo(double x) {
      return x == (double)1.0F ? (double)1.0F : (double)1 - Math.pow((double)2.0F, (double)-10 * x);
   }

   @JvmStatic
   public static final double easeInOutExpo(double x) {
      return x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : (x < (double)0.5F ? Math.pow((double)2.0F, (double)20 * x - (double)10) / (double)2 : ((double)2 - Math.pow((double)2.0F, (double)-20 * x + (double)10)) / (double)2));
   }

   public final double easeInCirc(double x) {
      return (double)1 - Math.sqrt((double)1 - Math.pow(x, (double)2));
   }

   public final double easeOutCirc(double x) {
      return Math.sqrt((double)1 - Math.pow(x - (double)1, (double)2));
   }

   public final double easeInOutCirc(double x) {
      return x < (double)0.5F ? ((double)1 - Math.sqrt((double)1 - Math.pow((double)2 * x, (double)2))) / (double)2 : (Math.sqrt((double)1 - Math.pow((double)-2 * x + (double)2, (double)2)) + (double)1) / (double)2;
   }

   public final double easeInBack(double x) {
      double c1 = 1.70158;
      double c3 = c1 + (double)1;
      return c3 * x * x * x - c1 * x * x;
   }

   @JvmStatic
   public static final double easeOutBack(double x) {
      double c1 = 1.70158;
      double c3 = c1 + (double)1;
      return (double)1 + c3 * Math.pow(x - (double)1, (double)3) + c1 * Math.pow(x - (double)1, (double)2);
   }

   public final double easeInOutBack(double x) {
      double c1 = 1.70158;
      double c2 = c1 * 1.525;
      return x < (double)0.5F ? Math.pow((double)2 * x, (double)2) * ((c2 + (double)1) * (double)2 * x - c2) / (double)2 : (Math.pow((double)2 * x - (double)2, (double)2) * ((c2 + (double)1) * (x * (double)2 - (double)2) + c2) + (double)2) / (double)2;
   }

   public final double easeInElastic(double x) {
      double c4 = 2.0943951023931953;
      return x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : Math.pow((double)-2.0F, (double)10 * x - (double)10) * Math.sin((x * (double)10 - (double)10.75F) * c4));
   }

   public final double easeOutElastic(double x) {
      double c4 = 2.0943951023931953;
      return x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : Math.pow((double)2.0F, (double)-10 * x) * Math.sin((x * (double)10 - (double)0.75F) * c4) + (double)1);
   }

   public final double easeInOutElastic(double x) {
      double c5 = 1.3962634015954636;
      return x == (double)0.0F ? (double)0.0F : (x == (double)1.0F ? (double)1.0F : (x < (double)0.5F ? -(Math.pow((double)2.0F, (double)20 * x - (double)10) * Math.sin(((double)20 * x - (double)11.125F) * c5)) / (double)2 : Math.pow((double)2.0F, (double)-20 * x + (double)10) * Math.sin(((double)20 * x - (double)11.125F) * c5) / (double)2 + (double)1));
   }

   @NotNull
   public final ListValue getEnumEasingList(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      EnumEasingType[] $this$map$iv = EaseUtils.EnumEasingType.values();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      EnumEasingType[] var7 = $this$map$iv;
      int var8 = 0;
      int var9 = $this$map$iv.length;

      while(var8 < var9) {
         Object item$iv$iv = var7[var8];
         ++var8;
         int var12 = 0;
         destination$iv$iv.add(((EnumEasingType)item$iv$iv).toString());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var10001 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var20 = (String[])var10001;
         String var15 = EaseUtils.EnumEasingType.SINE.toString();
         String[] var16 = var20;
         return new ListValue(name, var16, var15);
      }
   }

   @NotNull
   public final ListValue getEnumEasingOrderList(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      EnumEasingOrder[] $this$map$iv = EaseUtils.EnumEasingOrder.values();
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      EnumEasingOrder[] var7 = $this$map$iv;
      int var8 = 0;
      int var9 = $this$map$iv.length;

      while(var8 < var9) {
         Object item$iv$iv = var7[var8];
         ++var8;
         int var12 = 0;
         destination$iv$iv.add(((EnumEasingOrder)item$iv$iv).toString());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var10001 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var20 = (String[])var10001;
         String var15 = EaseUtils.EnumEasingOrder.FAST_AT_START.toString();
         String[] var16 = var20;
         return new ListValue(name, var16, var15);
      }
   }

   @JvmStatic
   public static final double apply(@NotNull EnumEasingType type, @NotNull EnumEasingOrder order, double value) {
      Intrinsics.checkNotNullParameter(type, "type");
      Intrinsics.checkNotNullParameter(order, "order");
      if (type == EaseUtils.EnumEasingType.NONE) {
         return value;
      } else {
         String methodName = "ease" + order.getMethodName() + type.getFriendlyName();
         Method[] var5 = INSTANCE.getClass().getDeclaredMethods();
         Intrinsics.checkNotNullExpressionValue(var5, "this.javaClass.declaredMethods");
         Object[] var7 = (Object[])var5;
         int var8 = 0;
         int var9 = var7.length;

         Object var10000;
         while(true) {
            if (var8 < var9) {
               Object var10 = var7[var8];
               ++var8;
               Method it = (Method)var10;
               int var12 = 0;
               if (!it.getName().equals(methodName)) {
                  continue;
               }

               var10000 = var10;
               break;
            }

            var10000 = null;
            break;
         }

         Method it = (Method)var10000;
         int var13 = 0;
         double var16;
         if (it != null) {
            EaseUtils var10001 = INSTANCE;
            Object[] var14 = new Object[]{value};
            Object var15 = it.invoke(var10001, var14);
            if (var15 == null) {
               throw new NullPointerException("null cannot be cast to non-null type kotlin.Double");
            }

            var16 = (Double)var15;
         } else {
            ClientUtils.INSTANCE.logError(Intrinsics.stringPlus("Cannot found easing method: ", methodName));
            var16 = value;
         }

         return var16;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "", "(Ljava/lang/String;I)V", "friendlyName", "", "getFriendlyName", "()Ljava/lang/String;", "NONE", "SINE", "QUAD", "CUBIC", "QUART", "QUINT", "EXPO", "CIRC", "BACK", "ELASTIC", "CrossSine"}
   )
   public static enum EnumEasingType {
      @NotNull
      private final String friendlyName;
      NONE,
      SINE,
      QUAD,
      CUBIC,
      QUART,
      QUINT,
      EXPO,
      CIRC,
      BACK,
      ELASTIC;

      private EnumEasingType() {
         String var4 = this.name().substring(0, 1);
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String…ing(startIndex, endIndex)");
         String var3 = var4.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toUpperCase(Locale.ROOT)");
         String var10001 = var3;
         var4 = this.name().substring(1, this.name().length());
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String…ing(startIndex, endIndex)");
         var3 = var4.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         this.friendlyName = Intrinsics.stringPlus(var10001, var3);
      }

      @NotNull
      public final String getFriendlyName() {
         return this.friendlyName;
      }

      // $FF: synthetic method
      private static final EnumEasingType[] $values() {
         EnumEasingType[] var0 = new EnumEasingType[]{NONE, SINE, QUAD, CUBIC, QUART, QUINT, EXPO, CIRC, BACK, ELASTIC};
         return var0;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "", "methodName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMethodName", "()Ljava/lang/String;", "FAST_AT_START", "FAST_AT_END", "FAST_AT_START_AND_END", "CrossSine"}
   )
   public static enum EnumEasingOrder {
      @NotNull
      private final String methodName;
      FAST_AT_START("Out"),
      FAST_AT_END("In"),
      FAST_AT_START_AND_END("InOut");

      private EnumEasingOrder(String methodName) {
         this.methodName = methodName;
      }

      @NotNull
      public final String getMethodName() {
         return this.methodName;
      }

      // $FF: synthetic method
      private static final EnumEasingOrder[] $values() {
         EnumEasingOrder[] var0 = new EnumEasingOrder[]{FAST_AT_START, FAST_AT_END, FAST_AT_START_AND_END};
         return var0;
      }
   }
}
