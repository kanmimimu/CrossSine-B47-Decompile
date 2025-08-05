package net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.packet.ClientboundPacketsb1_5;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.packet.ServerboundPacketsb1_5;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.storage.WorldTimeStorage;
import net.raphimc.vialegacy.protocol.beta.b1_5_0_2tob1_6_0_6.task.TimeTrackTask;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ClientboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ServerboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.PlayerInfoStorage;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_5_0_2Tob1_6_0_6 extends StatelessProtocol {
   public Protocolb1_5_0_2Tob1_6_0_6() {
      super(ClientboundPacketsb1_5.class, ClientboundPacketsb1_7.class, ServerboundPacketsb1_5.class, ServerboundPacketsb1_7.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsb1_5.SET_TIME, new PacketHandlers() {
         public void register() {
            this.map(Types.LONG);
            this.handler((wrapper) -> ((WorldTimeStorage)wrapper.user().get(WorldTimeStorage.class)).time = (Long)wrapper.get(Types.LONG, 0));
         }
      });
      this.registerClientbound(ClientboundPacketsb1_5.RESPAWN, new PacketHandlers() {
         public void register() {
            this.create(Types.BYTE, (byte)0);
         }
      });
      this.registerClientbound(ClientboundPacketsb1_5.ADD_ENTITY, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.create(Types.INT, 0);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_7.RESPAWN, new PacketHandlers() {
         public void register() {
            this.read(Types.BYTE);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_7.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_4_2.NBTLESS_ITEM);
            this.handler((wrapper) -> {
               PlayerInfoStorage playerInfoStorage = (PlayerInfoStorage)wrapper.user().get(PlayerInfoStorage.class);
               BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
               IdAndData block = ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).getBlockNotNull(pos);
               Item item = (Item)wrapper.get(Types1_4_2.NBTLESS_ITEM, 0);
               if (block.getId() == BlockList1_6.bed.blockId()) {
                  byte[][] headBlockToFootBlock = new byte[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
                  boolean isFoot = (block.getData() & 8) != 0;
                  if (!isFoot) {
                     int bedDirection = block.getData() & 3;
                     pos = new BlockPosition(pos.x() + headBlockToFootBlock[bedDirection][0], pos.y(), pos.z() + headBlockToFootBlock[bedDirection][1]);
                     block = ((ChunkTracker)wrapper.user().get(ChunkTracker.class)).getBlockNotNull(pos);
                     if (block.getId() != BlockList1_6.bed.blockId()) {
                        return;
                     }
                  }

                  boolean isOccupied = (block.getData() & 4) != 0;
                  if (isOccupied) {
                     PacketWrapper chat = PacketWrapper.create(ClientboundPacketsb1_7.CHAT, (UserConnection)wrapper.user());
                     chat.write(Types1_6_4.STRING, "This bed is occupied");
                     chat.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                     return;
                  }

                  int dayTime = (int)(((WorldTimeStorage)wrapper.user().get(WorldTimeStorage.class)).time % 24000L);
                  float dayPercent = ((float)dayTime + 1.0F) / 24000.0F - 0.25F;
                  if (dayPercent < 0.0F) {
                     ++dayPercent;
                  }

                  if (dayPercent > 1.0F) {
                     --dayPercent;
                  }

                  float var18 = 1.0F - (float)((Math.cos((double)dayPercent * Math.PI) + (double)1.0F) / (double)2.0F);
                  dayPercent += (var18 - dayPercent) / 3.0F;
                  float skyRotation = (float)((double)1.0F - (Math.cos((double)dayPercent * Math.PI * (double)2.0F) * (double)2.0F + (double)0.5F));
                  if (skyRotation < 0.0F) {
                     skyRotation = 0.0F;
                  }

                  if (skyRotation > 1.0F) {
                     skyRotation = 1.0F;
                  }

                  boolean isDayTime = (int)(skyRotation * 11.0F) < 4;
                  if (isDayTime) {
                     PacketWrapper chat = PacketWrapper.create(ClientboundPacketsb1_7.CHAT, (UserConnection)wrapper.user());
                     chat.write(Types1_6_4.STRING, "You can only sleep at night");
                     chat.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                     return;
                  }

                  if (Math.abs(playerInfoStorage.posX - (double)pos.x()) > (double)3.0F || Math.abs(playerInfoStorage.posY - (double)pos.y()) > (double)2.0F || Math.abs(playerInfoStorage.posZ - (double)pos.z()) > (double)3.0F) {
                     return;
                  }

                  PacketWrapper useBed = PacketWrapper.create(ClientboundPacketsb1_7.PLAYER_SLEEP, (UserConnection)wrapper.user());
                  useBed.write(Types.INT, playerInfoStorage.entityId);
                  useBed.write(Types.BYTE, (byte)0);
                  useBed.write(Types1_7_6.BLOCK_POSITION_BYTE, pos);
                  useBed.send(Protocolb1_5_0_2Tob1_6_0_6.class);
               } else if (block.getId() == BlockList1_6.jukebox.blockId()) {
                  if (block.getData() > 0) {
                     PacketWrapper effect = PacketWrapper.create(ClientboundPacketsb1_7.LEVEL_EVENT, (UserConnection)wrapper.user());
                     effect.write(Types.INT, 1005);
                     effect.write(Types1_7_6.BLOCK_POSITION_UBYTE, pos);
                     effect.write(Types.INT, 0);
                     effect.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                  } else if (item != null && (item.identifier() == ItemList1_6.record13.itemId() || item.identifier() == ItemList1_6.recordCat.itemId())) {
                     PacketWrapper effect = PacketWrapper.create(ClientboundPacketsb1_7.LEVEL_EVENT, (UserConnection)wrapper.user());
                     effect.write(Types.INT, 1005);
                     effect.write(Types1_7_6.BLOCK_POSITION_UBYTE, pos);
                     effect.write(Types.INT, item.identifier());
                     effect.send(Protocolb1_5_0_2Tob1_6_0_6.class);
                  }
               }

            });
         }
      });
   }

   public void register(ViaProviders providers) {
      Via.getPlatform().runRepeatingSync(new TimeTrackTask(), 1L);
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolb1_5_0_2Tob1_6_0_6.class, ClientboundPacketsb1_5::getPacket));
      userConnection.put(new WorldTimeStorage());
   }
}
