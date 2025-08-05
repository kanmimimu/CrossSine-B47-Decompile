package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.GameMode;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CommandBlockProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CompressionProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MainHandProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.ClientWorld1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.MovementTracker;
import com.viaversion.viaversion.util.ComponentUtil;

public class PlayerPacketRewriter1_9 {
   public static void register(final Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               JsonObject obj = (JsonObject)wrapper.get(Types.COMPONENT, 0);
               if (obj.get("translate") != null && obj.get("translate").getAsString().equals("gameMode.changed")) {
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  String gameMode = tracker.getGameMode().text();
                  JsonObject gameModeObject = new JsonObject();
                  gameModeObject.addProperty("text", gameMode);
                  gameModeObject.addProperty("color", "gray");
                  gameModeObject.addProperty("italic", true);
                  JsonArray array = new JsonArray();
                  array.add((JsonElement)gameModeObject);
                  obj.add("with", array);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.TAB_LIST, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
            this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.DISCONNECT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SET_TITLES, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 0 || action == 1) {
                  Protocol1_8To1_9.STRING_TO_JSON.write(wrapper, (String)wrapper.read(Types.STRING));
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BYTE);
            this.create(Types.VAR_INT, 0);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SET_PLAYER_TEAM, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               byte mode = (Byte)wrapper.get(Types.BYTE, 0);
               if (mode == 0 || mode == 2) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BYTE);
                  wrapper.passthrough(Types.STRING);
                  wrapper.write(Types.STRING, Via.getConfig().isPreventCollision() ? "never" : "");
                  wrapper.passthrough(Types.BYTE);
               }

               if (mode == 0 || mode == 3 || mode == 4) {
                  String[] players = (String[])wrapper.passthrough(Types.STRING_ARRAY);
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  String myName = wrapper.user().getProtocolInfo().getUsername();
                  String teamName = (String)wrapper.get(Types.STRING, 0);

                  for(String player : players) {
                     if (entityTracker.isAutoTeam() && player.equalsIgnoreCase(myName)) {
                        if (mode == 4) {
                           wrapper.send(Protocol1_8To1_9.class);
                           wrapper.cancel();
                           entityTracker.sendTeamPacket(true, true);
                           entityTracker.setCurrentTeam("viaversion");
                        } else {
                           entityTracker.sendTeamPacket(false, true);
                           entityTracker.setCurrentTeam(teamName);
                        }
                     }
                  }
               }

               if (mode == 1) {
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  String teamName = (String)wrapper.get(Types.STRING, 0);
                  if (entityTracker.isAutoTeam() && teamName.equals(entityTracker.getCurrentTeam())) {
                     wrapper.send(Protocol1_8To1_9.class);
                     wrapper.cancel();
                     entityTracker.sendTeamPacket(true, true);
                     entityTracker.setCurrentTeam("viaversion");
                  }
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.LOGIN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Types.INT, 0);
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               tracker.addEntity(entityId, EntityTypes1_9.EntityType.PLAYER);
               tracker.setClientEntityId(entityId);
            });
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               short gamemodeId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               gamemodeId = (short)(gamemodeId & -9);
               tracker.setGameMode(GameMode.getById(gamemodeId));
            });
            this.handler((wrapper) -> {
               ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_8To1_9.class);
               int dimensionId = (Byte)wrapper.get(Types.BYTE, 0);
               clientWorld.setEnvironment(dimensionId);
            });
            this.handler((wrapper) -> {
               CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
               provider.sendPermission(wrapper.user());
            });
            this.handler((wrapper) -> {
               EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               if (Via.getConfig().isAutoTeam()) {
                  entityTracker.setAutoTeam(true);
                  wrapper.send(Protocol1_8To1_9.class);
                  wrapper.cancel();
                  entityTracker.sendTeamPacket(true, true);
                  entityTracker.setCurrentTeam("viaversion");
               } else {
                  entityTracker.setAutoTeam(false);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.PLAYER_INFO, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               int count = (Integer)wrapper.get(Types.VAR_INT, 1);

               for(int i = 0; i < count; ++i) {
                  wrapper.passthrough(Types.UUID);
                  if (action != 0) {
                     if (action != 1 && action != 2) {
                        if (action == 3) {
                           String displayName = (String)wrapper.read(Types.OPTIONAL_STRING);
                           wrapper.write(Types.OPTIONAL_COMPONENT, displayName != null ? (JsonElement)Protocol1_8To1_9.STRING_TO_JSON.transform(wrapper, displayName) : null);
                        }
                     } else {
                        wrapper.passthrough(Types.VAR_INT);
                     }
                  } else {
                     wrapper.passthrough(Types.STRING);
                     int properties = (Integer)wrapper.passthrough(Types.VAR_INT);

                     for(int j = 0; j < properties; ++j) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.OPTIONAL_STRING);
                     }

                     wrapper.passthrough(Types.VAR_INT);
                     wrapper.passthrough(Types.VAR_INT);
                     String displayName = (String)wrapper.read(Types.OPTIONAL_STRING);
                     wrapper.write(Types.OPTIONAL_COMPONENT, displayName != null ? (JsonElement)Protocol1_8To1_9.STRING_TO_JSON.transform(wrapper, displayName) : null);
                  }
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String name = (String)wrapper.get(Types.STRING, 0);
               if (name.equals("MC|BOpen")) {
                  wrapper.write(Types.VAR_INT, 0);
               } else if (name.equals("MC|TrList")) {
                  protocol.getItemRewriter().handleTradeList(wrapper);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               CommandBlockProvider provider = (CommandBlockProvider)Via.getManager().getProviders().get(CommandBlockProvider.class);
               provider.sendPermission(wrapper.user());
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               int gamemode = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               tracker.setGameMode(GameMode.getById(gamemode));
               ClientWorld1_9 clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
               int dimensionId = (Integer)wrapper.get(Types.INT, 0);
               if (clientWorld.setEnvironment(dimensionId)) {
                  tracker.clearEntities();
                  clientWorld.getLoadedChunks().clear();
                  provider.unloadChunks(wrapper.user());
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               short reason = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (reason == 3) {
                  int gamemode = ((Float)wrapper.get(Types.FLOAT, 0)).intValue();
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  tracker.setGameMode(GameMode.getById(gamemode));
               } else if (reason == 4) {
                  wrapper.set(Types.FLOAT, 0, 1.0F);
               }

            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SET_COMPRESSION, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         CompressionProvider provider = (CompressionProvider)Via.getManager().getProviders().get(CompressionProvider.class);
         provider.handlePlayCompression(wrapper.user(), (Integer)wrapper.read(Types.VAR_INT));
      });
      protocol.registerServerbound(ServerboundPackets1_9.COMMAND_SUGGESTION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.read(Types.BOOLEAN);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT, Types.BYTE);
            this.map(Types.BOOLEAN);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               int hand = (Integer)wrapper.read(Types.VAR_INT);
               if (Via.getConfig().isLeftHandedHandling() && hand == 0) {
                  wrapper.set(Types.UNSIGNED_BYTE, 0, (short)(((Short)wrapper.get(Types.UNSIGNED_BYTE, 0)).intValue() | 128));
               }

               wrapper.sendToServer(Protocol1_8To1_9.class);
               wrapper.cancel();
               ((MainHandProvider)Via.getManager().getProviders().get(MainHandProvider.class)).setMainHand(wrapper.user(), hand);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.SWING, new PacketHandlers() {
         public void register() {
            this.read(Types.VAR_INT);
         }
      });
      protocol.cancelServerbound(ServerboundPackets1_9.ACCEPT_TELEPORTATION);
      protocol.cancelServerbound(ServerboundPackets1_9.MOVE_VEHICLE);
      protocol.cancelServerbound(ServerboundPackets1_9.PADDLE_BOAT);
      protocol.registerServerbound(ServerboundPackets1_9.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               String name = (String)wrapper.get(Types.STRING, 0);
               if (name.equals("MC|BSign")) {
                  Item item = (Item)wrapper.passthrough(Types.ITEM1_8);
                  if (item != null) {
                     item.setIdentifier(387);
                     CompoundTag tag = item.tag();
                     ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
                     if (pages == null) {
                        return;
                     }

                     for(int i = 0; i < pages.size(); ++i) {
                        StringTag pageTag = (StringTag)pages.get(i);
                        String value = pageTag.getValue();
                        pageTag.setValue(ComponentUtil.plainToJson(value).toString());
                     }
                  }
               }

               if (name.equals("MC|AutoCmd")) {
                  wrapper.set(Types.STRING, 0, "MC|AdvCdm");
                  wrapper.write(Types.BYTE, (byte)0);
                  wrapper.passthrough(Types.INT);
                  wrapper.passthrough(Types.INT);
                  wrapper.passthrough(Types.INT);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.BOOLEAN);
                  wrapper.clearInputBuffer();
               }

               if (name.equals("MC|AdvCmd")) {
                  wrapper.set(Types.STRING, 0, "MC|AdvCdm");
               }

            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.CLIENT_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 2) {
                  EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  if (tracker.isBlocking()) {
                     if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                        tracker.setSecondHand((Item)null);
                     }

                     tracker.setBlocking(false);
                  }
               }

            });
         }
      });
      final PacketHandler onGroundHandler = (wrapper) -> {
         MovementTracker tracker = (MovementTracker)wrapper.user().get(MovementTracker.class);
         tracker.incrementIdlePacket();
         tracker.setGround((Boolean)wrapper.get(Types.BOOLEAN, 0));
      };
      protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BOOLEAN);
            this.handler(onGroundHandler);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_POS_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler(onGroundHandler);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler(onGroundHandler);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_9.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers() {
         public void register() {
            this.map(Types.BOOLEAN);
            this.handler(onGroundHandler);
         }
      });
   }
}
