package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;

public class Typesb1_2 {
   public static final Type ENTITY_DATA = new EntityDataType();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
