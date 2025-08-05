package net.ccbluex.liquidbounce.utils.item;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.utils.RegexUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002#$B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0016\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u001d\u001a\u00020\t2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0016H\u0007J\"\u0010\u001f\u001a\u00020 2\u0006\u0010\u001e\u001a\u00020\u00162\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010J\u0016\u0010!\u001a\u00020\"2\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006%"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemUtils;", "", "()V", "armorDamageReduceEnchantments", "", "Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$Enchant;", "[Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$Enchant;", "otherArmorEnchantments", "compareArmor", "", "o1", "Lnet/ccbluex/liquidbounce/utils/item/ArmorPiece;", "o2", "nbtedPriority", "", "goal", "Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "getArmorDamageReduction", "defensePoints", "toughness", "getArmorEnchantmentThreshold", "itemStack", "Lnet/minecraft/item/ItemStack;", "getArmorThresholdedDamageReduction", "getArmorThresholdedEnchantmentDamageReduction", "getEnchantment", "enchantment", "Lnet/minecraft/enchantment/Enchantment;", "getEnchantmentCount", "getItemDurability", "stack", "getWeaponEnchantFactor", "", "hasNBTGoal", "", "Enchant", "EnumNBTPriorityType", "CrossSine"}
)
public final class ItemUtils {
   @NotNull
   public static final ItemUtils INSTANCE = new ItemUtils();
   @NotNull
   private static final Enchant[] armorDamageReduceEnchantments;
   @NotNull
   private static final Enchant[] otherArmorEnchantments;

   private ItemUtils() {
   }

   public final int getEnchantment(@NotNull ItemStack itemStack, @NotNull Enchantment enchantment) {
      Intrinsics.checkNotNullParameter(itemStack, "itemStack");
      Intrinsics.checkNotNullParameter(enchantment, "enchantment");
      if (itemStack.func_77986_q() != null && !itemStack.func_77986_q().func_82582_d()) {
         int var3 = 0;
         int var4 = itemStack.func_77986_q().func_74745_c();

         while(var3 < var4) {
            int i = var3++;
            NBTTagCompound tagCompound = itemStack.func_77986_q().func_150305_b(i);
            if (tagCompound.func_74764_b("ench") && tagCompound.func_74765_d("ench") == enchantment.field_77352_x || tagCompound.func_74764_b("id") && tagCompound.func_74765_d("id") == enchantment.field_77352_x) {
               return tagCompound.func_74765_d("lvl");
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   private final int getEnchantmentCount(ItemStack itemStack) {
      if (itemStack.func_77986_q() != null && !itemStack.func_77986_q().func_82582_d()) {
         int c = 0;
         int var3 = 0;
         int var4 = itemStack.func_77986_q().func_74745_c();

         while(var3 < var4) {
            int i = var3++;
            NBTTagCompound tagCompound = itemStack.func_77986_q().func_150305_b(i);
            if (tagCompound.func_74764_b("ench") || tagCompound.func_74764_b("id")) {
               ++c;
            }
         }

         return c;
      } else {
         return 0;
      }
   }

   @JvmStatic
   public static final int getItemDurability(@Nullable ItemStack stack) {
      return stack == null ? 0 : stack.func_77958_k() - stack.func_77952_i();
   }

   public final double getWeaponEnchantFactor(@NotNull ItemStack stack, float nbtedPriority, @NotNull EnumNBTPriorityType goal) {
      Intrinsics.checkNotNullParameter(stack, "stack");
      Intrinsics.checkNotNullParameter(goal, "goal");
      Enchantment var4 = Enchantment.field_180314_l;
      Intrinsics.checkNotNullExpressionValue(var4, "sharpness");
      double var10000 = (double)1.25F * (double)this.getEnchantment(stack, var4);
      var4 = Enchantment.field_77334_n;
      Intrinsics.checkNotNullExpressionValue(var4, "fireAspect");
      return var10000 + (double)1.0F * (double)this.getEnchantment(stack, var4) + (double)(this.hasNBTGoal(stack, goal) ? nbtedPriority : 0.0F);
   }

   // $FF: synthetic method
   public static double getWeaponEnchantFactor$default(ItemUtils var0, ItemStack var1, float var2, EnumNBTPriorityType var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0.0F;
      }

      if ((var4 & 4) != 0) {
         var3 = ItemUtils.EnumNBTPriorityType.NONE;
      }

      return var0.getWeaponEnchantFactor(var1, var2, var3);
   }

   public final int compareArmor(@NotNull ArmorPiece o1, @NotNull ArmorPiece o2, float nbtedPriority, @NotNull EnumNBTPriorityType goal) {
      Intrinsics.checkNotNullParameter(o1, "o1");
      Intrinsics.checkNotNullParameter(o2, "o2");
      Intrinsics.checkNotNullParameter(goal, "goal");
      int compare = Double.compare(RegexUtils.INSTANCE.round((double)this.getArmorThresholdedDamageReduction(o2.getItemStack()) - (double)(this.hasNBTGoal(o2.getItemStack(), goal) ? nbtedPriority / 5.0F : 0.0F), 3), RegexUtils.INSTANCE.round((double)this.getArmorThresholdedDamageReduction(o1.getItemStack()) - (double)(this.hasNBTGoal(o1.getItemStack(), goal) ? nbtedPriority / 5.0F : 0.0F), 3));
      if (compare == 0) {
         int otherEnchantmentCmp = Double.compare(RegexUtils.INSTANCE.round((double)this.getArmorEnchantmentThreshold(o1.getItemStack()), 3), RegexUtils.INSTANCE.round((double)this.getArmorEnchantmentThreshold(o2.getItemStack()), 3));
         if (otherEnchantmentCmp == 0) {
            int enchantmentCountCmp = Intrinsics.compare(this.getEnchantmentCount(o1.getItemStack()), this.getEnchantmentCount(o2.getItemStack()));
            if (enchantmentCountCmp != 0) {
               return enchantmentCountCmp;
            } else {
               Item var10000 = o1.getItemStack().func_77973_b();
               if (var10000 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
               } else {
                  ItemArmor o1a = (ItemArmor)var10000;
                  var10000 = o2.getItemStack().func_77973_b();
                  if (var10000 == null) {
                     throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
                  } else {
                     ItemArmor o2a = (ItemArmor)var10000;
                     int durabilityCmp = Intrinsics.compare(o1a.func_82812_d().func_78046_a(o1a.field_77881_a), o2a.func_82812_d().func_78046_a(o2a.field_77881_a));
                     return durabilityCmp != 0 ? durabilityCmp : Intrinsics.compare(o1a.func_82812_d().func_78045_a(), o2a.func_82812_d().func_78045_a());
                  }
               }
            }
         } else {
            return otherEnchantmentCmp;
         }
      } else {
         return compare;
      }
   }

   // $FF: synthetic method
   public static int compareArmor$default(ItemUtils var0, ArmorPiece var1, ArmorPiece var2, float var3, EnumNBTPriorityType var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0.0F;
      }

      if ((var5 & 8) != 0) {
         var4 = ItemUtils.EnumNBTPriorityType.NONE;
      }

      return var0.compareArmor(var1, var2, var3, var4);
   }

   private final float getArmorThresholdedDamageReduction(ItemStack itemStack) {
      Item var10000 = itemStack.func_77973_b();
      if (var10000 == null) {
         throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
      } else {
         ItemArmor item = (ItemArmor)var10000;
         return this.getArmorDamageReduction(item.func_82812_d().func_78044_b(item.field_77881_a), 0) * ((float)1 - this.getArmorThresholdedEnchantmentDamageReduction(itemStack));
      }
   }

   private final float getArmorDamageReduction(int defensePoints, int toughness) {
      return (float)1 - RangesKt.coerceAtMost(20.0F, RangesKt.coerceAtLeast((float)defensePoints / 5.0F, (float)defensePoints - (float)1 / ((float)2 + (float)toughness / 4.0F))) / 25.0F;
   }

   private final float getArmorThresholdedEnchantmentDamageReduction(ItemStack itemStack) {
      float sum = 0.0F;
      int var3 = 0;

      int i;
      for(int var4 = armorDamageReduceEnchantments.length; var3 < var4; sum += (float)this.getEnchantment(itemStack, armorDamageReduceEnchantments[i].getEnchantment()) * armorDamageReduceEnchantments[i].getFactor()) {
         i = var3++;
      }

      return sum;
   }

   private final float getArmorEnchantmentThreshold(ItemStack itemStack) {
      float sum = 0.0F;
      int var3 = 0;

      int i;
      for(int var4 = otherArmorEnchantments.length; var3 < var4; sum += (float)this.getEnchantment(itemStack, otherArmorEnchantments[i].getEnchantment()) * otherArmorEnchantments[i].getFactor()) {
         i = var3++;
      }

      return sum;
   }

   public final boolean hasNBTGoal(@NotNull ItemStack stack, @NotNull EnumNBTPriorityType goal) {
      Intrinsics.checkNotNullParameter(stack, "stack");
      Intrinsics.checkNotNullParameter(goal, "goal");
      if (stack.func_77942_o() && stack.func_77978_p().func_150297_b("display", 10)) {
         NBTTagCompound display = stack.func_77978_p().func_74775_l("display");
         if (goal == ItemUtils.EnumNBTPriorityType.HAS_DISPLAY_TAG) {
            return true;
         }

         if (goal == ItemUtils.EnumNBTPriorityType.HAS_NAME) {
            return display.func_74764_b("Name");
         }

         if (goal == ItemUtils.EnumNBTPriorityType.HAS_LORE) {
            return display.func_74764_b("Lore") && display.func_150295_c("Lore", 8).func_74745_c() > 0;
         }
      }

      return false;
   }

   static {
      Enchant[] var0 = new Enchant[4];
      Enchantment var1 = Enchantment.field_180310_c;
      Intrinsics.checkNotNullExpressionValue(var1, "protection");
      var0[0] = new Enchant(var1, 0.06F);
      var1 = Enchantment.field_180308_g;
      Intrinsics.checkNotNullExpressionValue(var1, "projectileProtection");
      var0[1] = new Enchant(var1, 0.032F);
      var1 = Enchantment.field_77329_d;
      Intrinsics.checkNotNullExpressionValue(var1, "fireProtection");
      var0[2] = new Enchant(var1, 0.0585F);
      var1 = Enchantment.field_77327_f;
      Intrinsics.checkNotNullExpressionValue(var1, "blastProtection");
      var0[3] = new Enchant(var1, 0.0304F);
      armorDamageReduceEnchantments = var0;
      var0 = new Enchant[5];
      var1 = Enchantment.field_180309_e;
      Intrinsics.checkNotNullExpressionValue(var1, "featherFalling");
      var0[0] = new Enchant(var1, 3.0F);
      var1 = Enchantment.field_92091_k;
      Intrinsics.checkNotNullExpressionValue(var1, "thorns");
      var0[1] = new Enchant(var1, 1.0F);
      var1 = Enchantment.field_180317_h;
      Intrinsics.checkNotNullExpressionValue(var1, "respiration");
      var0[2] = new Enchant(var1, 0.1F);
      var1 = Enchantment.field_77341_i;
      Intrinsics.checkNotNullExpressionValue(var1, "aquaAffinity");
      var0[3] = new Enchant(var1, 0.05F);
      var1 = Enchantment.field_77347_r;
      Intrinsics.checkNotNullExpressionValue(var1, "unbreaking");
      var0[4] = new Enchant(var1, 0.01F);
      otherArmorEnchantments = var0;
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$Enchant;", "", "enchantment", "Lnet/minecraft/enchantment/Enchantment;", "factor", "", "(Lnet/minecraft/enchantment/Enchantment;F)V", "getEnchantment", "()Lnet/minecraft/enchantment/Enchantment;", "getFactor", "()F", "CrossSine"}
   )
   public static final class Enchant {
      @NotNull
      private final Enchantment enchantment;
      private final float factor;

      public Enchant(@NotNull Enchantment enchantment, float factor) {
         Intrinsics.checkNotNullParameter(enchantment, "enchantment");
         super();
         this.enchantment = enchantment;
         this.factor = factor;
      }

      @NotNull
      public final Enchantment getEnchantment() {
         return this.enchantment;
      }

      public final float getFactor() {
         return this.factor;
      }
   }

   @Metadata(
      mv = {1, 6, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "", "(Ljava/lang/String;I)V", "HAS_NAME", "HAS_LORE", "HAS_DISPLAY_TAG", "NONE", "CrossSine"}
   )
   public static enum EnumNBTPriorityType {
      HAS_NAME,
      HAS_LORE,
      HAS_DISPLAY_TAG,
      NONE;

      // $FF: synthetic method
      private static final EnumNBTPriorityType[] $values() {
         EnumNBTPriorityType[] var0 = new EnumNBTPriorityType[]{HAS_NAME, HAS_LORE, HAS_DISPLAY_TAG, NONE};
         return var0;
      }
   }
}
