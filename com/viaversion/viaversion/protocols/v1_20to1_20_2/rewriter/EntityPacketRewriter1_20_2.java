package com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_20_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.storage.ConfigurationState;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.storage.LastResourcePack;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataFilter;
import java.util.Objects;

public final class EntityPacketRewriter1_20_2 extends EntityRewriter {
   public EntityPacketRewriter1_20_2(Protocol1_20To1_20_2 protocol) {
      super(protocol);
   }

   public void registerPackets() {
      this.registerTrackerWithData1_19(ClientboundPackets1_19_4.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
      this.registerSetEntityData(ClientboundPackets1_19_4.SET_ENTITY_DATA, Types1_20.ENTITY_DATA_LIST, Types1_20_2.ENTITY_DATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
      ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADD_PLAYER, ClientboundPackets1_20_2.ADD_ENTITY, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.UUID);
         wrapper.write(Types.VAR_INT, EntityTypes1_19_4.PLAYER.getId());
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         byte yaw = (Byte)wrapper.read(Types.BYTE);
         wrapper.passthrough(Types.BYTE);
         wrapper.write(Types.BYTE, yaw);
         wrapper.write(Types.BYTE, yaw);
         wrapper.write(Types.VAR_INT, 0);
         wrapper.write(Types.SHORT, Short.valueOf((short)0));
         wrapper.write(Types.SHORT, Short.valueOf((short)0));
         wrapper.write(Types.SHORT, Short.valueOf((short)0));
      });
      ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.LOGIN, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Types.INT);
               wrapper.passthrough(Types.BOOLEAN);
               byte gamemode = (Byte)wrapper.read(Types.BYTE);
               byte previousGamemode = (Byte)wrapper.read(Types.BYTE);
               wrapper.passthrough(Types.STRING_ARRAY);
               CompoundTag dimensionRegistry = (CompoundTag)wrapper.read(Types.NAMED_COMPOUND_TAG);
               String dimensionType = (String)wrapper.read(Types.STRING);
               String world = (String)wrapper.read(Types.STRING);
               long seed = (Long)wrapper.read(Types.LONG);
               EntityPacketRewriter1_20_2.this.trackBiomeSize(wrapper.user(), dimensionRegistry);
               EntityPacketRewriter1_20_2.this.cacheDimensionData(wrapper.user(), dimensionRegistry);
               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.write(Types.BOOLEAN, false);
               wrapper.write(Types.STRING, dimensionType);
               wrapper.write(Types.STRING, world);
               wrapper.write(Types.LONG, seed);
               wrapper.write(Types.BYTE, gamemode);
               wrapper.write(Types.BYTE, previousGamemode);
               ConfigurationState configurationBridge = (ConfigurationState)wrapper.user().get(ConfigurationState.class);
               if (!configurationBridge.setLastDimensionRegistry(dimensionRegistry)) {
                  PacketWrapper clientInformationPacket = configurationBridge.clientInformationPacket(wrapper.user());
                  if (clientInformationPacket != null) {
                     clientInformationPacket.scheduleSendToServer(Protocol1_20To1_20_2.class);
                  }

               } else if (configurationBridge.bridgePhase() == ConfigurationState.BridgePhase.NONE) {
                  PacketWrapper configurationPacket = wrapper.create(ClientboundPackets1_20_2.START_CONFIGURATION);
                  configurationPacket.send(Protocol1_20To1_20_2.class);
                  configurationBridge.setBridgePhase(ConfigurationState.BridgePhase.REENTERING_CONFIGURATION);
                  configurationBridge.setJoinGamePacket(wrapper);
                  wrapper.cancel();
               } else {
                  configurationBridge.setJoinGamePacket(wrapper);
                  wrapper.cancel();
                  Protocol1_20To1_20_2.sendConfigurationPackets(wrapper.user(), dimensionRegistry, (LastResourcePack)null);
               }
            });
            this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
         }
      });
      ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Types.STRING);
               wrapper.passthrough(Types.STRING);
               wrapper.passthrough(Types.LONG);
               wrapper.write(Types.BYTE, ((Short)wrapper.read(Types.UNSIGNED_BYTE)).byteValue());
               wrapper.passthrough(Types.BYTE);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.passthrough(Types.BOOLEAN);
               byte dataToKeep = (Byte)wrapper.read(Types.BYTE);
               wrapper.passthrough(Types.OPTIONAL_GLOBAL_POSITION);
               wrapper.passthrough(Types.VAR_INT);
               wrapper.write(Types.BYTE, dataToKeep);
            });
            this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
         }
      });
      ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_MOB_EFFECT, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.VAR_INT) - 1);
         wrapper.passthrough(Types.BYTE);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.BYTE);
         wrapper.write(Types.OPTIONAL_COMPOUND_TAG, (CompoundTag)wrapper.read(Types.OPTIONAL_NAMED_COMPOUND_TAG));
      });
      ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.REMOVE_MOB_EFFECT, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.VAR_INT) - 1);
      });
   }

   protected void registerRewrites() {
      EntityDataFilter.Builder var10000 = this.filter();
      EntityDataTypes1_20_2 var10001 = Types1_20_2.ENTITY_DATA_TYPES;
      Objects.requireNonNull(var10001);
      var10000.mapDataType(var10001::byId);
      this.registerEntityDataTypeHandler(Types1_20_2.ENTITY_DATA_TYPES.itemType, Types1_20_2.ENTITY_DATA_TYPES.blockStateType, Types1_20_2.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20_2.ENTITY_DATA_TYPES.particleType, (EntityDataType)null);
      this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
      this.filter().type(EntityTypes1_19_4.DISPLAY).addIndex(10);
   }

   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
