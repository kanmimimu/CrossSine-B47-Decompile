package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ItemArrayType;

public class Types1_4_2 {
   public static final Type UNSIGNED_BYTE_BYTE_ARRAY = new UnsignedByteByteArrayType();
   public static final Type NBTLESS_ITEM = new NbtLessItemType();
   public static final Type NBTLESS_ITEM_ARRAY;
   public static final Type ENTITY_DATA;
   public static final Type ENTITY_DATA_LIST;

   static {
      NBTLESS_ITEM_ARRAY = new ItemArrayType(NBTLESS_ITEM);
      ENTITY_DATA = new EntityDataType();
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
   }
}
