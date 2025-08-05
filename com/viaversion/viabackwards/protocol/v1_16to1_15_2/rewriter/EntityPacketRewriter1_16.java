package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.PlayerAttributesStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.WolfDataMaskStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.WorldNameTracker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;

public class EntityPacketRewriter1_16 extends EntityRewriter {
   final ValueTransformer dimensionTransformer;

   public EntityPacketRewriter1_16(Protocol1_16To1_15_2 protocol) {
      super(protocol);
      this.dimensionTransformer = new ValueTransformer(Types.STRING, Types.INT) {
         public Integer transform(PacketWrapper wrapper, String input) {
            Integer var10000;
            switch (Key.namespaced(input)) {
               case "minecraft:the_nether":
                  var10000 = -1;
                  break;
               case "minecraft:the_end":
                  var10000 = 1;
                  break;
               default:
                  var10000 = 0;
            }

            return var10000;
         }
      };
   }

   protected void registerPackets() {
      ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.ADD_ENTITY, new PacketHandlers() {
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
            this.handler((wrapper) -> {
               EntityType entityType = EntityPacketRewriter1_16.this.typeFromId((Integer)wrapper.get(Types.VAR_INT, 1));
               if (entityType == EntityTypes1_16.LIGHTNING_BOLT) {
                  wrapper.cancel();
                  PacketWrapper spawnLightningPacket = wrapper.create(ClientboundPackets1_15.ADD_GLOBAL_ENTITY);
                  spawnLightningPacket.write(Types.VAR_INT, (Integer)wrapper.get(Types.VAR_INT, 0));
                  spawnLightningPacket.write(Types.BYTE, (byte)1);
                  spawnLightningPacket.write(Types.DOUBLE, (Double)wrapper.get(Types.DOUBLE, 0));
                  spawnLightningPacket.write(Types.DOUBLE, (Double)wrapper.get(Types.DOUBLE, 1));
                  spawnLightningPacket.write(Types.DOUBLE, (Double)wrapper.get(Types.DOUBLE, 2));
                  spawnLightningPacket.send(Protocol1_16To1_15_2.class);
               }

            });
            this.handler(EntityPacketRewriter1_16.this.getSpawnTrackerWithDataHandler(EntityTypes1_16.FALLING_BLOCK));
         }
      });
      this.registerSpawnTracker(ClientboundPackets1_16.ADD_MOB);
      ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(EntityPacketRewriter1_16.this.dimensionTransformer);
            this.handler((wrapper) -> {
               WorldNameTracker worldNameTracker = (WorldNameTracker)wrapper.user().get(WorldNameTracker.class);
               String nextWorldName = (String)wrapper.read(Types.STRING);
               wrapper.passthrough(Types.LONG);
               wrapper.passthrough(Types.UNSIGNED_BYTE);
               wrapper.read(Types.BYTE);
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_16To1_15_2.class);
               int dimension = (Integer)wrapper.get(Types.INT, 0);
               if (clientWorld.getEnvironment() != null && dimension == clientWorld.getEnvironment().id() && (wrapper.user().isClientSide() || Via.getPlatform().isProxy() || wrapper.user().getProtocolInfo().protocolVersion().olderThanOrEqualTo(ProtocolVersion.v1_12_2) || !nextWorldName.equals(worldNameTracker.getWorldName()))) {
                  PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN);
                  packet.write(Types.INT, dimension == 0 ? -1 : 0);
                  packet.write(Types.LONG, 0L);
                  packet.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
                  packet.write(Types.STRING, "default");
                  packet.send(Protocol1_16To1_15_2.class);
               }

               if (clientWorld.setEnvironment(dimension)) {
                  EntityPacketRewriter1_16.this.tracker(wrapper.user()).clearEntities();
               }

               wrapper.write(Types.STRING, "default");
               wrapper.read(Types.BOOLEAN);
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  wrapper.set(Types.STRING, 0, "flat");
               }

               PlayerAttributesStorage attributes = (PlayerAttributesStorage)wrapper.user().get(PlayerAttributesStorage.class);
               boolean keepPlayerAttributes = (Boolean)wrapper.read(Types.BOOLEAN);
               if (keepPlayerAttributes) {
                  wrapper.send(Protocol1_16To1_15_2.class);
                  wrapper.cancel();
                  attributes.sendAttributes(wrapper.user(), EntityPacketRewriter1_16.this.tracker(wrapper.user()).clientEntityId());
               } else {
                  attributes.clearAttributes();
               }

               worldNameTracker.setWorldName(nextWorldName);
            });
         }
      });
      ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.read(Types.BYTE);
            this.read(Types.STRING_ARRAY);
            this.read(Types.NAMED_COMPOUND_TAG);
            this.map(EntityPacketRewriter1_16.this.dimensionTransformer);
            this.handler((wrapper) -> {
               WorldNameTracker worldNameTracker = (WorldNameTracker)wrapper.user().get(WorldNameTracker.class);
               worldNameTracker.setWorldName((String)wrapper.read(Types.STRING));
            });
            this.map(Types.LONG);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_16To1_15_2.class);
               clientWorld.setEnvironment((Integer)wrapper.get(Types.INT, 1));
               wrapper.write(Types.STRING, "default");
               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.passthrough(Types.BOOLEAN);
               wrapper.read(Types.BOOLEAN);
               if ((Boolean)wrapper.read(Types.BOOLEAN)) {
                  wrapper.set(Types.STRING, 0, "flat");
               }

            });
            this.handler(EntityPacketRewriter1_16.this.playerTrackerHandler());
         }
      });
      this.registerTracker(ClientboundPackets1_16.ADD_EXPERIENCE_ORB, EntityTypes1_16.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_16.ADD_PAINTING, EntityTypes1_16.PAINTING);
      this.registerTracker(ClientboundPackets1_16.ADD_PLAYER, EntityTypes1_16.PLAYER);
      this.registerRemoveEntities(ClientboundPackets1_16.REMOVE_ENTITIES);
      this.registerSetEntityData(ClientboundPackets1_16.SET_ENTITY_DATA, Types1_16.ENTITY_DATA_LIST, Types1_14.ENTITY_DATA_LIST);
      ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_ATTRIBUTES, (wrapper) -> {
         PlayerAttributesStorage attributes = (PlayerAttributesStorage)wrapper.user().get(PlayerAttributesStorage.class);
         int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
         int size = (Integer)wrapper.passthrough(Types.INT);

         for(int i = 0; i < size; ++i) {
            String identifier = Key.stripMinecraftNamespace((String)wrapper.read(Types.STRING));
            String mappedIdentifier = ((Protocol1_16To1_15_2)this.protocol).getMappingData().mappedAttributeIdentifier(identifier);
            wrapper.write(Types.STRING, mappedIdentifier);
            double value = (Double)wrapper.passthrough(Types.DOUBLE);
            int count = (Integer)wrapper.passthrough(Types.VAR_INT);
            PlayerAttributesStorage.AttributeModifier[] modifiers = new PlayerAttributesStorage.AttributeModifier[count];

            for(int j = 0; j < count; ++j) {
               UUID uuid = (UUID)wrapper.passthrough(Types.UUID);
               double amount = (Double)wrapper.passthrough(Types.DOUBLE);
               byte operation = (Byte)wrapper.passthrough(Types.BYTE);
               modifiers[j] = new PlayerAttributesStorage.AttributeModifier(uuid, amount, operation);
            }

            if (entityId == this.tracker(wrapper.user()).clientEntityId()) {
               attributes.addAttribute(mappedIdentifier, new PlayerAttributesStorage.Attribute(value, modifiers));
            }
         }

      });
      ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.PLAYER_INFO, (wrapper) -> {
         int action = (Integer)wrapper.passthrough(Types.VAR_INT);
         int playerCount = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < playerCount; ++i) {
            wrapper.passthrough(Types.UUID);
            if (action != 0) {
               if (action == 1) {
                  wrapper.passthrough(Types.VAR_INT);
               } else if (action == 2) {
                  wrapper.passthrough(Types.VAR_INT);
               } else if (action == 3) {
                  ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.OPTIONAL_COMPONENT));
               }
            } else {
               wrapper.passthrough(Types.STRING);
               int properties = (Integer)wrapper.passthrough(Types.VAR_INT);

               for(int j = 0; j < properties; ++j) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.OPTIONAL_STRING);
               }

               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.VAR_INT);
               ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.OPTIONAL_COMPONENT));
            }
         }

      });
   }

   protected void registerRewrites() {
      this.filter().handler((event, data) -> {
         data.setDataType(Types1_14.ENTITY_DATA_TYPES.byId(data.dataType().typeId()));
         EntityDataType type = data.dataType();
         if (type == Types1_14.ENTITY_DATA_TYPES.itemType) {
            data.setValue(((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.getValue()));
         } else if (type == Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType) {
            data.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId((Integer)data.getValue()));
         } else if (type == Types1_14.ENTITY_DATA_TYPES.particleType) {
            this.rewriteParticle(event.user(), (Particle)data.getValue());
         } else if (type == Types1_14.ENTITY_DATA_TYPES.optionalComponentType) {
            JsonElement text = (JsonElement)data.value();
            if (text != null) {
               ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(event.user(), text);
            }
         }

      });
      this.filter().type(EntityTypes1_16.ZOGLIN).cancel(16);
      this.filter().type(EntityTypes1_16.HOGLIN).cancel(15);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(16);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(17);
      this.filter().type(EntityTypes1_16.PIGLIN).cancel(18);
      this.filter().type(EntityTypes1_16.STRIDER).index(15).handler((event, data) -> {
         boolean baby = (Boolean)data.value();
         data.setTypeAndValue(Types1_14.ENTITY_DATA_TYPES.varIntType, baby ? 1 : 3);
      });
      this.filter().type(EntityTypes1_16.STRIDER).cancel(16);
      this.filter().type(EntityTypes1_16.STRIDER).cancel(17);
      this.filter().type(EntityTypes1_16.STRIDER).cancel(18);
      this.filter().type(EntityTypes1_16.FISHING_BOBBER).cancel(8);
      this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).cancel(8);
      this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).handler((event, data) -> {
         if (event.index() >= 8) {
            event.setIndex(event.index() + 1);
         }

      });
      this.filter().type(EntityTypes1_16.WOLF).index(16).handler((event, data) -> {
         byte mask = (Byte)data.value();
         StoredEntityData entityData = this.tracker(event.user()).entityData(event.entityId());
         entityData.put(new WolfDataMaskStorage(mask));
      });
      this.filter().type(EntityTypes1_16.WOLF).index(20).handler((event, data) -> {
         StoredEntityData entityData = this.tracker(event.user()).entityDataIfPresent(event.entityId());
         byte previousMask = 0;
         if (entityData != null) {
            WolfDataMaskStorage wolfData = (WolfDataMaskStorage)entityData.get(WolfDataMaskStorage.class);
            if (wolfData != null) {
               previousMask = wolfData.tameableMask();
            }
         }

         int angerTime = (Integer)data.value();
         byte tameableMask = (byte)(angerTime > 0 ? previousMask | 2 : previousMask & -3);
         event.createExtraData(new EntityData(16, Types1_14.ENTITY_DATA_TYPES.byteType, tameableMask));
         event.cancel();
      });
   }

   public void onMappingDataLoaded() {
      this.mapTypes();
      this.mapEntityTypeWithData(EntityTypes1_16.HOGLIN, EntityTypes1_16.COW).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.ZOGLIN, EntityTypes1_16.COW).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.PIGLIN, EntityTypes1_16.ZOMBIFIED_PIGLIN).jsonName();
      this.mapEntityTypeWithData(EntityTypes1_16.STRIDER, EntityTypes1_16.MAGMA_CUBE).jsonName();
   }

   public EntityType typeFromId(int typeId) {
      return EntityTypes1_16.getTypeFromId(typeId);
   }
}
