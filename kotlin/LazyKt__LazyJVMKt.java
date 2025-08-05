package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 5,
   xi = 49,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004¨\u0006\t"},
   d2 = {"lazy", "Lkotlin/Lazy;", "T", "initializer", "Lkotlin/Function0;", "lock", "", "mode", "Lkotlin/LazyThreadSafetyMode;", "kotlin-stdlib"},
   xs = "kotlin/LazyKt"
)
class LazyKt__LazyJVMKt {
   @NotNull
   public static final Lazy lazy(@NotNull Function0 initializer) {
      Intrinsics.checkNotNullParameter(initializer, "initializer");
      return new SynchronizedLazyImpl(initializer, (Object)null, 2, (DefaultConstructorMarker)null);
   }

   @NotNull
   public static final Lazy lazy(@NotNull LazyThreadSafetyMode mode, @NotNull Function0 initializer) {
      Intrinsics.checkNotNullParameter(mode, "mode");
      Intrinsics.checkNotNullParameter(initializer, "initializer");
      int var3 = LazyKt__LazyJVMKt.WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
      Lazy var10000;
      switch (var3) {
         case 1:
            var10000 = new SynchronizedLazyImpl(initializer, (Object)null, 2, (DefaultConstructorMarker)null);
            break;
         case 2:
            var10000 = new SafePublicationLazyImpl(initializer);
            break;
         case 3:
            var10000 = new UnsafeLazyImpl(initializer);
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   @NotNull
   public static final Lazy lazy(@Nullable Object lock, @NotNull Function0 initializer) {
      Intrinsics.checkNotNullParameter(initializer, "initializer");
      return new SynchronizedLazyImpl(initializer, lock);
   }

   public LazyKt__LazyJVMKt() {
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
         int[] var0 = new int[LazyThreadSafetyMode.values().length];
         var0[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
         var0[LazyThreadSafetyMode.PUBLICATION.ordinal()] = 2;
         var0[LazyThreadSafetyMode.NONE.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
