package com.viaversion.viarewind.api.minecraft.math;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Vector;

/** @deprecated */
@Deprecated
public class RelativeMoveUtil {
   public static Vector[] calculateRelativeMoves(UserConnection user, int entityId, int relX, int relY, int relZ) {
      EntityTracker1_9 tracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
      Vector offset = tracker.getEntityOffset(entityId);
      if (offset != null) {
         relX += offset.blockX();
         relY += offset.blockY();
         relZ += offset.blockZ();
      }

      int x;
      if (relX > 32767) {
         x = relX - 32767;
         relX = 32767;
      } else if (relX < -32768) {
         x = relX - Short.MIN_VALUE;
         relX = -32768;
      } else {
         x = 0;
      }

      int y;
      if (relY > 32767) {
         y = relY - 32767;
         relY = 32767;
      } else if (relY < -32768) {
         y = relY - Short.MIN_VALUE;
         relY = -32768;
      } else {
         y = 0;
      }

      int z;
      if (relZ > 32767) {
         z = relZ - 32767;
         relZ = 32767;
      } else if (relZ < -32768) {
         z = relZ - Short.MIN_VALUE;
         relZ = -32768;
      } else {
         z = 0;
      }

      int sentRelX;
      int sentRelY;
      int sentRelZ;
      Vector[] moves;
      if (relX <= 16256 && relX >= -16384 && relY <= 16256 && relY >= -16384 && relZ <= 16256 && relZ >= -16384) {
         sentRelX = Math.round((float)relX / 128.0F);
         sentRelY = Math.round((float)relY / 128.0F);
         sentRelZ = Math.round((float)relZ / 128.0F);
         moves = new Vector[]{new Vector(sentRelX, sentRelY, sentRelZ)};
      } else {
         byte relX1 = (byte)(relX / 256);
         byte relX2 = (byte)Math.round((float)(relX - relX1 * 128) / 128.0F);
         byte relY1 = (byte)(relY / 256);
         byte relY2 = (byte)Math.round((float)(relY - relY1 * 128) / 128.0F);
         byte relZ1 = (byte)(relZ / 256);
         byte relZ2 = (byte)Math.round((float)(relZ - relZ1 * 128) / 128.0F);
         sentRelX = relX1 + relX2;
         sentRelY = relY1 + relY2;
         sentRelZ = relZ1 + relZ2;
         moves = new Vector[]{new Vector(relX1, relY1, relZ1), new Vector(relX2, relY2, relZ2)};
      }

      x = x + relX - sentRelX * 128;
      y = y + relY - sentRelY * 128;
      z = z + relZ - sentRelZ * 128;
      tracker.setEntityOffset(entityId, new Vector(x, y, z));
      return moves;
   }
}
