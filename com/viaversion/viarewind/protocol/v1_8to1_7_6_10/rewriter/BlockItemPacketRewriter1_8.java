package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viarewind.api.rewriter.VRBlockItemRewriter;
import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.EntityTracker1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.UUID;

public class BlockItemPacketRewriter1_8 extends VRBlockItemRewriter {
   LegacyEnchantmentRewriter enchantmentRewriter;

   public BlockItemPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
      super(protocol, "1.8");
   }

   protected void registerPackets() {
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.OPEN_SCREEN, (wrapper) -> {
         InventoryTracker windowTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         short windowTypeId = InventoryTracker.getInventoryType((String)wrapper.read(Types.STRING));
         windowTracker.getWindowTypeMap().put(windowId, windowTypeId);
         wrapper.write(Types.UNSIGNED_BYTE, windowTypeId);
         JsonElement titleComponent = (JsonElement)wrapper.read(Types.COMPONENT);
         String title = ChatUtil.jsonToLegacy(titleComponent);
         title = ChatUtil.removeUnusedColor(title, '8');
         if (title.length() > 32) {
            title = title.substring(0, 32);
         }

         wrapper.write(Types.STRING, title);
         wrapper.passthrough(Types.UNSIGNED_BYTE);
         wrapper.write(Types.BOOLEAN, true);
         if (windowTypeId == 11) {
            wrapper.passthrough(Types.INT);
         }

      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_CLOSE, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         ((InventoryTracker)wrapper.user().get(InventoryTracker.class)).remove(windowId);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short windowType = ((InventoryTracker)wrapper.user().get(InventoryTracker.class)).get((Short)wrapper.get(Types.UNSIGNED_BYTE, 0));
               short slot = (Short)wrapper.get(Types.SHORT, 0);
               if (windowType == 4) {
                  if (slot == 1) {
                     wrapper.cancel();
                  } else if (slot >= 2) {
                     wrapper.set(Types.SHORT, 0, (short)(slot - 1));
                  }
               }

            });
            this.map(Types.ITEM1_8, RewindTypes.COMPRESSED_NBT_ITEM);
            this.handler((wrapper) -> BlockItemPacketRewriter1_8.this.handleItemToClient(wrapper.user(), (Item)wrapper.get(RewindTypes.COMPRESSED_NBT_ITEM, 0)));
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (windowId == 0) {
                  short slot = (Short)wrapper.get(Types.SHORT, 0);
                  if (slot >= 5 && slot <= 8) {
                     PlayerSessionStorage playerSession = (PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class);
                     Item item = (Item)wrapper.get(RewindTypes.COMPRESSED_NBT_ITEM, 0);
                     playerSession.setPlayerEquipment(wrapper.user().getProtocolInfo().getUuid(), item, 8 - slot);
                     EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                     if (tracker.isSpectator()) {
                        wrapper.cancel();
                     }

                  }
               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.handler((wrapper) -> {
               short windowType = ((InventoryTracker)wrapper.user().get(InventoryTracker.class)).get((Short)wrapper.get(Types.UNSIGNED_BYTE, 0));
               Item[] items = (Item[])wrapper.read(Types.ITEM1_8_SHORT_ARRAY);
               if (windowType == 4) {
                  Item[] old = items;
                  items = new Item[items.length - 1];
                  items[0] = old[0];
                  System.arraycopy(old, 2, items, 1, old.length - 3);
               }

               for(int i = 0; i < items.length; ++i) {
                  items[i] = BlockItemPacketRewriter1_8.this.handleItemToClient(wrapper.user(), items[i]);
               }

               wrapper.write(RewindTypes.COMPRESSED_NBT_ITEM_ARRAY, items);
            });
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               if (windowId == 0) {
                  EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                  UUID userId = wrapper.user().getProtocolInfo().getUuid();
                  Item[] items = (Item[])wrapper.get(RewindTypes.COMPRESSED_NBT_ITEM_ARRAY, 0);

                  for(int i = 5; i < 9; ++i) {
                     ((PlayerSessionStorage)wrapper.user().get(PlayerSessionStorage.class)).setPlayerEquipment(userId, items[i], 8 - i);
                     if (tracker.isSpectator()) {
                        items[i] = null;
                     }
                  }

                  if (tracker.isSpectator()) {
                     GameProfileStorage.GameProfile profile = ((GameProfileStorage)wrapper.user().get(GameProfileStorage.class)).get(userId);
                     if (profile != null) {
                        items[5] = profile.getSkull();
                     }
                  }

               }
            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.CONTAINER_SET_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               InventoryTracker windowTracker = (InventoryTracker)wrapper.user().get(InventoryTracker.class);
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               short windowType = windowTracker.get(windowId);
               short progressBarId = (Short)wrapper.get(Types.SHORT, 0);
               short progress = (Short)wrapper.get(Types.SHORT, 1);
               if (windowType == 2) {
                  InventoryTracker.FurnaceData furnace = (InventoryTracker.FurnaceData)windowTracker.getFurnaceData().computeIfAbsent(windowId, (x) -> new InventoryTracker.FurnaceData());
                  if (progressBarId != 0 && progressBarId != 1) {
                     if (progressBarId == 2 || progressBarId == 3) {
                        if (progressBarId == 2) {
                           furnace.progress = progress;
                        } else {
                           furnace.maxProgress = progress;
                        }

                        if (furnace.maxProgress == 0) {
                           wrapper.cancel();
                           return;
                        }

                        progress = (short)(200 * furnace.progress / furnace.maxProgress);
                        wrapper.set(Types.SHORT, 0, Short.valueOf((short)0));
                        wrapper.set(Types.SHORT, 1, progress);
                     }
                  } else {
                     if (progressBarId == 0) {
                        furnace.fuelLeft = progress;
                     } else {
                        furnace.maxFuel = progress;
                     }

                     if (furnace.maxFuel == 0) {
                        wrapper.cancel();
                        return;
                     }

                     progress = (short)(200 * furnace.fuelLeft / furnace.maxFuel);
                     wrapper.set(Types.SHORT, 0, Short.valueOf((short)1));
                     wrapper.set(Types.SHORT, 1, progress);
                  }
               } else if (windowType == 4 && progressBarId > 2) {
                  wrapper.cancel();
               } else if (windowType == 8) {
                  windowTracker.levelCost = progress;
                  windowTracker.anvilId = windowId;
               }

            });
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CONTAINER_CLOSE, (wrapper) -> {
         short windowId = (Short)wrapper.passthrough(Types.UNSIGNED_BYTE);
         ((InventoryTracker)wrapper.user().get(InventoryTracker.class)).remove(windowId);
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.BYTE, Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short windowId = (Short)wrapper.get(Types.UNSIGNED_BYTE, 0);
               short slot = (Short)wrapper.get(Types.SHORT, 0);
               short windowType = ((InventoryTracker)wrapper.user().get(InventoryTracker.class)).get(windowId);
               if (windowType == 4 && slot > 0) {
                  wrapper.set(Types.SHORT, 0, (short)(slot + 1));
               }

            });
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(RewindTypes.COMPRESSED_NBT_ITEM, Types.ITEM1_8);
            this.handler((wrapper) -> BlockItemPacketRewriter1_8.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_8To1_7_6_10)this.protocol).registerServerbound(ServerboundPackets1_7_2_5.SET_CREATIVE_MODE_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(RewindTypes.COMPRESSED_NBT_ITEM, Types.ITEM1_8);
            this.handler((wrapper) -> BlockItemPacketRewriter1_8.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
   }

   protected void registerRewrites() {
      this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName(), false);
      this.enchantmentRewriter.registerEnchantment(8, "§7Depth Strider");
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToClient(connection, item);
         CompoundTag tag = item.tag();
         if (tag == null) {
            return item;
         } else {
            this.enchantmentRewriter.handleToClient(item);
            if (item.identifier() == 387) {
               ListTag<StringTag> pages = tag.getListTag("pages", StringTag.class);
               if (pages == null) {
                  return item;
               }

               ListTag<StringTag> oldPages = new ListTag(StringTag.class);
               String var10 = this.nbtTagName();
               tag.put(var10 + "|pages", oldPages);

               for(StringTag page : pages) {
                  String value = page.getValue();
                  oldPages.add(new StringTag(value));
                  page.setValue(ChatUtil.jsonToLegacy(value));
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
         super.handleItemToServer(connection, item);
         CompoundTag tag = item.tag();
         if (tag == null) {
            return item;
         } else {
            this.enchantmentRewriter.handleToServer(item);
            if (item.identifier() == 387) {
               String var6 = this.nbtTagName();
               ListTag<StringTag> oldPages = tag.getListTag(var6 + "|pages", StringTag.class);
               if (oldPages != null) {
                  tag.remove("pages");
                  tag.put("pages", oldPages);
               }
            }

            return item;
         }
      }
   }
}
