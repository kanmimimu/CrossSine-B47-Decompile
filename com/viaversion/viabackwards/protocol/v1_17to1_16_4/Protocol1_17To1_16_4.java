package com.viaversion.viabackwards.protocol.v1_17to1_16_4;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.rewriter.BlockItemPacketRewriter1_17;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.rewriter.EntityPacketRewriter1_17;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Protocol1_17To1_16_4 extends BackwardsProtocol {
   public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.17", "1.16.2", Protocol1_16_4To1_17.class);
   static final RegistryType[] TAG_REGISTRY_TYPES;
   static final int[] EMPTY_ARRAY;
   final EntityPacketRewriter1_17 entityRewriter = new EntityPacketRewriter1_17(this);
   final BlockItemPacketRewriter1_17 blockItemPackets = new BlockItemPacketRewriter1_17(this);
   final TranslatableRewriter translatableRewriter;
   final TagRewriter tagRewriter;

   public Protocol1_17To1_16_4() {
      super(ClientboundPackets1_17.class, ClientboundPackets1_16_2.class, ServerboundPackets1_17.class, ServerboundPackets1_16_2.class);
      this.translatableRewriter = new TranslatableRewriter(this, ComponentRewriter.ReadType.JSON);
      this.tagRewriter = new TagRewriter(this);
   }

   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_17.CHAT);
      this.translatableRewriter.registerBossEvent(ClientboundPackets1_17.BOSS_EVENT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_17.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_17.TAB_LIST);
      this.translatableRewriter.registerOpenScreen(ClientboundPackets1_17.OPEN_SCREEN);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_17> soundRewriter = new SoundRewriter(this);
      soundRewriter.registerSound(ClientboundPackets1_17.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_17.SOUND_ENTITY);
      soundRewriter.registerNamedSound(ClientboundPackets1_17.CUSTOM_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_17.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_17.UPDATE_TAGS, (wrapper) -> {
         Map<String, List<TagData>> tags = new HashMap();
         int length = (Integer)wrapper.read(Types.VAR_INT);

         for(int i = 0; i < length; ++i) {
            String resourceKey = Key.stripMinecraftNamespace((String)wrapper.read(Types.STRING));
            List<TagData> tagList = new ArrayList();
            tags.put(resourceKey, tagList);
            int tagLength = (Integer)wrapper.read(Types.VAR_INT);

            for(int j = 0; j < tagLength; ++j) {
               String identifier = (String)wrapper.read(Types.STRING);
               int[] entries = (int[])wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
               tagList.add(new TagData(identifier, entries));
            }
         }

         for(RegistryType type : TAG_REGISTRY_TYPES) {
            List<TagData> tagList = (List)tags.get(type.resourceLocation());
            if (tagList == null) {
               wrapper.write(Types.VAR_INT, 0);
            } else {
               IdRewriteFunction rewriter = this.tagRewriter.getRewriter(type);
               wrapper.write(Types.VAR_INT, tagList.size());

               for(TagData tagData : tagList) {
                  int[] entries = tagData.entries();
                  if (rewriter != null) {
                     IntList idList = new IntArrayList(entries.length);

                     for(int id : entries) {
                        int mappedId = rewriter.rewrite(id);
                        if (mappedId != -1) {
                           idList.add(mappedId);
                        }
                     }

                     entries = idList.toArray(EMPTY_ARRAY);
                  }

                  wrapper.write(Types.STRING, tagData.identifier());
                  wrapper.write(Types.VAR_INT_ARRAY_PRIMITIVE, entries);
               }
            }
         }

      });
      (new StatisticsRewriter(this)).register(ClientboundPackets1_17.AWARD_STATS);
      this.registerClientbound(ClientboundPackets1_17.RESOURCE_PACK, (wrapper) -> {
         wrapper.passthrough(Types.STRING);
         wrapper.passthrough(Types.STRING);
         wrapper.read(Types.BOOLEAN);
         wrapper.read(Types.OPTIONAL_COMPONENT);
      });
      this.registerClientbound(ClientboundPackets1_17.EXPLODE, new PacketHandlers() {
         public void register() {
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.map(Types.FLOAT);
            this.handler((wrapper) -> wrapper.write(Types.INT, (Integer)wrapper.read(Types.VAR_INT)));
         }
      });
      this.registerClientbound(ClientboundPackets1_17.SET_DEFAULT_SPAWN_POSITION, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_14);
            this.handler((wrapper) -> wrapper.read(Types.FLOAT));
         }
      });
      this.registerClientbound(ClientboundPackets1_17.PING, (ClientboundPacketType)null, (wrapper) -> {
         wrapper.cancel();
         int id = (Integer)wrapper.read(Types.INT);
         short shortId = (short)id;
         if (id == shortId && ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
            PacketWrapper acknowledgementPacket = wrapper.create(ClientboundPackets1_16_2.CONTAINER_ACK);
            acknowledgementPacket.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
            acknowledgementPacket.write(Types.SHORT, shortId);
            acknowledgementPacket.write(Types.BOOLEAN, false);
            acknowledgementPacket.send(Protocol1_17To1_16_4.class);
         } else {
            PacketWrapper pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
            pongPacket.write(Types.INT, id);
            pongPacket.sendToServer(Protocol1_17To1_16_4.class);
         }
      });
      this.registerServerbound(ServerboundPackets1_16_2.CLIENT_INFORMATION, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.map(Types.BYTE);
            this.map(Types.VAR_INT);
            this.map(Types.BOOLEAN);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> wrapper.write(Types.BOOLEAN, false));
         }
      });
      this.rewriteTitlePacket(ClientboundPackets1_17.SET_TITLE_TEXT, 0);
      this.rewriteTitlePacket(ClientboundPackets1_17.SET_SUBTITLE_TEXT, 1);
      this.rewriteTitlePacket(ClientboundPackets1_17.SET_ACTION_BAR_TEXT, 2);
      this.mergePacket(ClientboundPackets1_17.SET_TITLES_ANIMATION, ClientboundPackets1_16_2.SET_TITLES, 3);
      this.registerClientbound(ClientboundPackets1_17.CLEAR_TITLES, ClientboundPackets1_16_2.SET_TITLES, (wrapper) -> {
         if ((Boolean)wrapper.read(Types.BOOLEAN)) {
            wrapper.write(Types.VAR_INT, 5);
         } else {
            wrapper.write(Types.VAR_INT, 4);
         }

      });
      this.cancelClientbound(ClientboundPackets1_17.ADD_VIBRATION_SIGNAL);
   }

   public void init(UserConnection user) {
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_17.PLAYER));
      user.put(new PlayerLastCursorItem());
   }

   public BackwardsMappingData getMappingData() {
      return MAPPINGS;
   }

   public TranslatableRewriter getComponentRewriter() {
      return this.translatableRewriter;
   }

   public void mergePacket(ClientboundPackets1_17 newPacketType, ClientboundPackets1_16_2 oldPacketType, int type) {
      this.registerClientbound(newPacketType, oldPacketType, (wrapper) -> wrapper.write(Types.VAR_INT, type));
   }

   void rewriteTitlePacket(ClientboundPackets1_17 newPacketType, int type) {
      this.registerClientbound(newPacketType, ClientboundPackets1_16_2.SET_TITLES, (wrapper) -> {
         wrapper.write(Types.VAR_INT, type);
         this.translatableRewriter.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT));
      });
   }

   public EntityPacketRewriter1_17 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPacketRewriter1_17 getItemRewriter() {
      return this.blockItemPackets;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }

   static {
      TAG_REGISTRY_TYPES = new RegistryType[]{RegistryType.BLOCK, RegistryType.ITEM, RegistryType.FLUID, RegistryType.ENTITY};
      EMPTY_ARRAY = new int[0];
   }
}
