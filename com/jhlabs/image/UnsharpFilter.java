package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class UnsharpFilter extends GaussianFilter {
   static final long serialVersionUID = 5377089073023183684L;
   private float amount = 0.5F;
   private int threshold = 1;

   public UnsharpFilter() {
      super.radius = 2.0F;
   }

   public void setThreshold(int threshold) {
      this.threshold = threshold;
   }

   public int getThreshold() {
      return this.threshold;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = ((ConvolveFilter)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      src.getRGB(0, 0, width, height, inPixels, 0, width);
      if (super.radius > 0.0F) {
         GaussianFilter.convolveAndTranspose(super.kernel, inPixels, outPixels, width, height, super.alpha, ConvolveFilter.CLAMP_EDGES);
         GaussianFilter.convolveAndTranspose(super.kernel, outPixels, inPixels, height, width, super.alpha, ConvolveFilter.CLAMP_EDGES);
      }

      src.getRGB(0, 0, width, height, outPixels, 0, width);
      float a = 4.0F * this.amount;
      int index = 0;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            int rgb1 = outPixels[index];
            int r1 = rgb1 >> 16 & 255;
            int g1 = rgb1 >> 8 & 255;
            int b1 = rgb1 & 255;
            int rgb2 = inPixels[index];
            int r2 = rgb2 >> 16 & 255;
            int g2 = rgb2 >> 8 & 255;
            int b2 = rgb2 & 255;
            if (Math.abs(r1 - r2) >= this.threshold) {
               r1 = PixelUtils.clamp((int)((a + 1.0F) * (float)(r1 - r2) + (float)r2));
            }

            if (Math.abs(g1 - g2) >= this.threshold) {
               g1 = PixelUtils.clamp((int)((a + 1.0F) * (float)(g1 - g2) + (float)g2));
            }

            if (Math.abs(b1 - b2) >= this.threshold) {
               b1 = PixelUtils.clamp((int)((a + 1.0F) * (float)(b1 - b2) + (float)b2));
            }

            inPixels[index] = rgb1 & -16777216 | r1 << 16 | g1 << 8 | b1;
            ++index;
         }
      }

      dst.setRGB(0, 0, width, height, inPixels, 0, width);
      return dst;
   }

   public String toString() {
      return "Blur/Unsharp Mask...";
   }
}
