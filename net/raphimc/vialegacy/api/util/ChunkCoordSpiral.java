package net.raphimc.vialegacy.api.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import net.raphimc.vialegacy.api.model.ChunkCoord;

public class ChunkCoordSpiral implements Iterable {
   final ChunkCoord center;
   final ChunkCoord lowerBound;
   final ChunkCoord upperBound;
   final int step;
   boolean returnCenter;

   public ChunkCoordSpiral(ChunkCoord center, ChunkCoord lowerBound, ChunkCoord upperBound, int step) {
      this.returnCenter = true;
      this.center = center;
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
      this.step = step;
   }

   public ChunkCoordSpiral(ChunkCoord center, ChunkCoord radius, int step) {
      this(center, new ChunkCoord(center.chunkX - radius.chunkX, center.chunkZ - radius.chunkZ), new ChunkCoord(center.chunkX + radius.chunkX, center.chunkZ + radius.chunkZ), step);
   }

   public ChunkCoordSpiral(ChunkCoord center, ChunkCoord radius) {
      this(center, radius, 1);
   }

   public Iterator iterator() {
      return new Iterator() {
         int x;
         int z;
         float n;
         int floorN;
         int i;
         int j;

         {
            this.x = ChunkCoordSpiral.this.center.chunkX;
            this.z = ChunkCoordSpiral.this.center.chunkZ;
            this.n = 1.0F;
            this.floorN = 1;
            this.i = 0;
            this.j = 0;
         }

         public boolean hasNext() {
            return ChunkCoordSpiral.this.returnCenter || this.x >= ChunkCoordSpiral.this.lowerBound.chunkX && this.x <= ChunkCoordSpiral.this.upperBound.chunkX && this.z >= ChunkCoordSpiral.this.lowerBound.chunkZ && this.z <= ChunkCoordSpiral.this.upperBound.chunkZ;
         }

         public ChunkCoord next() {
            if (ChunkCoordSpiral.this.returnCenter) {
               ChunkCoordSpiral.this.returnCenter = false;
               return new ChunkCoord(this.x, this.z);
            } else {
               this.floorN = (int)Math.floor((double)this.n);
               if (this.j < this.floorN) {
                  switch (this.i % 4) {
                     case 0:
                        this.z += ChunkCoordSpiral.this.step;
                        break;
                     case 1:
                        this.x += ChunkCoordSpiral.this.step;
                        break;
                     case 2:
                        this.z -= ChunkCoordSpiral.this.step;
                        break;
                     case 3:
                        this.x -= ChunkCoordSpiral.this.step;
                  }

                  ++this.j;
                  return new ChunkCoord(this.x, this.z);
               } else {
                  this.j = 0;
                  this.n = (float)((double)this.n + (double)0.5F);
                  ++this.i;
                  return this.next();
               }
            }
         }
      };
   }

   public Spliterator spliterator() {
      return Spliterators.spliteratorUnknownSize(this.iterator(), 16);
   }
}
