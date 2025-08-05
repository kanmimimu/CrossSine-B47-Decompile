package com.viaversion.viabackwards.protocol.v1_13_1to1_13;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter.CommandRewriter1_13_1;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter.EntityPacketRewriter1_13_1;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter.ItemPacketRewriter1_13_1;
import com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter.WorldPacketRewriter1_13_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;

public class Protocol1_13_1To1_13 extends BackwardsProtocol {
   public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.13.2", "1.13", Protocol1_13To1_13_1.class);
   final EntityPacketRewriter1_13_1 entityRewriter = new EntityPacketRewriter1_13_1(this);
   final ItemPacketRewriter1_13_1 itemRewriter = new ItemPacketRewriter1_13_1(this);
   final TranslatableRewriter translatableRewriter;
   final TagRewriter tagRewriter;

   public Protocol1_13_1To1_13() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
      this.translatableRewriter = new TranslatableRewriter(this, ComponentRewriter.ReadType.JSON);
      this.tagRewriter = new TagRewriter(this);
   }

   protected void registerPackets() {
      super.registerPackets();
      WorldPacketRewriter1_13_1.register(this);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT);
      this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_13.PLAYER_COMBAT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
      this.translatableRewriter.registerTitle(ClientboundPackets1_13.SET_TITLES);
      this.translatableRewriter.registerPing();
      (new CommandRewriter1_13_1(this)).registerDeclareCommands(ClientboundPackets1_13.COMMANDS);
      this.registerServerbound(ServerboundPackets1_13.COMMAND_SUGGESTION, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.STRING, new ValueTransformer(Types.STRING) {
               public String transform(PacketWrapper wrapper, String inputValue) {
                  return !inputValue.startsWith("/") ? "/" + inputValue : inputValue;
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers() {
         public void register() {
            this.map(Types.ITEM1_13);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               Protocol1_13_1To1_13.this.itemRewriter.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_13, 0));
               wrapper.write(Types.VAR_INT, 0);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.OPEN_SCREEN, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               JsonElement title = (JsonElement)wrapper.passthrough(Types.COMPONENT);
               Protocol1_13_1To1_13.this.translatableRewriter.processText(wrapper.user(), title);
               if (ViaBackwards.getConfig().fix1_13FormattedInventoryTitle()) {
                  if (title.isJsonObject() && title.getAsJsonObject().size() == 1 && title.getAsJsonObject().has("translate")) {
                     return;
                  }

                  JsonObject legacyComponent = new JsonObject();
                  legacyComponent.addProperty("text", ComponentUtil.jsonToLegacy(title));
                  wrapper.set(Types.COMPONENT, 0, legacyComponent);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.COMMAND_SUGGESTIONS, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int start = (Integer)wrapper.get(Types.VAR_INT, 1);
               wrapper.set(Types.VAR_INT, 1, start - 1);
               int count = (Integer)wrapper.get(Types.VAR_INT, 3);

               for(int i = 0; i < count; ++i) {
                  wrapper.passthrough(Types.STRING);
                  wrapper.passthrough(Types.OPTIONAL_COMPONENT);
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.BOSS_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 0 || action == 3) {
                  Protocol1_13_1To1_13.this.translatableRewriter.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT));
                  if (action == 0) {
                     wrapper.passthrough(Types.FLOAT);
                     wrapper.passthrough(Types.VAR_INT);
                     wrapper.passthrough(Types.VAR_INT);
                     short flags = (Short)wrapper.read(Types.UNSIGNED_BYTE);
                     if ((flags & 4) != 0) {
                        flags = (short)(flags | 2);
                     }

                     wrapper.write(Types.UNSIGNED_BYTE, flags);
                  }
               }

            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.UPDATE_ADVANCEMENTS, (wrapper) -> {
         wrapper.passthrough(Types.BOOLEAN);
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);

         for(int i = 0; i < size; ++i) {
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.OPTIONAL_STRING);
            if ((Boolean)wrapper.passthrough(Types.BOOLEAN)) {
               wrapper.passthrough(Types.COMPONENT);
               wrapper.passthrough(Types.COMPONENT);
               Item icon = (Item)wrapper.passthrough(Types.ITEM1_13);
               this.itemRewriter.handleItemToClient(wrapper.user(), icon);
               wrapper.passthrough(Types.VAR_INT);
               int flags = (Integer)wrapper.passthrough(Types.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Types.STRING);
               }

               wrapper.passthrough(Types.FLOAT);
               wrapper.passthrough(Types.FLOAT);
            }

            wrapper.passthrough(Types.STRING_ARRAY);
            int arrayLength = (Integer)wrapper.passthrough(Types.VAR_INT);

            for(int array = 0; array < arrayLength; ++array) {
               wrapper.passthrough(Types.STRING_ARRAY);
            }
         }

      });
      this.tagRewriter.register(ClientboundPackets1_13.UPDATE_TAGS, RegistryType.ITEM);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_13.AWARD_STATS);
   }

   public void init(UserConnection user) {
      user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_13.EntityType.PLAYER));
      user.addClientWorld(this.getClass(), new ClientWorld());
   }

   public BackwardsMappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityPacketRewriter1_13_1 getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemPacketRewriter1_13_1 getItemRewriter() {
      return this.itemRewriter;
   }

   public TranslatableRewriter translatableRewriter() {
      return this.translatableRewriter;
   }

   public TagRewriter getTagRewriter() {
      return this.tagRewriter;
   }
}
