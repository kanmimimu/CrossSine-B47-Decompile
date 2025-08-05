package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"},
   d2 = {"getFullName", "", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "CrossSine"}
)
public final class NetworkExtensionKt {
   @NotNull
   public static final String getFullName(@NotNull NetworkPlayerInfo $this$getFullName) {
      Intrinsics.checkNotNullParameter($this$getFullName, "<this>");
      if ($this$getFullName.func_178854_k() != null) {
         IChatComponent var4 = $this$getFullName.func_178854_k();
         Intrinsics.checkNotNull(var4);
         String var3 = var4.func_150254_d();
         Intrinsics.checkNotNullExpressionValue(var3, "displayName!!.formattedText");
         return var3;
      } else {
         ScorePlayerTeam team = $this$getFullName.func_178850_i();
         String name = $this$getFullName.func_178845_a().getName();
         String var10000 = team == null ? null : team.func_142053_d(name);
         if (var10000 == null) {
            Intrinsics.checkNotNullExpressionValue(name, "name");
            var10000 = name;
         }

         return var10000;
      }
   }
}
