package com.viaversion.viaversion.protocols.v1_9_1to1_9_3;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.data.FakeTileEntities1_9_1;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import java.util.List;

public class Protocol1_9_1To1_9_3 extends AbstractProtocol {
   public static final ValueTransformer ADJUST_PITCH;

   public Protocol1_9_1To1_9_3() {
      super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, (ClientboundPacketType)null, (wrapper) -> {
         BlockPosition position = (BlockPosition)wrapper.read(Types.BLOCK_POSITION1_8);
         JsonElement[] lines = new JsonElement[4];

         for(int i = 0; i < 4; ++i) {
            lines[i] = (JsonElement)wrapper.read(Types.COMPONENT);
         }

         wrapper.clearInputBuffer();
         wrapper.setPacketType(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA);
         wrapper.write(Types.BLOCK_POSITION1_8, position);
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)9));
         CompoundTag tag = new CompoundTag();
         tag.put("id", new StringTag("Sign"));
         tag.put("x", new IntTag(position.x()));
         tag.put("y", new IntTag(position.y()));
         tag.put("z", new IntTag(position.z()));

         for(int i = 0; i < lines.length; ++i) {
            int var6 = i + 1;
            tag.put("Text" + var6, new StringTag(lines[i].toString()));
         }

         wrapper.write(Types.NAMED_COMPOUND_TAG, tag);
      });
      this.registerClientbound(ClientboundPackets1_9.LEVEL_CHUNK, (wrapper) -> {
         ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_1To1_9_3.class);
         Chunk chunk = (Chunk)wrapper.read(ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment()));
         wrapper.write(ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment()), chunk);
         List<CompoundTag> tags = chunk.getBlockEntities();

         for(int s = 0; s < chunk.getSections().length; ++s) {
            ChunkSection section = chunk.getSections()[s];
            if (section != null) {
               DataPalette blocks = section.palette(PaletteType.BLOCKS);

               for(int idx = 0; idx < 4096; ++idx) {
                  int id = blocks.idAt(idx) >> 4;
                  if (FakeTileEntities1_9_1.isTileEntity(id)) {
                     tags.add(FakeTileEntities1_9_1.createTileEntity(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4), ChunkSection.yFromIndex(idx) + (s << 4), ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4), id));
                  }
               }
            }
         }

      });
      this.registerClientbound(ClientboundPackets1_9.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_1To1_9_3.class);
               int dimensionId = (Integer)wrapper.get(Types.INT, 1);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_1To1_9_3.class);
               int dimensionId = (Integer)wrapper.get(Types.INT, 0);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.FLOAT);
            this.map(Protocol1_9_1To1_9_3.ADJUST_PITCH);
         }
      });
   }

   public void init(UserConnection user) {
      user.addClientWorld(this.getClass(), new ClientWorld());
   }

   static {
      ADJUST_PITCH = new ValueTransformer(Types.UNSIGNED_BYTE, Types.UNSIGNED_BYTE) {
         public Short transform(PacketWrapper wrapper, Short inputValue) {
            return (short)Math.round((float)inputValue / 63.5F * 63.0F);
         }
      };
   }
}
