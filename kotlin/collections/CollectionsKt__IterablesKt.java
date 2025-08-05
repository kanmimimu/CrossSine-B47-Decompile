package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.TuplesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\bø\u0001\u0000\u001a \u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\u0007H\u0001\u001a\u001f\u0010\t\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0001¢\u0006\u0002\u0010\n\u001a\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a@\u0010\r\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000f0\f0\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000f*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000f0\u000e0\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0010"},
   d2 = {"Iterable", "", "T", "iterator", "Lkotlin/Function0;", "", "collectionSizeOrDefault", "", "default", "collectionSizeOrNull", "(Ljava/lang/Iterable;)Ljava/lang/Integer;", "flatten", "", "unzip", "Lkotlin/Pair;", "R", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
   @InlineOnly
   private static final Iterable Iterable(final Function0 iterator) {
      Intrinsics.checkNotNullParameter(iterator, "iterator");
      return new Iterable() {
         @NotNull
         public Iterator iterator() {
            return (Iterator)iterator.invoke();
         }
      };
   }

   @PublishedApi
   @Nullable
   public static final Integer collectionSizeOrNull(@NotNull Iterable $this$collectionSizeOrNull) {
      Intrinsics.checkNotNullParameter($this$collectionSizeOrNull, "<this>");
      return $this$collectionSizeOrNull instanceof Collection ? ((Collection)$this$collectionSizeOrNull).size() : null;
   }

   @PublishedApi
   public static final int collectionSizeOrDefault(@NotNull Iterable $this$collectionSizeOrDefault, int var1) {
      Intrinsics.checkNotNullParameter($this$collectionSizeOrDefault, "<this>");
      return $this$collectionSizeOrDefault instanceof Collection ? ((Collection)$this$collectionSizeOrDefault).size() : var1;
   }

   @NotNull
   public static final List flatten(@NotNull Iterable $this$flatten) {
      Intrinsics.checkNotNullParameter($this$flatten, "<this>");
      ArrayList result = new ArrayList();

      for(Iterable element : $this$flatten) {
         CollectionsKt.addAll((Collection)result, element);
      }

      return (List)result;
   }

   @NotNull
   public static final Pair unzip(@NotNull Iterable $this$unzip) {
      Intrinsics.checkNotNullParameter($this$unzip, "<this>");
      int expectedSize = CollectionsKt.collectionSizeOrDefault($this$unzip, 10);
      ArrayList listT = new ArrayList(expectedSize);
      ArrayList listR = new ArrayList(expectedSize);

      for(Pair pair : $this$unzip) {
         listT.add(pair.getFirst());
         listR.add(pair.getSecond());
      }

      return TuplesKt.to(listT, listR);
   }

   public CollectionsKt__IterablesKt() {
   }
}
