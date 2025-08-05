package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.model;

import com.viaversion.viaversion.api.minecraft.BlockPosition;

public class ClassicLevel {
   private final int sizeX;
   private final int sizeY;
   private final int sizeZ;
   private final byte[] blocks;
   private final int[] lightBlocking;

   public ClassicLevel(int sizeX, int sizeY, int sizeZ) {
      this(sizeX, sizeY, sizeZ, new byte[sizeX * sizeY * sizeZ], new int[sizeX * sizeZ]);
   }

   public ClassicLevel(int sizeX, int sizeY, int sizeZ, byte[] blocks) {
      this(sizeX, sizeY, sizeZ, blocks, new int[sizeX * sizeZ]);
   }

   public ClassicLevel(int sizeX, int sizeY, int sizeZ, byte[] blocks, int[] lightBlocking) {
      this.sizeX = sizeX;
      this.sizeY = sizeY;
      this.sizeZ = sizeZ;
      this.blocks = blocks;
      this.lightBlocking = lightBlocking;
   }

   public void calculateLight(int startX, int startZ, int sizeX, int sizeZ) {
      for(int x = startX; x < startX + sizeX; ++x) {
         for(int z = startZ; z < startZ + sizeZ; ++z) {
            if (this.isInBounds(x, 0, z)) {
               int y;
               for(y = this.sizeY - 1; y > 0 && !this.isLightBlocking(x, y, z); --y) {
               }

               this.lightBlocking[x + z * this.sizeX] = y;
            }
         }
      }

   }

   public int getSizeX() {
      return this.sizeX;
   }

   public int getSizeY() {
      return this.sizeY;
   }

   public int getSizeZ() {
      return this.sizeZ;
   }

   public int getBlock(BlockPosition position) {
      return this.getBlock(position.x(), position.y(), position.z());
   }

   public int getBlock(int x, int y, int z) {
      return this.isInBounds(x, y, z) ? this.blocks[(y * this.sizeZ + z) * this.sizeX + x] & 255 : 0;
   }

   public void setBlock(BlockPosition position, int block) {
      this.setBlock(position.x(), position.y(), position.z(), block);
   }

   public void setBlock(int x, int y, int z, int block) {
      if (this.isInBounds(x, y, z)) {
         this.blocks[(y * this.sizeZ + z) * this.sizeX + x] = (byte)block;
         this.calculateLight(x, z, 1, 1);
      }

   }

   public boolean isLightBlocking(BlockPosition position) {
      return this.isLightBlocking(position.x(), position.y(), position.z());
   }

   public boolean isLightBlocking(int x, int y, int z) {
      boolean var10000;
      switch (this.getBlock(x, y, z)) {
         case 0:
         case 8:
         case 9:
         case 10:
         case 11:
         case 18:
         case 20:
         case 37:
         case 38:
         case 39:
         case 40:
            var10000 = false;
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 19:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         default:
            var10000 = true;
      }

      return var10000;
   }

   public boolean isLit(BlockPosition position) {
      return this.isLit(position.x(), position.y(), position.z());
   }

   public boolean isLit(int x, int y, int z) {
      return !this.isInBounds(x, y, z) || y >= this.lightBlocking[x + z * this.sizeX];
   }

   public boolean isInBounds(int x, int y, int z) {
      return x >= 0 && y >= 0 && z >= 0 && x < this.sizeX && y < this.sizeY && z < this.sizeZ;
   }
}
