package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class GenLayerVillageLandscape_r1_0 extends GenLayer {
   private final NewBiomeGenBase[] allowedBiomes;

   public GenLayerVillageLandscape_r1_0(long l, GenLayer genlayer) {
      super(l);
      this.allowedBiomes = new NewBiomeGenBase[]{NewBiomeGenBase.desert, NewBiomeGenBase.forest, NewBiomeGenBase.extremeHills, NewBiomeGenBase.swampland, NewBiomeGenBase.plains, NewBiomeGenBase.taiga};
      this.parent = genlayer;
   }

   public int[] getInts(int i, int j, int k, int l) {
      int[] ai = this.parent.getInts(i, j, k, l);
      int[] ai1 = IntCache.getIntCache(k * l);

      for(int i1 = 0; i1 < l; ++i1) {
         for(int j1 = 0; j1 < k; ++j1) {
            this.initChunkSeed((long)(j1 + i), (long)(i1 + j));
            int k1 = ai[j1 + i1 * k];
            if (k1 == 0) {
               ai1[j1 + i1 * k] = 0;
            } else if (k1 == NewBiomeGenBase.mushroomIsland.biomeID) {
               ai1[j1 + i1 * k] = k1;
            } else if (k1 == 1) {
               ai1[j1 + i1 * k] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
            } else {
               ai1[j1 + i1 * k] = NewBiomeGenBase.icePlains.biomeID;
            }
         }
      }

      return ai1;
   }
}
