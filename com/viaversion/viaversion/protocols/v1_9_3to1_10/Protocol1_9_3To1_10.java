package com.viaversion.viaversion.protocols.v1_9_3to1_10;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.rewriter.ItemPacketRewriter1_10;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.storage.ResourcePackTracker;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Protocol1_9_3To1_10 extends AbstractProtocol {
   public static final ValueTransformer TO_NEW_PITCH;
   public static final ValueTransformer TRANSFORM_ENTITY_DATA;
   final ItemPacketRewriter1_10 itemRewriter = new ItemPacketRewriter1_10(this);

   public Protocol1_9_3To1_10() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   protected void registerPackets() {
      this.itemRewriter.register();
      this.registerClientbound(ClientboundPackets1_9_3.CUSTOM_SOUND, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.FLOAT);
            this.map(Types.UNSIGNED_BYTE, Protocol1_9_3To1_10.TO_NEW_PITCH);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.FLOAT);
            this.map(Types.UNSIGNED_BYTE, Protocol1_9_3To1_10.TO_NEW_PITCH);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.VAR_INT, 0);
               wrapper.set(Types.VAR_INT, 0, Protocol1_9_3To1_10.this.getNewSoundId(id));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SET_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types1_9.ENTITY_DATA_LIST, Protocol1_9_3To1_10.TRANSFORM_ENTITY_DATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.map(Types1_9.ENTITY_DATA_LIST, Protocol1_9_3To1_10.TRANSFORM_ENTITY_DATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.UUID);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types1_9.ENTITY_DATA_LIST, Protocol1_9_3To1_10.TRANSFORM_ENTITY_DATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
               int dimensionId = (Integer)wrapper.get(Types.INT, 1);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
               int dimensionId = (Integer)wrapper.get(Types.INT, 0);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, (wrapper) -> {
         ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_9_3To1_10.class);
         Chunk chunk = (Chunk)wrapper.passthrough(ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment()));
         if (Via.getConfig().isReplacePistons()) {
            int replacementId = Via.getConfig().getPistonReplacementId();

            for(ChunkSection section : chunk.getSections()) {
               if (section != null) {
                  section.palette(PaletteType.BLOCKS).replaceId(36, replacementId);
               }
            }
         }

      });
      this.registerClientbound(ClientboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
               tracker.setLastHash((String)wrapper.get(Types.STRING, 1));
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               ResourcePackTracker tracker = (ResourcePackTracker)wrapper.user().get(ResourcePackTracker.class);
               wrapper.write(Types.STRING, tracker.getLastHash());
               wrapper.write(Types.VAR_INT, (Integer)wrapper.read(Types.VAR_INT));
            });
         }
      });
   }

   public int getNewSoundId(int id) {
      int newId = id;
      if (id >= 24) {
         newId = id + 1;
      }

      if (id >= 248) {
         newId += 4;
      }

      if (id >= 296) {
         newId += 6;
      }

      if (id >= 354) {
         newId += 4;
      }

      if (id >= 372) {
         newId += 4;
      }

      return newId;
   }

   public void init(UserConnection userConnection) {
      userConnection.addClientWorld(this.getClass(), new ClientWorld());
      userConnection.put(new ResourcePackTracker());
   }

   public ItemPacketRewriter1_10 getItemRewriter() {
      return this.itemRewriter;
   }

   static {
      TO_NEW_PITCH = new ValueTransformer(Types.FLOAT) {
         public Float transform(PacketWrapper wrapper, Short inputValue) {
            return (float)inputValue / 63.0F;
         }
      };
      TRANSFORM_ENTITY_DATA = new ValueTransformer(Types1_9.ENTITY_DATA_LIST) {
         public List transform(PacketWrapper wrapper, List inputValue) {
            List<EntityData> dataList = new CopyOnWriteArrayList(inputValue);

            for(EntityData data : dataList) {
               if (data.id() >= 5) {
                  data.setId(data.id() + 1);
               }
            }

            return dataList;
         }
      };
   }
}
