package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EffectIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.PotionIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.SoundEffectMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CommandBlockProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.HandItemProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.ClientWorld1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Optional;

public class WorldPacketRewriter1_9 {
   public static void register(Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  String line = (String)wrapper.read(Types.STRING);
                  Protocol1_8To1_9.STRING_TO_JSON.write(wrapper, line);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.LEVEL_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.INT, 0);
               id = EffectIdMappings1_9.getNewId(id);
               wrapper.set(Types.INT, 0, id);
            });
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.INT, 0);
               if (id == 2002) {
                  int data = (Integer)wrapper.get(Types.INT, 1);
                  int newData = PotionIdMappings1_9.getNewPotionID(data);
                  wrapper.set(Types.INT, 1, newData);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CUSTOM_SOUND, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               String name = Key.stripMinecraftNamespace((String)wrapper.get(Types.STRING, 0));
               SoundEffectMappings1_9 effect = SoundEffectMappings1_9.getByName(name);
               int catid = 0;
               String newname = name;
               if (effect != null) {
                  catid = effect.getCategory().getId();
                  newname = effect.getNewName();
               }

               wrapper.set(Types.STRING, 0, newname);
               wrapper.write(Types.VAR_INT, catid);
               if (Via.getConfig().cancelBlockSounds()) {
                  if (effect != null && effect.isBreakSound()) {
                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                     int x = (Integer)wrapper.passthrough(Types.INT);
                     int y = (Integer)wrapper.passthrough(Types.INT);
                     int z = (Integer)wrapper.passthrough(Types.INT);
                     if (tracker.interactedBlockRecently((int)Math.floor((double)x / (double)8.0F), (int)Math.floor((double)y / (double)8.0F), (int)Math.floor((double)z / (double)8.0F))) {
                        wrapper.cancel();
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.LEVEL_CHUNK, (wrapper) -> {
         ClientWorld1_9 clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
         Chunk chunk = (Chunk)wrapper.read(ChunkType1_8.forEnvironment(clientWorld.getEnvironment()));
         long chunkHash = ClientWorld1_9.toLong(chunk.getX(), chunk.getZ());
         if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
            wrapper.setPacketType(ClientboundPackets1_9.FORGET_LEVEL_CHUNK);
            wrapper.write(Types.INT, chunk.getX());
            wrapper.write(Types.INT, chunk.getZ());
            CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
            provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
            clientWorld.getLoadedChunks().remove(chunkHash);
            if (Via.getConfig().isChunkBorderFix()) {
               for(BlockFace face : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + face.modX();
                  int chunkZ = chunk.getZ() + face.modZ();
                  if (!clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) {
                     PacketWrapper unloadChunk = wrapper.create(ClientboundPackets1_9.FORGET_LEVEL_CHUNK);
                     unloadChunk.write(Types.INT, chunkX);
                     unloadChunk.write(Types.INT, chunkZ);
                     unloadChunk.send(Protocol1_8To1_9.class);
                  }
               }
            }
         } else {
            Type<Chunk> chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());
            wrapper.write(chunkType, chunk);
            clientWorld.getLoadedChunks().add(chunkHash);
            if (Via.getConfig().isChunkBorderFix()) {
               for(BlockFace face : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + face.modX();
                  int chunkZ = chunk.getZ() + face.modZ();
                  if (!clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) {
                     PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
                     Chunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList());
                     emptyChunk.write(chunkType, c);
                     emptyChunk.send(Protocol1_8To1_9.class);
                  }
               }
            }
         }

      });
      protocol.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ClientWorld1_9 clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
         Chunk[] chunks = (Chunk[])wrapper.read(BulkChunkType1_8.TYPE);
         Type<Chunk> chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());

         for(Chunk chunk : chunks) {
            PacketWrapper chunkData = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
            chunkData.write(chunkType, chunk);
            chunkData.send(Protocol1_8To1_9.class);
            clientWorld.getLoadedChunks().add(ClientWorld1_9.toLong(chunk.getX(), chunk.getZ()));
            if (Via.getConfig().isChunkBorderFix()) {
               for(BlockFace face : BlockFace.HORIZONTAL) {
                  int chunkX = chunk.getX() + face.modX();
                  int chunkZ = chunk.getZ() + face.modZ();
                  if (!clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) {
                     PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
                     Chunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList());
                     emptyChunk.write(chunkType, c);
                     emptyChunk.send(Protocol1_8To1_9.class);
                  }
               }
            }
         }

      });
      protocol.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.handler((wrapper) -> {
               int action = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (action == 1) {
                  CompoundTag tag = (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                  if (tag != null) {
                     StringTag entityId = tag.getStringTag("EntityId");
                     if (entityId != null) {
                        String entity = entityId.getValue();
                        CompoundTag spawn = new CompoundTag();
                        spawn.putString("id", entity);
                        tag.put("SpawnData", spawn);
                     } else {
                        CompoundTag spawn = new CompoundTag();
                        spawn.putString("id", "AreaEffectCloud");
                        tag.put("SpawnData", spawn);
                     }
                  }
               }

               if (action == 2) {
                  CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
                  provider.addOrUpdateBlock(wrapper.user(), (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0), (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0));
                  wrapper.cancel();
               }

            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  String line = (String)wrapper.read(Types.STRING);
                  wrapper.write(Types.COMPONENT, ComponentUtil.plainToJson(line));
               }

            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.PLAYER_ACTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               int status = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (status == 6) {
                  wrapper.cancel();
               }

            });
            this.handler((wrapper) -> {
               int status = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (status == 5 || status == 4 || status == 3) {
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  if (entityTracker.isBlocking()) {
                     entityTracker.setBlocking(false);
                     if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                        entityTracker.setSecondHand((Item)null);
                     }
                  }
               }

            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, (ServerboundPacketType)null, (wrapper) -> {
         int hand = (Integer)wrapper.read(Types.VAR_INT);
         wrapper.clearInputBuffer();
         wrapper.setPacketType(ServerboundPackets1_8.USE_ITEM_ON);
         wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(-1, -1, -1));
         wrapper.write(Types.UNSIGNED_BYTE, (short)255);
         Item item = ((HandItemProvider)Via.getManager().getProviders().get(HandItemProvider.class)).getHandItem(wrapper.user());
         if (Via.getConfig().isShieldBlocking()) {
            EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
            boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
            boolean isSword = showShieldWhenSwordInHand ? tracker.hasSwordInHand() : item != null && Protocol1_8To1_9.isSword(item.identifier());
            if (isSword) {
               if (hand == 0 && !tracker.isBlocking()) {
                  tracker.setBlocking(true);
                  if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
                     Item shield = new DataItem(442, (byte)1, (short)0, (CompoundTag)null);
                     tracker.setSecondHand(shield);
                  }
               }

               boolean blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
               if (blockUsingMainHand && hand == 1 || !blockUsingMainHand && hand == 0) {
                  wrapper.cancel();
               }
            } else {
               if (!showShieldWhenSwordInHand) {
                  tracker.setSecondHand((Item)null);
               }

               tracker.setBlocking(false);
            }
         }

         wrapper.write(Types.ITEM1_8, item);
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
      });
      protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.VAR_INT, Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               int hand = (Integer)wrapper.read(Types.VAR_INT);
               if (hand != 0) {
                  wrapper.cancel();
               }

            });
            this.handler((wrapper) -> {
               Item item = ((HandItemProvider)Via.getManager().getProviders().get(HandItemProvider.class)).getHandItem(wrapper.user());
               wrapper.write(Types.ITEM1_8, item);
            });
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
               BlockPosition pos = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
               Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
               if (tag.isPresent()) {
                  PacketWrapper updateBlockEntity = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, (ByteBuf)null, wrapper.user());
                  updateBlockEntity.write(Types.BLOCK_POSITION1_8, pos);
                  updateBlockEntity.write(Types.UNSIGNED_BYTE, Short.valueOf((short)2));
                  updateBlockEntity.write(Types.NAMED_COMPOUND_TAG, (CompoundTag)tag.get());
                  updateBlockEntity.scheduleSend(Protocol1_8To1_9.class);
               }

            });
            if (Via.getConfig().cancelBlockSounds()) {
               this.handler((wrapper) -> {
                  int face = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
                  if (face != 255) {
                     BlockPosition p = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
                     int x = p.x();
                     int y = p.y();
                     int z = p.z();
                     switch (face) {
                        case 0:
                           --y;
                           break;
                        case 1:
                           ++y;
                           break;
                        case 2:
                           --z;
                           break;
                        case 3:
                           ++z;
                           break;
                        case 4:
                           --x;
                           break;
                        case 5:
                           ++x;
                     }

                     EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                     tracker.addBlockInteraction(new BlockPosition(x, y, z));
                  }
               });
            }
         }
      });
   }
}
