package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a3\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\bø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a3\u0010\u0000\u001a\u00020\u0001*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\n\u001a7\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\bø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u009920\u0001¨\u0006\u000b"},
   d2 = {"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)J", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/TimeSource;", "(Lkotlin/time/TimeSource;Lkotlin/jvm/functions/Function0;)J", "kotlin-stdlib"}
)
public final class MeasureTimeKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static final long measureTime(@NotNull Function0 block) {
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$measureTime = 0;
      TimeSource $this$measureTime$iv = TimeSource.Monotonic.INSTANCE;
      int $i$f$measureTime = 0;
      TimeMark mark$iv = $this$measureTime$iv.markNow();
      block.invoke();
      return mark$iv.elapsedNow-UwyO8pc();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static final long measureTime(@NotNull TimeSource $this$measureTime, @NotNull Function0 block) {
      Intrinsics.checkNotNullParameter($this$measureTime, "<this>");
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$measureTime = 0;
      TimeMark mark = $this$measureTime.markNow();
      block.invoke();
      return mark.elapsedNow-UwyO8pc();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   @NotNull
   public static final TimedValue measureTimedValue(@NotNull Function0 block) {
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$measureTimedValue = 0;
      TimeSource $this$measureTimedValue$iv = TimeSource.Monotonic.INSTANCE;
      int $i$f$measureTimedValue = 0;
      TimeMark mark$iv = $this$measureTimedValue$iv.markNow();
      Object result$iv = block.invoke();
      return new TimedValue(result$iv, mark$iv.elapsedNow-UwyO8pc(), (DefaultConstructorMarker)null);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   @NotNull
   public static final TimedValue measureTimedValue(@NotNull TimeSource $this$measureTimedValue, @NotNull Function0 block) {
      Intrinsics.checkNotNullParameter($this$measureTimedValue, "<this>");
      Intrinsics.checkNotNullParameter(block, "block");
      int $i$f$measureTimedValue = 0;
      TimeMark mark = $this$measureTimedValue.markNow();
      Object result = block.invoke();
      return new TimedValue(result, mark.elapsedNow-UwyO8pc(), (DefaultConstructorMarker)null);
   }
}
