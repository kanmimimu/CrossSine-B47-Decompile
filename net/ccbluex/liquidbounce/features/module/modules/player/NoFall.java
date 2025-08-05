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
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "NoFall",
   category = ModuleCategory.PLAYER,
   autoDisable = EnumAutoDisableType.FLAG
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020,H\u0007R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006-"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/NoFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "noVoid", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "wasTimer", "", "getWasTimer", "()Z", "setWasTimer", "(Z)V", "checkVoid", "onDisable", "", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class NoFall extends Module {
   @NotNull
   public static final NoFall INSTANCE = new NoFall();
   @NotNull
   private static final List modes;
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final BoolValue noVoid;
   private static boolean wasTimer;
   @NotNull
   private static final List values;

   private NoFall() {
   }

   @NotNull
   public final NoFallMode getMode() {
      Iterator var1 = ((Iterable)modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            NoFallMode it = (NoFallMode)var2;
            int var4 = 0;
            if (!modeValue.equals(it.getModeName())) {
               continue;
            }

            var10000 = var2;
            break;
         }

         var10000 = null;
         break;
      }

      return (NoFallMode)var10000;
   }

   public final boolean getWasTimer() {
      return wasTimer;
   }

   public final void setWasTimer(boolean var1) {
      wasTimer = var1;
   }

   public void onEnable() {
      wasTimer = false;
      this.getMode().onEnable();
   }

   public void onDisable() {
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
      MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
      MinecraftInstance.mc.field_71439_g.field_70145_X = false;
      MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
      MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02F;
      this.getMode().onDisable();
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (wasTimer) {
         MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0F;
         wasTimer = false;
      }

      this.getMode().onUpdate(event);
      if (this.getState()) {
         Module var10000 = CrossSine.INSTANCE.getModuleManager().get(FreeCam.class);
         Intrinsics.checkNotNull(var10000);
         if (!((FreeCam)var10000).getState()) {
            if (!MinecraftInstance.mc.field_71439_g.func_175149_v() && !MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75101_c && !MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75102_a) {
               AxisAlignedBB var2 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
               Intrinsics.checkNotNullExpressionValue(var2, "mc.thePlayer.entityBoundingBox");
               if (!BlockUtils.collideBlock(var2, null.INSTANCE) && !BlockUtils.collideBlock(new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c), null.INSTANCE)) {
                  if (this.checkVoid() && (Boolean)noVoid.get()) {
                     return;
                  }

                  this.getMode().onNoFall(event);
                  return;
               }

               return;
            }

            return;
         }
      }

   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.checkVoid() || !(Boolean)noVoid.get()) {
         this.getMode().onMotion(event);
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.checkVoid() || !(Boolean)noVoid.get()) {
         this.getMode().onPacket(event);
      }
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      this.getMode().onRender2D(event);
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.checkVoid() || !(Boolean)noVoid.get()) {
         this.getMode().onMove(event);
      }
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.checkVoid() || !(Boolean)noVoid.get()) {
         this.getMode().onJump(event);
      }
   }

   private final boolean checkVoid() {
      int i = (int)(-(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.4857625));
      boolean dangerous = true;

      while(i <= 0) {
         dangerous = MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w * (double)0.5F, (double)i, MinecraftInstance.mc.field_71439_g.field_70179_y * (double)0.5F)).isEmpty();
         ++i;
         if (!dangerous) {
            break;
         }
      }

      return dangerous;
   }

   @NotNull
   public List getValues() {
      return values;
   }

   @Nullable
   public String getTag() {
      return (String)modeValue.get();
   }

   static {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".nofalls"), NoFallMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var8 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode");
         }

         destination$iv$iv.add((NoFallMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      modes = CollectionsKt.sortedWith($this$map$iv, new NoFall$special$$inlined$sortedBy$1());
      Iterable $this$map$iv = (Iterable)modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         NoFallMode it = (NoFallMode)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(it.getModeName());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var40 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var40 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var21 = (String[])var40;
         modeValue = new ListValue(var21) {
            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (NoFall.INSTANCE.getState()) {
                  NoFall.INSTANCE.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (NoFall.INSTANCE.getState()) {
                  NoFall.INSTANCE.onEnable();
               }

            }
         };
         noVoid = new BoolValue("NoVoid", false);
         List var22 = CollectionsKt.toMutableList((Collection)INSTANCE.getValues());
         List it = var22;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)modes;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final NoFallMode mode = (NoFallMode)item$iv$iv;
            int var12 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var18 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return NoFall.modeValue.equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var41 = (List)destination$iv$iv;
         values = var22;
      }
   }
}
