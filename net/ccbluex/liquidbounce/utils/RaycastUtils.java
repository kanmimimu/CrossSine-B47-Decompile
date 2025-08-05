package net.ccbluex.liquidbounce.utils;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class RaycastUtils extends MinecraftInstance {
   public static Entity raycastEntity(double range, IEntityFilter entityFilter) {
      return raycastEntity(range, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), entityFilter);
   }

   private static Entity raycastEntity(double range, float yaw, float pitch, IEntityFilter entityFilter) {
      Entity renderViewEntity = mc.func_175606_aa();
      if (renderViewEntity != null && mc.field_71441_e != null) {
         double blockReachDistance = range;
         Vec3 eyePosition = renderViewEntity.func_174824_e(1.0F);
         float yawCos = MathHelper.func_76134_b(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
         float yawSin = MathHelper.func_76126_a(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
         float pitchCos = -MathHelper.func_76134_b(-pitch * ((float)Math.PI / 180F));
         float pitchSin = MathHelper.func_76126_a(-pitch * ((float)Math.PI / 180F));
         Vec3 entityLook = new Vec3((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
         Vec3 vector = eyePosition.func_72441_c(entityLook.field_72450_a * range, entityLook.field_72448_b * range, entityLook.field_72449_c * range);
         List<Entity> entityList = mc.field_71441_e.func_175674_a(renderViewEntity, renderViewEntity.func_174813_aQ().func_72321_a(entityLook.field_72450_a * range, entityLook.field_72448_b * range, entityLook.field_72449_c * range).func_72314_b((double)1.0F, (double)1.0F, (double)1.0F), Predicates.and(EntitySelectors.field_180132_d, Entity::func_70067_L));
         Entity pointedEntity = null;

         for(Entity entity : entityList) {
            if (entityFilter.canRaycast(entity)) {
               float collisionBorderSize = entity.func_70111_Y();
               AxisAlignedBB axisAlignedBB = entity.func_174813_aQ().func_72314_b((double)collisionBorderSize, (double)collisionBorderSize, (double)collisionBorderSize);
               MovingObjectPosition movingObjectPosition = axisAlignedBB.func_72327_a(eyePosition, vector);
               if (axisAlignedBB.func_72318_a(eyePosition)) {
                  if (blockReachDistance >= (double)0.0F) {
                     pointedEntity = entity;
                     blockReachDistance = (double)0.0F;
                  }
               } else if (movingObjectPosition != null) {
                  double eyeDistance = eyePosition.func_72438_d(movingObjectPosition.field_72307_f);
                  if (eyeDistance < blockReachDistance || blockReachDistance == (double)0.0F) {
                     if (entity == renderViewEntity.field_70154_o && !renderViewEntity.canRiderInteract()) {
                        if (blockReachDistance == (double)0.0F) {
                           pointedEntity = entity;
                        }
                     } else {
                        pointedEntity = entity;
                        blockReachDistance = eyeDistance;
                     }
                  }
               }
            }
         }

         return pointedEntity;
      } else {
         return null;
      }
   }

   public interface IEntityFilter {
      boolean canRaycast(Entity var1);
   }
}
