package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""},
   d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}
)
public final class UArraySortingKt {
   @ExperimentalUnsignedTypes
   private static final int partition_4UcCI2c/* $FF was: partition-4UcCI2c*/(byte[] array, int left, int right) {
      int i = left;
      int j = right;
      byte pivot = UByteArray.get-w2LRezQ(array, (left + right) / 2);

      while(i <= j) {
         byte tmp = UByteArray.get-w2LRezQ(array, i);
         if (Intrinsics.compare(tmp & 255, pivot & 255) >= 0) {
            while(true) {
               tmp = UByteArray.get-w2LRezQ(array, j);
               if (Intrinsics.compare(tmp & 255, pivot & 255) <= 0) {
                  if (i <= j) {
                     tmp = UByteArray.get-w2LRezQ(array, i);
                     UByteArray.set-VurrAj0(array, i, UByteArray.get-w2LRezQ(array, j));
                     UByteArray.set-VurrAj0(array, j, tmp);
                     ++i;
                     j += -1;
                  }
                  break;
               }

               j += -1;
            }
         } else {
            ++i;
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_4UcCI2c/* $FF was: quickSort-4UcCI2c*/(byte[] array, int left, int right) {
      int index = partition-4UcCI2c(array, left, right);
      if (left < index - 1) {
         quickSort-4UcCI2c(array, left, index - 1);
      }

      if (index < right) {
         quickSort-4UcCI2c(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_Aa5vz7o/* $FF was: partition-Aa5vz7o*/(short[] array, int left, int right) {
      int i = left;
      int j = right;
      short pivot = UShortArray.get-Mh2AYeg(array, (left + right) / 2);

      while(i <= j) {
         short tmp = UShortArray.get-Mh2AYeg(array, i);
         if (Intrinsics.compare(tmp & '\uffff', pivot & '\uffff') >= 0) {
            while(true) {
               tmp = UShortArray.get-Mh2AYeg(array, j);
               if (Intrinsics.compare(tmp & '\uffff', pivot & '\uffff') <= 0) {
                  if (i <= j) {
                     tmp = UShortArray.get-Mh2AYeg(array, i);
                     UShortArray.set-01HTLdE(array, i, UShortArray.get-Mh2AYeg(array, j));
                     UShortArray.set-01HTLdE(array, j, tmp);
                     ++i;
                     j += -1;
                  }
                  break;
               }

               j += -1;
            }
         } else {
            ++i;
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_Aa5vz7o/* $FF was: quickSort-Aa5vz7o*/(short[] array, int left, int right) {
      int index = partition-Aa5vz7o(array, left, right);
      if (left < index - 1) {
         quickSort-Aa5vz7o(array, left, index - 1);
      }

      if (index < right) {
         quickSort-Aa5vz7o(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_oBK06Vg/* $FF was: partition-oBK06Vg*/(int[] array, int left, int right) {
      int i = left;
      int j = right;
      int pivot = UIntArray.get-pVg5ArA(array, (left + right) / 2);

      while(i <= j) {
         int tmp = UIntArray.get-pVg5ArA(array, i);
         if (UnsignedKt.uintCompare(tmp, pivot) >= 0) {
            while(true) {
               tmp = UIntArray.get-pVg5ArA(array, j);
               if (UnsignedKt.uintCompare(tmp, pivot) <= 0) {
                  if (i <= j) {
                     tmp = UIntArray.get-pVg5ArA(array, i);
                     UIntArray.set-VXSXFK8(array, i, UIntArray.get-pVg5ArA(array, j));
                     UIntArray.set-VXSXFK8(array, j, tmp);
                     ++i;
                     j += -1;
                  }
                  break;
               }

               j += -1;
            }
         } else {
            ++i;
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_oBK06Vg/* $FF was: quickSort-oBK06Vg*/(int[] array, int left, int right) {
      int index = partition-oBK06Vg(array, left, right);
      if (left < index - 1) {
         quickSort-oBK06Vg(array, left, index - 1);
      }

      if (index < right) {
         quickSort-oBK06Vg(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition__nroSd4/* $FF was: partition--nroSd4*/(long[] array, int left, int right) {
      int i = left;
      int j = right;
      long pivot = ULongArray.get-s-VKNKU(array, (left + right) / 2);

      while(i <= j) {
         long tmp = ULongArray.get-s-VKNKU(array, i);
         if (UnsignedKt.ulongCompare(tmp, pivot) >= 0) {
            while(true) {
               tmp = ULongArray.get-s-VKNKU(array, j);
               if (UnsignedKt.ulongCompare(tmp, pivot) <= 0) {
                  if (i <= j) {
                     tmp = ULongArray.get-s-VKNKU(array, i);
                     ULongArray.set-k8EXiF4(array, i, ULongArray.get-s-VKNKU(array, j));
                     ULongArray.set-k8EXiF4(array, j, tmp);
                     ++i;
                     j += -1;
                  }
                  break;
               }

               j += -1;
            }
         } else {
            ++i;
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort__nroSd4/* $FF was: quickSort--nroSd4*/(long[] array, int left, int right) {
      int index = partition--nroSd4(array, left, right);
      if (left < index - 1) {
         quickSort--nroSd4(array, left, index - 1);
      }

      if (index < right) {
         quickSort--nroSd4(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_4UcCI2c/* $FF was: sortArray-4UcCI2c*/(@NotNull byte[] array, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter(array, "array");
      quickSort-4UcCI2c(array, fromIndex, toIndex - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_Aa5vz7o/* $FF was: sortArray-Aa5vz7o*/(@NotNull short[] array, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter(array, "array");
      quickSort-Aa5vz7o(array, fromIndex, toIndex - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_oBK06Vg/* $FF was: sortArray-oBK06Vg*/(@NotNull int[] array, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter(array, "array");
      quickSort-oBK06Vg(array, fromIndex, toIndex - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray__nroSd4/* $FF was: sortArray--nroSd4*/(@NotNull long[] array, int fromIndex, int toIndex) {
      Intrinsics.checkNotNullParameter(array, "array");
      quickSort--nroSd4(array, fromIndex, toIndex - 1);
   }
}
