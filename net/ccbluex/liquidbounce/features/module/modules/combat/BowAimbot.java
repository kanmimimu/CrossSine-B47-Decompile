package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "BowAimbot",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "markValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "predictSizeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "predictValue", "priorityValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "silentValue", "target", "Lnet/minecraft/entity/Entity;", "throughWallsValue", "getTarget", "throughWalls", "", "priorityMode", "", "hasTarget", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"}
)
public final class BowAimbot extends Module {
   @NotNull
   public static final BowAimbot INSTANCE = new BowAimbot();
   @NotNull
   private static final BoolValue silentValue = new BoolValue("Silent", true);
   @NotNull
   private static final BoolValue predictValue = new BoolValue("Predict", true);
   @NotNull
   private static final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
   @NotNull
   private static final FloatValue predictSizeValue = new FloatValue("PredictSize", 2.0F, 0.1F, 5.0F);
   @NotNull
   private static final ListValue priorityValue;
   @NotNull
   private static final BoolValue markValue;
   @Nullable
   private static Entity target;

   private BowAimbot() {
   }

   public void onDisable() {
      target = null;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      target = null;
      ItemStack var10000 = MinecraftInstance.mc.field_71439_g.func_71011_bu();
      if ((var10000 == null ? null : var10000.func_77973_b()) instanceof ItemBow) {
         Entity var3 = this.getTarget((Boolean)throughWallsValue.get(), (String)priorityValue.get());
         if (var3 == null) {
            return;
         }

         Entity entity = var3;
         target = entity;
         RotationUtils.faceBow(target, (Boolean)silentValue.get(), (Boolean)predictValue.get(), ((Number)predictSizeValue.get()).floatValue());
      }

   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (target != null && !priorityValue.equals("Multi") && (Boolean)markValue.get()) {
         RenderUtils.drawPlatform(target, new Color(37, 126, 255, 70));
      }

   }

   private final Entity getTarget(boolean throughWalls, String priorityMode) {
      List var4 = MinecraftInstance.mc.field_71441_e.field_72996_f;
      Intrinsics.checkNotNullExpressionValue(var4, "mc.theWorld.loadedEntityList");
      Iterable $this$filter$iv = (Iterable)var4;
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         Entity it = (Entity)element$iv$iv;
         int var14 = 0;
         if (it instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(it, true) && (throughWalls || MinecraftInstance.mc.field_71439_g.func_70685_l(it))) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      List targets = (List)destination$iv$iv;
      Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toUpperCase(Locale.ROOT)");
      Entity var49;
      switch ($this$filterTo$iv$iv) {
         case "DISTANCE":
            Iterable $this$minByOrNull$iv = (Iterable)targets;
            int $i$f$minByOrNull = 0;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            Object var51;
            if (!iterator$iv.hasNext()) {
               var51 = null;
            } else {
               Object minElem$iv = iterator$iv.next();
               if (!iterator$iv.hasNext()) {
                  var51 = minElem$iv;
               } else {
                  Entity it = (Entity)minElem$iv;
                  int var38 = 0;
                  float minValue$iv = MinecraftInstance.mc.field_71439_g.func_70032_d(it);

                  do {
                     Object e$iv = iterator$iv.next();
                     Entity it = (Entity)e$iv;
                     int var48 = 0;
                     float v$iv = MinecraftInstance.mc.field_71439_g.func_70032_d(it);
                     if (Float.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                     }
                  } while(iterator$iv.hasNext());

                  var51 = minElem$iv;
               }
            }

            var49 = (Entity)var51;
            return var49;
            break;
         case "DIRECTION":
            Iterable $this$minByOrNull$iv = (Iterable)targets;
            int $i$f$minByOrNull = 0;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            Object var50;
            if (!iterator$iv.hasNext()) {
               var50 = null;
            } else {
               Object minElem$iv = iterator$iv.next();
               if (!iterator$iv.hasNext()) {
                  var50 = minElem$iv;
               } else {
                  Entity it = (Entity)minElem$iv;
                  int var36 = 0;
                  double minValue$iv = RotationUtils.getRotationDifference(it);

                  do {
                     Object e$iv = iterator$iv.next();
                     Entity it = (Entity)e$iv;
                     int var47 = 0;
                     double v$iv = RotationUtils.getRotationDifference(it);
                     if (Double.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                     }
                  } while(iterator$iv.hasNext());

                  var50 = minElem$iv;
               }
            }

            var49 = (Entity)var50;
            return var49;
            break;
         case "HEALTH":
            Iterable $this$minByOrNull$iv = (Iterable)targets;
            int $i$f$minByOrNull = 0;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            Object var10000;
            if (!iterator$iv.hasNext()) {
               var10000 = null;
            } else {
               Object minElem$iv = iterator$iv.next();
               if (!iterator$iv.hasNext()) {
                  var10000 = minElem$iv;
               } else {
                  Entity it = (Entity)minElem$iv;
                  int var34 = 0;
                  if (it == null) {
                     throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                  }

                  float minValue$iv = ((EntityLivingBase)it).func_110143_aJ();

                  do {
                     Object e$iv = iterator$iv.next();
                     Entity it = (Entity)e$iv;
                     int var46 = 0;
                     if (it == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                     }

                     float v$iv = ((EntityLivingBase)it).func_110143_aJ();
                     if (Float.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                     }
                  } while(iterator$iv.hasNext());

                  var10000 = minElem$iv;
               }
            }

            var49 = (Entity)var10000;
            return var49;
      }

      var49 = null;
      return var49;
   }

   public final boolean hasTarget() {
      return target != null && MinecraftInstance.mc.field_71439_g.func_70685_l(target);
   }

   static {
      String[] var0 = new String[]{"Health", "Distance", "Direction"};
      priorityValue = new ListValue("Priority", var0, "Direction");
      markValue = new BoolValue("Mark", true);
   }
}
