package com.viaversion.viabackwards.provider;

import com.viaversion.viabackwards.protocol.v1_20_2to1_20.provider.AdvancementCriteriaProvider;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

public final class BukkitAdvancementCriteriaProvider extends AdvancementCriteriaProvider {
   private static final String[] EMPTY_CRITERIA = new String[0];

   public String[] getCriteria(String advancementKey) {
      Advancement advancement = Bukkit.getAdvancement(NamespacedKey.fromString(advancementKey));
      return advancement == null ? EMPTY_CRITERIA : (String[])advancement.getCriteria().toArray(EMPTY_CRITERIA);
   }
}
