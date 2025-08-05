package com.jhlabs.image;

import java.awt.Image;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class ImageCombiningFilter {
   public int filterRGB(int x, int y, int rgb1, int rgb2) {
      int a1 = rgb1 >> 24 & 255;
      int r1 = rgb1 >> 16 & 255;
      int g1 = rgb1 >> 8 & 255;
      int b1 = rgb1 & 255;
      int a2 = rgb2 >> 24 & 255;
      int r2 = rgb2 >> 16 & 255;
      int g2 = rgb2 >> 8 & 255;
      int b2 = rgb2 & 255;
      int r = PixelUtils.clamp(r1 + r2);
      int g = PixelUtils.clamp(r1 + r2);
      int b = PixelUtils.clamp(r1 + r2);
      return a1 << 24 | r << 16 | g << 8 | b;
   }

   public ImageProducer filter(Image image1, Image image2, int x, int y, int w, int h) {
      int[] pixels1 = new int[w * h];
      int[] pixels2 = new int[w * h];
      int[] pixels3 = new int[w * h];
      PixelGrabber pg1 = new PixelGrabber(image1, x, y, w, h, pixels1, 0, w);
      PixelGrabber pg2 = new PixelGrabber(image2, x, y, w, h, pixels2, 0, w);

      try {
         pg1.grabPixels();
         pg2.grabPixels();
      } catch (InterruptedException var15) {
         System.err.println("interrupted waiting for pixels!");
         return null;
      }

      if ((pg1.status() & 128) != 0) {
         System.err.println("image fetch aborted or errored");
         return null;
      } else if ((pg2.status() & 128) != 0) {
         System.err.println("image fetch aborted or errored");
         return null;
      } else {
         for(int j = 0; j < h; ++j) {
            for(int i = 0; i < w; ++i) {
               int k = j * w + i;
               pixels3[k] = this.filterRGB(x + i, y + j, pixels1[k], pixels2[k]);
            }
         }

         return new MemoryImageSource(w, h, pixels3, 0, w);
      }
   }
}
