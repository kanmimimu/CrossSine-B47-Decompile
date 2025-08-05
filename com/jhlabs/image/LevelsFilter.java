package com.jhlabs.image;

import java.awt.Rectangle;

public class LevelsFilter extends WholeImageFilter {
   protected int[][] lut;
   protected float lowLevel = 0.0F;
   protected float highLevel = 1.0F;
   protected float lowOutputLevel = 0.0F;
   protected float highOutputLevel = 1.0F;

   public void setLowLevel(float lowLevel) {
      this.lowLevel = lowLevel;
   }

   public float getLowLevel() {
      return this.lowLevel;
   }

   public void setHighLevel(float highLevel) {
      this.highLevel = highLevel;
   }

   public float getHighLevel() {
      return this.highLevel;
   }

   public void setLowOutputLevel(float lowOutputLevel) {
      this.lowOutputLevel = lowOutputLevel;
   }

   public float getLowOutputLevel() {
      return this.lowOutputLevel;
   }

   public void setHighOutputLevel(float highOutputLevel) {
      this.highOutputLevel = highOutputLevel;
   }

   public float getHighOutputLevel() {
      return this.highOutputLevel;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      Histogram histogram = new Histogram(inPixels, width, height, 0, width);
      if (histogram.getNumSamples() > 0) {
         float scale = 255.0F / (float)histogram.getNumSamples();
         this.lut = new int[3][256];
         float low = this.lowLevel * 255.0F;
         float high = this.highLevel * 255.0F;
         if (low == high) {
            ++high;
         }

         for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 256; ++j) {
               this.lut[i][j] = PixelUtils.clamp((int)(255.0F * (this.lowOutputLevel + (this.highOutputLevel - this.lowOutputLevel) * ((float)j - low) / (high - low))));
            }
         }
      } else {
         this.lut = (int[][])null;
      }

      int i = 0;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            inPixels[i] = this.filterRGB(x, y, inPixels[i]);
            ++i;
         }
      }

      this.lut = (int[][])null;
      return inPixels;
   }

   public int filterRGB(int x, int y, int rgb) {
      if (this.lut != null) {
         int a = rgb & -16777216;
         int r = this.lut[0][rgb >> 16 & 255];
         int g = this.lut[1][rgb >> 8 & 255];
         int b = this.lut[2][rgb & 255];
         return a | r << 16 | g << 8 | b;
      } else {
         return rgb;
      }
   }

   public String toString() {
      return "Colors/Levels...";
   }
}
