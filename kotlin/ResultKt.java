package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a.\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\t\u001a\u0087\u0001\u0010\n\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\r2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\u0014\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0000¢\u0006\u0002\u0010\u0012\u001a3\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u0006\u0010\u0014\u001a\u0002H\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a^\u0010\u0016\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aS\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0017\u001aZ\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aZ\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001ad\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aW\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0017\u001aC\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0006*\u0002H\u000b2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00060\r¢\u0006\u0002\b!H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0017\u001a\u0018\u0010\"\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u0005H\u0001ø\u0001\u0000¢\u0006\u0002\u0010#\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u009920\u0001¨\u0006$"},
   d2 = {"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"}
)
public final class ResultKt {
   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Object createFailure(@NotNull Throwable exception) {
      Intrinsics.checkNotNullParameter(exception, "exception");
      return new Result.Failure(exception);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwOnFailure(@NotNull Object $this$throwOnFailure) {
      if ($this$throwOnFailure instanceof Result.Failure) {
         throw ((Result.Failure)$this$throwOnFailure).exception;
      }
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object runCatching(Function0 block) {
      Intrinsics.checkNotNullParameter(block, "block");

      Object var1;
      try {
         Result.Companion var5 = Result.Companion;
         Object var2 = block.invoke();
         var1 = Result.constructor-impl(var2);
      } catch (Throwable e) {
         Result.Companion var3 = Result.Companion;
         var1 = Result.constructor-impl(createFailure(e));
      }

      return var1;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object runCatching(Object $this$runCatching, Function1 block) {
      Intrinsics.checkNotNullParameter(block, "block");

      Object var2;
      try {
         Result.Companion var6 = Result.Companion;
         Object var3 = block.invoke($this$runCatching);
         var2 = Result.constructor-impl(var3);
      } catch (Throwable e) {
         Result.Companion var4 = Result.Companion;
         var2 = Result.constructor-impl(createFailure(e));
      }

      return var2;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrThrow(Object $this$getOrThrow) {
      throwOnFailure($this$getOrThrow);
      return $this$getOrThrow;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrElse(Object $this$getOrElse, Function1 onFailure) {
      Intrinsics.checkNotNullParameter(onFailure, "onFailure");
      Throwable exception = Result.exceptionOrNull-impl($this$getOrElse);
      return exception == null ? $this$getOrElse : onFailure.invoke(exception);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object getOrDefault(Object $this$getOrDefault, Object defaultValue) {
      return Result.isFailure-impl($this$getOrDefault) ? defaultValue : $this$getOrDefault;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object fold(Object $this$fold, Function1 onSuccess, Function1 onFailure) {
      Intrinsics.checkNotNullParameter(onSuccess, "onSuccess");
      Intrinsics.checkNotNullParameter(onFailure, "onFailure");
      Throwable exception = Result.exceptionOrNull-impl($this$fold);
      return exception == null ? onSuccess.invoke($this$fold) : onFailure.invoke(exception);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object map(Object $this$map, Function1 transform) {
      Intrinsics.checkNotNullParameter(transform, "transform");
      Object var10000;
      if (Result.isSuccess-impl($this$map)) {
         Result.Companion var2 = Result.Companion;
         Object var3 = transform.invoke($this$map);
         var10000 = Result.constructor-impl(var3);
      } else {
         var10000 = Result.constructor-impl($this$map);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object mapCatching(Object $this$mapCatching, Function1 transform) {
      Intrinsics.checkNotNullParameter(transform, "transform");
      Object var10000;
      if (Result.isSuccess-impl($this$mapCatching)) {
         Object $this$mapCatching_u24lambda_u2d3 = $this$mapCatching;

         Object var3;
         try {
            Result.Companion var7 = Result.Companion;
            int var8 = 0;
            Object $this$mapCatching_u24lambda_u2d3 = transform.invoke($this$mapCatching_u24lambda_u2d3);
            var3 = Result.constructor-impl($this$mapCatching_u24lambda_u2d3);
         } catch (Throwable var6) {
            Result.Companion var5 = Result.Companion;
            var3 = Result.constructor-impl(createFailure(var6));
         }

         var10000 = var3;
      } else {
         var10000 = Result.constructor-impl($this$mapCatching);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object recover(Object $this$recover, Function1 transform) {
      Intrinsics.checkNotNullParameter(transform, "transform");
      Throwable exception = Result.exceptionOrNull-impl($this$recover);
      Object var10000;
      if (exception == null) {
         var10000 = $this$recover;
      } else {
         Result.Companion var3 = Result.Companion;
         Object var4 = transform.invoke(exception);
         var10000 = Result.constructor-impl(var4);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object recoverCatching(Object $this$recoverCatching, Function1 transform) {
      Intrinsics.checkNotNullParameter(transform, "transform");
      Throwable exception = Result.exceptionOrNull-impl($this$recoverCatching);
      Object var10000;
      if (exception == null) {
         var10000 = $this$recoverCatching;
      } else {
         Object var4;
         try {
            Result.Companion var8 = Result.Companion;
            int var9 = 0;
            Object $this$recoverCatching_u24lambda_u2d5 = transform.invoke(exception);
            var4 = Result.constructor-impl($this$recoverCatching_u24lambda_u2d5);
         } catch (Throwable var7) {
            Result.Companion var6 = Result.Companion;
            var4 = Result.constructor-impl(createFailure(var7));
         }

         var10000 = var4;
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object onFailure(Object $this$onFailure, Function1 action) {
      Intrinsics.checkNotNullParameter(action, "action");
      Throwable it = Result.exceptionOrNull-impl($this$onFailure);
      if (it != null) {
         int var5 = 0;
         action.invoke(it);
      }

      return $this$onFailure;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final Object onSuccess(Object $this$onSuccess, Function1 action) {
      Intrinsics.checkNotNullParameter(action, "action");
      if (Result.isSuccess-impl($this$onSuccess)) {
         action.invoke($this$onSuccess);
      }

      return $this$onSuccess;
   }
}
