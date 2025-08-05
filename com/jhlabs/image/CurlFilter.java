package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class CurlFilter extends TransformFilter {
   private float angle = 0.0F;
   private float transition = 0.0F;
   private float width;
   private float height;
   private float radius;

   public CurlFilter() {
      ((TransformFilter)this).setEdgeAction(0);
   }

   public void setTransition(float transition) {
      this.transition = transition;
   }

   public float getTransition() {
      return this.transition;
   }

   public void setAngle(float angle) {
      this.angle = angle;
   }

   public float getAngle() {
      return this.angle;
   }

   public void setRadius(float radius) {
      this.radius = radius;
   }

   public float getRadius() {
      return this.radius;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      this.width = (float)src.getWidth();
      this.height = (float)src.getHeight();
      int type = src.getType();
      super.originalSpace = new Rectangle(0, 0, width, height);
      super.transformedSpace = new Rectangle(0, 0, width, height);
      ((TransformFilter)this).transformSpace(super.transformedSpace);
      if (dst == null) {
         ColorModel dstCM = src.getColorModel();
         dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(super.transformedSpace.width, super.transformedSpace.height), dstCM.isAlphaPremultiplied(), (Hashtable)null);
      }

      WritableRaster dstRaster = dst.getRaster();
      int[] inPixels = ((AbstractBufferedImageOp)this).getRGB(src, 0, 0, width, height, (int[])null);
      if (super.interpolation == 0) {
         return ((TransformFilter)this).filterPixelsNN(dst, width, height, inPixels, super.transformedSpace);
      } else {
         int srcWidth = width;
         int srcHeight = height;
         int srcWidth1 = width - 1;
         int srcHeight1 = height - 1;
         int outWidth = super.transformedSpace.width;
         int outHeight = super.transformedSpace.height;
         int index = 0;
         int[] outPixels = new int[outWidth];
         int outX = super.transformedSpace.x;
         int outY = super.transformedSpace.y;
         float[] out = new float[4];

         for(int y = 0; y < outHeight; ++y) {
            for(int x = 0; x < outWidth; ++x) {
               this.transformInverse(outX + x, outY + y, out);
               int srcX = (int)Math.floor((double)out[0]);
               int srcY = (int)Math.floor((double)out[1]);
               float xWeight = out[0] - (float)srcX;
               float yWeight = out[1] - (float)srcY;
               int nw;
               int ne;
               int sw;
               int se;
               if (srcX >= 0 && srcX < srcWidth1 && srcY >= 0 && srcY < srcHeight1) {
                  int i = srcWidth * srcY + srcX;
                  nw = inPixels[i];
                  ne = inPixels[i + 1];
                  sw = inPixels[i + srcWidth];
                  se = inPixels[i + srcWidth + 1];
               } else {
                  nw = this.getPixel(inPixels, srcX, srcY, srcWidth, srcHeight);
                  ne = this.getPixel(inPixels, srcX + 1, srcY, srcWidth, srcHeight);
                  sw = this.getPixel(inPixels, srcX, srcY + 1, srcWidth, srcHeight);
                  se = this.getPixel(inPixels, srcX + 1, srcY + 1, srcWidth, srcHeight);
               }

               int rgb = ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
               int r = rgb >> 16 & 255;
               int g = rgb >> 8 & 255;
               int b = rgb & 255;
               float shade = out[2];
               r = (int)((float)r * shade);
               g = (int)((float)g * shade);
               b = (int)((float)b * shade);
               rgb = rgb & -16777216 | r << 16 | g << 8 | b;
               if (out[3] != 0.0F) {
                  outPixels[x] = PixelUtils.combinePixels(rgb, inPixels[srcWidth * y + x], 1);
               } else {
                  outPixels[x] = rgb;
               }
            }

            ((AbstractBufferedImageOp)this).setRGB(dst, 0, y, super.transformedSpace.width, 1, outPixels);
         }

         return dst;
      }
   }

   private final int getPixel(int[] pixels, int x, int y, int width, int height) {
      if (x >= 0 && x < width && y >= 0 && y < height) {
         return pixels[y * width + x];
      } else {
         switch (super.edgeAction) {
            case 0:
            default:
               return 0;
            case 1:
               return pixels[ImageMath.clamp(y, 0, height - 1) * width + ImageMath.clamp(x, 0, width - 1)];
            case 2:
               return pixels[ImageMath.mod(y, height) * width + ImageMath.mod(x, width)];
         }
      }
   }

   protected void transformInverse(int x, int y, float[] out) {
      float t = this.transition;
      float px = (float)x;
      float py = (float)y;
      float s = (float)Math.sin((double)this.angle);
      float c = (float)Math.cos((double)this.angle);
      float var10000 = t * this.width;
      float tx = t * (float)Math.sqrt((double)(this.width * this.width + this.height * this.height));
      float xoffset = c < 0.0F ? this.width : 0.0F;
      float yoffset = s < 0.0F ? this.height : 0.0F;
      px -= xoffset;
      py -= yoffset;
      float qx = px * c + py * s;
      float qy = -px * s + py * c;
      boolean outside = qx < tx;
      boolean unfolded = qx > tx * 2.0F;
      boolean oncurl = !outside && !unfolded;
      qx = qx > tx * 2.0F ? qx : 2.0F * tx - qx;
      px = qx * c - qy * s;
      py = qx * s + qy * c;
      px += xoffset;
      py += yoffset;
      boolean offpage = px < 0.0F || py < 0.0F || px >= this.width || py >= this.height;
      if (offpage && oncurl) {
         px = (float)x;
         py = (float)y;
      }

      float shade = !offpage && oncurl ? 1.9F * (1.0F - (float)Math.cos(Math.exp((double)((qx - tx) / this.radius)))) : 0.0F;
      out[2] = 1.0F - shade;
      if (outside) {
         out[0] = out[1] = -1.0F;
      } else {
         out[0] = px;
         out[1] = py;
      }

      out[3] = !offpage && oncurl ? 1.0F : 0.0F;
   }

   public String toString() {
      return "Distort/Curl...";
   }

   static class Sampler {
      private int edgeAction;
      private int width;
      private int height;
      private int[] inPixels;

      public Sampler(BufferedImage image) {
         int width = image.getWidth();
         int height = image.getHeight();
         int type = image.getType();
         this.inPixels = ImageUtils.getRGB(image, 0, 0, width, height, (int[])null);
      }

      public int sample(float x, float y) {
         int srcX = (int)Math.floor((double)x);
         int srcY = (int)Math.floor((double)y);
         float xWeight = x - (float)srcX;
         float yWeight = y - (float)srcY;
         int nw;
         int ne;
         int sw;
         int se;
         if (srcX >= 0 && srcX < this.width - 1 && srcY >= 0 && srcY < this.height - 1) {
            int i = this.width * srcY + srcX;
            nw = this.inPixels[i];
            ne = this.inPixels[i + 1];
            sw = this.inPixels[i + this.width];
            se = this.inPixels[i + this.width + 1];
         } else {
            nw = this.getPixel(this.inPixels, srcX, srcY, this.width, this.height);
            ne = this.getPixel(this.inPixels, srcX + 1, srcY, this.width, this.height);
            sw = this.getPixel(this.inPixels, srcX, srcY + 1, this.width, this.height);
            se = this.getPixel(this.inPixels, srcX + 1, srcY + 1, this.width, this.height);
         }

         return ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
      }

      private final int getPixel(int[] pixels, int x, int y, int width, int height) {
         if (x >= 0 && x < width && y >= 0 && y < height) {
            return pixels[y * width + x];
         } else {
            switch (this.edgeAction) {
               case 0:
               default:
                  return 0;
               case 1:
                  return pixels[ImageMath.clamp(y, 0, height - 1) * width + ImageMath.clamp(x, 0, width - 1)];
               case 2:
                  return pixels[ImageMath.mod(y, height) * width + ImageMath.mod(x, width)];
            }
         }
      }
   }
}
