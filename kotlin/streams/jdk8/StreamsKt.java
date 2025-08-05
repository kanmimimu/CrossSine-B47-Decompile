package kotlin.streams.jdk8;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u0005H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\t\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u0001H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\u0003H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f*\u00020\u0005H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f*\u00020\u0007H\u0007\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\b0\f\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007¨\u0006\r"},
   d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "", "Ljava/util/stream/DoubleStream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "T", "Ljava/util/stream/Stream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"},
   pn = "kotlin.streams"
)
@JvmName(
   name = "StreamsKt"
)
public final class StreamsKt {
   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull Stream $this$asSequence) {
      Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
      return new StreamsKt$asSequence$$inlined$Sequence$1($this$asSequence);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull IntStream $this$asSequence) {
      Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
      return new StreamsKt$asSequence$$inlined$Sequence$2($this$asSequence);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull LongStream $this$asSequence) {
      Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
      return new StreamsKt$asSequence$$inlined$Sequence$3($this$asSequence);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Sequence asSequence(@NotNull DoubleStream $this$asSequence) {
      Intrinsics.checkNotNullParameter($this$asSequence, "<this>");
      return new StreamsKt$asSequence$$inlined$Sequence$4($this$asSequence);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final Stream asStream(@NotNull Sequence $this$asStream) {
      Intrinsics.checkNotNullParameter($this$asStream, "<this>");
      Stream var1 = StreamSupport.stream(StreamsKt::asStream$lambda-4, 16, false);
      Intrinsics.checkNotNullExpressionValue(var1, "stream({ Spliterators.sp…literator.ORDERED, false)");
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull Stream $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      Object var1 = $this$toList.collect(Collectors.toList());
      Intrinsics.checkNotNullExpressionValue(var1, "collect(Collectors.toList<T>())");
      return (List)var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull IntStream $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      int[] var1 = $this$toList.toArray();
      Intrinsics.checkNotNullExpressionValue(var1, "toArray()");
      return ArraysKt.asList(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull LongStream $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      long[] var1 = $this$toList.toArray();
      Intrinsics.checkNotNullExpressionValue(var1, "toArray()");
      return ArraysKt.asList(var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List toList(@NotNull DoubleStream $this$toList) {
      Intrinsics.checkNotNullParameter($this$toList, "<this>");
      double[] var1 = $this$toList.toArray();
      Intrinsics.checkNotNullExpressionValue(var1, "toArray()");
      return ArraysKt.asList(var1);
   }

   private static final Spliterator asStream$lambda_4/* $FF was: asStream$lambda-4*/(Sequence $this_asStream) {
      Intrinsics.checkNotNullParameter($this_asStream, "$this_asStream");
      return Spliterators.spliteratorUnknownSize($this_asStream.iterator(), 16);
   }
}
