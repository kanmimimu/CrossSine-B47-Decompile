package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0096\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/FilteringSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "sendWhen", "", "predicate", "Lkotlin/Function1;", "(Lkotlin/sequences/Sequence;ZLkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}
)
public final class FilteringSequence implements Sequence {
   @NotNull
   private final Sequence sequence;
   private final boolean sendWhen;
   @NotNull
   private final Function1 predicate;

   public FilteringSequence(@NotNull Sequence sequence, boolean sendWhen, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter(sequence, "sequence");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      super();
      this.sequence = sequence;
      this.sendWhen = sendWhen;
      this.predicate = predicate;
   }

   // $FF: synthetic method
   public FilteringSequence(Sequence var1, boolean var2, Function1 var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 2) != 0) {
         var2 = true;
      }

      this(var1, var2, var3);
   }

   @NotNull
   public Iterator iterator() {
      return new Iterator() {
         @NotNull
         private final Iterator iterator;
         private int nextState;
         @Nullable
         private Object nextItem;

         {
            this.iterator = FilteringSequence.this.sequence.iterator();
            this.nextState = -1;
         }

         @NotNull
         public final Iterator getIterator() {
            return this.iterator;
         }

         public final int getNextState() {
            return this.nextState;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }

         @Nullable
         public final Object getNextItem() {
            return this.nextItem;
         }

         public final void setNextItem(@Nullable Object var1) {
            this.nextItem = var1;
         }

         private final void calcNext() {
            while(true) {
               if (this.iterator.hasNext()) {
                  Object item = this.iterator.next();
                  if ((Boolean)FilteringSequence.this.predicate.invoke(item) != FilteringSequence.this.sendWhen) {
                     continue;
                  }

                  this.nextItem = item;
                  this.nextState = 1;
                  return;
               }

               this.nextState = 0;
               return;
            }
         }

         public Object next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState == 0) {
               throw new NoSuchElementException();
            } else {
               Object result = this.nextItem;
               this.nextItem = null;
               this.nextState = -1;
               return result;
            }
         }

         public boolean hasNext() {
            if (this.nextState == -1) {
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
