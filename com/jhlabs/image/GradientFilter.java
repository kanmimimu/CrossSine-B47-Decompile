package com.jhlabs.image;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class GradientFilter extends AbstractBufferedImageOp {
   public static final int LINEAR = 0;
   public static final int BILINEAR = 1;
   public static final int RADIAL = 2;
   public static final int CONICAL = 3;
   public static final int BICONICAL = 4;
   public static final int SQUARE = 5;
   public static final int INT_LINEAR = 0;
   public static final int INT_CIRCLE_UP = 1;
   public static final int INT_CIRCLE_DOWN = 2;
   public static final int INT_SMOOTH = 3;
   private float angle = 0.0F;
   private int color1 = -16777216;
   private int color2 = -1;
   private Point p1 = new Point(0, 0);
   private Point p2 = new Point(64, 64);
   private boolean repeat = false;
   private float x1;
   private float y1;
   private float dx;
   private float dy;
   private Colormap colormap = null;
   private int type;
   private int interpolation = 0;
   private int paintMode = 1;

   public GradientFilter() {
   }

   public GradientFilter(Point p1, Point p2, int color1, int color2, boolean repeat, int type, int interpolation) {
      this.p1 = p1;
      this.p2 = p2;
      this.color1 = color1;
      this.color2 = color2;
      this.repeat = repeat;
      this.type = type;
      this.interpolation = interpolation;
      this.colormap = new LinearColormap(color1, color2);
   }

   public void setPoint1(Point point1) {
      this.p1 = point1;
   }

   public Point getPoint1() {
      return this.p1;
   }

   public void setPoint2(Point point2) {
      this.p2 = point2;
   }

   public Point getPoint2() {
      return this.p2;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }

   public void setInterpolation(int interpolation) {
      this.interpolation = interpolation;
   }

   public int getInterpolation() {
      return this.interpolation;
   }

   public void setAngle(float angle) {
      this.angle = angle;
      this.p2 = new Point((int)((double)64.0F * Math.cos((double)angle)), (int)((double)64.0F * Math.sin((double)angle)));
   }

   public float getAngle() {
      return this.angle;
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   public void setPaintMode(int paintMode) {
      this.paintMode = paintMode;
   }

   public int getPaintMode() {
      return this.paintMode;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      float x1 = (float)this.p1.x;
      float x2 = (float)this.p2.x;
      float y1;
      float y2;
      if (x1 > x2 && this.type != 2) {
         y1 = x1;
         x1 = x2;
         x2 = y1;
         y1 = (float)this.p2.y;
         y2 = (float)this.p1.y;
         int rgb1 = this.color2;
         int rgb2 = this.color1;
      } else {
         y1 = (float)this.p1.y;
         y2 = (float)this.p2.y;
         int rgb1 = this.color1;
         int rgb2 = this.color2;
      }

      float dx = x2 - x1;
      float dy = y2 - y1;
      float lenSq = dx * dx + dy * dy;
      this.x1 = x1;
      this.y1 = y1;
      if (lenSq >= Float.MIN_VALUE) {
         dx /= lenSq;
         dy /= lenSq;
         if (this.repeat) {
            dx %= 1.0F;
            dy %= 1.0F;
         }
      }

      this.dx = dx;
      this.dy = dy;
      int[] pixels = new int[width];

      for(int y = 0; y < height; ++y) {
         ((AbstractBufferedImageOp)this).getRGB(src, 0, y, width, 1, pixels);
         switch (this.type) {
            case 0:
            case 1:
               this.linearGradient(pixels, y, width, 1);
               break;
            case 2:
               this.radialGradient(pixels, y, width, 1);
               break;
            case 3:
            case 4:
               this.conicalGradient(pixels, y, width, 1);
               break;
            case 5:
               this.squareGradient(pixels, y, width, 1);
         }

         ((AbstractBufferedImageOp)this).setRGB(dst, 0, y, width, 1, pixels);
      }

      return dst;
   }

   private void repeatGradient(int[] pixels, int w, int h, float rowrel, float dx, float dy) {
      int off = 0;

      for(int y = 0; y < h; ++y) {
         float colrel = rowrel;
         int j = w;

         while(true) {
            --j;
            if (j < 0) {
               rowrel += dy;
               break;
            }

            int rgb;
            if (this.type == 1) {
               rgb = this.colormap.getColor(this.map(ImageMath.triangle(colrel)));
            } else {
               rgb = this.colormap.getColor(this.map(ImageMath.mod(colrel, 1.0F)));
            }

            pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
            ++off;
            colrel += dx;
         }
      }

   }

   private void singleGradient(int[] pixels, int w, int h, float rowrel, float dx, float dy) {
      int off = 0;

      for(int y = 0; y < h; ++y) {
         float colrel = rowrel;
         int j = w;
         if ((double)rowrel <= (double)0.0F) {
            int rgb = this.colormap.getColor(0.0F);

            do {
               pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
               ++off;
               colrel += dx;
               --j;
            } while(j > 0 && (double)colrel <= (double)0.0F);
         }

         while((double)colrel < (double)1.0F) {
            --j;
            if (j < 0) {
               break;
            }

            int rgb;
            if (this.type == 1) {
               rgb = this.colormap.getColor(this.map(ImageMath.triangle(colrel)));
            } else {
               rgb = this.colormap.getColor(this.map(colrel));
            }

            pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
            ++off;
            colrel += dx;
         }

         if (j > 0) {
            int rgb;
            if (this.type == 1) {
               rgb = this.colormap.getColor(0.0F);
            } else {
               rgb = this.colormap.getColor(1.0F);
            }

            do {
               pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
               ++off;
               --j;
            } while(j > 0);
         }

         rowrel += dy;
      }

   }

   private void linearGradient(int[] pixels, int y, int w, int h) {
      int x = 0;
      float rowrel = ((float)x - this.x1) * this.dx + ((float)y - this.y1) * this.dy;
      if (this.repeat) {
         this.repeatGradient(pixels, w, h, rowrel, this.dx, this.dy);
      } else {
         this.singleGradient(pixels, w, h, rowrel, this.dx, this.dy);
      }

   }

   private void radialGradient(int[] pixels, int y, int w, int h) {
      int off = 0;
      float radius = this.distance((float)(this.p2.x - this.p1.x), (float)(this.p2.y - this.p1.y));

      for(int x = 0; x < w; ++x) {
         float distance = this.distance((float)(x - this.p1.x), (float)(y - this.p1.y));
         float ratio = distance / radius;
         if (this.repeat) {
            ratio %= 2.0F;
         } else if ((double)ratio > (double)1.0F) {
            ratio = 1.0F;
         }

         int rgb = this.colormap.getColor(this.map(ratio));
         pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
         ++off;
      }

   }

   private void squareGradient(int[] pixels, int y, int w, int h) {
      int off = 0;
      float radius = (float)Math.max(Math.abs(this.p2.x - this.p1.x), Math.abs(this.p2.y - this.p1.y));

      for(int x = 0; x < w; ++x) {
         float distance = (float)Math.max(Math.abs(x - this.p1.x), Math.abs(y - this.p1.y));
         float ratio = distance / radius;
         if (this.repeat) {
            ratio %= 2.0F;
         } else if ((double)ratio > (double)1.0F) {
            ratio = 1.0F;
         }

         int rgb = this.colormap.getColor(this.map(ratio));
         pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
         ++off;
      }

   }

   private void conicalGradient(int[] pixels, int y, int w, int h) {
      int off = 0;
      float angle0 = (float)Math.atan2((double)(this.p2.x - this.p1.x), (double)(this.p2.y - this.p1.y));

      for(int x = 0; x < w; ++x) {
         float angle = (float)(Math.atan2((double)(x - this.p1.x), (double)(y - this.p1.y)) - (double)angle0) / ((float)Math.PI * 2F);
         ++angle;
         angle %= 1.0F;
         if (this.type == 4) {
            angle = ImageMath.triangle(angle);
         }

         int rgb = this.colormap.getColor(this.map(angle));
         pixels[off] = PixelUtils.combinePixels(rgb, pixels[off], this.paintMode);
         ++off;
      }

   }

   private float map(float v) {
      if (this.repeat) {
         v = (double)v > (double)1.0F ? 2.0F - v : v;
      }

      switch (this.interpolation) {
         case 1:
            v = ImageMath.circleUp(ImageMath.clamp(v, 0.0F, 1.0F));
            break;
         case 2:
            v = ImageMath.circleDown(ImageMath.clamp(v, 0.0F, 1.0F));
            break;
         case 3:
            v = ImageMath.smoothStep(0.0F, 1.0F, v);
      }

      return v;
   }

   private float distance(float a, float b) {
      return (float)Math.sqrt((double)(a * a + b * b));
   }

   public String toString() {
      return "Other/Gradient Fill...";
   }
}
