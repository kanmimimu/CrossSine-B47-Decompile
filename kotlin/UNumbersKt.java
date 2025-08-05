package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b)\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0004\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0007\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\n\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\r\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0004\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0007\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\n\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\r\u001a\u001f\u0010\u0018\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a\u001f\u0010\u0018\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001f\u0010\u0018\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001f\u0010\u0018\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001f\u0010\"\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b#\u0010\u001b\u001a\u001f\u0010\"\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b$\u0010\u001d\u001a\u001f\u0010\"\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b%\u0010\u001f\u001a\u001f\u0010\"\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b&\u0010!\u001a\u0017\u0010'\u001a\u00020\u0002*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010)\u001a\u0017\u0010'\u001a\u00020\u0005*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0007\u001a\u0017\u0010'\u001a\u00020\b*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u0017\u0010'\u001a\u00020\u000b*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u0017\u0010/\u001a\u00020\u0002*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b0\u0010)\u001a\u0017\u0010/\u001a\u00020\u0005*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b1\u0010\u0007\u001a\u0017\u0010/\u001a\u00020\b*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b2\u0010,\u001a\u0017\u0010/\u001a\u00020\u000b*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b3\u0010.\u0082\u0002\u0004\n\u0002\b\u0019¨\u00064"},
   d2 = {"countLeadingZeroBits", "", "Lkotlin/UByte;", "countLeadingZeroBits-7apg3OU", "(B)I", "Lkotlin/UInt;", "countLeadingZeroBits-WZ4Q5Ns", "(I)I", "Lkotlin/ULong;", "countLeadingZeroBits-VKZWuLQ", "(J)I", "Lkotlin/UShort;", "countLeadingZeroBits-xj2QHRw", "(S)I", "countOneBits", "countOneBits-7apg3OU", "countOneBits-WZ4Q5Ns", "countOneBits-VKZWuLQ", "countOneBits-xj2QHRw", "countTrailingZeroBits", "countTrailingZeroBits-7apg3OU", "countTrailingZeroBits-WZ4Q5Ns", "countTrailingZeroBits-VKZWuLQ", "countTrailingZeroBits-xj2QHRw", "rotateLeft", "bitCount", "rotateLeft-LxnNnR4", "(BI)B", "rotateLeft-V7xB4Y4", "(II)I", "rotateLeft-JSWoG40", "(JI)J", "rotateLeft-olVBNx4", "(SI)S", "rotateRight", "rotateRight-LxnNnR4", "rotateRight-V7xB4Y4", "rotateRight-JSWoG40", "rotateRight-olVBNx4", "takeHighestOneBit", "takeHighestOneBit-7apg3OU", "(B)B", "takeHighestOneBit-WZ4Q5Ns", "takeHighestOneBit-VKZWuLQ", "(J)J", "takeHighestOneBit-xj2QHRw", "(S)S", "takeLowestOneBit", "takeLowestOneBit-7apg3OU", "takeLowestOneBit-WZ4Q5Ns", "takeLowestOneBit-VKZWuLQ", "takeLowestOneBit-xj2QHRw", "kotlin-stdlib"}
)
@JvmName(
   name = "UNumbersKt"
)
public final class UNumbersKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countOneBits_WZ4Q5Ns/* $FF was: countOneBits-WZ4Q5Ns*/(int $this$countOneBits) {
      return Integer.bitCount($this$countOneBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countLeadingZeroBits_WZ4Q5Ns/* $FF was: countLeadingZeroBits-WZ4Q5Ns*/(int $this$countLeadingZeroBits) {
      return Integer.numberOfLeadingZeros($this$countLeadingZeroBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countTrailingZeroBits_WZ4Q5Ns/* $FF was: countTrailingZeroBits-WZ4Q5Ns*/(int $this$countTrailingZeroBits) {
      return Integer.numberOfTrailingZeros($this$countTrailingZeroBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int takeHighestOneBit_WZ4Q5Ns/* $FF was: takeHighestOneBit-WZ4Q5Ns*/(int $this$takeHighestOneBit) {
      int var1 = Integer.highestOneBit($this$takeHighestOneBit);
      return UInt.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int takeLowestOneBit_WZ4Q5Ns/* $FF was: takeLowestOneBit-WZ4Q5Ns*/(int $this$takeLowestOneBit) {
      int var1 = Integer.lowestOneBit($this$takeLowestOneBit);
      return UInt.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final int rotateLeft_V7xB4Y4/* $FF was: rotateLeft-V7xB4Y4*/(int $this$rotateLeft, int bitCount) {
      int var2 = Integer.rotateLeft($this$rotateLeft, bitCount);
      return UInt.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final int rotateRight_V7xB4Y4/* $FF was: rotateRight-V7xB4Y4*/(int $this$rotateRight, int bitCount) {
      int var2 = Integer.rotateRight($this$rotateRight, bitCount);
      return UInt.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countOneBits_VKZWuLQ/* $FF was: countOneBits-VKZWuLQ*/(long $this$countOneBits) {
      return Long.bitCount($this$countOneBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countLeadingZeroBits_VKZWuLQ/* $FF was: countLeadingZeroBits-VKZWuLQ*/(long $this$countLeadingZeroBits) {
      return Long.numberOfLeadingZeros($this$countLeadingZeroBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countTrailingZeroBits_VKZWuLQ/* $FF was: countTrailingZeroBits-VKZWuLQ*/(long $this$countTrailingZeroBits) {
      return Long.numberOfTrailingZeros($this$countTrailingZeroBits);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final long takeHighestOneBit_VKZWuLQ/* $FF was: takeHighestOneBit-VKZWuLQ*/(long $this$takeHighestOneBit) {
      long var2 = Long.highestOneBit($this$takeHighestOneBit);
      return ULong.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final long takeLowestOneBit_VKZWuLQ/* $FF was: takeLowestOneBit-VKZWuLQ*/(long $this$takeLowestOneBit) {
      long var2 = Long.lowestOneBit($this$takeLowestOneBit);
      return ULong.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final long rotateLeft_JSWoG40/* $FF was: rotateLeft-JSWoG40*/(long $this$rotateLeft, int bitCount) {
      long var3 = Long.rotateLeft($this$rotateLeft, bitCount);
      return ULong.constructor-impl(var3);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final long rotateRight_JSWoG40/* $FF was: rotateRight-JSWoG40*/(long $this$rotateRight, int bitCount) {
      long var3 = Long.rotateRight($this$rotateRight, bitCount);
      return ULong.constructor-impl(var3);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countOneBits_7apg3OU/* $FF was: countOneBits-7apg3OU*/(byte $this$countOneBits) {
      int var1 = UInt.constructor-impl($this$countOneBits & 255);
      return Integer.bitCount(var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countLeadingZeroBits_7apg3OU/* $FF was: countLeadingZeroBits-7apg3OU*/(byte $this$countLeadingZeroBits) {
      return Integer.numberOfLeadingZeros($this$countLeadingZeroBits & 255) - 24;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countTrailingZeroBits_7apg3OU/* $FF was: countTrailingZeroBits-7apg3OU*/(byte $this$countTrailingZeroBits) {
      return Integer.numberOfTrailingZeros($this$countTrailingZeroBits | 256);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final byte takeHighestOneBit_7apg3OU/* $FF was: takeHighestOneBit-7apg3OU*/(byte $this$takeHighestOneBit) {
      int var1 = $this$takeHighestOneBit & 255;
      var1 = Integer.highestOneBit(var1);
      return UByte.constructor-impl((byte)var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final byte takeLowestOneBit_7apg3OU/* $FF was: takeLowestOneBit-7apg3OU*/(byte $this$takeLowestOneBit) {
      int var1 = $this$takeLowestOneBit & 255;
      var1 = Integer.lowestOneBit(var1);
      return UByte.constructor-impl((byte)var1);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final byte rotateLeft_LxnNnR4/* $FF was: rotateLeft-LxnNnR4*/(byte $this$rotateLeft, int bitCount) {
      byte var2 = NumbersKt.rotateLeft($this$rotateLeft, bitCount);
      return UByte.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final byte rotateRight_LxnNnR4/* $FF was: rotateRight-LxnNnR4*/(byte $this$rotateRight, int bitCount) {
      byte var2 = NumbersKt.rotateRight($this$rotateRight, bitCount);
      return UByte.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countOneBits_xj2QHRw/* $FF was: countOneBits-xj2QHRw*/(short $this$countOneBits) {
      int var1 = UInt.constructor-impl($this$countOneBits & '\uffff');
      return Integer.bitCount(var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countLeadingZeroBits_xj2QHRw/* $FF was: countLeadingZeroBits-xj2QHRw*/(short $this$countLeadingZeroBits) {
      return Integer.numberOfLeadingZeros($this$countLeadingZeroBits & '\uffff') - 16;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final int countTrailingZeroBits_xj2QHRw/* $FF was: countTrailingZeroBits-xj2QHRw*/(short $this$countTrailingZeroBits) {
      return Integer.numberOfTrailingZeros($this$countTrailingZeroBits | 65536);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final short takeHighestOneBit_xj2QHRw/* $FF was: takeHighestOneBit-xj2QHRw*/(short $this$takeHighestOneBit) {
      int var1 = $this$takeHighestOneBit & '\uffff';
      var1 = Integer.highestOneBit(var1);
      return UShort.constructor-impl((short)var1);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class, ExperimentalStdlibApi.class}
   )
   @InlineOnly
   private static final short takeLowestOneBit_xj2QHRw/* $FF was: takeLowestOneBit-xj2QHRw*/(short $this$takeLowestOneBit) {
      int var1 = $this$takeLowestOneBit & '\uffff';
      var1 = Integer.lowestOneBit(var1);
      return UShort.constructor-impl((short)var1);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final short rotateLeft_olVBNx4/* $FF was: rotateLeft-olVBNx4*/(short $this$rotateLeft, int bitCount) {
      short var2 = NumbersKt.rotateLeft($this$rotateLeft, bitCount);
      return UShort.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.6"
   )
   @WasExperimental(
      markerClass = {ExperimentalStdlibApi.class, ExperimentalUnsignedTypes.class}
   )
   @InlineOnly
   private static final short rotateRight_olVBNx4/* $FF was: rotateRight-olVBNx4*/(short $this$rotateRight, int bitCount) {
      short var2 = NumbersKt.rotateRight($this$rotateRight, bitCount);
      return UShort.constructor-impl(var2);
   }
}
