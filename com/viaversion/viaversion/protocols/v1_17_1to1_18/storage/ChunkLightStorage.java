package com.viaversion.viaversion.protocols.v1_17_1to1_18.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ChunkLightStorage implements StorableObject {
   final Map lightPackets = new HashMap();
   final Set loadedChunks = new HashSet();

   public void storeLight(int x, int z, ChunkLight chunkLight) {
      this.lightPackets.put(this.getChunkSectionIndex(x, z), chunkLight);
   }

   public @Nullable ChunkLight removeLight(int x, int z) {
      return (ChunkLight)this.lightPackets.remove(this.getChunkSectionIndex(x, z));
   }

   public @Nullable ChunkLight getLight(int x, int z) {
      return (ChunkLight)this.lightPackets.get(this.getChunkSectionIndex(x, z));
   }

   public boolean addLoadedChunk(int x, int z) {
      return this.loadedChunks.add(this.getChunkSectionIndex(x, z));
   }

   public boolean isLoaded(int x, int z) {
      return this.loadedChunks.contains(this.getChunkSectionIndex(x, z));
   }

   public void clear(int x, int z) {
      long index = this.getChunkSectionIndex(x, z);
      this.lightPackets.remove(index);
      this.loadedChunks.remove(index);
   }

   public void clear() {
      this.loadedChunks.clear();
      this.lightPackets.clear();
   }

   long getChunkSectionIndex(int x, int z) {
      return ((long)x & 67108863L) << 38 | (long)z & 67108863L;
   }

   public static final class ChunkLight {
      final boolean trustEdges;
      final long[] skyLightMask;
      final long[] blockLightMask;
      final long[] emptySkyLightMask;
      final long[] emptyBlockLightMask;
      final byte[][] skyLight;
      final byte[][] blockLight;

      public ChunkLight(boolean trustEdges, long[] skyLightMask, long[] blockLightMask, long[] emptySkyLightMask, long[] emptyBlockLightMask, byte[][] skyLight, byte[][] blockLight) {
         this.trustEdges = trustEdges;
         this.skyLightMask = skyLightMask;
         this.blockLightMask = blockLightMask;
         this.emptySkyLightMask = emptySkyLightMask;
         this.emptyBlockLightMask = emptyBlockLightMask;
         this.skyLight = skyLight;
         this.blockLight = blockLight;
      }

      public boolean trustEdges() {
         return this.trustEdges;
      }

      public long[] skyLightMask() {
         return this.skyLightMask;
      }

      public long[] blockLightMask() {
         return this.blockLightMask;
      }

      public long[] emptySkyLightMask() {
         return this.emptySkyLightMask;
      }

      public long[] emptyBlockLightMask() {
         return this.emptyBlockLightMask;
      }

      public byte[][] skyLight() {
         return this.skyLight;
      }

      public byte[][] blockLight() {
         return this.blockLight;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ChunkLight)) {
            return false;
         } else {
            ChunkLight var2 = (ChunkLight)var1;
            return this.trustEdges == var2.trustEdges && Objects.equals(this.skyLightMask, var2.skyLightMask) && Objects.equals(this.blockLightMask, var2.blockLightMask) && Objects.equals(this.emptySkyLightMask, var2.emptySkyLightMask) && Objects.equals(this.emptyBlockLightMask, var2.emptyBlockLightMask) && Objects.equals(this.skyLight, var2.skyLight) && Objects.equals(this.blockLight, var2.blockLight);
         }
      }

      public int hashCode() {
         return ((((((0 * 31 + Boolean.hashCode(this.trustEdges)) * 31 + Objects.hashCode(this.skyLightMask)) * 31 + Objects.hashCode(this.blockLightMask)) * 31 + Objects.hashCode(this.emptySkyLightMask)) * 31 + Objects.hashCode(this.emptyBlockLightMask)) * 31 + Objects.hashCode(this.skyLight)) * 31 + Objects.hashCode(this.blockLight);
      }

      public String toString() {
         return String.format("%s[trustEdges=%s, skyLightMask=%s, blockLightMask=%s, emptySkyLightMask=%s, emptyBlockLightMask=%s, skyLight=%s, blockLight=%s]", this.getClass().getSimpleName(), Boolean.toString(this.trustEdges), Objects.toString(this.skyLightMask), Objects.toString(this.blockLightMask), Objects.toString(this.emptySkyLightMask), Objects.toString(this.emptyBlockLightMask), Objects.toString(this.skyLight), Objects.toString(this.blockLight));
      }
   }
}
