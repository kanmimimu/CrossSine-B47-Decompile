package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.type.Type;
import java.util.List;

public abstract class EntityDataListTypeTemplate extends Type {
   protected EntityDataListTypeTemplate() {
      super("Entity data list", List.class);
   }

   public Class getBaseClass() {
      return EntityDataListTypeTemplate.class;
   }
}
