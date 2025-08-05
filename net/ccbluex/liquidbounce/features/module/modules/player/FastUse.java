package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "FastUse",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\b\u0010\u001c\u001a\u00020\u0018H\u0002J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u0005H\u0002J\b\u0010\u001e\u001a\u00020\u0018H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FastUse;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "durationValue", "lastState", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "sentPacket", "tag", "", "getTag", "()Ljava/lang/String;", "timerValue", "", "usedTimer", "viaFixValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "send", "int", "stopUsing", "CrossSine"}
)
public final class FastUse extends Module {
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final Value timerValue;
   @NotNull
   private final Value durationValue;
   @NotNull
   private final Value delayValue;
   @NotNull
   private final BoolValue viaFixValue;
   @NotNull
   private final MSTimer msTimer;
   private boolean usedTimer;
   private boolean sentPacket;
   private boolean lastState;

   public FastUse() {
      String[] var1 = new String[]{"NCP", "Instant", "Timer", "CustomDelay", "DelayedInstant", "MinemoraTest", "AAC", "NewAAC", "Medusa", "Matrix", "Fast", "BlocksMC"};
      this.modeValue = new ListValue("Mode", var1, "DelayedInstant");
      this.timerValue = (new FloatValue("Timer", 1.22F, 0.1F, 2.0F)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return FastUse.this.modeValue.equals("Timer");
         }
      });
      this.durationValue = (new IntegerValue("InstantDelay", 14, 0, 35)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return FastUse.this.modeValue.equals("DelayedInstant");
         }
      });
      this.delayValue = (new IntegerValue("CustomDelay", 0, 0, 300)).displayable(new Function0() {
         @NotNull
         public final Boolean invoke() {
            return FastUse.this.modeValue.equals("CustomDelay");
         }
      });
      this.viaFixValue = new BoolValue("ViaVersion", false);
      this.msTimer = new MSTimer();
   }

   private final void send(int var1) {
      int var2 = 0;

      while(var2 < var1) {
         ++var2;
         int var5 = 0;
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E)));
      }

   }

   private final void send() {
      MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E)));
   }

   private final void stopUsing() {
      if ((Boolean)this.viaFixValue.get()) {
         this.sentPacket = true;
         if (MinecraftInstance.mc.field_71439_g.field_71072_f < 10) {
            MinecraftInstance.mc.field_71439_g.field_71072_f = 20;
         }
      } else {
         this.sentPacket = false;
         MinecraftInstance.mc.field_71442_b.func_78766_c((EntityPlayer)MinecraftInstance.mc.field_71439_g);
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.usedTimer) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         this.usedTimer = false;
      }

      if (!MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
         this.sentPacket = false;
         this.lastState = MinecraftInstance.mc.field_71439_g.func_71039_bw();
      } else {
         if (!this.lastState) {
            this.sentPacket = false;
         }

         this.lastState = MinecraftInstance.mc.field_71439_g.func_71039_bw();
         if ((Boolean)this.viaFixValue.get() && this.sentPacket) {
            if (MinecraftInstance.mc.field_71439_g.field_71072_f < 10) {
               MinecraftInstance.mc.field_71439_g.field_71072_f = 20;
            }

         } else {
            Item usingItem = MinecraftInstance.mc.field_71439_g.func_71011_bu().func_77973_b();
            if (usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || usingItem instanceof ItemPotion) {
               String var5 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var5.hashCode()) {
                  case -1773359950:
                     if (var5.equals("customdelay")) {
                        if (!this.msTimer.hasTimePassed((long)((Number)this.delayValue.get()).intValue())) {
                           return;
                        }

                        this.send();
                        this.msTimer.reset();
                     }
                     break;
                  case -1081239615:
                     if (var5.equals("matrix")) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5F;
                        this.usedTimer = true;
                        this.send();
                     }
                     break;
                  case -1078019017:
                     if (var5.equals("medusa")) {
                        if (MinecraftInstance.mc.field_71439_g.func_71057_bx() > 5 || !this.msTimer.hasTimePassed(360L)) {
                           return;
                        }

                        this.send(20);
                        this.msTimer.reset();
                     }
                     break;
                  case -1048843613:
                     if (var5.equals("newaac")) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.49F;
                        this.usedTimer = true;
                        this.send(2);
                     }
                     break;
                  case -1043820362:
                     if (var5.equals("minemoratest")) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5F;
                        this.usedTimer = true;
                        if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0) {
                           this.send(2);
                        }
                     }
                     break;
                  case -664563300:
                     if (var5.equals("blocksmc")) {
                        int var4 = 0;

                        while(var4 < 2) {
                           ++var4;
                           MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, MinecraftInstance.mc.field_71439_g.field_70122_E)));
                        }
                     }
                     break;
                  case -62406081:
                     if (var5.equals("delayedinstant") && MinecraftInstance.mc.field_71439_g.func_71057_bx() > ((Number)this.durationValue.get()).intValue()) {
                        this.send(35 - MinecraftInstance.mc.field_71439_g.func_71057_bx());
                        this.stopUsing();
                     }
                     break;
                  case 96323:
                     if (var5.equals("aac")) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.49F;
                        this.usedTimer = true;
                        if (MinecraftInstance.mc.field_71439_g.func_71057_bx() > 14) {
                           this.send(23);
                        }
                     }
                     break;
                  case 108891:
                     if (var5.equals("ncp") && MinecraftInstance.mc.field_71439_g.func_71057_bx() > 14) {
                        this.send(20);
                        this.stopUsing();
                     }
                     break;
                  case 3135580:
                     if (var5.equals("fast") && MinecraftInstance.mc.field_71439_g.func_71057_bx() < 25) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.3F;
                        this.usedTimer = true;
                        this.send(5);
                     }
                     break;
                  case 110364485:
                     if (var5.equals("timer")) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
                        this.usedTimer = true;
                     }
                     break;
                  case 1957570017:
                     if (var5.equals("instant")) {
                        this.send(35);
                        this.stopUsing();
                     }
               }

               if (MinecraftInstance.mc.field_71439_g.func_71057_bx() >= 30 && (Boolean)this.viaFixValue.get()) {
                  this.sentPacket = true;
                  if (MinecraftInstance.mc.field_71439_g.field_71072_f < 10) {
                     MinecraftInstance.mc.field_71439_g.field_71072_f = 20;
                  }
               }
            }

         }
      }
   }

   public void onDisable() {
      if (this.usedTimer) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         this.usedTimer = false;
      }

      this.sentPacket = false;
      this.lastState = MinecraftInstance.mc.field_71439_g.func_71039_bw();
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }
}
