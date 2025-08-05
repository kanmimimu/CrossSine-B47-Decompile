package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.minecraft.math.AABB;
import com.viaversion.viarewind.api.minecraft.math.Ray3d;
import com.viaversion.viarewind.api.minecraft.math.RayTracing;
import com.viaversion.viarewind.api.minecraft.math.Vector3d;
import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.provider.TitleRenderProvider;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.EntityTracker1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.util.ComponentUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PlayerPacketRewriter1_8 extends RewriterBase {
   public PlayerPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.COMPONENT);
            this.handler((wrapper) -> {
               int position = (Byte)wrapper.read(Types.BYTE);
               if (position == 2) {
                  wrapper.cancel();
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_DEFAULT_SPAWN_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_HEALTH, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.VAR_INT, Types.SHORT);
            this.map(Types.FLOAT);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               if (ViaRewind.getConfig().isReplaceAdventureMode() && (Short)wrapper.get(Types.UNSIGNED_BYTE, 1) == 2) {
                  wrapper.set(Types.UNSIGNED_BYTE, 1, Short.valueOf((short)0));
               }

               EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
               tracker.setClientEntityGameMode((Short)wrapper.get(Types.UNSIGNED_BYTE, 1));
               int dimension = (Integer)wrapper.get(Types.INT, 0);
               ClientWorld world = wrapper.user().getClientWorld(Protocol1_8To1_7_6_10.class);
               if (world.setEnvironment(dimension)) {
                  tracker.clearEntities();
                  tracker.addPlayer(tracker.clientEntityId(), wrapper.user().getProtocolInfo().getUuid());
               }

               wrapper.send(Protocol1_8To1_7_6_10.class);
               wrapper.cancel();
               if (!tracker.getEntityData().isEmpty()) {
                  PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_ENTITY_DATA, (UserConnection)wrapper.user());
                  setEntityData.write(Types.VAR_INT, tracker.clientEntityId());
                  setEntityData.write(Types1_7_6_10.ENTITY_DATA_LIST, tracker.getEntityData());
                  setEntityData.send(Protocol1_8To1_7_6_10.class);
               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               double x = (Double)wrapper.get(Types.DOUBLE, 0);
               double y = (Double)wrapper.get(Types.DOUBLE, 1);
               double z = (Double)wrapper.get(Types.DOUBLE, 2);
               float yaw = (Float)wrapper.get(Types.FLOAT, 0);
               float pitch = (Float)wrapper.get(Types.FLOAT, 1);
               PlayerSessionStorage playerSession = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
               int flags = (Byte)wrapper.read(Types.BYTE);
               if ((flags & 1) == 1) {
                  wrapper.set(Types.DOUBLE, 0, x + playerSession.getPosX());
               }

               if ((flags & 2) == 2) {
                  y += playerSession.getPosY();
               }

               playerSession.receivedPosY = y;
               wrapper.set(Types.DOUBLE, 1, y + (double)1.62F);
               if ((flags & 4) == 4) {
                  wrapper.set(Types.DOUBLE, 2, z + playerSession.getPosZ());
               }

               if ((flags & 8) == 8) {
                  wrapper.set(Types.FLOAT, 0, yaw + playerSession.yaw);
               }

               if ((flags & 16) == 16) {
                  wrapper.set(Types.FLOAT, 1, pitch + playerSession.pitch);
               }

               wrapper.write(Types.BOOLEAN, playerSession.onGround);
               EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
               if (!Objects.equals(tracker.spectatingClientEntityId, tracker.clientEntityIdOrNull())) {
                  wrapper.cancel();
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_EXPERIENCE, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.VAR_INT, Types.SHORT);
            this.map(Types.VAR_INT, Types.SHORT);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               if ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0) == 3) {
                  int gameMode = ((Float)wrapper.get(Types.FLOAT, 0)).intValue();
                  EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                  if (gameMode == 3 || tracker.isSpectator()) {
                     UUID myId = wrapper.user().getProtocolInfo().getUuid();
                     Item[] equipment = new Item[4];
                     if (gameMode == 3) {
                        GameProfileStorage.GameProfile profile = ((GameProfileStorage)wrapper.user().get(GameProfileStorage.class)).get(myId);
                        equipment[3] = profile == null ? null : profile.getSkull();
                     } else {
                        for(int i = 0; i < equipment.length; ++i) {
                           equipment[i] = ((PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class)).getPlayerEquipment(myId, i);
                        }
                     }

                     for(int i = 0; i < equipment.length; ++i) {
                        PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_7_2_5.CONTAINER_SET_SLOT, (UserConnection)wrapper.user());
                        setSlot.write(Types.BYTE, (byte)0);
                        setSlot.write(Types.SHORT, (short)(8 - i));
                        setSlot.write(RewindTypes.COMPRESSED_NBT_ITEM, equipment[i]);
                        setSlot.scheduleSend(Protocol1_8To1_7_6_10.class);
                     }
                  }

                  if (gameMode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                     gameMode = 0;
                     wrapper.set(Types.FLOAT, 0, 0.0F);
                  }

                  tracker.setClientEntityGameMode(gameMode);
               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_INFO, (wrapper) -> {
         wrapper.cancel();
         GameProfileStorage gameProfileStorage = (GameProfileStorage)wrapper.user().get(GameProfileStorage.class);
         int action = (Integer)wrapper.read(Types.VAR_INT);
         int count = (Integer)wrapper.read(Types.VAR_INT);

         for(int i = 0; i < count; ++i) {
            UUID uuid = (UUID)wrapper.read(Types.UUID);
            if (action == 0) {
               String name = (String)wrapper.read(Types.STRING);
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
               if (gameProfile == null) {
                  gameProfile = gameProfileStorage.put(uuid, name);
               }

               int propertyCount = (Integer)wrapper.read(Types.VAR_INT);

               while(propertyCount-- > 0) {
                  String propertyName = (String)wrapper.read(Types.STRING);
                  String propertyValue = (String)wrapper.read(Types.STRING);
                  String propertySignature = (String)wrapper.read(Types.OPTIONAL_STRING);
                  gameProfile.properties.add(new GameProfileStorage.Property(propertyName, propertyValue, propertySignature));
               }

               int gamemode = (Integer)wrapper.read(Types.VAR_INT);
               int ping = (Integer)wrapper.read(Types.VAR_INT);
               gameProfile.ping = ping;
               gameProfile.gamemode = gamemode;
               JsonElement displayName = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
               if (displayName != null) {
                  gameProfile.setDisplayName(ChatUtil.jsonToLegacy(displayName));
               }

               PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
               playerInfo.write(Types.STRING, gameProfile.getDisplayName());
               playerInfo.write(Types.BOOLEAN, true);
               playerInfo.write(Types.SHORT, (short)ping);
               playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
            } else if (action == 1) {
               int gamemode = (Integer)wrapper.read(Types.VAR_INT);
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
               if (gameProfile != null && gameProfile.gamemode != gamemode) {
                  if (gamemode == 3 || gameProfile.gamemode == 3) {
                     EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                     int entityId = tracker.getPlayerEntityId(uuid);
                     boolean isOwnPlayer = entityId == tracker.clientEntityId();
                     if (entityId != -1) {
                        Item[] equipment = new Item[isOwnPlayer ? 4 : 5];
                        if (gamemode == 3) {
                           equipment[isOwnPlayer ? 3 : 4] = gameProfile.getSkull();
                        } else {
                           for(int j = 0; j < equipment.length; ++j) {
                              equipment[j] = ((PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class)).getPlayerEquipment(uuid, j);
                           }
                        }

                        for(short slot = 0; slot < equipment.length; ++slot) {
                           PacketWrapper setEquipment = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_EQUIPPED_ITEM, (UserConnection)wrapper.user());
                           setEquipment.write(Types.INT, entityId);
                           setEquipment.write(Types.SHORT, slot);
                           setEquipment.write(RewindTypes.COMPRESSED_NBT_ITEM, equipment[slot]);
                           setEquipment.scheduleSend(Protocol1_8To1_7_6_10.class);
                        }
                     }
                  }

                  gameProfile.gamemode = gamemode;
               }
            } else if (action == 2) {
               int ping = (Integer)wrapper.read(Types.VAR_INT);
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
               if (gameProfile != null) {
                  PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
                  packet.write(Types.STRING, gameProfile.getDisplayName());
                  packet.write(Types.BOOLEAN, false);
                  packet.write(Types.SHORT, (short)gameProfile.ping);
                  packet.scheduleSend(Protocol1_8To1_7_6_10.class);
                  gameProfile.ping = ping;
                  packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
                  packet.write(Types.STRING, gameProfile.getDisplayName());
                  packet.write(Types.BOOLEAN, true);
                  packet.write(Types.SHORT, (short)ping);
                  packet.scheduleSend(Protocol1_8To1_7_6_10.class);
               }
            } else if (action == 3) {
               JsonElement displayNameComponent = (JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT);
               String displayName = displayNameComponent != null ? ChatUtil.jsonToLegacy(displayNameComponent) : null;
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
               if (gameProfile != null && (gameProfile.displayName != null || displayName != null)) {
                  PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
                  playerInfo.write(Types.STRING, gameProfile.getDisplayName());
                  playerInfo.write(Types.BOOLEAN, false);
                  playerInfo.write(Types.SHORT, (short)gameProfile.ping);
                  playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
                  if (gameProfile.displayName == null && displayName != null || gameProfile.displayName != null && displayName == null || !gameProfile.displayName.equals(displayName)) {
                     gameProfile.setDisplayName(displayName);
                  }

                  playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
                  playerInfo.write(Types.STRING, gameProfile.getDisplayName());
                  playerInfo.write(Types.BOOLEAN, true);
                  playerInfo.write(Types.SHORT, (short)gameProfile.ping);
                  playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
               }
            } else if (action == 4) {
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.remove(uuid);
               if (gameProfile != null) {
                  PacketWrapper playerInfo = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, (UserConnection)wrapper.user());
                  playerInfo.write(Types.STRING, gameProfile.getDisplayName());
                  playerInfo.write(Types.BOOLEAN, false);
                  playerInfo.write(Types.SHORT, (short)gameProfile.ping);
                  playerInfo.scheduleSend(Protocol1_8To1_7_6_10.class);
               }
            }
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_ABILITIES, (wrapper) -> {
         byte flags = (Byte)wrapper.passthrough(Types.BYTE);
         float flySpeed = (Float)wrapper.passthrough(Types.FLOAT);
         float walkSpeed = (Float)wrapper.passthrough(Types.FLOAT);
         PlayerSessionStorage abilities = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
         abilities.invincible = (flags & 8) == 8;
         abilities.allowFly = (flags & 4) == 4;
         abilities.flying = (flags & 2) == 2;
         abilities.creative = (flags & 1) == 1;
         abilities.flySpeed = flySpeed;
         abilities.walkSpeed = walkSpeed;
         if (abilities.sprinting && abilities.flying) {
            wrapper.set(Types.FLOAT, 0, abilities.flySpeed * 2.0F);
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               wrapper.cancel();
               String channel = (String)wrapper.get(Types.STRING, 0);
               if (channel.equals("MC|TrList")) {
                  wrapper.passthrough(Types.INT);
                  int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

                  for(int i = 0; i < size; ++i) {
                     Item item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), (Item)wrapper.read(Types.ITEM1_8));
                     wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                     item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), (Item)wrapper.read(Types.ITEM1_8));
                     wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                     boolean has3Items = (Boolean)wrapper.passthrough(Types.BOOLEAN);
                     if (has3Items) {
                        item = ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), (Item)wrapper.read(Types.ITEM1_8));
                        wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM, item);
                     }

                     wrapper.passthrough(Types.BOOLEAN);
                     wrapper.read(Types.INT);
                     wrapper.read(Types.INT);
                  }
               } else if (channel.equals("MC|Brand")) {
                  wrapper.write(Types.REMAINING_BYTES, ((String)wrapper.read(Types.STRING)).getBytes(StandardCharsets.UTF_8));
               }

               wrapper.setPacketType((PacketType)null);
               ByteBuf data = Unpooled.buffer();
               wrapper.writeToBuffer(data);
               PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.CUSTOM_PAYLOAD, data, wrapper.user());
               packet.passthrough(Types.STRING);
               if (data.readableBytes() <= 32767) {
                  packet.write(Types.SHORT, (short)data.readableBytes());
                  packet.send(Protocol1_8To1_7_6_10.class);
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_CAMERA, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         int entityId = (Integer)wrapper.read(Types.VAR_INT);
         EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
         if (tracker.spectatingClientEntityId != entityId) {
            tracker.setSpectating(entityId);
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_TITLES, (ClientboundPacketType)null, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> {
               wrapper.cancel();
               int action = (Integer)wrapper.read(Types.VAR_INT);
               TitleRenderProvider provider = (TitleRenderProvider)Via.getManager().getProviders().get(TitleRenderProvider.class);
               if (provider != null) {
                  UUID uuid = wrapper.user().getProtocolInfo().getUuid();
                  switch (action) {
                     case 0:
                        provider.setTitle(uuid, (String)wrapper.read(Types.STRING));
                        break;
                     case 1:
                        provider.setSubTitle(uuid, (String)wrapper.read(Types.STRING));
                        break;
                     case 2:
                        provider.setTimings(uuid, (Integer)wrapper.read(Types.INT), (Integer)wrapper.read(Types.INT), (Integer)wrapper.read(Types.INT));
                        break;
                     case 3:
                        provider.clear(uuid);
                        break;
                     case 4:
                        provider.reset(uuid);
                  }

               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.TAB_LIST);
      ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.PLAYER_COMBAT);
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.RESOURCE_PACK, ClientboundPackets1_7_2_5.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.create(Types.STRING, "MC|RPack");
            this.handler((wrapper) -> {
               ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

               try {
                  Types.STRING.write(buf, (String)wrapper.read(Types.STRING));
                  wrapper.write(Types.SHORT_BYTE_ARRAY, (byte[])Types.REMAINING_BYTES.read(buf));
               } finally {
                  buf.release();
               }

            });
            this.read(Types.STRING);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CHAT, (wrapper) -> {
         String message = (String)wrapper.passthrough(Types.STRING);
         EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
         if (tracker.isSpectator() && message.toLowerCase().startsWith("/stp ")) {
            String username = message.split(" ")[1];
            GameProfileStorage storage = (GameProfileStorage)wrapper.user().get(GameProfileStorage.class);
            GameProfileStorage.GameProfile profile = storage.get(username, true);
            if (profile != null && profile.uuid != null) {
               wrapper.cancel();
               PacketWrapper teleport = PacketWrapper.create(ClientboundPackets1_7_2_5.TELEPORT_ENTITY, (UserConnection)wrapper.user());
               teleport.write(Types.UUID, profile.uuid);
               teleport.send(Protocol1_8To1_7_6_10.class);
            }
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.INTERACT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.map(Types.BYTE, Types.VAR_INT);
            this.handler((wrapper) -> {
               int mode = (Integer)wrapper.get(Types.VAR_INT, 1);
               if (mode == 0) {
                  int entityId = (Integer)wrapper.get(Types.VAR_INT, 0);
                  EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                  PlayerSessionStorage position = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
                  if (tracker.getHolograms().containsKey(entityId)) {
                     AABB boundingBox = ((VirtualHologramEntity)tracker.getHolograms().get(entityId)).getBoundingBox();
                     Vector3d pos = new Vector3d(position.getPosX(), position.getPosY() + 1.8, position.getPosZ());
                     double yaw = Math.toRadians((double)position.yaw);
                     double pitch = Math.toRadians((double)position.pitch);
                     Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                     Ray3d ray = new Ray3d(pos, dir);
                     Vector3d intersection = RayTracing.trace(ray, boundingBox, (double)5.0F);
                     if (intersection != null) {
                        intersection.substract(boundingBox.getMin());
                        wrapper.set(Types.VAR_INT, 1, 2);
                        wrapper.write(Types.FLOAT, (float)intersection.getX());
                        wrapper.write(Types.FLOAT, (float)intersection.getY());
                        wrapper.write(Types.FLOAT, (float)intersection.getZ());
                     }
                  }
               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers() {
         public void register() {
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> ((PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class)).onGround = (Boolean)wrapper.get(Types.BOOLEAN, 0));
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_POS, (wrapper) -> {
         double x = (Double)wrapper.passthrough(Types.DOUBLE);
         double y = (Double)wrapper.passthrough(Types.DOUBLE);
         wrapper.read(Types.DOUBLE);
         double z = (Double)wrapper.passthrough(Types.DOUBLE);
         PlayerSessionStorage storage = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
         storage.onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
         storage.setPos(x, y, z);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_ROT, (wrapper) -> {
         PlayerSessionStorage storage = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
         storage.yaw = (Float)wrapper.passthrough(Types.FLOAT);
         storage.pitch = (Float)wrapper.passthrough(Types.FLOAT);
         storage.onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         double x = (Double)wrapper.passthrough(Types.DOUBLE);
         double y = (Double)wrapper.passthrough(Types.DOUBLE);
         wrapper.read(Types.DOUBLE);
         double z = (Double)wrapper.passthrough(Types.DOUBLE);
         PlayerSessionStorage storage = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
         storage.setPos(x, y, z);
         storage.yaw = (Float)wrapper.passthrough(Types.FLOAT);
         storage.pitch = (Float)wrapper.passthrough(Types.FLOAT);
         storage.onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ACTION, new PacketHandlers() {
         protected void register() {
            this.map(Types.VAR_INT);
            this.map(RewindTypes.U_BYTE_POSITION, Types.BLOCK_POSITION1_8);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.USE_ITEM_ON, new PacketHandlers() {
         protected void register() {
            this.map(RewindTypes.U_BYTE_POSITION, Types.BLOCK_POSITION1_8);
            this.map(Types.BYTE);
            this.map(RewindTypes.COMPRESSED_NBT_ITEM, Types.ITEM1_8);
            this.handler((wrapper) -> ((Protocol1_8To1_7_6_10)PlayerPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.SWING, (wrapper) -> {
         int entityId = (Integer)wrapper.read(Types.INT);
         int animation = (Byte)wrapper.read(Types.BYTE);
         if (animation != 1) {
            wrapper.cancel();
            switch (animation) {
               case 3:
                  animation = 2;
                  break;
               case 104:
                  animation = 0;
                  break;
               case 105:
                  animation = 1;
                  break;
               default:
                  return;
            }

            PacketWrapper animate = PacketWrapper.create(ClientboundPackets1_7_2_5.ANIMATE, (UserConnection)wrapper.user());
            animate.write(Types.VAR_INT, entityId);
            animate.write(Types.VAR_INT, animation);
            animate.write(Types.VAR_INT, 0);
            animate.send(Protocol1_8To1_7_6_10.class);
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> wrapper.write(Types.VAR_INT, (Byte)wrapper.read(Types.BYTE) - 1));
            this.map(Types.INT, Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 1);
               if (action == 3 || action == 4) {
                  PlayerSessionStorage storage = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
                  storage.sprinting = action == 3;
                  PacketWrapper abilities = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_ABILITIES, (UserConnection)wrapper.user());
                  abilities.write(Types.BYTE, storage.combineAbilities());
                  abilities.write(Types.FLOAT, storage.sprinting ? storage.flySpeed * 2.0F : storage.flySpeed);
                  abilities.write(Types.FLOAT, storage.walkSpeed);
                  abilities.scheduleSend(Protocol1_8To1_7_6_10.class);
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_INPUT, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               boolean jump = (Boolean)wrapper.read(Types.BOOLEAN);
               boolean unmount = (Boolean)wrapper.read(Types.BOOLEAN);
               short flags = 0;
               if (jump) {
                  ++flags;
               }

               if (unmount) {
                  flags = (short)(flags + 2);
               }

               wrapper.write(Types.UNSIGNED_BYTE, flags);
               if (unmount) {
                  EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                  if (tracker.spectatingClientEntityId != tracker.clientEntityId()) {
                     PacketWrapper sneakPacket = PacketWrapper.create(ServerboundPackets1_8.PLAYER_COMMAND, (UserConnection)wrapper.user());
                     sneakPacket.write(Types.VAR_INT, tracker.clientEntityId());
                     sneakPacket.write(Types.VAR_INT, 0);
                     sneakPacket.write(Types.VAR_INT, 0);
                     sneakPacket.scheduleSendToServer(Protocol1_8To1_7_6_10.class);
                  }

               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(RewindTypes.SHORT_POSITION, Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  String line = (String)wrapper.read(Types.STRING);
                  wrapper.write(Types.COMPONENT, ComponentUtil.legacyToJson(line));
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ABILITIES, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> {
               PlayerSessionStorage storage = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
               if (storage.allowFly) {
                  byte flags = (Byte)wrapper.get(Types.BYTE, 0);
                  storage.flying = (flags & 2) == 2;
               }

               wrapper.set(Types.FLOAT, 0, storage.flySpeed);
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.COMMAND_SUGGESTION, (wrapper) -> {
         String message = (String)wrapper.passthrough(Types.STRING);
         wrapper.write(Types.OPTIONAL_POSITION1_8, (Object)null);
         if (message.toLowerCase().startsWith("/stp ")) {
            wrapper.cancel();
            String[] args = message.split(" ");
            if (args.length <= 2) {
               String prefix = args.length == 1 ? "" : args[1];
               GameProfileStorage storage = (GameProfileStorage)wrapper.user().get(GameProfileStorage.class);
               List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
               PacketWrapper commandSuggestions = PacketWrapper.create(ClientboundPackets1_7_2_5.COMMAND_SUGGESTIONS, (UserConnection)wrapper.user());
               commandSuggestions.write(Types.VAR_INT, profiles.size());

               for(GameProfileStorage.GameProfile profile : profiles) {
                  commandSuggestions.write(Types.STRING, profile.name);
               }

               commandSuggestions.scheduleSend(Protocol1_8To1_7_6_10.class);
            }
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.BYTE);
            this.map(Types.BOOLEAN);
            this.read(Types.BYTE);
            this.handler((wrapper) -> {
               boolean showCape = (Boolean)wrapper.read(Types.BOOLEAN);
               wrapper.write(Types.UNSIGNED_BYTE, (short)(showCape ? 127 : 126));
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.read(Types.SHORT);
            this.handler((wrapper) -> {
               switch ((String)wrapper.get(Types.STRING, 0)) {
                  case "MC|TrSel":
                     wrapper.passthrough(Types.INT);
                     wrapper.read(Types.REMAINING_BYTES);
                     break;
                  case "MC|ItemName":
                     byte[] data = (byte[])wrapper.read(Types.REMAINING_BYTES);
                     wrapper.write(Types.STRING, new String(data, StandardCharsets.UTF_8));
                     InventoryTracker tracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
                     PacketWrapper updateCost = PacketWrapper.create(ClientboundPackets1_7_2_5.CONTAINER_SET_DATA, (UserConnection)wrapper.user());
                     updateCost.write(Types.UNSIGNED_BYTE, tracker.anvilId);
                     updateCost.write(Types.SHORT, Short.valueOf((short)0));
                     updateCost.write(Types.SHORT, tracker.levelCost);
                     updateCost.send(Protocol1_8To1_7_6_10.class);
                     break;
                  case "MC|BEdit":
                  case "MC|BSign":
                     Item book = (Item)wrapper.read(RewindTypes.COMPRESSED_NBT_ITEM);
                     CompoundTag tag = book.tag();
                     if (tag != null && tag.contains("pages")) {
                        ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);

                        for(int i = 0; i < pages.size(); ++i) {
                           StringTag page = (StringTag)pages.get(i);
                           String value = page.getValue();
                           value = ComponentUtil.legacyToJsonString(value);
                           page.setValue(value);
                        }
                     }

                     wrapper.write(Types.ITEM1_8, book);
                     break;
                  case "MC|Brand":
                     wrapper.write(Types.STRING, new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8));
               }

            });
         }
      });
   }
}
