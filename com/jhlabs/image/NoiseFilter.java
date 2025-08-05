package com.jhlabs.image;

import java.util.Random;

public class NoiseFilter extends PointFilter {
   public static final int GAUSSIAN = 0;
   public static final int UNIFORM = 1;
   private int amount = 25;
   private int distribution = 1;
   private boolean monochrome = false;
   private float density = 1.0F;
   private Random randomNumbers = new Random();

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public int getAmount() {
      return this.amount;
   }

   public void setDistribution(int distribution) {
      this.distribution = distribution;
   }

   public int getDistribution() {
      return this.distribution;
   }

   public void setMonochrome(boolean monochrome) {
      this.monochrome = monochrome;
   }

   public boolean getMonochrome() {
      return this.monochrome;
   }

   public void setDensity(float density) {
      this.density = density;
   }

   public float getDensity() {
      return this.density;
   }

   private int random(int x) {
      x += (int)((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (double)(2.0F * this.randomNumbers.nextFloat() - 1.0F)) * (double)this.amount);
      if (x < 0) {
         x = 0;
      } else if (x > 255) {
         x = 255;
      }

      return x;
   }

   public int filterRGB(int x, int y, int rgb) {
      if (this.randomNumbers.nextFloat() <= this.density) {
         int a = rgb & -16777216;
         int r = rgb >> 16 & 255;
         int g = rgb >> 8 & 255;
         int b = rgb & 255;
         if (this.monochrome) {
            int n = (int)((this.distribution == 0 ? this.randomNumbers.nextGaussian() : (double)(2.0F * this.randomNumbers.nextFloat() - 1.0F)) * (double)this.amount);
            r = PixelUtils.clamp(r + n);
            g = PixelUtils.clamp(g + n);
            b = PixelUtils.clamp(b + n);
         } else {
            r = this.random(r);
            g = this.random(g);
            b = this.random(b);
         }

         return a | r << 16 | g << 8 | b;
      } else {
         return rgb;
      }
   }

   public String toString() {
      return "Stylize/Add Noise...";
   }
}
