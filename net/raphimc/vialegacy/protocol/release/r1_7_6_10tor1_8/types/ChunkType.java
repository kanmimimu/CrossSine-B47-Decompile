package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.ExtendedBlockStorage;

public class ChunkType extends Type {
   private final boolean hasSkyLight;

   public ChunkType(boolean hasSkyLight) {
      super(Chunk.class);
      this.hasSkyLight = hasSkyLight;
   }

   protected void readUnusedInt(ByteBuf byteBuf) {
   }

   protected void writeUnusedInt(ByteBuf byteBuf, Chunk chunk) {
   }

   public Chunk read(ByteBuf byteBuf) {
      int chunkX = byteBuf.readInt();
      int chunkZ = byteBuf.readInt();
      boolean fullChunk = byteBuf.readBoolean();
      short primaryBitMask = byteBuf.readShort();
      short additionalBitMask = byteBuf.readShort();
      int compressedSize = byteBuf.readInt();
      this.readUnusedInt(byteBuf);
      byte[] data = new byte[compressedSize];
      byteBuf.readBytes(data);
      byte[] uncompressedData = new byte[getSize(primaryBitMask, additionalBitMask, fullChunk, this.hasSkyLight)];
      Inflater inflater = new Inflater();

      try {
         inflater.setInput(data, 0, compressedSize);
         inflater.inflate(uncompressedData);
      } catch (DataFormatException var15) {
         throw new RuntimeException("Bad compressed data format");
      } finally {
         inflater.end();
      }

      return (Chunk)(fullChunk && primaryBitMask == 0 ? new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], (int[])null, new ArrayList()) : deserialize(chunkX, chunkZ, fullChunk, this.hasSkyLight, primaryBitMask, additionalBitMask, uncompressedData));
   }

   public void write(ByteBuf byteBuf, Chunk chunk) {
      Pair<byte[], Short> chunkData = serialize(chunk);
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

      byteBuf.writeInt(chunk.getX());
      byteBuf.writeInt(chunk.getZ());
      byteBuf.writeBoolean(chunk.isFullChunk());
      byteBuf.writeShort(chunk.getBitmask());
      byteBuf.writeShort(additionalBitMask);
      byteBuf.writeInt(compressedSize);
      this.writeUnusedInt(byteBuf, chunk);
      byteBuf.writeBytes(compressedData, 0, compressedSize);
   }

   public static Chunk deserialize(int chunkX, int chunkZ, boolean fullChunk, boolean skyLight, int primaryBitMask, int additionalBitMask, byte[] chunkData) {
      ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];
      int dataPosition = 0;

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((primaryBitMask & 1 << i) != 0) {
            if (storageArrays[i] == null) {
               storageArrays[i] = new ExtendedBlockStorage(skyLight);
            }

            byte[] lsbArray = storageArrays[i].getBlockLSBArray();
            System.arraycopy(chunkData, dataPosition, lsbArray, 0, lsbArray.length);
            dataPosition += lsbArray.length;
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((primaryBitMask & 1 << i) != 0 && storageArrays[i] != null) {
            NibbleArray nibbleArray = storageArrays[i].getBlockMetadataArray();
            System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
            dataPosition += nibbleArray.getHandle().length;
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((primaryBitMask & 1 << i) != 0 && storageArrays[i] != null) {
            NibbleArray nibbleArray = storageArrays[i].getBlockLightArray();
            System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
            dataPosition += nibbleArray.getHandle().length;
         }
      }

      if (skyLight) {
         for(int i = 0; i < storageArrays.length; ++i) {
            if ((primaryBitMask & 1 << i) != 0 && storageArrays[i] != null) {
               NibbleArray nibbleArray = storageArrays[i].getSkyLightArray();
               System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
               dataPosition += nibbleArray.getHandle().length;
            }
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((additionalBitMask & 1 << i) != 0) {
            if (storageArrays[i] != null) {
               NibbleArray nibbleArray = storageArrays[i].getOrCreateBlockMSBArray();
               System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
               dataPosition += nibbleArray.getHandle().length;
            } else {
               dataPosition += 2048;
            }
         }
      }

      int[] biomeData = null;
      if (fullChunk) {
         biomeData = new int[256];

         for(int i = 0; i < biomeData.length; ++i) {
            biomeData[i] = chunkData[dataPosition + i] & 255;
         }

         int var10000 = dataPosition + biomeData.length;
      }

      ChunkSection[] sections = new ChunkSection[16];

      for(int i = 0; i < storageArrays.length; ++i) {
         ExtendedBlockStorage storage = storageArrays[i];
         if (storage != null) {
            ChunkSection section = sections[i] = new ChunkSectionImpl(true);
            section.palette(PaletteType.BLOCKS).addId(0);

            for(int x = 0; x < 16; ++x) {
               for(int z = 0; z < 16; ++z) {
                  for(int y = 0; y < 16; ++y) {
                     section.palette(PaletteType.BLOCKS).setIdAt(x, y, z, IdAndData.toRawData(storage.getBlockId(x, y, z), storage.getBlockMetadata(x, y, z)));
                  }
               }
            }

            section.getLight().setBlockLight(storage.getBlockLightArray().getHandle());
            if (skyLight) {
               section.getLight().setSkyLight(storage.getSkyLightArray().getHandle());
            }
         }
      }

      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitMask, sections, biomeData, new ArrayList());
   }

   public static Pair serialize(Chunk chunk) {
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
            output.writeBytes(storageArrays[i].getBlockLSBArray());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            output.writeBytes(storageArrays[i].getBlockMetadataArray().getHandle());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            output.writeBytes(storageArrays[i].getBlockLightArray().getHandle());
         }
      }

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0 && storageArrays[i].getSkyLightArray() != null) {
            output.writeBytes(storageArrays[i].getSkyLightArray().getHandle());
         }
      }

      short additionalBitMask = 0;

      for(int i = 0; i < storageArrays.length; ++i) {
         if ((chunk.getBitmask() & 1 << i) != 0 && storageArrays[i].hasBlockMSBArray()) {
            additionalBitMask = (short)(additionalBitMask | 1 << i);
            output.writeBytes(storageArrays[i].getOrCreateBlockMSBArray().getHandle());
         }
      }

      if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
         for(int biome : chunk.getBiomeData()) {
            output.write(biome);
         }
      }

      return new Pair(output.toByteArray(), additionalBitMask);
   }

   public static int getSize(short primaryBitMask, short additionalBitMask, boolean fullChunk, boolean skyLight) {
      int primarySectionCount = Integer.bitCount(primaryBitMask & '\uffff');
      int additionalSectionCount = Integer.bitCount(additionalBitMask & '\uffff');
      int size = 8192 * primarySectionCount + 2048 * additionalSectionCount;
      if (skyLight) {
         size += 2048 * primarySectionCount;
      }

      if (fullChunk) {
         size += 256;
      }

      return size;
   }
}
