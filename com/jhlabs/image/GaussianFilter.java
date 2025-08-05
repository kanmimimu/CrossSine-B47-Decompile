package com.jhlabs.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;

public class GaussianFilter extends ConvolveFilter {
   protected float radius;
   protected Kernel kernel;

   public GaussianFilter() {
      this(2.0F);
   }

   public GaussianFilter(float radius) {
      this.setRadius(radius);
   }

   public void setRadius(float radius) {
      this.radius = radius;
      this.kernel = makeKernel(radius);
   }

   public float getRadius() {
      return this.radius;
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
      if (this.radius > 0.0F) {
         convolveAndTranspose(this.kernel, inPixels, outPixels, width, height, super.alpha, ConvolveFilter.CLAMP_EDGES);
         convolveAndTranspose(this.kernel, outPixels, inPixels, height, width, super.alpha, ConvolveFilter.CLAMP_EDGES);
      }

      dst.setRGB(0, 0, width, height, inPixels, 0, width);
      return dst;
   }

   public static void convolveAndTranspose(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      float[] matrix = kernel.getKernelData((float[])null);
      int cols = kernel.getWidth();
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
         int index = y;
         int ioffset = y * width;

         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;
            int moffset = cols2;

            for(int col = -cols2; col <= cols2; ++col) {
               float f = matrix[moffset + col];
               if (f != 0.0F) {
                  int ix = x + col;
                  if (ix < 0) {
                     if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                        ix = 0;
                     } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                        ix = (x + width) % width;
                     }
                  } else if (ix >= width) {
                     if (edgeAction == ConvolveFilter.CLAMP_EDGES) {
                        ix = width - 1;
                     } else if (edgeAction == ConvolveFilter.WRAP_EDGES) {
                        ix = (x + width) % width;
                     }
                  }

                  int rgb = inPixels[ioffset + ix];
                  a += f * (float)(rgb >> 24 & 255);
                  r += f * (float)(rgb >> 16 & 255);
                  g += f * (float)(rgb >> 8 & 255);
                  b += f * (float)(rgb & 255);
               }
            }

            int ia = alpha ? PixelUtils.clamp((int)((double)a + (double)0.5F)) : 255;
            int ir = PixelUtils.clamp((int)((double)r + (double)0.5F));
            int ig = PixelUtils.clamp((int)((double)g + (double)0.5F));
            int ib = PixelUtils.clamp((int)((double)b + (double)0.5F));
            outPixels[index] = ia << 24 | ir << 16 | ig << 8 | ib;
            index += height;
         }
      }

   }

   public static Kernel makeKernel(float radius) {
      int r = (int)Math.ceil((double)radius);
      int rows = r * 2 + 1;
      float[] matrix = new float[rows];
      float sigma = radius / 3.0F;
      float sigma22 = 2.0F * sigma * sigma;
      float sigmaPi2 = ((float)Math.PI * 2F) * sigma;
      float sqrtSigmaPi2 = (float)Math.sqrt((double)sigmaPi2);
      float radius2 = radius * radius;
      float total = 0.0F;
      int index = 0;

      for(int row = -r; row <= r; ++row) {
         float distance = (float)(row * row);
         if (distance > radius2) {
            matrix[index] = 0.0F;
         } else {
            matrix[index] = (float)Math.exp((double)(-distance / sigma22)) / sqrtSigmaPi2;
         }

         total += matrix[index];
         ++index;
      }

      for(int i = 0; i < rows; ++i) {
         matrix[i] /= total;
      }

      return new Kernel(rows, 1, matrix);
   }

   public String toString() {
      return "Blur/Gaussian Blur...";
   }
}
