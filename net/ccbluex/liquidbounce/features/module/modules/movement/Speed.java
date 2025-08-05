package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Speed",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020)2\u0006\u0010,\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020)2\u0006\u0010,\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020)2\u0006\u0010,\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020)2\u0006\u0010,\u001a\u000205H\u0007J\u0010\u00106\u001a\u00020)2\u0006\u0010,\u001a\u000207H\u0007J\b\u00108\u001a\u00020\rH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001b¨\u00069"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "flagCheck", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getFlagCheck", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "flagMS", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getFlagMS", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "flagged", "", "getFlagged", "()Z", "setFlagged", "(Z)V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "getModes", "()Ljava/util/List;", "noWater", "tag", "", "getTag", "()Ljava/lang/String;", "timerMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getTimerMS", "()Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "values", "", "getValues", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "stopWorking", "CrossSine"}
)
public final class Speed extends Module {
   @NotNull
   public static final Speed INSTANCE = new Speed();
   @NotNull
   private static final List modes;
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue flagCheck;
   @NotNull
   private static final Value flagMS;
   @NotNull
   private static final BoolValue noWater;
   private static boolean flagged;
   @NotNull
   private static final TimerMS timerMS;
   @NotNull
   private static final List values;

   private Speed() {
   }

   @NotNull
   public final List getModes() {
      return modes;
   }

   private final SpeedMode getMode() {
      Iterator var1 = ((Iterable)modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            SpeedMode it = (SpeedMode)var2;
            int var4 = 0;
            if (!modeValue.equals(it.getModeName())) {
               continue;
            }

            var10000 = (SpeedMode)var2;
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
   public final BoolValue getFlagCheck() {
      return flagCheck;
   }

   @NotNull
   public final Value getFlagMS() {
      return flagMS;
   }

   public final boolean getFlagged() {
      return flagged;
   }

   public final void setFlagged(boolean var1) {
      flagged = var1;
   }

   @NotNull
   public final TimerMS getTimerMS() {
      return timerMS;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (timerMS.hasTimePassed((long)((Number)flagMS.get()).intValue())) {
         flagged = false;
      }

      if (!MinecraftInstance.mc.field_71439_g.func_70093_af() && (!MinecraftInstance.mc.field_71439_g.func_70090_H() || !(Boolean)noWater.get())) {
         if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0F && modeValue.equals("Legit") || MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71439_g.func_70051_ag();
         }

         if (!this.stopWorking()) {
            this.getMode().onUpdate();
         }
      }
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!(Boolean)Interface.INSTANCE.getDynamicIsland().get()) {
         DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
         if (this.stopWorking()) {
            Fonts.font32.drawCenteredString(Intrinsics.stringPlus("Disable Speed : ", decimalFormat3.format((double)(timerMS.time + (long)2000 - System.currentTimeMillis()) / (double)1000.0F)), (float)event.getScaledResolution().func_78326_a() / 2.0F, (float)event.getScaledResolution().func_78328_b() / 2.0F + 5.0F, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, (Object)null).getRGB(), true);
         }

      }
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.stopWorking()) {
         if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0F && modeValue.equals("Legit") || MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71439_g.func_70051_ag();
         }

         this.getMode().onMotion(event);
         if (!MinecraftInstance.mc.field_71439_g.func_70093_af() && event.getEventState() == EventState.PRE && (!MinecraftInstance.mc.field_71439_g.func_70090_H() || !(Boolean)noWater.get())) {
            this.getMode().onPreMotion();
         }
      }
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!MinecraftInstance.mc.field_71439_g.func_70093_af() && (!MinecraftInstance.mc.field_71439_g.func_70090_H() || !(Boolean)noWater.get())) {
         if (!this.stopWorking()) {
            this.getMode().onMove(event);
            Module var10000 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(var10000);
            ((TargetStrafe)var10000).doMove(event);
         }
      }
   }

   @EventTarget
   public final void onTick(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!MinecraftInstance.mc.field_71439_g.func_70093_af() && (!MinecraftInstance.mc.field_71439_g.func_70090_H() || !(Boolean)noWater.get())) {
         if (!this.stopWorking()) {
            this.getMode().onTick();
         }
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPacket() instanceof S08PacketPlayerPosLook) {
         flagged = true;
         timerMS.reset();
      }

      if (!this.stopWorking()) {
         this.getMode().onPacket(event);
      }
   }

   public void onEnable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         this.getMode().onEnable();
      }
   }

   public void onDisable() {
      if (MinecraftInstance.mc.field_71439_g != null) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         this.getMode().onDisable();
         flagged = false;
      }
   }

   private final boolean stopWorking() {
      return (Boolean)flagCheck.get() && flagged && !timerMS.hasTimePassed((long)((Number)flagMS.get()).intValue());
   }

   @NotNull
   public String getTag() {
      return (String)modeValue.get();
   }

   @NotNull
   public List getValues() {
      return values;
   }

   static {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".speeds"), SpeedMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var8 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode");
         }

         destination$iv$iv.add((SpeedMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      modes = CollectionsKt.sortedWith($this$map$iv, new Speed$special$$inlined$sortedBy$1());
      Speed var40 = INSTANCE;
      Iterable $this$map$iv = (Iterable)modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         SpeedMode it = (SpeedMode)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(it.getModeName());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      var40 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var40 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var21 = (String[])var40;
         modeValue = new ListValue(var21) {
            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Speed.INSTANCE.getState()) {
                  Speed.INSTANCE.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Speed.INSTANCE.getState()) {
                  Speed.INSTANCE.onEnable();
               }

            }
         };
         flagCheck = new BoolValue("Flag-Check", false);
         flagMS = (new IntegerValue("StopForMS", 500, 0, 2000)).displayable(null.INSTANCE);
         noWater = new BoolValue("NoWater", true);
         timerMS = new TimerMS();
         List var22 = CollectionsKt.toMutableList((Collection)INSTANCE.getValues());
         List it = var22;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)INSTANCE.getModes();
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final SpeedMode mode = (SpeedMode)item$iv$iv;
            int var12 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var18 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return Speed.modeValue.equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var42 = (List)destination$iv$iv;
         values = var22;
      }
   }
}
