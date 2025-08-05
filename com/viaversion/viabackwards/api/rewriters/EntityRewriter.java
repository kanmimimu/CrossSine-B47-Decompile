package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;

public abstract class EntityRewriter extends EntityRewriterBase {
   protected EntityRewriter(BackwardsProtocol protocol) {
      this(protocol, Types1_14.ENTITY_DATA_TYPES.optionalComponentType, Types1_14.ENTITY_DATA_TYPES.booleanType);
   }

   protected EntityRewriter(BackwardsProtocol protocol, EntityDataType displayType, EntityDataType displayVisibilityType) {
      super(protocol, displayType, 2, displayVisibilityType, 3);
   }

   public void registerTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler(fallingBlockType));
         }
      });
   }

   public void registerTrackerWithData1_19(ClientboundPacketType packetType, final EntityType fallingBlockType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.handler(EntityRewriter.this.getSpawnTrackerWithDataHandler1_19(fallingBlockType));
         }
      });
   }

   public PacketHandler getSpawnTrackerWithDataHandler(EntityType fallingBlockType) {
      return (wrapper) -> {
         EntityType entityType = this.trackAndMapEntity(wrapper);
         if (entityType == fallingBlockType) {
            int blockState = (Integer)wrapper.get(Types.INT, 0);
            wrapper.set(Types.INT, 0, ((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(blockState));
         }

      };
   }

   public PacketHandler getSpawnTrackerWithDataHandler1_19(EntityType fallingBlockType) {
      return (wrapper) -> {
         if (((BackwardsProtocol)this.protocol).getMappingData() != null) {
            EntityType entityType = this.trackAndMapEntity(wrapper);
            if (entityType == fallingBlockType) {
               int blockState = (Integer)wrapper.get(Types.VAR_INT, 2);
               wrapper.set(Types.VAR_INT, 2, ((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(blockState));
            }

         }
      };
   }

   public void registerSpawnTracker(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> EntityRewriter.this.trackAndMapEntity(wrapper));
         }
      });
   }

   public PacketHandler worldTrackerHandlerByKey() {
      return (wrapper) -> {
         EntityTracker tracker = this.tracker(wrapper.user());
         String world = (String)wrapper.get(Types.STRING, 1);
         if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
            tracker.clearEntities();
         }

         tracker.setCurrentWorld(world);
      };
   }

   protected EntityType trackAndMapEntity(PacketWrapper wrapper) {
      int typeId = (Integer)wrapper.get(Types.VAR_INT, 1);
      EntityType entityType = this.typeFromId(typeId);
      this.tracker(wrapper.user()).addEntity((Integer)wrapper.get(Types.VAR_INT, 0), entityType);
      int mappedTypeId = this.newEntityId(entityType.getId());
      if (typeId != mappedTypeId) {
         wrapper.set(Types.VAR_INT, 1, mappedTypeId);
      }

      return entityType;
   }
}
