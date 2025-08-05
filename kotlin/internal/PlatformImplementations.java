package kotlin.internal;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\u0013"},
   d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "getSuppressed", "", "ReflectThrowable", "kotlin-stdlib"}
)
public class PlatformImplementations {
   public void addSuppressed(@NotNull Throwable cause, @NotNull Throwable exception) {
      Intrinsics.checkNotNullParameter(cause, "cause");
      Intrinsics.checkNotNullParameter(exception, "exception");
      Method var3 = PlatformImplementations.ReflectThrowable.addSuppressed;
      if (var3 != null) {
         Object[] var4 = new Object[]{exception};
         var3.invoke(cause, var4);
      }

   }

   @NotNull
   public List getSuppressed(@NotNull Throwable exception) {
      Intrinsics.checkNotNullParameter(exception, "exception");
      Method var3 = PlatformImplementations.ReflectThrowable.getSuppressed;
      Object it = var3 == null ? null : var3.invoke(exception);
      List var10000;
      if (it == null) {
         var10000 = CollectionsKt.emptyList();
      } else {
         int var5 = 0;
         var10000 = ArraysKt.asList((Throwable[])it);
      }

      return var10000;
   }

   @Nullable
   public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
      Intrinsics.checkNotNullParameter(matchResult, "matchResult");
      Intrinsics.checkNotNullParameter(name, "name");
      throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
   }

   @NotNull
   public Random defaultPlatformRandom() {
      return new FallbackThreadLocalRandom();
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"},
      d2 = {"Lkotlin/internal/PlatformImplementations$ReflectThrowable;", "", "()V", "addSuppressed", "Ljava/lang/reflect/Method;", "getSuppressed", "kotlin-stdlib"}
   )
   private static final class ReflectThrowable {
      @NotNull
      public static final ReflectThrowable INSTANCE = new ReflectThrowable();
      @JvmField
      @Nullable
      public static final Method addSuppressed;
      @JvmField
      @Nullable
      public static final Method getSuppressed;

      static {
         Class throwableClass = Throwable.class;
         Method[] throwableMethods = throwableClass.getMethods();
         Intrinsics.checkNotNullExpressionValue(throwableMethods, "throwableMethods");
         Method[] var4 = throwableMethods;
         int var5 = 0;
         int var6 = throwableMethods.length;

         Method var16;
         while(true) {
            if (var5 >= var6) {
               var16 = null;
               break;
            }

            Method it;
            label34: {
               it = var4[var5];
               ++var5;
               int var9 = 0;
               if (Intrinsics.areEqual((Object)it.getName(), (Object)"addSuppressed")) {
                  Class[] var10 = it.getParameterTypes();
                  Intrinsics.checkNotNullExpressionValue(var10, "it.parameterTypes");
                  if (Intrinsics.areEqual((Object)ArraysKt.singleOrNull((Object[])var10), (Object)throwableClass)) {
                     var10000 = true;
                     break label34;
                  }
               }

               var10000 = false;
            }

            if (var10000) {
               var16 = it;
               break;
            }
         }

         addSuppressed = var16;
         var4 = throwableMethods;
         var5 = 0;
         var6 = throwableMethods.length;

         while(true) {
            if (var5 < var6) {
               Method it = var4[var5];
               ++var5;
               int var15 = 0;
               if (!Intrinsics.areEqual((Object)it.getName(), (Object)"getSuppressed")) {
                  continue;
               }

               var16 = it;
               break;
            }

            var16 = null;
            break;
         }

         getSuppressed = var16;
      }
   }
}
