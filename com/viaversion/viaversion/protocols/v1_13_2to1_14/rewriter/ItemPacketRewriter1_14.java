package com.viaversion.viaversion.protocols.v1_13_2to1_14.rewriter;

import com.google.common.collect.Sets;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.DoubleTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ItemPacketRewriter1_14 extends ItemRewriter {
   static final Set REMOVED_RECIPE_TYPES = Sets.newHashSet(new String[]{"crafting_special_banneraddpattern", "crafting_special_repairitem"});
   static final ComponentRewriter COMPONENT_REWRITER;

   public ItemPacketRewriter1_14(Protocol1_13_2To1_14 protocol) {
      super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
   }

   public void registerPackets() {
      this.registerCooldown(ClientboundPackets1_13.COOLDOWN);
      this.registerAdvancements(ClientboundPackets1_13.UPDATE_ADVANCEMENTS);
      ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_13.OPEN_SCREEN, (ClientboundPacketType)null, (wrapper) -> {
         Short windowId = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         String type = (String)wrapper.read(Types.STRING);
         JsonElement title = (JsonElement)wrapper.read(Types.COMPONENT);
         COMPONENT_REWRITER.processText(wrapper.user(), title);
         Short slots = (Short)wrapper.read(Types.UNSIGNED_BYTE);
         if (type.equals("EntityHorse")) {
            wrapper.setPacketType(ClientboundPackets1_14.HORSE_SCREEN_OPEN);
            int entityId = (Integer)wrapper.read(Types.INT);
            wrapper.write(Types.UNSIGNED_BYTE, windowId);
            wrapper.write(Types.VAR_INT, slots.intValue());
            wrapper.write(Types.INT, entityId);
         } else {
            wrapper.setPacketType(ClientboundPackets1_14.OPEN_SCREEN);
            wrapper.write(Types.VAR_INT, windowId.intValue());
            int typeId = -1;
            switch (type) {
               case "minecraft:crafting_table":
                  typeId = 11;
                  break;
               case "minecraft:furnace":
                  typeId = 13;
                  break;
               case "minecraft:dropper":
               case "minecraft:dispenser":
                  typeId = 6;
                  break;
               case "minecraft:enchanting_table":
                  typeId = 12;
                  break;
               case "minecraft:brewing_stand":
                  typeId = 10;
                  break;
               case "minecraft:villager":
                  typeId = 18;
                  break;
               case "minecraft:beacon":
                  typeId = 8;
                  break;
               case "minecraft:anvil":
                  typeId = 7;
                  break;
               case "minecraft:hopper":
                  typeId = 15;
                  break;
               case "minecraft:shulker_box":
                  typeId = 19;
                  break;
               default:
                  if (slots > 0 && slots <= 54) {
                     typeId = slots / 9 - 1;
                  }
            }

            if (typeId == -1) {
               ((Protocol1_13_2To1_14)this.protocol).getLogger().warning("Can't open inventory for player! Type: " + type + " Size: " + slots);
            }

            wrapper.write(Types.VAR_INT, typeId);
            wrapper.write(Types.COMPONENT, title);
         }

      });
      this.registerSetContent(ClientboundPackets1_13.CONTAINER_SET_CONTENT);
      this.registerSetSlot(ClientboundPackets1_13.CONTAINER_SET_SLOT);
      ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = Key.namespaced((String)wrapper.get(Types.STRING, 0));
               if (channel.equals("minecraft:trader_list")) {
                  wrapper.setPacketType(ClientboundPackets1_14.MERCHANT_OFFERS);
                  wrapper.resetReader();
                  wrapper.read(Types.STRING);
                  int windowId = (Integer)wrapper.read(Types.INT);
                  EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class);
                  tracker.setLatestTradeWindowId(windowId);
                  wrapper.write(Types.VAR_INT, windowId);
                  int size = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);

                  for(int i = 0; i < size; ++i) {
                     ItemPacketRewriter1_14.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                     ItemPacketRewriter1_14.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                     boolean secondItem = (Boolean)wrapper.passthrough(Types.BOOLEAN);
                     if (secondItem) {
                        ItemPacketRewriter1_14.this.handleItemToClient(wrapper.user(), (Item)wrapper.passthrough(Types.ITEM1_13_2));
                     }

                     wrapper.passthrough(Types.BOOLEAN);
                     wrapper.passthrough(Types.INT);
                     wrapper.passthrough(Types.INT);
                     wrapper.write(Types.INT, 0);
                     wrapper.write(Types.INT, 0);
                     wrapper.write(Types.FLOAT, 0.0F);
                  }

                  wrapper.write(Types.VAR_INT, 0);
                  wrapper.write(Types.VAR_INT, 0);
                  wrapper.write(Types.BOOLEAN, false);
                  wrapper.clearInputBuffer();
               } else if (channel.equals("minecraft:book_open")) {
                  int hand = (Integer)wrapper.read(Types.VAR_INT);
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_14.OPEN_BOOK);
                  wrapper.write(Types.VAR_INT, hand);
               }

            });
         }
      });
      this.registerSetEquippedItem(ClientboundPackets1_13.SET_EQUIPPED_ITEM);
      RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter(this.protocol);
      ((Protocol1_13_2To1_14)this.protocol).registerClientbound(ClientboundPackets1_13.UPDATE_RECIPES, (wrapper) -> {
         int size = (Integer)wrapper.passthrough(Types.VAR_INT);
         int deleted = 0;

         for(int i = 0; i < size; ++i) {
            String id = (String)wrapper.read(Types.STRING);
            String type = (String)wrapper.read(Types.STRING);
            if (REMOVED_RECIPE_TYPES.contains(type)) {
               ++deleted;
            } else {
               wrapper.write(Types.STRING, type);
               wrapper.write(Types.STRING, id);
               recipeRewriter.handleRecipeType(wrapper, type);
            }
         }

         wrapper.set(Types.VAR_INT, 0, size - deleted);
      });
      this.registerContainerClick(ServerboundPackets1_14.CONTAINER_CLICK);
      ((Protocol1_13_2To1_14)this.protocol).registerServerbound(ServerboundPackets1_14.SELECT_TRADE, (wrapper) -> {
         PacketWrapper resyncPacket = wrapper.create(ServerboundPackets1_13.CONTAINER_CLICK);
         EntityTracker1_14 tracker = (EntityTracker1_14)wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class);
         resyncPacket.write(Types.UNSIGNED_BYTE, (short)tracker.getLatestTradeWindowId());
         resyncPacket.write(Types.SHORT, -999);
         resyncPacket.write(Types.BYTE, (byte)2);
         resyncPacket.write(Types.SHORT, (short)ThreadLocalRandom.current().nextInt());
         resyncPacket.write(Types.VAR_INT, 5);
         CompoundTag tag = new CompoundTag();
         tag.put("force_resync", new DoubleTag(Double.NaN));
         resyncPacket.write(Types.ITEM1_13_2, new DataItem(1, (byte)1, tag));
         resyncPacket.scheduleSendToServer(Protocol1_13_2To1_14.class);
      });
      this.registerSetCreativeModeSlot(ServerboundPackets1_14.SET_CREATIVE_MODE_SLOT);
      this.registerLevelParticles(ClientboundPackets1_13.LEVEL_PARTICLES, Types.FLOAT);
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_13_2To1_14.MAPPINGS.getNewItemId(item.identifier()));
         if (item.tag() == null) {
            return item;
         } else {
            CompoundTag display = item.tag().getCompoundTag("display");
            if (display != null) {
               ListTag<StringTag> lore = display.getListTag("Lore", StringTag.class);
               if (lore != null) {
                  display.put(this.nbtTagName("Lore"), lore.copy());

                  for(StringTag loreEntry : lore) {
                     String jsonText = ComponentUtil.legacyToJsonString(loreEntry.getValue(), true);
                     loreEntry.setValue(jsonText);
                  }
               }
            }

            return item;
         }
      }
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         item.setIdentifier(Protocol1_13_2To1_14.MAPPINGS.getOldItemId(item.identifier()));
         if (item.tag() == null) {
            return item;
         } else {
            CompoundTag display = item.tag().getCompoundTag("display");
            if (display != null) {
               ListTag<StringTag> lore = display.getListTag("Lore", StringTag.class);
               if (lore != null) {
                  Tag savedLore = display.remove(this.nbtTagName("Lore"));
                  if (savedLore instanceof ListTag) {
                     display.put("Lore", savedLore.copy());
                  } else {
                     for(StringTag loreEntry : lore) {
                        loreEntry.setValue(ComponentUtil.jsonToLegacy(loreEntry.getValue()));
                     }
                  }
               }
            }

            return item;
         }
      }
   }

   static {
      COMPONENT_REWRITER = new ComponentRewriter((Protocol)null, ComponentRewriter.ReadType.JSON) {
         protected void handleTranslate(JsonObject object, String translate) {
            super.handleTranslate(object, translate);
            if (translate.startsWith("block.") && translate.endsWith(".name")) {
               object.addProperty("translate", translate.substring(0, translate.length() - 5));
            }

         }
      };
   }
}
