package com.viaversion.viarewind;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaRewindConfig extends Config implements com.viaversion.viarewind.api.ViaRewindConfig {
   private com.viaversion.viarewind.api.ViaRewindConfig.CooldownIndicator cooldownIndicator;
   private boolean replaceAdventureMode;
   private boolean replaceParticles;
   private int maxBookPages;
   private int maxBookPageSize;
   private boolean emulateWorldBorder;
   private boolean alwaysShowOriginalMobName;
   private String worldBorderParticle;
   private boolean enableOffhand;
   private String offhandCommand;
   private boolean emulateLevitationEffect;
   private boolean handlePlayerCombatPacket;

   public ViaRewindConfig(File configFile, Logger logger) {
      super(configFile, logger);
   }

   public void reload() {
      super.reload();
      this.loadFields();
   }

   private void loadFields() {
      this.cooldownIndicator = com.viaversion.viarewind.api.ViaRewindConfig.CooldownIndicator.valueOf(this.getString("cooldown-indicator", "TITLE").toUpperCase());
      this.replaceAdventureMode = this.getBoolean("replace-adventure", false);
      this.replaceParticles = this.getBoolean("replace-particles", false);
      this.maxBookPages = this.getInt("max-book-pages", 100);
      this.maxBookPageSize = this.getInt("max-book-page-length", 5000);
      this.emulateWorldBorder = this.getBoolean("emulate-world-border", true);
      this.alwaysShowOriginalMobName = this.getBoolean("always-show-original-mob-name", true);
      this.worldBorderParticle = this.getString("world-border-particle", "fireworksSpark");
      this.enableOffhand = this.getBoolean("enable-offhand", true);
      this.offhandCommand = this.getString("offhand-command", "/offhand");
      this.emulateLevitationEffect = this.getBoolean("emulate-levitation-effect", true);
      this.handlePlayerCombatPacket = this.getBoolean("handle-player-combat-packet", true);
   }

   public com.viaversion.viarewind.api.ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
      return this.cooldownIndicator;
   }

   public boolean isReplaceAdventureMode() {
      return this.replaceAdventureMode;
   }

   public boolean isReplaceParticles() {
      return this.replaceParticles;
   }

   public int getMaxBookPages() {
      return this.maxBookPages;
   }

   public int getMaxBookPageSize() {
      return this.maxBookPageSize;
   }

   public boolean isEmulateWorldBorder() {
      return this.emulateWorldBorder;
   }

   public boolean alwaysShowOriginalMobName() {
      return this.alwaysShowOriginalMobName;
   }

   public String getWorldBorderParticle() {
      return this.worldBorderParticle;
   }

   public boolean isEnableOffhand() {
      return this.enableOffhand;
   }

   public String getOffhandCommand() {
      return this.offhandCommand;
   }

   public boolean emulateLevitationEffect() {
      return this.emulateLevitationEffect;
   }

   public boolean handlePlayerCombatPacket() {
      return this.handlePlayerCombatPacket;
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viarewind/config.yml");
   }

   public InputStream getDefaultConfigInputStream() {
      return this.getClass().getClassLoader().getResourceAsStream("assets/viarewind/config.yml");
   }

   protected void handleConfig(Map map) {
   }

   public List getUnsupportedOptions() {
      return Collections.emptyList();
   }
}
