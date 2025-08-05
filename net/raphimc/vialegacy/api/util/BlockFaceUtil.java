package net.raphimc.vialegacy.api.util;

import com.viaversion.viaversion.api.minecraft.BlockFace;

public class BlockFaceUtil {
   public static BlockFace getFace(int direction) {
      BlockFace var10000;
      switch (direction) {
         case 0:
            var10000 = BlockFace.BOTTOM;
            break;
         case 1:
         default:
            var10000 = BlockFace.TOP;
            break;
         case 2:
            var10000 = BlockFace.NORTH;
            break;
         case 3:
            var10000 = BlockFace.SOUTH;
            break;
         case 4:
            var10000 = BlockFace.WEST;
            break;
         case 5:
            var10000 = BlockFace.EAST;
      }

      return var10000;
   }
}
