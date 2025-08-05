package com.viaversion.viarewind.api.type.version;

import com.viaversion.viarewind.api.type.entitydata.EntityDataListType;
import com.viaversion.viarewind.api.type.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;

public class Types1_7_6_10 {
   public static final Type ENTITY_DATA = new EntityDataType();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
