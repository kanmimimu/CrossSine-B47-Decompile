package com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChunkLightStorage implements StorableObject {
   public static final byte[] FULL_LIGHT = new byte[2048];
   public static final byte[] EMPTY_LIGHT = new byte[2048];
   static Constructor fastUtilLongObjectHashMap;
   final Map storedLight = this.createLongObjectMap();

   public void setStoredLight(byte[][] skyLight, byte[][] blockLight, int x, int z) {
      this.storedLight.put(this.getChunkSectionIndex(x, z), new ChunkLight(skyLight, blockLight));
   }

   public ChunkLight getStoredLight(int x, int z) {
      return (ChunkLight)this.storedLight.get(this.getChunkSectionIndex(x, z));
   }

   public void clear() {
      this.storedLight.clear();
   }

   public void unloadChunk(int x, int z) {
      this.storedLight.remove(this.getChunkSectionIndex(x, z));
   }

   long getChunkSectionIndex(int x, int z) {
      return ((long)x & 67108863L) << 38 | (long)z & 67108863L;
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
      Arrays.fill(FULL_LIGHT, (byte)-1);
      Arrays.fill(EMPTY_LIGHT, (byte)0);

      try {
         fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor();
      } catch (NoSuchMethodException | ClassNotFoundException var1) {
      }

   }

   public static final class ChunkLight {
      final byte[][] skyLight;
      final byte[][] blockLight;

      public ChunkLight(byte[][] skyLight, byte[][] blockLight) {
         this.skyLight = skyLight;
         this.blockLight = blockLight;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ChunkLight that = (ChunkLight)o;
            return !Arrays.deepEquals(this.skyLight, that.skyLight) ? false : Arrays.deepEquals(this.blockLight, that.blockLight);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = Arrays.deepHashCode(this.skyLight);
         result = 31 * result + Arrays.deepHashCode(this.blockLight);
         return result;
      }

      public byte[][] skyLight() {
         return this.skyLight;
      }

      public byte[][] blockLight() {
         return this.blockLight;
      }

      public String toString() {
         return String.format("%s[skyLight=%s, blockLight=%s]", this.getClass().getSimpleName(), Objects.toString(this.skyLight), Objects.toString(this.blockLight));
      }
   }
}
