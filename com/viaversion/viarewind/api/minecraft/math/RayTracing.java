package com.viaversion.viarewind.api.minecraft.math;

public class RayTracing {
   public static Vector3d trace(Ray3d ray, AABB aabb, double distance) {
      Vector3d invDir = new Vector3d((double)1.0F / ray.dir.x, (double)1.0F / ray.dir.y, (double)1.0F / ray.dir.z);
      boolean signDirX = invDir.x < (double)0.0F;
      boolean signDirY = invDir.y < (double)0.0F;
      boolean signDirZ = invDir.z < (double)0.0F;
      Vector3d bbox = signDirX ? aabb.max : aabb.min;
      double tmin = (bbox.x - ray.start.x) * invDir.x;
      bbox = signDirX ? aabb.min : aabb.max;
      double tmax = (bbox.x - ray.start.x) * invDir.x;
      bbox = signDirY ? aabb.max : aabb.min;
      double tymin = (bbox.y - ray.start.y) * invDir.y;
      bbox = signDirY ? aabb.min : aabb.max;
      double tymax = (bbox.y - ray.start.y) * invDir.y;
      if (!(tmin > tymax) && !(tymin > tmax)) {
         if (tymin > tmin) {
            tmin = tymin;
         }

         if (tymax < tmax) {
            tmax = tymax;
         }

         bbox = signDirZ ? aabb.max : aabb.min;
         double tzmin = (bbox.z - ray.start.z) * invDir.z;
         bbox = signDirZ ? aabb.min : aabb.max;
         double tzmax = (bbox.z - ray.start.z) * invDir.z;
         if (!(tmin > tzmax) && !(tzmin > tmax)) {
            if (tzmin > tmin) {
               tmin = tzmin;
            }

            if (tzmax < tmax) {
               tmax = tzmax;
            }

            return tmin <= distance && tmax > (double)0.0F ? ray.start.clone().add(ray.dir.clone().normalize().multiply(tmin)) : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }
}
