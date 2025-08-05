package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityDataIndex1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.EntityTracker;

public class HologramPartEntity {
   private static final float HORSE_HEIGHT = 1.6F;
   private static final float WITHER_SKULL_HEIGHT = 0.3125F;
   private final UserConnection user;
   private final EntityTracker entityTracker;
   private final int entityId;
   private final EntityTypes1_8.EntityType entityType;
   private HologramPartEntity riderEntity;
   private HologramPartEntity vehicleEntity;
   private Location location;
   private final Map entityData = new HashMap();
   private Integer mappedEntityId;

   public HologramPartEntity(UserConnection user, int entityId, EntityTypes1_8.EntityType entityType) {
      this.user = user;
      this.entityTracker = (EntityTracker)this.user.get(EntityTracker.class);
      this.entityId = entityId;
      this.entityType = entityType;
      this.location = new Location(Double.NaN, Double.NaN, Double.NaN);
      if (entityType == EntityTypes1_8.EntityType.HORSE) {
         this.entityData.put(EntityDataIndex1_7_6.ENTITY_FLAGS, (byte)0);
         this.entityData.put(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG_VISIBILITY, (byte)0);
         this.entityData.put(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG, "");
         this.entityData.put(EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE, 0);
      }

   }

   public void onChange() {
      if (this.vehicleEntity == null && this.riderEntity != null) {
         this.riderEntity.setPositionFromVehicle();
      }

      if (this.isHologram()) {
         if (this.wouldBeInvisible()) {
            this.destroyHologramPartEntities();
            this.destroyArmorStand();
            return;
         }

         if (this.mappedEntityId == null) {
            this.mappedEntityId = this.entityTracker.getNextMappedEntityId();
            this.entityTracker.getVirtualHolograms().put(this.mappedEntityId, this);
            this.destroyHologramPartEntities();
            this.spawnArmorStand();
         }

         this.updateArmorStand();
      } else if (this.mappedEntityId != null) {
         this.onRemove();
      }

   }

   public void onRemove() {
      if (this.mappedEntityId != null) {
         this.entityTracker.getVirtualHolograms().remove(this.mappedEntityId);
         this.destroyArmorStand();
         this.spawnHologramPartEntities();
         this.mappedEntityId = null;
      }

   }

   public void relocate(int newMappedEntityId) {
      this.destroyArmorStand();
      this.mappedEntityId = newMappedEntityId;
      this.spawnArmorStand();
      this.updateArmorStand();
   }

   private void spawnHologramPartEntities() {
      PacketWrapper spawnMob = PacketWrapper.create(ClientboundPackets1_8.ADD_MOB, (UserConnection)this.user);
      spawnMob.write(Types.VAR_INT, this.entityId);
      spawnMob.write(Types.UNSIGNED_BYTE, (short)this.entityType.getId());
      spawnMob.write(Types.INT, (int)(this.location.getX() * (double)32.0F));
      spawnMob.write(Types.INT, (int)(this.location.getY() * (double)32.0F));
      spawnMob.write(Types.INT, (int)(this.location.getZ() * (double)32.0F));
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types1_8.ENTITY_DATA_LIST, this.get1_8EntityData());
      spawnMob.send(Protocolr1_7_6_10Tor1_8.class);
      if (this.vehicleEntity != null) {
         int objectId = (Integer)Arrays.stream(EntityTypes1_8.ObjectType.values()).filter((o) -> o.getType() == this.vehicleEntity.entityType).map(EntityTypes1_8.ObjectType::getId).findFirst().orElse(-1);
         if (objectId == -1) {
            EntityTypes1_8.EntityType var6 = this.vehicleEntity.entityType;
            throw new IllegalStateException("Could not find object id for entity type " + var6);
         }

         PacketWrapper spawnEntity = PacketWrapper.create(ClientboundPackets1_8.ADD_ENTITY, (UserConnection)this.user);
         spawnEntity.write(Types.VAR_INT, this.vehicleEntity.entityId);
         spawnEntity.write(Types.BYTE, (byte)objectId);
         spawnEntity.write(Types.INT, (int)(this.vehicleEntity.location.getX() * (double)32.0F));
         spawnEntity.write(Types.INT, (int)(this.vehicleEntity.location.getY() * (double)32.0F));
         spawnEntity.write(Types.INT, (int)(this.vehicleEntity.location.getZ() * (double)32.0F));
         spawnEntity.write(Types.BYTE, (byte)0);
         spawnEntity.write(Types.BYTE, (byte)0);
         spawnEntity.write(Types.INT, 0);
         spawnEntity.send(Protocolr1_7_6_10Tor1_8.class);
         PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_DATA, (UserConnection)this.user);
         setEntityData.write(Types.VAR_INT, this.vehicleEntity.entityId);
         setEntityData.write(Types1_8.ENTITY_DATA_LIST, this.vehicleEntity.get1_8EntityData());
         setEntityData.send(Protocolr1_7_6_10Tor1_8.class);
         PacketWrapper attachEntity = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_LINK, (UserConnection)this.user);
         attachEntity.write(Types.INT, this.entityId);
         attachEntity.write(Types.INT, this.vehicleEntity.entityId);
         attachEntity.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
         attachEntity.send(Protocolr1_7_6_10Tor1_8.class);
      }

   }

   private void destroyHologramPartEntities() {
      PacketWrapper destroyEntities = PacketWrapper.create(ClientboundPackets1_8.REMOVE_ENTITIES, (UserConnection)this.user);
      destroyEntities.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.entityId, this.vehicleEntity.entityId});
      destroyEntities.scheduleSend(Protocolr1_7_6_10Tor1_8.class);
   }

   private void spawnArmorStand() {
      PacketWrapper spawnMob = PacketWrapper.create(ClientboundPackets1_8.ADD_MOB, (UserConnection)this.user);
      spawnMob.write(Types.VAR_INT, this.mappedEntityId);
      spawnMob.write(Types.UNSIGNED_BYTE, (short)EntityTypes1_8.EntityType.ARMOR_STAND.getId());
      spawnMob.write(Types.INT, (int)(this.location.getX() * (double)32.0F));
      spawnMob.write(Types.INT, (int)((this.location.getY() + (double)this.getHeight()) * (double)32.0F));
      spawnMob.write(Types.INT, (int)(this.location.getZ() * (double)32.0F));
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.BYTE, (byte)0);
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types.SHORT, Short.valueOf((short)0));
      spawnMob.write(Types1_8.ENTITY_DATA_LIST, this.getArmorStandEntityData());
      spawnMob.send(Protocolr1_7_6_10Tor1_8.class);
   }

   private void destroyArmorStand() {
      if (this.mappedEntityId != null) {
         PacketWrapper destroyEntities = PacketWrapper.create(ClientboundPackets1_8.REMOVE_ENTITIES, (UserConnection)this.user);
         destroyEntities.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[]{this.mappedEntityId});
         destroyEntities.send(Protocolr1_7_6_10Tor1_8.class);
      }
   }

   private void updateArmorStand() {
      if (this.mappedEntityId != null) {
         PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_DATA, (UserConnection)this.user);
         setEntityData.write(Types.VAR_INT, this.mappedEntityId);
         setEntityData.write(Types1_8.ENTITY_DATA_LIST, this.getArmorStandEntityData());
         setEntityData.send(Protocolr1_7_6_10Tor1_8.class);
         PacketWrapper entityTeleport = PacketWrapper.create(ClientboundPackets1_8.TELEPORT_ENTITY, (UserConnection)this.user);
         entityTeleport.write(Types.VAR_INT, this.mappedEntityId);
         entityTeleport.write(Types.INT, (int)(this.location.getX() * (double)32.0F));
         entityTeleport.write(Types.INT, (int)((this.location.getY() + (double)this.getHeight()) * (double)32.0F));
         entityTeleport.write(Types.INT, (int)(this.location.getZ() * (double)32.0F));
         entityTeleport.write(Types.BYTE, (byte)0);
         entityTeleport.write(Types.BYTE, (byte)0);
         entityTeleport.write(Types.BOOLEAN, false);
         entityTeleport.send(Protocolr1_7_6_10Tor1_8.class);
      }
   }

   private boolean isHologram() {
      if (this.entityType != EntityTypes1_8.EntityType.HORSE) {
         return false;
      } else if (this.vehicleEntity == null) {
         return false;
      } else if (this.riderEntity != null) {
         return false;
      } else if (this.vehicleEntity.riderEntity != this) {
         return false;
      } else if (this.vehicleEntity.vehicleEntity != null) {
         return false;
      } else {
         return (Integer)this.getEntityData(EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE) <= -44000;
      }
   }

   private boolean wouldBeInvisible() {
      if (this.entityType != EntityTypes1_8.EntityType.HORSE) {
         return false;
      } else {
         int age = (Integer)this.getEntityData(EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE);
         return age >= -50000;
      }
   }

   private float getHeight() {
      if (this.entityType == EntityTypes1_8.EntityType.HORSE) {
         int age = (Integer)this.getEntityData(EntityDataIndex1_7_6.ENTITY_AGEABLE_AGE);
         float size = age >= 0 ? 1.0F : 0.5F + (-24000.0F - (float)age) / -24000.0F * 0.5F;
         return 1.6F * size;
      } else {
         return 0.3125F;
      }
   }

   private void setPositionFromVehicle() {
      if (this.vehicleEntity != null) {
         this.location = new Location(this.vehicleEntity.location.getX(), this.vehicleEntity.location.getY() + (double)(this.vehicleEntity.getHeight() * 0.75F), this.vehicleEntity.location.getZ());
      }

      if (this.riderEntity != null) {
         this.riderEntity.setPositionFromVehicle();
      }

   }

   public int getEntityId() {
      return this.entityId;
   }

   public EntityTypes1_8.EntityType getEntityType() {
      return this.entityType;
   }

   public void setVehicleEntity(HologramPartEntity vehicleEntity) {
      if (vehicleEntity == null) {
         if (this.vehicleEntity != null) {
            this.location = this.vehicleEntity.location;
            this.location = new Location(this.location.getX(), this.location.getY() + (double)(this.vehicleEntity.entityType == EntityTypes1_8.EntityType.HORSE ? 1.6F : 0.3125F), this.location.getZ());
            this.vehicleEntity.riderEntity = null;
            this.vehicleEntity.onChange();
         }

         this.vehicleEntity = null;
      } else {
         if (this.vehicleEntity != null) {
            this.vehicleEntity.riderEntity = null;
            this.vehicleEntity.onChange();
         }

         for(HologramPartEntity entity = vehicleEntity.vehicleEntity; entity != null; entity = entity.riderEntity) {
            if (entity == this) {
               return;
            }
         }

         this.vehicleEntity = vehicleEntity;
         vehicleEntity.riderEntity = this;
         vehicleEntity.onChange();
      }

      this.onChange();
   }

   public HologramPartEntity getVehicleEntity() {
      return this.vehicleEntity;
   }

   public HologramPartEntity getRiderEntity() {
      return this.riderEntity;
   }

   public void setLocation(Location location) {
      this.location = location;
      this.onChange();
   }

   public Location getLocation() {
      return this.location;
   }

   public void setEntityData(EntityDataIndex1_7_6 index, Object value) {
      this.entityData.put(index, value);
   }

   public Object getEntityData(EntityDataIndex1_7_6 index) {
      return this.entityData.get(index);
   }

   private List get1_8EntityData() {
      List<EntityData> entityDataList = new ArrayList();

      for(Map.Entry entry : this.entityData.entrySet()) {
         entityDataList.add(new EntityData(((EntityDataIndex1_7_6)entry.getKey()).getOldIndex(), ((EntityDataIndex1_7_6)entry.getKey()).getOldType(), entry.getValue()));
      }

      ((Protocolr1_7_6_10Tor1_8)Via.getManager().getProtocolManager().getProtocol(Protocolr1_7_6_10Tor1_8.class)).getEntityDataRewriter().transform(this.user, this.entityType, entityDataList);
      return entityDataList;
   }

   private List getArmorStandEntityData() {
      List<EntityData> entityDataList = new ArrayList();
      if (this.entityType == EntityTypes1_8.EntityType.HORSE) {
         entityDataList.add(new EntityData(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG_VISIBILITY.getNewIndex(), EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG_VISIBILITY.getNewType(), this.getEntityData(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG_VISIBILITY)));
         entityDataList.add(new EntityData(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG.getNewIndex(), EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG.getNewType(), this.getEntityData(EntityDataIndex1_7_6.ENTITY_LIVING_NAME_TAG)));
         entityDataList.add(new EntityData(EntityDataIndex1_7_6.ENTITY_FLAGS.getNewIndex(), EntityDataIndex1_7_6.ENTITY_FLAGS.getNewType(), (byte)32));
         entityDataList.add(new EntityData(EntityDataIndex1_9.ARMOR_STAND_INFO.getIndex(), EntityDataIndex1_9.ARMOR_STAND_INFO.getOldType(), (byte)16));
      }

      return entityDataList;
   }
}
