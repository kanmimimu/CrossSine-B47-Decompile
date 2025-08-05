package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.util.KeyMappings;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BannerPatternStorage implements StorableObject {
   private KeyMappings bannerPatterns;

   public BannerPatternStorage() {
      this.bannerPatterns = BannerPatterns1_20_5.PATTERNS;
   }

   public KeyMappings bannerPatterns() {
      return this.bannerPatterns;
   }

   public void setBannerPatterns(KeyMappings bannerPatterns) {
      this.bannerPatterns = bannerPatterns;
   }

   public @Nullable String pattern(int id) {
      return this.bannerPatterns.idToKey(id);
   }

   public boolean clearOnServerSwitch() {
      return false;
   }
}
