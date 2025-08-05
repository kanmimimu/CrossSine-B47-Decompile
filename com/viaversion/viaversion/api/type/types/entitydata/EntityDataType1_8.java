package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;

public class EntityDataType1_8 extends OldEntityDataType {
   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return EntityDataTypes1_8.byId(index);
   }
}
