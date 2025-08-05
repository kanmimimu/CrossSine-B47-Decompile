package com.viaversion.viaversion.protocols.v1_10to1_11.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;

public class BlockEntityMappings1_11 {
   private static final BiMap OLD_TO_NEW_NAMES = HashBiMap.create();

   private static void rewrite(String oldName, String newName) {
      OLD_TO_NEW_NAMES.put(oldName, Key.namespaced(newName));
   }

   public static BiMap inverse() {
      return OLD_TO_NEW_NAMES.inverse();
   }

   public static String toNewIdentifier(String oldId) {
      String newName = (String)OLD_TO_NEW_NAMES.get(oldId);
      return newName != null ? newName : oldId;
   }

   static {
      rewrite("Furnace", "furnace");
      rewrite("Chest", "chest");
      rewrite("EnderChest", "ender_chest");
      rewrite("RecordPlayer", "jukebox");
      rewrite("Trap", "dispenser");
      rewrite("Dropper", "dropper");
      rewrite("Sign", "sign");
      rewrite("MobSpawner", "mob_spawner");
      rewrite("Music", "noteblock");
      rewrite("Piston", "piston");
      rewrite("Cauldron", "brewing_stand");
      rewrite("EnchantTable", "enchanting_table");
      rewrite("Airportal", "end_portal");
      rewrite("Beacon", "beacon");
      rewrite("Skull", "skull");
      rewrite("DLDetector", "daylight_detector");
      rewrite("Hopper", "hopper");
      rewrite("Comparator", "comparator");
      rewrite("FlowerPot", "flower_pot");
      rewrite("Banner", "banner");
      rewrite("Structure", "structure_block");
      rewrite("EndGateway", "end_gateway");
      rewrite("Control", "command_block");
   }
}
