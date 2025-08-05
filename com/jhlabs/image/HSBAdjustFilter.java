package com.jhlabs.image;

import java.awt.Color;

public class HSBAdjustFilter extends PointFilter {
   public float hFactor;
   public float sFactor;
   public float bFactor;
   private float[] hsb;

   public HSBAdjustFilter() {
      this(0.0F, 0.0F, 0.0F);
   }

   public HSBAdjustFilter(float r, float g, float b) {
      this.hsb = new float[3];
      this.hFactor = r;
      this.sFactor = g;
      this.bFactor = b;
      super.canFilterIndexColorModel = true;
   }

   public void setHFactor(float hFactor) {
      this.hFactor = hFactor;
   }

   public float getHFactor() {
      return this.hFactor;
   }

   public void setSFactor(float sFactor) {
      this.sFactor = sFactor;
   }

   public float getSFactor() {
      return this.sFactor;
   }

   public void setBFactor(float bFactor) {
      this.bFactor = bFactor;
   }

   public float getBFactor() {
      return this.bFactor;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      Color.RGBtoHSB(r, g, b, this.hsb);
      float[] var10000 = this.hsb;

      for(var10000[0] += this.hFactor; this.hsb[0] < 0.0F; var10000[0] = (float)((double)var10000[0] + (Math.PI * 2D))) {
         var10000 = this.hsb;
      }

      var10000 = this.hsb;
      var10000[1] += this.sFactor;
      if (this.hsb[1] < 0.0F) {
         this.hsb[1] = 0.0F;
      } else if ((double)this.hsb[1] > (double)1.0F) {
         this.hsb[1] = 1.0F;
      }

      var10000 = this.hsb;
      var10000[2] += this.bFactor;
      if (this.hsb[2] < 0.0F) {
         this.hsb[2] = 0.0F;
      } else if ((double)this.hsb[2] > (double)1.0F) {
         this.hsb[2] = 1.0F;
      }

      rgb = Color.HSBtoRGB(this.hsb[0], this.hsb[1], this.hsb[2]);
      return a | rgb & 16777215;
   }

   public String toString() {
      return "Colors/Adjust HSB...";
   }
}
