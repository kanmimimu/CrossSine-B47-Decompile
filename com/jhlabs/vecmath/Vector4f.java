package com.jhlabs.vecmath;

public class Vector4f extends Tuple4f {
   public Vector4f() {
      this(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public Vector4f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
      super.w = x[2];
   }

   public Vector4f(float x, float y, float z, float w) {
      super.x = x;
      super.y = y;
      super.z = z;
      super.w = w;
   }

   public Vector4f(Vector4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public Vector4f(Tuple4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public float dot(Vector4f v) {
      return v.x * super.x + v.y * super.y + v.z * super.z + v.w * super.w;
   }

   public float length() {
      return (float)Math.sqrt((double)(super.x * super.x + super.y * super.y + super.z * super.z + super.w * super.w));
   }

   public void normalize() {
      float d = 1.0F / (super.x * super.x + super.y * super.y + super.z * super.z + super.w * super.w);
      super.x *= d;
      super.y *= d;
      super.z *= d;
      super.w *= d;
   }
}
