package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2;

import com.google.common.collect.Lists;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.ChunkUtil;
import com.viaversion.viaversion.util.IdAndData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.model.Location;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.EntityList1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.Sound;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.sound.SoundType;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.AbstractTrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.TrackedEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.model.TrackedLivingEntity;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ServerboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.provider.OldAuthProvider;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.ChestStateTracker;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.task.EntityTrackerTickTask;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ClientboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ServerboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.EntityDataTypes1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.data.EntityDataIndex1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ProtocolMetadataStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data.EntityDataIndex1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_2_4_5Tor1_3_1_2 extends StatelessProtocol {
   final ItemRewriter itemRewriter = new ItemRewriter(this);

   public Protocolr1_2_4_5Tor1_3_1_2() {
      super(ClientboundPackets1_2_4.class, ClientboundPackets1_3_1.class, ServerboundPackets1_2_4.class, ServerboundPackets1_3_1.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.registerClientbound(ClientboundPackets1_2_4.HANDSHAKE, ClientboundPackets1_3_1.SHARED_KEY, (wrapper) -> {
         String serverHash = (String)wrapper.read(Types1_6_4.STRING);
         if (!serverHash.trim().isEmpty() && !serverHash.equalsIgnoreCase("-")) {
            try {
               ((OldAuthProvider)Via.getManager().getProviders().get(OldAuthProvider.class)).sendAuthRequest(wrapper.user(), serverHash);
            } catch (Throwable e) {
               ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Could not authenticate with mojang for joinserver request!", e);
               wrapper.cancel();
               PacketWrapper kick = PacketWrapper.create(ClientboundPackets1_3_1.DISCONNECT, (UserConnection)wrapper.user());
               kick.write(Types1_6_4.STRING, "Failed to log in: Invalid session (Try restarting your game and the launcher)");
               kick.send(Protocolr1_2_4_5Tor1_3_1_2.class);
               return;
            }
         }

         ProtocolInfo info = wrapper.user().getProtocolInfo();
         PacketWrapper login = PacketWrapper.create(ServerboundPackets1_2_4.LOGIN, (UserConnection)wrapper.user());
         login.write(Types.INT, info.serverProtocolVersion().getVersion());
         login.write(Types1_6_4.STRING, info.getUsername());
         login.write(Types1_6_4.STRING, "");
         login.write(Types.INT, 0);
         login.write(Types.INT, 0);
         login.write(Types.BYTE, (byte)0);
         login.write(Types.BYTE, (byte)0);
         login.write(Types.BYTE, (byte)0);
         login.sendToServer(Protocolr1_2_4_5Tor1_3_1_2.class);
         State currentState = wrapper.user().getProtocolInfo().getServerState();
         if (currentState != State.LOGIN) {
            wrapper.cancel();
         } else {
            wrapper.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
            wrapper.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
            ((ProtocolMetadataStorage)wrapper.user().get(ProtocolMetadataStorage.class)).skipEncryption = true;
         }

      });
      this.registerClientbound(ClientboundPackets1_2_4.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.read(Types1_6_4.STRING);
            this.map(Types1_6_4.STRING);
            this.map(Types.INT, Types.BYTE);
            this.map(Types.INT, Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class).setEnvironment((Byte)wrapper.get(Types.BYTE, 1));
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               entityTracker.setPlayerID((Integer)wrapper.get(Types.INT, 0));
               entityTracker.getTrackedEntities().put(entityTracker.getPlayerID(), new TrackedLivingEntity(entityTracker.getPlayerID(), new Location((double)8.0F, (double)64.0F, (double)8.0F), EntityTypes1_8.EntityType.PLAYER));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               int itemId = (Short)wrapper.read(Types.SHORT);
               short itemDamage = (Short)wrapper.read(Types.SHORT);
               wrapper.write(Types1_7_6.ITEM, itemId < 0 ? null : new DataItem(itemId, (byte)1, itemDamage, (CompoundTag)null));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types1_6_4.STRING);
            this.handler((wrapper) -> {
               if (wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class).setEnvironment((Integer)wrapper.get(Types.INT, 0))) {
                  ((ChestStateTracker)wrapper.user().get(ChestStateTracker.class)).clear();
                  EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
                  entityTracker.getTrackedEntities().clear();
                  entityTracker.getTrackedEntities().put(entityTracker.getPlayerID(), new TrackedLivingEntity(entityTracker.getPlayerID(), new Location((double)8.0F, (double)64.0F, (double)8.0F), EntityTypes1_8.EntityType.PLAYER));
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_6_4.STRING);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.UNSIGNED_SHORT);
            this.handler((wrapper) -> wrapper.write(Types1_3_1.ENTITY_DATA_LIST, Lists.newArrayList(new EntityData[]{new EntityData(0, EntityDataTypes1_3_1.BYTE, (byte)0)})));
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               double x = (double)(Integer)wrapper.get(Types.INT, 1) / (double)32.0F;
               double y = (double)(Integer)wrapper.get(Types.INT, 2) / (double)32.0F;
               double z = (double)(Integer)wrapper.get(Types.INT, 3) / (double)32.0F;
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               tracker.getTrackedEntities().put(entityId, new TrackedLivingEntity(entityId, new Location(x, y, z), EntityTypes1_8.EntityType.PLAYER));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.SPAWN_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_3_1.NBTLESS_ITEM);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               double x = (double)(Integer)wrapper.get(Types.INT, 1) / (double)32.0F;
               double y = (double)(Integer)wrapper.get(Types.INT, 2) / (double)32.0F;
               double z = (double)(Integer)wrapper.get(Types.INT, 3) / (double)32.0F;
               tracker.getTrackedEntities().put(entityId, new TrackedEntity(entityId, new Location(x, y, z), EntityTypes1_8.ObjectType.ITEM.getType()));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.TAKE_ITEM_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.handler((wrapper) -> ((EntityTracker)wrapper.user().get(EntityTracker.class)).getTrackedEntities().remove(wrapper.get(Types.INT, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               byte typeId = (Byte)wrapper.get(Types.BYTE, 0);
               EntityTypes1_8.EntityType type;
               if (typeId != 70 && typeId != 71 && typeId != 74) {
                  if (typeId != 10 && typeId != 11 && typeId != 12) {
                     type = EntityTypes1_8.getTypeFromId(typeId, true);
                  } else {
                     type = EntityTypes1_8.ObjectType.MINECART.getType();
                  }
               } else {
                  type = EntityTypes1_8.ObjectType.FALLING_BLOCK.getType();
                  wrapper.set(Types.BYTE, 0, (byte)EntityTypes1_8.ObjectType.FALLING_BLOCK.getId());
               }

               double x = (double)(Integer)wrapper.get(Types.INT, 1) / (double)32.0F;
               double y = (double)(Integer)wrapper.get(Types.INT, 2) / (double)32.0F;
               double z = (double)(Integer)wrapper.get(Types.INT, 3) / (double)32.0F;
               Location location = new Location(x, y, z);
               int throwerEntityId = (Integer)wrapper.get(Types.INT, 4);
               short speedX = 0;
               short speedY = 0;
               short speedZ = 0;
               if (throwerEntityId > 0) {
                  speedX = (Short)wrapper.read(Types.SHORT);
                  speedY = (Short)wrapper.read(Types.SHORT);
                  speedZ = (Short)wrapper.read(Types.SHORT);
               }

               if (typeId == 70) {
                  throwerEntityId = 12;
               }

               if (typeId == 71) {
                  throwerEntityId = 13;
               }

               if (typeId == 74) {
                  throwerEntityId = 122;
               }

               if (typeId == EntityTypes1_8.ObjectType.FISHIHNG_HOOK.getId()) {
                  Optional<AbstractTrackedEntity> nearestEntity = entityTracker.getNearestEntity(location, (double)2.0F, (e) -> e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.PLAYER));
                  Optional var10000 = nearestEntity.map(AbstractTrackedEntity::getEntityId);
                  Objects.requireNonNull(entityTracker);
                  throwerEntityId = (Integer)var10000.orElseGet(entityTracker::getPlayerID);
               }

               wrapper.set(Types.INT, 4, throwerEntityId);
               if (throwerEntityId > 0) {
                  wrapper.write(Types.SHORT, speedX);
                  wrapper.write(Types.SHORT, speedY);
                  wrapper.write(Types.SHORT, speedZ);
               }

               entityTracker.getTrackedEntities().put(entityId, new TrackedEntity(entityId, location, type));
               EntityTypes1_8.ObjectType objectType = EntityTypes1_8.ObjectType.findById(typeId);
               if (objectType != null) {
                  switch (objectType) {
                     case TNT_PRIMED:
                        entityTracker.playSoundAt(location, Sound.RANDOM_FUSE, 1.0F, 1.0F);
                        break;
                     case TIPPED_ARROW:
                        float pitch = 1.0F / (entityTracker.RND.nextFloat() * 0.4F + 1.2F) + 0.5F;
                        entityTracker.playSoundAt(location, Sound.RANDOM_BOW, 1.0F, pitch);
                        break;
                     case SNOWBALL:
                     case EGG:
                     case ENDER_PEARL:
                     case EYE_OF_ENDER:
                     case POTION:
                     case EXPERIENCE_BOTTLE:
                     case FISHIHNG_HOOK:
                        float pitch = 0.4F / (entityTracker.RND.nextFloat() * 0.4F + 0.8F);
                        entityTracker.playSoundAt(location, Sound.RANDOM_BOW, 0.5F, pitch);
                  }

               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.create(Types.SHORT, Short.valueOf((short)0));
            this.create(Types.SHORT, Short.valueOf((short)0));
            this.create(Types.SHORT, Short.valueOf((short)0));
            this.map(Types1_3_1.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               short type = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               double x = (double)(Integer)wrapper.get(Types.INT, 1) / (double)32.0F;
               double y = (double)(Integer)wrapper.get(Types.INT, 2) / (double)32.0F;
               double z = (double)(Integer)wrapper.get(Types.INT, 3) / (double)32.0F;
               List<EntityData> entityDataList = (List)wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0);
               EntityTypes1_8.EntityType entityType = EntityTypes1_8.getTypeFromId(type, false);
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               tracker.getTrackedEntities().put(entityId, new TrackedLivingEntity(entityId, new Location(x, y, z), entityType));
               tracker.updateEntityDataList(entityId, entityDataList);
               Protocolr1_2_4_5Tor1_3_1_2.this.handleEntityDataList(entityId, entityDataList, wrapper);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.REMOVE_ENTITIES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types1_7_6.INT_ARRAY, (i) -> new int[]{i});
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);

               for(int entityId : (int[])wrapper.get(Types1_7_6.INT_ARRAY, 0)) {
                  tracker.getTrackedEntities().remove(entityId);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.MOVE_ENTITY_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               byte x = (Byte)wrapper.get(Types.BYTE, 0);
               byte y = (Byte)wrapper.get(Types.BYTE, 1);
               byte z = (Byte)wrapper.get(Types.BYTE, 2);
               tracker.updateEntityLocation(entityId, x, y, z, true);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.MOVE_ENTITY_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               byte x = (Byte)wrapper.get(Types.BYTE, 0);
               byte y = (Byte)wrapper.get(Types.BYTE, 1);
               byte z = (Byte)wrapper.get(Types.BYTE, 2);
               tracker.updateEntityLocation(entityId, x, y, z, true);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.TELEPORT_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               int x = (Integer)wrapper.get(Types.INT, 1);
               int y = (Integer)wrapper.get(Types.INT, 2);
               int z = (Integer)wrapper.get(Types.INT, 3);
               tracker.updateEntityLocation(entityId, x, y, z, false);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.ENTITY_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               byte status = (Byte)wrapper.get(Types.BYTE, 0);
               if (status == 2) {
                  entityTracker.playSound(entityId, SoundType.HURT);
               } else if (status == 3) {
                  entityTracker.playSound(entityId, SoundType.DEATH);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.SET_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_3_1.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               List<EntityData> entityDataList = (List)wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0);
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               if (entityTracker.getTrackedEntities().containsKey(entityId)) {
                  entityTracker.updateEntityDataList(entityId, entityDataList);
                  Protocolr1_2_4_5Tor1_3_1_2.this.handleEntityDataList(entityId, entityDataList, wrapper);
               } else {
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.PRE_CHUNK, ClientboundPackets1_3_1.LEVEL_CHUNK, (wrapper) -> {
         int chunkX = (Integer)wrapper.read(Types.INT);
         int chunkZ = (Integer)wrapper.read(Types.INT);
         boolean load = (Boolean)wrapper.read(Types.BOOLEAN);
         ((ChestStateTracker)wrapper.user().get(ChestStateTracker.class)).unload(chunkX, chunkZ);
         Chunk chunk;
         if (load) {
            chunk = ChunkUtil.createEmptyChunk(chunkX, chunkZ);
         } else {
            chunk = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], (int[])null, new ArrayList());
         }

         wrapper.write(Types1_7_6.getChunk(wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class).getEnvironment()), chunk);
      });
      this.registerClientbound(ClientboundPackets1_2_4.LEVEL_CHUNK, (wrapper) -> {
         Environment dimension = wrapper.user().getClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class).getEnvironment();
         Chunk chunk = (Chunk)wrapper.read(Types1_2_4.CHUNK);
         ((ChestStateTracker)wrapper.user().get(ChestStateTracker.class)).unload(chunk.getX(), chunk.getZ());
         if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               ViaLegacy.getPlatform().getLogger().warning("Received empty 1.2.5 chunk packet");
            }

            chunk = ChunkUtil.createEmptyChunk(chunk.getX(), chunk.getZ());
            if (dimension == Environment.NORMAL) {
               ChunkUtil.setDummySkylight(chunk, true);
            }
         }

         if (dimension != Environment.NORMAL) {
            for(ChunkSection section : chunk.getSections()) {
               if (section != null) {
                  section.getLight().setSkyLight((byte[])null);
               }
            }
         }

         wrapper.write(Types1_7_6.getChunk(dimension), chunk);
      });
      this.registerClientbound(ClientboundPackets1_2_4.BLOCK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE, Types.UNSIGNED_SHORT);
            this.map(Types.UNSIGNED_BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.BLOCK_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               IdAndData block = ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).getBlockNotNull((BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0));
               wrapper.write(Types.SHORT, (short)block.getId());
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0);
               byte type = (Byte)wrapper.get(Types.BYTE, 0);
               short data = (short)(Byte)wrapper.get(Types.BYTE, 1);
               short blockId = (Short)wrapper.get(Types.SHORT, 0);
               if (blockId > 0) {
                  float volume = 1.0F;
                  float pitch = 1.0F;
                  Sound sound = null;
                  if (block.getId() == BlockList1_6.music.blockId()) {
                     Sound var10000;
                     switch (type) {
                        case 1:
                           var10000 = Sound.NOTE_CLICK;
                           break;
                        case 2:
                           var10000 = Sound.NOTE_SNARE;
                           break;
                        case 3:
                           var10000 = Sound.NOTE_HAT;
                           break;
                        case 4:
                           var10000 = Sound.NOTE_BASS_ATTACK;
                           break;
                        default:
                           var10000 = Sound.NOTE_HARP;
                     }

                     sound = var10000;
                     volume = 3.0F;
                     pitch = (float)Math.pow((double)2.0F, (double)(data - 12) / (double)12.0F);
                  } else if (block.getId() == BlockList1_6.chest.blockId()) {
                     if (type == 1) {
                        ChestStateTracker chestStateTracker = (ChestStateTracker)wrapper.user().get(ChestStateTracker.class);
                        if (chestStateTracker.isChestOpen(pos) && data <= 0) {
                           sound = Sound.CHEST_CLOSE;
                           chestStateTracker.closeChest(pos);
                        } else if (!chestStateTracker.isChestOpen(pos) && data > 0) {
                           sound = Sound.CHEST_OPEN;
                           chestStateTracker.openChest(pos);
                        }

                        volume = 0.5F;
                        pitch = entityTracker.RND.nextFloat() * 0.1F + 0.9F;
                     }
                  } else if (block.getId() == BlockList1_6.pistonBase.blockId() || block.getId() == BlockList1_6.pistonStickyBase.blockId()) {
                     if (type == 0) {
                        sound = Sound.PISTON_OUT;
                        volume = 0.5F;
                        pitch = entityTracker.RND.nextFloat() * 0.25F + 0.6F;
                     } else if (type == 1) {
                        sound = Sound.PISTON_IN;
                        volume = 0.5F;
                        pitch = entityTracker.RND.nextFloat() * 0.15F + 0.6F;
                     }
                  }

                  if (sound != null) {
                     entityTracker.playSoundAt(new Location(pos), sound, volume, pitch);
                  }

               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int count = (Integer)wrapper.get(Types.INT, 0);

               for(int i = 0; i < count * 3; ++i) {
                  wrapper.passthrough(Types.BYTE);
               }

            });
            this.create(Types.FLOAT, 0.0F);
            this.create(Types.FLOAT, 0.0F);
            this.create(Types.FLOAT, 0.0F);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               Location loc = new Location((Double)wrapper.get(Types.DOUBLE, 0), (Double)wrapper.get(Types.DOUBLE, 1), (Double)wrapper.get(Types.DOUBLE, 2));
               entityTracker.playSoundAt(loc, Sound.RANDOM_EXPLODE, 4.0F, (1.0F + (entityTracker.RND.nextFloat() - entityTracker.RND.nextFloat()) * 0.2F) * 0.7F);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types1_2_4.NBT_ITEM, Types1_7_6.ITEM);
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types1_2_4.NBT_ITEM_ARRAY, Types1_7_6.ITEM_ARRAY);
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.read(Types.INT);
               wrapper.read(Types.INT);
               wrapper.read(Types.INT);
               if ((Byte)wrapper.get(Types.BYTE, 0) != 1) {
                  wrapper.cancel();
               } else {
                  BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0);
                  CompoundTag tag = new CompoundTag();
                  tag.putString("EntityId", EntityList1_2_4.getEntityName(entityId));
                  tag.putShort("Delay", (short)20);
                  tag.putInt("x", pos.x());
                  tag.putInt("y", pos.y());
                  tag.putInt("z", pos.z());
                  wrapper.write(Types1_7_6.NBT, tag);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_2_4.PLAYER_ABILITIES, (wrapper) -> {
         boolean disableDamage = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean flying = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean allowFlying = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean creativeMode = (Boolean)wrapper.read(Types.BOOLEAN);
         byte mask = 0;
         if (disableDamage) {
            mask = (byte)(mask | 1);
         }

         if (flying) {
            mask = (byte)(mask | 2);
         }

         if (allowFlying) {
            mask = (byte)(mask | 4);
         }

         if (creativeMode) {
            mask = (byte)(mask | 8);
         }

         wrapper.write(Types.BYTE, mask);
         wrapper.write(Types.BYTE, (byte)12);
         wrapper.write(Types.BYTE, (byte)25);
      });
      this.registerServerbound(ServerboundPackets1_3_1.CLIENT_PROTOCOL, ServerboundPackets1_2_4.HANDSHAKE, (wrapper) -> {
         wrapper.read(Types.UNSIGNED_BYTE);
         String userName = (String)wrapper.read(Types1_6_4.STRING);
         String hostname = (String)wrapper.read(Types1_6_4.STRING);
         int port = (Integer)wrapper.read(Types.INT);
         wrapper.write(Types1_6_4.STRING, userName + ";" + hostname + ":" + port);
      });
      this.cancelServerbound(ServerboundPackets1_3_1.SHARED_KEY);
      this.registerServerbound(ServerboundPackets1_3_1.MOVE_PLAYER_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               AbstractTrackedEntity player = (AbstractTrackedEntity)entityTracker.getTrackedEntities().get(entityTracker.getPlayerID());
               if ((Double)wrapper.get(Types.DOUBLE, 1) == (double)-999.0F && (Double)wrapper.get(Types.DOUBLE, 2) == (double)-999.0F) {
                  player.setRiding(true);
               } else {
                  player.setRiding(false);
                  player.getLocation().setX((Double)wrapper.get(Types.DOUBLE, 0));
                  player.getLocation().setY((Double)wrapper.get(Types.DOUBLE, 1));
                  player.getLocation().setZ((Double)wrapper.get(Types.DOUBLE, 3));
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_3_1.MOVE_PLAYER_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               EntityTracker entityTracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
               AbstractTrackedEntity player = (AbstractTrackedEntity)entityTracker.getTrackedEntities().get(entityTracker.getPlayerID());
               if ((Double)wrapper.get(Types.DOUBLE, 1) == (double)-999.0F && (Double)wrapper.get(Types.DOUBLE, 2) == (double)-999.0F) {
                  player.setRiding(true);
               } else {
                  player.setRiding(false);
                  player.getLocation().setX((Double)wrapper.get(Types.DOUBLE, 0));
                  player.getLocation().setY((Double)wrapper.get(Types.DOUBLE, 1));
                  player.getLocation().setZ((Double)wrapper.get(Types.DOUBLE, 3));
               }

            });
         }
      });
      this.registerServerbound(ServerboundPackets1_3_1.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_7_6.ITEM, Types1_2_4.NBT_ITEM);
            this.read(Types.UNSIGNED_BYTE);
            this.read(Types.UNSIGNED_BYTE);
            this.read(Types.UNSIGNED_BYTE);
         }
      });
      this.registerServerbound(ServerboundPackets1_3_1.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types1_7_6.ITEM, Types1_2_4.NBT_ITEM);
         }
      });
      this.registerServerbound(ServerboundPackets1_3_1.PLAYER_ABILITIES, (wrapper) -> {
         byte mask = (Byte)wrapper.read(Types.BYTE);
         wrapper.read(Types.BYTE);
         wrapper.read(Types.BYTE);
         boolean disableDamage = (mask & 1) > 0;
         boolean flying = (mask & 2) > 0;
         boolean allowFlying = (mask & 4) > 0;
         boolean creativeMode = (mask & 8) > 0;
         wrapper.write(Types.BOOLEAN, disableDamage);
         wrapper.write(Types.BOOLEAN, flying);
         wrapper.write(Types.BOOLEAN, allowFlying);
         wrapper.write(Types.BOOLEAN, creativeMode);
      });
      this.registerServerbound(ServerboundPackets1_3_1.CLIENT_COMMAND, ServerboundPackets1_2_4.RESPAWN, (wrapper) -> {
         byte action = (Byte)wrapper.read(Types.BYTE);
         if (action != 1) {
            wrapper.cancel();
         }

         wrapper.write(Types.INT, 0);
         wrapper.write(Types.BYTE, (byte)0);
         wrapper.write(Types.BYTE, (byte)0);
         wrapper.write(Types.SHORT, Short.valueOf((short)0));
         wrapper.write(Types1_6_4.STRING, "");
      });
      this.cancelServerbound(ServerboundPackets1_3_1.COMMAND_SUGGESTION);
      this.cancelServerbound(ServerboundPackets1_3_1.CLIENT_INFORMATION);
   }

   void handleEntityDataList(int entityId, List entityDataList, PacketWrapper wrapper) {
      EntityTracker tracker = (EntityTracker)wrapper.user().get(EntityTracker.class);
      if (entityId != tracker.getPlayerID()) {
         AbstractTrackedEntity entity = (AbstractTrackedEntity)tracker.getTrackedEntities().get(entityId);

         for(EntityData entityData : entityDataList) {
            if (EntityDataIndex1_5_2.searchIndex(entity.getEntityType(), entityData.id()) == null) {
               EntityDataIndex1_7_6 index = EntityDataIndex1_7_6.searchIndex(entity.getEntityType(), entityData.id());
               if (index == EntityDataIndex1_7_6.ENTITY_FLAGS) {
                  if (((Byte)entityData.value() & 4) != 0) {
                     Optional<AbstractTrackedEntity> oNearbyEntity = tracker.getNearestEntity(entity.getLocation(), (double)1.0F, (e) -> e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.MINECART) || e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.PIG) || e.getEntityType().isOrHasParent(EntityTypes1_8.EntityType.BOAT));
                     if (oNearbyEntity.isPresent()) {
                        entity.setRiding(true);
                        AbstractTrackedEntity nearbyEntity = (AbstractTrackedEntity)oNearbyEntity.get();
                        PacketWrapper attachEntity = PacketWrapper.create(ClientboundPackets1_3_1.SET_ENTITY_LINK, (UserConnection)wrapper.user());
                        attachEntity.write(Types.INT, entityId);
                        attachEntity.write(Types.INT, nearbyEntity.getEntityId());
                        wrapper.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                        attachEntity.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                        wrapper.cancel();
                     }
                  } else if (((Byte)entityData.value() & 4) == 0 && entity.isRiding()) {
                     entity.setRiding(false);
                     PacketWrapper detachEntity = PacketWrapper.create(ClientboundPackets1_3_1.SET_ENTITY_LINK, (UserConnection)wrapper.user());
                     detachEntity.write(Types.INT, entityId);
                     detachEntity.write(Types.INT, -1);
                     detachEntity.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                     wrapper.send(Protocolr1_2_4_5Tor1_3_1_2.class);
                     wrapper.cancel();
                  }
                  break;
               }

               if (index == EntityDataIndex1_7_6.CREEPER_STATE && (Byte)entityData.value() > 0) {
                  tracker.playSoundAt(entity.getLocation(), Sound.RANDOM_FUSE, 1.0F, 0.5F);
               }
            }
         }

      }
   }

   public void register(ViaProviders providers) {
      providers.register(OldAuthProvider.class, new OldAuthProvider());
      if (ViaLegacy.getConfig().isSoundEmulation()) {
         Via.getPlatform().runRepeatingSync(new EntityTrackerTickTask(), 1L);
      }

   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolr1_2_4_5Tor1_3_1_2.class, ClientboundPackets1_2_4::getPacket));
      userConnection.addClientWorld(Protocolr1_2_4_5Tor1_3_1_2.class, new ClientWorld());
      userConnection.put(new ChestStateTracker());
      userConnection.put(new EntityTracker(userConnection));
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
