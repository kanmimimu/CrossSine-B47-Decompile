package com.jhlabs.vecmath;

public class Vector3f extends Tuple3f {
   public Vector3f() {
      this(0.0F, 0.0F, 0.0F);
   }

   public Vector3f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
   }

   public Vector3f(float x, float y, float z) {
      super.x = x;
      super.y = y;
      super.z = z;
   }

   public Vector3f(Vector3f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
   }

   public Vector3f(Tuple3f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
   }

   public float angle(Vector3f v) {
      return (float)Math.acos((double)(this.dot(v) / (this.length() * v.length())));
   }

   public float dot(Vector3f v) {
      return v.x * super.x + v.y * super.y + v.z * super.z;
   }

   public void cross(Vector3f v1, Vector3f v2) {
      super.x = v1.y * v2.z - v1.z * v2.y;
      super.y = v1.z * v2.x - v1.x * v2.z;
      super.z = v1.x * v2.y - v1.y * v2.x;
   }

   public float length() {
      return (float)Math.sqrt((double)(super.x * super.x + super.y * super.y + super.z * super.z));
   }

   public void normalize() {
      float d = 1.0F / (float)Math.sqrt((double)(super.x * super.x + super.y * super.y + super.z * super.z));
      super.x *= d;
      super.y *= d;
      super.z *= d;
   }
}
