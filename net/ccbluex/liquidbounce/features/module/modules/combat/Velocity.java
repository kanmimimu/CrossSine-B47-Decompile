package net.ccbluex.liquidbounce.features.module.modules.combat;

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
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Velocity",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020/H\u0016J\u0010\u00101\u001a\u00020/2\u0006\u00102\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020/2\u0006\u00102\u001a\u000205H\u0007R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0007¨\u00066"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chance", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getChance", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "horizontal", "getHorizontal", "m", "", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "noFireValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "oc", "og", "om", "tag", "getTag", "()Ljava/lang/String;", "values", "", "getValues", "()Ljava/util/List;", "velocityInput", "", "getVelocityInput", "()Z", "setVelocityInput", "(Z)V", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getVelocityTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "vertical", "getVertical", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class Velocity extends Module {
   @NotNull
   public static final Velocity INSTANCE = new Velocity();
   @NotNull
   private static final List modes;
   @NotNull
   private static final ListValue modeValue;
   @NotNull
   private static final Value horizontal;
   @NotNull
   private static final Value vertical;
   @NotNull
   private static final Value chance;
   @NotNull
   private static final Value m;
   @NotNull
   private static final BoolValue og;
   @NotNull
   private static final BoolValue oc;
   @NotNull
   private static final BoolValue om;
   @NotNull
   private static final BoolValue noFireValue;
   @NotNull
   private static final MSTimer velocityTimer;
   private static boolean velocityInput;
   @NotNull
   private static final List values;

   private Velocity() {
   }

   private final VelocityMode getMode() {
      Iterator var1 = ((Iterable)modes).iterator();

      Object var10000;
      while(true) {
         if (var1.hasNext()) {
            Object var2 = var1.next();
            VelocityMode it = (VelocityMode)var2;
            int var4 = 0;
            if (!INSTANCE.getModeValue().equals(it.getModeName())) {
               continue;
            }

            var10000 = (VelocityMode)var2;
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
      return modeValue;
   }

   @NotNull
   public final Value getHorizontal() {
      return horizontal;
   }

   @NotNull
   public final Value getVertical() {
      return vertical;
   }

   @NotNull
   public final Value getChance() {
      return chance;
   }

   @NotNull
   public final MSTimer getVelocityTimer() {
      return velocityTimer;
   }

   public final boolean getVelocityInput() {
      return velocityInput;
   }

   public final void setVelocityInput(boolean var1) {
      velocityInput = var1;
   }

   public void onEnable() {
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
      if (!MinecraftInstance.mc.field_71439_g.func_70090_H() && !MinecraftInstance.mc.field_71439_g.func_180799_ab() && !MinecraftInstance.mc.field_71439_g.field_70134_J) {
         if ((!(Boolean)og.get() || MinecraftInstance.mc.field_71439_g.field_70122_E) && (!(Boolean)oc.get() || CrossSine.INSTANCE.getCombatManager().getInCombat()) && (!(Boolean)om.get() || MovementUtils.INSTANCE.isMoving())) {
            if (!(Boolean)noFireValue.get() || !MinecraftInstance.mc.field_71439_g.func_70027_ad()) {
               this.getMode().onUpdate(event);
            }
         }
      }
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((!(Boolean)og.get() || MinecraftInstance.mc.field_71439_g.field_70122_E) && (!(Boolean)oc.get() || CrossSine.INSTANCE.getCombatManager().getInCombat()) && (!(Boolean)om.get() || MovementUtils.INSTANCE.isMoving())) {
         this.getMode().onPacket(event);
         Packet packet = event.getPacket();
         if (packet instanceof S12PacketEntityVelocity) {
            if (MinecraftInstance.mc.field_71439_g == null) {
               return;
            }

            WorldClient var10000 = MinecraftInstance.mc.field_71441_e;
            Entity var3 = var10000 == null ? null : var10000.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
            if (var3 == null) {
               return;
            }

            if (!Intrinsics.areEqual((Object)var3, (Object)MinecraftInstance.mc.field_71439_g)) {
               return;
            }

            if ((Boolean)noFireValue.get() && MinecraftInstance.mc.field_71439_g.func_70027_ad()) {
               return;
            }

            velocityTimer.reset();
            this.getMode().onVelocityPacket(event);
         }

      }
   }

   @NotNull
   public String getTag() {
      String var10000;
      if (Intrinsics.areEqual((Object)modeValue.get(), (Object)"Standard")) {
         String var1 = (String)m.get();
         var10000 = Intrinsics.areEqual((Object)var1, (Object)"Text") ? "Standard" : (Intrinsics.areEqual((Object)var1, (Object)"Percent") ? (new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH))).format(vertical.get()) + "% " + (new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH))).format(horizontal.get()) + '%' : "");
      } else {
         var10000 = (String)modeValue.get();
      }

      return var10000;
   }

   @NotNull
   public List getValues() {
      return values;
   }

   static {
      Iterable $this$map$iv = (Iterable)ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".velocitys"), VelocityMode.class);
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Class it = (Class)item$iv$iv;
         int var8 = 0;
         Object var10000 = it.newInstance();
         if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode");
         }

         destination$iv$iv.add((VelocityMode)var10000);
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = 0;
      modes = CollectionsKt.sortedWith($this$map$iv, new Velocity$special$$inlined$sortedBy$1());
      Iterable $this$map$iv = (Iterable)modes;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         VelocityMode it = (VelocityMode)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(it.getModeName());
      }

      Collection $this$toTypedArray$iv = (Collection)((List)destination$iv$iv);
      $i$f$map = 0;
      Object[] var41 = $this$toTypedArray$iv.toArray(new String[0]);
      if (var41 == null) {
         throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      } else {
         String[] var21 = (String[])var41;
         modeValue = new ListValue(var21) {
            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Velocity.INSTANCE.getState()) {
                  Velocity.INSTANCE.onDisable();
               }

            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
               Intrinsics.checkNotNullParameter(oldValue, "oldValue");
               Intrinsics.checkNotNullParameter(newValue, "newValue");
               if (Velocity.INSTANCE.getState()) {
                  Velocity.INSTANCE.onEnable();
               }

            }
         };
         horizontal = (new IntegerValue("Horizontal", 0, 0, 100)).displayable(null.INSTANCE);
         vertical = (new IntegerValue("Vertical", 0, 0, 100)).displayable(null.INSTANCE);
         chance = (new IntegerValue("Chance", 100, 0, 100)).displayable(null.INSTANCE);
         var21 = new String[]{"Text", "Percent"};
         m = (new ListValue("StandardTag", var21, "Text")).displayable(null.INSTANCE);
         og = new BoolValue("OnlyGround", false);
         oc = new BoolValue("OnlyCombat", false);
         om = new BoolValue("OnlyMove", false);
         noFireValue = new BoolValue("noFire", false);
         velocityTimer = new MSTimer();
         List var23 = CollectionsKt.toMutableList((Collection)INSTANCE.getValues());
         List it = var23;
         $i$f$map = 0;
         Iterable $this$map$iv = (Iterable)modes;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            final VelocityMode mode = (VelocityMode)item$iv$iv;
            int var12 = 0;
            Iterable $this$forEach$iv = (Iterable)mode.getValues();
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Value value = (Value)element$iv;
               int var18 = 0;
               it.add(value.displayable(new Function0() {
                  @NotNull
                  public final Boolean invoke() {
                     return Velocity.INSTANCE.getModeValue().equals(mode.getModeName());
                  }
               }));
            }

            destination$iv$iv.add(Unit.INSTANCE);
         }

         List var42 = (List)destination$iv$iv;
         values = var23;
      }
   }
}
