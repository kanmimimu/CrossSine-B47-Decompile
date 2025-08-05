package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aZ\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"},
   d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map eachCount(@NotNull Grouping $this$eachCount) {
      Intrinsics.checkNotNullParameter($this$eachCount, "<this>");
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$foldTo = 0;
      Grouping $this$aggregateTo$iv$iv = $this$eachCount;
      int $i$f$aggregateTo = 0;
      Iterator var6 = $this$eachCount.sourceIterator();
      Iterator var7 = var6;

      while(var7.hasNext()) {
         Object e$iv$iv = var7.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv.get(key$iv$iv);
         boolean first$iv = accumulator$iv$iv == null && !destination$iv.containsKey(key$iv$iv);
         int var15 = 0;
         Object var10001;
         if (first$iv) {
            int var18 = 0;
            Ref.IntRef var25 = new Ref.IntRef();
            var10001 = var25;
         } else {
            var10001 = accumulator$iv$iv;
         }

         Ref.IntRef $noName_1 = (Ref.IntRef)var10001;
         int var19 = 0;
         int var22 = 0;
         ++$noName_1.element;
         destination$iv.put(key$iv$iv, $noName_1);
      }

      Map $this$foldTo$iv = destination$iv;

      for(Object var28 : (Iterable)destination$iv.entrySet()) {
         Map.Entry var29 = (Map.Entry)var28;
         int var31 = 0;
         Integer var32 = ((Ref.IntRef)var29.getValue()).element;
         var29.setValue(var32);
      }

      return $this$foldTo$iv;
   }

   @PublishedApi
   @InlineOnly
   private static final Map mapValuesInPlace(Map $this$mapValuesInPlace, Function1 f) {
      Intrinsics.checkNotNullParameter($this$mapValuesInPlace, "<this>");
      Intrinsics.checkNotNullParameter(f, "f");
      Iterable $this$forEach$iv = (Iterable)$this$mapValuesInPlace.entrySet();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Map.Entry it = (Map.Entry)element$iv;
         int var7 = 0;
         it.setValue(f.invoke(it));
      }

      return $this$mapValuesInPlace;
   }

   public GroupingKt__GroupingJVMKt() {
   }
}
