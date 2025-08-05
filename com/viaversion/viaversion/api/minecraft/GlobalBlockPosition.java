package com.viaversion.viaversion.api.minecraft;

public final class GlobalBlockPosition extends BlockPosition {
   private final String dimension;

   public GlobalBlockPosition(String dimension, int x, int y, int z) {
      super(x, y, z);
      this.dimension = dimension;
   }

   public String dimension() {
      return this.dimension;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GlobalBlockPosition position = (GlobalBlockPosition)o;
         if (this.x != position.x) {
            return false;
         } else if (this.y != position.y) {
            return false;
         } else {
            return this.z != position.z ? false : this.dimension.equals(position.dimension);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.dimension.hashCode();
      result = 31 * result + this.x;
      result = 31 * result + this.y;
      result = 31 * result + this.z;
      return result;
   }

   public String toString() {
      int var6 = this.z;
      int var5 = this.y;
      int var4 = this.x;
      String var3 = this.dimension;
      return "GlobalBlockPosition{dimension='" + var3 + "', x=" + var4 + ", y=" + var5 + ", z=" + var6 + "}";
   }
}
