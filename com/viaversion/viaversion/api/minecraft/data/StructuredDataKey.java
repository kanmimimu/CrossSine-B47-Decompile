package com.viaversion.viaversion.api.minecraft.data;

import com.viaversion.viaversion.api.minecraft.item.data.AdventureModePredicate;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrim;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_21;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPatternLayer;
import com.viaversion.viaversion.api.minecraft.item.data.Bee;
import com.viaversion.viaversion.api.minecraft.item.data.BlockStateProperties;
import com.viaversion.viaversion.api.minecraft.item.data.DyedColor;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableString;
import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.minecraft.item.data.Fireworks;
import com.viaversion.viaversion.api.minecraft.item.data.FoodProperties;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.JukeboxPlayable;
import com.viaversion.viaversion.api.minecraft.item.data.LodestoneTracker;
import com.viaversion.viaversion.api.minecraft.item.data.PotDecorations;
import com.viaversion.viaversion.api.minecraft.item.data.PotionContents;
import com.viaversion.viaversion.api.minecraft.item.data.SuspiciousStewEffect;
import com.viaversion.viaversion.api.minecraft.item.data.ToolProperties;
import com.viaversion.viaversion.api.minecraft.item.data.Unbreakable;
import com.viaversion.viaversion.api.minecraft.item.data.WrittenBook;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import java.util.Objects;

public final class StructuredDataKey {
   private final String identifier;
   private final Type type;
   public static final StructuredDataKey CUSTOM_DATA;
   public static final StructuredDataKey MAX_STACK_SIZE;
   public static final StructuredDataKey MAX_DAMAGE;
   public static final StructuredDataKey DAMAGE;
   public static final StructuredDataKey UNBREAKABLE;
   public static final StructuredDataKey CUSTOM_NAME;
   public static final StructuredDataKey ITEM_NAME;
   public static final StructuredDataKey LORE;
   public static final StructuredDataKey RARITY;
   public static final StructuredDataKey ENCHANTMENTS;
   public static final StructuredDataKey CAN_PLACE_ON;
   public static final StructuredDataKey CAN_BREAK;
   public static final StructuredDataKey ATTRIBUTE_MODIFIERS1_20_5;
   public static final StructuredDataKey ATTRIBUTE_MODIFIERS1_21;
   public static final StructuredDataKey CUSTOM_MODEL_DATA;
   public static final StructuredDataKey HIDE_ADDITIONAL_TOOLTIP;
   public static final StructuredDataKey HIDE_TOOLTIP;
   public static final StructuredDataKey REPAIR_COST;
   public static final StructuredDataKey CREATIVE_SLOT_LOCK;
   public static final StructuredDataKey ENCHANTMENT_GLINT_OVERRIDE;
   public static final StructuredDataKey INTANGIBLE_PROJECTILE;
   public static final StructuredDataKey FOOD1_20_5;
   public static final StructuredDataKey FOOD1_21;
   public static final StructuredDataKey FIRE_RESISTANT;
   public static final StructuredDataKey TOOL;
   public static final StructuredDataKey STORED_ENCHANTMENTS;
   public static final StructuredDataKey DYED_COLOR;
   public static final StructuredDataKey MAP_COLOR;
   public static final StructuredDataKey MAP_ID;
   public static final StructuredDataKey MAP_DECORATIONS;
   public static final StructuredDataKey MAP_POST_PROCESSING;
   public static final StructuredDataKey CHARGED_PROJECTILES1_20_5;
   public static final StructuredDataKey CHARGED_PROJECTILES1_21;
   public static final StructuredDataKey BUNDLE_CONTENTS1_20_5;
   public static final StructuredDataKey BUNDLE_CONTENTS1_21;
   public static final StructuredDataKey POTION_CONTENTS;
   public static final StructuredDataKey SUSPICIOUS_STEW_EFFECTS;
   public static final StructuredDataKey WRITABLE_BOOK_CONTENT;
   public static final StructuredDataKey WRITTEN_BOOK_CONTENT;
   public static final StructuredDataKey TRIM;
   public static final StructuredDataKey DEBUG_STICK_STATE;
   public static final StructuredDataKey ENTITY_DATA;
   public static final StructuredDataKey BUCKET_ENTITY_DATA;
   public static final StructuredDataKey BLOCK_ENTITY_DATA;
   public static final StructuredDataKey INSTRUMENT;
   public static final StructuredDataKey OMINOUS_BOTTLE_AMPLIFIER;
   public static final StructuredDataKey JUKEBOX_PLAYABLE;
   public static final StructuredDataKey RECIPES;
   public static final StructuredDataKey LODESTONE_TRACKER;
   public static final StructuredDataKey FIREWORK_EXPLOSION;
   public static final StructuredDataKey FIREWORKS;
   public static final StructuredDataKey PROFILE;
   public static final StructuredDataKey NOTE_BLOCK_SOUND;
   public static final StructuredDataKey BANNER_PATTERNS;
   public static final StructuredDataKey BASE_COLOR;
   public static final StructuredDataKey POT_DECORATIONS;
   public static final StructuredDataKey CONTAINER1_20_5;
   public static final StructuredDataKey CONTAINER1_21;
   public static final StructuredDataKey BLOCK_STATE;
   public static final StructuredDataKey BEES;
   public static final StructuredDataKey LOCK;
   public static final StructuredDataKey CONTAINER_LOOT;

   public StructuredDataKey(String identifier, Type type) {
      this.identifier = identifier;
      this.type = type;
   }

   public String toString() {
      Type var4 = this.type;
      String var3 = this.identifier;
      return "StructuredDataKey{identifier='" + var3 + "', type=" + var4 + "}";
   }

   public String identifier() {
      return this.identifier;
   }

   public Type type() {
      return this.type;
   }

   static {
      CUSTOM_DATA = new StructuredDataKey("custom_data", Types.COMPOUND_TAG);
      MAX_STACK_SIZE = new StructuredDataKey("max_stack_size", Types.VAR_INT);
      MAX_DAMAGE = new StructuredDataKey("max_damage", Types.VAR_INT);
      DAMAGE = new StructuredDataKey("damage", Types.VAR_INT);
      UNBREAKABLE = new StructuredDataKey("unbreakable", Unbreakable.TYPE);
      CUSTOM_NAME = new StructuredDataKey("custom_name", Types.TAG);
      ITEM_NAME = new StructuredDataKey("item_name", Types.TAG);
      LORE = new StructuredDataKey("lore", Types.TAG_ARRAY);
      RARITY = new StructuredDataKey("rarity", Types.VAR_INT);
      ENCHANTMENTS = new StructuredDataKey("enchantments", Enchantments.TYPE);
      CAN_PLACE_ON = new StructuredDataKey("can_place_on", AdventureModePredicate.TYPE);
      CAN_BREAK = new StructuredDataKey("can_break", AdventureModePredicate.TYPE);
      ATTRIBUTE_MODIFIERS1_20_5 = new StructuredDataKey("attribute_modifiers", AttributeModifiers1_20_5.TYPE);
      ATTRIBUTE_MODIFIERS1_21 = new StructuredDataKey("attribute_modifiers", AttributeModifiers1_21.TYPE);
      CUSTOM_MODEL_DATA = new StructuredDataKey("custom_model_data", Types.VAR_INT);
      HIDE_ADDITIONAL_TOOLTIP = new StructuredDataKey("hide_additional_tooltip", Types.EMPTY);
      HIDE_TOOLTIP = new StructuredDataKey("hide_tooltip", Types.EMPTY);
      REPAIR_COST = new StructuredDataKey("repair_cost", Types.VAR_INT);
      CREATIVE_SLOT_LOCK = new StructuredDataKey("creative_slot_lock", Types.EMPTY);
      ENCHANTMENT_GLINT_OVERRIDE = new StructuredDataKey("enchantment_glint_override", Types.BOOLEAN);
      INTANGIBLE_PROJECTILE = new StructuredDataKey("intangible_projectile", Types.TAG);
      FOOD1_20_5 = new StructuredDataKey("food", FoodProperties.TYPE1_20_5);
      FOOD1_21 = new StructuredDataKey("food", FoodProperties.TYPE1_21);
      FIRE_RESISTANT = new StructuredDataKey("fire_resistant", Types.EMPTY);
      TOOL = new StructuredDataKey("tool", ToolProperties.TYPE);
      STORED_ENCHANTMENTS = new StructuredDataKey("stored_enchantments", Enchantments.TYPE);
      DYED_COLOR = new StructuredDataKey("dyed_color", DyedColor.TYPE);
      MAP_COLOR = new StructuredDataKey("map_color", Types.INT);
      MAP_ID = new StructuredDataKey("map_id", Types.VAR_INT);
      MAP_DECORATIONS = new StructuredDataKey("map_decorations", Types.COMPOUND_TAG);
      MAP_POST_PROCESSING = new StructuredDataKey("map_post_processing", Types.VAR_INT);
      CHARGED_PROJECTILES1_20_5 = new StructuredDataKey("charged_projectiles", Types1_20_5.ITEM_ARRAY);
      CHARGED_PROJECTILES1_21 = new StructuredDataKey("charged_projectiles", Types1_21.ITEM_ARRAY);
      BUNDLE_CONTENTS1_20_5 = new StructuredDataKey("bundle_contents", Types1_20_5.ITEM_ARRAY);
      BUNDLE_CONTENTS1_21 = new StructuredDataKey("bundle_contents", Types1_21.ITEM_ARRAY);
      POTION_CONTENTS = new StructuredDataKey("potion_contents", PotionContents.TYPE);
      SUSPICIOUS_STEW_EFFECTS = new StructuredDataKey("suspicious_stew_effects", SuspiciousStewEffect.ARRAY_TYPE);
      WRITABLE_BOOK_CONTENT = new StructuredDataKey("writable_book_content", FilterableString.ARRAY_TYPE);
      WRITTEN_BOOK_CONTENT = new StructuredDataKey("written_book_content", WrittenBook.TYPE);
      TRIM = new StructuredDataKey("trim", ArmorTrim.TYPE);
      DEBUG_STICK_STATE = new StructuredDataKey("debug_stick_state", Types.COMPOUND_TAG);
      ENTITY_DATA = new StructuredDataKey("entity_data", Types.COMPOUND_TAG);
      BUCKET_ENTITY_DATA = new StructuredDataKey("bucket_entity_data", Types.COMPOUND_TAG);
      BLOCK_ENTITY_DATA = new StructuredDataKey("block_entity_data", Types.COMPOUND_TAG);
      INSTRUMENT = new StructuredDataKey("instrument", Instrument.TYPE);
      OMINOUS_BOTTLE_AMPLIFIER = new StructuredDataKey("ominous_bottle_amplifier", Types.VAR_INT);
      JUKEBOX_PLAYABLE = new StructuredDataKey("jukebox_playable", JukeboxPlayable.TYPE);
      RECIPES = new StructuredDataKey("recipes", Types.TAG);
      LODESTONE_TRACKER = new StructuredDataKey("lodestone_tracker", LodestoneTracker.TYPE);
      FIREWORK_EXPLOSION = new StructuredDataKey("firework_explosion", FireworkExplosion.TYPE);
      FIREWORKS = new StructuredDataKey("fireworks", Fireworks.TYPE);
      PROFILE = new StructuredDataKey("profile", Types.GAME_PROFILE);
      NOTE_BLOCK_SOUND = new StructuredDataKey("note_block_sound", Types.STRING);
      BANNER_PATTERNS = new StructuredDataKey("banner_patterns", BannerPatternLayer.ARRAY_TYPE);
      BASE_COLOR = new StructuredDataKey("base_color", Types.VAR_INT);
      POT_DECORATIONS = new StructuredDataKey("pot_decorations", PotDecorations.TYPE);
      CONTAINER1_20_5 = new StructuredDataKey("container", Types1_20_5.ITEM_ARRAY);
      CONTAINER1_21 = new StructuredDataKey("container", Types1_21.ITEM_ARRAY);
      BLOCK_STATE = new StructuredDataKey("block_state", BlockStateProperties.TYPE);
      BEES = new StructuredDataKey("bees", Bee.ARRAY_TYPE);
      LOCK = new StructuredDataKey("lock", Types.TAG);
      CONTAINER_LOOT = new StructuredDataKey("container_loot", Types.COMPOUND_TAG);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof StructuredDataKey)) {
         return false;
      } else {
         StructuredDataKey var2 = (StructuredDataKey)var1;
         return Objects.equals(this.identifier, var2.identifier) && Objects.equals(this.type, var2.type);
      }
   }

   public int hashCode() {
      return (0 * 31 + Objects.hashCode(this.identifier)) * 31 + Objects.hashCode(this.type);
   }
}
