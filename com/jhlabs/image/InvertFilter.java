package com.jhlabs.image;

public class InvertFilter extends PointFilter {
   public InvertFilter() {
      super.canFilterIndexColorModel = true;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      return a | ~rgb & 16777215;
   }

   public String toString() {
      return "Colors/Invert";
   }
}
