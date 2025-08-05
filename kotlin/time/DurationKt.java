package kotlin.time;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000>\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b*\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0005H\u0002ø\u0001\u0000¢\u0006\u0002\u0010&\u001a\u0018\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0010\u00100\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u0001H\u0002\u001a \u00101\u001a\u00020\u00072\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002ø\u0001\u0000¢\u0006\u0002\u00106\u001a\u0010\u00107\u001a\u00020\u00012\u0006\u00102\u001a\u000203H\u0002\u001a)\u00108\u001a\u00020\u0005*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a)\u0010=\u001a\u000203*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\b2\u0006\u0010?\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010A\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\u00052\u0006\u0010?\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010C\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\b2\u0006\u0010E\u001a\u00020FH\u0007ø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00052\u0006\u0010E\u001a\u00020FH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00012\u0006\u0010E\u001a\u00020FH\u0007ø\u0001\u0000¢\u0006\u0002\u0010I\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\r\u001a\u0004\b\u000b\u0010\u000e\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000f\u001a\u0004\b\u000b\u0010\u0010\"!\u0010\u0011\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\n\u001a\u0004\b\u0013\u0010\f\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000e\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0010\"!\u0010\u0014\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\f\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\r\u001a\u0004\b\u0016\u0010\u000e\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0010\"!\u0010\u0017\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\n\u001a\u0004\b\u0019\u0010\f\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000e\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0010\"!\u0010\u001a\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\f\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000e\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0010\"!\u0010\u001d\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\n\u001a\u0004\b\u001f\u0010\f\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000e\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0010\"!\u0010 \u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\n\u001a\u0004\b\"\u0010\f\"!\u0010 \u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000e\"!\u0010 \u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006J"},
   d2 = {"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "parseDuration", "value", "", "strictIso", "", "(Ljava/lang/String;Z)J", "parseOverLongIsoComponent", "skipWhile", "startIndex", "predicate", "Lkotlin/Function1;", "", "substringWhile", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLkotlin/time/DurationUnit;)J", "(ILkotlin/time/DurationUnit;)J", "(JLkotlin/time/DurationUnit;)J", "kotlin-stdlib"}
)
public final class DurationKt {
   public static final int NANOS_IN_MILLIS = 1000000;
   public static final long MAX_NANOS = 4611686018426999999L;
   public static final long MAX_MILLIS = 4611686018427387903L;
   private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalTime.class}
   )
   public static final long toDuration(int $this$toDuration, @NotNull DurationUnit unit) {
      Intrinsics.checkNotNullParameter(unit, "unit");
      return unit.compareTo(DurationUnit.SECONDS) <= 0 ? durationOfNanos(DurationUnitKt.convertDurationUnitOverflow((long)$this$toDuration, unit, DurationUnit.NANOSECONDS)) : toDuration((long)$this$toDuration, unit);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalTime.class}
   )
   public static final long toDuration(long $this$toDuration, @NotNull DurationUnit unit) {
      Intrinsics.checkNotNullParameter(unit, "unit");
      long maxNsInUnit = DurationUnitKt.convertDurationUnitOverflow(4611686018426999999L, DurationUnit.NANOSECONDS, unit);
      if (-maxNsInUnit <= $this$toDuration ? $this$toDuration <= maxNsInUnit : false) {
         return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow($this$toDuration, unit, DurationUnit.NANOSECONDS));
      } else {
         long millis = DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnit.MILLISECONDS);
         return durationOfMillis(RangesKt.coerceIn(millis, -4611686018427387903L, 4611686018427387903L));
      }
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalTime.class}
   )
   public static final long toDuration(double $this$toDuration, @NotNull DurationUnit unit) {
      Intrinsics.checkNotNullParameter(unit, "unit");
      double valueInNs = DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnit.NANOSECONDS);
      boolean var5 = !Double.isNaN(valueInNs);
      if (!var5) {
         int var6 = 0;
         String var10 = "Duration value cannot be NaN.";
         throw new IllegalArgumentException(var10.toString());
      } else {
         long nanos = MathKt.roundToLong(valueInNs);
         long var10000;
         if (-4611686018426999999L <= nanos ? nanos < 4611686018427000000L : false) {
            var10000 = durationOfNanos(nanos);
         } else {
            long millis = MathKt.roundToLong(DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnit.MILLISECONDS));
            var10000 = durationOfMillisNormalized(millis);
         }

         return var10000;
      }
   }

   /** @deprecated */
   public static final long getNanoseconds(int $this$nanoseconds) {
      return toDuration($this$nanoseconds, DurationUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.nanoseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.nanoseconds",
   imports = {"kotlin.time.Duration.Companion.nanoseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getNanoseconds$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getNanoseconds(long $this$nanoseconds) {
      return toDuration($this$nanoseconds, DurationUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.nanoseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.nanoseconds",
   imports = {"kotlin.time.Duration.Companion.nanoseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getNanoseconds$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getNanoseconds(double $this$nanoseconds) {
      return toDuration($this$nanoseconds, DurationUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.nanoseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.nanoseconds",
   imports = {"kotlin.time.Duration.Companion.nanoseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getNanoseconds$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getMicroseconds(int $this$microseconds) {
      return toDuration($this$microseconds, DurationUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.microseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.microseconds",
   imports = {"kotlin.time.Duration.Companion.microseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMicroseconds$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getMicroseconds(long $this$microseconds) {
      return toDuration($this$microseconds, DurationUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.microseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.microseconds",
   imports = {"kotlin.time.Duration.Companion.microseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMicroseconds$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getMicroseconds(double $this$microseconds) {
      return toDuration($this$microseconds, DurationUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.microseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.microseconds",
   imports = {"kotlin.time.Duration.Companion.microseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMicroseconds$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getMilliseconds(int $this$milliseconds) {
      return toDuration($this$milliseconds, DurationUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.milliseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.milliseconds",
   imports = {"kotlin.time.Duration.Companion.milliseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMilliseconds$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getMilliseconds(long $this$milliseconds) {
      return toDuration($this$milliseconds, DurationUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.milliseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.milliseconds",
   imports = {"kotlin.time.Duration.Companion.milliseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMilliseconds$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getMilliseconds(double $this$milliseconds) {
      return toDuration($this$milliseconds, DurationUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.milliseconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.milliseconds",
   imports = {"kotlin.time.Duration.Companion.milliseconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMilliseconds$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getSeconds(int $this$seconds) {
      return toDuration($this$seconds, DurationUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.seconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.seconds",
   imports = {"kotlin.time.Duration.Companion.seconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getSeconds$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getSeconds(long $this$seconds) {
      return toDuration($this$seconds, DurationUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.seconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.seconds",
   imports = {"kotlin.time.Duration.Companion.seconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getSeconds$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getSeconds(double $this$seconds) {
      return toDuration($this$seconds, DurationUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.seconds' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.seconds",
   imports = {"kotlin.time.Duration.Companion.seconds"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getSeconds$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getMinutes(int $this$minutes) {
      return toDuration($this$minutes, DurationUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.minutes' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.minutes",
   imports = {"kotlin.time.Duration.Companion.minutes"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMinutes$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getMinutes(long $this$minutes) {
      return toDuration($this$minutes, DurationUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.minutes' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.minutes",
   imports = {"kotlin.time.Duration.Companion.minutes"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMinutes$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getMinutes(double $this$minutes) {
      return toDuration($this$minutes, DurationUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.minutes' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.minutes",
   imports = {"kotlin.time.Duration.Companion.minutes"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getMinutes$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getHours(int $this$hours) {
      return toDuration($this$hours, DurationUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.hours' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.hours",
   imports = {"kotlin.time.Duration.Companion.hours"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getHours$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getHours(long $this$hours) {
      return toDuration($this$hours, DurationUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.hours' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.hours",
   imports = {"kotlin.time.Duration.Companion.hours"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getHours$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getHours(double $this$hours) {
      return toDuration($this$hours, DurationUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.hours' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.hours",
   imports = {"kotlin.time.Duration.Companion.hours"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getHours$annotations(double var0) {
   }

   /** @deprecated */
   public static final long getDays(int $this$days) {
      return toDuration($this$days, DurationUnit.DAYS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Int.days' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.days",
   imports = {"kotlin.time.Duration.Companion.days"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getDays$annotations(int var0) {
   }

   /** @deprecated */
   public static final long getDays(long $this$days) {
      return toDuration($this$days, DurationUnit.DAYS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Long.days' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.days",
   imports = {"kotlin.time.Duration.Companion.days"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getDays$annotations(long var0) {
   }

   /** @deprecated */
   public static final long getDays(double $this$days) {
      return toDuration($this$days, DurationUnit.DAYS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'Double.days' extension property from Duration.Companion instead.",
      replaceWith = @ReplaceWith(
   expression = "this.days",
   imports = {"kotlin.time.Duration.Companion.days"}
)
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.5"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void getDays$annotations(double var0) {
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalTime.class}
   )
   @InlineOnly
   private static final long times_mvk6XK0/* $FF was: times-mvk6XK0*/(int $this$times, long duration) {
      return Duration.times-UwyO8pc(duration, $this$times);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalTime.class}
   )
   @InlineOnly
   private static final long times_kIfJnKk/* $FF was: times-kIfJnKk*/(double $this$times, long duration) {
      return Duration.times-UwyO8pc(duration, $this$times);
   }

   private static final long parseDuration(String value, boolean strictIso) {
      int length = value.length();
      if (length == 0) {
         throw new IllegalArgumentException("The string is empty");
      } else {
         int index = 0;
         long result = Duration.Companion.getZERO-UwyO8pc();
         String infinityString = "Infinity";
         boolean hasSign = (boolean)value.charAt(index);
         if (hasSign == 43 ? true : hasSign == 45) {
            ++index;
         }

         hasSign = index > 0;
         boolean isNegative = hasSign && StringsKt.startsWith$default((CharSequence)value, '-', false, 2, (Object)null);
         if (length <= index) {
            throw new IllegalArgumentException("No components");
         } else {
            if (value.charAt(index) != 'P') {
               if (strictIso) {
                  throw new IllegalArgumentException();
               }

               if (StringsKt.regionMatches(value, index, infinityString, 0, Math.max(length - index, infinityString.length()), true)) {
                  result = Duration.Companion.getINFINITE-UwyO8pc();
               } else {
                  DurationUnit prevUnit = null;
                  boolean afterFirst = false;
                  boolean allowSpaces = !hasSign;
                  if (hasSign && value.charAt(index) == '(' && StringsKt.last((CharSequence)value) == ')') {
                     allowSpaces = true;
                     ++index;
                     --length;
                     if (index == length) {
                        throw new IllegalArgumentException("No components");
                     }
                  }

                  while(index < length) {
                     if (afterFirst && allowSpaces) {
                        String $this$skipWhile$iv = value;
                        int $i$f$skipWhile = 0;

                        int i$iv;
                        for(i$iv = index; i$iv < $this$skipWhile$iv.length(); ++i$iv) {
                           char it = $this$skipWhile$iv.charAt(i$iv);
                           int whole = 0;
                           if (it != ' ') {
                              break;
                           }
                        }

                        index = i$iv;
                     }

                     afterFirst = true;
                     int $i$f$substringWhile = 0;
                     String $this$skipWhile$iv$iv = value;
                     int $i$f$skipWhile = 0;

                     int i$iv$iv;
                     for(i$iv$iv = index; i$iv$iv < $this$skipWhile$iv$iv.length(); ++i$iv$iv) {
                        char it = $this$skipWhile$iv$iv.charAt(i$iv$iv);
                        int var59 = 0;
                        if (!('0' <= it ? it < ':' : false) && it != '.') {
                           break;
                        }
                     }

                     String $this$skipWhile$iv$iv = value.substring(index, i$iv$iv);
                     Intrinsics.checkNotNullExpressionValue($this$skipWhile$iv$iv, "this as java.lang.String…ing(startIndex, endIndex)");
                     if (((CharSequence)$this$skipWhile$iv$iv).length() == 0) {
                        throw new IllegalArgumentException();
                     }

                     index += $this$skipWhile$iv$iv.length();
                     int $i$f$substringWhile = 0;
                     $this$skipWhile$iv$iv = value;
                     i$iv$iv = 0;

                     int i$iv$iv;
                     for(i$iv$iv = index; i$iv$iv < $this$skipWhile$iv$iv.length(); ++i$iv$iv) {
                        char it = $this$skipWhile$iv$iv.charAt(i$iv$iv);
                        int var21 = 0;
                        if (!('a' <= it ? it < '{' : false)) {
                           break;
                        }
                     }

                     String unitName = value.substring(index, i$iv$iv);
                     Intrinsics.checkNotNullExpressionValue(unitName, "this as java.lang.String…ing(startIndex, endIndex)");
                     index += unitName.length();
                     DurationUnit unit = DurationUnitKt.durationUnitByShortName(unitName);
                     if (prevUnit != null && prevUnit.compareTo(unit) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                     }

                     prevUnit = unit;
                     $i$f$substringWhile = StringsKt.indexOf$default((CharSequence)$this$skipWhile$iv$iv, '.', 0, false, 6, (Object)null);
                     if ($i$f$substringWhile > 0) {
                        byte unitName = 0;
                        String whole = $this$skipWhile$iv$iv.substring(unitName, $i$f$substringWhile);
                        Intrinsics.checkNotNullExpressionValue(whole, "this as java.lang.String…ing(startIndex, endIndex)");
                        result = Duration.plus-LRDsOJo(result, toDuration(Long.parseLong(whole), unit));
                        String unitName = $this$skipWhile$iv$iv.substring($i$f$substringWhile);
                        Intrinsics.checkNotNullExpressionValue(unitName, "this as java.lang.String).substring(startIndex)");
                        result = Duration.plus-LRDsOJo(result, toDuration(Double.parseDouble(unitName), unit));
                        if (index < length) {
                           throw new IllegalArgumentException("Fractional component must be last");
                        }
                     } else {
                        result = Duration.plus-LRDsOJo(result, toDuration(Long.parseLong($this$skipWhile$iv$iv), unit));
                     }
                  }
               }
            } else {
               ++index;
               if (index == length) {
                  throw new IllegalArgumentException();
               }

               String nonDigitSymbols = "+-.";
               boolean isTimeComponent = false;
               DurationUnit prevUnit = null;

               while(index < length) {
                  if (value.charAt(index) == 'T') {
                     if (isTimeComponent) {
                        throw new IllegalArgumentException();
                     }

                     ++index;
                     if (index == length) {
                        throw new IllegalArgumentException();
                     }

                     isTimeComponent = true;
                  } else {
                     int $i$f$substringWhile = 0;
                     String $this$skipWhile$iv$iv = value;
                     int $i$f$skipWhile = 0;

                     int i$iv$iv;
                     for(i$iv$iv = index; i$iv$iv < $this$skipWhile$iv$iv.length(); ++i$iv$iv) {
                        char it = $this$skipWhile$iv$iv.charAt(i$iv$iv);
                        int var20 = 0;
                        if (!('0' <= it ? it < ':' : false) && !StringsKt.contains$default((CharSequence)nonDigitSymbols, it, false, 2, (Object)null)) {
                           break;
                        }
                     }

                     String $i$f$skipWhile = value.substring(index, i$iv$iv);
                     Intrinsics.checkNotNullExpressionValue($i$f$skipWhile, "this as java.lang.String…ing(startIndex, endIndex)");
                     if (((CharSequence)$i$f$skipWhile).length() == 0) {
                        throw new IllegalArgumentException();
                     }

                     index += $i$f$skipWhile.length();
                     CharSequence unit = (CharSequence)value;
                     if (index < 0 || index > StringsKt.getLastIndex(unit)) {
                        int var41 = 0;
                        throw new IllegalArgumentException(Intrinsics.stringPlus("Missing unit for value ", $i$f$skipWhile));
                     }

                     char $this$substringWhile$iv = unit.charAt(index);
                     ++index;
                     DurationUnit i$iv = DurationUnitKt.durationUnitByIsoChar($this$substringWhile$iv, isTimeComponent);
                     if (prevUnit != null && prevUnit.compareTo(i$iv) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                     }

                     prevUnit = i$iv;
                     int dotIndex = StringsKt.indexOf$default((CharSequence)$i$f$skipWhile, '.', 0, false, 6, (Object)null);
                     if (i$iv == DurationUnit.SECONDS && dotIndex > 0) {
                        i$iv$iv = 0;
                        String it = $i$f$skipWhile.substring(i$iv$iv, dotIndex);
                        Intrinsics.checkNotNullExpressionValue(it, "this as java.lang.String…ing(startIndex, endIndex)");
                        result = Duration.plus-LRDsOJo(result, toDuration(parseOverLongIsoComponent(it), i$iv));
                        String unitName = $i$f$skipWhile.substring(dotIndex);
                        Intrinsics.checkNotNullExpressionValue(unitName, "this as java.lang.String).substring(startIndex)");
                        result = Duration.plus-LRDsOJo(result, toDuration(Double.parseDouble(unitName), i$iv));
                     } else {
                        result = Duration.plus-LRDsOJo(result, toDuration(parseOverLongIsoComponent($i$f$skipWhile), i$iv));
                     }
                  }
               }
            }

            return isNegative ? Duration.unaryMinus-UwyO8pc(result) : result;
         }
      }
   }

   private static final long parseOverLongIsoComponent(String value) {
      int length = value.length();
      int startIndex = 0;
      if (length > 0 && StringsKt.contains$default((CharSequence)"+-", value.charAt(0), false, 2, (Object)null)) {
         ++startIndex;
      }

      if (length - startIndex > 16) {
         Iterable $this$all$iv = new IntRange(startIndex, StringsKt.getLastIndex((CharSequence)value));
         int $i$f$all = 0;
         boolean var10000;
         if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
            var10000 = true;
         } else {
            label62: {
               Iterator var5 = $this$all$iv.iterator();

               while(var5.hasNext()) {
                  int element$iv = ((IntIterator)var5).nextInt();
                  int var8 = 0;
                  char var9 = value.charAt(element$iv);
                  if (!('0' <= var9 ? var9 < ':' : false)) {
                     var10000 = false;
                     break label62;
                  }
               }

               var10000 = true;
            }
         }

         if (var10000) {
            return value.charAt(0) == '-' ? Long.MIN_VALUE : Long.MAX_VALUE;
         }
      }

      return StringsKt.startsWith$default(value, "+", false, 2, (Object)null) ? Long.parseLong(StringsKt.drop(value, 1)) : Long.parseLong(value);
   }

   private static final String substringWhile(String $this$substringWhile, int startIndex, Function1 predicate) {
      int $i$f$substringWhile = 0;
      String $this$skipWhile$iv = $this$substringWhile;
      int $i$f$skipWhile = 0;

      int i$iv;
      for(i$iv = startIndex; i$iv < $this$skipWhile$iv.length() && (Boolean)predicate.invoke($this$skipWhile$iv.charAt(i$iv)); ++i$iv) {
      }

      String var9 = $this$substringWhile.substring(startIndex, i$iv);
      Intrinsics.checkNotNullExpressionValue(var9, "this as java.lang.String…ing(startIndex, endIndex)");
      return var9;
   }

   private static final int skipWhile(String $this$skipWhile, int startIndex, Function1 predicate) {
      int $i$f$skipWhile = 0;

      int i;
      for(i = startIndex; i < $this$skipWhile.length() && (Boolean)predicate.invoke($this$skipWhile.charAt(i)); ++i) {
      }

      return i;
   }

   private static final long nanosToMillis(long nanos) {
      return nanos / (long)1000000;
   }

   private static final long millisToNanos(long millis) {
      return millis * (long)1000000;
   }

   private static final long durationOfNanos(long normalNanos) {
      return Duration.constructor-impl(normalNanos << 1);
   }

   private static final long durationOfMillis(long normalMillis) {
      return Duration.constructor-impl((normalMillis << 1) + 1L);
   }

   private static final long durationOf(long normalValue, int unitDiscriminator) {
      return Duration.constructor-impl((normalValue << 1) + (long)unitDiscriminator);
   }

   private static final long durationOfNanosNormalized(long nanos) {
      return (-4611686018426999999L <= nanos ? nanos < 4611686018427000000L : false) ? durationOfNanos(nanos) : durationOfMillis(nanosToMillis(nanos));
   }

   private static final long durationOfMillisNormalized(long millis) {
      return (-4611686018426L <= millis ? millis < 4611686018427L : false) ? durationOfNanos(millisToNanos(millis)) : durationOfMillis(RangesKt.coerceIn(millis, -4611686018427387903L, 4611686018427387903L));
   }

   // $FF: synthetic method
   public static final long access$parseDuration(String value, boolean strictIso) {
      return parseDuration(value, strictIso);
   }

   // $FF: synthetic method
   public static final long access$durationOf(long normalValue, int unitDiscriminator) {
      return durationOf(normalValue, unitDiscriminator);
   }

   // $FF: synthetic method
   public static final long access$durationOfNanosNormalized(long nanos) {
      return durationOfNanosNormalized(nanos);
   }

   // $FF: synthetic method
   public static final long access$durationOfMillisNormalized(long millis) {
      return durationOfMillisNormalized(millis);
   }

   // $FF: synthetic method
   public static final long access$nanosToMillis(long nanos) {
      return nanosToMillis(nanos);
   }

   // $FF: synthetic method
   public static final long access$millisToNanos(long millis) {
      return millisToNanos(millis);
   }

   // $FF: synthetic method
   public static final long access$durationOfNanos(long normalNanos) {
      return durationOfNanos(normalNanos);
   }

   // $FF: synthetic method
   public static final long access$durationOfMillis(long normalMillis) {
      return durationOfMillis(normalMillis);
   }
}
