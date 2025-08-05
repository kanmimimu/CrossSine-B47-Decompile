package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007¢\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b¢\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "CollectionToArray"
)
public final class CollectionToArray {
   @NotNull
   private static final Object[] EMPTY;
   private static final int MAX_SIZE = 2147483645;

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection collection) {
      Intrinsics.checkNotNullParameter(collection, "collection");
      int $i$f$toArrayImpl = 0;
      int size$iv = collection.size();
      Object[] var10000;
      if (size$iv == 0) {
         int var3 = 0;
         var10000 = EMPTY;
      } else {
         Iterator iter$iv = collection.iterator();
         if (!iter$iv.hasNext()) {
            int result = 0;
            var10000 = EMPTY;
         } else {
            int var5 = 0;
            Object[] result$iv = new Object[size$iv];
            var5 = 0;

            while(true) {
               int newSize$iv = var5++;
               result$iv[newSize$iv] = iter$iv.next();
               if (var5 >= result$iv.length) {
                  if (!iter$iv.hasNext()) {
                     var10000 = result$iv;
                     break;
                  }

                  newSize$iv = var5 * 3 + 1 >>> 1;
                  if (newSize$iv <= var5) {
                     if (var5 >= 2147483645) {
                        throw new OutOfMemoryError();
                     }

                     newSize$iv = 2147483645;
                  }

                  Object[] var7 = Arrays.copyOf(result$iv, newSize$iv);
                  Intrinsics.checkNotNullExpressionValue(var7, "copyOf(result, newSize)");
                  result$iv = var7;
               } else if (!iter$iv.hasNext()) {
                  int var10 = 0;
                  Object[] var11 = Arrays.copyOf(result$iv, var5);
                  Intrinsics.checkNotNullExpressionValue(var11, "copyOf(result, size)");
                  var10000 = var11;
                  break;
               }
            }
         }
      }

      return var10000;
   }

   @JvmName(
      name = "toArray"
   )
   @NotNull
   public static final Object[] toArray(@NotNull Collection collection, @Nullable Object[] a) {
      Intrinsics.checkNotNullParameter(collection, "collection");
      if (a == null) {
         throw new NullPointerException();
      } else {
         int $i$f$toArrayImpl = 0;
         int size$iv = collection.size();
         Object[] var10000;
         if (size$iv == 0) {
            int var4 = 0;
            if (a.length > 0) {
               a[0] = null;
            }

            var10000 = a;
         } else {
            Iterator iter$iv = collection.iterator();
            if (!iter$iv.hasNext()) {
               int var13 = 0;
               if (a.length > 0) {
                  a[0] = null;
               }

               var10000 = a;
            } else {
               int var6 = 0;
               if (size$iv <= a.length) {
                  var10000 = a;
               } else {
                  Object var7 = Array.newInstance(a.getClass().getComponentType(), size$iv);
                  if (var7 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                  }

                  var10000 = var7;
               }

               Object[] result$iv = var10000;
               int i$iv = 0;

               while(true) {
                  var6 = i$iv++;
                  result$iv[var6] = iter$iv.next();
                  if (i$iv >= result$iv.length) {
                     if (!iter$iv.hasNext()) {
                        var10000 = result$iv;
                        break;
                     }

                     var6 = i$iv * 3 + 1 >>> 1;
                     if (var6 <= i$iv) {
                        if (i$iv >= 2147483645) {
                           throw new OutOfMemoryError();
                        }

                        var6 = 2147483645;
                     }

                     Object[] var17 = Arrays.copyOf(result$iv, var6);
                     Intrinsics.checkNotNullExpressionValue(var17, "copyOf(result, newSize)");
                     result$iv = var17;
                  } else if (!iter$iv.hasNext()) {
                     int var11 = 0;
                     if (result$iv == a) {
                        a[i$iv] = null;
                        var10000 = a;
                     } else {
                        Object[] var12 = Arrays.copyOf(result$iv, i$iv);
                        Intrinsics.checkNotNullExpressionValue(var12, "copyOf(result, size)");
                        var10000 = var12;
                     }
                     break;
                  }
               }
            }
         }

         return var10000;
      }
   }

   private static final Object[] toArrayImpl(Collection collection, Function0 empty, Function1 alloc, Function2 trim) {
      int $i$f$toArrayImpl = 0;
      int size = collection.size();
      if (size == 0) {
         return empty.invoke();
      } else {
         Iterator iter = collection.iterator();
         if (!iter.hasNext()) {
            return empty.invoke();
         } else {
            Object[] result = alloc.invoke(size);
            int i = 0;

            while(true) {
               int newSize = i++;
               result[newSize] = iter.next();
               if (i >= result.length) {
                  if (!iter.hasNext()) {
                     return result;
                  }

                  newSize = i * 3 + 1 >>> 1;
                  if (newSize <= i) {
                     if (i >= 2147483645) {
                        throw new OutOfMemoryError();
                     }

                     newSize = 2147483645;
                  }

                  Object[] var10 = Arrays.copyOf(result, newSize);
                  Intrinsics.checkNotNullExpressionValue(var10, "copyOf(result, newSize)");
                  result = var10;
               } else if (!iter.hasNext()) {
                  return trim.invoke(result, i);
               }
            }
         }
      }
   }

   static {
      int $i$f$emptyArray = 0;
      EMPTY = new Object[0];
   }
}
