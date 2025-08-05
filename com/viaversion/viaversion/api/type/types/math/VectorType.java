package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class VectorType extends Type {
   public VectorType() {
      super(Vector.class);
   }

   public Vector read(ByteBuf buffer) {
      int x = Types.INT.read(buffer);
      int y = Types.INT.read(buffer);
      int z = Types.INT.read(buffer);
      return new Vector(x, y, z);
   }

   public void write(ByteBuf buffer, Vector object) {
      Types.INT.write(buffer, object.blockX());
      Types.INT.write(buffer, object.blockY());
      Types.INT.write(buffer, object.blockZ());
   }
}
