package com.viaversion.viabackwards.api.rewriters;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityReplacement;
import com.viaversion.viabackwards.api.entities.storage.WrappedEntityData;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriterBase extends com.viaversion.viaversion.rewriter.EntityRewriter {
   private final Int2ObjectMap entityDataMappings = new Int2ObjectOpenHashMap();
   private final EntityDataType displayNameDataType;
   private final EntityDataType displayVisibilityDataType;
   private final int displayNameIndex;
   private final int displayVisibilityIndex;

   EntityRewriterBase(BackwardsProtocol protocol, EntityDataType displayNameDataType, int displayNameIndex, EntityDataType displayVisibilityDataType, int displayVisibilityIndex) {
      super(protocol, false);
      this.displayNameDataType = displayNameDataType;
      this.displayNameIndex = displayNameIndex;
      this.displayVisibilityDataType = displayVisibilityDataType;
      this.displayVisibilityIndex = displayVisibilityIndex;
   }

   public void handleEntityData(int entityId, List entityDataList, UserConnection connection) {
      TrackedEntity entity = this.tracker(connection).entity(entityId);
      boolean initialEntityData = entity == null || !entity.hasSentEntityData();
      super.handleEntityData(entityId, entityDataList, connection);
      if (entity != null) {
         EntityReplacement entityMapping = this.entityDataForType(entity.entityType());
         Object displayNameObject;
         if (entityMapping != null && (displayNameObject = entityMapping.entityName()) != null) {
            EntityData displayName = this.getData(this.displayNameIndex, entityDataList);
            if (initialEntityData) {
               if (displayName == null) {
                  entityDataList.add(new EntityData(this.displayNameIndex, this.displayNameDataType, displayNameObject));
                  this.addDisplayVisibilityData(entityDataList);
               } else if (displayName.getValue() == null || displayName.getValue().toString().isEmpty()) {
                  displayName.setValue(displayNameObject);
                  this.addDisplayVisibilityData(entityDataList);
               }
            } else if (displayName != null && (displayName.getValue() == null || displayName.getValue().toString().isEmpty())) {
               displayName.setValue(displayNameObject);
               this.addDisplayVisibilityData(entityDataList);
            }
         }

         if (entityMapping != null && entityMapping.hasBaseData() && initialEntityData) {
            entityMapping.defaultData().createData(new WrappedEntityData(entityDataList));
         }

      }
   }

   private void addDisplayVisibilityData(List entityDataList) {
      if (this.alwaysShowOriginalMobName()) {
         this.removeData(this.displayVisibilityIndex, entityDataList);
         entityDataList.add(new EntityData(this.displayVisibilityIndex, this.displayVisibilityDataType, this.getDisplayVisibilityDataValue()));
      }

   }

   protected Object getDisplayVisibilityDataValue() {
      return true;
   }

   protected boolean alwaysShowOriginalMobName() {
      return ViaBackwards.getConfig().alwaysShowOriginalMobName();
   }

   protected @Nullable EntityData getData(int dataIndex, List entityDataList) {
      for(EntityData entityData : entityDataList) {
         if (entityData.id() == dataIndex) {
            return entityData;
         }
      }

      return null;
   }

   protected void removeData(int dataIndex, List entityDataList) {
      entityDataList.removeIf((data) -> data.id() == dataIndex);
   }

   protected boolean hasData(EntityType type) {
      return this.entityDataMappings.containsKey(type.getId());
   }

   protected @Nullable EntityReplacement entityDataForType(EntityType type) {
      return (EntityReplacement)this.entityDataMappings.get(type.getId());
   }

   protected @Nullable StoredEntityData storedEntityData(EntityDataHandlerEvent event) {
      return this.tracker(event.user()).entityData(event.entityId());
   }

   protected EntityReplacement mapEntityTypeWithData(EntityType type, EntityType mappedType) {
      Preconditions.checkArgument(type.getClass() == mappedType.getClass(), "Both entity types need to be of the same class");
      int mappedReplacementId = this.newEntityId(mappedType.getId());
      EntityReplacement data = new EntityReplacement((BackwardsProtocol)this.protocol, type, mappedReplacementId);
      this.mapEntityType(type.getId(), mappedReplacementId);
      this.entityDataMappings.put(type.getId(), data);
      return data;
   }

   public void registerEntityDataTypeHandler(@Nullable EntityDataType itemType, @Nullable EntityDataType blockStateType, @Nullable EntityDataType optionalBlockStateType, @Nullable EntityDataType particleType, @Nullable EntityDataType componentType, @Nullable EntityDataType optionalComponentType) {
      this.filter().handler((event, data) -> {
         EntityDataType type = data.dataType();
         if (type == itemType) {
            ((BackwardsProtocol)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.value());
         } else if (type == blockStateType) {
            int value = (Integer)data.value();
            data.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(value));
         } else if (type == optionalBlockStateType) {
            int value = (Integer)data.value();
            if (value != 0) {
               data.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(value));
            }
         } else if (type == particleType) {
            this.rewriteParticle(event.user(), (Particle)data.value());
         } else if (type == optionalComponentType || type == componentType) {
            JsonElement text = (JsonElement)data.value();
            ((BackwardsProtocol)this.protocol).getComponentRewriter().processText(event.user(), text);
         }

      });
   }

   public void registerEntityDataTypeHandler1_20_3(@Nullable EntityDataType itemType, @Nullable EntityDataType blockStateType, @Nullable EntityDataType optionalBlockStateType, @Nullable EntityDataType particleType, @Nullable EntityDataType particlesType, @Nullable EntityDataType componentType, @Nullable EntityDataType optionalComponentType) {
      this.filter().handler((event, data) -> {
         EntityDataType type = data.dataType();
         if (type == itemType) {
            data.setValue(((BackwardsProtocol)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.value()));
         } else if (type == blockStateType) {
            int value = (Integer)data.value();
            data.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(value));
         } else if (type == optionalBlockStateType) {
            int value = (Integer)data.value();
            if (value != 0) {
               data.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(value));
            }
         } else if (type == particleType) {
            this.rewriteParticle(event.user(), (Particle)data.value());
         } else if (type == particlesType) {
            Particle[] particles = (Particle[])data.value();

            for(Particle particle : particles) {
               this.rewriteParticle(event.user(), particle);
            }
         } else if (type == optionalComponentType || type == componentType) {
            ((BackwardsProtocol)this.protocol).getComponentRewriter().processTag(event.user(), (Tag)data.value());
         }

      });
   }

   protected PacketHandler getTrackerHandler(Type intType, int typeIndex) {
      return (wrapper) -> {
         Number id = (Number)wrapper.get(intType, typeIndex);
         this.tracker(wrapper.user()).addEntity((Integer)wrapper.get(Types.VAR_INT, 0), this.typeFromId(id.intValue()));
      };
   }

   protected PacketHandler getTrackerHandler() {
      return this.getTrackerHandler(Types.VAR_INT, 1);
   }

   protected PacketHandler getTrackerHandler(EntityType entityType) {
      return (wrapper) -> this.tracker(wrapper.user()).addEntity((Integer)wrapper.get(Types.VAR_INT, 0), entityType);
   }

   protected PacketHandler getPlayerTrackerHandler() {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Types.INT, 0);
         EntityTracker tracker = this.tracker(wrapper.user());
         this.tracker(wrapper.user()).setClientEntityId(entityId);
         tracker.addEntity(entityId, tracker.playerType());
      };
   }

   protected PacketHandler getDimensionHandler(int index) {
      return (wrapper) -> {
         ClientWorld clientWorld = wrapper.user().getClientWorld(((BackwardsProtocol)this.protocol).getClass());
         int dimensionId = (Integer)wrapper.get(Types.INT, index);
         if (clientWorld.setEnvironment(dimensionId)) {
            this.tracker(wrapper.user()).clearEntities();
         }

      };
   }
}
