package com.viaversion.viaversion.api.minecraft;

public class BlockPosition {
   protected final int x;
   protected final int y;
   protected final int z;

   public BlockPosition(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public BlockPosition getRelative(BlockFace face) {
      return new BlockPosition(this.x + face.modX(), this.y + face.modY(), this.z + face.modZ());
   }

   public int x() {
      return this.x;
   }

   public int y() {
      return this.y;
   }

   public int z() {
      return this.z;
   }

   public GlobalBlockPosition withDimension(String dimension) {
      return new GlobalBlockPosition(dimension, this.x, this.y, this.z);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         BlockPosition position = (BlockPosition)o;
         if (this.x != position.x) {
            return false;
         } else if (this.y != position.y) {
            return false;
         } else {
            return this.z == position.z;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.x;
      result = 31 * result + this.y;
      result = 31 * result + this.z;
      return result;
   }

   public String toString() {
      int var5 = this.z;
      int var4 = this.y;
      int var3 = this.x;
      return "BlockPosition{x=" + var3 + ", y=" + var4 + ", z=" + var5 + "}";
   }
}
