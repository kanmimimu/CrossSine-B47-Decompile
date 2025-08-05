package com.viaversion.viarewind.protocol.v1_9to1_8.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.BossBarStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.CooldownStorage;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.PlayerPositionTracker;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerPacketRewriter1_9 extends RewriterBase {
   public PlayerPacketRewriter1_9(Protocol1_9To1_8 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.BOSS_EVENT, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         BossBarStorage bossbar = (BossBarStorage)wrapper.user().get(BossBarStorage.class);
         UUID uuid = (UUID)wrapper.read(Types.UUID);
         int action = (Integer)wrapper.read(Types.VAR_INT);
         if (action == 0) {
            JsonElement title = (JsonElement)wrapper.read(Types.COMPONENT);
            float health = (Float)wrapper.read(Types.FLOAT);
            wrapper.read(Types.VAR_INT);
            wrapper.read(Types.VAR_INT);
            wrapper.read(Types.UNSIGNED_BYTE);
            bossbar.add(uuid, ChatUtil.jsonToLegacy(title), health);
         } else if (action == 1) {
            bossbar.remove(uuid);
         } else if (action == 2) {
            float health = (Float)wrapper.read(Types.FLOAT);
            bossbar.updateHealth(uuid, health);
         } else if (action == 3) {
            JsonElement title = (JsonElement)wrapper.read(Types.COMPONENT);
            bossbar.updateTitle(uuid, ChatUtil.jsonToLegacy(title));
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.SET_PLAYER_TEAM, new PacketHandlers() {
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
                  wrapper.read(Types.STRING);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).cancelClientbound(ClientboundPackets1_9.COOLDOWN);
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = (String)wrapper.get(Types.STRING, 0);
               if (channel.equals("MC|BOpen")) {
                  wrapper.read(Types.VAR_INT);
               } else if (channel.equals("MC|TrList")) {
                  ((Protocol1_9To1_8)PlayerPacketRewriter1_9.this.protocol).getItemRewriter().handleTradeList(wrapper);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.PLAYER_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> {
               PlayerPositionTracker pos = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
               pos.setConfirmId((Integer)wrapper.read(Types.VAR_INT));
               byte flags = (Byte)wrapper.get(Types.BYTE, 0);
               double x = (Double)wrapper.get(Types.DOUBLE, 0);
               double y = (Double)wrapper.get(Types.DOUBLE, 1);
               double z = (Double)wrapper.get(Types.DOUBLE, 2);
               float yaw = (Float)wrapper.get(Types.FLOAT, 0);
               float pitch = (Float)wrapper.get(Types.FLOAT, 1);
               wrapper.set(Types.BYTE, 0, (byte)0);
               if (flags != 0) {
                  if ((flags & 1) != 0) {
                     x += pos.getPosX();
                     wrapper.set(Types.DOUBLE, 0, x);
                  }

                  if ((flags & 2) != 0) {
                     y += pos.getPosY();
                     wrapper.set(Types.DOUBLE, 1, y);
                  }

                  if ((flags & 4) != 0) {
                     z += pos.getPosZ();
                     wrapper.set(Types.DOUBLE, 2, z);
                  }

                  if ((flags & 8) != 0) {
                     yaw += pos.getYaw();
                     wrapper.set(Types.FLOAT, 0, yaw);
                  }

                  if ((flags & 16) != 0) {
                     pitch += pos.getPitch();
                     wrapper.set(Types.FLOAT, 1, pitch);
                  }
               }

               pos.setPos(x, y, z);
               pos.setYaw(yaw);
               pos.setPitch(pitch);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.handler((wrapper) -> {
               ClientWorld world = wrapper.user().getClientWorld(Protocol1_9To1_8.class);
               int dimension = (Integer)wrapper.get(Types.INT, 0);
               if (world.setEnvironment(dimension)) {
                  ((Protocol1_9To1_8)PlayerPacketRewriter1_9.this.protocol).getEntityRewriter().clearEntities(wrapper.user());
                  ((BossBarStorage)wrapper.user().get(BossBarStorage.class)).reset();
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.PLAYER_COMBAT, (wrapper) -> {
         if (ViaRewind.getConfig().handlePlayerCombatPacket()) {
            int action = (Integer)wrapper.passthrough(Types.VAR_INT);
            if (action == 2) {
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               int entityId = (Integer)wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.INT);
               if (entityId == tracker.clientEntityId()) {
                  JsonElement message = (JsonElement)wrapper.passthrough(Types.COMPONENT);
                  PacketWrapper killPlayer = wrapper.create(ClientboundPackets1_8.SET_HEALTH);
                  killPlayer.write(Types.FLOAT, 0.0F);
                  killPlayer.write(Types.VAR_INT, 0);
                  killPlayer.write(Types.FLOAT, 0.0F);
                  killPlayer.scheduleSend(Protocol1_9To1_8.class);
                  PacketWrapper chatMessage = wrapper.create(ClientboundPackets1_8.CHAT);
                  chatMessage.write(Types.COMPONENT, message);
                  chatMessage.write(Types.BYTE, (byte)2);
                  chatMessage.scheduleSend(Protocol1_9To1_8.class);
               }
            }
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CHAT, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               if (ViaRewind.getConfig().isEnableOffhand()) {
                  String message = (String)wrapper.get(Types.STRING, 0);
                  if (message.toLowerCase().trim().startsWith(ViaRewind.getConfig().getOffhandCommand())) {
                     wrapper.cancel();
                     PacketWrapper swapItems = PacketWrapper.create(ServerboundPackets1_9.PLAYER_ACTION, (UserConnection)wrapper.user());
                     swapItems.write(Types.VAR_INT, 6);
                     swapItems.write(Types.BLOCK_POSITION1_8, new BlockPosition(0, 0, 0));
                     swapItems.write(Types.UNSIGNED_BYTE, (short)255);
                     swapItems.sendToServer(Protocol1_9To1_8.class);
                  }

               }
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.INTERACT, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int type = (Integer)wrapper.get(Types.VAR_INT, 1);
               if (type == 2) {
                  wrapper.passthrough(Types.FLOAT);
                  wrapper.passthrough(Types.FLOAT);
                  wrapper.passthrough(Types.FLOAT);
               }

               if (type == 2 || type == 0) {
                  wrapper.write(Types.VAR_INT, 0);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_STATUS_ONLY, new PacketHandlers() {
         public void register() {
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               ((PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class)).sendAnimations();
               EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (tracker.isInsideVehicle(tracker.clientEntityId())) {
                  wrapper.cancel();
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_POS, new PacketHandlers() {
         public void register() {
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.DOUBLE);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               ((PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class)).sendAnimations();
               PlayerPositionTracker pos = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
               if (pos.getConfirmId() == -1) {
                  pos.setPos((Double)wrapper.get(Types.DOUBLE, 0), (Double)wrapper.get(Types.DOUBLE, 1), (Double)wrapper.get(Types.DOUBLE, 2));
                  pos.setOnGround((Boolean)wrapper.get(Types.BOOLEAN, 0));
               }
            });
            this.handler((wrapper) -> ((BossBarStorage)wrapper.user().get(BossBarStorage.class)).updateLocation());
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_ROT, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               ((PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class)).sendAnimations();
               PlayerPositionTracker pos = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
               if (pos.getConfirmId() == -1) {
                  pos.setYaw((Float)wrapper.get(Types.FLOAT, 0));
                  pos.setPitch((Float)wrapper.get(Types.FLOAT, 1));
                  pos.setOnGround((Boolean)wrapper.get(Types.BOOLEAN, 0));
               }
            });
            this.handler((wrapper) -> ((BossBarStorage)wrapper.user().get(BossBarStorage.class)).updateLocation());
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.MOVE_PLAYER_POS_ROT, (wrapper) -> {
         double x = (Double)wrapper.passthrough(Types.DOUBLE);
         double y = (Double)wrapper.passthrough(Types.DOUBLE);
         double z = (Double)wrapper.passthrough(Types.DOUBLE);
         float yaw = (Float)wrapper.passthrough(Types.FLOAT);
         float pitch = (Float)wrapper.passthrough(Types.FLOAT);
         boolean onGround = (Boolean)wrapper.passthrough(Types.BOOLEAN);
         PlayerPositionTracker storage = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
         storage.sendAnimations();
         if (storage.getConfirmId() != -1) {
            if (storage.getPosX() == x && storage.getPosY() == y && storage.getPosZ() == z && storage.getYaw() == yaw && storage.getPitch() == pitch) {
               PacketWrapper confirmTeleport = PacketWrapper.create(ServerboundPackets1_9.ACCEPT_TELEPORTATION, (UserConnection)wrapper.user());
               confirmTeleport.write(Types.VAR_INT, storage.getConfirmId());
               confirmTeleport.sendToServer(Protocol1_9To1_8.class);
               storage.setConfirmId(-1);
            }
         } else {
            storage.setPos(x, y, z);
            storage.setYaw(yaw);
            storage.setPitch(pitch);
            storage.setOnGround(onGround);
            ((BossBarStorage)wrapper.user().get(BossBarStorage.class)).updateLocation();
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.PLAYER_ACTION, (wrapper) -> {
         int status = (Integer)wrapper.passthrough(Types.VAR_INT);
         BlockPlaceDestroyTracker tracker = (BlockPlaceDestroyTracker)wrapper.user().get(BlockPlaceDestroyTracker.class);
         if (status == 0 || status == 1 || status == 2) {
            tracker.setMining();
         }

         CooldownStorage cooldown = (CooldownStorage)wrapper.user().get(CooldownStorage.class);
         if (status == 1) {
            tracker.setLastMining(0L);
            cooldown.hit();
         } else if (status == 2) {
            tracker.setLastMining(System.currentTimeMillis() + 100L);
            cooldown.setLastHit(0L);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.USE_ITEM_ON, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.BYTE, Types.VAR_INT);
            this.read(Types.ITEM1_8);
            this.create(Types.VAR_INT, 0);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               if ((Integer)wrapper.get(Types.VAR_INT, 0) == -1) {
                  wrapper.cancel();
                  PacketWrapper useItem = PacketWrapper.create(ServerboundPackets1_9.USE_ITEM, (UserConnection)wrapper.user());
                  useItem.write(Types.VAR_INT, 0);
                  useItem.sendToServer(Protocol1_9To1_8.class);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.SET_CARRIED_ITEM, (wrapper) -> ((CooldownStorage)wrapper.user().get(CooldownStorage.class)).hit());
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.SWING, (wrapper) -> {
         wrapper.cancel();
         PacketWrapper swing = PacketWrapper.create(ServerboundPackets1_9.SWING, (UserConnection)wrapper.user());
         swing.write(Types.VAR_INT, 0);
         ((PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class)).queueAnimation(swing);
         ((BlockPlaceDestroyTracker)wrapper.user().get(BlockPlaceDestroyTracker.class)).updateMining();
         ((CooldownStorage)wrapper.user().get(CooldownStorage.class)).hit();
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.PLAYER_COMMAND, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               PlayerPositionTracker tracker = (PlayerPositionTracker)wrapper.user().get(PlayerPositionTracker.class);
               int action = (Integer)wrapper.get(Types.VAR_INT, 1);
               if (action == 6) {
                  wrapper.set(Types.VAR_INT, 1, 7);
               } else if (action == 0 && !tracker.isOnGround()) {
                  PacketWrapper elytra = PacketWrapper.create(ServerboundPackets1_9.PLAYER_COMMAND, (UserConnection)wrapper.user());
                  elytra.write(Types.VAR_INT, (Integer)wrapper.get(Types.VAR_INT, 0));
                  elytra.write(Types.VAR_INT, 8);
                  elytra.write(Types.VAR_INT, 0);
                  elytra.scheduleSendToServer(Protocol1_9To1_8.class);
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.PLAYER_INPUT, (wrapper) -> {
         float sideways = (Float)wrapper.passthrough(Types.FLOAT);
         float forward = (Float)wrapper.passthrough(Types.FLOAT);
         EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
         int vehicle = tracker.getVehicle(tracker.clientEntityId());
         if (vehicle != -1 && tracker.entityType(vehicle) == EntityTypes1_9.EntityType.BOAT) {
            PacketWrapper paddleBoat = PacketWrapper.create(ServerboundPackets1_9.PADDLE_BOAT, (UserConnection)wrapper.user());
            paddleBoat.write(Types.BOOLEAN, forward != 0.0F || sideways < 0.0F);
            paddleBoat.write(Types.BOOLEAN, forward != 0.0F || sideways > 0.0F);
            paddleBoat.scheduleSendToServer(Protocol1_9To1_8.class);
         }

      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.SIGN_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               for(int i = 0; i < 4; ++i) {
                  wrapper.write(Types.STRING, ChatUtil.jsonToLegacy((JsonElement)wrapper.read(Types.COMPONENT)));
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.COMMAND_SUGGESTION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.create(Types.BOOLEAN, false);
            this.map(Types.OPTIONAL_POSITION1_8);
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.BYTE, Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.map(Types.UNSIGNED_BYTE);
            this.create(Types.VAR_INT, 1);
            this.handler((wrapper) -> {
               short flags = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               PacketWrapper updateSkin = PacketWrapper.create(ClientboundPackets1_8.SET_ENTITY_DATA, (UserConnection)wrapper.user());
               updateSkin.write(Types.VAR_INT, wrapper.user().getEntityTracker(Protocol1_9To1_8.class).clientEntityId());
               List<EntityData> entityData = new ArrayList();
               entityData.add(new EntityData(10, EntityDataTypes1_8.BYTE, (byte)flags));
               updateSkin.write(Types1_8.ENTITY_DATA_LIST, entityData);
               updateSkin.scheduleSend(Protocol1_9To1_8.class);
            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = (String)wrapper.get(Types.STRING, 0);
               if (!channel.equals("MC|BEdit") && !channel.equals("MC|BSign")) {
                  if (channel.equals("MC|AdvCdm")) {
                     wrapper.set(Types.STRING, 0, "MC|AdvCmd");
                  }
               } else {
                  Item book = (Item)wrapper.passthrough(Types.ITEM1_8);
                  book.setIdentifier(386);
                  CompoundTag tag = book.tag();
                  if (tag == null) {
                     return;
                  }

                  ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
                  if (pages == null) {
                     return;
                  }

                  if (pages.size() > ViaRewind.getConfig().getMaxBookPages()) {
                     wrapper.user().disconnect("Too many book pages");
                     return;
                  }

                  for(int i = 0; i < pages.size(); ++i) {
                     StringTag pageTag = (StringTag)pages.get(i);
                     String value = pageTag.getValue();
                     if (value.length() > ViaRewind.getConfig().getMaxBookPageSize()) {
                        wrapper.user().disconnect("Book page too large");
                        return;
                     }

                     pageTag.setValue(ChatUtil.jsonToLegacy(value));
                  }
               }

            });
         }
      });
   }
}
