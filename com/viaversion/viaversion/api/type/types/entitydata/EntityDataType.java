package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes;

public final class EntityDataType extends ModernEntityDataType {
   private final EntityDataTypes dataTypes;

   public EntityDataType(EntityDataTypes dataTypes) {
      this.dataTypes = dataTypes;
   }

   protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index) {
      return this.dataTypes.byId(index);
   }
}
