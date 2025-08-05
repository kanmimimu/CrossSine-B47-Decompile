package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Flight",
   category = ModuleCategory.MOVEMENT,
   autoDisable = EnumAutoDisableType.FLAG
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0007J\b\u0010D\u001a\u00020AH\u0016J\b\u0010E\u001a\u00020AH\u0016J\u0010\u0010F\u001a\u00020A2\u0006\u0010B\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020A2\u0006\u0010B\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020A2\u0006\u0010B\u001a\u00020KH\u0007J\u0010\u0010L\u001a\u00020A2\u0006\u0010B\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020A2\u0006\u0010B\u001a\u00020OH\u0007J\u0010\u0010P\u001a\u00020A2\u0006\u0010B\u001a\u00020QH\u0007J\u0010\u0010R\u001a\u00020A2\u0006\u0010B\u001a\u00020SH\u0007J\u0010\u0010T\u001a\u00020A2\u0006\u0010B\u001a\u00020UH\u0007J\u0010\u0010V\u001a\u00020A2\u0006\u0010B\u001a\u00020WH\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u001a\u0010\u001a\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0010R\u001a\u0010\u001d\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0014\"\u0004\b\u001f\u0010\u0016R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020#0(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040-X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR\u0014\u00101\u001a\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u001a\u00105\u001a\u000206X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001e\u0010;\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030-0<X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b=\u0010*R\u000e\u0010>\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020\f0-X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006X"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Flight;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "antiDesync", "", "getAntiDesync", "()Z", "setAntiDesync", "(Z)V", "fakeDamageValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "launchPitch", "", "getLaunchPitch", "()F", "setLaunchPitch", "(F)V", "launchX", "", "getLaunchX", "()D", "setLaunchX", "(D)V", "launchY", "getLaunchY", "setLaunchY", "launchYaw", "getLaunchYaw", "setLaunchYaw", "launchZ", "getLaunchZ", "setLaunchZ", "markValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "modeValue", "modes", "", "getModes", "()Ljava/util/List;", "motionResetValue", "motionResetYValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "needReset", "getNeedReset", "setNeedReset", "tag", "", "getTag", "()Ljava/lang/String;", "time", "", "getTime", "()I", "setTime", "(I)V", "values", "", "getValues", "viewBobbingValue", "viewBobbingYawValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3d", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class Flight extends Module {
   @NotNull
   public static final Flight INSTANCE = new Flight();
   @NotNull
   private static final List modes;
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue motionResetValue;
   @NotNull
   private static final Value motionResetYValue;
   @NotNull
   private static final ListValue markValue;
   @NotNull
   private static final BoolValue fakeDamageValue;
   @NotNull
   private static final BoolValue viewBobbingValue;
   @NotNull
   private static final Value viewBobbingYawValue;
   private static double launchX;
   private static double launchY;
   private static double launchZ;
   private static float launchYaw;
   private static float launchPitch;
   private static int time;
   private static boolean antiDesync;
   private static boolean needReset;
   @NotNull
   private static final List values;

   private Flight() {
   }

   @NotNull
   public final List getModes() {
      return modes;
   }

   private final FlightMode getMode() {
      Iterator var1 = ((Iterable)modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            FlightMode it = (FlightMode)var2;
            int var4 = 0;
            if (!modeValue.equals(it.getModeName())) {
               continue;
            }

            var10000 = (FlightMode)var2;
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

   public final double getLaunchX() {
      return launchX;
   }

   public final void setLaunchX(double var1) {
      launchX = var1;
   }

   public final double getLaunchY() {
      return launchY;
   }

   public final void setLaunchY(double var1) {
      launchY = var1;
   }

   public final double getLaunchZ() {
      return launchZ;
   }

   public final void setLaunchZ(double var1) {
      launchZ = var1;
   }

   public final float getLaunchYaw() {
      return launchYaw;
   }

   public final void setLaunchYaw(float var1) {
      launchYaw = var1;
   }

   public final float getLaunchPitch() {
      return launchPitch;
   }

   public final void setLaunchPitch(float var1) {
      launchPitch = var1;
   }

   public final int getTime() {
      return time;
   }

   public final void setTime(int var1) {
      time = var1;
   }

   public final boolean getAntiDesync() {
      return antiDesync;
   }

   public final void setAntiDesync(boolean var1) {
      antiDesync = var1;
   }

   public final boolean getNeedReset() {
      return needReset;
   }

   public final void setNeedReset(boolean var1) {
      needReset = var1;
   }

   public void onEnable() {
      antiDesync = false;
      needReset = true;
      if (MinecraftInstance.mc.field_71439_g.field_70122_E && (Boolean)fakeDamageValue.get()) {
         PacketEvent event = new PacketEvent((Packet)(new S19PacketEntityStatus((Entity)MinecraftInstance.mc.field_71439_g, (byte)2)), PacketEvent.Type.RECEIVE);
         CrossSine.INSTANCE.getEventManager().callEvent(event);
         if (!event.isCancelled()) {
            MinecraftInstance.mc.field_71439_g.func_70103_a((byte)2);
         }
      }

      launchX = MinecraftInstance.mc.field_71439_g.field_70165_t;
      launchY = MinecraftInstance.mc.field_71439_g.field_70163_u;
      launchZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
      launchYaw = MinecraftInstance.mc.field_71439_g.field_70177_z;
      launchPitch = MinecraftInstance.mc.field_71439_g.field_70125_A;
      this.getMode().onEnable();
   }

   public void onDisable() {
      antiDesync = false;
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
      MinecraftInstance.mc.field_71439_g.field_70145_X = false;
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02F;
      if ((Boolean)motionResetValue.get() && needReset) {
         MovementUtils.INSTANCE.resetMotion((Boolean)motionResetYValue.get());
      }

      this.getMode().onDisable();
      time = 0;
   }

   @EventTarget
   public final void onRender3d(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!markValue.equals("Off")) {
         RenderUtils.drawPlatform(markValue.equals("Up") ? launchY + (double)2.0F : launchY, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e < launchY + (double)2.0F ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), (double)1.0F);
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onUpdate(event);
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)viewBobbingValue.get()) {
         MinecraftInstance.mc.field_71439_g.field_71109_bG = ((Number)viewBobbingYawValue.get()).floatValue();
         MinecraftInstance.mc.field_71439_g.field_71107_bF = ((Number)viewBobbingYawValue.get()).floatValue();
      }

      this.getMode().onMotion(event);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onPacket(event);
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onWorld(event);
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onMove(event);
   }

   @EventTarget
   public final void onTick(@NotNull TickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onTick(event);
   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onBlockBB(event);
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onJump(event);
   }

   @EventTarget
   public final void onStep(@NotNull StepEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onStep(event);
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
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".flights"), FlightMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var8 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode");
         }

         destination$iv$iv.add((FlightMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      modes = CollectionsKt.sortedWith($this$map$iv, new Flight$special$$inlined$sortedBy$1());
      Flight var41 = INSTANCE;
      Iterable $this$map$iv = (Iterable)modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         FlightMode it = (FlightMode)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(it.getModeName());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      var41 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var41 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var21 = (String[])var41;
         modeValue = new ListValue(var21) {
            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Flight.INSTANCE.getState()) {
                  Flight.INSTANCE.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Flight.INSTANCE.getState()) {
                  Flight.INSTANCE.onEnable();
               }

            }
         };
         motionResetValue = new BoolValue("MotionReset", false);
         motionResetYValue = (new BoolValue("ResetY", false)).displayable(null.INSTANCE);
         var21 = new String[]{"Up", "Down", "Off"};
         markValue = new ListValue("Mark", var21, "Off");
         fakeDamageValue = new BoolValue("FakeDamage", false);
         viewBobbingValue = new BoolValue("ViewBobbing", false);
         viewBobbingYawValue = (new FloatValue("ViewBobbingYaw", 0.1F, 0.0F, 0.5F)).displayable(null.INSTANCE);
         needReset = true;
         List var23 = CollectionsKt.toMutableList((Collection)INSTANCE.getValues());
         List it = var23;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)INSTANCE.getModes();
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final FlightMode mode = (FlightMode)item$iv$iv;
            int var12 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var18 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return Flight.modeValue.equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var43 = (List)destination$iv$iv;
         values = var23;
      }
   }
}
