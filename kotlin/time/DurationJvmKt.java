package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0011"},
   d2 = {"durationAssertionsEnabled", "", "getDurationAssertionsEnabled", "()Z", "precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", "", "formatToExactDecimals", "", "value", "", "formatUpToDecimals", "kotlin-stdlib"}
)
public final class DurationJvmKt {
   private static final boolean durationAssertionsEnabled = Duration.class.desiredAssertionStatus();
   @NotNull
   private static final ThreadLocal[] precisionFormats;

   public static final boolean getDurationAssertionsEnabled() {
      return durationAssertionsEnabled;
   }

   private static final DecimalFormat createFormatForDecimals(int decimals) {
      DecimalFormat var1 = new DecimalFormat("0");
      int var3 = 0;
      if (decimals > 0) {
         var1.setMinimumFractionDigits(decimals);
      }

      var1.setRoundingMode(RoundingMode.HALF_UP);
      return var1;
   }

   @NotNull
   public static final String formatToExactDecimals(double value, int decimals) {
      DecimalFormat var10;
      if (decimals < precisionFormats.length) {
         ThreadLocal var4 = precisionFormats[decimals];
         Object var5 = var4.get();
         Object var10000;
         if (var5 == null) {
            int var6 = 0;
            DecimalFormat var9 = createFormatForDecimals(decimals);
            var4.set(var9);
            var10000 = var9;
         } else {
            var10000 = var5;
         }

         var10 = (DecimalFormat)var10000;
      } else {
         var10 = createFormatForDecimals(decimals);
      }

      DecimalFormat format = var10;
      String var8 = format.format(value);
      Intrinsics.checkNotNullExpressionValue(var8, "format.format(value)");
      return var8;
   }

   @NotNull
   public static final String formatUpToDecimals(double value, int decimals) {
      DecimalFormat $this$formatUpToDecimals_u24lambda_u2d2 = createFormatForDecimals(0);
      int var6 = 0;
      $this$formatUpToDecimals_u24lambda_u2d2.setMaximumFractionDigits(decimals);
      String var3 = $this$formatUpToDecimals_u24lambda_u2d2.format(value);
      Intrinsics.checkNotNullExpressionValue(var3, "createFormatForDecimals(… }\n        .format(value)");
      return var3;
   }

   static {
      int var0 = 0;

      ThreadLocal[] var1;
      for(var1 = new ThreadLocal[4]; var0 < 4; ++var0) {
         var1[var0] = new ThreadLocal();
      }

      precisionFormats = var1;
   }
}
