package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Gapple",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020.2\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020.2\u0006\u00100\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020.2\u0006\u00100\u001a\u000205H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\t\"\u0004\b\u001c\u0010\u000bR\u000e\u0010\u001d\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0013\"\u0004\b \u0010\u0015R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010\u0015R\u000e\u0010,\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Gapple;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AlertValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "absorpCheck", "delay", "", "getDelay", "()I", "setDelay", "(I)V", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "eating", "groundCheck", "invCheck", "isDisable", "", "()Z", "setDisable", "(Z)V", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "percent", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "prevSlot", "getPrevSlot", "setPrevSlot", "regenSec", "switchBack", "getSwitchBack", "setSwitchBack", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tryHeal", "getTryHeal", "setTryHeal", "waitRegen", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class Gapple extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final FloatValue percent;
   @NotNull
   private final IntegerRangeValue delayValue;
   @NotNull
   private final FloatValue regenSec;
   @NotNull
   private final BoolValue groundCheck;
   @NotNull
   private final BoolValue waitRegen;
   @NotNull
   private final BoolValue invCheck;
   @NotNull
   private final BoolValue absorpCheck;
   @NotNull
   private final BoolValue AlertValue;
   @NotNull
   private final MSTimer timer;
   private int eating;
   private int delay;
   private boolean isDisable;
   private boolean tryHeal;
   private int prevSlot;
   private boolean switchBack;

   public Gapple() {
      String[] var1 = new String[]{"Auto", "LegitAuto", "Legit", "Head"};
      this.modeValue = new ListValue("Mode", var1, "Auto");
      this.percent = new FloatValue("HealthPercent", 75.0F, 1.0F, 100.0F);
      this.delayValue = new IntegerRangeValue("Delay", 75, 125, 1, 5000, (Function0)null, 32, (DefaultConstructorMarker)null);
      this.regenSec = new FloatValue("MinRegenSec", 4.6F, 0.0F, 10.0F);
      this.groundCheck = new BoolValue("OnlyOnGround", false);
      this.waitRegen = new BoolValue("WaitRegen", true);
      this.invCheck = new BoolValue("InvCheck", false);
      this.absorpCheck = new BoolValue("NoAbsorption", true);
      this.AlertValue = new BoolValue("Alert", false);
      this.timer = new MSTimer();
      this.eating = -1;
      this.prevSlot = -1;
   }

   @NotNull
   public final MSTimer getTimer() {
      return this.timer;
   }

   public final int getDelay() {
      return this.delay;
   }

   public final void setDelay(int var1) {
      this.delay = var1;
   }

   public final boolean isDisable() {
      return this.isDisable;
   }

   public final void setDisable(boolean var1) {
      this.isDisable = var1;
   }

   public final boolean getTryHeal() {
      return this.tryHeal;
   }

   public final void setTryHeal(boolean var1) {
      this.tryHeal = var1;
   }

   public final int getPrevSlot() {
      return this.prevSlot;
   }

   public final void setPrevSlot(int var1) {
      this.prevSlot = var1;
   }

   public final boolean getSwitchBack() {
      return this.switchBack;
   }

   public final void setSwitchBack(boolean var1) {
      this.switchBack = var1;
   }

   public void onEnable() {
      this.eating = -1;
      this.prevSlot = -1;
      this.switchBack = false;
      this.timer.reset();
      this.isDisable = false;
      this.tryHeal = false;
      this.delay = MathHelper.func_76136_a(new Random(), this.delayValue.get().getFirst(), this.delayValue.get().getLast());
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.isDisable = true;
      this.tryHeal = false;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (this.eating != -1 && packet instanceof C03PacketPlayer) {
         int var3 = this.eating++;
      } else if (packet instanceof S09PacketHeldItemChange || packet instanceof C09PacketHeldItemChange) {
         this.eating = -1;
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.tryHeal) {
         Intrinsics.checkNotNullExpressionValue(var4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var4) {
            case "legitauto":
               if (this.eating == -1) {
                  InventoryUtils var19 = InventoryUtils.INSTANCE;
                  Item var16 = Items.field_151153_ao;
                  Intrinsics.checkNotNullExpressionValue(var16, "golden_apple");
                  int gappleInHotbar = var19.findItem(36, 45, var16);
                  if (gappleInHotbar == -1) {
                     this.tryHeal = false;
                     return;
                  }

                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(gappleInHotbar - 36)));
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm())));
                  this.eating = 0;
               } else if (this.eating > 35) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                  this.timer.reset();
                  this.tryHeal = false;
                  this.delay = MathHelper.func_76136_a(new Random(), this.delayValue.get().getFirst(), this.delayValue.get().getLast());
               }
               break;
            case "auto":
               InventoryUtils var18 = InventoryUtils.INSTANCE;
               Item var14 = Items.field_151153_ao;
               Intrinsics.checkNotNullExpressionValue(var14, "golden_apple");
               int gappleInHotbar = var18.findItem(36, 45, var14);
               if (gappleInHotbar != -1) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(gappleInHotbar - 36)));
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm())));
                  byte var15 = 35;
                  int var5 = 0;

                  while(var5 < var15) {
                     ++var5;
                     int var8 = 0;
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E)));
                  }

                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                  if ((Boolean)this.AlertValue.get()) {
                     this.alert("Gapple eaten");
                  }

                  this.tryHeal = false;
                  this.timer.reset();
                  this.delay = MathHelper.func_76136_a(new Random(), this.delayValue.get().getFirst(), this.delayValue.get().getLast());
               } else {
                  this.tryHeal = false;
               }
               break;
            case "head":
               InventoryUtils var17 = InventoryUtils.INSTANCE;
               Item var13 = Items.field_151144_bL;
               Intrinsics.checkNotNullExpressionValue(var13, "skull");
               int headInHotbar = var17.findItem(36, 45, var13);
               if (headInHotbar != -1) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(headInHotbar - 36)));
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm())));
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
                  this.timer.reset();
                  this.tryHeal = false;
                  this.delay = MathHelper.func_76136_a(new Random(), this.delayValue.get().getFirst(), this.delayValue.get().getLast());
               } else {
                  this.tryHeal = false;
               }
               break;
            case "legit":
               if (this.eating == -1) {
                  InventoryUtils var10000 = InventoryUtils.INSTANCE;
                  Item var12 = Items.field_151153_ao;
                  Intrinsics.checkNotNullExpressionValue(var12, "golden_apple");
                  int gappleInHotbar = var10000.findItem(36, 45, var12);
                  if (gappleInHotbar == -1) {
                     this.tryHeal = false;
                     return;
                  }

                  if (this.prevSlot == -1) {
                     this.prevSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
                  }

                  MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = gappleInHotbar - 36;
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm())));
                  this.eating = 0;
               } else if (this.eating > 35) {
                  this.timer.reset();
                  this.tryHeal = false;
                  this.delay = MathHelper.func_76136_a(new Random(), this.delayValue.get().getFirst(), this.delayValue.get().getLast());
               }
         }
      }

      if (MinecraftInstance.mc.field_71439_g.field_70173_aa <= 10 && this.isDisable) {
         this.isDisable = false;
      }

      int absorp = MathHelper.func_76143_f((double)MinecraftInstance.mc.field_71439_g.func_110139_bj());
      if (!this.tryHeal && this.prevSlot != -1) {
         if (!this.switchBack) {
            this.switchBack = true;
            return;
         }

         MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = this.prevSlot;
         this.prevSlot = -1;
         this.switchBack = false;
      }

      if ((!(Boolean)this.groundCheck.get() || MinecraftInstance.mc.field_71439_g.field_70122_E) && (!(Boolean)this.invCheck.get() || !(MinecraftInstance.mc.field_71462_r instanceof GuiContainer)) && (absorp <= 0 || !(Boolean)this.absorpCheck.get())) {
         if (!(Boolean)this.waitRegen.get() || !MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76428_l) || !((float)MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76428_l).func_76459_b() > ((Number)this.regenSec.get()).floatValue() * 20.0F)) {
            if (!this.isDisable && MinecraftInstance.mc.field_71439_g.func_110143_aJ() <= ((Number)this.percent.get()).floatValue() / 100.0F * MinecraftInstance.mc.field_71439_g.func_110138_aP() && this.timer.hasTimePassed((long)this.delay)) {
               if (this.tryHeal) {
                  return;
               }

               this.tryHeal = true;
            }

         }
      }
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
