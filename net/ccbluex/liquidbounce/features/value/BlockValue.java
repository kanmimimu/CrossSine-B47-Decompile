package net.ccbluex.liquidbounce.features.value;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/value/BlockValue;", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "name", "", "value", "", "(Ljava/lang/String;I)V", "CrossSine"}
)
public final class BlockValue extends IntegerValue {
   public BlockValue(@NotNull String name, int value) {
      Intrinsics.checkNotNullParameter(name, "name");
      super(name, value, 1, 197);
   }
}
