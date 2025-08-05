package com.viaversion.viaversion.protocols.v1_16_4to1_17.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_14;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataFilter;
import com.viaversion.viaversion.util.TagUtil;
import java.util.Objects;

public final class EntityPacketRewriter1_17 extends EntityRewriter {
   public EntityPacketRewriter1_17(Protocol1_16_4To1_17 protocol) {
      super(protocol);
   }

   public void registerPackets() {
      this.registerTrackerWithData(ClientboundPackets1_16_2.ADD_ENTITY, EntityTypes1_17.FALLING_BLOCK);
      this.registerTracker(ClientboundPackets1_16_2.ADD_MOB);
      this.registerTracker(ClientboundPackets1_16_2.ADD_PLAYER, EntityTypes1_17.PLAYER);
      this.registerSetEntityData(ClientboundPackets1_16_2.SET_ENTITY_DATA, Types1_16.ENTITY_DATA_LIST, Types1_17.ENTITY_DATA_LIST);
      ((Protocol1_16_4To1_17)this.protocol).appendClientbound(ClientboundPackets1_16_2.ADD_ENTITY, (wrapper) -> {
         int entityType = (Integer)wrapper.get(Types.VAR_INT, 1);
         if (entityType == EntityTypes1_17.ITEM_FRAME.getId()) {
            int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
            byte pitch = (Byte)wrapper.get(Types.BYTE, 0);
            byte yaw = (Byte)wrapper.get(Types.BYTE, 1);
            PacketWrapper setDirection = PacketWrapper.create(ClientboundPackets1_17.MOVE_ENTITY_ROT, (UserConnection)wrapper.user());
            setDirection.write(Types.VAR_INT, entityId);
            setDirection.write(Types.BYTE, yaw);
            setDirection.write(Types.BYTE, pitch);
            setDirection.write(Types.BOOLEAN, false);
            wrapper.send(Protocol1_16_4To1_17.class);
            wrapper.cancel();
            setDirection.send(Protocol1_16_4To1_17.class);
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.REMOVE_ENTITIES, (ClientboundPacketType)null, (wrapper) -> {
         int[] entityIds = (int[])wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
         wrapper.cancel();
         EntityTracker entityTracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);

         for(int entityId : entityIds) {
            entityTracker.removeEntity(entityId);
            PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
            newPacket.write(Types.VAR_INT, entityId);
            newPacket.send(Protocol1_16_4To1_17.class);
         }

      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.STRING_ARRAY);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.handler((wrapper) -> {
               CompoundTag registry = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0);

               for(CompoundTag dimension : TagUtil.getRegistryEntries(registry, "dimension_type")) {
                  CompoundTag dimensionCompound = dimension.getCompoundTag("element");
                  EntityPacketRewriter1_17.addNewDimensionData(dimensionCompound);
               }

               CompoundTag currentDimensionTag = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 1);
               EntityPacketRewriter1_17.addNewDimensionData(currentDimensionTag);
            });
            this.handler(EntityPacketRewriter1_17.this.playerTrackerHandler());
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.RESPAWN, (wrapper) -> {
         CompoundTag dimensionData = (CompoundTag)wrapper.passthrough(Types.NAMED_COMPOUND_TAG);
         addNewDimensionData(dimensionData);
         this.tracker(wrapper.user()).clearEntities();
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.UPDATE_ATTRIBUTES, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT)));
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.create(Types.BOOLEAN, false);
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.PLAYER_COMBAT, (ClientboundPacketType)null, (wrapper) -> {
         int type = (Integer)wrapper.read(Types.VAR_INT);
         ClientboundPackets1_17 var10000;
         switch (type) {
            case 0:
               var10000 = ClientboundPackets1_17.PLAYER_COMBAT_ENTER;
               break;
            case 1:
               var10000 = ClientboundPackets1_17.PLAYER_COMBAT_END;
               break;
            case 2:
               var10000 = ClientboundPackets1_17.PLAYER_COMBAT_KILL;
               break;
            default:
               throw new IllegalArgumentException("Invalid combat type received: " + type);
         }

         ClientboundPacketType packetType = var10000;
         wrapper.setPacketType(packetType);
      });
      ((Protocol1_16_4To1_17)this.protocol).cancelClientbound(ClientboundPackets1_16_2.MOVE_ENTITY);
   }

   protected void registerRewrites() {
      EntityDataFilter.Builder var10000 = this.filter();
      EntityDataTypes1_14 var10001 = Types1_17.ENTITY_DATA_TYPES;
      Objects.requireNonNull(var10001);
      var10000.mapDataType(var10001::byId);
      this.filter().dataType(Types1_17.ENTITY_DATA_TYPES.poseType).handler((event, data) -> {
         int pose = (Integer)data.value();
         if (pose > 5) {
            data.setValue(pose + 1);
         }

      });
      this.registerEntityDataTypeHandler(Types1_17.ENTITY_DATA_TYPES.itemType, Types1_17.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_17.ENTITY_DATA_TYPES.particleType);
      this.filter().type(EntityTypes1_17.ENTITY).addIndex(7);
      this.registerBlockStateHandler(EntityTypes1_17.ABSTRACT_MINECART, 11);
      this.filter().type(EntityTypes1_17.SHULKER).removeIndex(17);
   }

   public void onMappingDataLoaded() {
      this.mapTypes();
   }

   public EntityType typeFromId(int type) {
      return EntityTypes1_17.getTypeFromId(type);
   }

   static void addNewDimensionData(CompoundTag tag) {
      tag.put("min_y", new IntTag(0));
      tag.put("height", new IntTag(256));
   }
}
