package com.jhlabs.image;

import java.io.Serializable;

public class OpacityFilter extends PointFilter implements Serializable {
   static final long serialVersionUID = 5644263685527598345L;
   private int opacity;
   private int opacity24;

   public OpacityFilter() {
      this(136);
   }

   public OpacityFilter(int opacity) {
      this.setOpacity(opacity);
   }

   public void setOpacity(int opacity) {
      this.opacity = opacity;
      this.opacity24 = opacity << 24;
   }

   public int getOpacity() {
      return this.opacity;
   }

   public int filterRGB(int x, int y, int rgb) {
      return (rgb & -16777216) != 0 ? rgb & 16777215 | this.opacity24 : rgb;
   }

   public String toString() {
      return "Colors/Transparency...";
   }
}
