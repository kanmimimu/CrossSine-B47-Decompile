package com.jhlabs.image;

import java.awt.image.BufferedImage;

public class PolarFilter extends TransformFilter {
   public static final int RECT_TO_POLAR = 0;
   public static final int POLAR_TO_RECT = 1;
   public static final int INVERT_IN_CIRCLE = 2;
   private int type;
   private float width;
   private float height;
   private float centreX;
   private float centreY;
   private float radius;

   public PolarFilter() {
      this(0);
   }

   public PolarFilter(int type) {
      this.type = type;
      ((TransformFilter)this).setEdgeAction(1);
   }

   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
      this.width = (float)src.getWidth();
      this.height = (float)src.getHeight();
      this.centreX = this.width / 2.0F;
      this.centreY = this.height / 2.0F;
      this.radius = Math.max(this.centreY, this.centreX);
      return super.filter(src, dst);
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }

   private float sqr(float x) {
      return x * x;
   }

   protected void transformInverse(int x, int y, float[] out) {
      float r = 0.0F;
      switch (this.type) {
         case 0:
            float theta = 0.0F;
            if ((float)x >= this.centreX) {
               if ((float)y > this.centreY) {
                  theta = (float)Math.PI - (float)Math.atan((double)(((float)x - this.centreX) / ((float)y - this.centreY)));
                  r = (float)Math.sqrt((double)(this.sqr((float)x - this.centreX) + this.sqr((float)y - this.centreY)));
               } else if ((float)y < this.centreY) {
                  theta = (float)Math.atan((double)(((float)x - this.centreX) / (this.centreY - (float)y)));
                  r = (float)Math.sqrt((double)(this.sqr((float)x - this.centreX) + this.sqr(this.centreY - (float)y)));
               } else {
                  theta = ((float)Math.PI / 2F);
                  r = (float)x - this.centreX;
               }
            } else if ((float)x < this.centreX) {
               if ((float)y < this.centreY) {
                  theta = ((float)Math.PI * 2F) - (float)Math.atan((double)((this.centreX - (float)x) / (this.centreY - (float)y)));
                  r = (float)Math.sqrt((double)(this.sqr(this.centreX - (float)x) + this.sqr(this.centreY - (float)y)));
               } else if ((float)y > this.centreY) {
                  theta = (float)Math.PI + (float)Math.atan((double)((this.centreX - (float)x) / ((float)y - this.centreY)));
                  r = (float)Math.sqrt((double)(this.sqr(this.centreX - (float)x) + this.sqr((float)y - this.centreY)));
               } else {
                  theta = ((float)Math.PI * 1.5F);
                  r = this.centreX - (float)x;
               }
            }

            float m;
            if ((float)x != this.centreX) {
               m = Math.abs(((float)y - this.centreY) / ((float)x - this.centreX));
            } else {
               m = 0.0F;
            }

            if (m <= this.height / this.width) {
               if ((float)x == this.centreX) {
                  float xmax = 0.0F;
                  float ymax = this.centreY;
               } else {
                  float xmax = this.centreX;
                  float var26 = m * xmax;
               }
            } else {
               float ymax = this.centreY;
               float var27 = ymax / m;
            }

            out[0] = this.width - 1.0F - (this.width - 1.0F) / ((float)Math.PI * 2F) * theta;
            out[1] = this.height * r / this.radius;
            break;
         case 1:
            float theta = (float)x / this.width * ((float)Math.PI * 2F);
            float theta2;
            if (theta >= ((float)Math.PI * 1.5F)) {
               theta2 = ((float)Math.PI * 2F) - theta;
            } else if (theta >= (float)Math.PI) {
               theta2 = theta - (float)Math.PI;
            } else if (theta >= ((float)Math.PI / 2F)) {
               theta2 = (float)Math.PI - theta;
            } else {
               theta2 = theta;
            }

            float t = (float)Math.tan((double)theta2);
            float m;
            if (t != 0.0F) {
               m = 1.0F / t;
            } else {
               m = 0.0F;
            }

            if (m <= this.height / this.width) {
               if (theta2 == 0.0F) {
                  float xmax = 0.0F;
                  float ymax = this.centreY;
               } else {
                  float xmax = this.centreX;
                  float var10000 = m * xmax;
               }
            } else {
               float ymax = this.centreY;
               float var25 = ymax / m;
            }

            r = this.radius * ((float)y / this.height);
            float nx = -r * (float)Math.sin((double)theta2);
            float ny = r * (float)Math.cos((double)theta2);
            if (theta >= ((float)Math.PI * 1.5F)) {
               out[0] = this.centreX - nx;
               out[1] = this.centreY - ny;
            } else if ((double)theta >= Math.PI) {
               out[0] = this.centreX - nx;
               out[1] = this.centreY + ny;
            } else if ((double)theta >= (Math.PI / 2D)) {
               out[0] = this.centreX + nx;
               out[1] = this.centreY + ny;
            } else {
               out[0] = this.centreX + nx;
               out[1] = this.centreY - ny;
            }
            break;
         case 2:
            float dx = (float)x - this.centreX;
            float dy = (float)y - this.centreY;
            float distance2 = dx * dx + dy * dy;
            out[0] = this.centreX + this.centreX * this.centreX * dx / distance2;
            out[1] = this.centreY + this.centreY * this.centreY * dy / distance2;
      }

   }

   public String toString() {
      return "Distort/Polar Coordinates...";
   }
}
