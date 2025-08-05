package com.jhlabs.image;

import com.jhlabs.math.FFT;
import java.awt.image.BufferedImage;

public class LensBlurFilter extends AbstractBufferedImageOp {
   private float radius = 10.0F;
   private float bloom = 2.0F;
   private float bloomThreshold = 192.0F;
   private float angle = 0.0F;
   private int sides = 5;

   public void setRadius(float radius) {
      this.radius = radius;
   }

   public float getRadius() {
      return this.radius;
   }

   public void setSides(int sides) {
      this.sides = sides;
   }

   public int getSides() {
      return this.sides;
   }

   public void setBloom(float bloom) {
      this.bloom = bloom;
   }

   public float getBloom() {
      return this.bloom;
   }

   public void setBloomThreshold(float bloomThreshold) {
      this.bloomThreshold = bloomThreshold;
   }

   public float getBloomThreshold() {
      return this.bloomThreshold;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      int rows = 1;
      int cols = 1;
      int log2rows = 0;
      int log2cols = 0;
      int iradius = (int)Math.ceil((double)this.radius);
      int tileWidth = 128;
      int var10000 = width + iradius * 2;
      var10000 = height + iradius * 2;
      tileWidth = iradius < 32 ? Math.min(128, width + 2 * iradius) : Math.min(256, width + 2 * iradius);
      int tileHeight = iradius < 32 ? Math.min(128, height + 2 * iradius) : Math.min(256, height + 2 * iradius);
      if (dst == null) {
         dst = new BufferedImage(width, height, 2);
      }

      while(rows < tileHeight) {
         rows *= 2;
         ++log2rows;
      }

      while(cols < tileWidth) {
         cols *= 2;
         ++log2cols;
      }

      int w = cols;
      int h = rows;
      tileWidth = cols;
      tileHeight = rows;
      FFT fft = new FFT(Math.max(log2rows, log2cols));
      int[] rgb = new int[cols * rows];
      float[][] mask = new float[2][cols * rows];
      float[][] gb = new float[2][cols * rows];
      float[][] ar = new float[2][cols * rows];
      double polyAngle = Math.PI / (double)this.sides;
      double polyScale = (double)1.0F / Math.cos(polyAngle);
      double r2 = (double)(this.radius * this.radius);
      double rangle = Math.toRadians((double)this.angle);
      float total = 0.0F;
      int i = 0;

      for(int y = 0; y < h; ++y) {
         for(int x = 0; x < w; ++x) {
            double dx = (double)((float)x - (float)w / 2.0F);
            double dy = (double)((float)y - (float)h / 2.0F);
            double r = dx * dx + dy * dy;
            double f = r < r2 ? (double)1.0F : (double)0.0F;
            if (f != (double)0.0F) {
               r = Math.sqrt(r);
               if (this.sides != 0) {
                  double a = Math.atan2(dy, dx) + rangle;
                  a = ImageMath.mod(a, polyAngle * (double)2.0F) - polyAngle;
                  f = Math.cos(a) * polyScale;
               } else {
                  f = (double)1.0F;
               }

               f = f * r < (double)this.radius ? (double)1.0F : (double)0.0F;
            }

            total += (float)f;
            mask[0][i] = (float)f;
            mask[1][i] = 0.0F;
            ++i;
         }
      }

      i = 0;

      for(int y = 0; y < h; ++y) {
         for(int x = 0; x < w; ++x) {
            mask[0][i] /= total;
            ++i;
         }
      }

      fft.transform2D(mask[0], mask[1], w, h, true);

      for(int tileY = -iradius; tileY < height; tileY += tileHeight - 2 * iradius) {
         for(int tileX = -iradius; tileX < width; tileX += tileWidth - 2 * iradius) {
            int tx = tileX;
            int ty = tileY;
            int tw = tileWidth;
            int th = tileHeight;
            int fx = 0;
            int fy = 0;
            if (tileX < 0) {
               tw = tileWidth + tileX;
               fx -= tileX;
               tx = 0;
            }

            if (tileY < 0) {
               th = tileHeight + tileY;
               fy -= tileY;
               ty = 0;
            }

            if (tx + tw > width) {
               tw = width - tx;
            }

            if (ty + th > height) {
               th = height - ty;
            }

            src.getRGB(tx, ty, tw, th, rgb, fy * w + fx, w);
            i = 0;

            for(int y = 0; y < h; ++y) {
               int imageY = y + tileY;
               int j;
               if (imageY < 0) {
                  j = fy;
               } else if (imageY > height) {
                  j = fy + th - 1;
               } else {
                  j = y;
               }

               j *= w;

               for(int x = 0; x < w; ++x) {
                  int imageX = x + tileX;
                  int k;
                  if (imageX < 0) {
                     k = fx;
                  } else if (imageX > width) {
                     k = fx + tw - 1;
                  } else {
                     k = x;
                  }

                  k += j;
                  ar[0][i] = (float)(rgb[k] >> 24 & 255);
                  float r = (float)(rgb[k] >> 16 & 255);
                  float g = (float)(rgb[k] >> 8 & 255);
                  float b = (float)(rgb[k] & 255);
                  if (r > this.bloomThreshold) {
                     r *= this.bloom;
                  }

                  if (g > this.bloomThreshold) {
                     g *= this.bloom;
                  }

                  if (b > this.bloomThreshold) {
                     b *= this.bloom;
                  }

                  ar[1][i] = r;
                  gb[0][i] = g;
                  gb[1][i] = b;
                  ++i;
                  ++k;
               }
            }

            fft.transform2D(ar[0], ar[1], cols, rows, true);
            fft.transform2D(gb[0], gb[1], cols, rows, true);
            i = 0;

            for(int y = 0; y < h; ++y) {
               for(int x = 0; x < w; ++x) {
                  float re = ar[0][i];
                  float im = ar[1][i];
                  float rem = mask[0][i];
                  float imm = mask[1][i];
                  ar[0][i] = re * rem - im * imm;
                  ar[1][i] = re * imm + im * rem;
                  re = gb[0][i];
                  im = gb[1][i];
                  gb[0][i] = re * rem - im * imm;
                  gb[1][i] = re * imm + im * rem;
                  ++i;
               }
            }

            fft.transform2D(ar[0], ar[1], cols, rows, false);
            fft.transform2D(gb[0], gb[1], cols, rows, false);
            int row_flip = w >> 1;
            int col_flip = h >> 1;
            int index = 0;

            for(int y = 0; y < w; ++y) {
               int ym = y ^ row_flip;
               int yi = ym * cols;

               for(int x = 0; x < w; ++x) {
                  int xm = yi + (x ^ col_flip);
                  int a = (int)ar[0][xm];
                  int r = (int)ar[1][xm];
                  int g = (int)gb[0][xm];
                  int b = (int)gb[1][xm];
                  if (r > 255) {
                     r = 255;
                  }

                  if (g > 255) {
                     g = 255;
                  }

                  if (b > 255) {
                     b = 255;
                  }

                  int argb = a << 24 | r << 16 | g << 8 | b;
                  rgb[index++] = argb;
               }
            }

            tx = tileX + iradius;
            ty = tileY + iradius;
            tw = tileWidth - 2 * iradius;
            th = tileHeight - 2 * iradius;
            if (tx + tw > width) {
               tw = width - tx;
            }

            if (ty + th > height) {
               th = height - ty;
            }

            dst.setRGB(tx, ty, tw, th, rgb, iradius * w + iradius, w);
         }
      }

      return dst;
   }

   public String toString() {
      return "Blur/Lens Blur...";
   }
}
