package com.jhlabs.image;

import java.awt.Rectangle;

public class EdgeFilter extends WholeImageFilter {
   static final long serialVersionUID = -1084121755410916989L;
   public static final float R2 = (float)Math.sqrt((double)2.0F);
   public static final float[] ROBERTS_V = new float[]{0.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F};
   public static final float[] ROBERTS_H = new float[]{-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F};
   public static final float[] PREWITT_V = new float[]{-1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F};
   public static final float[] PREWITT_H = new float[]{-1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};
   public static final float[] SOBEL_V = new float[]{-1.0F, 0.0F, 1.0F, -2.0F, 0.0F, 2.0F, -1.0F, 0.0F, 1.0F};
   public static float[] SOBEL_H = new float[]{-1.0F, -2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F};
   public static final float[] FREI_CHEN_V;
   public static float[] FREI_CHEN_H;
   protected float[] vEdgeMatrix;
   protected float[] hEdgeMatrix;

   public EdgeFilter() {
      this.vEdgeMatrix = SOBEL_V;
      this.hEdgeMatrix = SOBEL_H;
   }

   public void setVEdgeMatrix(float[] vEdgeMatrix) {
      this.vEdgeMatrix = vEdgeMatrix;
   }

   public float[] getVEdgeMatrix() {
      return this.vEdgeMatrix;
   }

   public void setHEdgeMatrix(float[] hEdgeMatrix) {
      this.hEdgeMatrix = hEdgeMatrix;
   }

   public float[] getHEdgeMatrix() {
      return this.hEdgeMatrix;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int index = 0;
      int[] outPixels = new int[width * height];

      for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
            int r = 0;
            int g = 0;
            int b = 0;
            int rh = 0;
            int gh = 0;
            int bh = 0;
            int rv = 0;
            int gv = 0;
            int bv = 0;
            int a = inPixels[y * width + x] & -16777216;

            for(int row = -1; row <= 1; ++row) {
               int iy = y + row;
               int ioffset;
               if (0 <= iy && iy < height) {
                  ioffset = iy * width;
               } else {
                  ioffset = y * width;
               }

               int moffset = 3 * (row + 1) + 1;

               for(int col = -1; col <= 1; ++col) {
                  int ix = x + col;
                  if (0 > ix || ix >= width) {
                     ix = x;
                  }

                  int rgb = inPixels[ioffset + ix];
                  float h = this.hEdgeMatrix[moffset + col];
                  float v = this.vEdgeMatrix[moffset + col];
                  r = (rgb & 16711680) >> 16;
                  g = (rgb & '\uff00') >> 8;
                  b = rgb & 255;
                  rh += (int)(h * (float)r);
                  gh += (int)(h * (float)g);
                  bh += (int)(h * (float)b);
                  rv += (int)(v * (float)r);
                  gv += (int)(v * (float)g);
                  bv += (int)(v * (float)b);
               }
            }

            r = (int)(Math.sqrt((double)(rh * rh + rv * rv)) / 1.8);
            g = (int)(Math.sqrt((double)(gh * gh + gv * gv)) / 1.8);
            b = (int)(Math.sqrt((double)(bh * bh + bv * bv)) / 1.8);
            r = PixelUtils.clamp(r);
            g = PixelUtils.clamp(g);
            b = PixelUtils.clamp(b);
            outPixels[index++] = a | r << 16 | g << 8 | b;
         }
      }

      return outPixels;
   }

   public String toString() {
      return "Blur/Detect Edges";
   }

   static {
      FREI_CHEN_V = new float[]{-1.0F, 0.0F, 1.0F, -R2, 0.0F, R2, -1.0F, 0.0F, 1.0F};
      FREI_CHEN_H = new float[]{-1.0F, -R2, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, R2, 1.0F};
   }
}
