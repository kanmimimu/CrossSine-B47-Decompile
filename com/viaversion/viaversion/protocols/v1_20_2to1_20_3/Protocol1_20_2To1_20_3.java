package com.viaversion.viaversion.protocols.v1_20_2to1_20_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_3;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.CommandRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.BlockItemPacketRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.EntityPacketRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPacket1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.UUID;

public final class Protocol1_20_2To1_20_3 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.20.2", "1.20.3");
   final BlockItemPacketRewriter1_20_3 itemRewriter = new BlockItemPacketRewriter1_20_3(this);
   final EntityPacketRewriter1_20_3 entityRewriter = new EntityPacketRewriter1_20_3(this);
   final TagRewriter tagRewriter = new TagRewriter(this);

   public Protocol1_20_2To1_20_3() {
      super(ClientboundPacket1_20_2.class, ClientboundPacket1_20_3.class, ServerboundPacket1_20_2.class, ServerboundPacket1_20_3.class);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.cancelServerbound(ServerboundPackets1_20_3.CONTAINER_SLOT_STATE_CHANGED);
      this.tagRewriter.registerGeneric(ClientboundPackets1_20_2.UPDATE_TAGS);
      SoundRewriter<ClientboundPacket1_20_2> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_20_2.SOUND);
      soundRewriter.registerSound1_19_3(ClientboundPackets1_20_2.SOUND_ENTITY);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_20_2.AWARD_STATS);
      (new CommandRewriter1_19_4(this)).registerDeclareCommands1_19(ClientboundPackets1_20_2.COMMANDS);
      this.registerClientbound(ClientboundPackets1_20_2.SET_SCORE, (wrapper) -> {
         wrapper.passthrough(Types.STRING);
         int action = (Integer)wrapper.read(Types.VAR_INT);
         String objectiveName = (String)wrapper.read(Types.STRING);
         if (action == 1) {
            wrapper.write(Types.OPTIONAL_STRING, objectiveName.isEmpty() ? null : objectiveName);
            wrapper.setPacketType(ClientboundPackets1_20_3.RESET_SCORE);
         } else {
            wrapper.write(Types.STRING, objectiveName);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.OPTIONAL_TAG, (Object)null);
            wrapper.write(Types.BOOLEAN, false);
         }
      });
      this.registerClientbound(ClientboundPackets1_20_2.SET_OBJECTIVE, (wrapper) -> {
         wrapper.passthrough(Types.STRING);
         byte action = (Byte)wrapper.passthrough(Types.BYTE);
         if (action == 0 || action == 2) {
            this.convertComponent(wrapper);
            int render = (Integer)wrapper.passthrough(Types.VAR_INT);
            if (render == 0 && Via.getConfig().hideScoreboardNumbers()) {
               wrapper.write(Types.BOOLEAN, true);
               wrapper.write(Types.VAR_INT, 0);
            } else {
               wrapper.write(Types.BOOLEAN, false);
            }
         }

      });
      this.registerServerbound(ServerboundPackets1_20_3.SET_JIGSAW_BLOCK, (wrapper) -> {
         wrapper.passthrough(Types.BLOCK_POSITION1_14);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.STRING);
         wrapper.read(Types.VAR_INT);
         wrapper.read(Types.VAR_INT);
      });
      this.registerClientbound(ClientboundPackets1_20_2.UPDATE_ADVANCEMENTS, (wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               this.convertComponent(wrapper);
               this.convertComponent(wrapper);
               this.itemRewriter.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_20_2));
               wrapper.passthrough(Types.VAR_INT);
               int flags = (Integer)wrapper.passthrough(Types.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Types.STRING);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            int requirements = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int array = 0; array < requirements; ++array) {
               wrapper.passthrough(Types.STRING_ARRAY);
            }

            wrapper.passthrough(Types.BOOLEAN);
         }

      });
      this.registerClientbound(ClientboundPackets1_20_2.COMMAND_SUGGESTIONS, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.VAR_INT);
         int suggestions = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < suggestions; ++i) {
            wrapper.passthrough(Types.STRING);
            this.convertOptionalComponent(wrapper);
         }

      });
      this.registerClientbound(ClientboundPackets1_20_2.MAP_ITEM_DATA, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.BYTE);
         wrapper.passthrough(Types.BOOLEAN);
         if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
            int icons = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int i = 0; i < icons; ++i) {
               wrapper.passthrough(Types.VAR_INT);
               wrapper.passthrough(Types.BYTE);
               wrapper.passthrough(Types.BYTE);
               wrapper.passthrough(Types.BYTE);
               this.convertOptionalComponent(wrapper);
            }
         }

      });
      this.registerClientbound(ClientboundPackets1_20_2.BOSS_EVENT, (wrapper) -> {
         wrapper.passthrough(Types.UUID);
         int action = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (action == 0 || action == 3) {
            this.convertComponent(wrapper);
         }

      });
      this.registerClientbound(ClientboundPackets1_20_2.PLAYER_CHAT, (wrapper) -> {
         wrapper.passthrough(Types.UUID);
         wrapper.passthrough(Types.VAR_INT);
         wrapper.passthrough(Types.OPTIONAL_SIGNATURE_BYTES);
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.LONG);
         wrapper.passthrough(Types.LONG);
         int lastSeen = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < lastSeen; ++i) {
            int index = (Integer)wrapper.passthrough(Types.VAR_INT);
            if (index == 0) {
               wrapper.passthrough(Types.SIGNATURE_BYTES);
            }
         }

         this.convertOptionalComponent(wrapper);
         int filterMaskType = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (filterMaskType == 2) {
            wrapper.passthrough(Types.LONG_ARRAY_PRIMITIVE);
         }

         wrapper.passthrough(Types.VAR_INT);
         this.convertComponent(wrapper);
         this.convertOptionalComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_2.SET_PLAYER_TEAM, (wrapper) -> {
         wrapper.passthrough(Types.STRING);
         byte action = (Byte)wrapper.passthrough(Types.BYTE);
         if (action == 0 || action == 2) {
            this.convertComponent(wrapper);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.VAR_INT);
            this.convertComponent(wrapper);
            this.convertComponent(wrapper);
         }

      });
      this.registerClientbound(ClientboundConfigurationPackets1_20_2.DISCONNECT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.DISCONNECT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.RESOURCE_PACK, ClientboundPackets1_20_3.RESOURCE_PACK_PUSH, this.resourcePackHandler(ClientboundPackets1_20_3.RESOURCE_PACK_POP));
      this.registerClientbound(ClientboundPackets1_20_2.SERVER_DATA, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.SET_ACTION_BAR_TEXT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.SET_TITLE_TEXT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.SET_SUBTITLE_TEXT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.DISGUISED_CHAT, (wrapper) -> {
         this.convertComponent(wrapper);
         wrapper.passthrough(Types.VAR_INT);
         this.convertComponent(wrapper);
         this.convertOptionalComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_2.SYSTEM_CHAT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_2.OPEN_SCREEN, (wrapper) -> {
         wrapper.passthrough(Types.VAR_INT);
         int containerTypeId = (Integer)wrapper.read(Types.VAR_INT);
         wrapper.write(Types.VAR_INT, MAPPINGS.getMenuMappings().getNewId(containerTypeId));
         this.convertComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_2.TAB_LIST, (wrapper) -> {
         this.convertComponent(wrapper);
         this.convertComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_2.PLAYER_COMBAT_KILL, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> Protocol1_20_2To1_20_3.this.convertComponent(wrapper));
         }
      });
      this.registerClientbound(ClientboundPackets1_20_2.PLAYER_INFO_UPDATE, (wrapper) -> {
         BitSet actions = (BitSet)wrapper.passthrough(Types.PROFILE_ACTIONS_ENUM);
         int entries = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < entries; ++i) {
            wrapper.passthrough(Types.UUID);
            if (actions.get(0)) {
               wrapper.passthrough(Types.STRING);
               int properties = (Integer)wrapper.passthrough(Types.VAR_INT);

               for(int j = 0; j < properties; ++j) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.OPTIONAL_STRING);
               }
            }

            if (actions.get(1) && (Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               wrapper.passthrough(Types.UUID);
               wrapper.passthrough(Types.PROFILE_KEY);
            }

            if (actions.get(2)) {
               wrapper.passthrough(Types.VAR_INT);
            }

            if (actions.get(3)) {
               wrapper.passthrough(Types.BOOLEAN);
            }

            if (actions.get(4)) {
               wrapper.passthrough(Types.VAR_INT);
            }

            if (actions.get(5)) {
               this.convertOptionalComponent(wrapper);
            }
         }

      });
      this.registerServerbound(ServerboundPackets1_20_3.RESOURCE_PACK, this.resourcePackStatusHandler());
      this.registerServerbound(ServerboundConfigurationPackets1_20_2.RESOURCE_PACK, this.resourcePackStatusHandler());
      this.registerClientbound(ClientboundConfigurationPackets1_20_2.RESOURCE_PACK, ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_PUSH, this.resourcePackHandler(ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_POP));
      this.tagRewriter.registerGeneric(ClientboundConfigurationPackets1_20_2.UPDATE_TAGS);
   }

   PacketHandler resourcePackStatusHandler() {
      return (wrapper) -> {
         wrapper.read(Types.UUID);
         int action = (Integer)wrapper.read(Types.VAR_INT);
         if (action == 4) {
            wrapper.cancel();
         } else if (action > 4) {
            wrapper.write(Types.VAR_INT, 2);
         } else {
            wrapper.write(Types.VAR_INT, action);
         }

      };
   }

   PacketHandler resourcePackHandler(ClientboundPacketType popType) {
      return (wrapper) -> {
         PacketWrapper dropPacksPacket = wrapper.create(popType);
         dropPacksPacket.write(Types.OPTIONAL_UUID, (Object)null);
         dropPacksPacket.send(Protocol1_20_2To1_20_3.class);
         String url = (String)wrapper.read(Types.STRING);
         String hash = (String)wrapper.read(Types.STRING);
         wrapper.write(Types.UUID, UUID.nameUUIDFromBytes(url.getBytes(StandardCharsets.UTF_8)));
         wrapper.write(Types.STRING, url);
         wrapper.write(Types.STRING, hash);
         wrapper.passthrough(Types.BOOLEAN);
         this.convertOptionalComponent(wrapper);
      };
   }

   void convertComponent(PacketWrapper wrapper) {
      wrapper.write(Types.TAG, ComponentUtil.jsonToTag((JsonElement)wrapper.read(Types.COMPONENT)));
   }

   void convertOptionalComponent(PacketWrapper wrapper) {
      wrapper.write(Types.OPTIONAL_TAG, ComponentUtil.jsonToTag((JsonElement)wrapper.read(Types.OPTIONAL_COMPONENT)));
   }

   protected void onMappingDataLoaded() {
      EntityTypes1_20_3.initialize(this);
      Types1_20_3.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.item(Types.ITEM1_20_2)).reader("vibration", ParticleType.Readers.VIBRATION1_20_3).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
      super.onMappingDataLoaded();
   }

   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_3.PLAYER));
   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public BlockItemPacketRewriter1_20_3 getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityPacketRewriter1_20_3 getEntityRewriter() {
      return this.entityRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }

   protected PacketTypesProvider createPacketTypesProvider() {
      return new SimplePacketTypesProvider(ProtocolUtil.packetTypeMap(this.unmappedClientboundPacketType, ClientboundPackets1_20_2.class, ClientboundConfigurationPackets1_20_2.class), ProtocolUtil.packetTypeMap(this.mappedClientboundPacketType, ClientboundPackets1_20_3.class, ClientboundConfigurationPackets1_20_3.class), ProtocolUtil.packetTypeMap(this.mappedServerboundPacketType, ServerboundPackets1_20_2.class, ServerboundConfigurationPackets1_20_2.class), ProtocolUtil.packetTypeMap(this.unmappedServerboundPacketType, ServerboundPackets1_20_3.class, ServerboundConfigurationPackets1_20_2.class));
   }
}
