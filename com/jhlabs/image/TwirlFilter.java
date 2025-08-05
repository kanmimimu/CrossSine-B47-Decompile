package com.jhlabs.image;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TwirlFilter extends TransformFilter {
   static final long serialVersionUID = 1550445062822803342L;
   private float angle = 0.0F;
   private float centreX = 0.5F;
   private float centreY = 0.5F;
   private float radius = 100.0F;
   private float radius2 = 0.0F;
   private float icentreX;
   private float icentreY;

   public TwirlFilter() {
      ((TransformFilter)this).setEdgeAction(1);
   }

   public void setAngle(float angle) {
      this.angle = angle;
   }

   public float getAngle() {
      return this.angle;
   }

   public void setCentreX(float centreX) {
      this.centreX = centreX;
   }

   public float getCentreX() {
      return this.centreX;
   }

   public void setCentreY(float centreY) {
      this.centreY = centreY;
   }

   public float getCentreY() {
      return this.centreY;
   }

   public void setCentre(Point2D centre) {
      this.centreX = (float)centre.getX();
      this.centreY = (float)centre.getY();
   }

   public Point2D getCentre() {
      return new Point2D.Float(this.centreX, this.centreY);
   }

   public void setRadius(float radius) {
      this.radius = radius;
   }

   public float getRadius() {
      return this.radius;
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      this.icentreX = (float)src.getWidth() * this.centreX;
      this.icentreY = (float)src.getHeight() * this.centreY;
      if (this.radius == 0.0F) {
         this.radius = Math.min(this.icentreX, this.icentreY);
      }

      this.radius2 = this.radius * this.radius;
      return super.filter(src, dst);
   }

   protected void transformInverse(int x, int y, float[] out) {
      float dx = (float)x - this.icentreX;
      float dy = (float)y - this.icentreY;
      float distance = dx * dx + dy * dy;
      if (distance > this.radius2) {
         out[0] = (float)x;
         out[1] = (float)y;
      } else {
         distance = (float)Math.sqrt((double)distance);
         float a = (float)Math.atan2((double)dy, (double)dx) + this.angle * (this.radius - distance) / this.radius;
         out[0] = this.icentreX + distance * (float)Math.cos((double)a);
         out[1] = this.icentreY + distance * (float)Math.sin((double)a);
      }

   }

   public String toString() {
      return "Distort/Twirl...";
   }
}
