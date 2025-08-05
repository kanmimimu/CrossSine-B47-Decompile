package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsStructuredItemRewriter extends StructuredItemRewriter {
   public BackwardsStructuredItemRewriter(BackwardsProtocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType, Type itemCostType, Type optionalItemCostType, Type mappedItemCostType, Type mappedOptionalItemCostType, Type particleType, Type mappedParticleType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, itemCostType, optionalItemCostType, mappedItemCostType, mappedOptionalItemCostType, particleType, mappedParticleType);
   }

   public BackwardsStructuredItemRewriter(BackwardsProtocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType);
   }

   public BackwardsStructuredItemRewriter(BackwardsProtocol protocol, Type itemType, Type itemArrayType) {
      super(protocol, itemType, itemArrayType);
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item.isEmpty()) {
         return item;
      } else {
         StructuredDataContainer dataContainer = item.dataContainer();
         this.updateItemDataComponentTypeIds(dataContainer, true);
         BackwardsMappingData mappingData = ((BackwardsProtocol)this.protocol).getMappingData();
         MappedItem mappedItem = mappingData != null ? mappingData.getMappedItem(item.identifier()) : null;
         if (mappedItem == null) {
            if (mappingData != null && mappingData.getItemMappings() != null) {
               item.setIdentifier(mappingData.getNewItemId(item.identifier()));
            }

            this.updateItemDataComponents(connection, item, true);
            return item;
         } else {
            CompoundTag tag = this.createCustomTag(item);
            tag.putInt(this.nbtTagName("id"), item.identifier());
            item.setIdentifier(mappedItem.id());
            if (mappedItem.customModelData() != null && !dataContainer.has(StructuredDataKey.CUSTOM_MODEL_DATA)) {
               dataContainer.set(StructuredDataKey.CUSTOM_MODEL_DATA, mappedItem.customModelData());
            }

            if (!dataContainer.has(StructuredDataKey.CUSTOM_NAME)) {
               dataContainer.set(StructuredDataKey.CUSTOM_NAME, mappedItem.tagName());
               tag.putBoolean(this.nbtTagName("added_custom_name"), true);
            }

            this.updateItemDataComponents(connection, item, true);
            return item;
         }
      }
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item.isEmpty()) {
         return item;
      } else {
         StructuredDataContainer dataContainer = item.dataContainer();
         this.updateItemDataComponentTypeIds(dataContainer, false);
         BackwardsMappingData mappingData = ((BackwardsProtocol)this.protocol).getMappingData();
         if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getOldItemId(item.identifier()));
         }

         CompoundTag customData = (CompoundTag)dataContainer.get(StructuredDataKey.CUSTOM_DATA);
         if (customData != null) {
            Tag var7 = customData.remove(this.nbtTagName("id"));
            if (var7 instanceof IntTag) {
               IntTag originalTag = (IntTag)var7;
               item.setIdentifier(originalTag.asInt());
            }
         }

         this.restoreTextComponents(item);
         this.updateItemDataComponents(connection, item, false);
         return item;
      }
   }

   protected void saveListTag(CompoundTag tag, ListTag original, String name) {
      String backupName = this.nbtTagName(name);
      if (!tag.contains(backupName)) {
         tag.put(backupName, original.copy());
      }

   }

   public @Nullable ListTag removeListTag(CompoundTag tag, String tagName, Class tagType) {
      String backupName = this.nbtTagName(tagName);
      ListTag<T> data = tag.getListTag(backupName, tagType);
      if (data == null) {
         return null;
      } else {
         tag.remove(backupName);
         return data;
      }
   }

   protected void saveGenericTagList(CompoundTag tag, List original, String name) {
      String backupName = this.nbtTagName(name);
      if (!tag.contains(backupName)) {
         CompoundTag output = new CompoundTag();

         for(int i = 0; i < original.size(); ++i) {
            output.put(Integer.toString(i), (Tag)original.get(i));
         }

         tag.put(backupName, output);
      }

   }

   protected List removeGenericTagList(CompoundTag tag, String name) {
      String backupName = this.nbtTagName(name);
      CompoundTag data = tag.getCompoundTag(backupName);
      if (data == null) {
         return null;
      } else {
         tag.remove(backupName);
         return new ArrayList(data.values());
      }
   }

   public String nbtTagName() {
      String var3 = ((BackwardsProtocol)this.protocol).getClass().getSimpleName();
      return "VB|" + var3;
   }
}
