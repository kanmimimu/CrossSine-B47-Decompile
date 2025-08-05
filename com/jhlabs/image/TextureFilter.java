package com.jhlabs.image;

import com.jhlabs.math.Function2D;
import com.jhlabs.math.Noise;
import java.io.Serializable;

public class TextureFilter extends PointFilter implements Serializable {
   static final long serialVersionUID = -7538331862272404352L;
   private float scale = 32.0F;
   private float stretch = 1.0F;
   private float angle = 0.0F;
   public float amount = 1.0F;
   public float turbulence = 1.0F;
   public float gain = 0.5F;
   public float bias = 0.5F;
   public int operation;
   private float m00 = 1.0F;
   private float m01 = 0.0F;
   private float m10 = 0.0F;
   private float m11 = 1.0F;
   private Colormap colormap = new Gradient();
   private Function2D function = new Noise();

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setFunction(Function2D function) {
      this.function = function;
   }

   public Function2D getFunction() {
      return this.function;
   }

   public void setOperation(int operation) {
      this.operation = operation;
   }

   public int getOperation() {
      return this.operation;
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

   public void setTurbulence(float turbulence) {
      this.turbulence = turbulence;
   }

   public float getTurbulence() {
      return this.turbulence;
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
      nx /= this.scale;
      ny /= this.scale * this.stretch;
      float f = (double)this.turbulence == (double)1.0F ? Noise.noise2(nx, ny) : Noise.turbulence2(nx, ny, this.turbulence);
      f = f * 0.5F + 0.5F;
      f = ImageMath.gain(f, this.gain);
      f = ImageMath.bias(f, this.bias);
      f *= this.amount;
      int a = rgb & -16777216;
      int v;
      if (this.colormap != null) {
         v = this.colormap.getColor(f);
      } else {
         v = PixelUtils.clamp((int)(f * 255.0F));
         int r = v << 16;
         int g = v << 8;
         v = a | r | g | v;
      }

      if (this.operation != 0) {
         v = PixelUtils.combinePixels(rgb, v, this.operation);
      }

      return v;
   }

   public String toString() {
      return "Texture/Noise...";
   }
}
