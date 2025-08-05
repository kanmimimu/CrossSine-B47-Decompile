package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsItemRewriter extends BackwardsItemRewriterBase {
   public BackwardsItemRewriter(BackwardsProtocol protocol, Type itemType, Type itemArrayType) {
      super(protocol, itemType, itemArrayType, true);
   }

   public BackwardsItemRewriter(BackwardsProtocol protocol, Type itemType, Type itemArrayType, Type mappedItemType, Type mappedItemArrayType) {
      super(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, true);
   }

   public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         CompoundTag display = item.tag() != null ? item.tag().getCompoundTag("display") : null;
         if (((BackwardsProtocol)this.protocol).getComponentRewriter() != null && display != null) {
            StringTag name = display.getStringTag("Name");
            if (name != null) {
               String newValue = ((BackwardsProtocol)this.protocol).getComponentRewriter().processText(connection, name.getValue()).toString();
               if (!newValue.equals(name.getValue())) {
                  this.saveStringTag(display, name, "Name");
               }

               name.setValue(newValue);
            }

            ListTag<StringTag> lore = display.getListTag("Lore", StringTag.class);
            if (lore != null) {
               boolean changed = false;

               for(StringTag loreEntry : lore) {
                  String newValue = ((BackwardsProtocol)this.protocol).getComponentRewriter().processText(connection, loreEntry.getValue()).toString();
                  if (!changed && !newValue.equals(loreEntry.getValue())) {
                     changed = true;
                     this.saveListTag(display, lore, "Lore");
                  }

                  loreEntry.setValue(newValue);
               }
            }
         }

         MappedItem data = ((BackwardsProtocol)this.protocol).getMappingData() != null ? ((BackwardsProtocol)this.protocol).getMappingData().getMappedItem(item.identifier()) : null;
         if (data == null) {
            return super.handleItemToClient(connection, item);
         } else {
            if (item.tag() == null) {
               item.setTag(new CompoundTag());
            }

            item.tag().putInt(this.nbtTagName("id"), item.identifier());
            item.setIdentifier(data.id());
            if (data.customModelData() != null && !item.tag().contains("CustomModelData")) {
               item.tag().putInt("CustomModelData", data.customModelData());
            }

            if (display == null) {
               item.tag().put("display", display = new CompoundTag());
            }

            if (!display.contains("Name")) {
               display.put("Name", new StringTag(data.jsonName()));
               display.put(this.nbtTagName("customName"), new ByteTag(false));
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
         }

         return item;
      }
   }
}
