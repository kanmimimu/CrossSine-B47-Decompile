package kotlin.jdk7;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001aH\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"},
   d2 = {"closeFinally", "", "Ljava/lang/AutoCloseable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/lang/AutoCloseable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"},
   pn = "kotlin"
)
@JvmName(
   name = "AutoCloseableKt"
)
public final class AutoCloseableKt {
   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final Object use(AutoCloseable $this$use, Function1 block) {
      Intrinsics.checkNotNullParameter(block, "block");
      Throwable exception = null;

      Object var3;
      try {
         var3 = block.invoke($this$use);
      } catch (Throwable e) {
         exception = e;
         throw e;
      } finally {
         InlineMarker.finallyStart(1);
         closeFinally($this$use, exception);
         InlineMarker.finallyEnd(1);
      }

      return var3;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @PublishedApi
   public static final void closeFinally(@Nullable AutoCloseable $this$closeFinally, @Nullable Throwable cause) {
      if ($this$closeFinally != null) {
         if (cause == null) {
            $this$closeFinally.close();
         } else {
            try {
               $this$closeFinally.close();
            } catch (Throwable closeException) {
               ExceptionsKt.addSuppressed(cause, closeException);
            }
         }
      }

   }
}
