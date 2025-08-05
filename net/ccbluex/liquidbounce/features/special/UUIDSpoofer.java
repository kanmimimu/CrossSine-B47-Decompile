package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0004H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/special/UUIDSpoofer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "spoofId", "", "getSpoofId", "()Ljava/lang/String;", "setSpoofId", "(Ljava/lang/String;)V", "getUUID", "CrossSine"}
)
public final class UUIDSpoofer extends MinecraftInstance {
   @NotNull
   public static final UUIDSpoofer INSTANCE = new UUIDSpoofer();
   @Nullable
   private static String spoofId;

   private UUIDSpoofer() {
   }

   @Nullable
   public final String getSpoofId() {
      return spoofId;
   }

   public final void setSpoofId(@Nullable String var1) {
      spoofId = var1;
   }

   @JvmStatic
   @NotNull
   public static final String getUUID() {
      UUIDSpoofer var10000 = INSTANCE;
      String var1;
      if (spoofId == null) {
         var1 = MinecraftInstance.mc.field_71449_j.func_148255_b();
      } else {
         UUIDSpoofer var2 = INSTANCE;
         var1 = spoofId;
         Intrinsics.checkNotNull(var1);
      }

      String var0 = var1;
      Intrinsics.checkNotNullExpressionValue(var0, "if (spoofId == null) mc.…n.playerID else spoofId!!");
      return StringsKt.replace$default(var0, "-", "", false, 4, (Object)null);
   }
}
