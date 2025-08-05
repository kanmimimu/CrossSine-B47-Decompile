package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@ModuleInfo(
   name = "Dot",
   category = ModuleCategory.VISUAL
)
public class Dot extends Module {
   private static final BoolValue onlyTarget = new BoolValue("OnlyRotation", false);

   @EventTarget
   public void onRender3D(Render3DEvent event) {
      double maxDistance = (double)3.0F;
      Vec3 startVec = mc.field_71439_g.func_174824_e(event.getPartialTicks());
      Vec3 lookVec = mc.field_71439_g.func_174806_f(RotationUtils.targetRotation != null ? (float)RotationUtils.smoothPitch : mc.field_71439_g.field_70125_A, RotationUtils.fakeRotation != null ? RotationUtils.fakeRotation.getYaw() : (RotationUtils.targetRotation != null ? (float)RotationUtils.smoothYaw : mc.field_71439_g.field_70177_z));
      Vec3 endVec = startVec.func_72441_c(lookVec.field_72450_a * maxDistance, lookVec.field_72448_b * maxDistance, lookVec.field_72449_c * maxDistance);
      MovingObjectPosition mop = mc.field_71441_e.func_147447_a(startVec, endVec, false, true, false);
      if (mop != null && mop.field_72313_a == MovingObjectType.BLOCK) {
         maxDistance = mop.field_72307_f.func_72438_d(startVec);
      } else {
         Entity pointedEntity = this.getPointedEntity(mc.field_71439_g, maxDistance, event.getPartialTicks());
         if (pointedEntity != null) {
            maxDistance = (double)mc.field_71439_g.func_70032_d(pointedEntity);
         }
      }

      Vec3 dotVec = startVec.func_72441_c(lookVec.field_72450_a * maxDistance, lookVec.field_72448_b * maxDistance, lookVec.field_72449_c * maxDistance);
      if (!(Boolean)onlyTarget.get() || RotationUtils.targetRotation != null || RotationUtils.fakeRotation != null) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         this.renderDot(dotVec.field_72450_a - mc.func_175598_ae().field_78730_l, dotVec.field_72448_b - mc.func_175598_ae().field_78731_m, dotVec.field_72449_c - mc.func_175598_ae().field_78728_n);
         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }

   }

   private Entity getPointedEntity(Entity entity, double range, float partialTicks) {
      Entity pointedEntity = null;
      Vec3 startVec = entity.func_174824_e(partialTicks);
      Vec3 lookVec = entity.func_70676_i(partialTicks);
      Vec3 endVec = startVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
      float f1 = 1.0F;
      List<Entity> list = entity.field_70170_p.func_72839_b(entity, entity.func_174813_aQ().func_72321_a(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range).func_72314_b((double)f1, (double)f1, (double)f1));
      double d2 = (double)0.0F;

      for(Entity entity1 : list) {
         if (entity1.func_70067_L()) {
            float f2 = entity1.func_70111_Y();
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b((double)f2, (double)f2, (double)f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(startVec, endVec);
            if (axisalignedbb.func_72318_a(startVec)) {
               if ((double)0.0F < d2 || d2 == (double)0.0F) {
                  pointedEntity = entity1;
                  d2 = (double)0.0F;
               }
            } else if (movingobjectposition != null) {
               double d3 = startVec.func_72438_d(movingobjectposition.field_72307_f);
               if (d3 < d2 || d2 == (double)0.0F) {
                  if (entity1 == entity.field_70154_o && !entity.canRiderInteract()) {
                     if (d2 == (double)0.0F) {
                        pointedEntity = entity1;
                     }
                  } else {
                     pointedEntity = entity1;
                     d2 = d3;
                  }
               }
            }
         }
      }

      return pointedEntity;
   }

   private void renderDot(double x, double y, double z) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b(x, y, z);
      float size = 0.06F;
      AxisAlignedBB box = new AxisAlignedBB((double)(-size), (double)(-size), (double)(-size), (double)size, (double)size, (double)size);
      RenderUtils.drawAxisAlignedBB(box, ClientTheme.INSTANCE.getColorWithAlpha(0, 120, true), true, true, 2.0F);
      GlStateManager.func_179121_F();
      GlStateManager.func_179117_G();
   }
}
