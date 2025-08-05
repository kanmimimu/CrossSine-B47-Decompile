package kotlin.internal;

import kotlin.Metadata;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/internal/RequireKotlinVersionKind;", "", "(Ljava/lang/String;I)V", "LANGUAGE_VERSION", "COMPILER_VERSION", "API_VERSION", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.2"
)
public enum RequireKotlinVersionKind {
   LANGUAGE_VERSION,
   COMPILER_VERSION,
   API_VERSION;

   // $FF: synthetic method
   private static final RequireKotlinVersionKind[] $values() {
      RequireKotlinVersionKind[] var0 = new RequireKotlinVersionKind[]{LANGUAGE_VERSION, COMPILER_VERSION, API_VERSION};
      return var0;
   }
}
