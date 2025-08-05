package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.genlayer;

import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.IntCache;

public class GenLayerZoom extends GenLayer {
   public GenLayerZoom(long l, GenLayer genlayer) {
      super(l);
      super.parent = genlayer;
   }

   public int[] getInts(int i, int j, int k, int l) {
      int i1 = i >> 1;
      int j1 = j >> 1;
      int k1 = (k >> 1) + 3;
      int l1 = (l >> 1) + 3;
      int[] ai = this.parent.getInts(i1, j1, k1, l1);
      int[] ai1 = IntCache.getIntCache(k1 * 2 * l1 * 2);
      int i2 = k1 << 1;

      for(int j2 = 0; j2 < l1 - 1; ++j2) {
         int k2 = j2 << 1;
         int i3 = k2 * i2;
         int j3 = ai[0 + (j2 + 0) * k1];
         int k3 = ai[0 + (j2 + 1) * k1];

         for(int l3 = 0; l3 < k1 - 1; ++l3) {
            this.initChunkSeed((long)(l3 + i1 << 1), (long)(j2 + j1 << 1));
            int i4 = ai[l3 + 1 + (j2 + 0) * k1];
            int j4 = ai[l3 + 1 + (j2 + 1) * k1];
            ai1[i3] = j3;
            ai1[i3++ + i2] = this.func_35516_a(j3, k3);
            ai1[i3] = this.func_35516_a(j3, i4);
            ai1[i3++ + i2] = this.func_35514_b(j3, i4, k3, j4);
            j3 = i4;
            k3 = j4;
         }
      }

      int[] ai2 = IntCache.getIntCache(k * l);

      for(int l2 = 0; l2 < l; ++l2) {
         System.arraycopy(ai1, (l2 + (j & 1)) * (k1 << 1) + (i & 1), ai2, l2 * k, k);
      }

      return ai2;
   }

   protected int func_35516_a(int i, int j) {
      return this.nextInt(2) != 0 ? j : i;
   }

   protected int func_35514_b(int i, int j, int k, int l) {
      if (j == k && k == l) {
         return j;
      } else if (i == j && i == k) {
         return i;
      } else if (i == j && i == l) {
         return i;
      } else if (i == k && i == l) {
         return i;
      } else if (i == j && k != l) {
         return i;
      } else if (i == k && j != l) {
         return i;
      } else if (i == l && j != k) {
         return i;
      } else if (j == i && k != l) {
         return j;
      } else if (j == k && i != l) {
         return j;
      } else if (j == l && i != k) {
         return j;
      } else if (k == i && j != l) {
         return k;
      } else if (k == j && i != l) {
         return k;
      } else if (k == l && i != j) {
         return k;
      } else if (l == i && j != k) {
         return k;
      } else if (l == j && i != k) {
         return k;
      } else if (l == k && i != j) {
         return k;
      } else {
         int i1 = this.nextInt(4);
         if (i1 == 0) {
            return i;
         } else if (i1 == 1) {
            return j;
         } else {
            return i1 == 2 ? k : l;
         }
      }
   }

   public static GenLayer func_35515_a(long l, GenLayer genlayer, int i) {
      Object obj = genlayer;

      for(int j = 0; j < i; ++j) {
         obj = new GenLayerZoom(l + (long)j, (GenLayer)obj);
      }

      return (GenLayer)obj;
   }
}
