package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\nR\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b¸\u0006\u0000"},
   d2 = {"kotlin/coroutines/ContinuationKt$Continuation$1", "Lkotlin/coroutines/Continuation;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib"}
)
public final class DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1 implements Continuation {
   // $FF: synthetic field
   final CoroutineContext $context;
   // $FF: synthetic field
   final DeepRecursiveScopeImpl this$0;
   // $FF: synthetic field
   final Function3 $currentFunction$inlined;
   // $FF: synthetic field
   final Continuation $cont$inlined;

   public DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1(CoroutineContext $context, DeepRecursiveScopeImpl var2, Function3 var3, Continuation var4) {
      this.$context = $context;
      this.this$0 = var2;
      this.$currentFunction$inlined = var3;
      this.$cont$inlined = var4;
   }

   @NotNull
   public CoroutineContext getContext() {
      return this.$context;
   }

   public void resumeWith(@NotNull Object result) {
      int var3 = 0;
      DeepRecursiveScopeImpl.access$setFunction$p(this.this$0, this.$currentFunction$inlined);
      DeepRecursiveScopeImpl.access$setCont$p(this.this$0, this.$cont$inlined);
      DeepRecursiveScopeImpl.access$setResult$p(this.this$0, result);
   }
}
