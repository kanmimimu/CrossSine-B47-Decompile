package com.jhlabs.image;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class FieldWarpFilter extends TransformFilter {
   private float amount = 1.0F;
   private float power = 1.0F;
   private float strength = 2.0F;
   private Line[] inLines;
   private Line[] outLines;
   private Line[] intermediateLines;
   private float width;
   private float height;

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public float getAmount() {
      return this.amount;
   }

   public void setPower(float power) {
      this.power = power;
   }

   public float getPower() {
      return this.power;
   }

   public void setStrength(float strength) {
      this.strength = strength;
   }

   public float getStrength() {
      return this.strength;
   }

   public void setInLines(Line[] inLines) {
      this.inLines = inLines;
   }

   public Line[] getInLines() {
      return this.inLines;
   }

   public void setOutLines(Line[] outLines) {
      this.outLines = outLines;
   }

   public Line[] getOutLines() {
      return this.outLines;
   }

   protected void transform(int x, int y, Point out) {
   }

   protected void transformInverse(int x, int y, float[] out) {
      float u = 0.0F;
      float v = 0.0F;
      float fraction = 0.0F;
      float a = 0.001F;
      float b = 1.5F * this.strength + 0.5F;
      float p = this.power;
      float totalWeight = 0.0F;
      float sumX = 0.0F;
      float sumY = 0.0F;

      for(int line = 0; line < this.inLines.length; ++line) {
         Line l1 = this.inLines[line];
         Line l = this.intermediateLines[line];
         float dx = (float)(x - l.x1);
         float dy = (float)(y - l.y1);
         fraction = (dx * (float)l.dx + dy * (float)l.dy) / l.lengthSquared;
         float fdist = (dy * (float)l.dx - dx * (float)l.dy) / l.length;
         float distance;
         if (fraction <= 0.0F) {
            distance = (float)Math.sqrt((double)(dx * dx + dy * dy));
         } else if (fraction >= 1.0F) {
            dx = (float)(x - l.x2);
            dy = (float)(y - l.y2);
            distance = (float)Math.sqrt((double)(dx * dx + dy * dy));
         } else if (fdist >= 0.0F) {
            distance = fdist;
         } else {
            distance = -fdist;
         }

         u = (float)l1.x1 + fraction * (float)l1.dx - fdist * (float)l1.dy / l1.length;
         v = (float)l1.y1 + fraction * (float)l1.dy + fdist * (float)l1.dx / l1.length;
         float weight = (float)Math.pow(Math.pow((double)l.length, (double)p) / (double)(a + distance), (double)b);
         sumX += (u - (float)x) * weight;
         sumY += (v - (float)y) * weight;
         totalWeight += weight;
      }

      out[0] = (float)x + sumX / totalWeight + 0.5F;
      out[1] = (float)y + sumY / totalWeight + 0.5F;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      this.width = this.width;
      this.height = this.height;
      if (this.inLines != null && this.outLines != null) {
         this.intermediateLines = new Line[this.inLines.length];

         for(int line = 0; line < this.inLines.length; ++line) {
            Line l = this.intermediateLines[line] = new Line(ImageMath.lerp(this.amount, this.inLines[line].x1, this.outLines[line].x1), ImageMath.lerp(this.amount, this.inLines[line].y1, this.outLines[line].y1), ImageMath.lerp(this.amount, this.inLines[line].x2, this.outLines[line].x2), ImageMath.lerp(this.amount, this.inLines[line].y2, this.outLines[line].y2));
            l.setup();
            this.inLines[line].setup();
         }

         dst = super.filter(src, dst);
         this.intermediateLines = null;
         return dst;
      } else {
         return src;
      }
   }

   public String toString() {
      return "Distort/Field Warp...";
   }

   public static class Line {
      public int x1;
      public int y1;
      public int x2;
      public int y2;
      public int dx;
      public int dy;
      public float length;
      public float lengthSquared;

      public Line(int x1, int y1, int x2, int y2) {
         this.x1 = x1;
         this.y1 = y1;
         this.x2 = x2;
         this.y2 = y2;
      }

      public void setup() {
         this.dx = this.x2 - this.x1;
         this.dy = this.y2 - this.y1;
         this.lengthSquared = (float)(this.dx * this.dx + this.dy * this.dy);
         this.length = (float)Math.sqrt((double)this.lengthSquared);
      }
   }
}
