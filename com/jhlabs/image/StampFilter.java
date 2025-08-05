package com.jhlabs.image;

import java.awt.image.BufferedImage;

public class StampFilter extends PointFilter {
   private float threshold;
   private float softness;
   protected float radius;
   private float lowerThreshold3;
   private float upperThreshold3;
   private int white;
   private int black;

   public StampFilter() {
      this(0.5F);
   }

   public StampFilter(float threshold) {
      this.softness = 0.0F;
      this.radius = 5.0F;
      this.white = -1;
      this.black = -16777216;
      this.setThreshold(threshold);
   }

   public void setRadius(float radius) {
      this.radius = radius;
   }

   public float getRadius() {
      return this.radius;
   }

   public void setThreshold(float threshold) {
      this.threshold = threshold;
   }

   public float getThreshold() {
      return this.threshold;
   }

   public void setSoftness(float softness) {
      this.softness = softness;
   }

   public float getSoftness() {
      return this.softness;
   }

   public void setWhite(int white) {
      this.white = white;
   }

   public int getWhite() {
      return this.white;
   }

   public void setBlack(int black) {
      this.black = black;
   }

   public int getBlack() {
      return this.black;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      dst = (new GaussianFilter((float)((int)this.radius))).filter(src, (BufferedImage)null);
      this.lowerThreshold3 = 765.0F * (this.threshold - this.softness * 0.5F);
      this.upperThreshold3 = 765.0F * (this.threshold + this.softness * 0.5F);
      return super.filter(dst, dst);
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      int l = r + g + b;
      float f = ImageMath.smoothStep(this.lowerThreshold3, this.upperThreshold3, (float)l);
      return ImageMath.mixColors(f, this.black, this.white);
   }

   public String toString() {
      return "Stylize/Stamp...";
   }
}
