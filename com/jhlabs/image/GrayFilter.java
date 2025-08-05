package com.jhlabs.image;

public class GrayFilter extends PointFilter {
   public GrayFilter() {
      super.canFilterIndexColorModel = true;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      r = (r + 255) / 2;
      g = (g + 255) / 2;
      b = (b + 255) / 2;
      return a | r << 16 | g << 8 | b;
   }

   public String toString() {
      return "Colors/Gray Out";
   }
}
