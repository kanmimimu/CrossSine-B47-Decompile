package com.jhlabs.image;

public class Histogram {
   public static final int RED = 0;
   public static final int GREEN = 1;
   public static final int BLUE = 2;
   public static final int GRAY = 3;
   protected int[][] histogram;
   protected int numSamples;
   protected int[] minValue;
   protected int[] maxValue;
   protected int[] minFrequency;
   protected int[] maxFrequency;
   protected float[] mean;
   protected boolean isGray;

   public Histogram() {
      this.histogram = (int[][])null;
      this.numSamples = 0;
      this.isGray = true;
      this.minValue = null;
      this.maxValue = null;
      this.minFrequency = null;
      this.maxFrequency = null;
      this.mean = null;
   }

   public Histogram(int[] pixels, int w, int h, int offset, int stride) {
      this.histogram = new int[3][256];
      this.minValue = new int[4];
      this.maxValue = new int[4];
      this.minFrequency = new int[3];
      this.maxFrequency = new int[3];
      this.mean = new float[3];
      this.numSamples = w * h;
      this.isGray = true;
      int index = 0;

      for(int y = 0; y < h; ++y) {
         index = offset + y * stride;

         for(int x = 0; x < w; ++x) {
            int rgb = pixels[index++];
            int r = rgb >> 16 & 255;
            int g = rgb >> 8 & 255;
            int b = rgb & 255;
            int var10002 = this.histogram[0][r]++;
            var10002 = this.histogram[1][g]++;
            var10002 = this.histogram[2][b]++;
         }
      }

      for(int i = 0; i < 256; ++i) {
         if (this.histogram[0][i] != this.histogram[1][i] || this.histogram[1][i] != this.histogram[2][i]) {
            this.isGray = false;
            break;
         }
      }

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 256; ++j) {
            if (this.histogram[i][j] > 0) {
               this.minValue[i] = j;
               break;
            }
         }

         for(int j = 255; j >= 0; --j) {
            if (this.histogram[i][j] > 0) {
               this.maxValue[i] = j;
               break;
            }
         }

         this.minFrequency[i] = Integer.MAX_VALUE;
         this.maxFrequency[i] = 0;

         for(int j = 0; j < 256; ++j) {
            this.minFrequency[i] = Math.min(this.minFrequency[i], this.histogram[i][j]);
            this.maxFrequency[i] = Math.max(this.maxFrequency[i], this.histogram[i][j]);
            float[] var10000 = this.mean;
            var10000[i] += (float)(j * this.histogram[i][j]);
         }

         float[] var19 = this.mean;
         var19[i] /= (float)this.numSamples;
      }

      this.minValue[3] = Math.min(Math.min(this.minValue[0], this.minValue[1]), this.minValue[2]);
      this.maxValue[3] = Math.max(Math.max(this.maxValue[0], this.maxValue[1]), this.maxValue[2]);
   }

   public boolean isGray() {
      return this.isGray;
   }

   public int getNumSamples() {
      return this.numSamples;
   }

   public int getFrequency(int value) {
      return this.numSamples > 0 && this.isGray && value >= 0 && value <= 255 ? this.histogram[0][value] : -1;
   }

   public int getFrequency(int channel, int value) {
      return this.numSamples >= 1 && channel >= 0 && channel <= 2 && value >= 0 && value <= 255 ? this.histogram[channel][value] : -1;
   }

   public int getMinFrequency() {
      return this.numSamples > 0 && this.isGray ? this.minFrequency[0] : -1;
   }

   public int getMinFrequency(int channel) {
      return this.numSamples >= 1 && channel >= 0 && channel <= 2 ? this.minFrequency[channel] : -1;
   }

   public int getMaxFrequency() {
      return this.numSamples > 0 && this.isGray ? this.maxFrequency[0] : -1;
   }

   public int getMaxFrequency(int channel) {
      return this.numSamples >= 1 && channel >= 0 && channel <= 2 ? this.maxFrequency[channel] : -1;
   }

   public int getMinValue() {
      return this.numSamples > 0 && this.isGray ? this.minValue[0] : -1;
   }

   public int getMinValue(int channel) {
      return this.minValue[channel];
   }

   public int getMaxValue() {
      return this.numSamples > 0 && this.isGray ? this.maxValue[0] : -1;
   }

   public int getMaxValue(int channel) {
      return this.maxValue[channel];
   }

   public float getMeanValue() {
      return this.numSamples > 0 && this.isGray ? this.mean[0] : -1.0F;
   }

   public float getMeanValue(int channel) {
      return this.numSamples > 0 && 0 <= channel && channel <= 2 ? this.mean[channel] : -1.0F;
   }
}
