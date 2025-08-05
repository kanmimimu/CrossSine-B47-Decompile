package com.viaversion.viaversion.api.type.types.block;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BlockEntityType1_18 extends Type {
   public BlockEntityType1_18() {
      super(BlockEntity.class);
   }

   public BlockEntity read(ByteBuf buffer) {
      byte xz = buffer.readByte();
      short y = buffer.readShort();
      int typeId = Types.VAR_INT.readPrimitive(buffer);
      CompoundTag tag = (CompoundTag)Types.NAMED_COMPOUND_TAG.read(buffer);
      return new BlockEntityImpl(xz, y, typeId, tag);
   }

   public void write(ByteBuf buffer, BlockEntity entity) {
      buffer.writeByte(entity.packedXZ());
      buffer.writeShort(entity.y());
      Types.VAR_INT.writePrimitive(buffer, entity.typeId());
      Types.NAMED_COMPOUND_TAG.write(buffer, entity.tag());
   }
}
