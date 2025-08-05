package net.raphimc.viaaprilfools;

import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;

public class ViaAprilFools {
   public static final String VERSION = "3.0.2-SNAPSHOT";
   public static final String IMPL_VERSION = "git-ViaAprilFools-3.0.2-SNAPSHOT:c0f975c";
   private static ViaAprilFoolsPlatform platform;
   private static net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig config;

   private ViaAprilFools() {
   }

   public static void init(ViaAprilFoolsPlatform platform, net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig config) {
      if (ViaAprilFools.platform != null) {
         throw new IllegalStateException("ViaAprilFools is already initialized");
      } else {
         ViaAprilFools.platform = platform;
         ViaAprilFools.config = config;
      }
   }

   public static ViaAprilFoolsPlatform getPlatform() {
      return platform;
   }

   public static net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig getConfig() {
      return config;
   }
}
