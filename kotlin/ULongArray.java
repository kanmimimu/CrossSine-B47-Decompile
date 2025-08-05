package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt;
import kotlin.collections.ULongIterator;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@JvmInline
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\f\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u00012B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001a\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003¢\u0006\u0004\b\u001a\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020\u0004HÖ\u0001¢\u0006\u0004\b!\u0010\u000bJ\u000f\u0010\"\u001a\u00020\u000fH\u0016¢\u0006\u0004\b#\u0010$J\u0019\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020&H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(J#\u0010)\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b,\u0010-J\u0010\u0010.\u001a\u00020/HÖ\u0001¢\u0006\u0004\b0\u00101R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\r\u0088\u0001\u0007\u0092\u0001\u00020\bø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u00063"},
   d2 = {"Lkotlin/ULongArray;", "", "Lkotlin/ULong;", "size", "", "constructor-impl", "(I)[J", "storage", "", "([J)[J", "getSize-impl", "([J)I", "getStorage$annotations", "()V", "contains", "", "element", "contains-VKZWuLQ", "([JJ)Z", "containsAll", "elements", "containsAll-impl", "([JLjava/util/Collection;)Z", "equals", "other", "", "equals-impl", "([JLjava/lang/Object;)Z", "get", "index", "get-s-VKNKU", "([JI)J", "hashCode", "hashCode-impl", "isEmpty", "isEmpty-impl", "([J)Z", "iterator", "", "iterator-impl", "([J)Ljava/util/Iterator;", "set", "", "value", "set-k8EXiF4", "([JIJ)V", "toString", "", "toString-impl", "([J)Ljava/lang/String;", "Iterator", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class ULongArray implements Collection, KMappedMarker {
   @NotNull
   private final long[] storage;

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void getStorage$annotations() {
   }

   @NotNull
   public static long[] constructor_impl/* $FF was: constructor-impl*/(int size) {
      long[] var1 = constructor-impl(new long[size]);
      return var1;
   }

   public static final long get_s_VKNKU/* $FF was: get-s-VKNKU*/(long[] arg0, int index) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      return ULong.constructor-impl(arg0[index]);
   }

   public static final void set_k8EXiF4/* $FF was: set-k8EXiF4*/(long[] arg0, int index, long value) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      arg0[index] = value;
   }

   public static int getSize_impl/* $FF was: getSize-impl*/(long[] arg0) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      return arg0.length;
   }

   public int getSize() {
      return getSize-impl(this.storage);
   }

   @NotNull
   public static java.util.Iterator iterator_impl/* $FF was: iterator-impl*/(long[] arg0) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      return new Iterator(arg0);
   }

   @NotNull
   public java.util.Iterator iterator() {
      return iterator-impl(this.storage);
   }

   public static boolean contains_VKZWuLQ/* $FF was: contains-VKZWuLQ*/(long[] arg0, long element) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      return ArraysKt.contains(arg0, element);
   }

   public boolean contains_VKZWuLQ/* $FF was: contains-VKZWuLQ*/(long element) {
      return contains-VKZWuLQ(this.storage, element);
   }

   public static boolean containsAll_impl/* $FF was: containsAll-impl*/(long[] arg0, @NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      Intrinsics.checkNotNullParameter(elements, "elements");
      Iterable $this$all$iv = (Iterable)elements;
      int $i$f$all = 0;
      boolean var10000;
      if (((Collection)$this$all$iv).isEmpty()) {
         var10000 = true;
      } else {
         java.util.Iterator var4 = $this$all$iv.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = true;
               break;
            }

            Object element$iv = var4.next();
            int var7 = 0;
            if (!(element$iv instanceof ULong) || !ArraysKt.contains(arg0, ((ULong)element$iv).unbox-impl())) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }

   public boolean containsAll(@NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      return containsAll-impl(this.storage, elements);
   }

   public static boolean isEmpty_impl/* $FF was: isEmpty-impl*/(long[] arg0) {
      Intrinsics.checkNotNullParameter(arg0, "arg0");
      return arg0.length == 0;
   }

   public boolean isEmpty() {
      return isEmpty-impl(this.storage);
   }

   public static String toString_impl/* $FF was: toString-impl*/(long[] arg0) {
      return "ULongArray(storage=" + Arrays.toString(arg0) + ')';
   }

   public String toString() {
      return toString-impl(this.storage);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(long[] arg0) {
      return Arrays.hashCode(arg0);
   }

   public int hashCode() {
      return hashCode-impl(this.storage);
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(long[] arg0, Object other) {
      if (!(other instanceof ULongArray)) {
         return false;
      } else {
         long[] var2 = ((ULongArray)other).unbox-impl();
         return Intrinsics.areEqual((Object)arg0, (Object)var2);
      }
   }

   public boolean equals(Object other) {
      return equals-impl(this.storage, other);
   }

   public boolean add_VKZWuLQ/* $FF was: add-VKZWuLQ*/(long element) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection elements) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object element) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection elements) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection elements) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   // $FF: synthetic method
   @PublishedApi
   private ULongArray(long[] storage) {
      this.storage = storage;
   }

   @PublishedApi
   @NotNull
   public static long[] constructor_impl/* $FF was: constructor-impl*/(@NotNull long[] storage) {
      Intrinsics.checkNotNullParameter(storage, "storage");
      return storage;
   }

   // $FF: synthetic method
   public static final ULongArray box_impl/* $FF was: box-impl*/(long[] v) {
      return new ULongArray(v);
   }

   // $FF: synthetic method
   public final long[] unbox_impl/* $FF was: unbox-impl*/() {
      return this.storage;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(long[] p1, long[] p2) {
      return Intrinsics.areEqual((Object)p1, (Object)p2);
   }

   public Object[] toArray(Object[] array) {
      Intrinsics.checkNotNullParameter(array, "array");
      return CollectionToArray.toArray(this, array);
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0015\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\r"},
      d2 = {"Lkotlin/ULongArray$Iterator;", "Lkotlin/collections/ULongIterator;", "array", "", "([J)V", "index", "", "hasNext", "", "nextULong", "Lkotlin/ULong;", "nextULong-s-VKNKU", "()J", "kotlin-stdlib"}
   )
   private static final class Iterator extends ULongIterator {
      @NotNull
      private final long[] array;
      private int index;

      public Iterator(@NotNull long[] array) {
         Intrinsics.checkNotNullParameter(array, "array");
         super();
         this.array = array;
      }

      public boolean hasNext() {
         return this.index < this.array.length;
      }

      public long nextULong_s_VKNKU/* $FF was: nextULong-s-VKNKU*/() {
         if (this.index < this.array.length) {
            long[] var10000 = this.array;
            int var2 = this.index++;
            return ULong.constructor-impl(var10000[var2]);
         } else {
            throw new NoSuchElementException(String.valueOf(this.index));
         }
      }
   }
}
