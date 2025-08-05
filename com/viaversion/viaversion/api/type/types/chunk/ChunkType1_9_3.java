package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.util.ChunkUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class ChunkType1_9_3 extends Type {
   private static final ChunkType1_9_3 WITH_SKYLIGHT = new ChunkType1_9_3(true);
   private static final ChunkType1_9_3 WITHOUT_SKYLIGHT = new ChunkType1_9_3(false);
   private final boolean hasSkyLight;

   public ChunkType1_9_3(boolean hasSkyLight) {
      super(Chunk.class);
      this.hasSkyLight = hasSkyLight;
   }

   public static ChunkType1_9_3 forEnvironment(Environment environment) {
      return environment == Environment.NORMAL ? WITH_SKYLIGHT : WITHOUT_SKYLIGHT;
   }

   public Chunk read(ByteBuf input) {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int primaryBitmask = Types.VAR_INT.readPrimitive(input);
      ByteBuf data = input.readSlice(Types.VAR_INT.readPrimitive(input));
      ChunkSection[] sections = new ChunkSection[16];
      int[] biomeData = fullChunk ? new int[256] : null;

      try {
         for(int i = 0; i < 16; ++i) {
            if ((primaryBitmask & 1 << i) != 0) {
               ChunkSection section = (ChunkSection)Types1_9.CHUNK_SECTION.read(data);
               sections[i] = section;
               section.getLight().readBlockLight(data);
               if (this.hasSkyLight) {
                  section.getLight().readSkyLight(data);
               }
            }
         }

         if (fullChunk) {
            for(int i = 0; i < 256; ++i) {
               biomeData[i] = data.readByte() & 255;
            }
         }
      } catch (Throwable e) {
         Via.getPlatform().getLogger().log(Level.WARNING, "The server sent an invalid chunk data packet, returning an empty chunk instead", e);
         return ChunkUtil.createEmptyChunk(chunkX, chunkZ);
      }

      List<CompoundTag> nbtData = new ArrayList(Arrays.asList((CompoundTag[])Types.NAMED_COMPOUND_TAG_ARRAY.read(input)));
      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      Types.VAR_INT.writePrimitive(output, chunk.getBitmask());
      ByteBuf buf = output.alloc().buffer();

      try {
         for(int i = 0; i < 16; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               Types1_9.CHUNK_SECTION.write(buf, section);
               section.getLight().writeBlockLight(buf);
               if (section.getLight().hasSkyLight()) {
                  section.getLight().writeSkyLight(buf);
               }
            }
         }

         buf.readerIndex(0);
         Types.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      if (chunk.isBiomeData()) {
         for(int biome : chunk.getBiomeData()) {
            output.writeByte((byte)biome);
         }
      }

      Types.NAMED_COMPOUND_TAG_ARRAY.write(output, (CompoundTag[])chunk.getBlockEntities().toArray(new CompoundTag[0]));
   }
}
