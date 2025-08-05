package com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.UUID;

public final class StoredPainting implements StorableObject {
   private final int entityId;
   private final UUID uuid;
   private final BlockPosition position;
   private final byte direction;

   public StoredPainting(int entityId, UUID uuid, BlockPosition position, int direction3d) {
      this.entityId = entityId;
      this.uuid = uuid;
      this.position = position;
      this.direction = this.to2dDirection(direction3d);
   }

   public int entityId() {
      return this.entityId;
   }

   public UUID uuid() {
      return this.uuid;
   }

   public BlockPosition position() {
      return this.position;
   }

   public byte direction() {
      return this.direction;
   }

   private byte to2dDirection(int direction) {
      byte var10000;
      switch (direction) {
         case 0:
         case 1:
            var10000 = -1;
            break;
         case 2:
            var10000 = 2;
            break;
         case 3:
            var10000 = 0;
            break;
         case 4:
            var10000 = 1;
            break;
         case 5:
            var10000 = 3;
            break;
         default:
            throw new IllegalArgumentException("Invalid direction: " + direction);
      }

      return var10000;
   }
}
