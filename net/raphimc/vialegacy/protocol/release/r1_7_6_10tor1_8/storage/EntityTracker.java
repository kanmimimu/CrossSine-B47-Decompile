package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.HologramPartEntity;

public class EntityTracker extends StoredObject {
   private final Map entityMap = new ConcurrentHashMap();
   private final Map groundMap = new ConcurrentHashMap();
   private final Int2ObjectMap hologramParts = new Int2ObjectOpenHashMap();
   private final Int2ObjectMap virtualHolograms = new Int2ObjectOpenHashMap();
   private int playerID;

   public EntityTracker(UserConnection user) {
      super(user);
   }

   public int getPlayerID() {
      return this.playerID;
   }

   public void setPlayerID(int playerID) {
      this.playerID = playerID;
   }

   public Map getTrackedEntities() {
      return this.entityMap;
   }

   public Map getGroundMap() {
      return this.groundMap;
   }

   public Int2ObjectMap getVirtualHolograms() {
      return this.virtualHolograms;
   }

   public void trackEntity(int entityId, EntityTypes1_8.EntityType entityType) {
      if (this.virtualHolograms.containsKey(entityId)) {
         int newMappedEntityId = this.getNextMappedEntityId();
         HologramPartEntity hologramPartEntity = (HologramPartEntity)this.virtualHolograms.remove(entityId);
         hologramPartEntity.relocate(newMappedEntityId);
         this.hologramParts.put(newMappedEntityId, hologramPartEntity);
      }

      if (this.entityMap.containsKey(entityId)) {
         this.removeEntity(entityId);
      }

      this.entityMap.put(entityId, entityType);
      if (entityType == EntityTypes1_8.EntityType.HORSE || entityType == EntityTypes1_8.EntityType.WITHER_SKULL) {
         this.hologramParts.put(entityId, new HologramPartEntity(this.user(), entityId, entityType));
      }

   }

   public void removeEntity(int entityId) {
      this.entityMap.remove(entityId);
      this.groundMap.remove(entityId);
      HologramPartEntity removedEntity = (HologramPartEntity)this.hologramParts.remove(entityId);
      if (removedEntity != null) {
         if (removedEntity.getRiderEntity() != null) {
            removedEntity.getRiderEntity().setVehicleEntity((HologramPartEntity)null);
         }

         if (removedEntity.getVehicleEntity() != null) {
            removedEntity.setVehicleEntity((HologramPartEntity)null);
         }

         removedEntity.onRemove();
      }

   }

   public void updateEntityLocation(int entityId, int x, int y, int z, boolean relative) {
      HologramPartEntity entity = (HologramPartEntity)this.hologramParts.get(entityId);
      if (entity != null) {
         Location oldLoc = entity.getLocation();
         double xPos = (double)x / (double)32.0F;
         double yPos = (double)y / (double)32.0F;
         double zPos = (double)z / (double)32.0F;
         Location newLoc;
         if (relative) {
            newLoc = new Location(oldLoc.getX() + xPos, oldLoc.getY() + yPos, oldLoc.getZ() + zPos);
         } else {
            newLoc = new Location(xPos, yPos, zPos);
         }

         entity.setLocation(newLoc);
      }

   }

   public void updateEntityData(int entityId, List entityDataList) {
      HologramPartEntity entity = (HologramPartEntity)this.hologramParts.get(entityId);
      if (entity != null) {
         for(EntityData entityData : entityDataList) {
            EntityDataIndex1_7_6 entityDataIndex = EntityDataIndex1_7_6.searchIndex(entity.getEntityType(), entityData.id());
            if (entityDataIndex != null) {
               try {
                  entityData.setTypeAndValue(entityDataIndex.getOldType(), entityData.getValue());
               } catch (Throwable var8) {
                  continue;
               }

               entity.setEntityData(entityDataIndex, entityData.getValue());
            }
         }

         entity.onChange();
      }

   }

   public void updateEntityAttachState(int ridingId, int vehicleId) {
      HologramPartEntity ridingEntity = (HologramPartEntity)this.hologramParts.get(ridingId);
      if (ridingEntity != null) {
         ridingEntity.setVehicleEntity((HologramPartEntity)this.hologramParts.get(vehicleId));
      }

   }

   public void clear() {
      this.entityMap.clear();
      this.groundMap.clear();

      for(HologramPartEntity hologramPartEntity : this.hologramParts.values()) {
         hologramPartEntity.onRemove();
      }

      this.virtualHolograms.clear();
   }

   public int getNextMappedEntityId() {
      int id;
      do {
         id = ThreadLocalRandom.current().nextInt(1000000000, Integer.MAX_VALUE);
      } while(this.entityMap.containsKey(id) || this.virtualHolograms.containsKey(id));

      return id;
   }
}
