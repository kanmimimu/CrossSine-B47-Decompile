package com.jhlabs.image;

import java.awt.image.BufferedImage;

public class ChromeFilter extends LightFilter {
   private float amount = 0.5F;
   private float exposure = 1.0F;

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setExposure(float exposure) {
      this.exposure = exposure;
   }

   public float getExposure() {
      return this.exposure;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      ((LightFilter)this).setColorSource(1);
      dst = super.filter(src, dst);
      TransferFilter tf = new TransferFilter() {
         protected float transferFunction(float v) {
            v += ChromeFilter.this.amount * (float)Math.sin((double)(v * 2.0F) * Math.PI);
            return 1.0F - (float)Math.exp((double)(-v * ChromeFilter.this.exposure));
         }
      };
      return tf.filter(dst, dst);
   }

   public String toString() {
      return "Effects/Chrome...";
   }
}
