package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Hashtable;

public class VariableBlurFilter extends AbstractBufferedImageOp {
   private int hRadius = 1;
   private int vRadius = 1;
   private int iterations = 1;
   private BufferedImage blurMask;

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = new BufferedImage(width, height, 2);
      }

      int[] inPixels = new int[width * height];
      int[] outPixels = new int[width * height];
      ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, inPixels);

      for(int i = 0; i < this.iterations; ++i) {
         this.blur(inPixels, outPixels, width, height, this.hRadius, 1);
         this.blur(outPixels, inPixels, height, width, this.vRadius, 2);
      }

      ((AbstractBufferedImageOp)this).setRGB(dst, 0, 0, width, height, inPixels);
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

   public void blur(int[] in, int[] out, int width, int height, int radius, int pass) {
      int widthMinus1 = width - 1;
      int[] r = new int[width];
      int[] g = new int[width];
      int[] b = new int[width];
      int[] a = new int[width];
      int[] mask = new int[width];
      int inIndex = 0;

      for(int y = 0; y < height; ++y) {
         int outIndex = y;
         if (this.blurMask != null) {
            if (pass == 1) {
               ((AbstractBufferedImageOp)this).getRGB(this.blurMask, 0, y, width, 1, mask);
            } else {
               ((AbstractBufferedImageOp)this).getRGB(this.blurMask, y, 0, 1, width, mask);
            }
         }

         for(int x = 0; x < width; ++x) {
            int argb = in[inIndex + x];
            a[x] = argb >> 24 & 255;
            r[x] = argb >> 16 & 255;
            g[x] = argb >> 8 & 255;
            b[x] = argb & 255;
            if (x != 0) {
               a[x] += a[x - 1];
               r[x] += r[x - 1];
               g[x] += g[x - 1];
               b[x] += b[x - 1];
            }
         }

         for(int x = 0; x < width; ++x) {
            int ra;
            if (this.blurMask != null) {
               if (pass == 1) {
                  ra = (int)((float)((mask[x] & 255) * this.hRadius) / 255.0F);
               } else {
                  ra = (int)((float)((mask[x] & 255) * this.vRadius) / 255.0F);
               }
            } else if (pass == 1) {
               ra = (int)(this.blurRadiusAt(x, y, width, height) * (float)this.hRadius);
            } else {
               ra = (int)(this.blurRadiusAt(y, x, height, width) * (float)this.vRadius);
            }

            int divisor = 2 * ra + 1;
            int ta = 0;
            int tr = 0;
            int tg = 0;
            int tb = 0;
            int i1 = x + ra;
            if (i1 > widthMinus1) {
               int f = i1 - widthMinus1;
               ta += (a[widthMinus1] - a[widthMinus1 - 1]) * f;
               tr += (r[widthMinus1] - r[widthMinus1 - 1]) * f;
               tg += (g[widthMinus1] - g[widthMinus1 - 1]) * f;
               tb += (b[widthMinus1] - b[widthMinus1 - 1]) * f;
               i1 = widthMinus1;
            }

            int i2 = x - ra - 1;
            if (i2 < 0) {
               ta -= a[0] * i2;
               tr -= r[0] * i2;
               tg -= g[0] * i2;
               tb -= b[0] * i2;
               i2 = 0;
            }

            ta += a[i1] - a[i2];
            tr += r[i1] - r[i2];
            tg += g[i1] - g[i2];
            tb += b[i1] - b[i2];
            out[outIndex] = ta / divisor << 24 | tr / divisor << 16 | tg / divisor << 8 | tb / divisor;
            outIndex += height;
         }

         inIndex += width;
      }

   }

   protected float blurRadiusAt(int x, int y, int width, int height) {
      return (float)x / (float)width;
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

   public void setBlurMask(BufferedImage blurMask) {
      this.blurMask = blurMask;
   }

   public BufferedImage getBlurMask() {
      return this.blurMask;
   }

   public String toString() {
      return "Blur/Variable Blur...";
   }
}
