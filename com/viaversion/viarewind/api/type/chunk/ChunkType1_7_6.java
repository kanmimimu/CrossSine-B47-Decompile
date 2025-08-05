package com.viaversion.viarewind.api.type.chunk;

import com.viaversion.viarewind.api.minecraft.ExtendedBlockStorage;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

public class ChunkType1_7_6 extends Type {
   public static final ChunkType1_7_6 TYPE = new ChunkType1_7_6();

   public ChunkType1_7_6() {
      super(Chunk.class);
   }

   public Chunk read(ByteBuf byteBuf) {
      throw new UnsupportedOperationException();
   }

   public void write(ByteBuf output, Chunk chunk) {
      Pair<byte[], Short> chunkData;
      try {
         chunkData = serialize(chunk);
      } catch (IOException e) {
         throw new RuntimeException("Unable to serialize chunk", e);
      }

      byte[] data = (byte[])chunkData.key();
      short additionalBitMask = (Short)chunkData.value();
      Deflater deflater = new Deflater();

      byte[] compressedData;
      int compressedSize;
      try {
         deflater.setInput(data, 0, data.length);
         deflater.finish();
         compressedData = new byte[data.length];
         compressedSize = deflater.deflate(compressedData);
      } finally {
         deflater.end();
      }

      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      output.writeShort(chunk.getBitmask());
      output.writeShort(additionalBitMask);
      output.writeInt(compressedSize);
      output.writeBytes(compressedData, 0, compressedSize);
   }

   public static Pair serialize(Chunk chunk) throws IOException {
      ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];

      for(int i = 0; i < storageArrays.length; ++i) {
         ChunkSection section = chunk.getSections()[i];
         if (section != null) {
            ExtendedBlockStorage storage = storageArrays[i] = new ExtendedBlockStorage(section.getLight().hasSkyLight());

            for(int x = 0; x < 16; ++x) {
               for(int z = 0; z < 16; ++z) {
                  for(int y = 0; y < 16; ++y) {
                     int flatBlock = section.palette(PaletteType.BLOCKS).idAt(x, y, z);
                     storage.setBlockId(x, y, z, flatBlock >> 4);
                     storage.setBlockMetadata(x, y, z, flatBlock & 15);
                  }
               }
            }

            storage.getBlockLightArray().setHandle(section.getLight().getBlockLight());
            if (section.getLight().hasSkyLight()) {
               storage.getSkyLightArray().setHandle(section.getLight().getSkyLight());
            }
         }
      }

      ByteArrayOutputStream output = new ByteArrayOutputStream();

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            output.write(storageArrays[i].getBlockLSBArray());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            output.write(storageArrays[i].getBlockMetadataArray().getHandle());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            output.write(storageArrays[i].getBlockLightArray().getHandle());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0 && storageArrays[i].getSkyLightArray() != null) {
            output.write(storageArrays[i].getSkyLightArray().getHandle());
         }
      }

      short additionalBitMask = 0;

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0 && storageArrays[i].hasBlockMSBArray()) {
            additionalBitMask |= (short)(1 << i);
            output.write(storageArrays[i].getOrCreateBlockMSBArray().getHandle());
         }
      }

      if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
         for(int biome : chunk.getBiomeData()) {
            output.write(biome);
         }
      }

      return new Pair(output.toByteArray(), additionalBitMask);
   }
}
