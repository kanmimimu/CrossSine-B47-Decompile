package com.viaversion.viabackwards.protocol.v1_16to1_15_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.data.BackwardsMappingData1_16;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter.BlockItemPacketRewriter1_16;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter.CommandRewriter1_16;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter.EntityPacketRewriter1_16;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter.TranslatableRewriter1_16;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.PlayerAttributesStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.PlayerSneakStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.WorldNameTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.UUID;

public class Protocol1_16To1_15_2 extends BackwardsProtocol {
   public static final BackwardsMappingData1_16 MAPPINGS = new BackwardsMappingData1_16();
   final EntityPacketRewriter1_16 entityRewriter = new EntityPacketRewriter1_16(this);
   final BlockItemPacketRewriter1_16 blockItemPackets = new BlockItemPacketRewriter1_16(this);
   final TranslatableRewriter1_16 translatableRewriter = new TranslatableRewriter1_16(this);
   final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_16To1_15_2() {
      super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerBossEvent(ClientboundPackets1_16.BOSS_EVENT);
      this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_16.PLAYER_COMBAT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_16.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_16.TAB_LIST);
      this.translatableRewriter.registerTitle(ClientboundPackets1_16.SET_TITLES);
      this.translatableRewriter.registerPing();
      (new CommandRewriter1_16(this)).registerDeclareCommands(ClientboundPackets1_16.COMMANDS);
      this.registerClientbound(State.STATUS, ClientboundStatusPackets.STATUS_RESPONSE, (wrapper) -> {
         String original = (String)wrapper.passthrough(Types.STRING);
         JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(original, JsonObject.class);
         JsonElement description = object.get("description");
         if (description != null) {
            this.translatableRewriter.processText(wrapper.user(), description);
            wrapper.set(Types.STRING, 0, object.toString());
         }
      });
      this.registerClientbound(ClientboundPackets1_16.CHAT, new PacketHandlers() {
         public void register() {
            this.handler((wrapper) -> Protocol1_16To1_15_2.this.translatableRewriter.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT)));
            this.map(Types.BYTE);
            this.read(Types.UUID);
         }
      });
      this.registerClientbound(ClientboundPackets1_16.OPEN_SCREEN, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> Protocol1_16To1_15_2.this.translatableRewriter.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT)));
            this.handler((wrapper) -> {
               int windowType = (Integer)wrapper.get(Types.VAR_INT, 1);
               if (windowType == 20) {
                  wrapper.set(Types.VAR_INT, 1, 7);
               } else if (windowType > 20) {
                  --windowType;
                  wrapper.set(Types.VAR_INT, 1, windowType);
               }

            });
         }
      });
      SoundRewriter<ClientboundPackets1_16> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_16.SOUND_ENTITY);
      soundRewriter.registerNamedSound(ClientboundPackets1_16.CUSTOM_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_16.STOP_SOUND);
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (wrapper) -> {
         UUID uuid = (UUID)wrapper.read(Types.UUID);
         wrapper.write(Types.STRING, uuid.toString());
      });
      this.tagRewriter.register(ClientboundPackets1_16.UPDATE_TAGS, RegistryType.ENTITY);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_16.AWARD_STATS);
      this.registerServerbound(ServerboundPackets1_14.PLAYER_COMMAND, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int action = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (action == 0) {
            ((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).setSneaking(true);
         } else if (action == 1) {
            ((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).setSneaking(false);
         }

      });
      this.registerServerbound(ServerboundPackets1_14.INTERACT, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int action = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (action == 0 || action == 2) {
            if (action == 2) {
               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            wrapper.passthrough(Types.VAR_INT);
         }

         wrapper.write(Types.BOOLEAN, ((PlayerSneakStorage)wrapper.user().get(PlayerSneakStorage.class)).isSneaking());
      });
      this.registerServerbound(ServerboundPackets1_14.PLAYER_ABILITIES, (wrapper) -> {
         byte flags = (Byte)wrapper.read(Types.BYTE);
         flags = (byte)(flags & 2);
         wrapper.write(Types.BYTE, flags);
         wrapper.read(Types.FLOAT);
         wrapper.read(Types.FLOAT);
      });
      this.cancelServerbound(ServerboundPackets1_14.SET_JIGSAW_BLOCK);
   }

   public void init(UserConnection user) {
      user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_16.PLAYER));
      user.addClientWorld(this.getClass(), new ClientWorld());
      user.put(new PlayerSneakStorage());
      user.put(new WorldNameTracker());
      user.put(new PlayerAttributesStorage());
   }

   public TranslatableRewriter1_16 getComponentRewriter() {
      return this.translatableRewriter;
   }

   public BackwardsMappingData1_16 getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_16 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPacketRewriter1_16 getItemRewriter() {
      return this.blockItemPackets;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
