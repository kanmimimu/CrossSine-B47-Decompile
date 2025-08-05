package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.TeleportEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Phase",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020+H\u0007R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006,"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Phase;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPushOut", "Lnet/ccbluex/liquidbounce/event/PushOutEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onTeleport", "Lnet/ccbluex/liquidbounce/event/TeleportEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"}
)
public final class Phase extends Module {
   @NotNull
   private final List modes;
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final List values;

   public Phase() {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".phases"), PhaseMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var9 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode");
         }

         destination$iv$iv.add((PhaseMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      this.modes = CollectionsKt.sortedWith($this$map$iv, new Phase$special$$inlined$sortedBy$1());
      Iterable $this$map$iv = (Iterable)this.modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         PhaseMode it = (PhaseMode)item$iv$iv;
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
               if (Phase.this.getState()) {
                  Phase.this.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Phase.this.getState()) {
                  Phase.this.onEnable();
               }

            }
         };
         List var24 = CollectionsKt.toMutableList((Collection)super.getValues());
         List it = var24;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)this.modes;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final PhaseMode mode = (PhaseMode)item$iv$iv;
            int var13 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var19 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return Phase.this.modeValue.equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var42 = (List)destination$iv$iv;
         this.values = var24;
      }
   }

   private final PhaseMode getMode() {
      Iterator var1 = ((Iterable)this.modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            PhaseMode it = (PhaseMode)var2;
            int var4 = 0;
            if (!this.modeValue.equals(it.getModeName())) {
               continue;
            }

            var10000 = (PhaseMode)var2;
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

   public void onEnable() {
      this.getMode().onEnable();
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      this.getMode().onDisable();
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onUpdate(event);
   }

   @EventTarget
   public final void onTeleport(@NotNull TeleportEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onTeleport(event);
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onMotion(event);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onPacket(event);
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onMove(event);
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

   @EventTarget
   public final void onWorld(@NotNull WorldEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onWorld(event);
   }

   @EventTarget
   public final void onPushOut(@NotNull PushOutEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      event.cancelEvent();
   }

   @NotNull
   public String getTag() {
      return (String)this.modeValue.get();
   }

   @NotNull
   public List getValues() {
      return this.values;
   }
}
