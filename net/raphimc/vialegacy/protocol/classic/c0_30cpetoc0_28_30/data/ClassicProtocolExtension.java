package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data;

import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Arrays;

public enum ClassicProtocolExtension {
   CLICK_DISTANCE("ClickDistance", new int[0]),
   CUSTOM_BLOCKS("CustomBlocks", new int[]{1}),
   HELD_BLOCK("HeldBlock", new int[0]),
   TEXT_HOT_KEY("TextHotKey", new int[0]),
   EXT_PLAYER_LIST("ExtPlayerList", new int[0]),
   ENV_COLORS("EnvColors", new int[0]),
   SELECTION_CUBOID("SelectionCuboid", new int[0]),
   BLOCK_PERMISSIONS("BlockPermissions", new int[]{1}),
   CHANGE_MODEL("ChangeModel", new int[0]),
   ENV_MAP_APPEARANCE("EnvMapAppearance", new int[0]),
   ENV_WEATHER_TYPE("EnvWeatherType", new int[0]),
   HACK_CONTROL("HackControl", new int[]{1}),
   EMOTE_FIX("EmoteFix", new int[]{1}),
   MESSAGE_TYPES("MessageTypes", new int[0]),
   LONGER_MESSAGES("LongerMessages", new int[]{1}),
   FULL_CP437("FullCP437", new int[]{1}),
   BLOCK_DEFINITIONS("BlockDefinitions", new int[0]),
   BLOCK_DEFINITIONS_EXT("BlockDefinitionsExt", new int[0]),
   TEXT_COLORS("TextColors", new int[0]),
   BULK_BLOCK_UPDATE("BulkBlockUpdate", new int[]{1}),
   ENV_MAP_ASPECT("EnvMapAspect", new int[0]),
   PLAYER_CLICK("PlayerClick", new int[0]),
   ENTITY_PROPERTY("EntityProperty", new int[0]),
   EXT_ENTITY_POSITIONS("ExtEntityPositions", new int[0]),
   TWO_WAY_PING("TwoWayPing", new int[]{1}),
   INVENTORY_ORDER("InventoryOrder", new int[0]),
   INSTANT_MOTD("InstantMOTD", new int[]{1}),
   EXTENDED_BLOCKS("ExtendedBlocks", new int[0]),
   FAST_MAP("FastMap", new int[0]),
   EXTENDED_TEXTURES("ExtendedTextures", new int[0]),
   SET_HOTBAR("SetHotbar", new int[0]),
   SET_SPAWNPOINT("SetSpawnpoint", new int[0]),
   VELOCITY_CONTROL("VelocityControl", new int[0]),
   CUSTOM_PARTICLES("CustomParticles", new int[0]),
   CUSTOM_MODELS("CustomModels", new int[0]),
   EXT_ENTITY_TELEPORT("ExtEntityTeleport", new int[0]);

   private final String name;
   private final IntSet supportedVersions;

   private ClassicProtocolExtension(String name, int... supportedVersions) {
      this.name = name;
      this.supportedVersions = new IntOpenHashSet();

      for(int supportedVersion : supportedVersions) {
         this.supportedVersions.add(supportedVersion);
      }

   }

   public static ClassicProtocolExtension byName(String name) {
      return (ClassicProtocolExtension)Arrays.stream(values()).filter((e) -> e.name.equals(name)).findFirst().orElse((Object)null);
   }

   public static ClassicProtocolExtension byNameAndVersion(String name, int version) {
      ClassicProtocolExtension extension = byName(name);
      return extension != null && extension.supportsVersion(version) ? extension : null;
   }

   public boolean supportsVersion(int version) {
      return this.supportedVersions.contains(version);
   }

   public IntSet getSupportedVersions() {
      return this.supportedVersions;
   }

   public int getHighestSupportedVersion() {
      int highest = 0;
      IntIterator var2 = this.supportedVersions.iterator();

      while(var2.hasNext()) {
         int supportedVersion = (Integer)var2.next();
         if (supportedVersion > highest) {
            highest = supportedVersion;
         }
      }

      return highest;
   }

   public boolean isSupported() {
      return !this.supportedVersions.isEmpty();
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   // $FF: synthetic method
   private static ClassicProtocolExtension[] $values() {
      return new ClassicProtocolExtension[]{CLICK_DISTANCE, CUSTOM_BLOCKS, HELD_BLOCK, TEXT_HOT_KEY, EXT_PLAYER_LIST, ENV_COLORS, SELECTION_CUBOID, BLOCK_PERMISSIONS, CHANGE_MODEL, ENV_MAP_APPEARANCE, ENV_WEATHER_TYPE, HACK_CONTROL, EMOTE_FIX, MESSAGE_TYPES, LONGER_MESSAGES, FULL_CP437, BLOCK_DEFINITIONS, BLOCK_DEFINITIONS_EXT, TEXT_COLORS, BULK_BLOCK_UPDATE, ENV_MAP_ASPECT, PLAYER_CLICK, ENTITY_PROPERTY, EXT_ENTITY_POSITIONS, TWO_WAY_PING, INVENTORY_ORDER, INSTANT_MOTD, EXTENDED_BLOCKS, FAST_MAP, EXTENDED_TEXTURES, SET_HOTBAR, SET_SPAWNPOINT, VELOCITY_CONTROL, CUSTOM_PARTICLES, CUSTOM_MODELS, EXT_ENTITY_TELEPORT};
   }
}
