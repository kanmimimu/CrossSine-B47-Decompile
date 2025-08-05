package com.viaversion.viaversion.api.minecraft;

import java.util.EnumMap;
import java.util.Map;

public enum BlockFace {
   NORTH((byte)0, (byte)0, (byte)-1, BlockFace.EnumAxis.Z),
   SOUTH((byte)0, (byte)0, (byte)1, BlockFace.EnumAxis.Z),
   EAST((byte)1, (byte)0, (byte)0, BlockFace.EnumAxis.X),
   WEST((byte)-1, (byte)0, (byte)0, BlockFace.EnumAxis.X),
   TOP((byte)0, (byte)1, (byte)0, BlockFace.EnumAxis.Y),
   BOTTOM((byte)0, (byte)-1, (byte)0, BlockFace.EnumAxis.Y);

   public static final BlockFace[] HORIZONTAL = new BlockFace[]{NORTH, SOUTH, EAST, WEST};
   static final Map opposites = new EnumMap(BlockFace.class);
   final byte modX;
   final byte modY;
   final byte modZ;
   final EnumAxis axis;

   BlockFace(byte modX, byte modY, byte modZ, EnumAxis axis) {
      this.modX = modX;
      this.modY = modY;
      this.modZ = modZ;
      this.axis = axis;
   }

   public BlockFace opposite() {
      return (BlockFace)opposites.get(this);
   }

   public byte modX() {
      return this.modX;
   }

   public byte modY() {
      return this.modY;
   }

   public byte modZ() {
      return this.modZ;
   }

   public EnumAxis axis() {
      return this.axis;
   }

   // $FF: synthetic method
   static BlockFace[] $values() {
      return new BlockFace[]{NORTH, SOUTH, EAST, WEST, TOP, BOTTOM};
   }

   static {
      opposites.put(NORTH, SOUTH);
      opposites.put(SOUTH, NORTH);
      opposites.put(EAST, WEST);
      opposites.put(WEST, EAST);
      opposites.put(TOP, BOTTOM);
      opposites.put(BOTTOM, TOP);
   }

   public static enum EnumAxis {
      X,
      Y,
      Z;

      // $FF: synthetic method
      static EnumAxis[] $values() {
         return new EnumAxis[]{X, Y, Z};
      }
   }
}
