package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a>\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\bø\u0001\u0000\u001aY\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\t\u001aZ\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\bø\u0001\u0000\u001a>\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\bø\u0001\u0000\u001aZ\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\bø\u0001\u0000\u001a-\u0010\r\u001a\u00020\u000e\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0010\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0011\u001aA\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aY\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0014\u001a]\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aG\u0010\u0016\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022 \u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\bH\u0002¢\u0006\u0004\b\u0017\u0010\u0014\u001a&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a-\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a-\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a0\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\u001aO\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004\u001aR\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\bø\u0001\u0000\u001an\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\bø\u0001\u0000\u001aR\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\bø\u0001\u0000\u001an\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\bø\u0001\u0000\u001ap\u0010!\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u000328\b\u0004\u0010\"\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000e0#H\u0087\bø\u0001\u0000\u001aO\u0010&\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006'"},
   d2 = {"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt__ComparisonsKt {
   public static final int compareValuesBy(Object a, Object b, @NotNull Function1... selectors) {
      Intrinsics.checkNotNullParameter(selectors, "selectors");
      boolean var3 = selectors.length > 0;
      if (!var3) {
         String var4 = "Failed requirement.";
         throw new IllegalArgumentException(var4.toString());
      } else {
         return compareValuesByImpl$ComparisonsKt__ComparisonsKt(a, b, selectors);
      }
   }

   private static final int compareValuesByImpl$ComparisonsKt__ComparisonsKt(Object a, Object b, Function1[] selectors) {
      Function1[] var3 = selectors;
      int var4 = 0;
      int var5 = selectors.length;

      while(var4 < var5) {
         Function1 fn = var3[var4];
         ++var4;
         Comparable v1 = (Comparable)fn.invoke(a);
         Comparable v2 = (Comparable)fn.invoke(b);
         int diff = ComparisonsKt.compareValues(v1, v2);
         if (diff != 0) {
            return diff;
         }
      }

      return 0;
   }

   @InlineOnly
   private static final int compareValuesBy(Object a, Object b, Function1 selector) {
      Intrinsics.checkNotNullParameter(selector, "selector");
      return ComparisonsKt.compareValues((Comparable)selector.invoke(a), (Comparable)selector.invoke(b));
   }

   @InlineOnly
   private static final int compareValuesBy(Object a, Object b, Comparator comparator, Function1 selector) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return comparator.compare(selector.invoke(a), selector.invoke(b));
   }

   public static final int compareValues(@Nullable Comparable a, @Nullable Comparable b) {
      if (a == b) {
         return 0;
      } else if (a == null) {
         return -1;
      } else {
         return b == null ? 1 : a.compareTo(b);
      }
   }

   @NotNull
   public static final Comparator compareBy(@NotNull final Function1... selectors) {
      Intrinsics.checkNotNullParameter(selectors, "selectors");
      boolean var1 = selectors.length > 0;
      if (!var1) {
         String var2 = "Failed requirement.";
         throw new IllegalArgumentException(var2.toString());
      } else {
         return new Comparator() {
            public final int compare(Object a, Object b) {
               return ComparisonsKt__ComparisonsKt.compareValuesByImpl$ComparisonsKt__ComparisonsKt(a, b, selectors);
            }
         };
      }
   }

   @InlineOnly
   private static final Comparator compareBy(final Function1 selector) {
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            Function1 var3 = selector;
            return ComparisonsKt.compareValues((Comparable)var3.invoke(a), (Comparable)var3.invoke(b));
         }
      };
   }

   @InlineOnly
   private static final Comparator compareBy(final Comparator comparator, final Function1 selector) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            Comparator var3 = comparator;
            Function1 var4 = selector;
            return var3.compare(var4.invoke(a), var4.invoke(b));
         }
      };
   }

   @InlineOnly
   private static final Comparator compareByDescending(final Function1 selector) {
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            Function1 var3 = selector;
            return ComparisonsKt.compareValues((Comparable)var3.invoke(b), (Comparable)var3.invoke(a));
         }
      };
   }

   @InlineOnly
   private static final Comparator compareByDescending(final Comparator comparator, final Function1 selector) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            Comparator var3 = comparator;
            Function1 var4 = selector;
            return var3.compare(var4.invoke(b), var4.invoke(a));
         }
      };
   }

   @InlineOnly
   private static final Comparator thenBy(final Comparator $this$thenBy, final Function1 selector) {
      Intrinsics.checkNotNullParameter($this$thenBy, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenBy.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Function1 var4 = selector;
               var10000 = ComparisonsKt.compareValues((Comparable)var4.invoke(a), (Comparable)var4.invoke(b));
            }

            return var10000;
         }
      };
   }

   @InlineOnly
   private static final Comparator thenBy(final Comparator $this$thenBy, final Comparator comparator, final Function1 selector) {
      Intrinsics.checkNotNullParameter($this$thenBy, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenBy.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Comparator var4 = comparator;
               Function1 var5 = selector;
               var10000 = var4.compare(var5.invoke(a), var5.invoke(b));
            }

            return var10000;
         }
      };
   }

   @InlineOnly
   private static final Comparator thenByDescending(final Comparator $this$thenByDescending, final Function1 selector) {
      Intrinsics.checkNotNullParameter($this$thenByDescending, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenByDescending.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Function1 var4 = selector;
               var10000 = ComparisonsKt.compareValues((Comparable)var4.invoke(b), (Comparable)var4.invoke(a));
            }

            return var10000;
         }
      };
   }

   @InlineOnly
   private static final Comparator thenByDescending(final Comparator $this$thenByDescending, final Comparator comparator, final Function1 selector) {
      Intrinsics.checkNotNullParameter($this$thenByDescending, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(selector, "selector");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenByDescending.compare(a, b);
            int var10000;
            if (previousCompare != 0) {
               var10000 = previousCompare;
            } else {
               Comparator var4 = comparator;
               Function1 var5 = selector;
               var10000 = var4.compare(var5.invoke(b), var5.invoke(a));
            }

            return var10000;
         }
      };
   }

   @InlineOnly
   private static final Comparator thenComparator(final Comparator $this$thenComparator, final Function2 comparison) {
      Intrinsics.checkNotNullParameter($this$thenComparator, "<this>");
      Intrinsics.checkNotNullParameter(comparison, "comparison");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenComparator.compare(a, b);
            return previousCompare != 0 ? previousCompare : ((Number)comparison.invoke(a, b)).intValue();
         }
      };
   }

   @NotNull
   public static final Comparator then(@NotNull final Comparator $this$then, @NotNull final Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$then, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$then.compare(a, b);
            return previousCompare != 0 ? previousCompare : comparator.compare(a, b);
         }
      };
   }

   @NotNull
   public static final Comparator thenDescending(@NotNull final Comparator $this$thenDescending, @NotNull final Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$thenDescending, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return new Comparator() {
         public final int compare(Object a, Object b) {
            int previousCompare = $this$thenDescending.compare(a, b);
            return previousCompare != 0 ? previousCompare : comparator.compare(b, a);
         }
      };
   }

   @NotNull
   public static final Comparator nullsFirst(@NotNull final Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return new Comparator() {
         public final int compare(@Nullable Object a, @Nullable Object b) {
            return a == b ? 0 : (a == null ? -1 : (b == null ? 1 : comparator.compare(a, b)));
         }
      };
   }

   @InlineOnly
   private static final Comparator nullsFirst() {
      return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final Comparator nullsLast(@NotNull final Comparator comparator) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      return new Comparator() {
         public final int compare(@Nullable Object a, @Nullable Object b) {
            return a == b ? 0 : (a == null ? 1 : (b == null ? -1 : comparator.compare(a, b)));
         }
      };
   }

   @InlineOnly
   private static final Comparator nullsLast() {
      return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final Comparator naturalOrder() {
      return NaturalOrderComparator.INSTANCE;
   }

   @NotNull
   public static final Comparator reverseOrder() {
      return ReverseOrderComparator.INSTANCE;
   }

   @NotNull
   public static final Comparator reversed(@NotNull Comparator $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return $this$reversed instanceof ReversedComparator ? ((ReversedComparator)$this$reversed).getComparator() : (Intrinsics.areEqual((Object)$this$reversed, (Object)NaturalOrderComparator.INSTANCE) ? (Comparator)ReverseOrderComparator.INSTANCE : (Intrinsics.areEqual((Object)$this$reversed, (Object)ReverseOrderComparator.INSTANCE) ? (Comparator)NaturalOrderComparator.INSTANCE : (Comparator)(new ReversedComparator($this$reversed))));
   }

   public ComparisonsKt__ComparisonsKt() {
   }
}
