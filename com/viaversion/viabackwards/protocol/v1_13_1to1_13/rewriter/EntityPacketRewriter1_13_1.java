package com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import java.util.List;

public class EntityPacketRewriter1_13_1 extends LegacyEntityRewriter {
   public EntityPacketRewriter1_13_1(Protocol1_13_1To1_13 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.BYTE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               byte type = (Byte)wrapper.get(Types.BYTE, 0);
               EntityTypes1_13.EntityType entType = EntityTypes1_13.getTypeFromId(type, true);
               if (entType == null) {
                  ((Protocol1_13_1To1_13)EntityPacketRewriter1_13_1.this.protocol).getLogger().warning((new StringBuilder()).append("Could not find entity type ").append(type).toString());
               } else {
                  if (entType.is(EntityTypes1_13.EntityType.FALLING_BLOCK)) {
                     int data = (Integer)wrapper.get(Types.INT, 0);
                     wrapper.set(Types.INT, 0, ((Protocol1_13_1To1_13)EntityPacketRewriter1_13_1.this.protocol).getMappingData().getNewBlockStateId(data));
                  }

                  EntityPacketRewriter1_13_1.this.tracker(wrapper.user()).addEntity(entityId, entType);
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_13.ADD_EXPERIENCE_ORB, EntityTypes1_13.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_13.ADD_GLOBAL_ENTITY, EntityTypes1_13.EntityType.LIGHTNING_BOLT);
      ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_MOB, new PacketHandlers() {
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
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types1_13.ENTITY_DATA_LIST);
            this.handler(EntityPacketRewriter1_13_1.this.getTrackerHandler());
            this.handler((wrapper) -> {
               List<EntityData> entityDataList = (List)wrapper.get(Types1_13.ENTITY_DATA_LIST, 0);
               EntityPacketRewriter1_13_1.this.handleEntityData((Integer)wrapper.get(Types.VAR_INT, 0), entityDataList, wrapper.user());
            });
         }
      });
      ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types1_13.ENTITY_DATA_LIST);
            this.handler(EntityPacketRewriter1_13_1.this.getTrackerAndDataHandler(Types1_13.ENTITY_DATA_LIST, EntityTypes1_13.EntityType.PLAYER));
         }
      });
      this.registerTracker(ClientboundPackets1_13.ADD_PAINTING, EntityTypes1_13.EntityType.PAINTING);
      this.registerJoinGame(ClientboundPackets1_13.LOGIN, EntityTypes1_13.EntityType.PLAYER);
      this.registerRespawn(ClientboundPackets1_13.RESPAWN);
      this.registerRemoveEntities(ClientboundPackets1_13.REMOVE_ENTITIES);
      this.registerSetEntityData(ClientboundPackets1_13.SET_ENTITY_DATA, Types1_13.ENTITY_DATA_LIST);
   }

   protected void registerRewrites() {
      this.filter().handler((event, data) -> {
         if (data.dataType() == Types1_13.ENTITY_DATA_TYPES.itemType) {
            ((Protocol1_13_1To1_13)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.getValue());
         } else if (data.dataType() == Types1_13.ENTITY_DATA_TYPES.optionalBlockStateType) {
            int value = (Integer)data.getValue();
            data.setValue(((Protocol1_13_1To1_13)this.protocol).getMappingData().getNewBlockStateId(value));
         } else if (data.dataType() == Types1_13.ENTITY_DATA_TYPES.particleType) {
            this.rewriteParticle(event.user(), (Particle)data.getValue());
         } else if (data.dataType() == Types1_13.ENTITY_DATA_TYPES.optionalComponentType || data.dataType() == Types1_13.ENTITY_DATA_TYPES.componentType) {
            JsonElement element = (JsonElement)data.value();
            ((Protocol1_13_1To1_13)this.protocol).translatableRewriter().processText(event.user(), element);
         }

      });
      this.filter().type(EntityTypes1_13.EntityType.ABSTRACT_ARROW).cancel(7);
      this.filter().type(EntityTypes1_13.EntityType.SPECTRAL_ARROW).index(8).toIndex(7);
      this.filter().type(EntityTypes1_13.EntityType.TRIDENT).index(8).toIndex(7);
      this.registerBlockStateHandler(EntityTypes1_13.EntityType.ABSTRACT_MINECART, 9);
   }

   public EntityType typeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, false);
   }

   public EntityType objectTypeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, true);
   }
}
