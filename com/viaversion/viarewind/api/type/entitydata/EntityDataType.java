package com.viaversion.viarewind.api.type.entitydata;

import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataTypeTemplate;
import io.netty.buffer.ByteBuf;

public class EntityDataType extends EntityDataTypeTemplate {
   public EntityData read(ByteBuf buffer) {
      byte item = buffer.readByte();
      if (item == 127) {
         return null;
      } else {
         int typeID = (item & 224) >> 5;
         EntityDataTypes1_7_6_10 type = EntityDataTypes1_7_6_10.byId(typeID);
         int id = item & 31;
         return new EntityData(id, type, type.type().read(buffer));
      }
   }

   public void write(ByteBuf buffer, EntityData meta) {
      int item = (meta.dataType().typeId() << 5 | meta.id() & 31) & 255;
      buffer.writeByte(item);
      meta.dataType().type().write(buffer, meta.getValue());
   }
}
