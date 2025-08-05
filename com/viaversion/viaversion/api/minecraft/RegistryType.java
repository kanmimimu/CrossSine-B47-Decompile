package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum RegistryType {
   BLOCK("block"),
   ITEM("item"),
   FLUID("fluid"),
   ENTITY("entity_type"),
   GAME_EVENT("game_event"),
   ENCHANTMENT("enchantment");

   private static final Map MAP = new HashMap();
   private static final RegistryType[] VALUES = values();
   private final String resourceLocation;

   public static RegistryType[] getValues() {
      return VALUES;
   }

   public static @Nullable RegistryType getByKey(String resourceKey) {
      return (RegistryType)MAP.get(resourceKey);
   }

   private RegistryType(String resourceLocation) {
      this.resourceLocation = resourceLocation;
   }

   public String resourceLocation() {
      return this.resourceLocation;
   }

   // $FF: synthetic method
   private static RegistryType[] $values() {
      return new RegistryType[]{BLOCK, ITEM, FLUID, ENTITY, GAME_EVENT, ENCHANTMENT};
   }

   static {
      for(RegistryType type : getValues()) {
         MAP.put(type.resourceLocation, type);
      }

   }
}
