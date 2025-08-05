package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;

public abstract class EntityDataTypeTemplate extends Type {
   protected EntityDataTypeTemplate() {
      super("Entity data type", EntityData.class);
   }

   public Class getBaseClass() {
      return EntityDataTypeTemplate.class;
   }
}
