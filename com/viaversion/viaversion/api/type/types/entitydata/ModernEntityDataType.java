package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public abstract class ModernEntityDataType extends EntityDataTypeTemplate {
   private static final int END = 255;

   public EntityData read(ByteBuf buffer) {
      short index = buffer.readUnsignedByte();
      if (index == 255) {
         return null;
      } else {
         com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType type = this.getType(Types.VAR_INT.readPrimitive(buffer));
         return new EntityData(index, type, type.type().read(buffer));
      }
   }

   protected abstract com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int var1);

   public void write(ByteBuf buffer, EntityData object) {
      if (object == null) {
         buffer.writeByte(255);
      } else {
         buffer.writeByte(object.id());
         com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType type = object.dataType();
         Types.VAR_INT.writePrimitive(buffer, type.typeId());
         type.type().write(buffer, object.getValue());
      }

   }
}
