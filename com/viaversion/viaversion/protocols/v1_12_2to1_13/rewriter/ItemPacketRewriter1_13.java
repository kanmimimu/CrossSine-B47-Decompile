package com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.BlockIdData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.MappingData1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SoundSource1_12_2;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.SpawnEggMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.ProtocolLogger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ItemPacketRewriter1_13 extends ItemRewriter {
   public ItemPacketRewriter1_13(Protocol1_12_2To1_13 protocol) {
      super(protocol, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY);
   }

   public void registerPackets() {
      ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.ITEM1_8, Types.ITEM1_13);
            this.handler((wrapper) -> ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_13, 0)));
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_CONTENT, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.ITEM1_8_SHORT_ARRAY, Types.ITEM1_13_SHORT_ARRAY);
            this.handler((wrapper) -> {
               Item[] items = (Item[])wrapper.get(Types.ITEM1_13_SHORT_ARRAY, 0);

               for(Item item : items) {
                  ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), item);
               }

            });
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CONTAINER_SET_DATA, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.SHORT);
            this.handler((wrapper) -> {
               short property = (Short)wrapper.get(Types.SHORT, 0);
               if (property >= 4 && property <= 6) {
                  wrapper.set(Types.SHORT, 1, (short)((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId((Short)wrapper.get(Types.SHORT, 1)));
               }

            });
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handlerSoftFail((wrapper) -> {
               String channel = (String)wrapper.get(Types.STRING, 0);
               if (channel.equals("MC|StopSound")) {
                  String originalSource = (String)wrapper.read(Types.STRING);
                  String originalSound = (String)wrapper.read(Types.STRING);
                  wrapper.clearPacket();
                  wrapper.setPacketType(ClientboundPackets1_13.STOP_SOUND);
                  byte flags = 0;
                  wrapper.write(Types.BYTE, flags);
                  if (!originalSource.isEmpty()) {
                     flags = (byte)(flags | 1);
                     Optional<SoundSource1_12_2> finalSource = SoundSource1_12_2.findBySource(originalSource);
                     if (finalSource.isPresent() ^ true) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                           Protocol1_12_2To1_13.LOGGER.warning("Could not handle unknown sound source " + originalSource + " falling back to default: master");
                        }

                        finalSource = Optional.of(SoundSource1_12_2.MASTER);
                     }

                     wrapper.write(Types.VAR_INT, ((SoundSource1_12_2)finalSource.get()).getId());
                  }

                  if (!originalSound.isEmpty()) {
                     flags = (byte)(flags | 2);
                     wrapper.write(Types.STRING, originalSound);
                  }

                  wrapper.set(Types.BYTE, 0, flags);
               } else {
                  if (channel.equals("MC|TrList")) {
                     channel = "minecraft:trader_list";
                     ItemPacketRewriter1_13.this.handleTradeList(wrapper);
                  } else {
                     channel = ItemPacketRewriter1_13.getNewPluginChannelId(channel);
                     if (channel == null) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                           ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring clientbound plugin message with channel: " + channel);
                        }

                        wrapper.cancel();
                        return;
                     }

                     if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                        String[] channels = (new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
                        List<String> rewrittenChannels = new ArrayList();

                        for(String s : channels) {
                           String rewritten = ItemPacketRewriter1_13.getNewPluginChannelId(s);
                           if (rewritten != null) {
                              rewrittenChannels.add(rewritten);
                           } else if (!Via.getConfig().isSuppressConversionWarnings()) {
                              ProtocolLogger var10000 = ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger();
                              String var16 = Key.stripMinecraftNamespace(channel).toUpperCase(Locale.ROOT);
                              var10000.warning("Ignoring plugin channel in clientbound " + var16 + ": " + s);
                           }
                        }

                        if (rewrittenChannels.isEmpty()) {
                           wrapper.cancel();
                           return;
                        }

                        wrapper.write(Types.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                     }
                  }

                  wrapper.set(Types.STRING, 0, channel);
               }
            });
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.SET_EQUIPPED_ITEM, new PacketHandlers() {
         public void register() {
            this.map(Types.VAR_INT);
            this.map(Types.VAR_INT);
            this.map(Types.ITEM1_8, Types.ITEM1_13);
            this.handler((wrapper) -> ItemPacketRewriter1_13.this.handleItemToClient(wrapper.user(), (Item)wrapper.get(Types.ITEM1_13, 0)));
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.CONTAINER_CLICK, new PacketHandlers() {
         public void register() {
            this.map(Types.UNSIGNED_BYTE);
            this.map(Types.SHORT);
            this.map(Types.BYTE);
            this.map(Types.SHORT);
            this.map(Types.VAR_INT);
            this.map(Types.ITEM1_13, Types.ITEM1_8);
            this.handler((wrapper) -> ItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.CUSTOM_PAYLOAD, new PacketHandlers() {
         public void register() {
            this.map(Types.STRING);
            this.handler((wrapper) -> {
               String channel = (String)wrapper.get(Types.STRING, 0);
               channel = ItemPacketRewriter1_13.getOldPluginChannelId(channel);
               if (channel == null) {
                  if (!Via.getConfig().isSuppressConversionWarnings()) {
                     ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring serverbound plugin message with channel: " + channel);
                  }

                  wrapper.cancel();
               } else {
                  if (channel.equals("REGISTER") || channel.equals("UNREGISTER")) {
                     String[] channels = (new String((byte[])wrapper.read(Types.REMAINING_BYTES), StandardCharsets.UTF_8)).split("\u0000");
                     List<String> rewrittenChannels = new ArrayList();

                     for(String s : channels) {
                        String rewritten = ItemPacketRewriter1_13.getOldPluginChannelId(s);
                        if (rewritten != null) {
                           rewrittenChannels.add(rewritten);
                        } else if (!Via.getConfig().isSuppressConversionWarnings()) {
                           ((Protocol1_12_2To1_13)ItemPacketRewriter1_13.this.protocol).getLogger().warning("Ignoring plugin channel in serverbound " + channel + ": " + s);
                        }
                     }

                     wrapper.write(Types.REMAINING_BYTES, Joiner.on('\u0000').join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                  }

                  wrapper.set(Types.STRING, 0, channel);
               }
            });
         }
      });
      ((Protocol1_12_2To1_13)this.protocol).registerServerbound(ServerboundPackets1_13.SET_CREATIVE_MODE_SLOT, new PacketHandlers() {
         public void register() {
            this.map(Types.SHORT);
            this.map(Types.ITEM1_13, Types.ITEM1_8);
            this.handler((wrapper) -> ItemPacketRewriter1_13.this.handleItemToServer(wrapper.user(), (Item)wrapper.get(Types.ITEM1_8, 0)));
         }
      });
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
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

            tag.put("Damage", new IntTag(item.data()));
         }

         if (item.identifier() == 358) {
            if (tag == null) {
               item.setTag(tag = new CompoundTag());
            }

            tag.put("map", new IntTag(item.data()));
         }

         if (tag != null) {
            boolean banner = item.identifier() == 425;
            if (banner || item.identifier() == 442) {
               CompoundTag blockEntityTag = tag.getCompoundTag("BlockEntityTag");
               if (blockEntityTag != null) {
                  NumberTag baseTag = blockEntityTag.getNumberTag("Base");
                  if (baseTag != null) {
                     if (banner) {
                        rawId = 6800 + baseTag.asInt();
                     }

                     blockEntityTag.putInt("Base", 15 - baseTag.asInt());
                  }

                  ListTag<CompoundTag> patternsTag = blockEntityTag.getListTag("Patterns", CompoundTag.class);
                  if (patternsTag != null) {
                     for(CompoundTag pattern : patternsTag) {
                        NumberTag colorTag = pattern.getNumberTag("Color");
                        if (colorTag != null) {
                           pattern.putInt("Color", 15 - colorTag.asInt());
                        }
                     }
                  }
               }
            }

            CompoundTag display = tag.getCompoundTag("display");
            if (display != null) {
               StringTag name = display.getStringTag("Name");
               if (name != null) {
                  display.putString(this.nbtTagName("Name"), name.getValue());
                  name.setValue(ComponentUtil.legacyToJsonString(name.getValue(), true));
               }
            }

            ListTag<CompoundTag> ench = tag.getListTag("ench", CompoundTag.class);
            if (ench != null) {
               ListTag<CompoundTag> enchantments = new ListTag(CompoundTag.class);

               for(CompoundTag enchEntry : ench) {
                  short oldId = enchEntry.getShort("id", (short)0);
                  CompoundTag enchantmentEntry = new CompoundTag();
                  String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get(oldId);
                  if (newId == null) {
                     newId = (new StringBuilder()).append("viaversion:legacy/").append(oldId).toString();
                  }

                  enchantmentEntry.putString("id", newId);
                  enchantmentEntry.putShort("lvl", enchEntry.getShort("lvl", (short)0));
                  enchantments.add(enchantmentEntry);
               }

               tag.remove("ench");
               tag.put("Enchantments", enchantments);
            }

            ListTag<CompoundTag> storedEnch = tag.getListTag("StoredEnchantments", CompoundTag.class);
            if (storedEnch != null) {
               ListTag<CompoundTag> newStoredEnch = new ListTag(CompoundTag.class);

               for(CompoundTag enchEntry : storedEnch) {
                  NumberTag idTag = enchEntry.getNumberTag("id");
                  if (idTag != null) {
                     CompoundTag enchantmentEntry = new CompoundTag();
                     short oldId = idTag.asShort();
                     String newId = (String)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().get(oldId);
                     if (newId == null) {
                        newId = (new StringBuilder()).append("viaversion:legacy/").append(oldId).toString();
                     }

                     enchantmentEntry.putString("id", newId);
                     NumberTag levelTag = enchEntry.getNumberTag("lvl");
                     if (levelTag != null) {
                        enchantmentEntry.putShort("lvl", levelTag.asShort());
                     }

                     newStoredEnch.add(enchantmentEntry);
                  }
               }

               tag.put("StoredEnchantments", newStoredEnch);
            }

            ListTag<?> canPlaceOnTag = tag.getListTag("CanPlaceOn");
            if (canPlaceOnTag != null) {
               ListTag<StringTag> newCanPlaceOn = new ListTag(StringTag.class);
               tag.put(this.nbtTagName("CanPlaceOn"), canPlaceOnTag.copy());

               for(Tag oldTag : canPlaceOnTag) {
                  Object value = oldTag.getValue();
                  String oldId = Key.stripMinecraftNamespace(value.toString());
                  String numberConverted = (String)BlockIdData.numberIdToString.get(Ints.tryParse(oldId));
                  if (numberConverted != null) {
                     oldId = numberConverted;
                  }

                  String[] newValues = (String[])BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT));
                  if (newValues != null) {
                     for(String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                     }
                  } else {
                     newCanPlaceOn.add(new StringTag(oldId.toLowerCase(Locale.ROOT)));
                  }
               }

               tag.put("CanPlaceOn", newCanPlaceOn);
            }

            ListTag<?> canDestroyTag = tag.getListTag("CanDestroy");
            if (canDestroyTag != null) {
               ListTag<StringTag> newCanDestroy = new ListTag(StringTag.class);
               tag.put(this.nbtTagName("CanDestroy"), canDestroyTag.copy());

               for(Tag oldTag : canDestroyTag) {
                  Object value = oldTag.getValue();
                  String oldId = Key.stripMinecraftNamespace(value.toString());
                  String numberConverted = (String)BlockIdData.numberIdToString.get(Ints.tryParse(oldId));
                  if (numberConverted != null) {
                     oldId = numberConverted;
                  }

                  String[] newValues = (String[])BlockIdData.blockIdMapping.get(oldId.toLowerCase(Locale.ROOT));
                  if (newValues != null) {
                     for(String newValue : newValues) {
                        newCanDestroy.add(new StringTag(newValue));
                     }
                  } else {
                     newCanDestroy.add(new StringTag(oldId.toLowerCase(Locale.ROOT)));
                  }
               }

               tag.put("CanDestroy", newCanDestroy);
            }

            if (item.identifier() == 383) {
               CompoundTag entityTag = tag.getCompoundTag("EntityTag");
               if (entityTag != null) {
                  StringTag idTag = entityTag.getStringTag("id");
                  if (idTag != null) {
                     rawId = SpawnEggMappings1_13.getSpawnEggId(idTag.getValue());
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
               } else {
                  rawId = 25100288;
               }
            }

            if (tag.isEmpty()) {
               tag = null;
               item.setTag((CompoundTag)null);
            }
         }

         if (Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(rawId) == -1) {
            if (!isDamageable(item.identifier()) && item.identifier() != 358) {
               if (tag == null) {
                  item.setTag(tag = new CompoundTag());
               }

               tag.put(this.nbtTagName(), new IntTag(originalId));
            }

            if (item.identifier() == 31 && item.data() == 0) {
               rawId = IdAndData.toRawData(32);
            } else if (Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(IdAndData.removeData(rawId)) != -1) {
               rawId = IdAndData.removeData(rawId);
            } else {
               if (!Via.getConfig().isSuppressConversionWarnings()) {
                  ProtocolLogger var10000 = ((Protocol1_12_2To1_13)this.protocol).getLogger();
                  int var28 = item.identifier();
                  var10000.warning("Failed to get new item for " + var28);
               }

               rawId = 16;
            }
         }

         item.setIdentifier(Protocol1_12_2To1_13.MAPPINGS.getItemMappings().getNewId(rawId));
         item.setData((short)0);
         return item;
      }
   }

   public static String getNewPluginChannelId(String old) {
      String var10000;
      switch (old) {
         case "MC|TrList":
            var10000 = "minecraft:trader_list";
            break;
         case "MC|Brand":
            var10000 = "minecraft:brand";
            break;
         case "MC|BOpen":
            var10000 = "minecraft:book_open";
            break;
         case "MC|DebugPath":
            var10000 = "minecraft:debug/paths";
            break;
         case "MC|DebugNeighborsUpdate":
            var10000 = "minecraft:debug/neighbors_update";
            break;
         case "REGISTER":
            var10000 = "minecraft:register";
            break;
         case "UNREGISTER":
            var10000 = "minecraft:unregister";
            break;
         case "BungeeCord":
            var10000 = "bungeecord:main";
            break;
         case "bungeecord:main":
            var10000 = null;
            break;
         default:
            String mappedChannel = (String)Protocol1_12_2To1_13.MAPPINGS.getChannelMappings().get(old);
            var10000 = mappedChannel != null ? mappedChannel : MappingData1_13.validateNewChannel(old);
      }

      return var10000;
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item == null) {
         return null;
      } else {
         Integer rawId = null;
         boolean gotRawIdFromTag = false;
         CompoundTag tag = item.tag();
         if (tag != null) {
            NumberTag viaTag = tag.getNumberTag(this.nbtTagName());
            if (viaTag != null) {
               rawId = viaTag.asInt();
               tag.remove(this.nbtTagName());
               gotRawIdFromTag = true;
            }
         }

         if (rawId == null) {
            int oldId = Protocol1_12_2To1_13.MAPPINGS.getItemMappings().inverse().getNewId(item.identifier());
            if (oldId != -1) {
               Optional<String> eggEntityId = SpawnEggMappings1_13.getEntityId(oldId);
               if (eggEntityId.isPresent()) {
                  rawId = 25100288;
                  if (tag == null) {
                     item.setTag(tag = new CompoundTag());
                  }

                  if (!tag.contains("EntityTag")) {
                     CompoundTag entityTag = new CompoundTag();
                     entityTag.put("id", new StringTag((String)eggEntityId.get()));
                     tag.put("EntityTag", entityTag);
                  }
               } else {
                  rawId = IdAndData.getId(oldId) << 16 | oldId & 15;
               }
            }
         }

         if (rawId == null) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
               ProtocolLogger var10000 = ((Protocol1_12_2To1_13)this.protocol).getLogger();
               int var20 = item.identifier();
               var10000.warning("Failed to get old item for " + var20);
            }

            rawId = 65536;
         }

         item.setIdentifier((short)(rawId >> 16));
         item.setData((short)(rawId & '\uffff'));
         if (tag != null) {
            if (isDamageable(item.identifier())) {
               NumberTag damageTag = tag.getNumberTag("Damage");
               if (damageTag != null) {
                  if (!gotRawIdFromTag) {
                     item.setData(damageTag.asShort());
                  }

                  tag.remove("Damage");
               }
            }

            if (item.identifier() == 358) {
               NumberTag mapTag = tag.getNumberTag("map");
               if (mapTag != null) {
                  if (!gotRawIdFromTag) {
                     item.setData(mapTag.asShort());
                  }

                  tag.remove("map");
               }
            }

            if (item.identifier() == 442 || item.identifier() == 425) {
               CompoundTag blockEntityTag = tag.getCompoundTag("BlockEntityTag");
               if (blockEntityTag != null) {
                  NumberTag baseTag = blockEntityTag.getNumberTag("Base");
                  if (baseTag != null) {
                     blockEntityTag.putInt("Base", 15 - baseTag.asInt());
                  }

                  ListTag<CompoundTag> patternsTag = blockEntityTag.getListTag("Patterns", CompoundTag.class);
                  if (patternsTag != null) {
                     for(CompoundTag pattern : patternsTag) {
                        NumberTag colorTag = pattern.getNumberTag("Color");
                        pattern.putInt("Color", 15 - colorTag.asInt());
                     }
                  }
               }
            }

            CompoundTag display = tag.getCompoundTag("display");
            if (display != null) {
               StringTag name = display.getStringTag("Name");
               if (name != null) {
                  Tag via = display.remove(this.nbtTagName("Name"));
                  name.setValue(via instanceof StringTag ? (String)via.getValue() : ComponentUtil.jsonToLegacy(name.getValue()));
               }
            }

            ListTag<CompoundTag> enchantments = tag.getListTag("Enchantments", CompoundTag.class);
            if (enchantments != null) {
               ListTag<CompoundTag> ench = new ListTag(CompoundTag.class);

               for(CompoundTag enchantmentEntry : enchantments) {
                  StringTag idTag = enchantmentEntry.getStringTag("id");
                  if (idTag != null) {
                     CompoundTag enchEntry = new CompoundTag();
                     String newId = idTag.getValue();
                     Short oldId = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
                     if (oldId == null && newId.startsWith("viaversion:legacy/")) {
                        oldId = Short.valueOf(newId.substring(18));
                     }

                     if (oldId != null) {
                        enchEntry.putShort("id", oldId);
                        enchEntry.putShort("lvl", enchantmentEntry.getShort("lvl", (short)0));
                        ench.add(enchEntry);
                     }
                  }
               }

               tag.remove("Enchantments");
               tag.put("ench", ench);
            }

            ListTag<CompoundTag> storedEnch = tag.getListTag("StoredEnchantments", CompoundTag.class);
            if (storedEnch != null) {
               ListTag<CompoundTag> newStoredEnch = new ListTag(CompoundTag.class);

               for(CompoundTag enchantmentEntry : storedEnch) {
                  StringTag idTag = enchantmentEntry.getStringTag("id");
                  if (idTag != null) {
                     CompoundTag enchEntry = new CompoundTag();
                     String newId = idTag.getValue();
                     Short oldId = (Short)Protocol1_12_2To1_13.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
                     if (oldId == null && newId.startsWith("viaversion:legacy/")) {
                        oldId = Short.valueOf(newId.substring(18));
                     }

                     if (oldId != null) {
                        enchEntry.putShort("id", oldId);
                        NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
                        if (levelTag != null) {
                           enchEntry.putShort("lvl", levelTag.asShort());
                        }

                        newStoredEnch.add(enchEntry);
                     }
                  }
               }

               tag.put("StoredEnchantments", newStoredEnch);
            }

            if (tag.getListTag(this.nbtTagName("CanPlaceOn")) != null) {
               tag.put("CanPlaceOn", tag.remove(this.nbtTagName("CanPlaceOn")));
            } else if (tag.getListTag("CanPlaceOn") != null) {
               ListTag<?> old = tag.getListTag("CanPlaceOn");
               ListTag<StringTag> newCanPlaceOn = new ListTag(StringTag.class);

               for(Tag oldTag : old) {
                  Object value = oldTag.getValue();
                  String[] newValues = (String[])BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                  if (newValues != null) {
                     for(String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                     }
                  } else {
                     newCanPlaceOn.add(new StringTag(value.toString()));
                  }
               }

               tag.put("CanPlaceOn", newCanPlaceOn);
            }

            if (tag.getListTag(this.nbtTagName("CanDestroy")) != null) {
               tag.put("CanDestroy", tag.remove(this.nbtTagName("CanDestroy")));
            } else if (tag.getListTag("CanDestroy") != null) {
               ListTag<?> old = tag.getListTag("CanDestroy");
               ListTag<StringTag> newCanDestroy = new ListTag(StringTag.class);

               for(Tag oldTag : old) {
                  Object value = oldTag.getValue();
                  String[] newValues = (String[])BlockIdData.fallbackReverseMapping.get(value instanceof String ? Key.stripMinecraftNamespace((String)value) : null);
                  if (newValues != null) {
                     for(String newValue : newValues) {
                        newCanDestroy.add(new StringTag(newValue));
                     }
                  } else {
                     newCanDestroy.add(new StringTag(oldTag.getValue().toString()));
                  }
               }

               tag.put("CanDestroy", newCanDestroy);
            }
         }

         return item;
      }
   }

   public static String getOldPluginChannelId(String newId) {
      newId = MappingData1_13.validateNewChannel(newId);
      if (newId == null) {
         return null;
      } else {
         String var10000;
         switch (newId) {
            case "minecraft:trader_list":
               var10000 = "MC|TrList";
               break;
            case "minecraft:book_open":
               var10000 = "MC|BOpen";
               break;
            case "minecraft:debug/paths":
               var10000 = "MC|DebugPath";
               break;
            case "minecraft:debug/neighbors_update":
               var10000 = "MC|DebugNeighborsUpdate";
               break;
            case "minecraft:register":
               var10000 = "REGISTER";
               break;
            case "minecraft:unregister":
               var10000 = "UNREGISTER";
               break;
            case "minecraft:brand":
               var10000 = "MC|Brand";
               break;
            case "bungeecord:main":
               var10000 = "BungeeCord";
               break;
            default:
               String mappedChannel = (String)Protocol1_12_2To1_13.MAPPINGS.getChannelMappings().inverse().get(newId);
               var10000 = mappedChannel != null ? mappedChannel : (newId.length() > 20 ? newId.substring(0, 20) : newId);
         }

         return var10000;
      }
   }

   public static boolean isDamageable(int id) {
      return id >= 256 && id <= 259 || id == 261 || id >= 267 && id <= 279 || id >= 283 && id <= 286 || id >= 290 && id <= 294 || id >= 298 && id <= 317 || id == 346 || id == 359 || id == 398 || id == 442 || id == 443;
   }
}
