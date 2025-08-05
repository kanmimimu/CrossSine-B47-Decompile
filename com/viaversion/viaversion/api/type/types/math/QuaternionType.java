package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class QuaternionType extends Type {
   public QuaternionType() {
      super(Quaternion.class);
   }

   public Quaternion read(ByteBuf buffer) {
      float x = buffer.readFloat();
      float y = buffer.readFloat();
      float z = buffer.readFloat();
      float w = buffer.readFloat();
      return new Quaternion(x, y, z, w);
   }

   public void write(ByteBuf buffer, Quaternion object) {
      buffer.writeFloat(object.x());
      buffer.writeFloat(object.y());
      buffer.writeFloat(object.z());
      buffer.writeFloat(object.w());
   }
}
