package net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;

public class Types1_6_4 {
   public static final Type STRING = new StringType();
   public static final Type ENTITY_DATA = new EntityDataType();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
