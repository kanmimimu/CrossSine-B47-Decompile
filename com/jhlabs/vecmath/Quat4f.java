package com.jhlabs.vecmath;

public class Quat4f extends Tuple4f {
   public Quat4f() {
      this(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public Quat4f(float[] x) {
      super.x = x[0];
      super.y = x[1];
      super.z = x[2];
      super.w = x[3];
   }

   public Quat4f(float x, float y, float z, float w) {
      super.x = x;
      super.y = y;
      super.z = z;
      super.w = w;
   }

   public Quat4f(Quat4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public Quat4f(Tuple4f t) {
      super.x = t.x;
      super.y = t.y;
      super.z = t.z;
      super.w = t.w;
   }

   public void set(AxisAngle4f a) {
      float halfTheta = a.angle * 0.5F;
      float cosHalfTheta = (float)Math.cos((double)halfTheta);
      float sinHalfTheta = (float)Math.sin((double)halfTheta);
      super.x = a.x * sinHalfTheta;
      super.y = a.y * sinHalfTheta;
      super.z = a.z * sinHalfTheta;
      super.w = cosHalfTheta;
   }

   public void normalize() {
      float d = 1.0F / (super.x * super.x + super.y * super.y + super.z * super.z + super.w * super.w);
      super.x *= d;
      super.y *= d;
      super.z *= d;
      super.w *= d;
   }

   public void set(Matrix4f m) {
      float tr = m.m00 + m.m11 + m.m22;
      if ((double)tr > (double)0.0F) {
         float s = (float)Math.sqrt((double)(tr + 1.0F));
         super.w = s / 2.0F;
         s = 0.5F / s;
         super.x = (m.m12 - m.m21) * s;
         super.y = (m.m20 - m.m02) * s;
         super.z = (m.m01 - m.m10) * s;
      } else {
         int i = 0;
         if (m.m11 > m.m00) {
            i = 1;
            if (m.m22 > m.m11) {
               i = 2;
            }
         } else if (m.m22 > m.m00) {
            i = 2;
         }

         switch (i) {
            case 0:
               float s = (float)Math.sqrt((double)(m.m00 - (m.m11 + m.m22) + 1.0F));
               super.x = s * 0.5F;
               if ((double)s != (double)0.0F) {
                  s = 0.5F / s;
               }

               super.w = (m.m12 - m.m21) * s;
               super.y = (m.m01 + m.m10) * s;
               super.z = (m.m02 + m.m20) * s;
               break;
            case 1:
               float var7 = (float)Math.sqrt((double)(m.m11 - (m.m22 + m.m00) + 1.0F));
               super.y = var7 * 0.5F;
               if ((double)var7 != (double)0.0F) {
                  var7 = 0.5F / var7;
               }

               super.w = (m.m20 - m.m02) * var7;
               super.z = (m.m12 + m.m21) * var7;
               super.x = (m.m10 + m.m01) * var7;
               break;
            case 2:
               float var6 = (float)Math.sqrt((double)(m.m00 - (m.m11 + m.m22) + 1.0F));
               super.z = var6 * 0.5F;
               if ((double)var6 != (double)0.0F) {
                  var6 = 0.5F / var6;
               }

               super.w = (m.m01 - m.m10) * var6;
               super.x = (m.m20 + m.m02) * var6;
               super.y = (m.m21 + m.m12) * var6;
         }
      }

   }
}
