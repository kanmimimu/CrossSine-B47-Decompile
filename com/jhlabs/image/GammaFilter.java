package com.jhlabs.image;

public class GammaFilter extends TransferFilter {
   private float rGamma;
   private float gGamma;
   private float bGamma;

   public GammaFilter() {
      this(1.0F);
   }

   public GammaFilter(float gamma) {
      this(gamma, gamma, gamma);
   }

   public GammaFilter(float rGamma, float gGamma, float bGamma) {
      this.setGamma(rGamma, gGamma, bGamma);
   }

   public void setGamma(float rGamma, float gGamma, float bGamma) {
      this.rGamma = rGamma;
      this.gGamma = gGamma;
      this.bGamma = bGamma;
      super.initialized = false;
   }

   public void setGamma(float gamma) {
      this.setGamma(gamma, gamma, gamma);
   }

   public float getGamma() {
      return this.rGamma;
   }

   protected void initialize() {
      super.rTable = this.makeTable(this.rGamma);
      if (this.gGamma == this.rGamma) {
         super.gTable = super.rTable;
      } else {
         super.gTable = this.makeTable(this.gGamma);
      }

      if (this.bGamma == this.rGamma) {
         super.bTable = super.rTable;
      } else if (this.bGamma == this.gGamma) {
         super.bTable = super.gTable;
      } else {
         super.bTable = this.makeTable(this.bGamma);
      }

   }

   protected int[] makeTable(float gamma) {
      int[] table = new int[256];

      for(int i = 0; i < 256; ++i) {
         int v = (int)((double)255.0F * Math.pow((double)i / (double)255.0F, (double)1.0F / (double)gamma) + (double)0.5F);
         if (v > 255) {
            v = 255;
         }

         table[i] = v;
      }

      return table;
   }

   public String toString() {
      return "Colors/Gamma...";
   }
}
