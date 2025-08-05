package net.raphimc.vialegacy.protocol.beta.b1_4_0_1tob1_5_0_2.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;

public class Typesb1_4 {
   public static final Type ENTITY_DATA = new EntityDataType();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
