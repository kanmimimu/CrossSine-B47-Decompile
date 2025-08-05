package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;

public final class DataPaletteImpl implements DataPalette {
   static final int DEFAULT_INITIAL_SIZE = 16;
   final IntList palette;
   final Int2IntMap inversePalette;
   final int sizeBits;
   ChunkData values;

   public DataPaletteImpl(int valuesLength) {
      this(valuesLength, 16);
   }

   public DataPaletteImpl(int valuesLength, int initialSize) {
      this.values = new EmptyChunkData(valuesLength);
      this.sizeBits = Integer.numberOfTrailingZeros(valuesLength) / 3;
      this.palette = new IntArrayList(initialSize);
      this.inversePalette = new Int2IntOpenHashMap((int)((float)initialSize * 0.75F));
      this.inversePalette.defaultReturnValue(-1);
   }

   public int index(int x, int y, int z) {
      return (y << this.sizeBits | z) << this.sizeBits | x;
   }

   public int idAt(int sectionCoordinate) {
      int index = this.values.get(sectionCoordinate);
      return this.palette.getInt(index);
   }

   public void setIdAt(int sectionCoordinate, int id) {
      int index = this.inversePalette.get(id);
      if (index == -1) {
         index = this.palette.size();
         this.palette.add(id);
         this.inversePalette.put(id, index);
      }

      this.values.set(sectionCoordinate, index);
   }

   public int paletteIndexAt(int packedCoordinate) {
      return this.values.get(packedCoordinate);
   }

   public void setPaletteIndexAt(int sectionCoordinate, int index) {
      this.values.set(sectionCoordinate, index);
   }

   public int size() {
      return this.palette.size();
   }

   public int idByIndex(int index) {
      return this.palette.getInt(index);
   }

   public void setIdByIndex(int index, int id) {
      int oldId = this.palette.set(index, id);
      if (oldId != id) {
         this.inversePalette.put(id, index);
         if (this.inversePalette.get(oldId) == index) {
            this.inversePalette.remove(oldId);

            for(int i = 0; i < this.palette.size(); ++i) {
               if (this.palette.getInt(i) == oldId) {
                  this.inversePalette.put(oldId, i);
                  break;
               }
            }
         }

      }
   }

   public void replaceId(int oldId, int newId) {
      int index = this.inversePalette.remove(oldId);
      if (index != -1) {
         this.inversePalette.put(newId, index);

         for(int i = 0; i < this.palette.size(); ++i) {
            if (this.palette.getInt(i) == oldId) {
               this.palette.set(i, newId);
            }
         }

      }
   }

   public void addId(int id) {
      this.inversePalette.put(id, this.palette.size());
      this.palette.add(id);
   }

   public void clear() {
      this.palette.clear();
      this.inversePalette.clear();
   }

   private class EmptyChunkData implements ChunkData {
      final int size;

      public EmptyChunkData(int size) {
         this.size = size;
      }

      public int get(int idx) {
         return 0;
      }

      public void set(int idx, int val) {
         if (val != 0) {
            DataPaletteImpl.this.values = DataPaletteImpl.this.new ByteChunkData(this.size);
            DataPaletteImpl.this.values.set(idx, val);
         }

      }
   }

   private class ByteChunkData implements ChunkData {
      final byte[] data;

      public ByteChunkData(int size) {
         this.data = new byte[size];
      }

      public int get(int idx) {
         return this.data[idx] & 255;
      }

      public void set(int idx, int val) {
         if (val > 255) {
            DataPaletteImpl.this.values = new ShortChunkData(this.data);
            DataPaletteImpl.this.values.set(idx, val);
         } else {
            this.data[idx] = (byte)val;
         }
      }
   }

   private static class ShortChunkData implements ChunkData {
      final short[] data;

      public ShortChunkData(byte[] data) {
         this.data = new short[data.length];

         for(int i = 0; i < data.length; ++i) {
            this.data[i] = (short)(data[i] & 255);
         }

      }

      public int get(int idx) {
         return this.data[idx];
      }

      public void set(int idx, int val) {
         this.data[idx] = (short)val;
      }
   }

   interface ChunkData {
      int get(int var1);

      void set(int var1, int var2);
   }
}
