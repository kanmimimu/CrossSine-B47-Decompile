package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
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
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AutoBot",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0002J\u0010\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\tH\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoBowValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoBowWaitForBowAimValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "autoPotDelayValue", "", "autoPotGroundDistanceValue", "", "autoPotHealthValue", "autoPotNotCombatValue", "autoPotOnGround", "autoPotOpenInventoryValue", "autoPotPot", "autoPotRegenValue", "autoPotSelectValue", "autoPotSimulateInventoryValue", "autoPotThrowAngle", "autoPotThrowAngleOption", "autoPotThrowMode", "", "autoPotThrowTickValue", "autoPotThrowTime", "autoPotThrowing", "autoPotUtilityValue", "autoPotValue", "autoSoupBowlValue", "autoSoupDelayValue", "autoSoupHealthValue", "autoSoupOpenInventoryValue", "autoSoupSimulateInventoryValue", "autoSoupTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoSoupValue", "autoPotFindPotion", "startSlot", "endSlot", "autoPotFindSinglePotion", "slot", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class AutoBot extends Module {
   @NotNull
   private final BoolValue autoSoupValue = new BoolValue("AutoSoup", true);
   @NotNull
   private final BoolValue autoPotValue = new BoolValue("AutoPot", true);
   @NotNull
   private final BoolValue autoBowValue = new BoolValue("AutoBow", true);
   @NotNull
   private final Value autoSoupHealthValue = (new FloatValue("Health", 15.0F, 0.0F, 20.0F)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)AutoBot.this.autoSoupValue.get();
      }
   });
   @NotNull
   private final Value autoSoupDelayValue = (new IntegerValue("Delay", 150, 0, 500)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)AutoBot.this.autoSoupValue.get();
      }
   });
   @NotNull
   private final Value autoSoupOpenInventoryValue = (new BoolValue("OpenInv", false)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)AutoBot.this.autoSoupValue.get();
      }
   });
   @NotNull
   private final Value autoSoupSimulateInventoryValue = (new BoolValue("SimulateInventory", true)).displayable(new Function0() {
      @NotNull
      public final Boolean invoke() {
         return (Boolean)AutoBot.this.autoSoupValue.get();
      }
   });
   @NotNull
   private final Value autoSoupBowlValue;
   @NotNull
   private final MSTimer autoSoupTimer;
   @NotNull
   private final Value autoPotThrowMode;
   @NotNull
   private final Value autoPotHealthValue;
   @NotNull
   private final Value autoPotDelayValue;
   @NotNull
   private final Value autoPotThrowTickValue;
   @NotNull
   private final Value autoPotSelectValue;
   @NotNull
   private final Value autoPotGroundDistanceValue;
   @NotNull
   private final Value autoPotThrowAngleOption;
   @NotNull
   private final Value autoPotOpenInventoryValue;
   @NotNull
   private final Value autoPotSimulateInventoryValue;
   @NotNull
   private final Value autoPotRegenValue;
   @NotNull
   private final Value autoPotUtilityValue;
   @NotNull
   private final Value autoPotNotCombatValue;
   @NotNull
   private final Value autoPotOnGround;
   private boolean autoPotThrowing;
   private int autoPotThrowTime;
   private int autoPotPot;
   private float autoPotThrowAngle;
   @NotNull
   private final Value autoBowWaitForBowAimValue;

   public AutoBot() {
      String[] var1 = new String[]{"Drop", "Move", "Stay"};
      this.autoSoupBowlValue = (new ListValue("Bowl", var1, "Drop")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoSoupValue.get();
         }
      });
      this.autoSoupTimer = new MSTimer();
      var1 = new String[]{"Up", "Forward", "Down", "Custom"};
      this.autoPotThrowMode = (new ListValue("AutoPot-ThrowMode", var1, "Up")).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotHealthValue = (new FloatValue("AutoPot-Health", 15.0F, 1.0F, 20.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotDelayValue = (new IntegerValue("AutoPot-Delay", 500, 500, 1000)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotThrowTickValue = (new IntegerValue("AutoPot-ThrowTick", 3, 1, 10)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotSelectValue = (new IntegerValue("AutoPot-SelectSlot", -1, -1, 9)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotGroundDistanceValue = (new FloatValue("AutoPot-GroundDistance", 2.0F, 0.0F, 4.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get() && !(Boolean)AutoBot.this.autoPotOnGround.get();
         }
      });
      this.autoPotThrowAngleOption = (new IntegerValue("AutoPot-ThrowAngle", -45, -90, 90)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return AutoBot.this.autoPotThrowMode.equals("Custom") && (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotOpenInventoryValue = (new BoolValue("AutoPot-OpenInv", false)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotSimulateInventoryValue = (new BoolValue("AutoPot-SimulateInventory", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotRegenValue = (new BoolValue("AutoPot-Regen", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotUtilityValue = (new BoolValue("AutoPot-Utility", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotNotCombatValue = (new BoolValue("AutoPot-NotCombat", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotOnGround = (new BoolValue("AutoPot-OnGround", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoPotValue.get();
         }
      });
      this.autoPotPot = -1;
      this.autoBowWaitForBowAimValue = (new BoolValue("AutoBow-WaitForBowAimBot", true)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return (Boolean)AutoBot.this.autoBowValue.get();
         }
      });
   }

   private final int autoPotFindPotion(int startSlot, int endSlot) {
      int var3 = startSlot;

      while(var3 < endSlot) {
         int i = var3++;
         if (this.autoPotFindSinglePotion(i)) {
            return i;
         }
      }

      return -1;
   }

   private final boolean autoPotFindSinglePotion(int slot) {
      ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(slot).func_75211_c();
      if (stack != null && stack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(stack.func_77952_i())) {
         Item var10000 = stack.func_77973_b();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
         } else {
            ItemPotion itemPotion = (ItemPotion)var10000;
            if (MinecraftInstance.mc.field_71439_g.func_110143_aJ() < ((Number)this.autoPotHealthValue.get()).floatValue() && (Boolean)this.autoPotRegenValue.get()) {
               for(PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                  if (potionEffect.func_76456_a() == Potion.field_76432_h.field_76415_H) {
                     return true;
                  }
               }

               if (!MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76428_l)) {
                  for(PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                     if (potionEffect.func_76456_a() == Potion.field_76428_l.field_76415_H) {
                        return true;
                     }
                  }
               }
            } else if ((Boolean)this.autoPotUtilityValue.get()) {
               for(PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                  if (potionEffect.func_76456_a() != Potion.field_76432_h.field_76415_H && InventoryUtils.INSTANCE.isPositivePotionEffect(potionEffect.func_76456_a()) && !MinecraftInstance.mc.field_71439_g.func_82165_m(potionEffect.func_76456_a())) {
                     return true;
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.autoSoupValue.get()) {
         if (!this.autoSoupTimer.hasTimePassed((long)((Number)this.autoSoupDelayValue.get()).intValue())) {
            return;
         }

         InventoryUtils var10000 = InventoryUtils.INSTANCE;
         Item var3 = Items.field_151009_A;
         Intrinsics.checkNotNullExpressionValue(var3, "mushroom_stew");
         int soupInHotbar = var10000.findItem(36, 45, var3);
         if (MinecraftInstance.mc.field_71439_g.func_110143_aJ() <= ((Number)this.autoSoupHealthValue.get()).floatValue() && soupInHotbar != -1) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(soupInHotbar - 36)));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(soupInHotbar).func_75211_c())));
            if (this.autoSoupBowlValue.equals("Drop")) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.DROP_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN)));
            }

            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
            this.autoSoupTimer.reset();
            return;
         }

         var10000 = InventoryUtils.INSTANCE;
         Item var4 = Items.field_151054_z;
         Intrinsics.checkNotNullExpressionValue(var4, "bowl");
         int bowlInHotbar = var10000.findItem(36, 45, var4);
         if (this.autoSoupBowlValue.equals("Move") && bowlInHotbar != -1) {
            if ((Boolean)this.autoSoupOpenInventoryValue.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
               return;
            }

            boolean bowlMovable = false;
            boolean openInventory = (boolean)9;

            while(openInventory < 37) {
               int i = openInventory++;
               ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
               if (itemStack == null) {
                  bowlMovable = true;
                  break;
               }

               if (Intrinsics.areEqual((Object)itemStack.func_77973_b(), (Object)Items.field_151054_z) && itemStack.field_77994_a < 64) {
                  bowlMovable = true;
                  break;
               }
            }

            if (bowlMovable) {
               openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && (Boolean)this.autoSoupSimulateInventoryValue.get();
               if (openInventory) {
                  MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
               }

               MinecraftInstance.mc.field_71442_b.func_78753_a(0, bowlInHotbar, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            }
         }

         var10000 = InventoryUtils.INSTANCE;
         Item var21 = Items.field_151009_A;
         Intrinsics.checkNotNullExpressionValue(var21, "mushroom_stew");
         int soupInInventory = var10000.findItem(9, 36, var21);
         if (soupInInventory != -1 && InventoryUtils.INSTANCE.hasSpaceHotbar()) {
            if ((Boolean)this.autoSoupOpenInventoryValue.get() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
               return;
            }

            boolean openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && (Boolean)this.autoSoupSimulateInventoryValue.get();
            if (openInventory) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
            }

            MinecraftInstance.mc.field_71442_b.func_78753_a(0, soupInInventory, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            if (openInventory) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0DPacketCloseWindow()));
            }

            this.autoSoupTimer.reset();
         }
      }

      if ((Boolean)this.autoPotValue.get()) {
         Intrinsics.checkNotNullExpressionValue(var18, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var18) {
            case "custom":
               this.autoPotThrowAngle = (float)((Number)this.autoPotThrowAngleOption.get()).intValue();
               break;
            case "forward":
               this.autoPotThrowAngle = 0.0F;
               break;
            case "up":
               this.autoPotThrowAngle = -90.0F;
               break;
            case "down":
               this.autoPotThrowAngle = 90.0F;
         }

         if ((Boolean)this.autoPotNotCombatValue.get() && CrossSine.INSTANCE.getCombatManager().getInCombat()) {
            return;
         }

         if ((Boolean)this.autoPotOnGround.get() && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
            return;
         }

         if (!(Boolean)this.autoPotOnGround.get()) {
            FallingPlayer fallingPlayer = new FallingPlayer(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70159_w, MinecraftInstance.mc.field_71439_g.field_70181_x, MinecraftInstance.mc.field_71439_g.field_70179_y, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70702_br, MinecraftInstance.mc.field_71439_g.field_70701_bs, MinecraftInstance.mc.field_71439_g.field_70747_aH);
            BlockPos collisionBlock = fallingPlayer.findCollision(20);
            double var29 = MinecraftInstance.mc.field_71439_g.field_70163_u;
            int var10001;
            if (collisionBlock == null) {
               var10001 = 0;
            } else {
               int var23 = collisionBlock.func_177956_o();
               var10001 = var23;
            }

            if (var29 - (double)var10001 >= (double)(((Number)this.autoPotGroundDistanceValue.get()).floatValue() + 1.01F)) {
               return;
            }
         }

         if (this.autoPotThrowing) {
            int var12 = this.autoPotThrowTime++;
            RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, this.autoPotThrowAngle), 0);
            if (this.autoPotThrowTime == ((Number)this.autoPotThrowTickValue.get()).intValue()) {
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(this.autoPotPot - 36)));
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm())));
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c)));
               this.autoPotPot = -1;
            }

            if (this.autoPotThrowTime >= ((Number)this.autoPotThrowTickValue.get()).intValue() * 2) {
               this.autoPotThrowTime = 0;
               this.autoPotThrowing = false;
            }

            return;
         }

         if (!InventoryUtils.INSTANCE.getINV_TIMER().hasTimePassed((long)((Number)this.autoPotDelayValue.get()).intValue())) {
            return;
         }

         boolean enableSelect = ((Number)this.autoPotSelectValue.get()).intValue() != -1;
         int potion = enableSelect ? (this.autoPotFindSinglePotion(36 + ((Number)this.autoPotSelectValue.get()).intValue()) ? 36 + ((Number)this.autoPotSelectValue.get()).intValue() : -1) : this.autoPotFindPotion(36, 45);
         if (potion != -1) {
            RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, this.autoPotThrowAngle), 0);
            this.autoPotPot = potion;
            this.autoPotThrowing = true;
            InventoryUtils.INSTANCE.getINV_TIMER().reset();
            return;
         }

         if ((Boolean)this.autoPotOpenInventoryValue.get() && !enableSelect) {
            int invPotion = this.autoPotFindPotion(9, 36);
            if (invPotion != -1) {
               boolean openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && (Boolean)this.autoPotSimulateInventoryValue.get();
               if (InventoryUtils.INSTANCE.hasSpaceHotbar()) {
                  if (openInventory) {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
                  }

                  MinecraftInstance.mc.field_71442_b.func_78753_a(0, invPotion, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                  if (openInventory) {
                     MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0DPacketCloseWindow()));
                  }

                  return;
               }

               int var25 = 36;

               while(var25 < 45) {
                  int i = var25++;
                  ItemStack stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
                  if (stack != null && stack.func_77973_b() instanceof ItemPotion && ItemPotion.func_77831_g(stack.func_77952_i())) {
                     if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
                     }

                     MinecraftInstance.mc.field_71442_b.func_78753_a(0, invPotion, 0, 0, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                     MinecraftInstance.mc.field_71442_b.func_78753_a(0, i, 0, 0, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                     if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0DPacketCloseWindow()));
                     }
                     break;
                  }
               }
            }
         }
      }

      if ((Boolean)this.autoBowValue.get()) {
         Module var30 = CrossSine.INSTANCE.getModuleManager().get(BowAimbot.class);
         Intrinsics.checkNotNull(var30);
         BowAimbot bowAimbot = (BowAimbot)var30;
         if (MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
            ItemStack var31 = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            if (Intrinsics.areEqual((Object)(var31 == null ? null : var31.func_77973_b()), (Object)Items.field_151031_f) && MinecraftInstance.mc.field_71439_g.func_71057_bx() > 20 && (!(Boolean)this.autoBowWaitForBowAimValue.get() || !bowAimbot.getState() || bowAimbot.hasTarget())) {
               MinecraftInstance.mc.field_71439_g.func_71034_by();
               MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN)));
            }
         }
      }

   }
}
