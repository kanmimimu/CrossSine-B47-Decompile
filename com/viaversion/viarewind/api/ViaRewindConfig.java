package com.viaversion.viarewind.api;

import com.viaversion.viaversion.api.configuration.Config;

public interface ViaRewindConfig extends Config {
   CooldownIndicator getCooldownIndicator();

   boolean isReplaceAdventureMode();

   boolean isReplaceParticles();

   int getMaxBookPages();

   int getMaxBookPageSize();

   boolean isEmulateWorldBorder();

   boolean alwaysShowOriginalMobName();

   String getWorldBorderParticle();

   boolean isEnableOffhand();

   String getOffhandCommand();

   boolean emulateLevitationEffect();

   boolean handlePlayerCombatPacket();

   public static enum CooldownIndicator {
      TITLE,
      ACTION_BAR,
      BOSS_BAR,
      DISABLED;

      // $FF: synthetic method
      static CooldownIndicator[] $values() {
         return new CooldownIndicator[]{TITLE, ACTION_BAR, BOSS_BAR, DISABLED};
      }
   }
}
