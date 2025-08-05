package com.viaversion.viarewind.api.minecraft.math;

import java.util.Objects;

public class Vector3d {
   double x;
   double y;
   double z;

   public Vector3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3d() {
   }

   public void set(Vector3d vec) {
      this.x = vec.x;
      this.y = vec.y;
      this.z = vec.z;
   }

   public Vector3d set(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   public Vector3d multiply(double a) {
      this.x *= a;
      this.y *= a;
      this.z *= a;
      return this;
   }

   public Vector3d add(Vector3d vec) {
      this.x += vec.x;
      this.y += vec.y;
      this.z += vec.z;
      return this;
   }

   public Vector3d substract(Vector3d vec) {
      this.x -= vec.x;
      this.y -= vec.y;
      this.z -= vec.z;
      return this;
   }

   public double length() {
      return Math.sqrt(this.lengthSquared());
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public Vector3d normalize() {
      double length = this.length();
      this.multiply((double)1.0F / length);
      return this;
   }

   public Vector3d clone() {
      return new Vector3d(this.x, this.y, this.z);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Vector3d vector3d = (Vector3d)o;
         return Double.compare(vector3d.x, this.x) == 0 && Double.compare(vector3d.y, this.y) == 0 && Double.compare(vector3d.z, this.z) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.x, this.y, this.z});
   }

   public String toString() {
      double var7 = this.z;
      double var5 = this.y;
      double var3 = this.x;
      return "Vector3d{x=" + var3 + ", y=" + var5 + ", z=" + var7 + "}";
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setZ(double z) {
      this.z = z;
   }
}
