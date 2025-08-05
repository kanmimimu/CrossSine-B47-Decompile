package com.viaversion.viaversion.api.minecraft;

public final class Vector3f {
   private final float x;
   private final float y;
   private final float z;

   public Vector3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public float x() {
      return this.x;
   }

   public float y() {
      return this.y;
   }

   public float z() {
      return this.z;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Vector3f)) {
         return false;
      } else {
         Vector3f var2 = (Vector3f)var1;
         return Float.compare(this.x, var2.x) == 0 && Float.compare(this.y, var2.y) == 0 && Float.compare(this.z, var2.z) == 0;
      }
   }

   public int hashCode() {
      return ((0 * 31 + Float.hashCode(this.x)) * 31 + Float.hashCode(this.y)) * 31 + Float.hashCode(this.z);
   }

   public String toString() {
      return String.format("%s[x=%s, y=%s, z=%s]", this.getClass().getSimpleName(), Float.toString(this.x), Float.toString(this.y), Float.toString(this.z));
   }
}
