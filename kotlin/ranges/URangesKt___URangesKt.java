package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.ExperimentalStdlibApi;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\nø\u0001\u0000¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\nø\u0001\u0000¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%H\u0087\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\b*\u00020/H\u0087\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\f\u0010J\u001a\u000208*\u000208H\u0007\u001a\f\u0010J\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010K\u001a\u000208*\u0002082\u0006\u0010K\u001a\u00020LH\u0087\u0004\u001a\u0015\u0010K\u001a\u00020>*\u00020>2\u0006\u0010K\u001a\u00020MH\u0087\u0004\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bO\u0010P\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bQ\u0010R\u001a\u001f\u0010N\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bS\u0010T\u001a\u001f\u0010N\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\bU\u0010V\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006W"},
   d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"},
   xs = "kotlin/ranges/URangesKt"
)
class URangesKt___URangesKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final int random(UIntRange $this$random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      return URangesKt.random($this$random, Random.Default);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final long random(ULongRange $this$random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      return URangesKt.random($this$random, Random.Default);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int random(@NotNull UIntRange $this$random, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");

      try {
         return URandomKt.nextUInt(random, $this$random);
      } catch (IllegalArgumentException e) {
         throw new NoSuchElementException(e.getMessage());
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long random(@NotNull ULongRange $this$random, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$random, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");

      try {
         return URandomKt.nextULong(random, $this$random);
      } catch (IllegalArgumentException e) {
         throw new NoSuchElementException(e.getMessage());
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final UInt randomOrNull(UIntRange $this$randomOrNull) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      return URangesKt.randomOrNull($this$randomOrNull, Random.Default);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final ULong randomOrNull(ULongRange $this$randomOrNull) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      return URangesKt.randomOrNull($this$randomOrNull, Random.Default);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UInt randomOrNull(@NotNull UIntRange $this$randomOrNull, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      return $this$randomOrNull.isEmpty() ? null : UInt.box-impl(URandomKt.nextUInt(random, $this$randomOrNull));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final ULong randomOrNull(@NotNull ULongRange $this$randomOrNull, @NotNull Random random) {
      Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
      Intrinsics.checkNotNullParameter(random, "random");
      return $this$randomOrNull.isEmpty() ? null : ULong.box-impl(URandomKt.nextULong(random, $this$randomOrNull));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final boolean contains_biwQdVI/* $FF was: contains-biwQdVI*/(UIntRange $this$contains, UInt element) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return element != null && $this$contains.contains-WZ4Q5Ns(element.unbox-impl());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final boolean contains_GYNo2lE/* $FF was: contains-GYNo2lE*/(ULongRange $this$contains, ULong element) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return element != null && $this$contains.contains-VKZWuLQ(element.unbox-impl());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_68kG9v0/* $FF was: contains-68kG9v0*/(@NotNull UIntRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return $this$contains.contains-WZ4Q5Ns(UInt.constructor-impl(value & 255));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_ULb_yJY/* $FF was: contains-ULb-yJY*/(@NotNull ULongRange $this$contains, byte value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return $this$contains.contains-VKZWuLQ(ULong.constructor-impl((long)value & 255L));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_Gab390E/* $FF was: contains-Gab390E*/(@NotNull ULongRange $this$contains, int value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return $this$contains.contains-VKZWuLQ(ULong.constructor-impl((long)value & 4294967295L));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_fz5IDCE/* $FF was: contains-fz5IDCE*/(@NotNull UIntRange $this$contains, long value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      byte var3 = 32;
      return ULong.constructor-impl(value >>> var3) == 0L && $this$contains.contains-WZ4Q5Ns(UInt.constructor-impl((int)value));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_ZsK3CEQ/* $FF was: contains-ZsK3CEQ*/(@NotNull UIntRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return $this$contains.contains-WZ4Q5Ns(UInt.constructor-impl(value & '\uffff'));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final boolean contains_uhHAxoY/* $FF was: contains-uhHAxoY*/(@NotNull ULongRange $this$contains, short value) {
      Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
      return $this$contains.contains-VKZWuLQ(ULong.constructor-impl((long)value & 65535L));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntProgression downTo_Kr8caGY/* $FF was: downTo-Kr8caGY*/(byte $this$downTo, byte to) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl($this$downTo & 255), UInt.constructor-impl(to & 255), -1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntProgression downTo_J1ME1BU/* $FF was: downTo-J1ME1BU*/(int $this$downTo, int to) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs($this$downTo, to, -1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final ULongProgression downTo_eb3DHEI/* $FF was: downTo-eb3DHEI*/(long $this$downTo, long to) {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g($this$downTo, to, -1L);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntProgression downTo_5PvTz6A/* $FF was: downTo-5PvTz6A*/(short $this$downTo, short to) {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl($this$downTo & '\uffff'), UInt.constructor-impl(to & '\uffff'), -1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntProgression reversed(@NotNull UIntProgression $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs($this$reversed.getLast-pVg5ArA(), $this$reversed.getFirst-pVg5ArA(), -$this$reversed.getStep());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final ULongProgression reversed(@NotNull ULongProgression $this$reversed) {
      Intrinsics.checkNotNullParameter($this$reversed, "<this>");
      return ULongProgression.Companion.fromClosedRange-7ftBX0g($this$reversed.getLast-s-VKNKU(), $this$reversed.getFirst-s-VKNKU(), -$this$reversed.getStep());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntProgression step(@NotNull UIntProgression $this$step, int step) {
      Intrinsics.checkNotNullParameter($this$step, "<this>");
      RangesKt.checkStepIsPositive(step > 0, (Number)step);
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs($this$step.getFirst-pVg5ArA(), $this$step.getLast-pVg5ArA(), $this$step.getStep() > 0 ? step : -step);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final ULongProgression step(@NotNull ULongProgression $this$step, long step) {
      Intrinsics.checkNotNullParameter($this$step, "<this>");
      RangesKt.checkStepIsPositive(step > 0L, (Number)step);
      return ULongProgression.Companion.fromClosedRange-7ftBX0g($this$step.getFirst-s-VKNKU(), $this$step.getLast-s-VKNKU(), $this$step.getStep() > 0L ? step : -step);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntRange until_Kr8caGY/* $FF was: until-Kr8caGY*/(byte $this$until, byte to) {
      int var2 = 0;
      if (Intrinsics.compare(to & 255, var2 & 255) <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         var2 = UInt.constructor-impl($this$until & 255);
         int var3 = 1;
         int var4 = UInt.constructor-impl(to & 255);
         var3 = UInt.constructor-impl(var4 - var3);
         return new UIntRange(var2, var3, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntRange until_J1ME1BU/* $FF was: until-J1ME1BU*/(int $this$until, int to) {
      int var2 = 0;
      if (UnsignedKt.uintCompare(to, var2) <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         var2 = (byte)1;
         var2 = UInt.constructor-impl(to - var2);
         return new UIntRange($this$until, var2, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final ULongRange until_eb3DHEI/* $FF was: until-eb3DHEI*/(long $this$until, long to) {
      long var4 = 0L;
      if (UnsignedKt.ulongCompare(to, var4) <= 0) {
         return ULongRange.Companion.getEMPTY();
      } else {
         byte var7 = 1;
         long var5 = ULong.constructor-impl((long)var7 & 4294967295L);
         long var8 = ULong.constructor-impl(to - var5);
         return new ULongRange($this$until, var8, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final UIntRange until_5PvTz6A/* $FF was: until-5PvTz6A*/(short $this$until, short to) {
      int var2 = 0;
      if (Intrinsics.compare(to & '\uffff', var2 & '\uffff') <= 0) {
         return UIntRange.Companion.getEMPTY();
      } else {
         var2 = UInt.constructor-impl($this$until & '\uffff');
         int var3 = 1;
         int var4 = UInt.constructor-impl(to & '\uffff');
         var3 = UInt.constructor-impl(var4 - var3);
         return new UIntRange(var2, var3, (DefaultConstructorMarker)null);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int coerceAtLeast_J1ME1BU/* $FF was: coerceAtLeast-J1ME1BU*/(int $this$coerceAtLeast, int minimumValue) {
      return UnsignedKt.uintCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long coerceAtLeast_eb3DHEI/* $FF was: coerceAtLeast-eb3DHEI*/(long $this$coerceAtLeast, long minimumValue) {
      return UnsignedKt.ulongCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte coerceAtLeast_Kr8caGY/* $FF was: coerceAtLeast-Kr8caGY*/(byte $this$coerceAtLeast, byte minimumValue) {
      return Intrinsics.compare($this$coerceAtLeast & 255, minimumValue & 255) < 0 ? minimumValue : $this$coerceAtLeast;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short coerceAtLeast_5PvTz6A/* $FF was: coerceAtLeast-5PvTz6A*/(short $this$coerceAtLeast, short minimumValue) {
      return Intrinsics.compare($this$coerceAtLeast & '\uffff', minimumValue & '\uffff') < 0 ? minimumValue : $this$coerceAtLeast;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int coerceAtMost_J1ME1BU/* $FF was: coerceAtMost-J1ME1BU*/(int $this$coerceAtMost, int maximumValue) {
      return UnsignedKt.uintCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long coerceAtMost_eb3DHEI/* $FF was: coerceAtMost-eb3DHEI*/(long $this$coerceAtMost, long maximumValue) {
      return UnsignedKt.ulongCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte coerceAtMost_Kr8caGY/* $FF was: coerceAtMost-Kr8caGY*/(byte $this$coerceAtMost, byte maximumValue) {
      return Intrinsics.compare($this$coerceAtMost & 255, maximumValue & 255) > 0 ? maximumValue : $this$coerceAtMost;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short coerceAtMost_5PvTz6A/* $FF was: coerceAtMost-5PvTz6A*/(short $this$coerceAtMost, short maximumValue) {
      return Intrinsics.compare($this$coerceAtMost & '\uffff', maximumValue & '\uffff') > 0 ? maximumValue : $this$coerceAtMost;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int coerceIn_WZ9TVnA/* $FF was: coerceIn-WZ9TVnA*/(int $this$coerceIn, int minimumValue, int maximumValue) {
      if (UnsignedKt.uintCompare(minimumValue, maximumValue) > 0) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UInt.toString-impl(maximumValue) + " is less than minimum " + UInt.toString-impl(minimumValue) + '.');
      } else if (UnsignedKt.uintCompare($this$coerceIn, minimumValue) < 0) {
         return minimumValue;
      } else {
         return UnsignedKt.uintCompare($this$coerceIn, maximumValue) > 0 ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long coerceIn_sambcqE/* $FF was: coerceIn-sambcqE*/(long $this$coerceIn, long minimumValue, long maximumValue) {
      if (UnsignedKt.ulongCompare(minimumValue, maximumValue) > 0) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ULong.toString-impl(maximumValue) + " is less than minimum " + ULong.toString-impl(minimumValue) + '.');
      } else if (UnsignedKt.ulongCompare($this$coerceIn, minimumValue) < 0) {
         return minimumValue;
      } else {
         return UnsignedKt.ulongCompare($this$coerceIn, maximumValue) > 0 ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte coerceIn_b33U2AM/* $FF was: coerceIn-b33U2AM*/(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
      if (Intrinsics.compare(minimumValue & 255, maximumValue & 255) > 0) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UByte.toString-impl(maximumValue) + " is less than minimum " + UByte.toString-impl(minimumValue) + '.');
      } else if (Intrinsics.compare($this$coerceIn & 255, minimumValue & 255) < 0) {
         return minimumValue;
      } else {
         return Intrinsics.compare($this$coerceIn & 255, maximumValue & 255) > 0 ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short coerceIn_VKSA0NQ/* $FF was: coerceIn-VKSA0NQ*/(short $this$coerceIn, short minimumValue, short maximumValue) {
      if (Intrinsics.compare(minimumValue & '\uffff', maximumValue & '\uffff') > 0) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UShort.toString-impl(maximumValue) + " is less than minimum " + UShort.toString-impl(minimumValue) + '.');
      } else if (Intrinsics.compare($this$coerceIn & '\uffff', minimumValue & '\uffff') < 0) {
         return minimumValue;
      } else {
         return Intrinsics.compare($this$coerceIn & '\uffff', maximumValue & '\uffff') > 0 ? maximumValue : $this$coerceIn;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int coerceIn_wuiCnnA/* $FF was: coerceIn-wuiCnnA*/(int $this$coerceIn, @NotNull ClosedRange range) {
      Intrinsics.checkNotNullParameter(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((UInt)RangesKt.coerceIn(UInt.box-impl($this$coerceIn), (ClosedFloatingPointRange)range)).unbox-impl();
      } else if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         int var2 = ((UInt)range.getStart()).unbox-impl();
         int var10000;
         if (UnsignedKt.uintCompare($this$coerceIn, var2) < 0) {
            var10000 = ((UInt)range.getStart()).unbox-impl();
         } else {
            var2 = ((UInt)range.getEndInclusive()).unbox-impl();
            var10000 = UnsignedKt.uintCompare($this$coerceIn, var2) > 0 ? ((UInt)range.getEndInclusive()).unbox-impl() : $this$coerceIn;
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long coerceIn_JPwROB0/* $FF was: coerceIn-JPwROB0*/(long $this$coerceIn, @NotNull ClosedRange range) {
      Intrinsics.checkNotNullParameter(range, "range");
      if (range instanceof ClosedFloatingPointRange) {
         return ((ULong)RangesKt.coerceIn(ULong.box-impl($this$coerceIn), (ClosedFloatingPointRange)range)).unbox-impl();
      } else if (range.isEmpty()) {
         throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
      } else {
         long var3 = ((ULong)range.getStart()).unbox-impl();
         long var10000;
         if (UnsignedKt.ulongCompare($this$coerceIn, var3) < 0) {
            var10000 = ((ULong)range.getStart()).unbox-impl();
         } else {
            var3 = ((ULong)range.getEndInclusive()).unbox-impl();
            var10000 = UnsignedKt.ulongCompare($this$coerceIn, var3) > 0 ? ((ULong)range.getEndInclusive()).unbox-impl() : $this$coerceIn;
         }

         return var10000;
      }
   }

   public URangesKt___URangesKt() {
   }
}
