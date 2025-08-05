package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.BuilderInference;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u0088\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aC\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\bø\u0001\u0000\u001aC\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\bø\u0001\u0000\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u0019\u001aN\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e¢\u0006\u0002\b\u001fH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aF\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e¢\u0006\u0002\b\u001fH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u0012\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\"\u001a%\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\b\u0010%\u001a\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010&\u001a3\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010\"\u001a\u0015\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\"\u001a%\u0010(\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002¢\u0006\u0002\b+\u001a\b\u0010,\u001a\u00020\u001eH\u0001\u001a\b\u0010-\u001a\u00020\u001eH\u0001\u001a%\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000¢\u0006\u0002\u0010/\u001aS\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010%\u001a\u0002H\u00072\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000702j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`32\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006¢\u0006\u0002\u00104\u001a>\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u00100\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000706*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010%\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006¢\u0006\u0002\u00107\u001ag\u00108\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00109*\b\u0012\u0004\u0012\u0002H906*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u0010:\u001a\u0004\u0018\u0001H92\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0016\b\u0004\u0010;\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H90\u000eH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010<\u001a,\u0010=\u001a\u00020>\"\t\b\u0000\u0010\u0007¢\u0006\u0002\b?*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a;\u0010@\u001a\u0002HA\"\u0010\b\u0000\u0010B*\u0006\u0012\u0002\b\u00030\u0002*\u0002HA\"\u0004\b\u0001\u0010A*\u0002HB2\f\u0010C\u001a\b\u0012\u0004\u0012\u0002HA0DH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010E\u001a\u0019\u0010F\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010G\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001e\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\u001a&\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070K2\u0006\u0010L\u001a\u00020MH\u0007\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006N"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "buildList", "E", "capacity", "builderAction", "", "Lkotlin/ExtensionFunctionType;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "shuffled", "", "random", "Lkotlin/random/Random;", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
   @NotNull
   public static final Collection asCollection(@NotNull Object[] $this$asCollection) {
      Intrinsics.checkNotNullParameter($this$asCollection, "<this>");
      return new ArrayAsCollection($this$asCollection, false);
   }

   @NotNull
   public static final List emptyList() {
      return EmptyList.INSTANCE;
   }

   @NotNull
   public static final List listOf(@NotNull Object... elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      return elements.length > 0 ? ArraysKt.asList(elements) : CollectionsKt.emptyList();
   }

   @InlineOnly
   private static final List listOf() {
      return CollectionsKt.emptyList();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List mutableListOf() {
      return (List)(new ArrayList());
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final ArrayList arrayListOf() {
      return new ArrayList();
   }

   @NotNull
   public static final List mutableListOf(@NotNull Object... elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      return elements.length == 0 ? (List)(new ArrayList()) : (List)(new ArrayList(new ArrayAsCollection(elements, true)));
   }

   @NotNull
   public static final ArrayList arrayListOf(@NotNull Object... elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      return elements.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(elements, true));
   }

   @NotNull
   public static final List listOfNotNull(@Nullable Object element) {
      return element != null ? CollectionsKt.listOf(element) : CollectionsKt.emptyList();
   }

   @NotNull
   public static final List listOfNotNull(@NotNull Object... elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      return ArraysKt.filterNotNull(elements);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List List(int size, Function1 init) {
      Intrinsics.checkNotNullParameter(init, "init");
      ArrayList var2 = new ArrayList(size);
      int var3 = 0;

      while(var3 < size) {
         int var4 = var3++;
         var2.add(init.invoke(var4));
      }

      return (List)var2;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List MutableList(int size, Function1 init) {
      Intrinsics.checkNotNullParameter(init, "init");
      ArrayList list = new ArrayList(size);
      int var3 = 0;

      while(var3 < size) {
         int index = var3++;
         int var6 = 0;
         list.add(init.invoke(index));
      }

      return (List)list;
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final List buildList(@BuilderInference Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      List var1 = CollectionsKt.createListBuilder();
      builderAction.invoke(var1);
      return CollectionsKt.build(var1);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final List buildList(int capacity, @BuilderInference Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      List var2 = CollectionsKt.createListBuilder(capacity);
      builderAction.invoke(var2);
      return CollectionsKt.build(var2);
   }

   @NotNull
   public static final IntRange getIndices(@NotNull Collection $this$indices) {
      Intrinsics.checkNotNullParameter($this$indices, "<this>");
      return new IntRange(0, $this$indices.size() - 1);
   }

   public static final int getLastIndex(@NotNull List $this$lastIndex) {
      Intrinsics.checkNotNullParameter($this$lastIndex, "<this>");
      return $this$lastIndex.size() - 1;
   }

   @InlineOnly
   private static final boolean isNotEmpty(Collection $this$isNotEmpty) {
      Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
      return !$this$isNotEmpty.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean isNullOrEmpty(Collection $this$isNullOrEmpty) {
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
   }

   @InlineOnly
   private static final Collection orEmpty(Collection $this$orEmpty) {
      return $this$orEmpty == null ? (Collection)CollectionsKt.emptyList() : $this$orEmpty;
   }

   @InlineOnly
   private static final List orEmpty(List $this$orEmpty) {
      return $this$orEmpty == null ? CollectionsKt.emptyList() : $this$orEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(Collection $this$ifEmpty, Function0 defaultValue) {
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      return $this$ifEmpty.isEmpty() ? defaultValue.invoke() : $this$ifEmpty;
   }

   @InlineOnly
   private static final boolean containsAll(Collection $this$containsAll, Collection elements) {
      Intrinsics.checkNotNullParameter($this$containsAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$containsAll.containsAll(elements);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable $this$shuffled, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$shuffled, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      List $this$shuffled_u24lambda_u2d4 = CollectionsKt.toMutableList($this$shuffled);
      int var4 = 0;
      CollectionsKt.shuffle($this$shuffled_u24lambda_u2d4, random);
      return $this$shuffled_u24lambda_u2d4;
   }

   @NotNull
   public static final List optimizeReadOnlyList(@NotNull List $this$optimizeReadOnlyList) {
      Intrinsics.checkNotNullParameter($this$optimizeReadOnlyList, "<this>");
      int var1 = $this$optimizeReadOnlyList.size();
      List var10000;
      switch (var1) {
         case 0:
            var10000 = CollectionsKt.emptyList();
            break;
         case 1:
            var10000 = CollectionsKt.listOf($this$optimizeReadOnlyList.get(0));
            break;
         default:
            var10000 = $this$optimizeReadOnlyList;
      }

      return var10000;
   }

   public static final int binarySearch(@NotNull List $this$binarySearch, @Nullable Comparable element, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Comparable midVal = (Comparable)$this$binarySearch.get(mid);
         int cmp = ComparisonsKt.compareValues(midVal, element);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Comparable var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   public static final int binarySearch(@NotNull List $this$binarySearch, Object element, @NotNull Comparator comparator, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Object midVal = $this$binarySearch.get(mid);
         int cmp = comparator.compare(midVal, element);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Object var1, Comparator var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3, var4);
   }

   public static final int binarySearchBy(@NotNull List $this$binarySearchBy, @Nullable Comparable key, int fromIndex, int toIndex, @NotNull Function1 selector) {
      Intrinsics.checkNotNullParameter($this$binarySearchBy, "<this>");
      Intrinsics.checkNotNullParameter(selector, "selector");
      int $i$f$binarySearchBy = 0;
      return CollectionsKt.binarySearch($this$binarySearchBy, fromIndex, toIndex, new Function1() {
         @NotNull
         public final Integer invoke(Object it) {
            return ComparisonsKt.compareValues((Comparable)selector.invoke(it), key);
         }
      });
   }

   // $FF: synthetic method
   public static int binarySearchBy$default(List $this$binarySearchBy_u24default, final Comparable key, int fromIndex, int toIndex, final Function1 selector, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         fromIndex = 0;
      }

      if ((var5 & 4) != 0) {
         toIndex = $this$binarySearchBy_u24default.size();
      }

      int $i$f$binarySearchBy = 0;
      return CollectionsKt.binarySearch($this$binarySearchBy_u24default, fromIndex, toIndex, new Function1() {
         @NotNull
         public final Integer invoke(Object it) {
            return ComparisonsKt.compareValues((Comparable)selector.invoke(it), key);
         }
      });
   }

   public static final int binarySearch(@NotNull List $this$binarySearch, int fromIndex, int toIndex, @NotNull Function1 comparison) {
      Intrinsics.checkNotNullParameter($this$binarySearch, "<this>");
      Intrinsics.checkNotNullParameter(comparison, "comparison");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Object midVal = $this$binarySearch.get(mid);
         int cmp = ((Number)comparison.invoke(midVal)).intValue();
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, int var1, int var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   private static final void rangeCheck$CollectionsKt__CollectionsKt(int size, int fromIndex, int toIndex) {
      if (fromIndex > toIndex) {
         throw new IllegalArgumentException("fromIndex (" + fromIndex + ") is greater than toIndex (" + toIndex + ").");
      } else if (fromIndex < 0) {
         throw new IndexOutOfBoundsException("fromIndex (" + fromIndex + ") is less than zero.");
      } else if (toIndex > size) {
         throw new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ").");
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwIndexOverflow() {
      throw new ArithmeticException("Index overflow has happened.");
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwCountOverflow() {
      throw new ArithmeticException("Count overflow has happened.");
   }

   public CollectionsKt__CollectionsKt() {
   }
}
