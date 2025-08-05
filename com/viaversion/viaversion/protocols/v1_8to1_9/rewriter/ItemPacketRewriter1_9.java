package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityIds1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.PotionIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.InventoryTracker;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemPacketRewriter1_9 extends ItemRewriter {
   public ItemPacketRewriter1_9(Protocol1_8To1_9 protocol) {
      super(protocol, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
   }

   protected void registerPackets() {
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               short property = (Short)wrapper.get(Types.SHORT, 0);
               short value = (Short)wrapper.get(Types.SHORT, 1);
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equalsIgnoreCase("minecraft:enchanting_table") && property > 3 && property < 7) {
                  short level = (short)(value >> 8);
                  short enchantID = (short)(value & 255);
                  wrapper.create(wrapper.getId(), (propertyPacket) -> {
                     propertyPacket.write(Types.UNSIGNED_BYTE, windowId);
                     propertyPacket.write(Types.SHORT, property);
                     propertyPacket.write(Types.SHORT, enchantID);
                  }).scheduleSend(Protocol1_8To1_9.class);
                  wrapper.set(Types.SHORT, 0, (short)(property + 3));
                  wrapper.set(Types.SHORT, 1, level);
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.OPEN_SCREEN, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.STRING);
            this.map(Types.STRING, Protocol1_8To1_9.STRING_TO_JSON);
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               String inventory = (String)wrapper.get(Types.STRING, 0);
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               inventoryTracker.setInventory(inventory);
            });
            this.handler((wrapper) -> {
               String inventory = (String)wrapper.get(Types.STRING, 0);
               if (inventory.equals("minecraft:brewing_stand")) {
                  wrapper.set(Types.UNSIGNED_BYTE, 1, (short)((Short)wrapper.get(Types.UNSIGNED_BYTE, 1) + 1));
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> {
               Item stack = (Item)wrapper.get(Types.ITEM1_8, 0);
               boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
               if (showShieldWhenSwordInHand) {
                  InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  short slotID = (Short)wrapper.get(Types.SHORT, 0);
                  byte windowId = ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0)).byteValue();
                  inventoryTracker.setItemId((short)windowId, slotID, stack == null ? 0 : stack.identifier());
                  entityTracker.syncShieldWithSword();
               }

               ItemPacketRewriter1_9.this.handleItemToClient(wrapper.user(), stack);
            });
            this.handler((wrapper) -> {
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               short slotID = (Short)wrapper.get(Types.SHORT, 0);
               if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand") && slotID >= 4) {
                  wrapper.set(Types.SHORT, 0, (short)(slotID + 1));
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.ITEM1_8_SHORT_ARRAY);
            this.handler((wrapper) -> {
               Item[] stacks = (Item[])wrapper.get(Types.ITEM1_8_SHORT_ARRAY, 0);
               Short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();

               for(short i = 0; i < stacks.length; ++i) {
                  Item stack = stacks[i];
                  if (showShieldWhenSwordInHand) {
                     inventoryTracker.setItemId(windowId, i, stack == null ? 0 : stack.identifier());
                  }

                  ItemPacketRewriter1_9.this.handleItemToClient(wrapper.user(), stack);
               }

               if (showShieldWhenSwordInHand) {
                  entityTracker.syncShieldWithSword();
               }

            });
            this.handler((wrapper) -> {
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                  Item[] oldStack = (Item[])wrapper.get(Types.ITEM1_8_SHORT_ARRAY, 0);
                  Item[] newStack = new Item[oldStack.length + 1];

                  for(int i = 0; i < newStack.length; ++i) {
                     if (i > 4) {
                        newStack[i] = oldStack[i - 1];
                     } else if (i != 4) {
                        newStack[i] = oldStack[i];
                     }
                  }

                  wrapper.set(Types.ITEM1_8_SHORT_ARRAY, 0, newStack);
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_CLOSE, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               inventoryTracker.setInventory((String)null);
               inventoryTracker.resetInventory((Short)wrapper.get(Types.UNSIGNED_BYTE, 0));
            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerClientbound(ClientboundPackets1_8.MAP_ITEM_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BYTE);
            this.handler((wrapper) -> wrapper.write(Types.BOOLEAN, true));
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerServerbound(ServerboundPackets1_9.SET_CREATIVE_MODE_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> {
               Item stack = (Item)wrapper.get(Types.ITEM1_8, 0);
               boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
               if (showShieldWhenSwordInHand) {
                  InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
                  EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                  short slotID = (Short)wrapper.get(Types.SHORT, 0);
                  inventoryTracker.setItemId((short)0, slotID, stack == null ? 0 : stack.identifier());
                  entityTracker.syncShieldWithSword();
               }

               ItemPacketRewriter1_9.this.handleItemToServer(wrapper.user(), stack);
            });
            this.handler((wrapper) -> {
               short slot = (Short)wrapper.get(Types.SHORT, 0);
               boolean throwItem = slot == 45;
               if (throwItem) {
                  wrapper.create(ClientboundPackets1_9.CONTAINER_SET_SLOT, (PacketHandler)((w) -> {
                     w.write(Types.UNSIGNED_BYTE, Short.valueOf((short)0));
                     w.write(Types.SHORT, slot);
                     w.write(Types.ITEM1_8, (Object)null);
                  })).send(Protocol1_8To1_9.class);
                  wrapper.set(Types.SHORT, 0, -999);
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerServerbound(ServerboundPackets1_9.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.VAR_INT, Types.BYTE);
            this.map(Types.ITEM1_8);
            this.handler((wrapper) -> {
               Item stack = (Item)wrapper.get(Types.ITEM1_8, 0);
               if (Via.getConfig().isShowShieldWhenSwordInHand()) {
                  Short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
                  byte mode = (Byte)wrapper.get(Types.BYTE, 1);
                  short hoverSlot = (Short)wrapper.get(Types.SHORT, 0);
                  byte button = (Byte)wrapper.get(Types.BYTE, 0);
                  InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
                  inventoryTracker.handleWindowClick(wrapper.user(), windowId, mode, hoverSlot, button);
               }

               ItemPacketRewriter1_9.this.handleItemToServer(wrapper.user(), stack);
            });
            this.handler((wrapper) -> {
               short windowID = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               short slot = (Short)wrapper.get(Types.SHORT, 0);
               boolean throwItem = slot == 45 && windowID == 0;
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                  if (slot == 4) {
                     throwItem = true;
                  }

                  if (slot > 4) {
                     wrapper.set(Types.SHORT, 0, (short)(slot - 1));
                  }
               }

               if (throwItem) {
                  wrapper.create(ClientboundPackets1_9.CONTAINER_SET_SLOT, (PacketHandler)((w) -> {
                     w.write(Types.UNSIGNED_BYTE, windowID);
                     w.write(Types.SHORT, slot);
                     w.write(Types.ITEM1_8, (Object)null);
                  })).scheduleSend(Protocol1_8To1_9.class);
                  wrapper.set(Types.BYTE, 0, (byte)0);
                  wrapper.set(Types.BYTE, 1, (byte)0);
                  wrapper.set(Types.SHORT, 0, -999);
               }

            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerServerbound(ServerboundPackets1_9.CONTAINER_CLOSE, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               InventoryTracker inventoryTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               inventoryTracker.setInventory((String)null);
               inventoryTracker.resetInventory((Short)wrapper.get(Types.UNSIGNED_BYTE, 0));
            });
         }
      });
      ((Protocol1_8To1_9)this.protocol).registerServerbound(ServerboundPackets1_9.SET_CARRIED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
               EntityTracker1_9 entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
               if (entityTracker.isBlocking()) {
                  entityTracker.setBlocking(false);
                  if (!showShieldWhenSwordInHand) {
                     entityTracker.setSecondHand((Item)null);
                  }
               }

               if (showShieldWhenSwordInHand) {
                  entityTracker.setHeldItemSlot((Short)wrapper.get(Types.SHORT, 0));
                  entityTracker.syncShieldWithSword();
               }

            });
         }
      });
   }

   public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (item.identifier() == 383 && item.data() != 0) {
            CompoundTag tag = item.tag();
            if (tag == null) {
               tag = new CompoundTag();
            }

            CompoundTag entityTag = new CompoundTag();
            String entityName = (String)EntityIds1_8.ENTITY_ID_TO_NAME.get(Integer.valueOf(item.data()));
            if (entityName != null) {
               StringTag id = new StringTag(entityName);
               entityTag.put("id", id);
               tag.put("EntityTag", entityTag);
            }

            item.setTag(tag);
            item.setData((short)0);
         }

         if (item.identifier() == 373) {
            CompoundTag tag = item.tag();
            if (tag == null) {
               tag = new CompoundTag();
            }

            if (item.data() >= 16384) {
               item.setIdentifier(438);
               item.setData((short)(item.data() - 8192));
            }

            String name = PotionIdMappings1_9.potionNameFromDamage(item.data());
            StringTag potion = new StringTag(Key.namespaced(name));
            tag.put("Potion", potion);
            item.setTag(tag);
            item.setData((short)0);
         }

         if (item.identifier() == 387) {
            CompoundTag tag = item.tag();
            if (tag == null) {
               tag = new CompoundTag();
            }

            ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
            tag.put(this.nbtTagName("pages"), pages == null ? new ListTag(StringTag.class) : pages.copy());
            if (pages == null) {
               pages = new ListTag(Collections.singletonList(new StringTag(ComponentUtil.emptyJsonComponent().toString())));
               tag.put("pages", pages);
            } else {
               for(int i = 0; i < pages.size(); ++i) {
                  StringTag page = (StringTag)pages.get(i);
                  page.setValue(ComponentUtil.convertJsonOrEmpty(page.getValue(), SerializerVersion.V1_8, SerializerVersion.V1_9).toString());
               }
            }

            item.setTag(tag);
         }

         return item;
      }
   }

   public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (item.identifier() == 383 && item.data() == 0) {
            CompoundTag tag = item.tag();
            int data = 0;
            if (tag != null && tag.getCompoundTag("EntityTag") != null) {
               CompoundTag entityTag = tag.getCompoundTag("EntityTag");
               StringTag id = entityTag.getStringTag("id");
               if (id != null && EntityIds1_8.ENTITY_NAME_TO_ID.containsKey(id.getValue())) {
                  data = (Integer)EntityIds1_8.ENTITY_NAME_TO_ID.get(id.getValue());
               }

               tag.remove("EntityTag");
            }

            item.setTag(tag);
            item.setData((short)data);
         }

         if (item.identifier() == 373) {
            CompoundTag tag = item.tag();
            int data = 0;
            if (tag != null && tag.getStringTag("Potion") != null) {
               StringTag potion = tag.getStringTag("Potion");
               String potionName = Key.stripMinecraftNamespace(potion.getValue());
               if (PotionIdMappings1_9.POTION_NAME_TO_ID.containsKey(potionName)) {
                  data = (Integer)PotionIdMappings1_9.POTION_NAME_TO_ID.get(potionName);
               }

               tag.remove("Potion");
            }

            item.setTag(tag);
            item.setData((short)data);
         }

         if (item.identifier() == 438) {
            CompoundTag tag = item.tag();
            int data = 0;
            item.setIdentifier(373);
            if (tag != null && tag.getStringTag("Potion") != null) {
               StringTag potion = tag.getStringTag("Potion");
               String potionName = Key.stripMinecraftNamespace(potion.getValue());
               if (PotionIdMappings1_9.POTION_NAME_TO_ID.containsKey(potionName)) {
                  data = (Integer)PotionIdMappings1_9.POTION_NAME_TO_ID.get(potionName) + 8192;
               }

               tag.remove("Potion");
            }

            item.setTag(tag);
            item.setData((short)data);
         }

         if (item.identifier() == 387) {
            CompoundTag tag = item.tag();
            if (tag != null) {
               ListTag<StringTag> backup = (ListTag)tag.removeUnchecked(this.nbtTagName("pages"));
               if (backup != null) {
                  if (!backup.isEmpty()) {
                     tag.put("pages", backup);
                  } else {
                     tag.remove("pages");
                     if (tag.isEmpty()) {
                        item.setTag((CompoundTag)null);
                     }
                  }
               } else {
                  ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
                  if (pages != null) {
                     for(int i = 0; i < pages.size(); ++i) {
                        StringTag page = (StringTag)pages.get(i);
                        page.setValue(ComponentUtil.convertJsonOrEmpty(page.getValue(), SerializerVersion.V1_9, SerializerVersion.V1_8).toString());
                     }
                  }
               }
            }
         }

         boolean newItem = item.identifier() >= 198 && item.identifier() <= 212;
         newItem |= item.identifier() == 397 && item.data() == 5;
         newItem |= item.identifier() >= 432 && item.identifier() <= 448;
         if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
         }

         return item;
      }
   }
}
