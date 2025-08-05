package com.viaversion.viabackwards.api.data;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.IdentityMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsMappingData extends MappingDataBase {
   private final Class vvProtocolClass;
   protected Int2ObjectMap backwardsItemMappings;
   private Map backwardsSoundMappings;
   private Map entityNames;
   private Int2ObjectMap enchantmentNames;

   public BackwardsMappingData(String unmappedVersion, String mappedVersion) {
      this(unmappedVersion, mappedVersion, (Class)null);
   }

   public BackwardsMappingData(String unmappedVersion, String mappedVersion, @Nullable Class vvProtocolClass) {
      super(unmappedVersion, mappedVersion);
      Preconditions.checkArgument(vvProtocolClass == null || !BackwardsProtocol.class.isAssignableFrom(vvProtocolClass));
      this.vvProtocolClass = vvProtocolClass;
   }

   protected void loadExtras(CompoundTag data) {
      CompoundTag itemNames = data.getCompoundTag("itemnames");
      if (itemNames != null) {
         Preconditions.checkNotNull(this.itemMappings);
         this.backwardsItemMappings = new Int2ObjectOpenHashMap(itemNames.size());
         CompoundTag extraItemData = data.getCompoundTag("itemdata");

         for(Map.Entry entry : itemNames.entrySet()) {
            StringTag name = (StringTag)entry.getValue();
            int id = Integer.parseInt((String)entry.getKey());
            Integer customModelData = null;
            if (extraItemData != null && extraItemData.contains((String)entry.getKey())) {
               CompoundTag entryTag = extraItemData.getCompoundTag((String)entry.getKey());
               NumberTag customModelDataTag = entryTag.getNumberTag("custom_model_data");
               customModelData = customModelDataTag != null ? customModelDataTag.asInt() : null;
            }

            this.backwardsItemMappings.put(id, new MappedItem(this.getNewItemId(id), name.getValue(), customModelData));
         }
      }

      this.entityNames = this.loadNameByStringMappings(data, "entitynames");
      this.enchantmentNames = this.loadNameByIdMappings(data, "enchantmentnames");
      this.backwardsSoundMappings = this.loadNameByStringMappings(data, "soundnames");
   }

   private @Nullable Map loadNameByStringMappings(CompoundTag data, String key) {
      CompoundTag nameMappings = data.getCompoundTag(key);
      if (nameMappings == null) {
         return null;
      } else {
         Map<String, String> map = new HashMap(nameMappings.size());

         for(Map.Entry entry : nameMappings.entrySet()) {
            StringTag mappedTag = (StringTag)entry.getValue();
            map.put((String)entry.getKey(), mappedTag.getValue());
         }

         return map;
      }
   }

   private @Nullable Int2ObjectMap loadNameByIdMappings(CompoundTag data, String key) {
      CompoundTag nameMappings = data.getCompoundTag(key);
      if (nameMappings == null) {
         return null;
      } else {
         Int2ObjectMap<String> map = new Int2ObjectArrayMap(nameMappings.size());

         for(Map.Entry entry : nameMappings.entrySet()) {
            StringTag mappedTag = (StringTag)entry.getValue();
            map.put(Integer.parseInt((String)entry.getKey()), mappedTag.getValue());
         }

         return map;
      }
   }

   protected @Nullable BiMappings loadBiMappings(CompoundTag data, String key) {
      if (key.equals("items") && this.vvProtocolClass != null) {
         Mappings mappings = super.loadMappings(data, key);
         MappingData mappingData = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData();
         if (mappingData != null && mappingData.getItemMappings() != null) {
            BiMappings vvItemMappings = mappingData.getItemMappings();
            if (mappings == null) {
               mappings = new IdentityMappings(vvItemMappings.mappedSize(), vvItemMappings.size());
            }

            return ItemMappings.of(mappings, vvItemMappings);
         }
      }

      return super.loadBiMappings(data, key);
   }

   public int getNewItemId(int id) {
      return this.itemMappings.getNewId(id);
   }

   public int getNewBlockId(int id) {
      return this.blockMappings.getNewId(id);
   }

   public int getOldItemId(int id) {
      return this.checkValidity(id, this.itemMappings.inverse().getNewId(id), "item");
   }

   public int getNewAttributeId(int id) {
      return this.attributeMappings.getNewId(id);
   }

   public @Nullable MappedItem getMappedItem(int id) {
      return this.backwardsItemMappings != null ? (MappedItem)this.backwardsItemMappings.get(id) : null;
   }

   public @Nullable String getMappedNamedSound(String id) {
      return this.backwardsSoundMappings == null ? null : (String)this.backwardsSoundMappings.get(Key.stripMinecraftNamespace(id));
   }

   public @Nullable String mappedEntityName(String entityName) {
      if (this.entityNames == null) {
         this.getLogger().log(Level.SEVERE, "No entity mappings found when requesting them for " + entityName, new RuntimeException());
         return null;
      } else {
         return (String)this.entityNames.get(entityName);
      }
   }

   public @Nullable String mappedEnchantmentName(int enchantmentId) {
      if (this.enchantmentNames == null) {
         ViaBackwards.getPlatform().getLogger().log(Level.SEVERE, "No enchantment name mappings found when requesting " + enchantmentId, new RuntimeException());
         return null;
      } else {
         return (String)this.enchantmentNames.get(enchantmentId);
      }
   }

   public @Nullable Int2ObjectMap getBackwardsItemMappings() {
      return this.backwardsItemMappings;
   }

   public @Nullable Map getBackwardsSoundMappings() {
      return this.backwardsSoundMappings;
   }

   public @Nullable Class getViaVersionProtocolClass() {
      return this.vvProtocolClass;
   }

   protected Logger getLogger() {
      return ViaBackwards.getPlatform().getLogger();
   }

   protected @Nullable CompoundTag readMappingsFile(String name) {
      return BackwardsMappingDataLoader.INSTANCE.loadNBTFromDir(name);
   }
}
