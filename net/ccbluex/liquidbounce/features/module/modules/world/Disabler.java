package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Disabler",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\b\u0010\u001f\u001a\u00020\nH\u0016J\b\u0010 \u001a\u00020\nH\u0016J\u0010\u0010!\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020\n2\u0006\u0010\u001d\u001a\u000200H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u001e\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R&\u0010\u0013\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0014j\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e`\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Disabler;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "debugValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "mode", "Ljava/util/LinkedList;", "mode2", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "modeList", "", "Lkotlin/Unit;", "modeValue", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getModeValue", "()Ljava/util/List;", "modes", "getModes", "settings", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "values", "", "getValues", "debugMessage", "str", "", "onBlockBB", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class Disabler extends Module {
   @NotNull
   public static final Disabler INSTANCE = new Disabler();
   @NotNull
   private static final BoolValue debugValue = new BoolValue("Debug", false);
   @NotNull
   private static final LinkedList mode = new LinkedList();
   @NotNull
   private static final LinkedList mode2 = new LinkedList();
   @NotNull
   private static final ArrayList settings;
   @NotNull
   private static final Unit modeList;
   @NotNull
   private static final List modes;
   @NotNull
   private static final List values;

   private Disabler() {
   }

   @NotNull
   public final List getModes() {
      return modes;
   }

   public void onEnable() {
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var6 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onEnable();
         }
      }

   }

   public void onDisable() {
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var6 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onDisable();
         }
      }

      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onUpdate(event);
         }
      }

   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onRender2D(event);
         }
      }

   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onMotion(event);
         }
      }

   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onPacket(event);
         }
      }

   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onMove(event);
         }
      }

   }

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onBlockBB(event);
         }
      }

   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onJump(event);
         }
      }

   }

   @EventTarget
   public final void onStep(@NotNull StepEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onStep(event);
         }
      }

   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = (Iterable)modes;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         DisablerMode it = (DisablerMode)element$iv;
         int var7 = 0;
         Value var10000 = INSTANCE.getValue(it.getModeName());
         if (var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true)) {
            it.onWorld(event);
         }
      }

   }

   public final void debugMessage(@NotNull String str) {
      Intrinsics.checkNotNullParameter(str, "str");
      if ((Boolean)debugValue.get()) {
         this.alert(Intrinsics.stringPlus("§7[§c§lDisabler§7] §b", str));
      }

   }

   private final List getModeValue() {
      return (List)settings;
   }

   @NotNull
   public List getValues() {
      return values;
   }

   static {
      Value[] var0 = new Value[]{debugValue};
      settings = CollectionsKt.arrayListOf(var0);
      Iterable $this$sortedBy$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".disablers"), DisablerMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$sortedBy$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$sortedBy$iv) {
         Class it = (Class)item$iv$iv;
         int var8 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode");
         }

         destination$iv$iv.add((DisablerMode)var10000);
      }

      $this$sortedBy$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      $this$sortedBy$iv = (Iterable)CollectionsKt.sortedWith($this$sortedBy$iv, new Disabler$special$$inlined$sortedBy$1());
      $i$f$map = 0;

      for(Object element$iv : $this$sortedBy$iv) {
         final DisablerMode it = (DisablerMode)element$iv;
         int $this$mapTo$iv$iv = 0;
         String item$iv$iv = it.getModeName();
         <undefinedtype> modulesMode = new BoolValue(item$iv$iv) {
            protected void onChange(boolean oldValue, boolean newValue) {
               if (newValue && !oldValue) {
                  it.onEnable();
               } else if (!newValue && oldValue) {
                  it.onDisable();
               }

            }
         };
         settings.add(modulesMode);
         Disabler.mode.add(modulesMode);
         mode2.add(it);
      }

      modeList = Unit.INSTANCE;
      $this$sortedBy$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".disablers"), DisablerMode.class);
      $i$f$map = 0;
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$sortedBy$iv, 10)));
      $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$sortedBy$iv) {
         Class it = (Class)item$iv$iv;
         int var46 = 0;
         Object var48 = it.newInstance();
         if (var48 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode");
         }

         destination$iv$iv.add((DisablerMode)var48);
      }

      $this$sortedBy$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Disabler$special$$inlined$sortedBy$2());
      List var25 = CollectionsKt.toMutableList((Collection)INSTANCE.getModeValue());
      List it = var25;
      int $this$mapTo$iv$iv = 0;
      Iterable $this$map$iv = (Iterable)INSTANCE.getModes();
      $i$f$mapTo = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         final DisablerMode mode = (DisablerMode)item$iv$iv;
         int var12 = 0;
         Iterable $this$forEach$iv = (Iterable)mode.getValues();
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Value value = (Value)element$iv;
            int var18 = 0;
            it.add(value.displayable(new Function0() {
               @NotNull
               public final Boolean invoke() {
                  Value var10000 = Disabler.INSTANCE.getValue(mode.getModeName());
                  return var10000 == null ? false : Intrinsics.areEqual((Object)var10000.getValue(), (Object)true);
               }
            }));
         }

         destination$iv$iv.add(Unit.INSTANCE);
      }

      List var49 = (List)destination$iv$iv;
      values = var25;
   }
}
