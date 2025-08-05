package com.jhlabs.image;

import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import java.awt.Rectangle;
import java.awt.image.BufferedImageOp;
import java.io.Serializable;
import java.util.Random;

public class CellularFilter extends WholeImageFilter implements Function2D, MutatableFilter, Cloneable, Serializable {
   protected float scale = 32.0F;
   protected float stretch = 1.0F;
   protected float angle = 0.0F;
   public float amount = 1.0F;
   public float turbulence = 1.0F;
   public float gain = 0.5F;
   public float bias = 0.5F;
   public float distancePower = 2.0F;
   public boolean useColor = false;
   protected Colormap colormap = new Gradient();
   protected float[] coefficients = new float[]{1.0F, 0.0F, 0.0F, 0.0F};
   protected float angleCoefficient;
   protected Random random = new Random();
   protected float m00 = 1.0F;
   protected float m01 = 0.0F;
   protected float m10 = 0.0F;
   protected float m11 = 1.0F;
   protected Point[] results = null;
   protected float randomness = 0.0F;
   protected int gridType = 2;
   private float min;
   private float max;
   private static byte[] probabilities;
   private float gradientCoefficient;
   public static final int RANDOM = 0;
   public static final int SQUARE = 1;
   public static final int HEXAGONAL = 2;
   public static final int OCTAGONAL = 3;
   public static final int TRIANGULAR = 4;

   public CellularFilter() {
      this.results = new Point[3];

      for(int j = 0; j < this.results.length; ++j) {
         this.results[j] = new Point();
      }

      if (probabilities == null) {
         probabilities = new byte[8192];
         float factorial = 1.0F;
         float total = 0.0F;
         float mean = 2.5F;

         for(int i = 0; i < 10; ++i) {
            if (i > 1) {
               factorial *= (float)i;
            }

            float probability = (float)Math.pow((double)mean, (double)i) * (float)Math.exp((double)(-mean)) / factorial;
            int start = (int)(total * 8192.0F);
            total += probability;
            int end = (int)(total * 8192.0F);

            for(int j = start; j < end; ++j) {
               probabilities[j] = (byte)i;
            }
         }
      }

   }

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

   public void setCoefficient(int i, float v) {
      this.coefficients[i] = v;
   }

   public float getCoefficient(int i) {
      return this.coefficients[i];
   }

   public void setAngleCoefficient(float angleCoefficient) {
      this.angleCoefficient = angleCoefficient;
   }

   public float getAngleCoefficient() {
      return this.angleCoefficient;
   }

   public void setGradientCoefficient(float gradientCoefficient) {
      this.gradientCoefficient = gradientCoefficient;
   }

   public float getGradientCoefficient() {
      return this.gradientCoefficient;
   }

   public void setF1(float v) {
      this.coefficients[0] = v;
   }

   public float getF1() {
      return this.coefficients[0];
   }

   public void setF2(float v) {
      this.coefficients[1] = v;
   }

   public float getF2() {
      return this.coefficients[1];
   }

   public void setF3(float v) {
      this.coefficients[2] = v;
   }

   public float getF3() {
      return this.coefficients[2];
   }

   public void setF4(float v) {
      this.coefficients[3] = v;
   }

   public float getF4() {
      return this.coefficients[3];
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   public void setRandomness(float randomness) {
      this.randomness = randomness;
   }

   public float getRandomness() {
      return this.randomness;
   }

   public void setGridType(int gridType) {
      this.gridType = gridType;
   }

   public int getGridType() {
      return this.gridType;
   }

   public void setDistancePower(float distancePower) {
      this.distancePower = distancePower;
   }

   public float getDistancePower() {
      return this.distancePower;
   }

   public void setTurbulence(float turbulence) {
      this.turbulence = turbulence;
   }

   public float getTurbulence() {
      return this.turbulence;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   private float checkCube(float x, float y, int cubeX, int cubeY, Point[] results) {
      this.random.setSeed((long)(571 * cubeX + 23 * cubeY));
      int numPoints;
      switch (this.gridType) {
         case 0:
         default:
            numPoints = probabilities[this.random.nextInt() & 8191];
            break;
         case 1:
            numPoints = 1;
            break;
         case 2:
            numPoints = 1;
            break;
         case 3:
            numPoints = 2;
            break;
         case 4:
            numPoints = 2;
      }

      for(int i = 0; i < numPoints; ++i) {
         float px = 0.0F;
         float py = 0.0F;
         float weight = 1.0F;
         switch (this.gridType) {
            case 0:
               px = this.random.nextFloat();
               py = this.random.nextFloat();
               break;
            case 1:
               py = 0.5F;
               px = 0.5F;
               if (this.randomness != 0.0F) {
                  px = (float)((double)px + (double)this.randomness * ((double)this.random.nextFloat() - (double)0.5F));
                  py = (float)((double)py + (double)this.randomness * ((double)this.random.nextFloat() - (double)0.5F));
               }
               break;
            case 2:
               if ((cubeX & 1) == 0) {
                  px = 0.75F;
                  py = 0.0F;
               } else {
                  px = 0.75F;
                  py = 0.5F;
               }

               if (this.randomness != 0.0F) {
                  px += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px), 271.0F * ((float)cubeY + py));
                  py += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px) + 89.0F, 271.0F * ((float)cubeY + py) + 137.0F);
               }
               break;
            case 3:
               switch (i) {
                  case 0:
                     px = 0.207F;
                     py = 0.207F;
                     break;
                  case 1:
                     px = 0.707F;
                     py = 0.707F;
                     weight = 1.6F;
               }

               if (this.randomness != 0.0F) {
                  px += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px), 271.0F * ((float)cubeY + py));
                  py += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px) + 89.0F, 271.0F * ((float)cubeY + py) + 137.0F);
               }
               break;
            case 4:
               if ((cubeY & 1) == 0) {
                  if (i == 0) {
                     px = 0.25F;
                     py = 0.35F;
                  } else {
                     px = 0.75F;
                     py = 0.65F;
                  }
               } else if (i == 0) {
                  px = 0.75F;
                  py = 0.35F;
               } else {
                  px = 0.25F;
                  py = 0.65F;
               }

               if (this.randomness != 0.0F) {
                  px += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px), 271.0F * ((float)cubeY + py));
                  py += this.randomness * Noise.noise2(271.0F * ((float)cubeX + px) + 89.0F, 271.0F * ((float)cubeY + py) + 137.0F);
               }
         }

         float dx = Math.abs(x - px);
         float dy = Math.abs(y - py);
         dx *= weight;
         dy *= weight;
         float d;
         if (this.distancePower == 1.0F) {
            d = dx + dy;
         } else if (this.distancePower == 2.0F) {
            d = (float)Math.sqrt((double)(dx * dx + dy * dy));
         } else {
            d = (float)Math.pow((double)((float)Math.pow((double)dx, (double)this.distancePower) + (float)Math.pow((double)dy, (double)this.distancePower)), (double)(1.0F / this.distancePower));
         }

         if (d < results[0].distance) {
            Point p = results[2];
            results[2] = results[1];
            results[1] = results[0];
            results[0] = p;
            p.distance = d;
            p.dx = dx;
            p.dy = dy;
            p.x = (float)cubeX + px;
            p.y = (float)cubeY + py;
         } else if (d < results[1].distance) {
            Point p = results[2];
            results[2] = results[1];
            results[1] = p;
            p.distance = d;
            p.dx = dx;
            p.dy = dy;
            p.x = (float)cubeX + px;
            p.y = (float)cubeY + py;
         } else if (d < results[2].distance) {
            Point p = results[2];
            p.distance = d;
            p.dx = dx;
            p.dy = dy;
            p.x = (float)cubeX + px;
            p.y = (float)cubeY + py;
         }
      }

      return results[2].distance;
   }

   public float evaluate(float x, float y) {
      for(int j = 0; j < this.results.length; ++j) {
         this.results[j].distance = Float.POSITIVE_INFINITY;
      }

      int ix = (int)x;
      int iy = (int)y;
      float fx = x - (float)ix;
      float fy = y - (float)iy;
      float d = this.checkCube(fx, fy, ix, iy, this.results);
      if (d > fy) {
         d = this.checkCube(fx, fy + 1.0F, ix, iy - 1, this.results);
      }

      if (d > 1.0F - fy) {
         d = this.checkCube(fx, fy - 1.0F, ix, iy + 1, this.results);
      }

      if (d > fx) {
         this.checkCube(fx + 1.0F, fy, ix - 1, iy, this.results);
         if (d > fy) {
            d = this.checkCube(fx + 1.0F, fy + 1.0F, ix - 1, iy - 1, this.results);
         }

         if (d > 1.0F - fy) {
            d = this.checkCube(fx + 1.0F, fy - 1.0F, ix - 1, iy + 1, this.results);
         }
      }

      if (d > 1.0F - fx) {
         d = this.checkCube(fx - 1.0F, fy, ix + 1, iy, this.results);
         if (d > fy) {
            d = this.checkCube(fx - 1.0F, fy + 1.0F, ix + 1, iy - 1, this.results);
         }

         if (d > 1.0F - fy) {
            d = this.checkCube(fx - 1.0F, fy - 1.0F, ix + 1, iy + 1, this.results);
         }
      }

      float t = 0.0F;

      for(int i = 0; i < 3; ++i) {
         t += this.coefficients[i] * this.results[i].distance;
      }

      if (this.angleCoefficient != 0.0F) {
         float angle = (float)Math.atan2((double)(y - this.results[0].y), (double)(x - this.results[0].x));
         if (angle < 0.0F) {
            angle += ((float)Math.PI * 2F);
         }

         angle /= 12.566371F;
         t += this.angleCoefficient * angle;
      }

      if (this.gradientCoefficient != 0.0F) {
         float a = 1.0F / (this.results[0].dy + this.results[0].dx);
         t += this.gradientCoefficient * a;
      }

      return t;
   }

   public float turbulence2(float x, float y, float freq) {
      float t = 0.0F;

      for(float f = 1.0F; f <= freq; f *= 2.0F) {
         t += this.evaluate(f * x, f * y) / f;
      }

      return t;
   }

   public int getPixel(int x, int y, int[] inPixels, int width, int height) {
      try {
         float nx = this.m00 * (float)x + this.m01 * (float)y;
         float ny = this.m10 * (float)x + this.m11 * (float)y;
         nx /= this.scale;
         ny /= this.scale * this.stretch;
         nx += 1000.0F;
         ny += 1000.0F;
         float f = this.turbulence == 1.0F ? this.evaluate(nx, ny) : this.turbulence2(nx, ny, this.turbulence);
         f *= 2.0F;
         f *= this.amount;
         int a = -16777216;
         if (this.colormap != null) {
            int v = this.colormap.getColor(f);
            if (this.useColor) {
               int srcx = ImageMath.clamp((int)((this.results[0].x - 1000.0F) * this.scale), 0, width - 1);
               int srcy = ImageMath.clamp((int)((this.results[0].y - 1000.0F) * this.scale), 0, height - 1);
               v = inPixels[srcy * width + srcx];
               f = (this.results[1].distance - this.results[0].distance) / (this.results[1].distance + this.results[0].distance);
               f = ImageMath.smoothStep(this.coefficients[1], this.coefficients[0], f);
               v = ImageMath.mixColors(f, -16777216, v);
            }

            return v;
         } else {
            int v = PixelUtils.clamp((int)(f * 255.0F));
            int r = v << 16;
            int g = v << 8;
            return a | r | g | v;
         }
      } catch (Exception e) {
         ((Throwable)e).printStackTrace();
         return 0;
      }
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int index = 0;
      int[] outPixels = new int[width * height];

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            outPixels[index++] = this.getPixel(x, y, inPixels, width, height);
         }
      }

      return outPixels;
   }

   public void mutate(float mutationLevel, BufferedImageOp d, boolean keepShape, boolean keepColors) {
      CellularFilter dst = (CellularFilter)d;
      this.random.setSeed((long)((int)System.currentTimeMillis()));
      if (!keepShape && this.amount != 0.0F) {
         dst.scale = this.mutate(this.scale, mutationLevel, 0.4F, 5.0F, 3.0F, 200.0F);
         dst.setAngle(this.mutate(this.angle, mutationLevel, 0.3F, ((float)Math.PI / 2F)));
         dst.stretch = this.mutate(this.stretch, mutationLevel, 0.3F, 3.0F, 1.0F, 10.0F);
         dst.amount = this.mutate(this.amount, mutationLevel, 0.3F, 0.2F, 0.0F, 1.0F);
         dst.turbulence = this.mutate(this.turbulence, mutationLevel, 0.3F, 0.5F, 1.0F, 8.0F);
         dst.distancePower = this.mutate(this.distancePower, mutationLevel, 0.2F, 0.5F, 1.0F, 3.0F);
         dst.randomness = this.mutate(this.randomness, mutationLevel, 0.4F, 0.2F, 0.0F, 1.0F);

         for(int i = 0; i < this.coefficients.length; ++i) {
            dst.coefficients[i] = this.mutate(this.coefficients[i], mutationLevel, 0.3F, 0.2F, -1.0F, 1.0F);
         }

         if ((double)this.random.nextFloat() <= (double)mutationLevel * 0.2) {
            dst.gridType = this.random.nextInt() % 5;
         }

         dst.angleCoefficient = this.mutate(this.angleCoefficient, mutationLevel, 0.2F, 0.5F, -1.0F, 1.0F);
      } else {
         dst.setGridType(this.getGridType());
         dst.setRandomness(this.getRandomness());
         dst.setScale(this.getScale());
         dst.setAngle(this.getAngle());
         dst.setStretch(this.getStretch());
         dst.setAmount(this.getAmount());
         dst.setTurbulence(this.getTurbulence());
         dst.setColormap(this.getColormap());
         dst.setDistancePower(this.getDistancePower());
         dst.setAngleCoefficient(this.getAngleCoefficient());

         for(int i = 0; i < 4; ++i) {
            dst.setCoefficient(i, this.getCoefficient(i));
         }
      }

      if (!keepColors && mutationLevel != 0.0F) {
         if (this.random.nextFloat() <= mutationLevel) {
            if (this.random.nextFloat() <= mutationLevel) {
               dst.setColormap(Gradient.randomGradient());
            } else {
               ((Gradient)dst.getColormap()).mutate(mutationLevel);
            }
         }
      } else {
         dst.setColormap(this.getColormap());
      }

   }

   private float mutate(float n, float mutationLevel, float probability, float amount, float lower, float upper) {
      return this.random.nextFloat() <= mutationLevel * probability ? n : ImageMath.clamp(n + mutationLevel * amount * (float)this.random.nextGaussian(), lower, upper);
   }

   private float mutate(float n, float mutationLevel, float probability, float amount) {
      return this.random.nextFloat() <= mutationLevel * probability ? n : n + mutationLevel * amount * (float)this.random.nextGaussian();
   }

   public Object clone() {
      CellularFilter f = (CellularFilter)super.clone();
      f.coefficients = (float[])this.coefficients.clone();
      f.results = (Point[])this.results.clone();
      f.random = new Random();
      return f;
   }

   public String toString() {
      return "Texture/Cellular...";
   }

   public class Point {
      public int index;
      public float x;
      public float y;
      public float dx;
      public float dy;
      public float cubeX;
      public float cubeY;
      public float distance;
   }
}
