package com.jhlabs.image;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class SmearFilter extends WholeImageFilter implements Serializable {
   static final long serialVersionUID = 6491871753122667752L;
   public static final int CROSSES = 0;
   public static final int LINES = 1;
   public static final int CIRCLES = 2;
   public static final int SQUARES = 3;
   private Colormap colormap = new LinearColormap();
   private float angle = 0.0F;
   private float density = 0.5F;
   private float scatter = 0.0F;
   private int distance = 8;
   private Random randomGenerator = new Random();
   private long seed = 567L;
   private int shape = 1;
   private float mix = 0.5F;
   private int fadeout = 0;
   private boolean background = false;

   public void setShape(int shape) {
      this.shape = shape;
   }

   public int getShape() {
      return this.shape;
   }

   public void setDistance(int distance) {
      this.distance = distance;
   }

   public int getDistance() {
      return this.distance;
   }

   public void setDensity(float density) {
      this.density = density;
   }

   public float getDensity() {
      return this.density;
   }

   public void setScatter(float scatter) {
      this.scatter = scatter;
   }

   public float getScatter() {
      return this.scatter;
   }

   public void setAngle(float angle) {
      this.angle = angle;
   }

   public float getAngle() {
      return this.angle;
   }

   public void setMix(float mix) {
      this.mix = mix;
   }

   public float getMix() {
      return this.mix;
   }

   public void setFadeout(int fadeout) {
      this.fadeout = fadeout;
   }

   public int getFadeout() {
      return this.fadeout;
   }

   public void setBackground(boolean background) {
      this.background = background;
   }

   public boolean getBackground() {
      return this.background;
   }

   public void randomize() {
      this.seed = (new Date()).getTime();
   }

   private float random(float low, float high) {
      return low + (high - low) * this.randomGenerator.nextFloat();
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int[] outPixels = new int[width * height];
      this.randomGenerator.setSeed(this.seed);
      float sinAngle = (float)Math.sin((double)this.angle);
      float cosAngle = (float)Math.cos((double)this.angle);
      int i = 0;

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            outPixels[i] = this.background ? -1 : inPixels[i];
            ++i;
         }
      }

      switch (this.shape) {
         case 0:
            int numShapes = (int)(2.0F * this.density * (float)width * (float)height / (float)(this.distance + 1));

            for(int var30 = 0; var30 < numShapes; ++var30) {
               int x = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % width;
               int y = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % height;
               int length = this.randomGenerator.nextInt() % this.distance + 1;
               int rgb = inPixels[y * width + x];

               for(int x1 = x - length; x1 < x + length + 1; ++x1) {
                  if (x1 >= 0 && x1 < width) {
                     int rgb2 = this.background ? -1 : outPixels[y * width + x1];
                     outPixels[y * width + x1] = ImageMath.mixColors(this.mix, rgb2, rgb);
                  }
               }

               for(int y1 = y - length; y1 < y + length + 1; ++y1) {
                  if (y1 >= 0 && y1 < height) {
                     int rgb2 = this.background ? -1 : outPixels[y1 * width + x];
                     outPixels[y1 * width + x] = ImageMath.mixColors(this.mix, rgb2, rgb);
                  }
               }
            }
            break;
         case 1:
            int var31 = (int)(2.0F * this.density * (float)width * (float)height / 2.0F);

            for(int var29 = 0; var29 < var31; ++var29) {
               int sx = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % width;
               int sy = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % height;
               int rgb = inPixels[sy * width + sx];
               int length = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % this.distance;
               int dx = (int)((float)length * cosAngle);
               int dy = (int)((float)length * sinAngle);
               int x0 = sx - dx;
               int y0 = sy - dy;
               int x1 = sx + dx;
               int y1 = sy + dy;
               int ddx;
               if (x1 < x0) {
                  ddx = -1;
               } else {
                  ddx = 1;
               }

               int ddy;
               if (y1 < y0) {
                  ddy = -1;
               } else {
                  ddy = 1;
               }

               dx = x1 - x0;
               dy = y1 - y0;
               dx = Math.abs(dx);
               dy = Math.abs(dy);
               int x = x0;
               int y = y0;
               if (x0 < width && x0 >= 0 && y0 < height && y0 >= 0) {
                  int rgb2 = this.background ? -1 : outPixels[y0 * width + x0];
                  outPixels[y0 * width + x0] = ImageMath.mixColors(this.mix, rgb2, rgb);
               }

               if (Math.abs(dx) > Math.abs(dy)) {
                  int d = 2 * dy - dx;
                  int incrE = 2 * dy;
                  int incrNE = 2 * (dy - dx);

                  while(x != x1) {
                     if (d <= 0) {
                        d += incrE;
                     } else {
                        d += incrNE;
                        y += ddy;
                     }

                     x += ddx;
                     if (x < width && x >= 0 && y < height && y >= 0) {
                        int rgb2 = this.background ? -1 : outPixels[y * width + x];
                        outPixels[y * width + x] = ImageMath.mixColors(this.mix, rgb2, rgb);
                     }
                  }
               } else {
                  int d = 2 * dx - dy;
                  int incrE = 2 * dx;
                  int incrNE = 2 * (dx - dy);

                  while(y != y1) {
                     if (d <= 0) {
                        d += incrE;
                     } else {
                        d += incrNE;
                        x += ddx;
                     }

                     y += ddy;
                     if (x < width && x >= 0 && y < height && y >= 0) {
                        int rgb2 = this.background ? -1 : outPixels[y * width + x];
                        outPixels[y * width + x] = ImageMath.mixColors(this.mix, rgb2, rgb);
                     }
                  }
               }
            }
            break;
         case 2:
         case 3:
            int radius = this.distance + 1;
            int radius2 = radius * radius;
            int numShapes = (int)(2.0F * this.density * (float)width * (float)height / (float)radius);

            for(int var28 = 0; var28 < numShapes; ++var28) {
               int sx = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % width;
               int sy = (this.randomGenerator.nextInt() & Integer.MAX_VALUE) % height;
               int rgb = inPixels[sy * width + sx];

               for(int x = sx - radius; x < sx + radius + 1; ++x) {
                  for(int y = sy - radius; y < sy + radius + 1; ++y) {
                     int f;
                     if (this.shape == 2) {
                        f = (x - sx) * (x - sx) + (y - sy) * (y - sy);
                     } else {
                        f = 0;
                     }

                     if (x >= 0 && x < width && y >= 0 && y < height && f <= radius2) {
                        int rgb2 = this.background ? -1 : outPixels[y * width + x];
                        outPixels[y * width + x] = ImageMath.mixColors(this.mix, rgb2, rgb);
                     }
                  }
               }
            }
      }

      return outPixels;
   }

   public String toString() {
      return "Effects/Smear...";
   }
}
