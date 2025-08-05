package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import me.liuli.path.Cell;
import me.liuli.path.Pathfinder;
import net.ccbluex.liquidbounce.utils.block.MinecraftWorldProvider;
import net.minecraft.util.Vec3;

public final class PathUtils extends MinecraftInstance {
   public static List findBlinkPath(double tpX, double tpY, double tpZ) {
      return findBlinkPath(tpX, tpY, tpZ, (double)5.0F);
   }

   public static List findBlinkPath(double tpX, double tpY, double tpZ, double dist) {
      return findBlinkPath(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, tpX, tpY, tpZ, dist);
   }

   public static List findBlinkPath(double curX, double curY, double curZ, double tpX, double tpY, double tpZ, double dashDistance) {
      MinecraftWorldProvider worldProvider = new MinecraftWorldProvider(mc.field_71441_e);
      Pathfinder pathfinder = new Pathfinder(new Cell((int)curX, (int)curY, (int)curZ), new Cell((int)tpX, (int)tpY, (int)tpZ), Pathfinder.COMMON_NEIGHBORS, worldProvider);
      return simplifyPath(pathfinder.findPath(3000), dashDistance, worldProvider);
   }

   public static ArrayList simplifyPath(ArrayList path, double dashDistance, MinecraftWorldProvider worldProvider) {
      ArrayList<Vec3> finalPath = new ArrayList();
      Cell cell = (Cell)path.get(0);
      Vec3 lastLoc = new Vec3((double)cell.x + (double)0.5F, (double)cell.y, (double)cell.z + (double)0.5F);
      Vec3 lastDashLoc = lastLoc;

      for(int i = 1; i < path.size() - 1; ++i) {
         cell = (Cell)path.get(i);
         Vec3 vec3 = new Vec3((double)cell.x + (double)0.5F, (double)cell.y, (double)cell.z + (double)0.5F);
         boolean canContinue = true;
         if (vec3.func_72436_e(lastDashLoc) > dashDistance * dashDistance) {
            canContinue = false;
         } else {
            double smallX = Math.min(lastDashLoc.field_72450_a, vec3.field_72450_a);
            double smallY = Math.min(lastDashLoc.field_72448_b, vec3.field_72448_b);
            double smallZ = Math.min(lastDashLoc.field_72449_c, vec3.field_72449_c);
            double bigX = Math.max(lastDashLoc.field_72450_a, vec3.field_72450_a);
            double bigY = Math.max(lastDashLoc.field_72448_b, vec3.field_72448_b);
            double bigZ = Math.max(lastDashLoc.field_72449_c, vec3.field_72449_c);

            label46:
            for(int x = (int)smallX; (double)x <= bigX; ++x) {
               for(int y = (int)smallY; (double)y <= bigY; ++y) {
                  for(int z = (int)smallZ; (double)z <= bigZ; ++z) {
                     if (worldProvider.isBlocked(x, y, z)) {
                        canContinue = false;
                        break label46;
                     }
                  }
               }
            }
         }

         if (!canContinue) {
            finalPath.add(lastLoc);
            lastDashLoc = lastLoc;
         }

         lastLoc = vec3;
      }

      return finalPath;
   }
}
