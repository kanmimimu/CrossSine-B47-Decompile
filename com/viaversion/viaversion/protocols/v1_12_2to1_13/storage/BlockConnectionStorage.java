package com.viaversion.viaversion.protocols.v1_12_2to1_13.storage;

import com.google.common.collect.EvictingQueue;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockConnectionStorage implements StorableObject {
   static Constructor fastUtilLongObjectHashMap;
   final Map blockStorage = this.createLongObjectMap();
   final Queue modified = EvictingQueue.create(5);
   long lastIndex = -1L;
   SectionData lastSection;

   public static void init() {
   }

   public void store(int x, int y, int z, int blockState) {
      long index = getChunkSectionIndex(x, y, z);
      SectionData section = this.getSection(index);
      if (section == null) {
         if (blockState == 0) {
            return;
         }

         this.blockStorage.put(index, section = new SectionData());
         this.lastSection = section;
         this.lastIndex = index;
      }

      section.setBlockAt(x, y, z, blockState);
   }

   public int get(int x, int y, int z) {
      long pair = getChunkSectionIndex(x, y, z);
      SectionData section = this.getSection(pair);
      return section == null ? 0 : section.blockAt(x, y, z);
   }

   public void remove(int x, int y, int z) {
      long index = getChunkSectionIndex(x, y, z);
      SectionData section = this.getSection(index);
      if (section != null) {
         section.setBlockAt(x, y, z, 0);
         if (section.nonEmptyBlocks() == 0) {
            this.removeSection(index);
         }

      }
   }

   public void markModified(BlockPosition pos) {
      if (!this.modified.contains(pos)) {
         this.modified.add(pos);
      }

   }

   public boolean recentlyModified(BlockPosition pos) {
      for(BlockPosition p : this.modified) {
         if (Math.abs(pos.x() - p.x()) + Math.abs(pos.y() - p.y()) + Math.abs(pos.z() - p.z()) <= 2) {
            return true;
         }
      }

      return false;
   }

   public void clear() {
      this.blockStorage.clear();
      this.lastSection = null;
      this.lastIndex = -1L;
      this.modified.clear();
   }

   public void unloadChunk(int x, int z) {
      for(int y = 0; y < 16; ++y) {
         this.unloadSection(x, y, z);
      }

   }

   public void unloadSection(int x, int y, int z) {
      this.removeSection(getChunkSectionIndex(x << 4, y << 4, z << 4));
   }

   @Nullable SectionData getSection(long index) {
      if (this.lastIndex == index) {
         return this.lastSection;
      } else {
         this.lastIndex = index;
         return this.lastSection = (SectionData)this.blockStorage.get(index);
      }
   }

   void removeSection(long index) {
      this.blockStorage.remove(index);
      if (this.lastIndex == index) {
         this.lastIndex = -1L;
         this.lastSection = null;
      }

   }

   static long getChunkSectionIndex(int x, int y, int z) {
      return ((long)(x >> 4) & 67108863L) << 38 | ((long)(y >> 4) & 4095L) << 26 | (long)(z >> 4) & 67108863L;
   }

   Map createLongObjectMap() {
      if (fastUtilLongObjectHashMap != null) {
         try {
            return (Map)fastUtilLongObjectHashMap.newInstance();
         } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            ((ReflectiveOperationException)e).printStackTrace();
         }
      }

      return new HashMap();
   }

   static {
      try {
         String className = "it" + ".unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap";
         fastUtilLongObjectHashMap = Class.forName(className).getConstructor();
         Via.getPlatform().getLogger().info("Using FastUtil Long2ObjectOpenHashMap for block connections");
      } catch (NoSuchMethodException | ClassNotFoundException var1) {
      }

   }

   private static final class SectionData {
      final short[] blockStates = new short[4096];
      short nonEmptyBlocks;

      SectionData() {
      }

      public int blockAt(int x, int y, int z) {
         return this.blockStates[encodeBlockPos(x, y, z)];
      }

      public void setBlockAt(int x, int y, int z, int blockState) {
         int index = encodeBlockPos(x, y, z);
         if (blockState != this.blockStates[index]) {
            this.blockStates[index] = (short)blockState;
            if (blockState == 0) {
               --this.nonEmptyBlocks;
            } else {
               ++this.nonEmptyBlocks;
            }

         }
      }

      public short nonEmptyBlocks() {
         return this.nonEmptyBlocks;
      }

      static int encodeBlockPos(int x, int y, int z) {
         return (y & 15) << 8 | (x & 15) << 4 | z & 15;
      }
   }
}
