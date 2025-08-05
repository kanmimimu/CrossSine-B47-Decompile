package com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_19_4 extends EntityRewriter {
   public EntityPacketRewriter1_19_4(Protocol1_19_3To1_19_4 protocol) {
      super(protocol);
   }

   public void registerPackets() {
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.STRING_ARRAY);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.STRING);
            this.map(Types.STRING);
            this.handler(EntityPacketRewriter1_19_4.this.dimensionDataHandler());
            this.handler(EntityPacketRewriter1_19_4.this.biomeSizeTracker());
            this.handler(EntityPacketRewriter1_19_4.this.worldDataTrackerHandlerByKey());
            this.handler(EntityPacketRewriter1_19_4.this.playerTrackerHandler());
            this.handler((wrapper) -> {
               CompoundTag registry = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
               CompoundTag damageTypeRegistry = ((Protocol1_19_3To1_19_4)EntityPacketRewriter1_19_4.this.protocol).getMappingData().damageTypesRegistry();
               registry.put("minecraft:damage_type", damageTypeRegistry);

               for(CompoundTag biomeTag : TagUtil.getRegistryEntries(registry, "worldgen/biome")) {
                  CompoundTag biomeData = biomeTag.getCompoundTag("element");
                  StringTag precipitation = biomeData.getStringTag("precipitation");
                  byte precipitationByte = (byte)(precipitation.getValue().equals("none") ? 0 : 1);
                  biomeData.put("has_precipitation", new ByteTag(precipitationByte));
               }

            });
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_POSITION, new PacketHandlers() {
         protected void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  PlayerVehicleTracker playerVehicleTracker = (PlayerVehicleTracker)wrapper.user().get(PlayerVehicleTracker.class);
                  if (playerVehicleTracker.getVehicleId() != -1) {
                     PacketWrapper bundleStart = wrapper.create(ClientboundPackets1_19_4.BUNDLE_DELIMITER);
                     bundleStart.send(Protocol1_19_3To1_19_4.class);
                     PacketWrapper setPassengers = wrapper.create(ClientboundPackets1_19_4.SET_PASSENGERS);
                     setPassengers.write(Types.VAR_INT, playerVehicleTracker.getVehicleId());
                     setPassengers.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                     setPassengers.send(Protocol1_19_3To1_19_4.class);
                     wrapper.send(Protocol1_19_3To1_19_4.class);
                     wrapper.cancel();
                     PacketWrapper bundleEnd = wrapper.create(ClientboundPackets1_19_4.BUNDLE_DELIMITER);
                     bundleEnd.send(Protocol1_19_3To1_19_4.class);
                     playerVehicleTracker.setVehicleId(-1);
                  }
               }

            });
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.SET_PASSENGERS, new PacketHandlers() {
         protected void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT_ARRAY_PRIMITIVE);
            this.handler((wrapper) -> {
               PlayerVehicleTracker playerVehicleTracker = (PlayerVehicleTracker)wrapper.user().get(PlayerVehicleTracker.class);
               int clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_3To1_19_4.class).clientEntityId();
               int vehicleId = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (playerVehicleTracker.getVehicleId() == vehicleId) {
                  playerVehicleTracker.setVehicleId(-1);
               }

               int[] passengerIds = (int[])wrapper.get(Types.VAR_INT_ARRAY_PRIMITIVE, 0);

               for(int passengerId : passengerIds) {
                  if (passengerId == clientEntityId) {
                     playerVehicleTracker.setVehicleId(vehicleId);
                     break;
                  }
               }

            });
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.TELEPORT_ENTITY, new PacketHandlers() {
         protected void register() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.read(Types.VAR_INT);
               int clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_3To1_19_4.class).clientEntityId();
               if (entityId != clientEntityId) {
                  wrapper.write(Types.VAR_INT, entityId);
               } else {
                  wrapper.setPacketType(ClientboundPackets1_19_4.PLAYER_POSITION);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.passthrough(Types.DOUBLE);
                  wrapper.write(Types.FLOAT, (float)(Byte)wrapper.read(Types.BYTE) * 360.0F / 256.0F);
                  wrapper.write(Types.FLOAT, (float)(Byte)wrapper.read(Types.BYTE) * 360.0F / 256.0F);
                  wrapper.read(Types.BOOLEAN);
                  wrapper.write(Types.BYTE, (byte)0);
                  wrapper.write(Types.VAR_INT, -1);
               }
            });
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.ANIMATE, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               short action = (Short)wrapper.read(Types.UNSIGNED_BYTE);
               if (action != 1) {
                  wrapper.write(Types.UNSIGNED_BYTE, action);
               } else {
                  wrapper.setPacketType(ClientboundPackets1_19_4.HURT_ANIMATION);
                  wrapper.write(Types.FLOAT, 0.0F);
               }
            });
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.STRING);
            this.handler(EntityPacketRewriter1_19_4.this.worldDataTrackerHandlerByKey());
            this.handler((wrapper) -> wrapper.user().put(new PlayerVehicleTracker()));
         }
      });
      ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.ENTITY_EVENT, (wrapper) -> {
         int entityId = (Integer)wrapper.read(Types.INT);
         byte event = (Byte)wrapper.read(Types.BYTE);
         int damageType = this.damageTypeFromEntityEvent(event);
         if (damageType != -1) {
            wrapper.setPacketType(ClientboundPackets1_19_4.DAMAGE_EVENT);
            wrapper.write(Types.VAR_INT, entityId);
            wrapper.write(Types.VAR_INT, damageType);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.BOOLEAN, false);
         } else {
            wrapper.write(Types.INT, entityId);
            wrapper.write(Types.BYTE, event);
         }
      });
      this.registerTrackerWithData1_19(ClientboundPackets1_19_3.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
      this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
      this.registerSetEntityData(ClientboundPackets1_19_3.SET_ENTITY_DATA, Types1_19_3.ENTITY_DATA_LIST, Types1_19_4.ENTITY_DATA_LIST);
   }

   int damageTypeFromEntityEvent(byte entityEvent) {
      byte var10000;
      switch (entityEvent) {
         case 2:
         case 44:
            var10000 = 16;
            break;
         case 33:
            var10000 = 36;
            break;
         case 36:
            var10000 = 5;
            break;
         case 37:
            var10000 = 27;
            break;
         case 57:
            var10000 = 15;
            break;
         default:
            var10000 = -1;
      }

      return var10000;
   }

   protected void registerRewrites() {
      this.filter().mapDataType((typeId) -> Types1_19_4.ENTITY_DATA_TYPES.byId(typeId >= 14 ? typeId + 1 : typeId));
      this.registerEntityDataTypeHandler(Types1_19_4.ENTITY_DATA_TYPES.itemType, Types1_19_4.ENTITY_DATA_TYPES.blockStateType, Types1_19_4.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_19_4.ENTITY_DATA_TYPES.particleType, (EntityDataType)null);
      this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
      this.filter().type(EntityTypes1_19_4.BOAT).index(11).handler((event, data) -> {
         int boatType = (Integer)data.value();
         if (boatType > 4) {
            data.setValue(boatType + 1);
         }

      });
      this.filter().type(EntityTypes1_19_4.ABSTRACT_HORSE).removeIndex(18);
   }

   public void onMappingDataLoaded() {
      this.mapTypes();
   }

   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
