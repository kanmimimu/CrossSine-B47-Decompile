package com.jhlabs.image;

import com.jhlabs.math.Noise;
import java.awt.Rectangle;

public class RippleFilter extends TransformFilter {
   static final long serialVersionUID = 5101667633854087384L;
   public static final int SINE = 0;
   public static final int SAWTOOTH = 1;
   public static final int TRIANGLE = 2;
   public static final int NOISE = 3;
   public float xAmplitude = 5.0F;
   public float yAmplitude = 0.0F;
   public float xWavelength;
   public float yWavelength;
   private int waveType;

   public RippleFilter() {
      this.xWavelength = this.yWavelength = 16.0F;
   }

   public void setXAmplitude(float xAmplitude) {
      this.xAmplitude = xAmplitude;
   }

   public float getXAmplitude() {
      return this.xAmplitude;
   }

   public void setXWavelength(float xWavelength) {
      this.xWavelength = xWavelength;
   }

   public float getXWavelength() {
      return this.xWavelength;
   }

   public void setYAmplitude(float yAmplitude) {
      this.yAmplitude = yAmplitude;
   }

   public float getYAmplitude() {
      return this.yAmplitude;
   }

   public void setYWavelength(float yWavelength) {
      this.yWavelength = yWavelength;
   }

   public float getYWavelength() {
      return this.yWavelength;
   }

   public void setWaveType(int waveType) {
      this.waveType = waveType;
   }

   public int getWaveType() {
      return this.waveType;
   }

   protected void transformSpace(Rectangle r) {
      if (super.edgeAction == 0) {
         r.x -= (int)this.xAmplitude;
         r.width += (int)(2.0F * this.xAmplitude);
         r.y -= (int)this.yAmplitude;
         r.height += (int)(2.0F * this.yAmplitude);
      }

   }

   protected void transformInverse(int x, int y, float[] out) {
      float nx = (float)y / this.xWavelength;
      float ny = (float)x / this.yWavelength;
      float fx;
      float fy;
      switch (this.waveType) {
         case 0:
         default:
            fx = (float)Math.sin((double)nx);
            fy = (float)Math.sin((double)ny);
            break;
         case 1:
            fx = ImageMath.mod(nx, 1.0F);
            fy = ImageMath.mod(ny, 1.0F);
            break;
         case 2:
            fx = ImageMath.triangle(nx);
            fy = ImageMath.triangle(ny);
            break;
         case 3:
            fx = Noise.noise1(nx);
            fy = Noise.noise1(ny);
      }

      out[0] = (float)x + this.xAmplitude * fx;
      out[1] = (float)y + this.yAmplitude * fy;
   }

   public String toString() {
      return "Distort/Ripple...";
   }
}
