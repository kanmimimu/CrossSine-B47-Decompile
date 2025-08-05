package com.jhlabs.image;

public class FadeFilter extends PointFilter {
   private int width;
   private int height;
   private float angle = 0.0F;
   private float fadeStart = 1.0F;
   private float fadeWidth = 10.0F;
   private int sides;
   private boolean invert;
   private float m00 = 1.0F;
   private float m01 = 0.0F;
   private float m10 = 0.0F;
   private float m11 = 1.0F;

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

   public void setSides(int sides) {
      this.sides = sides;
   }

   public int getSides() {
      return this.sides;
   }

   public void setFadeStart(float fadeStart) {
      this.fadeStart = fadeStart;
   }

   public float getFadeStart() {
      return this.fadeStart;
   }

   public void setFadeWidth(float fadeWidth) {
      this.fadeWidth = fadeWidth;
   }

   public float getFadeWidth() {
      return this.fadeWidth;
   }

   public void setInvert(boolean invert) {
      this.invert = invert;
   }

   public boolean getInvert() {
      return this.invert;
   }

   public void setDimensions(int width, int height) {
      this.width = width;
      this.height = height;
      super.setDimensions(width, height);
   }

   public int filterRGB(int x, int y, int rgb) {
      float nx = this.m00 * (float)x + this.m01 * (float)y;
      float ny = this.m10 * (float)x + this.m11 * (float)y;
      if (this.sides == 2) {
         nx = (float)Math.sqrt((double)(nx * nx + ny * ny));
      } else if (this.sides == 3) {
         nx = ImageMath.mod(nx, 16.0F);
      } else if (this.sides == 4) {
         nx = this.symmetry(nx, 16.0F);
      }

      int alpha = (int)(ImageMath.smoothStep(this.fadeStart, this.fadeStart + this.fadeWidth, nx) * 255.0F);
      if (this.invert) {
         alpha = 255 - alpha;
      }

      return alpha << 24 | rgb & 16777215;
   }

   public float symmetry(float x, float b) {
      x = ImageMath.mod(x, 2.0F * b);
      return x > b ? 2.0F * b - x : x;
   }

   public String toString() {
      return "Fade...";
   }
}
