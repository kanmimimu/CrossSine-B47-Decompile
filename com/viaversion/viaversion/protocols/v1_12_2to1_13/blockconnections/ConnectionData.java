package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.nbt.tag.ByteArrayTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.providers.PacketBlockConnectionProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.providers.UserBlockData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ConnectionData {
   public static BlockConnectionProvider blockConnectionProvider;
   static final Object2IntMap KEY_TO_ID = new Object2IntOpenHashMap(8582);
   static final IntSet OCCLUDING_STATES = new IntOpenHashSet(377);
   static Int2ObjectMap connectionHandlerMap = new Int2ObjectOpenHashMap();
   static Int2ObjectMap blockConnectionData = new Int2ObjectOpenHashMap();
   static final BlockChangeRecord1_8[] EMPTY_RECORDS = new BlockChangeRecord1_8[0];

   public static void update(UserConnection user, BlockPosition position) {
      Boolean inSync = null;

      for(BlockFace face : BlockFace.values()) {
         BlockPosition pos = position.getRelative(face);
         int blockState = blockConnectionProvider.getBlockData(user, pos.x(), pos.y(), pos.z());
         ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
         if (handler != null) {
            int newBlockState = handler.connect(user, pos, blockState);
            if (newBlockState == blockState) {
               if (inSync == null) {
                  inSync = blockConnectionProvider.storesBlocks(user, position);
               }

               if (inSync) {
                  continue;
               }
            }

            updateBlockStorage(user, pos.x(), pos.y(), pos.z(), newBlockState);
            PacketWrapper blockUpdatePacket = PacketWrapper.create(ClientboundPackets1_13.BLOCK_UPDATE, (ByteBuf)null, user);
            blockUpdatePacket.write(Types.BLOCK_POSITION1_8, pos);
            blockUpdatePacket.write(Types.VAR_INT, newBlockState);
            blockUpdatePacket.send(Protocol1_12_2To1_13.class);
         }
      }

   }

   public static void updateBlockStorage(UserConnection userConnection, int x, int y, int z, int blockState) {
      if (needStoreBlocks()) {
         if (isWelcome(blockState)) {
            blockConnectionProvider.storeBlock(userConnection, x, y, z, blockState);
         } else {
            blockConnectionProvider.removeBlock(userConnection, x, y, z);
         }

      }
   }

   public static void clearBlockStorage(UserConnection connection) {
      if (needStoreBlocks()) {
         blockConnectionProvider.clearStorage(connection);
      }
   }

   public static void markModified(UserConnection connection, BlockPosition pos) {
      if (needStoreBlocks()) {
         blockConnectionProvider.modifiedBlock(connection, pos);
      }
   }

   public static boolean needStoreBlocks() {
      return blockConnectionProvider.storesBlocks((UserConnection)null, (BlockPosition)null);
   }

   public static void connectBlocks(UserConnection user, Chunk chunk) {
      int xOff = chunk.getX() << 4;
      int zOff = chunk.getZ() << 4;

      for(int s = 0; s < chunk.getSections().length; ++s) {
         ChunkSection section = chunk.getSections()[s];
         if (section != null) {
            DataPalette blocks = section.palette(PaletteType.BLOCKS);
            boolean willConnect = false;

            for(int p = 0; p < blocks.size(); ++p) {
               int id = blocks.idByIndex(p);
               if (connects(id)) {
                  willConnect = true;
                  break;
               }
            }

            if (willConnect) {
               int yOff = s << 4;

               for(int idx = 0; idx < 4096; ++idx) {
                  int id = blocks.idAt(idx);
                  ConnectionHandler handler = getConnectionHandler(id);
                  if (handler != null) {
                     BlockPosition position = new BlockPosition(xOff + ChunkSection.xFromIndex(idx), yOff + ChunkSection.yFromIndex(idx), zOff + ChunkSection.zFromIndex(idx));
                     int connectedId = handler.connect(user, position, id);
                     if (connectedId != id) {
                        blocks.setIdAt(idx, connectedId);
                        updateBlockStorage(user, position.x(), position.y(), position.z(), connectedId);
                     }
                  }
               }
            }
         }
      }

   }

   public static void init() {
      if (Via.getConfig().isServersideBlockConnections()) {
         Via.getPlatform().getLogger().info("Loading block connection mappings ...");
         ListTag<StringTag> blockStates = MappingDataLoader.INSTANCE.loadNBT("blockstates-1.13.nbt").getListTag("blockstates", StringTag.class);

         for(int id = 0; id < blockStates.size(); ++id) {
            String key = ((StringTag)blockStates.get(id)).getValue();
            KEY_TO_ID.put(key, id);
         }

         connectionHandlerMap = new Int2ObjectOpenHashMap(3650);
         if (!Via.getConfig().isReduceBlockStorageMemory()) {
            blockConnectionData = new Int2ObjectOpenHashMap(2048);
            CompoundTag data = MappingDataLoader.INSTANCE.loadNBT("blockConnections.nbt");

            for(CompoundTag blockTag : data.getListTag("data", CompoundTag.class)) {
               BlockData blockData = new BlockData();

               for(Map.Entry entry : blockTag.entrySet()) {
                  String key = (String)entry.getKey();
                  if (!key.equals("id") && !key.equals("ids")) {
                     boolean[] attachingFaces = new boolean[4];
                     ByteArrayTag connections = (ByteArrayTag)entry.getValue();

                     for(byte blockFaceId : connections.getValue()) {
                        attachingFaces[blockFaceId] = true;
                     }

                     int connectionTypeId = Integer.parseInt(key);
                     blockData.put(connectionTypeId, attachingFaces);
                  }
               }

               NumberTag idTag = blockTag.getNumberTag("id");
               if (idTag != null) {
                  blockConnectionData.put(idTag.asInt(), blockData);
               } else {
                  IntArrayTag idsTag = blockTag.getIntArrayTag("ids");

                  for(int id : idsTag.getValue()) {
                     blockConnectionData.put(id, blockData);
                  }
               }
            }

            IntArrayTag occludingStatesArray = data.getIntArrayTag("occluding-states");

            for(int blockStateId : occludingStatesArray.getValue()) {
               OCCLUDING_STATES.add(blockStateId);
            }
         }

         List<ConnectorInitAction> initActions = new ArrayList();
         initActions.add(PumpkinConnectionHandler.init());
         initActions.addAll(BasicFenceConnectionHandler.init());
         initActions.add(NetherFenceConnectionHandler.init());
         initActions.addAll(WallConnectionHandler.init());
         initActions.add(MelonConnectionHandler.init());
         initActions.addAll(GlassConnectionHandler.init());
         initActions.add(ChestConnectionHandler.init());
         initActions.add(DoorConnectionHandler.init());
         initActions.add(RedstoneConnectionHandler.init());
         initActions.add(StairConnectionHandler.init());
         initActions.add(FlowerConnectionHandler.init());
         initActions.addAll(ChorusPlantConnectionHandler.init());
         initActions.add(TripwireConnectionHandler.init());
         initActions.add(SnowyGrassConnectionHandler.init());
         initActions.add(FireConnectionHandler.init());
         if (Via.getConfig().isVineClimbFix()) {
            initActions.add(VineConnectionHandler.init());
         }

         for(String key : KEY_TO_ID.keySet()) {
            WrappedBlockData wrappedBlockData = WrappedBlockData.fromString(key);

            for(ConnectorInitAction action : initActions) {
               action.check(wrappedBlockData);
            }
         }

         if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
            blockConnectionProvider = new PacketBlockConnectionProvider();
            Via.getManager().getProviders().register(BlockConnectionProvider.class, blockConnectionProvider);
         }

      }
   }

   public static boolean isWelcome(int blockState) {
      return blockConnectionData.containsKey(blockState) || connectionHandlerMap.containsKey(blockState);
   }

   public static boolean connects(int blockState) {
      return connectionHandlerMap.containsKey(blockState);
   }

   public static int connect(UserConnection user, BlockPosition position, int blockState) {
      ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
      return handler != null ? handler.connect(user, position, blockState) : blockState;
   }

   public static ConnectionHandler getConnectionHandler(int blockstate) {
      return (ConnectionHandler)connectionHandlerMap.get(blockstate);
   }

   public static int getId(String key) {
      return KEY_TO_ID.getOrDefault(Key.stripMinecraftNamespace(key), -1);
   }

   public static Object2IntMap getKeyToId() {
      return KEY_TO_ID;
   }

   static {
      KEY_TO_ID.defaultReturnValue(-1);
   }

   public static final class NeighbourUpdater {
      final UserConnection user;
      final UserBlockData userBlockData;

      public NeighbourUpdater(UserConnection user) {
         this.user = user;
         this.userBlockData = ConnectionData.blockConnectionProvider.forUser(user);
      }

      public void updateChunkSectionNeighbours(int chunkX, int chunkZ, int chunkSectionY) {
         int chunkMinY = chunkSectionY << 4;
         List<BlockChangeRecord1_8> updates = new ArrayList();

         for(int chunkDeltaX = -1; chunkDeltaX <= 1; ++chunkDeltaX) {
            for(int chunkDeltaZ = -1; chunkDeltaZ <= 1; ++chunkDeltaZ) {
               int distance = Math.abs(chunkDeltaX) + Math.abs(chunkDeltaZ);
               if (distance != 0) {
                  int chunkMinX = chunkX + chunkDeltaX << 4;
                  int chunkMinZ = chunkZ + chunkDeltaZ << 4;
                  if (distance == 2) {
                     for(int blockY = chunkMinY; blockY < chunkMinY + 16; ++blockY) {
                        int blockPosX = chunkDeltaX == 1 ? 0 : 15;
                        int blockPosZ = chunkDeltaZ == 1 ? 0 : 15;
                        this.updateBlock(chunkMinX + blockPosX, blockY, chunkMinZ + blockPosZ, updates);
                     }
                  } else {
                     for(int blockY = chunkMinY; blockY < chunkMinY + 16; ++blockY) {
                        int zStart;
                        int zEnd;
                        int xStart;
                        int xEnd;
                        if (chunkDeltaX == 1) {
                           xStart = 0;
                           xEnd = 2;
                           zStart = 0;
                           zEnd = 16;
                        } else if (chunkDeltaX == -1) {
                           xStart = 14;
                           xEnd = 16;
                           zStart = 0;
                           zEnd = 16;
                        } else if (chunkDeltaZ == 1) {
                           xStart = 0;
                           xEnd = 16;
                           zStart = 0;
                           zEnd = 2;
                        } else {
                           xStart = 0;
                           xEnd = 16;
                           zStart = 14;
                           zEnd = 16;
                        }

                        for(int blockX = xStart; blockX < xEnd; ++blockX) {
                           for(int blockZ = zStart; blockZ < zEnd; ++blockZ) {
                              this.updateBlock(chunkMinX + blockX, blockY, chunkMinZ + blockZ, updates);
                           }
                        }
                     }
                  }

                  if (!updates.isEmpty()) {
                     PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_13.CHUNK_BLOCKS_UPDATE, (ByteBuf)null, this.user);
                     wrapper.write(Types.INT, chunkX + chunkDeltaX);
                     wrapper.write(Types.INT, chunkZ + chunkDeltaZ);
                     wrapper.write(Types.BLOCK_CHANGE_ARRAY, (BlockChangeRecord[])updates.toArray(ConnectionData.EMPTY_RECORDS));
                     wrapper.send(Protocol1_12_2To1_13.class);
                     updates.clear();
                  }
               }
            }
         }

      }

      void updateBlock(int x, int y, int z, List records) {
         int blockState = this.userBlockData.getBlockData(x, y, z);
         ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
         if (handler != null) {
            BlockPosition pos = new BlockPosition(x, y, z);
            int newBlockState = handler.connect(this.user, pos, blockState);
            if (blockState != newBlockState || !ConnectionData.blockConnectionProvider.storesBlocks(this.user, (BlockPosition)null)) {
               records.add(new BlockChangeRecord1_8(x & 15, y, z & 15, newBlockState));
               ConnectionData.updateBlockStorage(this.user, x, y, z, newBlockState);
            }

         }
      }
   }

   @FunctionalInterface
   interface ConnectorInitAction {
      void check(WrappedBlockData var1);
   }
}
