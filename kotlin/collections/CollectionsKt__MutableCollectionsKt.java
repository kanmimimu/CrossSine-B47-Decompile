package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\t\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0087\b¢\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u001d\u0010\u001e\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010 \u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001d\u0010!\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a\u001f\u0010\"\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a-\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010#\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010$\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0002\b%¨\u0006&"},
   d2 = {"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "removeFirst", "(Ljava/util/List;)Ljava/lang/Object;", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
   @InlineOnly
   private static final boolean remove(Collection $this$remove, Object element) {
      Intrinsics.checkNotNullParameter($this$remove, "<this>");
      return $this$remove.remove(element);
   }

   @InlineOnly
   private static final boolean removeAll(Collection $this$removeAll, Collection elements) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$removeAll.removeAll(elements);
   }

   @InlineOnly
   private static final boolean retainAll(Collection $this$retainAll, Collection elements) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$retainAll.retainAll(elements);
   }

   @InlineOnly
   private static final void plusAssign(Collection $this$plusAssign, Object element) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      $this$plusAssign.add(element);
   }

   @InlineOnly
   private static final void plusAssign(Collection $this$plusAssign, Iterable elements) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.addAll($this$plusAssign, elements);
   }

   @InlineOnly
   private static final void plusAssign(Collection $this$plusAssign, Object[] elements) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.addAll($this$plusAssign, elements);
   }

   @InlineOnly
   private static final void plusAssign(Collection $this$plusAssign, Sequence elements) {
      Intrinsics.checkNotNullParameter($this$plusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.addAll($this$plusAssign, elements);
   }

   @InlineOnly
   private static final void minusAssign(Collection $this$minusAssign, Object element) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      $this$minusAssign.remove(element);
   }

   @InlineOnly
   private static final void minusAssign(Collection $this$minusAssign, Iterable elements) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.removeAll($this$minusAssign, elements);
   }

   @InlineOnly
   private static final void minusAssign(Collection $this$minusAssign, Object[] elements) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.removeAll($this$minusAssign, elements);
   }

   @InlineOnly
   private static final void minusAssign(Collection $this$minusAssign, Sequence elements) {
      Intrinsics.checkNotNullParameter($this$minusAssign, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      CollectionsKt.removeAll($this$minusAssign, elements);
   }

   public static final boolean addAll(@NotNull Collection $this$addAll, @NotNull Iterable elements) {
      Intrinsics.checkNotNullParameter($this$addAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      if (elements instanceof Collection) {
         return $this$addAll.addAll((Collection)elements);
      } else {
         boolean result = false;

         for(Object item : elements) {
            if ($this$addAll.add(item)) {
               result = true;
            }
         }

         return result;
      }
   }

   public static final boolean addAll(@NotNull Collection $this$addAll, @NotNull Sequence elements) {
      Intrinsics.checkNotNullParameter($this$addAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      boolean result = false;

      for(Object item : elements) {
         if ($this$addAll.add(item)) {
            result = true;
         }
      }

      return result;
   }

   public static final boolean addAll(@NotNull Collection $this$addAll, @NotNull Object[] elements) {
      Intrinsics.checkNotNullParameter($this$addAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$addAll.addAll((Collection)ArraysKt.asList(elements));
   }

   public static final boolean removeAll(@NotNull Collection $this$removeAll, @NotNull Iterable elements) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$removeAll.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(elements, (Iterable)$this$removeAll));
   }

   public static final boolean removeAll(@NotNull Collection $this$removeAll, @NotNull Sequence elements) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      Collection set = BrittleContainsOptimizationKt.convertToSetForSetOperation(elements);
      return !set.isEmpty() && $this$removeAll.removeAll(set);
   }

   public static final boolean removeAll(@NotNull Collection $this$removeAll, @NotNull Object[] elements) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return elements.length != 0 && $this$removeAll.removeAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(elements));
   }

   public static final boolean retainAll(@NotNull Collection $this$retainAll, @NotNull Iterable elements) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return $this$retainAll.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperationWith(elements, (Iterable)$this$retainAll));
   }

   public static final boolean retainAll(@NotNull Collection $this$retainAll, @NotNull Object[] elements) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      return elements.length != 0 ? $this$retainAll.retainAll(BrittleContainsOptimizationKt.convertToSetForSetOperation(elements)) : retainNothing$CollectionsKt__MutableCollectionsKt($this$retainAll);
   }

   public static final boolean retainAll(@NotNull Collection $this$retainAll, @NotNull Sequence elements) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(elements, "elements");
      Collection set = BrittleContainsOptimizationKt.convertToSetForSetOperation(elements);
      return !set.isEmpty() ? $this$retainAll.retainAll(set) : retainNothing$CollectionsKt__MutableCollectionsKt($this$retainAll);
   }

   private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection $this$retainNothing) {
      boolean result = !$this$retainNothing.isEmpty();
      $this$retainNothing.clear();
      return result;
   }

   public static final boolean removeAll(@NotNull Iterable $this$removeAll, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt($this$removeAll, predicate, true);
   }

   public static final boolean retainAll(@NotNull Iterable $this$retainAll, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt($this$retainAll, predicate, false);
   }

   private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable $this$filterInPlace, Function1 predicate, boolean predicateResultToRemove) {
      boolean result = false;
      Iterator var4 = $this$filterInPlace.iterator();
      Iterator $this$filterInPlace_u24lambda_u2d0 = var4;
      int var6 = 0;

      while($this$filterInPlace_u24lambda_u2d0.hasNext()) {
         if ((Boolean)predicate.invoke($this$filterInPlace_u24lambda_u2d0.next()) == predicateResultToRemove) {
            $this$filterInPlace_u24lambda_u2d0.remove();
            result = true;
         }
      }

      return result;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use removeAt(index) instead.",
      replaceWith = @ReplaceWith(
   expression = "removeAt(index)",
   imports = {}
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final Object remove(List $this$remove, int index) {
      Intrinsics.checkNotNullParameter($this$remove, "<this>");
      return $this$remove.remove(index);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   public static final Object removeFirst(@NotNull List $this$removeFirst) {
      Intrinsics.checkNotNullParameter($this$removeFirst, "<this>");
      if ($this$removeFirst.isEmpty()) {
         throw new NoSuchElementException("List is empty.");
      } else {
         return $this$removeFirst.remove(0);
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @Nullable
   public static final Object removeFirstOrNull(@NotNull List $this$removeFirstOrNull) {
      Intrinsics.checkNotNullParameter($this$removeFirstOrNull, "<this>");
      return $this$removeFirstOrNull.isEmpty() ? null : $this$removeFirstOrNull.remove(0);
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   public static final Object removeLast(@NotNull List $this$removeLast) {
      Intrinsics.checkNotNullParameter($this$removeLast, "<this>");
      if ($this$removeLast.isEmpty()) {
         throw new NoSuchElementException("List is empty.");
      } else {
         return $this$removeLast.remove(CollectionsKt.getLastIndex($this$removeLast));
      }
   }

   @SinceKotlin(
      version = "1.4"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class}
   )
   @Nullable
   public static final Object removeLastOrNull(@NotNull List $this$removeLastOrNull) {
      Intrinsics.checkNotNullParameter($this$removeLastOrNull, "<this>");
      return $this$removeLastOrNull.isEmpty() ? null : $this$removeLastOrNull.remove(CollectionsKt.getLastIndex($this$removeLastOrNull));
   }

   public static final boolean removeAll(@NotNull List $this$removeAll, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$removeAll, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt($this$removeAll, predicate, true);
   }

   public static final boolean retainAll(@NotNull List $this$retainAll, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter($this$retainAll, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      return filterInPlace$CollectionsKt__MutableCollectionsKt($this$retainAll, predicate, false);
   }

   private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List $this$filterInPlace, Function1 predicate, boolean predicateResultToRemove) {
      if (!($this$filterInPlace instanceof RandomAccess)) {
         return filterInPlace$CollectionsKt__MutableCollectionsKt((Iterable)$this$filterInPlace, predicate, predicateResultToRemove);
      } else {
         int writeIndex = 0;
         int var4 = 0;
         int removeIndex = CollectionsKt.getLastIndex($this$filterInPlace);
         int readIndex;
         if (var4 <= removeIndex) {
            do {
               readIndex = var4++;
               Object element = $this$filterInPlace.get(readIndex);
               if ((Boolean)predicate.invoke(element) != predicateResultToRemove) {
                  if (writeIndex != readIndex) {
                     $this$filterInPlace.set(writeIndex, element);
                  }

                  ++writeIndex;
               }
            } while(readIndex != removeIndex);
         }

         if (writeIndex >= $this$filterInPlace.size()) {
            return false;
         } else {
            var4 = CollectionsKt.getLastIndex($this$filterInPlace);
            if (writeIndex <= var4) {
               do {
                  removeIndex = var4--;
                  $this$filterInPlace.remove(removeIndex);
               } while(removeIndex != writeIndex);
            }

            return true;
         }
      }
   }

   public CollectionsKt__MutableCollectionsKt() {
   }
}
