package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;

public class SmartBlurFilter extends AbstractBufferedImageOp {
   private int hRadius = 5;
   private int vRadius = 5;
   private int threshold = 10;

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, inPixels);
      Kernel kernel = GaussianFilter.makeKernel((float)this.hRadius);
      this.thresholdBlur(kernel, inPixels, outPixels, width, height, true);
      this.thresholdBlur(kernel, outPixels, inPixels, height, width, true);
      ((AbstractBufferedImageOp)this).setRGB(dst, 0, 0, width, height, inPixels);
      return dst;
   }

   public void thresholdBlur(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int cols = kernel.getWidth();
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
         int ioffset = y * width;
         int outIndex = y;

         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;
            int moffset = cols2;
            int rgb1 = inPixels[ioffset + x];
            int a1 = rgb1 >> 24 & 255;
            int r1 = rgb1 >> 16 & 255;
            int g1 = rgb1 >> 8 & 255;
            int b1 = rgb1 & 255;
            float af = 0.0F;
            float rf = 0.0F;
            float gf = 0.0F;
            float bf = 0.0F;

            for(int col = -cols2; col <= cols2; ++col) {
               float f = matrix[moffset + col];
               if (f != 0.0F) {
                  int ix = x + col;
                  if (0 > ix || ix >= width) {
                     ix = x;
                  }

                  int rgb2 = inPixels[ioffset + ix];
                  int a2 = rgb2 >> 24 & 255;
                  int r2 = rgb2 >> 16 & 255;
                  int g2 = rgb2 >> 8 & 255;
                  int b2 = rgb2 & 255;
                  int d = a1 - a2;
                  if (d >= -this.threshold && d <= this.threshold) {
                     a += f * (float)a2;
                     af += f;
                  }

                  d = r1 - r2;
                  if (d >= -this.threshold && d <= this.threshold) {
                     r += f * (float)r2;
                     rf += f;
                  }

                  d = g1 - g2;
                  if (d >= -this.threshold && d <= this.threshold) {
                     g += f * (float)g2;
                     gf += f;
                  }

                  d = b1 - b2;
                  if (d >= -this.threshold && d <= this.threshold) {
                     b += f * (float)b2;
                     bf += f;
                  }
               }
            }

            a = af == 0.0F ? (float)a1 : a / af;
            r = rf == 0.0F ? (float)r1 : r / rf;
            g = gf == 0.0F ? (float)g1 : g / gf;
            b = bf == 0.0F ? (float)b1 : b / bf;
            int ia = alpha ? PixelUtils.clamp((int)((double)a + (double)0.5F)) : 255;
            int ir = PixelUtils.clamp((int)((double)r + (double)0.5F));
            int ig = PixelUtils.clamp((int)((double)g + (double)0.5F));
            int ib = PixelUtils.clamp((int)((double)b + (double)0.5F));
            outPixels[outIndex] = ia << 24 | ir << 16 | ig << 8 | ib;
            outIndex += height;
         }
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

   public void setThreshold(int threshold) {
      this.threshold = threshold;
   }

   public int getThreshold() {
      return this.threshold;
   }

   public String toString() {
      return "Blur/Smart Blur...";
   }
}
