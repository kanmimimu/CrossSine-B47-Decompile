package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public class SpawnPacketRewriter1_9 {
   public static final ValueTransformer toNewDouble;

   public static void register(final Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               wrapper.write(Types.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               int typeID = (Byte)wrapper.get(Types.BYTE, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.getTypeFromId(typeID, true));
            });
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int data = (Integer)wrapper.get(Types.INT, 0);
               short vX = 0;
               short vY = 0;
               short vZ = 0;
               if (data > 0) {
                  vX = (Short)wrapper.read(Types.SHORT);
                  vY = (Short)wrapper.read(Types.SHORT);
                  vZ = (Short)wrapper.read(Types.SHORT);
               }

               wrapper.write(Types.SHORT, vX);
               wrapper.write(Types.SHORT, vY);
               wrapper.write(Types.SHORT, vZ);
            });
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               int data = (Integer)wrapper.get(Types.INT, 0);
               int typeID = (Byte)wrapper.get(Types.BYTE, 0);
               if (EntityTypes1_8.getTypeFromId(typeID, true) == EntityTypes1_8.EntityType.POTION) {
                  PacketWrapper entityDataPacket = wrapper.create(ClientboundPackets1_9.SET_ENTITY_DATA, (PacketHandler)((wrapper1) -> {
                     wrapper1.write(Types.VAR_INT, entityID);
                     List<EntityData> entityData = new ArrayList();
                     Item item = new DataItem(373, (byte)1, (short)data, (CompoundTag)null);
                     protocol.getItemRewriter().handleItemToClient(wrapper.user(), item);
                     EntityData potion = new EntityData(5, EntityDataTypes1_9.ITEM, item);
                     entityData.add(potion);
                     wrapper1.write(Types1_9.ENTITY_DATA_LIST, entityData);
                  }));
                  wrapper.send(Protocol1_8To1_9.class);
                  entityDataPacket.send(Protocol1_8To1_9.class);
                  wrapper.cancel();
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ADD_EXPERIENCE_ORB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.EntityType.EXPERIENCE_ORB);
            });
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.SHORT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ADD_GLOBAL_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.EntityType.LIGHTNING_BOLT);
            });
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               wrapper.write(Types.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               int typeID = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.getTypeFromId(typeID, false));
            });
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types1_8.ENTITY_DATA_LIST, Types1_9.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               List<EntityData> entityDataList = (List)wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               if (tracker.hasEntity(entityId)) {
                  protocol.getEntityRewriter().handleEntityData(entityId, entityDataList, wrapper.user());
               } else {
                  protocol.getLogger().warning("Unable to find entity for entity data, entity ID: " + entityId);
                  entityDataList.clear();
               }

            });
            this.handler((wrapper) -> {
               List<EntityData> entityDataList = (List)wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.handleEntityData(entityID, entityDataList);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ADD_PAINTING, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.EntityType.PAINTING);
            });
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               wrapper.write(Types.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Types.STRING);
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.BYTE);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityID, EntityTypes1_9.EntityType.PLAYER);
            });
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.INT, SpawnPacketRewriter1_9.toNewDouble);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               short item = (Short)wrapper.read(Types.SHORT);
               if (item != 0) {
                  PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_9.SET_EQUIPPED_ITEM, (ByteBuf)null, wrapper.user());
                  packet.write(Types.VAR_INT, (Integer)wrapper.get(Types.VAR_INT, 0));
                  packet.write(Types.VAR_INT, 0);
                  packet.write(Types.ITEM1_8, new DataItem(item, (byte)1, (short)0, (CompoundTag)null));
                  packet.send(Protocol1_8To1_9.class);
               }

            });
            this.map(Types1_8.ENTITY_DATA_LIST, Types1_9.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               List<EntityData> entityDataList = (List)wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               if (tracker.hasEntity(entityId)) {
                  protocol.getEntityRewriter().handleEntityData(entityId, entityDataList, wrapper.user());
               } else {
                  protocol.getLogger().warning("Unable to find entity for entity data, entity ID: " + entityId);
                  entityDataList.clear();
               }

            });
            this.handler((wrapper) -> {
               List<EntityData> entityDataList = (List)wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.handleEntityData(entityID, entityDataList);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.REMOVE_ENTITIES, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT_ARRAY_PRIMITIVE);
            this.handler((wrapper) -> {
               int[] entities = (int[])wrapper.get(Types.VAR_INT_ARRAY_PRIMITIVE, 0);
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_8To1_9.class);

               for(int entity : entities) {
                  tracker.removeEntity(entity);
               }

            });
         }
      });
   }

   static {
      toNewDouble = new ValueTransformer(Types.DOUBLE) {
         public Double transform(PacketWrapper wrapper, Integer inputValue) {
            return (double)inputValue / (double)32.0F;
         }
      };
   }
}
