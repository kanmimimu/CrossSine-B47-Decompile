package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@Metadata(
   mv = {1, 6, 0},
   k = 3,
   xi = 48,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class SilentAura$getTarget$$inlined$sortBy$1 implements Comparator {
   public final int compare(Object a, Object b) {
      EntityLivingBase it = (EntityLivingBase)a;
      int var4 = 0;
      EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNullExpressionValue(var5, "mc.thePlayer");
      Comparable var10000 = (Comparable)EntityExtensionKt.getDistanceToEntityBox((Entity)var5, (Entity)it);
      it = (EntityLivingBase)b;
      Comparable var6 = var10000;
      var4 = 0;
      var5 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNullExpressionValue(var5, "mc.thePlayer");
      return ComparisonsKt.compareValues(var6, (Comparable)EntityExtensionKt.getDistanceToEntityBox((Entity)var5, (Entity)it));
   }
}
