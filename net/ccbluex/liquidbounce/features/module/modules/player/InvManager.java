package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.injection.access.IItemStack;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "InvManager",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010@\u001a\u00020\u0016H\u0002J\u0015\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010B0\u001dH\u0002¢\u0006\u0002\u0010CJ!\u0010D\u001a\u0004\u0018\u00010\u00062\u0006\u0010E\u001a\u00020\u00062\b\u0010F\u001a\u0004\u0018\u00010GH\u0002¢\u0006\u0002\u0010HJ\u0016\u0010I\u001a\u00020\u00162\u0006\u0010J\u001a\u00020G2\u0006\u0010K\u001a\u00020\u0006J(\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020G0L2\b\b\u0002\u0010M\u001a\u00020\u00062\b\b\u0002\u0010N\u001a\u00020\u0006H\u0002J\u0018\u0010O\u001a\u00020\u00162\u0006\u0010P\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020\u0016H\u0002J\b\u0010R\u001a\u00020SH\u0016J\u0010\u0010T\u001a\u00020S2\u0006\u0010U\u001a\u00020VH\u0007J\u0010\u0010W\u001a\u00020\u001e2\u0006\u0010E\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010 \u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00160#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020$0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u00060#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00105\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00109\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006X"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/InvManager;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "currentSlot", "", "getCurrentSlot", "()I", "setCurrentSlot", "(I)V", "delay", "", "goal", "Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "getGoal", "()Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "hotbarValue", "ignoreVehiclesValue", "instantValue", "invOpenValue", "value", "", "invOpened", "setInvOpened", "(Z)V", "itemDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "items", "", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "nbtArmorPriority", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "nbtGoalValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "nbtItemNotGarbage", "nbtWeaponPriority", "noCombatValue", "noMoveValue", "onlyPositivePotionValue", "openInventory", "getOpenInventory", "()Z", "randomSlotValue", "simDelayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "simulateDelayValue", "simulateInventory", "sortSlot1Value", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "swingValue", "throwValue", "checkOpen", "findBestArmor", "Lnet/ccbluex/liquidbounce/utils/item/ArmorPiece;", "()[Lnet/ccbluex/liquidbounce/utils/item/ArmorPiece;", "findBetterItem", "targetSlot", "slotStack", "Lnet/minecraft/item/ItemStack;", "(ILnet/minecraft/item/ItemStack;)Ljava/lang/Integer;", "isUseful", "itemStack", "slot", "", "start", "end", "move", "item", "isArmorSlot", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "type", "CrossSine"}
)
public final class InvManager extends Module {
   @NotNull
   public static final InvManager INSTANCE = new InvManager();
   @NotNull
   private static final BoolValue instantValue = new BoolValue("Instant", false);
   @NotNull
   private static final IntegerValue maxDelayValue;
   @NotNull
   private static final IntegerValue minDelayValue;
   @NotNull
   private static final BoolValue invOpenValue;
   @NotNull
   private static final BoolValue simulateInventory;
   @NotNull
   private static final Value simulateDelayValue;
   @NotNull
   private static final BoolValue noMoveValue;
   @NotNull
   private static final BoolValue hotbarValue;
   @NotNull
   private static final BoolValue randomSlotValue;
   @NotNull
   private static final BoolValue sortValue;
   @NotNull
   private static final BoolValue throwValue;
   @NotNull
   private static final BoolValue armorValue;
   @NotNull
   private static final BoolValue noCombatValue;
   @NotNull
   private static final IntegerValue itemDelayValue;
   @NotNull
   private static final BoolValue swingValue;
   @NotNull
   private static final ListValue nbtGoalValue;
   @NotNull
   private static final Value nbtItemNotGarbage;
   @NotNull
   private static final Value nbtArmorPriority;
   @NotNull
   private static final Value nbtWeaponPriority;
   @NotNull
   private static final BoolValue ignoreVehiclesValue;
   @NotNull
   private static final BoolValue onlyPositivePotionValue;
   @NotNull
   private static final String[] items;
   @NotNull
   private static final Value sortSlot1Value;
   @NotNull
   private static final Value sortSlot2Value;
   @NotNull
   private static final Value sortSlot3Value;
   @NotNull
   private static final Value sortSlot4Value;
   @NotNull
   private static final Value sortSlot5Value;
   @NotNull
   private static final Value sortSlot6Value;
   @NotNull
   private static final Value sortSlot7Value;
   @NotNull
   private static final Value sortSlot8Value;
   @NotNull
   private static final Value sortSlot9Value;
   private static boolean invOpened;
   private static long delay;
   @NotNull
   private static final MSTimer simDelayTimer;
   private static int currentSlot;

   private InvManager() {
   }

   private final boolean getOpenInventory() {
      return !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && (Boolean)simulateInventory.get();
   }

   private final void setInvOpened(boolean value) {
      if (value != invOpened) {
         if (value) {
            InventoryUtils.INSTANCE.openPacket();
         } else {
            InventoryUtils.INSTANCE.closePacket();
         }
      }

      invOpened = value;
   }

   private final ItemUtils.EnumNBTPriorityType getGoal() {
      return ItemUtils.EnumNBTPriorityType.valueOf((String)nbtGoalValue.get());
   }

   public final int getCurrentSlot() {
      return currentSlot;
   }

   public final void setCurrentSlot(int var1) {
      currentSlot = var1;
   }

   public void onDisable() {
      this.setInvOpened(false);
   }

   private final boolean checkOpen() {
      if (!invOpened && this.getOpenInventory()) {
         this.setInvOpened(true);
         simDelayTimer.reset();
         return true;
      } else {
         return !simDelayTimer.hasTimePassed((long)((Number)simulateDelayValue.get()).intValue());
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((!(Boolean)noMoveValue.get() || !MovementUtils.INSTANCE.isMoving()) && (MinecraftInstance.mc.field_71439_g.field_71070_bA == null || MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c == 0) && (!CrossSine.INSTANCE.getCombatManager().getInCombat() || !(Boolean)noCombatValue.get())) {
         if ((InventoryUtils.CLICK_TIMER.hasTimePassed(delay) || (Boolean)instantValue.get()) && (MinecraftInstance.mc.field_71462_r instanceof GuiInventory || !(Boolean)invOpenValue.get())) {
            if ((Boolean)sortValue.get()) {
               int var2 = 0;

               while(var2 < 9) {
                  int index = var2++;
                  Integer var10000 = this.findBetterItem(index, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(index));
                  if (var10000 != null) {
                     int bestItem = var10000;
                     if (bestItem != index) {
                        if (this.checkOpen()) {
                           return;
                        }

                        MinecraftInstance.mc.field_71442_b.func_78753_a(0, bestItem < 9 ? bestItem + 36 : bestItem, index, 2, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                        currentSlot = bestItem < 9 ? bestItem + 36 : bestItem;
                        if (!(Boolean)instantValue.get()) {
                           delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
                           return;
                        }
                     }
                  }
               }
            }

            if ((Boolean)armorValue.get()) {
               ArmorPiece[] bestArmor = this.findBestArmor();
               int $this$filter$iv = 0;

               while($this$filter$iv < 4) {
                  int i = $this$filter$iv++;
                  ArmorPiece var23 = bestArmor[i];
                  if (bestArmor[i] != null) {
                     ArmorPiece armorPiece = var23;
                     int armorSlot = 3 - i;
                     ItemStack oldArmor = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70440_f(armorSlot);
                     if ((Boolean)instantValue.get()) {
                        if (oldArmor == null || !(oldArmor.func_77973_b() instanceof ItemArmor) || ItemUtils.INSTANCE.compareArmor(new ArmorPiece(oldArmor, -1), armorPiece, ((Number)nbtArmorPriority.get()).floatValue(), this.getGoal()) < 0) {
                           if (oldArmor != null) {
                              this.move(8 - armorSlot, true);
                           }

                           if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70440_f(armorSlot) == null) {
                              this.move(armorPiece.getSlot(), false);
                              currentSlot = armorPiece.getSlot();
                           }
                        }
                     } else if (oldArmor == null || !(oldArmor.func_77973_b() instanceof ItemArmor) || ItemUtils.INSTANCE.compareArmor(new ArmorPiece(oldArmor, -1), armorPiece, ((Number)nbtArmorPriority.get()).floatValue(), this.getGoal()) < 0) {
                        if (oldArmor != null && this.move(8 - armorSlot, true)) {
                           return;
                        }

                        if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70440_f(armorSlot) == null && this.move(armorPiece.getSlot(), false)) {
                           currentSlot = armorPiece.getSlot();
                           return;
                        }
                     }
                  }
               }
            }

            if ((Boolean)throwValue.get()) {
               Map var15 = this.items(5, (Boolean)hotbarValue.get() ? 45 : 36);
               int $i$f$filter = 0;
               Map destination$iv$iv = (Map)(new LinkedHashMap());
               int $i$f$filterTo = 0;

               for(Map.Entry element$iv$iv : var15.entrySet()) {
                  int var11 = 0;
                  if (!INSTANCE.isUseful((ItemStack)element$iv$iv.getValue(), ((Number)element$iv$iv.getKey()).intValue())) {
                     destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                  }
               }

               Set garbageItems = destination$iv$iv.keySet();
               if ((Boolean)instantValue.get()) {
                  if (!((Collection)garbageItems).isEmpty()) {
                     if (this.checkOpen()) {
                        return;
                     }

                     for(Iterator var16 = garbageItems.iterator(); var16.hasNext(); MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c, $i$f$filter, 1, 4, (EntityPlayer)MinecraftInstance.mc.field_71439_g)) {
                        $i$f$filter = ((Number)var16.next()).intValue();
                        if ((Boolean)swingValue.get()) {
                           MinecraftInstance.mc.field_71439_g.func_71038_i();
                        }
                     }
                  }

                  currentSlot = ((Number)CollectionsKt.first((Iterable)garbageItems)).intValue();
               } else {
                  Integer garbageItem = !((Collection)garbageItems).isEmpty() ? (Boolean)randomSlotValue.get() ? ((Number)CollectionsKt.toList((Iterable)garbageItems).get(RandomUtils.nextInt(0, garbageItems.size()))).intValue() : ((Number)CollectionsKt.first((Iterable)garbageItems)).intValue() : (Integer)null;
                  if (garbageItem != null) {
                     if (this.checkOpen()) {
                        return;
                     }

                     if ((Boolean)swingValue.get()) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                     }

                     MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c, garbageItem, 1, 4, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                     currentSlot = garbageItem;
                     if (!(Boolean)instantValue.get()) {
                        delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
                        return;
                     }
                  }
               }
            }

            if (InventoryUtils.CLICK_TIMER.hasTimePassed((long)((Number)simulateDelayValue.get()).intValue())) {
               this.setInvOpened(false);
            }

         }
      } else {
         if (InventoryUtils.CLICK_TIMER.hasTimePassed((long)((Number)simulateDelayValue.get()).intValue())) {
            this.setInvOpened(false);
         }

      }
   }

   public final boolean isUseful(@NotNull ItemStack itemStack, int slot) {
      Intrinsics.checkNotNullParameter(itemStack, "itemStack");

      boolean item;
      try {
         Item item = itemStack.func_77973_b();
         boolean var65;
         if (!(item instanceof ItemSword) && !(item instanceof ItemTool)) {
            if (item instanceof ItemBow) {
               ItemUtils var67 = ItemUtils.INSTANCE;
               Enchantment $i$f$none = Enchantment.field_77345_t;
               Intrinsics.checkNotNullExpressionValue($i$f$none, "power");
               int currPower = var67.getEnchantment(itemStack, $i$f$none);
               Map $this$none$iv = items$default(this, 0, 0, 3, (Object)null);
               int $i$f$none = 0;
               if ($this$none$iv.isEmpty()) {
                  var65 = true;
               } else {
                  Iterator $dstr$_u24__u24$stack = $this$none$iv.entrySet().iterator();

                  while(true) {
                     if (!$dstr$_u24__u24$stack.hasNext()) {
                        var65 = true;
                        break;
                     }

                     Map.Entry element$iv = (Map.Entry)$dstr$_u24__u24$stack.next();
                     int var48 = 0;
                     ItemStack stack = (ItemStack)element$iv.getValue();
                     boolean var68;
                     if (!Intrinsics.areEqual((Object)itemStack, (Object)stack) && stack.func_77973_b() instanceof ItemBow) {
                        ItemUtils var69 = ItemUtils.INSTANCE;
                        Enchantment stack = Enchantment.field_77345_t;
                        Intrinsics.checkNotNullExpressionValue(stack, "power");
                        int power = var69.getEnchantment(stack, stack);
                        if (currPower == power) {
                           int currDamage = item.getDamage(itemStack);
                           var68 = currDamage >= stack.func_77973_b().getDamage(stack);
                        } else {
                           var68 = currPower < power;
                        }
                     } else {
                        var68 = false;
                     }

                     if (var68) {
                        var65 = false;
                        break;
                     }
                  }
               }
            } else if (item instanceof ItemArmor) {
               ArmorPiece currArmor = new ArmorPiece(itemStack, slot);
               Map $this$none$iv = items$default(this, 0, 0, 3, (Object)null);
               int $i$f$none = 0;
               if ($this$none$iv.isEmpty()) {
                  var65 = true;
               } else {
                  Iterator $dstr$_u24__u24$stack = $this$none$iv.entrySet().iterator();

                  while(true) {
                     if (!$dstr$_u24__u24$stack.hasNext()) {
                        var65 = true;
                        break;
                     }

                     Map.Entry element$iv = (Map.Entry)$dstr$_u24__u24$stack.next();
                     int var47 = 0;
                     int slot = ((Number)element$iv.getKey()).intValue();
                     ItemStack stack = (ItemStack)element$iv.getValue();
                     if (!Intrinsics.areEqual((Object)stack, (Object)itemStack) && stack.func_77973_b() instanceof ItemArmor) {
                        ArmorPiece armor = new ArmorPiece(stack, slot);
                        if (armor.getArmorType() != currArmor.getArmorType()) {
                           var65 = false;
                        } else {
                           int currDamage = item.getDamage(itemStack);
                           int result = ItemUtils.INSTANCE.compareArmor(currArmor, armor, ((Number)nbtArmorPriority.get()).floatValue(), INSTANCE.getGoal());
                           var65 = result == 0 ? currDamage >= stack.func_77973_b().getDamage(stack) : result < 0;
                        }
                     } else {
                        var65 = false;
                     }

                     if (var65) {
                        var65 = false;
                        break;
                     }
                  }
               }
            } else if (item instanceof ItemFlintAndSteel) {
               int currDamage = item.getDamage(itemStack);
               Map $this$none$iv = items$default(this, 0, 0, 3, (Object)null);
               int $i$f$none = 0;
               if ($this$none$iv.isEmpty()) {
                  var65 = true;
               } else {
                  Iterator $dstr$_u24__u24$stack = $this$none$iv.entrySet().iterator();

                  while(true) {
                     if (!$dstr$_u24__u24$stack.hasNext()) {
                        var65 = true;
                        break;
                     }

                     Map.Entry element$iv = (Map.Entry)$dstr$_u24__u24$stack.next();
                     int var46 = 0;
                     ItemStack stack = (ItemStack)element$iv.getValue();
                     if (!Intrinsics.areEqual((Object)itemStack, (Object)stack) && stack.func_77973_b() instanceof ItemFlintAndSteel && currDamage >= stack.func_77973_b().getDamage(stack)) {
                        var65 = false;
                        break;
                     }
                  }
               }
            } else if (Intrinsics.areEqual((Object)itemStack.func_77977_a(), (Object)"item.compass")) {
               Map $this$none$iv = this.items(0, 45);
               int $i$f$none = 0;
               if ($this$none$iv.isEmpty()) {
                  var65 = true;
               } else {
                  Iterator $i$f$none = $this$none$iv.entrySet().iterator();

                  while(true) {
                     if (!$i$f$none.hasNext()) {
                        var65 = true;
                        break;
                     }

                     Map.Entry element$iv = (Map.Entry)$i$f$none.next();
                     int $dstr$_u24__u24$stack = 0;
                     ItemStack stack = (ItemStack)element$iv.getValue();
                     if (!Intrinsics.areEqual((Object)itemStack, (Object)stack) && Intrinsics.areEqual((Object)stack.func_77977_a(), (Object)"item.compass")) {
                        var65 = false;
                        break;
                     }
                  }
               }
            } else {
               var65 = (Boolean)nbtItemNotGarbage.get() && ItemUtils.INSTANCE.hasNBTGoal(itemStack, this.getGoal()) || item instanceof ItemFood || Intrinsics.areEqual((Object)itemStack.func_77977_a(), (Object)"item.arrow") || item instanceof ItemBlock && !InventoryUtils.INSTANCE.isBlockListBlock((ItemBlock)item) || item instanceof ItemBed || item instanceof ItemPotion && (!(Boolean)onlyPositivePotionValue.get() || InventoryUtils.INSTANCE.isPositivePotion((ItemPotion)item, itemStack)) || item instanceof ItemEnderPearl || item instanceof ItemBucket || (Boolean)ignoreVehiclesValue.get() && (item instanceof ItemBoat || item instanceof ItemMinecart);
            }
         } else {
            if (slot >= 36) {
               Integer var10000 = this.findBetterItem(slot - 36, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(slot - 36));
               int var4 = slot - 36;
               if (var10000 != null) {
                  if (var10000 == var4) {
                     return true;
                  }
               }
            }

            int var21 = 0;

            while(var21 < 9) {
               int i = var21++;
               if ((StringsKt.equals(this.type(i), "sword", true) && item instanceof ItemSword || StringsKt.equals(this.type(i), "pickaxe", true) && item instanceof ItemPickaxe || StringsKt.equals(this.type(i), "axe", true) && item instanceof ItemAxe) && this.findBetterItem(i, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i)) == null) {
                  return true;
               }
            }

            Collection $dstr$_u24__u24$stack = itemStack.func_111283_C().get("generic.attackDamage");
            Intrinsics.checkNotNullExpressionValue($dstr$_u24__u24$stack, "itemStack.attributeModif…s[\"generic.attackDamage\"]");
            AttributeModifier var60 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)$dstr$_u24__u24$stack);
            double var61;
            if (var60 == null) {
               var61 = (double)0.0F;
            } else {
               double $dstr$_u24__u24$stack = var60.func_111164_d();
               var61 = $dstr$_u24__u24$stack;
            }

            double damage = var61 + ItemUtils.INSTANCE.getWeaponEnchantFactor(itemStack, ((Number)nbtWeaponPriority.get()).floatValue(), this.getGoal());
            Map $this$none$iv = this.items(0, 45);
            int $i$f$none = 0;
            if ($this$none$iv.isEmpty()) {
               var65 = true;
            } else {
               for(Map.Entry element$iv : $this$none$iv.entrySet()) {
                  int var12 = 0;
                  ItemStack stack = (ItemStack)element$iv.getValue();
                  boolean var62;
                  if (!Intrinsics.areEqual((Object)stack, (Object)itemStack) && Intrinsics.areEqual((Object)stack.getClass(), (Object)itemStack.getClass())) {
                     Collection var14 = stack.func_111283_C().get("generic.attackDamage");
                     Intrinsics.checkNotNullExpressionValue(var14, "stack.attributeModifiers[\"generic.attackDamage\"]");
                     AttributeModifier var63 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var14);
                     double var64;
                     if (var63 == null) {
                        var64 = (double)0.0F;
                     } else {
                        double var55 = var63.func_111164_d();
                        var64 = var55;
                     }

                     double dmg = var64 + ItemUtils.INSTANCE.getWeaponEnchantFactor(stack, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
                     if (damage == dmg) {
                        int currDamage = item.getDamage(itemStack);
                        var62 = currDamage >= stack.func_77973_b().getDamage(stack);
                     } else {
                        var62 = damage < dmg;
                     }
                  } else {
                     var62 = false;
                  }

                  if (var62) {
                     var65 = false;
                     break label44;
                  }
               }

               var65 = true;
            }
         }

         item = var65;
      } catch (Exception ex) {
         ClientUtils.INSTANCE.logError("(InvManager) Failed to check item: " + itemStack.func_77977_a() + '.', (Throwable)ex);
         item = true;
      }

      return item;
   }

   private final ArmorPiece[] findBestArmor() {
      Map armorPieces = (Map)IntStream.range(0, 36).filter(InvManager::findBestArmor$lambda-7).mapToObj(InvManager::findBestArmor$lambda-8).collect(Collectors.groupingBy(InvManager::findBestArmor$lambda-9));
      ArmorPiece[] bestArmor = new ArmorPiece[4];
      Intrinsics.checkNotNullExpressionValue(armorPieces, "armorPieces");

      for(Map.Entry var4 : armorPieces.entrySet()) {
         Integer key = (Integer)var4.getKey();
         List value = (List)var4.getValue();
         Intrinsics.checkNotNull(key);
         int var12 = key;
         int var10 = 0;
         Intrinsics.checkNotNullExpressionValue(value, "it");
         CollectionsKt.sortWith(value, InvManager::findBestArmor$lambda-11$lambda-10);
         Unit var13 = Unit.INSTANCE;
         Intrinsics.checkNotNullExpressionValue(value, "value.also { it.sortWith…Priority.get(), goal) } }");
         bestArmor[var12] = (ArmorPiece)CollectionsKt.lastOrNull(value);
      }

      return bestArmor;
   }

   private final Integer findBetterItem(int targetSlot, ItemStack slotStack) {
      String type = this.type(targetSlot);
      String var6 = type.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      switch (var6.hashCode()) {
         case -1253135533:
            if (var6.equals("gapple")) {
               ItemStack[] var39 = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue(var39, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])var39;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] var67 = $this$forEachIndexed$iv;
               int var74 = 0;
               int var81 = $this$forEachIndexed$iv.length;

               while(var74 < var81) {
                  Object item$iv = var67[var74];
                  ++var74;
                  int index = index$iv++;
                  ItemStack stack = (ItemStack)item$iv;
                  int var110 = 0;
                  Item item = stack == null ? null : stack.func_77973_b();
                  if (item instanceof ItemAppleGold && !StringsKt.equals(INSTANCE.type(index), "Gapple", true)) {
                     boolean replaceCurr = slotStack == null || !(slotStack.func_77973_b() instanceof ItemAppleGold);
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         case -982431341:
            if (var6.equals("potion")) {
               ItemStack[] var37 = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue(var37, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])var37;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] var66 = $this$forEachIndexed$iv;
               int var73 = 0;
               int var80 = $this$forEachIndexed$iv.length;

               while(var73 < var80) {
                  Object item$iv = var66[var73];
                  ++var73;
                  int index = index$iv++;
                  ItemStack stack = (ItemStack)item$iv;
                  int var109 = 0;
                  Item item = stack == null ? null : stack.func_77973_b();
                  if (item instanceof ItemPotion && ItemPotion.func_77831_g(stack.func_77952_i()) && !StringsKt.equals(INSTANCE.type(index), "Potion", true)) {
                     boolean replaceCurr = slotStack == null || !(slotStack.func_77973_b() instanceof ItemPotion) || !ItemPotion.func_77831_g(slotStack.func_77952_i());
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         case -578028723:
            if (!var6.equals("pickaxe")) {
               return null;
            }
            break;
         case 97038:
            if (!var6.equals("axe")) {
               return null;
            }
            break;
         case 97738:
            if (var6.equals("bow")) {
               int bestBow = 0;
               bestBow = (slotStack == null ? null : slotStack.func_77973_b()) instanceof ItemBow ? targetSlot : -1;
               int bestPower = 0;
               int var130;
               if (bestBow != -1) {
                  ItemUtils var129 = ItemUtils.INSTANCE;
                  Intrinsics.checkNotNull(slotStack);
                  Enchantment var54 = Enchantment.field_77345_t;
                  Intrinsics.checkNotNullExpressionValue(var54, "power");
                  var130 = var129.getEnchantment(slotStack, var54);
               } else {
                  var130 = 0;
               }

               bestPower = var130;
               ItemStack[] var55 = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue(var55, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])var55;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] var78 = $this$forEachIndexed$iv;
               int var85 = 0;
               int index = $this$forEachIndexed$iv.length;

               while(var85 < index) {
                  Object item$iv = var78[var85];
                  ++var85;
                  int index = index$iv++;
                  ItemStack var107 = (ItemStack)item$iv;
                  int item = 0;
                  if ((var107 == null ? null : var107.func_77973_b()) instanceof ItemBow && !StringsKt.equals(INSTANCE.type(index), type, true)) {
                     if (bestBow == -1) {
                        bestBow = index;
                     } else {
                        ItemUtils var131 = ItemUtils.INSTANCE;
                        Intrinsics.checkNotNullExpressionValue(var107, "itemStack");
                        Enchantment bestStack = Enchantment.field_77345_t;
                        Intrinsics.checkNotNullExpressionValue(bestStack, "power");
                        int power = var131.getEnchantment(var107, bestStack);
                        var131 = ItemUtils.INSTANCE;
                        bestStack = Enchantment.field_77345_t;
                        Intrinsics.checkNotNullExpressionValue(bestStack, "power");
                        if (var131.getEnchantment(var107, bestStack) > bestPower) {
                           bestBow = index;
                           bestPower = power;
                        }
                     }
                  }
               }

               return bestBow != -1 ? bestBow : null;
            }

            return null;
         case 3148894:
            if (var6.equals("food")) {
               ItemStack[] $this$forEachIndexed$iv = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue($this$forEachIndexed$iv, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])$this$forEachIndexed$iv;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] var63 = $this$forEachIndexed$iv;
               int var70 = 0;
               int var77 = $this$forEachIndexed$iv.length;

               while(var70 < var77) {
                  Object item$iv = var63[var70];
                  ++var70;
                  int index = index$iv++;
                  ItemStack stack = (ItemStack)item$iv;
                  int var106 = 0;
                  Item item = stack == null ? null : stack.func_77973_b();
                  if (item instanceof ItemFood && !(item instanceof ItemAppleGold) && !StringsKt.equals(INSTANCE.type(index), "Food", true)) {
                     boolean replaceCurr = slotStack == null || !(slotStack.func_77973_b() instanceof ItemFood);
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         case 93832333:
            if (var6.equals("block")) {
               ItemStack[] $this$forEachIndexed$iv = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue($this$forEachIndexed$iv, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])$this$forEachIndexed$iv;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] var62 = $this$forEachIndexed$iv;
               int var69 = 0;
               int var76 = $this$forEachIndexed$iv.length;

               while(var69 < var76) {
                  Object item$iv = var62[var69];
                  ++var69;
                  int index = index$iv++;
                  ItemStack stack = (ItemStack)item$iv;
                  int var105 = 0;
                  Item item = stack == null ? null : stack.func_77973_b();
                  if (item instanceof ItemBlock && !InventoryUtils.INSTANCE.getBLOCK_BLACKLIST().contains(((ItemBlock)item).field_150939_a) && !StringsKt.equals(INSTANCE.type(index), "Block", true)) {
                     boolean replaceCurr = slotStack == null || !(slotStack.func_77973_b() instanceof ItemBlock);
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         case 106540102:
            if (var6.equals("pearl")) {
               ItemStack[] $this$forEachIndexed$iv = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue($this$forEachIndexed$iv, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])$this$forEachIndexed$iv;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] $i$f$forEachIndexed = $this$forEachIndexed$iv;
               int var68 = 0;
               int var75 = $this$forEachIndexed$iv.length;

               while(var68 < var75) {
                  Object item$iv = $i$f$forEachIndexed[var68];
                  ++var68;
                  int index = index$iv++;
                  ItemStack item$iv = (ItemStack)item$iv;
                  int itemStack = 0;
                  Item item = item$iv == null ? null : item$iv.func_77973_b();
                  if (item instanceof ItemEnderPearl && !StringsKt.equals(INSTANCE.type(index), "Pearl", true)) {
                     boolean replaceCurr = slotStack == null || !(slotStack.func_77973_b() instanceof ItemEnderPearl);
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         case 109860349:
            if (!var6.equals("sword")) {
               return null;
            }
            break;
         case 112903447:
            if (var6.equals("water")) {
               ItemStack[] currentType = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
               Intrinsics.checkNotNullExpressionValue(currentType, "mc.thePlayer.inventory.mainInventory");
               Object[] $this$forEachIndexed$iv = (Object[])currentType;
               int $i$f$forEachIndexed = 0;
               int index$iv = 0;
               Object[] $i$f$forEachIndexed = $this$forEachIndexed$iv;
               int index$iv = 0;
               int var10 = $this$forEachIndexed$iv.length;

               while(index$iv < var10) {
                  Object item$iv = $i$f$forEachIndexed[index$iv];
                  ++index$iv;
                  int index = index$iv++;
                  ItemStack item$iv = (ItemStack)item$iv;
                  int itemStack = 0;
                  Item item = item$iv == null ? null : item$iv.func_77973_b();
                  if (item instanceof ItemBucket && Intrinsics.areEqual((Object)((ItemBucket)item).field_77876_a, (Object)Blocks.field_150358_i) && !StringsKt.equals(INSTANCE.type(index), "Water", true)) {
                     boolean var128;
                     label257: {
                        if (slotStack != null && slotStack.func_77973_b() instanceof ItemBucket) {
                           Item var10000 = slotStack.func_77973_b();
                           if (var10000 == null) {
                              throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBucket");
                           }

                           if (Intrinsics.areEqual((Object)((ItemBucket)var10000).field_77876_a, (Object)Blocks.field_150358_i)) {
                              var128 = false;
                              break label257;
                           }
                        }

                        var128 = true;
                     }

                     boolean replaceCurr = var128;
                     return replaceCurr ? index : null;
                  }
               }
            }

            return null;
         default:
            return null;
      }

      Class var133;
      if (StringsKt.equals(type, "Sword", true)) {
         var133 = ItemSword.class;
      } else if (StringsKt.equals(type, "Pickaxe", true)) {
         var133 = ItemPickaxe.class;
      } else {
         if (!StringsKt.equals(type, "Axe", true)) {
            return null;
         }

         var133 = ItemAxe.class;
      }

      Class currentType = var133;
      int bestWeapon = 0;
      if (slotStack == null) {
         var133 = null;
      } else {
         Item var135 = slotStack.func_77973_b();
         var133 = var135 == null ? null : var135.getClass();
      }

      bestWeapon = Intrinsics.areEqual((Object)var133, (Object)currentType) ? targetSlot : -1;
      ItemStack[] var57 = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
      Intrinsics.checkNotNullExpressionValue(var57, "mc.thePlayer.inventory.mainInventory");
      Object[] $this$forEachIndexed$iv = (Object[])var57;
      int $i$f$forEachIndexed = 0;
      int index$iv = 0;
      Object[] var79 = $this$forEachIndexed$iv;
      int var86 = 0;
      int index = $this$forEachIndexed$iv.length;

      while(var86 < index) {
         Object item$iv = var79[var86];
         ++var86;
         int index = index$iv++;
         ItemStack var108 = (ItemStack)item$iv;
         int item = 0;
         if (var108 != null && Intrinsics.areEqual((Object)var108.func_77973_b().getClass(), (Object)currentType) && !StringsKt.equals(INSTANCE.type(index), type, true)) {
            if (bestWeapon == -1) {
               bestWeapon = index;
            } else {
               Collection var123 = var108.func_111283_C().get("generic.attackDamage");
               Intrinsics.checkNotNullExpressionValue(var123, "itemStack.attributeModif…s[\"generic.attackDamage\"]");
               AttributeModifier var136 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var123);
               double var137;
               if (var136 == null) {
                  var137 = (double)0.0F;
               } else {
                  double var124 = var136.func_111164_d();
                  var137 = var124;
               }

               double currDamage = var137 + ItemUtils.INSTANCE.getWeaponEnchantFactor(var108, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
               ItemStack var138 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestWeapon);
               if (var138 != null) {
                  ItemStack bestStack = var138;
                  Collection var23 = bestStack.func_111283_C().get("generic.attackDamage");
                  Intrinsics.checkNotNullExpressionValue(var23, "bestStack.attributeModif…s[\"generic.attackDamage\"]");
                  AttributeModifier var139 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var23);
                  double var140;
                  if (var139 == null) {
                     var140 = (double)0.0F;
                  } else {
                     double var127 = var139.func_111164_d();
                     var140 = var127;
                  }

                  double bestDamage = var140 + ItemUtils.INSTANCE.getWeaponEnchantFactor(bestStack, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
                  if (bestDamage < currDamage) {
                     bestWeapon = index;
                  }
               }
            }
         }
      }

      return bestWeapon == -1 && bestWeapon != targetSlot ? null : bestWeapon;
   }

   private final Map items(int start, int end) {
      Map items = (Map)(new LinkedHashMap());
      int var4 = end - 1;
      int i;
      if (start <= var4) {
         do {
            i = var4--;
            ItemStack var10000 = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
            if (var10000 != null) {
               ItemStack itemStack = var10000;
               if (itemStack.func_77973_b() != null) {
                  if ((!(36 <= i ? i < 45 : false) || !StringsKt.equals(this.type(i), "Ignore", true)) && System.currentTimeMillis() - ((IItemStack)itemStack).getItemDelay() >= (long)((Number)itemDelayValue.get()).intValue()) {
                     Integer var8 = i;
                     items.put(var8, itemStack);
                  }
               }
            }
         } while(i != start);
      }

      return items;
   }

   // $FF: synthetic method
   static Map items$default(InvManager var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = 45;
      }

      return var0.items(var1, var2);
   }

   private final boolean move(int item, boolean isArmorSlot) {
      if (item == -1) {
         return false;
      } else if (!isArmorSlot && item < 9 && (Boolean)hotbarValue.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(item)));
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(item).func_75211_c())));
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
         delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
         return true;
      } else if (this.checkOpen()) {
         return true;
      } else {
         if ((Boolean)throwValue.get() && isArmorSlot) {
            MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71069_bz.field_75152_c, item, 0, 4, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            currentSlot = item;
         } else {
            MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71069_bz.field_75152_c, isArmorSlot ? item : (item < 9 ? item + 36 : item), 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            currentSlot = isArmorSlot ? item : (item < 9 ? item + 36 : item);
         }

         if (!(Boolean)instantValue.get()) {
            delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
            return true;
         } else {
            return true;
         }
      }
   }

   private final String type(int targetSlot) {
      String var10000;
      switch (targetSlot) {
         case 0:
            var10000 = (String)sortSlot1Value.get();
            break;
         case 1:
            var10000 = (String)sortSlot2Value.get();
            break;
         case 2:
            var10000 = (String)sortSlot3Value.get();
            break;
         case 3:
            var10000 = (String)sortSlot4Value.get();
            break;
         case 4:
            var10000 = (String)sortSlot5Value.get();
            break;
         case 5:
            var10000 = (String)sortSlot6Value.get();
            break;
         case 6:
            var10000 = (String)sortSlot7Value.get();
            break;
         case 7:
            var10000 = (String)sortSlot8Value.get();
            break;
         case 8:
            var10000 = (String)sortSlot9Value.get();
            break;
         default:
            var10000 = "";
      }

      return var10000;
   }

   private static final boolean findBestArmor$lambda_7/* $FF was: findBestArmor$lambda-7*/(int i) {
      ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
      return itemStack != null && itemStack.func_77973_b() instanceof ItemArmor && (i < 9 || System.currentTimeMillis() - ((IItemStack)itemStack).getItemDelay() >= (long)((Number)itemDelayValue.get()).intValue());
   }

   private static final ArmorPiece findBestArmor$lambda_8/* $FF was: findBestArmor$lambda-8*/(int i) {
      ItemStack var1 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
      Intrinsics.checkNotNullExpressionValue(var1, "mc.thePlayer.inventory.getStackInSlot(i)");
      return new ArmorPiece(var1, i);
   }

   private static final Integer findBestArmor$lambda_9/* $FF was: findBestArmor$lambda-9*/(ArmorPiece obj) {
      Intrinsics.checkNotNullParameter(obj, "obj");
      return obj.getArmorType();
   }

   private static final int findBestArmor$lambda_11$lambda_10/* $FF was: findBestArmor$lambda-11$lambda-10*/(ArmorPiece armorPiece, ArmorPiece armorPiece2) {
      ItemUtils var10000 = ItemUtils.INSTANCE;
      Intrinsics.checkNotNullExpressionValue(armorPiece, "armorPiece");
      Intrinsics.checkNotNullExpressionValue(armorPiece2, "armorPiece2");
      return var10000.compareArmor(armorPiece, armorPiece2, ((Number)nbtArmorPriority.get()).floatValue(), INSTANCE.getGoal());
   }

   static {
      maxDelayValue = (IntegerValue)(new IntegerValue() {
         protected void onChanged(int oldValue, int newValue) {
            int minCPS = ((Number)InvManager.minDelayValue.get()).intValue();
            if (minCPS > newValue) {
               this.set(minCPS);
            }

         }
      }).displayable(null.INSTANCE);
      minDelayValue = (IntegerValue)(new IntegerValue() {
         protected void onChanged(int oldValue, int newValue) {
            int maxDelay = ((Number)InvManager.maxDelayValue.get()).intValue();
            if (maxDelay < newValue) {
               this.set(maxDelay);
            }

         }
      }).displayable(null.INSTANCE);
      invOpenValue = new BoolValue("InvOpen", false);
      simulateInventory = new BoolValue("InvSpoof", true);
      simulateDelayValue = (new IntegerValue("InvSpoof", 0, 0, 1000)).displayable(null.INSTANCE);
      noMoveValue = new BoolValue("NoMove", false);
      hotbarValue = new BoolValue("Hotbar", true);
      randomSlotValue = new BoolValue("RandomSlot", false);
      sortValue = new BoolValue("Sort", true);
      throwValue = new BoolValue("Drop", true);
      armorValue = new BoolValue("Armor", true);
      noCombatValue = new BoolValue("NoCombat", false);
      itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
      swingValue = new BoolValue("Swing", true);
      ItemUtils.EnumNBTPriorityType[] $this$map$iv = ItemUtils.EnumNBTPriorityType.values();
      String var11 = "NBTGoal";
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.length));
      int $i$f$mapTo = 0;
      ItemUtils.EnumNBTPriorityType[] var5 = $this$map$iv;
      int var6 = 0;
      int var7 = $this$map$iv.length;

      while(var6 < var7) {
         Object item$iv$iv = var5[var6];
         ++var6;
         int var10 = 0;
         destination$iv$iv.add(((ItemUtils.EnumNBTPriorityType)item$iv$iv).toString());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var10001 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var19 = (String[])var10001;
         String var13 = "NONE";
         String[] var14 = var19;
         nbtGoalValue = new ListValue(var11, var14, var13);
         nbtItemNotGarbage = (new BoolValue("NBTItemNotGarbage", true)).displayable(null.INSTANCE);
         nbtArmorPriority = (new FloatValue("NBTArmorPriority", 0.0F, 0.0F, 5.0F)).displayable(null.INSTANCE);
         nbtWeaponPriority = (new FloatValue("NBTWeaponPriority", 0.0F, 0.0F, 5.0F)).displayable(null.INSTANCE);
         ignoreVehiclesValue = new BoolValue("IgnoreVehicles", false);
         onlyPositivePotionValue = new BoolValue("OnlyPositivePotion", false);
         String[] thisCollection$iv = new String[]{"None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl", "Potion"};
         items = thisCollection$iv;
         sortSlot1Value = (new ListValue("SortSlot-1", items, "Sword")).displayable(null.INSTANCE);
         sortSlot2Value = (new ListValue("SortSlot-2", items, "Gapple")).displayable(null.INSTANCE);
         sortSlot3Value = (new ListValue("SortSlot-3", items, "Potion")).displayable(null.INSTANCE);
         sortSlot4Value = (new ListValue("SortSlot-4", items, "Pickaxe")).displayable(null.INSTANCE);
         sortSlot5Value = (new ListValue("SortSlot-5", items, "Axe")).displayable(null.INSTANCE);
         sortSlot6Value = (new ListValue("SortSlot-6", items, "None")).displayable(null.INSTANCE);
         sortSlot7Value = (new ListValue("SortSlot-7", items, "Block")).displayable(null.INSTANCE);
         sortSlot8Value = (new ListValue("SortSlot-8", items, "Pearl")).displayable(null.INSTANCE);
         sortSlot9Value = (new ListValue("SortSlot-9", items, "Food")).displayable(null.INSTANCE);
         simDelayTimer = new MSTimer();
         currentSlot = -1;
      }
   }
}
