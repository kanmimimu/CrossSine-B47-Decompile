package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.data.BlockColors1_11_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.IdAndData;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyBlockItemRewriter extends BackwardsItemRewriterBase {
   protected final Int2ObjectMap itemReplacements;
   protected final Int2ObjectMap blockReplacements;

   protected LegacyBlockItemRewriter(BackwardsProtocol protocol, String name, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, false);
      this.itemReplacements = new Int2ObjectOpenHashMap(8);
      this.blockReplacements = new Int2ObjectOpenHashMap(8);
      Int2ObjectMap<MappedLegacyBlockItem> blockItemReplacements = new Int2ObjectOpenHashMap(8);
      JsonObject jsonObject = this.readMappingsFile("item-mappings-" + name + ".json");
      this.addMappings(MappedLegacyBlockItem.Type.ITEM, jsonObject, this.itemReplacements);
      this.addMappings(MappedLegacyBlockItem.Type.BLOCK_ITEM, jsonObject, blockItemReplacements);
      this.addMappings(MappedLegacyBlockItem.Type.BLOCK, jsonObject, this.blockReplacements);
      this.blockReplacements.putAll(blockItemReplacements);
      this.itemReplacements.putAll(blockItemReplacements);
   }

   protected LegacyBlockItemRewriter(BackwardsProtocol protocol, String name, Type itemType, Type itemArrayType) {
      this(protocol, name, itemType, itemArrayType, itemType, itemArrayType);
   }

   protected LegacyBlockItemRewriter(BackwardsProtocol protocol, String name) {
      this(protocol, name, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
   }

   void addMappings(MappedLegacyBlockItem.Type type, JsonObject object, Int2ObjectMap mappings) {
      if (object.has(type.getName())) {
         JsonObject mappingsObject = object.getAsJsonObject(type.getName());

         for(Map.Entry dataEntry : mappingsObject.entrySet()) {
            this.addMapping((String)dataEntry.getKey(), ((JsonElement)dataEntry.getValue()).getAsJsonObject(), type, mappings);
         }
      }

   }

   void addMapping(String key, JsonObject object, MappedLegacyBlockItem.Type type, Int2ObjectMap mappings) {
      int id = object.getAsJsonPrimitive("id").getAsInt();
      JsonPrimitive jsonData = object.getAsJsonPrimitive("data");
      short data = jsonData != null ? jsonData.getAsShort() : 0;
      String name = type != MappedLegacyBlockItem.Type.BLOCK ? object.getAsJsonPrimitive("name").getAsString() : null;
      if (key.indexOf(45) == -1) {
         int dataSeparatorIndex = key.indexOf(58);
         int unmappedId;
         if (dataSeparatorIndex != -1) {
            short unmappedData = Short.parseShort(key.substring(dataSeparatorIndex + 1));
            unmappedId = Integer.parseInt(key.substring(0, dataSeparatorIndex));
            unmappedId = this.compress(unmappedId, unmappedData);
         } else {
            unmappedId = this.compress(Integer.parseInt(key), -1);
         }

         mappings.put(unmappedId, new MappedLegacyBlockItem(id, data, name, type));
      } else {
         String[] split = key.split("-", 2);
         int from = Integer.parseInt(split[0]);
         int to = Integer.parseInt(split[1]);
         if (name != null && name.contains("%color%")) {
            for(int i = from; i <= to; ++i) {
               mappings.put(this.compress(i, -1), new MappedLegacyBlockItem(id, data, name.replace("%color%", BlockColors1_11_1.get(i - from)), type));
            }
         } else {
            MappedLegacyBlockItem mappedBlockItem = new MappedLegacyBlockItem(id, data, name, type);

            for(int i = from; i <= to; ++i) {
               mappings.put(this.compress(i, -1), mappedBlockItem);
            }
         }

      }
   }

   public void registerBlockChange(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.BLOCK_POSITION1_8);
            this.map(Types.VAR_INT);
            this.handler((wrapper) -> {
               int idx = (Integer)wrapper.get(Types.VAR_INT, 0);
               wrapper.set(Types.VAR_INT, 0, LegacyBlockItemRewriter.this.handleBlockId(idx));
            });
         }
      });
   }

   public void registerMultiBlockChange(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketHandlers() {
         public void register() {
            this.map(Types.INT);
            this.map(Types.INT);
            this.map(Types.BLOCK_CHANGE_ARRAY);
            this.handler((wrapper) -> {
               for(BlockChangeRecord record : (BlockChangeRecord[])wrapper.get(Types.BLOCK_CHANGE_ARRAY, 0)) {
                  record.setBlockId(LegacyBlockItemRewriter.this.handleBlockId(record.getBlockId()));
               }

            });
         }
      });
   }

   public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         MappedLegacyBlockItem data = this.getMappedItem(item.identifier(), item.data());
         if (data == null) {
            return super.handleItemToClient(connection, item);
         } else {
            if (item.tag() == null) {
               item.setTag(new CompoundTag());
            }

            short originalData = item.data();
            item.tag().putInt(this.nbtTagName("id"), item.identifier());
            item.setIdentifier(data.getId());
            if (data.getData() != -1) {
               item.setData(data.getData());
               item.tag().putShort(this.nbtTagName("data"), originalData);
            }

            if (data.getName() != null) {
               CompoundTag display = item.tag().getCompoundTag("display");
               if (display == null) {
                  item.tag().put("display", display = new CompoundTag());
               }

               StringTag nameTag = display.getStringTag("Name");
               if (nameTag == null) {
                  nameTag = new StringTag(data.getName());
                  display.put("Name", nameTag);
                  display.put(this.nbtTagName("customName"), new ByteTag(false));
               }

               String value = nameTag.getValue();
               if (value.contains("%vb_color%")) {
                  display.putString("Name", value.replace("%vb_color%", BlockColors1_11_1.get(originalData)));
               }
            }

            return item;
         }
      }
   }

   public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(connection, item);
         if (item.tag() != null) {
            Tag originalId = item.tag().remove(this.nbtTagName("id"));
            if (originalId instanceof IntTag) {
               item.setIdentifier(((NumberTag)originalId).asInt());
            }

            Tag originalData = item.tag().remove(this.nbtTagName("data"));
            if (originalData instanceof ShortTag) {
               item.setData(((NumberTag)originalData).asShort());
            }
         }

         return item;
      }
   }

   public PacketHandler getFallingBlockHandler() {
      return (wrapper) -> {
         EntityTypes1_12.ObjectType type = EntityTypes1_12.ObjectType.findById((Byte)wrapper.get(Types.BYTE, 0));
         if (type == EntityTypes1_12.ObjectType.FALLING_BLOCK) {
            int objectData = (Integer)wrapper.get(Types.INT, 0);
            IdAndData block = this.handleBlock(objectData & 4095, objectData >> 12 & 15);
            if (block == null) {
               return;
            }

            wrapper.set(Types.INT, 0, block.getId() | block.getData() << 12);
         }

      };
   }

   public @Nullable IdAndData handleBlock(int blockId, int data) {
      MappedLegacyBlockItem settings = this.getMappedBlock(blockId, data);
      if (settings == null) {
         return null;
      } else {
         IdAndData block = settings.getBlock();
         return block.getData() == -1 ? block.withData(data) : block;
      }
   }

   public int handleBlockId(int rawId) {
      int id = IdAndData.getId(rawId);
      int data = IdAndData.getData(rawId);
      IdAndData mappedBlock = this.handleBlock(id, data);
      return mappedBlock == null ? rawId : IdAndData.toRawData(mappedBlock.getId(), mappedBlock.getData());
   }

   public void handleChunk(Chunk chunk) {
      Map<Pos, CompoundTag> tags = new HashMap();

      for(CompoundTag tag : chunk.getBlockEntities()) {
         NumberTag xTag;
         NumberTag yTag;
         NumberTag zTag;
         if ((xTag = tag.getNumberTag("x")) != null && (yTag = tag.getNumberTag("y")) != null && (zTag = tag.getNumberTag("z")) != null) {
            Pos pos = new Pos(xTag.asInt() & 15, yTag.asInt(), zTag.asInt() & 15);
            tags.put(pos, tag);
            if (pos.y() >= 0 && pos.y() <= 255) {
               ChunkSection section = chunk.getSections()[pos.y() >> 4];
               if (section != null) {
                  int block = section.palette(PaletteType.BLOCKS).idAt(pos.x(), pos.y() & 15, pos.z());
                  MappedLegacyBlockItem settings = this.getMappedBlock(block);
                  if (settings != null && settings.hasBlockEntityHandler()) {
                     settings.getBlockEntityHandler().handleCompoundTag(block, tag);
                  }
               }
            }
         }
      }

      for(int i = 0; i < chunk.getSections().length; ++i) {
         ChunkSection section = chunk.getSections()[i];
         if (section != null) {
            boolean hasBlockEntityHandler = false;
            DataPalette palette = section.palette(PaletteType.BLOCKS);

            for(int j = 0; j < palette.size(); ++j) {
               int block = palette.idByIndex(j);
               int btype = block >> 4;
               int meta = block & 15;
               IdAndData b = this.handleBlock(btype, meta);
               if (b != null) {
                  palette.setIdByIndex(j, IdAndData.toRawData(b.getId(), b.getData()));
               }

               if (!hasBlockEntityHandler) {
                  MappedLegacyBlockItem settings = this.getMappedBlock(block);
                  if (settings != null && settings.hasBlockEntityHandler()) {
                     hasBlockEntityHandler = true;
                  }
               }
            }

            if (hasBlockEntityHandler) {
               for(int x = 0; x < 16; ++x) {
                  for(int y = 0; y < 16; ++y) {
                     for(int z = 0; z < 16; ++z) {
                        int block = palette.idAt(x, y, z);
                        MappedLegacyBlockItem settings = this.getMappedBlock(block);
                        if (settings != null && settings.hasBlockEntityHandler()) {
                           Pos pos = new Pos(x, y + (i << 4), z);
                           if (!tags.containsKey(pos)) {
                              CompoundTag tag = new CompoundTag();
                              tag.putInt("x", x + (chunk.getX() << 4));
                              tag.putInt("y", y + (i << 4));
                              tag.putInt("z", z + (chunk.getZ() << 4));
                              settings.getBlockEntityHandler().handleCompoundTag(block, tag);
                              chunk.getBlockEntities().add(tag);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   protected CompoundTag getNamedTag(String text) {
      CompoundTag tag = new CompoundTag();
      CompoundTag displayTag = new CompoundTag();
      tag.put("display", displayTag);
      text = "§r" + text;
      displayTag.putString("Name", this.jsonNameFormat ? ComponentUtil.legacyToJsonString(text) : text);
      return tag;
   }

   @Nullable MappedLegacyBlockItem getMappedBlock(int id, int data) {
      MappedLegacyBlockItem mapping = (MappedLegacyBlockItem)this.blockReplacements.get(this.compress(id, data));
      return mapping != null ? mapping : (MappedLegacyBlockItem)this.blockReplacements.get(this.compress(id, -1));
   }

   @Nullable MappedLegacyBlockItem getMappedItem(int id, int data) {
      MappedLegacyBlockItem mapping = (MappedLegacyBlockItem)this.itemReplacements.get(this.compress(id, data));
      return mapping != null ? mapping : (MappedLegacyBlockItem)this.itemReplacements.get(this.compress(id, -1));
   }

   @Nullable MappedLegacyBlockItem getMappedBlock(int rawId) {
      int id = IdAndData.getId(rawId);
      int data = IdAndData.getData(rawId);
      return this.getMappedBlock(id, data);
   }

   protected JsonObject readMappingsFile(String name) {
      return BackwardsMappingDataLoader.INSTANCE.loadFromDataDir(name);
   }

   protected int compress(int id, int data) {
      return id << 16 | data & '\uffff';
   }

   private static final class Pos {
      final int x;
      final short y;
      final int z;

      public Pos(int x, int y, int z) {
         this(x, (short)y, z);
      }

      Pos(int x, short y, int z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public int x() {
         return this.x;
      }

      public short y() {
         return this.y;
      }

      public int z() {
         return this.z;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof Pos)) {
            return false;
         } else {
            Pos var2 = (Pos)var1;
            return this.x == var2.x && this.y == var2.y && this.z == var2.z;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Integer.hashCode(this.x)) * 31 + Short.hashCode(this.y)) * 31 + Integer.hashCode(this.z);
      }

      public String toString() {
         return String.format("%s[x=%s, y=%s, z=%s]", this.getClass().getSimpleName(), Integer.toString(this.x), Short.toString(this.y), Integer.toString(this.z));
      }
   }
}
