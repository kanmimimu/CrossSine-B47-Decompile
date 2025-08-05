package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0001¨\u0006\t"},
   d2 = {"durationUnitByIsoChar", "Lkotlin/time/DurationUnit;", "isoChar", "", "isTimeComponent", "", "durationUnitByShortName", "shortName", "", "kotlin-stdlib"},
   xs = "kotlin/time/DurationUnitKt"
)
class DurationUnitKt__DurationUnitKt extends DurationUnitKt__DurationUnitJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final String shortName(@NotNull DurationUnit $this$shortName) {
      Intrinsics.checkNotNullParameter($this$shortName, "<this>");
      int var2 = DurationUnitKt__DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[$this$shortName.ordinal()];
      String var10000;
      switch (var2) {
         case 1:
            var10000 = "ns";
            break;
         case 2:
            var10000 = "us";
            break;
         case 3:
            var10000 = "ms";
            break;
         case 4:
            var10000 = "s";
            break;
         case 5:
            var10000 = "m";
            break;
         case 6:
            var10000 = "h";
            break;
         case 7:
            var10000 = "d";
            break;
         default:
            throw new IllegalStateException(Intrinsics.stringPlus("Unknown unit: ", $this$shortName).toString());
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @NotNull
   public static final DurationUnit durationUnitByShortName(@NotNull String shortName) {
      Intrinsics.checkNotNullParameter(shortName, "shortName");
      DurationUnit var10000;
      switch (shortName.hashCode()) {
         case 100:
            if (!shortName.equals("d")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.DAYS;
            break;
         case 104:
            if (!shortName.equals("h")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.HOURS;
            break;
         case 109:
            if (!shortName.equals("m")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.MINUTES;
            break;
         case 115:
            if (!shortName.equals("s")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.SECONDS;
            break;
         case 3494:
            if (!shortName.equals("ms")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.MILLISECONDS;
            break;
         case 3525:
            if (!shortName.equals("ns")) {
               throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
            }

            var10000 = DurationUnit.NANOSECONDS;
            break;
         case 3742:
            if (shortName.equals("us")) {
               var10000 = DurationUnit.MICROSECONDS;
               break;
            }

            throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
         default:
            throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @NotNull
   public static final DurationUnit durationUnitByIsoChar(char isoChar, boolean isTimeComponent) {
      DurationUnit var10000;
      if (!isTimeComponent) {
         if (isoChar != 'D') {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Invalid or unsupported duration ISO non-time unit: ", isoChar));
         }

         var10000 = DurationUnit.DAYS;
      } else if (isoChar == 'H') {
         var10000 = DurationUnit.HOURS;
      } else if (isoChar == 'M') {
         var10000 = DurationUnit.MINUTES;
      } else {
         if (isoChar != 'S') {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Invalid duration ISO time unit: ", isoChar));
         }

         var10000 = DurationUnit.SECONDS;
      }

      return var10000;
   }

   public DurationUnitKt__DurationUnitKt() {
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[DurationUnit.values().length];
         var0[DurationUnit.NANOSECONDS.ordinal()] = 1;
         var0[DurationUnit.MICROSECONDS.ordinal()] = 2;
         var0[DurationUnit.MILLISECONDS.ordinal()] = 3;
         var0[DurationUnit.SECONDS.ordinal()] = 4;
         var0[DurationUnit.MINUTES.ordinal()] = 5;
         var0[DurationUnit.HOURS.ordinal()] = 6;
         var0[DurationUnit.DAYS.ordinal()] = 7;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
