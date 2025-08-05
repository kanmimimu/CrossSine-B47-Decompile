package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "TimerRange",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0004H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\b\u0010\u001b\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020!H\u0007J\b\u0010\"\u001a\u00020\u0004H\u0002J\b\u0010#\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/TimerRange;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "confirmAttack", "", "confirmKnockback", "cooldownTick", "", "cooldownTickValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "playerTicks", "rangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "smartTick", "tag", "", "getTag", "()Ljava/lang/String;", "ticksValue", "timerBoostValue", "timerChargedValue", "isPlayerMoving", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "shouldResetTimer", "timerReset", "CrossSine"}
)
public final class TimerRange extends Module {
   private int playerTicks;
   private int smartTick;
   private int cooldownTick;
   private boolean confirmAttack;
   private boolean confirmKnockback;
   @NotNull
   private final IntegerValue ticksValue = new IntegerValue("Ticks", 10, 1, 20);
   @NotNull
   private final FloatValue timerBoostValue = new FloatValue("TimerBoost", 1.5F, 0.01F, 35.0F);
   @NotNull
   private final FloatValue timerChargedValue = new FloatValue("TimerCharged", 0.45F, 0.05F, 5.0F);
   @NotNull
   private final FloatValue rangeValue = new FloatValue("Range", 3.5F, 1.0F, 5.0F);
   @NotNull
   private final IntegerValue cooldownTickValue = new IntegerValue("CooldownTick", 10, 1, 50);

   @NotNull
   public String getTag() {
      return "" + ((Number)this.timerBoostValue.get()).floatValue() + 'x';
   }

   private final void timerReset() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   public void onEnable() {
      this.timerReset();
   }

   public void onDisable() {
      this.timerReset();
      this.smartTick = 0;
      this.cooldownTick = 0;
      this.playerTicks = 0;
      this.confirmAttack = false;
      this.confirmKnockback = false;
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.timerReset();
      this.smartTick = 0;
      this.cooldownTick = 0;
      this.playerTicks = 0;
      this.confirmAttack = false;
      this.confirmKnockback = false;
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getTargetEntity() instanceof EntityLivingBase && !this.shouldResetTimer()) {
         this.confirmAttack = true;
         Entity targetEntity = event.getTargetEntity();
         EntityPlayerSP var5 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var5, "mc.thePlayer");
         double entityDistance = EntityExtensionKt.getDistanceToEntityBox((Entity)var5, targetEntity);
         boolean shouldSlowed = (boolean)(this.smartTick++);
         shouldSlowed = (boolean)(this.cooldownTick++);
         shouldSlowed = this.cooldownTick >= ((Number)this.cooldownTickValue.get()).intValue() && entityDistance <= (double)((Number)this.rangeValue.get()).floatValue();
         if (shouldSlowed && this.confirmAttack) {
            this.confirmAttack = false;
            this.playerTicks = ((Number)this.ticksValue.get()).intValue();
            this.confirmKnockback = true;
            this.cooldownTick = 0;
            this.smartTick = 0;
         } else {
            this.timerReset();
         }

      } else {
         this.timerReset();
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      double timerboost = Random.Default.nextDouble((double)0.5F, 0.56);
      double charged = Random.Default.nextDouble((double)0.75F, 0.91);
      if (this.playerTicks <= 0) {
         this.timerReset();
      } else {
         double tickProgress = (double)this.playerTicks / (double)((Number)this.ticksValue.get()).intValue();
         float playerSpeed = tickProgress < timerboost ? ((Number)this.timerBoostValue.get()).floatValue() : (tickProgress < charged ? ((Number)this.timerChargedValue.get()).floatValue() : 1.0F);
         float speedAdjustment = playerSpeed >= 0.0F ? playerSpeed : 1.0F + ((Number)this.ticksValue.get()).floatValue() - (float)this.playerTicks;
         float var11 = 0.0F;
         float adjustedTimerSpeed = Math.max(speedAdjustment, var11);
         MinecraftInstance.mc.field_71428_T.field_74278_d = adjustedTimerSpeed;
         int var12 = this.playerTicks;
         this.playerTicks = var12 + -1;
      }
   }

   private final boolean isPlayerMoving() {
      return MinecraftInstance.mc.field_71439_g.field_70701_bs != 0.0F || MinecraftInstance.mc.field_71439_g.field_70702_br != 0.0F;
   }

   private final boolean shouldResetTimer() {
      return this.playerTicks >= 1 || MinecraftInstance.mc.field_71439_g.func_175149_v() || MinecraftInstance.mc.field_71439_g.field_70128_L || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.func_180799_ab() || MinecraftInstance.mc.field_71439_g.field_70134_J || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.func_70115_ae();
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if ((this.isPlayerMoving() && !this.shouldResetTimer() && (double)MinecraftInstance.mc.field_71428_T.field_74278_d > (double)1.0F || (double)MinecraftInstance.mc.field_71428_T.field_74278_d < (double)1.0F) && this.confirmKnockback && packet instanceof S12PacketEntityVelocity && MinecraftInstance.mc.field_71439_g.func_145782_y() == ((S12PacketEntityVelocity)packet).func_149412_c() && ((S12PacketEntityVelocity)packet).field_149416_c > 0 && ((double)((S12PacketEntityVelocity)packet).field_149415_b != (double)0.0F || (double)((S12PacketEntityVelocity)packet).field_149414_d != (double)0.0F)) {
         this.confirmKnockback = false;
         this.timerReset();
      }

   }
}
