package kotlin.coroutines;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0007¨\u0006\b"},
   d2 = {"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}
)
public final class CoroutineContextImplKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @Nullable
   public static final CoroutineContext.Element getPolymorphicElement(@NotNull CoroutineContext.Element $this$getPolymorphicElement, @NotNull CoroutineContext.Key key) {
      Intrinsics.checkNotNullParameter($this$getPolymorphicElement, "<this>");
      Intrinsics.checkNotNullParameter(key, "key");
      if (key instanceof AbstractCoroutineContextKey) {
         CoroutineContext.Element var10000;
         if (((AbstractCoroutineContextKey)key).isSubKey$kotlin_stdlib($this$getPolymorphicElement.getKey())) {
            CoroutineContext.Element var2 = ((AbstractCoroutineContextKey)key).tryCast$kotlin_stdlib($this$getPolymorphicElement);
            var10000 = var2 instanceof CoroutineContext.Element ? var2 : null;
         } else {
            var10000 = null;
         }

         return var10000;
      } else {
         return $this$getPolymorphicElement.getKey() == key ? $this$getPolymorphicElement : null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final CoroutineContext minusPolymorphicKey(@NotNull CoroutineContext.Element $this$minusPolymorphicKey, @NotNull CoroutineContext.Key key) {
      Intrinsics.checkNotNullParameter($this$minusPolymorphicKey, "<this>");
      Intrinsics.checkNotNullParameter(key, "key");
      if (!(key instanceof AbstractCoroutineContextKey)) {
         return $this$minusPolymorphicKey.getKey() == key ? (CoroutineContext)EmptyCoroutineContext.INSTANCE : (CoroutineContext)$this$minusPolymorphicKey;
      } else {
         return ((AbstractCoroutineContextKey)key).isSubKey$kotlin_stdlib($this$minusPolymorphicKey.getKey()) && ((AbstractCoroutineContextKey)key).tryCast$kotlin_stdlib($this$minusPolymorphicKey) != null ? (CoroutineContext)EmptyCoroutineContext.INSTANCE : (CoroutineContext)$this$minusPolymorphicKey;
      }
   }
}
