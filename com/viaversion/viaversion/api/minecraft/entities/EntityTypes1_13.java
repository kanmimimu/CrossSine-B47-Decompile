package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.Map;

public class EntityTypes1_13 {
   public static EntityType getTypeFromId(int typeId, boolean object) {
      EntityType type;
      if (object) {
         type = EntityTypes1_13.ObjectType.getEntityType(typeId);
      } else {
         type = EntityTypes1_13.EntityType.findById(typeId);
      }

      if (type == null) {
         Via.getPlatform().getLogger().severe("Could not find 1.13 type id " + typeId + " objectType=" + object);
         return EntityTypes1_13.EntityType.ENTITY;
      } else {
         return type;
      }
   }

   public static enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
      ENTITY,
      AREA_EFFECT_CLOUD(0, ENTITY),
      END_CRYSTAL(16, ENTITY),
      EVOKER_FANGS(20, ENTITY),
      EXPERIENCE_ORB(22, ENTITY),
      EYE_OF_ENDER(23, ENTITY),
      FALLING_BLOCK(24, ENTITY),
      ITEM(32, ENTITY),
      TNT(55, ENTITY),
      LIGHTNING_BOLT(91, ENTITY),
      HANGING_ENTITY(ENTITY),
      LEASH_KNOT(35, HANGING_ENTITY),
      ITEM_FRAME(33, HANGING_ENTITY),
      PAINTING(49, HANGING_ENTITY),
      PROJECTILE(ENTITY),
      FIREWORK_ROCKET(25, ENTITY),
      LLAMA_SPIT(37, ENTITY),
      SHULKER_BULLET(60, ENTITY),
      SNOWBALL(67, PROJECTILE),
      ENDER_PEARL(75, PROJECTILE),
      EGG(74, PROJECTILE),
      EXPERIENCE_BOTTLE(76, PROJECTILE),
      POTION(77, PROJECTILE),
      FISHING_BOBBER(93, ENTITY),
      ABSTRACT_ARROW(ENTITY),
      ARROW(2, ABSTRACT_ARROW),
      SPECTRAL_ARROW(68, ABSTRACT_ARROW),
      TRIDENT(94, ABSTRACT_ARROW),
      HURTING_PROJECTILE(ENTITY),
      DRAGON_FIREBALL(13, HURTING_PROJECTILE),
      FIREBALL(34, HURTING_PROJECTILE),
      SMALL_FIREBALL(65, HURTING_PROJECTILE),
      WITHER_SKULL(85, HURTING_PROJECTILE),
      BOAT(5, ENTITY),
      ABSTRACT_MINECART(ENTITY),
      MINECART(39, ABSTRACT_MINECART),
      FURNACE_MINECART(42, ABSTRACT_MINECART),
      COMMAND_BLOCK_MINECART(41, ABSTRACT_MINECART),
      TNT_MINECART(45, ABSTRACT_MINECART),
      SPAWNER_MINECART(44, ABSTRACT_MINECART),
      ABSTRACT_MINECART_CONTAINER(ABSTRACT_MINECART),
      CHEST_MINECART(40, ABSTRACT_MINECART_CONTAINER),
      HOPPER_MINECART(43, ABSTRACT_MINECART_CONTAINER),
      LIVING_ENTITY_BASE(ENTITY),
      ARMOR_STAND(1, LIVING_ENTITY_BASE),
      PLAYER(92, LIVING_ENTITY_BASE),
      LIVING_ENTITY(LIVING_ENTITY_BASE),
      ENDER_DRAGON(17, LIVING_ENTITY),
      ABSTRACT_CREATURE(LIVING_ENTITY),
      SLIME(64, LIVING_ENTITY),
      MAGMA_CUBE(38, SLIME),
      FLYING_MOB(ABSTRACT_CREATURE),
      GHAST(26, FLYING_MOB),
      PHANTOM(90, FLYING_MOB),
      AMBIENT_CREATURE(-1, LIVING_ENTITY),
      BAT(3, AMBIENT_CREATURE),
      ABSTRACT_GOLEM(ABSTRACT_CREATURE),
      SNOW_GOLEM(66, ABSTRACT_GOLEM),
      IRON_GOLEM(80, ABSTRACT_GOLEM),
      SHULKER(59, ABSTRACT_GOLEM),
      WATER_ANIMAL(ABSTRACT_CREATURE),
      SQUID(70, WATER_ANIMAL),
      DOLPHIN(12, WATER_ANIMAL),
      ABSTRACT_FISH(WATER_ANIMAL),
      COD(8, ABSTRACT_FISH),
      PUFFERFISH(52, ABSTRACT_FISH),
      SALMON(57, ABSTRACT_FISH),
      TROPICAL_FISH(72, ABSTRACT_FISH),
      ABSTRACT_AGEABLE(ABSTRACT_CREATURE),
      VILLAGER(79, ABSTRACT_AGEABLE),
      ABSTRACT_ANIMAL(ABSTRACT_AGEABLE),
      CHICKEN(7, ABSTRACT_ANIMAL),
      COW(9, ABSTRACT_ANIMAL),
      MOOSHROOM(47, COW),
      PIG(51, ABSTRACT_ANIMAL),
      POLAR_BEAR(54, ABSTRACT_ANIMAL),
      RABBIT(56, ABSTRACT_ANIMAL),
      SHEEP(58, ABSTRACT_ANIMAL),
      TURTLE(73, ABSTRACT_ANIMAL),
      TAMABLE_ANIMAL(ABSTRACT_ANIMAL),
      OCELOT(48, TAMABLE_ANIMAL),
      WOLF(86, TAMABLE_ANIMAL),
      ABSTRACT_SHOULDER_RIDING(TAMABLE_ANIMAL),
      PARROT(50, ABSTRACT_SHOULDER_RIDING),
      ABSTRACT_HORSE(ABSTRACT_ANIMAL),
      HORSE(29, ABSTRACT_HORSE),
      SKELETON_HORSE(63, ABSTRACT_HORSE),
      ZOMBIE_HORSE(88, ABSTRACT_HORSE),
      CHESTED_HORSE(ABSTRACT_HORSE),
      DONKEY(11, CHESTED_HORSE),
      MULE(46, CHESTED_HORSE),
      LLAMA(36, CHESTED_HORSE),
      ABSTRACT_MONSTER(ABSTRACT_CREATURE),
      BLAZE(4, ABSTRACT_MONSTER),
      CREEPER(10, ABSTRACT_MONSTER),
      ENDERMITE(19, ABSTRACT_MONSTER),
      ENDERMAN(18, ABSTRACT_MONSTER),
      GIANT(27, ABSTRACT_MONSTER),
      SILVERFISH(61, ABSTRACT_MONSTER),
      VEX(78, ABSTRACT_MONSTER),
      WITCH(82, ABSTRACT_MONSTER),
      WITHER(83, ABSTRACT_MONSTER),
      ABSTRACT_SKELETON(ABSTRACT_MONSTER),
      SKELETON(62, ABSTRACT_SKELETON),
      STRAY(71, ABSTRACT_SKELETON),
      WITHER_SKELETON(84, ABSTRACT_SKELETON),
      ZOMBIE(87, ABSTRACT_MONSTER),
      DROWNED(14, ZOMBIE),
      HUSK(30, ZOMBIE),
      ZOMBIE_PIGMAN(53, ZOMBIE),
      ZOMBIE_VILLAGER(89, ZOMBIE),
      GUARDIAN(28, ABSTRACT_MONSTER),
      ELDER_GUARDIAN(15, GUARDIAN),
      SPIDER(69, ABSTRACT_MONSTER),
      CAVE_SPIDER(6, SPIDER),
      ABSTRACT_ILLAGER(ABSTRACT_MONSTER),
      SPELLCASTER_ILLAGER(ABSTRACT_ILLAGER),
      VINDICATOR(81, ABSTRACT_ILLAGER),
      EVOKER(21, SPELLCASTER_ILLAGER),
      ILLUSIONER(31, SPELLCASTER_ILLAGER);

      static final Map TYPES = new HashMap();
      final int id;
      final EntityType parent;

      EntityType() {
         this.id = -1;
         this.parent = null;
      }

      EntityType(EntityType parent) {
         this.id = -1;
         this.parent = parent;
      }

      EntityType(int id, EntityType parent) {
         this.id = id;
         this.parent = parent;
      }

      public int getId() {
         return this.id;
      }

      public EntityType getParent() {
         return this.parent;
      }

      public String identifier() {
         throw new UnsupportedOperationException();
      }

      public boolean isAbstractType() {
         return this.id != -1;
      }

      public static EntityType findById(int id) {
         return id == -1 ? null : (EntityType)TYPES.get(id);
      }

      // $FF: synthetic method
      static EntityType[] $values() {
         return new EntityType[]{ENTITY, AREA_EFFECT_CLOUD, END_CRYSTAL, EVOKER_FANGS, EXPERIENCE_ORB, EYE_OF_ENDER, FALLING_BLOCK, ITEM, TNT, LIGHTNING_BOLT, HANGING_ENTITY, LEASH_KNOT, ITEM_FRAME, PAINTING, PROJECTILE, FIREWORK_ROCKET, LLAMA_SPIT, SHULKER_BULLET, SNOWBALL, ENDER_PEARL, EGG, EXPERIENCE_BOTTLE, POTION, FISHING_BOBBER, ABSTRACT_ARROW, ARROW, SPECTRAL_ARROW, TRIDENT, HURTING_PROJECTILE, DRAGON_FIREBALL, FIREBALL, SMALL_FIREBALL, WITHER_SKULL, BOAT, ABSTRACT_MINECART, MINECART, FURNACE_MINECART, COMMAND_BLOCK_MINECART, TNT_MINECART, SPAWNER_MINECART, ABSTRACT_MINECART_CONTAINER, CHEST_MINECART, HOPPER_MINECART, LIVING_ENTITY_BASE, ARMOR_STAND, PLAYER, LIVING_ENTITY, ENDER_DRAGON, ABSTRACT_CREATURE, SLIME, MAGMA_CUBE, FLYING_MOB, GHAST, PHANTOM, AMBIENT_CREATURE, BAT, ABSTRACT_GOLEM, SNOW_GOLEM, IRON_GOLEM, SHULKER, WATER_ANIMAL, SQUID, DOLPHIN, ABSTRACT_FISH, COD, PUFFERFISH, SALMON, TROPICAL_FISH, ABSTRACT_AGEABLE, VILLAGER, ABSTRACT_ANIMAL, CHICKEN, COW, MOOSHROOM, PIG, POLAR_BEAR, RABBIT, SHEEP, TURTLE, TAMABLE_ANIMAL, OCELOT, WOLF, ABSTRACT_SHOULDER_RIDING, PARROT, ABSTRACT_HORSE, HORSE, SKELETON_HORSE, ZOMBIE_HORSE, CHESTED_HORSE, DONKEY, MULE, LLAMA, ABSTRACT_MONSTER, BLAZE, CREEPER, ENDERMITE, ENDERMAN, GIANT, SILVERFISH, VEX, WITCH, WITHER, ABSTRACT_SKELETON, SKELETON, STRAY, WITHER_SKELETON, ZOMBIE, DROWNED, HUSK, ZOMBIE_PIGMAN, ZOMBIE_VILLAGER, GUARDIAN, ELDER_GUARDIAN, SPIDER, CAVE_SPIDER, ABSTRACT_ILLAGER, SPELLCASTER_ILLAGER, VINDICATOR, EVOKER, ILLUSIONER};
      }

      static {
         for(EntityType type : values()) {
            TYPES.put(type.id, type);
         }

      }
   }

   public static enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType {
      BOAT(1, EntityTypes1_13.EntityType.BOAT),
      ITEM(2, EntityTypes1_13.EntityType.ITEM),
      AREA_EFFECT_CLOUD(3, EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD),
      MINECART(10, EntityTypes1_13.EntityType.MINECART),
      TNT_PRIMED(50, EntityTypes1_13.EntityType.TNT),
      ENDER_CRYSTAL(51, EntityTypes1_13.EntityType.END_CRYSTAL),
      TIPPED_ARROW(60, EntityTypes1_13.EntityType.ARROW),
      SNOWBALL(61, EntityTypes1_13.EntityType.SNOWBALL),
      EGG(62, EntityTypes1_13.EntityType.EGG),
      FIREBALL(63, EntityTypes1_13.EntityType.FIREBALL),
      SMALL_FIREBALL(64, EntityTypes1_13.EntityType.SMALL_FIREBALL),
      ENDER_PEARL(65, EntityTypes1_13.EntityType.ENDER_PEARL),
      WITHER_SKULL(66, EntityTypes1_13.EntityType.WITHER_SKULL),
      SHULKER_BULLET(67, EntityTypes1_13.EntityType.SHULKER_BULLET),
      LLAMA_SPIT(68, EntityTypes1_13.EntityType.LLAMA_SPIT),
      FALLING_BLOCK(70, EntityTypes1_13.EntityType.FALLING_BLOCK),
      ITEM_FRAME(71, EntityTypes1_13.EntityType.ITEM_FRAME),
      EYE_OF_ENDER(72, EntityTypes1_13.EntityType.EYE_OF_ENDER),
      POTION(73, EntityTypes1_13.EntityType.POTION),
      EXPERIENCE_BOTTLE(75, EntityTypes1_13.EntityType.EXPERIENCE_BOTTLE),
      FIREWORK_ROCKET(76, EntityTypes1_13.EntityType.FIREWORK_ROCKET),
      LEASH(77, EntityTypes1_13.EntityType.LEASH_KNOT),
      ARMOR_STAND(78, EntityTypes1_13.EntityType.ARMOR_STAND),
      EVOKER_FANGS(79, EntityTypes1_13.EntityType.EVOKER_FANGS),
      FISHIHNG_HOOK(90, EntityTypes1_13.EntityType.FISHING_BOBBER),
      SPECTRAL_ARROW(91, EntityTypes1_13.EntityType.SPECTRAL_ARROW),
      DRAGON_FIREBALL(93, EntityTypes1_13.EntityType.DRAGON_FIREBALL),
      TRIDENT(94, EntityTypes1_13.EntityType.TRIDENT);

      static final Map TYPES = new HashMap();
      final int id;
      final EntityType type;

      ObjectType(int id, EntityType type) {
         this.id = id;
         this.type = type;
      }

      public int getId() {
         return this.id;
      }

      public EntityType getType() {
         return this.type;
      }

      public static ObjectType findById(int id) {
         return id == -1 ? null : (ObjectType)TYPES.get(id);
      }

      public static EntityType getEntityType(int id) {
         ObjectType objectType = findById(id);
         return objectType != null ? objectType.type : null;
      }

      // $FF: synthetic method
      static ObjectType[] $values() {
         return new ObjectType[]{BOAT, ITEM, AREA_EFFECT_CLOUD, MINECART, TNT_PRIMED, ENDER_CRYSTAL, TIPPED_ARROW, SNOWBALL, EGG, FIREBALL, SMALL_FIREBALL, ENDER_PEARL, WITHER_SKULL, SHULKER_BULLET, LLAMA_SPIT, FALLING_BLOCK, ITEM_FRAME, EYE_OF_ENDER, POTION, EXPERIENCE_BOTTLE, FIREWORK_ROCKET, LEASH, ARMOR_STAND, EVOKER_FANGS, FISHIHNG_HOOK, SPECTRAL_ARROW, DRAGON_FIREBALL, TRIDENT};
      }

      static {
         for(ObjectType type : values()) {
            TYPES.put(type.id, type);
         }

      }
   }
}
