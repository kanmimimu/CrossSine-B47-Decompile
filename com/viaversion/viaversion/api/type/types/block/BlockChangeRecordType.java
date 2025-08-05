package com.viaversion.viaversion.api.type.types.block;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BlockChangeRecordType extends Type {
   public BlockChangeRecordType() {
      super(BlockChangeRecord.class);
   }

   public BlockChangeRecord read(ByteBuf buffer) {
      short position = Types.SHORT.readPrimitive(buffer);
      int blockId = Types.VAR_INT.readPrimitive(buffer);
      return new BlockChangeRecord1_8(position >> 12 & 15, position & 255, position >> 8 & 15, blockId);
   }

   public void write(ByteBuf buffer, BlockChangeRecord object) {
      Types.SHORT.writePrimitive(buffer, (short)(object.getSectionX() << 12 | object.getSectionZ() << 8 | object.getY()));
      Types.VAR_INT.writePrimitive(buffer, object.getBlockId());
   }
}
