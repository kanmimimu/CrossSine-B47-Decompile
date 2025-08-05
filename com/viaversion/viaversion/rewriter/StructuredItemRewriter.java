package com.viaversion.viaversion.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableComponent;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.WrittenBook;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StructuredItemRewriter extends ItemRewriter {
   public StructuredItemRewriter(Protocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType, Type itemCostType, Type optionalItemCostType, Type mappedItemCostType, Type mappedOptionalItemCostType, Type particleType, Type mappedParticleType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, itemCostType, optionalItemCostType, mappedItemCostType, mappedOptionalItemCostType, particleType, mappedParticleType);
   }

   public StructuredItemRewriter(Protocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType);
   }

   public StructuredItemRewriter(Protocol protocol, Type itemType, Type itemArrayType) {
      super(protocol, itemType, itemArrayType, itemType, itemArrayType);
   }

   public Item handleItemToClient(UserConnection connection, Item item) {
      if (item.isEmpty()) {
         return item;
      } else {
         MappingData mappingData = this.protocol.getMappingData();
         if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getNewItemId(item.identifier()));
         }

         this.updateItemDataComponentTypeIds(item.dataContainer(), true);
         this.updateItemDataComponents(connection, item, true);
         return item;
      }
   }

   public Item handleItemToServer(UserConnection connection, Item item) {
      if (item.isEmpty()) {
         return item;
      } else {
         MappingData mappingData = this.protocol.getMappingData();
         if (mappingData != null && mappingData.getItemMappings() != null) {
            item.setIdentifier(mappingData.getOldItemId(item.identifier()));
         }

         this.updateItemDataComponentTypeIds(item.dataContainer(), false);
         this.updateItemDataComponents(connection, item, false);
         this.restoreTextComponents(item);
         return item;
      }
   }

   protected void updateItemDataComponentTypeIds(StructuredDataContainer container, boolean mappedNames) {
      MappingData mappingData = this.protocol.getMappingData();
      if (mappingData != null) {
         FullMappings dataComponentMappings = mappingData.getDataComponentSerializerMappings();
         if (dataComponentMappings != null) {
            if (!mappedNames) {
               dataComponentMappings = dataComponentMappings.inverse();
            }

            container.setIdLookup(this.protocol, mappedNames);
            Protocol var10001 = this.protocol;
            Objects.requireNonNull(dataComponentMappings);
            container.updateIds(var10001, dataComponentMappings::getNewId);
         }
      }
   }

   protected void updateItemDataComponents(UserConnection connection, Item item, boolean clientbound) {
      StructuredDataContainer container = item.dataContainer();
      MappingData mappingData = this.protocol.getMappingData();
      if (mappingData.getItemMappings() != null) {
         Int2IntFunction var10000;
         if (clientbound) {
            Objects.requireNonNull(mappingData);
            var10000 = mappingData::getNewItemId;
         } else {
            Objects.requireNonNull(mappingData);
            var10000 = mappingData::getOldItemId;
         }

         Int2IntFunction itemIdRewriter = var10000;
         container.replace(StructuredDataKey.TRIM, (value) -> value.rewrite(itemIdRewriter));
         container.replace(StructuredDataKey.POT_DECORATIONS, (value) -> value.rewrite(itemIdRewriter));
      }

      if (mappingData.getBlockMappings() != null) {
         Int2IntFunction var28;
         if (clientbound) {
            Objects.requireNonNull(mappingData);
            var28 = mappingData::getNewBlockId;
         } else {
            Objects.requireNonNull(mappingData);
            var28 = mappingData::getOldBlockId;
         }

         Int2IntFunction blockIdRewriter = var28;
         container.replace(StructuredDataKey.TOOL, (value) -> value.rewrite(blockIdRewriter));
         container.replace(StructuredDataKey.CAN_PLACE_ON, (value) -> value.rewrite(blockIdRewriter));
         container.replace(StructuredDataKey.CAN_BREAK, (value) -> value.rewrite(blockIdRewriter));
      }

      if (mappingData.getSoundMappings() != null) {
         Int2IntFunction var29;
         if (clientbound) {
            Objects.requireNonNull(mappingData);
            var29 = mappingData::getNewSoundId;
         } else {
            Objects.requireNonNull(mappingData);
            var29 = mappingData::getOldSoundId;
         }

         Int2IntFunction soundIdRewriter = var29;
         container.replace(StructuredDataKey.INSTRUMENT, (value) -> value.isDirect() ? Holder.of(((Instrument)value.value()).rewrite(soundIdRewriter)) : value);
         container.replace(StructuredDataKey.JUKEBOX_PLAYABLE, (value) -> value.rewrite(soundIdRewriter));
      }

      if (clientbound && this.protocol.getComponentRewriter() != null) {
         this.updateComponent(connection, item, StructuredDataKey.ITEM_NAME, "item_name");
         this.updateComponent(connection, item, StructuredDataKey.CUSTOM_NAME, "custom_name");
         Tag[] lore = (Tag[])container.get(StructuredDataKey.LORE);
         if (lore != null) {
            for(Tag tag : lore) {
               this.protocol.getComponentRewriter().processTag(connection, tag);
            }
         }

         WrittenBook book = (WrittenBook)container.get(StructuredDataKey.WRITTEN_BOOK_CONTENT);
         if (book != null) {
            for(FilterableComponent page : book.pages()) {
               this.protocol.getComponentRewriter().processTag(connection, (Tag)page.raw());
               if (page.isFiltered()) {
                  this.protocol.getComponentRewriter().processTag(connection, (Tag)page.filtered());
               }
            }
         }
      }

      ItemHandler itemHandler = clientbound ? this::handleItemToClient : this::handleItemToServer;

      for(Map.Entry entry : container.data().entrySet()) {
         StructuredData<?> data = (StructuredData)entry.getValue();
         if (!data.isEmpty()) {
            StructuredDataKey<?> key = (StructuredDataKey)entry.getKey();
            Class<?> outputClass = key.type().getOutputClass();
            if (outputClass == Item.class) {
               data.setValue(itemHandler.rewrite(connection, (Item)data.value()));
            } else if (outputClass == Item[].class) {
               Item[] items = (Item[])data.value();

               for(int i = 0; i < items.length; ++i) {
                  items[i] = itemHandler.rewrite(connection, items[i]);
               }
            }
         }
      }

   }

   protected void updateComponent(UserConnection connection, Item item, StructuredDataKey key, String backupKey) {
      Tag name = (Tag)item.dataContainer().get(key);
      if (name != null) {
         Tag originalName = name.copy();
         this.protocol.getComponentRewriter().processTag(connection, name);
         if (!name.equals(originalName)) {
            this.saveTag(this.createCustomTag(item), originalName, backupKey);
         }

      }
   }

   protected void restoreTextComponents(Item item) {
      StructuredDataContainer data = item.dataContainer();
      CompoundTag customData = (CompoundTag)data.get(StructuredDataKey.CUSTOM_DATA);
      if (customData != null) {
         if (customData.remove(this.nbtTagName("added_custom_name")) != null) {
            data.remove(StructuredDataKey.CUSTOM_NAME);
         } else {
            Tag customName = this.removeBackupTag(customData, "custom_name");
            if (customName != null) {
               data.set(StructuredDataKey.CUSTOM_NAME, customName);
            }

            Tag itemName = this.removeBackupTag(customData, "item_name");
            if (itemName != null) {
               data.set(StructuredDataKey.ITEM_NAME, itemName);
            }
         }

      }
   }

   protected CompoundTag createCustomTag(Item item) {
      StructuredDataContainer data = item.dataContainer();
      CompoundTag customData = (CompoundTag)data.get(StructuredDataKey.CUSTOM_DATA);
      if (customData == null) {
         customData = new CompoundTag();
         data.set(StructuredDataKey.CUSTOM_DATA, customData);
      }

      return customData;
   }

   protected void saveTag(CompoundTag customData, Tag tag, String name) {
      String backupName = this.nbtTagName(name);
      if (!customData.contains(backupName)) {
         customData.put(backupName, tag);
      }

   }

   protected @Nullable Tag removeBackupTag(CompoundTag customData, String tagName) {
      return customData.remove(this.nbtTagName(tagName));
   }

   @FunctionalInterface
   private interface ItemHandler {
      Item rewrite(UserConnection var1, Item var2);
   }
}
