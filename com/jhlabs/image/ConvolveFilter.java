package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;
import java.util.Hashtable;

public class ConvolveFilter extends AbstractBufferedImageOp {
   static final long serialVersionUID = 2239251672685254626L;
   public static int ZERO_EDGES = 0;
   public static int CLAMP_EDGES = 1;
   public static int WRAP_EDGES = 2;
   protected Kernel kernel;
   protected boolean alpha;
   private int edgeAction;

   public ConvolveFilter() {
      this(new float[9]);
   }

   public ConvolveFilter(float[] matrix) {
      this(new Kernel(3, 3, matrix));
   }

   public ConvolveFilter(int rows, int cols, float[] matrix) {
      this(new Kernel(cols, rows, matrix));
   }

   public ConvolveFilter(Kernel kernel) {
      this.kernel = null;
      this.alpha = true;
      this.edgeAction = CLAMP_EDGES;
      this.kernel = kernel;
   }

   public void setKernel(Kernel kernel) {
      this.kernel = kernel;
   }

   public Kernel getKernel() {
      return this.kernel;
   }

   public void setEdgeAction(int edgeAction) {
      this.edgeAction = edgeAction;
   }

   public int getEdgeAction() {
      return this.edgeAction;
   }

   public void setUseAlpha(boolean useAlpha) {
      this.alpha = useAlpha;
   }

   public boolean getUseAlpha() {
      return this.alpha;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = this.createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, inPixels);
      convolve(this.kernel, inPixels, outPixels, width, height, this.alpha, this.edgeAction);
      ((AbstractBufferedImageOp)this).setRGB(dst, 0, 0, width, height, outPixels);
      return dst;
   }

   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
      if (dstCM == null) {
         dstCM = src.getColorModel();
      }

      return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), (Hashtable)null);
   }

   public Rectangle2D getBounds2D(BufferedImage src) {
      return new Rectangle(0, 0, src.getWidth(), src.getHeight());
   }

   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
      if (dstPt == null) {
         dstPt = new Point2D.Double();
      }

      dstPt.setLocation(srcPt.getX(), srcPt.getY());
      return dstPt;
   }

   public RenderingHints getRenderingHints() {
      return null;
   }

   public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, int edgeAction) {
      convolve(kernel, inPixels, outPixels, width, height, true, edgeAction);
   }

   public static void convolve(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      if (kernel.getHeight() == 1) {
         convolveH(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      } else if (kernel.getWidth() == 1) {
         convolveV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      } else {
         convolveHV(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
      }

   }

   public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int rows = kernel.getHeight();
      int cols = kernel.getWidth();
      int rows2 = rows / 2;
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;

            for(int row = -rows2; row <= rows2; ++row) {
               int iy = y + row;
               int ioffset;
               if (0 <= iy && iy < height) {
                  ioffset = iy * width;
               } else if (edgeAction == CLAMP_EDGES) {
                  ioffset = y * width;
               } else {
                  if (edgeAction != WRAP_EDGES) {
                     continue;
                  }

                  ioffset = (iy + height) % height * width;
               }

               int moffset = cols * (row + rows2) + cols2;

               for(int col = -cols2; col <= cols2; ++col) {
                  float f = matrix[moffset + col];
                  if (f != 0.0F) {
                     int ix = x + col;
                     if (0 > ix || ix >= width) {
                        if (edgeAction == CLAMP_EDGES) {
                           ix = x;
                        } else {
                           if (edgeAction != WRAP_EDGES) {
                              continue;
                           }

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
            }

            int ia = alpha ? PixelUtils.clamp((int)((double)a + (double)0.5F)) : 255;
            int ir = PixelUtils.clamp((int)((double)r + (double)0.5F));
            int ig = PixelUtils.clamp((int)((double)g + (double)0.5F));
            int ib = PixelUtils.clamp((int)((double)b + (double)0.5F));
            outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
         }
      }

   }

   public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int cols = kernel.getWidth();
      int cols2 = cols / 2;

      for(int y = 0; y < height; ++y) {
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
                     if (edgeAction == CLAMP_EDGES) {
                        ix = 0;
                     } else if (edgeAction == WRAP_EDGES) {
                        ix = (x + width) % width;
                     }
                  } else if (ix >= width) {
                     if (edgeAction == CLAMP_EDGES) {
                        ix = width - 1;
                     } else if (edgeAction == WRAP_EDGES) {
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
            outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
         }
      }

   }

   public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
      int index = 0;
      float[] matrix = kernel.getKernelData((float[])null);
      int rows = kernel.getHeight();
      int rows2 = rows / 2;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float a = 0.0F;

            for(int row = -rows2; row <= rows2; ++row) {
               int iy = y + row;
               int ioffset;
               if (iy < 0) {
                  if (edgeAction == CLAMP_EDGES) {
                     ioffset = 0;
                  } else if (edgeAction == WRAP_EDGES) {
                     ioffset = (y + height) % height * width;
                  } else {
                     ioffset = iy * width;
                  }
               } else if (iy >= height) {
                  if (edgeAction == CLAMP_EDGES) {
                     ioffset = (height - 1) * width;
                  } else if (edgeAction == WRAP_EDGES) {
                     ioffset = (y + height) % height * width;
                  } else {
                     ioffset = iy * width;
                  }
               } else {
                  ioffset = iy * width;
               }

               float f = matrix[row + rows2];
               if (f != 0.0F) {
                  int rgb = inPixels[ioffset + x];
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
            outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
         }
      }

   }

   public String toString() {
      return "Blur/Convolve...";
   }
}
