package com.jhlabs.image;

public class GrayscaleFilter extends PointFilter {
   public GrayscaleFilter() {
      super.canFilterIndexColorModel = true;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      rgb = r * 77 + g * 151 + b * 28 >> 8;
      return a | rgb << 16 | rgb << 8 | rgb;
   }

   public String toString() {
      return "Colors/Grayscale";
   }
}
