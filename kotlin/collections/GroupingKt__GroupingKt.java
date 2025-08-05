package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009e\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\bø\u0001\u0000\u001a·\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0016\u001a¿\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\bø\u0001\u0000\u001a\u007f\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001aØ\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0093\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u001a\u008b\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\bø\u0001\u0000\u001a¤\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010#\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006$"},
   d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map aggregate(@NotNull Grouping $this$aggregate, @NotNull Function4 operation) {
      Intrinsics.checkNotNullParameter($this$aggregate, "<this>");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$aggregate = 0;
      Grouping $this$aggregateTo$iv = $this$aggregate;
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = 0;
      Iterator var6 = $this$aggregate.sourceIterator();
      Iterator var7 = var6;

      while(var7.hasNext()) {
         Object e$iv = var7.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination$iv.get(key$iv);
         Object var11 = operation.invoke(key$iv, accumulator$iv, e$iv, accumulator$iv == null && !destination$iv.containsKey(key$iv));
         destination$iv.put(key$iv, var11);
      }

      return destination$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map aggregateTo(@NotNull Grouping $this$aggregateTo, @NotNull Map destination, @NotNull Function4 operation) {
      Intrinsics.checkNotNullParameter($this$aggregateTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$aggregateTo = 0;
      Iterator var5 = $this$aggregateTo.sourceIterator();
      Iterator var4 = var5;

      while(var4.hasNext()) {
         Object e = var4.next();
         Object key = $this$aggregateTo.keyOf(e);
         Object accumulator = destination.get(key);
         Object var9 = operation.invoke(key, accumulator, e, accumulator == null && !destination.containsKey(key));
         destination.put(key, var9);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map fold(@NotNull Grouping $this$fold, @NotNull Function2 initialValueSelector, @NotNull Function3 operation) {
      Intrinsics.checkNotNullParameter($this$fold, "<this>");
      Intrinsics.checkNotNullParameter(initialValueSelector, "initialValueSelector");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$fold = 0;
      int $i$f$aggregate = 0;
      Grouping $this$aggregateTo$iv$iv = $this$fold;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = 0;
      Iterator var9 = $this$fold.sourceIterator();
      Iterator var10 = var9;

      while(var10.hasNext()) {
         Object e$iv$iv = var10.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var18 = 0;
         Object key = operation.invoke(key$iv$iv, first ? initialValueSelector.invoke(key$iv$iv, e$iv$iv) : accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, key);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map foldTo(@NotNull Grouping $this$foldTo, @NotNull Map destination, @NotNull Function2 initialValueSelector, @NotNull Function3 operation) {
      Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(initialValueSelector, "initialValueSelector");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$foldTo = 0;
      Grouping $this$aggregateTo$iv = $this$foldTo;
      int $i$f$aggregateTo = 0;
      Iterator var7 = $this$foldTo.sourceIterator();
      Iterator var8 = var7;

      while(var8.hasNext()) {
         Object e$iv = var8.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var16 = 0;
         Object key = operation.invoke(key$iv, first ? initialValueSelector.invoke(key$iv, e$iv) : accumulator$iv, e$iv);
         destination.put(key$iv, key);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map fold(@NotNull Grouping $this$fold, Object initialValue, @NotNull Function2 operation) {
      Intrinsics.checkNotNullParameter($this$fold, "<this>");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$fold = 0;
      int $i$f$aggregate = 0;
      Grouping $this$aggregateTo$iv$iv = $this$fold;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = 0;
      Iterator var9 = $this$fold.sourceIterator();
      Iterator var10 = var9;

      while(var10.hasNext()) {
         Object e$iv$iv = var10.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var18 = 0;
         Object $noName_0 = operation.invoke(first ? initialValue : accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, $noName_0);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map foldTo(@NotNull Grouping $this$foldTo, @NotNull Map destination, Object initialValue, @NotNull Function2 operation) {
      Intrinsics.checkNotNullParameter($this$foldTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$foldTo = 0;
      Grouping $this$aggregateTo$iv = $this$foldTo;
      int $i$f$aggregateTo = 0;
      Iterator var7 = $this$foldTo.sourceIterator();
      Iterator var8 = var7;

      while(var8.hasNext()) {
         Object e$iv = var8.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var16 = 0;
         Object $noName_0 = operation.invoke(first ? initialValue : accumulator$iv, e$iv);
         destination.put(key$iv, $noName_0);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map reduce(@NotNull Grouping $this$reduce, @NotNull Function3 operation) {
      Intrinsics.checkNotNullParameter($this$reduce, "<this>");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$reduce = 0;
      int $i$f$aggregate = 0;
      Grouping $this$aggregateTo$iv$iv = $this$reduce;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = 0;
      Iterator var8 = $this$reduce.sourceIterator();
      Iterator var9 = var8;

      while(var9.hasNext()) {
         Object e$iv$iv = var9.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var17 = 0;
         Object key = first ? e$iv$iv : operation.invoke(key$iv$iv, accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, key);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map reduceTo(@NotNull Grouping $this$reduceTo, @NotNull Map destination, @NotNull Function3 operation) {
      Intrinsics.checkNotNullParameter($this$reduceTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Intrinsics.checkNotNullParameter(operation, "operation");
      int $i$f$reduceTo = 0;
      Grouping $this$aggregateTo$iv = $this$reduceTo;
      int $i$f$aggregateTo = 0;
      Iterator var6 = $this$reduceTo.sourceIterator();
      Iterator var7 = var6;

      while(var7.hasNext()) {
         Object e$iv = var7.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var15 = 0;
         Object key = first ? e$iv : operation.invoke(key$iv, accumulator$iv, e$iv);
         destination.put(key$iv, key);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map eachCountTo(@NotNull Grouping $this$eachCountTo, @NotNull Map destination) {
      Intrinsics.checkNotNullParameter($this$eachCountTo, "<this>");
      Intrinsics.checkNotNullParameter(destination, "destination");
      Object initialValue$iv = 0;
      int $i$f$foldTo = 0;
      Grouping $this$aggregateTo$iv$iv = $this$eachCountTo;
      int $i$f$aggregateTo = 0;
      Iterator var7 = $this$eachCountTo.sourceIterator();
      Iterator var8 = var7;

      while(var8.hasNext()) {
         Object e$iv$iv = var8.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination.get(key$iv$iv);
         boolean first$iv = accumulator$iv$iv == null && !destination.containsKey(key$iv$iv);
         int var16 = 0;
         int acc = ((Number)(first$iv ? initialValue$iv : accumulator$iv$iv)).intValue();
         int var19 = 0;
         Integer $noName_0$iv = acc + 1;
         destination.put(key$iv$iv, $noName_0$iv);
      }

      return destination;
   }

   public GroupingKt__GroupingKt() {
   }
}
