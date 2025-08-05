package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public abstract class IteratorAction {
   protected final Object self;
   protected Object thisArg;
   protected final Object callbackfn;
   protected Object result;
   protected long index;
   private final ArrayLikeIterator iter;

   public IteratorAction(Object self, Object callbackfn, Object thisArg, Object initialResult) {
      this(self, callbackfn, thisArg, initialResult, ArrayLikeIterator.arrayLikeIterator(self));
   }

   public IteratorAction(Object self, Object callbackfn, Object thisArg, Object initialResult, ArrayLikeIterator iter) {
      this.self = self;
      this.callbackfn = callbackfn;
      this.result = initialResult;
      this.iter = iter;
      this.thisArg = thisArg;
   }

   protected void applyLoopBegin(ArrayLikeIterator iterator) {
   }

   public final Object apply() {
      boolean strict = Bootstrap.isStrictCallable(this.callbackfn);
      this.thisArg = this.thisArg == ScriptRuntime.UNDEFINED && !strict ? Context.getGlobal() : this.thisArg;
      this.applyLoopBegin(this.iter);
      boolean reverse = this.iter.isReverse();

      while(this.iter.hasNext()) {
         Object val = this.iter.next();
         this.index = this.iter.nextIndex() + (long)(reverse ? 1 : -1);

         try {
            if (!this.forEach(val, (double)this.index)) {
               return this.result;
            }
         } catch (Error | RuntimeException e) {
            throw e;
         } catch (Throwable t) {
            throw new RuntimeException(t);
         }
      }

      return this.result;
   }

   protected abstract boolean forEach(Object var1, double var2) throws Throwable;
}
