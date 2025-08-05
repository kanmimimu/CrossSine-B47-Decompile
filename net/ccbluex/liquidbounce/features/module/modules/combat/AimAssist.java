package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AimAssist",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0015H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AimAssist;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "breakBlocks", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "faceCheck", "fovValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "hComSpeed", "hNorspeed", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onClickValue", "rangeValue", "vcomSpeed", "vnorspeed", "FovFromTarget", "", "tg", "Lnet/minecraft/entity/Entity;", "FovToTarget", "", "calculatePitchAdjustment", "target", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"}
)
public final class AimAssist extends Module {
   @NotNull
   private final FloatValue rangeValue = new FloatValue("Range", 5.0F, 1.0F, 10.0F);
   @NotNull
   private final ListValue mode;
   @NotNull
   private final FloatValue hNorspeed;
   @NotNull
   private final FloatValue hComSpeed;
   @NotNull
   private final FloatValue vnorspeed;
   @NotNull
   private final FloatValue vcomSpeed;
   @NotNull
   private final FloatValue fovValue;
   @NotNull
   private final BoolValue faceCheck;
   @NotNull
   private final BoolValue onClickValue;
   @NotNull
   private final BoolValue breakBlocks;
   @NotNull
   private final MSTimer clickTimer;

   public AimAssist() {
      String[] var1 = new String[]{"Center", "Head", "Full"};
      this.mode = new ListValue("Mode", var1, "Center");
      this.hNorspeed = new FloatValue("Horizontal-Speed", 1.0F, 1.0F, 60.0F);
      this.hComSpeed = new FloatValue("Horizontal-CompliSpeed", 1.0F, 1.0F, 50.0F);
      this.vnorspeed = new FloatValue("Vertical-Speed", 1.0F, 1.0F, 60.0F);
      this.vcomSpeed = new FloatValue("Vertical-CompliSpeed", 1.0F, 1.0F, 50.0F);
      this.fovValue = new FloatValue("FOV", 180.0F, 1.0F, 180.0F);
      this.faceCheck = new BoolValue("FaceCheck", false);
      this.onClickValue = new BoolValue("MouseDown", true);
      this.breakBlocks = new BoolValue("AllowBreakBlock", false);
      this.clickTimer = new MSTimer();
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if ((Boolean)this.breakBlocks.get() && MinecraftInstance.mc.field_71476_x != null) {
         BlockPos p = MinecraftInstance.mc.field_71476_x.func_178782_a();
         if (p != null) {
            Block bl = MinecraftInstance.mc.field_71441_e.func_180495_p(p).func_177230_c();
            Intrinsics.checkNotNullExpressionValue(bl, "mc.theWorld.getBlockState(p).block");
            if (!Intrinsics.areEqual((Object)bl, (Object)Blocks.field_150350_a) && !(bl instanceof BlockLiquid)) {
               return;
            }
         }
      }

      if (MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d()) {
         this.clickTimer.reset();
      }

      if (!(Boolean)this.onClickValue.get() || !this.clickTimer.hasTimePassed(100L)) {
         EntityPlayerSP var10000 = MinecraftInstance.mc.field_71439_g;
         if (var10000 != null) {
            EntityPlayerSP player = var10000;
            float range = ((Number)this.rangeValue.get()).floatValue();
            List var6 = MinecraftInstance.mc.field_71441_e.field_72996_f;
            Intrinsics.checkNotNullExpressionValue(var6, "mc.theWorld.loadedEntityList");
            Iterable $this$minByOrNull$iv = (Iterable)var6;
            int $i$f$filter = 0;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            int $i$f$filterTo = 0;

            for(Object element$iv$iv : $this$minByOrNull$iv) {
               Entity it = (Entity)element$iv$iv;
               int var16 = 0;
               EntityUtils var32 = EntityUtils.INSTANCE;
               Intrinsics.checkNotNullExpressionValue(it, "it");
               if (var32.isSelected(it, true) && player.func_70685_l(it) && EntityExtensionKt.getDistanceToEntityBox((Entity)player, it) <= (double)range && RotationUtils.getRotationDifference(it) <= (double)((Number)this.fovValue.get()).floatValue()) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $this$minByOrNull$iv = (Iterable)((List)destination$iv$iv);
            $i$f$filter = 0;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            Object var33;
            if (!iterator$iv.hasNext()) {
               var33 = null;
            } else {
               Object minElem$iv = iterator$iv.next();
               if (!iterator$iv.hasNext()) {
                  var33 = minElem$iv;
               } else {
                  Entity it = (Entity)minElem$iv;
                  int e$iv = 0;
                  double minValue$iv = RotationUtils.getRotationDifference(it);

                  do {
                     Object e$iv = iterator$iv.next();
                     Entity it = (Entity)e$iv;
                     int var31 = 0;
                     double v$iv = RotationUtils.getRotationDifference(it);
                     if (Double.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                     }
                  } while(iterator$iv.hasNext());

                  var33 = minElem$iv;
               }
            }

            Entity var5 = (Entity)var33;
            if (var5 != null) {
               Entity entity = var5;
               if (!(Boolean)this.faceCheck.get() || !RotationUtils.isFaced(var5, (double)range)) {
                  EntityPlayerSP var19 = MinecraftInstance.mc.field_71439_g;
                  var19.field_70177_z += (float)(-(this.FovFromTarget(entity) * (ThreadLocalRandom.current().nextDouble(((Number)this.hComSpeed.get()).doubleValue() - 1.47328, ((Number)this.hComSpeed.get()).doubleValue() + 2.48293) / (double)100) + this.FovFromTarget(entity) / ((double)101.0F - ThreadLocalRandom.current().nextDouble(((Number)this.hNorspeed.get()).doubleValue() - 4.723847, (double)((Number)this.hNorspeed.get()).floatValue()))));
                  if (!RotationUtils.isFaced(entity, (double)range) || !this.mode.equals("Full")) {
                     var19 = MinecraftInstance.mc.field_71439_g;
                     var19.field_70125_A += (float)this.calculatePitchAdjustment(entity);
                  }
               }
            }
         }
      }
   }

   private final double calculatePitchAdjustment(Entity target) {
      EntityPlayerSP player = MinecraftInstance.mc.field_71439_g;
      String var7 = ((String)this.mode.get()).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
      double targetY = Intrinsics.areEqual((Object)var7, (Object)"center") ? target.field_70163_u + (double)(target.field_70131_O / (float)2) : (Intrinsics.areEqual((Object)var7, (Object)"head") ? target.field_70163_u + (double)target.func_70047_e() : target.field_70163_u + (double)(target.field_70131_O / (float)2));
      double deltaY = targetY - (player.field_70163_u + (double)player.eyeHeight);
      double distanceXZ = player.func_70011_f(target.field_70165_t, player.field_70163_u, target.field_70161_v);
      double pitchToTarget = -Math.toDegrees(Math.atan2(deltaY, distanceXZ));
      double pitchDifference = pitchToTarget - (double)player.field_70125_A;
      double compliAdjustment = ThreadLocalRandom.current().nextDouble(((Number)this.vcomSpeed.get()).doubleValue() - (double)1.5F, ((Number)this.vcomSpeed.get()).doubleValue() + (double)2.5F) / (double)100;
      return pitchDifference / ((double)101.0F - ThreadLocalRandom.current().nextDouble(((Number)this.vnorspeed.get()).doubleValue() - (double)2.0F, (double)((Number)this.vnorspeed.get()).floatValue())) + pitchDifference * compliAdjustment;
   }

   private final double FovFromTarget(Entity tg) {
      return ((double)(MinecraftInstance.mc.field_71439_g.field_70177_z - this.FovToTarget(tg)) % (double)360.0F + (double)540.0F) % (double)360.0F - (double)180.0F;
   }

   private final float FovToTarget(Entity tg) {
      double x = tg.field_70165_t - MinecraftInstance.mc.field_71439_g.field_70165_t;
      double z = tg.field_70161_v - MinecraftInstance.mc.field_71439_g.field_70161_v;
      double yaw = Math.atan2(x, z) * 57.2957795;
      return (float)(yaw * (double)-1.0F);
   }
}
