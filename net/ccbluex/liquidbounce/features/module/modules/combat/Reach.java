package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Reach",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Reach;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "reachValue", "Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;", "getReachValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatRangeValue;", "tag", "", "getTag", "()Ljava/lang/String;", "throughWall", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getThroughWall", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "call", "", "getReach", "", "CrossSine"}
)
public final class Reach extends Module {
   @NotNull
   public static final Reach INSTANCE = new Reach();
   @NotNull
   private static final FloatRangeValue reachValue = new FloatRangeValue("Range", 3.0F, 3.2F, 3.0F, 7.0F);
   @NotNull
   private static final BoolValue throughWall = new BoolValue("Through-Wall", false);

   private Reach() {
   }

   @NotNull
   public final FloatRangeValue getReachValue() {
      return reachValue;
   }

   @NotNull
   public final BoolValue getThroughWall() {
      return throughWall;
   }

   public final double getReach() {
      double min = (double)RangesKt.coerceAtMost(((Number)reachValue.get().getStart()).floatValue(), ((Number)reachValue.get().getEndInclusive()).floatValue());
      double max = (double)RangesKt.coerceAtLeast(((Number)reachValue.get().getStart()).floatValue(), ((Number)reachValue.get().getEndInclusive()).floatValue());
      return Math.random() * (max - min) + min;
   }

   @NotNull
   public String getTag() {
      return (new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH))).format(reachValue.get().getEndInclusive()) + " - " + (new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH))).format(reachValue.get().getStart());
   }

   public final boolean call() {
      if (!(Boolean)throughWall.get() && MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x != null) {
         BlockPos p = MinecraftInstance.mc.field_71476_x.func_178782_a();
         if (p != null && !Intrinsics.areEqual((Object)MinecraftInstance.mc.field_71441_e.func_180495_p(p).func_177230_c(), (Object)Blocks.field_150350_a)) {
            return false;
         }
      }

      double r = this.getReach();
      Object[] o = PlayerUtils.INSTANCE.getEntity(r, (double)0.0F);
      if (o == null) {
         return false;
      } else {
         Object var10000 = o[0];
         if (o[0] == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
         } else {
            Entity en = (Entity)var10000;
            Minecraft var6 = MinecraftInstance.mc;
            MovingObjectPosition var10001 = new MovingObjectPosition;
            Object var10004 = o[1];
            if (o[1] == null) {
               throw new NullPointerException("null cannot be cast to non-null type net.minecraft.util.Vec3");
            } else {
               var10001.<init>(en, (Vec3)var10004);
               var6.field_71476_x = var10001;
               MinecraftInstance.mc.field_147125_j = en;
               return true;
            }
         }
      }
   }
}
