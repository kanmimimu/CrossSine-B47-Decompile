package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.EntityRewriterBase;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.util.function.Supplier;

public class EntityPositionHandler {
   public static final double RELATIVE_MOVE_FACTOR = (double)4096.0F;
   private final EntityRewriterBase entityRewriter;
   private final Class storageClass;
   private final Supplier storageSupplier;
   private boolean warnedForMissingEntity;

   public EntityPositionHandler(EntityRewriterBase entityRewriter, Class storageClass, Supplier storageSupplier) {
      this.entityRewriter = entityRewriter;
      this.storageClass = storageClass;
      this.storageSupplier = storageSupplier;
   }

   public void cacheEntityPosition(PacketWrapper wrapper, boolean create, boolean relative) {
      this.cacheEntityPosition(wrapper, (Double)wrapper.get(Types.DOUBLE, 0), (Double)wrapper.get(Types.DOUBLE, 1), (Double)wrapper.get(Types.DOUBLE, 2), create, relative);
   }

   public void cacheEntityPosition(PacketWrapper wrapper, double x, double y, double z, boolean create, boolean relative) {
      int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
      StoredEntityData storedEntity = this.entityRewriter.tracker(wrapper.user()).entityData(entityId);
      if (storedEntity != null) {
         EntityPositionStorage positionStorage;
         if (create) {
            positionStorage = (EntityPositionStorage)this.storageSupplier.get();
            storedEntity.put(positionStorage);
         } else {
            positionStorage = (EntityPositionStorage)storedEntity.get(this.storageClass);
            if (positionStorage == null) {
               ProtocolLogger var10000 = ((BackwardsProtocol)this.entityRewriter.protocol()).getLogger();
               String var24 = this.storageClass.getSimpleName();
               var10000.warning("Stored entity with id " + entityId + " missing " + var24);
               return;
            }
         }

         positionStorage.setCoordinates(x, y, z, relative);
      } else {
         if (Via.getManager().isDebug()) {
            ProtocolLogger logger = ((BackwardsProtocol)this.entityRewriter.protocol()).getLogger();
            String var21 = this.storageClass.getSimpleName();
            logger.warning("Stored entity with id " + entityId + " missing at position: " + x + " - " + y + " - " + z + " in " + var21);
            if (entityId == -1 && x == (double)0.0F && y == (double)0.0F && z == (double)0.0F) {
               logger.warning("DO NOT REPORT THIS TO VIA, THIS IS A PLUGIN ISSUE");
            } else if (!this.warnedForMissingEntity) {
               this.warnedForMissingEntity = true;
               logger.warning("This is very likely caused by a plugin sending a teleport packet for an entity outside of the player's range.");
            }
         }

      }
   }

   public EntityPositionStorage getStorage(UserConnection user, int entityId) {
      StoredEntityData storedEntity = this.entityRewriter.tracker(user).entityData(entityId);
      EntityPositionStorage entityStorage;
      if (storedEntity != null && (entityStorage = (EntityPositionStorage)storedEntity.get(EntityPositionStorage.class)) != null) {
         return entityStorage;
      } else {
         ProtocolLogger var10000 = ((BackwardsProtocol)this.entityRewriter.protocol()).getLogger();
         String var7 = this.storageClass.getSimpleName();
         var10000.warning("Untracked entity with id " + entityId + " in " + var7);
         return null;
      }
   }

   public static void writeFacingAngles(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
      double dX = targetX - x;
      double dY = targetY - y;
      double dZ = targetZ - z;
      double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
      double yaw = -Math.atan2(dX, dZ) / Math.PI * (double)180.0F;
      if (yaw < (double)0.0F) {
         yaw += (double)360.0F;
      }

      double pitch = -Math.asin(dY / r) / Math.PI * (double)180.0F;
      wrapper.write(Types.BYTE, (byte)((int)(yaw * (double)256.0F / (double)360.0F)));
      wrapper.write(Types.BYTE, (byte)((int)(pitch * (double)256.0F / (double)360.0F)));
   }

   public static void writeFacingDegrees(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
      double dX = targetX - x;
      double dY = targetY - y;
      double dZ = targetZ - z;
      double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
      double yaw = -Math.atan2(dX, dZ) / Math.PI * (double)180.0F;
      if (yaw < (double)0.0F) {
         yaw += (double)360.0F;
      }

      double pitch = -Math.asin(dY / r) / Math.PI * (double)180.0F;
      wrapper.write(Types.FLOAT, (float)yaw);
      wrapper.write(Types.FLOAT, (float)pitch);
   }
}
