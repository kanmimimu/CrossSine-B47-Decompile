package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class EntityList1_2_4 {
   private static final BiMap ENTITY_IDS = HashBiMap.create();

   public static int getEntityId(String entityName) {
      return (Integer)ENTITY_IDS.get(entityName);
   }

   public static String getEntityName(int entityId) {
      return (String)ENTITY_IDS.inverse().get(entityId);
   }

   static {
      ENTITY_IDS.put("Item", 1);
      ENTITY_IDS.put("XPOrb", 2);
      ENTITY_IDS.put("Painting", 9);
      ENTITY_IDS.put("Arrow", 10);
      ENTITY_IDS.put("Snowball", 11);
      ENTITY_IDS.put("Fireball", 12);
      ENTITY_IDS.put("SmallFireball", 13);
      ENTITY_IDS.put("ThrownEnderpearl", 14);
      ENTITY_IDS.put("EyeOfEnderSignal", 15);
      ENTITY_IDS.put("ThrownPotion", 16);
      ENTITY_IDS.put("ThrownExpBottle", 17);
      ENTITY_IDS.put("PrimedTnt", 20);
      ENTITY_IDS.put("FallingSand", 21);
      ENTITY_IDS.put("Minecart", 40);
      ENTITY_IDS.put("Boat", 41);
      ENTITY_IDS.put("Mob", 48);
      ENTITY_IDS.put("Monster", 49);
      ENTITY_IDS.put("Creeper", 50);
      ENTITY_IDS.put("Skeleton", 51);
      ENTITY_IDS.put("Spider", 52);
      ENTITY_IDS.put("Giant", 53);
      ENTITY_IDS.put("Zombie", 54);
      ENTITY_IDS.put("Slime", 55);
      ENTITY_IDS.put("Ghast", 56);
      ENTITY_IDS.put("PigZombie", 57);
      ENTITY_IDS.put("Enderman", 58);
      ENTITY_IDS.put("CaveSpider", 59);
      ENTITY_IDS.put("Silverfish", 60);
      ENTITY_IDS.put("Blaze", 61);
      ENTITY_IDS.put("LavaSlime", 62);
      ENTITY_IDS.put("EnderDragon", 63);
      ENTITY_IDS.put("Pig", 90);
      ENTITY_IDS.put("Sheep", 91);
      ENTITY_IDS.put("Cow", 92);
      ENTITY_IDS.put("Chicken", 93);
      ENTITY_IDS.put("Squid", 94);
      ENTITY_IDS.put("Wolf", 95);
      ENTITY_IDS.put("MushroomCow", 96);
      ENTITY_IDS.put("SnowMan", 97);
      ENTITY_IDS.put("Ozelot", 98);
      ENTITY_IDS.put("VillagerGolem", 99);
      ENTITY_IDS.put("Villager", 120);
      ENTITY_IDS.put("EnderCrystal", 200);
   }
}
