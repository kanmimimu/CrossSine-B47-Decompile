package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.google.common.primitives.Ints;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.NoteBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.BlockIdData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SpawnEggMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class BlockItemPacketRewriter1_13 extends BackwardsItemRewriter {
   final Map enchantmentMappings = new HashMap();
   final String extraNbtTag = this.nbtTagName("2");

   public BlockItemPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
      super(protocol, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
   }

   public static boolean isDamageable(int id) {
      return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
   }

   protected void registerPackets() {
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.COOLDOWN, (wrapper) -> {
         int itemId = (Integer)wrapper.read(Types.VAR_INT);
         int oldId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().getNewId(itemId);
         if (oldId == -1) {
            wrapper.cancel();
         } else if (SpawnEggMappings1_13.getEntityId(oldId).isPresent()) {
            wrapper.write(Types.VAR_INT, IdAndData.toRawData(383));
         } else {
            wrapper.write(Types.VAR_INT, IdAndData.getId(oldId));
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int blockId = (Integer)wrapper.get(Types.VAR_INT, 0);
               if (blockId == 73) {
                  blockId = 25;
               } else if (blockId == 99) {
                  blockId = 33;
               } else if (blockId == 92) {
                  blockId = 29;
               } else if (blockId == 142) {
                  blockId = 54;
               } else if (blockId == 305) {
                  blockId = 146;
               } else if (blockId == 249) {
                  blockId = 130;
               } else if (blockId == 257) {
                  blockId = 138;
               } else if (blockId == 140) {
                  blockId = 52;
               } else if (blockId == 472) {
                  blockId = 209;
               } else if (blockId >= 483 && blockId <= 498) {
                  blockId = blockId - 483 + 219;
               }

               if (blockId == 25) {
                  NoteBlockStorage noteBlockStorage = (NoteBlockStorage)wrapper.user().get(NoteBlockStorage.class);
                  BlockPosition position = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
                  Pair<Integer, Integer> update = noteBlockStorage.getNoteBlockUpdate(position);
                  if (update != null) {
                     wrapper.set(Types.UNSIGNED_BYTE, 0, ((Integer)update.key()).shortValue());
                     wrapper.set(Types.UNSIGNED_BYTE, 1, ((Integer)update.value()).shortValue());
                  }
               }

               wrapper.set(Types.VAR_INT, 0, blockId);
            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.NAMED_COMPOUND_TAG);
            this.handler((wrapper) -> {
               BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
               if ((Short)wrapper.get(Types.UNSIGNED_BYTE, 0) == 5) {
                  wrapper.cancel();
               }

               wrapper.set(Types.NAMED_COMPOUND_TAG, 0, provider.transform(wrapper.user(), (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0), (CompoundTag)wrapper.get(Types.NAMED_COMPOUND_TAG, 0)));
            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.FORGET_LEVEL_CHUNK, (wrapper) -> {
         int chunkMinX = (Integer)wrapper.passthrough(Types.INT) << 4;
         int chunkMinZ = (Integer)wrapper.passthrough(Types.INT) << 4;
         int chunkMaxX = chunkMinX + 15;
         int chunkMaxZ = chunkMinZ + 15;
         BackwardsBlockStorage blockStorage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
         blockStorage.getBlocks().entrySet().removeIf((entry) -> {
            BlockPosition position = (BlockPosition)entry.getKey();
            return position.x() >= chunkMinX && position.z() >= chunkMinZ && position.x() <= chunkMaxX && position.z() <= chunkMaxZ;
         });
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.BLOCK_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.handler((wrapper) -> {
               int blockState = (Integer)wrapper.read(Types.VAR_INT);
               BlockPosition position = (BlockPosition)wrapper.get(Types.BLOCK_POSITION1_8, 0);
               if (blockState >= 249 && blockState <= 748) {
                  ((NoteBlockStorage)wrapper.user().get(NoteBlockStorage.class)).storeNoteBlockUpdate(position, blockState);
               }

               BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);
               storage.checkAndStore(position, blockState);
               wrapper.write(Types.VAR_INT, ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(blockState));
               BlockItemPacketRewriter1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CHUNK_BLOCKS_UPDATE, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BLOCK_CHANGE_ARRAY);
            this.handler((wrapper) -> {
               BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);

               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Types.BLOCK_CHANGE_ARRAY, 0)) {
                  int chunkX = (Integer)wrapper.get(Types.INT, 0);
                  int chunkZ = (Integer)wrapper.get(Types.INT, 1);
                  int block = record.getBlockId();
                  BlockPosition position = new BlockPosition(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                  storage.checkAndStore(position, block);
                  BlockItemPacketRewriter1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                  record.setBlockId(((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(block));
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.ITEM1_13_SHORT_ARRAY, Types.ITEM1_8_SHORT_ARRAY);
            this.handler((wrapper) -> {
               Item[] items = (Item[])wrapper.get(Types.ITEM1_8_SHORT_ARRAY, 0);

               for(Item item : items) {
                  BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), item);
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.ITEM1_13, Types.ITEM1_8);
            this.handler((wrapper) -> BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_CHUNK, (wrapper) -> {
         ClientWorld clientWorld = wrapper.user().getClientWorld(Protocol1_13To1_12_2.class);
         ChunkType1_9_3 type_old = ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment());
         ChunkType1_13 type = ChunkType1_13.forEnvironment(clientWorld.getEnvironment());
         Chunk chunk = (Chunk)wrapper.read(type);
         BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
         BackwardsBlockStorage storage = (BackwardsBlockStorage)wrapper.user().get(BackwardsBlockStorage.class);

         for(CompoundTag tag : chunk.getBlockEntities()) {
            StringTag idTag = tag.getStringTag("id");
            if (idTag != null) {
               String id = idTag.getValue();
               if (provider.isHandled(id)) {
                  int sectionIndex = tag.getNumberTag("y").asInt() >> 4;
                  if (sectionIndex >= 0 && sectionIndex <= 15) {
                     ChunkSection section = chunk.getSections()[sectionIndex];
                     int x = tag.getNumberTag("x").asInt();
                     short y = tag.getNumberTag("y").asShort();
                     int z = tag.getNumberTag("z").asInt();
                     BlockPosition position = new BlockPosition(x, y, z);
                     int block = section.palette(PaletteType.BLOCKS).idAt(x & 15, y & 15, z & 15);
                     storage.checkAndStore(position, block);
                     provider.transform(wrapper.user(), position, tag);
                  }
               }
            }
         }

         for(int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               DataPalette palette = section.palette(PaletteType.BLOCKS);

               for(int y = 0; y < 16; ++y) {
                  for(int z = 0; z < 16; ++z) {
                     for(int x = 0; x < 16; ++x) {
                        int block = palette.idAt(x, y, z);
                        if (FlowerPotHandler.isFlowah(block)) {
                           BlockPosition pos = new BlockPosition(x + (chunk.getX() << 4), (short)(y + (i << 4)), z + (chunk.getZ() << 4));
                           storage.checkAndStore(pos, block);
                           CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
                           chunk.getBlockEntities().add(nbt);
                        }
                     }
                  }
               }

               for(int j = 0; j < palette.size(); ++j) {
                  int mappedBlockStateId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getNewBlockStateId(palette.idByIndex(j));
                  palette.setIdByIndex(j, mappedBlockStateId);
               }
            }
         }

         if (chunk.isBiomeData()) {
            for(int i = 0; i < 256; ++i) {
               int biome = chunk.getBiomeData()[i];
               byte var10000;
               switch (biome) {
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                     var10000 = 9;
                     break;
                  case 44:
                  case 45:
                  case 46:
                     var10000 = 0;
                     break;
                  case 47:
                  case 48:
                  case 49:
                     var10000 = 24;
                     break;
                  case 50:
                     var10000 = 10;
                     break;
                  default:
                     var10000 = -1;
               }

               int newId = var10000;
               if (newId != -1) {
                  chunk.getBiomeData()[i] = newId;
               }
            }
         }

         wrapper.write(type_old, chunk);
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.LEVEL_EVENT, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Types.INT, 0);
               int data = (Integer)wrapper.get(Types.INT, 1);
               if (id == 1010) {
                  wrapper.set(Types.INT, 1, ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getItemMappings().getNewId(data) >> 4);
               } else if (id == 2001) {
                  data = ((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getNewBlockStateId(data);
                  int blockId = data >> 4;
                  int blockData = data & 15;
                  wrapper.set(Types.INT, 1, blockId & 4095 | blockData << 12);
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.MAP_ITEM_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.BYTE);
            this.map(Types.BOOLEAN);
            this.handler((wrapper) -> {
               int iconCount = (Integer)wrapper.passthrough(Types.VAR_INT);

               for(int i = 0; i < iconCount; ++i) {
                  int type = (Integer)wrapper.read(Types.VAR_INT);
                  byte x = (Byte)wrapper.read(Types.BYTE);
                  byte z = (Byte)wrapper.read(Types.BYTE);
                  byte direction = (Byte)wrapper.read(Types.BYTE);
                  wrapper.read(Types.OPTIONAL_COMPONENT);
                  if (type > 9) {
                     wrapper.set(Types.VAR_INT, 1, (Integer)wrapper.get(Types.VAR_INT, 1) - 1);
                  } else {
                     wrapper.write(Types.BYTE, (byte)(type << 4 | direction & 15));
                     wrapper.write(Types.BYTE, x);
                     wrapper.write(Types.BYTE, z);
                  }
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.ITEM1_13, Types.ITEM1_8);
            this.handler((wrapper) -> BlockItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.CONTAINER_SET_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short property = (Short)wrapper.get(Types.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  short oldId = (Short)wrapper.get(Types.SHORT, 1);
                  wrapper.set(Types.SHORT, 1, (short)((Protocol1_13To1_12_2)BlockItemPacketRewriter1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId));
               }

            });
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.SET_CREATIVE_MODE_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(Types.ITEM1_8, Types.ITEM1_13);
            this.handler((wrapper) -> BlockItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_13, 0)));
         }
      });
      ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.VAR_INT);
            this.map(Types.ITEM1_8, Types.ITEM1_13);
            this.handler((wrapper) -> BlockItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_13, 0)));
         }
      });
   }

   protected void registerRewrites() {
      this.enchantmentMappings.put("minecraft:loyalty", "§7Loyalty");
      this.enchantmentMappings.put("minecraft:impaling", "§7Impaling");
      this.enchantmentMappings.put("minecraft:riptide", "§7Riptide");
      this.enchantmentMappings.put("minecraft:channeling", "§7Channeling");
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         int originalId = item.identifier();
         Integer rawId = null;
         boolean gotRawIdFromTag = false;
         CompoundTag tag = item.tag();
         Tag originalIdTag;
         if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) instanceof NumberTag) {
            rawId = ((NumberTag)originalIdTag).asInt();
            gotRawIdFromTag = true;
         }

         if (rawId == null) {
            super.handleItemToClient(connection, item);
            if (item.identifier() == -1) {
               if (originalId == 362) {
                  rawId = 15007744;
               } else {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Failed to get new item for " + originalId);
                  }

                  rawId = 65536;
               }
            } else {
               if (tag == null) {
                  tag = item.tag();
               }

               rawId = this.itemIdToRaw(item.identifier(), item, tag);
            }
         }

         item.setIdentifier(rawId >> 16);
         item.setData((short)(rawId & '\uffff'));
         if (tag != null) {
            if (isDamageable(item.identifier())) {
               Tag damageTag = tag.remove("Damage");
               if (!gotRawIdFromTag && damageTag instanceof NumberTag) {
                  item.setData(((NumberTag)damageTag).asShort());
               }
            }

            if (item.identifier() == 358) {
               Tag mapTag = tag.remove("map");
               if (!gotRawIdFromTag && mapTag instanceof NumberTag) {
                  item.setData(((NumberTag)mapTag).asShort());
               }
            }

            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = tag.getCompoundTag("display");
            if (display != null) {
               StringTag name = display.getStringTag("Name");
               if (name != null) {
                  String var13 = this.extraNbtTag;
                  display.putString(var13 + "|Name", name.getValue());
                  name.setValue(((Protocol1_13To1_12_2)this.protocol).jsonToLegacy(connection, name.getValue()));
               }
            }

            this.rewriteEnchantmentsToClient(tag, false);
            this.rewriteEnchantmentsToClient(tag, true);
            this.rewriteCanPlaceToClient(tag, "CanPlaceOn");
            this.rewriteCanPlaceToClient(tag, "CanDestroy");
         }

         return item;
      }
   }

   int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
      Optional<String> eggEntityId = SpawnEggMappings1_13.getEntityId(oldId);
      if (eggEntityId.isPresent()) {
         if (tag == null) {
            item.setTag(tag = new CompoundTag());
         }

         if (!tag.contains("EntityTag")) {
            CompoundTag entityTag = new CompoundTag();
            entityTag.putString("id", (String)eggEntityId.get());
            tag.put("EntityTag", entityTag);
         }

         return 25100288;
      } else {
         return oldId >> 4 << 16 | oldId & 15;
      }
   }

   void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
      ListTag<?> blockTag = tag.getListTag(tagName);
      if (blockTag != null) {
         ListTag<StringTag> newCanPlaceOn = new ListTag(StringTag.class);
         String var14 = this.extraNbtTag;
         tag.put(var14 + "|" + tagName, blockTag.copy());

         for(Tag oldTag : blockTag) {
            Object value = oldTag.getValue();
            String[] newValues = value instanceof String ? (String[])BlockIdData.fallbackReverseMapping.get(Key.stripMinecraftNamespace((String)value)) : null;
            if (newValues != null) {
               for(String newValue : newValues) {
                  newCanPlaceOn.add(new StringTag(newValue));
               }
            } else {
               newCanPlaceOn.add(new StringTag(oldTag.getValue().toString()));
            }
         }

         tag.put(tagName, newCanPlaceOn);
      }
   }

   void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
      String key = storedEnch ? "StoredEnchantments" : "Enchantments";
      ListTag<CompoundTag> enchantments = tag.getListTag(key, CompoundTag.class);
      if (enchantments != null) {
         ListTag<CompoundTag> noMapped = new ListTag(CompoundTag.class);
         ListTag<CompoundTag> newEnchantments = new ListTag(CompoundTag.class);
         List<StringTag> lore = new ArrayList();
         boolean hasValidEnchants = false;

         for(CompoundTag enchantmentEntry : enchantments.copy()) {
            StringTag idTag = enchantmentEntry.getStringTag("id");
            if (idTag != null) {
               String newId = idTag.getValue();
               NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
               if (levelTag != null) {
                  int levelValue = levelTag.asInt();
                  short level = levelValue < 32767 ? (short)levelValue : 32767;
                  String mappedEnchantmentId = (String)this.enchantmentMappings.get(newId);
                  if (mappedEnchantmentId != null) {
                     String var22 = EnchantmentRewriter.getRomanNumber(level);
                     lore.add(new StringTag(mappedEnchantmentId + " " + var22));
                     noMapped.add(enchantmentEntry);
                  } else if (!newId.isEmpty()) {
                     Short oldId = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get(Key.stripMinecraftNamespace(newId));
                     if (oldId == null) {
                        if (!newId.startsWith("viaversion:legacy/")) {
                           noMapped.add(enchantmentEntry);
                           if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                              String name = newId;
                              int index = newId.indexOf(58) + 1;
                              if (index != 0 && index != newId.length()) {
                                 name = newId.substring(index);
                              }

                              char var10000 = Character.toUpperCase(name.charAt(0));
                              String var25 = name.substring(1).toLowerCase(Locale.ENGLISH);
                              char var24 = var10000;
                              name = "§7" + var24 + var25;
                              String var28 = EnchantmentRewriter.getRomanNumber(level);
                              lore.add(new StringTag(name + " " + var28));
                           }

                           if (Via.getManager().isDebug()) {
                              ((Protocol1_13To1_12_2)this.protocol).getLogger().warning("Found unknown enchant: " + newId);
                           }
                           continue;
                        }

                        oldId = Short.valueOf(newId.substring(18));
                     }

                     if (level != 0) {
                        hasValidEnchants = true;
                     }

                     CompoundTag newEntry = new CompoundTag();
                     newEntry.putShort("id", oldId);
                     newEntry.putShort("lvl", level);
                     newEnchantments.add(newEntry);
                  }
               }
            }
         }

         if (!storedEnch && !hasValidEnchants) {
            NumberTag hideFlags = tag.getNumberTag("HideFlags");
            if (hideFlags == null) {
               hideFlags = new IntTag();
               String var32 = this.extraNbtTag;
               tag.put(var32 + "|DummyEnchant", new ByteTag(false));
            } else {
               String var34 = this.extraNbtTag;
               tag.putInt(var34 + "|OldHideFlags", hideFlags.asByte());
            }

            if (newEnchantments.isEmpty()) {
               CompoundTag enchEntry = new CompoundTag();
               enchEntry.putShort("id", (short)0);
               enchEntry.putShort("lvl", (short)0);
               newEnchantments.add(enchEntry);
            }

            int value = hideFlags.asByte() | 1;
            tag.putInt("HideFlags", value);
         }

         if (!noMapped.isEmpty()) {
            String var36 = this.extraNbtTag;
            tag.put(var36 + "|" + key, noMapped);
            if (!lore.isEmpty()) {
               CompoundTag display = tag.getCompoundTag("display");
               if (display == null) {
                  tag.put("display", display = new CompoundTag());
               }

               ListTag<StringTag> loreTag = display.getListTag("Lore", StringTag.class);
               if (loreTag == null) {
                  display.put("Lore", loreTag = new ListTag(StringTag.class));
                  String var39 = this.extraNbtTag;
                  tag.put(var39 + "|DummyLore", new ByteTag(false));
               } else if (!loreTag.isEmpty()) {
                  ListTag<StringTag> oldLore = new ListTag(StringTag.class);

                  for(StringTag value : loreTag) {
                     oldLore.add(value.copy());
                  }

                  String var41 = this.extraNbtTag;
                  tag.put(var41 + "|OldLore", oldLore);
                  lore.addAll(loreTag.getValue());
               }

               loreTag.setValue(lore);
            }
         }

         tag.remove("Enchantments");
         tag.put(storedEnch ? key : "ench", newEnchantments);
      }
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag tag = item.tag();
         int originalId = item.identifier() << 16 | item.data() & '\uffff';
         int rawId = IdAndData.toRawData(item.identifier(), item.data());
         if (isDamageable(item.identifier())) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            tag.putInt("Damage", item.data());
         }

         if (item.identifier() == 358) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            tag.putInt("map", item.data());
         }

         if (tag != null) {
            this.invertShieldAndBannerId(item, tag);
            CompoundTag display = tag.getCompoundTag("display");
            if (display != null) {
               StringTag name = display.getStringTag("Name");
               if (name != null) {
                  String var10 = this.extraNbtTag;
                  Tag via = display.remove(var10 + "|Name");
                  name.setValue(via instanceof StringTag ? ((StringTag)via).getValue() : ComponentUtil.legacyToJsonString(name.getValue()));
               }
            }

            this.rewriteEnchantmentsToServer(tag, false);
            this.rewriteEnchantmentsToServer(tag, true);
            this.rewriteCanPlaceToServer(tag, "CanPlaceOn");
            this.rewriteCanPlaceToServer(tag, "CanDestroy");
            if (item.identifier() == 383) {
               CompoundTag entityTag = tag.getCompoundTag("EntityTag");
               StringTag identifier;
               if (entityTag != null && (identifier = entityTag.getStringTag("id")) != null) {
                  rawId = SpawnEggMappings1_13.getSpawnEggId(identifier.getValue());
                  if (rawId == -1) {
                     rawId = 25100288;
                  } else {
                     entityTag.remove("id");
                     if (entityTag.isEmpty()) {
                        tag.remove("EntityTag");
                     }
                  }
               } else {
                  rawId = 25100288;
               }
            }

            if (tag.isEmpty()) {
               tag = null;
               item.setTag((CompoundTag)null);
            }
         }

         int identifier = item.identifier();
         item.setIdentifier(rawId);
         super.handleItemToServer(connection, item);
         if (item.identifier() != rawId && item.identifier() != -1) {
            return item;
         } else {
            item.setIdentifier(identifier);
            int newId = -1;
            if (((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId) == -1) {
               if (!isDamageable(item.identifier()) && item.identifier() != 358) {
                  if (tag == null) {
                     item.setTag(tag = new CompoundTag());
                  }

                  tag.putInt(this.extraNbtTag, originalId);
               }

               if (item.identifier() == 229) {
                  newId = 362;
               } else if (item.identifier() == 31 && item.data() == 0) {
                  rawId = IdAndData.toRawData(32);
               } else if (((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId & -16) != -1) {
                  rawId &= -16;
               } else {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ProtocolLogger var10000 = ((Protocol1_13To1_12_2)this.protocol).getLogger();
                     int var12 = item.identifier();
                     var10000.warning("Failed to get old item for " + var12);
                  }

                  rawId = 16;
               }
            }

            if (newId == -1) {
               newId = ((Protocol1_13To1_12_2)this.protocol).getMappingData().getItemMappings().inverse().getNewId(rawId);
            }

            item.setIdentifier(newId);
            item.setData((short)0);
            return item;
         }
      }
   }

   void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
      if (tag.getListTag(tagName) != null) {
         String var18 = this.extraNbtTag;
         ListTag<?> blockTag = tag.getListTag(var18 + "|" + tagName);
         if (blockTag != null) {
            String var21 = this.extraNbtTag;
            tag.remove(var21 + "|" + tagName);
            tag.put(tagName, blockTag.copy());
         } else if ((blockTag = tag.getListTag(tagName)) != null) {
            ListTag<StringTag> newCanPlaceOn = new ListTag(StringTag.class);

            for(Tag oldTag : blockTag) {
               Object value = oldTag.getValue();
               String oldId = Key.stripMinecraftNamespace(value.toString());
               int key = Ints.tryParse(oldId);
               String numberConverted = (String)BlockIdData.numberIdToString.get(key);
               if (numberConverted != null) {
                  oldId = numberConverted;
               }

               String lowerCaseId = oldId.toLowerCase(Locale.ROOT);
               String[] newValues = (String[])BlockIdData.blockIdMapping.get(lowerCaseId);
               if (newValues != null) {
                  for(String newValue : newValues) {
                     newCanPlaceOn.add(new StringTag(newValue));
                  }
               } else {
                  newCanPlaceOn.add(new StringTag(lowerCaseId));
               }
            }

            tag.put(tagName, newCanPlaceOn);
         }

      }
   }

   void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
      String key = storedEnch ? "StoredEnchantments" : "Enchantments";
      ListTag<CompoundTag> enchantments = tag.getListTag(storedEnch ? key : "ench", CompoundTag.class);
      if (enchantments != null) {
         ListTag<CompoundTag> newEnchantments = new ListTag(CompoundTag.class);
         boolean dummyEnchant = false;
         if (!storedEnch) {
            String var16 = this.extraNbtTag;
            Tag hideFlags = tag.remove(var16 + "|OldHideFlags");
            if (hideFlags instanceof IntTag) {
               tag.putInt("HideFlags", ((NumberTag)hideFlags).asByte());
               dummyEnchant = true;
            } else {
               String var18 = this.extraNbtTag;
               if (tag.remove(var18 + "|DummyEnchant") != null) {
                  tag.remove("HideFlags");
                  dummyEnchant = true;
               }
            }
         }

         for(CompoundTag entryTag : enchantments) {
            NumberTag idTag = entryTag.getNumberTag("id");
            NumberTag levelTag = entryTag.getNumberTag("lvl");
            CompoundTag enchantmentEntry = new CompoundTag();
            short oldId = idTag != null ? idTag.asShort() : 0;
            short level = levelTag != null ? levelTag.asShort() : 0;
            if (!dummyEnchant || oldId != 0 || level != 0) {
               String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get(oldId);
               if (newId == null) {
                  newId = (new StringBuilder()).append("viaversion:legacy/").append(oldId).toString();
               }

               enchantmentEntry.putString("id", newId);
               enchantmentEntry.putShort("lvl", level);
               newEnchantments.add(enchantmentEntry);
            }
         }

         String var22 = this.extraNbtTag;
         ListTag<CompoundTag> noMapped = tag.getListTag(var22 + "|Enchantments", CompoundTag.class);
         if (noMapped != null) {
            for(CompoundTag value : noMapped) {
               newEnchantments.add(value);
            }

            String var24 = this.extraNbtTag;
            tag.remove(var24 + "|Enchantments");
         }

         CompoundTag display = tag.getCompoundTag("display");
         if (display == null) {
            tag.put("display", display = new CompoundTag());
         }

         String var26 = this.extraNbtTag;
         ListTag<StringTag> oldLore = tag.getListTag(var26 + "|OldLore", StringTag.class);
         if (oldLore != null) {
            ListTag<StringTag> lore = display.getListTag("Lore", StringTag.class);
            if (lore == null) {
               tag.put("Lore", lore = new ListTag(StringTag.class));
            }

            lore.setValue(oldLore.getValue());
            String var28 = this.extraNbtTag;
            tag.remove(var28 + "|OldLore");
         } else {
            String var30 = this.extraNbtTag;
            if (tag.remove(var30 + "|DummyLore") != null) {
               display.remove("Lore");
               if (display.isEmpty()) {
                  tag.remove("display");
               }
            }
         }

         if (!storedEnch) {
            tag.remove("ench");
         }

         tag.put(key, newEnchantments);
      }
   }

   void invertShieldAndBannerId(Item item, CompoundTag tag) {
      if (item.identifier() == 442 || item.identifier() == 425) {
         CompoundTag blockEntityTag = tag.getCompoundTag("BlockEntityTag");
         if (blockEntityTag != null) {
            NumberTag base = blockEntityTag.getNumberTag("Base");
            if (base != null) {
               blockEntityTag.putInt("Base", 15 - base.asInt());
            }

            ListTag<CompoundTag> patterns = blockEntityTag.getListTag("Patterns", CompoundTag.class);
            if (patterns != null) {
               for(CompoundTag pattern : patterns) {
                  NumberTag colorTag = pattern.getNumberTag("Color");
                  pattern.putInt("Color", 15 - colorTag.asInt());
               }
            }

         }
      }
   }

   static void flowerPotSpecialTreatment(UserConnection user, int blockState, BlockPosition position) {
      if (FlowerPotHandler.isFlowah(blockState)) {
         BackwardsBlockEntityProvider beProvider = (BackwardsBlockEntityProvider)Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
         CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
         PacketWrapper blockUpdateRemove = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_UPDATE, (UserConnection)user);
         blockUpdateRemove.write(Types.BLOCK_POSITION1_8, position);
         blockUpdateRemove.write(Types.VAR_INT, 0);
         blockUpdateRemove.scheduleSend(Protocol1_13To1_12_2.class);
         PacketWrapper blockCreate = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_UPDATE, (UserConnection)user);
         blockCreate.write(Types.BLOCK_POSITION1_8, position);
         blockCreate.write(Types.VAR_INT, Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(blockState));
         blockCreate.scheduleSend(Protocol1_13To1_12_2.class);
         PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, (UserConnection)user);
         wrapper.write(Types.BLOCK_POSITION1_8, position);
         wrapper.write(Types.UNSIGNED_BYTE, Short.valueOf((short)5));
         wrapper.write(Types.NAMED_COMPOUND_TAG, nbt);
         wrapper.scheduleSend(Protocol1_13To1_12_2.class);
      }

   }
}
