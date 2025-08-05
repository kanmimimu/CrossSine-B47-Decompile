package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_12;

public class EntityDataType1_12 extends ModernEntityDataType {
   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return EntityDataTypes1_12.byId(index);
   }
}
