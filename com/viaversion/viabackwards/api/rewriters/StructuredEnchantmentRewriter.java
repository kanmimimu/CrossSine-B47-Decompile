package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructuredEnchantmentRewriter {
   protected final BackwardsStructuredItemRewriter itemRewriter;
   boolean rewriteIds = true;

   public StructuredEnchantmentRewriter(BackwardsStructuredItemRewriter itemRewriter) {
      this.itemRewriter = itemRewriter;
   }

   public void handleToClient(Item item) {
      StructuredDataContainer data = item.dataContainer();
      BackwardsMappingData mappingData = ((BackwardsProtocol)this.itemRewriter.protocol()).getMappingData();
      IdRewriteFunction idRewriteFunction = (id) -> {
         Mappings mappings = mappingData.getEnchantmentMappings();
         return mappings.getNewId(id);
      };
      DescriptionSupplier descriptionSupplier = (id, level) -> {
         String remappedName = mappingData.mappedEnchantmentName(id);
         return ComponentUtil.jsonStringToTag(ChatUtil.legacyToJsonString("§7" + remappedName, "enchantment.level.%s".formatted(level), true));
      };
      this.rewriteEnchantmentsToClient(data, StructuredDataKey.ENCHANTMENTS, idRewriteFunction, descriptionSupplier, false);
      this.rewriteEnchantmentsToClient(data, StructuredDataKey.STORED_ENCHANTMENTS, idRewriteFunction, descriptionSupplier, true);
   }

   public void handleToServer(Item item) {
      StructuredDataContainer data = item.dataContainer();
      CompoundTag customData = (CompoundTag)data.get(StructuredDataKey.CUSTOM_DATA);
      if (customData != null) {
         this.rewriteEnchantmentsToServer(data, customData, StructuredDataKey.ENCHANTMENTS);
         this.rewriteEnchantmentsToServer(data, customData, StructuredDataKey.STORED_ENCHANTMENTS);
      }

   }

   public void rewriteEnchantmentsToClient(StructuredDataContainer data, StructuredDataKey key, IdRewriteFunction rewriteFunction, DescriptionSupplier descriptionSupplier, boolean storedEnchant) {
      Enchantments enchantments = (Enchantments)data.get(key);
      if (enchantments != null && enchantments.size() != 0) {
         List<Tag> loreToAdd = new ArrayList();
         boolean removedEnchantments = false;
         boolean updatedLore = false;
         ObjectIterator<Int2IntMap.Entry> iterator = enchantments.enchantments().int2IntEntrySet().iterator();
         List<PendingIdChange> updatedIds = new ArrayList();

         while(iterator.hasNext()) {
            Int2IntMap.Entry entry = (Int2IntMap.Entry)iterator.next();
            int id = entry.getIntKey();
            int mappedId = rewriteFunction.rewrite(id);
            int level = entry.getIntValue();
            if (mappedId != -1) {
               if (this.rewriteIds) {
                  updatedIds.add(new PendingIdChange(id, mappedId, level));
               }
            } else {
               if (!removedEnchantments) {
                  CompoundTag customData = this.customData(data);
                  this.itemRewriter.saveListTag(customData, this.asTag(enchantments), key.identifier());
                  removedEnchantments = true;
               }

               Tag description = descriptionSupplier.get(id, level);
               if (description != null && enchantments.showInTooltip()) {
                  loreToAdd.add(description);
                  updatedLore = true;
               }

               iterator.remove();
            }
         }

         for(PendingIdChange change : updatedIds) {
            enchantments.remove(change.id());
         }

         for(PendingIdChange change : updatedIds) {
            enchantments.add(change.mappedId(), change.level());
         }

         if (removedEnchantments) {
            CompoundTag tag = this.customData(data);
            if (!storedEnchant && enchantments.size() == 0) {
               Boolean glintOverride = (Boolean)data.get(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE);
               if (glintOverride != null) {
                  tag.putBoolean(this.itemRewriter.nbtTagName("glint"), glintOverride);
               } else {
                  tag.putBoolean(this.itemRewriter.nbtTagName("noglint"), true);
               }

               data.set(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, true);
            }

            if (enchantments.showInTooltip()) {
               BackwardsStructuredItemRewriter var10001 = this.itemRewriter;
               String var18 = key.identifier();
               tag.putBoolean(var10001.nbtTagName("show_" + var18), true);
            }
         }

         if (updatedLore) {
            CompoundTag tag = this.customData(data);
            Tag[] lore = (Tag[])data.get(StructuredDataKey.LORE);
            if (lore != null) {
               List<Tag> loreList = Arrays.asList(lore);
               this.itemRewriter.saveGenericTagList(tag, loreList, "lore");
               loreToAdd.addAll(loreList);
            } else {
               tag.putBoolean(this.itemRewriter.nbtTagName("nolore"), true);
            }

            data.set(StructuredDataKey.LORE, (Tag[])loreToAdd.toArray(new Tag[0]));
         }

      }
   }

   CompoundTag customData(StructuredDataContainer data) {
      CompoundTag tag = (CompoundTag)data.get(StructuredDataKey.CUSTOM_DATA);
      if (tag == null) {
         tag = new CompoundTag();
         data.set(StructuredDataKey.CUSTOM_DATA, tag);
      }

      return tag;
   }

   ListTag asTag(Enchantments enchantments) {
      ListTag<CompoundTag> listTag = new ListTag(CompoundTag.class);

      for(Int2IntMap.Entry entry : enchantments.enchantments().int2IntEntrySet()) {
         CompoundTag enchantment = new CompoundTag();
         enchantment.putInt("id", entry.getIntKey());
         enchantment.putInt("lvl", entry.getIntValue());
         listTag.add(enchantment);
      }

      return listTag;
   }

   public void rewriteEnchantmentsToServer(StructuredDataContainer data, CompoundTag tag, StructuredDataKey key) {
      ListTag<CompoundTag> enchantmentsTag = this.itemRewriter.removeListTag(tag, key.identifier(), CompoundTag.class);
      if (enchantmentsTag != null) {
         Tag glintTag = tag.remove(this.itemRewriter.nbtTagName("glint"));
         if (glintTag instanceof ByteTag) {
            data.set(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, ((NumberTag)glintTag).asBoolean());
         } else if (tag.remove(this.itemRewriter.nbtTagName("noglint")) != null) {
            data.remove(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE);
         }

         List<Tag> lore = this.itemRewriter.removeGenericTagList(tag, "lore");
         if (lore != null) {
            data.set(StructuredDataKey.LORE, (Tag[])lore.toArray(new Tag[0]));
         } else if (tag.remove(this.itemRewriter.nbtTagName("nolore")) != null) {
            data.remove(StructuredDataKey.LORE);
         }

         BackwardsStructuredItemRewriter var10003 = this.itemRewriter;
         String var11 = key.identifier();
         Enchantments enchantments = new Enchantments(tag.remove(var10003.nbtTagName("show_" + var11)) != null);

         for(CompoundTag enchantment : enchantmentsTag) {
            enchantments.add(enchantment.getInt("id"), enchantment.getInt("lvl"));
         }

         data.set(key, enchantments);
      }
   }

   public void setRewriteIds(boolean rewriteIds) {
      this.rewriteIds = rewriteIds;
   }

   private static final class PendingIdChange {
      final int id;
      final int mappedId;
      final int level;

      PendingIdChange(int id, int mappedId, int level) {
         this.id = id;
         this.mappedId = mappedId;
         this.level = level;
      }

      public int id() {
         return this.id;
      }

      public int mappedId() {
         return this.mappedId;
      }

      public int level() {
         return this.level;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof PendingIdChange)) {
            return false;
         } else {
            PendingIdChange var2 = (PendingIdChange)var1;
            return this.id == var2.id && this.mappedId == var2.mappedId && this.level == var2.level;
         }
      }

      public int hashCode() {
         return ((0 * 31 + Integer.hashCode(this.id)) * 31 + Integer.hashCode(this.mappedId)) * 31 + Integer.hashCode(this.level);
      }

      public String toString() {
         return String.format("%s[id=%s, mappedId=%s, level=%s]", this.getClass().getSimpleName(), Integer.toString(this.id), Integer.toString(this.mappedId), Integer.toString(this.level));
      }
   }

   @FunctionalInterface
   public interface DescriptionSupplier {
      Tag get(int var1, int var2);
   }
}
