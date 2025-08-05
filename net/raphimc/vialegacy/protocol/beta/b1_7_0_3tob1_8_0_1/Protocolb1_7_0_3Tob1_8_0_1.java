package net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.AlphaInventoryTracker;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ClientboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.packet.ServerboundPacketsb1_7;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.storage.PlayerHealthTracker;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.storage.PlayerNameTracker;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ClientboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ServerboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.types.Typesb1_8_0_1;
import net.raphimc.vialegacy.protocol.classic.c0_0_20a_27toc0_28_30.Protocolc0_0_20a_27Toc0_28_30;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.LegacyNibbleArray;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.storage.SeedStorage;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.Types1_1;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.storage.EntityTracker;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolb1_7_0_3Tob1_8_0_1 extends StatelessProtocol {
   public Protocolb1_7_0_3Tob1_8_0_1() {
      super(ClientboundPacketsb1_7.class, ClientboundPacketsb1_8.class, ServerboundPacketsb1_7.class, ServerboundPacketsb1_8.class);
   }

   protected void registerPackets() {
      this.registerClientbound(ClientboundPacketsb1_7.KEEP_ALIVE, (wrapper) -> wrapper.write(Types.INT, ThreadLocalRandom.current().nextInt(1, 32767)));
      this.registerClientbound(ClientboundPacketsb1_7.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_6_4.STRING);
            this.map(Types.LONG);
            this.create(Types.INT, 0);
            this.map(Types.BYTE);
            this.create(Types.BYTE, (byte)1);
            this.create(Types.BYTE, -128);
            this.create(Types.BYTE, (byte)100);
            this.handler((wrapper) -> {
               PacketWrapper playerListEntry = PacketWrapper.create(ClientboundPacketsb1_8.PLAYER_INFO, (UserConnection)wrapper.user());
               playerListEntry.write(Types1_6_4.STRING, wrapper.user().getProtocolInfo().getUsername());
               playerListEntry.write(Types.BOOLEAN, true);
               playerListEntry.write(Types.SHORT, Short.valueOf((short)0));
               PacketWrapper updateHealth = PacketWrapper.create(ClientboundPacketsb1_7.SET_HEALTH, (UserConnection)wrapper.user());
               updateHealth.write(Types.SHORT, Short.valueOf((short)20));
               wrapper.send(Protocolb1_7_0_3Tob1_8_0_1.class);
               wrapper.cancel();
               playerListEntry.send(Protocolb1_7_0_3Tob1_8_0_1.class);
               updateHealth.send(Protocolb1_7_0_3Tob1_8_0_1.class, false);
            });
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.SET_HEALTH, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.create(Types.SHORT, Short.valueOf((short)6));
            this.create(Types.FLOAT, 0.0F);
            this.handler((wrapper) -> ((PlayerHealthTracker)wrapper.user().get(PlayerHealthTracker.class)).setHealth((Short)wrapper.get(Types.SHORT, 0)));
            this.handler((wrapper) -> {
               if (ViaLegacy.getConfig().enableB1_7_3Sprinting()) {
                  wrapper.set(Types.SHORT, 1, Short.valueOf((short)20));
                  wrapper.set(Types.FLOAT, 0, 0.0F);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.create(Types.BYTE, (byte)1);
            this.create(Types.BYTE, (byte)0);
            this.create(Types.SHORT, (short)128);
            this.handler((wrapper) -> wrapper.write(Types.LONG, ((SeedStorage)wrapper.user().get(SeedStorage.class)).seed));
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.ADD_PLAYER, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_6_4.STRING);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.UNSIGNED_SHORT);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               PlayerNameTracker playerNameTracker = (PlayerNameTracker)wrapper.user().get(PlayerNameTracker.class);
               playerNameTracker.names.put(entityId, (String)wrapper.get(Types1_6_4.STRING, 0));
               PacketWrapper playerListEntry = PacketWrapper.create(ClientboundPacketsb1_8.PLAYER_INFO, (UserConnection)wrapper.user());
               playerListEntry.write(Types1_6_4.STRING, (String)playerNameTracker.names.get(entityId));
               playerListEntry.write(Types.BOOLEAN, true);
               playerListEntry.write(Types.SHORT, Short.valueOf((short)0));
               playerListEntry.send(Protocolb1_7_0_3Tob1_8_0_1.class);
            });
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.ADD_MOB, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types1_3_1.ENTITY_DATA_LIST);
            this.handler((wrapper) -> {
               short entityType = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (entityType == 49) {
                  PacketWrapper spawnMonster = PacketWrapper.create(ClientboundPacketsb1_8.ADD_PLAYER, (UserConnection)wrapper.user());
                  spawnMonster.write(Types.INT, (Integer)wrapper.get(Types.INT, 0));
                  spawnMonster.write(Types1_6_4.STRING, "Monster");
                  spawnMonster.write(Types.INT, (Integer)wrapper.get(Types.INT, 1));
                  spawnMonster.write(Types.INT, (Integer)wrapper.get(Types.INT, 2));
                  spawnMonster.write(Types.INT, (Integer)wrapper.get(Types.INT, 3));
                  spawnMonster.write(Types.BYTE, (Byte)wrapper.get(Types.BYTE, 0));
                  spawnMonster.write(Types.BYTE, (Byte)wrapper.get(Types.BYTE, 1));
                  spawnMonster.write(Types.UNSIGNED_SHORT, 0);
                  PacketWrapper setEntityData = PacketWrapper.create(ClientboundPacketsb1_8.SET_ENTITY_DATA, (UserConnection)wrapper.user());
                  setEntityData.write(Types.INT, (Integer)wrapper.get(Types.INT, 0));
                  setEntityData.write(Types1_3_1.ENTITY_DATA_LIST, (List)wrapper.get(Types1_3_1.ENTITY_DATA_LIST, 0));
                  wrapper.cancel();
                  spawnMonster.send(Protocolb1_7_0_3Tob1_8_0_1.class);
                  setEntityData.send(Protocolb1_7_0_3Tob1_8_0_1.class);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.REMOVE_ENTITIES, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               PlayerNameTracker playerNameTracker = (PlayerNameTracker)wrapper.user().get(PlayerNameTracker.class);
               String name = (String)playerNameTracker.names.get((Integer)wrapper.get(Types.INT, 0));
               if (name != null) {
                  PacketWrapper playerListEntry = PacketWrapper.create(ClientboundPacketsb1_8.PLAYER_INFO, (UserConnection)wrapper.user());
                  playerListEntry.write(Types1_6_4.STRING, name);
                  playerListEntry.write(Types.BOOLEAN, false);
                  playerListEntry.write(Types.SHORT, Short.valueOf((short)0));
                  playerListEntry.send(Protocolb1_7_0_3Tob1_8_0_1.class);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.LEVEL_CHUNK, (wrapper) -> {
         Chunk chunk = (Chunk)wrapper.passthrough(Types1_1.CHUNK);
         boolean hasChest = false;

         for(ChunkSection section : chunk.getSections()) {
            if (section != null && section.getLight().hasSkyLight()) {
               for(int i = 0; i < section.palette(PaletteType.BLOCKS).size(); ++i) {
                  if (section.palette(PaletteType.BLOCKS).idByIndex(i) >> 4 == BlockList1_6.chest.blockId()) {
                     hasChest = true;
                     break;
                  }
               }

               if (hasChest) {
                  LegacyNibbleArray sectionSkyLight = new LegacyNibbleArray(section.getLight().getSkyLight(), 4);

                  for(int y = 0; y < 16; ++y) {
                     for(int x = 0; x < 16; ++x) {
                        for(int z = 0; z < 16; ++z) {
                           if (section.palette(PaletteType.BLOCKS).idAt(x, y, z) >> 4 == BlockList1_6.chest.blockId()) {
                              sectionSkyLight.set(x, y, z, 15);
                           }
                        }
                     }
                  }
               }
            }
         }

      });
      this.registerClientbound(ClientboundPacketsb1_7.GAME_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.create(Types.BYTE, (byte)0);
         }
      });
      this.registerClientbound(ClientboundPacketsb1_7.OPEN_SCREEN, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Typesb1_7_0_3.STRING, Types1_6_4.STRING);
            this.map(Types.UNSIGNED_BYTE);
         }
      });
      this.registerServerbound(State.PLAY, ServerboundPacketsb1_8.SERVER_PING.getId(), -2, (wrapper) -> {
         if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolc0_0_20a_27Toc0_28_30.class)) {
            wrapper.clearPacket();
         } else {
            wrapper.cancel();
         }

         PacketWrapper pingResponse = PacketWrapper.create(ClientboundPacketsb1_8.DISCONNECT, (UserConnection)wrapper.user());
         pingResponse.write(Types1_6_4.STRING, "The server seems to be running!\nWait 5 seconds between each connection§0§1");
         pingResponse.send(Protocolb1_7_0_3Tob1_8_0_1.class);
      });
      this.registerServerbound(ServerboundPacketsb1_8.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types1_6_4.STRING);
            this.map(Types.LONG);
            this.read(Types.INT);
            this.map(Types.BYTE);
            this.read(Types.BYTE);
            this.read(Types.BYTE);
            this.read(Types.BYTE);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.read(Types.BYTE);
            this.read(Types.BYTE);
            this.read(Types.SHORT);
            this.read(Types.LONG);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.PLAYER_ACTION, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               short status = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (status == 5) {
                  wrapper.cancel();
               }

            });
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types1_4_2.NBTLESS_ITEM);
            this.handler((wrapper) -> {
               if ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0) == 255) {
                  Item item = (Item)wrapper.get(Types1_4_2.NBTLESS_ITEM, 0);
                  if (item != null && Protocolb1_7_0_3Tob1_8_0_1.this.isSword(item)) {
                     wrapper.cancel();
                     PacketWrapper entityStatus = PacketWrapper.create(ClientboundPacketsb1_8.ENTITY_EVENT, (UserConnection)wrapper.user());
                     entityStatus.write(Types.INT, ((EntityTracker)wrapper.user().get(EntityTracker.class)).getPlayerID());
                     entityStatus.write(Types.BYTE, (byte)9);
                     entityStatus.send(Protocolb1_7_0_3Tob1_8_0_1.class);
                  }
               } else {
                  BlockPosition pos = (BlockPosition)wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0);
                  if (((ChunkTracker)wrapper.user().get(ChunkTracker.class)).getBlockNotNull(pos).getId() == BlockList1_6.cake.blockId()) {
                     PacketWrapper updateHealth = PacketWrapper.create(ClientboundPacketsb1_7.SET_HEALTH, (UserConnection)wrapper.user());
                     updateHealth.write(Types.SHORT, ((PlayerHealthTracker)wrapper.user().get(PlayerHealthTracker.class)).getHealth());
                     updateHealth.send(Protocolb1_7_0_3Tob1_8_0_1.class, false);
                  }
               }

            });
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.PLAYER_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               if ((Byte)wrapper.get(Types.BYTE, 0) > 3) {
                  wrapper.cancel();
               }

            });
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               if ((Short)wrapper.passthrough(Types.SHORT) == -1) {
                  wrapper.cancel();
               }

            });
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types1_4_2.NBTLESS_ITEM);
         }
      });
      this.registerServerbound(ServerboundPacketsb1_8.SET_CREATIVE_MODE_SLOT, (ServerboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         AlphaInventoryTracker inventoryTracker = (AlphaInventoryTracker)wrapper.user().get(AlphaInventoryTracker.class);
         if (inventoryTracker != null) {
            inventoryTracker.handleCreativeSetSlot((Short)wrapper.read(Types.SHORT), (Item)wrapper.read(Typesb1_8_0_1.CREATIVE_ITEM));
         }

      });
      this.registerServerbound(ServerboundPacketsb1_8.KEEP_ALIVE, (wrapper) -> {
         if ((Integer)wrapper.read(Types.INT) != 0) {
            wrapper.cancel();
         }

      });
   }

   public void init(UserConnection userConnection) {
      userConnection.put(new PreNettySplitter(Protocolb1_7_0_3Tob1_8_0_1.class, ClientboundPacketsb1_7::getPacket));
      userConnection.put(new PlayerNameTracker());
      userConnection.put(new PlayerHealthTracker());
   }

   boolean isSword(Item item) {
      return item.identifier() == ItemList1_6.swordWood.itemId() || item.identifier() == ItemList1_6.swordStone.itemId() || item.identifier() == ItemList1_6.swordIron.itemId() || item.identifier() == ItemList1_6.swordGold.itemId() || item.identifier() == ItemList1_6.swordDiamond.itemId();
   }
}
