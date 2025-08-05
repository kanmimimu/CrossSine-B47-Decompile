package com.jhlabs.image;

import java.awt.image.BufferedImage;

public class DiffuseFilter extends TransformFilter {
   public float[] sinTable;
   public float[] cosTable;
   public float scale = 4.0F;

   public DiffuseFilter() {
      ((TransformFilter)this).setEdgeAction(1);
   }

   public void setScale(float scale) {
      this.scale = scale;
   }

   public float getScale() {
      return this.scale;
   }

   protected void transformInverse(int x, int y, float[] out) {
      int angle = (int)(Math.random() * (double)255.0F);
      float distance = (float)Math.random();
      out[0] = (float)x + distance * this.sinTable[angle];
      out[1] = (float)y + distance * this.cosTable[angle];
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      this.sinTable = new float[256];
      this.cosTable = new float[256];

      for(int i = 0; i < 256; ++i) {
         float angle = ((float)Math.PI * 2F) * (float)i / 256.0F;
         this.sinTable[i] = (float)((double)this.scale * Math.sin((double)angle));
         this.cosTable[i] = (float)((double)this.scale * Math.cos((double)angle));
      }

      return super.filter(src, dst);
   }

   public String toString() {
      return "Distort/Diffuse...";
   }
}
