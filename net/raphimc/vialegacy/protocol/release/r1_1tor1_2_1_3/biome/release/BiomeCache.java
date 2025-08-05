package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeCache {
   private final WorldChunkManager_r1_1 chunkmanager;
   private long lastCleanupTime = 0L;
   private final Map cacheMap = new HashMap();
   private final List cache = new ArrayList();

   public BiomeCache(WorldChunkManager_r1_1 worldchunkmanager) {
      this.chunkmanager = worldchunkmanager;
   }

   public BiomeCacheBlock getBiomeCacheBlock(int i, int j) {
      i >>= 4;
      j >>= 4;
      long l = (long)i & 4294967295L | ((long)j & 4294967295L) << 32;
      BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)this.cacheMap.get(l);
      if (biomecacheblock == null) {
         biomecacheblock = new BiomeCacheBlock(this, i, j);
         this.cacheMap.put(l, biomecacheblock);
         this.cache.add(biomecacheblock);
      }

      biomecacheblock.lastAccessTime = System.currentTimeMillis();
      return biomecacheblock;
   }

   public NewBiomeGenBase getBiomeGenAt(int i, int j) {
      return this.getBiomeCacheBlock(i, j).getBiomeGenAt(i, j);
   }

   public void cleanupCache() {
      long l = System.currentTimeMillis();
      long l1 = l - this.lastCleanupTime;
      if (l1 > 7500L || l1 < 0L) {
         this.lastCleanupTime = l;

         for(int i = 0; i < this.cache.size(); ++i) {
            BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)this.cache.get(i);
            long l2 = l - biomecacheblock.lastAccessTime;
            if (l2 > 30000L || l2 < 0L) {
               this.cache.remove(i--);
               long l3 = (long)biomecacheblock.xPosition & 4294967295L | ((long)biomecacheblock.zPosition & 4294967295L) << 32;
               this.cacheMap.remove(l3);
            }
         }
      }

   }

   public NewBiomeGenBase[] getCachedBiomes(int i, int j) {
      return this.getBiomeCacheBlock(i, j).biomes;
   }

   static WorldChunkManager_r1_1 getWorldChunkManager(BiomeCache biomecache) {
      return biomecache.chunkmanager;
   }
}
