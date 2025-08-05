package net.ccbluex.liquidbounce.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005J\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fJ\u001e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u0016\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0006\u0010\u001b\u001a\u00020\u0014J\u0006\u0010\u001c\u001a\u00020\u0014J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020 J\b\u0010$\u001a\u00020\u000fH\u0016J\u0006\u0010%\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020(J\u0016\u0010)\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020*2\u0006\u0010#\u001a\u00020 J\u000e\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020\u0014J\u0010\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020/H\u0007J\u0006\u00100\u001a\u00020\u0012R\u001f\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/InventoryUtils;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BLOCK_BLACKLIST", "", "Lnet/minecraft/block/Block;", "kotlin.jvm.PlatformType", "getBLOCK_BLACKLIST", "()Ljava/util/List;", "CLICK_TIMER", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "INV_TIMER", "getINV_TIMER", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "canPlaceBlock", "", "block", "closePacket", "", "findAutoBlockBlock", "", "biggest", "findItem", "startSlot", "endSlot", "item", "Lnet/minecraft/item/Item;", "findSword", "getBestSwapSlot", "getDamageLevel", "", "itemStack", "Lnet/minecraft/item/ItemStack;", "getItemDurability", "", "stack", "handleEvents", "hasSpaceHotbar", "isBlockListBlock", "itemBlock", "Lnet/minecraft/item/ItemBlock;", "isPositivePotion", "Lnet/minecraft/item/ItemPotion;", "isPositivePotionEffect", "id", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "openPacket", "CrossSine"}
)
public final class InventoryUtils implements Listenable {
   @NotNull
   public static final InventoryUtils INSTANCE = new InventoryUtils();
   @JvmField
   @NotNull
   public static final MSTimer CLICK_TIMER = new MSTimer();
   @NotNull
   private static final MSTimer INV_TIMER = new MSTimer();
   @NotNull
   private static final List BLOCK_BLACKLIST;

   private InventoryUtils() {
   }

   @NotNull
   public final MSTimer getINV_TIMER() {
      return INV_TIMER;
   }

   @NotNull
   public final List getBLOCK_BLACKLIST() {
      return BLOCK_BLACKLIST;
   }

   public final int findItem(int startSlot, int endSlot, @NotNull Item item) {
      Intrinsics.checkNotNullParameter(item, "item");
      int var4 = startSlot;

      while(var4 < endSlot) {
         int i = var4++;
         ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
         if (stack != null && stack.func_77973_b() == item) {
            return i;
         }
      }

      return -1;
   }

   public final int findItem(@Nullable Item item) {
      int var2 = 0;

      while(var2 < 9) {
         int i = var2++;
         ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (itemStack == null) {
            if (item == null) {
               return i;
            }
         } else if (itemStack.func_77973_b() == item) {
            return i;
         }
      }

      return -1;
   }

   public final int findSword() {
      int bestDurability = -1;
      float bestDamage = -1.0F;
      int bestSlot = -1;
      int var4 = 0;

      while(var4 < 9) {
         int i = var4++;
         ItemStack var10000 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
         if (var10000 != null) {
            ItemStack itemStack = var10000;
            if (itemStack.func_77973_b() instanceof ItemSword) {
               Item var11 = itemStack.func_77973_b();
               if (var11 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemSword");
               }

               ItemSword sword = (ItemSword)var11;
               int sharpnessLevel = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, itemStack);
               float damage = sword.func_150931_i() + (float)sharpnessLevel * 1.25F;
               int durability = sword.func_77612_l();
               if (bestDamage < damage) {
                  bestDamage = damage;
                  bestDurability = durability;
                  bestSlot = i;
               }

               if (damage == bestDamage && durability > bestDurability) {
                  bestDurability = durability;
                  bestSlot = i;
               }
            }
         }
      }

      return bestSlot;
   }

   public final boolean hasSpaceHotbar() {
      int var1 = 36;

      while(var1 < 45) {
         int i = var1++;
         if (MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c() == null) {
            return true;
         }
      }

      return false;
   }

   public final int getBestSwapSlot() {
      int currentSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
      int bestSlot = -1;
      double bestDamage = (double)-1.0F;
      int var5 = 0;

      while(var5 < 9) {
         int i = var5++;
         if (i != currentSlot) {
            ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
            double damage = this.getDamageLevel(stack);
            if (damage != (double)0.0F && damage > bestDamage) {
               bestDamage = damage;
               bestSlot = i;
            }
         }
      }

      if (bestSlot == -1) {
         var5 = 0;

         int i;
         while(true) {
            if (var5 >= 9) {
               return bestSlot;
            }

            i = var5++;
            if (i != currentSlot) {
               ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
               if (stack == null) {
                  break;
               }

               String[] var13 = new String[]{"compass", "snowball", "spawn", "skull"};
               if (Arrays.stream(var13).noneMatch(InventoryUtils::getBestSwapSlot$lambda-0)) {
                  break;
               }
            }
         }

         bestSlot = i;
      }

      return bestSlot;
   }

   public final double getDamageLevel(@Nullable ItemStack itemStack) {
      double baseDamage = (double)0.0F;
      if (itemStack != null) {
         for(Map.Entry var5 : itemStack.func_111283_C().entries()) {
            Intrinsics.checkNotNullExpressionValue(var5, "itemStack.attributeModifiers.entries()");
            String key = (String)var5.getKey();
            AttributeModifier value = (AttributeModifier)var5.getValue();
            if (Intrinsics.areEqual((Object)key, (Object)"generic.attackDamage")) {
               baseDamage = value.func_111164_d();
               break;
            }
         }
      }

      int sharp_level = EnchantmentHelper.func_77506_a(Enchantment.field_180314_l.field_77352_x, itemStack);
      int fire_level = EnchantmentHelper.func_77506_a(Enchantment.field_77334_n.field_77352_x, itemStack);
      return baseDamage + (double)sharp_level * (double)1.25F + (double)(fire_level * 4 - 1);
   }

   public final int findAutoBlockBlock(boolean biggest) {
      if (biggest) {
         int a = -1;
         int aa = 0;
         int var10 = 36;

         while(var10 < 45) {
            int i = var10++;
            if (MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
               Item aaa = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b();
               ItemStack aaaa = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
               if (aaa instanceof ItemBlock && aaaa.field_77994_a > aa) {
                  aa = aaaa.field_77994_a;
                  a = i;
               }
            }
         }

         return a;
      } else {
         int a = 36;

         while(a < 45) {
            int i = a++;
            ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (itemStack != null && itemStack.func_77973_b() instanceof ItemBlock) {
               Item var10000 = itemStack.func_77973_b();
               if (var10000 == null) {
                  throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
               }

               ItemBlock itemBlock = (ItemBlock)var10000;
               Block block = itemBlock.func_179223_d();
               Intrinsics.checkNotNullExpressionValue(block, "block");
               if (this.canPlaceBlock(block)) {
                  EntityPlayerSP aaaa = MinecraftInstance.mc.field_71439_g;
                  Intrinsics.checkNotNullExpressionValue(aaaa, "mc.thePlayer");
                  if (EntityExtensionKt.getPing((EntityLivingBase)aaaa) > 100 && itemStack.field_77994_a > 2 || itemStack.field_77994_a != 0) {
                     return i;
                  }
               }
            }
         }

         return -1;
      }
   }

   public final boolean canPlaceBlock(@NotNull Block block) {
      Intrinsics.checkNotNullParameter(block, "block");
      return block.func_149686_d() && !BLOCK_BLACKLIST.contains(block);
   }

   public final boolean isBlockListBlock(@NotNull ItemBlock itemBlock) {
      Intrinsics.checkNotNullParameter(itemBlock, "itemBlock");
      Block block = itemBlock.func_179223_d();
      return BLOCK_BLACKLIST.contains(block) || !block.func_149686_d();
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof C0EPacketClickWindow || packet instanceof C08PacketPlayerBlockPlacement) {
         INV_TIMER.reset();
      }

      if (packet instanceof C08PacketPlayerBlockPlacement) {
         CLICK_TIMER.reset();
      } else if (packet instanceof C0EPacketClickWindow) {
         CLICK_TIMER.reset();
      }

   }

   public final void openPacket() {
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
   }

   public final void closePacket() {
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0DPacketCloseWindow()));
   }

   public final boolean isPositivePotionEffect(int id) {
      return id == Potion.field_76428_l.field_76415_H || id == Potion.field_76424_c.field_76415_H || id == Potion.field_76432_h.field_76415_H || id == Potion.field_76439_r.field_76415_H || id == Potion.field_76430_j.field_76415_H || id == Potion.field_76441_p.field_76415_H || id == Potion.field_76429_m.field_76415_H || id == Potion.field_76427_o.field_76415_H || id == Potion.field_76444_x.field_76415_H || id == Potion.field_76422_e.field_76415_H || id == Potion.field_76420_g.field_76415_H || id == Potion.field_180152_w.field_76415_H || id == Potion.field_76426_n.field_76415_H;
   }

   public final boolean isPositivePotion(@NotNull ItemPotion item, @NotNull ItemStack stack) {
      Intrinsics.checkNotNullParameter(item, "item");
      Intrinsics.checkNotNullParameter(stack, "stack");
      List var3 = item.func_77832_l(stack);
      Intrinsics.checkNotNullExpressionValue(var3, "item.getEffects(stack)");
      Iterable $this$forEach$iv = (Iterable)var3;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         PotionEffect it = (PotionEffect)element$iv;
         int var8 = 0;
         if (INSTANCE.isPositivePotionEffect(it.func_76456_a())) {
            return true;
         }
      }

      return false;
   }

   public final float getItemDurability(@NotNull ItemStack stack) {
      Intrinsics.checkNotNullParameter(stack, "stack");
      return stack.func_77984_f() && stack.func_77958_k() > 0 ? (float)(stack.func_77958_k() - stack.func_77952_i()) / (float)stack.func_77958_k() : 1.0F;
   }

   public boolean handleEvents() {
      return true;
   }

   private static final boolean getBestSwapSlot$lambda_0/* $FF was: getBestSwapSlot$lambda-0*/(ItemStack $stack, CharSequence s) {
      String var2 = $stack.func_77977_a();
      Intrinsics.checkNotNullExpressionValue(var2, "stack.unlocalizedName");
      Locale var3 = Locale.getDefault();
      Intrinsics.checkNotNullExpressionValue(var3, "getDefault()");
      String var4 = var2.toLowerCase(var3);
      Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(locale)");
      CharSequence var10000 = (CharSequence)var4;
      Intrinsics.checkNotNull(s);
      return StringsKt.contains$default(var10000, s, false, 2, (Object)null);
   }

   static {
      Block[] var0 = new Block[]{Blocks.field_150381_bn, (Block)Blocks.field_150486_ae, Blocks.field_150477_bB, Blocks.field_150447_bR, Blocks.field_150467_bQ, (Block)Blocks.field_150354_m, Blocks.field_150321_G, Blocks.field_150478_aa, Blocks.field_150462_ai, Blocks.field_150460_al, Blocks.field_150392_bi, Blocks.field_150367_z, Blocks.field_150456_au, Blocks.field_150452_aw, (Block)Blocks.field_150328_O, Blocks.field_150457_bL, (Block)Blocks.field_150327_N, Blocks.field_150323_B, Blocks.field_150409_cd, Blocks.field_180393_cK, Blocks.field_180394_cL, Blocks.field_150335_W};
      BLOCK_BLACKLIST = CollectionsKt.listOf(var0);
   }
}
