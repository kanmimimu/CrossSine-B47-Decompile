package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class BoxBlurFilter extends AbstractBufferedImageOp {
   private int hRadius;
   private int vRadius;
   private int iterations = 1;

   public BoxBlurFilter() {
   }

   public BoxBlurFilter(int hRadius, int vRadius, int iterations) {
      this.hRadius = hRadius;
      this.vRadius = vRadius;
      this.iterations = iterations;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, inPixels);

      for(int i = 0; i < this.iterations; ++i) {
         blur(inPixels, outPixels, width, height, this.hRadius);
         blur(outPixels, inPixels, height, width, this.vRadius);
      }

      ((AbstractBufferedImageOp)this).setRGB(dst, 0, 0, width, height, inPixels);
      return dst;
   }

   public static void blur(int[] in, int[] out, int width, int height, int radius) {
      int widthMinus1 = width - 1;
      int tableSize = 2 * radius + 1;
      int[] divide = new int[256 * tableSize];

      for(int i = 0; i < 256 * tableSize; ++i) {
         divide[i] = i / tableSize;
      }

      int inIndex = 0;

      for(int y = 0; y < height; ++y) {
         int outIndex = y;
         int ta = 0;
         int tr = 0;
         int tg = 0;
         int tb = 0;

         for(int i = -radius; i <= radius; ++i) {
            int rgb = in[inIndex + ImageMath.clamp(i, 0, width - 1)];
            ta += rgb >> 24 & 255;
            tr += rgb >> 16 & 255;
            tg += rgb >> 8 & 255;
            tb += rgb & 255;
         }

         for(int x = 0; x < width; ++x) {
            out[outIndex] = divide[ta] << 24 | divide[tr] << 16 | divide[tg] << 8 | divide[tb];
            int i1 = x + radius + 1;
            if (i1 > widthMinus1) {
               i1 = widthMinus1;
            }

            int i2 = x - radius;
            if (i2 < 0) {
               i2 = 0;
            }

            int rgb1 = in[inIndex + i1];
            int rgb2 = in[inIndex + i2];
            ta += (rgb1 >> 24 & 255) - (rgb2 >> 24 & 255);
            tr += (rgb1 & 16711680) - (rgb2 & 16711680) >> 16;
            tg += (rgb1 & '\uff00') - (rgb2 & '\uff00') >> 8;
            tb += (rgb1 & 255) - (rgb2 & 255);
            outIndex += height;
         }

         inIndex += width;
      }

   }

   public void setHRadius(int hRadius) {
      this.hRadius = hRadius;
   }

   public int getHRadius() {
      return this.hRadius;
   }

   public void setVRadius(int vRadius) {
      this.vRadius = vRadius;
   }

   public int getVRadius() {
      return this.vRadius;
   }

   public void setRadius(int radius) {
      this.hRadius = this.vRadius = radius;
   }

   public int getRadius() {
      return this.hRadius;
   }

   public void setIterations(int iterations) {
      this.iterations = iterations;
   }

   public int getIterations() {
      return this.iterations;
   }

   public String toString() {
      return "Blur/Box Blur...";
   }
}
