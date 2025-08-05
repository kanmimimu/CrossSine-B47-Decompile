package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta;

import java.awt.Color;

public class OldBiomeGenBase {
   private static final OldBiomeGenBase[] biomeLookupTable = new OldBiomeGenBase[4096];
   public static final OldBiomeGenBase rainforest = new OldBiomeGenBase();
   public static final OldBiomeGenBase swampland = new OldBiomeGenBase();
   public static final OldBiomeGenBase seasonalForest = new OldBiomeGenBase();
   public static final OldBiomeGenBase forest = new OldBiomeGenBase();
   public static final OldBiomeGenBase savanna = new OldBiomeGenBase();
   public static final OldBiomeGenBase shrubland = new OldBiomeGenBase();
   public static final OldBiomeGenBase taiga = new OldBiomeGenBase();
   public static final OldBiomeGenBase desert = new OldBiomeGenBase();
   public static final OldBiomeGenBase plains = new OldBiomeGenBase();
   public static final OldBiomeGenBase iceDesert = new OldBiomeGenBase();
   public static final OldBiomeGenBase tundra = new OldBiomeGenBase();
   public static final OldBiomeGenBase hell = new OldBiomeGenBase();
   public static final OldBiomeGenBase sky = new OldBiomeGenBase();

   protected OldBiomeGenBase() {
   }

   public static void generateBiomeLookup() {
      for(int i = 0; i < 64; ++i) {
         for(int j = 0; j < 64; ++j) {
            biomeLookupTable[i + j * 64] = getBiome((float)i / 63.0F, (float)j / 63.0F);
         }
      }

   }

   public static OldBiomeGenBase getBiomeFromLookup(double d, double d1) {
      int i = (int)(d * (double)63.0F);
      int j = (int)(d1 * (double)63.0F);
      return biomeLookupTable[i + j * 64];
   }

   public static OldBiomeGenBase getBiome(float f, float f1) {
      f1 *= f;
      if (f < 0.1F) {
         return tundra;
      } else if (f1 < 0.2F) {
         if (f < 0.5F) {
            return tundra;
         } else {
            return f < 0.95F ? savanna : desert;
         }
      } else if (f1 > 0.5F && f < 0.7F) {
         return swampland;
      } else if (f < 0.5F) {
         return taiga;
      } else if (f < 0.97F) {
         return f1 < 0.35F ? shrubland : forest;
      } else if (f1 < 0.45F) {
         return plains;
      } else {
         return f1 < 0.9F ? seasonalForest : rainforest;
      }
   }

   public int getSkyColorByTemp(float f) {
      f /= 3.0F;
      if (f < -1.0F) {
         f = -1.0F;
      }

      if (f > 1.0F) {
         f = 1.0F;
      }

      return Color.getHSBColor(0.6222222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
   }

   static {
      generateBiomeLookup();
   }
}
