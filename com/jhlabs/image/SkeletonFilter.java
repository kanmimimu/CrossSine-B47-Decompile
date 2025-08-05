package com.jhlabs.image;

import java.awt.Rectangle;

public class SkeletonFilter extends BinaryFilter {
   private static final byte[] skeletonTable = new byte[]{0, 0, 0, 1, 0, 0, 1, 3, 0, 0, 3, 1, 1, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 3, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 3, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 1, 3, 1, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 1, 3, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 1, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 0};

   public SkeletonFilter() {
      super.newColor = -1;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int[] outPixels = new int[width * height];
      int count = 0;
      int black = -16777216;
      int white = -1;

      for(int i = 0; i < super.iterations; ++i) {
         count = 0;

         for(int pass = 0; pass < 2; ++pass) {
            for(int y = 1; y < height - 1; ++y) {
               int offset = y * width + 1;

               for(int x = 1; x < width - 1; ++x) {
                  int pixel = inPixels[offset];
                  if (pixel == black) {
                     int tableIndex = 0;
                     if (inPixels[offset - width - 1] == black) {
                        tableIndex |= 1;
                     }

                     if (inPixels[offset - width] == black) {
                        tableIndex |= 2;
                     }

                     if (inPixels[offset - width + 1] == black) {
                        tableIndex |= 4;
                     }

                     if (inPixels[offset + 1] == black) {
                        tableIndex |= 8;
                     }

                     if (inPixels[offset + width + 1] == black) {
                        tableIndex |= 16;
                     }

                     if (inPixels[offset + width] == black) {
                        tableIndex |= 32;
                     }

                     if (inPixels[offset + width - 1] == black) {
                        tableIndex |= 64;
                     }

                     if (inPixels[offset - 1] == black) {
                        tableIndex |= 128;
                     }

                     int code = skeletonTable[tableIndex];
                     if (pass == 1) {
                        if (code == 2 || code == 3) {
                           if (super.colormap != null) {
                              pixel = super.colormap.getColor((float)i / (float)super.iterations);
                           } else {
                              pixel = super.newColor;
                           }

                           ++count;
                        }
                     } else if (code == 1 || code == 3) {
                        if (super.colormap != null) {
                           pixel = super.colormap.getColor((float)i / (float)super.iterations);
                        } else {
                           pixel = super.newColor;
                        }

                        ++count;
                     }
                  }

                  outPixels[offset++] = pixel;
               }
            }

            if (pass == 0) {
               inPixels = outPixels;
               outPixels = new int[width * height];
            }
         }

         if (count == 0) {
            break;
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Binary/Skeletonize...";
   }
}
