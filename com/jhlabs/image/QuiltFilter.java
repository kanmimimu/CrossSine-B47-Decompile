package com.jhlabs.image;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class QuiltFilter extends WholeImageFilter implements Serializable {
   private Random randomGenerator = new Random();
   private long seed = 567L;
   private int iterations = 25000;
   private float a = -0.59F;
   private float b = 0.2F;
   private float c = 0.1F;
   private float d = 0.0F;
   private int k = 0;
   private Colormap colormap = new LinearColormap();

   public void randomize() {
      this.seed = (new Date()).getTime();
      this.randomGenerator.setSeed(this.seed);
      this.a = this.randomGenerator.nextFloat();
      this.b = this.randomGenerator.nextFloat();
      this.c = this.randomGenerator.nextFloat();
      this.d = this.randomGenerator.nextFloat();
      this.k = this.randomGenerator.nextInt() % 20 - 10;
   }

   public void setIterations(int iterations) {
      this.iterations = iterations;
   }

   public int getIterations() {
      return this.iterations;
   }

   public void setA(float a) {
      this.a = a;
   }

   public float getA() {
      return this.a;
   }

   public void setB(float b) {
      this.b = b;
   }

   public float getB() {
      return this.b;
   }

   public void setC(float c) {
      this.c = c;
   }

   public float getC() {
      return this.c;
   }

   public void setD(float d) {
      this.d = d;
   }

   public float getD() {
      return this.d;
   }

   public void setK(int k) {
      this.k = k;
   }

   public int getK() {
      return this.k;
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int[] outPixels = new int[width * height];
      int i = 0;
      int max = 0;
      float x = 0.1F;
      float y = 0.3F;

      for(int n = 0; n < 20; ++n) {
         float mx = (float)Math.PI * x;
         float my = (float)Math.PI * y;
         float smx2 = (float)Math.sin((double)(2.0F * mx));
         float smy2 = (float)Math.sin((double)(2.0F * my));
         float x1 = (float)((double)(this.a * smx2) + (double)(this.b * smx2) * Math.cos((double)(2.0F * my)) + (double)this.c * Math.sin((double)(4.0F * mx)) + (double)this.d * Math.sin((double)(6.0F * mx)) * Math.cos((double)(4.0F * my)) + (double)((float)this.k * x));
         x1 = x1 >= 0.0F ? x1 - (float)((int)x1) : x1 - (float)((int)x1) + 1.0F;
         float y1 = (float)((double)(this.a * smy2) + (double)(this.b * smy2) * Math.cos((double)(2.0F * mx)) + (double)this.c * Math.sin((double)(4.0F * my)) + (double)this.d * Math.sin((double)(6.0F * my)) * Math.cos((double)(4.0F * mx)) + (double)((float)this.k * y));
         y1 = y1 >= 0.0F ? y1 - (float)((int)y1) : y1 - (float)((int)y1) + 1.0F;
         x = x1;
         y = y1;
      }

      for(int n = 0; n < this.iterations; ++n) {
         float mx = (float)Math.PI * x;
         float my = (float)Math.PI * y;
         float x1 = (float)((double)this.a * Math.sin((double)(2.0F * mx)) + (double)this.b * Math.sin((double)(2.0F * mx)) * Math.cos((double)(2.0F * my)) + (double)this.c * Math.sin((double)(4.0F * mx)) + (double)this.d * Math.sin((double)(6.0F * mx)) * Math.cos((double)(4.0F * my)) + (double)((float)this.k * x));
         x1 = x1 >= 0.0F ? x1 - (float)((int)x1) : x1 - (float)((int)x1) + 1.0F;
         float y1 = (float)((double)this.a * Math.sin((double)(2.0F * my)) + (double)this.b * Math.sin((double)(2.0F * my)) * Math.cos((double)(2.0F * mx)) + (double)this.c * Math.sin((double)(4.0F * my)) + (double)this.d * Math.sin((double)(6.0F * my)) * Math.cos((double)(4.0F * mx)) + (double)((float)this.k * y));
         y1 = y1 >= 0.0F ? y1 - (float)((int)y1) : y1 - (float)((int)y1) + 1.0F;
         x = x1;
         y = y1;
         int ix = (int)((float)width * x1);
         int iy = (int)((float)height * y1);
         if (ix >= 0 && ix < width && iy >= 0 && iy < height) {
            int var10001 = width * iy + ix;
            int var10003 = outPixels[width * iy + ix];
            outPixels[var10001] = outPixels[width * iy + ix] + 1;
            int t = var10003;
            if (t > max) {
               max = t;
            }
         }
      }

      if (this.colormap != null) {
         int index = 0;

         for(float var19 = 0.0F; var19 < (float)height; ++var19) {
            for(float var18 = 0.0F; var18 < (float)width; ++var18) {
               outPixels[index] = this.colormap.getColor((float)outPixels[index] / (float)max);
               ++index;
            }
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Texture/Chaotic Quilt...";
   }
}
