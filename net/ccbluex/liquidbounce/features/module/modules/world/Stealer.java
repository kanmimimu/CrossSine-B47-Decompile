package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "Stealer",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020\u000f2\u0006\u0010D\u001a\u00020EH\u0002J\u0018\u0010F\u001a\u00020B2\u0006\u0010G\u001a\u00020E2\u0006\u0010H\u001a\u00020IH\u0002J\u0010\u0010J\u001a\u00020B2\u0006\u0010K\u001a\u00020LH\u0007J\u0010\u0010M\u001a\u00020B2\u0006\u0010K\u001a\u00020NH\u0003J\u0010\u0010O\u001a\u00020B2\u0006\u0010K\u001a\u00020PH\u0007J\u0010\u0010Q\u001a\u00020B2\u0006\u0010K\u001a\u00020RH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001d0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0014\u00103\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u000e\u00106\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u000208X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010<\u001a\b\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\b\n\u0000\u001a\u0004\b=\u00102R\u0011\u0010>\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010$R\u000e\u0010@\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006S"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Stealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Aura", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "AuraclickedBlocks", "", "Lnet/minecraft/util/BlockPos;", "getAuraclickedBlocks", "()Ljava/util/List;", "AuracurrentBlock", "AuradelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "AuradiscoverDelayEnabledValue", "", "AuradiscoverDelayValue", "AuranoCombatingValue", "AuranotOpenedValue", "AuraonlyOnGroundValue", "AurarangeValue", "", "AurarotationsValue", "AuraswingValue", "", "AurathroughWallsValue", "AuraunderClick", "alwayTake", "autoCloseDelayValue", "Lkotlin/ranges/IntRange;", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "chestTimer", "chestTitleValue", "getChestTitleValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "chestValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "closeOnFullValue", "contentReceived", "currentSlot", "getCurrentSlot", "()I", "setCurrentSlot", "(I)V", "delayTimer", "delayValue", "freelookValue", "getFreelookValue", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "fullInventory", "getFullInventory", "()Z", "instantValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "onlyItemsValue", "silentTitleValue", "getSilentTitleValue", "silentValue", "getSilentValue", "takeRandomizedValue", "click", "", "isEmpty", "chest", "Lnet/minecraft/client/gui/inventory/GuiChest;", "move", "screen", "slot", "Lnet/minecraft/inventory/Slot;", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class Stealer extends Module {
   @NotNull
   public static final Stealer INSTANCE = new Stealer();
   @NotNull
   private static final BoolValue instantValue = new BoolValue("Instant", false);
   @NotNull
   private static final Value delayValue;
   @NotNull
   private static final IntegerValue chestValue;
   @NotNull
   private static final BoolValue takeRandomizedValue;
   @NotNull
   private static final Value alwayTake;
   @NotNull
   private static final BoolValue onlyItemsValue;
   @NotNull
   private static final BoolValue noCompassValue;
   @NotNull
   private static final BoolValue autoCloseValue;
   @NotNull
   private static final Value freelookValue;
   @NotNull
   private static final BoolValue silentValue;
   @NotNull
   private static final Value silentTitleValue;
   @NotNull
   private static final Value autoCloseDelayValue;
   @NotNull
   private static final Value closeOnFullValue;
   @NotNull
   private static final BoolValue chestTitleValue;
   @NotNull
   private static final BoolValue Aura;
   @NotNull
   private static final Value AurarangeValue;
   @NotNull
   private static final Value AuradelayValue;
   @NotNull
   private static final Value AurathroughWallsValue;
   @NotNull
   private static final Value AuraswingValue;
   @NotNull
   private static final Value AurarotationsValue;
   @NotNull
   private static final Value AuradiscoverDelayEnabledValue;
   @NotNull
   private static final Value AuradiscoverDelayValue;
   @NotNull
   private static final Value AuraonlyOnGroundValue;
   @NotNull
   private static final Value AuranotOpenedValue;
   @NotNull
   private static final Value AuranoCombatingValue;
   @Nullable
   private static BlockPos AuracurrentBlock;
   private static boolean AuraunderClick;
   @NotNull
   private static final List AuraclickedBlocks;
   @NotNull
   private static final MSTimer delayTimer;
   @NotNull
   private static final MSTimer chestTimer;
   private static long nextDelay;
   @NotNull
   private static final MSTimer autoCloseTimer;
   private static long nextCloseDelay;
   private static int currentSlot;
   private static int contentReceived;

   private Stealer() {
   }

   @NotNull
   public final Value getFreelookValue() {
      return freelookValue;
   }

   @NotNull
   public final BoolValue getSilentValue() {
      return silentValue;
   }

   @NotNull
   public final Value getSilentTitleValue() {
      return silentTitleValue;
   }

   @NotNull
   public final BoolValue getChestTitleValue() {
      return chestTitleValue;
   }

   @NotNull
   public final List getAuraclickedBlocks() {
      return AuraclickedBlocks;
   }

   public final int getCurrentSlot() {
      return currentSlot;
   }

   public final void setCurrentSlot(int var1) {
      currentSlot = var1;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (chestTimer.hasTimePassed((long)((Number)chestValue.get()).intValue())) {
         GuiScreen screen = MinecraftInstance.mc.field_71462_r;
         if (screen instanceof GuiChest && delayTimer.hasTimePassed(nextDelay)) {
            if ((Boolean)noCompassValue.get()) {
               ItemStack var10000 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g();
               String var19;
               if (var10000 == null) {
                  var19 = null;
               } else {
                  Item var20 = var10000.func_77973_b();
                  var19 = var20 == null ? null : var20.func_77658_a();
               }

               if (Intrinsics.areEqual((Object)var19, (Object)"item.compass")) {
                  return;
               }
            }

            if ((Boolean)chestTitleValue.get()) {
               if (((GuiChest)screen).field_147015_w == null) {
                  return;
               }

               String var3 = ((GuiChest)screen).field_147015_w.func_70005_c_();
               Intrinsics.checkNotNullExpressionValue(var3, "screen.lowerChestInventory.name");
               CharSequence var21 = (CharSequence)var3;
               var3 = (new ItemStack((Item)Item.field_150901_e.func_82594_a(new ResourceLocation("minecraft:chest")))).func_82833_r();
               Intrinsics.checkNotNullExpressionValue(var3, "ItemStack(Item.itemRegis…aft:chest\"))).displayName");
               if (!StringsKt.contains$default(var21, (CharSequence)var3, false, 2, (Object)null)) {
                  return;
               }
            }

            Module var22 = CrossSine.INSTANCE.getModuleManager().get(InvManager.class);
            Intrinsics.checkNotNull(var22);
            InvManager invManager = (InvManager)var22;
            if (!this.isEmpty((GuiChest)screen) && (!(Boolean)closeOnFullValue.get() || !this.getFullInventory())) {
               autoCloseTimer.reset();
               if ((Boolean)takeRandomizedValue.get()) {
                  List items;
                  do {
                     items = (List)(new ArrayList());
                     int randomSlot = 0;
                     int var15 = ((GuiChest)screen).field_147018_x * 9;

                     while(randomSlot < var15) {
                        int slotIndex = randomSlot++;
                        Slot slot = (Slot)((GuiChest)screen).field_147002_h.field_75151_b.get(slotIndex);
                        if (slot.func_75211_c() != null && (!(Boolean)onlyItemsValue.get() || !(slot.func_75211_c().func_77973_b() instanceof ItemBlock))) {
                           if (invManager.getState() && !(Boolean)alwayTake.get()) {
                              ItemStack var9 = slot.func_75211_c();
                              Intrinsics.checkNotNullExpressionValue(var9, "slot.stack");
                              if (!invManager.isUseful(var9, -1)) {
                                 continue;
                              }
                           }

                           Intrinsics.checkNotNullExpressionValue(slot, "slot");
                           items.add(slot);
                        }
                     }

                     randomSlot = Random.Default.nextInt(items.size());
                     Slot slot = (Slot)items.get(randomSlot);
                     this.move((GuiChest)screen, slot);
                     currentSlot = slot.getSlotIndex();
                  } while((Boolean)instantValue.get() || delayTimer.hasTimePassed(nextDelay) && !((Collection)items).isEmpty());

                  return;
               }

               int items = 0;
               int randomSlot = ((GuiChest)screen).field_147018_x * 9;

               while(items < randomSlot) {
                  int slotIndex = items++;
                  Slot slot = (Slot)((GuiChest)screen).field_147002_h.field_75151_b.get(slotIndex);
                  if (!(Boolean)instantValue.get()) {
                     if (!delayTimer.hasTimePassed(nextDelay) || slot.func_75211_c() == null || (Boolean)onlyItemsValue.get() && slot.func_75211_c().func_77973_b() instanceof ItemBlock) {
                        continue;
                     }

                     if (invManager.getState()) {
                        ItemStack slot = slot.func_75211_c();
                        Intrinsics.checkNotNullExpressionValue(slot, "slot.stack");
                        if (!invManager.isUseful(slot, -1)) {
                           continue;
                        }
                     }
                  }

                  GuiChest var10001 = (GuiChest)screen;
                  Intrinsics.checkNotNullExpressionValue(slot, "slot");
                  this.move(var10001, slot);
                  currentSlot = slotIndex;
               }
            } else if ((Boolean)instantValue.get()) {
               MinecraftInstance.mc.field_71439_g.func_71053_j();
            } else if ((Boolean)autoCloseValue.get() && ((GuiChest)screen).field_147002_h.field_75152_c == contentReceived && autoCloseTimer.hasTimePassed(nextCloseDelay)) {
               MinecraftInstance.mc.field_71439_g.func_71053_j();
               nextCloseDelay = TimeUtils.INSTANCE.randomDelay(((IntRange)autoCloseDelayValue.get()).getFirst(), ((IntRange)autoCloseDelayValue.get()).getLast());
            }

         } else {
            autoCloseTimer.reset();
         }
      }
   }

   @EventTarget
   private final void onPacket(PacketEvent event) {
      Packet packet = event.getPacket();
      if (packet instanceof S30PacketWindowItems) {
         contentReceived = ((S30PacketWindowItems)packet).func_148911_c();
      }

      if (packet instanceof S2DPacketOpenWindow) {
         chestTimer.reset();
      }

      if ((Boolean)Aura.get() && (Boolean)AuranotOpenedValue.get() && event.getPacket() instanceof S24PacketBlockAction) {
         Packet packet = event.getPacket();
         if (((S24PacketBlockAction)packet).func_148868_c() instanceof BlockChest && ((S24PacketBlockAction)packet).func_148864_h() == 1 && !AuraclickedBlocks.contains(((S24PacketBlockAction)packet).func_179825_a())) {
            List var10000 = AuraclickedBlocks;
            BlockPos var4 = ((S24PacketBlockAction)packet).func_179825_a();
            Intrinsics.checkNotNullExpressionValue(var4, "packet.blockPosition");
            var10000.add(var4);
         }
      }

   }

   private final void move(GuiChest screen, Slot slot) {
      screen.func_146984_a(slot, slot.field_75222_d, 0, 1);
      delayTimer.reset();
      nextDelay = TimeUtils.INSTANCE.randomDelay(((IntRange)delayValue.get()).getFirst(), ((IntRange)delayValue.get()).getLast());
   }

   private final boolean isEmpty(GuiChest chest) {
      Module var10000 = CrossSine.INSTANCE.getModuleManager().get(InvManager.class);
      Intrinsics.checkNotNull(var10000);
      InvManager invManager = (InvManager)var10000;
      int var3 = 0;
      int var4 = chest.field_147018_x * 9;

      while(true) {
         if (var3 >= var4) {
            return true;
         }

         int i = var3++;
         Slot slot = (Slot)chest.field_147002_h.field_75151_b.get(i);
         if (slot.func_75211_c() != null && (!(Boolean)onlyItemsValue.get() || !(slot.func_75211_c().func_77973_b() instanceof ItemBlock))) {
            if (!invManager.getState()) {
               break;
            }

            ItemStack var7 = slot.func_75211_c();
            Intrinsics.checkNotNullExpressionValue(var7, "slot.stack");
            if (invManager.isUseful(var7, -1)) {
               break;
            }
         }
      }

      return false;
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)Aura.get()) {
         if ((Boolean)AuraonlyOnGroundValue.get() && !MinecraftInstance.mc.field_71439_g.field_70122_E || (Boolean)AuranoCombatingValue.get() && CrossSine.INSTANCE.getCombatManager().getInCombat()) {
            return;
         }

         if (event.getEventState() == EventState.PRE) {
            if (MinecraftInstance.mc.field_71462_r instanceof GuiContainer) {
               return;
            }

            float radius = ((Number)AurarangeValue.get()).floatValue() + (float)1;
            Vec3 eyesPos = new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)MinecraftInstance.mc.field_71439_g.func_70047_e(), MinecraftInstance.mc.field_71439_g.field_70161_v);
            Map $this$filter$iv = BlockUtils.searchBlocks((int)radius);
            int $i$f$filter = 0;
            Map destination$iv$iv = (Map)(new LinkedHashMap());
            int $i$f$filterTo = 0;

            for(Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
               int var15 = 0;
               if (element$iv$iv.getValue() instanceof BlockChest && !INSTANCE.getAuraclickedBlocks().contains(element$iv$iv.getKey()) && BlockUtils.getCenterDistance((BlockPos)element$iv$iv.getKey()) < (double)((Number)AurarangeValue.get()).floatValue()) {
                  destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
               }
            }

            $i$f$filter = 0;
            Map $this$filterTo$iv$iv = destination$iv$iv;
            destination$iv$iv = (Map)(new LinkedHashMap());
            $i$f$filterTo = 0;

            for(Map.Entry element$iv$iv : $this$filterTo$iv$iv.entrySet()) {
               int var34 = 0;
               boolean var10000;
               if ((Boolean)AurathroughWallsValue.get()) {
                  var10000 = true;
               } else {
                  BlockPos blockPos = (BlockPos)element$iv$iv.getKey();
                  MovingObjectPosition movingObjectPosition = MinecraftInstance.mc.field_71441_e.func_147447_a(eyesPos, BlockExtensionKt.getVec(blockPos), false, true, false);
                  var10000 = movingObjectPosition != null && Intrinsics.areEqual((Object)movingObjectPosition.func_178782_a(), (Object)blockPos);
               }

               if (var10000) {
                  destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
               }
            }

            Iterator $this$filterTo$iv$iv = ((Iterable)destination$iv$iv.entrySet()).iterator();
            Object var35;
            if (!$this$filterTo$iv$iv.hasNext()) {
               var35 = null;
            } else {
               Object var24 = $this$filterTo$iv$iv.next();
               if (!$this$filterTo$iv$iv.hasNext()) {
                  var35 = var24;
               } else {
                  Map.Entry it = (Map.Entry)var24;
                  int var29 = 0;
                  double var27 = BlockUtils.getCenterDistance((BlockPos)it.getKey());

                  do {
                     Object var30 = $this$filterTo$iv$iv.next();
                     Map.Entry it = (Map.Entry)var30;
                     int it = 0;
                     double it = BlockUtils.getCenterDistance((BlockPos)it.getKey());
                     if (Double.compare(var27, it) > 0) {
                        var24 = var30;
                        var27 = it;
                     }
                  } while($this$filterTo$iv$iv.hasNext());

                  var35 = var24;
               }
            }

            AuracurrentBlock = (Map.Entry)var35 == null ? null : (BlockPos)((Map.Entry)var35).getKey();
            if ((Boolean)AurarotationsValue.get()) {
               BlockPos var36 = AuracurrentBlock;
               if (var36 == null) {
                  return;
               }

               VecRotation var4 = RotationUtils.faceBlock(var36);
               if (var4 == null) {
                  return;
               }

               RotationUtils.setTargetRotation(var4.getRotation(), 0);
            }
         } else if (AuracurrentBlock != null && InventoryUtils.INSTANCE.getINV_TIMER().hasTimePassed((long)((Number)AuradelayValue.get()).intValue()) && !AuraunderClick) {
            AuraunderClick = true;
            if ((Boolean)AuradiscoverDelayEnabledValue.get()) {
               Timer var18 = new Timer();
               long var19 = (long)((Number)AuradiscoverDelayValue.get()).intValue();
               TimerTask $this$filter$iv = new Stealer$onMotion$$inlined$schedule$1();
               var18.schedule($this$filter$iv, var19);
            } else {
               this.click();
            }
         }
      }

   }

   private final void click() {
      try {
         PlayerControllerMP var10000 = MinecraftInstance.mc.field_71442_b;
         EntityPlayerSP var10001 = MinecraftInstance.mc.field_71439_g;
         WorldClient var10002 = MinecraftInstance.mc.field_71441_e;
         ItemStack var10003 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
         BlockPos var10004 = AuracurrentBlock;
         EnumFacing var10005 = EnumFacing.DOWN;
         BlockPos var10006 = AuracurrentBlock;
         Intrinsics.checkNotNull(var10006);
         if (var10000.func_178890_a(var10001, var10002, var10003, var10004, var10005, BlockExtensionKt.getVec(var10006))) {
            if (AuraswingValue.equals("packet")) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
            } else if (AuraswingValue.equals("normal")) {
               MinecraftInstance.mc.field_71439_g.func_71038_i();
            }

            List var3 = AuraclickedBlocks;
            BlockPos var4 = AuracurrentBlock;
            Intrinsics.checkNotNull(var4);
            var3.add(var4);
            AuracurrentBlock = null;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      AuraunderClick = false;
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)Aura.get()) {
         AuraclickedBlocks.clear();
      }

   }

   private final boolean getFullInventory() {
      ItemStack[] var1 = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
      Intrinsics.checkNotNullExpressionValue(var1, "mc.thePlayer.inventory.mainInventory");
      Object[] $this$none$iv = (Object[])var1;
      int $i$f$none = 0;
      Object[] var3 = $this$none$iv;
      int var4 = 0;
      int var5 = $this$none$iv.length;

      boolean var10000;
      while(true) {
         if (var4 < var5) {
            Object element$iv = var3[var4];
            ++var4;
            ItemStack it = (ItemStack)element$iv;
            int var8 = 0;
            if (it != null) {
               continue;
            }

            var10000 = false;
            break;
         }

         var10000 = true;
         break;
      }

      return var10000;
   }

   // $FF: synthetic method
   public static final void access$click(Stealer $this) {
      $this.click();
   }

   static {
      delayValue = (new IntegerRangeValue("StealerDelay", 200, 400, 0, 400, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      chestValue = new IntegerValue("ChestOpenDelay", 300, 0, 1000);
      takeRandomizedValue = new BoolValue("TakeRandomized", false);
      alwayTake = (new BoolValue("AlwayTakeItem", false)).displayable(null.INSTANCE);
      onlyItemsValue = new BoolValue("OnlyItems", false);
      noCompassValue = new BoolValue("NoCompass", false);
      autoCloseValue = new BoolValue("AutoClose", true);
      freelookValue = (new BoolValue("FreeLook", false)).displayable(null.INSTANCE);
      silentValue = new BoolValue("Silent", true);
      silentTitleValue = (new BoolValue("Title", true)).displayable(null.INSTANCE);
      autoCloseDelayValue = (new IntegerRangeValue("AutoCloseDelay", 0, 0, 0, 400, (Function0)null, 32, (DefaultConstructorMarker)null)).displayable(null.INSTANCE);
      closeOnFullValue = (new BoolValue("CloseOnFull", true)).displayable(null.INSTANCE);
      chestTitleValue = new BoolValue("ChestTitle", false);
      Aura = new BoolValue("Aura", false);
      AurarangeValue = (new FloatValue("Range", 5.0F, 1.0F, 6.0F)).displayable(null.INSTANCE);
      AuradelayValue = (new IntegerValue("AuraDelay", 100, 50, 500)).displayable(null.INSTANCE);
      AurathroughWallsValue = (new BoolValue("ThroughWalls", true)).displayable(null.INSTANCE);
      String[] var0 = new String[]{"Normal", "Packet", "None"};
      AuraswingValue = (new ListValue("Swing", var0, "Normal")).displayable(null.INSTANCE);
      AurarotationsValue = (new BoolValue("Rotations", true)).displayable(null.INSTANCE);
      AuradiscoverDelayEnabledValue = (new BoolValue("DiscoverDelay", false)).displayable(null.INSTANCE);
      AuradiscoverDelayValue = (new IntegerValue("DiscoverDelayValue", 200, 50, 300)).displayable(null.INSTANCE);
      AuraonlyOnGroundValue = (new BoolValue("OnlyOnGround", true)).displayable(null.INSTANCE);
      AuranotOpenedValue = (new BoolValue("NotOpened", false)).displayable(null.INSTANCE);
      AuranoCombatingValue = (new BoolValue("NoCombating", true)).displayable(null.INSTANCE);
      AuraclickedBlocks = (List)(new ArrayList());
      delayTimer = new MSTimer();
      chestTimer = new MSTimer();
      nextDelay = TimeUtils.INSTANCE.randomDelay(((IntRange)delayValue.get()).getFirst(), ((IntRange)delayValue.get()).getLast());
      autoCloseTimer = new MSTimer();
      nextCloseDelay = TimeUtils.INSTANCE.randomDelay(((IntRange)autoCloseDelayValue.get()).getFirst(), ((IntRange)autoCloseDelayValue.get()).getLast());
      currentSlot = -1;
   }
}
