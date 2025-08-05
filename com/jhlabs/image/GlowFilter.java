package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class GlowFilter extends GaussianFilter {
   static final long serialVersionUID = 5377089073023183684L;
   private float amount = 0.5F;

   public GlowFilter() {
      super.radius = 2.0F;
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
            r1 = PixelUtils.clamp((int)((float)r1 + a * (float)r2));
            g1 = PixelUtils.clamp((int)((float)g1 + a * (float)g2));
            b1 = PixelUtils.clamp((int)((float)b1 + a * (float)b2));
            inPixels[index] = rgb1 & -16777216 | r1 << 16 | g1 << 8 | b1;
            ++index;
         }
      }

      dst.setRGB(0, 0, width, height, inPixels, 0, width);
      return dst;
   }

   public String toString() {
      return "Blur/Glow...";
   }
}
