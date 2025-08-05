package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.FixedByteArrayType;
import com.viaversion.viaversion.util.IdAndData;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.Protocola1_0_15Toa1_0_16_2;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.data.ClassicBlocks;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.model.ClassicLevel;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ClientboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ServerboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicOpLevelStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicProgressStorage;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.Typesc0_30;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data.ClassicProtocolExtension;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data.ExtendedClassicBlocks;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.packet.ClientboundPacketsc0_30cpe;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.packet.ServerboundPacketsc0_30cpe;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage.ExtBlockPermissionsStorage;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.storage.ExtensionProtocolMetadataStorage;
import net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.task.ClassicPingTask;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.Types1_1;
import net.raphimc.vialegacy.protocol.release.r1_6_1tor1_6_2.Protocolr1_6_1Tor1_6_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ClientboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;

public class Protocolc0_30cpeToc0_28_30 extends StatelessProtocol {
   public Protocolc0_30cpeToc0_28_30() {
      super(ClientboundPacketsc0_30cpe.class, ClientboundPacketsc0_28.class, ServerboundPacketsc0_30cpe.class, ServerboundPacketsc0_28.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsc0_30cpe.LOGIN, (wrapper) -> {
         if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_6_1Tor1_6_2.class)) {
            ExtensionProtocolMetadataStorage protocolMetadataStorage = (ExtensionProtocolMetadataStorage)wrapper.user().get(ExtensionProtocolMetadataStorage.class);
            PacketWrapper brand = PacketWrapper.create(ClientboundPackets1_6_4.CUSTOM_PAYLOAD, (UserConnection)wrapper.user());
            brand.write(Types1_6_4.STRING, "MC|Brand");
            byte[] brandBytes = protocolMetadataStorage.getServerSoftwareName().getBytes(StandardCharsets.UTF_8);
            brand.write(Types.SHORT, (short)brandBytes.length);
            brand.write(Types.REMAINING_BYTES, brandBytes);
            wrapper.send(Protocolc0_30cpeToc0_28_30.class);
            brand.send(Protocolr1_6_1Tor1_6_2.class);
            wrapper.cancel();
         }

      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXTENSION_PROTOCOL_INFO, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ExtensionProtocolMetadataStorage protocolMetadataStorage = (ExtensionProtocolMetadataStorage)wrapper.user().get(ExtensionProtocolMetadataStorage.class);
         protocolMetadataStorage.setServerSoftwareName((String)wrapper.read(Typesc0_30.STRING));
         protocolMetadataStorage.setExtensionCount((Short)wrapper.read(Types.SHORT));
         ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
         classicProgressStorage.progress = 0;
         classicProgressStorage.upperBound = protocolMetadataStorage.getExtensionCount();
         classicProgressStorage.status = "Receiving extension list...";
      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXTENSION_PROTOCOL_ENTRY, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ExtensionProtocolMetadataStorage protocolMetadataStorage = (ExtensionProtocolMetadataStorage)wrapper.user().get(ExtensionProtocolMetadataStorage.class);
         String extensionName = (String)wrapper.read(Typesc0_30.STRING);
         int extensionVersion = (Integer)wrapper.read(Types.INT);
         ClassicProtocolExtension extension = ClassicProtocolExtension.byName(extensionName);
         if (extension != null) {
            protocolMetadataStorage.addServerExtension(extension, extensionVersion);
         } else if (!Via.getConfig().isSuppressConversionWarnings()) {
            ViaLegacy.getPlatform().getLogger().warning("Received unknown classic protocol extension: (" + extensionName + " v" + extensionVersion + ")");
         }

         protocolMetadataStorage.incrementReceivedExtensions();
         ClassicProgressStorage classicProgressStorage = (ClassicProgressStorage)wrapper.user().get(ClassicProgressStorage.class);
         classicProgressStorage.progress = protocolMetadataStorage.getReceivedExtensions();
         if (protocolMetadataStorage.getReceivedExtensions() >= protocolMetadataStorage.getExtensionCount()) {
            classicProgressStorage.status = "Sending extension list...";
            List<ClassicProtocolExtension> supportedExtensions = new ArrayList();

            for(ClassicProtocolExtension protocolExtension : ClassicProtocolExtension.values()) {
               if (protocolExtension.isSupported()) {
                  supportedExtensions.add(protocolExtension);
               }
            }

            if (supportedExtensions.contains(ClassicProtocolExtension.BLOCK_PERMISSIONS)) {
               wrapper.user().put(new ExtBlockPermissionsStorage());
            }

            PacketWrapper extensionProtocolInfo = PacketWrapper.create(ServerboundPacketsc0_30cpe.EXTENSION_PROTOCOL_INFO, (UserConnection)wrapper.user());
            extensionProtocolInfo.write(Typesc0_30.STRING, ViaLegacy.getPlatform().getCpeAppName());
            extensionProtocolInfo.write(Types.SHORT, (short)supportedExtensions.size());
            extensionProtocolInfo.sendToServer(Protocolc0_30cpeToc0_28_30.class);

            for(ClassicProtocolExtension protocolExtension : supportedExtensions) {
               PacketWrapper extensionProtocolEntry = PacketWrapper.create(ServerboundPacketsc0_30cpe.EXTENSION_PROTOCOL_ENTRY, (UserConnection)wrapper.user());
               extensionProtocolEntry.write(Typesc0_30.STRING, protocolExtension.getName());
               extensionProtocolEntry.write(Types.INT, protocolExtension.getHighestSupportedVersion());
               extensionProtocolEntry.sendToServer(Protocolc0_30cpeToc0_28_30.class);
            }
         }

      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXT_CUSTOM_BLOCKS_SUPPORT_LEVEL, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         byte level = (Byte)wrapper.read(Types.BYTE);
         if (level != 1) {
            ViaLegacy.getPlatform().getLogger().info((new StringBuilder()).append("Classic server supports CustomBlocks level ").append(level).toString());
         }

         PacketWrapper response = PacketWrapper.create(ServerboundPacketsc0_30cpe.EXT_CUSTOM_BLOCKS_SUPPORT_LEVEL, (UserConnection)wrapper.user());
         response.write(Types.BYTE, (byte)1);
         response.sendToServer(Protocolc0_30cpeToc0_28_30.class);
      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXT_HACK_CONTROL, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ClassicOpLevelStorage opLevelStorage = (ClassicOpLevelStorage)wrapper.user().get(ClassicOpLevelStorage.class);
         boolean flying = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean noClip = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean speed = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean respawn = (Boolean)wrapper.read(Types.BOOLEAN);
         wrapper.read(Types.BOOLEAN);
         wrapper.read(Types.SHORT);
         opLevelStorage.updateHax(flying, noClip, speed, respawn);
      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXT_SET_BLOCK_PERMISSION, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ExtBlockPermissionsStorage blockPermissionsStorage = (ExtBlockPermissionsStorage)wrapper.user().get(ExtBlockPermissionsStorage.class);
         byte blockId = (Byte)wrapper.read(Types.BYTE);
         boolean canPlace = (Boolean)wrapper.read(Types.BOOLEAN);
         boolean canDelete = (Boolean)wrapper.read(Types.BOOLEAN);
         if (canPlace) {
            blockPermissionsStorage.addPlaceable(blockId);
         } else {
            blockPermissionsStorage.removePlaceable(blockId);
         }

         if (canDelete) {
            blockPermissionsStorage.addBreakable(blockId);
         } else {
            blockPermissionsStorage.removeBreakable(blockId);
         }

      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXT_BULK_BLOCK_UPDATE, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         ClassicLevelStorage levelStorage = (ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class);
         if (levelStorage != null && levelStorage.hasReceivedLevel()) {
            ClassicBlockRemapper remapper = (ClassicBlockRemapper)wrapper.user().get(ClassicBlockRemapper.class);
            ClassicLevel level = levelStorage.getClassicLevel();
            int count = (Short)wrapper.read(Types.UNSIGNED_BYTE) + 1;
            byte[] indices = (byte[])wrapper.read(new FixedByteArrayType(1024));
            byte[] blocks = (byte[])wrapper.read(new FixedByteArrayType(256));
            if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocola1_0_15Toa1_0_16_2.class)) {
               Map<ChunkCoord, List<BlockChangeRecord>> records = new HashMap();

               for(int i = 0; i < count; ++i) {
                  int index = (indices[i * 4] & 255) << 24 | (indices[i * 4 + 1] & 255) << 16 | (indices[i * 4 + 2] & 255) << 8 | indices[i * 4 + 3] & 255;
                  BlockPosition pos = new BlockPosition(index % level.getSizeX(), index / level.getSizeX() / level.getSizeZ(), index / level.getSizeX() % level.getSizeZ());
                  byte blockId = blocks[i];
                  level.setBlock(pos, blockId);
                  if (levelStorage.isChunkLoaded(pos)) {
                     IdAndData mappedBlock = (IdAndData)remapper.mapper().get(blockId);
                     ((List)records.computeIfAbsent(new ChunkCoord(pos.x() >> 4, pos.z() >> 4), (k) -> new ArrayList())).add(new BlockChangeRecord1_8(pos.x() & 15, pos.y(), pos.z() & 15, mappedBlock.toRawData()));
                  }
               }

               for(Map.Entry entry : records.entrySet()) {
                  PacketWrapper multiBlockChange = PacketWrapper.create(ClientboundPacketsa1_0_15.CHUNK_BLOCKS_UPDATE, (UserConnection)wrapper.user());
                  multiBlockChange.write(Types.INT, ((ChunkCoord)entry.getKey()).chunkX);
                  multiBlockChange.write(Types.INT, ((ChunkCoord)entry.getKey()).chunkZ);
                  multiBlockChange.write(Types1_1.BLOCK_CHANGE_RECORD_ARRAY, (BlockChangeRecord[])((List)entry.getValue()).toArray(new BlockChangeRecord[0]));
                  multiBlockChange.send(Protocola1_0_15Toa1_0_16_2.class);
               }
            }

         }
      });
      this.registerClientbound(ClientboundPacketsc0_30cpe.EXT_TWO_WAY_PING, ClientboundPacketsc0_28.KEEP_ALIVE, (wrapper) -> {
         byte direction = (Byte)wrapper.read(Types.BYTE);
         short data = (Short)wrapper.read(Types.SHORT);
         if (direction == 1) {
            PacketWrapper pingResponse = PacketWrapper.create(ServerboundPacketsc0_30cpe.EXT_TWO_WAY_PING, (UserConnection)wrapper.user());
            pingResponse.write(Types.BYTE, direction);
            pingResponse.write(Types.SHORT, data);
            pingResponse.sendToServer(Protocolc0_30cpeToc0_28_30.class);
         }

      });
      this.registerServerbound(ServerboundPacketsc0_28.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Typesc0_30.STRING);
            this.map(Typesc0_30.STRING);
            this.map(Types.BYTE);
            this.handler((wrapper) -> wrapper.set(Types.BYTE, 1, (byte)66));
         }
      });
      this.registerServerbound(ServerboundPacketsc0_28.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Typesc0_30.STRING);
            this.handler((wrapper) -> {
               ExtensionProtocolMetadataStorage protocolMetadata = (ExtensionProtocolMetadataStorage)wrapper.user().get(ExtensionProtocolMetadataStorage.class);
               if (protocolMetadata.hasServerExtension(ClassicProtocolExtension.LONGER_MESSAGES, 1)) {
                  wrapper.cancel();
                  String message = (String)wrapper.get(Typesc0_30.STRING, 0);

                  while(!message.isEmpty()) {
                     int pos = Math.min(message.length(), 64);
                     String msg = message.substring(0, pos);
                     message = message.substring(pos);
                     PacketWrapper chatMessage = PacketWrapper.create(ServerboundPacketsc0_30cpe.CHAT, (UserConnection)wrapper.user());
                     chatMessage.write(Types.BYTE, (byte)(!message.isEmpty() ? 1 : 0));
                     chatMessage.write(Typesc0_30.STRING, msg);
                     chatMessage.sendToServer(Protocolc0_30cpeToc0_28_30.class);
                  }

               }
            });
         }
      });
      this.registerServerbound(ServerboundPacketsc0_28.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Typesc0_30.BLOCK_POSITION);
            this.map(Types.BOOLEAN);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               if (wrapper.user().has(ExtBlockPermissionsStorage.class)) {
                  ExtBlockPermissionsStorage blockPermissions = (ExtBlockPermissionsStorage)wrapper.user().get(ExtBlockPermissionsStorage.class);
                  ClassicLevel level = ((ClassicLevelStorage)wrapper.user().get(ClassicLevelStorage.class)).getClassicLevel();
                  BlockPosition position = (BlockPosition)wrapper.get(Typesc0_30.BLOCK_POSITION, 0);
                  boolean placeBlock = (Boolean)wrapper.get(Types.BOOLEAN, 0);
                  int blockId = (Byte)wrapper.get(Types.BYTE, 0);
                  int block = level.getBlock(position);
                  boolean disallow = placeBlock && blockPermissions.isPlacingDenied(blockId) || !placeBlock && blockPermissions.isBreakingDenied(block);
                  if (disallow) {
                     wrapper.cancel();
                     PacketWrapper chatMessage = PacketWrapper.create(ClientboundPacketsc0_30cpe.CHAT, (UserConnection)wrapper.user());
                     chatMessage.write(Types.BYTE, (byte)0);
                     chatMessage.write(Typesc0_30.STRING, "&cYou are not allowed to place/break this block");
                     chatMessage.send(Protocolc0_30cpeToc0_28_30.class);
                  } else {
                     block = placeBlock ? blockId : 0;
                     level.setBlock(position, block);
                  }

                  PacketWrapper blockChange = PacketWrapper.create(ClientboundPacketsc0_30cpe.BLOCK_UPDATE, (UserConnection)wrapper.user());
                  blockChange.write(Typesc0_30.BLOCK_POSITION, position);
                  blockChange.write(Types.BYTE, (byte)block);
                  blockChange.send(Protocolc0_30cpeToc0_28_30.class);
               }
            });
         }
      });
   }

   public void register(ViaProviders providers) {
      Via.getPlatform().runRepeatingSync(new ClassicPingTask(), 20L);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolc0_30cpeToc0_28_30.class, ClientboundPacketsc0_30cpe::getPacket));
      userConnection.put(new ExtensionProtocolMetadataStorage());
      userConnection.put(new ClassicOpLevelStorage(userConnection, true));
      ClassicBlockRemapper previousRemapper = (ClassicBlockRemapper)userConnection.get(ClassicBlockRemapper.class);
      userConnection.put(new ClassicBlockRemapper((i) -> {
         if (ClassicBlocks.MAPPING.containsKey(i)) {
            return (IdAndData)previousRemapper.mapper().get(i);
         } else {
            ExtensionProtocolMetadataStorage extensionProtocol = (ExtensionProtocolMetadataStorage)userConnection.get(ExtensionProtocolMetadataStorage.class);
            return extensionProtocol.hasServerExtension(ClassicProtocolExtension.CUSTOM_BLOCKS, 1) ? (IdAndData)ExtendedClassicBlocks.MAPPING.get(i) : new IdAndData(BlockList1_6.stone.blockId(), 0);
         }
      }, (o) -> {
         if (ClassicBlocks.REVERSE_MAPPING.containsKey(o)) {
            return previousRemapper.reverseMapper().getInt(o);
         } else {
            ExtensionProtocolMetadataStorage extensionProtocol = (ExtensionProtocolMetadataStorage)userConnection.get(ExtensionProtocolMetadataStorage.class);
            return extensionProtocol.hasServerExtension(ClassicProtocolExtension.CUSTOM_BLOCKS, 1) ? ExtendedClassicBlocks.REVERSE_MAPPING.getInt(o) : 1;
         }
      }));
   }
}
