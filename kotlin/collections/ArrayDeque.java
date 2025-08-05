package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\b\u0007\u0018\u0000 P*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001PB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b¢\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000¢\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000¢\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010>\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u0010?\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u0016\u0010@\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010A\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010.J\u000b\u0010B\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010C\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u000b\u0010D\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010E\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010F\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010G\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010HJ\u0017\u0010I\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0000¢\u0006\u0004\bJ\u0010KJ)\u0010I\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0000¢\u0006\u0004\bJ\u0010NJ\u0015\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0016¢\u0006\u0002\u0010KJ'\u0010O\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0016¢\u0006\u0002\u0010NR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006Q"},
   d2 = {"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "testToArray", "testToArray$kotlin_stdlib", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toArray", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.4"
)
@WasExperimental(
   markerClass = {ExperimentalStdlibApi.class}
)
public final class ArrayDeque extends AbstractMutableList {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   private int head;
   @NotNull
   private Object[] elementData;
   private int size;
   @NotNull
   private static final Object[] emptyElementData;
   private static final int maxArraySize = 2147483639;
   private static final int defaultMinCapacity = 10;

   public int getSize() {
      return this.size;
   }

   public ArrayDeque(int initialCapacity) {
      Object[] var10001;
      if (initialCapacity == 0) {
         var10001 = emptyElementData;
      } else {
         if (initialCapacity <= 0) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Illegal Capacity: ", initialCapacity));
         }

         var10001 = new Object[initialCapacity];
      }

      this.elementData = var10001;
   }

   public ArrayDeque() {
      this.elementData = emptyElementData;
   }

   public ArrayDeque(@NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      super();
      int $i$f$toTypedArray = 0;
      Object[] var5 = elements.toArray(new Object[0]);
      if (var5 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         this.elementData = var5;
         this.size = this.elementData.length;
         if (this.elementData.length == 0) {
            this.elementData = emptyElementData;
         }

      }
   }

   private final void ensureCapacity(int minCapacity) {
      if (minCapacity < 0) {
         throw new IllegalStateException("Deque is too big.");
      } else if (minCapacity > this.elementData.length) {
         if (this.elementData == emptyElementData) {
            this.elementData = new Object[RangesKt.coerceAtLeast(minCapacity, 10)];
         } else {
            int newCapacity = Companion.newCapacity$kotlin_stdlib(this.elementData.length, minCapacity);
            this.copyElements(newCapacity);
         }
      }
   }

   private final void copyElements(int newCapacity) {
      Object[] newElements = new Object[newCapacity];
      ArraysKt.copyInto(this.elementData, newElements, 0, this.head, this.elementData.length);
      ArraysKt.copyInto(this.elementData, newElements, this.elementData.length - this.head, 0, this.head);
      this.head = 0;
      this.elementData = newElements;
   }

   @InlineOnly
   private final Object internalGet(int internalIndex) {
      return access$getElementData$p(this)[internalIndex];
   }

   private final int positiveMod(int index) {
      return index >= this.elementData.length ? index - this.elementData.length : index;
   }

   private final int negativeMod(int index) {
      return index < 0 ? index + this.elementData.length : index;
   }

   @InlineOnly
   private final int internalIndex(int index) {
      return access$positiveMod(this, access$getHead$p(this) + index);
   }

   private final int incremented(int index) {
      return index == ArraysKt.getLastIndex(this.elementData) ? 0 : index + 1;
   }

   private final int decremented(int index) {
      return index == 0 ? ArraysKt.getLastIndex(this.elementData) : index - 1;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public final Object first() {
      if (this.isEmpty()) {
         throw new NoSuchElementException("ArrayDeque is empty.");
      } else {
         int var2 = this.head;
         return access$getElementData$p(this)[var2];
      }
   }

   @Nullable
   public final Object firstOrNull() {
      Object var10000;
      if (this.isEmpty()) {
         var10000 = null;
      } else {
         int var2 = this.head;
         var10000 = access$getElementData$p(this)[var2];
      }

      return var10000;
   }

   public final Object last() {
      if (this.isEmpty()) {
         throw new NoSuchElementException("ArrayDeque is empty.");
      } else {
         int var3 = CollectionsKt.getLastIndex(this);
         int var2 = access$positiveMod(this, access$getHead$p(this) + var3);
         return access$getElementData$p(this)[var2];
      }
   }

   @Nullable
   public final Object lastOrNull() {
      Object var10000;
      if (this.isEmpty()) {
         var10000 = null;
      } else {
         int var3 = CollectionsKt.getLastIndex(this);
         int var2 = access$positiveMod(this, access$getHead$p(this) + var3);
         var10000 = access$getElementData$p(this)[var2];
      }

      return var10000;
   }

   public final void addFirst(Object element) {
      this.ensureCapacity(this.size() + 1);
      this.head = this.decremented(this.head);
      this.elementData[this.head] = element;
      this.size = this.size() + 1;
   }

   public final void addLast(Object element) {
      this.ensureCapacity(this.size() + 1);
      Object[] var10000 = this.elementData;
      int var3 = this.size();
      var10000[access$positiveMod(this, access$getHead$p(this) + var3)] = element;
      this.size = this.size() + 1;
   }

   public final Object removeFirst() {
      if (this.isEmpty()) {
         throw new NoSuchElementException("ArrayDeque is empty.");
      } else {
         int var3 = this.head;
         Object element = access$getElementData$p(this)[var3];
         this.elementData[this.head] = null;
         this.head = this.incremented(this.head);
         this.size = this.size() - 1;
         return element;
      }
   }

   @Nullable
   public final Object removeFirstOrNull() {
      return this.isEmpty() ? null : this.removeFirst();
   }

   public final Object removeLast() {
      if (this.isEmpty()) {
         throw new NoSuchElementException("ArrayDeque is empty.");
      } else {
         int var3 = CollectionsKt.getLastIndex(this);
         int internalLastIndex = access$positiveMod(this, access$getHead$p(this) + var3);
         Object element = access$getElementData$p(this)[internalLastIndex];
         this.elementData[internalLastIndex] = null;
         this.size = this.size() - 1;
         return element;
      }
   }

   @Nullable
   public final Object removeLastOrNull() {
      return this.isEmpty() ? null : this.removeLast();
   }

   public boolean add(Object element) {
      this.addLast(element);
      return true;
   }

   public void add(int index, Object element) {
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, this.size());
      if (index == this.size()) {
         this.addLast(element);
      } else if (index == 0) {
         this.addFirst(element);
      } else {
         this.ensureCapacity(this.size() + 1);
         int internalIndex = access$positiveMod(this, access$getHead$p(this) + index);
         if (index < this.size() + 1 >> 1) {
            int decrementedInternalIndex = this.decremented(internalIndex);
            int decrementedHead = this.decremented(this.head);
            if (decrementedInternalIndex >= this.head) {
               this.elementData[decrementedHead] = this.elementData[this.head];
               ArraysKt.copyInto(this.elementData, this.elementData, this.head, this.head + 1, decrementedInternalIndex + 1);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, this.head - 1, this.head, this.elementData.length);
               this.elementData[this.elementData.length - 1] = this.elementData[0];
               ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, decrementedInternalIndex + 1);
            }

            this.elementData[decrementedInternalIndex] = element;
            this.head = decrementedHead;
         } else {
            int var6 = this.size();
            int tail = access$positiveMod(this, access$getHead$p(this) + var6);
            if (internalIndex < tail) {
               ArraysKt.copyInto(this.elementData, this.elementData, internalIndex + 1, internalIndex, tail);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, tail);
               this.elementData[0] = this.elementData[this.elementData.length - 1];
               ArraysKt.copyInto(this.elementData, this.elementData, internalIndex + 1, internalIndex, this.elementData.length - 1);
            }

            this.elementData[internalIndex] = element;
         }

         this.size = this.size() + 1;
      }
   }

   private final void copyCollectionElements(int internalIndex, Collection elements) {
      Iterator iterator = elements.iterator();
      int var4 = internalIndex;

      int index;
      for(int var5 = this.elementData.length; var4 < var5; this.elementData[index] = iterator.next()) {
         index = var4++;
         if (!iterator.hasNext()) {
            break;
         }
      }

      var4 = 0;

      for(int var8 = this.head; var4 < var8; this.elementData[index] = iterator.next()) {
         index = var4++;
         if (!iterator.hasNext()) {
            break;
         }
      }

      this.size = this.size() + elements.size();
   }

   public boolean addAll(@NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      if (elements.isEmpty()) {
         return false;
      } else {
         this.ensureCapacity(this.size() + elements.size());
         int var3 = this.size();
         this.copyCollectionElements(access$positiveMod(this, access$getHead$p(this) + var3), elements);
         return true;
      }
   }

   public boolean addAll(int index, @NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, this.size());
      if (elements.isEmpty()) {
         return false;
      } else if (index == this.size()) {
         return this.addAll(elements);
      } else {
         this.ensureCapacity(this.size() + elements.size());
         int elementsSize = this.size();
         int tail = access$positiveMod(this, access$getHead$p(this) + elementsSize);
         int internalIndex = access$positiveMod(this, access$getHead$p(this) + index);
         elementsSize = elements.size();
         if (index < this.size() + 1 >> 1) {
            int shiftedHead = this.head - elementsSize;
            if (internalIndex >= this.head) {
               if (shiftedHead >= 0) {
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedHead, this.head, internalIndex);
               } else {
                  shiftedHead += this.elementData.length;
                  int elementsToShift = internalIndex - this.head;
                  int shiftToBack = this.elementData.length - shiftedHead;
                  if (shiftToBack >= elementsToShift) {
                     ArraysKt.copyInto(this.elementData, this.elementData, shiftedHead, this.head, internalIndex);
                  } else {
                     ArraysKt.copyInto(this.elementData, this.elementData, shiftedHead, this.head, this.head + shiftToBack);
                     ArraysKt.copyInto(this.elementData, this.elementData, 0, this.head + shiftToBack, internalIndex);
                  }
               }
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, shiftedHead, this.head, this.elementData.length);
               if (elementsSize >= internalIndex) {
                  ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - elementsSize, 0, internalIndex);
               } else {
                  ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - elementsSize, 0, elementsSize);
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, elementsSize, internalIndex);
               }
            }

            this.head = shiftedHead;
            this.copyCollectionElements(this.negativeMod(internalIndex - elementsSize), elements);
         } else {
            int shiftedInternalIndex = internalIndex + elementsSize;
            if (internalIndex < tail) {
               if (tail + elementsSize <= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedInternalIndex, internalIndex, tail);
               } else if (shiftedInternalIndex >= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedInternalIndex - this.elementData.length, internalIndex, tail);
               } else {
                  int shiftToFront = tail + elementsSize - this.elementData.length;
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, tail - shiftToFront, tail);
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedInternalIndex, internalIndex, tail - shiftToFront);
               }
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, elementsSize, 0, tail);
               if (shiftedInternalIndex >= this.elementData.length) {
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedInternalIndex - this.elementData.length, internalIndex, this.elementData.length);
               } else {
                  ArraysKt.copyInto(this.elementData, this.elementData, 0, this.elementData.length - elementsSize, this.elementData.length);
                  ArraysKt.copyInto(this.elementData, this.elementData, shiftedInternalIndex, internalIndex, this.elementData.length - elementsSize);
               }
            }

            this.copyCollectionElements(internalIndex, elements);
         }

         return true;
      }
   }

   public Object get(int index) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.size());
      int var3 = access$positiveMod(this, access$getHead$p(this) + index);
      return access$getElementData$p(this)[var3];
   }

   public Object set(int index, Object element) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.size());
      int internalIndex = access$positiveMod(this, access$getHead$p(this) + index);
      Object oldElement = access$getElementData$p(this)[internalIndex];
      this.elementData[internalIndex] = element;
      return oldElement;
   }

   public boolean contains(Object element) {
      return this.indexOf(element) != -1;
   }

   public int indexOf(Object element) {
      int index = this.size();
      int tail = access$positiveMod(this, access$getHead$p(this) + index);
      if (this.head < tail) {
         int var3 = this.head;

         while(var3 < tail) {
            index = var3++;
            if (Intrinsics.areEqual(element, this.elementData[index])) {
               return index - this.head;
            }
         }
      } else if (this.head >= tail) {
         int var6 = this.head;
         index = this.elementData.length;

         while(var6 < index) {
            int index = var6++;
            if (Intrinsics.areEqual(element, this.elementData[index])) {
               return index - this.head;
            }
         }

         var6 = 0;

         while(var6 < tail) {
            index = var6++;
            if (Intrinsics.areEqual(element, this.elementData[index])) {
               return index + this.elementData.length - this.head;
            }
         }
      }

      return -1;
   }

   public int lastIndexOf(Object element) {
      int index = this.size();
      int tail = access$positiveMod(this, access$getHead$p(this) + index);
      if (this.head < tail) {
         int var3 = tail - 1;
         index = this.head;
         int index;
         if (index <= var3) {
            do {
               index = var3--;
               if (Intrinsics.areEqual(element, this.elementData[index])) {
                  return index - this.head;
               }
            } while(index != index);
         }
      } else if (this.head > tail) {
         int var6 = tail - 1;
         if (0 <= var6) {
            do {
               index = var6--;
               if (Intrinsics.areEqual(element, this.elementData[index])) {
                  return index + this.elementData.length - this.head;
               }
            } while(0 <= var6);
         }

         var6 = ArraysKt.getLastIndex(this.elementData);
         index = this.head;
         int index;
         if (index <= var6) {
            do {
               index = var6--;
               if (Intrinsics.areEqual(element, this.elementData[index])) {
                  return index - this.head;
               }
            } while(index != index);
         }
      }

      return -1;
   }

   public boolean remove(Object element) {
      int index = this.indexOf(element);
      if (index == -1) {
         return false;
      } else {
         this.remove(index);
         return true;
      }
   }

   public Object removeAt(int index) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.size());
      if (index == CollectionsKt.getLastIndex(this)) {
         return this.removeLast();
      } else if (index == 0) {
         return this.removeFirst();
      } else {
         int internalIndex = access$positiveMod(this, access$getHead$p(this) + index);
         Object element = access$getElementData$p(this)[internalIndex];
         if (index < this.size() >> 1) {
            if (internalIndex >= this.head) {
               ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, internalIndex);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, internalIndex);
               this.elementData[0] = this.elementData[this.elementData.length - 1];
               ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, this.elementData.length - 1);
            }

            this.elementData[this.head] = null;
            this.head = this.incremented(this.head);
         } else {
            int var6 = CollectionsKt.getLastIndex(this);
            int internalLastIndex = access$positiveMod(this, access$getHead$p(this) + var6);
            if (internalIndex <= internalLastIndex) {
               ArraysKt.copyInto(this.elementData, this.elementData, internalIndex, internalIndex + 1, internalLastIndex + 1);
            } else {
               ArraysKt.copyInto(this.elementData, this.elementData, internalIndex, internalIndex + 1, this.elementData.length);
               this.elementData[this.elementData.length - 1] = this.elementData[0];
               ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, internalLastIndex + 1);
            }

            this.elementData[internalLastIndex] = null;
         }

         this.size = this.size() - 1;
         return element;
      }
   }

   public boolean removeAll(@NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      ArrayDeque this_$iv = this;
      int $i$f$filterInPlace = 0;
      boolean var10000;
      if (!this.isEmpty() && access$getElementData$p(this).length != 0) {
         boolean modified$iv = (boolean)this.size();
         int tail$iv = access$positiveMod(this, access$getHead$p(this) + modified$iv);
         int newTail$iv = access$getHead$p(this);
         modified$iv = (boolean)0;
         if (access$getHead$p(this) < tail$iv) {
            int var16 = access$getHead$p(this);

            while(var16 < tail$iv) {
               int index$iv = var16++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               int it = 0;
               if (!elements.contains(element$iv)) {
                  Object[] var24 = access$getElementData$p(this_$iv);
                  int element$iv = newTail$iv++;
                  var24[element$iv] = element$iv;
               } else {
                  modified$iv = (boolean)1;
               }
            }

            ArraysKt.fill(access$getElementData$p(this_$iv), (Object)null, newTail$iv, tail$iv);
         } else {
            int var7 = access$getHead$p(this);
            int index$iv = access$getElementData$p(this).length;

            while(var7 < index$iv) {
               int index$iv = var7++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               access$getElementData$p(this_$iv)[index$iv] = null;
               int var12 = 0;
               if (!elements.contains(element$iv)) {
                  Object[] var23 = access$getElementData$p(this_$iv);
                  int var11 = newTail$iv++;
                  var23[var11] = element$iv;
               } else {
                  modified$iv = (boolean)1;
               }
            }

            newTail$iv = access$positiveMod(this_$iv, newTail$iv);
            var7 = 0;

            while(var7 < tail$iv) {
               index$iv = var7++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               access$getElementData$p(this_$iv)[index$iv] = null;
               int var13 = 0;
               if (!elements.contains(element$iv)) {
                  access$getElementData$p(this_$iv)[newTail$iv] = element$iv;
                  newTail$iv = access$incremented(this_$iv, newTail$iv);
               } else {
                  modified$iv = (boolean)1;
               }
            }
         }

         if (modified$iv) {
            access$setSize$p(this_$iv, access$negativeMod(this_$iv, newTail$iv - access$getHead$p(this_$iv)));
         }

         var10000 = modified$iv;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public boolean retainAll(@NotNull Collection elements) {
      Intrinsics.checkNotNullParameter(elements, "elements");
      ArrayDeque this_$iv = this;
      int $i$f$filterInPlace = 0;
      boolean var10000;
      if (!this.isEmpty() && access$getElementData$p(this).length != 0) {
         boolean modified$iv = (boolean)this.size();
         int tail$iv = access$positiveMod(this, access$getHead$p(this) + modified$iv);
         int newTail$iv = access$getHead$p(this);
         modified$iv = (boolean)0;
         if (access$getHead$p(this) < tail$iv) {
            int var16 = access$getHead$p(this);

            while(var16 < tail$iv) {
               int index$iv = var16++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               int it = 0;
               if (elements.contains(element$iv)) {
                  Object[] var24 = access$getElementData$p(this_$iv);
                  int element$iv = newTail$iv++;
                  var24[element$iv] = element$iv;
               } else {
                  modified$iv = (boolean)1;
               }
            }

            ArraysKt.fill(access$getElementData$p(this_$iv), (Object)null, newTail$iv, tail$iv);
         } else {
            int var7 = access$getHead$p(this);
            int index$iv = access$getElementData$p(this).length;

            while(var7 < index$iv) {
               int index$iv = var7++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               access$getElementData$p(this_$iv)[index$iv] = null;
               int var12 = 0;
               if (elements.contains(element$iv)) {
                  Object[] var23 = access$getElementData$p(this_$iv);
                  int var11 = newTail$iv++;
                  var23[var11] = element$iv;
               } else {
                  modified$iv = (boolean)1;
               }
            }

            newTail$iv = access$positiveMod(this_$iv, newTail$iv);
            var7 = 0;

            while(var7 < tail$iv) {
               index$iv = var7++;
               Object element$iv = access$getElementData$p(this_$iv)[index$iv];
               access$getElementData$p(this_$iv)[index$iv] = null;
               int var13 = 0;
               if (elements.contains(element$iv)) {
                  access$getElementData$p(this_$iv)[newTail$iv] = element$iv;
                  newTail$iv = access$incremented(this_$iv, newTail$iv);
               } else {
                  modified$iv = (boolean)1;
               }
            }
         }

         if (modified$iv) {
            access$setSize$p(this_$iv, access$negativeMod(this_$iv, newTail$iv - access$getHead$p(this_$iv)));
         }

         var10000 = modified$iv;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   private final boolean filterInPlace(Function1 predicate) {
      int $i$f$filterInPlace = 0;
      if (!this.isEmpty() && access$getElementData$p(this).length != 0) {
         boolean modified = (boolean)this.size();
         int tail = access$positiveMod(this, access$getHead$p(this) + modified);
         int newTail = access$getHead$p(this);
         modified = (boolean)0;
         if (access$getHead$p(this) < tail) {
            int var6 = access$getHead$p(this);

            while(var6 < tail) {
               int index = var6++;
               Object element = access$getElementData$p(this)[index];
               if ((Boolean)predicate.invoke(element)) {
                  Object[] var10000 = access$getElementData$p(this);
                  int var9 = newTail++;
                  var10000[var9] = element;
               } else {
                  modified = (boolean)1;
               }
            }

            ArraysKt.fill(access$getElementData$p(this), (Object)null, newTail, tail);
         } else {
            int var12 = access$getHead$p(this);
            int index = access$getElementData$p(this).length;

            while(var12 < index) {
               int index = var12++;
               Object element = access$getElementData$p(this)[index];
               access$getElementData$p(this)[index] = null;
               if ((Boolean)predicate.invoke(element)) {
                  Object[] var19 = access$getElementData$p(this);
                  int var10 = newTail++;
                  var19[var10] = element;
               } else {
                  modified = (boolean)1;
               }
            }

            newTail = access$positiveMod(this, newTail);
            var12 = 0;

            while(var12 < tail) {
               index = var12++;
               Object element = access$getElementData$p(this)[index];
               access$getElementData$p(this)[index] = null;
               if ((Boolean)predicate.invoke(element)) {
                  access$getElementData$p(this)[newTail] = element;
                  newTail = access$incremented(this, newTail);
               } else {
                  modified = (boolean)1;
               }
            }
         }

         if (modified) {
            access$setSize$p(this, access$negativeMod(this, newTail - access$getHead$p(this)));
         }

         return modified;
      } else {
         return false;
      }
   }

   public void clear() {
      int var3 = this.size();
      int tail = access$positiveMod(this, access$getHead$p(this) + var3);
      if (this.head < tail) {
         ArraysKt.fill(this.elementData, (Object)null, this.head, tail);
      } else if (!((Collection)this).isEmpty()) {
         ArraysKt.fill(this.elementData, (Object)null, this.head, this.elementData.length);
         ArraysKt.fill(this.elementData, (Object)null, 0, tail);
      }

      this.head = 0;
      this.size = 0;
   }

   @NotNull
   public Object[] toArray(@NotNull Object[] array) {
      Intrinsics.checkNotNullParameter(array, "array");
      Object[] dest = array.length >= this.size() ? array : ArraysKt.arrayOfNulls(array, this.size());
      int var5 = this.size();
      int tail = access$positiveMod(this, access$getHead$p(this) + var5);
      if (this.head < tail) {
         ArraysKt.copyInto$default(this.elementData, dest, 0, this.head, tail, 2, (Object)null);
      } else if (!((Collection)this).isEmpty()) {
         ArraysKt.copyInto(this.elementData, dest, 0, this.head, this.elementData.length);
         ArraysKt.copyInto(this.elementData, dest, this.elementData.length - this.head, 0, tail);
      }

      if (dest.length > this.size()) {
         dest[this.size()] = null;
      }

      return dest;
   }

   @NotNull
   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   @NotNull
   public final Object[] testToArray$kotlin_stdlib(@NotNull Object[] array) {
      Intrinsics.checkNotNullParameter(array, "array");
      return this.toArray(array);
   }

   @NotNull
   public final Object[] testToArray$kotlin_stdlib() {
      return this.toArray();
   }

   public final void internalStructure$kotlin_stdlib(@NotNull Function2 structure) {
      Intrinsics.checkNotNullParameter(structure, "structure");
      int var4 = this.size();
      int tail = access$positiveMod(this, access$getHead$p(this) + var4);
      int head = !this.isEmpty() && this.head >= tail ? this.head - this.elementData.length : this.head;
      structure.invoke(head, this.toArray());
   }

   // $FF: synthetic method
   public static final Object[] access$getElementData$p(ArrayDeque $this) {
      return $this.elementData;
   }

   // $FF: synthetic method
   public static final int access$positiveMod(ArrayDeque $this, int index) {
      return $this.positiveMod(index);
   }

   // $FF: synthetic method
   public static final int access$getHead$p(ArrayDeque $this) {
      return $this.head;
   }

   // $FF: synthetic method
   public static final int access$incremented(ArrayDeque $this, int index) {
      return $this.incremented(index);
   }

   // $FF: synthetic method
   public static final void access$setSize$p(ArrayDeque $this, int var1) {
      $this.size = var1;
   }

   // $FF: synthetic method
   public static final int access$negativeMod(ArrayDeque $this, int index) {
      return $this.negativeMod(index);
   }

   static {
      int $i$f$emptyArray = 0;
      emptyElementData = new Object[0];
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"},
      d2 = {"Lkotlin/collections/ArrayDeque$Companion;", "", "()V", "defaultMinCapacity", "", "emptyElementData", "", "[Ljava/lang/Object;", "maxArraySize", "newCapacity", "oldCapacity", "minCapacity", "newCapacity$kotlin_stdlib", "kotlin-stdlib"}
   )
   public static final class Companion {
      private Companion() {
      }

      public final int newCapacity$kotlin_stdlib(int oldCapacity, int minCapacity) {
         int newCapacity = oldCapacity + (oldCapacity >> 1);
         if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
         }

         if (newCapacity - 2147483639 > 0) {
            newCapacity = minCapacity > 2147483639 ? Integer.MAX_VALUE : 2147483639;
         }

         return newCapacity;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
