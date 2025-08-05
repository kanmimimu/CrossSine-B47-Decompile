package com.viaversion.viaversion.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.TagUtil;
import java.util.BitSet;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ComponentRewriter implements com.viaversion.viaversion.api.rewriter.ComponentRewriter {
   protected final Protocol protocol;
   protected final ReadType type;

   public ComponentRewriter(Protocol protocol, ReadType type) {
      this.protocol = protocol;
      this.type = type;
   }

   public void registerComponentPacket(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, this::passthroughAndProcess);
   }

   public void registerBossEvent(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UUID);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int action = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (action == 0 || action == 3) {
                  ComponentRewriter.this.passthroughAndProcess(wrapper);
               }

            });
         }
      }));
   }

   public void registerPlayerCombat(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         if ((Integer)wrapper.passthrough(Types.VAR_INT) == 2) {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.INT);
            this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT));
         }

      }));
   }

   public void registerTitle(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         int action = (Integer)wrapper.passthrough(Types.VAR_INT);
         if (action >= 0 && action <= 2) {
            this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT));
         }

      }));
   }

   public void registerPing() {
      this.protocol.registerClientbound((State)State.LOGIN, ClientboundLoginPackets.LOGIN_DISCONNECT, (wrapper) -> this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT)));
   }

   public void registerLegacyOpenWindow(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.handler((wrapper) -> ComponentRewriter.this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT)));
         }
      }));
   }

   public void registerOpenScreen(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> ComponentRewriter.this.passthroughAndProcess(wrapper));
         }
      }));
   }

   public void registerTabList(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         this.passthroughAndProcess(wrapper);
         this.passthroughAndProcess(wrapper);
      }));
   }

   public void registerPlayerInfoUpdate1_20_3(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)((wrapper) -> {
         BitSet actions = (BitSet)wrapper.passthrough(Types.PROFILE_ACTIONS_ENUM);
         if (actions.get(5)) {
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

               this.processTag(wrapper.user(), (Tag)wrapper.passthrough(Types.OPTIONAL_TAG));
            }

         }
      }));
   }

   public void registerPlayerCombatKill(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.INT);
            this.handler((wrapper) -> ComponentRewriter.this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT)));
         }
      }));
   }

   public void registerPlayerCombatKill1_20(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, (PacketHandler)(new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> ComponentRewriter.this.passthroughAndProcess(wrapper));
         }
      }));
   }

   public void passthroughAndProcess(PacketWrapper wrapper) {
      switch (this.type) {
         case JSON:
            this.processText(wrapper.user(), (JsonElement)wrapper.passthrough(Types.COMPONENT));
            break;
         case NBT:
            this.processTag(wrapper.user(), (Tag)wrapper.passthrough(Types.TAG));
      }

   }

   public JsonElement processText(UserConnection connection, String value) {
      try {
         JsonElement root = JsonParser.parseString(value);
         this.processText(connection, root);
         return root;
      } catch (JsonSyntaxException e) {
         if (Via.getManager().isDebug()) {
            this.protocol.getLogger().severe("Error when trying to parse json: " + value);
            throw e;
         } else {
            return new JsonPrimitive(value);
         }
      }
   }

   public void processText(UserConnection connection, JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         if (element.isJsonArray()) {
            this.processJsonArray(connection, element.getAsJsonArray());
         } else if (element.isJsonObject()) {
            this.processJsonObject(connection, element.getAsJsonObject());
         }

      }
   }

   protected void processJsonArray(UserConnection connection, JsonArray array) {
      for(JsonElement jsonElement : array) {
         this.processText(connection, jsonElement);
      }

   }

   protected void processJsonObject(UserConnection connection, JsonObject object) {
      JsonElement translate = object.get("translate");
      if (translate != null && translate.isJsonPrimitive()) {
         this.handleTranslate(object, translate.getAsString());
         JsonElement with = object.get("with");
         if (with != null && with.isJsonArray()) {
            this.processJsonArray(connection, with.getAsJsonArray());
         }
      }

      JsonElement extra = object.get("extra");
      if (extra != null && extra.isJsonArray()) {
         this.processJsonArray(connection, extra.getAsJsonArray());
      }

      JsonElement hoverEvent = object.get("hoverEvent");
      if (hoverEvent != null && hoverEvent.isJsonObject()) {
         this.handleHoverEvent(connection, hoverEvent.getAsJsonObject());
      }

   }

   protected void handleTranslate(JsonObject object, String translate) {
   }

   protected void handleHoverEvent(UserConnection connection, JsonObject hoverEvent) {
      JsonPrimitive actionElement = hoverEvent.getAsJsonPrimitive("action");
      if (actionElement.isString()) {
         String action = actionElement.getAsString();
         if (action.equals("show_text")) {
            JsonElement value = hoverEvent.get("value");
            this.processText(connection, value != null ? value : hoverEvent.get("contents"));
         } else if (action.equals("show_entity")) {
            JsonElement contents = hoverEvent.get("contents");
            if (contents != null && contents.isJsonObject()) {
               this.processText(connection, contents.getAsJsonObject().get("name"));
            }
         }

      }
   }

   public void processTag(UserConnection connection, @Nullable Tag tag) {
      if (tag != null) {
         if (tag instanceof ListTag) {
            this.processListTag(connection, (ListTag)tag);
         } else if (tag instanceof CompoundTag) {
            this.processCompoundTag(connection, (CompoundTag)tag);
         }

      }
   }

   void processListTag(UserConnection connection, ListTag tag) {
      for(Tag entry : tag) {
         this.processTag(connection, entry);
      }

   }

   protected void processCompoundTag(UserConnection connection, CompoundTag tag) {
      StringTag translate = tag.getStringTag("translate");
      if (translate != null) {
         this.handleTranslate(connection, tag, translate);
         ListTag<?> with = tag.getListTag("with");
         if (with != null) {
            this.processListTag(connection, with);
         }
      }

      ListTag<?> extra = tag.getListTag("extra");
      if (extra != null) {
         this.processListTag(connection, extra);
      }

      CompoundTag hoverEvent = tag.getCompoundTag("hoverEvent");
      if (hoverEvent != null) {
         this.handleHoverEvent(connection, hoverEvent);
      }

   }

   protected void handleTranslate(UserConnection connection, CompoundTag parentTag, StringTag translateTag) {
   }

   protected void handleHoverEvent(UserConnection connection, CompoundTag hoverEventTag) {
      StringTag actionTag = hoverEventTag.getStringTag("action");
      if (actionTag != null) {
         String action = actionTag.getValue();
         if (action.equals("show_text")) {
            Tag value = hoverEventTag.get("value");
            this.processTag(connection, value != null ? value : hoverEventTag.get("contents"));
         } else if (action.equals("show_entity")) {
            CompoundTag contents = hoverEventTag.getCompoundTag("contents");
            if (contents != null) {
               this.processTag(connection, contents.get("name"));
            }
         } else if (action.equals("show_item")) {
            this.convertLegacyContents(hoverEventTag);
            CompoundTag contentsTag = hoverEventTag.getCompoundTag("contents");
            if (contentsTag == null) {
               return;
            }

            CompoundTag componentsTag = contentsTag.getCompoundTag("components");
            this.handleShowItem(connection, contentsTag, componentsTag);
            if (componentsTag != null) {
               this.handleContainerContents(connection, componentsTag);
               if (this.inputSerializerVersion() != null) {
                  this.handleWrittenBookContents(connection, componentsTag);
               }

               this.handleItemArrayContents(connection, componentsTag, "bundle_contents");
               this.handleItemArrayContents(connection, componentsTag, "charged_projectiles");
            }
         }

      }
   }

   protected void handleShowItem(UserConnection connection, CompoundTag itemTag, @Nullable CompoundTag componentsTag) {
      StringTag idTag = itemTag.getStringTag("id");
      String mappedId = this.protocol.getMappingData().getFullItemMappings().mappedIdentifier(idTag.getValue());
      if (mappedId != null) {
         idTag.setValue(mappedId);
      }

   }

   protected void handleContainerContents(UserConnection connection, CompoundTag tag) {
      ListTag<CompoundTag> container = TagUtil.getNamespacedCompoundTagList(tag, "minecraft:container");
      if (container != null) {
         for(CompoundTag entryTag : container) {
            CompoundTag itemTag = entryTag.getCompoundTag("item");
            this.handleShowItem(connection, itemTag, itemTag.getCompoundTag("components"));
         }

      }
   }

   protected void handleWrittenBookContents(UserConnection connection, CompoundTag tag) {
      CompoundTag book = TagUtil.getNamespacedCompoundTag(tag, "minecraft:written_book_content");
      if (book != null) {
         ListTag<CompoundTag> pagesTag = book.getListTag("pages", CompoundTag.class);
         if (pagesTag != null) {
            for(CompoundTag compoundTag : pagesTag) {
               StringTag raw = compoundTag.getStringTag("raw");
               this.processJsonString(connection, raw);
               StringTag filtered = compoundTag.getStringTag("filtered");
               this.processJsonString(connection, filtered);
            }

         }
      }
   }

   void processJsonString(UserConnection connection, StringTag tag) {
      if (tag != null) {
         SerializerVersion input = this.inputSerializerVersion();
         SerializerVersion output = this.outputSerializerVersion();
         Tag asTag = input.toTag(input.toComponent(tag.getValue()));
         this.processTag(connection, asTag);
         tag.setValue(output.toString(output.toComponent(asTag)));
      }
   }

   protected void handleItemArrayContents(UserConnection connection, CompoundTag tag, String key) {
      ListTag<CompoundTag> container = TagUtil.getNamespacedCompoundTagList(tag, key);
      if (container != null) {
         for(CompoundTag itemTag : container) {
            this.handleShowItem(connection, itemTag, itemTag.getCompoundTag("components"));
         }

      }
   }

   protected SerializerVersion inputSerializerVersion() {
      return null;
   }

   protected SerializerVersion outputSerializerVersion() {
      return null;
   }

   void convertLegacyContents(CompoundTag hoverEvent) {
      if (this.inputSerializerVersion() != null && this.outputSerializerVersion() != null) {
         Tag valueTag = hoverEvent.remove("value");
         if (valueTag != null) {
            CompoundTag tag = ComponentUtil.deserializeShowItem(valueTag, this.inputSerializerVersion());
            CompoundTag contentsTag = new CompoundTag();
            contentsTag.put("id", tag.getStringTag("id"));
            contentsTag.put("count", tag.getIntTag("count"));
            if (tag.get("tag") instanceof CompoundTag) {
               contentsTag.putString("tag", this.outputSerializerVersion().toSNBT(tag.getCompoundTag("tag")));
            }

            hoverEvent.put("contents", contentsTag);
         }

      }
   }

   public static enum ReadType {
      JSON,
      NBT;

      // $FF: synthetic method
      static ReadType[] $values() {
         return new ReadType[]{JSON, NBT};
      }
   }
}
