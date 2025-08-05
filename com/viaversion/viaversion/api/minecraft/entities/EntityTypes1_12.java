package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.Map;

public class EntityTypes1_12 {
   public static EntityType getTypeFromId(int typeId, boolean object) {
      EntityType type;
      if (object) {
         type = EntityTypes1_12.ObjectType.getEntityType(typeId);
      } else {
         type = EntityTypes1_12.EntityType.findById(typeId);
      }

      if (type == null) {
         Via.getPlatform().getLogger().severe("Could not find 1.12 type id " + typeId + " objectType=" + object);
         return EntityTypes1_12.EntityType.ENTITY;
      } else {
         return type;
      }
   }

   public static enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
      ENTITY,
      AREA_EFFECT_CLOUD(3, ENTITY),
      END_CRYSTAL(200, ENTITY),
      EVOKER_FANGS(33, ENTITY),
      EXPERIENCE_ORB(2, ENTITY),
      EYE_OF_ENDER(15, ENTITY),
      FALLING_BLOCK(21, ENTITY),
      ITEM(1, ENTITY),
      TNT(20, ENTITY),
      LIGHTNING_BOLT(ENTITY),
      HANGING_ENTITY(ENTITY),
      LEASH_KNOT(8, HANGING_ENTITY),
      ITEM_FRAME(18, HANGING_ENTITY),
      PAINTING(9, HANGING_ENTITY),
      PROJECTILE(ENTITY),
      FIREWORK_ROCKET(22, ENTITY),
      LLAMA_SPIT(104, ENTITY),
      SHULKER_BULLET(25, ENTITY),
      SNOWBALL(11, PROJECTILE),
      ENDER_PEARL(14, PROJECTILE),
      EGG(7, PROJECTILE),
      EXPERIENCE_BOTTLE(17, PROJECTILE),
      POTION(16, PROJECTILE),
      FISHING_HOOK(ENTITY),
      ABSTRACT_ARROW(ENTITY),
      ARROW(10, ABSTRACT_ARROW),
      SPECTRAL_ARROW(24, ABSTRACT_ARROW),
      HURTING_PROJECTILE(ENTITY),
      DRAGON_FIREBALL(26, HURTING_PROJECTILE),
      FIREBALL(12, HURTING_PROJECTILE),
      SMALL_FIREBALL(13, HURTING_PROJECTILE),
      WITHER_SKULL(19, HURTING_PROJECTILE),
      BOAT(41, ENTITY),
      ABSTRACT_MINECART(ENTITY),
      MINECART(42, ABSTRACT_MINECART),
      FURNACE_MINECART(44, ABSTRACT_MINECART),
      COMMAND_BLOCK_MINECART(40, ABSTRACT_MINECART),
      TNT_MINECART(45, ABSTRACT_MINECART),
      SPAWNER_MINECART(47, ABSTRACT_MINECART),
      ABSTRACT_MINECART_CONTAINER(ABSTRACT_MINECART),
      CHEST_MINECART(43, ABSTRACT_MINECART_CONTAINER),
      HOPPER_MINECART(46, ABSTRACT_MINECART_CONTAINER),
      LIVING_ENTITY_BASE(ENTITY),
      ARMOR_STAND(30, LIVING_ENTITY_BASE),
      PLAYER(LIVING_ENTITY_BASE),
      LIVING_ENTITY(LIVING_ENTITY_BASE),
      ENDER_DRAGON(63, LIVING_ENTITY),
      ABSTRACT_CREATURE(LIVING_ENTITY),
      SLIME(55, LIVING_ENTITY),
      MAGMA_CUBE(62, SLIME),
      FLYING_MOB(LIVING_ENTITY),
      GHAST(56, FLYING_MOB),
      AMBIENT_CREATURE(LIVING_ENTITY),
      BAT(65, AMBIENT_CREATURE),
      ABSTRACT_GOLEM(ABSTRACT_CREATURE),
      SNOW_GOLEM(97, ABSTRACT_GOLEM),
      IRON_GOLEM(99, ABSTRACT_GOLEM),
      SHULKER(69, ABSTRACT_GOLEM),
      WATER_ANIMAL(ABSTRACT_CREATURE),
      SQUID(94, WATER_ANIMAL),
      ABSTRACT_AGEABLE(ABSTRACT_CREATURE),
      VILLAGER(120, ABSTRACT_AGEABLE),
      ABSTRACT_ANIMAL(ABSTRACT_AGEABLE),
      CHICKEN(93, ABSTRACT_ANIMAL),
      COW(92, ABSTRACT_ANIMAL),
      MOOSHROOM(96, COW),
      PIG(90, ABSTRACT_ANIMAL),
      POLAR_BEAR(102, ABSTRACT_ANIMAL),
      RABBIT(101, ABSTRACT_ANIMAL),
      SHEEP(91, ABSTRACT_ANIMAL),
      TAMABLE_ANIMAL(ABSTRACT_ANIMAL),
      OCELOT(98, TAMABLE_ANIMAL),
      WOLF(95, TAMABLE_ANIMAL),
      ABSTRACT_SHOULDER_RIDING(TAMABLE_ANIMAL),
      PARROT(105, ABSTRACT_SHOULDER_RIDING),
      ABSTRACT_HORSE(ABSTRACT_ANIMAL),
      HORSE(100, ABSTRACT_HORSE),
      SKELETON_HORSE(28, ABSTRACT_HORSE),
      ZOMBIE_HORSE(29, ABSTRACT_HORSE),
      CHESTED_HORSE(ABSTRACT_HORSE),
      DONKEY(31, CHESTED_HORSE),
      MULE(32, CHESTED_HORSE),
      LLAMA(103, CHESTED_HORSE),
      ABSTRACT_MONSTER(ABSTRACT_CREATURE),
      BLAZE(61, ABSTRACT_MONSTER),
      CREEPER(50, ABSTRACT_MONSTER),
      ENDERMITE(67, ABSTRACT_MONSTER),
      ENDERMAN(58, ABSTRACT_MONSTER),
      GIANT(53, ABSTRACT_MONSTER),
      SILVERFISH(60, ABSTRACT_MONSTER),
      VEX(35, ABSTRACT_MONSTER),
      WITCH(66, ABSTRACT_MONSTER),
      WITHER(64, ABSTRACT_MONSTER),
      ABSTRACT_SKELETON(ABSTRACT_MONSTER),
      SKELETON(51, ABSTRACT_SKELETON),
      STRAY(6, ABSTRACT_SKELETON),
      WITHER_SKELETON(5, ABSTRACT_SKELETON),
      ZOMBIE(54, ABSTRACT_MONSTER),
      HUSK(23, ZOMBIE),
      ZOMBIE_PIGMEN(57, ZOMBIE),
      ZOMBIE_VILLAGER(27, ZOMBIE),
      GUARDIAN(68, ABSTRACT_MONSTER),
      ELDER_GUARDIAN(4, GUARDIAN),
      SPIDER(52, ABSTRACT_MONSTER),
      CAVE_SPIDER(59, ABSTRACT_MONSTER),
      ABSTRACT_ILLAGER(ABSTRACT_MONSTER),
      SPELLCASTER_ILLAGER(ABSTRACT_ILLAGER),
      VINDICATOR(36, ABSTRACT_ILLAGER),
      EVOKER(34, SPELLCASTER_ILLAGER),
      ILLUSIONER(37, SPELLCASTER_ILLAGER);

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
         return new EntityType[]{ENTITY, AREA_EFFECT_CLOUD, END_CRYSTAL, EVOKER_FANGS, EXPERIENCE_ORB, EYE_OF_ENDER, FALLING_BLOCK, ITEM, TNT, LIGHTNING_BOLT, HANGING_ENTITY, LEASH_KNOT, ITEM_FRAME, PAINTING, PROJECTILE, FIREWORK_ROCKET, LLAMA_SPIT, SHULKER_BULLET, SNOWBALL, ENDER_PEARL, EGG, EXPERIENCE_BOTTLE, POTION, FISHING_HOOK, ABSTRACT_ARROW, ARROW, SPECTRAL_ARROW, HURTING_PROJECTILE, DRAGON_FIREBALL, FIREBALL, SMALL_FIREBALL, WITHER_SKULL, BOAT, ABSTRACT_MINECART, MINECART, FURNACE_MINECART, COMMAND_BLOCK_MINECART, TNT_MINECART, SPAWNER_MINECART, ABSTRACT_MINECART_CONTAINER, CHEST_MINECART, HOPPER_MINECART, LIVING_ENTITY_BASE, ARMOR_STAND, PLAYER, LIVING_ENTITY, ENDER_DRAGON, ABSTRACT_CREATURE, SLIME, MAGMA_CUBE, FLYING_MOB, GHAST, AMBIENT_CREATURE, BAT, ABSTRACT_GOLEM, SNOW_GOLEM, IRON_GOLEM, SHULKER, WATER_ANIMAL, SQUID, ABSTRACT_AGEABLE, VILLAGER, ABSTRACT_ANIMAL, CHICKEN, COW, MOOSHROOM, PIG, POLAR_BEAR, RABBIT, SHEEP, TAMABLE_ANIMAL, OCELOT, WOLF, ABSTRACT_SHOULDER_RIDING, PARROT, ABSTRACT_HORSE, HORSE, SKELETON_HORSE, ZOMBIE_HORSE, CHESTED_HORSE, DONKEY, MULE, LLAMA, ABSTRACT_MONSTER, BLAZE, CREEPER, ENDERMITE, ENDERMAN, GIANT, SILVERFISH, VEX, WITCH, WITHER, ABSTRACT_SKELETON, SKELETON, STRAY, WITHER_SKELETON, ZOMBIE, HUSK, ZOMBIE_PIGMEN, ZOMBIE_VILLAGER, GUARDIAN, ELDER_GUARDIAN, SPIDER, CAVE_SPIDER, ABSTRACT_ILLAGER, SPELLCASTER_ILLAGER, VINDICATOR, EVOKER, ILLUSIONER};
      }

      static {
         for(EntityType type : values()) {
            TYPES.put(type.id, type);
         }

      }
   }

   public static enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType {
      BOAT(1, EntityTypes1_12.EntityType.BOAT),
      ITEM(2, EntityTypes1_12.EntityType.ITEM),
      AREA_EFFECT_CLOUD(3, EntityTypes1_12.EntityType.AREA_EFFECT_CLOUD),
      MINECART(10, EntityTypes1_12.EntityType.MINECART),
      TNT_PRIMED(50, EntityTypes1_12.EntityType.TNT),
      ENDER_CRYSTAL(51, EntityTypes1_12.EntityType.END_CRYSTAL),
      TIPPED_ARROW(60, EntityTypes1_12.EntityType.ARROW),
      SNOWBALL(61, EntityTypes1_12.EntityType.SNOWBALL),
      EGG(62, EntityTypes1_12.EntityType.EGG),
      FIREBALL(63, EntityTypes1_12.EntityType.FIREBALL),
      SMALL_FIREBALL(64, EntityTypes1_12.EntityType.SMALL_FIREBALL),
      ENDER_PEARL(65, EntityTypes1_12.EntityType.ENDER_PEARL),
      WITHER_SKULL(66, EntityTypes1_12.EntityType.WITHER_SKULL),
      SHULKER_BULLET(67, EntityTypes1_12.EntityType.SHULKER_BULLET),
      LLAMA_SPIT(68, EntityTypes1_12.EntityType.LLAMA_SPIT),
      FALLING_BLOCK(70, EntityTypes1_12.EntityType.FALLING_BLOCK),
      ITEM_FRAME(71, EntityTypes1_12.EntityType.ITEM_FRAME),
      EYE_OF_ENDER(72, EntityTypes1_12.EntityType.EYE_OF_ENDER),
      POTION(73, EntityTypes1_12.EntityType.POTION),
      EXPERIENCE_BOTTLE(75, EntityTypes1_12.EntityType.EXPERIENCE_BOTTLE),
      FIREWORK_ROCKET(76, EntityTypes1_12.EntityType.FIREWORK_ROCKET),
      LEASH(77, EntityTypes1_12.EntityType.LEASH_KNOT),
      ARMOR_STAND(78, EntityTypes1_12.EntityType.ARMOR_STAND),
      EVOKER_FANGS(79, EntityTypes1_12.EntityType.EVOKER_FANGS),
      FISHIHNG_HOOK(90, EntityTypes1_12.EntityType.FISHING_HOOK),
      SPECTRAL_ARROW(91, EntityTypes1_12.EntityType.SPECTRAL_ARROW),
      DRAGON_FIREBALL(93, EntityTypes1_12.EntityType.DRAGON_FIREBALL);

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
         return new ObjectType[]{BOAT, ITEM, AREA_EFFECT_CLOUD, MINECART, TNT_PRIMED, ENDER_CRYSTAL, TIPPED_ARROW, SNOWBALL, EGG, FIREBALL, SMALL_FIREBALL, ENDER_PEARL, WITHER_SKULL, SHULKER_BULLET, LLAMA_SPIT, FALLING_BLOCK, ITEM_FRAME, EYE_OF_ENDER, POTION, EXPERIENCE_BOTTLE, FIREWORK_ROCKET, LEASH, ARMOR_STAND, EVOKER_FANGS, FISHIHNG_HOOK, SPECTRAL_ARROW, DRAGON_FIREBALL};
      }

      static {
         for(ObjectType type : values()) {
            TYPES.put(type.id, type);
         }

      }
   }
}
