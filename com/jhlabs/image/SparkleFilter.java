package com.jhlabs.image;

import java.io.Serializable;
import java.util.Random;

public class SparkleFilter extends PointFilter implements Serializable {
   static final long serialVersionUID = 1692413049411710802L;
   private int rays = 50;
   private int radius = 25;
   private int amount = 50;
   private int color = -1;
   private int randomness = 25;
   private int width;
   private int height;
   private int centreX;
   private int centreY;
   private long seed = 371L;
   private float[] rayLengths;
   private Random randomNumbers = new Random();

   public void setColor(int color) {
      this.color = color;
   }

   public int getColor() {
      return this.color;
   }

   public void setRandomness(int randomness) {
      this.randomness = randomness;
   }

   public int getRandomness() {
      return this.randomness;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public int getAmount() {
      return this.amount;
   }

   public void setRays(int rays) {
      this.rays = rays;
   }

   public int getRays() {
      return this.rays;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

   public int getRadius() {
      return this.radius;
   }

   public void setDimensions(int width, int height) {
      this.width = width;
      this.height = height;
      this.centreX = width / 2;
      this.centreY = height / 2;
      super.setDimensions(width, height);
      this.randomNumbers.setSeed(this.seed);
      this.rayLengths = new float[this.rays];

      for(int i = 0; i < this.rays; ++i) {
         this.rayLengths[i] = (float)this.radius + (float)this.randomness / 100.0F * (float)this.radius * (float)this.randomNumbers.nextGaussian();
      }

   }

   public int filterRGB(int x, int y, int rgb) {
      float dx = (float)(x - this.centreX);
      float dy = (float)(y - this.centreY);
      float distance = dx * dx + dy * dy;
      float angle = (float)Math.atan2((double)dy, (double)dx);
      float d = (angle + (float)Math.PI) / ((float)Math.PI * 2F) * (float)this.rays;
      int i = (int)d;
      float f = d - (float)i;
      if (this.radius != 0) {
         float length = ImageMath.lerp(f, this.rayLengths[i % this.rays], this.rayLengths[(i + 1) % this.rays]);
         float g = length * length / (distance + 1.0E-4F);
         g = (float)Math.pow((double)g, (double)(100 - this.amount) / (double)50.0F);
         f -= 0.5F;
         f = 1.0F - f * f;
         f *= g;
      }

      f = ImageMath.clamp(f, 0.0F, 1.0F);
      return ImageMath.mixColors(f, rgb, this.color);
   }

   public String toString() {
      return "Stylize/Sparkle...";
   }
}
