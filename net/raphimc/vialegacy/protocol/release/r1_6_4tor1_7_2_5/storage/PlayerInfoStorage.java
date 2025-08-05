package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerInfoStorage implements StorableObject {
   public int entityId = -1;
   public boolean onGround = false;
   public double posX = (double)8.0F;
   public double posY = (double)64.0F;
   public double posZ = (double)8.0F;
   public float yaw = -180.0F;
   public float pitch = 0.0F;
}
