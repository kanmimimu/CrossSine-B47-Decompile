package com.jhlabs.image;

import java.io.Serializable;

public class PosterizeFilter extends PointFilter implements Serializable {
   private int numLevels;
   private int[] levels;
   private boolean initialized = false;

   public PosterizeFilter() {
      this.setNumLevels(6);
   }

   public void setNumLevels(int numLevels) {
      this.numLevels = numLevels;
      this.initialized = false;
   }

   public int getNumLevels() {
      return this.numLevels;
   }

   protected void initialize() {
      this.levels = new int[256];
      if (this.numLevels != 1) {
         for(int i = 0; i < 256; ++i) {
            this.levels[i] = 255 * (this.numLevels * i / 256) / (this.numLevels - 1);
         }
      }

   }

   public int filterRGB(int x, int y, int rgb) {
      if (!this.initialized) {
         this.initialized = true;
         this.initialize();
      }

      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      r = this.levels[r];
      g = this.levels[g];
      b = this.levels[b];
      return a | r << 16 | g << 8 | b;
   }

   public String toString() {
      return "Colors/Posterize...";
   }
}
