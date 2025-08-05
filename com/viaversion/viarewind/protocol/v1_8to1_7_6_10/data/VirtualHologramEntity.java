package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data;

import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viarewind.api.minecraft.math.AABB;
import com.viaversion.viarewind.api.minecraft.math.Vector3d;
import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter.EntityPacketRewriter1_8;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.TrackedEntityImpl;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEventImpl;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public class VirtualHologramEntity {
   final List entityDataTracker = new ArrayList();
   double locX;
   double locY;
   double locZ;
   final UserConnection user;
   final int entityId;
   int[] entityIds = null;
   State currentState = null;
   String name = null;
   float yaw;
   float pitch;
   float headYaw;
   boolean small = false;
   boolean marker = false;

   public VirtualHologramEntity(UserConnection user, int entityId) {
      this.user = user;
      this.entityId = entityId;
   }

   public void setPosition(double x, double y, double z) {
      if (x != this.locX || y != this.locY || z != this.locZ) {
         this.locX = x;
         this.locY = y;
         this.locZ = z;
         this.updateLocation(false);
      }
   }

   public void setRelativePosition(double x, double y, double z) {
      if (x != (double)0.0F || y != (double)0.0F || z != (double)0.0F) {
         this.locX += x;
         this.locY += y;
         this.locZ += z;
         this.updateLocation(false);
      }
   }

   public void setRotation(float yaw, float pitch) {
      if (this.yaw != yaw && this.headYaw != yaw && this.pitch != pitch) {
         this.yaw = yaw;
         this.headYaw = yaw;
         this.pitch = pitch;
         this.updateLocation(false);
      }
   }

   public void setHeadYaw(float yaw) {
      if (this.headYaw != yaw) {
         this.headYaw = yaw;
         this.updateLocation(false);
      }
   }

   public void syncState(EntityPacketRewriter1_8 entityRewriter, List entityDataList) {
      for(EntityData entityData : entityDataList) {
         this.entityDataTracker.removeIf((m) -> m.id() == entityData.id());
         this.entityDataTracker.add(entityData);
      }

      byte flags = 0;
      byte armorStandFlags = 0;

      for(EntityData entityData : this.entityDataTracker) {
         if (entityData.id() == 0 && entityData.dataType() == EntityDataTypes1_8.BYTE) {
            flags = ((Number)entityData.getValue()).byteValue();
         } else if (entityData.id() == 2 && entityData.dataType() == EntityDataTypes1_8.STRING) {
            this.name = entityData.getValue().toString();
            if (this.name != null && this.name.isEmpty()) {
               this.name = null;
            }
         } else if (entityData.id() == 10 && entityData.dataType() == EntityDataTypes1_8.BYTE) {
            armorStandFlags = ((Number)entityData.getValue()).byteValue();
         }
      }

      boolean invisible = (flags & 32) != 0;
      this.small = (armorStandFlags & 1) != 0;
      this.marker = (armorStandFlags & 16) != 0;
      State prevState = this.currentState;
      if (invisible && this.name != null) {
         this.currentState = VirtualHologramEntity.State.HOLOGRAM;
      } else {
         this.currentState = VirtualHologramEntity.State.ZOMBIE;
      }

      if (this.currentState != prevState) {
         this.deleteEntity();
         this.sendSpawnPacket(entityRewriter);
      } else {
         this.sendEntityDataUpdate(entityRewriter);
         this.updateLocation(false);
      }

   }

   void updateLocation(boolean remount) {
      if (this.entityIds != null) {
         if (this.currentState == VirtualHologramEntity.State.ZOMBIE) {
            this.teleportEntity(this.entityId, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            PacketWrapper entityHeadLook = PacketWrapper.create(ClientboundPackets1_7_2_5.ROTATE_HEAD, (UserConnection)this.user);
            entityHeadLook.write(Types.INT, this.entityId);
            entityHeadLook.write(Types.BYTE, (byte)((int)(this.headYaw / 360.0F * 256.0F)));
            entityHeadLook.send(Protocol1_8To1_7_6_10.class);
         } else if (this.currentState == VirtualHologramEntity.State.HOLOGRAM) {
            if (remount) {
               PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_LINK, (UserConnection)this.user);
               detach.write(Types.INT, this.entityIds[1]);
               detach.write(Types.INT, -1);
               detach.write(Types.BOOLEAN, false);
               detach.send(Protocol1_8To1_7_6_10.class);
            }

            this.teleportEntity(this.entityIds[0], this.locX, this.locY + (this.marker ? 54.85 : (this.small ? (double)56.0F : (double)57.0F)) - 0.16, this.locZ, 0.0F, 0.0F);
            if (remount) {
               this.teleportEntity(this.entityIds[1], this.locX, this.locY + (double)56.75F, this.locZ, 0.0F, 0.0F);
               PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_LINK, (ByteBuf)null, this.user);
               attach.write(Types.INT, this.entityIds[1]);
               attach.write(Types.INT, this.entityIds[0]);
               attach.write(Types.BOOLEAN, false);
               attach.send(Protocol1_8To1_7_6_10.class);
            }
         }

      }
   }

   protected void teleportEntity(int entityId, double x, double y, double z, float yaw, float pitch) {
      PacketWrapper entityTeleport = PacketWrapper.create(ClientboundPackets1_7_2_5.TELEPORT_ENTITY, (UserConnection)this.user);
      entityTeleport.write(Types.INT, entityId);
      entityTeleport.write(Types.INT, (int)(x * (double)32.0F));
      entityTeleport.write(Types.INT, (int)(y * (double)32.0F));
      entityTeleport.write(Types.INT, (int)(z * (double)32.0F));
      entityTeleport.write(Types.BYTE, (byte)((int)(yaw / 360.0F * 256.0F)));
      entityTeleport.write(Types.BYTE, (byte)((int)(pitch / 360.0F * 256.0F)));
      entityTeleport.send(Protocol1_8To1_7_6_10.class);
   }

   protected void spawnEntity(int entityId, int type, double locX, double locY, double locZ) {
      PacketWrapper addMob = PacketWrapper.create(ClientboundPackets1_7_2_5.ADD_MOB, (UserConnection)this.user);
      addMob.write(Types.VAR_INT, entityId);
      addMob.write(Types.UNSIGNED_BYTE, (short)type);
      addMob.write(Types.INT, (int)(locX * (double)32.0F));
      addMob.write(Types.INT, (int)(locY * (double)32.0F));
      addMob.write(Types.INT, (int)(locZ * (double)32.0F));
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.BYTE, (byte)0);
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      addMob.write(Types.SHORT, Short.valueOf((short)0));
      addMob.write(Types1_7_6_10.ENTITY_DATA_LIST, new ArrayList());
      addMob.send(Protocol1_8To1_7_6_10.class);
   }

   public void sendEntityDataUpdate(EntityPacketRewriter1_8 entityRewriter) {
      if (this.entityIds != null) {
         PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_DATA, (UserConnection)this.user);
         if (this.currentState == VirtualHologramEntity.State.ZOMBIE) {
            this.writeZombieMeta(entityRewriter, setEntityData);
         } else {
            if (this.currentState != VirtualHologramEntity.State.HOLOGRAM) {
               return;
            }

            this.writeHologramMeta(setEntityData);
         }

         setEntityData.send(Protocol1_8To1_7_6_10.class);
      }
   }

   void writeZombieMeta(EntityPacketRewriter1_8 entityRewriter, PacketWrapper wrapper) {
      wrapper.write(Types.INT, this.entityIds[0]);
      List<EntityData> entityDataList = new ArrayList();

      for(EntityData entityData : this.entityDataTracker) {
         if (entityData.id() >= 0 && entityData.id() <= 9) {
            entityDataList.add(new EntityData(entityData.id(), entityData.dataType(), entityData.getValue()));
         }
      }

      if (this.small) {
         entityDataList.add(new EntityData(12, EntityDataTypes1_8.BYTE, (byte)1));
      }

      for(EntityData entityData : (EntityData[])entityDataList.toArray(new EntityData[0])) {
         EntityDataHandlerEvent event = new EntityDataHandlerEventImpl(wrapper.user(), new TrackedEntityImpl(EntityTypes1_8.EntityType.ZOMBIE), -1, entityData, entityDataList);

         try {
            entityRewriter.handleEntityData(event, entityData);
         } catch (Exception var10) {
            entityDataList.remove(entityData);
            break;
         }

         if (event.cancelled()) {
            entityDataList.remove(entityData);
            break;
         }
      }

      wrapper.write(Types1_7_6_10.ENTITY_DATA_LIST, entityDataList);
   }

   void writeHologramMeta(PacketWrapper wrapper) {
      wrapper.write(Types.INT, this.entityIds[1]);
      List<EntityData> entityDataList = new ArrayList();
      entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.ABSTRACT_AGEABLE_AGE.getIndex(), EntityDataTypes1_7_6_10.INT, -1700000));
      entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.LIVING_ENTITY_BASE_NAME_TAG.getIndex(), EntityDataTypes1_7_6_10.STRING, this.name));
      entityDataList.add(new EntityData(EntityDataIndex1_7_6_10.LIVING_ENTITY_BASE_NAME_TAG_VISIBILITY.getIndex(), EntityDataTypes1_7_6_10.BYTE, (byte)1));
      wrapper.write(Types1_7_6_10.ENTITY_DATA_LIST, entityDataList);
   }

   public void sendSpawnPacket(EntityPacketRewriter1_8 entityRewriter) {
      if (this.entityIds != null) {
         this.deleteEntity();
      }

      if (this.currentState == VirtualHologramEntity.State.ZOMBIE) {
         this.spawnEntity(this.entityId, EntityTypes1_8.EntityType.ZOMBIE.getId(), this.locX, this.locY, this.locZ);
         this.entityIds = new int[]{this.entityId};
      } else if (this.currentState == VirtualHologramEntity.State.HOLOGRAM) {
         int[] entityIds = new int[]{this.entityId, this.additionalEntityId()};
         PacketWrapper spawnSkull = PacketWrapper.create(ClientboundPackets1_7_2_5.ADD_ENTITY, (UserConnection)this.user);
         spawnSkull.write(Types.VAR_INT, entityIds[0]);
         spawnSkull.write(Types.BYTE, (byte)66);
         spawnSkull.write(Types.INT, (int)(this.locX * (double)32.0F));
         spawnSkull.write(Types.INT, (int)(this.locY * (double)32.0F));
         spawnSkull.write(Types.INT, (int)(this.locZ * (double)32.0F));
         spawnSkull.write(Types.BYTE, (byte)0);
         spawnSkull.write(Types.BYTE, (byte)0);
         spawnSkull.write(Types.INT, 0);
         spawnSkull.send(Protocol1_8To1_7_6_10.class);
         this.spawnEntity(entityIds[1], EntityTypes1_8.EntityType.HORSE.getId(), this.locX, this.locY, this.locZ);
         this.entityIds = entityIds;
      }

      this.sendEntityDataUpdate(entityRewriter);
      this.updateLocation(true);
   }

   public AABB getBoundingBox() {
      double width = this.small ? (double)0.25F : (double)0.5F;
      double height = this.small ? 0.9875 : 1.975;
      Vector3d min = new Vector3d(this.locX - width / (double)2.0F, this.locY, this.locZ - width / (double)2.0F);
      Vector3d max = new Vector3d(this.locX + width / (double)2.0F, this.locY + height, this.locZ + width / (double)2.0F);
      return new AABB(min, max);
   }

   int additionalEntityId() {
      return 2147467647 - this.entityId;
   }

   public void deleteEntity() {
      if (this.entityIds != null) {
         PacketWrapper despawn = PacketWrapper.create(ClientboundPackets1_7_2_5.REMOVE_ENTITIES, (UserConnection)this.user);
         despawn.write(Types.BYTE, (byte)this.entityIds.length);

         for(int id : this.entityIds) {
            despawn.write(Types.INT, id);
         }

         this.entityIds = null;
         despawn.send(Protocol1_8To1_7_6_10.class);
      }
   }

   private static enum State {
      HOLOGRAM,
      ZOMBIE;

      // $FF: synthetic method
      static State[] $values() {
         return new State[]{HOLOGRAM, ZOMBIE};
      }
   }
}
