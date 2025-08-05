package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.api.type.chunk.BulkChunkType1_7_6;
import com.viaversion.viarewind.api.type.chunk.ChunkType1_7_6;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.Particles1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.WorldBorderEmulator;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.FixedByteArrayType;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.IdAndData;

public class WorldPacketRewriter1_8 extends RewriterBase {
   public WorldPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_CHUNK, (wrapper) -> {
         ClientWorld world = wrapper.user().getClientWorld(Protocol1_8To1_7_6_10.class);
         Chunk chunk = (Chunk)wrapper.read(ChunkType1_8.forEnvironment(world.getEnvironment()));
         ((Protocol1_8To1_7_6_10)this.protocol).getItemRewriter().handleChunk(chunk);
         wrapper.write(ChunkType1_7_6.TYPE, chunk);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_UPDATE, new PacketHandlers() {
         protected void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.U_BYTE_POSITION);
            this.handler((wrapper) -> {
               int data = (Integer)wrapper.read(Types.VAR_INT);
               data = ((Protocol1_8To1_7_6_10)WorldPacketRewriter1_8.this.protocol).getItemRewriter().handleBlockId(data);
               wrapper.write(Types.VAR_INT, IdAndData.getId(data));
               wrapper.write(Types.UNSIGNED_BYTE, (short)IdAndData.getData(data));
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CHUNK_BLOCKS_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               BlockChangeRecord[] records = (BlockChangeRecord[])wrapper.read(Types.BLOCK_CHANGE_ARRAY);
               wrapper.write(Types.SHORT, (short)records.length);
               wrapper.write(Types.INT, records.length * 4);

               for(BlockChangeRecord record : records) {
                  wrapper.write(Types.SHORT, (short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY()));
                  wrapper.write(Types.SHORT, (short)((Protocol1_8To1_7_6_10)WorldPacketRewriter1_8.this.protocol).getItemRewriter().handleBlockId(record.getBlockId()));
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_DESTRUCTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
            this.map(Types.BYTE);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, (wrapper) -> {
         Chunk[] chunks = (Chunk[])wrapper.read(BulkChunkType1_8.TYPE);

         for(Chunk chunk : chunks) {
            ((Protocol1_8To1_7_6_10)this.protocol).getItemRewriter().handleChunk(chunk);
         }

         wrapper.write(BulkChunkType1_7_6.TYPE, chunks);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.BYTE_POSITION);
            this.map(Types.INT);
            this.map(Types.BOOLEAN);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LEVEL_PARTICLES, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               int particleId = (Integer)wrapper.read(Types.INT);
               Particles1_8 particle = Particles1_8.find(particleId);
               if (particle == null) {
                  particle = Particles1_8.CRIT;
               }

               wrapper.write(Types.STRING, particle.name);
            });
            this.read(Types.BOOLEAN);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               String name = (String)wrapper.get(Types.STRING, 0);
               Particles1_8 particle = Particles1_8.find(name);
               if (particle == Particles1_8.ICON_CRACK || particle == Particles1_8.BLOCK_CRACK || particle == Particles1_8.BLOCK_DUST) {
                  int id = (Integer)wrapper.read(Types.VAR_INT);
                  int data = particle == Particles1_8.ICON_CRACK ? (Integer)wrapper.read(Types.VAR_INT) : id / 4096;
                  id %= 4096;
                  if ((id < 256 || id > 422) && (id < 2256 || id > 2267)) {
                     if ((id < 0 || id > 164) && (id < 170 || id > 175)) {
                        wrapper.cancel();
                        return;
                     }

                     if (particle == Particles1_8.ICON_CRACK) {
                        particle = Particles1_8.BLOCK_CRACK;
                     }
                  } else {
                     particle = Particles1_8.ICON_CRACK;
                  }

                  String var6 = particle.name;
                  name = var6 + "_" + id + "_" + data;
               }

               wrapper.set(Types.STRING, 0, name);
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  String line = (String)wrapper.read(Types.STRING);
                  line = ChatUtil.jsonToLegacy(line);
                  line = ChatUtil.removeUnusedColor(line, '0');
                  if (line.length() > 15) {
                     line = ChatColorUtil.stripColor(line);
                     if (line.length() > 15) {
                        line = line.substring(0, 15);
                     }
                  }

                  wrapper.write(Types.STRING, line);
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MAP_ITEM_DATA, (wrapper) -> {
         wrapper.cancel();
         int id = (Integer)wrapper.read(Types.VAR_INT);
         byte scale = (Byte)wrapper.read(Types.BYTE);
         int iconCount = (Integer)wrapper.read(Types.VAR_INT);
         byte[] icons = new byte[iconCount * 4];

         for(int i = 0; i < iconCount; ++i) {
            int directionAndType = (Byte)wrapper.read(Types.BYTE);
            icons[i * 4] = (byte)(directionAndType >> 4 & 15);
            icons[i * 4 + 1] = (Byte)wrapper.read(Types.BYTE);
            icons[i * 4 + 2] = (Byte)wrapper.read(Types.BYTE);
            icons[i * 4 + 3] = (byte)(directionAndType & 15);
         }

         short columns = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         if (columns > 0) {
            short rows = (Short)wrapper.read(Types.UNSIGNED_BYTE);
            short x = (Short)wrapper.read(Types.UNSIGNED_BYTE);
            short z = (Short)wrapper.read(Types.UNSIGNED_BYTE);
            byte[] data = (byte[])wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);

            for(int column = 0; column < columns; ++column) {
               byte[] columnData = new byte[rows + 3];
               columnData[0] = 0;
               columnData[1] = (byte)(x + column);
               columnData[2] = (byte)z;

               for(int i = 0; i < rows; ++i) {
                  columnData[i + 3] = data[column + i * columns];
               }

               PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, (UserConnection)wrapper.user());
               mapData.write(Types.VAR_INT, id);
               mapData.write(Types.SHORT, (short)columnData.length);
               mapData.write(new FixedByteArrayType(columnData.length), columnData);
               mapData.send(Protocol1_8To1_7_6_10.class);
            }
         }

         if (iconCount > 0) {
            byte[] iconData = new byte[iconCount * 3 + 1];
            iconData[0] = 1;

            for(int i = 0; i < iconCount; ++i) {
               iconData[i * 3 + 1] = (byte)(icons[i * 4] << 4 | icons[i * 4 + 3] & 15);
               iconData[i * 3 + 2] = icons[i * 4 + 1];
               iconData[i * 3 + 3] = icons[i * 4 + 2];
            }

            PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, (UserConnection)wrapper.user());
            mapData.write(Types.VAR_INT, id);
            mapData.write(Types.SHORT, (short)iconData.length);
            mapData.write(new FixedByteArrayType(iconData.length), iconData);
            mapData.send(Protocol1_8To1_7_6_10.class);
         }

         PacketWrapper mapData = PacketWrapper.create(ClientboundPackets1_8.MAP_ITEM_DATA, (UserConnection)wrapper.user());
         mapData.write(Types.VAR_INT, id);
         mapData.write(Types.SHORT, Short.valueOf((short)2));
         mapData.write(new FixedByteArrayType(2), new byte[]{2, scale});
         mapData.send(Protocol1_8To1_7_6_10.class);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.SHORT_POSITION);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.NAMED_COMPOUND_TAG, RewindTypes.COMPRESSED_NBT);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.CHANGE_DIFFICULTY);
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_BORDER, (ClientboundPacketType)null, (wrapper) -> {
         WorldBorderEmulator emulator = (WorldBorderEmulator)wrapper.user().get(WorldBorderEmulator.class);
         wrapper.cancel();
         int action = (Integer)wrapper.read(Types.VAR_INT);
         if (action == 0) {
            emulator.setSize((Double)wrapper.read(Types.DOUBLE));
         } else if (action == 1) {
            emulator.lerpSize((Double)wrapper.read(Types.DOUBLE), (Double)wrapper.read(Types.DOUBLE), (Long)wrapper.read(Types.VAR_LONG));
         } else if (action == 2) {
            emulator.setCenter((Double)wrapper.read(Types.DOUBLE), (Double)wrapper.read(Types.DOUBLE));
         } else if (action == 3) {
            emulator.init((Double)wrapper.read(Types.DOUBLE), (Double)wrapper.read(Types.DOUBLE), (Double)wrapper.read(Types.DOUBLE), (Double)wrapper.read(Types.DOUBLE), (Long)wrapper.read(Types.VAR_LONG));
         }

      });
   }
}
