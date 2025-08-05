package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.util.Random;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.IWorldChunkManager;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.NewBiomeGenBase;

public class WorldChunkManager_b1_7 implements IWorldChunkManager {
   private final NoiseGeneratorOctaves2 field_4194_e;
   private final NoiseGeneratorOctaves2 field_4193_f;
   private final NoiseGeneratorOctaves2 field_4192_g;
   public double[] temperature;
   public double[] humidity;
   public double[] field_4196_c;
   public OldBiomeGenBase[] field_4195_d;

   public WorldChunkManager_b1_7(long seed) {
      this.field_4194_e = new NoiseGeneratorOctaves2(new Random(seed * 9871L), 4);
      this.field_4193_f = new NoiseGeneratorOctaves2(new Random(seed * 39811L), 4);
      this.field_4192_g = new NoiseGeneratorOctaves2(new Random(seed * 543321L), 2);
   }

   public byte[] getBiomeDataAt(int chunkX, int chunkZ) {
      byte[] biomeData = new byte[256];

      for(int x = 0; x < 16; ++x) {
         for(int z = 0; z < 16; ++z) {
            biomeData[z << 4 | x] = (byte)this.getBiomeGenAt(chunkX * 16 + x, chunkZ * 16 + z).biomeID;
         }
      }

      return biomeData;
   }

   public NewBiomeGenBase getBiomeGenAtChunkCoord(ChunkCoord chunkcoordintpair) {
      return this.getBiomeGenAt(chunkcoordintpair.chunkX << 4, chunkcoordintpair.chunkZ << 4);
   }

   public NewBiomeGenBase getBiomeGenAt(int i, int j) {
      OldBiomeGenBase oldBiomeGenBase = this.func_4069_a(i, j, 1, 1)[0];
      if (oldBiomeGenBase.equals(OldBiomeGenBase.rainforest)) {
         return NewBiomeGenBase.jungle;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.swampland)) {
         return NewBiomeGenBase.swampland;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.seasonalForest)) {
         return NewBiomeGenBase.forest;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.forest)) {
         return NewBiomeGenBase.forest;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.savanna)) {
         return NewBiomeGenBase.savanna;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.shrubland)) {
         return NewBiomeGenBase.mutatedJungleEdge;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.taiga)) {
         return NewBiomeGenBase.taiga;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.desert)) {
         return NewBiomeGenBase.desert;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.plains)) {
         return NewBiomeGenBase.plains;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.iceDesert)) {
         return NewBiomeGenBase.icePlains;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.tundra)) {
         return NewBiomeGenBase.icePlains;
      } else if (oldBiomeGenBase.equals(OldBiomeGenBase.hell)) {
         return NewBiomeGenBase.hell;
      } else {
         return oldBiomeGenBase.equals(OldBiomeGenBase.sky) ? NewBiomeGenBase.sky : NewBiomeGenBase.plains;
      }
   }

   public double getTemperature(int i, int j) {
      this.temperature = this.field_4194_e.func_4112_a(this.temperature, (double)i, (double)j, 1, 1, (double)0.025F, (double)0.025F, (double)0.5F);
      return this.temperature[0];
   }

   public OldBiomeGenBase[] func_4069_a(int i, int j, int k, int l) {
      this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, i, j, k, l);
      return this.field_4195_d;
   }

   public double[] getTemperatures(double[] ad, int i, int j, int k, int l) {
      if (ad == null || ad.length < k * l) {
         ad = new double[k * l];
      }

      ad = this.field_4194_e.func_4112_a(ad, (double)i, (double)j, k, l, (double)0.025F, (double)0.025F, (double)0.25F);
      this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, (double)i, (double)j, k, l, (double)0.25F, (double)0.25F, 0.5882352941176471);
      int i1 = 0;

      for(int j1 = 0; j1 < k; ++j1) {
         for(int k1 = 0; k1 < l; ++k1) {
            double d = this.field_4196_c[i1] * 1.1 + (double)0.5F;
            double d1 = 0.01;
            double d2 = (double)1.0F - d1;
            double d3 = (ad[i1] * 0.15 + 0.7) * d2 + d * d1;
            d3 = (double)1.0F - ((double)1.0F - d3) * ((double)1.0F - d3);
            if (d3 < (double)0.0F) {
               d3 = (double)0.0F;
            }

            if (d3 > (double)1.0F) {
               d3 = (double)1.0F;
            }

            ad[i1] = d3;
            ++i1;
         }
      }

      return ad;
   }

   public OldBiomeGenBase[] loadBlockGeneratorData(OldBiomeGenBase[] abiomegenbase, int i, int j, int k, int l) {
      if (abiomegenbase == null || abiomegenbase.length < k * l) {
         abiomegenbase = new OldBiomeGenBase[k * l];
      }

      this.temperature = this.field_4194_e.func_4112_a(this.temperature, (double)i, (double)j, k, k, (double)0.025F, (double)0.025F, (double)0.25F);
      this.humidity = this.field_4193_f.func_4112_a(this.humidity, (double)i, (double)j, k, k, (double)0.05F, (double)0.05F, 0.3333333333333333);
      this.field_4196_c = this.field_4192_g.func_4112_a(this.field_4196_c, (double)i, (double)j, k, k, (double)0.25F, (double)0.25F, 0.5882352941176471);
      int i1 = 0;

      for(int j1 = 0; j1 < k; ++j1) {
         for(int k1 = 0; k1 < l; ++k1) {
            double d = this.field_4196_c[i1] * 1.1 + (double)0.5F;
            double d1 = 0.01;
            double d2 = (double)1.0F - d1;
            double d3 = (this.temperature[i1] * 0.15 + 0.7) * d2 + d * d1;
            d1 = 0.002;
            d2 = (double)1.0F - d1;
            double d4 = (this.humidity[i1] * 0.15 + (double)0.5F) * d2 + d * d1;
            d3 = (double)1.0F - ((double)1.0F - d3) * ((double)1.0F - d3);
            if (d3 < (double)0.0F) {
               d3 = (double)0.0F;
            }

            if (d4 < (double)0.0F) {
               d4 = (double)0.0F;
            }

            if (d3 > (double)1.0F) {
               d3 = (double)1.0F;
            }

            if (d4 > (double)1.0F) {
               d4 = (double)1.0F;
            }

            this.temperature[i1] = d3;
            this.humidity[i1] = d4;
            abiomegenbase[i1++] = OldBiomeGenBase.getBiomeFromLookup(d3, d4);
         }
      }

      return abiomegenbase;
   }
}
