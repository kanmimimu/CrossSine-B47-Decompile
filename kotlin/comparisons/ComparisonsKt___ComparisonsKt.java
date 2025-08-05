package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u0018\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\u001aG\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\b\u001a?\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\t\u001aK\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\u000b\"\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\f\u001aG\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\b\u001a?\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\t\u001aK\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00010\u000b\"\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\f¨\u0006\u000e"},
   d2 = {"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "other", "", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt___ComparisonsKt extends ComparisonsKt___ComparisonsJvmKt {
   @SinceKotlin(
      version = "1.1"
   )
   public static final Object maxOf(Object a, Object b, Object c, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return ComparisonsKt.maxOf(a, ComparisonsKt.maxOf(b, c, comparator), comparator);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final Object maxOf(Object a, Object b, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return comparator.compare(a, b) >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final Object maxOf(Object a, @NotNull Object[] other, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(other, "other");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Object max = a;
      Object[] var4 = other;
      int var5 = 0;
      int var6 = other.length;

      while(var5 < var6) {
         Object e = var4[var5];
         ++var5;
         if (comparator.compare(max, e) < 0) {
            max = e;
         }
      }

      return max;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final Object minOf(Object a, Object b, Object c, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return ComparisonsKt.minOf(a, ComparisonsKt.minOf(b, c, comparator), comparator);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public static final Object minOf(Object a, Object b, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return comparator.compare(a, b) <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.4"
   )
   public static final Object minOf(Object a, @NotNull Object[] other, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter(other, "other");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Object min = a;
      Object[] var4 = other;
      int var5 = 0;
      int var6 = other.length;

      while(var5 < var6) {
         Object e = var4[var5];
         ++var5;
         if (comparator.compare(min, e) > 0) {
            min = e;
         }
      }

      return min;
   }

   public ComparisonsKt___ComparisonsKt() {
   }
}
