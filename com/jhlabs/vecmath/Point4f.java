package com.jhlabs.vecmath;

public class Point4f extends Tuple4f {
   public Point4f() {
      this(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public Point4f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
      super.w = x[3];
   }

   public Point4f(float x, float y, float z, float w) {
      super.x = x;
      super.y = y;
      super.z = z;
      super.w = w;
   }

   public Point4f(Point4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public Point4f(Tuple4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public float distanceL1(Point4f p) {
      return Math.abs(super.x - p.x) + Math.abs(super.y - p.y) + Math.abs(super.z - p.z) + Math.abs(super.w - p.w);
   }

   public float distanceSquared(Point4f p) {
      float dx = super.x - p.x;
      float dy = super.y - p.y;
      float dz = super.z - p.z;
      float dw = super.w - p.w;
      return dx * dx + dy * dy + dz * dz + dw * dw;
   }

   public float distance(Point4f p) {
      float dx = super.x - p.x;
      float dy = super.y - p.y;
      float dz = super.z - p.z;
      float dw = super.w - p.w;
      return (float)Math.sqrt((double)(dx * dx + dy * dy + dz * dz + dw * dw));
   }
}
