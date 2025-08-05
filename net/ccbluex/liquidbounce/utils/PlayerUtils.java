package net.ccbluex.liquidbounce.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001bH\u0007J\u000e\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u0004J\u001e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"J\r\u0010%\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010&J%\u0010'\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010(2\u0006\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\"¢\u0006\u0002\u0010+J\u0006\u0010,\u001a\u00020\u001bJ\u0010\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u000200J\u0006\u00101\u001a\u00020.R$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\u0007\"\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0007\"\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0013\u0010\u0002\u001a\u0004\b\u0014\u0010\u0007\"\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0017\u0010\u0002\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\t¨\u00062"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/PlayerUtils;", "", "()V", "groundTicks", "", "getGroundTicks$annotations", "getGroundTicks", "()I", "setGroundTicks", "(I)V", "lastGroundTicks", "getLastGroundTicks$annotations", "getLastGroundTicks", "setLastGroundTicks", "lastOffGroundTicks", "getLastOffGroundTicks$annotations", "getLastOffGroundTicks", "setLastOffGroundTicks", "offGroundTicks", "getOffGroundTicks$annotations", "getOffGroundTicks", "setOffGroundTicks", "sinceTeleportTicks", "getSinceTeleportTicks$annotations", "getSinceTeleportTicks", "setSinceTeleportTicks", "BlockUnderPlayerIsEmpty", "", "NotNull", "blockNear", "range", "blockRelativeToPlayer", "Lnet/minecraft/block/Block;", "offsetX", "", "offsetY", "offsetZ", "findSlimeBlock", "()Ljava/lang/Integer;", "getEntity", "", "distance", "expand", "(DD)[Ljava/lang/Object;", "isOnEdge", "setCorrectBlockPos", "", "speed", "", "swing", "CrossSine"}
)
public final class PlayerUtils {
   @NotNull
   public static final PlayerUtils INSTANCE = new PlayerUtils();
   private static int offGroundTicks;
   private static int groundTicks;
   private static int lastOffGroundTicks;
   private static int lastGroundTicks;
   private static int sinceTeleportTicks;

   private PlayerUtils() {
   }

   public static final int getOffGroundTicks() {
      return offGroundTicks;
   }

   public static final void setOffGroundTicks(int var0) {
      offGroundTicks = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void getOffGroundTicks$annotations() {
   }

   public static final int getGroundTicks() {
      return groundTicks;
   }

   public static final void setGroundTicks(int var0) {
      groundTicks = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void getGroundTicks$annotations() {
   }

   public static final int getLastOffGroundTicks() {
      return lastOffGroundTicks;
   }

   public static final void setLastOffGroundTicks(int var0) {
      lastOffGroundTicks = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void getLastOffGroundTicks$annotations() {
   }

   public static final int getLastGroundTicks() {
      return lastGroundTicks;
   }

   public static final void setLastGroundTicks(int var0) {
      lastGroundTicks = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void getLastGroundTicks$annotations() {
   }

   public static final int getSinceTeleportTicks() {
      return sinceTeleportTicks;
   }

   public static final void setSinceTeleportTicks(int var0) {
      sinceTeleportTicks = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void getSinceTeleportTicks$annotations() {
   }

   @NotNull
   public final Block blockRelativeToPlayer(double offsetX, double offsetY, double offsetZ) {
      Block var10000 = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t + offsetX, MinecraftInstance.mc.field_71439_g.field_70163_u + offsetY, MinecraftInstance.mc.field_71439_g.field_70161_v + offsetZ));
      Intrinsics.checkNotNull(var10000);
      return var10000;
   }

   public final boolean blockNear(int range) {
      int var2 = -range;
      int x;
      if (var2 <= range) {
         do {
            x = var2++;
            int var4 = -range;
            int y;
            if (var4 <= range) {
               do {
                  y = var4++;
                  int var6 = -range;
                  int z;
                  if (var6 <= range) {
                     do {
                        z = var6++;
                        Block var9 = this.blockRelativeToPlayer((double)x, (double)y, (double)z);
                        BlockAir var10000 = var9 instanceof BlockAir ? (BlockAir)var9 : null;
                        if ((var9 instanceof BlockAir ? (BlockAir)var9 : null) == null) {
                           return true;
                        }

                        Block var8 = (Block)var10000;
                     } while(z != range);
                  }
               } while(y != range);
            }
         } while(x != range);
      }

      return false;
   }

   @Nullable
   public final Integer findSlimeBlock() {
      int var1 = 0;

      while(var1 < 9) {
         int i = var1++;
         ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (itemStack != null && itemStack.func_77973_b() != null && itemStack.func_77973_b() instanceof ItemBlock) {
            Item var10000 = itemStack.func_77973_b();
            if (var10000 == null) {
               throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
            }

            ItemBlock block = (ItemBlock)var10000;
            if (block.field_150939_a instanceof BlockSlime) {
               return i;
            }
         }
      }

      return -1;
   }

   public final void swing() {
      EntityPlayerSP var2 = MinecraftInstance.mc.field_71439_g;
      Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer");
      EntityPlayerSP player = var2;
      float swingEnd = (float)(var2.func_70644_a(Potion.field_76422_e) ? 6 - (1 + var2.func_70660_b(Potion.field_76422_e).func_76458_c()) : (var2.func_70644_a(Potion.field_76419_f) ? 6 + (1 + var2.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2 : 6)) * (Animations.INSTANCE.getState() ? ((Number)Animations.INSTANCE.getSwingSpeedValue().get()).floatValue() : 1.0F);
      if (!player.field_82175_bq || (float)player.field_110158_av >= swingEnd / (float)2 || player.field_110158_av < 0) {
         player.field_110158_av = -1;
         player.field_82175_bq = true;
      }

   }

   public final void setCorrectBlockPos(float speed) {
      double x = MinecraftInstance.mc.field_71439_g.field_70165_t;
      double z = MinecraftInstance.mc.field_71439_g.field_70161_v;
      double targetX = Math.floor(x) + (double)0.5F;
      double targetZ = Math.floor(z) + (double)0.5F;
      double deltaX = targetX - x;
      double deltaZ = targetZ - z;
      double threshold = 0.01;
      if (!(Math.abs(deltaX) > threshold) && !(Math.abs(deltaZ) > threshold)) {
         MinecraftInstance.mc.field_71439_g.field_70159_w = (double)0.0F;
         MinecraftInstance.mc.field_71439_g.field_70179_y = (double)0.0F;
      } else {
         MinecraftInstance.mc.field_71439_g.field_70159_w = deltaX * (double)speed;
         MinecraftInstance.mc.field_71439_g.field_70179_y = deltaZ * (double)speed;
      }

   }

   // $FF: synthetic method
   public static void setCorrectBlockPos$default(PlayerUtils var0, float var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 1.0F;
      }

      var0.setCorrectBlockPos(var1);
   }

   public final boolean isOnEdge() {
      return MinecraftInstance.mc.field_71439_g.field_70122_E && !MinecraftInstance.mc.field_71439_g.func_70093_af() && !MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d() && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)0.0F, (double)-0.5F, (double)0.0F).func_72314_b(-0.001, (double)0.0F, -0.001)).isEmpty();
   }

   public final boolean BlockUnderPlayerIsEmpty() {
      return MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w / (double)3.5F, (double)-1.0F, MinecraftInstance.mc.field_71439_g.field_70179_y / (double)3.5F)).isEmpty();
   }

   @Nullable
   public final Object[] getEntity(double distance, double expand) {
      Entity var2 = MinecraftInstance.mc.func_175606_aa();
      Entity entity = null;
      if (var2 != null && MinecraftInstance.mc.field_71441_e != null) {
         MinecraftInstance.mc.field_71424_I.func_76320_a("pick");
         Vec3 var12 = var2.func_174824_e(0.0F);
         Intrinsics.checkNotNullExpressionValue(var12, "var2.getPositionEyes(0.0f)");
         Vec3 var7 = var12;
         Vec3 var8 = var2.func_70676_i(0.0F);
         Intrinsics.checkNotNullExpressionValue(var8, "var2.getLook(0.0f)");
         Vec3 var10 = var12.func_72441_c(var8.field_72450_a * distance, var8.field_72448_b * distance, var8.field_72449_c * distance);
         Intrinsics.checkNotNullExpressionValue(var10, "var7.addVector(var8.xCoo…var3, var8.zCoord * var3)");
         Vec3 var9 = var10;
         var10 = null;
         float var11 = 1.0F;
         List var17 = MinecraftInstance.mc.field_71441_e.func_72839_b(var2, var2.func_174813_aQ().func_72321_a(var8.field_72450_a * distance, var8.field_72448_b * distance, var8.field_72449_c * distance).func_72314_b((double)var11, (double)var11, (double)var11));
         Intrinsics.checkNotNullExpressionValue(var17, "mc.theWorld.getEntitiesW…toDouble())\n            )");
         List var12 = var17;
         double var13 = distance;
         int var19 = 0;
         int var20 = var17.size();

         while(var19 < var20) {
            int var15 = var19++;
            Entity var16 = (Entity)var12.get(var15);
            if (var16.func_70067_L()) {
               float var17 = var16.func_70111_Y();
               AxisAlignedBB var18 = var16.func_174813_aQ().func_72314_b((double)var17, (double)var17, (double)var17);
               Intrinsics.checkNotNullExpressionValue(var18, "var16.entityBoundingBox.…uble(), var17.toDouble())");
               var18 = var18.func_72314_b(expand, expand, expand);
               Intrinsics.checkNotNullExpressionValue(var18, "var18.expand(expand, expand, expand)");
               MovingObjectPosition var19 = var18.func_72327_a(var7, var9);
               if (var18.func_72318_a(var7)) {
                  if ((double)0.0F < var13 || var13 == (double)0.0F) {
                     entity = var16;
                     Vec3 var10000;
                     if (var19 == null) {
                        var10000 = var7;
                     } else {
                        var10000 = var19.field_72307_f;
                        if (var10000 == null) {
                           var10000 = var7;
                        }
                     }

                     var10 = var10000;
                     var13 = (double)0.0F;
                  }
               } else if (var19 != null) {
                  double var20 = var7.func_72438_d(var19.field_72307_f);
                  if (var20 < var13 || var13 == (double)0.0F) {
                     if (var16 == var2.field_70154_o && !var2.canRiderInteract()) {
                        if (var13 == (double)0.0F) {
                           entity = var16;
                           var10 = var19.field_72307_f;
                        }
                     } else {
                        entity = var16;
                        var10 = var19.field_72307_f;
                        var13 = var20;
                     }
                  }
               }
            }
         }

         if (var13 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
         }

         MinecraftInstance.mc.field_71424_I.func_76319_b();
         if (entity != null && var10 != null) {
            Object[] var32 = new Object[]{entity, var10};
            return var32;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @JvmStatic
   public static final boolean NotNull() {
      return MinecraftInstance.mc.field_71439_g != null && MinecraftInstance.mc.field_71441_e != null;
   }
}
