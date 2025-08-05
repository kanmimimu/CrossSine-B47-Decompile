package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;

public class GenLayerIsland_b1_8 extends GenLayer {
   public GenLayerIsland_b1_8(long l, GenLayer genlayer) {
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
            int k2 = ai[j2 + 0 + (i2 + 0) * k1];
            int l2 = ai[j2 + 2 + (i2 + 0) * k1];
            int i3 = ai[j2 + 0 + (i2 + 2) * k1];
            int j3 = ai[j2 + 2 + (i2 + 2) * k1];
            int k3 = ai[j2 + 1 + (i2 + 1) * k1];
            this.initChunkSeed((long)(j2 + i), (long)(i2 + j));
            if (k3 != 0 || k2 == 0 && l2 == 0 && i3 == 0 && j3 == 0) {
               if (k3 != 1 || k2 == 1 && l2 == 1 && i3 == 1 && j3 == 1) {
                  ai1[j2 + i2 * k] = k3;
               } else {
                  ai1[j2 + i2 * k] = 1 - this.nextInt(5) / 4;
               }
            } else {
               ai1[j2 + i2 * k] = 0 + this.nextInt(3) / 2;
            }
         }
      }

      return ai1;
   }
}
