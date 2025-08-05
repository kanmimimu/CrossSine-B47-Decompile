package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.api.util.BlockFaceUtil;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ServerboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.storage.TimeLockStorage;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1;
import net.raphimc.vialegacy.protocol.alpha.a1_1_0_1_1_2_1toa1_2_0_1_2_1_1.packet.ClientboundPacketsa1_1_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.AlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.AlphaInventoryTracker;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.storage.BlockDigStorage;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.Protocolb1_5_0_2Tob1_6_0_6;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.Protocolb1_7_0_3Tob1_8_0_1;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ClientboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ClientboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.data.ClassicBlocks;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.model.ClassicLevel;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ClientboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ServerboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicCustomCommandProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicMPPassProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicWorldHeightProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicOpLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicPositionTracker;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicProgressStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicServerTitleStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.task.ClassicLevelStorageTickTask;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.Typesc0_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.Protocolc0_30cpeToc0_28_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage.ExtBlockPermissionsStorage;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.Protocolr1_7_6_10Tor1_8;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolc0_28_30Toa1_0_15 extends StatelessProtocol {
   public Protocolc0_28_30Toa1_0_15() {
      super(ClientboundPacketsc0_28.class, ClientboundPacketsa1_0_15.class, ServerboundPacketsc0_28.class, ServerboundPacketsa1_0_15.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsc0_28.LOGIN, new PacketHandlers() {
         public void register() {
            this.read(Types.BYTE);
            this.handler((wrapper) -> {
               String title = ((String)wrapper.read(Typesc0_30.STRING)).replace("&", "§");
               String motd = ((String)wrapper.read(Typesc0_30.STRING)).replace("&", "§");
               byte opLevel = (Byte)wrapper.read(Types.BYTE);
               wrapper.user().put(new ClassicServerTitleStorage(wrapper.user(), title, motd));
               ((ClassicOpLevelStorage)wrapper.user().get(ClassicOpLevelStorage.class)).setOpLevel(opLevel);
               wrapper.write(Types.INT, wrapper.user().getProtocolInfo().getUsername().hashCode());
               wrapper.write(Typesb1_7_0_3.STRING, wrapper.user().getProtocolInfo().getUsername());
               wrapper.write(Typesb1_7_0_3.STRING, "");
               if (wrapper.user().has(ClassicLevelStorage.class)) {
                  wrapper.cancel();
               }

               if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_7_6_10Tor1_8.class)) {
                  PacketWrapper tabList = PacketWrapper.create(ClientboundPackets1_8.TAB_LIST, (UserConnection)wrapper.user());
                  tabList.write(Types.STRING, (String)Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON.transform(wrapper, "§6" + title + "\n"));
                  tabList.write(Types.STRING, (String)Protocolr1_7_6_10Tor1_8.LEGACY_TO_JSON.transform(wrapper, "\n§b" + motd));
                  tabList.send(Protocolr1_7_6_10Tor1_8.class);
               }

               ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
               classicProgressStorage.progress = 1;
               classicProgressStorage.upperBound = 2;
               classicProgressStorage.status = "Waiting for server...";
            });
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.LEVEL_INIT, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         if (wrapper.user().has(ClassicLevelStorage.class) && wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_5_0_2Tob1_6_0_6.class)) {
            PacketWrapper fakeRespawn = PacketWrapper.create(ClientboundPacketsb1_7.RESPAWN, (UserConnection)wrapper.user());
            fakeRespawn.write(Types.BYTE, -1);
            fakeRespawn.send(Protocolb1_5_0_2Tob1_6_0_6.class);
            PacketWrapper respawn = PacketWrapper.create(ClientboundPacketsb1_7.RESPAWN, (UserConnection)wrapper.user());
            respawn.write(Types.BYTE, (byte)0);
            respawn.send(Protocolb1_5_0_2Tob1_6_0_6.class);
            ((ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class)).spawned = false;
         }

         if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_7_0_3Tob1_8_0_1.class)) {
            PacketWrapper gameEvent = PacketWrapper.create(ClientboundPacketsb1_8.GAME_EVENT, (UserConnection)wrapper.user());
            gameEvent.write(Types.BYTE, (byte)3);
            gameEvent.write(Types.BYTE, (byte)1);
            gameEvent.send(Protocolb1_7_0_3Tob1_8_0_1.class);
         }

         ((ClassicOpLevelStorage)wrapper.user().get(ClassicOpLevelStorage.class)).updateAbilities();
         wrapper.user().put(new ClassicLevelStorage(wrapper.user()));
         ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
         classicProgressStorage.progress = 2;
         classicProgressStorage.upperBound = 2;
         classicProgressStorage.status = "Waiting for server...";
      });
      this.registerClientbound(ClientboundPacketsc0_28.LEVEL_DATA, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         short partSize = (Short)wrapper.read(Types.SHORT);
         byte[] data = (byte[])wrapper.read(Typesc0_30.BYTE_ARRAY);
         byte progress = (Byte)wrapper.read(Types.BYTE);
         ((ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class)).addDataPart(data, partSize);
         ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
         classicProgressStorage.upperBound = 100;
         classicProgressStorage.progress = progress;
         classicProgressStorage.status = (new StringBuilder()).append("Receiving level... §7").append(progress).append("%").toString();
      });
      this.registerClientbound(ClientboundPacketsc0_28.LEVEL_FINALIZE, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         short sizeX = (Short)wrapper.read(Types.SHORT);
         short sizeY = (Short)wrapper.read(Types.SHORT);
         short sizeZ = (Short)wrapper.read(Types.SHORT);
         ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
         ClassicLevelStorage levelStorage = (ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class);
         short maxChunkSectionCount = ((ClassicWorldHeightProvider)Via.getManager().getProviders().get(ClassicWorldHeightProvider.class)).getMaxChunkSectionCount(wrapper.user());
         classicProgressStorage.upperBound = 2;
         classicProgressStorage.progress = 0;
         classicProgressStorage.status = "Finishing level... §7Decompressing";
         levelStorage.finish(sizeX, sizeY, sizeZ);
         levelStorage.sendChunk(new ChunkCoord(0, 0));
         if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_7_6_10Tor1_8.class)) {
            PacketWrapper worldBorder = PacketWrapper.create(ClientboundPackets1_8.SET_BORDER, (UserConnection)wrapper.user());
            worldBorder.write(Types.VAR_INT, 3);
            worldBorder.write(Types.DOUBLE, (double)sizeX / (double)2.0F);
            worldBorder.write(Types.DOUBLE, (double)sizeZ / (double)2.0F);
            worldBorder.write(Types.DOUBLE, (double)0.0F);
            worldBorder.write(Types.DOUBLE, (double)Math.max(sizeX, sizeZ));
            worldBorder.write(Types.VAR_LONG, 0L);
            worldBorder.write(Types.VAR_INT, Math.max(sizeX, sizeZ));
            worldBorder.write(Types.VAR_INT, 0);
            worldBorder.write(Types.VAR_INT, 0);
            worldBorder.send(Protocolr1_7_6_10Tor1_8.class);
         }

         this.sendChatMessage(wrapper.user(), (new StringBuilder()).append("§aWorld dimensions: §6").append(sizeX).append("§ax§6").append(sizeY).append("§ax§6").append(sizeZ).toString());
         if (sizeY > maxChunkSectionCount << 4) {
            UserConnection var10001 = wrapper.user();
            int var14 = maxChunkSectionCount << 4;
            this.sendChatMessage(var10001, "§cThis server has a world higher than " + var14 + " blocks! Expect world errors");
         }

         classicProgressStorage.progress = 1;
         classicProgressStorage.status = "Finishing level... §7Waiting for server";
      });
      this.registerClientbound(ClientboundPacketsc0_28.BLOCK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Typesc0_30.BLOCK_POSITION, Types1_7_6.BLOCK_POSITION_UBYTE);
            this.handler((wrapper) -> {
               ClassicLevelStorage levelStorage = (ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class);
               if (levelStorage != null && levelStorage.hasReceivedLevel()) {
                  ClassicBlockRemapper remapper = (ClassicBlockRemapper)wrapper.user().get(ClassicBlockRemapper.class);
                  BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
                  byte blockId = (Byte)wrapper.read(Types.BYTE);
                  levelStorage.getClassicLevel().setBlock(pos, blockId);
                  if (!levelStorage.isChunkLoaded(pos)) {
                     wrapper.cancel();
                  } else {
                     IdAndData mappedBlock = (IdAndData)remapper.mapper().get(blockId);
                     wrapper.write(Types.UNSIGNED_BYTE, (short)mappedBlock.getId());
                     wrapper.write(Types.UNSIGNED_BYTE, (short)mappedBlock.getData());
                  }
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
            this.map(Typesc0_30.STRING, Typesb1_7_0_3.STRING, (n) -> n.replace("&", "§"));
            this.map(Types.SHORT, Types.INT);
            this.map(Types.SHORT, Types.INT);
            this.map(Types.SHORT, Types.INT);
            this.map(Types.BYTE, Types.BYTE, (yaw) -> (byte)(yaw + 128));
            this.map(Types.BYTE);
            this.create(Types.UNSIGNED_SHORT, 0);
            this.handler((wrapper) -> {
               if ((Integer)wrapper.get(Types.INT, 0) < 0) {
                  wrapper.cancel();
                  int x = (Integer)wrapper.get(Types.INT, 1);
                  int y = (Integer)wrapper.get(Types.INT, 2);
                  int z = (Integer)wrapper.get(Types.INT, 3);
                  byte yaw = (Byte)wrapper.get(Types.BYTE, 0);
                  byte pitch = (Byte)wrapper.get(Types.BYTE, 1);
                  ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
                  classicProgressStorage.progress = 2;
                  classicProgressStorage.status = "Finishing level... §7Loading spawn chunks";
                  ClassicPositionTracker classicPositionTracker = (ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class);
                  classicPositionTracker.posX = (double)((float)x / 32.0F);
                  classicPositionTracker.stance = (double)((float)y / 32.0F + 0.714F);
                  classicPositionTracker.posZ = (double)((float)z / 32.0F);
                  classicPositionTracker.yaw = (float)(yaw * 360) / 256.0F;
                  classicPositionTracker.pitch = (float)(pitch * 360) / 256.0F;
                  ((ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class)).sendChunks(classicPositionTracker.getChunkPosition(), 1);
                  if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1.class)) {
                     PacketWrapper spawnPosition = PacketWrapper.create(ClientboundPacketsa1_1_0.SET_DEFAULT_SPAWN_POSITION, (UserConnection)wrapper.user());
                     spawnPosition.write(Types1_7_6.BLOCK_POSITION_INT, new BlockPosition((int)classicPositionTracker.posX, (int)classicPositionTracker.stance, (int)classicPositionTracker.posZ));
                     spawnPosition.send(Protocola1_0_17_1_0_17_4Toa1_1_0_1_1_2_1.class);
                  }

                  PacketWrapper playerPosition = PacketWrapper.create(ClientboundPacketsa1_0_15.PLAYER_POSITION, (UserConnection)wrapper.user());
                  playerPosition.write(Types.DOUBLE, classicPositionTracker.posX);
                  playerPosition.write(Types.DOUBLE, classicPositionTracker.stance);
                  playerPosition.write(Types.DOUBLE, classicPositionTracker.stance - (double)1.62F);
                  playerPosition.write(Types.DOUBLE, classicPositionTracker.posZ);
                  playerPosition.write(Types.FLOAT, classicPositionTracker.yaw);
                  playerPosition.write(Types.FLOAT, classicPositionTracker.pitch);
                  playerPosition.write(Types.BOOLEAN, true);
                  playerPosition.send(Protocolc0_28_30Toa1_0_15.class);
                  classicPositionTracker.spawned = true;
               } else {
                  wrapper.set(Types.INT, 2, (Integer)wrapper.get(Types.INT, 2) - Float.valueOf(51.84F).intValue());
               }

            });
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.TELEPORT_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
            this.map(Types.SHORT, Types.INT);
            this.map(Types.SHORT, Types.INT);
            this.map(Types.SHORT, Types.INT);
            this.map(Types.BYTE, Types.BYTE, (yaw) -> (byte)(yaw + 128));
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               if ((Integer)wrapper.get(Types.INT, 0) < 0) {
                  wrapper.set(Types.INT, 2, (Integer)wrapper.get(Types.INT, 2) - 29);
                  wrapper.set(Types.INT, 0, wrapper.user().getProtocolInfo().getUsername().hashCode());
               } else {
                  wrapper.set(Types.INT, 2, (Integer)wrapper.get(Types.INT, 2) - Float.valueOf(51.84F).intValue());
               }

            });
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE, Types.BYTE, (yaw) -> (byte)(yaw + 128));
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.MOVE_ENTITY_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
            this.map(Types.BYTE, Types.BYTE, (yaw) -> (byte)(yaw + 128));
            this.map(Types.BYTE);
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.REMOVE_ENTITIES, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.INT);
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.CHAT, new PacketHandlers() {
         public void register() {
            this.handler((packetWrapper) -> {
               byte senderId = (Byte)packetWrapper.read(Types.BYTE);
               String message = ((String)packetWrapper.read(Typesc0_30.STRING)).replace("&", "§");
               if (senderId < 0) {
                  message = "§e" + message;
               }

               packetWrapper.write(Typesb1_7_0_3.STRING, message);
            });
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.DISCONNECT, new PacketHandlers() {
         public void register() {
            this.map(Typesc0_30.STRING, Typesb1_7_0_3.STRING, (s) -> s.replace("&", "§"));
         }
      });
      this.registerClientbound(ClientboundPacketsc0_28.OP_LEVEL_UPDATE, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ClassicOpLevelStorage opLevelStorage = (ClassicOpLevelStorage)wrapper.user().get(ClassicOpLevelStorage.class);
         byte opLevel = (Byte)wrapper.read(Types.BYTE);
         opLevelStorage.setOpLevel(opLevel);
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.BYTE);
            this.map(Typesb1_7_0_3.STRING, Typesc0_30.STRING);
            this.read(Typesb1_7_0_3.STRING);
            this.handler((wrapper) -> {
               wrapper.write(Typesc0_30.STRING, ((ClassicMPPassProvider)Via.getManager().getProviders().get(ClassicMPPassProvider.class)).getMpPass(wrapper.user()));
               wrapper.write(Types.BYTE, (byte)0);
               ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
               classicProgressStorage.upperBound = 2;
               classicProgressStorage.status = "Logging in...";
            });
         }
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.CHAT, (wrapper) -> {
         String message = (String)wrapper.read(Typesb1_7_0_3.STRING);
         wrapper.write(Types.BYTE, -1);
         wrapper.write(Typesc0_30.STRING, message);
         if (((ClassicCustomCommandProvider)Via.getManager().getProviders().get(ClassicCustomCommandProvider.class)).handleChatMessage(wrapper.user(), message)) {
            wrapper.cancel();
         }

      });
      this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_STATUS_ONLY, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, new PacketHandlers() {
         public void register() {
            this.read(Types.BOOLEAN);
            this.handler((wrapper) -> ((ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class)).writeToPacket(wrapper));
         }
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_POS, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         ClassicPositionTracker positionTracker = (ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class);
         positionTracker.posX = (Double)wrapper.read(Types.DOUBLE);
         wrapper.read(Types.DOUBLE);
         positionTracker.stance = (Double)wrapper.read(Types.DOUBLE);
         positionTracker.posZ = (Double)wrapper.read(Types.DOUBLE);
         wrapper.read(Types.BOOLEAN);
         positionTracker.writeToPacket(wrapper);
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_ROT, ServerboundPacketsc0_28.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         ClassicPositionTracker positionTracker = (ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class);
         positionTracker.yaw = (Float)wrapper.read(Types.FLOAT);
         positionTracker.pitch = (Float)wrapper.read(Types.FLOAT);
         wrapper.read(Types.BOOLEAN);
         positionTracker.writeToPacket(wrapper);
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         ClassicPositionTracker positionTracker = (ClassicPositionTracker)wrapper.user().get(ClassicPositionTracker.class);
         positionTracker.posX = (Double)wrapper.read(Types.DOUBLE);
         wrapper.read(Types.DOUBLE);
         positionTracker.stance = (Double)wrapper.read(Types.DOUBLE);
         positionTracker.posZ = (Double)wrapper.read(Types.DOUBLE);
         positionTracker.yaw = (Float)wrapper.read(Types.FLOAT);
         positionTracker.pitch = (Float)wrapper.read(Types.FLOAT);
         wrapper.read(Types.BOOLEAN);
         positionTracker.writeToPacket(wrapper);
      });
      this.registerServerbound(ServerboundPacketsa1_0_15.PLAYER_ACTION, ServerboundPacketsc0_28.USE_ITEM_ON, (wrapper) -> {
         wrapper.user().getStoredObjects().remove(BlockDigStorage.class);
         ClassicLevel level = ((ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class)).getClassicLevel();
         ClassicOpLevelStorage opTracker = (ClassicOpLevelStorage)wrapper.user().get(ClassicOpLevelStorage.class);
         boolean extendedVerification = wrapper.user().has(ExtBlockPermissionsStorage.class);
         short status = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         BlockPosition pos = (BlockPosition)wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
         wrapper.read(Types.UNSIGNED_BYTE);
         int blockId = level.getBlock(pos);
         boolean hasCreative = wrapper.user().getProtocolInfo().getPipeline().contains(Protocolb1_7_0_3Tob1_8_0_1.class);
         if (status == 0 && hasCreative || status == 2 && !hasCreative) {
            if (!extendedVerification && blockId == 7 && opTracker.getOpLevel() < 100) {
               wrapper.cancel();
               this.sendChatMessage(wrapper.user(), "§cOnly op players can break bedrock!");
               this.sendBlockChange(wrapper.user(), pos, new IdAndData(BlockList1_6.bedrock.blockId(), 0));
               return;
            }

            if (!extendedVerification) {
               level.setBlock(pos, 0);
               this.sendBlockChange(wrapper.user(), pos, new IdAndData(0, 0));
            }

            wrapper.write(Typesc0_30.BLOCK_POSITION, pos);
            wrapper.write(Types.BOOLEAN, false);
            wrapper.write(Types.BYTE, (byte)1);
         } else {
            wrapper.cancel();
         }

      });
      this.registerServerbound(ServerboundPacketsa1_0_15.USE_ITEM_ON, (wrapper) -> {
         ClassicLevel level = ((ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class)).getClassicLevel();
         ClassicBlockRemapper remapper = (ClassicBlockRemapper)wrapper.user().get(ClassicBlockRemapper.class);
         boolean extendedVerification = wrapper.user().has(ExtBlockPermissionsStorage.class);
         wrapper.read(Types.SHORT);
         BlockPosition pos = (BlockPosition)wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
         short direction = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         Item item = ((AlphaInventoryProvider)Via.getManager().getProviders().get(AlphaInventoryProvider.class)).getHandItem(wrapper.user());
         if (item != null && direction != 255) {
            pos = pos.getRelative(BlockFaceUtil.getFace(direction));
            if (pos.y() >= level.getSizeY()) {
               wrapper.cancel();
               UserConnection var10001 = wrapper.user();
               int var10 = level.getSizeY();
               this.sendChatMessage(var10001, "§cHeight limit for building is " + var10 + " blocks");
               this.sendBlockChange(wrapper.user(), pos, new IdAndData(0, 0));
            } else {
               byte classicBlock = (byte)remapper.reverseMapper().getInt(new IdAndData(item.identifier(), item.data() & 15));
               if (!extendedVerification) {
                  level.setBlock(pos, classicBlock);
                  this.sendBlockChange(wrapper.user(), pos, (IdAndData)remapper.mapper().get(classicBlock));
               }

               wrapper.write(Typesc0_30.BLOCK_POSITION, pos);
               wrapper.write(Types.BOOLEAN, true);
               wrapper.write(Types.BYTE, classicBlock);
            }
         } else {
            wrapper.cancel();
         }
      });
      this.cancelServerbound(ServerboundPacketsa1_0_15.KEEP_ALIVE);
      this.cancelServerbound(ServerboundPacketsa1_0_15.SET_CARRIED_ITEM);
      this.cancelServerbound(ServerboundPacketsa1_0_15.SWING);
      this.cancelServerbound(ServerboundPacketsa1_0_15.SPAWN_ITEM);
      this.cancelServerbound(ServerboundPacketsa1_0_15.DISCONNECT);
   }

   void sendChatMessage(UserConnection user, String msg) {
      PacketWrapper message = PacketWrapper.create(ClientboundPacketsa1_0_15.CHAT, (UserConnection)user);
      message.write(Typesb1_7_0_3.STRING, msg);
      message.send(Protocolc0_28_30Toa1_0_15.class);
   }

   void sendBlockChange(UserConnection user, BlockPosition pos, IdAndData block) {
      PacketWrapper blockChange = PacketWrapper.create(ClientboundPacketsa1_0_15.BLOCK_UPDATE, (UserConnection)user);
      blockChange.write(Types1_7_6.BLOCK_POSITION_UBYTE, pos);
      blockChange.write(Types.UNSIGNED_BYTE, (short)block.getId());
      blockChange.write(Types.UNSIGNED_BYTE, (short)block.getData());
      blockChange.send(Protocolc0_28_30Toa1_0_15.class);
   }

   public void register(ViaProviders providers) {
      providers.register(ClassicWorldHeightProvider.class, new ClassicWorldHeightProvider());
      providers.register(ClassicMPPassProvider.class, new ClassicMPPassProvider());
      providers.register(ClassicCustomCommandProvider.class, new ClassicCustomCommandProvider());
      Via.getPlatform().runRepeatingSync(new ClassicLevelStorageTickTask(), 2L);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolc0_28_30Toa1_0_15.class, ClientboundPacketsc0_28::getPacket));
      userConnection.put(new ClassicPositionTracker());
      userConnection.put(new ClassicOpLevelStorage(userConnection, ViaLegacy.getConfig().enableClassicFly()));
      userConnection.put(new ClassicProgressStorage());
      userConnection.put(new ClassicBlockRemapper((i) -> (IdAndData)ClassicBlocks.MAPPING.get(i), (o) -> {
         int block = ClassicBlocks.REVERSE_MAPPING.getInt(o);
         if (!userConnection.getProtocolInfo().getPipeline().contains(Protocolc0_30cpeToc0_28_30.class)) {
            if (block == 2) {
               block = 3;
            } else if (block == 7) {
               block = 1;
            } else if (block == 9) {
               block = 29;
            } else if (block == 11) {
               block = 22;
            }
         }

         return block;
      }));
      if (userConnection.has(AlphaInventoryTracker.class)) {
         ((AlphaInventoryTracker)userConnection.get(AlphaInventoryTracker.class)).setCreativeMode(true);
      }

      if (userConnection.has(TimeLockStorage.class)) {
         ((TimeLockStorage)userConnection.get(TimeLockStorage.class)).setTime(6000L);
      }

   }
}
