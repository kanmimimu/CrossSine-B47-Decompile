package com.jhlabs.image;

import java.awt.Rectangle;

public class EmbossFilter extends WholeImageFilter {
   private static final float pixelScale = 255.9F;
   private float azimuth = 2.3561945F;
   private float elevation = ((float)Math.PI / 6F);
   private boolean emboss = false;
   private float width45 = 3.0F;

   public void setAzimuth(float azimuth) {
      this.azimuth = azimuth;
   }

   public float getAzimuth() {
      return this.azimuth;
   }

   public void setElevation(float elevation) {
      this.elevation = elevation;
   }

   public float getElevation() {
      return this.elevation;
   }

   public void setBumpHeight(float bumpHeight) {
      this.width45 = 3.0F * bumpHeight;
   }

   public float getBumpHeight() {
      return this.width45 / 3.0F;
   }

   public void setEmboss(boolean emboss) {
      this.emboss = emboss;
   }

   public boolean getEmboss() {
      return this.emboss;
   }

   protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
      int index = 0;
      int[] outPixels = new int[width * height];
      int bumpMapWidth = width;
      int[] bumpPixels = new int[width * height];

      for(int i = 0; i < inPixels.length; ++i) {
         bumpPixels[i] = PixelUtils.brightness(inPixels[i]);
      }

      int Lx = (int)(Math.cos((double)this.azimuth) * Math.cos((double)this.elevation) * (double)255.9F);
      int Ly = (int)(Math.sin((double)this.azimuth) * Math.cos((double)this.elevation) * (double)255.9F);
      int Lz = (int)(Math.sin((double)this.elevation) * (double)255.9F);
      int Nz = (int)(1530.0F / this.width45);
      int Nz2 = Nz * Nz;
      int NzLz = Nz * Lz;
      int background = Lz;
      int bumpIndex = 0;

      for(int y = 0; y < height; bumpIndex += bumpMapWidth) {
         int s1 = bumpIndex;
         int s2 = bumpIndex + bumpMapWidth;
         int s3 = s2 + bumpMapWidth;

         for(int x = 0; x < width; ++s3) {
            int shade;
            if (y != 0 && y < height - 2 && x != 0 && x < width - 2) {
               int Nx = bumpPixels[s1 - 1] + bumpPixels[s2 - 1] + bumpPixels[s3 - 1] - bumpPixels[s1 + 1] - bumpPixels[s2 + 1] - bumpPixels[s3 + 1];
               int Ny = bumpPixels[s3 - 1] + bumpPixels[s3] + bumpPixels[s3 + 1] - bumpPixels[s1 - 1] - bumpPixels[s1] - bumpPixels[s1 + 1];
               if (Nx == 0 && Ny == 0) {
                  shade = background;
               } else {
                  int NdotL;
                  if ((NdotL = Nx * Lx + Ny * Ly + NzLz) < 0) {
                     shade = 0;
                  } else {
                     shade = (int)((double)NdotL / Math.sqrt((double)(Nx * Nx + Ny * Ny + Nz2)));
                  }
               }
            } else {
               shade = background;
            }

            if (this.emboss) {
               int rgb = inPixels[index];
               int a = rgb & -16777216;
               int r = rgb >> 16 & 255;
               int g = rgb >> 8 & 255;
               int b = rgb & 255;
               r = r * shade >> 8;
               g = g * shade >> 8;
               b = b * shade >> 8;
               outPixels[index++] = a | r << 16 | g << 8 | b;
            } else {
               outPixels[index++] = -16777216 | shade << 16 | shade << 8 | shade;
            }

            ++x;
            ++s1;
            ++s2;
         }

         ++y;
      }

      return outPixels;
   }

   public String toString() {
      return "Stylize/Emboss...";
   }
}
