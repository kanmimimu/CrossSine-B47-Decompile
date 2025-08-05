package com.jhlabs.image;

import com.jhlabs.math.Noise;
import java.io.Serializable;

public class MarbleTexFilter extends PointFilter implements Serializable {
   private float scale = 32.0F;
   private float stretch = 1.0F;
   private float angle = 0.0F;
   private float turbulence = 1.0F;
   private float turbulenceFactor = 0.5F;
   private Colormap colormap;
   private float m00 = 1.0F;
   private float m01 = 0.0F;
   private float m10 = 0.0F;
   private float m11 = 1.0F;

   public void setScale(float scale) {
      this.scale = scale;
   }

   public float getScale() {
      return this.scale;
   }

   public void setStretch(float stretch) {
      this.stretch = stretch;
   }

   public float getStretch() {
      return this.stretch;
   }

   public void setAngle(float angle) {
      this.angle = angle;
      float cos = (float)Math.cos((double)angle);
      float sin = (float)Math.sin((double)angle);
      this.m00 = cos;
      this.m01 = sin;
      this.m10 = -sin;
      this.m11 = cos;
   }

   public float getAngle() {
      return this.angle;
   }

   public void setTurbulence(float turbulence) {
      this.turbulence = turbulence;
   }

   public float getTurbulence() {
      return this.turbulence;
   }

   public void setTurbulenceFactor(float turbulenceFactor) {
      this.turbulenceFactor = turbulenceFactor;
   }

   public float getTurbulenceFactor() {
      return this.turbulenceFactor;
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   public int filterRGB(int x, int y, int rgb) {
      float nx = this.m00 * (float)x + this.m01 * (float)y;
      float ny = this.m10 * (float)x + this.m11 * (float)y;
      nx /= this.scale * this.stretch;
      ny /= this.scale;
      int a = rgb & -16777216;
      if (this.colormap != null) {
         float chaos = this.turbulenceFactor * Noise.turbulence2(nx, ny, this.turbulence);
         float f = 3.0F * this.turbulenceFactor * chaos + ny;
         f = (float)Math.sin((double)f * Math.PI);
         float perturb = (float)Math.sin((double)40.0F * (double)chaos);
         f = (float)((double)f + 0.2 * (double)perturb);
         return this.colormap.getColor(f);
      } else {
         float chaos = this.turbulenceFactor * Noise.turbulence2(nx, ny, this.turbulence);
         float t = (float)Math.sin(Math.sin((double)8.0F * (double)chaos + (double)(7.0F * nx) + (double)3.0F * (double)ny));
         float brownLayer;
         float greenLayer = brownLayer = Math.abs(t);
         float perturb = (float)Math.sin((double)40.0F * (double)chaos);
         perturb = Math.abs(perturb);
         float brownPerturb = 0.6F * perturb + 0.3F;
         float greenPerturb = 0.2F * perturb + 0.8F;
         float grnPerturb = 0.15F * perturb + 0.85F;
         float grn = 0.5F * (float)Math.pow((double)Math.abs(brownLayer), 0.3);
         brownLayer = (float)Math.pow((double)0.5F * ((double)brownLayer + (double)1.0F), 0.6) * brownPerturb;
         greenLayer = (float)Math.pow((double)0.5F * ((double)greenLayer + (double)1.0F), 0.6) * greenPerturb;
         float red = (0.5F * brownLayer + 0.35F * greenLayer) * 2.0F * grn;
         float blu = (0.25F * brownLayer + 0.35F * greenLayer) * 2.0F * grn;
         grn *= Math.max(brownLayer, greenLayer) * grnPerturb;
         int r = rgb >> 16 & 255;
         int g = rgb >> 8 & 255;
         int b = rgb & 255;
         r = PixelUtils.clamp((int)((float)r * red));
         g = PixelUtils.clamp((int)((float)g * grn));
         b = PixelUtils.clamp((int)((float)b * blu));
         return rgb & -16777216 | r << 16 | g << 8 | b;
      }
   }

   public String toString() {
      return "Texture/Marble Texture...";
   }
}
