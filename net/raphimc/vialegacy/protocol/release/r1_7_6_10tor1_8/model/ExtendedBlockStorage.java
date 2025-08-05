package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;

public class ExtendedBlockStorage {
   private final byte[] blockLSBArray = new byte[4096];
   private NibbleArray blockMSBArray;
   private final NibbleArray blockMetadataArray;
   private final NibbleArray blockLightArray;
   private NibbleArray skyLightArray;

   public ExtendedBlockStorage(boolean skylight) {
      this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length);
      this.blockLightArray = new NibbleArray(this.blockLSBArray.length);
      if (skylight) {
         this.skyLightArray = new NibbleArray(this.blockLSBArray.length);
      }

   }

   public int getBlockId(int x, int y, int z) {
      int value = this.blockLSBArray[ChunkSection.index(x, y, z)] & 255;
      if (this.blockMSBArray != null) {
         value |= this.blockMSBArray.get(x, y, z) << 8;
      }

      return value;
   }

   public void setBlockId(int x, int y, int z, int value) {
      this.blockLSBArray[ChunkSection.index(x, y, z)] = (byte)(value & 255);
      if (value > 255) {
         this.getOrCreateBlockMSBArray().set(x, y, z, (value & 3840) >> 8);
      } else if (this.blockMSBArray != null) {
         this.blockMSBArray.set(x, y, z, 0);
      }

   }

   public int getBlockMetadata(int x, int y, int z) {
      return this.blockMetadataArray.get(x, y, z);
   }

   public void setBlockMetadata(int x, int y, int z, int value) {
      this.blockMetadataArray.set(x, y, z, value);
   }

   public int getBlockLight(int x, int y, int z) {
      return this.blockLightArray.get(x, y, z);
   }

   public void setBlockLight(int x, int y, int z, int value) {
      this.blockLightArray.set(x, y, z, value);
   }

   public int getSkyLight(int x, int y, int z) {
      return this.skyLightArray.get(x, y, z);
   }

   public void setSkyLight(int x, int y, int z, int value) {
      this.skyLightArray.set(x, y, z, value);
   }

   public boolean hasBlockMSBArray() {
      return this.blockMSBArray != null;
   }

   public byte[] getBlockLSBArray() {
      return this.blockLSBArray;
   }

   public NibbleArray getOrCreateBlockMSBArray() {
      return this.blockMSBArray == null ? (this.blockMSBArray = new NibbleArray(this.blockLSBArray.length)) : this.blockMSBArray;
   }

   public NibbleArray getBlockMetadataArray() {
      return this.blockMetadataArray;
   }

   public NibbleArray getBlockLightArray() {
      return this.blockLightArray;
   }

   public NibbleArray getSkyLightArray() {
      return this.skyLightArray;
   }
}
