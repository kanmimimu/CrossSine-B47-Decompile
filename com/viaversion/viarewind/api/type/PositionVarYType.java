package com.viaversion.viarewind.api.type;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;

public class PositionVarYType extends Type {
   private final Type yType;
   private final IntFunction toY;

   public PositionVarYType(Type yType, IntFunction toY) {
      super(BlockPosition.class);
      this.yType = yType;
      this.toY = toY;
   }

   public BlockPosition read(ByteBuf buffer) {
      int x = buffer.readInt();
      int y = ((Number)this.yType.read(buffer)).intValue();
      int z = buffer.readInt();
      return new BlockPosition(x, y, z);
   }

   public void write(ByteBuf buffer, BlockPosition value) {
      buffer.writeInt(value.x());
      this.yType.write(buffer, (Number)this.toY.apply(value.y()));
      buffer.writeInt(value.z());
   }
}
