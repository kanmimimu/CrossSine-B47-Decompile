package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release;

public class BiomeCacheBlock {
   public NewBiomeGenBase[] biomes;
   public int xPosition;
   public int zPosition;
   public long lastAccessTime;
   final BiomeCache biomeCache;

   public BiomeCacheBlock(BiomeCache biomecache, int i, int j) {
      this.biomeCache = biomecache;
      this.biomes = new NewBiomeGenBase[256];
      this.xPosition = i;
      this.zPosition = j;
      BiomeCache.getWorldChunkManager(biomecache).getBiomeGenAt(this.biomes, i << 4, j << 4, 16, 16, false);
   }

   public NewBiomeGenBase getBiomeGenAt(int i, int j) {
      return this.biomes[i & 15 | (j & 15) << 4];
   }
}
