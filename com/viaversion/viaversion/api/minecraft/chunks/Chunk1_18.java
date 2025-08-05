package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import java.util.BitSet;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Chunk1_18 implements Chunk {
   protected final int x;
   protected final int z;
   protected ChunkSection[] sections;
   protected CompoundTag heightMap;
   protected final List blockEntities;

   public Chunk1_18(int x, int z, ChunkSection[] sections, CompoundTag heightMap, List blockEntities) {
      this.x = x;
      this.z = z;
      this.sections = sections;
      this.heightMap = heightMap;
      this.blockEntities = blockEntities;
   }

   public boolean isBiomeData() {
      return false;
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public boolean isFullChunk() {
      return true;
   }

   public boolean isIgnoreOldLightData() {
      return false;
   }

   public void setIgnoreOldLightData(boolean ignoreOldLightData) {
      throw new UnsupportedOperationException();
   }

   public int getBitmask() {
      return -1;
   }

   public void setBitmask(int bitmask) {
      throw new UnsupportedOperationException();
   }

   public @Nullable BitSet getChunkMask() {
      return null;
   }

   public void setChunkMask(BitSet chunkSectionMask) {
      throw new UnsupportedOperationException();
   }

   public ChunkSection[] getSections() {
      return this.sections;
   }

   public void setSections(ChunkSection[] sections) {
      this.sections = sections;
   }

   public int @Nullable [] getBiomeData() {
      return null;
   }

   public void setBiomeData(int @Nullable [] biomeData) {
      throw new UnsupportedOperationException();
   }

   public @Nullable CompoundTag getHeightMap() {
      return this.heightMap;
   }

   public void setHeightMap(CompoundTag heightMap) {
      this.heightMap = heightMap;
   }

   public List getBlockEntities() {
      throw new UnsupportedOperationException();
   }

   public List blockEntities() {
      return this.blockEntities;
   }
}
