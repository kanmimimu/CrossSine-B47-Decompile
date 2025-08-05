package com.viaversion.viaversion.api.type.types.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;

public final class EntityDataListType extends EntityDataListTypeTemplate {
   private final Type type;

   public EntityDataListType(Type type) {
      Preconditions.checkNotNull(type);
      this.type = type;
   }

   public List read(ByteBuf buffer) {
      List<EntityData> list = new ArrayList();

      EntityData data;
      do {
         data = (EntityData)this.type.read(buffer);
         if (data != null) {
            list.add(data);
         }
      } while(data != null);

      return list;
   }

   public void write(ByteBuf buffer, List object) {
      for(EntityData data : object) {
         this.type.write(buffer, data);
      }

      this.type.write(buffer, (Object)null);
   }
}
