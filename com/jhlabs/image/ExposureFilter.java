package com.jhlabs.image;

public class ExposureFilter extends TransferFilter {
   private float exposure = 1.0F;

   protected float transferFunction(float f) {
      return 1.0F - (float)Math.exp((double)(-f * this.exposure));
   }

   public void setExposure(float exposure) {
      this.exposure = exposure;
      super.initialized = false;
   }

   public float getExposure() {
      return this.exposure;
   }

   public String toString() {
      return "Colors/Exposure...";
   }
}
