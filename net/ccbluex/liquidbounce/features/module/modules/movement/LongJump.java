package net.ccbluex.liquidbounce.features.module.modules.movement;

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
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "LongJump",
   category = ModuleCategory.MOVEMENT,
   autoDisable = EnumAutoDisableType.FLAG
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0007J\b\u0010/\u001a\u00020,H\u0016J\b\u00100\u001a\u00020,H\u0016J\u0010\u00101\u001a\u00020,2\u0006\u0010-\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020,2\u0006\u0010-\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020,2\u0006\u0010-\u001a\u000206H\u0007J\u0010\u00107\u001a\u00020,2\u0006\u0010-\u001a\u000208H\u0007J\u0010\u00109\u001a\u00020,2\u0006\u0010-\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u00020,2\u0006\u0010-\u001a\u00020<H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0006R\u001a\u0010\u001c\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000eR\u001a\u0010\u001f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\f\"\u0004\b!\u0010\u000eR\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001e\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0'X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*¨\u0006="},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/LongJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoDisableValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getAutoDisableValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoJumpValue", "getAutoJumpValue", "hasJumped", "", "getHasJumped", "()Z", "setHasJumped", "(Z)V", "jumped", "getJumped", "setJumped", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "motionResetValue", "getMotionResetValue", "needReset", "getNeedReset", "setNeedReset", "no", "getNo", "setNo", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class LongJump extends Module {
   @NotNull
   private final List modes;
   @NotNull
   private final ListValue modeValue;
   @NotNull
   private final BoolValue motionResetValue;
   @NotNull
   private final BoolValue autoJumpValue;
   @NotNull
   private final BoolValue autoDisableValue;
   private boolean jumped;
   private boolean hasJumped;
   private boolean no;
   private boolean needReset;
   @NotNull
   private final List values;

   public LongJump() {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".longjumps"), LongJumpMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var9 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode");
         }

         destination$iv$iv.add((LongJumpMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      this.modes = CollectionsKt.sortedWith($this$map$iv, new LongJump$special$$inlined$sortedBy$1());
      Iterable $this$map$iv = (Iterable)this.modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         LongJumpMode it = (LongJumpMode)item$iv$iv;
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
               if (LongJump.this.getState()) {
                  LongJump.this.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (LongJump.this.getState()) {
                  LongJump.this.onEnable();
               }

            }
         };
         this.motionResetValue = new BoolValue("MotionReset", true);
         this.autoJumpValue = new BoolValue("AutoJump", true);
         this.autoDisableValue = new BoolValue("AutoDisable", true);
         this.needReset = true;
         List var24 = CollectionsKt.toMutableList((Collection)super.getValues());
         List it = var24;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)this.modes;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final LongJumpMode mode = (LongJumpMode)item$iv$iv;
            int var13 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var19 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return LongJump.this.modeValue.equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var42 = (List)destination$iv$iv;
         this.values = var24;
      }
   }

   private final LongJumpMode getMode() {
      Iterator var1 = ((Iterable)this.modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            LongJumpMode it = (LongJumpMode)var2;
            int var4 = 0;
            if (!this.modeValue.equals(it.getModeName())) {
               continue;
            }

            var10000 = (LongJumpMode)var2;
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
   public final BoolValue getMotionResetValue() {
      return this.motionResetValue;
   }

   @NotNull
   public final BoolValue getAutoJumpValue() {
      return this.autoJumpValue;
   }

   @NotNull
   public final BoolValue getAutoDisableValue() {
      return this.autoDisableValue;
   }

   public final boolean getJumped() {
      return this.jumped;
   }

   public final void setJumped(boolean var1) {
      this.jumped = var1;
   }

   public final boolean getHasJumped() {
      return this.hasJumped;
   }

   public final void setHasJumped(boolean var1) {
      this.hasJumped = var1;
   }

   public final boolean getNo() {
      return this.no;
   }

   public final void setNo(boolean var1) {
      this.no = var1;
   }

   public final boolean getNeedReset() {
      return this.needReset;
   }

   public final void setNeedReset(boolean var1) {
      this.needReset = var1;
   }

   public void onEnable() {
      this.jumped = false;
      this.hasJumped = false;
      this.no = false;
      this.needReset = true;
      this.getMode().onEnable();
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
      MinecraftInstance.mc.field_71439_g.field_70145_X = false;
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02F;
      this.getMode().onDisable();
      if ((Boolean)this.motionResetValue.get() && this.needReset) {
         MovementUtils.INSTANCE.resetMotion(true);
      }

   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onUpdate(event);
         if (!this.no && (Boolean)this.autoJumpValue.get() && MinecraftInstance.mc.field_71439_g.field_70122_E && MovementUtils.INSTANCE.isMoving()) {
            this.jumped = true;
            if (this.hasJumped && (Boolean)this.autoDisableValue.get()) {
               this.setState(false);
               return;
            }

            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, (double)0.0F, 6, (Object)null);
            this.hasJumped = true;
         }

      }
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onMotion(event);
         if (event.getEventState() == EventState.PRE) {
            this.getMode().onPreMotion(event);
         }
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.getState()) {
         this.getMode().onPacket(event);
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
         this.jumped = true;
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

   @NotNull
   public List getValues() {
      return this.values;
   }
}
