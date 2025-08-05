package kotlin.comparisons;

import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\b\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\"\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a+\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011\u001a&\u0010\u0000\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a\"\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a+\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019\u001a&\u0010\u0000\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a\"\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a+\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\bø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a&\u0010\u0000\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a\"\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005\u001a+\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010\b\u001a&\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b(\u0010\f\u001a\"\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0004\b)\u0010\u000f\u001a+\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0011\u001a&\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0004\b+\u0010\u0014\u001a\"\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007ø\u0001\u0000¢\u0006\u0004\b,\u0010\u0017\u001a+\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\bø\u0001\u0000¢\u0006\u0004\b-\u0010\u0019\u001a&\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007ø\u0001\u0000¢\u0006\u0004\b.\u0010\u001c\u001a\"\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007ø\u0001\u0000¢\u0006\u0004\b/\u0010\u001f\u001a+\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\bø\u0001\u0000¢\u0006\u0004\b0\u0010!\u001a&\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007ø\u0001\u0000¢\u0006\u0004\b1\u0010$\u0082\u0002\u0004\n\u0002\b\u0019¨\u00062"},
   d2 = {"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "other", "Lkotlin/UByteArray;", "maxOf-Wr6uiD8", "(B[B)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/UIntArray;", "maxOf-Md2H83M", "(I[I)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/ULongArray;", "maxOf-R03FKyM", "(J[J)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "Lkotlin/UShortArray;", "maxOf-t1qELG4", "(S[S)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-Wr6uiD8", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-Md2H83M", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-R03FKyM", "minOf-5PvTz6A", "minOf-VKSA0NQ", "minOf-t1qELG4", "kotlin-stdlib"},
   xs = "kotlin/comparisons/UComparisonsKt"
)
class UComparisonsKt___UComparisonsKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int maxOf_J1ME1BU/* $FF was: maxOf-J1ME1BU*/(int a, int b) {
      return UnsignedKt.uintCompare(a, b) >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long maxOf_eb3DHEI/* $FF was: maxOf-eb3DHEI*/(long a, long b) {
      return UnsignedKt.ulongCompare(a, b) >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte maxOf_Kr8caGY/* $FF was: maxOf-Kr8caGY*/(byte a, byte b) {
      return Intrinsics.compare(a & 255, b & 255) >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short maxOf_5PvTz6A/* $FF was: maxOf-5PvTz6A*/(short a, short b) {
      return Intrinsics.compare(a & '\uffff', b & '\uffff') >= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final int maxOf_WZ9TVnA/* $FF was: maxOf-WZ9TVnA*/(int a, int b, int c) {
      return UComparisonsKt.maxOf-J1ME1BU(a, UComparisonsKt.maxOf-J1ME1BU(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final long maxOf_sambcqE/* $FF was: maxOf-sambcqE*/(long a, long b, long c) {
      return UComparisonsKt.maxOf-eb3DHEI(a, UComparisonsKt.maxOf-eb3DHEI(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final byte maxOf_b33U2AM/* $FF was: maxOf-b33U2AM*/(byte a, byte b, byte c) {
      return UComparisonsKt.maxOf-Kr8caGY(a, UComparisonsKt.maxOf-Kr8caGY(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final short maxOf_VKSA0NQ/* $FF was: maxOf-VKSA0NQ*/(short a, short b, short c) {
      return UComparisonsKt.maxOf-5PvTz6A(a, UComparisonsKt.maxOf-5PvTz6A(b, c));
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final int maxOf_Md2H83M/* $FF was: maxOf-Md2H83M*/(int a, @NotNull int... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      int max = a;

      int e;
      for(Iterator var3 = UIntArray.iterator-impl(other); var3.hasNext(); max = UComparisonsKt.maxOf-J1ME1BU(max, e)) {
         e = ((UInt)var3.next()).unbox-impl();
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final long maxOf_R03FKyM/* $FF was: maxOf-R03FKyM*/(long a, @NotNull long... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      long max = a;

      long e;
      for(Iterator var5 = ULongArray.iterator-impl(other); var5.hasNext(); max = UComparisonsKt.maxOf-eb3DHEI(max, e)) {
         e = ((ULong)var5.next()).unbox-impl();
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final byte maxOf_Wr6uiD8/* $FF was: maxOf-Wr6uiD8*/(byte a, @NotNull byte... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      byte max = a;

      byte e;
      for(Iterator var3 = UByteArray.iterator-impl(other); var3.hasNext(); max = UComparisonsKt.maxOf-Kr8caGY(max, e)) {
         e = ((UByte)var3.next()).unbox-impl();
      }

      return max;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final short maxOf_t1qELG4/* $FF was: maxOf-t1qELG4*/(short a, @NotNull short... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      short max = a;

      short e;
      for(Iterator var3 = UShortArray.iterator-impl(other); var3.hasNext(); max = UComparisonsKt.maxOf-5PvTz6A(max, e)) {
         e = ((UShort)var3.next()).unbox-impl();
      }

      return max;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int minOf_J1ME1BU/* $FF was: minOf-J1ME1BU*/(int a, int b) {
      return UnsignedKt.uintCompare(a, b) <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long minOf_eb3DHEI/* $FF was: minOf-eb3DHEI*/(long a, long b) {
      return UnsignedKt.ulongCompare(a, b) <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte minOf_Kr8caGY/* $FF was: minOf-Kr8caGY*/(byte a, byte b) {
      return Intrinsics.compare(a & 255, b & 255) <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short minOf_5PvTz6A/* $FF was: minOf-5PvTz6A*/(short a, short b) {
      return Intrinsics.compare(a & '\uffff', b & '\uffff') <= 0 ? a : b;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final int minOf_WZ9TVnA/* $FF was: minOf-WZ9TVnA*/(int a, int b, int c) {
      return UComparisonsKt.minOf-J1ME1BU(a, UComparisonsKt.minOf-J1ME1BU(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final long minOf_sambcqE/* $FF was: minOf-sambcqE*/(long a, long b, long c) {
      return UComparisonsKt.minOf-eb3DHEI(a, UComparisonsKt.minOf-eb3DHEI(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final byte minOf_b33U2AM/* $FF was: minOf-b33U2AM*/(byte a, byte b, byte c) {
      return UComparisonsKt.minOf-Kr8caGY(a, UComparisonsKt.minOf-Kr8caGY(b, c));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final short minOf_VKSA0NQ/* $FF was: minOf-VKSA0NQ*/(short a, short b, short c) {
      return UComparisonsKt.minOf-5PvTz6A(a, UComparisonsKt.minOf-5PvTz6A(b, c));
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final int minOf_Md2H83M/* $FF was: minOf-Md2H83M*/(int a, @NotNull int... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      int min = a;

      int e;
      for(Iterator var3 = UIntArray.iterator-impl(other); var3.hasNext(); min = UComparisonsKt.minOf-J1ME1BU(min, e)) {
         e = ((UInt)var3.next()).unbox-impl();
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final long minOf_R03FKyM/* $FF was: minOf-R03FKyM*/(long a, @NotNull long... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      long min = a;

      long e;
      for(Iterator var5 = ULongArray.iterator-impl(other); var5.hasNext(); min = UComparisonsKt.minOf-eb3DHEI(min, e)) {
         e = ((ULong)var5.next()).unbox-impl();
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final byte minOf_Wr6uiD8/* $FF was: minOf-Wr6uiD8*/(byte a, @NotNull byte... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      byte min = a;

      byte e;
      for(Iterator var3 = UByteArray.iterator-impl(other); var3.hasNext(); min = UComparisonsKt.minOf-Kr8caGY(min, e)) {
         e = ((UByte)var3.next()).unbox-impl();
      }

      return min;
   }

   @SinceKotlin(
      version = "1.4"
   )
   @ExperimentalUnsignedTypes
   public static final short minOf_t1qELG4/* $FF was: minOf-t1qELG4*/(short a, @NotNull short... other) {
      Intrinsics.checkNotNullParameter(other, "other");
      short min = a;

      short e;
      for(Iterator var3 = UShortArray.iterator-impl(other); var3.hasNext(); min = UComparisonsKt.minOf-5PvTz6A(min, e)) {
         e = ((UShort)var3.next()).unbox-impl();
      }

      return min;
   }

   public UComparisonsKt___UComparisonsKt() {
   }
}
