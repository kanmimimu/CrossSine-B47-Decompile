package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\r\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010\t\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\nJ'\u0010\u000b\u001a\u00020\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\u0002\u0010\u000eJ5\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015J&\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J5\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010\u001f\u001a\u00020\u0017H\u0007¢\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0004J5\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\t\u001a\u00020\u0004¢\u0006\u0002\u0010&J\u000e\u0010'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J_\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010*\u001a\u00020\u00042\b\b\u0002\u0010+\u001a\u00020\u00172\b\b\u0002\u0010,\u001a\u00020\u00172\u0014\b\u0002\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070.H\u0007¢\u0006\u0002\u0010/J\u001a\u00100\u001a\u000201*\u00020\u00042\u0006\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00020\u0004J\u001a\u00100\u001a\u000201*\u00020\u00152\u0006\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u00020\u0015J\u001a\u00100\u001a\u000201*\u00020\u00172\u0006\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u0017J\u001a\u00104\u001a\u000205*\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u0004J\n\u00109\u001a\u00020\u0004*\u00020\u0004J\n\u0010:\u001a\u00020\u0004*\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006;"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/MathUtils;", "", "()V", "DEGREES_TO_RADIANS", "", "RADIANS_TO_DEGREES", "calcCurvePoint", "", "points", "t", "([[Ljava/lang/Double;D)[Ljava/lang/Double;", "distanceSq", "a", "b", "([Ljava/lang/Double;[Ljava/lang/Double;)D", "distanceToSegmentSq", "p", "v", "w", "([Ljava/lang/Double;[Ljava/lang/Double;[Ljava/lang/Double;)D", "gaussian", "", "x", "", "sigma", "getDistance", "x1", "y1", "x2", "y2", "getPointsOnCurve", "num", "([[Ljava/lang/Double;I)[[Ljava/lang/Double;", "interpolate", "old", "current", "partialTicks", "lerp", "([Ljava/lang/Double;[Ljava/lang/Double;D)[Ljava/lang/Double;", "radians", "degrees", "simplifyPoints", "epsilon", "start", "end", "outPoints", "", "([[Ljava/lang/Double;DIILjava/util/List;)[[Ljava/lang/Double;", "inRange", "", "base", "range", "offset", "Lnet/minecraft/util/Vec3;", "direction", "Lnet/minecraft/util/EnumFacing;", "value", "toDegrees", "toRadians", "CrossSine"}
)
public final class MathUtils {
   @NotNull
   public static final MathUtils INSTANCE = new MathUtils();
   public static final double DEGREES_TO_RADIANS = (Math.PI / 180D);
   public static final double RADIANS_TO_DEGREES = (180D / Math.PI);

   private MathUtils() {
   }

   public final double radians(double degrees) {
      return degrees * Math.PI / (double)180;
   }

   public final double interpolate(double old, double current, double partialTicks) {
      return old + (current - old) * partialTicks;
   }

   public final double getDistance(double x1, double y1, double x2, double y2) {
      return Math.sqrt(Math.pow(x1 - x2, (double)2.0F) + Math.pow(y1 - y2, (double)2.0F));
   }

   @NotNull
   public final Double[] lerp(@NotNull Double[] a, @NotNull Double[] b, double t) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      Double[] var5 = new Double[]{a[0] + (b[0] - a[0]) * t, a[1] + (b[1] - a[1]) * t};
      return var5;
   }

   public final double distanceSq(@NotNull Double[] a, @NotNull Double[] b) {
      Intrinsics.checkNotNullParameter(a, "a");
      Intrinsics.checkNotNullParameter(b, "b");
      return Math.pow(a[0] - b[0], (double)2) + Math.pow(a[1] - b[1], (double)2);
   }

   public final double distanceToSegmentSq(@NotNull Double[] p, @NotNull Double[] v, @NotNull Double[] w) {
      Intrinsics.checkNotNullParameter(p, "p");
      Intrinsics.checkNotNullParameter(v, "v");
      Intrinsics.checkNotNullParameter(w, "w");
      double l2 = this.distanceSq(v, w);
      return l2 == (double)0.0F ? this.distanceSq(p, v) : this.distanceSq(p, this.lerp(v, w, RangesKt.coerceAtLeast(RangesKt.coerceAtMost(((p[0] - v[0]) * (w[0] - v[0]) + (p[1] - v[1]) * (w[1] - v[1])) / l2, (double)1.0F), (double)0.0F)));
   }

   public final boolean inRange(float $this$inRange, float base, float range) {
      float var4 = base - range;
      return $this$inRange <= base + range ? var4 <= $this$inRange : false;
   }

   public final boolean inRange(int $this$inRange, int base, int range) {
      int var4 = base - range;
      return $this$inRange <= base + range ? var4 <= $this$inRange : false;
   }

   public final boolean inRange(double $this$inRange, double base, double range) {
      double var7 = base - range;
      return $this$inRange <= base + range ? var7 <= $this$inRange : false;
   }

   @JvmStatic
   @NotNull
   public static final Double[] calcCurvePoint(@NotNull Double[][] points, double t) {
      Intrinsics.checkNotNullParameter(points, "points");
      List cpoints = (List)(new ArrayList());
      int thisCollection$iv = 0;
      int $i$f$toTypedArray = ((Object[])points).length - 1;

      while(thisCollection$iv < $i$f$toTypedArray) {
         int i = thisCollection$iv++;
         cpoints.add(INSTANCE.lerp(points[i], points[i + 1], t));
      }

      Double[] var10000;
      if (cpoints.size() == 1) {
         var10000 = (Double[])cpoints.get(0);
      } else {
         MathUtils var9 = INSTANCE;
         Collection $this$toTypedArray$iv = (Collection)cpoints;
         $i$f$toTypedArray = 0;
         var9 = $this$toTypedArray$iv.toArray(new Double[0][]);
         if (var9 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
         }

         var10000 = calcCurvePoint((Double[][])var9, t);
      }

      return var10000;
   }

   @JvmStatic
   @NotNull
   public static final Double[][] getPointsOnCurve(@NotNull Double[][] points, int num) {
      Intrinsics.checkNotNullParameter(points, "points");
      List cpoints = (List)(new ArrayList());
      int thisCollection$iv = 0;

      while(thisCollection$iv < num) {
         int i = thisCollection$iv++;
         double t = (double)i / ((double)num - (double)1.0F);
         MathUtils var10001 = INSTANCE;
         cpoints.add(calcCurvePoint(points, t));
      }

      Collection $this$toTypedArray$iv = (Collection)cpoints;
      int $i$f$toTypedArray = 0;
      Object[] var10000 = $this$toTypedArray$iv.toArray(new Double[0][]);
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         return (Double[][])var10000;
      }
   }

   public final double toRadians(double $this$toRadians) {
      return $this$toRadians * (Math.PI / 180D);
   }

   @NotNull
   public final Vec3 offset(@NotNull Vec3 $this$offset, @NotNull EnumFacing direction, double value) {
      Intrinsics.checkNotNullParameter($this$offset, "<this>");
      Intrinsics.checkNotNullParameter(direction, "direction");
      Vec3i vec3i = direction.func_176730_m();
      return new Vec3($this$offset.field_72450_a + value * (double)vec3i.func_177958_n(), $this$offset.field_72448_b + value * (double)vec3i.func_177956_o(), $this$offset.field_72449_c + value * (double)vec3i.func_177952_p());
   }

   public final double toDegrees(double $this$toDegrees) {
      return $this$toDegrees * (180D / Math.PI);
   }

   public final float gaussian(int x, float sigma) {
      float s = sigma * sigma * (float)2;
      return 1.0F / (float)Math.sqrt((double)((float)Math.PI * s)) * (float)Math.exp((double)((float)(-(x * x)) / s));
   }

   @JvmStatic
   @JvmOverloads
   @NotNull
   public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start, int end, @NotNull List outPoints) {
      Intrinsics.checkNotNullParameter(points, "points");
      Intrinsics.checkNotNullParameter(outPoints, "outPoints");
      Double[] s = points[start];
      Double[] e = points[end - 1];
      double maxDistSq = (double)0.0F;
      int maxNdx = 1;
      int thisCollection$iv = start + 1;
      int $i$f$toTypedArray = end - 1;

      while(thisCollection$iv < $i$f$toTypedArray) {
         int i = thisCollection$iv++;
         double distSq = INSTANCE.distanceToSegmentSq(points[i], s, e);
         if (distSq > maxDistSq) {
            maxDistSq = distSq;
            maxNdx = i;
         }
      }

      if (Math.sqrt(maxDistSq) > epsilon) {
         MathUtils var10000 = INSTANCE;
         simplifyPoints(points, epsilon, start, maxNdx + 1, outPoints);
         var10000 = INSTANCE;
         simplifyPoints(points, epsilon, maxNdx, end, outPoints);
      } else {
         outPoints.add(s);
         outPoints.add(e);
      }

      Collection $this$toTypedArray$iv = (Collection)outPoints;
      $i$f$toTypedArray = 0;
      Object[] var19 = $this$toTypedArray$iv.toArray(new Double[0][]);
      if (var19 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         return (Double[][])var19;
      }
   }

   // $FF: synthetic method
   public static Double[][] simplifyPoints$default(Double[][] var0, double var1, int var3, int var4, List var5, int var6, Object var7) {
      if ((var6 & 4) != 0) {
         var3 = 0;
      }

      if ((var6 & 8) != 0) {
         var4 = ((Object[])var0).length;
      }

      if ((var6 & 16) != 0) {
         var5 = (List)(new ArrayList());
      }

      return simplifyPoints(var0, var1, var3, var4, var5);
   }

   @JvmStatic
   @JvmOverloads
   @NotNull
   public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start, int end) {
      Intrinsics.checkNotNullParameter(points, "points");
      return simplifyPoints$default(points, epsilon, start, end, (List)null, 16, (Object)null);
   }

   @JvmStatic
   @JvmOverloads
   @NotNull
   public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start) {
      Intrinsics.checkNotNullParameter(points, "points");
      return simplifyPoints$default(points, epsilon, start, 0, (List)null, 24, (Object)null);
   }

   @JvmStatic
   @JvmOverloads
   @NotNull
   public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon) {
      Intrinsics.checkNotNullParameter(points, "points");
      return simplifyPoints$default(points, epsilon, 0, 0, (List)null, 28, (Object)null);
   }
}
