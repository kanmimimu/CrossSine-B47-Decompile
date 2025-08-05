package kotlin.random;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"},
   d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}
)
public final class URandomKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int nextUInt(@NotNull Random $this$nextUInt) {
      Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
      return UInt.constructor-impl($this$nextUInt.nextInt());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int nextUInt_qCasIEU/* $FF was: nextUInt-qCasIEU*/(@NotNull Random $this$nextUInt, int until) {
      Intrinsics.checkNotNullParameter($this$nextUInt, "$this$nextUInt");
      return nextUInt-a8DCA5k($this$nextUInt, 0, until);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int nextUInt_a8DCA5k/* $FF was: nextUInt-a8DCA5k*/(@NotNull Random $this$nextUInt, int from, int until) {
      Intrinsics.checkNotNullParameter($this$nextUInt, "$this$nextUInt");
      checkUIntRangeBounds-J1ME1BU(from, until);
      int signedFrom = from ^ Integer.MIN_VALUE;
      int signedUntil = until ^ Integer.MIN_VALUE;
      int signedResult = $this$nextUInt.nextInt(signedFrom, signedUntil) ^ Integer.MIN_VALUE;
      return UInt.constructor-impl(signedResult);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int nextUInt(@NotNull Random $this$nextUInt, @NotNull UIntRange range) {
      Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      if (range.isEmpty()) {
         throw new IllegalArgumentException(Intrinsics.stringPlus("Cannot get random in empty range: ", range));
      } else {
         return UnsignedKt.uintCompare(range.getLast-pVg5ArA(), -1) < 0 ? nextUInt-a8DCA5k($this$nextUInt, range.getFirst-pVg5ArA(), UInt.constructor-impl(range.getLast-pVg5ArA() + 1)) : (UnsignedKt.uintCompare(range.getFirst-pVg5ArA(), 0) > 0 ? UInt.constructor-impl(nextUInt-a8DCA5k($this$nextUInt, UInt.constructor-impl(range.getFirst-pVg5ArA() - 1), range.getLast-pVg5ArA()) + 1) : nextUInt($this$nextUInt));
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long nextULong(@NotNull Random $this$nextULong) {
      Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
      return ULong.constructor-impl($this$nextULong.nextLong());
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long nextULong_V1Xi4fY/* $FF was: nextULong-V1Xi4fY*/(@NotNull Random $this$nextULong, long until) {
      Intrinsics.checkNotNullParameter($this$nextULong, "$this$nextULong");
      return nextULong-jmpaW-c($this$nextULong, 0L, until);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long nextULong_jmpaW_c/* $FF was: nextULong-jmpaW-c*/(@NotNull Random $this$nextULong, long from, long until) {
      Intrinsics.checkNotNullParameter($this$nextULong, "$this$nextULong");
      checkULongRangeBounds-eb3DHEI(from, until);
      long signedFrom = from ^ Long.MIN_VALUE;
      long signedUntil = until ^ Long.MIN_VALUE;
      long signedResult = $this$nextULong.nextLong(signedFrom, signedUntil) ^ Long.MIN_VALUE;
      return ULong.constructor-impl(signedResult);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long nextULong(@NotNull Random $this$nextULong, @NotNull ULongRange range) {
      Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
      Intrinsics.checkNotNullParameter(range, "range");
      if (range.isEmpty()) {
         throw new IllegalArgumentException(Intrinsics.stringPlus("Cannot get random in empty range: ", range));
      } else {
         long var10000;
         if (UnsignedKt.ulongCompare(range.getLast-s-VKNKU(), -1L) < 0) {
            long var10001 = range.getFirst-s-VKNKU();
            long var2 = range.getLast-s-VKNKU();
            byte var4 = 1;
            long var5 = ULong.constructor-impl((long)var4 & 4294967295L);
            var10000 = nextULong-jmpaW-c($this$nextULong, var10001, ULong.constructor-impl(var2 + var5));
         } else if (UnsignedKt.ulongCompare(range.getFirst-s-VKNKU(), 0L) > 0) {
            long var7 = range.getFirst-s-VKNKU();
            byte var9 = 1;
            long var11 = ULong.constructor-impl((long)var9 & 4294967295L);
            var7 = nextULong-jmpaW-c($this$nextULong, ULong.constructor-impl(var7 - var11), range.getLast-s-VKNKU());
            var9 = 1;
            var11 = ULong.constructor-impl((long)var9 & 4294967295L);
            var10000 = ULong.constructor-impl(var7 + var11);
         } else {
            var10000 = nextULong($this$nextULong);
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_EVgfTAA/* $FF was: nextUBytes-EVgfTAA*/(@NotNull Random $this$nextUBytes, @NotNull byte[] array) {
      Intrinsics.checkNotNullParameter($this$nextUBytes, "$this$nextUBytes");
      Intrinsics.checkNotNullParameter(array, "array");
      $this$nextUBytes.nextBytes(array);
      return array;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes(@NotNull Random $this$nextUBytes, int size) {
      Intrinsics.checkNotNullParameter($this$nextUBytes, "<this>");
      return UByteArray.constructor-impl($this$nextUBytes.nextBytes(size));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_Wvrt4B4/* $FF was: nextUBytes-Wvrt4B4*/(@NotNull Random $this$nextUBytes, @NotNull byte[] array, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter($this$nextUBytes, "$this$nextUBytes");
      Intrinsics.checkNotNullParameter(array, "array");
      $this$nextUBytes.nextBytes(array, fromIndex, toIndex);
      return array;
   }

   // $FF: synthetic method
   public static byte[] nextUBytes_Wvrt4B4$default/* $FF was: nextUBytes-Wvrt4B4$default*/(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var1);
      }

      return nextUBytes-Wvrt4B4(var0, var1, var2, var3);
   }

   public static final void checkUIntRangeBounds_J1ME1BU/* $FF was: checkUIntRangeBounds-J1ME1BU*/(int from, int until) {
      boolean var2 = UnsignedKt.uintCompare(until, from) > 0;
      if (!var2) {
         int var3 = 0;
         String var4 = RandomKt.boundsErrorMessage(UInt.box-impl(from), UInt.box-impl(until));
         throw new IllegalArgumentException(var4.toString());
      }
   }

   public static final void checkULongRangeBounds_eb3DHEI/* $FF was: checkULongRangeBounds-eb3DHEI*/(long from, long until) {
      boolean var4 = UnsignedKt.ulongCompare(until, from) > 0;
      if (!var4) {
         int var5 = 0;
         String var6 = RandomKt.boundsErrorMessage(ULong.box-impl(from), ULong.box-impl(until));
         throw new IllegalArgumentException(var6.toString());
      }
   }
}
