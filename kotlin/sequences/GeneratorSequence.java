package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B+\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0096\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/GeneratorSequence;", "T", "", "Lkotlin/sequences/Sequence;", "getInitialValue", "Lkotlin/Function0;", "getNextValue", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}
)
final class GeneratorSequence implements Sequence {
   @NotNull
   private final Function0 getInitialValue;
   @NotNull
   private final Function1 getNextValue;

   public GeneratorSequence(@NotNull Function0 getInitialValue, @NotNull Function1 getNextValue) {
      Intrinsics.checkNotNullParameter(getInitialValue, "getInitialValue");
      Intrinsics.checkNotNullParameter(getNextValue, "getNextValue");
      super();
      this.getInitialValue = getInitialValue;
      this.getNextValue = getNextValue;
   }

   @NotNull
   public Iterator iterator() {
      return new Iterator() {
         @Nullable
         private Object nextItem;
         private int nextState = -2;

         @Nullable
         public final Object getNextItem() {
            return this.nextItem;
         }

         public final void setNextItem(@Nullable Object var1) {
            this.nextItem = var1;
         }

         public final int getNextState() {
            return this.nextState;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }

         private final void calcNext() {
            Object var10001;
            if (this.nextState == -2) {
               var10001 = (Function1)GeneratorSequence.this.getInitialValue.invoke();
            } else {
               var10001 = GeneratorSequence.this.getNextValue;
               Object var10002 = this.nextItem;
               Intrinsics.checkNotNull(var10002);
               var10001 = (Function1)var10001.invoke(var10002);
            }

            this.nextItem = var10001;
            this.nextState = this.nextItem == null ? 0 : 1;
         }

         @NotNull
         public Object next() {
            if (this.nextState < 0) {
               this.calcNext();
            }

            if (this.nextState == 0) {
               throw new NoSuchElementException();
            } else {
               Object result = this.nextItem;
               if (result == null) {
                  throw new NullPointerException("null cannot be cast to non-null type T of kotlin.sequences.GeneratorSequence");
               } else {
                  this.nextState = -1;
                  return result;
               }
            }
         }

         public boolean hasNext() {
            if (this.nextState < 0) {
               this.calcNext();
            }

            return this.nextState == 1;
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
