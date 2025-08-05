package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;

public class EntityDataType1_9 extends ModernEntityDataType {
   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return EntityDataTypes1_9.byId(index);
   }
}
