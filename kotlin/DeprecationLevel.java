package kotlin;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/DeprecationLevel;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "HIDDEN", "kotlin-stdlib"}
)
public enum DeprecationLevel {
   WARNING,
   ERROR,
   HIDDEN;

   // $FF: synthetic method
   private static final DeprecationLevel[] $values() {
      DeprecationLevel[] var0 = new DeprecationLevel[]{WARNING, ERROR, HIDDEN};
      return var0;
   }
}
