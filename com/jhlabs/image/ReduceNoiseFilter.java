package com.jhlabs.image;

import java.awt.Rectangle;

public class ReduceNoiseFilter extends WholeImageFilter {
   private int smooth(int[] v) {
      int minindex = 0;
      int maxindex = 0;
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;

      for(int i = 0; i < 9; ++i) {
         if (i != 4) {
            if (v[i] < min) {
               min = v[i];
               minindex = i;
            }

            if (v[i] > max) {
               max = v[i];
               maxindex = i;
            }
         }
      }

      if (v[4] < min) {
         return v[minindex];
      } else if (v[4] > max) {
         return v[maxindex];
      } else {
         return v[4];
      }
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int index = 0;
      int[] r = new int[9];
      int[] g = new int[9];
      int[] b = new int[9];
      int[] outPixels = new int[width * height];

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            int k = 0;

            for(int dy = -1; dy <= 1; ++dy) {
               int iy = y + dy;
               if (0 <= iy && iy < height) {
                  int ioffset = iy * width;

                  for(int dx = -1; dx <= 1; ++dx) {
                     int ix = x + dx;
                     if (0 <= ix && ix < width) {
                        int rgb = inPixels[ioffset + ix];
                        r[k] = rgb >> 16 & 255;
                        g[k] = rgb >> 8 & 255;
                        b[k] = rgb & 255;
                        ++k;
                     }
                  }
               }
            }

            while(k < 9) {
               r[k] = g[k] = b[k] = 0;
               ++k;
            }

            outPixels[index] = inPixels[index] & -16777216 | this.smooth(r) << 16 | this.smooth(g) << 8 | this.smooth(b);
            ++index;
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Blur/Smooth";
   }
}
