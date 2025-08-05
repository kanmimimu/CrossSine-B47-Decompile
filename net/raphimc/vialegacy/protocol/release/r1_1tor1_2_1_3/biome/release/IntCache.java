package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

public class IntCache {
   private static final ThreadLocal INT_CACHE = ThreadLocal.withInitial(IntCacheInstance::new);

   public static int[] getIntCache(int i) {
      return ((IntCacheInstance)INT_CACHE.get()).getIntCache(i);
   }

   public static void resetIntCache() {
      ((IntCacheInstance)INT_CACHE.get()).resetIntCache();
   }

   public static void resetEverything() {
      ((IntCacheInstance)INT_CACHE.get()).resetEverything();
   }
}
