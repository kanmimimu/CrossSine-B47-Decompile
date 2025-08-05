package com.viaversion.viaversion.protocols.v1_8to1_9.data;

import java.util.HashMap;
import java.util.Map;

public class EntityIds1_8 {
   public static final Map ENTITY_NAME_TO_ID = new HashMap();
   public static final Map ENTITY_ID_TO_NAME = new HashMap();

   private static void register(int id, String name) {
      ENTITY_ID_TO_NAME.put(id, name);
      ENTITY_NAME_TO_ID.put(name, id);
   }

   static {
      register(1, "Item");
      register(2, "XPOrb");
      register(7, "ThrownEgg");
      register(8, "LeashKnot");
      register(9, "Painting");
      register(10, "Arrow");
      register(11, "Snowball");
      register(12, "Fireball");
      register(13, "SmallFireball");
      register(14, "ThrownEnderpearl");
      register(15, "EyeOfEnderSignal");
      register(16, "ThrownPotion");
      register(17, "ThrownExpBottle");
      register(18, "ItemFrame");
      register(19, "WitherSkull");
      register(20, "PrimedTnt");
      register(21, "FallingSand");
      register(22, "FireworksRocketEntity");
      register(30, "ArmorStand");
      register(40, "MinecartCommandBlock");
      register(41, "Boat");
      register(42, "MinecartRideable");
      register(43, "MinecartChest");
      register(44, "MinecartFurnace");
      register(45, "MinecartTNT");
      register(46, "MinecartHopper");
      register(47, "MinecartSpawner");
      register(48, "Mob");
      register(49, "Monster");
      register(50, "Creeper");
      register(51, "Skeleton");
      register(52, "Spider");
      register(53, "Giant");
      register(54, "Zombie");
      register(55, "Slime");
      register(56, "Ghast");
      register(57, "PigZombie");
      register(58, "Enderman");
      register(59, "CaveSpider");
      register(60, "Silverfish");
      register(61, "Blaze");
      register(62, "LavaSlime");
      register(63, "EnderDragon");
      register(64, "WitherBoss");
      register(65, "Bat");
      register(66, "Witch");
      register(67, "Endermite");
      register(68, "Guardian");
      register(90, "Pig");
      register(91, "Sheep");
      register(92, "Cow");
      register(93, "Chicken");
      register(94, "Squid");
      register(95, "Wolf");
      register(96, "MushroomCow");
      register(97, "SnowMan");
      register(98, "Ozelot");
      register(99, "VillagerGolem");
      register(100, "EntityHorse");
      register(101, "Rabbit");
      register(120, "Villager");
      register(200, "EnderCrystal");
   }
}
