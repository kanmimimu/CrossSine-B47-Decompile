package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data;

import java.util.HashMap;

public enum Particle1_7_6 {
   EXPLOSION_NORMAL("explode"),
   EXPLOSION_LARGE("largeexplode"),
   EXPLOSION_HUGE("hugeexplosion"),
   FIREWORKS_SPARK("fireworksSpark"),
   WATER_BUBBLE("bubble"),
   WATER_SPLASH("splash"),
   WATER_WAKE("wake"),
   SUSPENDED("suspended"),
   SUSPENDED_DEPTH("depthsuspend"),
   CRIT("crit"),
   CRIT_MAGIC("magicCrit"),
   SMOKE_NORMAL("smoke"),
   SMOKE_LARGE("largesmoke"),
   SPELL("spell"),
   SPELL_INSTANT("instantSpell"),
   SPELL_MOB("mobSpell"),
   SPELL_MOB_AMBIENT("mobSpellAmbient"),
   SPELL_WITCH("witchMagic"),
   DRIP_WATER("dripWater"),
   DRIP_LAVA("dripLava"),
   VILLAGER_ANGRY("angryVillager"),
   VILLAGER_HAPPY("happyVillager"),
   TOWN_AURA("townaura"),
   NOTE("note"),
   PORTAL("portal"),
   ENCHANTMENT_TABLE("enchantmenttable"),
   FLAME("flame"),
   LAVA("lava"),
   FOOTSTEP("footstep"),
   CLOUD("cloud"),
   REDSTONE("reddust"),
   SNOWBALL("snowballpoof"),
   SNOW_SHOVEL("snowshovel"),
   SLIME("slime"),
   HEART("heart"),
   BARRIER("barrier"),
   ICON_CRACK("iconcrack", 2),
   BLOCK_CRACK("blockcrack", 1),
   BLOCK_DUST("blockdust", 1),
   WATER_DROP("droplet"),
   ITEM_TAKE("take"),
   MOB_APPEARANCE("mobappearance");

   public final String name;
   public final int extra;
   private static final HashMap particleMap = new HashMap();

   private Particle1_7_6(String name) {
      this(name, 0);
   }

   private Particle1_7_6(String name, int extra) {
      this.name = name;
      this.extra = extra;
   }

   public static Particle1_7_6 find(String part) {
      return (Particle1_7_6)particleMap.get(part);
   }

   // $FF: synthetic method
   private static Particle1_7_6[] $values() {
      return new Particle1_7_6[]{EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER, ICON_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, ITEM_TAKE, MOB_APPEARANCE};
   }

   static {
      for(Particle1_7_6 particle : values()) {
         particleMap.put(particle.name, particle);
      }

   }
}
