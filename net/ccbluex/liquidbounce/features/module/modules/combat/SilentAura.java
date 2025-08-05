package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerRangeValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.WorldSettings.GameType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "SilentAura",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010M\u001a\u00020NH\u0002J\u0006\u0010O\u001a\u00020PJ\u0010\u0010Q\u001a\u00020\u00072\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010H\u001a\u00020NH\u0002J\u0010\u0010R\u001a\u00020N2\u0006\u0010S\u001a\u00020TH\u0007J\b\u0010U\u001a\u00020NH\u0016J\b\u0010V\u001a\u00020NH\u0016J\u0010\u0010W\u001a\u00020N2\u0006\u0010S\u001a\u00020XH\u0007J\u0010\u0010Y\u001a\u00020N2\u0006\u0010S\u001a\u00020ZH\u0007J\u0010\u0010[\u001a\u00020N2\u0006\u0010S\u001a\u00020\\H\u0007J\b\u0010]\u001a\u00020NH\u0002J\b\u0010^\u001a\u00020NH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010'\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020)0(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00101\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\r0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u00109\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\u001fR\u000e\u0010;\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010@\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020BX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u000203X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010D\u001a\u0004\u0018\u00010>8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010FR\u001c\u0010G\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0014\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006_"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/SilentAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LastBlock", "", "alwaysBlock", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "attackMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "attacked", "autoBlock", "autoBlockTime", "", "blockDelay", "blockRangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "blockTime", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "blockingStatus", "bypassTick", "canBlock", "getCanBlock", "()Z", "canSwing", "cancelAttack", "getCancelAttack", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerRangeValue;", "discoverValue", "getDiscoverValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "discoveredTargets", "", "Lnet/minecraft/entity/EntityLivingBase;", "entity", "Lnet/minecraft/entity/Entity;", "fovValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getAABB", "Lkotlin/Function1;", "Lnet/minecraft/util/AxisAlignedBB;", "hitable", "hitableCheck", "hurtTime", "inRangeDiscoveredTargets", "lastCanBeSeen", "leftDelay", "leftLastSwing", "lessFix", "markValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "movementFix", "onlyClick", "onlyWeapon", "prevTargetEntities", "random", "reachValue", "getReachValue", "rotationSpeed", "scaffoldCheck", "swingMode", "", "swingRange", "switchDelay", "switchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "switchValue", "tag", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "whileMoving", "bypassBlock", "", "getReach", "", "getRot", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onEnable", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "stopBlocking", "updateHitable", "CrossSine"}
)
public final class SilentAura extends Module {
   @NotNull
   public static final SilentAura INSTANCE = new SilentAura();
   @NotNull
   private static final BoolValue onlyWeapon = new BoolValue("Only-Weapon", true);
   @NotNull
   private static final BoolValue onlyClick = new BoolValue("Mouse-Down", false);
   @NotNull
   private static final ListValue attackMode;
   @NotNull
   private static final Value hitableCheck;
   @NotNull
   private static final Value swingMode;
   @NotNull
   private static final ListValue autoBlock;
   @NotNull
   private static final Value whileMoving;
   @NotNull
   private static final Value alwaysBlock;
   @NotNull
   private static final Value bypassTick;
   @NotNull
   private static final Value autoBlockTime;
   @NotNull
   private static final BoolValue switchValue;
   @NotNull
   private static final BoolValue scaffoldCheck;
   @NotNull
   private static final BoolValue markValue;
   @NotNull
   private static final BoolValue movementFix;
   @NotNull
   private static final Value lessFix;
   @NotNull
   private static final Value switchDelay;
   @NotNull
   private static final FloatValue reachValue;
   @NotNull
   private static final FloatValue swingRange;
   @NotNull
   private static final FloatValue discoverValue;
   @NotNull
   private static final FloatValue blockRangeValue;
   @NotNull
   private static final IntegerValue fovValue;
   @NotNull
   private static final IntegerRangeValue cpsValue;
   @NotNull
   private static final IntegerRangeValue rotationSpeed;
   @NotNull
   private static final FloatValue random;
   @Nullable
   private static Entity entity;
   private static long leftDelay;
   private static long leftLastSwing;
   @NotNull
   private static final MSTimer switchTimer;
   @Nullable
   private static EntityLivingBase target;
   @NotNull
   private static final List prevTargetEntities;
   @NotNull
   private static final List discoveredTargets;
   @NotNull
   private static final List inRangeDiscoveredTargets;
   private static boolean lastCanBeSeen;
   private static long blockDelay;
   private static long LastBlock;
   private static boolean blockingStatus;
   @NotNull
   private static TimerMS blockTime;
   private static boolean attacked;
   private static int hurtTime;
   private static boolean canSwing;
   private static boolean hitable;
   @NotNull
   private static final Function1 getAABB;

   private SilentAura() {
   }

   @NotNull
   public final FloatValue getReachValue() {
      return reachValue;
   }

   @NotNull
   public final FloatValue getDiscoverValue() {
      return discoverValue;
   }

   @Nullable
   public final EntityLivingBase getTarget() {
      return target;
   }

   public final void setTarget(@Nullable EntityLivingBase var1) {
      target = var1;
   }

   private final boolean getCancelAttack() {
      return (Boolean)onlyClick.get() && !MinecraftInstance.mc.field_71474_y.field_74312_F.field_74513_e || (Boolean)onlyWeapon.get() && (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword) && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPickaxe) && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemAxe)) || (Boolean)scaffoldCheck.get() && Scaffold.INSTANCE.getState();
   }

   public final boolean getCanBlock() {
      return ((Boolean)alwaysBlock.get() || autoBlock.equals("Fake")) && blockingStatus;
   }

   public void onDisable() {
      blockingStatus = false;
      entity = null;
      target = null;
      this.stopBlocking();
      prevTargetEntities.clear();
      discoveredTargets.clear();
      inRangeDiscoveredTargets.clear();
      hurtTime = 0;
      MouseUtils.INSTANCE.setLeftClicked(false);
   }

   public void onEnable() {
      entity = null;
      lastCanBeSeen = false;
      blockingStatus = false;
      this.getTarget();
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      blockTime.reset();
      attacked = true;
      if (hurtTime == 0) {
         hurtTime = 10;
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getTarget();
      if (target != null) {
         MovementFix.INSTANCE.applyForceStrafe((Boolean)lessFix.get(), (Boolean)movementFix.get());
      }

   }

   @EventTarget
   public final void onPreUpdate(@NotNull PreUpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getCancelAttack()) {
         discoveredTargets.clear();
         inRangeDiscoveredTargets.clear();
         target = null;
      }

      this.updateHitable();
      this.getTarget();
      if (hurtTime > 0) {
         hurtTime += -1;
         int var10000 = hurtTime;
      }

      EntityUtils var4 = EntityUtils.INSTANCE;
      EntityLivingBase var10001 = target;
      Intrinsics.checkNotNull(var10001);
      if (var4.isSelected((Entity)var10001, true)) {
         if (attackMode.equals("Legit")) {
            if (target != null && !this.getCancelAttack()) {
               if (System.currentTimeMillis() - leftLastSwing >= leftDelay && (!HitSelect.INSTANCE.getState() || HitSelect.INSTANCE.getMode().equals("Active") || !HitSelect.INSTANCE.getCancelClick())) {
                  if (canSwing) {
                     KeyBinding.func_74507_a(MinecraftInstance.mc.field_71474_y.field_74312_F.func_151463_i());
                     MouseUtils.INSTANCE.setLeftClicked(true);
                  }

                  leftLastSwing = System.currentTimeMillis();
                  leftDelay = TimeUtils.INSTANCE.randomClickDelay(cpsValue.get().getFirst(), cpsValue.get().getLast());
               } else {
                  MouseUtils.INSTANCE.setLeftClicked(false);
               }
            }
         } else if (target != null && !this.getCancelAttack()) {
            if (System.currentTimeMillis() - leftLastSwing >= leftDelay && (!HitSelect.INSTANCE.getState() || !HitSelect.INSTANCE.getCancelClick())) {
               if (ProtocolFixer.newerThan1_8()) {
                  if (hitable) {
                     EntityLivingBase var10002 = target;
                     Intrinsics.checkNotNull(var10002);
                     AttackEvent attackEvent = new AttackEvent((Entity)var10002);
                     CrossSine.INSTANCE.getEventManager().callEvent(attackEvent);
                     if (!attackEvent.isCancelled()) {
                        MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, entity);
                        CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
                        if (MinecraftInstance.mc.field_71442_b.field_78779_k != GameType.SPECTATOR) {
                           MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)target);
                        }
                     }
                  }

                  if (canSwing) {
                     if (swingMode.equals("ClientSide")) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                     } else if (swingMode.equals("ServerSide")) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                     }

                     MouseUtils.INSTANCE.setLeftClicked(true);
                  }
               } else {
                  if (canSwing) {
                     if (swingMode.equals("ClientSide")) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                     } else if (swingMode.equals("ServerSide")) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C0APacketAnimation()));
                     }

                     MouseUtils.INSTANCE.setLeftClicked(true);
                  }

                  if (hitable) {
                     EntityLivingBase var9 = target;
                     Intrinsics.checkNotNull(var9);
                     AttackEvent attackEvent = new AttackEvent((Entity)var9);
                     CrossSine.INSTANCE.getEventManager().callEvent(attackEvent);
                     if (!attackEvent.isCancelled()) {
                        MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, entity);
                        CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
                        if (MinecraftInstance.mc.field_71442_b.field_78779_k != GameType.SPECTATOR) {
                           MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)target);
                        }
                     }
                  }
               }

               leftLastSwing = System.currentTimeMillis();
               leftDelay = TimeUtils.INSTANCE.randomClickDelay(cpsValue.get().getFirst(), cpsValue.get().getLast());
            } else if (System.currentTimeMillis() - leftLastSwing >= 30L) {
               MouseUtils.INSTANCE.setLeftClicked(false);
            }
         }
      }

      if ((Boolean)switchValue.get()) {
         if (switchTimer.hasTimePassed((long)((Number)switchDelay.get()).intValue())) {
            List var5 = prevTargetEntities;
            var10001 = target;
            Intrinsics.checkNotNull(var10001);
            var5.add(var10001.func_145782_y());
            switchTimer.reset();
         }
      } else {
         List var6 = prevTargetEntities;
         var10001 = target;
         Intrinsics.checkNotNull(var10001);
         var6.add(var10001.func_145782_y());
      }

      if (target == null || discoveredTargets.isEmpty()) {
         this.stopBlocking();
      }
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (target != null) {
         if ((Boolean)markValue.get() && !((Collection)discoveredTargets).isEmpty()) {
            KillAura var10000 = KillAura.INSTANCE;
            EntityLivingBase var10001 = target;
            Intrinsics.checkNotNull(var10001);
            var10000.draw(var10001, event);
            GlStateManager.func_179117_G();
         }

         EntityUtils var4 = EntityUtils.INSTANCE;
         EntityLivingBase var5 = target;
         Intrinsics.checkNotNull(var5);
         if (var4.isSelected((Entity)var5, true) && target != null && MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)target) <= (attackMode.equals("Legit") ? ((Number)blockRangeValue.get()).floatValue() : ((Number)reachValue.get()).floatValue())) {
            if (!autoBlock.equals("None") && MinecraftInstance.mc.field_71439_g.func_70632_aY() && attackMode.equals("Legit")) {
               PlayerUtils.INSTANCE.swing();
            }

            if (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword) {
               String var3 = ((String)autoBlock.get()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
               switch (var3.hashCode()) {
                  case -1374130968:
                     if (var3.equals("bypass")) {
                        this.bypassBlock();
                     }
                     break;
                  case 3135317:
                     if (var3.equals("fake")) {
                        blockingStatus = true;
                     }
                     break;
                  case 3560141:
                     if (var3.equals("time") && attacked) {
                        blockingStatus = true;
                        MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = true;
                        MouseUtils.INSTANCE.setRightClicked(true);
                        if (discoveredTargets.isEmpty() || blockTime.hasTimePassed((long)((Number)autoBlockTime.get()).intValue())) {
                           attacked = false;
                           MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G));
                           MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G);
                        }
                     }
                     break;
                  case 701808476:
                     if (var3.equals("hurttime")) {
                        if (((Boolean)whileMoving.get() || MovementUtils.INSTANCE.isMoving()) && !MovementUtils.INSTANCE.isMoving()) {
                           this.stopBlocking();
                        } else {
                           MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = hurtTime > 1;
                           MouseUtils.INSTANCE.setRightClicked(hurtTime > 1);
                           blockingStatus = true;
                        }
                     }
               }
            }
         }

      }
   }

   private final void getTarget() {
      int fov = ((Number)fovValue.get()).intValue();
      boolean switchMode = (Boolean)switchValue.get();
      discoveredTargets.clear();

      for(Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
         if (entity instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(entity, true) && (!switchMode || !prevTargetEntities.contains(((EntityLivingBase)entity).func_145782_y()))) {
            EntityPlayerSP var7 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(var7, "mc.thePlayer");
            double distance = EntityExtensionKt.getDistanceToEntityBox((Entity)var7, entity);
            double entityFov = RotationUtils.getRotationDifference(entity);
            if (distance <= (double)((Number)discoverValue.get()).floatValue() && (fov == 180 || entityFov <= (double)fov)) {
               discoveredTargets.add(entity);
            }
         }
      }

      List $this$sortBy$iv = discoveredTargets;
      int $i$f$sortBy = 0;
      if ($this$sortBy$iv.size() > 1) {
         CollectionsKt.sortWith($this$sortBy$iv, new SilentAura$getTarget$$inlined$sortBy$1());
      }

      inRangeDiscoveredTargets.clear();
      Iterable var15 = (Iterable)discoveredTargets;
      List var13 = inRangeDiscoveredTargets;
      $i$f$sortBy = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : var15) {
         EntityLivingBase it = (EntityLivingBase)element$iv$iv;
         int var11 = 0;
         EntityPlayerSP var12 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var12, "mc.thePlayer");
         if (EntityExtensionKt.getDistanceToEntityBox((Entity)var12, (Entity)it) < (double)3.0F) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      var13.addAll((Collection)((List)destination$iv$iv));
      if (inRangeDiscoveredTargets.isEmpty() && !((Collection)prevTargetEntities).isEmpty()) {
         prevTargetEntities.clear();
         this.getTarget();
      } else {
         for(EntityLivingBase entity : discoveredTargets) {
            if (!this.getRot((Entity)entity)) {
               boolean success = false;
            } else {
               EntityPlayerSP success = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNullExpressionValue(success, "mc.thePlayer");
               if (EntityExtensionKt.getDistanceToEntityBox((Entity)success, (Entity)entity) < (double)((Number)discoverValue.get()).floatValue()) {
                  target = entity;
                  TargetStrafe var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var10000);
                  var10000 = var10000;
                  EntityLivingBase var10001 = target;
                  if (var10001 == null) {
                     return;
                  }

                  var10000.setTargetEntity(var10001);
                  var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var10000);
                  var10000 = var10000;
                  Module var27 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
                  Intrinsics.checkNotNull(var27);
                  var10000.setDoStrafe(((TargetStrafe)var27).toggleStrafe());
                  return;
               }
            }
         }

         target = null;
         this.stopBlocking();
      }
   }

   private final void updateHitable() {
      if (target == null) {
         canSwing = false;
         hitable = false;
      } else {
         EntityPlayerSP var3 = MinecraftInstance.mc.field_71439_g;
         Intrinsics.checkNotNullExpressionValue(var3, "mc.thePlayer");
         Entity var10000 = (Entity)var3;
         EntityLivingBase var10001 = target;
         if (var10001 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
         } else {
            double entityDist = EntityExtensionKt.getDistanceToEntityBox(var10000, (Entity)var10001);
            canSwing = entityDist <= (double)((Number)swingRange.get()).floatValue();
            if ((Boolean)hitableCheck.get() && attackMode.equals("Packet")) {
               hitable = entityDist <= (double)((Number)reachValue.get()).floatValue();
            } else if ((float)rotationSpeed.get().getLast() <= 0.0F) {
               hitable = true;
            } else {
               EntityPlayerSP var4 = MinecraftInstance.mc.field_71439_g;
               Intrinsics.checkNotNullExpressionValue(var4, "mc.thePlayer");
               MovingObjectPosition wallTrace = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)var4, entityDist);
               hitable = RotationUtils.isFaced((Entity)target, (double)((Number)reachValue.get()).floatValue()) && (entityDist < (double)((Number)discoverValue.get()).floatValue() || (wallTrace == null ? null : wallTrace.field_72313_a) != MovingObjectType.BLOCK);
            }
         }
      }
   }

   private final boolean getRot(Entity entity) {
      if (this.getCancelAttack()) {
         return false;
      } else {
         double entityFov = RotationUtils.getRotationDifference(RotationUtils.toRotation(RotationUtils.getCenter(EntityExtensionKt.getHitBox(entity)), true), RotationUtils.serverRotation);
         if (entityFov <= (double)MinecraftInstance.mc.field_71474_y.field_74334_X) {
            lastCanBeSeen = true;
         } else if (lastCanBeSeen) {
            lastCanBeSeen = false;
         }

         AxisAlignedBB boundingBox = (AxisAlignedBB)getAABB.invoke(entity);
         VecRotation var5 = RotationUtils.calculateCenter("HalfUp", true, (double)((Number)random.get()).floatValue(), true, boundingBox, false, true);
         if (var5 == null) {
            return false;
         } else {
            Rotation directRotation = var5.component2();
            double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, directRotation);
            if (diffAngle < (double)0.0F) {
               diffAngle = -diffAngle;
            }

            if (diffAngle > (double)180.0F) {
               diffAngle = (double)180.0F;
            }

            double calculateSpeed = diffAngle / (double)360 * (double)rotationSpeed.get().getLast() + ((double)1 - diffAngle / (double)360) * (double)rotationSpeed.get().getStart();
            Rotation rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
            Intrinsics.checkNotNullExpressionValue(rotation, "limitAngleChange(Rotatio…calculateSpeed.toFloat())");
            RotationUtils.setFreeLookRotation(rotation, 1, (Boolean)movementFix.get() && !(Boolean)lessFix.get());
            return true;
         }
      }
   }

   private final void bypassBlock() {
      if (System.currentTimeMillis() - LastBlock >= blockDelay) {
         KeyBinding.func_74507_a(MinecraftInstance.mc.field_71474_y.field_74313_G.func_151463_i());
         MouseUtils.INSTANCE.setRightClicked(true);
         LastBlock = System.currentTimeMillis();
         blockDelay = TimeUtils.INSTANCE.randomClickDelay(((Number)bypassTick.get()).intValue(), ((Number)bypassTick.get()).intValue());
      } else {
         MouseUtils.INSTANCE.setRightClicked(false);
      }

      blockingStatus = true;
   }

   private final void stopBlocking() {
      if (blockingStatus) {
         MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G);
         MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a(MinecraftInstance.mc.field_71474_y.field_74313_G));
         blockingStatus = false;
      }

   }

   public final double getReach() {
      return (double)((Number)reachValue.get()).floatValue();
   }

   @Nullable
   public String getTag() {
      return String.valueOf(inRangeDiscoveredTargets.size());
   }

   static {
      String[] var0 = new String[]{"Legit", "Packet"};
      attackMode = new ListValue("AttackMode", var0, "Legit");
      hitableCheck = (new BoolValue("Hitable", false)).displayable(null.INSTANCE);
      var0 = new String[]{"ClientSide", "ServerSide", "None"};
      swingMode = (new ListValue("SwingMode", var0, "ClientSide")).displayable(null.INSTANCE);
      var0 = new String[]{"Fake", "Bypass", "HurtTime", "Time", "None"};
      autoBlock = new ListValue("Blocking", var0, "None");
      whileMoving = (new BoolValue("WhileMoving", false)).displayable(null.INSTANCE);
      alwaysBlock = (new BoolValue("Always-Block", false)).displayable(null.INSTANCE);
      bypassTick = (new IntegerValue("BypassTick", 15, 1, 20)).displayable(null.INSTANCE);
      autoBlockTime = (new IntegerValue("Time-Press", 0, 0, 1000)).displayable(null.INSTANCE);
      switchValue = new BoolValue("Switch-Target", false);
      scaffoldCheck = new BoolValue("Scaffold-Check", true);
      markValue = new BoolValue("Mark", false);
      movementFix = new BoolValue("MovementFix", false);
      lessFix = (new BoolValue("LessFix", false)).displayable(null.INSTANCE);
      switchDelay = (new IntegerValue("Switch-Delay", 140, 0, 1000)).displayable(null.INSTANCE);
      reachValue = new FloatValue() {
         protected void onChanged(float oldValue, float newValue) {
            float minreach = ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue();
            if (minreach < newValue) {
               this.set(minreach);
            }

         }
      };
      swingRange = new FloatValue() {
         protected void onChanged(float oldValue, float newValue) {
            if (newValue > ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue()) {
               this.set(SilentAura.INSTANCE.getDiscoverValue().get());
            }

            if (newValue < ((Number)SilentAura.INSTANCE.getReachValue().get()).floatValue()) {
               this.set(SilentAura.INSTANCE.getReachValue().get());
            }

         }
      };
      discoverValue = new FloatValue() {
         protected void onChanged(float oldValue, float newValue) {
            float maxreach = ((Number)SilentAura.INSTANCE.getReachValue().get()).floatValue();
            if (maxreach > newValue) {
               this.set(maxreach);
            }

         }
      };
      blockRangeValue = (FloatValue)(new FloatValue() {
         protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue();
            if (i < newValue) {
               this.set(i);
            }

         }
      }).displayable(null.INSTANCE);
      fovValue = new IntegerValue("Fov", 180, 0, 180);
      cpsValue = new IntegerRangeValue("CPS", 16, 20, 1, 20, (Function0)null, 32, (DefaultConstructorMarker)null);
      rotationSpeed = new IntegerRangeValue("Rotation-Speed", 145, 180, 1, 180, (Function0)null, 32, (DefaultConstructorMarker)null);
      random = new FloatValue("Random-Amount", 0.0F, 0.0F, 2.0F);
      leftDelay = 50L;
      switchTimer = new MSTimer();
      prevTargetEntities = (List)(new ArrayList());
      discoveredTargets = (List)(new ArrayList());
      inRangeDiscoveredTargets = (List)(new ArrayList());
      blockDelay = 50L;
      blockTime = new TimerMS();
      getAABB = null.INSTANCE;
   }
}
