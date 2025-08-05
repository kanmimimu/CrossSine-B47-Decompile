package com.viaversion.viaversion.data.entity;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viaversion.api.data.entity.DimensionData;

public final class DimensionDataImpl implements DimensionData {
   private final int id;
   private final int minY;
   private final int height;

   public DimensionDataImpl(int id, int minY, int height) {
      this.id = id;
      this.minY = minY;
      this.height = height;
   }

   public DimensionDataImpl(int id, CompoundTag dimensionData) {
      this.id = id;
      NumberTag height = dimensionData.getNumberTag("height");
      if (height == null) {
         throw new IllegalArgumentException("height missing in dimension data: " + dimensionData);
      } else {
         this.height = height.asInt();
         NumberTag minY = dimensionData.getNumberTag("min_y");
         if (minY == null) {
            throw new IllegalArgumentException("min_y missing in dimension data: " + dimensionData);
         } else {
            this.minY = minY.asInt();
         }
      }
   }

   public static DimensionData withDefaultsFor(String key, int id) {
      DimensionDataImpl var10000;
      switch (key) {
         case "overworld":
         case "overworld_caves":
            var10000 = new DimensionDataImpl(id, -64, 384);
            break;
         case "the_nether":
         case "the_end":
            var10000 = new DimensionDataImpl(id, 0, 256);
            break;
         default:
            throw new IllegalArgumentException("Missing registry data for unknown dimension: " + key);
      }

      return var10000;
   }

   public int id() {
      return this.id;
   }

   public int minY() {
      return this.minY;
   }

   public int height() {
      return this.height;
   }

   public String toString() {
      int var5 = this.height;
      int var4 = this.minY;
      int var3 = this.id;
      return "DimensionDataImpl{id=" + var3 + ", minY=" + var4 + ", height=" + var5 + "}";
   }
}
