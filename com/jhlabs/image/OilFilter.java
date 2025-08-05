package com.jhlabs.image;

import java.awt.Rectangle;

public class OilFilter extends WholeImageFilter {
   static final long serialVersionUID = 1722613531684653826L;
   private int range = 3;
   private int levels = 256;

   public void setRange(int range) {
      this.range = range;
   }

   public int getRange() {
      return this.range;
   }

   public void setLevels(int levels) {
      this.levels = levels;
   }

   public int getLevels() {
      return this.levels;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int index = 0;
      int[] rHistogram = new int[this.levels];
      int[] gHistogram = new int[this.levels];
      int[] bHistogram = new int[this.levels];
      int[] rTotal = new int[this.levels];
      int[] gTotal = new int[this.levels];
      int[] bTotal = new int[this.levels];
      int[] outPixels = new int[width * height];

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            for(int i = 0; i < this.levels; ++i) {
               rHistogram[i] = gHistogram[i] = bHistogram[i] = rTotal[i] = gTotal[i] = bTotal[i] = 0;
            }

            for(int row = -this.range; row <= this.range; ++row) {
               int iy = y + row;
               if (0 <= iy && iy < height) {
                  int ioffset = iy * width;

                  for(int col = -this.range; col <= this.range; ++col) {
                     int ix = x + col;
                     if (0 <= ix && ix < width) {
                        int rgb = inPixels[ioffset + ix];
                        int r = rgb >> 16 & 255;
                        int g = rgb >> 8 & 255;
                        int b = rgb & 255;
                        int ri = r * this.levels / 256;
                        int gi = g * this.levels / 256;
                        int bi = b * this.levels / 256;
                        rTotal[ri] += r;
                        gTotal[gi] += g;
                        bTotal[bi] += b;
                        int var10002 = rHistogram[ri]++;
                        var10002 = gHistogram[gi]++;
                        var10002 = bHistogram[bi]++;
                     }
                  }
               }
            }

            int r = 0;
            int g = 0;
            int b = 0;

            for(int i = 1; i < this.levels; ++i) {
               if (rHistogram[i] > rHistogram[r]) {
                  r = i;
               }

               if (gHistogram[i] > gHistogram[g]) {
                  g = i;
               }

               if (bHistogram[i] > bHistogram[b]) {
                  b = i;
               }
            }

            r = rTotal[r] / rHistogram[r];
            g = gTotal[g] / gHistogram[g];
            b = bTotal[b] / bHistogram[b];
            outPixels[index++] = -16777216 | r << 16 | g << 8 | b;
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Stylize/Oil...";
   }
}
