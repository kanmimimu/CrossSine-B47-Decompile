package com.jhlabs.image;

public class RescaleFilter extends TransferFilter {
   static final long serialVersionUID = -2724874183243154495L;
   private float scale = 1.0F;

   protected float transferFunction(float v) {
      return (float)PixelUtils.clamp((int)(v * this.scale));
   }

   public void setScale(float scale) {
      this.scale = scale;
      super.initialized = false;
   }

   public float getScale() {
      return this.scale;
   }

   public String toString() {
      return "Colors/Rescale...";
   }
}
