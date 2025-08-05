package com.jhlabs.image;

import java.awt.Rectangle;

public class DiffusionFilter extends WholeImageFilter {
   protected static final int[] diffusionMatrix = new int[]{0, 0, 0, 0, 0, 7, 3, 5, 1};
   private int[] matrix;
   private int sum = 16;
   private boolean serpentine = true;
   private boolean colorDither = true;
   public int levels = 6;

   public DiffusionFilter() {
      this.setMatrix(diffusionMatrix);
   }

   public void setSerpentine(boolean serpentine) {
      this.serpentine = serpentine;
   }

   public boolean getSerpentine() {
      return this.serpentine;
   }

   public void setColorDither(boolean colorDither) {
      this.colorDither = colorDither;
   }

   public boolean getColorDither() {
      return this.colorDither;
   }

   public void setMatrix(int[] matrix) {
      this.matrix = matrix;
      this.sum = 0;

      for(int i = 0; i < matrix.length; ++i) {
         this.sum += matrix[i];
      }

   }

   public int[] getMatrix() {
      return this.matrix;
   }

   public void setLevels(int levels) {
      this.levels = levels;
   }

   public int getLevels() {
      return this.levels;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int[] outPixels = new int[width * height];
      int index = 0;
      int[] map = new int[this.levels];

      for(int i = 0; i < this.levels; ++i) {
         int v = 255 * i / (this.levels - 1);
         map[i] = v;
      }

      int[] div = new int[256];

      for(int i = 0; i < 256; ++i) {
         div[i] = this.levels * i / 256;
      }

      for(int y = 0; y < height; ++y) {
         boolean reverse = this.serpentine && (y & 1) == 1;
         int direction;
         if (reverse) {
            index = y * width + width - 1;
            direction = -1;
         } else {
            index = y * width;
            direction = 1;
         }

         for(int x = 0; x < width; ++x) {
            int rgb1 = inPixels[index];
            int r1 = rgb1 >> 16 & 255;
            int g1 = rgb1 >> 8 & 255;
            int b1 = rgb1 & 255;
            if (!this.colorDither) {
               r1 = g1 = b1 = (r1 + g1 + b1) / 3;
            }

            int r2 = map[div[r1]];
            int g2 = map[div[g1]];
            int b2 = map[div[b1]];
            outPixels[index] = -16777216 | r2 << 16 | g2 << 8 | b2;
            int er = r1 - r2;
            int eg = g1 - g2;
            int eb = b1 - b2;

            for(int i = -1; i <= 1; ++i) {
               int iy = i + y;
               if (0 <= iy && iy < height) {
                  for(int j = -1; j <= 1; ++j) {
                     int jx = j + x;
                     if (0 <= jx && jx < width) {
                        int w;
                        if (reverse) {
                           w = this.matrix[(i + 1) * 3 - j + 1];
                        } else {
                           w = this.matrix[(i + 1) * 3 + j + 1];
                        }

                        if (w != 0) {
                           int k = reverse ? index - j : index + j;
                           rgb1 = inPixels[k];
                           r1 = rgb1 >> 16 & 255;
                           g1 = rgb1 >> 8 & 255;
                           b1 = rgb1 & 255;
                           r1 += er * w / this.sum;
                           g1 += eg * w / this.sum;
                           b1 += eb * w / this.sum;
                           inPixels[k] = PixelUtils.clamp(r1) << 16 | PixelUtils.clamp(g1) << 8 | PixelUtils.clamp(b1);
                        }
                     }
                  }
               }
            }

            index += direction;
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Colors/Diffusion Dither...";
   }
}
