package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Random;

public class BrushedMetalFilter implements BufferedImageOp {
   private int radius = 10;
   private float amount = 0.1F;
   private int color = -7829368;
   private float shine = 0.1F;
   private boolean monochrome = true;
   private Random randomNumbers;

   public BrushedMetalFilter() {
   }

   public BrushedMetalFilter(int color, int radius, float amount, boolean monochrome, float shine) {
      this.color = color;
      this.radius = radius;
      this.amount = amount;
      this.monochrome = monochrome;
      this.shine = shine;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = this.createCompatibleDestImage(src, (ColorModel)null);
      }

      int[] inPixels = new int[width];
      int[] outPixels = new int[width];
      this.randomNumbers = new Random(0L);
      int a = this.color & -16777216;
      int r = this.color >> 16 & 255;
      int g = this.color >> 8 & 255;
      int b = this.color & 255;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            int tr = r;
            int tg = g;
            int tb = b;
            if (this.shine != 0.0F) {
               int f = (int)((double)(255.0F * this.shine) * Math.sin((double)x / (double)width * Math.PI));
               tr = r + f;
               tg = g + f;
               tb = b + f;
            }

            if (this.monochrome) {
               int n = (int)(255.0F * (2.0F * this.randomNumbers.nextFloat() - 1.0F) * this.amount);
               inPixels[x] = a | clamp(tr + n) << 16 | clamp(tg + n) << 8 | clamp(tb + n);
            } else {
               inPixels[x] = a | this.random(tr) << 16 | this.random(tg) << 8 | this.random(tb);
            }
         }

         if (this.radius != 0) {
            this.blur(inPixels, outPixels, width, this.radius);
            this.setRGB(dst, 0, y, width, 1, outPixels);
         } else {
            this.setRGB(dst, 0, y, width, 1, inPixels);
         }
      }

      return dst;
   }

   private int random(int x) {
      x += (int)(255.0F * (2.0F * this.randomNumbers.nextFloat() - 1.0F) * this.amount);
      if (x < 0) {
         x = 0;
      } else if (x > 255) {
         x = 255;
      }

      return x;
   }

   private static int clamp(int c) {
      if (c < 0) {
         return 0;
      } else {
         return c > 255 ? 255 : c;
      }
   }

   private static int mod(int a, int b) {
      int n = a / b;
      a -= n * b;
      return a < 0 ? a + b : a;
   }

   public void blur(int[] in, int[] out, int width, int radius) {
      int widthMinus1 = width - 1;
      int r2 = 2 * radius + 1;
      int tr = 0;
      int tg = 0;
      int tb = 0;

      for(int i = -radius; i <= radius; ++i) {
         int rgb = in[mod(i, width)];
         tr += rgb >> 16 & 255;
         tg += rgb >> 8 & 255;
         tb += rgb & 255;
      }

      for(int x = 0; x < width; ++x) {
         out[x] = -16777216 | tr / r2 << 16 | tg / r2 << 8 | tb / r2;
         int i1 = x + radius + 1;
         if (i1 > widthMinus1) {
            i1 = mod(i1, width);
         }

         int i2 = x - radius;
         if (i2 < 0) {
            i2 = mod(i2, width);
         }

         int rgb1 = in[i1];
         int rgb2 = in[i2];
         tr += (rgb1 & 16711680) - (rgb2 & 16711680) >> 16;
         tg += (rgb1 & '\uff00') - (rgb2 & '\uff00') >> 8;
         tb += (rgb1 & 255) - (rgb2 & 255);
      }

   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

   public int getRadius() {
      return this.radius;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setColor(int color) {
      this.color = color;
   }

   public int getColor() {
      return this.color;
   }

   public void setMonochrome(boolean monochrome) {
      this.monochrome = monochrome;
   }

   public boolean getMonochrome() {
      return this.monochrome;
   }

   public void setShine(float shine) {
      this.shine = shine;
   }

   public float getShine() {
      return this.shine;
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

   public void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
      int type = image.getType();
      if (type != 2 && type != 1) {
         image.setRGB(x, y, width, height, pixels, 0, width);
      } else {
         image.getRaster().setDataElements(x, y, width, height, pixels);
      }

   }

   public String toString() {
      return "Texture/Brushed Metal...";
   }
}
