package kotlin.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH$J\b\u0010\n\u001a\u00020\tH\u0004J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\fH\u0002R\u0012\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lkotlin/collections/AbstractIterator;", "T", "", "()V", "nextValue", "Ljava/lang/Object;", "state", "Lkotlin/collections/State;", "computeNext", "", "done", "hasNext", "", "next", "()Ljava/lang/Object;", "setNext", "value", "(Ljava/lang/Object;)V", "tryToComputeNext", "kotlin-stdlib"}
)
public abstract class AbstractIterator implements Iterator, KMappedMarker {
   @NotNull
   private State state;
   @Nullable
   private Object nextValue;

   public AbstractIterator() {
      this.state = State.NotReady;
   }

   public boolean hasNext() {
      boolean var1 = this.state != State.Failed;
      if (!var1) {
         String var4 = "Failed requirement.";
         throw new IllegalArgumentException(var4.toString());
      } else {
         State var3 = this.state;
         int var2 = AbstractIterator.WhenMappings.$EnumSwitchMapping$0[var3.ordinal()];
         boolean var10000;
         switch (var2) {
            case 1:
               var10000 = false;
               break;
            case 2:
               var10000 = true;
               break;
            default:
               var10000 = this.tryToComputeNext();
         }

         return var10000;
      }
   }

   public Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         this.state = State.NotReady;
         return this.nextValue;
      }
   }

   private final boolean tryToComputeNext() {
      this.state = State.Failed;
      this.computeNext();
      return this.state == State.Ready;
   }

   protected abstract void computeNext();

   protected final void setNext(Object value) {
      this.nextValue = value;
      this.state = State.Ready;
   }

   protected final void done() {
      this.state = State.Done;
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[State.values().length];
         var0[State.Done.ordinal()] = 1;
         var0[State.Ready.ordinal()] = 2;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
