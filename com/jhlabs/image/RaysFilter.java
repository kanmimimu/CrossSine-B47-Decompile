package com.jhlabs.image;

import com.jhlabs.composite.MiscComposite;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class RaysFilter extends MotionBlurOp {
   private float opacity = 1.0F;
   private float threshold = 0.0F;
   private float strength = 0.5F;
   private boolean raysOnly = false;
   private Colormap colormap;

   public void setOpacity(float opacity) {
      this.opacity = opacity;
   }

   public float getOpacity() {
      return this.opacity;
   }

   public void setThreshold(float threshold) {
      this.threshold = threshold;
   }

   public float getThreshold() {
      return this.threshold;
   }

   public void setStrength(float strength) {
      this.strength = strength;
   }

   public float getStrength() {
      return this.strength;
   }

   public void setRaysOnly(boolean raysOnly) {
      this.raysOnly = raysOnly;
   }

   public boolean getRaysOnly() {
      return this.raysOnly;
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
      int[] srcPixels = new int[width];
      BufferedImage rays = new BufferedImage(width, height, 2);
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

         ((AbstractBufferedImageOp)this).setRGB(rays, 0, y, width, 1, pixels);
      }

      rays = super.filter(rays, (BufferedImage)null);

      for(int y = 0; y < height; ++y) {
         ((AbstractBufferedImageOp)this).getRGB(rays, 0, y, width, 1, pixels);
         ((AbstractBufferedImageOp)this).getRGB(src, 0, y, width, 1, srcPixels);

         for(int x = 0; x < width; ++x) {
            int rgb = pixels[x];
            int a = rgb & -16777216;
            int r = rgb >> 16 & 255;
            int g = rgb >> 8 & 255;
            int b = rgb & 255;
            if (this.colormap != null) {
               int l = r + g + b;
               rgb = this.colormap.getColor((float)l * this.strength * 0.33333334F);
            } else {
               r = PixelUtils.clamp((int)((float)r * this.strength));
               g = PixelUtils.clamp((int)((float)g * this.strength));
               b = PixelUtils.clamp((int)((float)b * this.strength));
               rgb = a | r << 16 | g << 8 | b;
            }

            pixels[x] = rgb;
         }

         ((AbstractBufferedImageOp)this).setRGB(rays, 0, y, width, 1, pixels);
      }

      if (dst == null) {
         dst = ((AbstractBufferedImageOp)this).createCompatibleDestImage(src, (ColorModel)null);
      }

      Graphics2D g = dst.createGraphics();
      if (!this.raysOnly) {
         g.setComposite(AlphaComposite.SrcOver);
         g.drawRenderedImage(src, (AffineTransform)null);
      }

      g.setComposite(MiscComposite.getInstance(1, this.opacity));
      g.drawRenderedImage(rays, (AffineTransform)null);
      ((Graphics)g).dispose();
      return dst;
   }

   public String toString() {
      return "Stylize/Rays...";
   }
}
