package com.viaversion.viaversion.api.minecraft.chunks;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface ChunkSection {
   int SIZE = 4096;
   int BIOME_SIZE = 64;

   static int index(int x, int y, int z) {
      return y << 8 | z << 4 | x;
   }

   static int xFromIndex(int idx) {
      return idx & 15;
   }

   static int yFromIndex(int idx) {
      return idx >> 8 & 15;
   }

   static int zFromIndex(int idx) {
      return idx >> 4 & 15;
   }

   int getNonAirBlocksCount();

   void setNonAirBlocksCount(int var1);

   default boolean hasLight() {
      return this.getLight() != null;
   }

   @Nullable ChunkSectionLight getLight();

   void setLight(@Nullable ChunkSectionLight var1);

   @Nullable DataPalette palette(PaletteType var1);

   void addPalette(PaletteType var1, DataPalette var2);

   void removePalette(PaletteType var1);
}
