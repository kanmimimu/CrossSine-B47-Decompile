package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import org.jetbrains.annotations.NotNull;

@JvmInline
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001tB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u000fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0012J\u001b\u0010&\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u000fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0012J\u001b\u00102\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b8\u0010\u000bJ\u001b\u00107\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0004\b9\u0010\u0012J\u001b\u00107\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b:\u0010\u001fJ\u001b\u00107\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b;\u0010<J\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u000fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0012J\u001b\u0010?\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010\u000fJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bJ\u0010\u0012J\u001b\u0010H\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010H\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u0018J\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u000fJ\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bO\u0010\u0012J\u001b\u0010M\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bP\u0010\u001fJ\u001b\u0010M\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u0018J\u0010\u0010R\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bS\u0010\u0005J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u0010\u0010\\\u001a\u00020\rH\u0087\b¢\u0006\u0004\b]\u0010-J\u0010\u0010^\u001a\u00020_H\u0087\b¢\u0006\u0004\b`\u0010aJ\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u000f\u0010f\u001a\u00020gH\u0016¢\u0006\u0004\bh\u0010iJ\u0016\u0010j\u001a\u00020\u0000H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bk\u0010\u0005J\u0016\u0010l\u001a\u00020\u0010H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bm\u0010-J\u0016\u0010n\u001a\u00020\u0013H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bo\u0010aJ\u0016\u0010p\u001a\u00020\u0016H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bq\u0010eJ\u001b\u0010r\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bs\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006u"},
   d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(BS)S", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.5"
)
@WasExperimental(
   markerClass = {ExperimentalUnsignedTypes.class}
)
public final class UByte implements Comparable {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   private final byte data;
   public static final byte MIN_VALUE = 0;
   public static final byte MAX_VALUE = -1;
   public static final int SIZE_BYTES = 1;
   public static final int SIZE_BITS = 8;

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void getData$annotations() {
   }

   @InlineOnly
   private static int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(byte arg0, byte other) {
      return Intrinsics.compare(arg0 & 255, other & 255);
   }

   @InlineOnly
   private int compareTo_7apg3OU/* $FF was: compareTo-7apg3OU*/(byte other) {
      byte var2 = this.unbox-impl();
      return Intrinsics.compare(var2 & 255, other & 255);
   }

   @InlineOnly
   private static final int compareTo_xj2QHRw/* $FF was: compareTo-xj2QHRw*/(byte arg0, short other) {
      return Intrinsics.compare(arg0 & 255, other & '\uffff');
   }

   @InlineOnly
   private static final int compareTo_WZ4Q5Ns/* $FF was: compareTo-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UnsignedKt.uintCompare(var2, other);
   }

   @InlineOnly
   private static final int compareTo_VKZWuLQ/* $FF was: compareTo-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return UnsignedKt.ulongCompare(var3, other);
   }

   @InlineOnly
   private static final int plus_7apg3OU/* $FF was: plus-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UInt.constructor-impl(var2 + var3);
   }

   @InlineOnly
   private static final int plus_xj2QHRw/* $FF was: plus-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UInt.constructor-impl(var2 + var3);
   }

   @InlineOnly
   private static final int plus_WZ4Q5Ns/* $FF was: plus-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UInt.constructor-impl(var2 + other);
   }

   @InlineOnly
   private static final long plus_VKZWuLQ/* $FF was: plus-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return ULong.constructor-impl(var3 + other);
   }

   @InlineOnly
   private static final int minus_7apg3OU/* $FF was: minus-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UInt.constructor-impl(var2 - var3);
   }

   @InlineOnly
   private static final int minus_xj2QHRw/* $FF was: minus-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UInt.constructor-impl(var2 - var3);
   }

   @InlineOnly
   private static final int minus_WZ4Q5Ns/* $FF was: minus-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UInt.constructor-impl(var2 - other);
   }

   @InlineOnly
   private static final long minus_VKZWuLQ/* $FF was: minus-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return ULong.constructor-impl(var3 - other);
   }

   @InlineOnly
   private static final int times_7apg3OU/* $FF was: times-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UInt.constructor-impl(var2 * var3);
   }

   @InlineOnly
   private static final int times_xj2QHRw/* $FF was: times-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UInt.constructor-impl(var2 * var3);
   }

   @InlineOnly
   private static final int times_WZ4Q5Ns/* $FF was: times-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UInt.constructor-impl(var2 * other);
   }

   @InlineOnly
   private static final long times_VKZWuLQ/* $FF was: times-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return ULong.constructor-impl(var3 * other);
   }

   @InlineOnly
   private static final int div_7apg3OU/* $FF was: div-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UnsignedKt.uintDivide-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int div_xj2QHRw/* $FF was: div-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UnsignedKt.uintDivide-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int div_WZ4Q5Ns/* $FF was: div-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UnsignedKt.uintDivide-J1ME1BU(var2, other);
   }

   @InlineOnly
   private static final long div_VKZWuLQ/* $FF was: div-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return UnsignedKt.ulongDivide-eb3DHEI(var3, other);
   }

   @InlineOnly
   private static final int rem_7apg3OU/* $FF was: rem-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UnsignedKt.uintRemainder-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int rem_xj2QHRw/* $FF was: rem-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UnsignedKt.uintRemainder-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int rem_WZ4Q5Ns/* $FF was: rem-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UnsignedKt.uintRemainder-J1ME1BU(var2, other);
   }

   @InlineOnly
   private static final long rem_VKZWuLQ/* $FF was: rem-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return UnsignedKt.ulongRemainder-eb3DHEI(var3, other);
   }

   @InlineOnly
   private static final int floorDiv_7apg3OU/* $FF was: floorDiv-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      return UnsignedKt.uintDivide-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int floorDiv_xj2QHRw/* $FF was: floorDiv-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      return UnsignedKt.uintDivide-J1ME1BU(var2, var3);
   }

   @InlineOnly
   private static final int floorDiv_WZ4Q5Ns/* $FF was: floorDiv-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UnsignedKt.uintDivide-J1ME1BU(var2, other);
   }

   @InlineOnly
   private static final long floorDiv_VKZWuLQ/* $FF was: floorDiv-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return UnsignedKt.ulongDivide-eb3DHEI(var3, other);
   }

   @InlineOnly
   private static final byte mod_7apg3OU/* $FF was: mod-7apg3OU*/(byte arg0, byte other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & 255);
      var2 = UnsignedKt.uintRemainder-J1ME1BU(var2, var3);
      return constructor-impl((byte)var2);
   }

   @InlineOnly
   private static final short mod_xj2QHRw/* $FF was: mod-xj2QHRw*/(byte arg0, short other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      int var3 = UInt.constructor-impl(other & '\uffff');
      var2 = UnsignedKt.uintRemainder-J1ME1BU(var2, var3);
      return UShort.constructor-impl((short)var2);
   }

   @InlineOnly
   private static final int mod_WZ4Q5Ns/* $FF was: mod-WZ4Q5Ns*/(byte arg0, int other) {
      int var2 = UInt.constructor-impl(arg0 & 255);
      return UnsignedKt.uintRemainder-J1ME1BU(var2, other);
   }

   @InlineOnly
   private static final long mod_VKZWuLQ/* $FF was: mod-VKZWuLQ*/(byte arg0, long other) {
      long var3 = ULong.constructor-impl((long)arg0 & 255L);
      return UnsignedKt.ulongRemainder-eb3DHEI(var3, other);
   }

   @InlineOnly
   private static final byte inc_w2LRezQ/* $FF was: inc-w2LRezQ*/(byte arg0) {
      return constructor-impl((byte)(arg0 + 1));
   }

   @InlineOnly
   private static final byte dec_w2LRezQ/* $FF was: dec-w2LRezQ*/(byte arg0) {
      return constructor-impl((byte)(arg0 + -1));
   }

   @InlineOnly
   private static final UIntRange rangeTo_7apg3OU/* $FF was: rangeTo-7apg3OU*/(byte arg0, byte other) {
      return new UIntRange(UInt.constructor-impl(arg0 & 255), UInt.constructor-impl(other & 255), (DefaultConstructorMarker)null);
   }

   @InlineOnly
   private static final byte and_7apg3OU/* $FF was: and-7apg3OU*/(byte arg0, byte other) {
      return constructor-impl((byte)(arg0 & other));
   }

   @InlineOnly
   private static final byte or_7apg3OU/* $FF was: or-7apg3OU*/(byte arg0, byte other) {
      return constructor-impl((byte)(arg0 | other));
   }

   @InlineOnly
   private static final byte xor_7apg3OU/* $FF was: xor-7apg3OU*/(byte arg0, byte other) {
      return constructor-impl((byte)(arg0 ^ other));
   }

   @InlineOnly
   private static final byte inv_w2LRezQ/* $FF was: inv-w2LRezQ*/(byte arg0) {
      return constructor-impl((byte)(~arg0));
   }

   @InlineOnly
   private static final byte toByte_impl/* $FF was: toByte-impl*/(byte arg0) {
      return arg0;
   }

   @InlineOnly
   private static final short toShort_impl/* $FF was: toShort-impl*/(byte arg0) {
      return (short)((short)arg0 & 255);
   }

   @InlineOnly
   private static final int toInt_impl/* $FF was: toInt-impl*/(byte arg0) {
      return arg0 & 255;
   }

   @InlineOnly
   private static final long toLong_impl/* $FF was: toLong-impl*/(byte arg0) {
      return (long)arg0 & 255L;
   }

   @InlineOnly
   private static final byte toUByte_w2LRezQ/* $FF was: toUByte-w2LRezQ*/(byte arg0) {
      return arg0;
   }

   @InlineOnly
   private static final short toUShort_Mh2AYeg/* $FF was: toUShort-Mh2AYeg*/(byte arg0) {
      return UShort.constructor-impl((short)((short)arg0 & 255));
   }

   @InlineOnly
   private static final int toUInt_pVg5ArA/* $FF was: toUInt-pVg5ArA*/(byte arg0) {
      return UInt.constructor-impl(arg0 & 255);
   }

   @InlineOnly
   private static final long toULong_s_VKNKU/* $FF was: toULong-s-VKNKU*/(byte arg0) {
      return ULong.constructor-impl((long)arg0 & 255L);
   }

   @InlineOnly
   private static final float toFloat_impl/* $FF was: toFloat-impl*/(byte arg0) {
      return (float)(arg0 & 255);
   }

   @InlineOnly
   private static final double toDouble_impl/* $FF was: toDouble-impl*/(byte arg0) {
      return (double)(arg0 & 255);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(byte arg0) {
      return String.valueOf(arg0 & 255);
   }

   @NotNull
   public String toString() {
      return toString-impl(this.data);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(byte arg0) {
      return arg0;
   }

   public int hashCode() {
      return hashCode-impl(this.data);
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(byte arg0, Object other) {
      if (!(other instanceof UByte)) {
         return false;
      } else {
         byte var2 = ((UByte)other).unbox-impl();
         return arg0 == var2;
      }
   }

   public boolean equals(Object other) {
      return equals-impl(this.data, other);
   }

   // $FF: synthetic method
   @PublishedApi
   private UByte(byte data) {
      this.data = data;
   }

   @PublishedApi
   public static byte constructor_impl/* $FF was: constructor-impl*/(byte data) {
      return data;
   }

   // $FF: synthetic method
   public static final UByte box_impl/* $FF was: box-impl*/(byte v) {
      return new UByte(v);
   }

   // $FF: synthetic method
   public final byte unbox_impl/* $FF was: unbox-impl*/() {
      return this.data;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(byte p1, byte p2) {
      return p1 == p2;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"},
      d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
