package kotlin.text;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.WasExperimental;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "},
   d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}
)
@JvmName(
   name = "UStringsKt"
)
public final class UStringsKt {
   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final String toString_LxnNnR4/* $FF was: toString-LxnNnR4*/(byte $this$toString, int radix) {
      int var2 = $this$toString & 255;
      String var3 = Integer.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkNotNullExpressionValue(var3, "toString(this, checkRadix(radix))");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final String toString_olVBNx4/* $FF was: toString-olVBNx4*/(short $this$toString, int radix) {
      int var2 = $this$toString & '\uffff';
      String var3 = Integer.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkNotNullExpressionValue(var3, "toString(this, checkRadix(radix))");
      return var3;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final String toString_V7xB4Y4/* $FF was: toString-V7xB4Y4*/(int $this$toString, int radix) {
      long var2 = (long)$this$toString & 4294967295L;
      String var4 = Long.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkNotNullExpressionValue(var4, "toString(this, checkRadix(radix))");
      return var4;
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @NotNull
   public static final String toString_JSWoG40/* $FF was: toString-JSWoG40*/(long $this$toString, int radix) {
      return UnsignedKt.ulongToString($this$toString, CharsKt.checkRadix(radix));
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte toUByte(@NotNull String $this$toUByte) {
      Intrinsics.checkNotNullParameter($this$toUByte, "<this>");
      UByte var1 = toUByteOrNull($this$toUByte);
      if (var1 == null) {
         StringsKt.numberFormatError($this$toUByte);
         throw new KotlinNothingValueException();
      } else {
         return var1.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final byte toUByte(@NotNull String $this$toUByte, int radix) {
      Intrinsics.checkNotNullParameter($this$toUByte, "<this>");
      UByte var2 = toUByteOrNull($this$toUByte, radix);
      if (var2 == null) {
         StringsKt.numberFormatError($this$toUByte);
         throw new KotlinNothingValueException();
      } else {
         return var2.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short toUShort(@NotNull String $this$toUShort) {
      Intrinsics.checkNotNullParameter($this$toUShort, "<this>");
      UShort var1 = toUShortOrNull($this$toUShort);
      if (var1 == null) {
         StringsKt.numberFormatError($this$toUShort);
         throw new KotlinNothingValueException();
      } else {
         return var1.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final short toUShort(@NotNull String $this$toUShort, int radix) {
      Intrinsics.checkNotNullParameter($this$toUShort, "<this>");
      UShort var2 = toUShortOrNull($this$toUShort, radix);
      if (var2 == null) {
         StringsKt.numberFormatError($this$toUShort);
         throw new KotlinNothingValueException();
      } else {
         return var2.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int toUInt(@NotNull String $this$toUInt) {
      Intrinsics.checkNotNullParameter($this$toUInt, "<this>");
      UInt var1 = toUIntOrNull($this$toUInt);
      if (var1 == null) {
         StringsKt.numberFormatError($this$toUInt);
         throw new KotlinNothingValueException();
      } else {
         return var1.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final int toUInt(@NotNull String $this$toUInt, int radix) {
      Intrinsics.checkNotNullParameter($this$toUInt, "<this>");
      UInt var2 = toUIntOrNull($this$toUInt, radix);
      if (var2 == null) {
         StringsKt.numberFormatError($this$toUInt);
         throw new KotlinNothingValueException();
      } else {
         return var2.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long toULong(@NotNull String $this$toULong) {
      Intrinsics.checkNotNullParameter($this$toULong, "<this>");
      ULong var1 = toULongOrNull($this$toULong);
      if (var1 == null) {
         StringsKt.numberFormatError($this$toULong);
         throw new KotlinNothingValueException();
      } else {
         return var1.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   public static final long toULong(@NotNull String $this$toULong, int radix) {
      Intrinsics.checkNotNullParameter($this$toULong, "<this>");
      ULong var2 = toULongOrNull($this$toULong, radix);
      if (var2 == null) {
         StringsKt.numberFormatError($this$toULong);
         throw new KotlinNothingValueException();
      } else {
         return var2.unbox-impl();
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull) {
      Intrinsics.checkNotNullParameter($this$toUByteOrNull, "<this>");
      return toUByteOrNull($this$toUByteOrNull, 10);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull, int radix) {
      Intrinsics.checkNotNullParameter($this$toUByteOrNull, "<this>");
      UInt var3 = toUIntOrNull($this$toUByteOrNull, radix);
      if (var3 == null) {
         return null;
      } else {
         int var2 = var3.unbox-impl();
         byte var5 = -1;
         int var4 = UInt.constructor-impl(var5 & 255);
         return UnsignedKt.uintCompare(var2, var4) > 0 ? null : UByte.box-impl(UByte.constructor-impl((byte)var2));
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull) {
      Intrinsics.checkNotNullParameter($this$toUShortOrNull, "<this>");
      return toUShortOrNull($this$toUShortOrNull, 10);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull, int radix) {
      Intrinsics.checkNotNullParameter($this$toUShortOrNull, "<this>");
      UInt var3 = toUIntOrNull($this$toUShortOrNull, radix);
      if (var3 == null) {
         return null;
      } else {
         int var2 = var3.unbox-impl();
         byte var5 = -1;
         int var4 = UInt.constructor-impl(var5 & '\uffff');
         return UnsignedKt.uintCompare(var2, var4) > 0 ? null : UShort.box-impl(UShort.constructor-impl((short)var2));
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull) {
      Intrinsics.checkNotNullParameter($this$toUIntOrNull, "<this>");
      return toUIntOrNull($this$toUIntOrNull, 10);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull, int radix) {
      Intrinsics.checkNotNullParameter($this$toUIntOrNull, "<this>");
      CharsKt.checkRadix(radix);
      int length = $this$toUIntOrNull.length();
      if (length == 0) {
         return null;
      } else {
         int limit = -1;
         int start = 0;
         char firstChar = $this$toUIntOrNull.charAt(0);
         if (Intrinsics.compare(firstChar, 48) < 0) {
            if (length == 1 || firstChar != '+') {
               return null;
            }

            start = 1;
         } else {
            start = 0;
         }

         int limitForMaxRadix = 119304647;
         int limitBeforeMul = limitForMaxRadix;
         int uradix = UInt.constructor-impl(radix);
         int result = 0;
         int var10 = start;

         while(var10 < length) {
            int i = var10++;
            int digit = CharsKt.digitOf($this$toUIntOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            if (UnsignedKt.uintCompare(result, limitBeforeMul) > 0) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               limitBeforeMul = UnsignedKt.uintDivide-J1ME1BU(limit, uradix);
               if (UnsignedKt.uintCompare(result, limitBeforeMul) > 0) {
                  return null;
               }
            }

            result = UInt.constructor-impl(result * uradix);
            int var14 = UInt.constructor-impl(digit);
            result = UInt.constructor-impl(result + var14);
            if (UnsignedKt.uintCompare(result, result) < 0) {
               return null;
            }
         }

         return UInt.box-impl(result);
      }
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull) {
      Intrinsics.checkNotNullParameter($this$toULongOrNull, "<this>");
      return toULongOrNull($this$toULongOrNull, 10);
   }

   @SinceKotlin(
      version = "1.5"
   )
   @WasExperimental(
      markerClass = {ExperimentalUnsignedTypes.class}
   )
   @Nullable
   public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull, int radix) {
      Intrinsics.checkNotNullParameter($this$toULongOrNull, "<this>");
      CharsKt.checkRadix(radix);
      int length = $this$toULongOrNull.length();
      if (length == 0) {
         return null;
      } else {
         long limit = -1L;
         int start = 0;
         char firstChar = $this$toULongOrNull.charAt(0);
         if (Intrinsics.compare(firstChar, 48) < 0) {
            if (length == 1 || firstChar != '+') {
               return null;
            }

            start = 1;
         } else {
            start = 0;
         }

         long limitForMaxRadix = 512409557603043100L;
         long limitBeforeMul = limitForMaxRadix;
         long uradix = ULong.constructor-impl((long)radix);
         long result = 0L;
         int var15 = start;

         while(var15 < length) {
            int i = var15++;
            int digit = CharsKt.digitOf($this$toULongOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            if (UnsignedKt.ulongCompare(result, limitBeforeMul) > 0) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               limitBeforeMul = UnsignedKt.ulongDivide-eb3DHEI(limit, uradix);
               if (UnsignedKt.ulongCompare(result, limitBeforeMul) > 0) {
                  return null;
               }
            }

            result = ULong.constructor-impl(result * uradix);
            int var20 = UInt.constructor-impl(digit);
            long var21 = ULong.constructor-impl((long)var20 & 4294967295L);
            result = ULong.constructor-impl(result + var21);
            if (UnsignedKt.ulongCompare(result, result) < 0) {
               return null;
            }
         }

         return ULong.box-impl(result);
      }
   }
}
