package net.ccbluex.liquidbounce.utils.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J&\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u000f0\u0016H\u0007J&\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u000f0\u0016H\u0007J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0007J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019H\u0007J(\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\t2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000fJ\u0010\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0007H\u0007J\u000e\u0010%\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0007J\u001d\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050'¢\u0006\u0002\u0010(J\u0010\u0010)\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0014\u0010*\u001a\u0004\u0018\u00010+2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0012\u0010,\u001a\u00020-2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J,\u0010.\u001a\u00020\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u001e\u001a\u0004\u0018\u00010\t2\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u000fH\u0002J\u0006\u0010/\u001a\u00020\u000fJ\u0010\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u0014H\u0007J\u0012\u00101\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u0012\u00102\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001c\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\t042\u0006\u00105\u001a\u00020\u0007H\u0007R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blockNames", "", "Lkotlin/Pair;", "", "", "blockRelativeToPlayer", "Lnet/minecraft/block/Block;", "offsetX", "", "offsetY", "offsetZ", "canBeClicked", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "collideBlock", "axisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "collide", "Lkotlin/Function1;", "collideBlockIntersects", "floorVec3", "Lnet/minecraft/util/Vec3;", "vec3", "getBlock", "getBlockHardness", "", "block", "itemStack", "Lnet/minecraft/item/ItemStack;", "ignoreSlow", "ignoreGround", "getBlockName", "id", "getBlockName2", "getBlockNamesAndIDs", "", "()[Lkotlin/Pair;", "getCenterDistance", "getMaterial", "Lnet/minecraft/block/material/Material;", "getState", "Lnet/minecraft/block/state/IBlockState;", "getToolDigEfficiency", "insideBlock", "bb", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", "CrossSine"}
)
public final class BlockUtils extends MinecraftInstance {
   @NotNull
   public static final BlockUtils INSTANCE = new BlockUtils();
   @NotNull
   private static final List blockNames = (List)(new ArrayList());

   private BlockUtils() {
   }

   @NotNull
   public final Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
      Block var7 = MinecraftInstance.mc.field_71441_e.func_180495_p((new BlockPos((Entity)MinecraftInstance.mc.field_71439_g)).func_177963_a(offsetX, offsetY, offsetZ)).func_177230_c();
      Intrinsics.checkNotNullExpressionValue(var7, "mc.theWorld.getBlockStat… offsetY, offsetZ)).block");
      return var7;
   }

   public final boolean insideBlock() {
      if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 5) {
         return false;
      } else {
         AxisAlignedBB var1 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
         Intrinsics.checkNotNullExpressionValue(var1, "mc.thePlayer.entityBoundingBox");
         return insideBlock(var1);
      }
   }

   @JvmStatic
   public static final boolean insideBlock(@NotNull AxisAlignedBB bb) {
      Intrinsics.checkNotNullParameter(bb, "bb");
      WorldClient world = MinecraftInstance.mc.field_71441_e;
      int var2 = MathHelper.func_76128_c(bb.field_72340_a);
      int var3 = MathHelper.func_76128_c(bb.field_72336_d) + 1;

      while(var2 < var3) {
         int x = var2++;
         int var5 = MathHelper.func_76128_c(bb.field_72338_b);
         int var6 = MathHelper.func_76128_c(bb.field_72337_e) + 1;

         while(var5 < var6) {
            int y = var5++;
            int var8 = MathHelper.func_76128_c(bb.field_72339_c);
            int var9 = MathHelper.func_76128_c(bb.field_72334_f) + 1;

            while(var8 < var9) {
               int z = var8++;
               Block block = world.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
               Object boundingBox = null;
               if (block != null && !(block instanceof BlockAir)) {
                  AxisAlignedBB it = block.func_180640_a((World)world, new BlockPos(x, y, z), world.func_180495_p(new BlockPos(x, y, z)));
                  int var15 = 0;
                  if (it != null && bb.func_72326_a(it)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public final float getBlockHardness(@NotNull Block block, @Nullable ItemStack itemStack, boolean ignoreSlow, boolean ignoreGround) {
      Intrinsics.checkNotNullParameter(block, "block");
      float getBlockHardness = block.func_176195_g((World)MinecraftInstance.mc.field_71441_e, (BlockPos)null);
      if (getBlockHardness < 0.0F) {
         return 0.0F;
      } else {
         return !block.func_149688_o().func_76229_l() && (itemStack == null || !itemStack.func_150998_b(block)) ? this.getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 100.0F : this.getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 30.0F;
      }
   }

   private final float getToolDigEfficiency(ItemStack itemStack, Block block, boolean ignoreSlow, boolean ignoreGround) {
      float n = itemStack == null ? 1.0F : itemStack.func_77973_b().func_150893_a(itemStack, block);
      if (n > 1.0F) {
         int getEnchantmentLevel = EnchantmentHelper.func_77506_a(Enchantment.field_77349_p.field_77352_x, itemStack);
         if (getEnchantmentLevel > 0 && itemStack != null) {
            n += (float)(getEnchantmentLevel * getEnchantmentLevel + 1);
         }
      }

      if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76422_e)) {
         n *= 1.0F + (float)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c() + 1) * 0.2F;
      }

      if (!ignoreSlow) {
         if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76419_f)) {
            float var10000;
            switch (MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c()) {
               case 0:
                  var10000 = 0.3F;
                  break;
               case 1:
                  var10000 = 0.09F;
                  break;
               case 2:
                  var10000 = 0.0027F;
                  break;
               default:
                  var10000 = 8.1E-4F;
            }

            float n2 = var10000;
            n *= n2;
         }

         if (MinecraftInstance.mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_77510_g((EntityLivingBase)MinecraftInstance.mc.field_71439_g)) {
            n /= 5.0F;
         }

         if (!MinecraftInstance.mc.field_71439_g.field_70122_E && !ignoreGround) {
            n /= 5.0F;
         }
      }

      return n;
   }

   @JvmStatic
   @Nullable
   public static final Block getBlock(@Nullable Vec3 vec3) {
      BlockUtils var10000 = INSTANCE;
      return getBlock(new BlockPos(vec3));
   }

   @JvmStatic
   @Nullable
   public static final Block getBlock(@Nullable BlockPos blockPos) {
      WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
      Block var1;
      if (var10000 == null) {
         var1 = null;
      } else {
         IBlockState var2 = var10000.func_180495_p(blockPos);
         var1 = var2 == null ? null : var2.func_177230_c();
      }

      return var1;
   }

   @JvmStatic
   @Nullable
   public static final Material getMaterial(@Nullable BlockPos blockPos) {
      BlockUtils var10000 = INSTANCE;
      Block var1 = getBlock(blockPos);
      return var1 == null ? null : var1.func_149688_o();
   }

   @JvmStatic
   public static final boolean isReplaceable(@Nullable BlockPos blockPos) {
      BlockUtils var10000 = INSTANCE;
      Material var2 = getMaterial(blockPos);
      boolean var3;
      if (var2 == null) {
         var3 = false;
      } else {
         boolean var1 = var2.func_76222_j();
         var3 = var1;
      }

      return var3;
   }

   @JvmStatic
   @NotNull
   public static final IBlockState getState(@Nullable BlockPos blockPos) {
      IBlockState var1 = MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos);
      Intrinsics.checkNotNullExpressionValue(var1, "mc.theWorld.getBlockState(blockPos)");
      return var1;
   }

   @JvmStatic
   public static final boolean canBeClicked(@Nullable BlockPos blockPos) {
      BlockUtils var10000 = INSTANCE;
      Block var2 = getBlock(blockPos);
      boolean var3;
      if (var2 == null) {
         var3 = false;
      } else {
         BlockUtils var10001 = INSTANCE;
         boolean var1 = var2.func_176209_a(getState(blockPos), false);
         var3 = var1;
      }

      return var3 && MinecraftInstance.mc.field_71441_e.func_175723_af().func_177746_a(blockPos);
   }

   @JvmStatic
   @NotNull
   public static final String getBlockName(int id) {
      String var1 = Block.func_149729_e(id).func_149732_F();
      Intrinsics.checkNotNullExpressionValue(var1, "getBlockById(id).localizedName");
      return var1;
   }

   @JvmStatic
   public static final boolean isFullBlock(@Nullable BlockPos blockPos) {
      BlockUtils var10000 = INSTANCE;
      Block var3 = getBlock(blockPos);
      AxisAlignedBB var4;
      if (var3 == null) {
         var4 = null;
      } else {
         World var10001 = (World)MinecraftInstance.mc.field_71441_e;
         BlockUtils var10003 = INSTANCE;
         var4 = var3.func_180640_a(var10001, blockPos, getState(blockPos));
      }

      AxisAlignedBB axisAlignedBB = var4;
      if (axisAlignedBB == null) {
         return false;
      } else {
         return axisAlignedBB.field_72336_d - axisAlignedBB.field_72340_a == (double)1.0F && axisAlignedBB.field_72337_e - axisAlignedBB.field_72338_b == (double)1.0F && axisAlignedBB.field_72334_f - axisAlignedBB.field_72339_c == (double)1.0F;
      }
   }

   @JvmStatic
   public static final double getCenterDistance(@NotNull BlockPos blockPos) {
      Intrinsics.checkNotNullParameter(blockPos, "blockPos");
      return MinecraftInstance.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n() + (double)0.5F, (double)blockPos.func_177956_o() + (double)0.5F, (double)blockPos.func_177952_p() + (double)0.5F);
   }

   @JvmStatic
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
                        BlockPos blockPos = new BlockPos((int)MinecraftInstance.mc.field_71439_g.field_70165_t + x, (int)MinecraftInstance.mc.field_71439_g.field_70163_u + y, (int)MinecraftInstance.mc.field_71439_g.field_70161_v + z);
                        BlockUtils var10000 = INSTANCE;
                        Block var13 = getBlock(blockPos);
                        if (var13 != null) {
                           Block block = var13;
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

   @JvmStatic
   public static final boolean collideBlock(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
      Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
      Intrinsics.checkNotNullParameter(collide, "collide");
      int var2 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a);
      int var3 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;

      while(var2 < var3) {
         int x = var2++;
         int var5 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c);
         int var6 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;

         while(var5 < var6) {
            int z = var5++;
            BlockUtils var10000 = INSTANCE;
            Block block = getBlock(new BlockPos((double)x, axisAlignedBB.field_72338_b, (double)z));
            if (!(Boolean)collide.invoke(block)) {
               return false;
            }
         }
      }

      return true;
   }

   @NotNull
   public final Pair[] getBlockNamesAndIDs() {
      if (blockNames.isEmpty()) {
         int thisCollection$iv = 0;

         while(thisCollection$iv < 32769) {
            int id = thisCollection$iv++;
            Block block = Block.func_149729_e(id);
            if (block != Blocks.field_150350_a) {
               List var10000 = blockNames;
               String var4 = block.getRegistryName();
               Intrinsics.checkNotNullExpressionValue(var4, "block.registryName");
               CharSequence var11 = (CharSequence)var4;
               Regex var5 = new Regex("^minecraft:");
               String var6 = "";
               var10000.add(TuplesKt.to(var5.replace(var11, var6), id));
            }
         }

         List $this$sortBy$iv = blockNames;
         int $i$f$sortBy = 0;
         if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new BlockUtils$getBlockNamesAndIDs$$inlined$sortBy$1());
         }
      }

      Collection $this$toTypedArray$iv = (Collection)blockNames;
      int $i$f$toTypedArray = 0;
      Object[] var12 = $this$toTypedArray$iv.toArray(new Pair[0]);
      if (var12 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         return (Pair[])var12;
      }
   }

   @JvmStatic
   public static final boolean collideBlockIntersects(@NotNull AxisAlignedBB axisAlignedBB, @NotNull Function1 collide) {
      Intrinsics.checkNotNullParameter(axisAlignedBB, "axisAlignedBB");
      Intrinsics.checkNotNullParameter(collide, "collide");
      int var2 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a);
      int var3 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d) + 1;

      while(var2 < var3) {
         int x = var2++;
         int var5 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c);
         int var6 = MathHelper.func_76128_c(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f) + 1;

         while(var5 < var6) {
            int z = var5++;
            BlockPos blockPos = new BlockPos((double)x, axisAlignedBB.field_72338_b, (double)z);
            BlockUtils var10000 = INSTANCE;
            Block block = getBlock(blockPos);
            if ((Boolean)collide.invoke(block)) {
               AxisAlignedBB var12;
               if (block == null) {
                  var12 = null;
               } else {
                  World var10001 = (World)MinecraftInstance.mc.field_71441_e;
                  BlockUtils var10003 = INSTANCE;
                  var12 = block.func_180640_a(var10001, blockPos, getState(blockPos));
               }

               AxisAlignedBB boundingBox = var12;
               if (boundingBox != null && MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72326_a(boundingBox)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @NotNull
   public final String getBlockName2(int id) {
      String var2 = Block.func_149729_e(id).getRegistryName();
      Intrinsics.checkNotNullExpressionValue(var2, "getBlockById(id).registryName");
      CharSequence var5 = (CharSequence)var2;
      Regex var3 = new Regex("^minecraft:");
      String var4 = "";
      return var3.replace(var5, var4);
   }

   @JvmStatic
   @NotNull
   public static final Vec3 floorVec3(@NotNull Vec3 vec3) {
      Intrinsics.checkNotNullParameter(vec3, "vec3");
      return new Vec3(Math.floor(vec3.field_72450_a), Math.floor(vec3.field_72448_b), Math.floor(vec3.field_72449_c));
   }
}
