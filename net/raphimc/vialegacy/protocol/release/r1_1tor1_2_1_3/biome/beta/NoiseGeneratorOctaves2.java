package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.util.Random;

public class NoiseGeneratorOctaves2 {
   private final NoiseGenerator2[] field_4234_a;
   private final int field_4233_b;

   public NoiseGeneratorOctaves2(Random random, int i) {
      this.field_4233_b = i;
      this.field_4234_a = new NoiseGenerator2[i];

      for(int j = 0; j < i; ++j) {
         this.field_4234_a[j] = new NoiseGenerator2(random);
      }

   }

   public double[] func_4112_a(double[] ad, double d, double d1, int i, int j, double d2, double d3, double d4) {
      return this.func_4111_a(ad, d, d1, i, j, d2, d3, d4, (double)0.5F);
   }

   public double[] func_4111_a(double[] ad, double d, double d1, int i, int j, double d2, double d3, double d4, double d5) {
      d2 /= (double)1.5F;
      d3 /= (double)1.5F;
      if (ad != null && ad.length >= i * j) {
         for(int k = 0; k < ad.length; ++k) {
            ad[k] = (double)0.0F;
         }
      } else {
         ad = new double[i * j];
      }

      double d6 = (double)1.0F;
      double d7 = (double)1.0F;

      for(int l = 0; l < this.field_4233_b; ++l) {
         this.field_4234_a[l].func_4157_a(ad, d, d1, i, j, d2 * d7, d3 * d7, 0.55 / d6);
         d7 *= d4;
         d6 *= d5;
      }

      return ad;
   }
}
