package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.collections.builders.ListBuilder;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000R\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\b\f\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0080\b\u001a\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006H\u0001\u001a?\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\u0006\u0010\b\u001a\u00020\t2\u001d\u0010\n\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0006\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\u0081\bø\u0001\u0000\u001a7\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\u001d\u0010\n\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0006\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\b\rH\u0081\bø\u0001\u0000\u001a\u0011\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0081\b\u001a\u0011\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0081\b\u001a\"\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00132\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0081\b¢\u0006\u0002\u0010\u0017\u001a4\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0013\"\u0004\b\u0000\u0010\u00182\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0013H\u0081\b¢\u0006\u0002\u0010\u001a\u001a\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006\"\u0004\b\u0000\u0010\u0004H\u0001\u001a\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006\"\u0004\b\u0000\u0010\u00042\u0006\u0010\b\u001a\u00020\tH\u0001\u001a\u001f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0003\"\u0004\b\u0000\u0010\u00182\u0006\u0010\u001d\u001a\u0002H\u0018¢\u0006\u0002\u0010\u001e\u001a1\u0010\u001f\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00140\u0013\"\u0004\b\u0000\u0010\u0018*\n\u0012\u0006\b\u0001\u0012\u0002H\u00180\u00132\u0006\u0010 \u001a\u00020\u0001H\u0000¢\u0006\u0002\u0010!\u001a\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0003\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180#H\u0007\u001a&\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0003\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180#2\u0006\u0010$\u001a\u00020%H\u0007\u001a\u001f\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0003\"\u0004\b\u0000\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00180'H\u0087\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006("},
   d2 = {"brittleContainsOptimizationEnabled", "", "build", "", "E", "builder", "", "buildListInternal", "capacity", "", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "checkCountOverflow", "count", "checkIndexOverflow", "index", "copyToArrayImpl", "", "", "collection", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "T", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "createListBuilder", "listOf", "element", "(Ljava/lang/Object;)Ljava/util/List;", "copyToArrayOfAny", "isVarargs", "([Ljava/lang/Object;Z)[Ljava/lang/Object;", "shuffled", "", "random", "Ljava/util/Random;", "toList", "Ljava/util/Enumeration;", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsJVMKt {
   @NotNull
   public static final List listOf(Object element) {
      List var1 = Collections.singletonList(element);
      Intrinsics.checkNotNullExpressionValue(var1, "singletonList(element)");
      return var1;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final List buildListInternal(Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      List var1 = CollectionsKt.createListBuilder();
      builderAction.invoke(var1);
      return CollectionsKt.build(var1);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final List buildListInternal(int capacity, Function1 builderAction) {
      Intrinsics.checkNotNullParameter(builderAction, "builderAction");
      List var2 = CollectionsKt.createListBuilder(capacity);
      builderAction.invoke(var2);
      return CollectionsKt.build(var2);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final List createListBuilder() {
      return new ListBuilder();
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final List createListBuilder(int capacity) {
      return new ListBuilder(capacity);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final List build(@NotNull List builder) {
      Intrinsics.checkNotNullParameter(builder, "builder");
      return ((ListBuilder)builder).build();
   }

   @InlineOnly
   private static final List toList(Enumeration $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      ArrayList var1 = Collections.list($this$toList);
      Intrinsics.checkNotNullExpressionValue(var1, "list(this)");
      return (List)var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable $this$shuffled) {
      Intrinsics.checkNotNullParameter($this$shuffled, "<this>");
      List $this$shuffled_u24lambda_u2d0 = CollectionsKt.toMutableList($this$shuffled);
      int var3 = 0;
      Collections.shuffle($this$shuffled_u24lambda_u2d0);
      return $this$shuffled_u24lambda_u2d0;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable $this$shuffled, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$shuffled, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      List $this$shuffled_u24lambda_u2d1 = CollectionsKt.toMutableList($this$shuffled);
      int var4 = 0;
      Collections.shuffle($this$shuffled_u24lambda_u2d1, random);
      return $this$shuffled_u24lambda_u2d1;
   }

   @InlineOnly
   private static final Object[] copyToArrayImpl(Collection collection) {
      Intrinsics.checkNotNullParameter(collection, "collection");
      return CollectionToArray.toArray(collection);
   }

   @InlineOnly
   private static final Object[] copyToArrayImpl(Collection collection, Object[] array) {
      Intrinsics.checkNotNullParameter(collection, "collection");
      Intrinsics.checkNotNullParameter(array, "array");
      return CollectionToArray.toArray(collection, array);
   }

   @NotNull
   public static final Object[] copyToArrayOfAny(@NotNull Object[] $this$copyToArrayOfAny, boolean isVarargs) {
      Intrinsics.checkNotNullParameter($this$copyToArrayOfAny, "<this>");
      Object[] var10000;
      if (isVarargs && Intrinsics.areEqual((Object)$this$copyToArrayOfAny.getClass(), (Object)Object[].class)) {
         var10000 = $this$copyToArrayOfAny;
      } else {
         Object[] var2 = Arrays.copyOf($this$copyToArrayOfAny, $this$copyToArrayOfAny.length, Object[].class);
         Intrinsics.checkNotNullExpressionValue(var2, "copyOf(this, this.size, Array<Any?>::class.java)");
         var10000 = var2;
      }

      return var10000;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int checkIndexOverflow(int index) {
      if (index < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw new ArithmeticException("Index overflow has happened.");
         }

         CollectionsKt.throwIndexOverflow();
      }

      return index;
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int checkCountOverflow(int count) {
      if (count < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw new ArithmeticException("Count overflow has happened.");
         }

         CollectionsKt.throwCountOverflow();
      }

      return count;
   }

   public static final boolean brittleContainsOptimizationEnabled() {
      int $i$f$brittleContainsOptimizationEnabled = 0;
      return CollectionSystemProperties.brittleContainsOptimizationEnabled;
   }

   public CollectionsKt__CollectionsJVMKt() {
   }
}
