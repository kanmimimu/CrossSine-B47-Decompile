package com.jhlabs.image;

public class JavaLnFFilter extends PointFilter {
   public int filterRGB(int x, int y, int rgb) {
      return (x & 1) == (y & 1) ? rgb : ImageMath.mixColors(0.25F, -6710887, rgb);
   }

   public String toString() {
      return "Stylize/Java L&F Stipple";
   }
}
