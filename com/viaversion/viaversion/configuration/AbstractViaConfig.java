package com.viaversion.viaversion.configuration;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocol.BlockedProtocolVersionsImpl;
import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractViaConfig extends Config implements ViaVersionConfig {
   public static final List BUKKIT_ONLY_OPTIONS = Arrays.asList("register-userconnections-on-join", "quick-move-action-fix", "change-1_9-hitbox", "change-1_14-hitbox", "blockconnection-method", "armor-toggle-fix", "use-new-deathmessages", "item-cache", "nms-player-ticking");
   public static final List VELOCITY_ONLY_OPTIONS = Arrays.asList("velocity-ping-interval", "velocity-ping-save", "velocity-servers");
   private boolean checkForUpdates;
   private boolean preventCollision;
   private boolean useNewEffectIndicator;
   private boolean suppressMetadataErrors;
   private boolean shieldBlocking;
   private boolean noDelayShieldBlocking;
   private boolean showShieldWhenSwordInHand;
   private boolean hologramPatch;
   private boolean pistonAnimationPatch;
   private boolean bossbarPatch;
   private boolean bossbarAntiFlicker;
   private double hologramOffset;
   private int maxPPS;
   private String maxPPSKickMessage;
   private int trackingPeriod;
   private int warningPPS;
   private int maxPPSWarnings;
   private String maxPPSWarningsKickMessage;
   private boolean sendSupportedVersions;
   private boolean simulatePlayerTick;
   private boolean replacePistons;
   private int pistonReplacementId;
   private boolean chunkBorderFix;
   private boolean autoTeam;
   private boolean nbtArrayFix;
   private BlockedProtocolVersions blockedProtocolVersions;
   private String blockedDisconnectMessage;
   private String reloadDisconnectMessage;
   private boolean suppressConversionWarnings;
   private boolean disable1_13TabComplete;
   private boolean teamColourFix;
   private boolean serversideBlockConnections;
   private boolean reduceBlockStorageMemory;
   private boolean flowerStemWhenBlockAbove;
   private boolean vineClimbFix;
   private boolean snowCollisionFix;
   private boolean infestedBlocksFix;
   private int tabCompleteDelay;
   private boolean truncate1_14Books;
   private boolean leftHandedHandling;
   private boolean fullBlockLightFix;
   private boolean healthNaNFix;
   private boolean instantRespawn;
   private boolean ignoreLongChannelNames;
   private boolean forcedUse1_17ResourcePack;
   private JsonElement resourcePack1_17PromptMessage;
   private WorldIdentifiers map1_16WorldNames;
   private boolean cache1_17Light;
   private boolean translateOcelotToCat;
   private boolean enforceSecureChat;
   private boolean handleInvalidItemCount;
   private boolean cancelBlockSounds;
   private boolean hideScoreboardNumbers;
   private boolean fix1_21PlacementRotation;

   protected AbstractViaConfig(File configFile, Logger logger) {
      super(configFile, logger);
   }

   public void reload() {
      super.reload();
      this.loadFields();
   }

   protected void loadFields() {
      this.checkForUpdates = this.getBoolean("check-for-updates", true);
      this.preventCollision = this.getBoolean("prevent-collision", true);
      this.useNewEffectIndicator = this.getBoolean("use-new-effect-indicator", true);
      this.suppressMetadataErrors = this.getBoolean("suppress-metadata-errors", false);
      this.shieldBlocking = this.getBoolean("shield-blocking", true);
      this.noDelayShieldBlocking = this.getBoolean("no-delay-shield-blocking", false);
      this.showShieldWhenSwordInHand = this.getBoolean("show-shield-when-sword-in-hand", false);
      this.hologramPatch = this.getBoolean("hologram-patch", false);
      this.pistonAnimationPatch = this.getBoolean("piston-animation-patch", false);
      this.bossbarPatch = this.getBoolean("bossbar-patch", true);
      this.bossbarAntiFlicker = this.getBoolean("bossbar-anti-flicker", false);
      this.hologramOffset = this.getDouble("hologram-y", -0.96);
      this.maxPPS = this.getInt("max-pps", 800);
      this.maxPPSKickMessage = this.getString("max-pps-kick-msg", "Sending packets too fast? lag?");
      this.trackingPeriod = this.getInt("tracking-period", 6);
      this.warningPPS = this.getInt("tracking-warning-pps", 120);
      this.maxPPSWarnings = this.getInt("tracking-max-warnings", 3);
      this.maxPPSWarningsKickMessage = this.getString("tracking-max-kick-msg", "You are sending too many packets, :(");
      this.sendSupportedVersions = this.getBoolean("send-supported-versions", false);
      this.simulatePlayerTick = this.getBoolean("simulate-pt", true);
      this.replacePistons = this.getBoolean("replace-pistons", false);
      this.pistonReplacementId = this.getInt("replacement-piston-id", 0);
      this.chunkBorderFix = this.getBoolean("chunk-border-fix", false);
      this.autoTeam = this.getBoolean("auto-team", true);
      this.nbtArrayFix = this.getBoolean("chat-nbt-fix", true);
      this.blockedProtocolVersions = this.loadBlockedProtocolVersions();
      this.blockedDisconnectMessage = this.getString("block-disconnect-msg", "You are using an unsupported Minecraft version!");
      this.reloadDisconnectMessage = this.getString("reload-disconnect-msg", "Server reload, please rejoin!");
      this.teamColourFix = this.getBoolean("team-colour-fix", true);
      this.suppressConversionWarnings = this.getBoolean("suppress-conversion-warnings", false);
      this.disable1_13TabComplete = this.getBoolean("disable-1_13-auto-complete", false);
      this.serversideBlockConnections = this.getBoolean("serverside-blockconnections", true);
      this.reduceBlockStorageMemory = this.getBoolean("reduce-blockstorage-memory", false);
      this.flowerStemWhenBlockAbove = this.getBoolean("flowerstem-when-block-above", false);
      this.vineClimbFix = this.getBoolean("vine-climb-fix", false);
      this.snowCollisionFix = this.getBoolean("fix-low-snow-collision", false);
      this.infestedBlocksFix = this.getBoolean("fix-infested-block-breaking", true);
      this.tabCompleteDelay = this.getInt("1_13-tab-complete-delay", 0);
      this.truncate1_14Books = this.getBoolean("truncate-1_14-books", false);
      this.leftHandedHandling = this.getBoolean("left-handed-handling", true);
      this.fullBlockLightFix = this.getBoolean("fix-non-full-blocklight", false);
      this.healthNaNFix = this.getBoolean("fix-1_14-health-nan", true);
      this.instantRespawn = this.getBoolean("use-1_15-instant-respawn", false);
      this.ignoreLongChannelNames = this.getBoolean("ignore-long-1_16-channel-names", true);
      this.forcedUse1_17ResourcePack = this.getBoolean("forced-use-1_17-resource-pack", false);
      this.resourcePack1_17PromptMessage = this.getSerializedComponent("resource-pack-1_17-prompt");
      Map<String, String> worlds = (Map)this.get("map-1_16-world-names", new HashMap());
      this.map1_16WorldNames = new WorldIdentifiers((String)worlds.getOrDefault("overworld", "minecraft:overworld"), (String)worlds.getOrDefault("nether", "minecraft:the_nether"), (String)worlds.getOrDefault("end", "minecraft:the_end"));
      this.cache1_17Light = this.getBoolean("cache-1_17-light", true);
      this.translateOcelotToCat = this.getBoolean("translate-ocelot-to-cat", true);
      this.enforceSecureChat = this.getBoolean("enforce-secure-chat", false);
      this.handleInvalidItemCount = this.getBoolean("handle-invalid-item-count", false);
      this.cancelBlockSounds = this.getBoolean("cancel-block-sounds", true);
      this.hideScoreboardNumbers = this.getBoolean("hide-scoreboard-numbers", false);
      this.fix1_21PlacementRotation = this.getBoolean("fix-1_21-placement-rotation", false);
   }

   private BlockedProtocolVersions loadBlockedProtocolVersions() {
      List<Integer> blockProtocols = this.getListSafe("block-protocols", Integer.class, "Invalid blocked version protocol found in config: '%s'");
      List<String> blockVersions = this.getListSafe("block-versions", String.class, "Invalid blocked version found in config: '%s'");
      ObjectSet<ProtocolVersion> blockedProtocols = (ObjectSet)blockProtocols.stream().map(ProtocolVersion::getProtocol).collect(ObjectOpenHashSet::of, Set::add, Set::addAll);
      ProtocolVersion lowerBound = ProtocolVersion.unknown;
      ProtocolVersion upperBound = ProtocolVersion.unknown;

      for(String s : blockVersions) {
         if (!s.isEmpty()) {
            char c = s.charAt(0);
            if (c != '<' && c != '>') {
               ProtocolVersion protocolVersion = this.protocolVersion(s);
               if (protocolVersion != null && !blockedProtocols.add(protocolVersion)) {
                  this.logger.warning("Duplicated blocked protocol version " + protocolVersion);
               }
            } else {
               ProtocolVersion protocolVersion = this.protocolVersion(s.substring(1));
               if (protocolVersion != null) {
                  if (c == '<') {
                     if (lowerBound.isKnown()) {
                        Logger var10000 = this.logger;
                        String var12 = protocolVersion.getName();
                        var10000.warning("Already set lower bound " + lowerBound + " overridden by " + var12);
                     }

                     lowerBound = protocolVersion;
                  } else {
                     if (upperBound.isKnown()) {
                        Logger var19 = this.logger;
                        String var15 = protocolVersion.getName();
                        var19.warning("Already set upper bound " + upperBound + " overridden by " + var15);
                     }

                     upperBound = protocolVersion;
                  }
               }
            }
         }
      }

      if (lowerBound.isKnown() || upperBound.isKnown()) {
         blockedProtocols.removeIf((version) -> {
            if ((!lowerBound.isKnown() || !version.olderThan(lowerBound)) && (!upperBound.isKnown() || !version.newerThan(upperBound))) {
               return false;
            } else {
               this.logger.warning("Blocked protocol version " + version + " already covered by upper or lower bound");
               return true;
            }
         });
      }

      return new BlockedProtocolVersionsImpl(blockedProtocols, lowerBound, upperBound);
   }

   private @Nullable ProtocolVersion protocolVersion(String s) {
      ProtocolVersion protocolVersion = ProtocolVersion.getClosest(s);
      if (protocolVersion == null) {
         this.logger.warning("Unknown protocol version in block-versions: " + s);
         return null;
      } else {
         return protocolVersion;
      }
   }

   public boolean isCheckForUpdates() {
      return this.checkForUpdates;
   }

   public void setCheckForUpdates(boolean checkForUpdates) {
      this.checkForUpdates = checkForUpdates;
      this.set("checkforupdates", checkForUpdates);
   }

   public boolean isPreventCollision() {
      return this.preventCollision;
   }

   public boolean isNewEffectIndicator() {
      return this.useNewEffectIndicator;
   }

   public boolean isShowNewDeathMessages() {
      return false;
   }

   public boolean isSuppressMetadataErrors() {
      return this.suppressMetadataErrors;
   }

   public boolean isShieldBlocking() {
      return this.shieldBlocking;
   }

   public boolean isNoDelayShieldBlocking() {
      return this.noDelayShieldBlocking;
   }

   public boolean isShowShieldWhenSwordInHand() {
      return this.showShieldWhenSwordInHand;
   }

   public boolean isHologramPatch() {
      return this.hologramPatch;
   }

   public boolean isPistonAnimationPatch() {
      return this.pistonAnimationPatch;
   }

   public boolean isBossbarPatch() {
      return this.bossbarPatch;
   }

   public boolean isBossbarAntiflicker() {
      return this.bossbarAntiFlicker;
   }

   public double getHologramYOffset() {
      return this.hologramOffset;
   }

   public int getMaxPPS() {
      return this.maxPPS;
   }

   public String getMaxPPSKickMessage() {
      return this.maxPPSKickMessage;
   }

   public int getTrackingPeriod() {
      return this.trackingPeriod;
   }

   public int getWarningPPS() {
      return this.warningPPS;
   }

   public int getMaxWarnings() {
      return this.maxPPSWarnings;
   }

   public String getMaxWarningsKickMessage() {
      return this.maxPPSWarningsKickMessage;
   }

   public boolean isSendSupportedVersions() {
      return this.sendSupportedVersions;
   }

   public boolean isSimulatePlayerTick() {
      return this.simulatePlayerTick;
   }

   public boolean isItemCache() {
      return false;
   }

   public boolean isNMSPlayerTicking() {
      return false;
   }

   public boolean isReplacePistons() {
      return this.replacePistons;
   }

   public int getPistonReplacementId() {
      return this.pistonReplacementId;
   }

   public boolean isChunkBorderFix() {
      return this.chunkBorderFix;
   }

   public boolean isAutoTeam() {
      return this.preventCollision && this.autoTeam;
   }

   public boolean is1_12NBTArrayFix() {
      return this.nbtArrayFix;
   }

   public boolean shouldRegisterUserConnectionOnJoin() {
      return false;
   }

   public boolean is1_12QuickMoveActionFix() {
      return false;
   }

   public BlockedProtocolVersions blockedProtocolVersions() {
      return this.blockedProtocolVersions;
   }

   public String getBlockedDisconnectMsg() {
      return this.blockedDisconnectMessage;
   }

   public String getReloadDisconnectMsg() {
      return this.reloadDisconnectMessage;
   }

   public boolean is1_13TeamColourFix() {
      return this.teamColourFix;
   }

   public boolean isSuppressConversionWarnings() {
      return this.suppressConversionWarnings && !Via.getManager().isDebug();
   }

   public boolean isDisable1_13AutoComplete() {
      return this.disable1_13TabComplete;
   }

   public boolean isServersideBlockConnections() {
      return this.serversideBlockConnections;
   }

   public String getBlockConnectionMethod() {
      return "packet";
   }

   public boolean isReduceBlockStorageMemory() {
      return this.reduceBlockStorageMemory;
   }

   public boolean isStemWhenBlockAbove() {
      return this.flowerStemWhenBlockAbove;
   }

   public boolean isVineClimbFix() {
      return this.vineClimbFix;
   }

   public boolean isSnowCollisionFix() {
      return this.snowCollisionFix;
   }

   public boolean isInfestedBlocksFix() {
      return this.infestedBlocksFix;
   }

   public int get1_13TabCompleteDelay() {
      return this.tabCompleteDelay;
   }

   public boolean isTruncate1_14Books() {
      return this.truncate1_14Books;
   }

   public boolean isLeftHandedHandling() {
      return this.leftHandedHandling;
   }

   public boolean is1_9HitboxFix() {
      return false;
   }

   public boolean is1_14HitboxFix() {
      return false;
   }

   public boolean isNonFullBlockLightFix() {
      return this.fullBlockLightFix;
   }

   public boolean is1_14HealthNaNFix() {
      return this.healthNaNFix;
   }

   public boolean is1_15InstantRespawn() {
      return this.instantRespawn;
   }

   public boolean isIgnoreLong1_16ChannelNames() {
      return this.ignoreLongChannelNames;
   }

   public boolean isForcedUse1_17ResourcePack() {
      return this.forcedUse1_17ResourcePack;
   }

   public JsonElement get1_17ResourcePackPrompt() {
      return this.resourcePack1_17PromptMessage;
   }

   public WorldIdentifiers get1_16WorldNamesMap() {
      return this.map1_16WorldNames;
   }

   public boolean cache1_17Light() {
      return this.cache1_17Light;
   }

   public boolean isArmorToggleFix() {
      return false;
   }

   public boolean translateOcelotToCat() {
      return this.translateOcelotToCat;
   }

   public boolean enforceSecureChat() {
      return this.enforceSecureChat;
   }

   public boolean handleInvalidItemCount() {
      return this.handleInvalidItemCount;
   }

   public boolean cancelBlockSounds() {
      return this.cancelBlockSounds;
   }

   public boolean hideScoreboardNumbers() {
      return this.hideScoreboardNumbers;
   }

   public boolean fix1_21PlacementRotation() {
      return this.fix1_21PlacementRotation;
   }
}
