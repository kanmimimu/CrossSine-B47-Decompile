package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class GenLayerSnow extends GenLayer {
   public GenLayerSnow(long l, GenLayer genlayer) {
      super(l);
      this.parent = genlayer;
   }

   public int[] getInts(int i, int j, int k, int l) {
      int i1 = i - 1;
      int j1 = j - 1;
      int k1 = k + 2;
      int l1 = l + 2;
      int[] ai = this.parent.getInts(i1, j1, k1, l1);
      int[] ai1 = IntCache.getIntCache(k * l);

      for(int i2 = 0; i2 < l; ++i2) {
         for(int j2 = 0; j2 < k; ++j2) {
            int k2 = ai[j2 + 1 + (i2 + 1) * k1];
            this.initChunkSeed((long)(j2 + i), (long)(i2 + j));
            if (k2 == 0) {
               ai1[j2 + i2 * k] = 0;
            } else {
               int l2 = this.nextInt(5);
               if (l2 == 0) {
                  l2 = NewBiomeGenBase.icePlains.biomeID;
               } else {
                  l2 = 1;
               }

               ai1[j2 + i2 * k] = l2;
            }
         }
      }

      return ai1;
   }
}
