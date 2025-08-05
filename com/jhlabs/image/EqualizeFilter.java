package com.jhlabs.image;

import java.awt.Rectangle;
import java.io.Serializable;

public class EqualizeFilter extends WholeImageFilter implements Serializable {
   protected int[][] lut;

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      Histogram histogram = new Histogram(inPixels, width, height, 0, width);
      if (histogram.getNumSamples() > 0) {
         float scale = 255.0F / (float)histogram.getNumSamples();
         this.lut = new int[3][256];

         for(int i = 0; i < 3; ++i) {
            this.lut[i][0] = histogram.getFrequency(i, 0);

            for(int j = 1; j < 256; ++j) {
               this.lut[i][j] = this.lut[i][j - 1] + histogram.getFrequency(i, j);
            }

            for(int var11 = 0; var11 < 256; ++var11) {
               this.lut[i][var11] = Math.round((float)this.lut[i][var11] * scale);
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
      return "Colors/Equalize";
   }
}
