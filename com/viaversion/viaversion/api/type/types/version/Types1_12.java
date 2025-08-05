package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType1_12;

public final class Types1_12 {
   public static final Type ENTITY_DATA = new EntityDataType1_12();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
