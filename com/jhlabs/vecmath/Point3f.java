package com.jhlabs.vecmath;

public class Point3f extends Tuple3f {
   public Point3f() {
      this(0.0F, 0.0F, 0.0F);
   }

   public Point3f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
   }

   public Point3f(float x, float y, float z) {
      super.x = x;
      super.y = y;
      super.z = z;
   }

   public Point3f(Point3f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
   }

   public Point3f(Tuple3f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
   }

   public float distanceL1(Point3f p) {
      return Math.abs(super.x - p.x) + Math.abs(super.y - p.y) + Math.abs(super.z - p.z);
   }

   public float distanceSquared(Point3f p) {
      float dx = super.x - p.x;
      float dy = super.y - p.y;
      float dz = super.z - p.z;
      return dx * dx + dy * dy + dz * dz;
   }

   public float distance(Point3f p) {
      float dx = super.x - p.x;
      float dy = super.y - p.y;
      float dz = super.z - p.z;
      return (float)Math.sqrt((double)(dx * dx + dy * dy + dz * dz));
   }
}
