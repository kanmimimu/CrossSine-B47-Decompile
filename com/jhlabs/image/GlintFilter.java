package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class GlintFilter extends AbstractBufferedImageOp {
   private float threshold = 1.0F;
   private int length = 5;
   private float blur = 0.0F;
   private float amount = 0.1F;
   private boolean glintOnly = false;
   private Colormap colormap = new LinearColormap(-1, -16777216);

   public void setThreshold(float threshold) {
      this.threshold = threshold;
   }

   public float getThreshold() {
      return this.threshold;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setLength(int length) {
      this.length = length;
   }

   public int getLength() {
      return this.length;
   }

   public void setBlur(float blur) {
      this.blur = blur;
   }

   public float getBlur() {
      return this.blur;
   }

   public void setGlintOnly(boolean glintOnly) {
      this.glintOnly = glintOnly;
   }

   public boolean getGlintOnly() {
      return this.glintOnly;
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      int[] pixels = new int[width];
      int length2 = (int)((float)this.length / 1.414F);
      int[] colors = new int[this.length + 1];
      int[] colors2 = new int[length2 + 1];
      if (this.colormap != null) {
         for(int i = 0; i <= this.length; ++i) {
            int argb = this.colormap.getColor((float)i / (float)this.length);
            int r = argb >> 16 & 255;
            int g = argb >> 8 & 255;
            int b = argb & 255;
            argb = argb & -16777216 | (int)(this.amount * (float)r) << 16 | (int)(this.amount * (float)g) << 8 | (int)(this.amount * (float)b);
            colors[i] = argb;
         }

         for(int i = 0; i <= length2; ++i) {
            int argb = this.colormap.getColor((float)i / (float)length2);
            int r = argb >> 16 & 255;
            int g = argb >> 8 & 255;
            int b = argb & 255;
            argb = argb & -16777216 | (int)(this.amount * (float)r) << 16 | (int)(this.amount * (float)g) << 8 | (int)(this.amount * (float)b);
            colors2[i] = argb;
         }
      }

      BufferedImage mask = new BufferedImage(width, height, 2);
      int threshold3 = (int)(this.threshold * 3.0F * 255.0F);

      for(int y = 0; y < height; ++y) {
         ((AbstractBufferedImageOp)this).getRGB(src, 0, y, width, 1, pixels);

         for(int x = 0; x < width; ++x) {
            int rgb = pixels[x];
            int a = rgb & -16777216;
            int r = rgb >> 16 & 255;
            int g = rgb >> 8 & 255;
            int b = rgb & 255;
            int l = r + g + b;
            if (l < threshold3) {
               pixels[x] = -16777216;
            } else {
               l /= 3;
               pixels[x] = a | l << 16 | l << 8 | l;
            }
         }

         ((AbstractBufferedImageOp)this).setRGB(mask, 0, y, width, 1, pixels);
      }

      if (this.blur != 0.0F) {
         mask = (new GaussianFilter(this.blur)).filter(mask, (BufferedImage)null);
      }

      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] dstPixels;
      if (this.glintOnly) {
         dstPixels = new int[width * height];
      } else {
         dstPixels = ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, (int[])null);
      }

      for(int y = 0; y < height; ++y) {
         int index = y * width;
         ((AbstractBufferedImageOp)this).getRGB(mask, 0, y, width, 1, pixels);
         int ymin = Math.max(y - this.length, 0) - y;
         int ymax = Math.min(y + this.length, height - 1) - y;
         int ymin2 = Math.max(y - length2, 0) - y;
         int ymax2 = Math.min(y + length2, height - 1) - y;

         for(int x = 0; x < width; ++x) {
            if ((float)(pixels[x] & 255) > this.threshold * 255.0F) {
               int xmin = Math.max(x - this.length, 0) - x;
               int xmax = Math.min(x + this.length, width - 1) - x;
               int xmin2 = Math.max(x - length2, 0) - x;
               int xmax2 = Math.min(x + length2, width - 1) - x;
               int i = 0;

               for(int k = 0; i <= xmax; ++k) {
                  dstPixels[index + i] = PixelUtils.combinePixels(dstPixels[index + i], colors[k], 4);
                  ++i;
               }

               i = -1;

               for(int k = 1; i >= xmin; ++k) {
                  dstPixels[index + i] = PixelUtils.combinePixels(dstPixels[index + i], colors[k], 4);
                  --i;
               }

               i = 1;
               int j = index + width;

               for(int k = 0; i <= ymax; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors[k], 4);
                  ++i;
                  j += width;
               }

               i = -1;
               j = index - width;

               for(int k = 0; i >= ymin; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors[k], 4);
                  --i;
                  j -= width;
               }

               Math.max(xmin2, ymin2);
               Math.min(xmax2, ymax2);
               int count = Math.min(xmax2, ymax2);
               int i = 1;
               int j = index + width + 1;

               for(int k = 0; i <= count; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors2[k], 4);
                  ++i;
                  j += width + 1;
               }

               count = Math.min(-xmin2, -ymin2);
               i = 1;
               j = index - width - 1;

               for(int k = 0; i <= count; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors2[k], 4);
                  ++i;
                  j -= width + 1;
               }

               count = Math.min(xmax2, -ymin2);
               i = 1;
               j = index - width + 1;

               for(int k = 0; i <= count; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors2[k], 4);
                  ++i;
                  j += -width + 1;
               }

               count = Math.min(-xmin2, ymax2);
               i = 1;
               j = index + width - 1;

               for(int k = 0; i <= count; ++k) {
                  dstPixels[j] = PixelUtils.combinePixels(dstPixels[j], colors2[k], 4);
                  ++i;
                  j += width - 1;
               }
            }

            ++index;
         }
      }

      ((AbstractBufferedImageOp)this).setRGB(dst, 0, 0, width, height, dstPixels);
      return dst;
   }

   public String toString() {
      return "Effects/Glint...";
   }
}
