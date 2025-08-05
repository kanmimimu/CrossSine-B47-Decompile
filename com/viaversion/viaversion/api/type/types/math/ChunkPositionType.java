package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.ChunkPosition;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class ChunkPositionType extends Type {
   public ChunkPositionType() {
      super(ChunkPosition.class);
   }

   public ChunkPosition read(ByteBuf buffer) {
      long chunkKey = Types.LONG.readPrimitive(buffer);
      return new ChunkPosition(chunkKey);
   }

   public void write(ByteBuf buffer, ChunkPosition chunkPosition) {
      Types.LONG.writePrimitive(buffer, chunkPosition.chunkKey());
   }
}
