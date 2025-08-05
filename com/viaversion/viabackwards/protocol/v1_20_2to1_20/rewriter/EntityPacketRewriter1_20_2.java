package com.viaversion.viabackwards.protocol.v1_20_2to1_20.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.storage.ConfigurationPacketStorage;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;

public final class EntityPacketRewriter1_20_2 extends EntityRewriter {
   public EntityPacketRewriter1_20_2(Protocol1_20_2To1_20 protocol) {
      super(protocol, Types1_20.ENTITY_DATA_TYPES.optionalComponentType, Types1_20.ENTITY_DATA_TYPES.booleanType);
   }

   public void registerPackets() {
      this.registerSetEntityData(ClientboundPackets1_20_2.SET_ENTITY_DATA, Types1_20_2.ENTITY_DATA_LIST, Types1_20.ENTITY_DATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_20_2.REMOVE_ENTITIES);
      ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.ADD_ENTITY, new PacketHandlers() {
         protected void register() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.UUID);
               int entityType = (Integer)wrapper.read(Types.VAR_INT);
               EntityPacketRewriter1_20_2.this.tracker(wrapper.user()).addEntity(entityId, EntityPacketRewriter1_20_2.this.typeFromId(entityType));
               if (entityType != EntityTypes1_19_4.PLAYER.getId()) {
                  wrapper.write(Types.VAR_INT, entityType);
                  if (entityType == EntityTypes1_19_4.FALLING_BLOCK.getId()) {
                     wrapper.passthrough(Types.DOUBLE);
                     wrapper.passthrough(Types.DOUBLE);
                     wrapper.passthrough(Types.DOUBLE);
                     wrapper.passthrough(Types.BYTE);
                     wrapper.passthrough(Types.BYTE);
                     wrapper.passthrough(Types.BYTE);
                     int blockState = (Integer)wrapper.read(Types.VAR_INT);
                     wrapper.write(Types.VAR_INT, ((Protocol1_20_2To1_20)EntityPacketRewriter1_20_2.this.protocol).getMappingData().getNewBlockStateId(blockState));
                  }

               } else {
                  wrapper.setPacketType(ClientboundPackets1_19_4.ADD_PLAYER);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.passthrough(Types.DOUBLE);
                  byte pitch = (Byte)wrapper.read(Types.BYTE);
                  wrapper.passthrough(Types.BYTE);
                  wrapper.write(Types.BYTE, pitch);
                  wrapper.read(Types.BYTE);
                  wrapper.read(Types.VAR_INT);
                  short velocityX = (Short)wrapper.read(Types.SHORT);
                  short velocityY = (Short)wrapper.read(Types.SHORT);
                  short velocityZ = (Short)wrapper.read(Types.SHORT);
                  if (velocityX != 0 || velocityY != 0 || velocityZ != 0) {
                     wrapper.send(Protocol1_20_2To1_20.class);
                     wrapper.cancel();
                     PacketWrapper velocityPacket = wrapper.create(ClientboundPackets1_19_4.SET_ENTITY_MOTION);
                     velocityPacket.write(Types.VAR_INT, entityId);
                     velocityPacket.write(Types.SHORT, velocityX);
                     velocityPacket.write(Types.SHORT, velocityY);
                     velocityPacket.write(Types.SHORT, velocityZ);
                     velocityPacket.send(Protocol1_20_2To1_20.class);
                  }
               }
            });
         }
      });
      ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.LOGIN, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               ConfigurationPacketStorage configurationPacketStorage = (ConfigurationPacketStorage)wrapper.user().get(ConfigurationPacketStorage.class);
               wrapper.passthrough(Types.INT);
               wrapper.passthrough(Types.BOOLEAN);
               String[] worlds = (String[])wrapper.read(Types.STRING_ARRAY);
               int maxPlayers = (Integer)wrapper.read(Types.VAR_INT);
               int viewDistance = (Integer)wrapper.read(Types.VAR_INT);
               int simulationDistance = (Integer)wrapper.read(Types.VAR_INT);
               boolean reducedDebugInfo = (Boolean)wrapper.read(Types.BOOLEAN);
               boolean showRespawnScreen = (Boolean)wrapper.read(Types.BOOLEAN);
               wrapper.read(Types.BOOLEAN);
               String dimensionType = (String)wrapper.read(Types.STRING);
               String world = (String)wrapper.read(Types.STRING);
               long seed = (Long)wrapper.read(Types.LONG);
               wrapper.passthrough(Types.BYTE);
               wrapper.passthrough(Types.BYTE);
               wrapper.write(Types.STRING_ARRAY, worlds);
               wrapper.write(Types.NAMED_COMPOUND_TAG, configurationPacketStorage.registry());
               wrapper.write(Types.STRING, dimensionType);
               wrapper.write(Types.STRING, world);
               wrapper.write(Types.LONG, seed);
               wrapper.write(Types.VAR_INT, maxPlayers);
               wrapper.write(Types.VAR_INT, viewDistance);
               wrapper.write(Types.VAR_INT, simulationDistance);
               wrapper.write(Types.BOOLEAN, reducedDebugInfo);
               wrapper.write(Types.BOOLEAN, showRespawnScreen);
               EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey().handle(wrapper);
               wrapper.send(Protocol1_20_2To1_20.class);
               wrapper.cancel();
               if (configurationPacketStorage.enabledFeatures() != null) {
                  PacketWrapper featuresPacket = wrapper.create(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES);
                  featuresPacket.write(Types.STRING_ARRAY, configurationPacketStorage.enabledFeatures());
                  featuresPacket.send(Protocol1_20_2To1_20.class);
               }

               configurationPacketStorage.sendQueuedPackets(wrapper.user());
            });
         }
      });
      ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.RESPAWN, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Types.STRING);
               wrapper.passthrough(Types.STRING);
               wrapper.passthrough(Types.LONG);
               wrapper.write(Types.UNSIGNED_BYTE, ((Byte)wrapper.read(Types.BYTE)).shortValue());
               wrapper.passthrough(Types.BYTE);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.passthrough(Types.BOOLEAN);
               GlobalBlockPosition lastDeathPosition = (GlobalBlockPosition)wrapper.read(Types.OPTIONAL_GLOBAL_POSITION);
               int portalCooldown = (Integer)wrapper.read(Types.VAR_INT);
               wrapper.passthrough(Types.BYTE);
               wrapper.write(Types.OPTIONAL_GLOBAL_POSITION, lastDeathPosition);
               wrapper.write(Types.VAR_INT, portalCooldown);
            });
            this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
         }
      });
      ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.UPDATE_MOB_EFFECT, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.VAR_INT) + 1);
         wrapper.passthrough(Types.BYTE);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.BYTE);
         CompoundTag factorData = (CompoundTag)wrapper.read(Types.OPTIONAL_COMPOUND_TAG);
         wrapper.write(Types.OPTIONAL_NAMED_COMPOUND_TAG, factorData);
      });
      ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.REMOVE_MOB_EFFECT, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.VAR_INT) + 1);
      });
   }

   protected void registerRewrites() {
      this.filter().handler((event, data) -> data.setDataType(Types1_20.ENTITY_DATA_TYPES.byId(data.dataType().typeId())));
      this.registerEntityDataTypeHandler(Types1_20.ENTITY_DATA_TYPES.itemType, Types1_20.ENTITY_DATA_TYPES.blockStateType, Types1_20.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20.ENTITY_DATA_TYPES.particleType, (EntityDataType)null, (EntityDataType)null);
      this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
      this.filter().type(EntityTypes1_19_4.DISPLAY).removeIndex(10);
   }

   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
