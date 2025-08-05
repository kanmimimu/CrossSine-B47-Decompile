package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004BK\u00129\u0010\u0005\u001a5\b\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b\u0012\u0006\u0010\t\u001a\u00028\u0000ø\u0001\u0000¢\u0006\u0002\u0010\nJ\u0019\u0010\u0015\u001a\u00028\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0016Jc\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000429\u0010\u0018\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b2\u000e\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u001b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\u0013H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u001cJ\u000b\u0010\u001d\u001a\u00028\u0001¢\u0006\u0002\u0010\u001eJ5\u0010\u0015\u001a\u0002H\u001f\"\u0004\b\u0002\u0010 \"\u0004\b\u0003\u0010\u001f*\u000e\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H\u001f0!2\u0006\u0010\t\u001a\u0002H H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\"R\u0018\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fRF\u0010\u0010\u001a5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\bX\u0082\u000eø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0011R\u001e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0013X\u0082\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0014R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006#"},
   d2 = {"Lkotlin/DeepRecursiveScopeImpl;", "T", "R", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)V", "cont", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "function", "Lkotlin/jvm/functions/Function3;", "result", "Lkotlin/Result;", "Ljava/lang/Object;", "callRecursive", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crossFunctionCompletion", "currentFunction", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resumeWith", "", "(Ljava/lang/Object;)V", "runCallLoop", "()Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}
)
@ExperimentalStdlibApi
final class DeepRecursiveScopeImpl extends DeepRecursiveScope implements Continuation {
   @NotNull
   private Function3 function;
   @Nullable
   private Object value;
   @Nullable
   private Continuation cont;
   @NotNull
   private Object result;

   public DeepRecursiveScopeImpl(@NotNull Function3 block, Object value) {
      Intrinsics.checkNotNullParameter(block, "block");
      super((DefaultConstructorMarker)null);
      this.function = block;
      this.value = value;
      this.cont = this;
      this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
   }

   @NotNull
   public CoroutineContext getContext() {
      return EmptyCoroutineContext.INSTANCE;
   }

   public void resumeWith(@NotNull Object result) {
      this.cont = null;
      this.result = result;
   }

   @Nullable
   public Object callRecursive(Object value, @NotNull Continuation $completion) {
      int var4 = 0;
      this.cont = $completion;
      this.value = value;
      Object var10000 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended($completion);
      }

      return var10000;
   }

   @Nullable
   public Object callRecursive(@NotNull DeepRecursiveFunction $this$callRecursive, Object value, @NotNull Continuation $completion) {
      int var5 = 0;
      Function3 function = $this$callRecursive.getBlock$kotlin_stdlib();
      DeepRecursiveScopeImpl $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1 = this;
      int var8 = 0;
      Function3 currentFunction = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function;
      if (function != currentFunction) {
         $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.function = function;
         $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.crossFunctionCompletion(currentFunction, $completion);
      } else {
         $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.cont = $completion;
      }

      $this$callRecursive_u24lambda_u2d2_u24lambda_u2d1.value = value;
      Object var10000 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended($completion);
      }

      return var10000;
   }

   private final Continuation crossFunctionCompletion(Function3 currentFunction, Continuation cont) {
      CoroutineContext var3 = EmptyCoroutineContext.INSTANCE;
      return new DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1(var3, this, currentFunction, cont);
   }

   public final Object runCallLoop() {
      while(true) {
         Object result = this.result;
         Continuation var3 = this.cont;
         if (var3 == null) {
            ResultKt.throwOnFailure(result);
            return result;
         }

         Continuation cont = var3;
         if (Result.equals-impl0(DeepRecursiveKt.access$getUNDEFINED_RESULT$p(), result)) {
            Object r;
            try {
               Function3 r = this.function;
               Object var5 = this.value;
               r = ((Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity(r, 3)).invoke(this, var5, cont);
            } catch (Throwable e) {
               Result.Companion var7 = Result.Companion;
               var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(e)));
               continue;
            }

            if (r != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               Result.Companion var10 = Result.Companion;
               var3.resumeWith(Result.constructor-impl(r));
            }
         } else {
            this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
            var3.resumeWith(result);
         }
      }
   }

   // $FF: synthetic method
   public static final void access$setFunction$p(DeepRecursiveScopeImpl $this, Function3 var1) {
      $this.function = var1;
   }

   // $FF: synthetic method
   public static final void access$setCont$p(DeepRecursiveScopeImpl $this, Continuation var1) {
      $this.cont = var1;
   }

   // $FF: synthetic method
   public static final void access$setResult$p(DeepRecursiveScopeImpl $this, Object var1) {
      $this.result = var1;
   }
}
