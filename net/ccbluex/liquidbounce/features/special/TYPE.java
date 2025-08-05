package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/special/TYPE;", "", "(Ljava/lang/String;I)V", "SUCCESS", "INFO", "ERROR", "WARNING", "CrossSine"}
)
public enum TYPE {
   SUCCESS,
   INFO,
   ERROR,
   WARNING;

   // $FF: synthetic method
   private static final TYPE[] $values() {
      TYPE[] var0 = new TYPE[]{SUCCESS, INFO, ERROR, WARNING};
      return var0;
   }
}
