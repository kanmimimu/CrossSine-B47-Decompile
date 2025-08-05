package com.viaversion.viarewind.protocol.v1_9to1_8.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viarewind.api.rewriter.VRBlockItemRewriter;
import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.data.PotionIdMappings1_8;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityIds1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.PotionIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.util.Key;
import java.util.HashSet;
import java.util.Set;

public class BlockItemPacketRewriter1_9 extends VRBlockItemRewriter {
   public final Set VALID_ATTRIBUTES = new HashSet();
   LegacyEnchantmentRewriter enchantmentRewriter;

   public BlockItemPacketRewriter1_9(Protocol1_9To1_8 protocol) {
      super(protocol, "1.9");
      this.VALID_ATTRIBUTES.add("generic.maxHealth");
      this.VALID_ATTRIBUTES.add("generic.followRange");
      this.VALID_ATTRIBUTES.add("generic.knockbackResistance");
      this.VALID_ATTRIBUTES.add("generic.movementSpeed");
      this.VALID_ATTRIBUTES.add("generic.attackDamage");
      this.VALID_ATTRIBUTES.add("horse.jumpStrength");
      this.VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
   }

   protected void registerPackets() {
      this.registerBlockChange(ClientboundPackets1_9.BLOCK_UPDATE);
      this.registerMultiBlockChange(ClientboundPackets1_9.CHUNK_BLOCKS_UPDATE);
      this.registerSetCreativeModeSlot(ServerboundPackets1_8.SET_CREATIVE_MODE_SLOT);
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_CLOSE, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         WindowTracker tracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
         String windowType = tracker.get(windowId);
         if (windowType != null && windowType.equalsIgnoreCase("minecraft:enchanting_table")) {
            tracker.clearEnchantmentProperties();
         }

         tracker.remove(windowId);
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.OPEN_SCREEN, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         String windowType = (String)wrapper.passthrough(Types.STRING);
         ((WindowTracker)wrapper.user().get(WindowTracker.class)).put(windowId, windowType);
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_CONTENT, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         Item[] items = (Item[])wrapper.read(Types.ITEM1_8_SHORT_ARRAY);

         for(int i = 0; i < items.length; ++i) {
            items[i] = this.handleItemToClient(wrapper.user(), items[i]);
         }

         if (windowId == 0 && items.length == 46) {
            Item[] old = items;
            items = new Item[45];
            System.arraycopy(old, 0, items, 0, 45);
         } else {
            String type = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
            if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
               System.arraycopy(items, 0, ((WindowTracker)wrapper.user().get(WindowTracker.class)).getBrewingItems(windowId), 0, 4);
               WindowTracker.updateBrewingStand(wrapper.user(), items[4], windowId);
               Item[] old = items;
               items = new Item[items.length - 1];
               System.arraycopy(old, 0, items, 0, 4);
               System.arraycopy(old, 5, items, 4, old.length - 5);
            }
         }

         wrapper.write(Types.ITEM1_8_SHORT_ARRAY, items);
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_SLOT, (wrapper) -> {
         byte windowId = ((Short)wrapper.passthrough(Types.UNSIGNED_BYTE)).byteValue();
         short slot = (Short)wrapper.passthrough(Types.SHORT);
         Item item = (Item)wrapper.passthrough(Types.ITEM1_8);
         this.handleItemToClient(wrapper.user(), item);
         if (windowId == 0 && slot == 45) {
            wrapper.cancel();
         } else {
            WindowTracker windowTracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
            String windowType = windowTracker.get((short)windowId);
            if (windowType != null && windowType.equalsIgnoreCase("minecraft:brewing_stand")) {
               if (slot > 4) {
                  wrapper.set(Types.SHORT, 0, (short)(slot - 1));
               } else if (slot == 4) {
                  wrapper.cancel();
                  WindowTracker.updateBrewingStand(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0), (short)windowId);
               } else {
                  windowTracker.getBrewingItems((short)windowId)[slot] = (Item)wrapper.get(Types.ITEM1_8, 0);
               }
            }

         }
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CONTAINER_CLOSE, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         ((WindowTracker)wrapper.user().get(WindowTracker.class)).remove(windowId);
      });
      ((Protocol1_9To1_8)this.protocol).registerServerbound(ServerboundPackets1_8.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE, Types.VAR_INT);
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> BlockItemPacketRewriter1_9.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               String windowType = ((WindowTracker)wrapper.user().get(WindowTracker.class)).get(windowId);
               if (windowType != null && windowType.equalsIgnoreCase("minecraft:brewing_stand")) {
                  short slot = (Short)wrapper.get(Types.SHORT, 0);
                  if (slot > 3) {
                     wrapper.set(Types.SHORT, 0, (short)(slot + 1));
                  }
               }

            });
         }
      });
      ((Protocol1_9To1_8)this.protocol).registerClientbound(ClientboundPackets1_9.CONTAINER_SET_DATA, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         short key = (Short)wrapper.read(Types.SHORT);
         short value = (Short)wrapper.read(Types.SHORT);
         WindowTracker tracker = (WindowTracker)wrapper.user().get(WindowTracker.class);
         String windowType = tracker.get(windowId);
         if (windowType != null && windowType.equalsIgnoreCase("minecraft:enchanting_table")) {
            if (key >= 4 && key <= 6) {
               tracker.putEnchantmentProperty(key, value);
               wrapper.cancel();
            } else if (key >= 7 && key <= 9) {
               key = (short)(key - 3);
               short property = tracker.getEnchantmentValue(key);
               value = (short)(property | value << 8);
            }
         }

         wrapper.write(Types.SHORT, key);
         wrapper.write(Types.SHORT, value);
      });
   }

   protected void registerRewrites() {
      this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName());
      this.enchantmentRewriter.registerEnchantment(9, "§7Frost Walker");
      this.enchantmentRewriter.registerEnchantment(70, "§7Mending");
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToClient(connection, item);
         CompoundTag tag = item.tag();
         this.enchantmentRewriter.handleToClient(item);
         CompoundTag displayTag = tag == null ? null : tag.getCompoundTag("display");
         if (item.data() != 0) {
            ByteTag unbreakableTag = tag == null ? null : tag.getByteTag("Unbreakable");
            if (unbreakableTag != null && unbreakableTag.asByte() != 0) {
               String var12 = this.nbtTagName();
               tag.put(var12 + "|Unbreakable", new ByteTag(unbreakableTag.asByte()));
               tag.remove("Unbreakable");
               if (displayTag == null) {
                  tag.put("display", displayTag = new CompoundTag());
                  String var14 = this.nbtTagName();
                  tag.put(var14 + "|noDisplay", new ByteTag(true));
               }

               ListTag<StringTag> loreTag = displayTag.getListTag("Lore", StringTag.class);
               if (loreTag == null) {
                  displayTag.put("Lore", loreTag = new ListTag(StringTag.class));
               }

               loreTag.add(new StringTag("§9Unbreakable"));
            }
         }

         if (item.identifier() == 383 && item.data() == 0) {
            int data = 0;
            CompoundTag entityTag = tag == null ? null : tag.getCompoundTag("EntityTag");
            if (entityTag != null) {
               StringTag idTag = entityTag.getStringTag("id");
               if (idTag != null) {
                  String id = idTag.getValue();
                  if (EntityIds1_8.ENTITY_NAME_TO_ID.containsKey(id)) {
                     data = (Integer)EntityIds1_8.ENTITY_NAME_TO_ID.get(id);
                  } else if (displayTag == null) {
                     tag.put("display", displayTag = new CompoundTag());
                     String var16 = this.nbtTagName();
                     tag.put(var16 + "|noDisplay", new ByteTag(true));
                     displayTag.put("Name", new StringTag("§rSpawn " + id));
                  }
               }
            }

            item.setData((short)data);
         }

         boolean potion = item.identifier() == 373;
         boolean splashPotion = item.identifier() == 438;
         boolean lingeringPotion = item.identifier() == 441;
         if (potion || splashPotion || lingeringPotion) {
            int data = 0;
            StringTag potionTag = tag == null ? null : tag.getStringTag("Potion");
            if (potionTag != null) {
               String potionName = Key.stripMinecraftNamespace(potionTag.getValue());
               if (PotionIdMappings1_8.POTION_NAME_TO_ID.containsKey(potionName)) {
                  data = (Integer)PotionIdMappings1_8.POTION_NAME_TO_ID.get(potionName);
               }

               if (splashPotion) {
                  potionName = potionName + "_splash";
               } else if (lingeringPotion) {
                  potionName = potionName + "_lingering";
               }

               if ((displayTag == null || !displayTag.contains("Name")) && PotionIdMappings1_8.POTION_NAME_INDEX.containsKey(potionName)) {
                  tag.put("display", displayTag = new CompoundTag());
                  String var24 = this.nbtTagName();
                  tag.put(var24 + "|noDisplay", new ByteTag(true));
                  displayTag.put("Name", new StringTag((String)PotionIdMappings1_8.POTION_NAME_INDEX.get(potionName)));
               }
            }

            if (splashPotion || lingeringPotion) {
               item.setIdentifier(373);
               data += 8192;
            }

            item.setData((short)data);
         }

         ListTag<CompoundTag> attributeModifiers = tag == null ? null : tag.getListTag("AttributeModifiers", CompoundTag.class);
         if (attributeModifiers != null) {
            String var26 = this.nbtTagName();
            tag.put(var26 + "|AttributeModifiers", attributeModifiers.copy());
            attributeModifiers.getValue().removeIf((entries) -> {
               StringTag nameTag = entries.getStringTag("AttributeName");
               return nameTag != null && !this.VALID_ATTRIBUTES.contains(nameTag.getValue());
            });
         }

         return item;
      }
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(connection, item);
         CompoundTag tag = item.tag();
         this.enchantmentRewriter.handleToServer(item);
         if (item.identifier() == 383 && item.data() != 0) {
            if ((tag == null || !tag.contains("EntityTag")) && EntityIds1_8.ENTITY_ID_TO_NAME.containsKey(Integer.valueOf(item.data()))) {
               if (tag == null) {
                  item.setTag(tag = new CompoundTag());
               }

               CompoundTag entityTag = new CompoundTag();
               entityTag.put("id", new StringTag((String)EntityIds1_8.ENTITY_ID_TO_NAME.get(Integer.valueOf(item.data()))));
               tag.put("EntityTag", entityTag);
            }

            item.setData((short)0);
         }

         if (item.identifier() == 373 && (tag == null || !tag.contains("Potion"))) {
            if (item.data() >= 16384) {
               item.setIdentifier(438);
               item.setData((short)(item.data() - 8192));
            }

            if (tag == null) {
               item.setTag(tag = new CompoundTag());
               String name = item.data() == 8192 ? "water" : PotionIdMappings1_9.potionNameFromDamage(item.data());
               tag.put("Potion", new StringTag("minecraft:" + name));
            }

            item.setData((short)0);
         }

         if (tag == null) {
            return item;
         } else {
            String var10 = this.nbtTagName();
            Tag noDisplayTag = tag.remove(var10 + "|noDisplay");
            if (noDisplayTag != null) {
               tag.remove("display");
               if (tag.isEmpty()) {
                  item.setTag((CompoundTag)null);
               }
            }

            String var12 = this.nbtTagName();
            Tag unbreakableTag = tag.remove(var12 + "|Unbreakable");
            if (unbreakableTag != null) {
               tag.put("Unbreakable", unbreakableTag);
            }

            String var14 = this.nbtTagName();
            Tag attributeModifiersTag = tag.remove(var14 + "|AttributeModifiers");
            if (attributeModifiersTag != null) {
               tag.put("AttributeModifiers", attributeModifiersTag);
            }

            return item;
         }
      }
   }
}
