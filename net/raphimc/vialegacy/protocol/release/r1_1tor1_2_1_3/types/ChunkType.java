package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdAndData;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.LegacyNibbleArray;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.NonFullChunk;

public class ChunkType extends Type {
   public ChunkType() {
      super(Chunk.class);
   }

   public Chunk read(ByteBuf byteBuf) {
      int xPosition = byteBuf.readInt();
      int yPosition = byteBuf.readShort();
      int zPosition = byteBuf.readInt();
      int xSize = byteBuf.readUnsignedByte() + 1;
      int ySize = byteBuf.readUnsignedByte() + 1;
      int zSize = byteBuf.readUnsignedByte() + 1;
      int chunkSize = byteBuf.readInt();
      byte[] compressedData = new byte[chunkSize];
      byteBuf.readBytes(compressedData);
      byte[] uncompressedData = new byte[xSize * ySize * zSize * 5 / 2];
      Inflater inflater = new Inflater();
      inflater.setInput(compressedData);

      try {
         inflater.inflate(uncompressedData);
      } catch (DataFormatException var16) {
         throw new RuntimeException("Bad compressed data format");
      } finally {
         inflater.end();
      }

      return deserialize(xPosition, yPosition, zPosition, xSize, ySize, zSize, uncompressedData);
   }

   public void write(ByteBuf byteBuf, Chunk chunk) {
      throw new UnsupportedOperationException();
   }

   public static Chunk deserialize(int xPosition, int yPosition, int zPosition, int xSize, int ySize, int zSize, byte[] chunkData) {
      int chunkX = xPosition >> 4;
      int chunkZ = zPosition >> 4;
      int endChunkX = xPosition + xSize - 1 >> 4;
      int endChunkZ = zPosition + zSize - 1 >> 4;
      int startX = Math.max(xPosition - chunkX * 16, 0);
      int endX = Math.min(xPosition + xSize - chunkX * 16, 16);
      int startY = Math.max(yPosition, 0);
      int endY = Math.min(yPosition + ySize, 128);
      int startZ = Math.max(zPosition - chunkZ * 16, 0);
      int endZ = Math.min(zPosition + zSize - chunkZ * 16, 16);
      boolean fullChunk = (xPosition & 15) == 0 && yPosition == 0 && (zPosition & 15) == 0 && xSize == 16 && ySize == 128 && zSize == 16;
      byte[] blockArray = new byte['耀'];
      LegacyNibbleArray blockDataArray = new LegacyNibbleArray(blockArray.length);
      LegacyNibbleArray blockLightArray = new LegacyNibbleArray(blockArray.length);
      LegacyNibbleArray skyLightArray = new LegacyNibbleArray(blockArray.length);
      if (fullChunk) {
         int blockDataOffset = blockArray.length;
         int blockLightOffset = blockArray.length + blockDataArray.getHandle().length;
         int skyLightOffset = chunkData.length - blockLightArray.getHandle().length;
         System.arraycopy(chunkData, 0, blockArray, 0, blockArray.length);
         System.arraycopy(chunkData, blockDataOffset, blockDataArray.getHandle(), 0, blockDataArray.getHandle().length);
         System.arraycopy(chunkData, blockLightOffset, blockLightArray.getHandle(), 0, blockLightArray.getHandle().length);
         System.arraycopy(chunkData, skyLightOffset, skyLightArray.getHandle(), 0, skyLightArray.getHandle().length);
      } else {
         if (chunkX != endChunkX || chunkZ != endChunkZ) {
            throw new IllegalStateException("Can't decode 1.1 non full-chunk which spans over multiple chunks");
         }

         int offset = 0;

         for(int x = startX; x < endX; ++x) {
            for(int z = startZ; z < endZ; ++z) {
               int index = x << 11 | z << 7 | startY;
               int size = endY - startY;
               System.arraycopy(chunkData, offset, blockArray, index, size);
               offset += size;
            }
         }

         for(int x = startX; x < endX; ++x) {
            for(int z = startZ; z < endZ; ++z) {
               int index = (x << 11 | z << 7 | startY) >> 1;
               int size = (endY - startY) / 2;
               System.arraycopy(chunkData, offset, blockDataArray.getHandle(), index, size);
               offset += size;
            }
         }

         for(int x = startX; x < endX; ++x) {
            for(int z = startZ; z < endZ; ++z) {
               int index = (x << 11 | z << 7 | startY) >> 1;
               int size = (endY - startY) / 2;
               System.arraycopy(chunkData, offset, blockLightArray.getHandle(), index, size);
               offset += size;
            }
         }

         for(int x = startX; x < endX; ++x) {
            for(int z = startZ; z < endZ; ++z) {
               int index = (x << 11 | z << 7 | startY) >> 1;
               int size = (endY - startY) / 2;
               System.arraycopy(chunkData, offset, skyLightArray.getHandle(), index, size);
               offset += size;
            }
         }
      }

      ChunkSection[] modernSections = new ChunkSection[8];
      int bitmask = 0;
      ChunkSection section = null;
      LegacyNibbleArray sectionSkyLight = null;
      LegacyNibbleArray sectionBlockLight = null;

      for(int y = startY; y < endY; ++y) {
         if (section == null || y % 16 == 0) {
            int sectionY = y >> 4;
            bitmask |= 1 << sectionY;
            section = modernSections[sectionY] = new ChunkSectionImpl(true);
            section.palette(PaletteType.BLOCKS).addId(0);
            sectionSkyLight = new LegacyNibbleArray(4096, 4);
            sectionBlockLight = new LegacyNibbleArray(4096, 4);
         }

         for(int x = startX; x < endX; ++x) {
            for(int z = startZ; z < endZ; ++z) {
               section.palette(PaletteType.BLOCKS).setIdAt(x, y & 15, z, IdAndData.toRawData(blockArray[x << 11 | z << 7 | y] & 255, blockDataArray.get(x, y, z)));
               sectionSkyLight.set(x, y & 15, z, skyLightArray.get(x, y, z));
               sectionBlockLight.set(x, y & 15, z, blockLightArray.get(x, y, z));
            }
         }

         section.getLight().setBlockLight(sectionBlockLight.getHandle());
         section.getLight().setSkyLight(sectionSkyLight.getHandle());
      }

      if (fullChunk) {
         return new BaseChunk(chunkX, chunkZ, true, false, 255, modernSections, new int[256], new ArrayList());
      } else {
         return new NonFullChunk(chunkX, chunkZ, bitmask, modernSections, new BlockPosition(startX, startY, startZ), new BlockPosition(endX, endY, endZ));
      }
   }
}
