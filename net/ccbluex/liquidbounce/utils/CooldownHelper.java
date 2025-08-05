package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0004J\u0006\u0010\b\u001a\u00020\u0004J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u0010\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/CooldownHelper;", "", "()V", "genericAttackSpeed", "", "lastAttackedTicks", "", "getAttackCooldownProgress", "getAttackCooldownProgressPerTick", "incrementLastAttackedTicks", "", "resetLastAttackedTicks", "updateGenericAttackSpeed", "itemStack", "Lnet/minecraft/item/ItemStack;", "CrossSine"}
)
public final class CooldownHelper {
   @NotNull
   public static final CooldownHelper INSTANCE = new CooldownHelper();
   private static int lastAttackedTicks;
   private static double genericAttackSpeed;

   private CooldownHelper() {
   }

   public final void updateGenericAttackSpeed(@Nullable ItemStack itemStack) {
      Item var2 = itemStack == null ? null : itemStack.func_77973_b();
      double var10000;
      if (var2 instanceof ItemSword) {
         var10000 = 1.6;
      } else if (var2 instanceof ItemAxe) {
         Item var6 = itemStack.func_77973_b();
         if (var6 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemAxe");
         }

         ItemAxe axe = (ItemAxe)var6;
         Item.ToolMaterial var7 = axe.func_150913_i();
         switch (var7 == null ? -1 : CooldownHelper.WhenMappings.$EnumSwitchMapping$0[var7.ordinal()]) {
            case 1:
               var10000 = 0.9;
               break;
            case 2:
            case 3:
               var10000 = 0.8;
               break;
            default:
               var10000 = (double)1.0F;
         }
      } else if (var2 instanceof ItemPickaxe) {
         var10000 = 1.2;
      } else if (var2 instanceof ItemSpade) {
         var10000 = (double)1.0F;
      } else if (var2 instanceof ItemHoe) {
         label74: {
            Item var8 = itemStack.func_77973_b();
            if (var8 == null) {
               throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemHoe");
            }

            ItemHoe hoe = (ItemHoe)var8;
            if (var4 != null) {
               switch (var4) {
                  case "DIAMOND":
                     var10000 = (double)4.0F;
                     break label74;
                  case "IRON":
                     var10000 = (double)3.0F;
                     break label74;
                  case "STONE":
                     var10000 = (double)2.0F;
                     break label74;
               }
            }

            var10000 = (double)1.0F;
         }
      } else {
         var10000 = (double)4.0F;
      }

      genericAttackSpeed = var10000;
      if (MinecraftInstanceKt.getMc().field_71439_g.func_70644_a(Potion.field_76419_f)) {
         genericAttackSpeed *= (double)1.0F - Math.min((double)1.0F, 0.1 * (double)MinecraftInstanceKt.getMc().field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c() + (double)1);
      }

      if (MinecraftInstanceKt.getMc().field_71439_g.func_70644_a(Potion.field_76422_e)) {
         genericAttackSpeed *= (double)1.0F + 0.1 * (double)MinecraftInstanceKt.getMc().field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c() + (double)1;
      }

   }

   public final double getAttackCooldownProgressPerTick() {
      return (double)1.0F / genericAttackSpeed * (double)20.0F;
   }

   public final double getAttackCooldownProgress() {
      return MathHelper.func_151237_a((double)lastAttackedTicks / this.getAttackCooldownProgressPerTick(), (double)0.0F, (double)1.0F);
   }

   public final void resetLastAttackedTicks() {
      lastAttackedTicks = 0;
   }

   public final void incrementLastAttackedTicks() {
      int var1 = lastAttackedTicks++;
   }

   // $FF: synthetic class
   @Metadata(
      mv = {1, 6, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[ToolMaterial.values().length];
         var0[ToolMaterial.IRON.ordinal()] = 1;
         var0[ToolMaterial.WOOD.ordinal()] = 2;
         var0[ToolMaterial.STONE.ordinal()] = 3;
         $EnumSwitchMapping$0 = var0;
      }
   }
}
