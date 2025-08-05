package com.jhlabs.image;

public class LookupFilter extends PointFilter {
   private Colormap colormap = new Gradient();

   public LookupFilter() {
      super.canFilterIndexColorModel = true;
   }

   public LookupFilter(Colormap colormap) {
      super.canFilterIndexColorModel = true;
      this.colormap = colormap;
   }

   public void setColormap(Colormap colormap) {
      this.colormap = colormap;
   }

   public Colormap getColormap() {
      return this.colormap;
   }

   public int filterRGB(int x, int y, int rgb) {
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      rgb = (r + g + b) / 3;
      return this.colormap.getColor((float)rgb / 255.0F);
   }

   public String toString() {
      return "Colors/Lookup...";
   }
}
