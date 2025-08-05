package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import io.netty.buffer.ByteBuf;

public abstract class OldEntityDataType extends EntityDataTypeTemplate {
   private static final int END = 127;

   public EntityData read(ByteBuf buffer) {
      byte index = buffer.readByte();
      if (index == 127) {
         return null;
      } else {
         com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType type = this.getType((index & 224) >> 5);
         return new EntityData(index & 31, type, type.type().read(buffer));
      }
   }

   protected abstract com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int var1);

   public void write(ByteBuf buffer, EntityData object) {
      if (object == null) {
         buffer.writeByte(127);
      } else {
         int index = (object.dataType().typeId() << 5 | object.id() & 31) & 255;
         buffer.writeByte(index);
         object.dataType().type().write(buffer, object.getValue());
      }

   }
}
