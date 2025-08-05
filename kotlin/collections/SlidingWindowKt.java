package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000¨\u0006\u000f"},
   d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}
)
public final class SlidingWindowKt {
   public static final void checkWindowSizeStep(int size, int step) {
      boolean var2 = size > 0 && step > 0;
      if (!var2) {
         int var3 = 0;
         String var4 = size != step ? "Both size " + size + " and step " + step + " must be greater than zero." : "size " + size + " must be greater than zero.";
         throw new IllegalArgumentException(var4.toString());
      }
   }

   @NotNull
   public static final Sequence windowedSequence(@NotNull Sequence $this$windowedSequence, int size, int step, boolean partialWindows, boolean reuseBuffer) {
      Intrinsics.checkNotNullParameter($this$windowedSequence, "<this>");
      checkWindowSizeStep(size, step);
      return new SlidingWindowKt$windowedSequence$$inlined$Sequence$1($this$windowedSequence, size, step, partialWindows, reuseBuffer);
   }

   @NotNull
   public static final Iterator windowedIterator(@NotNull final Iterator iterator, final int size, final int step, final boolean partialWindows, final boolean reuseBuffer) {
      Intrinsics.checkNotNullParameter(iterator, "iterator");
      return !iterator.hasNext() ? (Iterator)EmptyIterator.INSTANCE : SequencesKt.iterator(new Function2((Continuation)null) {
         Object L$1;
         Object L$2;
         int I$0;
         int label;
         // $FF: synthetic field
         private Object L$0;

         @Nullable
         public final Object invokeSuspend(@NotNull Object $result) {
            SequenceScope $this$iterator;
            RingBuffer buffer;
            Object var9;
            label169: {
               Iterator skipx;
               label191: {
                  var9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  int gap;
                  Iterator var7;
                  ArrayList buffer;
                  int skip;
                  switch (this.label) {
                     case 0:
                        ResultKt.throwOnFailure($result);
                        $this$iterator = (SequenceScope)this.L$0;
                        int bufferInitialCapacity = RangesKt.coerceAtMost(size, 1024);
                        gap = step - size;
                        if (gap < 0) {
                           buffer = new RingBuffer(bufferInitialCapacity);
                           skipx = iterator;
                           break label191;
                        }

                        buffer = new ArrayList(bufferInitialCapacity);
                        skip = 0;
                        var7 = iterator;
                        break;
                     case 1:
                        gap = this.I$0;
                        var7 = (Iterator)this.L$2;
                        buffer = (ArrayList)this.L$1;
                        $this$iterator = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        if (reuseBuffer) {
                           buffer.clear();
                        } else {
                           buffer = new ArrayList(size);
                        }

                        skip = gap;
                        break;
                     case 2:
                        ResultKt.throwOnFailure($result);
                        return Unit.INSTANCE;
                     case 3:
                        skipx = (Iterator)this.L$2;
                        buffer = (RingBuffer)this.L$1;
                        $this$iterator = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        buffer.removeFirst(step);
                        break label191;
                     case 4:
                        buffer = (RingBuffer)this.L$1;
                        $this$iterator = (SequenceScope)this.L$0;
                        ResultKt.throwOnFailure($result);
                        buffer.removeFirst(step);
                        break label169;
                     case 5:
                        ResultKt.throwOnFailure($result);
                        return Unit.INSTANCE;
                     default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  while(var7.hasNext()) {
                     Object e = var7.next();
                     if (skip > 0) {
                        --skip;
                     } else {
                        buffer.add(e);
                        if (buffer.size() == size) {
                           Continuation var10002 = this;
                           this.L$0 = $this$iterator;
                           this.L$1 = buffer;
                           this.L$2 = var7;
                           this.I$0 = gap;
                           this.label = 1;
                           if ($this$iterator.yield(buffer, var10002) == var9) {
                              return var9;
                           }

                           if (reuseBuffer) {
                              buffer.clear();
                           } else {
                              buffer = new ArrayList(size);
                           }

                           skip = gap;
                        }
                     }
                  }

                  if (((Collection)buffer).isEmpty() || !partialWindows && buffer.size() != size) {
                     return Unit.INSTANCE;
                  }

                  Continuation var14 = this;
                  this.L$0 = null;
                  this.L$1 = null;
                  this.L$2 = null;
                  this.label = 2;
                  if ($this$iterator.yield(buffer, var14) == var9) {
                     return var9;
                  }

                  return Unit.INSTANCE;
               }

               while(skipx.hasNext()) {
                  Object e = skipx.next();
                  buffer.add(e);
                  if (buffer.isFull()) {
                     if (buffer.size() < size) {
                        buffer = buffer.expanded(size);
                     } else {
                        List var10001 = reuseBuffer ? (List)buffer : (List)(new ArrayList(buffer));
                        Continuation var15 = this;
                        this.L$0 = $this$iterator;
                        this.L$1 = buffer;
                        this.L$2 = skipx;
                        this.label = 3;
                        if ($this$iterator.yield(var10001, var15) == var9) {
                           return var9;
                        }

                        buffer.removeFirst(step);
                     }
                  }
               }

               if (!partialWindows) {
                  return Unit.INSTANCE;
               }
            }

            while(buffer.size() > step) {
               List var13 = reuseBuffer ? (List)buffer : (List)(new ArrayList(buffer));
               Continuation var16 = this;
               this.L$0 = $this$iterator;
               this.L$1 = buffer;
               this.L$2 = null;
               this.label = 4;
               if ($this$iterator.yield(var13, var16) == var9) {
                  return var9;
               }

               buffer.removeFirst(step);
            }

            if (!((Collection)buffer).isEmpty()) {
               Continuation var17 = this;
               this.L$0 = null;
               this.L$1 = null;
               this.L$2 = null;
               this.label = 5;
               if ($this$iterator.yield(buffer, var17) == var9) {
                  return var9;
               }
            }

            return Unit.INSTANCE;
         }

         @NotNull
         public final Continuation create(@Nullable Object value, @NotNull Continuation $completion) {
            Function2 var3 = new <anonymous constructor>($completion);
            var3.L$0 = value;
            return (Continuation)var3;
         }

         @Nullable
         public final Object invoke(@NotNull SequenceScope p1, @Nullable Continuation p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      });
   }
}
