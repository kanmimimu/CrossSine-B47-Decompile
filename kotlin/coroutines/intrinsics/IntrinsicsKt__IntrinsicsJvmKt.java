package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b¢\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001an\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0014\"\u0004\b\u0002\u0010\u0003*)\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0015¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0006\u0010\u0016\u001a\u0002H\u00142\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0081\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"},
   d2 = {"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "P", "Lkotlin/Function3;", "param", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"},
   xs = "kotlin/coroutines/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object startCoroutineUninterceptedOrReturn(Function1 $this$startCoroutineUninterceptedOrReturn, Continuation completion) {
      Intrinsics.checkNotNullParameter($this$startCoroutineUninterceptedOrReturn, "<this>");
      Intrinsics.checkNotNullParameter(completion, "completion");
      return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 1)).invoke(completion);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object startCoroutineUninterceptedOrReturn(Function2 $this$startCoroutineUninterceptedOrReturn, Object receiver, Continuation completion) {
      Intrinsics.checkNotNullParameter($this$startCoroutineUninterceptedOrReturn, "<this>");
      Intrinsics.checkNotNullParameter(completion, "completion");
      return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 2)).invoke(receiver, completion);
   }

   @InlineOnly
   private static final Object startCoroutineUninterceptedOrReturn(Function3 $this$startCoroutineUninterceptedOrReturn, Object receiver, Object param, Continuation completion) {
      Intrinsics.checkNotNullParameter($this$startCoroutineUninterceptedOrReturn, "<this>");
      Intrinsics.checkNotNullParameter(completion, "completion");
      return ((Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 3)).invoke(receiver, param, completion);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation createCoroutineUnintercepted(@NotNull Function1 $this$createCoroutineUnintercepted, @NotNull Continuation completion) {
      Intrinsics.checkNotNullParameter($this$createCoroutineUnintercepted, "<this>");
      Intrinsics.checkNotNullParameter(completion, "completion");
      Continuation probeCompletion = DebugProbesKt.probeCoroutineCreated(completion);
      Continuation var10000;
      if ($this$createCoroutineUnintercepted instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)$this$createCoroutineUnintercepted).create(probeCompletion);
      } else {
         int $i$f$createCoroutineFromSuspendFunction = 0;
         CoroutineContext context$iv = probeCompletion.getContext();
         var10000 = context$iv == EmptyCoroutineContext.INSTANCE ? (Continuation)(new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1(probeCompletion, $this$createCoroutineUnintercepted)) : (Continuation)(new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2(probeCompletion, context$iv, $this$createCoroutineUnintercepted));
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation createCoroutineUnintercepted(@NotNull Function2 $this$createCoroutineUnintercepted, Object receiver, @NotNull Continuation completion) {
      Intrinsics.checkNotNullParameter($this$createCoroutineUnintercepted, "<this>");
      Intrinsics.checkNotNullParameter(completion, "completion");
      Continuation probeCompletion = DebugProbesKt.probeCoroutineCreated(completion);
      Continuation var10000;
      if ($this$createCoroutineUnintercepted instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)$this$createCoroutineUnintercepted).create(receiver, probeCompletion);
      } else {
         int $i$f$createCoroutineFromSuspendFunction = 0;
         CoroutineContext context$iv = probeCompletion.getContext();
         var10000 = context$iv == EmptyCoroutineContext.INSTANCE ? (Continuation)(new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3(probeCompletion, $this$createCoroutineUnintercepted, receiver)) : (Continuation)(new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4(probeCompletion, context$iv, $this$createCoroutineUnintercepted, receiver));
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Continuation intercepted(@NotNull Continuation $this$intercepted) {
      Intrinsics.checkNotNullParameter($this$intercepted, "<this>");
      ContinuationImpl var1 = $this$intercepted instanceof ContinuationImpl ? (ContinuationImpl)$this$intercepted : null;
      return var1 == null ? $this$intercepted : var1.intercepted();
   }

   @SinceKotlin(
      version = "1.3"
   )
   private static final Continuation createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(final Continuation completion, final Function1 block) {
      int $i$f$createCoroutineFromSuspendFunction = 0;
      final CoroutineContext context = completion.getContext();
      return context == EmptyCoroutineContext.INSTANCE ? (Continuation)(new RestrictedContinuationImpl() {
         private int label;

         @Nullable
         protected Object invokeSuspend(@NotNull Object result) {
            int var2 = this.label;
            Object var10000;
            switch (var2) {
               case 0:
                  this.label = 1;
                  ResultKt.throwOnFailure(result);
                  var10000 = block.invoke(this);
                  break;
               case 1:
                  this.label = 2;
                  ResultKt.throwOnFailure(result);
                  var10000 = result;
                  break;
               default:
                  throw new IllegalStateException("This coroutine had already completed".toString());
            }

            return var10000;
         }
      }) : (Continuation)(new ContinuationImpl() {
         private int label;

         @Nullable
         protected Object invokeSuspend(@NotNull Object result) {
            int var2 = this.label;
            Object var10000;
            switch (var2) {
               case 0:
                  this.label = 1;
                  ResultKt.throwOnFailure(result);
                  var10000 = block.invoke(this);
                  break;
               case 1:
                  this.label = 2;
                  ResultKt.throwOnFailure(result);
                  var10000 = result;
                  break;
               default:
                  throw new IllegalStateException("This coroutine had already completed".toString());
            }

            return var10000;
         }
      });
   }

   public IntrinsicsKt__IntrinsicsJvmKt() {
   }
}
