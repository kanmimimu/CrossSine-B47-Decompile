package com.jhlabs.image;

public class InvertAlphaFilter extends PointFilter {
   public InvertAlphaFilter() {
      super.canFilterIndexColorModel = true;
   }

   public int filterRGB(int x, int y, int rgb) {
      return rgb ^ -16777216;
   }

   public String toString() {
      return "Alpha/Invert";
   }
}
