package net.raphimc.vialegacy;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaLegacyConfig extends Config implements net.raphimc.vialegacy.platform.ViaLegacyConfig {
   private boolean dynamicOnground;
   private boolean ignoreLongChannelNames;
   private boolean legacySkullLoading;
   private boolean legacySkinLoading;
   private boolean soundEmulation;
   private boolean oldBiomes;
   private boolean enableB1_7_3Sprinting;
   private int classicChunkRange;
   private boolean enableClassicFly;

   public ViaLegacyConfig(File configFile, Logger logger) {
      super(configFile, logger);
   }

   public void reload() {
      super.reload();
      this.loadFields();
   }

   private void loadFields() {
      this.dynamicOnground = this.getBoolean("dynamic-onground", true);
      this.ignoreLongChannelNames = this.getBoolean("ignore-long-1_8-channel-names", true);
      this.legacySkullLoading = this.getBoolean("legacy-skull-loading", false);
      this.legacySkinLoading = this.getBoolean("legacy-skin-loading", false);
      this.soundEmulation = this.getBoolean("sound-emulation", true);
      this.oldBiomes = this.getBoolean("old-biomes", true);
      this.enableB1_7_3Sprinting = this.getBoolean("enable-b1_7_3-sprinting", false);
      this.classicChunkRange = this.getInt("classic-chunk-range", 10);
      this.enableClassicFly = this.getBoolean("enable-classic-fly", false);
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/vialegacy/vialegacy.yml");
   }

   protected void handleConfig(Map map) {
   }

   public List getUnsupportedOptions() {
      return Collections.emptyList();
   }

   public boolean isDynamicOnground() {
      return this.dynamicOnground;
   }

   public boolean isIgnoreLong1_8ChannelNames() {
      return this.ignoreLongChannelNames;
   }

   public boolean isLegacySkullLoading() {
      return this.legacySkullLoading;
   }

   public boolean isLegacySkinLoading() {
      return this.legacySkinLoading;
   }

   public boolean isSoundEmulation() {
      return this.soundEmulation;
   }

   public boolean isOldBiomes() {
      return this.oldBiomes;
   }

   public boolean enableB1_7_3Sprinting() {
      return this.enableB1_7_3Sprinting;
   }

   public int getClassicChunkRange() {
      return this.classicChunkRange;
   }

   public boolean enableClassicFly() {
      return this.enableClassicFly;
   }
}
