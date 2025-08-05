package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkSectionType1_8;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType1_8;

public final class Types1_8 {
   public static final Type ENTITY_DATA = new EntityDataType1_8();
   public static final Type ENTITY_DATA_LIST;
   public static final Type CHUNK_SECTION;

   static {
      ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
      CHUNK_SECTION = new ChunkSectionType1_8();
   }
}
