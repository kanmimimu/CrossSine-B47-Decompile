package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;

public abstract class AbstractEntityDataTypes implements EntityDataTypes {
   private final EntityDataType[] values;

   protected AbstractEntityDataTypes(int values) {
      this.values = new EntityDataType[values];
   }

   public EntityDataType byId(int id) {
      return this.values[id];
   }

   public EntityDataType[] values() {
      return this.values;
   }

   protected EntityDataType add(int typeId, Type type) {
      EntityDataType dataType = EntityDataType.create(typeId, type);
      this.values[typeId] = dataType;
      return dataType;
   }
}
