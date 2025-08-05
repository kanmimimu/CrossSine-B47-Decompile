package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LegacyEnchantmentRewriter {
   private final Map enchantmentMappings;
   private final String nbtTagName;
   private final boolean dummyEnchantment;
   private Set hideLevelForEnchants;

   public LegacyEnchantmentRewriter(String nbtTagName) {
      this(nbtTagName, true);
   }

   public LegacyEnchantmentRewriter(String nbtTagName, boolean dummyEnchantment) {
      this.enchantmentMappings = new HashMap();
      this.nbtTagName = nbtTagName;
      this.dummyEnchantment = dummyEnchantment;
   }

   public void registerEnchantment(int id, String replacementLore) {
      this.enchantmentMappings.put((short)id, replacementLore);
   }

   public void handleToClient(Item item) {
      CompoundTag tag = item.tag();
      if (tag != null) {
         if (tag.getListTag("ench") != null) {
            this.rewriteEnchantmentsToClient(tag, false);
         }

         if (tag.getListTag("StoredEnchantments") != null) {
            this.rewriteEnchantmentsToClient(tag, true);
         }

      }
   }

   public void handleToServer(Item item) {
      CompoundTag tag = item.tag();
      if (tag != null) {
         String var4 = this.nbtTagName;
         if (tag.getListTag(var4 + "|ench", CompoundTag.class) != null) {
            this.rewriteEnchantmentsToServer(tag, false);
         }

         String var6 = this.nbtTagName;
         if (tag.getListTag(var6 + "|StoredEnchantments", CompoundTag.class) != null) {
            this.rewriteEnchantmentsToServer(tag, true);
         }

      }
   }

   public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
      String key = storedEnchant ? "StoredEnchantments" : "ench";
      ListTag<CompoundTag> enchantments = tag.getListTag(key, CompoundTag.class);
      ListTag<CompoundTag> remappedEnchantments = new ListTag(CompoundTag.class);
      List<StringTag> lore = new ArrayList();

      for(CompoundTag enchantmentEntry : enchantments.copy()) {
         NumberTag idTag = enchantmentEntry.getNumberTag("id");
         if (idTag != null) {
            short newId = idTag.asShort();
            String enchantmentName = (String)this.enchantmentMappings.get(newId);
            if (enchantmentName != null) {
               enchantments.remove(enchantmentEntry);
               NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
               short level = levelTag != null ? levelTag.asShort() : 1;
               if (this.hideLevelForEnchants != null && this.hideLevelForEnchants.contains(newId)) {
                  lore.add(new StringTag(enchantmentName));
               } else {
                  String var16 = EnchantmentRewriter.getRomanNumber(level);
                  lore.add(new StringTag(enchantmentName + " " + var16));
               }

               remappedEnchantments.add(enchantmentEntry);
            }
         }
      }

      if (!lore.isEmpty()) {
         if (this.dummyEnchantment && !storedEnchant && enchantments.isEmpty()) {
            CompoundTag dummyEnchantment = new CompoundTag();
            dummyEnchantment.putShort("id", (short)0);
            dummyEnchantment.putShort("lvl", (short)0);
            enchantments.add(dummyEnchantment);
            String var18 = this.nbtTagName;
            tag.put(var18 + "|dummyEnchant", new ByteTag(false));
            NumberTag hideFlags = tag.getNumberTag("HideFlags");
            if (hideFlags == null) {
               hideFlags = new IntTag();
            } else {
               String var20 = this.nbtTagName;
               tag.putInt(var20 + "|oldHideFlags", hideFlags.asByte());
            }

            int flags = hideFlags.asByte() | 1;
            tag.putInt("HideFlags", flags);
         }

         String var22 = this.nbtTagName;
         tag.put(var22 + "|" + key, remappedEnchantments);
         CompoundTag display = tag.getCompoundTag("display");
         if (display == null) {
            tag.put("display", display = new CompoundTag());
         }

         ListTag<StringTag> loreTag = display.getListTag("Lore", StringTag.class);
         if (loreTag == null) {
            display.put("Lore", loreTag = new ListTag(StringTag.class));
         }

         lore.addAll(loreTag.getValue());
         loreTag.setValue(lore);
      }

   }

   public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
      String key = storedEnchant ? "StoredEnchantments" : "ench";
      ListTag<CompoundTag> enchantments = tag.getListTag(key, CompoundTag.class);
      if (enchantments == null) {
         enchantments = new ListTag(CompoundTag.class);
      }

      if (!storedEnchant) {
         String var12 = this.nbtTagName;
         if (tag.remove(var12 + "|dummyEnchant") != null) {
            for(CompoundTag enchantment : enchantments.copy()) {
               NumberTag idTag = enchantment.getNumberTag("id");
               NumberTag levelTag = enchantment.getNumberTag("lvl");
               short id = idTag != null ? idTag.asShort() : 0;
               short level = levelTag != null ? levelTag.asShort() : 0;
               if (id == 0 && level == 0) {
                  enchantments.remove(enchantment);
               }
            }

            String var14 = this.nbtTagName;
            Tag hideFlags = tag.remove(var14 + "|oldHideFlags");
            if (hideFlags instanceof IntTag) {
               IntTag intTag = (IntTag)hideFlags;
               tag.putInt("HideFlags", intTag.asByte());
            } else {
               tag.remove("HideFlags");
            }
         }
      }

      CompoundTag display = tag.getCompoundTag("display");
      ListTag<StringTag> lore = display != null ? display.getListTag("Lore", StringTag.class) : null;
      String var16 = this.nbtTagName;
      ListTag<CompoundTag> remappedEnchantments = (ListTag)tag.remove(var16 + "|" + key);

      for(CompoundTag enchantment : remappedEnchantments.copy()) {
         enchantments.add(enchantment);
         if (lore != null && !lore.isEmpty()) {
            lore.remove((StringTag)lore.get(0));
         }
      }

      if (lore != null && lore.isEmpty()) {
         display.remove("Lore");
         if (display.isEmpty()) {
            tag.remove("display");
         }
      }

      tag.put(key, enchantments);
   }

   public void setHideLevelForEnchants(int... enchants) {
      this.hideLevelForEnchants = new HashSet();

      for(int enchant : enchants) {
         this.hideLevelForEnchants.add((short)enchant);
      }

   }
}
