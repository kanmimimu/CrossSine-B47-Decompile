package com.viaversion.viarewind.api.type.entitydata;

import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListTypeTemplate;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public class EntityDataListType extends EntityDataListTypeTemplate {
   private final Type type;

   public EntityDataListType(Type type) {
      this.type = type;
   }

   public List read(ByteBuf buffer) {
      ArrayList<EntityData> entityData = new ArrayList();

      EntityData data;
      do {
         data = (EntityData)this.type.read(buffer);
         if (data != null) {
            entityData.add(data);
         }
      } while(data != null);

      return entityData;
   }

   public void write(ByteBuf buffer, List entityData) {
      for(EntityData meta : entityData) {
         this.type.write(buffer, meta);
      }

      if (entityData.isEmpty()) {
         this.type.write(buffer, new EntityData(0, EntityDataTypes1_7_6_10.BYTE, (byte)0));
      }

      buffer.writeByte(127);
   }
}
