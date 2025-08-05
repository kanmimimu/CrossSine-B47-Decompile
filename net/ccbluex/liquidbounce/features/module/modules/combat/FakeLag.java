package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.value.FloatRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "FakeLag",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001aH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0007J\b\u0010\"\u001a\u00020\u001fH\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020(H\u0007J\b\u0010)\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/FakeLag;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackTick", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "freeze", "freezeMS", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "hurtTime", "lagged", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "tag", "", "getTag", "()Ljava/lang/String;", "target", "Lnet/minecraft/entity/player/EntityPlayer;", "targetPos", "Lnet/minecraft/util/Vec3;", "findTarget", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "reset", "CrossSine"}
)
public final class FakeLag extends Module {
   @NotNull
   public static final FakeLag INSTANCE = new FakeLag();
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final IntegerValue freezeMS;
   @NotNull
   private static final Value rangeValue;
   private static boolean lagged;
   @NotNull
   private static TimerMS freeze;
   @Nullable
   private static EntityPlayer target;
   @Nullable
   private static Vec3 targetPos;
   private static int hurtTime;
   private static int attackTick;
   @NotNull
   private static final TimerMS attackTimer;

   private FakeLag() {
   }

   @NotNull
   public final ListValue getModeValue() {
      return modeValue;
   }

   public void onDisable() {
      if (!Blink.INSTANCE.getState()) {
         this.reset();
         BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
      }

   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      EntityPlayer target = (EntityPlayer)event.getTargetEntity();
      if (!Intrinsics.areEqual((Object)target, (Object)FakeLag.target)) {
         FakeLag.target = target;
         hurtTime = 10;
      }

      attackTick = 0;
      if (hurtTime == 0) {
         hurtTime = 10;
      }

      attackTimer.reset();
      if (modeValue.equals("DelayAttack") && (double)MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)FakeLag.target) >= 3.2 && !lagged) {
         lagged = true;
         freeze.reset();
      }

   }

   @EventTarget
   public final void onPreUpdate(@NotNull PreUpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!Blink.INSTANCE.getState()) {
         int var2 = attackTick++;
         if (target == null) {
            target = this.findTarget();
         }

         if (modeValue.equals("Dynamic")) {
            if (target != null && attackTick > 0) {
               if (freeze.hasTimePassed((long)((Number)freezeMS.get()).intValue()) && lagged) {
                  lagged = false;
                  BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                  target = null;
                  freeze.reset();
               }

               float distance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target);
               float var3 = ((Number)((ClosedFloatingPointRange)rangeValue.get()).getStart()).floatValue();
               if ((distance <= ((Number)((ClosedFloatingPointRange)rangeValue.get()).getEndInclusive()).floatValue() ? var3 <= distance : false) && !lagged) {
                  lagged = true;
                  BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
                  freeze.reset();
               }
            } else if (lagged || attackTick == 0) {
               BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
               lagged = false;
            }
         } else if (modeValue.equals("Normal")) {
            if (attackTimer.hasTimePassed(500L) && !freeze.hasTimePassed((long)((Number)freezeMS.get()).intValue()) && (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFood) && (!(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPotion) || ItemPotion.func_77831_g(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77960_j())) && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBucketMilk) || !MinecraftInstance.mc.field_71439_g.func_71039_bw())) {
               BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
            } else {
               BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
               freeze.reset();
            }
         } else if (modeValue.equals("DelayAttack")) {
            if (lagged) {
               if (!freeze.hasTimePassed((long)((Number)freezeMS.get()).intValue())) {
                  BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, (Object)null);
               } else {
                  BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
                  lagged = false;
               }
            }

            if ((double)MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target) < (double)2.5F && lagged) {
               BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
               lagged = false;
            }
         }

      }
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.reset();
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Packet packet = event.getPacket();
      if (packet instanceof S08PacketPlayerPosLook) {
         this.reset();
      }

      if (!event.isCancelled()) {
         String var3 = packet.getClass().getSimpleName();
         Intrinsics.checkNotNullExpressionValue(var3, "packet.javaClass.simpleName");
         if (StringsKt.startsWith(var3, "S", true)) {
            if (packet instanceof S14PacketEntity) {
               if (target != null) {
                  int var10000 = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e).func_145782_y();
                  EntityPlayer var10001 = target;
                  Intrinsics.checkNotNull(var10001);
                  if (var10000 == var10001.func_145782_y()) {
                     Vec3 var4 = targetPos;
                     Intrinsics.checkNotNull(var4);
                     targetPos = var4.func_72441_c((double)((S14PacketEntity)packet).func_149062_c() / (double)32.0F, (double)((S14PacketEntity)packet).func_149061_d() / (double)32.0F, (double)((S14PacketEntity)packet).func_149064_e() / (double)32.0F);
                  }
               }
            } else if (packet instanceof S18PacketEntityTeleport && target != null) {
               int var5 = ((S18PacketEntityTeleport)packet).func_149451_c();
               EntityPlayer var6 = target;
               Intrinsics.checkNotNull(var6);
               if (var5 == var6.func_145782_y()) {
                  targetPos = new Vec3((double)((S18PacketEntityTeleport)packet).func_149449_d() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149448_e() / (double)32.0F, (double)((S18PacketEntityTeleport)packet).func_149446_f() / (double)32.0F);
               }
            }
         }
      }

      if (packet instanceof S08PacketPlayerPosLook) {
         this.reset();
      }

   }

   private final void reset() {
      lagged = false;
      target = this.findTarget();
      BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, (Object)null);
      targetPos = null;
      freeze.reset();
   }

   private final EntityPlayer findTarget() {
      float closetDistance = Float.MAX_VALUE;
      EntityPlayer target = null;

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         EntityUtils var10000 = EntityUtils.INSTANCE;
         Intrinsics.checkNotNullExpressionValue(entity, "entity");
         if (var10000.isSelected(entity, true)) {
            float distance = MinecraftInstance.mc.field_71439_g.func_70032_d(entity);
            if (!Intrinsics.areEqual((Object)entity, (Object)MinecraftInstance.mc.field_71439_g) && entity instanceof EntityPlayer && distance < closetDistance) {
               closetDistance = distance;
               target = (EntityPlayer)entity;
            }
         }
      }

      return target;
   }

   @NotNull
   public String getTag() {
      return (lagged ? "§C " : "") + (String)modeValue.get() + ' ' + ((Number)freezeMS.get()).intValue() + "MS";
   }

   static {
      String[] var0 = new String[]{"Dynamic", "Normal", "DelayAttack"};
      modeValue = new ListValue("Mode", var0, "Dynamic");
      freezeMS = new IntegerValue("FreezeMS", 350, 100, 500);
      rangeValue = (new FloatRangeValue("Range", 3.5F, 6.0F, 1.0F, 7.0F)).displayable(null.INSTANCE);
      freeze = new TimerMS();
      attackTimer = new TimerMS();
   }
}
