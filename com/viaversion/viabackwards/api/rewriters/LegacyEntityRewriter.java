package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
import com.viaversion.viabackwards.api.entities.storage.EntityReplacement;
import com.viaversion.viabackwards.api.entities.storage.WrappedEntityData;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyEntityRewriter extends EntityRewriterBase {
   final Map objectTypes;

   protected LegacyEntityRewriter(BackwardsProtocol protocol) {
      this(protocol, EntityDataTypes1_9.STRING, EntityDataTypes1_9.BOOLEAN);
   }

   protected LegacyEntityRewriter(BackwardsProtocol protocol, EntityDataType displayType, EntityDataType displayVisibilityType) {
      super(protocol, displayType, 2, displayVisibilityType, 3);
      this.objectTypes = new HashMap();
   }

   protected EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
      EntityObjectData entData = new EntityObjectData((BackwardsProtocol)this.protocol, oldObjectType.getType().name(), oldObjectType.getId(), replacement.getId(), data);
      this.objectTypes.put(oldObjectType, entData);
      return entData;
   }

   protected @Nullable EntityReplacement getObjectData(ObjectType type) {
      return (EntityReplacement)this.objectTypes.get(type);
   }

   protected void registerRespawn(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(((BackwardsProtocol)LegacyEntityRewriter.this.protocol).getClass());
               if (clientWorld.setEnvironment((Integer)wrapper.get(Types.INT, 0))) {
                  LegacyEntityRewriter.this.tracker(wrapper.user()).clearEntities();
               }

            });
         }
      });
   }

   protected void registerJoinGame(ClientboundPacketType packetType, final EntityType playerType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(((BackwardsProtocol)LegacyEntityRewriter.this.protocol).getClass());
               clientWorld.setEnvironment((Integer)wrapper.get(Types.INT, 1));
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               LegacyEntityRewriter.this.addTrackedEntity(wrapper, entityId, playerType);
               LegacyEntityRewriter.this.tracker(wrapper.user()).setClientEntityId(entityId);
            });
         }
      });
   }

   protected PacketHandler getMobSpawnRewriter(Type dataType, IdSetter idSetter) {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
         EntityType type = this.tracker(wrapper.user()).entityType(entityId);
         List<EntityData> entityDataList = (List)wrapper.get(dataType, 0);
         this.handleEntityData(entityId, entityDataList, wrapper.user());
         EntityReplacement entityReplacement = this.entityDataForType(type);
         if (entityReplacement != null) {
            idSetter.setId(wrapper, entityReplacement.replacementId());
            if (entityReplacement.hasBaseData()) {
               entityReplacement.defaultData().createData(new WrappedEntityData(entityDataList));
            }
         }

      };
   }

   public PacketHandler getMobSpawnRewriter(Type dataType) {
      return this.getMobSpawnRewriter(dataType, (wrapper, id) -> wrapper.set(Types.UNSIGNED_BYTE, 0, (short)id));
   }

   public PacketHandler getMobSpawnRewriter1_11(Type dataType) {
      return this.getMobSpawnRewriter(dataType, (wrapper, id) -> wrapper.set(Types.VAR_INT, 1, id));
   }

   protected PacketHandler getObjectTrackerHandler() {
      return (wrapper) -> this.addTrackedEntity(wrapper, (Integer)wrapper.get(Types.VAR_INT, 0), this.objectTypeFromId((Byte)wrapper.get(Types.BYTE, 0)));
   }

   protected PacketHandler getTrackerAndDataHandler(Type dataType, EntityType entityType) {
      return (wrapper) -> {
         this.addTrackedEntity(wrapper, (Integer)wrapper.get(Types.VAR_INT, 0), entityType);
         List<EntityData> entityDataList = (List)wrapper.get(dataType, 0);
         this.handleEntityData((Integer)wrapper.get(Types.VAR_INT, 0), entityDataList, wrapper.user());
      };
   }

   protected PacketHandler getObjectRewriter(Function objectGetter) {
      return (wrapper) -> {
         ObjectType type = (ObjectType)objectGetter.apply((Byte)wrapper.get(Types.BYTE, 0));
         if (type == null) {
            ProtocolLogger var10000 = ((BackwardsProtocol)this.protocol).getLogger();
            Object var6 = wrapper.get(Types.BYTE, 0);
            var10000.warning("Could not find entity type " + var6);
         } else {
            EntityReplacement data = this.getObjectData(type);
            if (data != null) {
               wrapper.set(Types.BYTE, 0, (byte)data.replacementId());
               if (data.objectData() != -1) {
                  wrapper.set(Types.INT, 0, data.objectData());
               }
            }

         }
      };
   }

   /** @deprecated */
   @Deprecated
   protected void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) {
      this.tracker(wrapper.user()).addEntity(entityId, type);
   }

   @FunctionalInterface
   protected interface IdSetter {
      void setId(PacketWrapper var1, int var2);
   }
}
