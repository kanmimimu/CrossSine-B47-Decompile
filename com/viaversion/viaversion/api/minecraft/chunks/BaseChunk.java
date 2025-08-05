package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.nbt.tag.CompoundTag;
import java.util.BitSet;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BaseChunk implements Chunk {
   protected final int x;
   protected final int z;
   protected final boolean fullChunk;
   protected boolean ignoreOldLightData;
   protected BitSet chunkSectionBitSet;
   protected int bitmask;
   protected ChunkSection[] sections;
   protected int[] biomeData;
   protected CompoundTag heightMap;
   protected final List blockEntities;

   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, @Nullable BitSet chunkSectionBitSet, ChunkSection[] sections, int @Nullable [] biomeData, @Nullable CompoundTag heightMap, List blockEntities) {
      this.x = x;
      this.z = z;
      this.fullChunk = fullChunk;
      this.ignoreOldLightData = ignoreOldLightData;
      this.chunkSectionBitSet = chunkSectionBitSet;
      this.sections = sections;
      this.biomeData = biomeData;
      this.heightMap = heightMap;
      this.blockEntities = blockEntities;
   }

   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, CompoundTag heightMap, List blockEntities) {
      this(x, z, fullChunk, ignoreOldLightData, (BitSet)null, sections, biomeData, heightMap, blockEntities);
      this.bitmask = bitmask;
   }

   public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, List blockEntities) {
      this(x, z, fullChunk, ignoreOldLightData, bitmask, sections, biomeData, (CompoundTag)null, blockEntities);
   }

   public boolean isBiomeData() {
      return this.biomeData != null;
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public boolean isFullChunk() {
      return this.fullChunk;
   }

   public boolean isIgnoreOldLightData() {
      return this.ignoreOldLightData;
   }

   public void setIgnoreOldLightData(boolean ignoreOldLightData) {
      this.ignoreOldLightData = ignoreOldLightData;
   }

   public int getBitmask() {
      return this.bitmask;
   }

   public void setBitmask(int bitmask) {
      this.bitmask = bitmask;
   }

   public @Nullable BitSet getChunkMask() {
      return this.chunkSectionBitSet;
   }

   public void setChunkMask(BitSet chunkSectionMask) {
      this.chunkSectionBitSet = chunkSectionMask;
   }

   public ChunkSection[] getSections() {
      return this.sections;
   }

   public void setSections(ChunkSection[] sections) {
      this.sections = sections;
   }

   public int @Nullable [] getBiomeData() {
      return this.biomeData;
   }

   public void setBiomeData(int @Nullable [] biomeData) {
      this.biomeData = biomeData;
   }

   public @Nullable CompoundTag getHeightMap() {
      return this.heightMap;
   }

   public void setHeightMap(CompoundTag heightMap) {
      this.heightMap = heightMap;
   }

   public List getBlockEntities() {
      return this.blockEntities;
   }

   public List blockEntities() {
      throw new UnsupportedOperationException();
   }
}
