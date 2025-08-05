package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.collections.builders.MapBuilder;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000d\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a4\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007H\u0001\u001aQ\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\t\u001a\u00020\u00012#\u0010\n\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\u0081\bø\u0001\u0000\u001aI\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052#\u0010\n\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\u0081\bø\u0001\u0000\u001a \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0001\u001a(\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0001\u001a2\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013\u001aa\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00040\u00172*\u0010\u0018\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00130\u0019\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013H\u0007¢\u0006\u0002\u0010\u001a\u001aY\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u001b\"\u0004\b\u0001\u0010\u00052*\u0010\u0018\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00130\u0019\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0013¢\u0006\u0002\u0010\u001c\u001aC\u0010\u001d\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u001e2\u0006\u0010\u001f\u001a\u0002H\u00042\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00050!H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u0019\u0010#\u001a\u00020$*\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020%0\u0003H\u0087\b\u001a2\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0000\u001a1\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0081\b\u001a:\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u001b\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\u001a@\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0015\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u000e\u0010\u0016\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00040\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"},
   d2 = {"INT_MAX_POWER_OF_TWO", "", "build", "", "K", "V", "builder", "", "buildMapInternal", "capacity", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createMapBuilder", "mapCapacity", "expectedSize", "mapOf", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "comparator", "Ljava/util/Comparator;", "pairs", "", "(Ljava/util/Comparator;[Lkotlin/Pair;)Ljava/util/SortedMap;", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
   private static final int INT_MAX_POWER_OF_TWO = 1073741824;

   @NotNull
   public static final Map mapOf(@NotNull Pair pair) {
      Intrinsics.checkNotNullParameter(pair, "pair");
      Map var1 = Collections.singletonMap(pair.getFirst(), pair.getSecond());
      Intrinsics.checkNotNullExpressionValue(var1, "singletonMap(pair.first, pair.second)");
      return var1;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Map buildMapInternal(Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      Map var1 = MapsKt.createMapBuilder();
      builderAction.invoke(var1);
      return MapsKt.build(var1);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Map buildMapInternal(int capacity, Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      Map var2 = MapsKt.createMapBuilder(capacity);
      builderAction.invoke(var2);
      return MapsKt.build(var2);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Map createMapBuilder() {
      return new MapBuilder();
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Map createMapBuilder(int capacity) {
      return new MapBuilder(capacity);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Map build(@NotNull Map builder) {
      Intrinsics.checkNotNullParameter(builder, "builder");
      return ((MapBuilder)builder).build();
   }

   public static final Object getOrPut(@NotNull ConcurrentMap $this$getOrPut, Object key, @NotNull Function0 defaultValue) {
      Intrinsics.checkNotNullParameter($this$getOrPut, "<this>");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      int $i$f$getOrPut = 0;
      Object var4 = $this$getOrPut.get(key);
      Object var10000;
      if (var4 == null) {
         Object var6_1 = defaultValue.invoke();
         int var7 = 0;
         Object var8 = $this$getOrPut.putIfAbsent(key, var6_1);
         var10000 = var8 == null ? var6_1 : var8;
      } else {
         var10000 = var4;
      }

      return var10000;
   }

   @NotNull
   public static final SortedMap toSortedMap(@NotNull Map $this$toSortedMap) {
      Intrinsics.checkNotNullParameter($this$toSortedMap, "<this>");
      return (SortedMap)(new TreeMap($this$toSortedMap));
   }

   @NotNull
   public static final SortedMap toSortedMap(@NotNull Map $this$toSortedMap, @NotNull Comparator comparator) {
      Intrinsics.checkNotNullParameter($this$toSortedMap, "<this>");
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      TreeMap $this$toSortedMap_u24lambda_u2d1 = new TreeMap(comparator);
      int var4 = 0;
      $this$toSortedMap_u24lambda_u2d1.putAll($this$toSortedMap);
      return (SortedMap)$this$toSortedMap_u24lambda_u2d1;
   }

   @NotNull
   public static final SortedMap sortedMapOf(@NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      TreeMap $this$sortedMapOf_u24lambda_u2d2 = new TreeMap();
      int var3 = 0;
      MapsKt.putAll((Map)$this$sortedMapOf_u24lambda_u2d2, pairs);
      return (SortedMap)$this$sortedMapOf_u24lambda_u2d2;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @NotNull
   public static final SortedMap sortedMapOf(@NotNull Comparator comparator, @NotNull Pair... pairs) {
      Intrinsics.checkNotNullParameter(comparator, "comparator");
      Intrinsics.checkNotNullParameter(pairs, "pairs");
      TreeMap $this$sortedMapOf_u24lambda_u2d3 = new TreeMap(comparator);
      int var4 = 0;
      MapsKt.putAll((Map)$this$sortedMapOf_u24lambda_u2d3, pairs);
      return (SortedMap)$this$sortedMapOf_u24lambda_u2d3;
   }

   @InlineOnly
   private static final Properties toProperties(Map $this$toProperties) {
      Intrinsics.checkNotNullParameter($this$toProperties, "<this>");
      Properties $this$toProperties_u24lambda_u2d4 = new Properties();
      int var3 = 0;
      $this$toProperties_u24lambda_u2d4.putAll($this$toProperties);
      return $this$toProperties_u24lambda_u2d4;
   }

   @InlineOnly
   private static final Map toSingletonMapOrSelf(Map $this$toSingletonMapOrSelf) {
      Intrinsics.checkNotNullParameter($this$toSingletonMapOrSelf, "<this>");
      return MapsKt.toSingletonMap($this$toSingletonMapOrSelf);
   }

   @NotNull
   public static final Map toSingletonMap(@NotNull Map $this$toSingletonMap) {
      Intrinsics.checkNotNullParameter($this$toSingletonMap, "<this>");
      Object var2 = $this$toSingletonMap.entrySet().iterator().next();
      Map.Entry $this$toSingletonMap_u24lambda_u2d5 = (Map.Entry)var2;
      int var4 = 0;
      Map var1 = Collections.singletonMap($this$toSingletonMap_u24lambda_u2d5.getKey(), $this$toSingletonMap_u24lambda_u2d5.getValue());
      Intrinsics.checkNotNullExpressionValue(var1, "with(entries.iterator().…ingletonMap(key, value) }");
      return var1;
   }

   @PublishedApi
   public static final int mapCapacity(int expectedSize) {
      return expectedSize < 0 ? expectedSize : (expectedSize < 3 ? expectedSize + 1 : (expectedSize < 1073741824 ? (int)((float)expectedSize / 0.75F + 1.0F) : Integer.MAX_VALUE));
   }

   public MapsKt__MapsJVMKt() {
   }
}
