package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8;

import com.google.common.base.Joiner;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.util.IdAndData;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.util.PacketUtil;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ClientboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ServerboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.Particle1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.MapData;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.MapIcon;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.TabListEntry;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider.GameProfileFetcher;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.rewriter.EntityDataRewriter;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.rewriter.TextRewriter;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.MapStorage;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.TablistStorage;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage.WindowTracker;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_7_6_10Tor1_8 extends AbstractProtocol {
   final ItemRewriter itemRewriter = new ItemRewriter(this);
   final TextRewriter chatComponentRewriter = new TextRewriter(this);
   final EntityDataRewriter entityDataRewriter = new EntityDataRewriter(this);
   public static final ValueTransformer LEGACY_TO_JSON;
   public static final ValueTransformer LEGACY_TO_JSON_TRANSLATE;

   public Protocolr1_7_6_10Tor1_8() {
      super(ClientboundPackets1_7_2.class, ClientboundPackets1_8.class, ServerboundPackets1_7_2.class, ServerboundPackets1_8.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.SHORT_BYTE_ARRAY, Types.BYTE_ARRAY_PRIMITIVE);
            this.map(Types.SHORT_BYTE_ARRAY, Types.BYTE_ARRAY_PRIMITIVE);
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, new PacketHandlers() {
         public void register() {
            this.read(Types.STRING);
            this.read(Types.STRING);
            this.handler((wrapper) -> {
               ProtocolInfo protocolInfo = wrapper.user().getProtocolInfo();
               wrapper.write(Types.STRING, protocolInfo.getUuid().toString());
               wrapper.write(Types.STRING, protocolInfo.getUsername());
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.create(Types.BOOLEAN, false);
            this.handler((wrapper) -> {
               ProtocolInfo protocolInfo = wrapper.user().getProtocolInfo();
               TablistStorage tablistStorage = (TablistStorage)wrapper.user().get(TablistStorage.class);
               tablistStorage.sendTempEntry(new TabListEntry(protocolInfo.getUsername(), protocolInfo.getUuid()));
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               byte dimensionId = (Byte)wrapper.get(Types.BYTE, 0);
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               tracker.trackEntity(entityId, EntityTypes1_8.EntityType.PLAYER);
               tracker.setPlayerID(entityId);
               wrapper.user().getClientWorld(Protocolr1_7_6_10Tor1_8.class).setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.CHAT, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> wrapper.write(Types.STRING, Protocolr1_7_6_10Tor1_8.this.chatComponentRewriter.toClient(wrapper.user(), (String)wrapper.read(Types.STRING))));
            this.create(Types.BYTE, (byte)0);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.SHORT);
            this.map(Types1_7_6.ITEM, Types.ITEM1_8);
            this.handler((wrapper) -> Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_DEFAULT_SPAWN_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_INT, Types.BLOCK_POSITION1_8);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_HEALTH, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.SHORT, Types.VAR_INT);
            this.map(Types.FLOAT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               if (wrapper.user().getClientWorld(Protocolr1_7_6_10Tor1_8.class).setEnvironment((Integer)wrapper.get(Types.INT, 0))) {
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).clear();
                  entityTracker.clear();
                  entityTracker.trackEntity(entityTracker.getPlayerID(), EntityTypes1_8.EntityType.PLAYER);
               }

               ProtocolInfo protocolInfo = wrapper.user().getProtocolInfo();
               TablistStorage tablistStorage = (TablistStorage)wrapper.user().get(TablistStorage.class);
               tablistStorage.sendTempEntry(new TabListEntry(protocolInfo.getUsername(), protocolInfo.getUuid()));
               wrapper.send(Protocolr1_7_6_10Tor1_8.class);
               wrapper.cancel();
               List<EntityData> defaultEntityData = new ArrayList();
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.ENTITY_FLAGS.getNewIndex(), EntityDataTypes1_8.BYTE, (byte)0));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.ENTITY_AIR.getNewIndex(), EntityDataTypes1_8.SHORT, (short)300));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.ENTITY_LIVING_POTION_EFFECT_COLOR.getNewIndex(), EntityDataTypes1_8.INT, 0));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.ENTITY_LIVING_IS_POTION_EFFECT_AMBIENT.getNewIndex(), EntityDataTypes1_8.BYTE, (byte)0));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.ENTITY_LIVING_ARROWS.getNewIndex(), EntityDataTypes1_8.BYTE, (byte)0));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.HUMAN_SKIN_FLAGS.getNewIndex(), EntityDataTypes1_8.BYTE, (byte)0));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.HUMAN_ABSORPTION_HEARTS.getNewIndex(), EntityDataTypes1_8.FLOAT, 0.0F));
               defaultEntityData.add(new EntityData(EntityDataIndex1_7_6.HUMAN_SCORE.getNewIndex(), EntityDataTypes1_8.INT, 0));
               PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_DATA, (UserConnection)wrapper.user());
               setEntityData.write(Types.VAR_INT, entityTracker.getPlayerID());
               setEntityData.write(Types1_8.ENTITY_DATA_LIST, defaultEntityData);
               setEntityData.send(Protocolr1_7_6_10Tor1_8.class);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE, Types.DOUBLE, (stance) -> stance - (double)1.62F);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.read(Types.BOOLEAN);
            this.create(Types.BYTE, (byte)0);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.PLAYER_SLEEP, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types1_7_6.BLOCK_POSITION_BYTE, Types.BLOCK_POSITION1_8);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ADD_PLAYER, (wrapper) -> {
         int entityID = (Integer)wrapper.passthrough(Types.VAR_INT);
         UUID uuid = UUID.fromString((String)wrapper.read(Types.STRING));
         wrapper.write(Types.UUID, uuid);
         String name = (String)wrapper.read(Types.STRING);
         TablistStorage tablistStorage = (TablistStorage)wrapper.user().get(TablistStorage.class);
         TabListEntry tempTabEntry = new TabListEntry(name, uuid);
         int dataCount = (Integer)wrapper.read(Types.VAR_INT);

         for(int i = 0; i < dataCount; ++i) {
            String key = (String)wrapper.read(Types.STRING);
            String value = (String)wrapper.read(Types.STRING);
            String signature = (String)wrapper.read(Types.STRING);
            tempTabEntry.gameProfile.addProperty(new GameProfile.Property(key, value, signature));
         }

         wrapper.passthrough(Types.INT);
         wrapper.passthrough(Types.INT);
         wrapper.passthrough(Types.INT);
         wrapper.passthrough(Types.BYTE);
         wrapper.passthrough(Types.BYTE);
         short itemId = (Short)wrapper.read(Types.SHORT);
         Item currentItem = new DataItem(itemId, (byte)1, (short)0, (CompoundTag)null);
         this.itemRewriter.handleItemToClient(wrapper.user(), currentItem);
         wrapper.write(Types.SHORT, (short)currentItem.identifier());
         List<EntityData> entityDataList = (List)wrapper.read(Types1_7_6.ENTITY_DATA_LIST);
         this.entityDataRewriter.transform(wrapper.user(), EntityTypes1_8.EntityType.PLAYER, entityDataList);
         wrapper.write(Types1_8.ENTITY_DATA_LIST, entityDataList);
         tablistStorage.sendTempEntry(tempTabEntry);
         ((EntityTracker)wrapper.user().get(EntityTracker.class)).trackEntity(entityID, EntityTypes1_8.EntityType.PLAYER);
      });
      this.registerClientbound(ClientboundPackets1_7_2.TAKE_ITEM_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> ((EntityTracker)wrapper.user().get(EntityTracker.class)).removeEntity((Integer)wrapper.get(Types.VAR_INT, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               int typeID = (Byte)wrapper.get(Types.BYTE, 0);
               int x = (Integer)wrapper.get(Types.INT, 0);
               int y = (Integer)wrapper.get(Types.INT, 1);
               int z = (Integer)wrapper.get(Types.INT, 2);
               byte yaw = (Byte)wrapper.get(Types.BYTE, 2);
               int data = (Integer)wrapper.get(Types.INT, 3);
               EntityTypes1_8.EntityType type = EntityTypes1_8.getTypeFromId(typeID, true);
               tracker.trackEntity(entityID, type);
               tracker.updateEntityLocation(entityID, x, y, z, false);
               if (type == EntityTypes1_8.ObjectType.ITEM_FRAME.getType()) {
                  byte var10000;
                  switch (data) {
                     case 0:
                        z += 32;
                        var10000 = 0;
                        break;
                     case 1:
                        x -= 32;
                        var10000 = 64;
                        break;
                     case 2:
                        z -= 32;
                        var10000 = -128;
                        break;
                     case 3:
                        x += 32;
                        var10000 = -64;
                        break;
                     default:
                        var10000 = yaw;
                  }

                  yaw = var10000;
               } else if (type == EntityTypes1_8.ObjectType.FALLING_BLOCK.getType()) {
                  int id = data & '\uffff';
                  int metadata = data >> 16;
                  IdAndData block = new IdAndData(id, metadata);
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).remapBlockParticle(block);
                  data = block.getId() | block.getData() << 12;
               }

               y = Protocolr1_7_6_10Tor1_8.this.realignEntityY(type, y);
               wrapper.set(Types.INT, 0, x);
               wrapper.set(Types.INT, 1, y);
               wrapper.set(Types.INT, 2, z);
               wrapper.set(Types.BYTE, 2, yaw);
               wrapper.set(Types.INT, 3, data);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types1_7_6.ENTITY_DATA_LIST, Types1_8.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               int typeID = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               int x = (Integer)wrapper.get(Types.INT, 0);
               int y = (Integer)wrapper.get(Types.INT, 1);
               int z = (Integer)wrapper.get(Types.INT, 2);
               List<EntityData> entityDataList = (List)wrapper.get(Types1_8.ENTITY_DATA_LIST, 0);
               EntityTypes1_8.EntityType entityType = EntityTypes1_8.getTypeFromId(typeID, false);
               tracker.trackEntity(entityID, entityType);
               tracker.updateEntityLocation(entityID, x, y, z, false);
               tracker.updateEntityData(entityID, entityDataList);
               Protocolr1_7_6_10Tor1_8.this.entityDataRewriter.transform(wrapper.user(), entityType, entityDataList);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ADD_PAINTING, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.STRING);
            this.map(Types1_7_6.BLOCK_POSITION_INT, Types.BLOCK_POSITION1_8);
            this.map(Types.INT, Types.BYTE);
            this.handler((wrapper) -> {
               short rotation = (short)(Byte)wrapper.get(Types.BYTE, 0);
               BlockPosition pos = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
               int modX = 0;
               int modZ = 0;
               switch (rotation) {
                  case 0:
                     modZ = 1;
                     break;
                  case 1:
                     modX = -1;
                     break;
                  case 2:
                     modZ = -1;
                     break;
                  case 3:
                     modX = 1;
               }

               wrapper.set(Types.BLOCK_POSITION1_8, 0, new BlockPosition(pos.x() + modX, pos.y(), pos.z() + modZ));
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               ((EntityTracker)wrapper.user().get(EntityTracker.class)).trackEntity(entityID, EntityTypes1_8.EntityType.PAINTING);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ADD_EXPERIENCE_ORB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               ((EntityTracker)wrapper.user().get(EntityTracker.class)).trackEntity(entityID, EntityTypes1_8.EntityType.EXPERIENCE_ORB);
               wrapper.set(Types.INT, 1, Protocolr1_7_6_10Tor1_8.this.realignEntityY(EntityTypes1_8.EntityType.EXPERIENCE_ORB, (Integer)wrapper.get(Types.INT, 1)));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_ENTITY_MOTION, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.REMOVE_ENTITIES, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.INT_ARRAY, Types.VAR_INT_ARRAY_PRIMITIVE);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);

               for(int entityId : (int[])wrapper.get(Types.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                  tracker.removeEntity(entityId);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MOVE_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MOVE_ENTITY_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               byte x = (Byte)wrapper.get(Types.BYTE, 0);
               byte y = (Byte)wrapper.get(Types.BYTE, 1);
               byte z = (Byte)wrapper.get(Types.BYTE, 2);
               entityTracker.updateEntityLocation(entityId, x, y, z, true);
               if (ViaLegacy.getConfig().isDynamicOnground()) {
                  boolean onGround = (Byte)wrapper.get(Types.BYTE, 1) > -8;
                  entityTracker.getGroundMap().put((Integer)wrapper.get(Types.VAR_INT, 0), onGround);
                  wrapper.write(Types.BOOLEAN, onGround);
               } else {
                  wrapper.write(Types.BOOLEAN, true);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MOVE_ENTITY_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               if (ViaLegacy.getConfig().isDynamicOnground()) {
                  EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                  wrapper.write(Types.BOOLEAN, (Boolean)entityTracker.getGroundMap().getOrDefault(wrapper.get(Types.VAR_INT, 0), true));
               } else {
                  wrapper.write(Types.BOOLEAN, true);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MOVE_ENTITY_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               byte x = (Byte)wrapper.get(Types.BYTE, 0);
               byte y = (Byte)wrapper.get(Types.BYTE, 1);
               byte z = (Byte)wrapper.get(Types.BYTE, 2);
               entityTracker.updateEntityLocation(entityId, x, y, z, true);
               if (ViaLegacy.getConfig().isDynamicOnground()) {
                  boolean onGround = (Byte)wrapper.get(Types.BYTE, 1) > -8;
                  entityTracker.getGroundMap().put((Integer)wrapper.get(Types.VAR_INT, 0), onGround);
                  wrapper.write(Types.BOOLEAN, onGround);
               } else {
                  wrapper.write(Types.BOOLEAN, true);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.TELEPORT_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.create(Types.BOOLEAN, true);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
               int x = (Integer)wrapper.get(Types.INT, 0);
               int y = (Integer)wrapper.get(Types.INT, 1);
               int z = (Integer)wrapper.get(Types.INT, 2);
               entityTracker.updateEntityLocation(entityId, x, y, z, false);
               EntityTypes1_8.EntityType type = (EntityTypes1_8.EntityType)entityTracker.getTrackedEntities().get(entityId);
               wrapper.set(Types.INT, 1, Protocolr1_7_6_10Tor1_8.this.realignEntityY(type, y));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.ROTATE_HEAD, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_ENTITY_LINK, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               short leashState = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (leashState == 0) {
                  EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                  int ridingId = (Integer)wrapper.get(Types.INT, 0);
                  int vehicleId = (Integer)wrapper.get(Types.INT, 1);
                  tracker.updateEntityAttachState(ridingId, vehicleId);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types1_7_6.ENTITY_DATA_LIST, Types1_8.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               List<EntityData> entityDataList = (List)wrapper.get(Types1_8.ENTITY_DATA_LIST, 0);
               int entityID = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (tracker.getTrackedEntities().containsKey(entityID)) {
                  tracker.updateEntityData(entityID, entityDataList);
                  Protocolr1_7_6_10Tor1_8.this.entityDataRewriter.transform(wrapper.user(), (EntityTypes1_8.EntityType)tracker.getTrackedEntities().get(entityID), entityDataList);
                  if (entityDataList.isEmpty()) {
                     wrapper.cancel();
                  }
               } else {
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.UPDATE_MOB_EFFECT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT, Types.VAR_INT);
            this.create(Types.BOOLEAN, false);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.REMOVE_MOB_EFFECT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_EXPERIENCE, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.SHORT, Types.VAR_INT);
            this.map(Types.SHORT, Types.VAR_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.UPDATE_ATTRIBUTES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> {
               int amount = (Integer)wrapper.passthrough(Types.INT);

               for(int i = 0; i < amount; ++i) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.DOUBLE);
                  int modifierlength = (Short)wrapper.read(Types.SHORT);
                  wrapper.write(Types.VAR_INT, modifierlength);

                  for(int j = 0; j < modifierlength; ++j) {
                     wrapper.passthrough(Types.UUID);
                     wrapper.passthrough(Types.DOUBLE);
                     wrapper.passthrough(Types.BYTE);
                  }
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.LEVEL_CHUNK, (wrapper) -> {
         Environment dimension = wrapper.user().getClientWorld(Protocolr1_7_6_10Tor1_8.class).getEnvironment();
         Chunk chunk = (Chunk)wrapper.read(Types1_7_6.getChunk(dimension));
         ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(chunk);
         wrapper.write(ChunkType1_8.forEnvironment(dimension), chunk);
      });
      this.registerClientbound(ClientboundPackets1_7_2.CHUNK_BLOCKS_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY, Types.BLOCK_CHANGE_ARRAY);
            this.handler((wrapper) -> {
               int chunkX = (Integer)wrapper.get(Types.INT, 0);
               int chunkZ = (Integer)wrapper.get(Types.INT, 1);
               BlockChangeRecord[] blockChangeRecords = (BlockChangeRecord[])wrapper.get(Types.BLOCK_CHANGE_ARRAY, 0);

               for(BlockChangeRecord record : blockChangeRecords) {
                  int targetX = record.getSectionX() + (chunkX << 4);
                  int targetY = record.getY(-1);
                  int targetZ = record.getSectionZ() + (chunkZ << 4);
                  IdAndData block = IdAndData.fromRawData(record.getBlockId());
                  BlockPosition pos = new BlockPosition(targetX, targetY, targetZ);
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(pos, block);
                  record.setBlockId(block.toRawData());
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.BLOCK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE, Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               int blockId = (Integer)wrapper.read(Types.VAR_INT);
               int data = (Short)wrapper.read(Types.UNSIGNED_BYTE);
               BlockPosition pos = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
               IdAndData block = new IdAndData(blockId, data);
               ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(pos, block);
               wrapper.write(Types.VAR_INT, block.toRawData());
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.BLOCK_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT, Types.BLOCK_POSITION1_8);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.BLOCK_DESTRUCTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types1_7_6.BLOCK_POSITION_INT, Types.BLOCK_POSITION1_8);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MAP_BULK_CHUNK, (wrapper) -> {
         Chunk[] chunks = (Chunk[])wrapper.read(Types1_7_6.CHUNK_BULK);

         for(Chunk chunk : chunks) {
            ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(chunk);
         }

         wrapper.write(BulkChunkType1_8.TYPE, chunks);
      });
      this.registerClientbound(ClientboundPackets1_7_2.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int x = ((Float)wrapper.get(Types.FLOAT, 0)).intValue();
               int y = ((Float)wrapper.get(Types.FLOAT, 1)).intValue();
               int z = ((Float)wrapper.get(Types.FLOAT, 2)).intValue();
               int recordCount = (Integer)wrapper.get(Types.INT, 0);
               ChunkTracker chunkTracker = (ChunkTracker)wrapper.user().get(ChunkTracker.class);

               for(int i = 0; i < recordCount; ++i) {
                  BlockPosition pos = new BlockPosition(x + (Byte)wrapper.passthrough(Types.BYTE), y + (Byte)wrapper.passthrough(Types.BYTE), z + (Byte)wrapper.passthrough(Types.BYTE));
                  chunkTracker.trackAndRemap(pos, new IdAndData(0, 0));
               }

            });
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.LEVEL_EVENT, (wrapper) -> {
         int effectId = (Integer)wrapper.read(Types.INT);
         BlockPosition pos = (BlockPosition)wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
         int data = (Integer)wrapper.read(Types.INT);
         boolean disableRelativeVolume = (Boolean)wrapper.read(Types.BOOLEAN);
         if (!disableRelativeVolume && effectId == 2006) {
            wrapper.setPacketType(ClientboundPackets1_8.LEVEL_PARTICLES);
            Random rnd = new Random();
            ChunkTracker chunkTracker = (ChunkTracker)wrapper.user().get(ChunkTracker.class);
            IdAndData block = chunkTracker.getBlockNotNull(pos);
            if (block.getId() != 0) {
               double var21 = (double)Math.min(0.2F + (float)data / 15.0F, 10.0F);
               if (var21 > (double)2.5F) {
                  var21 = (double)2.5F;
               }

               float var25 = this.randomFloatClamp(rnd, 0.0F, ((float)Math.PI * 2F));
               double var26 = (double)this.randomFloatClamp(rnd, 0.75F, 1.0F);
               float offsetY = (float)((double)0.2F + var21 / (double)100.0F);
               float offsetX = (float)(Math.cos((double)var25) * (double)0.2F * var26 * var26 * (var21 + 0.2));
               float offsetZ = (float)(Math.sin((double)var25) * (double)0.2F * var26 * var26 * (var21 + 0.2));
               int amount = (int)((double)150.0F * var21);
               wrapper.write(Types.INT, Particle1_7_6.BLOCK_DUST.ordinal());
               wrapper.write(Types.BOOLEAN, false);
               wrapper.write(Types.FLOAT, (float)pos.x() + 0.5F);
               wrapper.write(Types.FLOAT, (float)pos.y() + 1.0F);
               wrapper.write(Types.FLOAT, (float)pos.z() + 0.5F);
               wrapper.write(Types.FLOAT, offsetX);
               wrapper.write(Types.FLOAT, offsetY);
               wrapper.write(Types.FLOAT, offsetZ);
               wrapper.write(Types.FLOAT, 0.15F);
               wrapper.write(Types.INT, amount);
               wrapper.write(Types.VAR_INT, block.getId() | block.getData() << 12);
            } else {
               wrapper.cancel();
            }
         } else {
            if (!disableRelativeVolume && effectId == 1003) {
               if (Math.random() > (double)0.5F) {
                  effectId = 1006;
               }
            } else if (!disableRelativeVolume && effectId == 2001) {
               ChunkTracker chunkTracker = (ChunkTracker)wrapper.user().get(ChunkTracker.class);
               int blockID = data & 4095;
               int blockData = data >> 12 & 255;
               IdAndData block = new IdAndData(blockID, blockData);
               chunkTracker.remapBlockParticle(block);
               data = block.getId() | block.getData() << 12;
            }

            wrapper.write(Types.INT, effectId);
            wrapper.write(Types.BLOCK_POSITION1_8, pos);
            wrapper.write(Types.INT, data);
            wrapper.write(Types.BOOLEAN, disableRelativeVolume);
         }

      });
      this.registerClientbound(ClientboundPackets1_7_2.LEVEL_PARTICLES, (wrapper) -> {
         String[] parts = ((String)wrapper.read(Types.STRING)).split("_", 3);
         Particle1_7_6 particle = Particle1_7_6.find(parts[0]);
         if (particle == null) {
            particle = Particle1_7_6.BARRIER;
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               Logger var10000 = ViaLegacy.getPlatform().getLogger();
               String var8 = Arrays.toString(parts);
               var10000.warning("Could not find 1.8 particle for " + var8);
            }
         }

         wrapper.write(Types.INT, particle.ordinal());
         wrapper.write(Types.BOOLEAN, false);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.INT);
         if (particle == Particle1_7_6.ICON_CRACK) {
            int id = Integer.parseInt(parts[1]);
            int damage = 0;
            if (parts.length > 2) {
               damage = Integer.parseInt(parts[2]);
            }

            DataItem item = new DataItem(id, (byte)1, (short)damage, (CompoundTag)null);
            this.itemRewriter.handleItemToClient(wrapper.user(), item);
            wrapper.write(Types.VAR_INT, item.identifier());
            if (item.data() != 0) {
               wrapper.write(Types.VAR_INT, Integer.valueOf(item.data()));
            }
         } else if (particle != Particle1_7_6.BLOCK_CRACK && particle != Particle1_7_6.BLOCK_DUST) {
            if (particle.extra > 0) {
               throw new IllegalStateException("Tried to write particle which requires extra data, but no handler was found");
            }
         } else {
            int id = Integer.parseInt(parts[1]);
            int metadata = Integer.parseInt(parts[2]);
            IdAndData block = new IdAndData(id, metadata);
            ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).remapBlockParticle(block);
            wrapper.write(Types.VAR_INT, block.getId() | block.getData() << 12);
         }

      });
      this.registerClientbound(ClientboundPackets1_7_2.GAME_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               if ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0) == 3) {
                  PacketWrapper chatMessage = PacketWrapper.create(ClientboundPackets1_8.CHAT, (UserConnection)wrapper.user());
                  chatMessage.write(Types.STRING, (String)Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON.transform(chatMessage, "Your game mode has been updated"));
                  chatMessage.write(Types.BYTE, (byte)0);
                  chatMessage.send(Protocolr1_7_6_10Tor1_8.class);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.OPEN_SCREEN, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         short windowType = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         String title = (String)wrapper.read(Types.STRING);
         short slots = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         boolean useProvidedWindowTitle = (Boolean)wrapper.read(Types.BOOLEAN);
         ((WindowTracker)wrapper.user().get(WindowTracker.class)).types.put(windowId, windowType);
         String inventoryName;
         switch (windowType) {
            case 0:
               inventoryName = "minecraft:chest";
               break;
            case 1:
               inventoryName = "minecraft:crafting_table";
               title = "container.crafting";
               useProvidedWindowTitle = false;
               break;
            case 2:
               inventoryName = "minecraft:furnace";
               if (!useProvidedWindowTitle) {
                  title = "container.furnace";
               }
               break;
            case 3:
               inventoryName = "minecraft:dispenser";
               if (!useProvidedWindowTitle) {
                  title = "container.dispenser";
               }
               break;
            case 4:
               inventoryName = "minecraft:enchanting_table";
               if (!useProvidedWindowTitle) {
                  title = "container.enchant";
               }
               break;
            case 5:
               inventoryName = "minecraft:brewing_stand";
               if (!useProvidedWindowTitle) {
                  title = "container.brewing";
               }
               break;
            case 6:
               inventoryName = "minecraft:villager";
               if (!useProvidedWindowTitle || title.isEmpty()) {
                  title = "entity.Villager.name";
                  useProvidedWindowTitle = false;
               }
               break;
            case 7:
               inventoryName = "minecraft:beacon";
               if (!useProvidedWindowTitle) {
                  title = "container.beacon";
               }
               break;
            case 8:
               inventoryName = "minecraft:anvil";
               title = "container.repair";
               useProvidedWindowTitle = false;
               break;
            case 9:
               inventoryName = "minecraft:hopper";
               if (!useProvidedWindowTitle) {
                  title = "container.hopper";
               }
               break;
            case 10:
               inventoryName = "minecraft:dropper";
               if (!useProvidedWindowTitle) {
                  title = "container.dropper";
               }
               break;
            case 11:
               inventoryName = "EntityHorse";
               break;
            default:
               throw new IllegalArgumentException((new StringBuilder()).append("Unknown window type: ").append(windowType).toString());
         }

         if (windowType == 1 || windowType == 4 || windowType == 8) {
            slots = 0;
         }

         if (useProvidedWindowTitle) {
            title = (String)LEGACY_TO_JSON.transform(wrapper, title);
         } else {
            title = (String)LEGACY_TO_JSON_TRANSLATE.transform(wrapper, title);
         }

         wrapper.write(Types.STRING, inventoryName);
         wrapper.write(Types.STRING, title);
         wrapper.write(Types.UNSIGNED_BYTE, slots);
         if (windowType == 11) {
            wrapper.passthrough(Types.INT);
         }

      });
      this.registerClientbound(ClientboundPackets1_7_2.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               short windowId = (short)(Byte)wrapper.read(Types.BYTE);
               wrapper.write(Types.UNSIGNED_BYTE, windowId);
               short slot = (Short)wrapper.read(Types.SHORT);
               short windowType = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
               if (windowType == 4 && slot >= 1) {
                  ++slot;
               }

               wrapper.write(Types.SHORT, slot);
            });
            this.map(Types1_7_6.ITEM, Types.ITEM1_8);
            this.handler((wrapper) -> Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
               short windowType = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
               Item[] items = (Item[])wrapper.read(Types1_7_6.ITEM_ARRAY);
               if (windowType == 4) {
                  Item[] old = items;
                  items = new Item[items.length + 1];
                  items[0] = old[0];
                  System.arraycopy(old, 1, items, 2, old.length - 1);
                  items[1] = new DataItem(351, (byte)3, (short)4, (CompoundTag)null);
               }

               for(Item item : items) {
                  Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), item);
               }

               wrapper.write(Types.ITEM1_8_SHORT_ARRAY, items);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.CONTAINER_SET_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               short progressBar = (Short)wrapper.get(Types.SHORT, 0);
               short windowType = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
               if (windowType == 2) {
                  switch (progressBar) {
                     case 0:
                        progressBar = 2;
                        PacketWrapper windowProperty = PacketWrapper.create(ClientboundPackets1_8.CONTAINER_SET_DATA, (UserConnection)wrapper.user());
                        windowProperty.write(Types.UNSIGNED_BYTE, windowId);
                        windowProperty.write(Types.SHORT, Short.valueOf((short)3));
                        windowProperty.write(Types.SHORT, (short)200);
                        windowProperty.send(Protocolr1_7_6_10Tor1_8.class);
                        break;
                     case 1:
                        progressBar = 0;
                        break;
                     case 2:
                        progressBar = 1;
                  }

                  wrapper.set(Types.SHORT, 0, progressBar);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.UPDATE_SIGN, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT, Types.BLOCK_POSITION1_8);
            this.map(Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON);
            this.map(Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON);
            this.map(Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON);
            this.map(Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.MAP_ITEM_DATA, (wrapper) -> {
         int id = (Integer)wrapper.passthrough(Types.VAR_INT);
         byte[] data = (byte[])wrapper.read(Types.SHORT_BYTE_ARRAY);
         MapStorage mapStorage = (MapStorage)wrapper.user().get(MapStorage.class);
         MapData mapData = mapStorage.getMapData(id);
         if (mapData == null) {
            mapStorage.putMapData(id, mapData = new MapData());
         }

         if (data[0] == 1) {
            int count = (data.length - 1) / 3;
            mapData.mapIcons = new MapIcon[count];

            for(int i = 0; i < count; ++i) {
               mapData.mapIcons[i] = new MapIcon((byte)(data[i * 3 + 1] >> 4), (byte)(data[i * 3 + 1] & 15), data[i * 3 + 2], data[i * 3 + 3]);
            }
         } else if (data[0] == 2) {
            mapData.scale = data[1];
         }

         wrapper.write(Types.BYTE, mapData.scale);
         wrapper.write(Types.VAR_INT, mapData.mapIcons.length);

         for(MapIcon mapIcon : mapData.mapIcons) {
            wrapper.write(Types.BYTE, (byte)(mapIcon.direction << 4 | mapIcon.type & 15));
            wrapper.write(Types.BYTE, mapIcon.x);
            wrapper.write(Types.BYTE, mapIcon.z);
         }

         if (data[0] == 0) {
            byte x = data[1];
            byte z = data[2];
            int rows = data.length - 3;
            byte[] newData = new byte[rows];
            System.arraycopy(data, 3, newData, 0, rows);
            wrapper.write(Types.BYTE, (byte)1);
            wrapper.write(Types.BYTE, (byte)rows);
            wrapper.write(Types.BYTE, x);
            wrapper.write(Types.BYTE, z);
            wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, newData);
         } else {
            wrapper.write(Types.BYTE, (byte)0);
         }

      });
      this.registerClientbound(ClientboundPackets1_7_2.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT, Types.BLOCK_POSITION1_8);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_7_6.NBT, Types.NAMED_COMPOUND_TAG);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.OPEN_SIGN_EDITOR, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_INT, Types.BLOCK_POSITION1_8);
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.PLAYER_INFO, (wrapper) -> {
         String name = (String)wrapper.read(Types.STRING);
         boolean online = (Boolean)wrapper.read(Types.BOOLEAN);
         short ping = (Short)wrapper.read(Types.SHORT);
         TablistStorage tablistStorage = (TablistStorage)wrapper.user().get(TablistStorage.class);
         TabListEntry entry = (TabListEntry)tablistStorage.tablist.get(name);
         if (entry == null && online) {
            tablistStorage.tablist.put(name, entry = new TabListEntry(name, ping));
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, 1);
            wrapper.write(Types.UUID, entry.gameProfile.uuid);
            wrapper.write(Types.STRING, entry.gameProfile.userName);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, 0);
            wrapper.write(Types.VAR_INT, entry.ping);
            wrapper.write(Types.OPTIONAL_STRING, (Object)null);
         } else if (entry != null && !online) {
            tablistStorage.tablist.remove(name);
            wrapper.write(Types.VAR_INT, 4);
            wrapper.write(Types.VAR_INT, 1);
            wrapper.write(Types.UUID, entry.gameProfile.uuid);
         } else if (entry != null) {
            entry.ping = ping;
            wrapper.write(Types.VAR_INT, 2);
            wrapper.write(Types.VAR_INT, 1);
            wrapper.write(Types.UUID, entry.gameProfile.uuid);
            wrapper.write(Types.VAR_INT, entry.ping);
         } else {
            wrapper.cancel();
         }

      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_OBJECTIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               String value = (String)wrapper.read(Types.STRING);
               byte mode = (Byte)wrapper.passthrough(Types.BYTE);
               if (mode == 0 || mode == 2) {
                  wrapper.write(Types.STRING, value);
                  wrapper.write(Types.STRING, "integer");
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_SCORE, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE, Types.VAR_INT);
            this.handler((wrapper) -> {
               int mode = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (mode == 0) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.INT));
               } else {
                  wrapper.write(Types.STRING, "");
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.SET_PLAYER_TEAM, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               byte mode = (Byte)wrapper.passthrough(Types.BYTE);
               if (mode == 0 || mode == 2) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BYTE);
                  wrapper.write(Types.STRING, "always");
                  wrapper.write(Types.BYTE, (byte)0);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  int count = (Short)wrapper.read(Types.SHORT);
                  String[] playerNames = new String[count];

                  for(int i = 0; i < count; ++i) {
                     playerNames[i] = (String)wrapper.read(Types.STRING);
                  }

                  wrapper.write(Types.STRING_ARRAY, playerNames);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_7_2.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.read(Types.UNSIGNED_SHORT);
            this.handlerSoftFail((wrapper) -> {
               switch ((String)wrapper.get(Types.STRING, 0)) {
                  case "MC|Brand":
                     wrapper.write(Types.STRING, new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8));
                     break;
                  case "MC|TrList":
                     wrapper.passthrough(Types.INT);
                     int count = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

                     for(int i = 0; i < count; ++i) {
                        Item item = (Item)wrapper.read(Types1_7_6.ITEM);
                        Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), item);
                        wrapper.write(Types.ITEM1_8, item);
                        item = (Item)wrapper.read(Types1_7_6.ITEM);
                        Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), item);
                        wrapper.write(Types.ITEM1_8, item);
                        boolean has3Items = (Boolean)wrapper.passthrough(Types.BOOLEAN);
                        if (has3Items) {
                           item = (Item)wrapper.read(Types1_7_6.ITEM);
                           Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToClient(wrapper.user(), item);
                           wrapper.write(Types.ITEM1_8, item);
                        }

                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.write(Types.INT, 0);
                        wrapper.write(Types.INT, Integer.MAX_VALUE);
                     }
                     break;
                  case "MC|RPack":
                     String url = new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8);
                     wrapper.clearPacket();
                     wrapper.setPacketType(ClientboundPackets1_8.RESOURCE_PACK);
                     wrapper.write(Types.STRING, url);
                     wrapper.write(Types.STRING, "legacy");
               }

            });
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               String name = (String)wrapper.passthrough(Types.STRING);
               ProtocolInfo info = wrapper.user().getProtocolInfo();
               if (info.getUsername() == null) {
                  info.setUsername(name);
               }

               if (info.getUuid() == null) {
                  info.setUuid(ViaLegacy.getConfig().isLegacySkinLoading() ? ((GameProfileFetcher)Via.getManager().getProviders().get(GameProfileFetcher.class)).getMojangUUID(name) : (new GameProfile(name)).uuid);
               }

            });
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE_ARRAY_PRIMITIVE, Types.SHORT_BYTE_ARRAY);
            this.map(Types.BYTE_ARRAY_PRIMITIVE, Types.SHORT_BYTE_ARRAY);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.KEEP_ALIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT, Types.INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.INTERACT, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT, Types.INT);
            this.handler((wrapper) -> {
               int mode = (Integer)wrapper.read(Types.VAR_INT);
               if (mode == 2) {
                  wrapper.write(Types.BYTE, (byte)0);
                  wrapper.read(Types.FLOAT);
                  wrapper.read(Types.FLOAT);
                  wrapper.read(Types.FLOAT);
                  EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                  EntityTypes1_8.EntityType entityType = (EntityTypes1_8.EntityType)entityTracker.getTrackedEntities().get(wrapper.get(Types.INT, 0));
                  if (entityType == null || !entityType.isOrHasParent(EntityTypes1_8.EntityType.ARMOR_STAND)) {
                     wrapper.cancel();
                  }
               } else {
                  wrapper.write(Types.BYTE, (byte)mode);
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.handler((wrapper) -> wrapper.write(Types.DOUBLE, (Double)wrapper.get(Types.DOUBLE, 1) + 1.62));
            this.map(Types.DOUBLE);
            this.map(Types.BOOLEAN);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.handler((wrapper) -> wrapper.write(Types.DOUBLE, (Double)wrapper.get(Types.DOUBLE, 1) + 1.62));
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.PLAYER_ACTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT, Types.UNSIGNED_BYTE);
            this.map(Types.BLOCK_POSITION1_8, Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               short status = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (status == 5) {
                  wrapper.set(Types.UNSIGNED_BYTE, 1, (short)255);
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_8.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.ITEM1_8, Types1_7_6.ITEM);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               short direction = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               Item item = (Item)wrapper.get(Types1_7_6.ITEM, 0);
               Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToServer(wrapper.user(), item);
               if (item != null && item.identifier() == ItemList1_6.writtenBook.itemId() && direction == 255) {
                  PacketWrapper openBook = PacketWrapper.create(ClientboundPackets1_8.CUSTOM_PAYLOAD, (UserConnection)wrapper.user());
                  openBook.write(Types.STRING, "MC|BOpen");
                  openBook.send(Protocolr1_7_6_10Tor1_8.class);
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_8.SWING, (wrapper) -> {
         EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
         wrapper.write(Types.INT, entityTracker.getPlayerID());
         wrapper.write(Types.BYTE, (byte)1);
      });
      this.registerServerbound(ServerboundPackets1_8.PLAYER_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT, Types.INT);
            this.map(Types.VAR_INT, Types.BYTE, (action) -> (byte)(action + 1));
            this.map(Types.VAR_INT, Types.INT);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.PLAYER_INPUT, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               byte flags = (Byte)wrapper.read(Types.BYTE);
               wrapper.write(Types.BOOLEAN, (flags & 1) > 0);
               wrapper.write(Types.BOOLEAN, (flags & 2) > 0);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_8.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.read(Types.UNSIGNED_BYTE);
               wrapper.write(Types.BYTE, (byte)windowId);
               short slot = (Short)wrapper.passthrough(Types.SHORT);
               short windowType = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
               if (windowType == 4) {
                  if (slot == 1) {
                     PacketWrapper resetHandItem = PacketWrapper.create(ClientboundPackets1_8.CONTAINER_SET_SLOT, (UserConnection)wrapper.user());
                     resetHandItem.write(Types.UNSIGNED_BYTE, Short.valueOf((short)-1));
                     resetHandItem.write(Types.SHORT, Short.valueOf((short)0));
                     resetHandItem.write(Types.ITEM1_8, (Object)null);
                     resetHandItem.send(Protocolr1_7_6_10Tor1_8.class);
                     PacketWrapper setLapisSlot = PacketWrapper.create(ClientboundPackets1_8.CONTAINER_SET_SLOT, (UserConnection)wrapper.user());
                     setLapisSlot.write(Types.UNSIGNED_BYTE, windowId);
                     setLapisSlot.write(Types.SHORT, slot);
                     setLapisSlot.write(Types.ITEM1_8, new DataItem(351, (byte)3, (short)4, (CompoundTag)null));
                     setLapisSlot.send(Protocolr1_7_6_10Tor1_8.class);
                     wrapper.cancel();
                  } else if (slot > 1) {
                     wrapper.set(Types.SHORT, 0, (short)(slot - 1));
                  }
               }

            });
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.ITEM1_8, Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
         }
      });
      this.registerServerbound(ServerboundPackets1_8.SET_CREATIVE_MODE_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(Types.ITEM1_8, Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
         }
      });
      this.registerServerbound(ServerboundPackets1_8.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, Types1_7_6.BLOCK_POSITION_SHORT);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  JsonElement component = (JsonElement)wrapper.read(Types.COMPONENT);
                  String text = TextComponentSerializer.V1_8.deserialize(component).asUnformattedString();
                  if (text.length() > 15) {
                     text = text.substring(0, 15);
                  }

                  wrapper.write(Types.STRING, text);
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_8.COMMAND_SUGGESTION, (wrapper) -> {
         String text = (String)wrapper.read(Types.STRING);
         wrapper.clearPacket();
         wrapper.write(Types.STRING, text);
      });
      this.registerServerbound(ServerboundPackets1_8.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BOOLEAN);
            this.create(Types.BYTE, (byte)2);
            this.map(Types.UNSIGNED_BYTE, Types.BOOLEAN, (flags) -> (flags & 1) == 1);
         }
      });
      this.registerServerbound(ServerboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               String channel = (String)wrapper.read(Types.STRING);
               if (ViaLegacy.getConfig().isIgnoreLong1_8ChannelNames() && channel.length() > 16) {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ViaLegacy.getPlatform().getLogger().warning("Ignoring serverbound plugin channel, as it is longer than 16 characters: '" + channel + "'");
                  }

                  wrapper.cancel();
               } else {
                  switch (channel) {
                     case "MC|BEdit":
                     case "MC|BSign":
                        Item item = (Item)wrapper.read(Types.ITEM1_8);
                        Protocolr1_7_6_10Tor1_8.this.itemRewriter.handleItemToServer(wrapper.user(), item);
                        wrapper.write(Types1_7_6.ITEM, item);
                        break;
                     case "MC|Brand":
                     case "MC|ItemName":
                        String content = (String)wrapper.read(Types.STRING);
                        wrapper.write(Types.REMAINING_BYTES, content.getBytes(StandardCharsets.UTF_8));
                        break;
                     case "MC|AdvCdm":
                        byte type = (Byte)wrapper.passthrough(Types.BYTE);
                        if (type == 0) {
                           wrapper.passthrough(Types.INT);
                           wrapper.passthrough(Types.INT);
                           wrapper.passthrough(Types.INT);
                        } else {
                           if (type != 1) {
                              if (!Via.getConfig().isSuppressConversionWarnings()) {
                                 ViaLegacy.getPlatform().getLogger().warning((new StringBuilder()).append("Unknown 1.8 command block type: ").append(type).toString());
                              }

                              wrapper.cancel();
                              return;
                           }

                           wrapper.passthrough(Types.INT);
                        }

                        wrapper.passthrough(Types.STRING);
                        wrapper.read(Types.BOOLEAN);
                        break;
                     case "REGISTER":
                     case "UNREGISTER":
                        byte[] channels = (byte[])wrapper.read(Types.REMAINING_BYTES);
                        if (ViaLegacy.getConfig().isIgnoreLong1_8ChannelNames()) {
                           String[] registeredChannels = (new String(channels, StandardCharsets.UTF_8)).split("\u0000");
                           List<String> validChannels = new ArrayList(registeredChannels.length);

                           for(String registeredChannel : registeredChannels) {
                              if (registeredChannel.length() > 16) {
                                 if (!Via.getConfig().isSuppressConversionWarnings()) {
                                    ViaLegacy.getPlatform().getLogger().warning("Ignoring serverbound plugin channel register of '" + registeredChannel + "', as it is longer than 16 characters");
                                 }
                              } else {
                                 validChannels.add(registeredChannel);
                              }
                           }

                           if (validChannels.isEmpty()) {
                              wrapper.cancel();
                              return;
                           }

                           channels = Joiner.on('\u0000').join(validChannels).getBytes(StandardCharsets.UTF_8);
                        }

                        wrapper.write(Types.REMAINING_BYTES, channels);
                  }

                  short length = (short)PacketUtil.calculateLength(wrapper);
                  wrapper.resetReader();
                  wrapper.write(Types.STRING, channel);
                  wrapper.write(Types.SHORT, length);
               }
            });
         }
      });
      this.cancelServerbound(ServerboundPackets1_8.TELEPORT_TO_ENTITY);
      this.cancelServerbound(ServerboundPackets1_8.RESOURCE_PACK);
   }

   float randomFloatClamp(Random rnd, float min, float max) {
      return min >= max ? min : rnd.nextFloat() * (max - min) + min;
   }

   int realignEntityY(EntityTypes1_8.EntityType type, int y) {
      float yPos = (float)y / 32.0F;
      float yOffset = 0.0F;
      if (type == EntityTypes1_8.ObjectType.FALLING_BLOCK.getType()) {
         yOffset = 0.49F;
      }

      if (type == EntityTypes1_8.ObjectType.TNT_PRIMED.getType()) {
         yOffset = 0.49F;
      }

      if (type == EntityTypes1_8.ObjectType.ENDER_CRYSTAL.getType()) {
         yOffset = 1.0F;
      } else if (type == EntityTypes1_8.ObjectType.MINECART.getType()) {
         yOffset = 0.35F;
      } else if (type == EntityTypes1_8.ObjectType.BOAT.getType()) {
         yOffset = 0.3F;
      } else if (type == EntityTypes1_8.ObjectType.ITEM.getType()) {
         yOffset = 0.12F;
      } else if (type == EntityTypes1_8.ObjectType.LEASH.getType()) {
         yOffset = 0.5F;
      } else if (type == EntityTypes1_8.EntityType.EXPERIENCE_ORB) {
         yOffset = 0.25F;
      }

      return (int)Math.floor((double)((yPos - yOffset) * 32.0F));
   }

   public void register(ViaProviders providers) {
      providers.require(GameProfileFetcher.class);
   }

   public void init(UserConnection userConnection) {
      userConnection.addClientWorld(Protocolr1_7_6_10Tor1_8.class, new ClientWorld());
      userConnection.put(new TablistStorage(userConnection));
      userConnection.put(new WindowTracker());
      userConnection.put(new EntityTracker(userConnection));
      userConnection.put(new MapStorage());
      userConnection.put(new ChunkTracker());
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityDataRewriter getEntityDataRewriter() {
      return this.entityDataRewriter;
   }

   static {
      LEGACY_TO_JSON = new ValueTransformer(Types.STRING, Types.STRING) {
         public String transform(PacketWrapper packetWrapper, String message) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", message);
            return jsonObject.toString();
         }
      };
      LEGACY_TO_JSON_TRANSLATE = new ValueTransformer(Types.STRING, Types.STRING) {
         public String transform(PacketWrapper packetWrapper, String message) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("translate", message);
            return jsonObject.toString();
         }
      };
   }
}
