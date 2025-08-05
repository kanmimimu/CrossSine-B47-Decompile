package com.jhlabs.image;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SphereFilter extends TransformFilter {
   static final long serialVersionUID = -8148404526162968279L;
   private float a = 0.0F;
   private float b = 0.0F;
   private float a2 = 0.0F;
   private float b2 = 0.0F;
   private float centreX = 0.5F;
   private float centreY = 0.5F;
   private float refractionIndex = 1.5F;
   private float icentreX;
   private float icentreY;

   public SphereFilter() {
      ((TransformFilter)this).setEdgeAction(1);
      this.setRadius(100.0F);
   }

   public void setRefractionIndex(float refractionIndex) {
      this.refractionIndex = refractionIndex;
   }

   public float getRefractionIndex() {
      return this.refractionIndex;
   }

   public void setRadius(float r) {
      this.a = r;
      this.b = r;
   }

   public float getRadius() {
      return this.a;
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

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      int width = src.getWidth();
      int height = src.getHeight();
      this.icentreX = (float)width * this.centreX;
      this.icentreY = (float)height * this.centreY;
      if (this.a == 0.0F) {
         this.a = (float)(width / 2);
      }

      if (this.b == 0.0F) {
         this.b = (float)(height / 2);
      }

      this.a2 = this.a * this.a;
      this.b2 = this.b * this.b;
      return super.filter(src, dst);
   }

   protected void transformInverse(int x, int y, float[] out) {
      float dx = (float)x - this.icentreX;
      float dy = (float)y - this.icentreY;
      float x2 = dx * dx;
      float y2 = dy * dy;
      if (y2 >= this.b2 - this.b2 * x2 / this.a2) {
         out[0] = (float)x;
         out[1] = (float)y;
      } else {
         float rRefraction = 1.0F / this.refractionIndex;
         float z = (float)Math.sqrt((double)((1.0F - x2 / this.a2 - y2 / this.b2) * this.a * this.b));
         float z2 = z * z;
         float xAngle = (float)Math.acos((double)dx / Math.sqrt((double)(x2 + z2)));
         float angle1 = ((float)Math.PI / 2F) - xAngle;
         float angle2 = (float)Math.asin(Math.sin((double)angle1) * (double)rRefraction);
         angle2 = ((float)Math.PI / 2F) - xAngle - angle2;
         out[0] = (float)x - (float)Math.tan((double)angle2) * z;
         float yAngle = (float)Math.acos((double)dy / Math.sqrt((double)(y2 + z2)));
         angle1 = ((float)Math.PI / 2F) - yAngle;
         angle2 = (float)Math.asin(Math.sin((double)angle1) * (double)rRefraction);
         angle2 = ((float)Math.PI / 2F) - yAngle - angle2;
         out[1] = (float)y - (float)Math.tan((double)angle2) * z;
      }

   }

   public String toString() {
      return "Distort/Sphere...";
   }
}
