package net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;

public class Types1_3_1 {
   public static final Type NBTLESS_ITEM = new NbtLessItemType();
   public static final Type ENTITY_DATA = new EntityDataType();
   public static final Type ENTITY_DATA_LIST;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
