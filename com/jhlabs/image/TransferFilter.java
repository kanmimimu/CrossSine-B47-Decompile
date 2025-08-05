package com.jhlabs.image;

import java.awt.image.BufferedImage;

public abstract class TransferFilter extends PointFilter {
   protected int[] rTable;
   protected int[] gTable;
   protected int[] bTable;
   protected boolean initialized = false;

   public TransferFilter() {
      super.canFilterIndexColorModel = true;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      r = this.rTable[r];
      g = this.gTable[g];
      b = this.bTable[b];
      return a | r << 16 | g << 8 | b;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      if (!this.initialized) {
         this.initialize();
      }

      return super.filter(src, dst);
   }

   protected void initialize() {
      this.initialized = true;
      this.rTable = this.gTable = this.bTable = this.makeTable();
   }

   protected int[] makeTable() {
      int[] table = new int[256];

      for(int i = 0; i < 256; ++i) {
         table[i] = PixelUtils.clamp((int)(255.0F * this.transferFunction((float)i / 255.0F)));
      }

      return table;
   }

   protected float transferFunction(float v) {
      return 0.0F;
   }

   public int[] getLUT() {
      if (!this.initialized) {
         this.initialize();
      }

      int[] lut = new int[256];

      for(int i = 0; i < 256; ++i) {
         lut[i] = this.filterRGB(0, 0, i << 24 | i << 16 | i << 8 | i);
      }

      return lut;
   }
}
