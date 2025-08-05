package net.ccbluex.liquidbounce.utils.extensions;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 2,
   xi = 48,
   d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003\u001a*\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0004\u0012\u00020\u00070\nH\u0086\bø\u0001\u0000\u001a\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003\u001a\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\u00102\u0006\u0010\u0012\u001a\u00020\u0003\u001a\u0012\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0015\u001a\f\u0010\u0016\u001a\u0004\u0018\u00010\u000b*\u00020\u0011\u001a\n\u0010\u0017\u001a\u00020\u0015*\u00020\u0011\u001a\f\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u0011\u001a\n\u0010\u001a\u001a\u00020\u001b*\u00020\u0011\u001a\u0011\u0010\u001c\u001a\u0004\u0018\u00010\u0007*\u00020\u0011¢\u0006\u0002\u0010\u001d\u001a\n\u0010\u001e\u001a\u00020\u0007*\u00020\u0011\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001f"},
   d2 = {"AxisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "x", "", "y", "z", "collideBlock", "", "aabb", "predicate", "Lkotlin/Function1;", "Lnet/minecraft/block/Block;", "getBlockName", "", "id", "searchBlocks", "", "Lnet/minecraft/util/BlockPos;", "radius", "down", "height", "", "getBlock", "getCenterDistance", "getMaterial", "Lnet/minecraft/block/material/Material;", "getVec", "Lnet/minecraft/util/Vec3;", "isFullBlock", "(Lnet/minecraft/util/BlockPos;)Ljava/lang/Boolean;", "isReplaceable", "CrossSine"}
)
public final class BlockExtensionKt {
   @Nullable
   public static final Block getBlock(@NotNull BlockPos $this$getBlock) {
      Intrinsics.checkNotNullParameter($this$getBlock, "<this>");
      return BlockUtils.getBlock($this$getBlock);
   }

   @NotNull
   public static final Vec3 getVec(@NotNull BlockPos $this$getVec) {
      Intrinsics.checkNotNullParameter($this$getVec, "<this>");
      return new Vec3((double)$this$getVec.func_177958_n() + (double)0.5F, (double)$this$getVec.func_177956_o() + (double)0.5F, (double)$this$getVec.func_177952_p() + (double)0.5F);
   }

   @Nullable
   public static final Material getMaterial(@NotNull BlockPos $this$getMaterial) {
      Intrinsics.checkNotNullParameter($this$getMaterial, "<this>");
      Block var10000 = getBlock($this$getMaterial);
      return var10000 == null ? null : var10000.func_149688_o();
   }

   @Nullable
   public static final Boolean isFullBlock(@NotNull BlockPos $this$isFullBlock) {
      Intrinsics.checkNotNullParameter($this$isFullBlock, "<this>");
      Block var10000 = getBlock($this$isFullBlock);
      return var10000 == null ? null : var10000.func_149730_j();
   }

   public static final boolean isReplaceable(@NotNull BlockPos $this$isReplaceable) {
      Intrinsics.checkNotNullParameter($this$isReplaceable, "<this>");
      Material var10000 = getMaterial($this$isReplaceable);
      boolean var2;
      if (var10000 == null) {
         var2 = false;
      } else {
         boolean var1 = var10000.func_76222_j();
         var2 = var1;
      }

      return var2;
   }

   public static final double getCenterDistance(@NotNull BlockPos $this$getCenterDistance) {
      Intrinsics.checkNotNullParameter($this$getCenterDistance, "<this>");
      return ClientUtils.INSTANCE.getMc().field_71439_g.func_70011_f((double)$this$getCenterDistance.func_177958_n() + (double)0.5F, (double)$this$getCenterDistance.func_177956_o() + (double)0.5F, (double)$this$getCenterDistance.func_177952_p() + (double)0.5F);
   }

   @NotNull
   public static final AxisAlignedBB AxisAlignedBB(int x, int y, int z) {
      return new AxisAlignedBB((double)x, (double)y, (double)z, (double)x + (double)1.0F, (double)y + (double)1.0F, (double)z + (double)1.0F);
   }

   @NotNull
   public static final AxisAlignedBB down(@NotNull AxisAlignedBB $this$down, double height) {
      Intrinsics.checkNotNullParameter($this$down, "<this>");
      return new AxisAlignedBB($this$down.field_72340_a, $this$down.field_72338_b, $this$down.field_72339_c, $this$down.field_72336_d, $this$down.field_72337_e - height, $this$down.field_72334_f);
   }

   @NotNull
   public static final Map searchBlocks(int radius) {
      Map blocks = (Map)(new LinkedHashMap());
      int var2 = radius;
      int var3 = -radius + 1;
      int x;
      if (var3 <= radius) {
         do {
            x = var2--;
            int var5 = radius;
            int var6 = -radius + 1;
            int y;
            if (var6 <= radius) {
               do {
                  y = var5--;
                  int var8 = radius;
                  int var9 = -radius + 1;
                  int z;
                  if (var9 <= radius) {
                     do {
                        z = var8--;
                        BlockPos blockPos = new BlockPos((int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70165_t + x, (int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70163_u + y, (int)ClientUtils.INSTANCE.getMc().field_71439_g.field_70161_v + z);
                        Block var10000 = getBlock(blockPos);
                        if (var10000 != null) {
                           Block block = var10000;
                           blocks.put(blockPos, block);
                        }
                     } while(z != var9);
                  }
               } while(y != var6);
            }
         } while(x != var3);
      }

      return blocks;
   }

   @NotNull
   public static final String getBlockName(int id) {
      String var1 = Block.func_149729_e(id).func_149732_F();
      Intrinsics.checkNotNullExpressionValue(var1, "getBlockById(id).localizedName");
      return var1;
   }

   public static final boolean collideBlock(@NotNull AxisAlignedBB aabb, @NotNull Function1 predicate) {
      Intrinsics.checkNotNullParameter(aabb, "aabb");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      int $i$f$collideBlock = 0;
      int var3 = MathHelper.func_76128_c(aabb.field_72340_a);
      int var4 = MathHelper.func_76128_c(aabb.field_72336_d) + 1;

      while(var3 < var4) {
         int x = var3++;
         int var6 = MathHelper.func_76128_c(aabb.field_72339_c);
         int var7 = MathHelper.func_76128_c(aabb.field_72334_f) + 1;

         while(var6 < var7) {
            int z = var6++;
            Block block = getBlock(new BlockPos((double)x, aabb.field_72338_b, (double)z));
            if (!(Boolean)predicate.invoke(block)) {
               return false;
            }
         }
      }

      return true;
   }
}
