package kotlin.text;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\f\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"},
   d2 = {"titlecaseImpl", "", "", "kotlin-stdlib"}
)
public final class _OneToManyTitlecaseMappingsKt {
   @NotNull
   public static final String titlecaseImpl(char $this$titlecaseImpl) {
      String uppercase = String.valueOf($this$titlecaseImpl).toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(uppercase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
      if (uppercase.length() > 1) {
         String var10000;
         if ($this$titlecaseImpl == 329) {
            var10000 = uppercase;
         } else {
            char var2 = uppercase.charAt(0);
            byte var4 = 1;
            String var5 = uppercase.substring(var4);
            Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).substring(startIndex)");
            String var6 = var5.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            var10000 = var2 + var6;
         }

         return var10000;
      } else {
         return String.valueOf(Character.toTitleCase($this$titlecaseImpl));
      }
   }
}
