package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.Int2IntMapBiMappings;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiHashMap;
import com.viaversion.viaversion.util.Key;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingData1_13 extends MappingDataBase {
   final Map blockTags = new HashMap();
   final Map itemTags = new HashMap();
   final Map fluidTags = new HashMap();
   final BiMap oldEnchantmentsIds = HashBiMap.create();
   final Map translateMapping = new HashMap();
   final Map mojangTranslation = new HashMap();
   final BiMap channelMappings = HashBiMap.create();

   public MappingData1_13() {
      super("1.12", "1.13");
   }

   protected void loadExtras(CompoundTag data) {
      this.loadTags(this.blockTags, data.getCompoundTag("block_tags"));
      this.loadTags(this.itemTags, data.getCompoundTag("item_tags"));
      this.loadTags(this.fluidTags, data.getCompoundTag("fluid_tags"));
      CompoundTag legacyEnchantments = data.getCompoundTag("legacy_enchantments");
      this.loadEnchantments(this.oldEnchantmentsIds, legacyEnchantments);
      if (Via.getConfig().isSnowCollisionFix()) {
         this.blockMappings.setNewId(1248, 3416);
      }

      if (Via.getConfig().isInfestedBlocksFix()) {
         this.blockMappings.setNewId(1552, 1);
         this.blockMappings.setNewId(1553, 14);
         this.blockMappings.setNewId(1554, 3983);
         this.blockMappings.setNewId(1555, 3984);
         this.blockMappings.setNewId(1556, 3985);
         this.blockMappings.setNewId(1557, 3986);
      }

      JsonObject object = MappingDataLoader.INSTANCE.loadFromDataDir("channelmappings-1.13.json");
      if (object != null) {
         for(Map.Entry entry : object.entrySet()) {
            String oldChannel = (String)entry.getKey();
            String newChannel = ((JsonElement)entry.getValue()).getAsString();
            if (!Key.isValid(newChannel)) {
               this.getLogger().warning("Channel '" + newChannel + "' is not a valid 1.13 plugin channel, please check your configuration!");
            } else {
               this.channelMappings.put(oldChannel, newChannel);
            }
         }
      }

      Map<String, String> translationMappingData = (Map)GsonUtil.getGson().fromJson((Reader)(new InputStreamReader(MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json"))), (Type)(new TypeToken() {
      }).getType());

      String[] unmappedTranslationLines;
      try {
         Reader reader = new InputStreamReader(MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8);

         try {
            unmappedTranslationLines = CharStreams.toString(reader).split("\n");
         } catch (Throwable var17) {
            try {
               reader.close();
            } catch (Throwable var16) {
               var17.addSuppressed(var16);
            }

            throw var17;
         }

         reader.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      for(String line : unmappedTranslationLines) {
         if (!line.isEmpty()) {
            String[] keyAndTranslation = line.split("=", 2);
            if (keyAndTranslation.length == 2) {
               String key = keyAndTranslation[0];
               String translation = keyAndTranslation[1].replaceAll("%(\\d\\$)?d", "%$1s").trim();
               this.mojangTranslation.put(key, translation);
               if (translationMappingData.containsKey(key)) {
                  String mappedKey = (String)translationMappingData.get(key);
                  this.translateMapping.put(key, mappedKey != null ? mappedKey : key);
               }
            }
         }
      }

   }

   protected @Nullable Mappings loadMappings(CompoundTag data, String key) {
      if (key.equals("blocks")) {
         return super.loadMappings(data, "blockstates");
      } else {
         return key.equals("blockstates") ? null : super.loadMappings(data, key);
      }
   }

   protected @Nullable BiMappings loadBiMappings(CompoundTag data, String key) {
      return key.equals("items") ? (BiMappings)MappingDataLoader.INSTANCE.loadMappings(data, "items", (size) -> {
         Int2IntBiHashMap map = new Int2IntBiHashMap(size);
         map.defaultReturnValue(-1);
         return map;
      }, Int2IntBiHashMap::put, (v, mappedSize) -> Int2IntMapBiMappings.of(v)) : super.loadBiMappings(data, key);
   }

   public static String validateNewChannel(String newId) {
      return !Key.isValid(newId) ? null : Key.namespaced(newId);
   }

   void loadTags(Map output, CompoundTag newTags) {
      for(Map.Entry entry : newTags.entrySet()) {
         IntArrayTag ids = (IntArrayTag)entry.getValue();
         output.put(Key.namespaced((String)entry.getKey()), ids.getValue());
      }

   }

   void loadEnchantments(Map output, CompoundTag enchantments) {
      for(Map.Entry enty : enchantments.entrySet()) {
         output.put(Short.parseShort((String)enty.getKey()), ((StringTag)enty.getValue()).getValue());
      }

   }

   public Map getBlockTags() {
      return this.blockTags;
   }

   public Map getItemTags() {
      return this.itemTags;
   }

   public Map getFluidTags() {
      return this.fluidTags;
   }

   public BiMap getOldEnchantmentsIds() {
      return this.oldEnchantmentsIds;
   }

   public Map getTranslateMapping() {
      return this.translateMapping;
   }

   public Map getMojangTranslation() {
      return this.mojangTranslation;
   }

   public BiMap getChannelMappings() {
      return this.channelMappings;
   }
}
