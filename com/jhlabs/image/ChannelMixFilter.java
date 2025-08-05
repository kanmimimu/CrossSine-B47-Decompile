package com.jhlabs.image;

public class ChannelMixFilter extends PointFilter {
   public int blueGreen;
   public int redBlue;
   public int greenRed;
   public int intoR;
   public int intoG;
   public int intoB;

   public ChannelMixFilter() {
      super.canFilterIndexColorModel = true;
   }

   public void setBlueGreen(int blueGreen) {
      this.blueGreen = blueGreen;
   }

   public int getBlueGreen() {
      return this.blueGreen;
   }

   public void setRedBlue(int redBlue) {
      this.redBlue = redBlue;
   }

   public int getRedBlue() {
      return this.redBlue;
   }

   public void setGreenRed(int greenRed) {
      this.greenRed = greenRed;
   }

   public int getGreenRed() {
      return this.greenRed;
   }

   public void setIntoR(int intoR) {
      this.intoR = intoR;
   }

   public int getIntoR() {
      return this.intoR;
   }

   public void setIntoG(int intoG) {
      this.intoG = intoG;
   }

   public int getIntoG() {
      return this.intoG;
   }

   public void setIntoB(int intoB) {
      this.intoB = intoB;
   }

   public int getIntoB() {
      return this.intoB;
   }

   public int filterRGB(int x, int y, int rgb) {
      int a = rgb & -16777216;
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb & 255;
      int nr = PixelUtils.clamp((this.intoR * (this.blueGreen * g + (255 - this.blueGreen) * b) / 255 + (255 - this.intoR) * r) / 255);
      int ng = PixelUtils.clamp((this.intoG * (this.redBlue * b + (255 - this.redBlue) * r) / 255 + (255 - this.intoG) * g) / 255);
      int nb = PixelUtils.clamp((this.intoB * (this.greenRed * r + (255 - this.greenRed) * g) / 255 + (255 - this.intoB) * b) / 255);
      return a | nr << 16 | ng << 8 | nb;
   }

   public String toString() {
      return "Colors/Mix Channels...";
   }
}
