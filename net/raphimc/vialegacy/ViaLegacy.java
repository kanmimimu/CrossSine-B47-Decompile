package net.raphimc.vialegacy;

import net.raphimc.vialegacy.platform.ViaLegacyPlatform;

public class ViaLegacy {
   public static final String VERSION = "3.0.3-SNAPSHOT";
   public static final String IMPL_VERSION = "git-ViaLegacy-3.0.3-SNAPSHOT:01c9881";
   private static ViaLegacyPlatform platform;
   private static net.raphimc.vialegacy.platform.ViaLegacyConfig config;

   private ViaLegacy() {
   }

   public static void init(ViaLegacyPlatform platform, net.raphimc.vialegacy.platform.ViaLegacyConfig config) {
      if (ViaLegacy.platform != null) {
         throw new IllegalStateException("ViaLegacy is already initialized");
      } else {
         ViaLegacy.platform = platform;
         ViaLegacy.config = config;
      }
   }

   public static ViaLegacyPlatform getPlatform() {
      return platform;
   }

   public static net.raphimc.vialegacy.platform.ViaLegacyConfig getConfig() {
      return config;
   }
}
