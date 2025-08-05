package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0BPacketAnimation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Criticals",
   category = ModuleCategory.COMBAT,
   autoDisable = EnumAutoDisableType.FLAG
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u0002022\u0006\u00103\u001a\u000206H\u0007J\b\u00107\u001a\u000202H\u0016J\b\u00108\u001a\u000202H\u0016J\u0010\u00109\u001a\u0002022\u0006\u00103\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u0002022\u0006\u00103\u001a\u00020<H\u0007J\u0010\u0010=\u001a\u0002022\u0006\u00103\u001a\u00020>H\u0007J\u0010\u0010?\u001a\u0002022\u0006\u00103\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u0002022\u0006\u00103\u001a\u00020BH\u0007J\u0010\u0010C\u001a\u0002022\u0006\u00103\u001a\u00020DH\u0007J,\u0010E\u001a\u0002022\b\b\u0002\u0010F\u001a\u00020G2\b\b\u0002\u0010H\u001a\u00020G2\b\b\u0002\u0010I\u001a\u00020G2\u0006\u0010J\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00140\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001e\u0010-\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e0.X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100¨\u0006K"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "CritTiming", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "antiDesync", "", "getAntiDesync", "()Z", "setAntiDesync", "(Z)V", "debugValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "flagTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "hurtTimeValue", "lookValue", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "modeValue", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "msTimer", "s08DelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "s08FlagValue", "getS08FlagValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "syncTimer", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()I", "setTarget", "(I)V", "values", "", "getValues", "()Ljava/util/List;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onBlockBB", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sendCriticalPacket", "xOffset", "", "yOffset", "zOffset", "ground", "CrossSine"}
)
public final class Criticals extends Module {
   @NotNull
   private final List modes;
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final IntegerValue delayValue;
   @NotNull
   private final BoolValue s08FlagValue;
   @NotNull
   private final Value s08DelayValue;
   @NotNull
   private final IntegerValue hurtTimeValue;
   @NotNull
   private final ListValue CritTiming;
   @NotNull
   private final BoolValue lookValue;
   @NotNull
   private final BoolValue debugValue;
   @NotNull
   private final MSTimer msTimer;
   @NotNull
   private final MSTimer flagTimer;
   @NotNull
   private final MSTimer syncTimer;
   private int target;
   private boolean antiDesync;
   @NotNull
   private final List values;

   public Criticals() {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".criticals"), CriticalMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var9 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode");
         }

         destination$iv$iv.add((CriticalMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      this.modes = CollectionsKt.sortedWith($this$map$iv, new Criticals$special$$inlined$sortedBy$1());
      Iterable $this$map$iv = (Iterable)this.modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         CriticalMode it = (CriticalMode)item$iv$iv;
         int var10 = 0;
         destination$iv$iv.add(it.getModeName());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var10001 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var10001 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var23 = (String[])var10001;
         this.modeValue = new ListValue(var23) {
            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Criticals.this.getState()) {
                  Criticals.this.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Criticals.this.getState()) {
                  Criticals.this.onEnable();
               }

            }
         };
         this.delayValue = new IntegerValue("Delay", 0, 0, 500);
         this.s08FlagValue = new BoolValue("FlagPause", true);
         this.s08DelayValue = (new IntegerValue("FlagPause-Time", 100, 100, 5000)).displayable(new Function0() {
            @NotNull
            public final Boolean invoke() {
               return (Boolean)Criticals.this.getS08FlagValue().get();
            }
         });
         this.hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
         var23 = new String[]{"Always", "OnGround", "OffGround"};
         this.CritTiming = new ListValue("CritTiming", var23, "Always");
         this.lookValue = new BoolValue("UseC06Packet", false);
         this.debugValue = new BoolValue("DebugMessage", false);
         this.msTimer = new MSTimer();
         this.flagTimer = new MSTimer();
         this.syncTimer = new MSTimer();
         List var25 = CollectionsKt.toMutableList((Collection)super.getValues());
         List it = var25;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)this.modes;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final CriticalMode mode = (CriticalMode)item$iv$iv;
            int var13 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var19 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return Criticals.this.getModeValue().equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var43 = (List)destination$iv$iv;
         this.values = var25;
      }
   }

   private final CriticalMode getMode() {
      Iterator var1 = ((Iterable)this.modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            CriticalMode it = (CriticalMode)var2;
            int var4 = 0;
            if (!this.getModeValue().equals(it.getModeName())) {
               continue;
            }

            var10000 = (CriticalMode)var2;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000;
      if (var10000 == null) {
         throw new NullPointerException();
      } else {
         return var10000;
      }
   }

   @NotNull
   public final ListValue getModeValue() {
      return this.modeValue;
   }

   @NotNull
   public final BoolValue getS08FlagValue() {
      return this.s08FlagValue;
   }

   public final int getTarget() {
      return this.target;
   }

   public final void setTarget(int var1) {
      this.target = var1;
   }

   public final boolean getAntiDesync() {
      return this.antiDesync;
   }

   public final void setAntiDesync(boolean var1) {
      this.antiDesync = var1;
   }

   public void onEnable() {
      this.target = 0;
      this.msTimer.reset();
      this.flagTimer.reset();
      this.syncTimer.reset();
      this.getMode().onEnable();
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      this.getMode().onDisable();
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         String var3 = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var3.hashCode()) {
            case -1414557169:
               if (!var3.equals("always")) {
               }
               break;
            case 2002614198:
               if (var3.equals("offground") && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
               break;
            case 2077082662:
               if (var3.equals("onground") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
         }

         this.getMode().onUpdate(event);
      }
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         String var3 = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var3.hashCode()) {
            case -1414557169:
               if (!var3.equals("always")) {
               }
               break;
            case 2002614198:
               if (var3.equals("offground") && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
               break;
            case 2077082662:
               if (var3.equals("onground") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
         }

         this.getMode().onMotion(event);
         if (event.getEventState() == EventState.PRE) {
            this.getMode().onPreMotion(event);
         }
      }
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         String var3 = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
         switch (var3.hashCode()) {
            case -1414557169:
               if (!var3.equals("always")) {
               }
               break;
            case 2002614198:
               if (var3.equals("offground") && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
               break;
            case 2077082662:
               if (var3.equals("onground") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                  return;
               }
         }

         if (event.getTargetEntity() instanceof EntityLivingBase) {
            Entity entity = event.getTargetEntity();
            this.target = ((EntityLivingBase)entity).func_145782_y();
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.field_70134_J || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.func_180799_ab() || MinecraftInstance.mc.field_71439_g.field_70154_o != null || ((EntityLivingBase)entity).field_70737_aN > ((Number)this.hurtTimeValue.get()).intValue() || !this.msTimer.hasTimePassed((long)((Number)this.delayValue.get()).intValue())) {
               return;
            }

            if ((Boolean)this.s08FlagValue.get() && !this.flagTimer.hasTimePassed((long)((Number)this.s08DelayValue.get()).intValue())) {
               return;
            }

            this.antiDesync = true;
            this.getMode().onAttack(event);
            this.msTimer.reset();
         }

      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         Packet packet = event.getPacket();
         if (packet instanceof S08PacketPlayerPosLook) {
            this.flagTimer.reset();
            this.antiDesync = false;
            if ((Boolean)this.debugValue.get()) {
               this.alert("FLAG");
            }
         }

         if (packet instanceof C03PacketPlayer && (MovementUtils.INSTANCE.isMoving() || this.syncTimer.hasTimePassed(1000L) || this.msTimer.hasTimePassed((long)(((Number)this.delayValue.get()).intValue() / 5 + 75)))) {
            this.antiDesync = false;
         }

         if (!(Boolean)this.s08FlagValue.get() || this.flagTimer.hasTimePassed((long)((Number)this.s08DelayValue.get()).intValue())) {
            this.getMode().onPacket(event);
            if (packet instanceof S0BPacketAnimation && (Boolean)this.debugValue.get() && ((S0BPacketAnimation)packet).func_148977_d() == 4 && ((S0BPacketAnimation)packet).func_148978_c() == this.target) {
               this.alert("CRIT");
            }

         }
      }
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onMove(event);
      }
   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onBlockBB(event);
      }
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onJump(event);
      }
   }

   @EventTarget
   public final void onStep(@NotNull StepEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onStep(event);
      }
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }

   public final void sendCriticalPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
      double x = MinecraftInstance.mc.field_71439_g.field_70165_t + xOffset;
      double y = MinecraftInstance.mc.field_71439_g.field_70163_u + yOffset;
      double z = MinecraftInstance.mc.field_71439_g.field_70161_v + zOffset;
      if ((Boolean)this.lookValue.get()) {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, ground)));
      } else {
         MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground)));
      }

   }

   // $FF: synthetic method
   public static void sendCriticalPacket$default(Criticals var0, double var1, double var3, double var5, boolean var7, int var8, Object var9) {
      if ((var8 & 1) != 0) {
         var1 = (double)0.0F;
      }

      if ((var8 & 2) != 0) {
         var3 = (double)0.0F;
      }

      if ((var8 & 4) != 0) {
         var5 = (double)0.0F;
      }

      var0.sendCriticalPacket(var1, var3, var5, var7);
   }

   @NotNull
   public List getValues() {
      return this.values;
   }
}
