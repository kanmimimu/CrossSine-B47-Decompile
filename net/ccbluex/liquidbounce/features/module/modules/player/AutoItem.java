package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AutoItem",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoItem;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackEnemy", "", "autoTool", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoWeapon", "bestSlot", "", "mining", "onlySwordValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "onlyTools", "prevItem", "prevItemWeapon", "sneakValue", "spoof", "spoofTick", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class AutoItem extends Module {
   @NotNull
   public static final AutoItem INSTANCE = new AutoItem();
   @NotNull
   private static final BoolValue autoTool = new BoolValue("AutoTool", true);
   @NotNull
   private static final Value sneakValue;
   @NotNull
   private static final Value onlyTools;
   @NotNull
   private static final BoolValue autoWeapon;
   @NotNull
   private static final Value onlySwordValue;
   @NotNull
   private static final BoolValue spoof;
   private static int prevItem;
   private static boolean mining;
   private static int bestSlot;
   private static boolean attackEnemy;
   private static int prevItemWeapon;
   private static int spoofTick;

   private AutoItem() {
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)autoTool.get()) {
         if (!MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x.field_72313_a == MovingObjectType.BLOCK && (!(Boolean)sneakValue.get() || MinecraftInstance.mc.field_71439_g.func_70093_af())) {
            int bestSpeed = 0;
            if (!mining) {
               prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
            }

            Block block = MinecraftInstance.mc.field_71441_e.func_180495_p(MinecraftInstance.mc.field_71476_x.func_178782_a()).func_177230_c();
            int var4 = 0;

            while(var4 < 9) {
               int i = var4++;
               ItemStack var10000 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
               if (var10000 != null) {
                  ItemStack item = var10000;
                  float speed = item.func_150997_a(block);
                  if (speed > (float)bestSpeed) {
                     bestSpeed = (int)speed;
                     bestSlot = i;
                  }

                  if (bestSlot != -1) {
                     Item item = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot).func_77973_b();
                     if (!(Boolean)onlyTools.get() || item instanceof ItemShears || item instanceof ItemTool) {
                        SlotUtils.INSTANCE.setSlot(bestSlot, (Boolean)spoof.get(), this.getName());
                     }
                  }
               }
            }

            mining = true;
         } else if (mining) {
            SlotUtils.INSTANCE.stopSet();
            mining = false;
         } else {
            prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
         }
      }

   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      attackEnemy = true;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)autoWeapon.get() && event.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.getPacket()).func_149565_c() == Action.ATTACK && attackEnemy) {
         attackEnemy = false;
         Iterable $this$map$iv = new IntRange(0, 8);
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;
         Iterator var9 = $this$map$iv.iterator();

         while(var9.hasNext()) {
            int item$iv$iv = ((IntIterator)var9).nextInt();
            int var13 = 0;
            destination$iv$iv.add(new Pair(item$iv$iv, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(item$iv$iv)));
         }

         $this$map$iv = (Iterable)((List)destination$iv$iv);
         $i$f$map = 0;
         destination$iv$iv = (Collection)(new ArrayList());
         $i$f$mapTo = 0;

         for(Object element$iv$iv : $this$map$iv) {
            Pair it = (Pair)element$iv$iv;
            int var39 = 0;
            if (it.getSecond() != null && (((ItemStack)it.getSecond()).func_77973_b() instanceof ItemSword || ((ItemStack)it.getSecond()).func_77973_b() instanceof ItemTool && !(Boolean)onlySwordValue.get())) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$map$iv = (Iterable)((List)destination$iv$iv);
         $i$f$map = 0;
         Iterator iterator$iv = $this$map$iv.iterator();
         Object var45;
         if (!iterator$iv.hasNext()) {
            var45 = null;
         } else {
            Object maxElem$iv = iterator$iv.next();
            if (!iterator$iv.hasNext()) {
               var45 = maxElem$iv;
            } else {
               Pair it = (Pair)maxElem$iv;
               int var28 = 0;
               Collection it = ((ItemStack)it.getSecond()).func_111283_C().get("generic.attackDamage");
               Intrinsics.checkNotNullExpressionValue(it, "it.second.attributeModif…s[\"generic.attackDamage\"]");
               AttributeModifier it = (AttributeModifier)CollectionsKt.first((Iterable)it);
               double var10000;
               if (it == null) {
                  var10000 = (double)0.0F;
               } else {
                  double v$iv = it.func_111164_d();
                  var10000 = v$iv;
               }

               ItemUtils var10002 = ItemUtils.INSTANCE;
               Object var36 = it.getSecond();
               Intrinsics.checkNotNullExpressionValue(var36, "it.second");
               ItemStack var10003 = (ItemStack)var36;
               Enchantment var37 = Enchantment.field_180314_l;
               Intrinsics.checkNotNullExpressionValue(var37, "sharpness");
               double maxValue$iv = var10000 + (double)1.25F * (double)var10002.getEnchantment(var10003, var37);

               do {
                  Object e$iv = iterator$iv.next();
                  Pair it = (Pair)e$iv;
                  int var38 = 0;
                  Collection var40 = ((ItemStack)it.getSecond()).func_111283_C().get("generic.attackDamage");
                  Intrinsics.checkNotNullExpressionValue(var40, "it.second.attributeModif…s[\"generic.attackDamage\"]");
                  AttributeModifier var15 = (AttributeModifier)CollectionsKt.first((Iterable)var40);
                  if (var15 == null) {
                     var10000 = (double)0.0F;
                  } else {
                     double var41 = var15.func_111164_d();
                     var10000 = var41;
                  }

                  var10002 = ItemUtils.INSTANCE;
                  Object var42 = it.getSecond();
                  Intrinsics.checkNotNullExpressionValue(var42, "it.second");
                  var10003 = (ItemStack)var42;
                  Enchantment var43 = Enchantment.field_180314_l;
                  Intrinsics.checkNotNullExpressionValue(var43, "sharpness");
                  double v$iv = var10000 + (double)1.25F * (double)var10002.getEnchantment(var10003, var43);
                  if (Double.compare(maxValue$iv, v$iv) < 0) {
                     maxElem$iv = e$iv;
                     maxValue$iv = v$iv;
                  }
               } while(iterator$iv.hasNext());

               var45 = maxElem$iv;
            }
         }

         Pair var2 = (Pair)var45;
         if (var2 == null) {
            return;
         }

         int slot = ((Number)var2.component1()).intValue();
         if (slot == MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c) {
            return;
         }

         if (!SlotUtils.INSTANCE.getChanged()) {
            prevItemWeapon = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
         }

         spoofTick = 15;
         SlotUtils.INSTANCE.setSlot(slot, (Boolean)spoof.get(), this.getName());
         MinecraftInstance.mc.field_71442_b.func_78765_e();
         MinecraftInstance.mc.func_147114_u().func_147297_a(event.getPacket());
         event.cancelEvent();
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)autoWeapon.get() && spoofTick > 0) {
         if (spoofTick == 1) {
            SlotUtils.INSTANCE.stopSet();
         }

         int var2 = spoofTick;
         spoofTick = var2 + -1;
      }

   }

   static {
      sneakValue = (new BoolValue("Sneak", false)).displayable(null.INSTANCE);
      onlyTools = (new BoolValue("Only-Tool", true)).displayable(null.INSTANCE);
      autoWeapon = new BoolValue("AutoWeapon", false);
      onlySwordValue = (new BoolValue("OnlySword", false)).displayable(null.INSTANCE);
      spoof = new BoolValue("Spoof-Item", true);
   }
}
