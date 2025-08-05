package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundStatusPackets;
import com.viaversion.viaversion.protocols.base.v1_7.ClientboundBaseProtocol1_7;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.IdAndData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import java.util.List;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.protocol.StatelessTransitionProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.PacketUtil;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ClientboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.provider.EncryptionProvider;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.SoundRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.StatisticRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.rewriter.TextRewriter;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.HandshakeStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.PlayerInfoStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ProtocolMetadataStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.StatisticsStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ClientboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ServerboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider.GameProfileFetcher;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.EntityDataTypes1_7_6;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_6_4Tor1_7_2_5 extends StatelessTransitionProtocol {
   final ItemRewriter itemRewriter = new ItemRewriter(this);

   public Protocolr1_6_4Tor1_7_2_5() {
      super(ClientboundPackets1_6_4.class, ClientboundPackets1_7_2.class, ServerboundPackets1_6_4.class, ServerboundPackets1_7_2.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.registerClientboundTransition(ClientboundPackets1_6_4.LOGIN, new Object[]{ClientboundPackets1_7_2.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ((PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class)).entityId = (Integer)wrapper.get(Types.INT, 0);
               String terrainType = (String)wrapper.read(Types1_6_4.STRING);
               short gameType = (short)(Byte)wrapper.read(Types.BYTE);
               byte dimension = (Byte)wrapper.read(Types.BYTE);
               short difficulty = (short)(Byte)wrapper.read(Types.BYTE);
               wrapper.read(Types.BYTE);
               short maxPlayers = (short)(Byte)wrapper.read(Types.BYTE);
               wrapper.write(Types.UNSIGNED_BYTE, gameType);
               wrapper.write(Types.BYTE, dimension);
               wrapper.write(Types.UNSIGNED_BYTE, difficulty);
               wrapper.write(Types.UNSIGNED_BYTE, maxPlayers);
               wrapper.write(Types.STRING, terrainType);
            });
            this.handler((wrapper) -> {
               byte dimensionId = (Byte)wrapper.get(Types.BYTE, 0);
               wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class).setEnvironment(dimensionId);
               wrapper.user().put(new ChunkTracker(wrapper.user()));
            });
         }
      }, State.LOGIN, (PacketHandler)(wrapper) -> {
         ViaLegacy.getPlatform().getLogger().warning("Server skipped LOGIN state");
         PacketWrapper sharedKey = PacketWrapper.create(ClientboundPackets1_6_4.SHARED_KEY, (UserConnection)wrapper.user());
         sharedKey.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
         sharedKey.write(Types.SHORT_BYTE_ARRAY, new byte[0]);
         ((ProtocolMetadataStorage)wrapper.user().get(ProtocolMetadataStorage.class)).skipEncryption = true;
         sharedKey.send(Protocolr1_6_4Tor1_7_2_5.class, false);
         ((ProtocolMetadataStorage)wrapper.user().get(ProtocolMetadataStorage.class)).skipEncryption = false;
         wrapper.setPacketType(ClientboundPackets1_6_4.LOGIN);
         wrapper.send(Protocolr1_6_4Tor1_7_2_5.class, false);
         wrapper.cancel();
      }});
      this.registerClientbound(ClientboundPackets1_6_4.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClient);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.SHORT);
            this.map(Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.read(Types.SHORT);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.handler((wrapper) -> {
               if (wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class).setEnvironment((Integer)wrapper.get(Types.INT, 0))) {
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).clear();
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_STATUS_ONLY, ClientboundPackets1_7_2.PLAYER_POSITION, (wrapper) -> {
         PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
         boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posX);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posY + (double)1.62F);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posZ);
         wrapper.write(Types.FLOAT, supportsFlags ? 0.0F : playerInfoStorage.yaw);
         wrapper.write(Types.FLOAT, supportsFlags ? 0.0F : playerInfoStorage.pitch);
         if (supportsFlags) {
            wrapper.read(Types.BOOLEAN);
            wrapper.write(Types.BYTE, (byte)31);
            wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
            wrapper.send(Protocolr1_7_6_10Tor1_8.class);
            wrapper.cancel();
         } else {
            wrapper.passthrough(Types.BOOLEAN);
         }

         PacketWrapper setVelocityToZero = PacketWrapper.create(ClientboundPackets1_7_2.SET_ENTITY_MOTION, (UserConnection)wrapper.user());
         setVelocityToZero.write(Types.INT, playerInfoStorage.entityId);
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         if (!wrapper.isCancelled()) {
            wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
         }

         setVelocityToZero.send(Protocolr1_6_4Tor1_7_2_5.class);
         wrapper.cancel();
      });
      this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_POS, ClientboundPackets1_7_2.PLAYER_POSITION, (wrapper) -> {
         PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
         boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.read(Types.DOUBLE);
         wrapper.passthrough(Types.DOUBLE);
         wrapper.write(Types.FLOAT, supportsFlags ? 0.0F : playerInfoStorage.yaw);
         wrapper.write(Types.FLOAT, supportsFlags ? 0.0F : playerInfoStorage.pitch);
         if (supportsFlags) {
            wrapper.read(Types.BOOLEAN);
            wrapper.write(Types.BYTE, (byte)24);
            wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
            wrapper.send(Protocolr1_7_6_10Tor1_8.class);
            wrapper.cancel();
         } else {
            wrapper.passthrough(Types.BOOLEAN);
         }

      });
      this.registerClientbound(ClientboundPackets1_6_4.MOVE_PLAYER_ROT, ClientboundPackets1_7_2.PLAYER_POSITION, (wrapper) -> {
         PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
         boolean supportsFlags = wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_8);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posX);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posY + (double)1.62F);
         wrapper.write(Types.DOUBLE, supportsFlags ? (double)0.0F : playerInfoStorage.posZ);
         wrapper.passthrough(Types.FLOAT);
         wrapper.passthrough(Types.FLOAT);
         if (supportsFlags) {
            wrapper.read(Types.BOOLEAN);
            wrapper.write(Types.BYTE, (byte)7);
            wrapper.setPacketType(ClientboundPackets1_8.PLAYER_POSITION);
            wrapper.send(Protocolr1_7_6_10Tor1_8.class);
            wrapper.cancel();
         } else {
            wrapper.passthrough(Types.BOOLEAN);
         }

         PacketWrapper setVelocityToZero = PacketWrapper.create(ClientboundPackets1_7_2.SET_ENTITY_MOTION, (UserConnection)wrapper.user());
         setVelocityToZero.write(Types.INT, playerInfoStorage.entityId);
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         setVelocityToZero.write(Types.SHORT, Short.valueOf((short)0));
         if (!wrapper.isCancelled()) {
            wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
         }

         setVelocityToZero.send(Protocolr1_6_4Tor1_7_2_5.class);
         wrapper.cancel();
      });
      this.registerClientbound(ClientboundPackets1_6_4.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.read(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_CARRIED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT, Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.PLAYER_SLEEP, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               if ((Byte)wrapper.read(Types.BYTE) != 0) {
                  wrapper.cancel();
               }

            });
            this.map(Types1_7_6.BLOCK_POSITION_BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ANIMATE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> {
               short animate = (short)(Byte)wrapper.read(Types.BYTE);
               if (animate == 0 || animate == 4) {
                  wrapper.cancel();
               }

               if (animate >= 1 && animate <= 3) {
                  --animate;
               } else {
                  animate = (short)(animate - 2);
               }

               wrapper.write(Types.UNSIGNED_BYTE, animate);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> {
               String name = (String)wrapper.read(Types1_6_4.STRING);
               wrapper.write(Types.STRING, (ViaLegacy.getConfig().isLegacySkinLoading() ? ((GameProfileFetcher)Via.getManager().getProviders().get(GameProfileFetcher.class)).getMojangUUID(name) : (new GameProfile(name)).uuid).toString().replace("-", ""));
               wrapper.write(Types.STRING, name);
            });
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               Item currentItem = new DataItem((Integer)wrapper.read(Types.UNSIGNED_SHORT), (byte)1, (short)0, (CompoundTag)null);
               Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), currentItem);
               wrapper.write(Types.SHORT, (short)currentItem.identifier());
            });
            this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), (List)wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int data = (Integer)wrapper.get(Types.INT, 3);
               if (EntityTypes1_8.getTypeFromId((Byte)wrapper.get(Types.BYTE, 0), true) == EntityTypes1_8.ObjectType.FALLING_BLOCK.getType()) {
                  int id = data & '\uffff';
                  int metadata = data >> 16;
                  IdAndData block = new IdAndData(id, metadata);
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).remapBlockParticle(block);
                  data = block.getId() & '\uffff' | block.getData() << 16;
               }

               wrapper.set(Types.INT, 3, data);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
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
            this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), (List)wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_PAINTING, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types1_7_6.BLOCK_POSITION_INT);
            this.map(Types.INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_EXPERIENCE_ORB, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.SHORT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_6_4.ENTITY_DATA_LIST, Types1_7_6.ENTITY_DATA_LIST);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.rewriteEntityData(wrapper.user(), (List)wrapper.get(Types1_7_6.ENTITY_DATA_LIST, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.UPDATE_ATTRIBUTES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int amount = (Integer)wrapper.passthrough(Types.INT);

               for(int i = 0; i < amount; ++i) {
                  wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
                  wrapper.passthrough(Types.DOUBLE);
                  int modifierCount = (Short)wrapper.passthrough(Types.SHORT);

                  for(int x = 0; x < modifierCount; ++x) {
                     wrapper.passthrough(Types.UUID);
                     wrapper.passthrough(Types.DOUBLE);
                     wrapper.passthrough(Types.BYTE);
                  }
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.LEVEL_CHUNK, (wrapper) -> {
         Chunk chunk = (Chunk)wrapper.passthrough(Types1_7_6.getChunk(wrapper.user().getClientWorld(Protocolr1_6_4Tor1_7_2_5.class).getEnvironment()));
         ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(chunk);
      });
      this.registerClientbound(ClientboundPackets1_6_4.CHUNK_BLOCKS_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler((wrapper) -> {
               int chunkX = (Integer)wrapper.get(Types.INT, 0);
               int chunkZ = (Integer)wrapper.get(Types.INT, 1);
               BlockChangeRecord[] blockChangeRecords = (BlockChangeRecord[])wrapper.get(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY, 0);

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
      this.registerClientbound(ClientboundPackets1_6_4.BLOCK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_SHORT, Types.VAR_INT);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
               int blockId = (Integer)wrapper.get(Types.VAR_INT, 0);
               int data = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               IdAndData block = new IdAndData(blockId, data);
               ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(pos, block);
               wrapper.set(Types.VAR_INT, 0, block.getId());
               wrapper.set(Types.UNSIGNED_BYTE, 0, (short)block.getData());
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.BLOCK_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.SHORT, Types.VAR_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.BLOCK_DESTRUCTION, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types1_7_6.BLOCK_POSITION_INT);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.MAP_BULK_CHUNK, (wrapper) -> {
         Chunk[] chunks = (Chunk[])wrapper.passthrough(Types1_7_6.CHUNK_BULK);

         for(Chunk chunk : chunks) {
            ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).trackAndRemap(chunk);
         }

      });
      this.registerClientbound(ClientboundPackets1_6_4.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE, Types.FLOAT);
            this.map(Types.DOUBLE, Types.FLOAT);
            this.map(Types.DOUBLE, Types.FLOAT);
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
      this.registerClientbound(ClientboundPackets1_6_4.CUSTOM_SOUND, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               String oldSound = (String)wrapper.read(Types1_6_4.STRING);
               String newSound = SoundRewriter.map(oldSound);
               if (oldSound.isEmpty()) {
                  newSound = "";
               }

               if (newSound == null) {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ViaLegacy.getPlatform().getLogger().warning("Unable to map 1.6.4 sound '" + oldSound + "'");
                  }

                  newSound = "";
               }

               if (newSound.isEmpty()) {
                  wrapper.cancel();
               } else {
                  wrapper.write(Types.STRING, newSound);
               }
            });
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.FLOAT);
            this.map(Types.UNSIGNED_BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.LEVEL_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int effectId = (Integer)wrapper.get(Types.INT, 0);
               int data = (Integer)wrapper.get(Types.INT, 1);
               boolean disableRelativeVolume = (Boolean)wrapper.get(Types.BOOLEAN, 0);
               if (!disableRelativeVolume && effectId == 2001) {
                  ChunkTracker chunkTracker = (ChunkTracker)wrapper.user().get(ChunkTracker.class);
                  int blockID = data & 4095;
                  int blockData = data >> 12 & 255;
                  IdAndData block = new IdAndData(blockID, blockData);
                  chunkTracker.remapBlockParticle(block);
                  data = block.getId() & 4095 | block.getData() << 12;
                  wrapper.set(Types.INT, 1, data);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.LEVEL_PARTICLES, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               String[] parts = ((String)wrapper.get(Types.STRING, 0)).split("_", 3);
               if (parts[0].equals("tilecrack")) {
                  parts[0] = "blockcrack";
               }

               if (parts[0].equals("blockcrack") || parts[0].equals("blockdust")) {
                  int id = Integer.parseInt(parts[1]);
                  int metadata = Integer.parseInt(parts[2]);
                  IdAndData block = new IdAndData(id, metadata);
                  ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).remapBlockParticle(block);
                  parts[1] = String.valueOf(block.getId());
                  parts[2] = String.valueOf(block.getData());
               }

               wrapper.set(Types.STRING, 0, String.join("_", parts));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.GAME_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.BYTE, Types.FLOAT);
            this.handler((wrapper) -> {
               short gameState = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (gameState == 1) {
                  PacketWrapper startRain = PacketWrapper.create(ClientboundPackets1_7_2.GAME_EVENT, (UserConnection)wrapper.user());
                  startRain.write(Types.UNSIGNED_BYTE, Short.valueOf((short)7));
                  startRain.write(Types.FLOAT, 1.0F);
                  wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
                  startRain.send(Protocolr1_6_4Tor1_7_2_5.class);
                  wrapper.cancel();
               } else if (gameState == 2) {
                  PacketWrapper stopRain = PacketWrapper.create(ClientboundPackets1_7_2.GAME_EVENT, (UserConnection)wrapper.user());
                  stopRain.write(Types.UNSIGNED_BYTE, Short.valueOf((short)7));
                  stopRain.write(Types.FLOAT, 0.0F);
                  wrapper.send(Protocolr1_6_4Tor1_7_2_5.class);
                  stopRain.send(Protocolr1_6_4Tor1_7_2_5.class);
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.ADD_GLOBAL_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.OPEN_SCREEN, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.BOOLEAN);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_CLOSE, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               Item[] items = (Item[])wrapper.passthrough(Types1_7_6.ITEM_ARRAY);

               for(Item item : items) {
                  Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), item);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.UPDATE_SIGN, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types1_6_4.STRING, Types.STRING);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.MAP_ITEM_DATA, new PacketHandlers() {
         public void register() {
            this.read(Types.SHORT);
            this.map(Types.SHORT, Types.VAR_INT);
            this.map(Types.SHORT_BYTE_ARRAY);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types1_7_6.NBT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.OPEN_SIGN_EDITOR, new PacketHandlers() {
         public void register() {
            this.read(Types.BYTE);
            this.map(Types1_7_6.BLOCK_POSITION_INT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.AWARD_STATS, (wrapper) -> {
         wrapper.cancel();
         StatisticsStorage statisticsStorage = (StatisticsStorage)wrapper.user().get(StatisticsStorage.class);
         int statId = (Integer)wrapper.read(Types.INT);
         int increment = (Integer)wrapper.read(Types.INT);
         statisticsStorage.values.put(statId, statisticsStorage.values.get(statId) + increment);
      });
      this.registerClientbound(ClientboundPackets1_6_4.PLAYER_INFO, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types.BOOLEAN);
            this.map(Types.SHORT);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.COMMAND_SUGGESTIONS, (wrapper) -> {
         String completions = (String)wrapper.read(Types1_6_4.STRING);
         String[] completionsArray = completions.split("\u0000");
         wrapper.write(Types.VAR_INT, completionsArray.length);

         for(String s : completionsArray) {
            wrapper.write(Types.STRING, s);
         }

      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_OBJECTIVE, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_SCORE, (wrapper) -> {
         wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
         byte mode = (Byte)wrapper.passthrough(Types.BYTE);
         if (mode == 0) {
            wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
            wrapper.passthrough(Types.INT);
         }

      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_DISPLAY_OBJECTIVE, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types1_6_4.STRING, Types.STRING);
         }
      });
      this.registerClientbound(ClientboundPackets1_6_4.SET_PLAYER_TEAM, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING);
            this.handler((wrapper) -> {
               byte mode = (Byte)wrapper.passthrough(Types.BYTE);
               if (mode == 0 || mode == 2) {
                  wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
                  wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
                  wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
                  wrapper.passthrough(Types.BYTE);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  int count = (Short)wrapper.passthrough(Types.SHORT);

                  for(int i = 0; i < count; ++i) {
                     wrapper.write(Types.STRING, (String)wrapper.read(Types1_6_4.STRING));
                  }
               }

            });
         }
      });
      this.registerClientboundTransition(ClientboundPackets1_6_4.CUSTOM_PAYLOAD, new Object[]{ClientboundPackets1_7_2.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               String channel = (String)wrapper.read(Types1_6_4.STRING);
               int length = (Short)wrapper.read(Types.SHORT);
               if (length < 0) {
                  wrapper.write(Types.STRING, channel);
                  wrapper.write(Types.UNSIGNED_SHORT, 0);
               } else {
                  try {
                     if (channel.equals("MC|TrList")) {
                        wrapper.passthrough(Types.INT);
                        int count = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

                        for(int i = 0; i < count; ++i) {
                           Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types1_7_6.ITEM));
                           Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types1_7_6.ITEM));
                           if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
                              Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types1_7_6.ITEM));
                           }

                           wrapper.passthrough(Types.BOOLEAN);
                        }

                        length = PacketUtil.calculateLength(wrapper);
                     }
                  } catch (Exception e) {
                     if (!Via.getConfig().isSuppressConversionWarnings()) {
                        Via.getPlatform().getLogger().log(Level.WARNING, "Failed to handle packet", e);
                     }

                     wrapper.cancel();
                     return;
                  }

                  wrapper.resetReader();
                  wrapper.write(Types.STRING, channel);
                  wrapper.write(Types.UNSIGNED_SHORT, length);
               }
            });
         }
      }, State.LOGIN, PacketWrapper::cancel});
      this.registerClientboundTransition(ClientboundPackets1_6_4.SHARED_KEY, new Object[]{ClientboundLoginPackets.GAME_PROFILE, (PacketHandler)(wrapper) -> {
         ProtocolInfo info = wrapper.user().getProtocolInfo();
         ProtocolMetadataStorage protocolMetadata = (ProtocolMetadataStorage)wrapper.user().get(ProtocolMetadataStorage.class);
         wrapper.read(Types.SHORT_BYTE_ARRAY);
         wrapper.read(Types.SHORT_BYTE_ARRAY);
         wrapper.write(Types.STRING, info.getUuid().toString().replace("-", ""));
         wrapper.write(Types.STRING, info.getUsername());
         if (!protocolMetadata.skipEncryption) {
            ((EncryptionProvider)Via.getManager().getProviders().get(EncryptionProvider.class)).enableDecryption(wrapper.user());
         }

         ClientboundBaseProtocol1_7.onLoginSuccess(wrapper.user());
         PacketWrapper respawn = PacketWrapper.create(ServerboundPackets1_6_4.CLIENT_COMMAND, (UserConnection)wrapper.user());
         respawn.write(Types.BYTE, (byte)0);
         respawn.sendToServer(Protocolr1_6_4Tor1_7_2_5.class);
      }});
      this.registerClientboundTransition(ClientboundPackets1_6_4.SERVER_AUTH_DATA, new Object[]{ClientboundLoginPackets.HELLO, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING);
            this.map(Types.SHORT_BYTE_ARRAY);
            this.map(Types.SHORT_BYTE_ARRAY);
            this.handler((wrapper) -> {
               ProtocolMetadataStorage protocolMetadata = (ProtocolMetadataStorage)wrapper.user().get(ProtocolMetadataStorage.class);
               String serverHash = (String)wrapper.get(Types.STRING, 0);
               protocolMetadata.authenticate = !serverHash.equals("-");
            });
         }
      }});
      this.registerClientboundTransition(ClientboundPackets1_6_4.DISCONNECT, new Object[]{ClientboundStatusPackets.STATUS_RESPONSE, (PacketHandler)(wrapper) -> {
         String reason = (String)wrapper.read(Types1_6_4.STRING);

         try {
            String[] motdParts = reason.split("\u0000");
            JsonObject rootObject = new JsonObject();
            JsonObject descriptionObject = new JsonObject();
            JsonObject playersObject = new JsonObject();
            JsonObject versionObject = new JsonObject();
            descriptionObject.addProperty("text", motdParts[3]);
            playersObject.addProperty("max", (Number)Integer.parseInt(motdParts[5]));
            playersObject.addProperty("online", (Number)Integer.parseInt(motdParts[4]));
            versionObject.addProperty("name", motdParts[2]);
            versionObject.addProperty("protocol", (Number)Integer.parseInt(motdParts[1]));
            rootObject.add("description", descriptionObject);
            rootObject.add("players", playersObject);
            rootObject.add("version", versionObject);
            wrapper.write(Types.STRING, rootObject.toString());
         } catch (Throwable e) {
            ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Could not parse 1.6.4 ping: " + reason, e);
            wrapper.cancel();
         }

      }, ClientboundLoginPackets.LOGIN_DISCONNECT, new PacketHandlers() {
         protected void register() {
            this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClientDisconnect);
         }
      }, ClientboundPackets1_7_2.DISCONNECT, new PacketHandlers() {
         public void register() {
            this.map(Types1_6_4.STRING, Types.STRING, TextRewriter::toClientDisconnect);
         }
      }});
      this.cancelClientbound(ClientboundPackets1_6_4.SET_CREATIVE_MODE_SLOT);
      this.registerServerboundTransition(ServerboundHandshakePackets.CLIENT_INTENTION, (ServerboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         wrapper.read(Types.VAR_INT);
         String hostname = (String)wrapper.read(Types.STRING);
         int port = (Integer)wrapper.read(Types.UNSIGNED_SHORT);
         wrapper.user().put(new HandshakeStorage(hostname, port));
      });
      this.registerServerboundTransition(ServerboundStatusPackets.STATUS_REQUEST, ServerboundPackets1_6_4.SERVER_PING, (wrapper) -> {
         HandshakeStorage handshakeStorage = (HandshakeStorage)wrapper.user().get(HandshakeStorage.class);
         String ip = handshakeStorage.getHostname();
         int port = handshakeStorage.getPort();
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)1));
         wrapper.write(Types.UNSIGNED_BYTE, (short)ServerboundPackets1_6_4.CUSTOM_PAYLOAD.getId());
         wrapper.write(Types1_6_4.STRING, "MC|PingHost");
         wrapper.write(Types.SHORT, (short)(3 + 2 * ip.length() + 4));
         wrapper.write(Types.UNSIGNED_BYTE, (short)wrapper.user().getProtocolInfo().serverProtocolVersion().getVersion());
         wrapper.write(Types1_6_4.STRING, ip);
         wrapper.write(Types.INT, port);
      });
      this.registerServerboundTransition(ServerboundStatusPackets.PING_REQUEST, (ServerboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         PacketWrapper pong = PacketWrapper.create(ClientboundStatusPackets.PONG_RESPONSE, (UserConnection)wrapper.user());
         pong.write(Types.LONG, (Long)wrapper.read(Types.LONG));
         pong.send(Protocolr1_6_4Tor1_7_2_5.class);
      });
      this.registerServerboundTransition(ServerboundLoginPackets.HELLO, ServerboundPackets1_6_4.CLIENT_PROTOCOL, (wrapper) -> {
         HandshakeStorage handshakeStorage = (HandshakeStorage)wrapper.user().get(HandshakeStorage.class);
         String name = (String)wrapper.read(Types.STRING);
         wrapper.write(Types.UNSIGNED_BYTE, (short)wrapper.user().getProtocolInfo().serverProtocolVersion().getVersion());
         wrapper.write(Types1_6_4.STRING, name);
         wrapper.write(Types1_6_4.STRING, handshakeStorage.getHostname());
         wrapper.write(Types.INT, handshakeStorage.getPort());
         ProtocolInfo info = wrapper.user().getProtocolInfo();
         if (info.getUsername() == null) {
            info.setUsername(name);
         }

         if (info.getUuid() == null) {
            info.setUuid(ViaLegacy.getConfig().isLegacySkinLoading() ? ((GameProfileFetcher)Via.getManager().getProviders().get(GameProfileFetcher.class)).getMojangUUID(name) : (new GameProfile(name)).uuid);
         }

      });
      this.registerServerboundTransition(ServerboundLoginPackets.ENCRYPTION_KEY, ServerboundPackets1_6_4.SHARED_KEY, (PacketHandler)null);
      this.registerServerbound(ServerboundPackets1_7_2.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Types1_6_4.STRING);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.INTERACT, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> wrapper.write(Types.INT, ((PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class)).entityId));
            this.map(Types.INT);
            this.map(Types.BYTE);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers() {
         public void register() {
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> ((PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class)).onGround = (Boolean)wrapper.get(Types.BOOLEAN, 0));
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
               playerInfoStorage.posX = (Double)wrapper.get(Types.DOUBLE, 0);
               playerInfoStorage.posY = (Double)wrapper.get(Types.DOUBLE, 1);
               playerInfoStorage.posZ = (Double)wrapper.get(Types.DOUBLE, 3);
               playerInfoStorage.onGround = (Boolean)wrapper.get(Types.BOOLEAN, 0);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
               playerInfoStorage.yaw = (Float)wrapper.get(Types.FLOAT, 0);
               playerInfoStorage.pitch = (Float)wrapper.get(Types.FLOAT, 1);
               playerInfoStorage.onGround = (Boolean)wrapper.get(Types.BOOLEAN, 0);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.MOVE_PLAYER_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
               playerInfoStorage.posX = (Double)wrapper.get(Types.DOUBLE, 0);
               playerInfoStorage.posY = (Double)wrapper.get(Types.DOUBLE, 1);
               playerInfoStorage.posZ = (Double)wrapper.get(Types.DOUBLE, 3);
               playerInfoStorage.yaw = (Float)wrapper.get(Types.FLOAT, 0);
               playerInfoStorage.pitch = (Float)wrapper.get(Types.FLOAT, 1);
               playerInfoStorage.onGround = (Boolean)wrapper.get(Types.BOOLEAN, 0);
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types1_7_6.ITEM);
            this.handler((wrapper) -> Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types1_7_6.ITEM, 0)));
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_SHORT);
            this.map(Types.STRING, Types1_6_4.STRING);
            this.map(Types.STRING, Types1_6_4.STRING);
            this.map(Types.STRING, Types1_6_4.STRING);
            this.map(Types.STRING, Types1_6_4.STRING);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.COMMAND_SUGGESTION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Types1_6_4.STRING);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Types1_6_4.STRING);
            this.handler((wrapper) -> {
               byte renderDistance = (Byte)wrapper.read(Types.BYTE);
               if (renderDistance <= 2) {
                  renderDistance = 3;
               } else if (renderDistance <= 4) {
                  renderDistance = 2;
               } else if (renderDistance <= 8) {
                  renderDistance = 1;
               } else {
                  renderDistance = 0;
               }

               wrapper.write(Types.BYTE, renderDistance);
               byte chatVisibility = (Byte)wrapper.read(Types.BYTE);
               boolean enableColors = (Boolean)wrapper.read(Types.BOOLEAN);
               byte mask = (byte)(chatVisibility | (enableColors ? 1 : 0) << 3);
               wrapper.write(Types.BYTE, mask);
            });
            this.map(Types.BYTE);
            this.map(Types.BOOLEAN);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.CLIENT_COMMAND, (wrapper) -> {
         int action = (Integer)wrapper.read(Types.VAR_INT);
         if (action == 1) {
            Object2IntMap<String> loadedStatistics = new Object2IntOpenHashMap();

            for(Int2IntMap.Entry entry : ((StatisticsStorage)wrapper.user().get(StatisticsStorage.class)).values.int2IntEntrySet()) {
               String key = StatisticRewriter.map(entry.getIntKey());
               if (key != null) {
                  loadedStatistics.put(key, entry.getIntValue());
               }
            }

            PacketWrapper statistics = PacketWrapper.create(ClientboundPackets1_8.AWARD_STATS, (UserConnection)wrapper.user());
            statistics.write(Types.VAR_INT, loadedStatistics.size());

            for(Object2IntMap.Entry entry : loadedStatistics.object2IntEntrySet()) {
               statistics.write(Types.STRING, (String)entry.getKey());
               statistics.write(Types.VAR_INT, entry.getIntValue());
            }

            statistics.send(Protocolr1_6_4Tor1_7_2_5.class);
         }

         if (action != 0) {
            wrapper.cancel();
         } else {
            wrapper.write(Types.BYTE, (byte)1);
         }
      });
      this.registerServerbound(ServerboundPackets1_7_2.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               String channel = (String)wrapper.read(Types.STRING);
               short length = (Short)wrapper.read(Types.SHORT);
               switch (channel) {
                  case "MC|BEdit":
                  case "MC|BSign":
                     Protocolr1_6_4Tor1_7_2_5.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.passthrough(Types1_7_6.ITEM));
                     length = (short)PacketUtil.calculateLength(wrapper);
                     break;
                  case "MC|AdvCdm":
                     byte type = (Byte)wrapper.read(Types.BYTE);
                     if (type != 0) {
                        wrapper.cancel();
                        return;
                     }

                     wrapper.passthrough(Types.INT);
                     wrapper.passthrough(Types.INT);
                     wrapper.passthrough(Types.INT);
                     wrapper.passthrough(Types.STRING);
                     length = (short)PacketUtil.calculateLength(wrapper);
               }

               wrapper.resetReader();
               wrapper.write(Types1_6_4.STRING, channel);
               wrapper.write(Types.SHORT, length);
            });
         }
      });
   }

   void rewriteEntityData(UserConnection user, List entityDataList) {
      for(EntityData entityData : entityDataList) {
         if (entityData.dataType().equals(EntityDataTypes1_6_4.ITEM)) {
            this.itemRewriter.handleItemToClient(user, (Item)entityData.value());
         }

         entityData.setDataType(EntityDataTypes1_7_6.byId(entityData.dataType().typeId()));
      }

   }

   public void register(ViaProviders providers) {
      providers.require(EncryptionProvider.class);
   }

   public void init(final UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolr1_6_4Tor1_7_2_5.class, ClientboundPackets1_6_4::getPacket));
      userConnection.addClientWorld(Protocolr1_6_4Tor1_7_2_5.class, new ClientWorld());
      userConnection.put(new ProtocolMetadataStorage());
      userConnection.put(new PlayerInfoStorage());
      userConnection.put(new StatisticsStorage());
      userConnection.put(new ChunkTracker(userConnection));
      if (userConnection.getChannel() != null) {
         userConnection.getChannel().pipeline().addFirst(new ChannelHandler[]{new ChannelOutboundHandlerAdapter() {
            public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
               if (ctx.channel().isWritable() && userConnection.getProtocolInfo().getClientState().equals(State.PLAY) && ((PlayerInfoStorage)userConnection.get(PlayerInfoStorage.class)).entityId != -1) {
                  PacketWrapper disconnect = PacketWrapper.create(ServerboundPackets1_6_4.DISCONNECT, (UserConnection)userConnection);
                  disconnect.write(Types1_6_4.STRING, "Quitting");
                  disconnect.sendToServer(Protocolr1_6_4Tor1_7_2_5.class);
               }

               super.close(ctx, promise);
            }
         }});
      }

   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
