package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/sequences/TakeSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}
)
public final class TakeSequence implements Sequence, DropTakeSequence {
   @NotNull
   private final Sequence sequence;
   private final int count;

   public TakeSequence(@NotNull Sequence sequence, int count) {
      Intrinsics.checkNotNullParameter(sequence, "sequence");
      super();
      this.sequence = sequence;
      this.count = count;
      boolean var3 = this.count >= 0;
      if (!var3) {
         int var4 = 0;
         String var5 = "count must be non-negative, but was " + this.count + '.';
         throw new IllegalArgumentException(var5.toString());
      }
   }

   @NotNull
   public Sequence drop(int n) {
      return n >= this.count ? SequencesKt.emptySequence() : (Sequence)(new SubSequence(this.sequence, n, this.count));
   }

   @NotNull
   public Sequence take(int n) {
      return n >= this.count ? (Sequence)this : (Sequence)(new TakeSequence(this.sequence, n));
   }

   @NotNull
   public Iterator iterator() {
      return new Iterator() {
         private int left;
         @NotNull
         private final Iterator iterator;

         {
            this.left = TakeSequence.this.count;
            this.iterator = TakeSequence.this.sequence.iterator();
         }

         public final int getLeft() {
            return this.left;
         }

         public final void setLeft(int var1) {
            this.left = var1;
         }

         @NotNull
         public final Iterator getIterator() {
            return this.iterator;
         }

         public Object next() {
            if (this.left == 0) {
               throw new NoSuchElementException();
            } else {
               int var2 = this.left;
               this.left = var2 + -1;
               return this.iterator.next();
            }
         }

         public boolean hasNext() {
            return this.left > 0 && this.iterator.hasNext();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
