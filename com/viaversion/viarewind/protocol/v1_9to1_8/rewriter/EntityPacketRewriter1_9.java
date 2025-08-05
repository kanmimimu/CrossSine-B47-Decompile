package com.viaversion.viarewind.protocol.v1_9to1_8.rewriter;

import com.viaversion.viarewind.api.minecraft.math.RelativeMoveUtil;
import com.viaversion.viarewind.api.rewriter.VREntityRewriter;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.data.EntityDataIndex1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.CooldownStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.LevitationStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.PlayerPositionTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.EulerAngle;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityDataIndex1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityPacketRewriter1_9 extends VREntityRewriter {
   static final byte HAND_ACTIVE_BIT = 0;
   static final byte STATUS_USE_BIT = 4;

   public EntityPacketRewriter1_9(Protocol1_9To1_8 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      this.registerJoinGame1_8(ClientboundPackets1_9.LOGIN);
      this.registerRemoveEntities(ClientboundPackets1_9.REMOVE_ENTITIES);
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SET_ENTITY_DATA, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (!this.tracker(wrapper.user()).hasEntity(entityId)) {
            wrapper.cancel();
         } else {
            List<EntityData> entityData = (List)wrapper.read(Types1_9.ENTITY_DATA_LIST);
            this.handleEntityData(entityId, entityData, wrapper.user());
            wrapper.write(Types1_8.ENTITY_DATA_LIST, entityData);
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.read(Types.UUID);
            this.map(Types.BYTE);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler(EntityPacketRewriter1_9.this.getObjectTrackerHandler());
            this.handler(EntityPacketRewriter1_9.this.getObjectRewriter(EntityTypes1_9.ObjectType::findById));
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               int entityType = (Byte)wrapper.get(Types.BYTE, 0);
               EntityTypes1_9.EntityType type = EntityTypes1_9.getTypeFromId(entityType, true);
               if (type != EntityTypes1_9.EntityType.AREA_EFFECT_CLOUD && type != EntityTypes1_9.EntityType.SPECTRAL_ARROW && type != EntityTypes1_9.EntityType.DRAGON_FIREBALL) {
                  if (type.is(EntityTypes1_9.EntityType.BOAT)) {
                     byte yaw = (Byte)wrapper.get(Types.BYTE, 1);
                     yaw = (byte)(yaw - 64);
                     wrapper.set(Types.BYTE, 1, yaw);
                     int y = (Integer)wrapper.get(Types.INT, 1);
                     y += 10;
                     wrapper.set(Types.INT, 1, y);
                  }

                  int data = (Integer)wrapper.get(Types.INT, 3);
                  if (type.isOrHasParent(EntityTypes1_9.EntityType.ARROW) && data != 0) {
                     --data;
                     wrapper.set(Types.INT, 3, data);
                  }

                  if (type.is(EntityTypes1_9.EntityType.FALLING_BLOCK)) {
                     int blockId = data & 4095;
                     int blockData = data >> 12 & 15;
                     IdAndData replace = ((Protocol1_9To1_8)EntityPacketRewriter1_9.this.protocol).getItemRewriter().handleBlock(blockId, blockData);
                     if (replace != null) {
                        wrapper.set(Types.INT, 3, replace.getId() | replace.getData() << 12);
                     }
                  }

                  if (data > 0) {
                     wrapper.passthrough(Types.SHORT);
                     wrapper.passthrough(Types.SHORT);
                     wrapper.passthrough(Types.SHORT);
                  } else {
                     short velocityX = (Short)wrapper.read(Types.SHORT);
                     short velocityY = (Short)wrapper.read(Types.SHORT);
                     short velocityZ = (Short)wrapper.read(Types.SHORT);
                     PacketWrapper setEntityMotion = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_MOTION, (UserConnection)wrapper.user());
                     setEntityMotion.write(Types.VAR_INT, entityId);
                     setEntityMotion.write(Types.SHORT, velocityX);
                     setEntityMotion.write(Types.SHORT, velocityY);
                     setEntityMotion.write(Types.SHORT, velocityZ);
                     setEntityMotion.scheduleSend(Protocol1_9To1_8.class);
                  }

               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_EXPERIENCE_ORB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               wrapper.user().getEntityTracker(Protocol1_9To1_8.class).addEntity(entityId, EntityTypes1_9.EntityType.EXPERIENCE_ORB);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_GLOBAL_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               wrapper.user().getEntityTracker(Protocol1_9To1_8.class).addEntity(entityId, EntityTypes1_9.EntityType.LIGHTNING_BOLT);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.read(Types.UUID);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types1_9.ENTITY_DATA_LIST, Types1_8.ENTITY_DATA_LIST);
            this.handler(EntityPacketRewriter1_9.this.getTrackerHandler(Types.UNSIGNED_BYTE, 0));
            this.handler(EntityPacketRewriter1_9.this.getMobSpawnRewriter(Types1_8.ENTITY_DATA_LIST));
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_PAINTING, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.read(Types.UUID);
            this.map(Types.STRING);
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               wrapper.user().getEntityTracker(Protocol1_9To1_8.class).addEntity(entityId, EntityTypes1_9.EntityType.PAINTING);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.create(Types.SHORT, Short.valueOf((short)0));
            this.map(Types1_9.ENTITY_DATA_LIST, Types1_8.ENTITY_DATA_LIST);
            this.handler(EntityPacketRewriter1_9.this.getTrackerAndDataHandler(Types1_8.ENTITY_DATA_LIST, EntityTypes1_9.EntityType.PLAYER));
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.ENTITY_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               byte status = (Byte)wrapper.read(Types.BYTE);
               if (status > 23) {
                  wrapper.cancel();
               } else {
                  wrapper.write(Types.BYTE, status);
               }
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.MOVE_ENTITY_POS, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         int deltaX = (Short)wrapper.read(Types.SHORT);
         int deltaY = (Short)wrapper.read(Types.SHORT);
         int deltaZ = (Short)wrapper.read(Types.SHORT);
         Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(wrapper.user(), entityId, deltaX, deltaY, deltaZ);
         wrapper.write(Types.BYTE, (byte)moves[0].blockX());
         wrapper.write(Types.BYTE, (byte)moves[0].blockY());
         wrapper.write(Types.BYTE, (byte)moves[0].blockZ());
         boolean onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
         if (moves.length > 1) {
            PacketWrapper secondPacket = PacketWrapper.create(ClientboundPackets1_8.MOVE_ENTITY_POS, (UserConnection)wrapper.user());
            secondPacket.write(Types.VAR_INT, entityId);
            secondPacket.write(Types.BYTE, (byte)moves[1].blockX());
            secondPacket.write(Types.BYTE, (byte)moves[1].blockY());
            secondPacket.write(Types.BYTE, (byte)moves[1].blockZ());
            secondPacket.write(Types.BOOLEAN, onGround);
            secondPacket.scheduleSend(Protocol1_9To1_8.class);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.MOVE_ENTITY_POS_ROT, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         int deltaX = (Short)wrapper.read(Types.SHORT);
         int deltaY = (Short)wrapper.read(Types.SHORT);
         int deltaZ = (Short)wrapper.read(Types.SHORT);
         Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(wrapper.user(), entityId, deltaX, deltaY, deltaZ);
         wrapper.write(Types.BYTE, (byte)moves[0].blockX());
         wrapper.write(Types.BYTE, (byte)moves[0].blockY());
         wrapper.write(Types.BYTE, (byte)moves[0].blockZ());
         byte yaw = (Byte)wrapper.passthrough(Types.BYTE);
         byte pitch = (Byte)wrapper.passthrough(Types.BYTE);
         boolean onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
         EntityType type = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).entityType(entityId);
         if (type == EntityTypes1_9.EntityType.BOAT) {
            yaw = (byte)(yaw - 64);
            wrapper.set(Types.BYTE, 3, yaw);
         }

         if (moves.length > 1) {
            PacketWrapper secondPacket = PacketWrapper.create(ClientboundPackets1_8.MOVE_ENTITY_POS_ROT, (UserConnection)wrapper.user());
            secondPacket.write(Types.VAR_INT, entityId);
            secondPacket.write(Types.BYTE, (byte)moves[1].blockX());
            secondPacket.write(Types.BYTE, (byte)moves[1].blockY());
            secondPacket.write(Types.BYTE, (byte)moves[1].blockZ());
            secondPacket.write(Types.BYTE, yaw);
            secondPacket.write(Types.BYTE, pitch);
            secondPacket.write(Types.BOOLEAN, onGround);
            secondPacket.scheduleSend(Protocol1_9To1_8.class);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.MOVE_ENTITY_ROT, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         EntityType type = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).entityType(entityId);
         if (type == EntityTypes1_9.EntityType.BOAT) {
            byte yaw = (Byte)wrapper.read(Types.BYTE);
            yaw = (byte)(yaw - 64);
            wrapper.write(Types.BYTE, yaw);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.MOVE_VEHICLE, ClientboundPackets1_8.TELEPORT_ENTITY, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               int vehicle = tracker.getVehicle(tracker.clientEntityId());
               if (vehicle == -1) {
                  wrapper.cancel();
               }

               wrapper.write(Types.VAR_INT, vehicle);
            });
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.FLOAT, Protocol1_9To1_8.DEGREES_TO_ANGLE);
            this.map(Types.FLOAT, Protocol1_9To1_8.DEGREES_TO_ANGLE);
            this.handler((wrapper) -> {
               if (!wrapper.isCancelled()) {
                  PlayerPositionTracker storage = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
                  double x = (double)(Integer)wrapper.get(Types.INT, 0) / (double)32.0F;
                  double y = (double)(Integer)wrapper.get(Types.INT, 1) / (double)32.0F;
                  double z = (double)(Integer)wrapper.get(Types.INT, 2) / (double)32.0F;
                  storage.setPos(x, y, z);
               }
            });
            this.create(Types.BOOLEAN, true);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityType type = wrapper.user().getEntityTracker(Protocol1_9To1_8.class).entityType(entityId);
               if (type == EntityTypes1_9.EntityType.BOAT) {
                  byte yaw = (Byte)wrapper.get(Types.BYTE, 1);
                  yaw = (byte)(yaw - 64);
                  wrapper.set(Types.BYTE, 0, yaw);
                  int y = (Integer)wrapper.get(Types.INT, 1);
                  y += 10;
                  wrapper.set(Types.INT, 1, y);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.REMOVE_MOB_EFFECT, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         int effectId = (Byte)wrapper.passthrough(Types.BYTE);
         if (effectId > 23) {
            wrapper.cancel();
         }

         EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
         if (effectId == 25 && entityId == tracker.clientEntityId()) {
            ((LevitationStorage)wrapper.user().get(LevitationStorage.class)).setActive(false);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SET_ENTITY_LINK, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.create(Types.BOOLEAN, true);
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int slot = (Integer)wrapper.read(Types.VAR_INT);
               if (slot == 1) {
                  wrapper.cancel();
               } else if (slot > 1) {
                  --slot;
               }

               wrapper.write(Types.SHORT, (short)slot);
            });
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> ((Protocol1_9To1_8)EntityPacketRewriter1_9.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SET_PASSENGERS, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
         int vehicle = (Integer)wrapper.read(Types.VAR_INT);
         IntList oldPassengers = tracker.getPassengers(vehicle);
         int count = (Integer)wrapper.read(Types.VAR_INT);
         IntList passengers = new IntArrayList();

         for(int i = 0; i < count; ++i) {
            passengers.add((Integer)wrapper.read(Types.VAR_INT));
         }

         tracker.setPassengers(vehicle, passengers);
         if (!oldPassengers.isEmpty()) {
            for(Integer passenger : oldPassengers) {
               PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_LINK, (UserConnection)wrapper.user());
               detach.write(Types.INT, passenger);
               detach.write(Types.INT, -1);
               detach.write(Types.BOOLEAN, false);
               detach.scheduleSend(Protocol1_9To1_8.class);
            }
         }

         for(int i = 0; i < count; ++i) {
            int attachedEntityId = passengers.getInt(i);
            int holdingEntityId = i == 0 ? vehicle : passengers.getInt(i - 1);
            PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_LINK, (UserConnection)wrapper.user());
            attach.write(Types.INT, attachedEntityId);
            attach.write(Types.INT, holdingEntityId);
            attach.write(Types.BOOLEAN, false);
            attach.scheduleSend(Protocol1_9To1_8.class);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.TELEPORT_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.DOUBLE, Protocol1_9To1_8.DOUBLE_TO_INT_TIMES_32);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (tracker.entityType(entityId) == EntityTypes1_9.EntityType.BOAT) {
                  byte yaw = (Byte)wrapper.get(Types.BYTE, 1);
                  yaw = (byte)(yaw - 64);
                  wrapper.set(Types.BYTE, 0, yaw);
                  int y = (Integer)wrapper.get(Types.INT, 1);
                  y += 10;
                  wrapper.set(Types.INT, 1, y);
               }

               tracker.resetEntityOffset(entityId);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.UPDATE_ATTRIBUTES, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
         boolean player = entityId == tracker.clientEntityId();
         int removed = 0;
         int size = (Integer)wrapper.passthrough(Types.INT);

         for(int i = 0; i < size; ++i) {
            String key = (String)wrapper.read(Types.STRING);
            double value = (Double)wrapper.read(Types.DOUBLE);
            int modifierSize = (Integer)wrapper.read(Types.VAR_INT);
            boolean valid = ((Protocol1_9To1_8)this.protocol).getItemRewriter().VALID_ATTRIBUTES.contains(key);
            if (valid) {
               wrapper.write(Types.STRING, key);
               wrapper.write(Types.DOUBLE, value);
               wrapper.write(Types.VAR_INT, modifierSize);
            }

            List<Pair<Byte, Double>> modifiers = new ArrayList();

            for(int j = 0; j < modifierSize; ++j) {
               UUID modifierId = (UUID)wrapper.read(Types.UUID);
               double amount = (Double)wrapper.read(Types.DOUBLE);
               byte operation = (Byte)wrapper.read(Types.BYTE);
               if (valid) {
                  wrapper.write(Types.UUID, modifierId);
                  wrapper.write(Types.DOUBLE, amount);
                  wrapper.write(Types.BYTE, operation);
               }

               modifiers.add(new Pair(operation, amount));
            }

            if (!valid) {
               if (player && key.equals("generic.attackSpeed")) {
                  ((CooldownStorage)wrapper.user().get(CooldownStorage.class)).setAttackSpeed(value, modifiers);
               }

               ++removed;
            }
         }

         wrapper.set(Types.INT, 0, size - removed);
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.UPDATE_MOB_EFFECT, (wrapper) -> {
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         int effectId = (Byte)wrapper.passthrough(Types.BYTE);
         byte amplifier = (Byte)wrapper.passthrough(Types.BYTE);
         if (effectId > 23) {
            wrapper.cancel();
         }

         EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
         if (effectId == 25 && entityId == tracker.clientEntityId()) {
            LevitationStorage levitation = (LevitationStorage)wrapper.user().get(LevitationStorage.class);
            levitation.setActive(true);
            levitation.setAmplifier(amplifier);
         }

      });
   }

   protected void registerRewrites() {
      this.mapEntityTypeWithData(EntityTypes1_9.EntityType.SHULKER, EntityTypes1_9.EntityType.MAGMA_CUBE).plainName();
      this.mapEntityTypeWithData(EntityTypes1_9.EntityType.SHULKER_BULLET, EntityTypes1_9.EntityType.WITCH).plainName();
      this.filter().handler(this::handleEntityData);
   }

   void handleEntityData(EntityDataHandlerEvent event, EntityData entityData) {
      EntityTracker1_9 tracker = (EntityTracker1_9)this.tracker(event.user());
      if (entityData.id() == EntityDataIndex1_9.ENTITY_STATUS.getIndex()) {
         tracker.getStatus().put(event.entityId(), (Byte)entityData.value());
      }

      EntityDataIndex1_9 metaIndex = EntityDataIndex1_8.searchIndex(event.entityType(), entityData.id());
      if (metaIndex == null) {
         event.cancel();
      } else if (metaIndex.getOldType() != null && metaIndex.getNewType() != null) {
         entityData.setId(metaIndex.getIndex());
         entityData.setDataTypeUnsafe(metaIndex.getOldType());
         Object value = entityData.getValue();
         switch (metaIndex.getNewType()) {
            case BYTE:
               if (metaIndex.getOldType() == EntityDataTypes1_8.BYTE) {
                  entityData.setValue(value);
               }

               if (metaIndex.getOldType() == EntityDataTypes1_8.INT) {
                  entityData.setValue(((Byte)value).intValue());
               }
               break;
            case OPTIONAL_UUID:
               if (metaIndex.getOldType() != EntityDataTypes1_8.STRING) {
                  event.cancel();
               } else {
                  UUID owner = (UUID)value;
                  entityData.setValue(owner != null ? owner.toString() : "");
               }
               break;
            case OPTIONAL_BLOCK_STATE:
               event.cancel();
               event.createExtraData(new EntityData(metaIndex.getIndex(), EntityDataTypes1_8.SHORT, ((Integer)value).shortValue()));
               break;
            case VAR_INT:
               if (metaIndex.getOldType() == EntityDataTypes1_8.BYTE) {
                  entityData.setValue(((Integer)value).byteValue());
               }

               if (metaIndex.getOldType() == EntityDataTypes1_8.SHORT) {
                  entityData.setValue(((Integer)value).shortValue());
               }

               if (metaIndex.getOldType() == EntityDataTypes1_8.INT) {
                  entityData.setValue(value);
               }
               break;
            case FLOAT:
            case STRING:
            case COMPONENT:
               entityData.setValue(value);
               break;
            case BOOLEAN:
               boolean bool = (Boolean)value;
               if (metaIndex == EntityDataIndex1_9.ABSTRACT_AGEABLE_AGE) {
                  entityData.setValue((byte)(bool ? -1 : 0));
               } else {
                  entityData.setValue((byte)(bool ? 1 : 0));
               }
               break;
            case ITEM:
               entityData.setValue(((Protocol1_9To1_8)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)value));
               break;
            case BLOCK_POSITION:
               BlockPosition position = (BlockPosition)value;
               entityData.setValue(position);
               break;
            case ROTATIONS:
               EulerAngle angle = (EulerAngle)value;
               entityData.setValue(angle);
               break;
            default:
               event.cancel();
         }

      } else {
         if (metaIndex == EntityDataIndex1_9.PLAYER_HAND) {
            byte status = (byte)tracker.getStatus().getOrDefault(event.entityId(), 0);
            if (((Byte)entityData.value() & 1) != 0) {
               status = (byte)(status | 16);
            } else {
               status = (byte)(status & -17);
            }

            event.createExtraData(new EntityData(EntityDataIndex1_9.ENTITY_STATUS.getIndex(), EntityDataTypes1_8.BYTE, status));
         }

         event.cancel();
      }
   }

   public EntityTypes1_9.EntityType typeFromId(int type) {
      return EntityTypes1_9.getTypeFromId(type, false);
   }

   public EntityTypes1_9.EntityType objectTypeFromId(int type) {
      return EntityTypes1_9.getTypeFromId(type, true);
   }
}
