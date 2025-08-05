package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004BA\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\tH\u0096\u0002R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/FlatteningSequence;", "T", "R", "E", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "iterator", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"}
)
public final class FlatteningSequence implements Sequence {
   @NotNull
   private final Sequence sequence;
   @NotNull
   private final Function1 transformer;
   @NotNull
   private final Function1 iterator;

   public FlatteningSequence(@NotNull Sequence sequence, @NotNull Function1 transformer, @NotNull Function1 iterator) {
      Intrinsics.checkNotNullParameter(sequence, "sequence");
      Intrinsics.checkNotNullParameter(transformer, "transformer");
      Intrinsics.checkNotNullParameter(iterator, "iterator");
      super();
      this.sequence = sequence;
      this.transformer = transformer;
      this.iterator = iterator;
   }

   @NotNull
   public Iterator iterator() {
      return new Iterator() {
         @NotNull
         private final Iterator iterator;
         @Nullable
         private Iterator itemIterator;

         {
            this.iterator = FlatteningSequence.this.sequence.iterator();
         }

         @NotNull
         public final Iterator getIterator() {
            return this.iterator;
         }

         @Nullable
         public final Iterator getItemIterator() {
            return this.itemIterator;
         }

         public final void setItemIterator(@Nullable Iterator var1) {
            this.itemIterator = var1;
         }

         public Object next() {
            if (!this.ensureItemIterator()) {
               throw new NoSuchElementException();
            } else {
               Iterator var10000 = this.itemIterator;
               Intrinsics.checkNotNull(var10000);
               return var10000.next();
            }
         }

         public boolean hasNext() {
            return this.ensureItemIterator();
         }

         private final boolean ensureItemIterator() {
            Iterator var1 = this.itemIterator;
            if (var1 == null ? false : !var1.hasNext()) {
               this.itemIterator = null;
            }

            while(this.itemIterator == null) {
               if (!this.iterator.hasNext()) {
                  return false;
               }

               Object element = this.iterator.next();
               Iterator nextItemIterator = (Iterator)FlatteningSequence.this.iterator.invoke(FlatteningSequence.this.transformer.invoke(element));
               if (nextItemIterator.hasNext()) {
                  this.itemIterator = nextItemIterator;
                  return true;
               }
            }

            return true;
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
